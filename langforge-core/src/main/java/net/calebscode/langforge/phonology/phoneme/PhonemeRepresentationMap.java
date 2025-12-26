package net.calebscode.langforge.phonology.phoneme;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/// A [PhonemeRepresentationMapper] that associates [Phoneme]s with their [String] representations
/// using an internal mapping between the two.
public class PhonemeRepresentationMap implements PhonemeRepresentationMapper {

	private Map<String, Phoneme> stringToPhoneme = new HashMap<>();
	private Map<Phoneme, String> phonemeToString = new HashMap<>();

	/// Adds a mapping between a [String] and a [Phoneme].
	/// @param string the [String] value
	/// @param phoneme the [Phoneme] value
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

	/// Removes the [Phoneme] mapping for the given [String].
	/// @param string the string mapping to remove
	/// @return the [Phoneme] that was mapped to the give [String]; `null` if there was no mapping
	public Phoneme removeMapping(String string) {
		phonemeToString.values().remove(string);
		return stringToPhoneme.remove(string);
	}

	/// Removes the [String] mapping for a given [Phoneme].
	/// @param phoneme the phoneme mapping to remove
	/// @return the [String] that was mapped to the given [Phoneme]; `null` if there was no mapping
	public String removeMapping(Phoneme phoneme) {
		stringToPhoneme.values().remove(phoneme);
		return phonemeToString.remove(phoneme);
	}

	/// {@inheritDoc}
	@Override
	public boolean canMap(Phoneme phoneme) {
		return phonemeToString.containsKey(phoneme);
	}

	/// {@inheritDoc}
	@Override
	public boolean canMap(String string) {
		return stringToPhoneme.containsKey(string);
	}

	/// {@inheritDoc}
	@Override
	public Optional<String> getRepresentation(Phoneme phoneme) {
		return Optional.ofNullable(phonemeToString.get(phoneme));
	}

	/// {@inheritDoc}
	@Override
	public Optional<Phoneme> getPhoneme(String string) {
		return Optional.ofNullable(stringToPhoneme.get(string));
	}

}
