package net.calebscode.langtool;

import static net.calebscode.langtool.StandardPhonemeFeatures.CATEGORY_VOWEL;
import static net.calebscode.langtool.StandardPhonemeFeatures.FEATURE_PHONEME_CATEGORY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.SyllableRuleCompiler.SyllableRule;

public class SyllableGenerator {

	private Map<Character, List<Phoneme>> phonemeClasses = new HashMap<>();
	private SyllableRule rule;
	private Random rand = new Random();

	public SyllableGenerator(String pattern) {
		rule = new SyllableRuleCompiler().compile(pattern);
	}
	
	public void addClassPhoneme(Character classChar, Phoneme phoneme) {
		var phonemes = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<Phoneme>());
		phonemes.add(phoneme);
	}
	
	public void addClassPhonemes(Character classChar, Phoneme... phonemes) {
		var existing = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<Phoneme>());
		existing.addAll(List.of(phonemes));
	}
	
	public Set<String> generateAllSyllables() {
		return rule.allSyllables().stream()
			.flatMap(pattern -> {
				var all = Set.of("");
				for (var classChar : pattern.toCharArray()) {
					all = all.stream().flatMap(
						existing -> phonemeClasses.get(classChar).stream().map(phoneme -> existing + phoneme.getRepresentation())
					).collect(Collectors.toSet());
				}
				return all.stream();
			}).collect(Collectors.toSet());
	}
	
	public Syllable generateSyllable() {
		String pattern = rule.generateSyllable();
		var phonemes = pattern.chars()
				.mapToObj(classChar -> getRandomPhonemeForClass((char) classChar))
				.toList();
		
		var onset = new ArrayList<Phoneme>();
		var nucleus = new ArrayList<Phoneme>();
		var coda = new ArrayList<Phoneme>();
		
		int current = 0;
		while (current < phonemes.size() && !phonemes.get(current).getFeature(FEATURE_PHONEME_CATEGORY).equals(CATEGORY_VOWEL)) {
			onset.add(phonemes.get(current));
			current++;
		}
		
		while (current < phonemes.size() && phonemes.get(current).getFeature(FEATURE_PHONEME_CATEGORY).equals(CATEGORY_VOWEL)) {
			nucleus.add(phonemes.get(current));
			current++;
		}
		
		while (current < phonemes.size()) {
			coda.add(phonemes.get(current));
			current++;
		}
		
		return new Syllable(new PhonemeSequence(onset), new PhonemeSequence(nucleus), new PhonemeSequence(coda));
	}
	
	private Phoneme getRandomPhonemeForClass(Character classChar) {
		if (!phonemeClasses.containsKey(classChar)) {
			throw new RuntimeException("Invalid class character '" + classChar + "'. No phonemes available for this class.");
		}
		
		var options = phonemeClasses.get(classChar);
		return options.get(rand.nextInt(options.size()));
	}
	
}
