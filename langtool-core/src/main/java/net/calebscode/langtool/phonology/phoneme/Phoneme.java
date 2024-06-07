package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.CATEGORY_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_ARTICULATION_PLACE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_ARTICULATION_TYPE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_BACKNESS;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_OPENNESS;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_PHONEME_CATEGORY;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_ROUNDEDNESS;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_VOICING;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.VOICING_VOICED;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Phoneme {

	private static final PhonemeFeature MISSING_FEATURE = new PhonemeFeature(null, null);
	private static final List<String> CONSONANT_PRIMARY_FEATURES = List.of(FEATURE_VOICING, FEATURE_ARTICULATION_PLACE, FEATURE_ARTICULATION_TYPE);
	private static final List<String> VOWEL_PRIMARY_FEATURES = List.of(FEATURE_OPENNESS, FEATURE_BACKNESS, FEATURE_ROUNDEDNESS, FEATURE_PHONEME_CATEGORY);

	private static final Predicate<PhonemeFeature> CONSONANT_IGNORED_FEATURES;
	private static final Predicate<PhonemeFeature> VOWEL_IGNORED_FEATURES;

	static {
		List<Predicate<PhonemeFeature>> consonantIgnores = List.of(
			// Don't label consonants as consonants (implied)
			(feature) -> feature.featureType.equals(FEATURE_PHONEME_CATEGORY)
		);
		CONSONANT_IGNORED_FEATURES = consonantIgnores.stream().reduce(Predicate::or).orElse(x -> false);

		List<Predicate<PhonemeFeature>> vowelIgnores = List.of(
			// Don't label voiced vowels - only unvoiced (voiced is implied)
			(feature) -> feature.featureType.equals(FEATURE_VOICING) && feature.equals(VOICING_VOICED)
		);
		VOWEL_IGNORED_FEATURES = vowelIgnores.stream().reduce(Predicate::or).orElse(x -> false);
	}

	private String representation;
	private Map<String, PhonemeFeature> features = new HashMap<>();

	public Phoneme(String representation, PhonemeFeature... features) {
		this.representation = representation;

		for (var feature : features) {
			this.features.put(feature.featureType, feature);
		}
	}

	public boolean hasFeature(String featureType) {
		return features.containsKey(featureType);
	}

	public PhonemeFeature getFeature(String featureName) {
		return features.get(featureName);
	}

	public String getName() {
		String primaryFeatures;
		String secondaryFeatures;

		if (Objects.equals(features.get(FEATURE_PHONEME_CATEGORY), CATEGORY_VOWEL)) {
			primaryFeatures = getPrimaryFeatures(VOWEL_PRIMARY_FEATURES);
			secondaryFeatures = getSecondaryFeatures(VOWEL_PRIMARY_FEATURES, VOWEL_IGNORED_FEATURES);
		}
		else {
			primaryFeatures = getPrimaryFeatures(CONSONANT_PRIMARY_FEATURES);
			secondaryFeatures = getSecondaryFeatures(CONSONANT_PRIMARY_FEATURES, CONSONANT_IGNORED_FEATURES);
		}

		if (primaryFeatures.isBlank() && secondaryFeatures.isBlank()) {
			return String.format("[%s]", representation);
		}
		else if (secondaryFeatures.isBlank()) {
			return primaryFeatures;
		}
		else if (primaryFeatures.isBlank()) {
			return secondaryFeatures;
		}
		else {
			return String.format("%s (%s)", primaryFeatures, secondaryFeatures);
		}
	}

	@Override
	public String toString() {
		return representation;
	}

	public String getRepresentation() {
		return representation;
	}

	public Map<String, PhonemeFeature> getFeatures() {
		return Collections.unmodifiableMap(features);
	}

	private String getPrimaryFeatures(Collection<String> primaryFeatures) {
		return primaryFeatures.stream()
			.map(featureName -> features.getOrDefault(featureName, MISSING_FEATURE))
			.filter(feature -> feature != MISSING_FEATURE)
			.map(feature -> feature.featureValue)
			.collect(Collectors.joining(" "));
	}

	private String getSecondaryFeatures(Collection<String> primaryFeatures, Predicate<PhonemeFeature> ignore) {
		return features.entrySet().stream()
			.filter(featureEntry -> !primaryFeatures.contains(featureEntry.getKey()))
			.filter(featureEntry -> !ignore.test(featureEntry.getValue()))
			.sorted((a, b) -> a.getKey().compareTo(b.getKey()))
			.map(featureEntry -> featureEntry.getValue())
			.map(feature -> feature.featureValue)
			.collect(Collectors.joining(" "));
	}

}
