package net.calebscode.langtool.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;

public class IpaPhonemeMap implements IpaPhonemeMapper {

	private Map<String, Phoneme> ipaMap = new HashMap<>();
	private Map<Map<String, String>, String> featureMap = new HashMap<>();

	public void add(Phoneme phoneme) throws IllegalArgumentException {
		if (ipaMap.containsKey(phoneme.representation())) {
			throw new IllegalArgumentException("Phoneme with ipa '" + phoneme.representation() + "' already exists in this mapper.");
		}

		ipaMap.put(phoneme.representation(), phoneme);
		featureMap.put(phoneme.features(), phoneme.representation());
	}

	public void remove(String ipa) {
		ipaMap.remove(ipa);
		featureMap.values().remove(ipa);
	}

	public void remove(Phoneme phoneme) {
		ipaMap.remove(phoneme.representation());
		featureMap.values().remove(phoneme.representation());
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

	@Override
	public String getIpa(Map<String, String> features) {
		return featureMap.get(features);
	}

	@Override
	public boolean hasIpaForFeatures(Map<String, String> features) {
		return featureMap.containsKey(features);
	}

}
