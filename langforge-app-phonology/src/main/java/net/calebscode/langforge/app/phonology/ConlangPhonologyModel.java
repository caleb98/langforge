package net.calebscode.langforge.app.phonology;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class ConlangPhonologyModel {

	private PhonemeFeatureDefinitions featureDefinitions = PhonemeFeatureDefinitions.createStandardPhonemes();
	private ListProperty<Phoneme> phonemes = new SimpleListProperty<>(FXCollections.observableArrayList());

	public PhonemeFeatureDefinitions getFeatureDefinitions() {
		return featureDefinitions;
	}

	public ListProperty<Phoneme> phonemesProperty() {
		return phonemes;
	}

	public ObservableList<Phoneme> getPhonemes() {
		return phonemes.getValue();
	}

	public void setPhonemes(ObservableList<Phoneme> phonemes) {
		this.phonemes.setValue(phonemes);
	}

}
