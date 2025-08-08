package net.calebscode.langforge.app.phonology.model;

public class LanguagePhonologyModel {

	private final PhonologicalInventoryModel phonologicalInventory;
	private final SyllablePatternCategoryMapModel syllablePatternCategories;
	private final SyllablePatternCollectionModel syllablePatterns;
	private final PhonologicalRuleCollectionModel phonologicalRules;

	public LanguagePhonologyModel(
		PhonologicalInventoryModel phonologicalInventory,
		SyllablePatternCategoryMapModel syllablePatternCategories,
		SyllablePatternCollectionModel syllablePatterns,
		PhonologicalRuleCollectionModel phonologicalRules
	) {
		this.phonologicalInventory = phonologicalInventory;
		this.syllablePatternCategories = syllablePatternCategories;
		this.syllablePatterns = syllablePatterns;
		this.phonologicalRules = phonologicalRules;
	}

	public PhonologicalInventoryModel getPhonologicalInventory() {
		return phonologicalInventory;
	}

	public SyllablePatternCategoryMapModel getSyllablePatternCategories() {
		return syllablePatternCategories;
	}

	public SyllablePatternCollectionModel getSyllablePatterns() {
		return syllablePatterns;
	}

	public PhonologicalRuleCollectionModel getPhonologicalRules() {
		return phonologicalRules;
	}

}
