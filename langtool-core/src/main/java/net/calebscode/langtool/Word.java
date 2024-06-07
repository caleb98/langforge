package net.calebscode.langtool;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.syllable.Syllable;

public class Word {

	public List<Syllable> syllables = new ArrayList<>();

	public Word(Syllable... syllables) {
		for (var syllable : syllables) {
			this.syllables.add(syllable);
		}
	}

	@Override
	public String toString() {
		return syllables.stream()
			.map(syllable -> syllable.toString())
			.collect(Collectors.joining("."));
	}

}
