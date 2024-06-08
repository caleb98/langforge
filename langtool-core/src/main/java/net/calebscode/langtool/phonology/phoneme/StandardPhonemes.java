package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.BACKNESS_BACK;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.BACKNESS_CENTRAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.BACKNESS_FRONT;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.CATEGORY_CONSONANT;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.CATEGORY_VOWEL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_CLOSE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_CLOSE_MID;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_MID;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_NEAR_CLOSE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_NEAR_OPEN;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_OPEN;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.OPENNESS_OPEN_MID;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_ALVEOLAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_ALVEOLO_PALATAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_BILABIAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_DENTAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_GLOTTAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_LABIODENTAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_LABIOVELAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_PALATAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_PALATO_ALVEOLAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_PHARYNGEAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_POST_ALVEOLAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_RETROFLEX;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_UVULAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.PLACE_VELAR;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.ROUNDEDNESS_ROUNDED;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.ROUNDEDNESS_UNROUNDED;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_AFFRICATE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_APPROXIMATE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_FLAP;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_FRICATIVE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_LATERAL_APPROXIMATE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_LATERAL_FRICATIVE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_NASAL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_PLOSIVE;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.TYPE_TRILL;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.VOICING_VOICED;
import static net.calebscode.langtool.phonology.phoneme.StandardPhonemeFeatures.VOICING_VOICELESS;

public class StandardPhonemes {

