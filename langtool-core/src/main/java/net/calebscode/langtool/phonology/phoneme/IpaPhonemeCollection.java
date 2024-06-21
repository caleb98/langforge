package net.calebscode.langtool.phonology.phoneme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IpaPhonemeCollection implements IpaPhonemeMapper {

	private List<IpaPhonemeMap> maps = new ArrayList<>();

	public void addPhonemeMap(IpaPhonemeMap map) {
		maps.add(map);
	}

	@Override
	public boolean hasIpa(String ipa) {
		for (var map : maps) {
			if (map.hasIpa(ipa)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canMapTo(Phoneme phoneme) {
		var retrieved = getPhoneme(phoneme.representation());
		return Objects.equals(retrieved, phoneme);
	}

	@Override
	public Phoneme getPhoneme(String ipa) {
		for (var map : maps) {
			if (map.hasIpa(ipa)) {
				return map.getPhoneme(ipa);
			}
		}
		return null;
	}

	@Override
	public String getIpa(Map<String, String> features) {
		for (var map : maps) {
			if (map.hasIpaForFeatures(features)) {
				return map.getIpa(features);
			}
		}
		return null;
	}

	@Override
	public boolean hasIpaForFeatures(Map<String, String> features) {
		for (var map : maps) {
			if (map.hasIpaForFeatures(features)) {
				return true;
			}
		}
		return false;
	}

}
