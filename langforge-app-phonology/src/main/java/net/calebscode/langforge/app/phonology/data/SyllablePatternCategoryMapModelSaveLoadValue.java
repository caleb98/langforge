package net.calebscode.langforge.app.phonology.data;

import java.util.List;

import net.calebscode.langforge.app.data.SaveLoadInteger;
import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryMapModel;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class SyllablePatternCategoryMapModelSaveLoadValue extends SaveLoadObject {

	public SyllablePatternCategoryMapModelSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public SyllablePatternCategoryMapModelSaveLoadValue(
		SyllablePatternCategoryMapModel model,
		List<Phoneme> phonemes
	) {
		for (var category : model.getCategories()) {
			var categoryPhonemes = model
				.getPhonemes(category)
				.stream()
				.map(phonemes::indexOf)
				.toList();

			put(category.toString(), categoryPhonemes, SaveLoadInteger::new);
		}
	}

	public SyllablePatternCategoryMapModel toModel(List<Phoneme> phonemes) {
		var model = new SyllablePatternCategoryMapModel();

		for (var category : keySet()) {
			var categoryPhonemeIndices = get(category)
				.asList()
				.stream()
				.map(SaveLoadValue::asInteger)
				.map(SaveLoadInteger::value)
				.toList();


			for (var index : categoryPhonemeIndices) {
				model.addPhoneme(category.charAt(0), phonemes.get(index));
			}
		}

		return model;
	}

}
