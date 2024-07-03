package net.calebscode.langforge.phonology;

import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;

public interface IpaRenderable {

	public String render(IpaPhonemeMapper mapper);

}
