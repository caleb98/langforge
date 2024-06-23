package net.calebscode.langtool.phonology;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;

import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class PhonemeSequenceValidatorTest {

	@Test
	void test() {
		var builder = new PhonemeSequenceBuilder();
		builder.append("kjojkokojjokjo", STANDARD_IPA_PHONEMES);
//		builder.append("kokjok", STANDARD_IPA_PHONEMES);
		var seq = builder.build();

		var categories = new SyllablePatternCategoryMap();
		categories.addGeneratablePhoneme('C', seq.phonemeAt(0));
		categories.addGeneratablePhoneme('C', seq.phonemeAt(1));
		categories.addGeneratablePhoneme('G', seq.phonemeAt(1));
		categories.addGeneratablePhoneme('V', seq.phonemeAt(2));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)V(C)");

		var validator = new PhonemeSequenceValidator();
		var validated = validator.revalidate(seq, pattern);

		validated.ifPresent(sequence -> System.out.println(sequence.render(STANDARD_IPA_PHONEMES)));
	}

}
