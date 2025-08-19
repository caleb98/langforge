package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.phoneme.PhonemeString.PhonemeTransition;

public class PhonemeSequenceTest {

	private static final Phoneme NULL_PHONEME = new Phoneme(Map.of());
	private static final Phoneme PHONEME_A = new Phoneme(Map.of("foo", "bar"));
	private static final Phoneme PHONEME_B = new Phoneme(Map.of("zig", "zag"));
	private static final Phoneme PHONEME_C = new Phoneme(Map.of("hip", "hop"));

	private static final PhonemeSequenceRenderer RENDERER = new PhonemeSequenceRenderer(IPA_PHONEME_STRING_MAP);
	private static final PhonemeContext DEFAULT_CONTEXT = new PhonemeContext(false, false, false, false);

	@Test
	void constructDefault() {
		var sequence = new PhonemeString();

		var phonemes = sequence.getPhonemes();
		var contexts = sequence.getPhonemeContexts();

		assertEquals(0, sequence.length());

		assertTrue(phonemes.isEmpty());
		assertTrue(contexts.isEmpty());

		assertThrows(UnsupportedOperationException.class, () -> phonemes.add(new Phoneme(Map.of())));
		assertThrows(UnsupportedOperationException.class, () -> contexts.add(new PhonemeContext(false, false, false, false)));
	}

	@Test
	void constructWithLists() {
		var initPhonemes = new ArrayList<Phoneme>();
		var initContexts = new ArrayList<PhonemeContext>();
		initPhonemes.add(NULL_PHONEME);
		initContexts.add(DEFAULT_CONTEXT);

		var sequence = new PhonemeString(initPhonemes, initContexts);

		var phonemes = sequence.getPhonemes();
		var contexts = sequence.getPhonemeContexts();

		assertEquals(1, sequence.length());
		assertEquals(1, phonemes.size());
		assertEquals(1, contexts.size());

		assertEquals(NULL_PHONEME, phonemes.get(0));
		assertEquals(DEFAULT_CONTEXT, contexts.get(0));

		assertThrows(UnsupportedOperationException.class, () -> phonemes.add(NULL_PHONEME));
		assertThrows(UnsupportedOperationException.class, () -> contexts.add(DEFAULT_CONTEXT));
	}

	@Test
	void constructWithListsDifferentSize() {
		assertThrows(IllegalArgumentException.class, () -> new PhonemeString(List.of(NULL_PHONEME), List.of()));
	}

	@Test
	void replaceAt() {
		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));
		var expected = new PhonemeString(
				List.of(PHONEME_A, PHONEME_A, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));

		var replaced = sequence.replaceAt(1, PHONEME_A);

		assertEquals(expected, replaced);
	}

	@Test
	void replaceAtWithContext() {
		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_CONTEXT, DEFAULT_CONTEXT, DEFAULT_CONTEXT));
		var expected = new PhonemeString(
				List.of(PHONEME_A, PHONEME_A, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));

		var replaced = sequence.replaceAt(1, PHONEME_A, new PhonemeContext(true, true, true, true));

		assertEquals(expected, replaced);
	}

	@Test
	void append() {
		var first = new PhonemeString(List.of(PHONEME_A), List.of(DEFAULT_CONTEXT));
		var second = new PhonemeString(List.of(PHONEME_B), List.of(DEFAULT_CONTEXT));
		var expected = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B),
				List.of(DEFAULT_CONTEXT, DEFAULT_CONTEXT));

		var concat = first.append(second);

		assertEquals(expected, concat);
	}

	@Test
	void phonemeAt() {
		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));

		assertEquals(PHONEME_A, sequence.phonemeAt(0));
		assertEquals(PHONEME_B, sequence.phonemeAt(1));
		assertEquals(PHONEME_C, sequence.phonemeAt(2));
	}

	@Test
	void contextAt() {
		var context1 = new PhonemeContext(true, false, false, false);
		var context2 = new PhonemeContext(false, true, false, false);
		var context3 = new PhonemeContext(false, false, true, false);

		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(context1, context2, context3));

		assertEquals(context1, sequence.contextAt(0));
		assertEquals(context2, sequence.contextAt(1));
		assertEquals(context3, sequence.contextAt(2));
	}

	@Test
	void appendEmpty() {
		var first = new PhonemeString(List.of(PHONEME_A), List.of(DEFAULT_CONTEXT));
		var second = new PhonemeString();

		var concat = first.append(second);

		assertEquals(first, concat);
	}

	@Test
	void subsequenceBegin() {
		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));
		var expected = new PhonemeString(
				List.of(PHONEME_B, PHONEME_C),
				List.of(new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));

		var subsequence = sequence.substring(1);

		assertEquals(expected, subsequence);
	}

	@Test
	void subsequenceBeginAndEnd() {
		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(DEFAULT_CONTEXT, new PhonemeContext(true, true, true, true), DEFAULT_CONTEXT));
		var expected = new PhonemeString(
				List.of(PHONEME_B),
				List.of(new PhonemeContext(true, true, true, true)));

		var subsequence = sequence.substring(1, 2);

		assertEquals(expected, subsequence);
	}

	@Test
	void getTransition() {
		var context1 = new PhonemeContext(true, false, true, false);
		var context2 = new PhonemeContext(false, false, false, false);
		var context3 = new PhonemeContext(false, true, false, true);

		var sequence = new PhonemeString(
				List.of(PHONEME_A, PHONEME_B, PHONEME_C),
				List.of(context1, context2, context3));

		assertEquals(new PhonemeTransition(true, true, true), sequence.getTransition(-1));
		assertEquals(new PhonemeTransition(false, false, false), sequence.getTransition(0));
		assertEquals(new PhonemeTransition(false, false, false), sequence.getTransition(1));
		assertEquals(new PhonemeTransition(true, true, true), sequence.getTransition(2));
	}

	@Test
	void getTransitionEmptySequence() {
		var sequence = new PhonemeString();
		var expected = new PhonemeTransition(false, false, false);

		assertEquals(expected, sequence.getTransition(-1));
		assertEquals(expected, sequence.getTransition(0));
		assertEquals(expected, sequence.getTransition(1));
	}

	@Test
	void render() {
		var sequence = new PhonemeString(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeContext(true, false, true, false),
					new PhonemeContext(false, true, false, false),
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, true)
				));
		var expected = "#ta.ta#";

		var rendered = RENDERER.renderWithContext(sequence);

		assertEquals(expected, rendered);
	}

	@Test
	void renderSyllableOnly() {
		var sequence = new PhonemeString(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, false),
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, false)
				));
		var expected = ".ta.ta.";

		var rendered = RENDERER.renderWithContext(sequence);

		assertEquals(expected, rendered);
	}

	@Test
	void renderSequenceOnly() {
		var sequence = new PhonemeString(
				List.of(VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(DEFAULT_CONTEXT, DEFAULT_CONTEXT, DEFAULT_CONTEXT, DEFAULT_CONTEXT));
		var expected = "tata";

		var rendered = RENDERER.renderWithContext(sequence);

		assertEquals(expected, rendered);
	}

	@Test
	void renderEmptySequence() {
		var sequence = new PhonemeString();
		var expected = "";

		var rendered = RENDERER.renderWithContext(sequence);

		assertEquals(expected, rendered);
	}

}
