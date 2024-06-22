package net.calebscode.langtool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.IpaRenderable;
import net.calebscode.langtool.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langtool.phonology.syllable.Syllable;

public class Word implements IpaRenderable {

	private List<Syllable> syllables = new ArrayList<>();

	public Word(Syllable... syllables) {
		for (var syllable : syllables) {
			this.syllables.add(syllable);
		}
	}

	public List<Syllable> getSyllables() {
		return Collections.unmodifiableList(syllables);
	}

	@Override
	public String render(IpaPhonemeMapper mapper) {
		return syllables.stream()
				.flatMap(Syllable::phonemeStream)
				.map(mapper::getIpa)
				.collect(Collectors.joining());
	}

}
