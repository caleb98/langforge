package net.calebscode.langtool.phonology.phoneme;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record Phoneme(String representation, Map<String, String> features) {

	public Phoneme(String representation, Map<String, String> features) {
		this.representation = representation;
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

	@Override
	public String toString() {
		return representation;
	}

}
