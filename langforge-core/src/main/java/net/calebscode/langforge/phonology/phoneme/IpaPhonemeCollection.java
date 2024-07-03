package net.calebscode.langforge.phonology.phoneme;

import java.util.Set;

public class IpaPhonemeCollection implements IpaPhonemeMapper {

	private IpaPhonemeMap map = new IpaPhonemeMap();

	public void addPhonemeMap(IpaPhonemeMapper newMap) {
		for(var entry : newMap.entrySet()) {
			map.addMapping(entry.ipa(), entry.phoneme());
		}
	}

	@Override
	public boolean canMap(Phoneme phoneme) {
		return map.canMap(phoneme);
	}

	@Override
	public boolean canMap(String ipa) {
		return map.canMap(ipa);
	}

	@Override
	public String getIpa(Phoneme phoneme) {
		return map.getIpa(phoneme);
	}

	@Override
	public Phoneme getPhoneme(String ipa) {
		return map.getPhoneme(ipa);
	}

	@Override
	public Set<Entry> entrySet() {
		return map.entrySet();
	}

}
