package net.calebscode.langforge.phonology.phoneme.string;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_REPRESENTATION_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMappingException;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceRenderer;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeString;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeStringBuilder;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeStringValidationException;
import net.calebscode.langforge.phonology.phoneme.string.SyllablePatternValidator;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllableUtils;

public class SyllablePatternValidatorTest {

	private static final PhonemeSequenceRenderer RENDERER = new PhonemeSequenceRenderer(IPA_PHONEME_REPRESENTATION_MAPPER);

	private SyllablePatternCategoryMap categories;

	@BeforeEach
	void beforeEach() {
		categories = new SyllablePatternCategoryMap();
	}

	@Test
	void revalidate() throws PhonemeStringValidationException, PhonemeRepresentationMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var patterns = SyllableUtils.expandSyllablePatterns("C(G)V(C)");
		var validator = new SyllablePatternValidator(categories, patterns);
		var input = ipaSequence("kjojkokojjokjo");

		var validated = validator.validate(input);

		assertEquals(".kjoj.ko.ko.jjo.kjo.", RENDERER.renderWithContext(validated));
	}

	@Test
	void revalidateInvalid() throws PhonemeRepresentationMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var patterns = SyllableUtils.expandSyllablePatterns("C(G)VC");
		var validator = new SyllablePatternValidator(categories, patterns);
		var input = ipaSequence("kjojkokojjokjo");

		assertThrows(PhonemeStringValidationException.class, () -> validator.validate(input));
	}

	@Test
	void revalidateInvalid2() throws PhonemeRepresentationMappingException {
		categories.addPhoneme('C', ipaPhoneme("k"));
		categories.addPhoneme('C', ipaPhoneme("j"));
		categories.addPhoneme('G', ipaPhoneme("j"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var patterns = SyllableUtils.expandSyllablePatterns("CGVC");
		var validator = new SyllablePatternValidator(categories, patterns);
		var input = ipaSequence("kkjok");

		assertThrows(PhonemeStringValidationException.class, () -> validator.validate(input));
	}

	@Test
	void revalidateMaximalOnset() throws PhonemeStringValidationException, PhonemeRepresentationMappingException {
		categories.addPhoneme('C', ipaPhoneme("b"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var patterns = SyllableUtils.expandSyllablePatterns("(C)(C)(C)(C)VC");
		var validator = new SyllablePatternValidator(categories, patterns);
		var input = ipaSequence("obbobbbobbbbobbbbbob");

		var validated = validator.validate(input);

		assertEquals(".ob.bob.bbob.bbbob.bbbbob.", RENDERER.renderWithContext(validated));
	}

	@Test
	void revalidateMaximalOnset2() throws PhonemeStringValidationException, PhonemeRepresentationMappingException {
		categories.addPhoneme('X', ipaPhoneme("t"));
		categories.addPhoneme('Y', ipaPhoneme("k"));
		categories.addPhoneme('Z', ipaPhoneme("p"));
		categories.addPhoneme('V', ipaPhoneme("o"));
		var patterns = SyllableUtils.expandSyllablePatterns("(((X)Y)Z)V((X|Y|Z))((X|Y|Z))((X|Y|Z))");
		var validator = new SyllablePatternValidator(categories, patterns);
		var input = ipaSequence("optkpokpko");

		var validated = validator.validate(input);
		assertEquals(".op.tkpokpk.o.", RENDERER.renderWithContext(validated));
	}

	private static Phoneme ipaPhoneme(String ipaString) throws PhonemeRepresentationMappingException {
		return new PhonemeStringBuilder()
				.append(ipaString, IPA_PHONEME_REPRESENTATION_MAPPER)
				.build()
				.phonemeAt(0);
	}

	private static PhonemeString ipaSequence(String ipaString) throws PhonemeRepresentationMappingException {
		return new PhonemeStringBuilder()
				.append(ipaString, IPA_PHONEME_REPRESENTATION_MAPPER)
				.build();
	}

}
