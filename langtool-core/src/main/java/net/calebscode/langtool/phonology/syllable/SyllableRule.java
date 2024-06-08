package net.calebscode.langtool.phonology.syllable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.syllable.SyllableRuleCompiler.LiteralResolver;

public class SyllableRule {
	
	private List<LiteralResolver> parts;
	
	public SyllableRule(List<LiteralResolver> parts) {
		this.parts = parts;
	}
	
	public String generateSyllable() {
		StringBuilder sb = new StringBuilder();
		for (var part : parts) {
			sb.append(part.resolve());
		}
		return sb.toString();
	}
	
	public Set<String> allSyllables() {
		var all = Set.of("");
		for (var part : parts) {
			all = all.stream().flatMap(
				existing -> part.resolveAll().stream().map(resolved -> existing + resolved)
			).collect(Collectors.toSet());
		}
		return all;
	}
	
}