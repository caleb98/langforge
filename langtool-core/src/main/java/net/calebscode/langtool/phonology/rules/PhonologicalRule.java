package net.calebscode.langtool.phonology.rules;

import java.util.Collections;
import java.util.List;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler.PhonemeRepresentation;

public class PhonologicalRule {

	private PhonemeRepresentation match;
	private Phoneme replacement;

	private List<PhonemeRepresentation> preMatches;
	private List<PhonemeRepresentation> postMatches;

	public PhonologicalRule(PhonemeRepresentation match, Phoneme replacement, List<PhonemeRepresentation> preMatches,
			List<PhonemeRepresentation> postMatches) {
		this.match = match;
		this.replacement = replacement;
		this.preMatches = Collections.unmodifiableList(preMatches);
		this.postMatches = Collections.unmodifiableList(postMatches);
	}

	public PhonemeRepresentation getMatch() {
		return match;
	}

	public Phoneme getReplacement() {
		return replacement;
	}

	public List<PhonemeRepresentation> getPreMatches() {
		return Collections.unmodifiableList(preMatches);
	}

	public List<PhonemeRepresentation> getPostMatches() {
		return Collections.unmodifiableList(postMatches);
	}

}
