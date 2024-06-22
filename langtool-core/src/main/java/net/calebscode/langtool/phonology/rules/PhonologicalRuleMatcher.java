package net.calebscode.langtool.phonology.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.Feature;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeFeatureset;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeLiteral;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeRepresentationMatcher;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.SyllableBoundary;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.WordBoundary;

public class PhonologicalRuleMatcher implements PhonemeRepresentationMatcher {

	private PhonologicalRule rule;

	private PhonemeSequence sequence;
	private int position;
	private int replacePosition;
	private Map<BindKey, String> binds = new HashMap<>();

	public PhonologicalRuleMatcher(PhonologicalRule rule) {
		this.rule = rule;
	}

	public PhonemeSequence apply(PhonemeSequence inputSequence) {
		position = 0;
		binds.clear();
		sequence = inputSequence;

		System.out.println("Applying rule " + rule.getSource() + " to " + sequence);

		for (int start = 0; start < sequence.length(); start++) {
			replacePosition = -1;
			if (tryMatch(start)) {
				System.out.printf("Found match at position %d. Replace at %d: ", start, replacePosition, position);
				var oldSeq = sequence;

				sequence = switch(rule.getReplacement()) {
					case PhonemeLiteral literal -> sequence.replaceAt(replacePosition, literal.phoneme());
					case PhonemeFeatureset features -> replaceFeatures(sequence, features);

					case null -> throw new RuntimeException("Invalid match type: null");
					default -> throw new RuntimeException("Invalid match type: " + rule.getMatch().getClass());
				};

				// Only restart processing if something actually changed
				if (!oldSeq.equals(sequence)) {
					start = -1;
					System.out.printf("%s -> %s\n", oldSeq, sequence);
				}
				else {
					System.out.println("Match caused no change.");
				}
			}
		}

		return sequence;
	}

	private PhonemeSequence replaceFeatures(PhonemeSequence sequence, PhonemeFeatureset featureset) {
		Map<String, String> newFeatures = new HashMap<>(sequence.phonemeAt(replacePosition).features());

		for (var feature : featureset.features()) {
			boolean isBound = feature.bindNumber() != -1;

			// Not bound, not negated - just a regular feature
			if (!isBound && !feature.negate()) {
				newFeatures.put(feature.featureName(), feature.featureValue());
			}

			// Regular feature, but negated
			else if (!isBound && feature.negate()) {
				if (feature.featureName().isBlank()) {
					while(newFeatures.values().remove(feature.featureValue())) {}
				} else {
					newFeatures.remove(feature.featureName(), feature.featureValue());
				}
			}

			// Bound and not negated, so we need to add the bound value
			// to this feature name
			else if (isBound && !feature.negate()) {
				var bindValue = getBindValue(feature);
				if (bindValue.isEmpty()) {
					throw new RuntimeException("No bound value for replacment of feature type " + feature.featureName());
				}
				newFeatures.put(feature.featureName(), bindValue.get());
			}

			// Bound and negated. Remove the feature matching the bound feature name/value
			else {
				var bindValue = getBindValue(feature);
				if (bindValue.isEmpty()) {
					throw new RuntimeException("No bound value for replacment of feature type " + feature.featureName());
				}
				newFeatures.remove(feature.featureName(), bindValue.get());
			}
		}

		return sequence.replaceAt(replacePosition, new Phoneme(newFeatures));
	}

	private boolean tryMatch(int start) {
		position = start;
		for (var representation : rule.getPreMatches()) {
			if (!representation.match(this)) {
				return false;
			}
		}

		replacePosition = position;
		if (!rule.getMatch().match(this)) {
			return false;
		}

		for (var representation : rule.getPostMatches()) {
			if (!representation.match(this)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean matchesWordBoundary(WordBoundary wordBoundary) {
		return sequence.getTransition(position - 1).crossedWordBoundary();
	}

	@Override
	public boolean matchesSyllableBoundary(SyllableBoundary syllableBoundary) {
		return sequence.getTransition(position - 1).crossedSyllableBoundary();
	}

	@Override
	public boolean matchesPhonemeLiteral(PhonemeLiteral phonemeLiteral) {
		boolean matches = position < sequence.length() && phonemeLiteral.phoneme().equals(sequence.phonemeAt(position));
		position++;
		return matches;
	}

	@Override
	public boolean matchesPhonemeFeatureset(PhonemeFeatureset phonemeFeatureset) {
		if (position >= sequence.length()) {
			return false;
		}

		var phoneme = sequence.phonemeAt(position);

		// TODO handle negation

		var phonemeFeatureValues = new HashSet<>(phoneme.features().values());

		// Split into bound and unbound features
		var bindGroups = phonemeFeatureset.features().stream().collect(Collectors.partitioningBy(feature -> feature.bindNumber() != -1));
		var boundFeatures = bindGroups.get(true);
		var unboundFeatures = bindGroups.get(false);

		// Match against unbound features
		for (var feature : unboundFeatures) {
			if (!matchesAnyFeatureValue(phonemeFeatureValues, feature)
				&& !matchesExactFeatureValue(phoneme, feature)) {
				return false;
			}
		}

		for (var boundFeature : boundFeatures) {
			var bindData = getBindValue(boundFeature);
			var phonemeFeatureValue = phoneme.getFeatureValue(boundFeature.featureName());

			// If the phoneme doesn't even have this feature, it's impossible to match. We can't
			// bind to it since it has no value for the feature, and we also can't match against
			// it since it has no value.
			if (phonemeFeatureValue.isEmpty()) {
				return false;
			}
			// Bind the feature
			else if (bindData.isEmpty()) {
				bindFeature(boundFeature, phonemeFeatureValue.get());
			}
			// Match against the already-bound feature
			else if (!bindData.get().equals(phonemeFeatureValue.get())) {
				return false;
			}
		}

		position++;
		return true;
	}

	private static boolean matchesExactFeatureValue(Phoneme phoneme, Feature feature) {
		return !feature.featureName().isEmpty()
				&& phoneme.featureValueMatches(feature.featureName(), feature.featureValue());
	}

	private static boolean matchesAnyFeatureValue(Set<String> phonemeFeatureValues, Feature feature) {
		return feature.featureName().isEmpty()
				&& phonemeFeatureValues.contains(feature.featureValue());
	}

	private void bindFeature(Feature feature, String featureValue) {
		binds.put(new BindKey(feature.bindNumber(), feature.featureName()), featureValue);
	}

	private Optional<String> getBindValue(Feature feature) {
		return Optional.ofNullable(binds.get(new BindKey(feature.bindNumber(), feature.featureName())));
	}

	private record BindKey(int bindNumber, String featureType) {}

}
