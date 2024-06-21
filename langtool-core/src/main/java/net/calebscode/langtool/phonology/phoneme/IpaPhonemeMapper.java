package net.calebscode.langtool.phonology.phoneme;

import java.util.Map;

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

	/**
	 * Retrieves an IPA String for a Phoneme with the specified features.
	 * @param features
	 * @return
	 */
	public String getIpa(Map<String, String> features);

	/**
	 * Returns whether or not this mapper can convert the given feature set into
	 * an IPA String.
	 * @param features
	 * @return
	 */
	public boolean hasIpaForFeatures(Map<String, String> features);

}
