package net.calebscode.langtool.phonology;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class PhonemeSequenceValidatorTest {

	private PhonemeSequenceValidator validator;
	private SyllablePatternCategoryMap categories;

	@BeforeEach
	void beforeEach() {
		validator = new PhonemeSequenceValidator();
		categories = new SyllablePatternCategoryMap();
	}

	@Test
	void revalidate() {
		categories.addGeneratablePhoneme('C', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('C', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('G', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)V(C)");
		var input = ipaSequence("kjojkokojjokjo");

		var validated = validator.revalidate(input, pattern);
		assertTrue(validated.isPresent());

		var sequence = validated.get();
		assertEquals(".kjoj.ko.ko.jjo.kjo.", sequence.render(STANDARD_IPA_PHONEMES));
	}

	@Test
	void revalidateInvalid() {
		categories.addGeneratablePhoneme('C', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('C', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('G', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)VC");
		var input = ipaSequence("kjojkokojjokjo");

		var validated = validator.revalidate(input, pattern);

		assertTrue(validated.isEmpty());
	}

	@Test
	void revalidateMaximalOnset() {
		categories.addGeneratablePhoneme('C', ipaPhoneme("b"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(C)(C)(C)(C)VC");
		var input = ipaSequence("obbobbbobbbbobbbbbob");

		var validated = validator.revalidate(input, pattern);
		assertTrue(validated.isPresent());

		var sequence = validated.get();
		assertEquals(".ob.bob.bbob.bbbob.bbbbob.", sequence.render(STANDARD_IPA_PHONEMES));
	}

	@Test
	void revalidateMaximalOnset2() {
		categories.addGeneratablePhoneme('X', ipaPhoneme("t"));
		categories.addGeneratablePhoneme('Y', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('Z', ipaPhoneme("p"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(((X)Y)Z)V((X|Y|Z))((X|Y|Z))((X|Y|Z))");
		var input = ipaSequence("optkpokpko");

		var validated = validator.revalidate(input, pattern);
		assertTrue(validated.isPresent());

		var sequence = validated.get();
		assertEquals(".op.tkpokpk.o.", sequence.render(STANDARD_IPA_PHONEMES));
	}

	private static Phoneme ipaPhoneme(String ipaString) {
		return new PhonemeSequenceBuilder()
				.append(ipaString, STANDARD_IPA_PHONEMES)
				.build()
				.phonemeAt(0);
	}

	private static PhonemeSequence ipaSequence(String ipaString) {
		return new PhonemeSequenceBuilder()
				.append(ipaString, STANDARD_IPA_PHONEMES)
				.build();
	}

}
