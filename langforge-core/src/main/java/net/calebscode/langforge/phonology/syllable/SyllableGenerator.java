package net.calebscode.langforge.phonology.syllable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class SyllableGenerator {

	private Map<String, Integer> patterns;
	private RandomGenerator random;
	private int totalWeight;

	public SyllableGenerator() {
		this(new Random());
	}

	public SyllableGenerator(RandomGenerator random) {
		this.random = random;
		patterns = new HashMap<>();
	}

	public void addPattern(String pattern) {
		addPattern(pattern, 1);
	}

	public void addPattern(String pattern, int weight) {
		if (weight < 1) {
			throw new IllegalArgumentException("Weight must be positive.");
		}

		Integer previousWeight = patterns.put(pattern, weight);
		if (previousWeight != null) {
			totalWeight -= previousWeight;
		}
		totalWeight += weight;
	}

	public void removePattern(String pattern) {
		Integer weight = patterns.remove(pattern);
		if (weight != null) {
			totalWeight -= weight;
		}
	}

	public Set<String> getPatterns() {
		return patterns
			.entrySet()
			.stream()
			.map(Entry::getKey)
			.collect(Collectors.toSet());
	}

	public Map<String, Integer> getPatternsWithWeights() {
		return Collections.unmodifiableMap(patterns);
	}

	public String randomPattern() {
		int selection = random.nextInt(totalWeight);
		int currentWeight = 0;

		for (var entry : patterns.entrySet()) {
			currentWeight += entry.getValue();
			if (selection < currentWeight) {
				return entry.getKey();
			}
		}

		throw new IllegalStateException("Invalid totalWeight in SyllableGenerator. If you're seeing this, its due to a library bug - please report it!");
	}

	public Syllable randomSyllable(SyllablePatternCategoryMap categories) {
		if (patterns.isEmpty()) {
			throw new IllegalStateException("Syllable generator must have patterns to generate from.");
		}

		String pattern = randomPattern();
		var phonemes = pattern
			.chars()
			.mapToObj(categoryChar -> {
				var phonemesForCategory = categories.getPhonemes((char) categoryChar);
				var selection = random.nextInt(phonemesForCategory.size());
				return phonemesForCategory.stream().skip(selection).findFirst().get();
			})
			.toList();

		return new Syllable(phonemes);
	}

	public Set<Syllable> generateAllSyllables(SyllablePatternCategoryMap categories) {
		return patterns
			.keySet()
			.stream()
			.flatMap(pattern -> generateAllSyllables(List.of(), pattern, categories).stream())
			.distinct()
			.map(Syllable::new)
			.collect(Collectors.toSet());
	}

	private static Set<List<Phoneme>> generateAllSyllables(List<Phoneme> currentSyllable, String pattern, SyllablePatternCategoryMap categories) {
		if (pattern.length() == 0) {
			return Set.of(currentSyllable);
		}

		var outcomes = new HashSet<List<Phoneme>>();
		var generatable = categories.getPhonemes(pattern.charAt(0));
		for (Phoneme phonemeToAdd : generatable) {
			var modifiedSyllable = new ArrayList<>(currentSyllable);
			modifiedSyllable.add(phonemeToAdd);
			outcomes.addAll(generateAllSyllables(modifiedSyllable, pattern.substring(1), categories));
		}

		return outcomes;
	}

}
