package net.calebscode.langforge.phonology.phoneme.string;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.calebscode.langforge.phonology.syllable.Syllable;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;

public class SyllablePatternValidator implements PhonemeStringValidator {

	private SyllablePatternCategoryMap categories;
	private Set<String> validPatterns;

	public SyllablePatternValidator(SyllablePatternCategoryMap categories, Collection<String> validPatterns) {
		this.categories = categories;
		this.validPatterns = new HashSet<>(validPatterns);
	}

	@Override
	public PhonemeString validate(PhonemeString sequence) throws PhonemeStringValidationException {
		var syllablePatterns = getSyllablePatterns(sequence, sequence.length() - 1);
		if (syllablePatterns == null) {
			throw new PhonemeStringValidationException("Could not parse sequence into series of valid syllables.");
		}

		var syllables = new ArrayList<Syllable>(syllablePatterns.size());
		for (var syllablePattern : syllablePatterns) {
			syllables.add(new Syllable(sequence.substring(0, syllablePattern.length()).getPhonemes()));
			sequence = sequence.substring(syllablePattern.length());
		}

		var builder = new PhonemeStringBuilder();
		for (var syllable : syllables) {
			builder.append(syllable);
		}

		return builder.build();
	}

	private List<String> getSyllablePatterns(PhonemeString sequence, int position) throws PhonemeStringValidationException {
		// We're done once we're past the start of the phoneme sequence.
		if (position < 0) {
			return List.of();
		}

		var options = new TreeSet<String>((a, b) -> Integer.compare(a.length(), b.length()));
		options.addAll(getPossibleSyllablePatterns(sequence, position, ""));

		// If options is initially empty, there's no way for us to create a valid syllable
		// to complete the phoneme sequence. Return "null" to indicate that this path is a
		// dead end.
		if (options.isEmpty()) {
			return null;
		}

		while (!options.isEmpty()) {
			var testPattern = options.removeLast();
			var nextPatterns = getSyllablePatterns(sequence, position - testPattern.length());
			if (nextPatterns != null) {
				var patterns = new ArrayList<String>();
				patterns.addAll(nextPatterns);
				patterns.add(testPattern);
				return patterns;
			}
		}

		// None of the possible syllable patterns that could have been used here lead us
		// to an acceptable sequence of syllable patterns. So, this whole branch is a dead
		// end.
		return null;
	}

	private Set<String> getPossibleSyllablePatterns(PhonemeString sequence, int position, String currentPattern) throws PhonemeStringValidationException {
		// Stop when we're out of phonemes or our pattern is invalid.
		if (!validPatterns.stream().anyMatch(p -> p.endsWith(currentPattern))) {
			return Set.of();
		}
		else if (position < 0) {
			return validPatterns.contains(currentPattern) ? Set.of(currentPattern) : Set.of();
		}

		var patterns = new HashSet<String>();

		// If the current pattern exactly matches a valid pattern, go ahead and add it to the result.
		if (validPatterns.contains(currentPattern)) {
			patterns.add(currentPattern);
		}

		// Recurse through patterns that would be created by the next phoneme
		var currentPhoneme = sequence.phonemeAt(position);
		var possibleCategories = categories.computePhonemeCategories(currentPhoneme);

		if (possibleCategories.size() == 0) {
			throw new PhonemeStringValidationException("Encountered phoneme that does not fit into any syllable rule categories: " + currentPhoneme);
		}

		for (var category : possibleCategories) {
			patterns.addAll(getPossibleSyllablePatterns(sequence, position - 1, category + currentPattern));
		}

		return patterns;
	}

}
