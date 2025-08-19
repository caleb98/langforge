package net.calebscode.langforge.phonology.phoneme;

import java.util.ArrayList;

import net.calebscode.langforge.Word;
import net.calebscode.langforge.phonology.syllable.Syllable;

public class PhonemeSequenceBuilder {

	private ArrayList<Phoneme> phonemes = new ArrayList<>();
	private ArrayList<PhonemeContext> contexts = new ArrayList<>();

	private boolean foundWordBoundary = false;
	private boolean foundSyllableBoundary = false;

	public PhonemeSequenceBuilder append(String ipa, PhonemeRepresentationMapper mapper) throws PhonemeRepresentationMappingException {
		for (int i = 0; i < ipa.length(); i++) {
			if (ipa.charAt(i) == '#') {
				foundWordBoundary = true;
				foundSyllableBoundary = true;
			}
			else if (ipa.charAt(i) == '.') {
				foundSyllableBoundary = true;
			}
			else {

				String longestMatch = null;
				for (int k = i + 1; k <= ipa.length(); k++) {
					String sub = ipa.substring(i, k);
					if (mapper.canMap(sub)) {
						longestMatch = sub;
					}
				}

				if (longestMatch == null) {
					throw new PhonemeRepresentationMappingException(
						"Unable to append representation string '" + ipa +
						"' at position " + i + ". No corresponding phoneme was found.");
				}

				append(mapper.getPhoneme(longestMatch));

			}
		}

		return this;
	}

	public PhonemeSequenceBuilder append(Phoneme phoneme) {
		var newContext = new PhonemeContext(foundSyllableBoundary, false, foundWordBoundary, false);

		finalizePreviousPhonemeContext();

		phonemes.add(phoneme);
		contexts.add(newContext);
		return this;
	}

	public PhonemeSequenceBuilder append(Syllable syllable) {
		appendSyllableBoundary();
		syllable.phonemes().stream().forEach(this::append);
		appendSyllableBoundary();
		return this;
	}

	public PhonemeSequenceBuilder append(Word word) {
		appendWordBoundary();
		word.getSyllables().forEach(this::append);
		appendWordBoundary();
		return this;
	}

	public PhonemeSequenceBuilder appendSyllableBoundary() {
		foundSyllableBoundary = true;
		return this;
	}

	public PhonemeSequenceBuilder appendWordBoundary() {
		foundWordBoundary = true;
		return this;
	}

	public PhonemeString build() {
		finalizePreviousPhonemeContext();
		return new PhonemeString(phonemes, contexts);
	}

	private void finalizePreviousPhonemeContext() {
		if (contexts.size() > 0) {
			var previousContext = contexts.getLast();
			var previousContextUpdated = new PhonemeContext(
					previousContext.isSyllableStart(),
					foundSyllableBoundary,
					previousContext.isWordStart(),
					foundWordBoundary
				);
			contexts.set(contexts.size() - 1, previousContextUpdated);
		}

		foundWordBoundary = false;
		foundSyllableBoundary = false;
	}

}
