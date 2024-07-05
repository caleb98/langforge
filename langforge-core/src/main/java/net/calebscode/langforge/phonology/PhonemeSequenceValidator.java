package net.calebscode.langforge.phonology;

import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;

public interface PhonemeSequenceValidator {

	/**
	 * Validates a phoneme sequence and returns a new sequence with updated
	 * metadata based on the validation performed.
	 * @param sequence
	 * @return
	 * @throws PhonemeSequenceValidationException if the phonemes in the sequence cannot produce valid metadata.
	 */
	public PhonemeSequence validate(PhonemeSequence sequence) throws PhonemeSequenceValidationException;

}