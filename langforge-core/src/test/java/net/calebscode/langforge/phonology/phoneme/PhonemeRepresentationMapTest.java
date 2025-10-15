package net.calebscode.langforge.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhonemeRepresentationMapTest {

	private final Phoneme testPhoneme1 = new Phoneme(Map.of("foo", "bar"));
	private final Phoneme testPhoneme2 = new Phoneme(Map.of("zig", "zag"));

	private PhonemeRepresentationMap phonemeMap;

	@BeforeEach
	void init() {
		phonemeMap = new PhonemeRepresentationMap();
	}

	@Test
	void add() {
		phonemeMap.addMapping("a", testPhoneme1);
		phonemeMap.addMapping("b", testPhoneme2);

		assertEquals("a", phonemeMap.getRepresentation(testPhoneme1).get());
		assertEquals("b", phonemeMap.getRepresentation(testPhoneme2).get());

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a").get());
		assertEquals(testPhoneme2, phonemeMap.getPhoneme("b").get());

		phonemeMap.addMapping("b", testPhoneme1);

		assertEquals("b", phonemeMap.getRepresentation(testPhoneme1).get());
		assertEquals(testPhoneme1, phonemeMap.getPhoneme("b").get());

		assertFalse(phonemeMap.canMap(testPhoneme2));
		assertFalse(phonemeMap.canMap("a"));

		assertTrue(phonemeMap.getRepresentation(testPhoneme2).isEmpty());
		assertTrue(phonemeMap.getPhoneme("a").isEmpty());
	}

	@Test
	void removeMapping() {
		phonemeMap.addMapping("a", testPhoneme1);
		phonemeMap.addMapping("b", testPhoneme2);

		assertEquals("a", phonemeMap.getRepresentation(testPhoneme1).get());
		assertEquals("b", phonemeMap.getRepresentation(testPhoneme2).get());

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a").get());
		assertEquals(testPhoneme2, phonemeMap.getPhoneme("b").get());

		phonemeMap.removeMapping(testPhoneme1);

		assertFalse(phonemeMap.canMap(testPhoneme1));
		assertFalse(phonemeMap.canMap("a"));

		assertTrue(phonemeMap.getRepresentation(testPhoneme1).isEmpty());
		assertTrue(phonemeMap.getPhoneme("a").isEmpty());

		phonemeMap.removeMapping("b");

		assertFalse(phonemeMap.canMap(testPhoneme2));
		assertFalse(phonemeMap.canMap("b"));

		assertTrue(phonemeMap.getRepresentation(testPhoneme2).isEmpty());
		assertTrue(phonemeMap.getPhoneme("b").isEmpty());
	}

	@Test
	void canMap() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertTrue(phonemeMap.canMap("a"));
		assertTrue(phonemeMap.canMap(testPhoneme1));
		assertTrue(phonemeMap.canMap(new Phoneme(Map.of("foo", "bar"))));

		assertFalse(phonemeMap.canMap("b"));
		assertFalse(phonemeMap.canMap(testPhoneme2));
		assertFalse(phonemeMap.canMap(new Phoneme(Map.of("zig", "zag"))));
	}

	@Test
	void getIpa() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals("a", phonemeMap.getRepresentation(testPhoneme1).get());
		assertEquals("a", phonemeMap.getRepresentation(new Phoneme(Map.of("foo", "bar"))).get());

		assertTrue(phonemeMap.getRepresentation(testPhoneme2).isEmpty());
		assertTrue(phonemeMap.getRepresentation(new Phoneme(Map.of("zig", "zag"))).isEmpty());
	}

	@Test
	void getPhoneme() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a").get());
		assertTrue(phonemeMap.getPhoneme("b").isEmpty());
	}

	@Test
	void validateInvariant() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals("a", phonemeMap.getRepresentation(phonemeMap.getPhoneme("a").get()).get());
		assertEquals(testPhoneme1, phonemeMap.getPhoneme(phonemeMap.getRepresentation(testPhoneme1).get()).get());
	}

}
