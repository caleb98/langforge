package net.calebscode.langforge.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;

/// A builder class for creating [Phoneme]s.
public class PhonemeBuilder {

	private Map<String, String> features = new HashMap<>();

	/// Creates an empty [PhonemeBuilder].
	public PhonemeBuilder() {}

	/// Creates a [PhonemeBuilder] initialized with the features of the provided [Phoneme].
	/// @param template the template phoneme
	public PhonemeBuilder(Phoneme template) {
		features.putAll(template.features());
	}

	/// Sets the value for a feature in this builder.
	/// @param featureName the name of the feature to set
	/// @param featureValue the value to assign to the feature
	/// @return this [PhonemeBuilder] for method chaining
	public PhonemeBuilder withFeature(String featureName, String featureValue) {
		features.put(featureName, featureValue);
		return this;
	}

	/// Builds the [Phoneme] using the features of this builder.
	public Phoneme build() {
		return new Phoneme(features);
	}

}
