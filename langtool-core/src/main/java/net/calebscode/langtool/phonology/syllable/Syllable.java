package net.calebscode.langtool.phonology.syllable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.calebscode.langtool.phonology.IpaRenderable;
import net.calebscode.langtool.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langtool.phonology.phoneme.Phoneme;

public class Syllable implements IpaRenderable {

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
	public String render(IpaPhonemeMapper mapper) {
		return phonemeStream().map(mapper::getIpa).collect(Collectors.joining());
	}

}
