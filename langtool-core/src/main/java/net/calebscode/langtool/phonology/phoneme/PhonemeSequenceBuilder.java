package net.calebscode.langtool.phonology.phoneme;

import java.util.ArrayList;

import net.calebscode.langtool.Word;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence.PhonemeMetadata;
import net.calebscode.langtool.phonology.syllable.Syllable;

public class PhonemeSequenceBuilder {

	private ArrayList<Phoneme> phonemes = new ArrayList<>();
	private ArrayList<PhonemeMetadata> metadata = new ArrayList<>();

	private boolean foundWordBoundary = false;
	private boolean foundSyllableBoundary = false;

	public PhonemeSequenceBuilder append(String ipa, IpaPhonemeMapper mapper) {
		for (int i = 0; i < ipa.length(); i++) {
			if (ipa.charAt(i) == '#') {
				foundWordBoundary = true;
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
					throw new RuntimeException("Unable to append IPA '" + ipa + "'. Unmappable IPA sequence at position " + i);
				}

				append(mapper.getPhoneme(longestMatch));

			}
		}

		return this;
	}

	public PhonemeSequenceBuilder append(Phoneme phoneme) {
		var newMeta = new PhonemeMetadata(foundSyllableBoundary, false, foundWordBoundary, false);

		finalizePreviousPhonemeMetadata();

		phonemes.add(phoneme);
		metadata.add(newMeta);
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

	public PhonemeSequence build() {
		finalizePreviousPhonemeMetadata();
		return new PhonemeSequence(phonemes, metadata);
	}

	private void finalizePreviousPhonemeMetadata() {
		if (metadata.size() > 0) {
			var prevMeta = metadata.getLast();
			var prevMetaUpdated = new PhonemeMetadata(
					prevMeta.isSyllableStart(),
					foundSyllableBoundary,
					prevMeta.isWordStart(),
					foundWordBoundary
				);
			metadata.set(metadata.size() - 1, prevMetaUpdated);
		}

		foundWordBoundary = false;
		foundSyllableBoundary = false;
	}

}
