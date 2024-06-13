package net.calebscode.langtool.phonology.rules;

import java.util.stream.Collectors;

import net.calebscode.langtool.Word;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeRepresentation;

public class PhonologicalRule  {

	private PhonemeRepresentation match;
	private Phoneme replacement;
	private String environment;

	public PhonologicalRule(PhonemeRepresentation match, Phoneme replacement) {
		this.match = match;
		this.replacement = replacement;
		// TODO environment = "";
	}

	public Word applyTo(Word initial) {
		String ipa = initial.getSyllables().stream().map(syllable -> syllable.toString()).collect(Collectors.joining("."));
		ipa = "#" + ipa + "#";

		System.out.println(ipa);
		return null;
	}

}
