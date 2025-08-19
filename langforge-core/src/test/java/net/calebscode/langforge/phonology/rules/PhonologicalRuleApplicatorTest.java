package net.calebscode.langforge.phonology.rules;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_REPRESENTATION_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.PhonologicalRuleApplicationException;
import net.calebscode.langforge.phonology.SyllablePatternPhonemeSequenceValidator;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMappingException;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceRenderer;
import net.calebscode.langforge.phonology.phoneme.PhonemeString;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllableUtils;

public class PhonologicalRuleApplicatorTest {

	private static final PhonemeSequenceRenderer RENDERER = new PhonemeSequenceRenderer(IPA_PHONEME_REPRESENTATION_MAPPER);

	private static SyllablePatternCategoryMap categoryMap = new SyllablePatternCategoryMap();;

	@BeforeAll
	static void beforeAll() throws PhonemeRepresentationMappingException {
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
	void applySubstitutionRule() throws PhonemeRepresentationMappingException {
		testRuleApplications(
			"CV",
			"/a/ -> [i] / [j] _",
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
	void applyAssimilationRule() throws PhonemeRepresentationMappingException {
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
	void testDeletionRule() throws PhonemeRepresentationMappingException {
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
	void testInsertionRule() throws PhonemeRepresentationMappingException {
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

	@Test
	void testWordBoundaryBeginningOfWord() throws PhonemeRepresentationMappingException {
		testRuleApplications(
			"(C)V",
			"~ -> [a] / # _ [+consonant]",
			true,
			Map.of(
				"#ka", ".a.ka.",
				"#go", ".a.go.",
				"#igo", ".i.go."
			)
		);
	}

	@Test
	void testWordBoundaryEndOfWord() throws PhonemeRepresentationMappingException {
		testRuleApplications(
			"CV(C)",
			"~ -> [s] / [+vowel] _ #",
			true,
			Map.of(
				"ka#", ".kas.",
				"go#", ".gos.",
				"gok#", ".gok."
			)
		);
	}

	@Test
	void testSyllableBoundaryBeginningOfSyllable() throws PhonemeRepresentationMappingException {
		testRuleApplications(
			"(C)V(C)",
			"[+consonant, -voiced] -> [+voicing:voiced] / . _",
			true,
			Map.of(
				".kag.", ".gag.",
				".gok.", ".gok.",
				".i.ka.", ".i.ga."
			)
		);
	}

	@Test
	void testSyllableBoundaryEndOfSyllable() throws PhonemeRepresentationMappingException {
		testRuleApplications(
			"CV(G)",
			"~ -> [j] / [+front, +vowel] _ .",
			true,
			Map.of(
				".ka.ki.ko.", ".kaj.kij.ko.",
				".kak.kik.kok.", ".kak.kik.kok."
			)
		);
	}

	private void testRuleApplications(String patternSource, String ruleSource, boolean lenient, Map<String, String> expectations) throws PhonemeRepresentationMappingException {
		try {
			var patterns = SyllableUtils.expandSyllablePatterns(patternSource);
			var validator = new SyllablePatternPhonemeSequenceValidator(categoryMap, patterns);
			var rule = compileRule(ruleSource);
			var applicator = new PhonologicalRuleApplicator(rule);

			for (var entry : expectations.entrySet()) {
				var inputIpa = entry.getKey();
				var expectIpa = entry.getValue();

				var test = ipaSequence(inputIpa);
				var result = applicator.apply(test, validator, lenient);

				assertEquals(expectIpa, RENDERER.renderWithContext(result));
			}
		} catch (PhonologicalRuleApplicationException ex) {
			fail(ex);
		}
	}

	private PhonologicalRule compileRule(String rule) {
		return new PhonologicalRuleCompiler(IPA_PHONEME_REPRESENTATION_MAPPER).compile(rule);
	}

	private static Phoneme ipaPhoneme(String ipaString) throws PhonemeRepresentationMappingException {
		return new PhonemeSequenceBuilder()
				.append(ipaString, IPA_PHONEME_REPRESENTATION_MAPPER)
				.build()
				.phonemeAt(0);
	}

	private static PhonemeString ipaSequence(String ipaString) throws PhonemeRepresentationMappingException {
		return new PhonemeSequenceBuilder()
				.append(ipaString, IPA_PHONEME_REPRESENTATION_MAPPER)
				.build();
	}

}
