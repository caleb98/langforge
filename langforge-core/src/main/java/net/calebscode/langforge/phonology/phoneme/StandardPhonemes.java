package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StandardPhonemes {

	/*
	 * Vowels
	 */
	
	/// A phoneme for IPA "i"
	public static final Phoneme CLOSE_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "y"
	public static final Phoneme CLOSE_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɨ"
	public static final Phoneme CLOSE_CENTRAL_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ʉ"
	public static final Phoneme CLOSE_CENTRAL_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɯ"
	public static final Phoneme CLOSE_BRACK_UNROUNDED_VOWEL;

	/// A phoneme for IPA "u"
	public static final Phoneme CLOSE_BRACK_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɪ"
	public static final Phoneme NEAR_CLOSE_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ʏ"
	public static final Phoneme NEAR_CLOSE_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ʊ"
	public static final Phoneme NEAR_CLOSE_BRACK_ROUNDED_VOWEL;

	/// A phoneme for IPA "e"
	public static final Phoneme CLOSE_MID_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ø"
	public static final Phoneme CLOSE_MID_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɘ"
	public static final Phoneme CLOSE_MID_CENTRAL_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɵ"
	public static final Phoneme CLOSE_MID_CENTRAL_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɤ"
	public static final Phoneme CLOSE_MID_BRACK_UNROUNDED_VOWEL;

	/// A phoneme for IPA "o"
	public static final Phoneme CLOSE_MID_BRACK_ROUNDED_VOWEL;

	/// A phoneme for IPA "e̞"
	public static final Phoneme MID_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ø̞"
	public static final Phoneme MID_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ə"
	public static final Phoneme MID_CENTRAL_VOWEL;

	/// A phoneme for IPA "ɤ̞"
	public static final Phoneme MID_BRACK_UNROUNDED_VOWEL;

	/// A phoneme for IPA "o̞"
	public static final Phoneme MID_BRACK_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɛ"
	public static final Phoneme OPEN_MID_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "œ"
	public static final Phoneme OPEN_MID_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ɜ"
	public static final Phoneme OPEN_MID_CENTRAL_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɞ"
	public static final Phoneme OPEN_MID_CENTRAL_ROUNDED_VOWEL;

	/// A phoneme for IPA "ʌ"
	public static final Phoneme OPEN_MID_BRACK_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɔ"
	public static final Phoneme OPEN_MID_BRACK_ROUNDED_VOWEL;

	/// A phoneme for IPA "æ"
	public static final Phoneme NEAR_OPEN_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɐ"
	public static final Phoneme NEAR_OPEN_CENTRAL_VOWEL;

	/// A phoneme for IPA "a"
	public static final Phoneme OPEN_FRONT_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɶ"
	public static final Phoneme OPEN_FRONT_ROUNDED_VOWEL;

	/// A phoneme for IPA "ä"
	public static final Phoneme OPEN_CENTRAL_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɑ"
	public static final Phoneme OPEN_BRACK_UNROUNDED_VOWEL;

	/// A phoneme for IPA "ɒ"
	public static final Phoneme OPEN_BRACK_ROUNDED_VOWEL;

	/*
	 * Plosives
	 */
	
	/// A phoneme for IPA "p"
	public static final Phoneme VOICELESS_BILABIAL_PLOSIVE;

	/// A phoneme for IPA "b"
	public static final Phoneme VOICED_BILABIAL_PLOSIVE;

	/// A phoneme for IPA "t̪"
	public static final Phoneme VOICELESS_DENTAL_PLOSIVE;

	/// A phoneme for IPA "d̪"
	public static final Phoneme VOICED_DENTAL_PLOSIVE;

	/// A phoneme for IPA "t"
	public static final Phoneme VOICELESS_ALVEOLAR_PLOSIVE;

	/// A phoneme for IPA "d"
	public static final Phoneme VOICED_ALVEOLAR_PLOSIVE;

	/// A phoneme for IPA "ʈ"
	public static final Phoneme VOICELESS_RETROFLEX_PLOSIVE;

	/// A phoneme for IPA "ɖ"
	public static final Phoneme VOICED_RETROFLEX_PLOSIVE;

	/// A phoneme for IPA "c"
	public static final Phoneme VOICELESS_PALATAL_PLOSIVE;

	/// A phoneme for IPA "ɟ"
	public static final Phoneme VOICED_PALATAL_PLOSIVE;

	/// A phoneme for IPA "k"
	public static final Phoneme VOICELESS_VELAR_PLOSIVE;

	/// A phoneme for IPA "g"
	public static final Phoneme VOICED_VELAR_PLOSIVE;

	/// A phoneme for IPA "q"
	public static final Phoneme VOICELESS_UVULAR_PLOSIVE;

	/// A phoneme for IPA "ɢ"
	public static final Phoneme VOICED_UVULAR_PLOSIVE;

	/// A phoneme for IPA "ʔ"
	public static final Phoneme VOICELESS_GLOTTAL_PLOSIVE;

	/*
	 * Nasals
	 */
	
	/// A phoneme for IPA "m"
	public static final Phoneme VOICED_BILABIAL_NASAL;

	/// A phoneme for IPA "ɱ"
	public static final Phoneme VOICED_LABIODENTAL_NASAL;

	/// A phoneme for IPA "n"
	public static final Phoneme VOICED_ALVEOLAR_NASAL;

	/// A phoneme for IPA "ɳ"
	public static final Phoneme VOICED_RETROFLEX_NASAL;

	/// A phoneme for IPA "ɲ"
	public static final Phoneme VOICED_PALATAL_NASAL;

	/// A phoneme for IPA "ŋ"
	public static final Phoneme VOICED_VELAR_NASAL;

	/// A phoneme for IPA "ɴ"
	public static final Phoneme VOICED_UVULAR_NASAL;

	/*
	 * Trills
	 */
	
	/// A phoneme for IPA "ʙ"
	public static final Phoneme VOICED_BILABIAL_TRILL;

	/// A phoneme for IPA "r"
	public static final Phoneme VOICED_ALVEOLAR_TRILL;

	/// A phoneme for IPA "ʀ"
	public static final Phoneme VOICED_UVULAR_TRILL;

	/*
	 * Flaps
	 */
	
	/// A phoneme for IPA "ⱱ"
	public static final Phoneme VOICED_LABIODENTAL_FLAP;

	/// A phoneme for IPA "ɾ"
	public static final Phoneme VOICED_ALVEOLAR_FLAP;

	/// A phoneme for IPA "ɽ"
	public static final Phoneme VOICED_RETROFLEX_FLAP;

	/*
	 * Fricatives
	 */
	
	/// A phoneme for IPA "ɸ"
	public static final Phoneme VOICELESS_BILABIAL_FRICATIVE;

	/// A phoneme for IPA "β"
	public static final Phoneme VOICED_BILABIAL_FRICATIVE;

	/// A phoneme for IPA "f"
	public static final Phoneme VOICELESS_LABIODENTAL_FRICATIVE;

	/// A phoneme for IPA "v"
	public static final Phoneme VOICED_LABIODENTAL_FRICATIVE;

	/// A phoneme for IPA "θ"
	public static final Phoneme VOICELESS_DENTAL_FRICATIVE;

	/// A phoneme for IPA "ð"
	public static final Phoneme VOICED_DENTAL_FRICATIVE;

	/// A phoneme for IPA "s"
	public static final Phoneme VOICELESS_ALVEOLAR_FRICATIVE;

	/// A phoneme for IPA "z"
	public static final Phoneme VOICED_ALVEOLAR_FRICATIVE;

	/// A phoneme for IPA "ʃ"
	public static final Phoneme VOICELESS_POST_ALVEOLAR_FRICATIVE;

	/// A phoneme for IPA "ʒ"
	public static final Phoneme VOICED_POST_ALVEOLAR_FRICATIVE;

	/// A phoneme for IPA "ʂ"
	public static final Phoneme VOICELESS_RETROFLEX_FRICATIVE;

	/// A phoneme for IPA "ʐ"
	public static final Phoneme VOICED_RETROFLEX_FRICATIVE;

	/// A phoneme for IPA "ç"
	public static final Phoneme VOICELESS_PALATAL_FRICATIVE;

	/// A phoneme for IPA "ʝ"
	public static final Phoneme VOICED_PALATAL_FRICATIVE;

	/// A phoneme for IPA "x"
	public static final Phoneme VOICELESS_VELAR_FRICATIVE;

	/// A phoneme for IPA "ɣ"
	public static final Phoneme VOICED_VELAR_FRICATIVE;

	/// A phoneme for IPA "χ"
	public static final Phoneme VOICELESS_UVULAR_FRICATIVE;

	/// A phoneme for IPA "ʁ"
	public static final Phoneme VOICED_UVULAR_FRICATIVE;

	/// A phoneme for IPA "ħ"
	public static final Phoneme VOICELESS_PHARYNGEAL_FRICATIVE;

	/// A phoneme for IPA "ʕ"
	public static final Phoneme VOICED_PHARYNGEAL_FRICATIVE;

	/// A phoneme for IPA "h"
	public static final Phoneme VOICELESS_GLOTTAL_FRICATIVE;

	/// A phoneme for IPA "ɦ"
	public static final Phoneme VOICED_GLOTTAL_FRICATIVE;

	/*
	 * Lateral Fricatives
	 */
	
	/// A phoneme for IPA "ɬ"
	public static final Phoneme VOICELESS_ALVEOLAR_LATERAL_FRICATIVE;

	/// A phoneme for IPA "ɮ"
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_FRICATIVE;

	/*
	 * Approximates
	 */
	
	/// A phoneme for IPA "ʋ"
	public static final Phoneme VOICED_LABIODENTAL_APPROXIMATE;

	/// A phoneme for IPA "ɹ"
	public static final Phoneme VOICED_ALVEOLAR_APPROXIMATE;

	/// A phoneme for IPA "ɻ"
	public static final Phoneme VOICED_RETROFLEX_APPROXIMATE;

	/// A phoneme for IPA "j"
	public static final Phoneme VOICED_PALATAL_APPROXIMATE;

	/// A phoneme for IPA "ɰ"
	public static final Phoneme VOICED_VELAR_APPROXIMATE;

	/// A phoneme for IPA "w"
	public static final Phoneme VOICED_LABIOVELAR_APPROXIXIMATE;

	/*
	 * Lateral Approximates
	 */
	
	/// A phoneme for IPA "l"
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_APPROXIMATE;

	/// A phoneme for IPA "ɭ"
	public static final Phoneme VOICED_RETROFLEX_LATERAL_APPROXIMATE;

	/// A phoneme for IPA "ʎ"
	public static final Phoneme VOICED_PALATAL_LATERAL_APPROXIMATE;

	/// A phoneme for IPA "ʟ"
	public static final Phoneme VOICED_VELAR_LATERAL_APPROXIMATE;

	/*
	 * Affricates
	 */
	
	/// A phoneme for IPA "t͡s"
	public static final Phoneme VOICELESS_ALVEOLAR_AFFRICATE;

	/// A phoneme for IPA "d͡z"
	public static final Phoneme VOICED_ALVEOLAR_AFFRICATE;

	/// A phoneme for IPA "t͡ʃ"
	public static final Phoneme VOICELESS_PALATO_ALVEOLAR_AFFRICATE;

	/// A phoneme for IPA "d͡ʒ"
	public static final Phoneme VOICED_PALATO_ALVEOLAR_AFFRICATE;

	/// A phoneme for IPA "t͡ɕ"
	public static final Phoneme VOICELESS_ALVEOLO_PALATAL_AFFRICATE;

	/// A phoneme for IPA "d͡ʑ"
	public static final Phoneme VOICED_ALVEOLO_PALATAL_AFFRICATE;

	/// A phoneme for IPA "ʈ͡ʂ"
	public static final Phoneme VOICELESS_RETROFLEX_AFFRICATE;

	/// A phoneme for IPA "ɖ͡ʐ"
	public static final Phoneme VOICED_RETROFLEX_AFFRICATE;

	/*
	 * Utility Constants
	 */

	/// A [PhonemeRepresentationMapper] that is backed by the standard IPA phoneme representations.
	public static final PhonemeRepresentationMapper IPA_PHONEME_REPRESENTATION_MAPPER;
	
	/// A [PhonemeSequenceRenderer] backed by [StandardPhonemes#IPA_PHONEME_REPRESENTATION_MAPPER].
	public static final PhonemeSequenceRenderer IPA_PHONEME_SEQUENCE_RENDERER;
	
	/// A set of all [Phoneme] constants defined in this class.
	public static final Set<Phoneme> IPA_PHONEMES;

	static {
		CLOSE_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		CLOSE_CENTRAL_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_CENTRAL_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		CLOSE_BRACK_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		NEAR_CLOSE_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_NEAR_CLOSE)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		NEAR_CLOSE_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_NEAR_CLOSE)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		NEAR_CLOSE_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_NEAR_CLOSE)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		CLOSE_MID_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_MID_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		CLOSE_MID_CENTRAL_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_MID_CENTRAL_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		CLOSE_MID_BRACK_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		CLOSE_MID_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_CLOSE_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		MID_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		MID_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		MID_CENTRAL_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_MID)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		MID_BRACK_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		MID_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		OPEN_MID_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		OPEN_MID_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		OPEN_MID_CENTRAL_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		OPEN_MID_CENTRAL_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		OPEN_MID_BRACK_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		OPEN_MID_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN_MID)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		NEAR_OPEN_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_NEAR_OPEN)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		NEAR_OPEN_CENTRAL_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_NEAR_OPEN)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


		OPEN_FRONT_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		OPEN_FRONT_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN)
			.withFeature(BACKNESS, BACKNESS_FRONT)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		OPEN_CENTRAL_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN)
			.withFeature(BACKNESS, BACKNESS_CENTRAL)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();

		OPEN_BRACK_UNROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_UNROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();
		OPEN_BRACK_ROUNDED_VOWEL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(OPENNESS, OPENNESS_OPEN)
			.withFeature(BACKNESS, BACKNESS_BACK)
			.withFeature(ROUNDEDNESS, ROUNDEDNESS_ROUNDED)
			.withFeature(CATEGORY, CATEGORY_VOWEL)
			.build();


			// Plosives
		VOICELESS_BILABIAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_BILABIAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_DENTAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_DENTAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_DENTAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_DENTAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_ALVEOLAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_RETROFLEX_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_PALATAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_VELAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_VELAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_UVULAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_UVULAR_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_GLOTTAL_PLOSIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_GLOTTAL)
			.withFeature(TYPE, TYPE_PLOSIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Nasals
		VOICED_BILABIAL_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_LABIODENTAL_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_LABIODENTAL)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATAL_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_VELAR_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_UVULAR_NASAL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_NASAL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Trills
		VOICED_BILABIAL_TRILL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_TRILL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_TRILL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_TRILL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_UVULAR_TRILL = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_TRILL)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Flaps
		VOICED_LABIODENTAL_FLAP = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_LABIODENTAL)
			.withFeature(TYPE, TYPE_FLAP)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_FLAP = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_FLAP)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_FLAP = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_FLAP)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Fricatives
		VOICELESS_BILABIAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_BILABIAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_BILABIAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_LABIODENTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_LABIODENTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_LABIODENTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_LABIODENTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_DENTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_DENTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_DENTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_DENTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_ALVEOLAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_POST_ALVEOLAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_POST_ALVEOLAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_POST_ALVEOLAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_POST_ALVEOLAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_RETROFLEX_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_PALATAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_VELAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_VELAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_UVULAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_UVULAR_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_UVULAR)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_PHARYNGEAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_PHARYNGEAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PHARYNGEAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PHARYNGEAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_GLOTTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_GLOTTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_GLOTTAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_GLOTTAL)
			.withFeature(TYPE, TYPE_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Lateral Fricatives
		VOICELESS_ALVEOLAR_LATERAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_LATERAL_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_LATERAL_FRICATIVE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_LATERAL_FRICATIVE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Approximates
		VOICED_LABIODENTAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_LABIODENTAL)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_VELAR_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_LABIOVELAR_APPROXIXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_LABIOVELAR)
			.withFeature(TYPE, TYPE_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Lateral Approximates
		VOICED_ALVEOLAR_LATERAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_LATERAL_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_LATERAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_LATERAL_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATAL_LATERAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATAL)
			.withFeature(TYPE, TYPE_LATERAL_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_VELAR_LATERAL_APPROXIMATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_VELAR)
			.withFeature(TYPE, TYPE_LATERAL_APPROXIMATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();


			// Affricates
		VOICELESS_ALVEOLAR_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLAR_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLAR)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_PALATO_ALVEOLAR_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_PALATO_ALVEOLAR)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_PALATO_ALVEOLAR_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_PALATO_ALVEOLAR)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_ALVEOLO_PALATAL_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_ALVEOLO_PALATAL)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_ALVEOLO_PALATAL_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_ALVEOLO_PALATAL)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		VOICELESS_RETROFLEX_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICELESS)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();
		VOICED_RETROFLEX_AFFRICATE = new PhonemeBuilder()
			.withFeature(VOICING, VOICING_VOICED)
			.withFeature(PLACE, PLACE_RETROFLEX)
			.withFeature(TYPE, TYPE_AFFRICATE)
			.withFeature(CATEGORY, CATEGORY_CONSONANT)
			.build();

		// Vowels
		var ipaPhonemeRepresentationMapper = new PhonemeRepresentationMap();
		ipaPhonemeRepresentationMapper.addMapping("i", CLOSE_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("y", CLOSE_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɨ", CLOSE_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ʉ", CLOSE_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɯ", CLOSE_BRACK_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("u", CLOSE_BRACK_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɪ", NEAR_CLOSE_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ʏ", NEAR_CLOSE_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ʊ", NEAR_CLOSE_BRACK_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("e", CLOSE_MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ø", CLOSE_MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɘ", CLOSE_MID_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɵ", CLOSE_MID_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɤ", CLOSE_MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("o", CLOSE_MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("e̞", MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ø̞", MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ə", MID_CENTRAL_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɤ̞", MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("o̞", MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɛ", OPEN_MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("œ", OPEN_MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɜ", OPEN_MID_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɞ", OPEN_MID_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ʌ", OPEN_MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɔ", OPEN_MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("æ", NEAR_OPEN_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɐ", NEAR_OPEN_CENTRAL_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("a", OPEN_FRONT_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɶ", OPEN_FRONT_ROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ä", OPEN_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɑ", OPEN_BRACK_UNROUNDED_VOWEL);
		ipaPhonemeRepresentationMapper.addMapping("ɒ", OPEN_BRACK_ROUNDED_VOWEL);

		// Plosives
		ipaPhonemeRepresentationMapper.addMapping("p", VOICELESS_BILABIAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("b", VOICED_BILABIAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("t̪", VOICELESS_DENTAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("d̪", VOICED_DENTAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("t", VOICELESS_ALVEOLAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("d", VOICED_ALVEOLAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʈ", VOICELESS_RETROFLEX_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɖ", VOICED_RETROFLEX_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("c", VOICELESS_PALATAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɟ", VOICED_PALATAL_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("k", VOICELESS_VELAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("g", VOICED_VELAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("q", VOICELESS_UVULAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɢ", VOICED_UVULAR_PLOSIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʔ", VOICELESS_GLOTTAL_PLOSIVE);

		// Nasals
		ipaPhonemeRepresentationMapper.addMapping("m", VOICED_BILABIAL_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("ɱ", VOICED_LABIODENTAL_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("n", VOICED_ALVEOLAR_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("ɳ", VOICED_RETROFLEX_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("ɲ", VOICED_PALATAL_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("ŋ", VOICED_VELAR_NASAL);
		ipaPhonemeRepresentationMapper.addMapping("ɴ", VOICED_UVULAR_NASAL);

		// Trills
		ipaPhonemeRepresentationMapper.addMapping("ʙ", VOICED_BILABIAL_TRILL);
		ipaPhonemeRepresentationMapper.addMapping("r", VOICED_ALVEOLAR_TRILL);
		ipaPhonemeRepresentationMapper.addMapping("ʀ", VOICED_UVULAR_TRILL);

		// Flaps
		ipaPhonemeRepresentationMapper.addMapping("ⱱ", VOICED_LABIODENTAL_FLAP);
		ipaPhonemeRepresentationMapper.addMapping("ɾ", VOICED_ALVEOLAR_FLAP);
		ipaPhonemeRepresentationMapper.addMapping("ɽ", VOICED_RETROFLEX_FLAP);

		// Fricatives
		ipaPhonemeRepresentationMapper.addMapping("ɸ", VOICELESS_BILABIAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("β", VOICED_BILABIAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("f", VOICELESS_LABIODENTAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("v", VOICED_LABIODENTAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("θ", VOICELESS_DENTAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ð", VOICED_DENTAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("s", VOICELESS_ALVEOLAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("z", VOICED_ALVEOLAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʃ", VOICELESS_POST_ALVEOLAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʒ", VOICED_POST_ALVEOLAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʂ", VOICELESS_RETROFLEX_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʐ", VOICED_RETROFLEX_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ç", VOICELESS_PALATAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʝ", VOICED_PALATAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("x", VOICELESS_VELAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɣ", VOICED_VELAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("χ", VOICELESS_UVULAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʁ", VOICED_UVULAR_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ħ", VOICELESS_PHARYNGEAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ʕ", VOICED_PHARYNGEAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("h", VOICELESS_GLOTTAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɦ", VOICED_GLOTTAL_FRICATIVE);

		// Lateral Fricatives
		ipaPhonemeRepresentationMapper.addMapping("ɬ", VOICELESS_ALVEOLAR_LATERAL_FRICATIVE);
		ipaPhonemeRepresentationMapper.addMapping("ɮ", VOICED_ALVEOLAR_LATERAL_FRICATIVE);

		// Approximates
		ipaPhonemeRepresentationMapper.addMapping("ʋ", VOICED_LABIODENTAL_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ɹ", VOICED_ALVEOLAR_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ɻ", VOICED_RETROFLEX_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("j", VOICED_PALATAL_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ɰ", VOICED_VELAR_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("w", VOICED_LABIOVELAR_APPROXIXIMATE);

		// Lateral Approximates
		ipaPhonemeRepresentationMapper.addMapping("l", VOICED_ALVEOLAR_LATERAL_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ɭ", VOICED_RETROFLEX_LATERAL_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ʎ", VOICED_PALATAL_LATERAL_APPROXIMATE);
		ipaPhonemeRepresentationMapper.addMapping("ʟ", VOICED_VELAR_LATERAL_APPROXIMATE);

		// Affricates
		ipaPhonemeRepresentationMapper.addMapping("t͡s", VOICELESS_ALVEOLAR_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("d͡z", VOICED_ALVEOLAR_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("t͡ʃ", VOICELESS_PALATO_ALVEOLAR_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("d͡ʒ", VOICED_PALATO_ALVEOLAR_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("t͡ɕ", VOICELESS_ALVEOLO_PALATAL_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("d͡ʑ", VOICED_ALVEOLO_PALATAL_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("ʈ͡ʂ", VOICELESS_RETROFLEX_AFFRICATE);
		ipaPhonemeRepresentationMapper.addMapping("ɖ͡ʐ", VOICED_RETROFLEX_AFFRICATE);

		IPA_PHONEME_REPRESENTATION_MAPPER = ipaPhonemeRepresentationMapper;
		IPA_PHONEME_SEQUENCE_RENDERER = new PhonemeSequenceRenderer(IPA_PHONEME_REPRESENTATION_MAPPER);

		// Create the list of all IPA phonemes
		var ipaPhonemesList = new HashSet<Phoneme>();

		ipaPhonemesList.add(CLOSE_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_BRACK_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(NEAR_CLOSE_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(NEAR_CLOSE_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(NEAR_CLOSE_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemesList.add(CLOSE_MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(MID_CENTRAL_VOWEL);
		ipaPhonemesList.add(MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemesList.add(MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_CENTRAL_ROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_BRACK_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_MID_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(NEAR_OPEN_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(NEAR_OPEN_CENTRAL_VOWEL);
		ipaPhonemesList.add(OPEN_FRONT_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_FRONT_ROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_CENTRAL_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_BRACK_UNROUNDED_VOWEL);
		ipaPhonemesList.add(OPEN_BRACK_ROUNDED_VOWEL);
		ipaPhonemesList.add(VOICELESS_BILABIAL_PLOSIVE);
		ipaPhonemesList.add(VOICED_BILABIAL_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_DENTAL_PLOSIVE);
		ipaPhonemesList.add(VOICED_DENTAL_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_ALVEOLAR_PLOSIVE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_RETROFLEX_PLOSIVE);
		ipaPhonemesList.add(VOICED_RETROFLEX_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_PALATAL_PLOSIVE);
		ipaPhonemesList.add(VOICED_PALATAL_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_VELAR_PLOSIVE);
		ipaPhonemesList.add(VOICED_VELAR_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_UVULAR_PLOSIVE);
		ipaPhonemesList.add(VOICED_UVULAR_PLOSIVE);
		ipaPhonemesList.add(VOICELESS_GLOTTAL_PLOSIVE);
		ipaPhonemesList.add(VOICED_BILABIAL_NASAL);
		ipaPhonemesList.add(VOICED_LABIODENTAL_NASAL);
		ipaPhonemesList.add(VOICED_ALVEOLAR_NASAL);
		ipaPhonemesList.add(VOICED_RETROFLEX_NASAL);
		ipaPhonemesList.add(VOICED_PALATAL_NASAL);
		ipaPhonemesList.add(VOICED_VELAR_NASAL);
		ipaPhonemesList.add(VOICED_UVULAR_NASAL);
		ipaPhonemesList.add(VOICED_BILABIAL_TRILL);
		ipaPhonemesList.add(VOICED_ALVEOLAR_TRILL);
		ipaPhonemesList.add(VOICED_UVULAR_TRILL);
		ipaPhonemesList.add(VOICED_LABIODENTAL_FLAP);
		ipaPhonemesList.add(VOICED_ALVEOLAR_FLAP);
		ipaPhonemesList.add(VOICED_RETROFLEX_FLAP);
		ipaPhonemesList.add(VOICELESS_BILABIAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_BILABIAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_LABIODENTAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_LABIODENTAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_DENTAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_DENTAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_ALVEOLAR_FRICATIVE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_POST_ALVEOLAR_FRICATIVE);
		ipaPhonemesList.add(VOICED_POST_ALVEOLAR_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_RETROFLEX_FRICATIVE);
		ipaPhonemesList.add(VOICED_RETROFLEX_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_PALATAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_PALATAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_VELAR_FRICATIVE);
		ipaPhonemesList.add(VOICED_VELAR_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_UVULAR_FRICATIVE);
		ipaPhonemesList.add(VOICED_UVULAR_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_PHARYNGEAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_PHARYNGEAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_GLOTTAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_GLOTTAL_FRICATIVE);
		ipaPhonemesList.add(VOICELESS_ALVEOLAR_LATERAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_LATERAL_FRICATIVE);
		ipaPhonemesList.add(VOICED_LABIODENTAL_APPROXIMATE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_APPROXIMATE);
		ipaPhonemesList.add(VOICED_RETROFLEX_APPROXIMATE);
		ipaPhonemesList.add(VOICED_PALATAL_APPROXIMATE);
		ipaPhonemesList.add(VOICED_VELAR_APPROXIMATE);
		ipaPhonemesList.add(VOICED_LABIOVELAR_APPROXIXIMATE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_LATERAL_APPROXIMATE);
		ipaPhonemesList.add(VOICED_RETROFLEX_LATERAL_APPROXIMATE);
		ipaPhonemesList.add(VOICED_PALATAL_LATERAL_APPROXIMATE);
		ipaPhonemesList.add(VOICED_VELAR_LATERAL_APPROXIMATE);
		ipaPhonemesList.add(VOICELESS_ALVEOLAR_AFFRICATE);
		ipaPhonemesList.add(VOICED_ALVEOLAR_AFFRICATE);
		ipaPhonemesList.add(VOICELESS_PALATO_ALVEOLAR_AFFRICATE);
		ipaPhonemesList.add(VOICED_PALATO_ALVEOLAR_AFFRICATE);
		ipaPhonemesList.add(VOICELESS_ALVEOLO_PALATAL_AFFRICATE);
		ipaPhonemesList.add(VOICED_ALVEOLO_PALATAL_AFFRICATE);
		ipaPhonemesList.add(VOICELESS_RETROFLEX_AFFRICATE);
		ipaPhonemesList.add(VOICED_RETROFLEX_AFFRICATE);

		IPA_PHONEMES = Collections.unmodifiableSet(ipaPhonemesList);
	}

}
