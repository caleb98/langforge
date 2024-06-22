package net.calebscode.langtool.phonology;

import net.calebscode.langtool.phonology.phoneme.IpaPhonemeMapper;

public interface IpaRenderable {

	public String render(IpaPhonemeMapper mapper);

}
