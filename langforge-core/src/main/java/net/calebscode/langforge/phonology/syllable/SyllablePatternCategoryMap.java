package net.calebscode.langforge.phonology.syllable;

import static java.util.Collections.emptySet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class SyllablePatternCategoryMap {

	private Map<Character, Set<Phoneme>> categories = new HashMap<>();

	public Set<Character> getCategories() {
		return categories.keySet();
	}

	public Set<Phoneme> getPhonemes(Character category) {
		return categories.getOrDefault(category, emptySet());
	}

	public void addPhoneme(Character category, Phoneme phoneme) {
		var entry = categories.computeIfAbsent(category, c -> new HashSet<>());
		entry.add(phoneme);
	}

	public void removeCategory(Character category) {
		categories.remove(category);
	}

	public void removePhoneme(Character category, Phoneme phoneme) {
		var entry = categories.get(category);
		if (entry == null) {
			return;
		}

		entry.remove(phoneme);
	}

	public Set<Character> computePhonemeCategories(Phoneme phoneme) {
		var phonemeCategories = new HashSet<Character>();
		for (var entry : categories.entrySet()) {
			if (entry.getValue().contains(phoneme)) {
				phonemeCategories.add(entry.getKey());
			}
		}
		return phonemeCategories;
	}

}
