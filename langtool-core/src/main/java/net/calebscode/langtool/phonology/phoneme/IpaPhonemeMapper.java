package net.calebscode.langtool.phonology.phoneme;

public interface IpaPhonemeMapper {

	/**
	 * Returns whether or not <code>ipa</code> can map to a single phoneme.
	 * @param ipa
	 * @return true if this mapper contains a mapping for <code>ipa</code>; false otherwise
	 */
	public boolean hasIpa(String ipa);

	/**
	 * Returns whether or not this mapper is capable of producing the given phoneme.
	 * @param phoneme
	 * @return
	 */
	public boolean canMapTo(Phoneme phoneme);

	/**
	 * Retrieves the phoneme for a given ipa string.
	 * @param ipa
	 * @return the <code>Phoneme</code> associated with the ipa string; null if no mapping
	 */
	public Phoneme getPhoneme(String ipa);

}
