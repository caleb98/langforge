package net.calebscode.langforge.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhonemeRepresentationCollectionTest {

	private static final PhonemeStringMap firstMapper = new PhonemeStringMap();
	private static final PhonemeStringMap secondMapper = new PhonemeStringMap();

	private static final Phoneme phoneme1 = new Phoneme(Map.of("foo", "bar"));
	private static final Phoneme phoneme2 = new Phoneme(Map.of("zig", "zag"));
	private static final Phoneme phoneme3 = new Phoneme(Map.of("up", "down"));
	private static final Phoneme phoneme4 = new Phoneme(Map.of("sun", "moon"));
	private static final Phoneme phoneme5 = new Phoneme(Map.of("open", "close"));

	private PhonemeRepresentationCollection collection;

	@BeforeAll
	static void beforeAll() {
		firstMapper.addMapping("a", phoneme1);
		firstMapper.addMapping("b", phoneme2);
		firstMapper.addMapping("c", phoneme3);

		secondMapper.addMapping("b", phoneme4);
		secondMapper.addMapping("z", phoneme3);
	}

	@BeforeEach
	void beforeEach() {
		collection = new PhonemeRepresentationCollection();
	}

	@Test
	void canMap() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertTrue(collection.canMap(phoneme1));
		assertTrue(collection.canMap(phoneme3));
		assertTrue(collection.canMap(phoneme4));

		assertFalse(collection.canMap(phoneme2));
		assertFalse(collection.canMap(phoneme5));

		assertTrue(collection.canMap("a"));
		assertTrue(collection.canMap("b"));
		assertTrue(collection.canMap("z"));

		assertFalse(collection.canMap("c"));
		assertFalse(collection.canMap("d"));
	}

	@Test
	void getIpa() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertEquals("a", collection.getIpa(phoneme1));
		assertEquals("z", collection.getIpa(phoneme3));
		assertEquals("b", collection.getIpa(phoneme4));

		assertEquals(null, collection.getIpa(phoneme2));
		assertEquals(null, collection.getIpa(phoneme5));
	}

	@Test
	void getPhoneme() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		assertEquals(phoneme1, collection.getPhoneme("a"));
		assertEquals(phoneme3, collection.getPhoneme("z"));
		assertEquals(phoneme4, collection.getPhoneme("b"));

		assertEquals(null, collection.getPhoneme("c"));
		assertEquals(null, collection.getPhoneme("d"));
	}

	@Test
	void entrySet() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		var expected = Set.of(
				new PhonemeRepresentationMapper.Entry("a", new Phoneme(Map.of("foo", "bar"))),
				new PhonemeRepresentationMapper.Entry("b", new Phoneme(Map.of("sun", "moon"))),
				new PhonemeRepresentationMapper.Entry("z", new Phoneme(Map.of("up", "down"))));

		assertEquals(expected, collection.entrySet());
	}

	@Test
	void validateInvariant() {
		collection.addPhonemeMap(firstMapper);
		collection.addPhonemeMap(secondMapper);

		// Valid mappings
		assertEquals("a", collection.getIpa(collection.getPhoneme("a")));
		assertEquals("b", collection.getIpa(collection.getPhoneme("b")));
		assertEquals("z", collection.getIpa(collection.getPhoneme("z")));

		assertEquals(phoneme1, collection.getPhoneme(collection.getIpa(phoneme1)));
		assertEquals(phoneme3, collection.getPhoneme(collection.getIpa(phoneme3)));
		assertEquals(phoneme4, collection.getPhoneme(collection.getIpa(phoneme4)));

		// Invalid mappings
		assertEquals(null, collection.getIpa(collection.getPhoneme("c")));
		assertEquals(null, collection.getIpa(collection.getPhoneme("d")));

		assertEquals(null, collection.getPhoneme(collection.getIpa(phoneme2)));
		assertEquals(null, collection.getPhoneme(collection.getIpa(phoneme5)));
	}

}
