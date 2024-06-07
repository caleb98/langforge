package net.calebscode.langtool;

import java.util.Collection;

public interface SyllableParser {

	public Collection<Syllable> parse(Collection<Phoneme> phonemes);

	public default Word parseWord(Collection<Phoneme> phonemes) {
		return new Word((Syllable[]) parse(phonemes).toArray());
	}

}
