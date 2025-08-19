package net.calebscode.langforge.phonology.phoneme;

import java.util.Set;

public class PhonemeRepresentationCollection implements PhonemeRepresentationMapper {

	private PhonemeStringMap map = new PhonemeStringMap();

	public void addPhonemeMap(PhonemeRepresentationMapper newMap) {
		for(var entry : newMap.entrySet()) {
			map.addMapping(entry.string(), entry.phoneme());
		}
	}

	@Override
	public boolean canMap(Phoneme phoneme) {
		return map.canMap(phoneme);
	}

	@Override
	public boolean canMap(String string) {
		return map.canMap(string);
	}

	@Override
	public String getRepresentation(Phoneme phoneme) {
		return map.getRepresentation(phoneme);
	}

	@Override
	public Phoneme getPhoneme(String string) {
		return map.getPhoneme(string);
	}

	@Override
	public Set<Entry> entrySet() {
		return map.entrySet();
	}

}
