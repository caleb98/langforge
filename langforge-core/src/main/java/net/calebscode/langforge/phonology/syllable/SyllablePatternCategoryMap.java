package net.calebscode.langforge.phonology.syllable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class SyllablePatternCategoryMap {

	private record CategoryEntry(Set<Phoneme> generatable, Set<Phoneme> ungeneratable) {}

	private Map<Character, CategoryEntry> categories = new HashMap<>();

	public Set<Character> getCategories() {
		return categories.keySet();
	}

	public Set<Phoneme> getAllPhonemes(Character category) {
		var entry = categories.get(category);

		if (entry == null) {
			return null;
		}

		var fullSet = new HashSet<Phoneme>();
		fullSet.addAll(entry.generatable);
		fullSet.addAll(entry.ungeneratable);

		return fullSet;
	}

	public Set<Phoneme> getGeneratablePhonemes(Character category) {
		return categories.containsKey(category) ?
				Collections.unmodifiableSet(categories.get(category).generatable) :
				null;
	}

	public Set<Phoneme> getUngeneratablePhonemes(Character category) {
		return categories.containsKey(category) ?
				Collections.unmodifiableSet(categories.get(category).ungeneratable) :
				null;
	}

	public void addGeneratablePhoneme(Character category, Phoneme phoneme) {
		var entry = categories.computeIfAbsent(category, c -> new CategoryEntry(new HashSet<>(), new HashSet<>()));
		entry.ungeneratable.remove(phoneme);
		entry.generatable.add(phoneme);
	}

	public void addUngeneratablePhoneme(Character category, Phoneme phoneme) {
		var entry = categories.computeIfAbsent(category, c -> new CategoryEntry(new HashSet<>(), new HashSet<>()));
		entry.generatable.remove(phoneme);
		entry.ungeneratable.add(phoneme);
	}

	public void removeCategory(Character category) {
		categories.remove(category);
	}

	public void removePhoneme(Character category, Phoneme phoneme) {
		var entry = categories.get(category);
		if (entry == null) {
			return;
		}

		entry.generatable.remove(phoneme);
		entry.ungeneratable.remove(phoneme);
	}

	public Set<Character> computePhonemeCategories(Phoneme phoneme) {
		var phonemeCategories = new HashSet<Character>();
		for (var entry : categories.entrySet()) {
			if (entry.getValue().generatable.contains(phoneme) || entry.getValue().ungeneratable.contains(phoneme)) {
				phonemeCategories.add(entry.getKey());
			}
		}
		return phonemeCategories;
	}

}
