package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.Word;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence.PhonemeMetadata;
import net.calebscode.langforge.phonology.syllable.Syllable;

public class PhonemeSequenceBuilderTest {

	private static final PhonemeMetadata DEFAULT_METADATA = new PhonemeMetadata(false, false, false, false);

	private PhonemeSequenceBuilder builder;

	@BeforeEach
	void beforeEach() {
		builder = new PhonemeSequenceBuilder();
	}

	@Test
	void appendIpaStringPhonemes() throws IpaMappingException {
		builder.append("kat", STANDARD_IPA_PHONEMES);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE),
				List.of(DEFAULT_METADATA, DEFAULT_METADATA, DEFAULT_METADATA));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringSyllable() throws IpaMappingException {
		builder.append(".ka.ta.", STANDARD_IPA_PHONEMES);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, false),
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, false)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringWord() throws IpaMappingException {
		builder.append("#kat#", STANDARD_IPA_PHONEMES);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE),
				List.of(
					new PhonemeMetadata(true, false, true, false),
					DEFAULT_METADATA,
					new PhonemeMetadata(false, true, false, true)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringMultisyllabicWord() throws IpaMappingException {
		builder.append("#ka.ta#", STANDARD_IPA_PHONEMES);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL, VOICELESS_ALVEOLAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(
					new PhonemeMetadata(true, false, true, false),
					new PhonemeMetadata(false, true, false, false),
					new PhonemeMetadata(true, false, false, false),
					new PhonemeMetadata(false, true, false, true)
				));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendIpaStringUnmappableIpa() {
		assertThrows(IpaMappingException.class, () -> builder.append("a", new IpaPhonemeMap()));
	}

	@Test
	void appendPhoneme() {
		builder.append(VOICELESS_VELAR_PLOSIVE);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE),
				List.of(DEFAULT_METADATA));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendSyllable() {
		var syllable = new Syllable(List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL));
		builder.append(syllable);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(new PhonemeMetadata(true, false, false, false), new PhonemeMetadata(false, true, false, false)));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

	@Test
	void appendWord() {
		var syllable = new Syllable(List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL));
		var word = new Word(syllable);
		builder.append(word);
		var expected = new PhonemeSequence(
				List.of(VOICELESS_VELAR_PLOSIVE, OPEN_FRONT_UNROUNDED_VOWEL),
				List.of(new PhonemeMetadata(true, false, true, false), new PhonemeMetadata(false, true, false, true)));

		var sequence = builder.build();

		assertEquals(expected, sequence);
	}

}
