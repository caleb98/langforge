package net.calebscode.langtool.phonology.phoneme;

import java.util.Collections;
import java.util.List;

public record PhonemeSequence(List<Phoneme> phonemes) {

	public static final PhonemeSequence EMPTY = new PhonemeSequence();
	
	public PhonemeSequence(List<Phoneme> phonemes) {
		this.phonemes = Collections.unmodifiableList(phonemes);
	}
	
	public PhonemeSequence(Phoneme... phonemes) {
		this(List.of(phonemes));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var p : phonemes) {
			sb.append(p.toString());
		}
		return sb.toString();
	}

}
