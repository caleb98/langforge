package net.calebscode.langforge.phonology;

import java.util.List;

import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.PhonemeContext;

public interface PhonemeSequence {

	public List<Phoneme> getPhonemes();

	public default List<PhonemeContext> getPhonemeContexts() {
		return List.of();
	}

	public default boolean hasContexts() {
		return false;
	}

}
