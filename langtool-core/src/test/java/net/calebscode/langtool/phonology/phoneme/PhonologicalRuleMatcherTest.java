package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;

import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.rules.PhonologicalRule;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler;

public class PhonologicalRuleMatcherTest {

	private static final PhonologicalRuleCompiler compiler = new PhonologicalRuleCompiler(STANDARD_IPA_PHONEMES);

	private PhonologicalRule rule;

	@Test
	void applyBasicReplacementRules() {

	}

}
