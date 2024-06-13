package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.CLOSE_MID_BRACK_ROUNDED_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.OPEN_FRONT_UNROUNDED_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.VOICED_ALVEOLAR_LATERAL_APPROXIMATE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.VOICELESS_ALVEOLAR_PLOSIVE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.VOICELESS_VELAR_PLOSIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class SyllablePatternTest {

	private static final SyllablePatternCategoryMap categories = new SyllablePatternCategoryMap();
	private static final SyllablePatternCompiler compiler = new SyllablePatternCompiler(categories);

	@BeforeAll
	static void beforeAll() {
		categories.addCategoryPhonemes('C',
			VOICELESS_VELAR_PLOSIVE,   // k
			VOICELESS_ALVEOLAR_PLOSIVE // t
		);
		categories.addCategoryPhonemes('V',
			OPEN_FRONT_UNROUNDED_VOWEL,   // a
			CLOSE_MID_BRACK_ROUNDED_VOWEL // o
		);
		categories.addCategoryPhonemes('K',
			VOICED_ALVEOLAR_LATERAL_APPROXIMATE // l
		);
	}

	@Test
	void allPatterns() {
		testPattern("CV",       Set.of("CV"                      ));
		testPattern("(C)V",     Set.of("CV", "V"                 ));
		testPattern("C(C)V(C)", Set.of("CV", "CCV", "CVC", "CCVC"));
		testPattern("(C|K)V",   Set.of("CV", "KV"                ));
		testPattern("((C|K))V", Set.of("CV", "KV", "V"           ));
	}

	@Test
	void randomPattern() {
		var pattern = compiler.compile("CV");
		assertEquals("CV", pattern.randomPattern());

		pattern = compiler.compile("(C)V");
		assertTrue(Set.of("CV", "V").contains(pattern.randomPattern()));
	}

	@Test
	void allSyllables() {
		testSyllables("CV", Set.of("ka", "ko", "ta", "to"));

		testSyllables("(C)V(C)", Set.of(
				"a", "o",
				"ka", "ko",
				"ta", "to",
				"ak", "ok",
				"at", "ot",
				"kat", "kot",
				"tat", "tot",
				"kak", "kok",
				"tak", "tok"
		));

 		testSyllables("(C|K)V", Set.of("ka", "ta", "ko", "to", "la", "lo"));
 		testSyllables("((C|K))V", Set.of("ka", "ta", "ko", "to", "la", "lo", "a", "o"));
	}

	@Test
	void randomSyllable() {
		var pattern = compiler.compile("(C|K)VK");
		assertTrue(Set.of("kal", "tal", "kol", "tol", "lal", "lol").contains(pattern.randomSyllable().toString()));
	}

	@Test
	void randomSyllableThrowsWithInvalidCategory() {
		var pattern = compiler.compile("A");
		assertThrows(RuntimeException.class, pattern::randomSyllable);
	}

	private void testPattern(String source, Set<String> expectedPatterns) {
		var pattern = compiler.compile(source);
		assertEquals(expectedPatterns, pattern.allPatterns());
	}

	private void testSyllables(String source, Set<String> expectedSyllables) {
		var pattern = compiler.compile(source);
		assertEquals(expectedSyllables, pattern.allSyllables());
	}

}
