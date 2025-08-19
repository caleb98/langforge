package net.calebscode.langforge.phonology.phoneme;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record Phoneme(Map<String, String> features) implements PhonemeSequence {

	public Phoneme(Map<String, String> features) {
		this.features = Collections.unmodifiableMap(features);
	}

	public boolean hasFeature(String featureName) {
		return features.containsKey(featureName);
	}

	public boolean featureValueMatches(String featureName, String featureValue) {
		return features.containsKey(featureValue) ? Objects.equals(featureValue, features.get(featureName)) : false;
	}

	public Optional<String> getFeatureValue(String featureName) {
		return Optional.ofNullable(features.get(featureName));
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
