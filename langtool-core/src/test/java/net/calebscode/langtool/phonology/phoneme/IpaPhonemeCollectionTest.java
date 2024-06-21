package net.calebscode.langtool.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IpaPhonemeCollectionTest {

	private static final IpaPhonemeMap firstMapper = new IpaPhonemeMap();
	private static final IpaPhonemeMap secondMapper = new IpaPhonemeMap();

	private static final Phoneme phonemeOne = new Phoneme("a", Map.of("foo", "bar"));
	private static final Phoneme phonemeTwo = new Phoneme("b", Map.of("zig", "zag"));
	private static final Phoneme phonemeThree = new Phoneme("a", Map.of("up", "down"));

	private IpaPhonemeCollection collection;

	@BeforeAll
	static void beforeAll() {
		firstMapper.add(phonemeOne);

		secondMapper.add(phonemeTwo);
		secondMapper.add(phonemeThree);
	}

	@BeforeEach
	void beforeEach() {
		collection = new IpaPhonemeCollection();
	}

	@Test
	void hasIpa() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertTrue(collection.hasIpa("a"));
		assertTrue(collection.hasIpa("b"));
		assertFalse(collection.hasIpa("c"));
	}

	@Test
	void hasIpaNoMappers() {
		assertFalse(collection.hasIpa("a"));
	}

	@Test
	void canMapTo() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertTrue(collection.canMapTo(phonemeOne));
		assertTrue(collection.canMapTo(phonemeTwo));
		assertFalse(collection.canMapTo(phonemeThree));
	}

	@Test
	void getPhoneme() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertEquals(phonemeOne, collection.getPhoneme("a"));
		assertEquals(phonemeTwo, collection.getPhoneme("b"));
		assertNull(collection.getPhoneme("c"));
	}

	@Test
	void getIpa() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertEquals("a", collection.getIpa(Map.of("foo", "bar")));
		assertEquals("b", collection.getIpa(Map.of("zig", "zag")));
		assertEquals("a", collection.getIpa(Map.of("up", "down")));
		assertNull(collection.getIpa(Map.of()));
		assertNull(collection.getIpa(Map.of("foo", "")));
		assertNull(collection.getIpa(Map.of("", "bar")));
	}

	@Test
	void hasIpaForFeatures() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertTrue(collection.hasIpaForFeatures(Map.of("foo", "bar")));
		assertTrue(collection.hasIpaForFeatures(Map.of("zig", "zag")));
		assertTrue(collection.hasIpaForFeatures(Map.of("up", "down")));
	}

	@Test
	void hasIpaForFeaturesNoMappers() {
		assertFalse(collection.hasIpaForFeatures(Map.of("foo", "bar")));
		assertFalse(collection.hasIpaForFeatures(Map.of("zig", "zag")));
		assertFalse(collection.hasIpaForFeatures(Map.of("up", "down")));
	}

}
