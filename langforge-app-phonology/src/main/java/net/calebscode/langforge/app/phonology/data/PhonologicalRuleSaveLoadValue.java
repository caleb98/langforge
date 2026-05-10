package net.calebscode.langforge.app.phonology.data;

import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;

public class PhonologicalRuleSaveLoadValue extends SaveLoadObject {

	public PhonologicalRuleSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public PhonologicalRuleSaveLoadValue(PhonologicalRuleModel model) {
		put("name", model.getName());
		put("source", model.getSource());
	}

	public PhonologicalRuleModel toModel() {
		var model = new PhonologicalRuleModel();
		model.setName(get("name").asStringValue());
		model.setSource(get("source").asStringValue());
		return model;
	}

}
