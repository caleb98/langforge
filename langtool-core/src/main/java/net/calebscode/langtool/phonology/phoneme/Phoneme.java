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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Phoneme(String representation, Map<String, PhonemeFeature> features) {

	private static final List<String> CONSONANT_PRIMARY_FEATURES = List.of(FEATURE_VOICING, FEATURE_ARTICULATION_PLACE, FEATURE_ARTICULATION_TYPE);
	private static final List<String> VOWEL_PRIMARY_FEATURES = List.of(FEATURE_OPENNESS, FEATURE_BACKNESS, FEATURE_ROUNDEDNESS, FEATURE_PHONEME_CATEGORY);

	private static final Predicate<PhonemeFeature> CONSONANT_IGNORED_FEATURES;
	private static final Predicate<PhonemeFeature> VOWEL_IGNORED_FEATURES;

	static {
		List<Predicate<PhonemeFeature>> consonantIgnores = List.of(
			// Don't label consonants as consonants (implied)
			(feature) -> feature.featureType().equals(FEATURE_PHONEME_CATEGORY)
		);
		CONSONANT_IGNORED_FEATURES = consonantIgnores.stream().reduce(Predicate::or).orElse(x -> false);

		List<Predicate<PhonemeFeature>> vowelIgnores = List.of(
			// Don't label voiced vowels - only unvoiced (voiced is implied)
			(feature) -> feature.featureType().equals(FEATURE_VOICING) && feature.equals(VOICING_VOICED)
		);
		VOWEL_IGNORED_FEATURES = vowelIgnores.stream().reduce(Predicate::or).orElse(x -> false);
	}

	public Phoneme(String representation, Map<String, PhonemeFeature> features) {
		this.representation = representation;
		this.features = Collections.unmodifiableMap(features);
	}
	
	public Phoneme(String representation, PhonemeFeature... features) {
		this(representation, Arrays.stream(features).collect(Collectors.toMap(PhonemeFeature::featureType, feat -> feat)));
	}

	public boolean hasFeature(String featureType) {
		return features.containsKey(featureType);
	}

	public PhonemeFeature getFeature(String featureType) {
		return features.get(featureType);
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

	private String getPrimaryFeatures(Collection<String> primaryFeatures) {
		return primaryFeatures.stream()
			.map(featureName -> features.getOrDefault(featureName, null))
			.filter(feature -> feature != null)
			.map(feature -> feature.featureValue())
			.collect(Collectors.joining(" "));
	}

	private String getSecondaryFeatures(Collection<String> primaryFeatures, Predicate<PhonemeFeature> ignore) {
		return features.entrySet().stream()
			.filter(featureEntry -> !primaryFeatures.contains(featureEntry.getKey()))
			.filter(featureEntry -> !ignore.test(featureEntry.getValue()))
			.sorted((a, b) -> a.getKey().compareTo(b.getKey()))
			.map(featureEntry -> featureEntry.getValue())
			.map(feature -> feature.featureValue())
			.collect(Collectors.joining(" "));
	}

}
