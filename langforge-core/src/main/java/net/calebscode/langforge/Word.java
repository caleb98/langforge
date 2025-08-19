package net.calebscode.langforge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.calebscode.langforge.phonology.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.syllable.Syllable;

public class Word implements PhonemeSequence {

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
	public List<Phoneme> getPhonemes() {
		return syllables
			.stream()
			.flatMap(syllable -> syllable.phonemes().stream())
			.toList();
	}

}
