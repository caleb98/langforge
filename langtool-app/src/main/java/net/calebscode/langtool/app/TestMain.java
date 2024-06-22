package net.calebscode.langtool.app;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;

import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleMatcher;

public class TestMain {
	public static void main(String[] args) throws Exception {

		var ruleCompiler = new PhonologicalRuleCompiler(STANDARD_IPA_PHONEMES);
		var rule = ruleCompiler.compile("[0_place, 1_voicing, 2_type, +consonant] -> ~ / [0_place, 1_voicing, 2_type, +consonant] _");
		var matcher = new PhonologicalRuleMatcher(rule);

		var seqBuilder = new PhonemeSequenceBuilder();
		seqBuilder.append("takkalogka", STANDARD_IPA_PHONEMES);
		var seq = seqBuilder.build();

		System.out.println(seq.render(STANDARD_IPA_PHONEMES));
		System.out.println(matcher.apply(seq).render(STANDARD_IPA_PHONEMES));

	}
}