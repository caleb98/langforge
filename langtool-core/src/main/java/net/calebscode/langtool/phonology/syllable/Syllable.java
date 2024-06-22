package net.calebscode.langtool.phonology.syllable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.IpaRenderable;
import net.calebscode.langtool.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langtool.phonology.phoneme.Phoneme;

public record Syllable(List<Phoneme> phonemes) implements IpaRenderable {

	public Syllable(List<Phoneme> phonemes) {
		this.phonemes = Collections.unmodifiableList(phonemes);
	}

	public Syllable(Phoneme... phonemes) {
		this(List.of(phonemes));
	}

	@Override
	public String render(IpaPhonemeMapper mapper) {
		return phonemes.stream().map(mapper::getIpa).collect(Collectors.joining());
	}

}
