package net.calebscode.langforge.phonology.rules;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.PhonologicalRuleApplicationException;
import net.calebscode.langforge.phonology.SyllablePatternPhonemeSequenceValidator;
import net.calebscode.langforge.phonology.phoneme.IpaMappingException;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langforge.phonology.syllable.SyllablePattern;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCompiler;

public class PhonologicalRuleApplicatorTest {

	private static SyllablePatternCategoryMap categoryMap = new SyllablePatternCategoryMap();

	@BeforeAll
	static void beforeAll() throws IpaMappingException {
		categoryMap.addPhoneme('C', ipaPhoneme("t"));
		categoryMap.addPhoneme('C', ipaPhoneme("d"));
		categoryMap.addPhoneme('C', ipaPhoneme("s"));
		categoryMap.addPhoneme('C', ipaPhoneme("k"));
		categoryMap.addPhoneme('C', ipaPhoneme("g"));
		categoryMap.addPhoneme('C', ipaPhoneme("j"));

		categoryMap.addPhoneme('G', ipaPhoneme("j"));

		categoryMap.addPhoneme('V', ipaPhoneme("a"));
		categoryMap.addPhoneme('V', ipaPhoneme("o"));
		categoryMap.addPhoneme('V', ipaPhoneme("i"));
	}

	@Test
	void applySubstitutionRule() throws IpaMappingException {
		testRuleApplications(
			"CV",
			"/a/ -> [i] / /j/ _",
			false,
			Map.of(
				"ja", ".ji.",
				"taja", ".ta.ji.",
				"jata", ".ji.ta.",
				"tasa", ".ta.sa."
			)
		);
	}

	@Test
	void applyAssimilationRule() throws IpaMappingException {
		testRuleApplications(
			"CV(C)",
			"[+consonant] -> [0_place] / [+consonant, 0_place] _",
			false,
			Map.of(
				"tasta", ".tas.ta.",
				"takta", ".tak.ka.",
				"katka", ".kat.ta.",
				"katga", ".kat.da."
			)
		);
	}

	@Test
	void testDeletionRule() throws IpaMappingException {
		testRuleApplications(
			"CV(C)",
			"[+consonant, 0_place, 1_type] -> ~ / [+consonant, 0_place, 1_type] _",
			false,
			Map.of(
				"katta", ".ka.ta.",
				"kakga", ".ka.ka.",
				"katsa", ".kat.sa."
			)
		);
	}

	@Test
	void testInsertionRule() throws IpaMappingException {
		testRuleApplications(
			"CV",
			"~ -> [a] / [+consonant] _ [+consonant]",
			true,
			Map.of(
				"katta", ".ka.ta.ta.",
				"katttta", ".ka.ta.ta.ta.ta.",
				"kakta", ".ka.ka.ta.",
				"kastja", ".ka.sa.ta.ja.",
				"goggokka", ".go.ga.go.ka.ka."
			)
		);
	}

	private void testRuleApplications(String patternSource, String ruleSource, boolean lenient, Map<String, String> expectations) throws IpaMappingException {
		try {
			var pattern = compilePattern(patternSource);
			var validator = new SyllablePatternPhonemeSequenceValidator(pattern);
			var rule = compileRule(ruleSource);
			var applicator = new PhonologicalRuleApplicator(rule);

			for (var entry : expectations.entrySet()) {
				var inputIpa = entry.getKey();
				var expectIpa = entry.getValue();

				var test = ipaSequence(inputIpa);
				var result = applicator.apply(test, validator, lenient);

				assertEquals(expectIpa, result.render(IPA_MAPPER));
			}
		} catch (PhonologicalRuleApplicationException ex) {
			fail(ex);
		}
	}

	private PhonologicalRule compileRule(String rule) {
		return new PhonologicalRuleCompiler(IPA_MAPPER).compile(rule);
	}

	private static SyllablePattern compilePattern(String pattern) {
		return new SyllablePatternCompiler(categoryMap).compile(pattern);
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
