package net.calebscode.langtool;

import java.util.Collection;

public class PhonemeSequence {

	public static final PhonemeSequence EMPTY = new PhonemeSequence();

	final Phoneme[] phonemes;

	public PhonemeSequence(Collection<Phoneme> phonemes) {
		this.phonemes = phonemes.toArray(new Phoneme[phonemes.size()]);
	}
	
	public PhonemeSequence(Phoneme... phonemes) {
		this.phonemes = phonemes;
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
