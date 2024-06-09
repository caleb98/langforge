package net.calebscode.langtool.phonology.syllable;

import java.util.ArrayList;
import java.util.Optional;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;

public class Syllable {

	public Optional<PhonemeSequence> onset;
	public Optional<PhonemeSequence> nucleus;
	public Optional<PhonemeSequence> coda;

	public Syllable(PhonemeSequence onset, PhonemeSequence nucleus, PhonemeSequence coda) {
		this.onset = Optional.ofNullable(onset);
		this.nucleus = Optional.ofNullable(nucleus);
		this.coda = Optional.ofNullable(coda);
	}

	public PhonemeSequence asPhonemeSequence() {
		var phonemes = new ArrayList<Phoneme>();
		onset.ifPresent(seq -> phonemes.addAll(seq.phonemes()));
		nucleus.ifPresent(seq -> phonemes.addAll(seq.phonemes()));
		coda.ifPresent(seq -> phonemes.addAll(seq.phonemes()));
		return new PhonemeSequence(phonemes);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(onset.orElse(PhonemeSequence.EMPTY).toString());
		sb.append(nucleus.orElse(PhonemeSequence.EMPTY).toString());
		sb.append(coda.orElse(PhonemeSequence.EMPTY).toString());

		return sb.toString();
	}

}
