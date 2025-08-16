package net.calebscode.langforge.app.phonology.api;

import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;

public class LangforgeCorePhonologyApi {

	private final LanguagePhonologyModel phonologyModel;

	public LangforgeCorePhonologyApi(LanguagePhonologyModel phonologyModel) {
		this.phonologyModel = phonologyModel;
	}

	public LanguagePhonologyModel getPhonologyModel() {
		return phonologyModel;
	}

}
