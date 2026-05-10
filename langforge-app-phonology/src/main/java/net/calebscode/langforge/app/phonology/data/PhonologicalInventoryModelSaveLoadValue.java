package net.calebscode.langforge.app.phonology.data;

import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;

public class PhonologicalInventoryModelSaveLoadValue extends SaveLoadObject {

	public PhonologicalInventoryModelSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public PhonologicalInventoryModelSaveLoadValue(PhonologicalInventoryModel model) {
		put("phonemes", model.phonemesProperty(), PhonemeSaveLoadValue::new);
		put("features", model.featuresProperty(), PhonemeFeatureModelSaveLoadValue::new);
	}

}
