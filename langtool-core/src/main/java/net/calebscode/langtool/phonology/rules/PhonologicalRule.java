package net.calebscode.langtool.phonology.rules;

import java.util.Collections;
import java.util.List;

import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeRepresentation;

public class PhonologicalRule {

	private String source;
	private PhonemeRepresentation match;
	private PhonemeRepresentation replacement;

	private List<PhonemeRepresentation> preMatches;
	private List<PhonemeRepresentation> postMatches;

	public PhonologicalRule(String source, PhonemeRepresentation match, PhonemeRepresentation replacement,
			List<PhonemeRepresentation> preMatches, List<PhonemeRepresentation> postMatches) {
		this.source = source;
		this.match = match;
		this.replacement = replacement;
		this.preMatches = Collections.unmodifiableList(preMatches);
		this.postMatches = Collections.unmodifiableList(postMatches);
	}

	public String getSource() {
		return source;
	}

	public PhonemeRepresentation getMatch() {
		return match;
	}

	public PhonemeRepresentation getReplacement() {
		return replacement;
	}

	public List<PhonemeRepresentation> getPreMatches() {
		return Collections.unmodifiableList(preMatches);
	}

	public List<PhonemeRepresentation> getPostMatches() {
		return Collections.unmodifiableList(postMatches);
	}

}
