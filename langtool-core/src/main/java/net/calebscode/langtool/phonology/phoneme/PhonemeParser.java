package net.calebscode.langtool.phonology.phoneme;

import java.util.Collection;

public interface PhonemeParser {

	public Collection<Phoneme> parse(String raw);

	public default PhonemeSequence parseSequence(String raw) {
		return new PhonemeSequence((Phoneme[]) parse(raw).toArray());
	}

}