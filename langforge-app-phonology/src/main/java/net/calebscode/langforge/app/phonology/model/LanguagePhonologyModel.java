package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMapper;

public class LanguagePhonologyModel {

	private final PhonologicalInventoryModel phonologicalInventory;
	private final SyllablePatternCategoryMapModel syllablePatternCategories;
	private final ListProperty<PhonologicalRuleModel> phonologicalRules;
	private final ListProperty<String> syllablePatterns;
	private final ObjectProperty<PhonemeRepresentationMapper> phonemeMapper;

	public LanguagePhonologyModel(
		PhonologicalInventoryModel phonologicalInventory,
		SyllablePatternCategoryMapModel syllablePatternCategories,
		ObservableList<PhonologicalRuleModel> phonologicalRules,
		ObservableList<String> syllablePatterns,
		PhonemeRepresentationMapper phonemeMapper
	) {
		this.phonologicalInventory = phonologicalInventory;
		this.syllablePatternCategories = syllablePatternCategories;
		this.phonologicalRules = new SimpleListProperty<>(phonologicalRules);
		this.syllablePatterns = new SimpleListProperty<>(syllablePatterns);
		this.phonemeMapper = new SimpleObjectProperty<>(phonemeMapper);
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

	public ObjectProperty<PhonemeRepresentationMapper> phonemeMapperProperty() {
		return phonemeMapper;
	}

	public PhonemeRepresentationMapper getPhonemeMapper() {
		return phonemeMapper.get();
	}

}
