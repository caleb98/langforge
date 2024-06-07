package net.calebscode.langtool.phonology.phoneme;

import java.util.stream.Collectors;

import net.calebscode.langtool.Word;

public class PhonologicalRule {

	private String match;
	private String output;
	private String environment;
	
	public PhonologicalRule(String match, String output, String environment) {
		this.match = match;
		this.output = output;
		this.environment = environment;
	}
	
	public Word applyTo(Word initial) {
		String ipa = initial.syllables.stream().map(syllable -> syllable.toString()).collect(Collectors.joining("."));
		ipa = "#" + ipa + "#";
		
		System.out.println(ipa);
		return null;
	}
	
}
