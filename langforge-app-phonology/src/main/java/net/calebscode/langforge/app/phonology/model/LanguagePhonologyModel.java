package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class LanguagePhonologyModel {

	private final PhonologicalInventoryModel phonologicalInventory;
	private final SyllablePatternCategoryMapModel syllablePatternCategories;
	private final ListProperty<PhonologicalRuleModel> phonologicalRules;
	private final ListProperty<String> syllablePatterns;

	public LanguagePhonologyModel(
		PhonologicalInventoryModel phonologicalInventory,
		SyllablePatternCategoryMapModel syllablePatternCategories,
		ObservableList<PhonologicalRuleModel> phonologicalRules,
		ObservableList<String> syllablePatterns
	) {
		this.phonologicalInventory = phonologicalInventory;
		this.syllablePatternCategories = syllablePatternCategories;
		this.phonologicalRules = new SimpleListProperty<>(phonologicalRules);
		this.syllablePatterns = new SimpleListProperty<>(syllablePatterns);


	}

	public PhonologicalInventoryModel getPhonologicalInventory() {
		return phonologicalInventory;
	}

	public SyllablePatternCategoryMapModel getSyllablePatternCategories() {
		return syllablePatternCategories;
	}
	
	public ListProperty<PhonologicalRuleModel> phonologicalRulesProperty() {
		return phonologicalRules;
	}

	public ObservableList<PhonologicalRuleModel> getPhonologicalRules() {
		return phonologicalRules.get();
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
	
}
