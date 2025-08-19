package net.calebscode.langforge.phonology.syllable;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SyllableGeneratorTest {

	private static final SyllablePatternCategoryMap categories = new SyllablePatternCategoryMap();

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

	@BeforeAll
	static void beforeAll() {
		var k = VOICELESS_VELAR_PLOSIVE;
		var t = VOICELESS_ALVEOLAR_PLOSIVE;

		var a = OPEN_FRONT_UNROUNDED_VOWEL;
		var o = VOICED_ALVEOLAR_LATERAL_APPROXIMATE;

		var l = VOICED_ALVEOLAR_LATERAL_APPROXIMATE;

		categories.addPhoneme('C', k);
		categories.addPhoneme('C', t);

		categories.addPhoneme('V', a);
		categories.addPhoneme('V', o);

		categories.addPhoneme('K', l);

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
	}

	private record AllSyllablesTestInput(String pattern, Set<Syllable> expectedPossible) {}

	private static Stream<AllSyllablesTestInput> generateAllSyllablesInputProvider() {
		return Stream.of(
			new AllSyllablesTestInput("CV", Set.of(syl_ka, syl_ko, syl_ta, syl_to)),
			new AllSyllablesTestInput("(C)V(C)", Set.of(
					syl_a, syl_o,
					syl_ka, syl_ko,
					syl_ta, syl_to,
					syl_ak, syl_ok,
					syl_at, syl_ot,
					syl_kat, syl_kot,
					syl_tat, syl_tot,
					syl_kak, syl_kok,
					syl_tak, syl_tok
			)),
			new AllSyllablesTestInput("(C|K)V", Set.of(syl_ka, syl_ta, syl_ko, syl_to, syl_la, syl_lo)),
			new AllSyllablesTestInput("((C|K))V", Set.of(syl_ka, syl_ta, syl_ko, syl_to, syl_la, syl_lo, syl_a, syl_o))
		);
	}

	@ParameterizedTest
	@MethodSource("generateAllSyllablesInputProvider")
	void generateAllSyllables(AllSyllablesTestInput input) {
		var patterns = SyllableUtils.expandSyllablePatterns(input.pattern());
		var generator = new SyllableGenerator();
		for (var pattern : patterns) {
			generator.addPattern(pattern);
		}

		var syllables = generator.generateAllSyllables(categories);

		assertEquals(input.expectedPossible(), syllables);
	}

}
