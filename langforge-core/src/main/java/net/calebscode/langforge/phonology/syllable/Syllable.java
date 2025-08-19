package net.calebscode.langforge.phonology.syllable;

import java.util.Collections;
import java.util.List;

import net.calebscode.langforge.phonology.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public record Syllable(List<Phoneme> phonemes) implements PhonemeSequence {

	public Syllable(List<Phoneme> phonemes) {
		this.phonemes = Collections.unmodifiableList(phonemes);
	}

	public Syllable(Phoneme... phonemes) {
		this(List.of(phonemes));
	}

	@Override
	public List<Phoneme> getPhonemes() {
		return phonemes;
	}

}
