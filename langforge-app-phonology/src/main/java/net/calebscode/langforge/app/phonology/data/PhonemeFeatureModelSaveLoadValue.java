package net.calebscode.langforge.app.phonology.data;

import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadString;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.phonology.model.PhonemeFeatureModel;

public class PhonemeFeatureModelSaveLoadValue extends SaveLoadObject {

	public PhonemeFeatureModelSaveLoadValue(PhonemeFeatureModel model) {
		put("name", model.getName());
		put("values", model.valuesProperty(), SaveLoadString::new);
	}

	public PhonemeFeatureModelSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public PhonemeFeatureModel toModel() {
		var model = new PhonemeFeatureModel();

		model.setName(get("name").asStringValue());

		var values = getList("values").stream().map(SaveLoadValue::asStringValue).toList();
		model.valuesProperty().setAll(values);

		return model;
	}

}
