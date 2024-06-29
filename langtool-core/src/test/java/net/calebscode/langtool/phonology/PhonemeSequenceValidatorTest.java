package net.calebscode.langtool.phonology;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class PhonemeSequenceValidatorTest {

	private SyllablePatternCategoryMap categories;

	@BeforeEach
	void beforeEach() {
		categories = new SyllablePatternCategoryMap();
	}

	@Test
	void revalidate() throws PhonemeSequenceValidationException {
		categories.addGeneratablePhoneme('C', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('C', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('G', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)V(C)");
		var validator = new PhonemeSequenceValidator(pattern);
		var input = ipaSequence("kjojkokojjokjo");

		var validated = validator.revalidate(input);

		assertEquals(".kjoj.ko.ko.jjo.kjo.", validated.render(STANDARD_IPA_PHONEMES));
	}

	@Test
	void revalidateInvalid() {
		categories.addGeneratablePhoneme('C', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('C', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('G', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)VC");
		var validator = new PhonemeSequenceValidator(pattern);
		var input = ipaSequence("kjojkokojjokjo");

		assertThrows(PhonemeSequenceValidationException.class, () -> validator.revalidate(input));
	}

	@Test
	void revalidateInvalid2() {
		categories.addGeneratablePhoneme('C', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('C', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('G', ipaPhoneme("j"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("CGVC");
		var validator = new PhonemeSequenceValidator(pattern);
		var input = ipaSequence("kkjok");

		assertThrows(PhonemeSequenceValidationException.class, () -> validator.revalidate(input));
	}

	@Test
	void revalidateMaximalOnset() throws PhonemeSequenceValidationException {
		categories.addGeneratablePhoneme('C', ipaPhoneme("b"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(C)(C)(C)(C)VC");
		var validator = new PhonemeSequenceValidator(pattern);
		var input = ipaSequence("obbobbbobbbbobbbbbob");

		var validated = validator.revalidate(input);

		assertEquals(".ob.bob.bbob.bbbob.bbbbob.", validated .render(STANDARD_IPA_PHONEMES));
	}

	@Test
	void revalidateMaximalOnset2() throws PhonemeSequenceValidationException {
		categories.addGeneratablePhoneme('X', ipaPhoneme("t"));
		categories.addGeneratablePhoneme('Y', ipaPhoneme("k"));
		categories.addGeneratablePhoneme('Z', ipaPhoneme("p"));
		categories.addGeneratablePhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(((X)Y)Z)V((X|Y|Z))((X|Y|Z))((X|Y|Z))");
		var validator = new PhonemeSequenceValidator(pattern);
		var input = ipaSequence("optkpokpko");

		var validated = validator.revalidate(input);
		assertEquals(".op.tkpokpk.o.", validated.render(STANDARD_IPA_PHONEMES));
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
