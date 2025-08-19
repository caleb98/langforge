package net.calebscode.langforge.phonology.phoneme.string;

public interface PhonemeStringValidator {

	/**
	 * Validates a <code>PhonemeString</code> and returns a new string with updated context data based on
	 * the validation performed.
	 * @param sequence
	 * @return
	 * @throws PhonemeStringValidationException if the phonemes in the string cannot be validated.
	 */
	public PhonemeString validate(PhonemeString sequence) throws PhonemeStringValidationException;

}
