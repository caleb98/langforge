package net.calebscode.langtool.phonology.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeFeature;
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
	private Map<BindKey, String> binds = new HashMap<>();

	public PhonologicalRuleMatcher(PhonologicalRule rule) {
		this.rule = rule;
	}

	public void apply(PhonemeSequence sequence) {
		position = 0;
		binds.clear();

		this.sequence = sequence;

		for (int start = 0; start < sequence.length(); start++) {
			if (tryMatch(start)) {
				System.out.println("Found match at position " + start + "!");
			}
		}
	}

	private boolean tryMatch(int start) {
		position = start;
		for (var representation : rule.getPreMatches()) {
			if (!representation.match(this)) {
				return false;
			}
		}

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

		var phonemeFeatureValues = phoneme.features().values().stream()
				.map(PhonemeFeature::featureValue)
				.collect(Collectors.toSet());

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
			var phonemeFeatureValue = phoneme.getFeatureValue(boundFeature.featureType());

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
		return !feature.featureType().isEmpty()
				&& phoneme.hasFeature(feature.featureType())
				&& Objects.equals(
					phoneme.getFeature(feature.featureType()).featureValue(),
					feature.featureValue()
				);
	}

	private static boolean matchesAnyFeatureValue(Set<String> phonemeFeatureValues, Feature feature) {
		return feature.featureType().isEmpty()
				&& phonemeFeatureValues.contains(feature.featureValue());
	}

	private void bindFeature(Feature feature, String featureValue) {
		binds.put(new BindKey(feature.bindNumber(), feature.featureType()), featureValue);
	}

	private Optional<String> getBindValue(Feature feature) {
		return Optional.ofNullable(binds.get(new BindKey(feature.bindNumber(), feature.featureType())));
	}

	private record BindKey(int bindNumber, String featureType) {}

}
