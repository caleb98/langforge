package net.calebscode.langforge.phonology.phoneme;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/// Represents a single phoneme. A phoneme is a collection of feature names, each associated with
/// a value.
public record Phoneme(Map<String, String> features) implements PhonemeSequence {

	public Phoneme(Map<String, String> features) {
		Objects.requireNonNull(features, "features may not be null");
		boolean containsNullKeyOrValue = features
				.entrySet()
				.stream()
				.anyMatch(e -> e.getKey() == null || e.getValue() == null);
		
		if (containsNullKeyOrValue) {
			throw new IllegalArgumentException("Feature names and values must not be null.");
		}
		
		this.features = Collections.unmodifiableMap(features);
	}

	public boolean hasFeature(String featureName) {
		return features.containsKey(featureName);
	}

	public boolean featureValueMatches(String featureName, String featureValue) {
		return getFeatureValue(featureName).map(featureValue::equals).orElse(false);
	}

	public Optional<String> getFeatureValue(String featureName) {
		return Optional.ofNullable(features.get(featureName));
	}
	
	public Phoneme withFeature(String featureName, String featureValue) {
		if (featureValueMatches(featureName, featureValue)) {
			return this;
		}
		
		var newFeatures = new HashMap<>(features);
		newFeatures.put(featureName, featureValue);
		
		return new Phoneme(newFeatures);
	}

	public Phoneme withoutFeature(String featureName) {
		if (!hasFeature(featureName)) {
			return this;
		}

		var newFeatures = new HashMap<>(features);
		newFeatures.remove(featureName);

		return new Phoneme(newFeatures);
	}

	@Override
	public List<Phoneme> getPhonemes() {
		return List.of(this);
	}

}
