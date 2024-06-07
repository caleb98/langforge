package net.calebscode.langtool;

import java.util.Optional;

public class Syllable {

	public Optional<PhonemeSequence> onset;
	public Optional<PhonemeSequence> nucleus;
	public Optional<PhonemeSequence> coda;

	public Syllable(PhonemeSequence onset, PhonemeSequence nucleus, PhonemeSequence coda) {
		this.onset = Optional.ofNullable(onset);
		this.nucleus = Optional.ofNullable(nucleus);
		this.coda = Optional.ofNullable(coda);
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
