package net.calebscode.langtool.phonology.phoneme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PhonemeSequence {

	public static final PhonemeSequence EMPTY = new PhonemeSequence();

	final List<Phoneme> phonemes;

	public PhonemeSequence(Collection<Phoneme> phonemes) {
		this.phonemes = new ArrayList<>(phonemes);
	}
	
	public PhonemeSequence(Phoneme... phonemes) {
		this.phonemes = List.of(phonemes);
	}

	public List<Phoneme> getPhonemes() {
		return Collections.unmodifiableList(phonemes);
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
