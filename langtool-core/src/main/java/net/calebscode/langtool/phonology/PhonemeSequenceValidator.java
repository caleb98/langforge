package net.calebscode.langtool.phonology;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.syllable.Syllable;
import net.calebscode.langtool.phonology.syllable.SyllablePattern;

public class PhonemeSequenceValidator {

	private SyllablePattern pattern;

	public PhonemeSequenceValidator(SyllablePattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Optionally returns a PhonemeSequence that has been revalidated based on the given acceptable
	 * syllable patterns. If a valid series of syllables could not be constructed from the given
	 * sequence with these patterns, an empty optional is returned.
	 * @param sequence
	 * @return
	 */
	public PhonemeSequence revalidate(PhonemeSequence sequence) throws PhonemeSequenceValidationException {
		var syllablePatterns = getSyllablePatterns(sequence, sequence.length() - 1);
		if (syllablePatterns == null) {
			throw new PhonemeSequenceValidationException("Could parse sequence into series of valid syllables.");
		}

		var syllables = new ArrayList<Syllable>(syllablePatterns.size());
		for (var syllablePattern : syllablePatterns) {
			syllables.add(new Syllable(sequence.subsequence(0, syllablePattern.length()).getPhonemes()));
			sequence = sequence.subsequence(syllablePattern.length());
		}

		var builder = new PhonemeSequenceBuilder();
		for (var syllable : syllables) {
			builder.append(syllable);
		}

		return builder.build();
	}

	List<String> getSyllablePatterns(PhonemeSequence sequence, int position) throws PhonemeSequenceValidationException {
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

	Set<String> getPossibleSyllablePatterns(PhonemeSequence sequence, int position, String currentPattern) throws PhonemeSequenceValidationException {
		System.out.print("");
		// Stop when we're out of phonemes or our pattern is invalid.
		if (!pattern.allPatterns().stream().anyMatch(p -> p.endsWith(currentPattern))) {
			return Set.of();
		}
		else if (position < 0) {
			return pattern.allPatterns().contains(currentPattern) ? Set.of(currentPattern) : Set.of();
		}

		var patterns = new HashSet<String>();

		// If the current pattern exactly matches a valid pattern, go ahead and add it to the result.
		if (pattern.allPatterns().contains(currentPattern)) {
			patterns.add(currentPattern);
		}

		// Recurse through patterns that would be created by the next phoneme
		var currentPhoneme = sequence.phonemeAt(position);
		var possibleCategories = pattern.getCategoryMap().computePhonemeCategories(currentPhoneme);

		if (possibleCategories.size() == 0) {
			throw new PhonemeSequenceValidationException("Encountered phoneme that does not fit into any syllable rule categories: " + currentPhoneme);
		}

		for (var category : possibleCategories) {
			patterns.addAll(getPossibleSyllablePatterns(sequence, position - 1, category + currentPattern));
		}

		return patterns;
	}

}
