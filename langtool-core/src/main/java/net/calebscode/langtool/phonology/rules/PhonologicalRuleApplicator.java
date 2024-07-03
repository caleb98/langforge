package net.calebscode.langtool.phonology.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.PhonemeSequenceValidationException;
import net.calebscode.langtool.phonology.PhonemeSequenceValidator;
import net.calebscode.langtool.phonology.PhonologicalRuleApplicationException;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.Feature;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.NullPhoneme;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeFeatureset;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeLiteral;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeRepresentationMatcher;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.SyllableBoundary;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.WordBoundary;

public class PhonologicalRuleApplicator implements PhonemeRepresentationMatcher {

	private PhonologicalRule rule;

	private PhonemeSequence sequence;
	private int position;
	private int replacePosition;
	private Map<BindKey, String> binds = new HashMap<>();

	public PhonologicalRuleApplicator(PhonologicalRule rule) {
		this.rule = rule;
	}

	/**
	 * @param inputSequence
	 * @param validator
	 * @param lenient whether or the rule application should fail if a sequence cannot be validated
	 * @return
	 * @throws PhonologicalRuleApplicationException
	 */
	public PhonemeSequence apply(PhonemeSequence inputSequence, PhonemeSequenceValidator validator, boolean lenient) throws PhonologicalRuleApplicationException {
		position = 0;
		sequence = inputSequence;

		for (int start = 0; start < sequence.length(); start++) {
			binds.clear();
			replacePosition = -1;
			if (tryMatch(start)) {
				var oldSeq = sequence;

				// Check for insertion
				if (rule.getMatch() instanceof NullPhoneme) {
					sequence = switch(rule.getReplacement()) {
						case NullPhoneme nullPhoneme -> sequence; // TODO: warning here? The rule does nothing since it's replacing nothing with nothing...
						case PhonemeLiteral literal -> insertPhoneme(literal);

						case PhonemeFeatureset f -> throw new PhonologicalRuleApplicationException("Can only apply insertion rule when replacement is a phoneme literal.");
						case null -> throw new PhonologicalRuleApplicationException("Invalid replacement type: null");
						default -> throw new PhonologicalRuleApplicationException("Invalid replacement type: " + rule.getMatch().getClass());
					};
				}
				// Otherwise, we're updating the existing feature
				else {
					sequence = switch(rule.getReplacement()) {
						case NullPhoneme nullPhoneme -> deletePhoneme();
						case PhonemeLiteral literal -> sequence.replaceAt(replacePosition, literal.phoneme());
						case PhonemeFeatureset features -> replaceFeatures(features);

						case null -> throw new PhonologicalRuleApplicationException("Invalid replacement type: null");
						default -> throw new PhonologicalRuleApplicationException("Invalid replacement type: " + rule.getMatch().getClass());
					};
				}

				// Only restart processing if something actually changed
				if (!oldSeq.equals(sequence)) {
					start = -1;
					try {
						sequence = validator.validate(sequence);
					} catch (PhonemeSequenceValidationException ex) {
						if (!lenient) {
							 var message = String.format("Unable to apply rule: %s", ex.getMessage());
							 throw new PhonologicalRuleApplicationException(message, ex);
						}
					}
				}
			}
		}

		try {
			sequence = validator.validate(sequence);
		} catch (PhonemeSequenceValidationException ex) {
			if (!lenient) {
				 var message = String.format("Unable to apply rule: %s", ex.getMessage());
				 throw new PhonologicalRuleApplicationException(message, ex);
			}
		}
		return sequence;
	}

	private PhonemeSequence deletePhoneme() {
		var front = sequence.subsequence(0, replacePosition);
		var back = sequence.subsequence(replacePosition + 1);

		return front.append(back);
	}

	private PhonemeSequence insertPhoneme(PhonemeLiteral literal) {
		var front = sequence.subsequence(0, replacePosition);
		var back = sequence.subsequence(replacePosition);

		Phoneme insert = literal.phoneme();
		var insertSeq = new PhonemeSequenceBuilder().append(insert).build();

		return front.append(insertSeq).append(back);
	}

	private PhonemeSequence replaceFeatures(PhonemeFeatureset featureset) throws PhonologicalRuleApplicationException {
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
					throw new PhonologicalRuleApplicationException("No bound value for replacment of feature type " + feature.featureName());
				}
				newFeatures.put(feature.featureName(), bindValue.get());
			}

			// Bound and negated. Remove the feature matching the bound feature name/value
			else {
				var bindValue = getBindValue(feature);
				if (bindValue.isEmpty()) {
					throw new PhonologicalRuleApplicationException("No bound value for replacment of feature type " + feature.featureName());
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
	public boolean matchesNullPhoneme(NullPhoneme nullPhoneme) {
		return true;
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
