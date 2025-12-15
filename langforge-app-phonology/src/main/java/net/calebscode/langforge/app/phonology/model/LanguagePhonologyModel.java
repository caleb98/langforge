package net.calebscode.langforge.app.phonology.model;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.calebscode.langforge.app.data.RuntimeType;
import net.calebscode.langforge.app.data.SaveLoadModel;

public class LanguagePhonologyModel extends SaveLoadModel {

	private final PhonologicalInventoryModel phonologicalInventory;
	private final SyllablePatternCategoryMapModel syllablePatternCategories;
	private final PhonologicalRuleCollectionModel phonologicalRules;

	private final ListProperty<String> syllablePatterns;

	public LanguagePhonologyModel(
		PhonologicalInventoryModel phonologicalInventory,
		SyllablePatternCategoryMapModel syllablePatternCategories,
		PhonologicalRuleCollectionModel phonologicalRules,
		ObservableList<String> syllablePatterns
	) {
		this.phonologicalInventory = phonologicalInventory;
		this.syllablePatternCategories = syllablePatternCategories;
		this.phonologicalRules = phonologicalRules;
		this.syllablePatterns = new SimpleListProperty<>(syllablePatterns);

		this.persistList("syllablePatterns", new RuntimeType<String>(){}, this::getSyllablePatterns, this::setSyllablePatterns);
	}

	public PhonologicalInventoryModel getPhonologicalInventory() {
		return phonologicalInventory;
	}

	public SyllablePatternCategoryMapModel getSyllablePatternCategories() {
		return syllablePatternCategories;
	}

	public PhonologicalRuleCollectionModel getPhonologicalRules() {
		return phonologicalRules;
	}

	public ListProperty<String> syllablePatternsProperty() {
		return syllablePatterns;
	}

	public ObservableList<String> getSyllablePatterns() {
		return syllablePatterns.get();
	}

	public void setSyllablePatterns(ObservableList<String> syllablePatterns) {
		this.syllablePatterns.set(syllablePatterns);
	}
	
	private void setSyllablePatterns(List<String> syllablePatterns) {
		this.syllablePatterns.set(FXCollections.observableArrayList(syllablePatterns));
	}

}
