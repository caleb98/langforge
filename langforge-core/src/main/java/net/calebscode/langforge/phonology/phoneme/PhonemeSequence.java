package net.calebscode.langforge.phonology.phoneme;

import java.util.List;

public interface PhonemeSequence {

	public List<Phoneme> getPhonemes();

	public default List<PhonemeContext> getPhonemeContexts() {
		return List.of();
	}

	public default boolean hasContexts() {
		return false;
	}

}
