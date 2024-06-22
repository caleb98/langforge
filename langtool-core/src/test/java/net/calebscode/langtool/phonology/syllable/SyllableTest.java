package net.calebscode.langtool.phonology.syllable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.phoneme.Phoneme;

public class SyllableTest {

	private final Phoneme phonemeA = new Phoneme(Map.of("foo", "bar"));
	private final Phoneme phonemeB = new Phoneme(Map.of("zig", "zag"));
	private final Phoneme phonemeC = new Phoneme(Map.of("sun", "moon"));

	private Syllable testSyllable;

	@Test
	void constructorList() {
		testSyllable = new Syllable(List.of(phonemeA, phonemeB, phonemeC));

		var expectedValues = Stream.of(phonemeA, phonemeB, phonemeC).toList();
		var actualValues = testSyllable.phonemes();
		assertEquals(expectedValues, actualValues);
	}

	@Test
	void constructorVarargs() {
		testSyllable = new Syllable(phonemeA, phonemeB, phonemeC);

		var expectedValues = Stream.of(phonemeA, phonemeB, phonemeC).toList();
		var actualValues = testSyllable.phonemes();
		assertEquals(expectedValues, actualValues);
	}

}
