package net.calebscode.langforge.phonology.syllable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.calebscode.langforge.phonology.IpaRenderable;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public record Syllable(List<Phoneme> phonemes) implements IpaRenderable {

	public Syllable(List<Phoneme> phonemes) {
		this.phonemes = Collections.unmodifiableList(phonemes);
	}

	public Syllable(Phoneme... phonemes) {
		this(List.of(phonemes));
	}

	@Override
	public String render(PhonemeRepresentationMapper mapper) {
		return phonemes.stream().map(mapper::getIpa).collect(Collectors.joining());
	}

}
