package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllableUtils;

public class SyllableUtilsTest {

	private static final SyllablePatternCategoryMap categories = new SyllablePatternCategoryMap();

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
	}

	private record ExpandSyllablePatternsInput(String unexpanded, Set<String> expected) {}

	private static Stream<ExpandSyllablePatternsInput> expandSyllablePatternsInputProvider() {
		return Stream.of(
			new ExpandSyllablePatternsInput("CV",       Set.of("CV"                      )),
			new ExpandSyllablePatternsInput("(C)V",     Set.of("CV", "V"                 )),
			new ExpandSyllablePatternsInput("C(C)V(C)", Set.of("CV", "CCV", "CVC", "CCVC")),
			new ExpandSyllablePatternsInput("(C|K)V",   Set.of("CV", "KV"                )),
			new ExpandSyllablePatternsInput("((C|K))V", Set.of("CV", "KV", "V"           )),

			new ExpandSyllablePatternsInput("((X)Y)" , Set.of("", "Y", "XY")),
			new ExpandSyllablePatternsInput("(((X)Y)Z)", Set.of("", "Z", "YZ", "XYZ")),
			new ExpandSyllablePatternsInput("(((X)Y)Z)A", Set.of("A", "ZA", "YZA", "XYZA")),

			new ExpandSyllablePatternsInput("(X|Y)", Set.of("X", "Y")),
			new ExpandSyllablePatternsInput("((X|Y))", Set.of("X", "Y", ""))
		);
	}

	@ParameterizedTest
	@MethodSource("expandSyllablePatternsInputProvider")
	void allPatterns(ExpandSyllablePatternsInput input) {
		var patterns = SyllableUtils.expandSyllablePatterns(input.unexpanded());
		assertEquals(input.expected(), patterns);
	}

}
