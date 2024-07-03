package net.calebscode.langforge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.calebscode.langforge.phonology.IpaRenderable;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.syllable.Syllable;

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
				.flatMap(syllable -> syllable.phonemes().stream())
				.map(mapper::getIpa)
				.collect(Collectors.joining());
	}

}
