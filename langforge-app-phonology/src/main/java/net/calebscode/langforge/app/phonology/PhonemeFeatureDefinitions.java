package net.calebscode.langforge.app.phonology;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

public class PhonemeFeatureDefinitions {

	private ReadOnlyListWrapper<String> featureNames = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());
	private Map<String, ReadOnlyListWrapper<String>> featureValues = new HashMap<>();

	public static PhonemeFeatureDefinitions createStandardPhonemes() {
		var standardPhonemes = new PhonemeFeatureDefinitions();

		standardPhonemes.addFeatureWithValues(CATEGORY,    STANDARD_CATEGORIES);
		standardPhonemes.addFeatureWithValues(VOICING,     STANDARD_VOICINGS);
		standardPhonemes.addFeatureWithValues(PLACE,       STANDARD_PLACES);
		standardPhonemes.addFeatureWithValues(TYPE,        STANDARD_TYPES);
		standardPhonemes.addFeatureWithValues(OPENNESS,    STANDARD_OPENNESSES);
		standardPhonemes.addFeatureWithValues(BACKNESS,    STANDARD_BACKNESSES);
		standardPhonemes.addFeatureWithValues(ROUNDEDNESS, STANDARD_ROUNDEDNESSES);

		return standardPhonemes;
	}

	public ReadOnlyListProperty<String> featureNamesProperty() {
		return featureNames.getReadOnlyProperty();
	}

	public ReadOnlyListProperty<String> featureValuesProperty(String featureName) {
		var values = featureValues.get(featureName);
		if (values == null) {
			return null;
		}
		return values.getReadOnlyProperty();
	}

	public void addFeature(String featureName) {
		if (!featureNames.contains(featureName)) {
			featureNames.add(featureName);
			Collections.sort(featureNames);
			featureValues.put(featureName, new ReadOnlyListWrapper<>(FXCollections.observableArrayList()));
		}
	}

	public void removeFeature(String featureName) {
		if (featureNames.contains(featureName)) {
			featureNames.remove(featureName);
			featureValues.remove(featureName).setValue(null);
		}
	}

	public void addFeatureValue(String featureName, String featureValue) {
		if (featureNames.contains(featureName)) {
			var values = featureValues.get(featureName);
			if (!values.contains(featureValue)) {
				values.add(featureValue);
				Collections.sort(values);
			}
		}
	}

	public void removeFeatureValue(String featureName, String featureValue) {
		if (featureNames.contains(featureName)) {
			featureValues.get(featureName).remove(featureValue);
		}
	}

	public void addFeatureWithValues(String featureName, Collection<String> values) {
		if (!featureNames.contains(featureName)) {
			featureNames.add(featureName);
			Collections.sort(featureNames);

			featureValues.put(featureName, new ReadOnlyListWrapper<>(FXCollections.observableArrayList(values)));
			Collections.sort(featureValues.get(featureName));
		}
	}

}
