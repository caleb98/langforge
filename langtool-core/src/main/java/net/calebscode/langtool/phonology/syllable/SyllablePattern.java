package net.calebscode.langtool.phonology.syllable;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.CATEGORY_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.FEATURE_PHONEME_CATEGORY;

import java.util.ArrayList;
import java.util.Collections;
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
	private Set<String> allSyllables;

	private Random rand = new Random();

	public SyllablePattern(SyllablePatternCategoryMap categoryMap, List<LiteralResolver> parts) {
		this.categoryMap = categoryMap;
		this.parts = parts;
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
			allPatterns = Set.of("");
			for (var part : parts) {
				allPatterns = allPatterns.stream().flatMap(
					existing -> part.resolveAll().stream().map(resolved -> existing + resolved)
				).collect(Collectors.toSet());
			}
		}

		return Collections.unmodifiableSet(allPatterns);
	}

	public Syllable randomSyllable() {
		String pattern = randomPattern();
		var phonemes = pattern.chars()
				.mapToObj(categoryChar -> getRandomPhonemeForCategory((char) categoryChar))
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

		return new Syllable(
			onset.toArray(new Phoneme[onset.size()]),
			nucleus.toArray(new Phoneme[nucleus.size()]),
			coda.toArray(new Phoneme[coda.size()])
		);
	}

	public Set<String> allSyllables() {
		if (allSyllables == null) {
			allSyllables = allPatterns().stream()
				.flatMap(pattern -> {
					var all = Set.of("");
					for (var categoryChar : pattern.toCharArray()) {
						all = all.stream().flatMap(
							existing -> categoryMap.getPhonemesForCategory(categoryChar).stream().map(phonemeSeq -> existing + phonemeSeq.toString())
						).collect(Collectors.toSet());
					}
					return all.stream();
				}).collect(Collectors.toSet());
		}

		return allSyllables;
	}

	private Phoneme getRandomPhonemeForCategory(Character categoryChar) {
		var options = categoryMap.getPhonemesForCategory(categoryChar);
		if (options.isEmpty()) {
			throw new RuntimeException("Invalid class character '" + categoryChar + "'. No phonemes available for this class.");
		}
		return options.stream().skip(rand.nextInt(options.size())).findFirst().get();
	}

}