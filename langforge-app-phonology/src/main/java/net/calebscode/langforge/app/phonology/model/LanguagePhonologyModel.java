package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import net.calebscode.langforge.app.data.RuntimeType;
import net.calebscode.langforge.app.data.SaveLoadSchema;
import net.calebscode.langforge.app.data.SaveLoadable;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class LanguagePhonologyModel implements SaveLoadable<LanguagePhonologyModel> {

	private static final SaveLoadSchema<LanguagePhonologyModel> schema = new SaveLoadSchema<>();
	
	static {
		schema.add("phonologicalInventory", LanguagePhonologyModel::getPhonologicalInventory);
		schema.add("syllablePatternCategories", LanguagePhonologyModel::getSyllablePatternCategories);
		schema.addList("syllablePatterns", new RuntimeType<String>(){}, LanguagePhonologyModel::getSyllablePatterns);
		schema.addList(
			"phonologicalRules",
			new RuntimeType<PhonologicalRuleModel>() {},
			_ -> new PhonologicalRuleModel(StandardPhonemes.IPA_PHONEME_REPRESENTATION_MAPPER),
			LanguagePhonologyModel::getPhonologicalRules
		);
	}
	
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
	
	@Override
	public LanguagePhonologyModel getValue() {
		return this;
	}
	
	@Override
	public SaveLoadSchema<LanguagePhonologyModel> getSchema() {
		return schema;
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
