package net.calebscode.langforge.phonology.phoneme;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardPhonemes {

	// Vowels
	public static final Phoneme CLOSE_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_FRONT_ROUNDED_VOWEL;

	public static final Phoneme CLOSE_CENTRAL_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_CENTRAL_ROUNDED_VOWEL;

	public static final Phoneme CLOSE_BRACK_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_BRACK_ROUNDED_VOWEL;


	public static final Phoneme NEAR_CLOSE_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme NEAR_CLOSE_FRONT_ROUNDED_VOWEL;

	public static final Phoneme NEAR_CLOSE_BRACK_ROUNDED_VOWEL;


	public static final Phoneme CLOSE_MID_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_MID_FRONT_ROUNDED_VOWEL;

	public static final Phoneme CLOSE_MID_CENTRAL_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_MID_CENTRAL_ROUNDED_VOWEL;

	public static final Phoneme CLOSE_MID_BRACK_UNROUNDED_VOWEL;
	public static final Phoneme CLOSE_MID_BRACK_ROUNDED_VOWEL;


	public static final Phoneme MID_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme MID_FRONT_ROUNDED_VOWEL;

	public static final Phoneme MID_CENTRAL_VOWEL;

	public static final Phoneme MID_BRACK_UNROUNDED_VOWEL;
	public static final Phoneme MID_BRACK_ROUNDED_VOWEL;


	public static final Phoneme OPEN_MID_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme OPEN_MID_FRONT_ROUNDED_VOWEL;

	public static final Phoneme OPEN_MID_CENTRAL_UNROUNDED_VOWEL;
	public static final Phoneme OPEN_MID_CENTRAL_ROUNDED_VOWEL;

	public static final Phoneme OPEN_MID_BRACK_UNROUNDED_VOWEL;
	public static final Phoneme OPEN_MID_BRACK_ROUNDED_VOWEL;


	public static final Phoneme NEAR_OPEN_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme NEAR_OPEN_CENTRAL_VOWEL;


	public static final Phoneme OPEN_FRONT_UNROUNDED_VOWEL;
	public static final Phoneme OPEN_FRONT_ROUNDED_VOWEL;

	public static final Phoneme OPEN_CENTRAL_UNROUNDED_VOWEL;

	public static final Phoneme OPEN_BRACK_UNROUNDED_VOWEL;
	public static final Phoneme OPEN_BRACK_ROUNDED_VOWEL;


	// Plosives
	public static final Phoneme VOICELESS_BILABIAL_PLOSIVE;
	public static final Phoneme VOICED_BILABIAL_PLOSIVE;

	public static final Phoneme VOICELESS_DENTAL_PLOSIVE;
	public static final Phoneme VOICED_DENTAL_PLOSIVE;

	public static final Phoneme VOICELESS_ALVEOLAR_PLOSIVE;
	public static final Phoneme VOICED_ALVEOLAR_PLOSIVE;

	public static final Phoneme VOICELESS_RETROFLEX_PLOSIVE;
	public static final Phoneme VOICED_RETROFLEX_PLOSIVE;

	public static final Phoneme VOICELESS_PALATAL_PLOSIVE;
	public static final Phoneme VOICED_PALATAL_PLOSIVE;

	public static final Phoneme VOICELESS_VELAR_PLOSIVE;
	public static final Phoneme VOICED_VELAR_PLOSIVE;

	public static final Phoneme VOICELESS_UVULAR_PLOSIVE;
	public static final Phoneme VOICED_UVULAR_PLOSIVE;

	public static final Phoneme VOICELESS_GLOTTAL_PLOSIVE;


	// Nasals
	public static final Phoneme VOICED_BILABIAL_NASAL;
	public static final Phoneme VOICED_LABIODENTAL_NASAL;
	public static final Phoneme VOICED_ALVEOLAR_NASAL;
	public static final Phoneme VOICED_RETROFLEX_NASAL;
	public static final Phoneme VOICED_PALATAL_NASAL;
	public static final Phoneme VOICED_VELAR_NASAL;
	public static final Phoneme VOICED_UVULAR_NASAL;


	// Trills
	public static final Phoneme VOICED_BILABIAL_TRILL;
	public static final Phoneme VOICED_ALVEOLAR_TRILL;
	public static final Phoneme VOICED_UVULAR_TRILL;


	// Flaps
	public static final Phoneme VOICED_LABIODENTAL_FLAP;
	public static final Phoneme VOICED_ALVEOLAR_FLAP;
	public static final Phoneme VOICED_RETROFLEX_FLAP;


	// Fricatives
	public static final Phoneme VOICELESS_BILABIAL_FRICATIVE;
	public static final Phoneme VOICED_BILABIAL_FRICATIVE;

	public static final Phoneme VOICELESS_LABIODENTAL_FRICATIVE;
	public static final Phoneme VOICED_LABIODENTAL_FRICATIVE;

	public static final Phoneme VOICELESS_DENTAL_FRICATIVE;
	public static final Phoneme VOICED_DENTAL_FRICATIVE;

	public static final Phoneme VOICELESS_ALVEOLAR_FRICATIVE;
	public static final Phoneme VOICED_ALVEOLAR_FRICATIVE;

	public static final Phoneme VOICELESS_POST_ALVEOLAR_FRICATIVE;
	public static final Phoneme VOICED_POST_ALVEOLAR_FRICATIVE;

	public static final Phoneme VOICELESS_RETROFLEX_FRICATIVE;
	public static final Phoneme VOICED_RETROFLEX_FRICATIVE;

	public static final Phoneme VOICELESS_PALATAL_FRICATIVE;
	public static final Phoneme VOICED_PALATAL_FRICATIVE;

	public static final Phoneme VOICELESS_VELAR_FRICATIVE;
	public static final Phoneme VOICED_VELAR_FRICATIVE;

	public static final Phoneme VOICELESS_UVULAR_FRICATIVE;
	public static final Phoneme VOICED_UVULAR_FRICATIVE;

	public static final Phoneme VOICELESS_PHARYNGEAL_FRICATIVE;
	public static final Phoneme VOICED_PHARYNGEAL_FRICATIVE;

	public static final Phoneme VOICELESS_GLOTTAL_FRICATIVE;
	public static final Phoneme VOICED_GLOTTAL_FRICATIVE;


	// Lateral Fricatives
	public static final Phoneme VOICELESS_ALVEOLAR_LATERAL_FRICATIVE;
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_FRICATIVE;


	// Approximates
	public static final Phoneme VOICED_LABIODENTAL_APPROXIMATE;
	public static final Phoneme VOICED_ALVEOLAR_APPROXIMATE;
	public static final Phoneme VOICED_RETROFLEX_APPROXIMATE;
	public static final Phoneme VOICED_PALATAL_APPROXIMATE;
	public static final Phoneme VOICED_VELAR_APPROXIMATE;
	public static final Phoneme VOICED_LABIOVELAR_APPROXIXIMATE;


	// Lateral Approximates
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_APPROXIMATE;
	public static final Phoneme VOICED_RETROFLEX_LATERAL_APPROXIMATE;
	public static final Phoneme VOICED_PALATAL_LATERAL_APPROXIMATE;
	public static final Phoneme VOICED_VELAR_LATERAL_APPROXIMATE;


	// Affricates
	public static final Phoneme VOICELESS_ALVEOLAR_AFFRICATE;
	public static final Phoneme VOICED_ALVEOLAR_AFFRICATE;

	public static final Phoneme VOICELESS_PALATO_ALVEOLAR_AFFRICATE;
	public static final Phoneme VOICED_PALATO_ALVEOLAR_AFFRICATE;

	public static final Phoneme VOICELESS_ALVEOLO_PALATAL_AFFRICATE;
	public static final Phoneme VOICED_ALVEOLO_PALATAL_AFFRICATE;

	public static final Phoneme VOICELESS_RETROFLEX_AFFRICATE;
	public static final Phoneme VOICED_RETROFLEX_AFFRICATE;


	// Standard Phoneme Map
	public static final PhonemeStringMap IPA_PHONEME_STRING_MAP = new PhonemeStringMap();
	public static final List<Phoneme> IPA_PHONEMES;

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
		IPA_PHONEME_STRING_MAP.addMapping("i", CLOSE_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("y", CLOSE_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɨ", CLOSE_CENTRAL_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ʉ", CLOSE_CENTRAL_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɯ", CLOSE_BRACK_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("u", CLOSE_BRACK_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɪ", NEAR_CLOSE_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ʏ", NEAR_CLOSE_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ʊ", NEAR_CLOSE_BRACK_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("e", CLOSE_MID_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ø", CLOSE_MID_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɘ", CLOSE_MID_CENTRAL_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɵ", CLOSE_MID_CENTRAL_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɤ", CLOSE_MID_BRACK_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("o", CLOSE_MID_BRACK_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("e̞", MID_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ø̞", MID_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ə", MID_CENTRAL_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɤ̞", MID_BRACK_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("o̞", MID_BRACK_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɛ", OPEN_MID_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("œ", OPEN_MID_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɜ", OPEN_MID_CENTRAL_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɞ", OPEN_MID_CENTRAL_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ʌ", OPEN_MID_BRACK_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɔ", OPEN_MID_BRACK_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("æ", NEAR_OPEN_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɐ", NEAR_OPEN_CENTRAL_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("a", OPEN_FRONT_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɶ", OPEN_FRONT_ROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ä", OPEN_CENTRAL_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɑ", OPEN_BRACK_UNROUNDED_VOWEL);
		IPA_PHONEME_STRING_MAP.addMapping("ɒ", OPEN_BRACK_ROUNDED_VOWEL);

		// Plosives
		IPA_PHONEME_STRING_MAP.addMapping("p", VOICELESS_BILABIAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("b", VOICED_BILABIAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("t̪", VOICELESS_DENTAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("d̪", VOICED_DENTAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("t", VOICELESS_ALVEOLAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("d", VOICED_ALVEOLAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʈ", VOICELESS_RETROFLEX_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɖ", VOICED_RETROFLEX_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("c", VOICELESS_PALATAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɟ", VOICED_PALATAL_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("k", VOICELESS_VELAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("g", VOICED_VELAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("q", VOICELESS_UVULAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɢ", VOICED_UVULAR_PLOSIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʔ", VOICELESS_GLOTTAL_PLOSIVE);

		// Nasals
		IPA_PHONEME_STRING_MAP.addMapping("m", VOICED_BILABIAL_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("ɱ", VOICED_LABIODENTAL_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("n", VOICED_ALVEOLAR_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("ɳ", VOICED_RETROFLEX_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("ɲ", VOICED_PALATAL_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("ŋ", VOICED_VELAR_NASAL);
		IPA_PHONEME_STRING_MAP.addMapping("ɴ", VOICED_UVULAR_NASAL);

		// Trills
		IPA_PHONEME_STRING_MAP.addMapping("ʙ", VOICED_BILABIAL_TRILL);
		IPA_PHONEME_STRING_MAP.addMapping("r", VOICED_ALVEOLAR_TRILL);
		IPA_PHONEME_STRING_MAP.addMapping("ʀ", VOICED_UVULAR_TRILL);

		// Flaps
		IPA_PHONEME_STRING_MAP.addMapping("ⱱ", VOICED_LABIODENTAL_FLAP);
		IPA_PHONEME_STRING_MAP.addMapping("ɾ", VOICED_ALVEOLAR_FLAP);
		IPA_PHONEME_STRING_MAP.addMapping("ɽ", VOICED_RETROFLEX_FLAP);

		// Fricatives
		IPA_PHONEME_STRING_MAP.addMapping("ɸ", VOICELESS_BILABIAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("β", VOICED_BILABIAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("f", VOICELESS_LABIODENTAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("v", VOICED_LABIODENTAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("θ", VOICELESS_DENTAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ð", VOICED_DENTAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("s", VOICELESS_ALVEOLAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("z", VOICED_ALVEOLAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʃ", VOICELESS_POST_ALVEOLAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʒ", VOICED_POST_ALVEOLAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʂ", VOICELESS_RETROFLEX_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʐ", VOICED_RETROFLEX_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ç", VOICELESS_PALATAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʝ", VOICED_PALATAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("x", VOICELESS_VELAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɣ", VOICED_VELAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("χ", VOICELESS_UVULAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʁ", VOICED_UVULAR_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ħ", VOICELESS_PHARYNGEAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ʕ", VOICED_PHARYNGEAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("h", VOICELESS_GLOTTAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɦ", VOICED_GLOTTAL_FRICATIVE);

		// Lateral Fricatives
		IPA_PHONEME_STRING_MAP.addMapping("ɬ", VOICELESS_ALVEOLAR_LATERAL_FRICATIVE);
		IPA_PHONEME_STRING_MAP.addMapping("ɮ", VOICED_ALVEOLAR_LATERAL_FRICATIVE);

		// Approximates
		IPA_PHONEME_STRING_MAP.addMapping("ʋ", VOICED_LABIODENTAL_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ɹ", VOICED_ALVEOLAR_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ɻ", VOICED_RETROFLEX_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("j", VOICED_PALATAL_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ɰ", VOICED_VELAR_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("w", VOICED_LABIOVELAR_APPROXIXIMATE);

		// Lateral Approximates
		IPA_PHONEME_STRING_MAP.addMapping("l", VOICED_ALVEOLAR_LATERAL_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ɭ", VOICED_RETROFLEX_LATERAL_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ʎ", VOICED_PALATAL_LATERAL_APPROXIMATE);
		IPA_PHONEME_STRING_MAP.addMapping("ʟ", VOICED_VELAR_LATERAL_APPROXIMATE);

		// Affricates
		IPA_PHONEME_STRING_MAP.addMapping("t͡s", VOICELESS_ALVEOLAR_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("d͡z", VOICED_ALVEOLAR_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("t͡ʃ", VOICELESS_PALATO_ALVEOLAR_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("d͡ʒ", VOICED_PALATO_ALVEOLAR_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("t͡ɕ", VOICELESS_ALVEOLO_PALATAL_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("d͡ʑ", VOICED_ALVEOLO_PALATAL_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("ʈ͡ʂ", VOICELESS_RETROFLEX_AFFRICATE);
		IPA_PHONEME_STRING_MAP.addMapping("ɖ͡ʐ", VOICED_RETROFLEX_AFFRICATE);

		// Create the list of all IPA phonemes
		var ipaPhonemesList = new ArrayList<Phoneme>();

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

		IPA_PHONEMES = Collections.unmodifiableList(ipaPhonemesList);
	}

}
