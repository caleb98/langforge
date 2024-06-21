package net.calebscode.langtool.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;

public class PhonemeBuilder {

	private String representation;
	private Map<String, String> features = new HashMap<>();

	public PhonemeBuilder() {
		this("");
	}

	public PhonemeBuilder(String representation) {
		this.representation = representation;
	}

	public PhonemeBuilder withRepresentation(String representation) {
		this.representation = representation;
		return this;
	}

	public PhonemeBuilder withFeature(String featureName, String featureValue) {
		features.put(featureName, featureValue);
		return this;
	}

	public Phoneme build() {
		return new Phoneme(representation, features);
	}

}
