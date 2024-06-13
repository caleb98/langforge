package net.calebscode.langtool.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IpaPhonemeMapTest {

	private final Phoneme testPhoneme = new Phoneme("a", Map.of());

	private IpaPhonemeMap phonemeMap;

	@BeforeEach
	void init() {
		phonemeMap = new IpaPhonemeMap();
	}

	@Test
	void add() {
		phonemeMap.add(testPhoneme);

		assertTrue(phonemeMap.hasIpa("a"));
		assertTrue(phonemeMap.canMapTo(testPhoneme));
		assertEquals(testPhoneme, phonemeMap.getPhoneme("a"));
	}

	@Test
	void addThrowsIfPhonemePresent() {
		var sameIpaPhoneme = new Phoneme("a", Map.of("foo", new PhonemeFeature("foo", "bar")));
		phonemeMap.add(testPhoneme);

		assertThrows(IllegalArgumentException.class, () -> phonemeMap.add(sameIpaPhoneme));
		assertThrows(IllegalArgumentException.class, () -> phonemeMap.add(testPhoneme));
	}

	@Test
	void removeIpa() {
		phonemeMap.add(testPhoneme);

		assertTrue(phonemeMap.hasIpa("a"));

		phonemeMap.remove("a");

		assertFalse(phonemeMap.hasIpa("a"));
	}

	@Test
	void removePhoneme() {
		phonemeMap.add(testPhoneme);

		assertTrue(phonemeMap.hasIpa("a"));

		phonemeMap.remove(testPhoneme);

		assertFalse(phonemeMap.hasIpa("a"));
	}

	@Test
	void hasIpa() {
		assertFalse(phonemeMap.hasIpa("a"));

		phonemeMap.add(testPhoneme);

		assertTrue(phonemeMap.hasIpa("a"));
	}

	@Test
	void canMapTo() {
		assertFalse(phonemeMap.canMapTo(testPhoneme));

		phonemeMap.add(testPhoneme);

		assertTrue(phonemeMap.canMapTo(testPhoneme));
	}

	@Test
	void getPhoneme() {
		phonemeMap.add(testPhoneme);

		assertEquals(testPhoneme, phonemeMap.getPhoneme("a"));
	}

	@Test
	void getPhonemeMissing() {
		assertNull(phonemeMap.getPhoneme("a"));
	}


}
