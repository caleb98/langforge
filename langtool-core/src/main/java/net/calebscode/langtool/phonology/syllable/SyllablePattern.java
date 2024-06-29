package net.calebscode.langtool.phonology.syllable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler.LiteralResolver;

public class SyllablePattern {

	private SyllablePatternCategoryMap categoryMap;
	private List<LiteralResolver> parts;

	private Set<String> allPatterns;
	private Set<Syllable> allSyllables;

	private Random rand = new Random();

	public SyllablePattern(SyllablePatternCategoryMap categoryMap, List<LiteralResolver> parts) {
		this.categoryMap = categoryMap;
		this.parts = parts;
	}

	public SyllablePatternCategoryMap getCategoryMap() {
		return categoryMap;
	}

	public String randomPattern() {
		StringBuilder sb = new StringBuilder();
		for (var part : parts) {
			sb.append(part.resolve());
		}
		return sb.toString();
	}

	public Set<String> allPatterns() {
		if (allPatterns == null) {
			generateAllPatterns();
		}
		return Collections.unmodifiableSet(allPatterns);
	}

	private void generateAllPatterns() {
		allPatterns = Set.of("");
		for (var part : parts) {
			allPatterns = allPatterns.stream().flatMap(
				existing -> part.resolveAll().stream().map(resolved -> existing + resolved)
			).collect(Collectors.toSet());
		}
	}

	public Syllable randomSyllable() {
		String pattern = randomPattern();
		var phonemes = pattern.chars()
				.mapToObj(categoryChar -> getRandomPhonemeForCategory((char) categoryChar))
				.toList();

		return new Syllable(phonemes);
	}

	public Set<Syllable> allSyllables() {
		if (allSyllables == null) {
			generateAllSyllables();
		}
		return Collections.unmodifiableSet(allSyllables);
	}

	private void generateAllSyllables() {
		Set<List<Phoneme>> allSequences = new HashSet<List<Phoneme>>();
		for (var pattern : allPatterns()) {
			allSequences.addAll(generateAllSyllables(List.of(), pattern));
		}

		allSyllables = allSequences.stream().map(Syllable::new).collect(Collectors.toSet());
	}

	private Set<List<Phoneme>> generateAllSyllables(List<Phoneme> currentSyllable, String pattern) {
		if (pattern.length() == 0) {
			return Set.of(currentSyllable);
		}

		var outcomes = new HashSet<List<Phoneme>>();
		var generatable = categoryMap.getGeneratablePhonemes(pattern.charAt(0));
		for (Phoneme phonemeToAdd : generatable) {
			var modifiedSyllable = new ArrayList<>(currentSyllable);
			modifiedSyllable.add(phonemeToAdd);
			outcomes.addAll(generateAllSyllables(modifiedSyllable, pattern.substring(1)));
		}

		return outcomes;
	}

	private Phoneme getRandomPhonemeForCategory(Character categoryChar) {
		var options = categoryMap.getGeneratablePhonemes(categoryChar);
		if (options.isEmpty()) {
			throw new RuntimeException("Invalid class character '" + categoryChar + "'. No phonemes available for this class.");
		}
		return options.stream().skip(rand.nextInt(options.size())).findFirst().get();
	}

}