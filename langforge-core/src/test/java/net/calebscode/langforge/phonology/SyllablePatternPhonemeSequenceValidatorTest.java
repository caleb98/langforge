package net.calebscode.langforge.phonology;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.phoneme.IpaMappingException;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCompiler;

public class SyllablePatternPhonemeSequenceValidatorTest {

	private SyllablePatternCategoryMap categories;

	@BeforeEach
	void beforeEach() {
		categories = new SyllablePatternCategoryMap();
	}

	@Test
	void revalidate() throws PhonemeSequenceValidationException, IpaMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)V(C)");
		var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
		var input = ipaSequence("kjojkokojjokjo");

		var validated = validator.validate(input);

		assertEquals(".kjoj.ko.ko.jjo.kjo.", validated.render(IPA_MAPPER));
	}

	@Test
	void revalidateInvalid() throws IpaMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("C(G)VC");
		var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
		var input = ipaSequence("kjojkokojjokjo");

		assertThrows(PhonemeSequenceValidationException.class, () -> validator.validate(input));
	}

	@Test
	void revalidateInvalid2() throws IpaMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("CGVC");
		var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
		var input = ipaSequence("kkjok");

		assertThrows(PhonemeSequenceValidationException.class, () -> validator.validate(input));
	}

	@Test
	void revalidateMaximalOnset() throws PhonemeSequenceValidationException, IpaMappingException {
		categories.addPhoneme('C', ipaPhoneme("b"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(C)(C)(C)(C)VC");
		var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
		var input = ipaSequence("obbobbbobbbbobbbbbob");

		var validated = validator.validate(input);

		assertEquals(".ob.bob.bbob.bbbob.bbbbob.", validated .render(IPA_MAPPER));
	}

	@Test
	void revalidateMaximalOnset2() throws PhonemeSequenceValidationException, IpaMappingException {
		categories.addPhoneme('X', ipaPhoneme("t"));
		categories.addPhoneme('Y', ipaPhoneme("k"));
		categories.addPhoneme('Z', ipaPhoneme("p"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var pattern = new SyllablePatternCompiler(categories).compile("(((X)Y)Z)V((X|Y|Z))((X|Y|Z))((X|Y|Z))");
		var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
		var input = ipaSequence("optkpokpko");

		var validated = validator.validate(input);
		assertEquals(".op.tkpokpk.o.", validated.render(IPA_MAPPER));
	}

	private static Phoneme ipaPhoneme(String ipaString) throws IpaMappingException {
		return new PhonemeSequenceBuilder()
				.append(ipaString, IPA_MAPPER)
				.build()
				.phonemeAt(0);
	}

	private static PhonemeSequence ipaSequence(String ipaString) throws IpaMappingException {
		return new PhonemeSequenceBuilder()
				.append(ipaString, IPA_MAPPER)
				.build();
	}

}
