package net.calebscode.langforge.app.phonology.data;

import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;

public class LanguagePhonologyModelSaveLoadValue extends SaveLoadObject {

	public LanguagePhonologyModelSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public LanguagePhonologyModelSaveLoadValue(LanguagePhonologyModel model) {
		var inventory = model.getPhonologicalInventory();
		var phonemes = inventory.phonemesProperty();

		put("inventory", new PhonologicalInventoryModelSaveLoadValue(inventory));
		put("phonologicalRules", model.getPhonologicalRules(), PhonologicalRuleSaveLoadValue::new);
		put(
			"syllablePatternCategories",
			new SyllablePatternCategoryMapModelSaveLoadValue(
				model.getSyllablePatternCategories(),
				phonemes
			)
		);
	}

	public LanguagePhonologyModel toModel() {
		var model = new LanguagePhonologyModel(null, null, null, null, null);
		return model;
	}

}
