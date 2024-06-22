package net.calebscode.langtool.phonology.phoneme;

import java.util.Set;

/**
 * Maps between Phonemes and IPA Strings.
 *
 * For all <code>IpaPhonemeMapper</code>s, any Phoneme or IPA String x which has a valid mapping
 * must satisfy the following conditions:
 * <li><code>getIpa(getPhoneme(x)).equals(x)</code>
 * <li><code>getPhoneme(getIpa(x)).equals(x)</code>
 */
public interface IpaPhonemeMapper {

	/**
	 * Whether or not this mapper can convert the Phoneme into an IPA String.
	 * @param phoneme
	 * @return
	 */
	public boolean canMap(Phoneme phoneme);

	/**
	 * Whether or not this mapper can conver the IPA String into a Phoneme.
	 * @param ipa
	 * @return
	 */
	public boolean canMap(String ipa);

	public String getIpa(Phoneme phoneme);
	public Phoneme getPhoneme(String ipa);

	public Set<Entry> entrySet();

	public record Entry(String ipa, Phoneme phoneme) {}

}