	// Vowels
	public static final Phoneme CLOSE_FRONT_UNROUNDED_VOWEL = new Phoneme("i", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_FRONT_ROUNDED_VOWEL = new Phoneme("y", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme CLOSE_CENTRAL_UNROUNDED_VOWEL = new Phoneme("ɨ", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_CENTRAL, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_CENTRAL_ROUNDED_VOWEL = new Phoneme("ʉ", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_CENTRAL, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme CLOSE_BRACK_UNROUNDED_VOWEL = new Phoneme("ɯ", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_BACK, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_BRACK_ROUNDED_VOWEL = new Phoneme("u", VOICING_VOICED, OPENNESS_CLOSE, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	public static final Phoneme NEAR_CLOSE_FRONT_UNROUNDED_VOWEL = new Phoneme("ɪ", VOICING_VOICED, OPENNESS_NEAR_CLOSE, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme NEAR_CLOSE_FRONT_ROUNDED_VOWEL = new Phoneme("ʏ", VOICING_VOICED, OPENNESS_NEAR_CLOSE, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme NEAR_CLOSE_BRACK_ROUNDED_VOWEL = new Phoneme("ʊ", VOICING_VOICED, OPENNESS_NEAR_CLOSE, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	public static final Phoneme CLOSE_MID_FRONT_UNROUNDED_VOWEL = new Phoneme("e", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_MID_FRONT_ROUNDED_VOWEL = new Phoneme("ø", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme CLOSE_MID_CENTRAL_UNROUNDED_VOWEL = new Phoneme("ɘ", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_CENTRAL, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_MID_CENTRAL_ROUNDED_VOWEL = new Phoneme("ɵ", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_CENTRAL, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme CLOSE_MID_BRACK_UNROUNDED_VOWEL = new Phoneme("ɤ", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_BACK, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme CLOSE_MID_BRACK_ROUNDED_VOWEL = new Phoneme("o", VOICING_VOICED, OPENNESS_CLOSE_MID, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	public static final Phoneme MID_FRONT_UNROUNDED_VOWEL = new Phoneme("e̞", VOICING_VOICED, OPENNESS_MID, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme MID_FRONT_ROUNDED_VOWEL = new Phoneme("ø̞", VOICING_VOICED, OPENNESS_MID, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme MID_CENTRAL_VOWEL = new Phoneme("ə", VOICING_VOICED, OPENNESS_MID, BACKNESS_CENTRAL, CATEGORY_VOWEL);

	public static final Phoneme MID_BRACK_UNROUNDED_VOWEL = new Phoneme("ɤ̞", VOICING_VOICED, OPENNESS_MID, BACKNESS_BACK, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme MID_BRACK_ROUNDED_VOWEL = new Phoneme("o̞", VOICING_VOICED, OPENNESS_MID, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	public static final Phoneme OPEN_MID_FRONT_UNROUNDED_VOWEL = new Phoneme("ɛ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme OPEN_MID_FRONT_ROUNDED_VOWEL = new Phoneme("œ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme OPEN_MID_CENTRAL_UNROUNDED_VOWEL = new Phoneme("ɜ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_CENTRAL, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme OPEN_MID_CENTRAL_ROUNDED_VOWEL = new Phoneme("ɞ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_CENTRAL, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme OPEN_MID_BRACK_UNROUNDED_VOWEL = new Phoneme("ʌ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_BACK, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme OPEN_MID_BRACK_ROUNDED_VOWEL = new Phoneme("ɔ", VOICING_VOICED, OPENNESS_OPEN_MID, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	public static final Phoneme NEAR_OPEN_FRONT_UNROUNDED_VOWEL = new Phoneme("æ", VOICING_VOICED, OPENNESS_NEAR_OPEN, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme NEAR_OPEN_CENTRAL_VOWEL = new Phoneme("ɐ", VOICING_VOICED, OPENNESS_NEAR_OPEN, BACKNESS_CENTRAL, CATEGORY_VOWEL);


	public static final Phoneme OPEN_FRONT_UNROUNDED_VOWEL = new Phoneme("a", VOICING_VOICED, OPENNESS_OPEN, BACKNESS_FRONT, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme OPEN_FRONT_ROUNDED_VOWEL = new Phoneme("ɶ", VOICING_VOICED, OPENNESS_OPEN, BACKNESS_FRONT, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);

	public static final Phoneme OPEN_CENTRAL_UNROUNDED_VOWEL = new Phoneme("ä", VOICING_VOICED, OPENNESS_OPEN, BACKNESS_CENTRAL, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);

	public static final Phoneme OPEN_BRACK_UNROUNDED_VOWEL = new Phoneme("ɑ", VOICING_VOICED, OPENNESS_OPEN, BACKNESS_BACK, ROUNDEDNESS_UNROUNDED, CATEGORY_VOWEL);
	public static final Phoneme OPEN_BRACK_ROUNDED_VOWEL = new Phoneme("ɒ", VOICING_VOICED, OPENNESS_OPEN, BACKNESS_BACK, ROUNDEDNESS_ROUNDED, CATEGORY_VOWEL);


	// Plosives
	public static final Phoneme VOICELESS_BILABIAL_PLOSIVE = new Phoneme("p", VOICING_VOICELESS, PLACE_BILABIAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_BILABIAL_PLOSIVE = new Phoneme("b", VOICING_VOICED, PLACE_BILABIAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_DENTAL_PLOSIVE = new Phoneme("t̪", VOICING_VOICELESS, PLACE_DENTAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_DENTAL_PLOSIVE = new Phoneme("d̪", VOICING_VOICED, PLACE_DENTAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_ALVEOLAR_PLOSIVE = new Phoneme("t", VOICING_VOICELESS, PLACE_ALVEOLAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_PLOSIVE = new Phoneme("d", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_RETROFLEX_PLOSIVE = new Phoneme("ʈ", VOICING_VOICELESS, PLACE_RETROFLEX, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_PLOSIVE = new Phoneme("ɖ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_PALATAL_PLOSIVE = new Phoneme("c", VOICING_VOICELESS, PLACE_PALATAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATAL_PLOSIVE = new Phoneme("ɟ", VOICING_VOICED, PLACE_PALATAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_VELAR_PLOSIVE = new Phoneme("k", VOICING_VOICELESS, PLACE_VELAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_VELAR_PLOSIVE = new Phoneme("g", VOICING_VOICED, PLACE_VELAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_UVULAR_PLOSIVE = new Phoneme("q", VOICING_VOICELESS, PLACE_UVULAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_UVULAR_PLOSIVE = new Phoneme("ɢ", VOICING_VOICED, PLACE_UVULAR, TYPE_PLOSIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_GLOTTAL_PLOSIVE = new Phoneme("ʔ", VOICING_VOICELESS, PLACE_GLOTTAL, TYPE_PLOSIVE, CATEGORY_CONSONANT);


	// Nasals
	public static final Phoneme VOICED_BILABIAL_NASAL = new Phoneme("m", VOICING_VOICED, PLACE_BILABIAL, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_LABIODENTAL_NASAL = new Phoneme("ɱ", VOICING_VOICED, PLACE_LABIODENTAL, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_NASAL = new Phoneme("n", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_NASAL = new Phoneme("ɳ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATAL_NASAL = new Phoneme("ɲ", VOICING_VOICED, PLACE_PALATAL, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_VELAR_NASAL = new Phoneme("ŋ", VOICING_VOICED, PLACE_VELAR, TYPE_NASAL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_UVULAR_NASAL = new Phoneme("ɴ", VOICING_VOICED, PLACE_UVULAR, TYPE_NASAL, CATEGORY_CONSONANT);


	// Trills
	public static final Phoneme VOICED_BILABIAL_TRILL = new Phoneme("ʙ", VOICING_VOICED, PLACE_BILABIAL, TYPE_TRILL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_TRILL = new Phoneme("r", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_TRILL, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_UVULAR_TRILL = new Phoneme("ʀ", VOICING_VOICED, PLACE_UVULAR, TYPE_TRILL, CATEGORY_CONSONANT);


	// Flaps
	public static final Phoneme VOICED_LABIODENTAL_FLAP = new Phoneme("ⱱ", VOICING_VOICED, PLACE_LABIODENTAL, TYPE_FLAP, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_FLAP = new Phoneme("ɾ", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_FLAP, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_FLAP = new Phoneme("ɽ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_FLAP, CATEGORY_CONSONANT);


	// Fricatives
	public static final Phoneme VOICELESS_BILABIAL_FRICATIVE = new Phoneme("ɸ", VOICING_VOICELESS, PLACE_BILABIAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_BILABIAL_FRICATIVE = new Phoneme("β", VOICING_VOICED, PLACE_BILABIAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_LABIODENTAL_FRICATIVE = new Phoneme("f", VOICING_VOICELESS, PLACE_LABIODENTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_LABIODENTAL_FRICATIVE = new Phoneme("v", VOICING_VOICED, PLACE_LABIODENTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_DENTAL_FRICATIVE = new Phoneme("θ", VOICING_VOICELESS, PLACE_DENTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_DENTAL_FRICATIVE = new Phoneme("ð", VOICING_VOICED, PLACE_DENTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_ALVEOLAR_FRICATIVE = new Phoneme("s", VOICING_VOICELESS, PLACE_ALVEOLAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_FRICATIVE = new Phoneme("z", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_POST_ALVEOLAR_FRICATIVE = new Phoneme("ʃ", VOICING_VOICELESS, PLACE_POST_ALVEOLAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_POST_ALVEOLAR_FRICATIVE = new Phoneme("ʒ", VOICING_VOICED, PLACE_POST_ALVEOLAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_RETROFLEX_FRICATIVE = new Phoneme("ʂ", VOICING_VOICELESS, PLACE_RETROFLEX, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_FRICATIVE = new Phoneme("ʐ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_PALATAL_FRICATIVE = new Phoneme("ç", VOICING_VOICELESS, PLACE_PALATAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATAL_FRICATIVE = new Phoneme("ʝ", VOICING_VOICED, PLACE_PALATAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_VELAR_FRICATIVE = new Phoneme("x", VOICING_VOICELESS, PLACE_VELAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_VELAR_FRICATIVE = new Phoneme("ɣ", VOICING_VOICED, PLACE_VELAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_UVULAR_FRICATIVE = new Phoneme("χ", VOICING_VOICELESS, PLACE_UVULAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_UVULAR_FRICATIVE = new Phoneme("ʁ", VOICING_VOICED, PLACE_UVULAR, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_PHARYNGEAL_FRICATIVE = new Phoneme("ħ", VOICING_VOICELESS, PLACE_PHARYNGEAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PHARYNGEAL_FRICATIVE = new Phoneme("ʕ", VOICING_VOICED, PLACE_PHARYNGEAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_GLOTTAL_FRICATIVE = new Phoneme("h", VOICING_VOICELESS, PLACE_GLOTTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_GLOTTAL_FRICATIVE = new Phoneme("ɦ", VOICING_VOICED, PLACE_GLOTTAL, TYPE_FRICATIVE, CATEGORY_CONSONANT);


	// Lateral Fricatives
	public static final Phoneme VOICELESS_ALVEOLAR_LATERAL_FRICATIVE = new Phoneme("ɬ", VOICING_VOICELESS, PLACE_ALVEOLAR, TYPE_LATERAL_FRICATIVE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_FRICATIVE = new Phoneme("ɮ", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_LATERAL_FRICATIVE, CATEGORY_CONSONANT);


	// Approximates
	public static final Phoneme VOICED_LABIODENTAL_APPROXIMATE = new Phoneme("ʋ", VOICING_VOICED, PLACE_LABIODENTAL, TYPE_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_APPROXIMATE = new Phoneme("ɹ", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_APPROXIMATE = new Phoneme("ɻ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATAL_APPROXIMATE = new Phoneme("j", VOICING_VOICED, PLACE_PALATAL, TYPE_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_VELAR_APPROXIMATE = new Phoneme("ɰ", VOICING_VOICED, PLACE_VELAR, TYPE_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_LABIOVELAR_APPROXIXIMATE = new Phoneme("w", VOICING_VOICED, PLACE_LABIOVELAR, TYPE_APPROXIMATE, CATEGORY_CONSONANT);


	// Lateral Approximates
	public static final Phoneme VOICED_ALVEOLAR_LATERAL_APPROXIMATE = new Phoneme("l", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_LATERAL_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_LATERAL_APPROXIMATE = new Phoneme("ɭ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_LATERAL_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATAL_LATERAL_APPROXIMATE = new Phoneme("ʎ", VOICING_VOICED, PLACE_PALATAL, TYPE_LATERAL_APPROXIMATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_VELAR_LATERAL_APPROXIMATE = new Phoneme("ʟ", VOICING_VOICED, PLACE_VELAR, TYPE_LATERAL_APPROXIMATE, CATEGORY_CONSONANT);


	// Affricates
	public static final Phoneme VOICELESS_ALVEOLAR_AFFRICATE = new Phoneme("t͡s", VOICING_VOICELESS, PLACE_ALVEOLAR, TYPE_AFFRICATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLAR_AFFRICATE = new Phoneme("d͡z", VOICING_VOICED, PLACE_ALVEOLAR, TYPE_AFFRICATE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_PALATO_ALVEOLAR_AFFRICATE = new Phoneme("t͡ʃ", VOICING_VOICELESS, PLACE_PALATO_ALVEOLAR, TYPE_AFFRICATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_PALATO_ALVEOLAR_AFFRICATE = new Phoneme("d͡ʒ", VOICING_VOICED, PLACE_PALATO_ALVEOLAR, TYPE_AFFRICATE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_ALVEOLO_PALATAL_AFFRICATE = new Phoneme("t͡ɕ", VOICING_VOICELESS, PLACE_ALVEOLO_PALATAL, TYPE_AFFRICATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_ALVEOLO_PALATAL_AFFRICATE = new Phoneme("d͡ʑ", VOICING_VOICED, PLACE_ALVEOLO_PALATAL, TYPE_AFFRICATE, CATEGORY_CONSONANT);

	public static final Phoneme VOICELESS_RETROFLEX_AFFRICATE = new Phoneme("ʈ͡ʂ", VOICING_VOICELESS, PLACE_RETROFLEX, TYPE_AFFRICATE, CATEGORY_CONSONANT);
	public static final Phoneme VOICED_RETROFLEX_AFFRICATE = new Phoneme("ɖ͡ʐ", VOICING_VOICED, PLACE_RETROFLEX, TYPE_AFFRICATE, CATEGORY_CONSONANT);

	
}