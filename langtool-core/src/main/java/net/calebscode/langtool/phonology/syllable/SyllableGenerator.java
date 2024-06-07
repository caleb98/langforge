package net.calebscode.langtool.phonology.syllable;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.CATEGORY_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_PHONEME_CATEGORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.syllable.SyllableRuleCompiler.SyllableRule;

public class SyllableGenerator {

	private Map<Character, List<PhonemeSequence>> phonemeClasses = new HashMap<>();
	private SyllableRule rule;
	private Random rand = new Random();

	public SyllableGenerator(String pattern) {
		rule = new SyllableRuleCompiler().compile(pattern);
	}
	
	public void addClassPhonemeSequence(Character classChar, PhonemeSequence sequence) {
		var existing = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<PhonemeSequence>());
		existing.add(sequence);
	}
	
	public void addClassPhonemeSequences(Character classChar, PhonemeSequence... sequences) {
		var existing = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<PhonemeSequence>());
		existing.addAll(List.of(sequences));
	}
	
	public void addClassPhoneme(Character classChar, Phoneme phoneme) {
		var existing = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<PhonemeSequence>());
		existing.add(new PhonemeSequence(phoneme));
	}
	
	public void addClassPhonemes(Character classChar, Phoneme... phonemes) {
		var existing = phonemeClasses.computeIfAbsent(classChar, c -> new ArrayList<PhonemeSequence>());
		for (var phoneme : phonemes) {
			existing.add(new PhonemeSequence(phoneme));
		}
	}
	
	public Set<String> generateAllSyllables() {
		return rule.allSyllables().stream()
			.flatMap(pattern -> {
				var all = Set.of("");
				for (var classChar : pattern.toCharArray()) {
					all = all.stream().flatMap(
						existing -> phonemeClasses.get(classChar).stream().map(phonemeSeq -> existing + phonemeSeq.toString())
					).collect(Collectors.toSet());
				}
				return all.stream();
			}).collect(Collectors.toSet());
	}
	
	public Syllable generateSyllable() {
		String pattern = rule.generateSyllable();
		var phonemes = pattern.chars()
				.mapToObj(classChar -> getRandomPhonemeSequenceForClass((char) classChar))
				.flatMap(phonemeSeq -> phonemeSeq.getPhonemes().stream())
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
	
	private PhonemeSequence getRandomPhonemeSequenceForClass(Character classChar) {
		if (!phonemeClasses.containsKey(classChar)) {
			throw new RuntimeException("Invalid class character '" + classChar + "'. No phonemes available for this class.");
		}
		
		var options = phonemeClasses.get(classChar);
		return options.get(rand.nextInt(options.size()));
	}
	
}
