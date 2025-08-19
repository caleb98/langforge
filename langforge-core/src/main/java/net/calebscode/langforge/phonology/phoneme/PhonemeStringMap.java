package net.calebscode.langforge.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PhonemeStringMap implements PhonemeRepresentationMapper {

	private Map<String, Phoneme> stringToPhoneme = new HashMap<>();
	private Map<Phoneme, String> phonemeToString = new HashMap<>();

	public void addMapping(String string, Phoneme phoneme) {
		// If a map contains a key, that means that key exists
		// as a value in the other map. So, whenever we encounter
		// an existing key, have to remove the entry with the
		// same value from the other map. We don't need to remove
		// from the map where the key exists, though, since the
		// call to put(...) below will do those overwrites.
		if (stringToPhoneme.containsKey(string)) {
			phonemeToString.values().remove(string);
		}

		if (phonemeToString.containsKey(phoneme)) {
			stringToPhoneme.values().remove(phoneme);
		}

		stringToPhoneme.put(string, phoneme);
		phonemeToString.put(phoneme, string);
	}

	public Phoneme removeMapping(String string) {
		phonemeToString.values().remove(string);
		return stringToPhoneme.remove(string);
	}

	public String removeMapping(Phoneme phoneme) {
		stringToPhoneme.values().remove(phoneme);
		return phonemeToString.remove(phoneme);
	}

	@Override
	public boolean canMap(Phoneme phoneme) {
		return phonemeToString.containsKey(phoneme);
	}

	@Override
	public boolean canMap(String string) {
		return stringToPhoneme.containsKey(string);
	}

	@Override
	public String getIpa(Phoneme phoneme) {
		return phonemeToString.get(phoneme);
	}

	@Override
	public Phoneme getPhoneme(String string) {
		return stringToPhoneme.get(string);
	}

	@Override
	public Set<Entry> entrySet() {
		return stringToPhoneme
			.entrySet()
			.stream()
			.map(entry -> new Entry(entry.getKey(), entry.getValue()))
			.collect(Collectors.toSet());
	}

}
