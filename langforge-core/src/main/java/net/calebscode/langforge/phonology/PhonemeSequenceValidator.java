package net.calebscode.langforge.phonology;

import net.calebscode.langforge.phonology.phoneme.PhonemeString;

public interface PhonemeSequenceValidator {

	/**
	 * Validates a phoneme sequence and returns a new sequence with updated context data based on
	 * the validation performed.
	 * @param sequence
	 * @return
	 * @throws PhonemeSequenceValidationException if the phonemes in the sequence cannot be validated.
	 */
	public PhonemeString validate(PhonemeString sequence) throws PhonemeSequenceValidationException;

}
