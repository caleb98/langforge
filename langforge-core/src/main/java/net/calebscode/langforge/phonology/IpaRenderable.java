package net.calebscode.langforge.phonology;

import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMapper;

public interface IpaRenderable {

	public String render(PhonemeRepresentationMapper mapper);

}
