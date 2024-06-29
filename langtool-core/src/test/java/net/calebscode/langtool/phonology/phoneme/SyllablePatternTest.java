package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.syllable.Syllable;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class SyllablePatternTest {

	private static final SyllablePatternCategoryMap categories = new SyllablePatternCategoryMap();
	private static final SyllablePatternCompiler compiler = new SyllablePatternCompiler(categories);

	private static Syllable syl_a;
	private static Syllable syl_o;
	private static Syllable syl_ka;
	private static Syllable syl_ko;
	private static Syllable syl_ta;
	private static Syllable syl_to;
	private static Syllable syl_la;
	private static Syllable syl_lo;
	private static Syllable syl_ak;
	private static Syllable syl_ok;
	private static Syllable syl_at;
	private static Syllable syl_ot;
	private static Syllable syl_kak;
	private static Syllable syl_kok;
	private static Syllable syl_kat;
	private static Syllable syl_kot;
	private static Syllable syl_tat;
	private static Syllable syl_tot;
	private static Syllable syl_tak;
	private static Syllable syl_tok;
	private static Syllable syl_kal;
	private static Syllable syl_tal;
	private static Syllable syl_kol;
	private static Syllable syl_tol;
	private static Syllable syl_lal;
	private static Syllable syl_lol;

	@BeforeAll
	static void beforeAll() {
		var k = VOICELESS_VELAR_PLOSIVE;
		var t = VOICELESS_ALVEOLAR_PLOSIVE;

		var a = OPEN_FRONT_UNROUNDED_VOWEL;
		var o = VOICED_ALVEOLAR_LATERAL_APPROXIMATE;

		var l = VOICED_ALVEOLAR_LATERAL_APPROXIMATE;

		categories.addGeneratablePhoneme('C', k);
		categories.addGeneratablePhoneme('C', t);

		categories.addGeneratablePhoneme('V', a);
		categories.addGeneratablePhoneme('V', o);

		categories.addGeneratablePhoneme('K', l);

		syl_a = new Syllable(a);
		syl_o = new Syllable(o);
		syl_ka = new Syllable(k,a);
		syl_ko = new Syllable(k,o);
		syl_ta = new Syllable(t,a);
		syl_to = new Syllable(t,o);
		syl_la = new Syllable(l,a);
		syl_lo = new Syllable(l,o);
		syl_ak = new Syllable(a,k);
		syl_ok = new Syllable(o,k);
		syl_at = new Syllable(a,t);
		syl_ot = new Syllable(o,t);
		syl_kak = new Syllable(k,a,k);
		syl_kok = new Syllable(k,o,k);
		syl_kat = new Syllable(k,a,t);
		syl_kot = new Syllable(k,o,t);
		syl_tat = new Syllable(t,a,t);
		syl_tot = new Syllable(t,o,t);
		syl_tak = new Syllable(t,a,k);
		syl_tok = new Syllable(t,o,k);
		syl_kal = new Syllable(k,a,l);
		syl_tal = new Syllable(t,a,l);
		syl_kol = new Syllable(k,o,l);
		syl_tol = new Syllable(t,o,l);
		syl_lal = new Syllable(l,a,l);
		syl_lol = new Syllable(l,o,l);
	}

	@Test
	void allPatterns() {
		testPattern("CV",       Set.of("CV"                      ));
		testPattern("(C)V",     Set.of("CV", "V"                 ));
		testPattern("C(C)V(C)", Set.of("CV", "CCV", "CVC", "CCVC"));
		testPattern("(C|K)V",   Set.of("CV", "KV"                ));
		testPattern("((C|K))V", Set.of("CV", "KV", "V"           ));

		testPattern("((X)Y)" , Set.of("", "Y", "XY"));
		testPattern("(((X)Y)Z)", Set.of("", "Z", "YZ", "XYZ"));
		testPattern("(((X)Y)Z)A", Set.of("A", "ZA", "YZA", "XYZA"));

		testPattern("(X|Y)", Set.of("X", "Y"));
		testPattern("((X|Y))", Set.of("X", "Y", ""));
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
		testSyllables("CV", Set.of(syl_ka, syl_ko, syl_ta, syl_to));

		testSyllables("(C)V(C)", Set.of(
				syl_a, syl_o,
				syl_ka, syl_ko,
				syl_ta, syl_to,
				syl_ak, syl_ok,
				syl_at, syl_ot,
				syl_kat, syl_kot,
				syl_tat, syl_tot,
				syl_kak, syl_kok,
				syl_tak, syl_tok
		));

 		testSyllables("(C|K)V", Set.of(syl_ka, syl_ta, syl_ko, syl_to, syl_la, syl_lo));
 		testSyllables("((C|K))V", Set.of(syl_ka, syl_ta, syl_ko, syl_to, syl_la, syl_lo, syl_a, syl_o));
	}

	@Test
	void randomSyllable() {
		var pattern = compiler.compile("(C|K)VK");
		assertTrue(Set.of(syl_kal, syl_tal, syl_kol, syl_tol, syl_lal, syl_lol).contains(pattern.randomSyllable()));
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

	private void testSyllables(String source, Set<Syllable> expectedSyllables) {
		var pattern = compiler.compile(source);
		assertEquals(expectedSyllables, pattern.allSyllables());
	}

}
