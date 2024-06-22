package net.calebscode.langtool.phonology.syllable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import net.calebscode.langtool.phonology.phoneme.Phoneme;

public class SyllableTest {

	private final Phoneme phonemeA = new Phoneme(Map.of("foo", "bar"));
	private final Phoneme phonemeB = new Phoneme(Map.of("zig", "zag"));
	private final Phoneme phonemeC = new Phoneme(Map.of("sun", "moon"));

	private final Phoneme[] testOnset   = new Phoneme[] {phonemeA};
	private final Phoneme[] testNucleus = new Phoneme[] {phonemeB};
	private final Phoneme[] testCoda    = new Phoneme[] {phonemeC};

	private Syllable testSyllable;

	@Test
	void phonemesStream() {
		testSyllable = new Syllable(testOnset, testNucleus, testCoda);

		var expectedValues = Stream.of(phonemeA, phonemeB, phonemeC).toList();
		var actualValues = testSyllable.phonemeStream().toList();
		assertEquals(expectedValues, actualValues);
	}

	@Test
	void phonemesStreamWithNullOnset() {
		testSyllable = new Syllable(null, testNucleus, testCoda);

		var expectedValues = Stream.of(phonemeB, phonemeC).toList();
		var actualValues = testSyllable.phonemeStream().toList();
		assertEquals(expectedValues, actualValues);
	}

	@Test
	void phonemesStreamWithNullNucleus() {
		testSyllable = new Syllable(testOnset, null, testCoda);

		var expectedValues = Stream.of(phonemeA, phonemeC).toList();
		var actualValues = testSyllable.phonemeStream().toList();
		assertEquals(expectedValues, actualValues);
	}

	@Test
	void phonemesStreamWithNullCoda() {
		testSyllable = new Syllable(testOnset, testNucleus, null);

		var expectedValues = Stream.of(phonemeA, phonemeB).toList();
		var actualValues = testSyllable.phonemeStream().toList();
		assertEquals(expectedValues, actualValues);
	}

	@Test
	void syllableToString() {
		testSyllable = new Syllable(testOnset, testNucleus, testCoda);

		assertEquals("abc", testSyllable.toString());
	}

	@Test
	void syllableToStringNullOnset() {
		testSyllable = new Syllable(null, testNucleus, testCoda);

		assertEquals("bc", testSyllable.toString());
	}

	@Test
	void syllableToStringNullNucleus() {
		testSyllable = new Syllable(testOnset, null, testCoda);

		assertEquals("ac", testSyllable.toString());
	}

	@Test
	void syllableToStringNullCoda() {
		testSyllable = new Syllable(testOnset, testNucleus, null);

		assertEquals("ab", testSyllable.toString());
	}

}
