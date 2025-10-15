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

	/// Creates a new phoneme with the given feature map. Note that phonemes do not allow null
	/// feature names or values.
	/// @param features the map of features for the phoneme
	/// @throws [NullPointerException] if `features` is null
	/// @throws [IllegalArgumentException] if `features` contains any null keys or values
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

	/// Checks whether or not this phoneme has a feature with the given name.
	/// @param featureName the name of the feature to check for
	/// @return `true` if this phoneme has a feature with that name; `false` otherwise
	public boolean hasFeature(String featureName) {
		return features.containsKey(featureName);
	}

	/// Checks whether or not this phoneme has a feature with the given name matching the given
	/// value.
	/// @param featureName the name of the feature to check
	/// @param featureValue the expected feature value to match
	/// @return true if this phoneme contains a feature with the given name with a value equal to
	/// `featureValue`; false otherwise 
	public boolean featureValueMatches(String featureName, String featureValue) {
		return getFeatureValue(featureName).map(featureValue::equals).orElse(false);
	}

	/// Gets the current value for a given feature.
	/// @param featureName the name of the feature to get
	/// @return an [Optional] containing the feature value string. If no feature with name
	/// `featureName` is present, returns an empty [Optional].
	public Optional<String> getFeatureValue(String featureName) {
		return Optional.ofNullable(features.get(featureName));
	}
	
	/// Returns a new [Phoneme] containing all features of this phoneme, with the provided feature
	/// having the provided value.
	/// @param featureName the feature to set on the new phoneme
	/// @param featureValue the value that the feature should have
	/// @return a new [Phoneme] with the given `featureName` set to `featureValue`
	public Phoneme withFeature(String featureName, String featureValue) {
		if (featureValueMatches(featureName, featureValue)) {
			return this;
		}
		
		var newFeatures = new HashMap<>(features);
		newFeatures.put(featureName, featureValue);
		
		return new Phoneme(newFeatures);
	}

	/// Returns a new [Phoneme] derived from this [Phoneme], containing all the same features and
	/// values except for the given `featureName`, which is removed.
	/// @param featureName the name of the feature to remove
	/// @return a new [Phoneme] identical to this one, but without the `featureName` feature
	public Phoneme withoutFeature(String featureName) {
		if (!hasFeature(featureName)) {
			return this;
		}

		var newFeatures = new HashMap<>(features);
		newFeatures.remove(featureName);

		return new Phoneme(newFeatures);
	}

	/// @inheritDoc
	/// @return a list containing only this phoneme
	@Override
	public List<Phoneme> getPhonemes() {
		return List.of(this);
	}

}
