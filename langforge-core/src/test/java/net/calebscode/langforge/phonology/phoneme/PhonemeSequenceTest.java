package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.phoneme.PhonemeSequence.PhonemeMetadata;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence.PhonemeTransition;

public class PhonemeSequenceTest {

	private static final Phoneme NULL_PHONEME = new Phoneme(Map.of());
	private static final Phoneme PHONEME_A = new Phoneme(Map.of("foo", "bar"));
	private static final Phoneme PHONEME_B = new Phoneme(Map.of("zig", "zag"));
	private static final Phoneme PHONEME_C = new Phoneme(Map.of("hip", "hop"));

	private static final PhonemeMetadata DEFAULT_METADATA = new PhonemeMetadata(false, false, false, false);

	@Test
	void constructDefault() {
		var sequence = new PhonemeSequence();

		var phonemes = sequence.getPhonemes();
		var metadata = sequence.getMetadata();

		assertEquals(0, sequence.length());

		assertTrue(phonemes.isEmpty());
		assertTrue(metadata.isEmpty());

		assertThrows(UnsupportedOperationException.class, () -> phonemes.add(new Phoneme(Map.of())));
		assertThrows(UnsupportedOperationException.class, () -> metadata.add(new PhonemeMetadata(false, false, false, false)));
	}

	@Test
	void constructWithLists() {
		var initPhonemes = new ArrayList<Phoneme>();
		var initMetadata = new ArrayList<PhonemeMetadata>();
		initPhonemes.add(NULL_PHONEME);
		initMetadata.add(DEFAULT_METADATA);

		var sequence = new PhonemeSequence(initPhonemes, initMetadata);

		var phonemes = sequence.getPhonemes();
		var metadata = sequence.getMetadata();

		assertEquals(1, sequence.length());
		assertEquals(1, phonemes.size());
		assertEquals(1, metadata.size());

		assertEquals(NULL_PHONEME, phonemes.get(0));
		assertEquals(DEFAULT_METADATA, metadata.get(0));

		assertThrows(UnsupportedOperationException.class, () -> phonemes.add(NULL_PHONEME));
		assertThrows(UnsupportedOperationException.class, () -> metadata.add(DEFAULT_METADATA));
	}

	@Test
	void constructWithListsDifferentSize() {
		assertThrows(IllegalArgumentException.class, () -> new PhonemeSequence(List.of(NULL_PHONEME), List.of()));
	}

	@Test
	void replaceAt() {
		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));
		var expected = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_A, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));

		var replaced = sequence.replaceAt(1, PHONEME_A);

		assertEquals(expected, replaced);
	}

	@Test
	void replaceAtWithMetadata() {
		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_METADATA, DEFAULT_METADATA, DEFAULT_METADATA));
		var expected = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_A, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));

		var replaced = sequence.replaceAt(1, PHONEME_A, new PhonemeMetadata(true, true, true, true));

		assertEquals(expected, replaced);
	}

	@Test
	void append() {
		var first = new PhonemeSequence(List.of(PHONEME_A), List.of(DEFAULT_METADATA));
		var second = new PhonemeSequence(List.of(PHONEME_B), List.of(DEFAULT_METADATA));
		var expected = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B),
				List.of(DEFAULT_METADATA, DEFAULT_METADATA));

		var concat = first.append(second);

		assertEquals(expected, concat);
	}

	@Test
	void phonemeAt() {
		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));

		assertEquals(PHONEME_A, sequence.phonemeAt(0));
		assertEquals(PHONEME_B, sequence.phonemeAt(1));
		assertEquals(PHONEME_C, sequence.phonemeAt(2));
	}

	@Test
	void metadataAt() {
		var meta1 = new PhonemeMetadata(true, false, false, false);
		var meta2 = new PhonemeMetadata(false, true, false, false);
		var meta3 = new PhonemeMetadata(false, false, true, false);

		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(meta1, meta2, meta3));

		assertEquals(meta1, sequence.metadataAt(0));
		assertEquals(meta2, sequence.metadataAt(1));
		assertEquals(meta3, sequence.metadataAt(2));
	}

	@Test
	void appendEmpty() {
		var first = new PhonemeSequence(List.of(PHONEME_A), List.of(DEFAULT_METADATA));
		var second = new PhonemeSequence();

		var concat = first.append(second);

		assertEquals(first, concat);
	}

	@Test
	void subsequenceBegin() {
		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));
		var expected = new PhonemeSequence(
				List.of(PHONEME_B, PHONEME_C),
				List.of(new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));

		var subsequence = sequence.subsequence(1);

		assertEquals(expected, subsequence);
	}

	@Test
	void subsequenceBeginAndEnd() {
		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_METADATA, new PhonemeMetadata(true, true, true, true), DEFAULT_METADATA));
		var expected = new PhonemeSequence(
				List.of(PHONEME_B),
				List.of(new PhonemeMetadata(true, true, true, true)));

		var subsequence = sequence.subsequence(1, 2);

		assertEquals(expected, subsequence);
	}

	@Test
	void getTransition() {
		var meta1 = new PhonemeMetadata(true, false, true, false);
		var meta2 = new PhonemeMetadata(false, false, false, false);
		var meta3 = new PhonemeMetadata(false, true, false, true);

		var sequence = new PhonemeSequence(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(meta1, meta2, meta3));

		assertEquals(new PhonemeTransition(true, true, true), sequence.getTransition(-1));
		assertEquals(new PhonemeTransition(false, false, false), sequence.getTransition(0));
		assertEquals(new PhonemeTransition(false, false, false), sequence.getTransition(1));
		assertEquals(new PhonemeTransition(true, true, true), sequence.getTransition(2));
	}

	@Test
	void getTransitionEmptySequence() {
		var sequence = new PhonemeSequence();
		var expected = new PhonemeTransition(false, false, false);

		assertEquals(expected, sequence.getTransition(-1));
		assertEquals(expected, sequence.getTransition(0));
		assertEquals(expected, sequence.getTransition(1));
	}

	@Test
	void render() {
		var sequence = new PhonemeSequence(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeMetadata(true, false, true, false),
					new PhonemeMetadata(false, true, false, false),
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, true)
				));
		var expected = "#ta.ta#";

		var rendered = sequence.render(IPA_PHONEME_STRING_MAP);

		assertEquals(expected, rendered);
	}

	@Test
	void renderSyllableOnly() {
		var sequence = new PhonemeSequence(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, false),
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, false)
				));
		var expected = ".ta.ta.";

		var rendered = sequence.render(IPA_PHONEME_STRING_MAP);

		assertEquals(expected, rendered);
	}

	@Test
	void renderSequenceOnly() {
		var sequence = new PhonemeSequence(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(DEFAULT_METADATA, DEFAULT_METADATA, DEFAULT_METADATA, DEFAULT_METADATA));
		var expected = "tata";

		var rendered = sequence.render(IPA_PHONEME_STRING_MAP);

		assertEquals(expected, rendered);
	}

	@Test
	void renderEmptySequence() {
		var sequence = new PhonemeSequence();
		var expected = "";

		var rendered = sequence.render(IPA_PHONEME_STRING_MAP);

		assertEquals(expected, rendered);
	}

}
