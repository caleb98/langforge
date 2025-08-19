package net.calebscode.langforge.phonology.phoneme;

import java.util.Set;

/**
 * Maps between Phonemes and their representation Strings.
 *
 * All implementations of <code>PhonemeRepresentationMapper</code> must ensure that for any
 * Phoneme or String x which has a valid mapping, the following conditions are true:
 * <li><code>getIpa(getPhoneme(x)).equals(x)</code>
 * <li><code>getPhoneme(getIpa(x)).equals(x)</code>
 */
public interface PhonemeRepresentationMapper {

	public boolean canMap(Phoneme phoneme);

	public boolean canMap(String string);

	public String getRepresentation(Phoneme phoneme);

	public Phoneme getPhoneme(String ipa);

	public Set<Entry> entrySet();

	public record Entry(String string, Phoneme phoneme) {}

}
