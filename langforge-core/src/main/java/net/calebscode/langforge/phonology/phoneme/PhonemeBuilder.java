package net.calebscode.langforge.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;

public class PhonemeBuilder {

	private Map<String, String> features = new HashMap<>();

	public PhonemeBuilder() {}

	public PhonemeBuilder(Phoneme template) {
		features.putAll(template.features());
	}

	public PhonemeBuilder withFeature(String featureName, String featureValue) {
		features.put(featureName, featureValue);
		return this;
	}

	public Phoneme build() {
		return new Phoneme(features);
	}

}
