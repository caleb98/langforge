package net.calebscode.langtool.phonology.syllable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import net.calebscode.langtool.phonology.phoneme.Phoneme;

public class Syllable {

	private static final Phoneme[] EMPTY_PHONEME_ARRAY = new Phoneme[0];

	public Optional<Phoneme[]> onset;
	public Optional<Phoneme[]> nucleus;
	public Optional<Phoneme[]> coda;

	public Syllable(Phoneme[] onset, Phoneme[] nucleus, Phoneme[] coda) {
		this.onset = Optional.ofNullable(onset);
		this.nucleus = Optional.ofNullable(nucleus);
		this.coda = Optional.ofNullable(coda);
	}

	public Stream<Phoneme> phonemeStream() {
		var onsetStream = Arrays.stream(onset.orElse(EMPTY_PHONEME_ARRAY));
		var nucleusStream = Arrays.stream(nucleus.orElse(EMPTY_PHONEME_ARRAY));
		var codaStream = Arrays.stream(coda.orElse(EMPTY_PHONEME_ARRAY));

		return Stream.of(onsetStream, nucleusStream, codaStream)
				.flatMap(Function.identity());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(onset.orElse(EMPTY_PHONEME_ARRAY).toString());
		sb.append(nucleus.orElse(EMPTY_PHONEME_ARRAY).toString());
		sb.append(coda.orElse(EMPTY_PHONEME_ARRAY).toString());

		return sb.toString();
	}

}
