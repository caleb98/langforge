package net.calebscode.langtool.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;

public class IpaPhonemeMap implements IpaPhonemeMapper {

	private Map<String, Phoneme> ipaMap = new HashMap<>();

	public void add(Phoneme phoneme) throws IllegalArgumentException {
		if (ipaMap.containsKey(phoneme.representation())) {
			throw new IllegalArgumentException("Phoneme with ipa '" + phoneme.representation() + "' already exists in this mapper.");
		}

		ipaMap.put(phoneme.representation(), phoneme);
	}

	public void remove(String ipa) {
		ipaMap.remove(ipa);
	}

	public void remove(Phoneme phoneme) {
		ipaMap.remove(phoneme.representation());
	}

	@Override
	public boolean hasIpa(String ipa) {
		return ipaMap.containsKey(ipa);
	}

	@Override
	public boolean canMapTo(Phoneme phoneme) {
		return ipaMap.containsValue(phoneme);
	}

	@Override
	public Phoneme getPhoneme(String ipa) {
		return ipaMap.get(ipa);
	}

}
