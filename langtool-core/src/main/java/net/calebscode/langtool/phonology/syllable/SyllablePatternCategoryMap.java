package net.calebscode.langtool.phonology.syllable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.calebscode.langtool.phonology.phoneme.Phoneme;

public class SyllablePatternCategoryMap {

	private Map<Character, Set<Phoneme>> categoryToPhoneme = new HashMap<>();
	private Map<Phoneme, Set<Character>> phonemeToCategory = new HashMap<>();
	
	public void addClassPhoneme(Character category, Phoneme phoneme) {		
		var catToPhoneMapping = categoryToPhoneme.computeIfAbsent(category, c -> new HashSet<Phoneme>());
		var phoneToCatMapping = phonemeToCategory.computeIfAbsent(phoneme, p -> new HashSet<Character>());
		
		catToPhoneMapping.add(phoneme);
		phoneToCatMapping.add(category);
	}
	
	public void addCategoryPhonemes(Character category, Phoneme... phonemes) {		
		var catToPhoneMapping = categoryToPhoneme.computeIfAbsent(category, c -> new HashSet<Phoneme>());
		catToPhoneMapping.addAll(List.of(phonemes));
		
		for (var phoneme : phonemes) {
			var phoneToCatMapping = phonemeToCategory.computeIfAbsent(phoneme, p -> new HashSet<Character>());
			phoneToCatMapping.add(category);
		}
	}
	
	public boolean isValidPhonemeForCategory(Character category, Phoneme phoneme) {
		return getPhonemesForCategory(category).contains(phoneme);
	}
	
	public boolean isValidCategoryForPhoneme(Phoneme phoneme, Character category) {
		return getCategoriesForPhoneme(phoneme).contains(category);
	}
	
	public Set<Phoneme> getPhonemesForCategory(Character category) {
		return categoryToPhoneme.getOrDefault(category, Collections.emptySet());
	}
	
	public Set<Character> getCategoriesForPhoneme(Phoneme phoneme) {
		return phonemeToCategory.getOrDefault(phoneme, Collections.emptySet());
	}
	
}
