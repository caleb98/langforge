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

	public void append(Phoneme phoneme) {
		var newMeta = new PhonemeMetadata(foundSyllableBoundary, false, foundWordBoundary, false);

		finalizePreviousPhonemeMetadata();

		phonemes.add(phoneme);
		metadata.add(newMeta);
	}

	public void append(Syllable syllable) {
		appendSyllableBoundary();
		syllable.phonemeStream().forEach(this::append);
		appendSyllableBoundary();
	}

	public void append(Word word) {
		appendWordBoundary();
		word.getSyllables().forEach(this::append);
		appendWordBoundary();
	}

	public void appendSyllableBoundary() {
		foundSyllableBoundary = true;
	}

	public void appendWordBoundary() {
		foundWordBoundary = true;
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
