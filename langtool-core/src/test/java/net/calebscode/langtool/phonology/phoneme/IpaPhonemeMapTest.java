package net.calebscode.langtool.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IpaPhonemeMapTest {

	private final Phoneme testPhoneme1 = new Phoneme(Map.of("foo", "bar"));
	private final Phoneme testPhoneme2 = new Phoneme(Map.of("zig", "zag"));

	private IpaPhonemeMap phonemeMap;

	@BeforeEach
	void init() {
		phonemeMap = new IpaPhonemeMap();
	}

	@Test
	void add() {
		phonemeMap.addMapping("a", testPhoneme1);
		phonemeMap.addMapping("b", testPhoneme2);

		assertEquals("a", phonemeMap.getIpa(testPhoneme1));
		assertEquals("b", phonemeMap.getIpa(testPhoneme2));

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a"));
		assertEquals(testPhoneme2, phonemeMap.getPhoneme("b"));

		phonemeMap.addMapping("b", testPhoneme1);

		assertEquals("b", phonemeMap.getIpa(testPhoneme1));
		assertEquals(testPhoneme1, phonemeMap.getPhoneme("b"));

		assertFalse(phonemeMap.canMap(testPhoneme2));
		assertFalse(phonemeMap.canMap("a"));

		assertNull(phonemeMap.getIpa(testPhoneme2));
		assertNull(phonemeMap.getPhoneme("a"));
	}

	@Test
	void removeMapping() {
		phonemeMap.addMapping("a", testPhoneme1);
		phonemeMap.addMapping("b", testPhoneme2);

		assertEquals("a", phonemeMap.getIpa(testPhoneme1));
		assertEquals("b", phonemeMap.getIpa(testPhoneme2));

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a"));
		assertEquals(testPhoneme2, phonemeMap.getPhoneme("b"));

		phonemeMap.removeMapping(testPhoneme1);

		assertFalse(phonemeMap.canMap(testPhoneme1));
		assertFalse(phonemeMap.canMap("a"));

		assertNull(phonemeMap.getIpa(testPhoneme1));
		assertNull(phonemeMap.getPhoneme("a"));

		phonemeMap.removeMapping("b");

		assertFalse(phonemeMap.canMap(testPhoneme2));
		assertFalse(phonemeMap.canMap("b"));

		assertNull(phonemeMap.getIpa(testPhoneme2));
		assertNull(phonemeMap.getPhoneme("b"));
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

		assertEquals("a", phonemeMap.getIpa(testPhoneme1));
		assertEquals("a", phonemeMap.getIpa(new Phoneme(Map.of("foo", "bar"))));

		assertNull(phonemeMap.getIpa(testPhoneme2));
		assertNull(phonemeMap.getIpa(new Phoneme(Map.of("zig", "zag"))));
	}

	@Test
	void getPhoneme() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals(testPhoneme1, phonemeMap.getPhoneme("a"));
		assertNull(phonemeMap.getPhoneme("b"));
	}

	@Test
	void entrySet() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals(Set.of(new IpaPhonemeMapper.Entry("a", testPhoneme1)), phonemeMap.entrySet());
		assertEquals(Set.of(new IpaPhonemeMapper.Entry("a", new Phoneme(Map.of("foo", "bar")))), phonemeMap.entrySet());
	}

	@Test
	void validateInvariant() {
		phonemeMap.addMapping("a", testPhoneme1);

		assertEquals("a", phonemeMap.getIpa(phonemeMap.getPhoneme("a")));
		assertEquals(testPhoneme1, phonemeMap.getPhoneme(phonemeMap.getIpa(testPhoneme1)));
	}

}
