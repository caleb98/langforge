package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.Word;
import net.calebscode.langforge.phonology.syllable.Syllable;

public class PhonemeSequenceBuilderTest {

	private static final PhonemeContext DEFAULT_CONTEXT = new PhonemeContext(false, false, false, false);

	private PhonemeSequenceBuilder builder;

	@BeforeEach
	void beforeEach() {
		builder = new PhonemeSequenceBuilder();
	}

	@Test
	void appendIpaStringPhonemes() throws PhonemeRepresentationMappingException {
		builder.append("kat", IPA_PHONEME_REPRESENTATION_MAPPER);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE),
				List.of(DEFAULT_CONTEXT, DEFAULT_CONTEXT, DEFAULT_CONTEXT));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringSyllable() throws PhonemeRepresentationMappingException {
		builder.append(".ka.ta.", IPA_PHONEME_REPRESENTATION_MAPPER);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, false),
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, false)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringWord() throws PhonemeRepresentationMappingException {
		builder.append("#kat#", IPA_PHONEME_REPRESENTATION_MAPPER);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE),
				List.of(
					new PhonemeContext(true, false, true, false),
					DEFAULT_CONTEXT,
					new PhonemeContext(false, true, false, true)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringMultisyllabicWord() throws PhonemeRepresentationMappingException {
		builder.append("#ka.ta#", IPA_PHONEME_REPRESENTATION_MAPPER);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeContext(true, false, true, false),
					new PhonemeContext(false, true, false, false),
					new PhonemeContext(true, false, false, false),
					new PhonemeContext(false, true, false, true)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringUnmappableIpa() {
		assertThrows(PhonemeRepresentationMappingException.class, () -> builder.append("a", new PhonemeRepresentationMap()));
	}

	@Test
	void appendPhoneme() {
		builder.append(VOICELESS_VELAR_PLOSIVE);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE),
				List.of(DEFAULT_CONTEXT));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendSyllable() {
		var syllable = new Syllable(List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL));
		builder.append(syllable);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(new PhonemeContext(true, false, false, false), new PhonemeContext(false, true, false, false)));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendWord() {
		var syllable = new Syllable(List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL));
		var word = new Word(syllable);
		builder.append(word);
		var expected = new PhonemeString(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(new PhonemeContext(true, false, true, false), new PhonemeContext(false, true, false, true)));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

}
