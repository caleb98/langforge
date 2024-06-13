package net.calebscode.langtool.phonology.phoneme;

public interface IpaPhonemeMapper {

	public boolean hasIpa(String ipa);
	public boolean hasPhoneme(Phoneme phoneme);
	
	public Phoneme getPhoneme(String ipa);
	
}
