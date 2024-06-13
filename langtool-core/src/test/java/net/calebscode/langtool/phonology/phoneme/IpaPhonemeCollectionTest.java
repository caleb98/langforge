package net.calebscode.langtool.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IpaPhonemeCollectionTest {

	private static final IpaPhonemeMap firstMapper = new IpaPhonemeMap();
	private static final IpaPhonemeMap secondMapper = new IpaPhonemeMap();

	private static final Phoneme phonemeOne = new Phoneme("a", Map.of("foo", new PhonemeFeature("foo", "bar")));
	private static final Phoneme phonemeTwo = new Phoneme("b", Map.of("zig", new PhonemeFeature("zig", "zag")));
	private static final Phoneme phonemeThree = new Phoneme("a", Map.of("up", new PhonemeFeature("up", "down")));

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

}
