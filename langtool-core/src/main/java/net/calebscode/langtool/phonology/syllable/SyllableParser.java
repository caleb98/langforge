package net.calebscode.langtool.phonology.syllable;

import java.util.Collection;

import net.calebscode.langtool.Word;
import net.calebscode.langtool.phonology.phoneme.Phoneme;

public interface SyllableParser {

	public Collection<Syllable> parse(Collection<Phoneme> phonemes);

	public default Word parseWord(Collection<Phoneme> phonemes) {
		return new Word((Syllable[]) parse(phonemes).toArray());
	}

}
