package net.calebscode.langforge.app.phonology.data;

import static java.util.stream.Collectors.toMap;

import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadString;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonemeSaveLoadValue extends SaveLoadObject {

	public PhonemeSaveLoadValue(SaveLoadObject source) {
		super(source);
	}

	public PhonemeSaveLoadValue(Phoneme model) {
		super(model.features(), SaveLoadString::new);
	}

	public Phoneme toModel() {
		var features = entrySet()
			.stream()
			.collect(toMap(
				Entry::getKey,
				e -> e.getValue().asStringValue())
			);

		return new Phoneme(features);
	}

}
