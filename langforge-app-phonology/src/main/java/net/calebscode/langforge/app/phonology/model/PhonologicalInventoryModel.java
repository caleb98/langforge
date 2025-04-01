package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;
import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener.Change;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonologicalInventoryModel {

	private ListProperty<Phoneme> phonemes;
	private ListProperty<PhonemeFeatureModel> features;

	public PhonologicalInventoryModel() {
		phonemes = new SimpleListProperty<>(observableArrayList());
		phonemes.addListener(this::validatePhonemes);

		features = new SimpleListProperty<>(observableArrayList(element -> new Observable[] { element.valuesProperty() }));
		features.addListener(this::validatePhonemeFeatures);
	}

	public ReadOnlyListProperty<Phoneme> phonemesProperty() {
		return phonemes;
	}

	public ReadOnlyListProperty<PhonemeFeatureModel> featuresProperty() {
		return features;
	}

	public PhonemeFeatureModel addFeature(String name) {
		var maybeFeature = features.stream().filter(f -> Objects.equals(f.getName(), name)).findFirst();
		if (maybeFeature.isEmpty()) {
			PhonemeFeatureModel newFeature = new PhonemeFeatureModel(name);
			features.add(newFeature);
			return newFeature;
		}
		else {
			return maybeFeature.get();
		}
	}

	public boolean removeFeature(String name) {
		var maybeFeature = features.stream().filter(f -> f.getName().equals(name)).findFirst();
		if (maybeFeature.isEmpty()) {
			return false;
		}

		var feature = maybeFeature.get();
		feature.markAsDeleted();
		return features.remove(feature);
	}

	public Optional<PhonemeFeatureModel> getFeature(String name) {
		return features.stream().filter(f -> Objects.equals(f.getName(), name)).findFirst();
	}

	public boolean isPhonemeValid(Phoneme phoneme) {
		for (var entry : phoneme.features().entrySet()) {
			var featureName = entry.getKey();
			var featureValue = entry.getValue();

			var maybeFeature = features.stream().filter(f -> Objects.equals(f.getName(), featureName)).findFirst();
			if (maybeFeature.isEmpty() || !maybeFeature.get().valuesProperty().contains(featureValue)) {
				return false;
			}
		}

		return true;
	}

	public boolean isPhonemeInvalid(Phoneme phoneme) {
		return !isPhonemeValid(phoneme);
	}

	private void validatePhonemes(Change<? extends Phoneme> change) {
		while (change.next()) {
			if (change.wasAdded()) {
				var invalid = change.getAddedSubList().stream()
					.filter(this::isPhonemeInvalid)
					.toList();

				phonemes.removeAll(invalid);

				var deduped = phonemes.stream().distinct().toList();
				if (deduped.size() != phonemes.size()) {
					phonemes.set(observableArrayList(deduped));
				}
			}
		}
	}

	private void validatePhonemeFeatures(Change<? extends PhonemeFeatureModel> change) {
		while (change.next()) {
			// Whenever the set of valid features changes, we need to verify that all of the phonemes are still valid.
			// First, for any removed features, we update all the phonemes by removing that feature.
			change.getRemoved().forEach(this::removeFeatureFromPhonemes);

			// Next, it's possible that a value for a feature was removed. So, we need to look through all the phonemes
			// and remove that feature if the current value is no longer valid.
			var updatedPhonemes = new ArrayList<Phoneme>();
			boolean anyChanged = false;

			for (var phoneme : phonemes) {
				var fixed = removeInvalidFeatures(phoneme);
				updatedPhonemes.add(fixed);
				if (!fixed.equals(phoneme)) {
					anyChanged = true;
				}
			}

			if (anyChanged) {
				phonemes.set(observableArrayList(updatedPhonemes));
			}
		}
	}

	private void removeFeatureFromPhonemes(PhonemeFeatureModel feature) {
		var updatedPhonemes = new ArrayList<Phoneme>();
		boolean anyChanged = false;

		for (var phoneme : phonemes) {
			var withoutFeature = phoneme.withoutFeature(feature.getName());
			updatedPhonemes.add(withoutFeature);
			if (!withoutFeature.equals(phoneme)) {
				anyChanged = true;
			}
		}

		if (anyChanged) {
			phonemes.set(observableArrayList(updatedPhonemes));
		}
	}

	private Phoneme removeInvalidFeatures(Phoneme phoneme) {
		for (var entry : phoneme.features().entrySet()) {
			var featureName = entry.getKey();
			var featureValue = entry.getValue();

			var featureDefinition = features.filtered(f -> Objects.equals(f.getName(), featureName)).getFirst();

			if (!featureDefinition.valuesProperty().contains(featureValue)) {
				phoneme = phoneme.withoutFeature(featureName);
			}
		}

		return phoneme;
	}

	public static PhonologicalInventoryModel createModelWithIpaDefaults() {
		var model = new PhonologicalInventoryModel();

		var categoryFeature = model.addFeature(CATEGORY);
		categoryFeature.valuesProperty().addAll(STANDARD_CATEGORIES);

		var voicingFeature = model.addFeature(VOICING);
		voicingFeature.valuesProperty().addAll(STANDARD_VOICINGS);

		var placeFeature = model.addFeature(PLACE);
		placeFeature.valuesProperty().addAll(STANDARD_PLACES);

		var typeFeature = model.addFeature(TYPE);
		typeFeature.valuesProperty().addAll(STANDARD_TYPES);

		var opennessFeature = model.addFeature(OPENNESS);
		opennessFeature.valuesProperty().addAll(STANDARD_OPENNESSES);

		var backnessFeature = model.addFeature(BACKNESS);
		backnessFeature.valuesProperty().addAll(STANDARD_BACKNESSES);

		var roundednessFeature = model.addFeature(ROUNDEDNESS);
		roundednessFeature.valuesProperty().addAll(STANDARD_ROUNDEDNESSES);

		return model;
	}
}
