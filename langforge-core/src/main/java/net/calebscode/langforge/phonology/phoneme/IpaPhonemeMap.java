package net.calebscode.langforge.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class IpaPhonemeMap implements IpaPhonemeMapper {

	private Map<String, Phoneme> ipaToPhonemeMap = new HashMap<>();
	private Map<Phoneme, String> phonemeToIpaMap = new HashMap<>();

	public void addMapping(String ipa, Phoneme phoneme) {
		// If a map contains a key, that means that key exists
		// as a value in the other map. So, whenever we encounter
		// an existing key, have to remove the entry with the
		// same value from the other map. We don't need to remove
		// from the map where the key exists, though, since the
		// call to put(...) below will do those overwrites.
		if (ipaToPhonemeMap.containsKey(ipa)) {
			phonemeToIpaMap.values().remove(ipa);
		}

		if (phonemeToIpaMap.containsKey(phoneme)) {
			ipaToPhonemeMap.values().remove(phoneme);
		}

		ipaToPhonemeMap.put(ipa, phoneme);
		phonemeToIpaMap.put(phoneme, ipa);
	}

	public Phoneme removeMapping(String ipa) {
		phonemeToIpaMap.values().remove(ipa);
		return ipaToPhonemeMap.remove(ipa);
	}

	public String removeMapping(Phoneme phoneme) {
		ipaToPhonemeMap.values().remove(phoneme);
		return phonemeToIpaMap.remove(phoneme);
	}

	@Override
	public boolean canMap(Phoneme phoneme) {
		return phonemeToIpaMap.containsKey(phoneme);
	}

	@Override
	public boolean canMap(String ipa) {
		return ipaToPhonemeMap.containsKey(ipa);
	}

	@Override
	public String getIpa(Phoneme phoneme) {
		return phonemeToIpaMap.get(phoneme);
	}

	@Override
	public Phoneme getPhoneme(String ipa) {
		return ipaToPhonemeMap.get(ipa);
	}

	@Override
	public Set<Entry> entrySet() {
		return ipaToPhonemeMap.entrySet().stream()
				.map(entry -> new Entry(entry.getKey(), entry.getValue()))
				.collect(Collectors.toSet());
	}

}
