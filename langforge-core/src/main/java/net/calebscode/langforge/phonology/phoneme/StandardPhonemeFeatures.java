package net.calebscode.langforge.phonology.phoneme;

import java.util.Set;

public class StandardPhonemeFeatures {

	// Feature Names
	public static final String CATEGORY = "category";
	public static final String VOICING = "voicing";
	public static final String PLACE = "place";
	public static final String TYPE = "type";
	public static final String OPENNESS = "openness";
	public static final String BACKNESS = "backness";
	public static final String ROUNDEDNESS = "roundedness";

	public static final Set<String> STANDARD_FEATURE_NAMES =
			Set.of(CATEGORY, VOICING, PLACE, TYPE, OPENNESS, BACKNESS, ROUNDEDNESS);

	// Phoneme Category Features
	public static final String CATEGORY_CONSONANT = "consonant";
	public static final String CATEGORY_VOWEL = "vowel";

	public static final Set<String> STANDARD_CATEGORIES =
			Set.of(CATEGORY_CONSONANT, CATEGORY_VOWEL);

	// Voicing Features
	public static final String VOICING_VOICED = "voiced";
	public static final String VOICING_VOICELESS = "voiceless";

	public static final Set<String> STANDARD_VOICINGS =
			Set.of(VOICING_VOICED, VOICING_VOICELESS);

	// Place of Articulation Features
	public static final String PLACE_BILABIAL = "bilabial";
	public static final String PLACE_LABIODENTAL = "labio-dental";
	public static final String PLACE_LABIOVELAR = "labio-velar";
	public static final String PLACE_DENTAL = "dental";
	public static final String PLACE_ALVEOLAR = "alveolar";
	public static final String PLACE_POST_ALVEOLAR = "post-alveolar";
	public static final String PLACE_PALATO_ALVEOLAR = "palato-alveolar";
	public static final String PLACE_ALVEOLO_PALATAL = "alveolo-palatal";
	public static final String PLACE_RETROFLEX = "retroflex";
	public static final String PLACE_PALATAL = "palatal";
	public static final String PLACE_VELAR = "velar";
	public static final String PLACE_UVULAR = "uvular";
	public static final String PLACE_PHARYNGEAL = "pharyngeal";
	public static final String PLACE_GLOTTAL = "glottal";

	public static final Set<String> STANDARD_PLACES =
			Set.of(PLACE_BILABIAL, PLACE_LABIODENTAL, PLACE_LABIOVELAR, PLACE_DENTAL,
					PLACE_ALVEOLAR, PLACE_POST_ALVEOLAR, PLACE_PALATO_ALVEOLAR,
					PLACE_ALVEOLO_PALATAL, PLACE_RETROFLEX, PLACE_PALATAL, PLACE_VELAR,
					PLACE_UVULAR, PLACE_PHARYNGEAL, PLACE_GLOTTAL);

	// Type of Articulation Features
	public static final String TYPE_PLOSIVE = "plosive";
	public static final String TYPE_NASAL = "nasal";
	public static final String TYPE_TRILL = "trill";
	public static final String TYPE_FLAP = "flap";
	public static final String TYPE_FRICATIVE = "fricative";
	public static final String TYPE_AFFRICATE = "affricate";
	public static final String TYPE_LATERAL_FRICATIVE = "lateral fricative";
	public static final String TYPE_APPROXIMATE = "approximate";
	public static final String TYPE_LATERAL_APPROXIMATE = "lateral approximate";

	public static final Set<String> STANDARD_TYPES =
			Set.of(TYPE_PLOSIVE, TYPE_NASAL, TYPE_TRILL, TYPE_FLAP, TYPE_FRICATIVE,
					TYPE_AFFRICATE, TYPE_LATERAL_FRICATIVE, TYPE_APPROXIMATE,
					TYPE_LATERAL_APPROXIMATE);

	// Openness Features
	public static final String OPENNESS_CLOSE = "close";
	public static final String OPENNESS_NEAR_CLOSE = "near-close";
	public static final String OPENNESS_CLOSE_MID = "close-mid";
	public static final String OPENNESS_MID = "mid";
	public static final String OPENNESS_OPEN_MID = "open-mid";
	public static final String OPENNESS_NEAR_OPEN = "near-open";
	public static final String OPENNESS_OPEN = "open";

	public static final Set<String> STANDARD_OPENNESSES =
			Set.of(OPENNESS_CLOSE, OPENNESS_NEAR_CLOSE, OPENNESS_CLOSE_MID, OPENNESS_MID,
					OPENNESS_OPEN_MID, OPENNESS_NEAR_OPEN, OPENNESS_OPEN);

	// Backness Features
	public static final String BACKNESS_FRONT = "front";
	public static final String BACKNESS_CENTRAL = "central";
	public static final String BACKNESS_BACK = "back";

	public static final Set<String> STANDARD_BACKNESSES =
			Set.of(BACKNESS_FRONT, BACKNESS_CENTRAL, BACKNESS_BACK);

	// Roundedness Features
	public static final String ROUNDEDNESS_UNROUNDED = "unrounded";
	public static final String ROUNDEDNESS_ROUNDED = "rounded";

	public static final Set<String> STANDARD_ROUNDEDNESSES =
			Set.of(ROUNDEDNESS_UNROUNDED, ROUNDEDNESS_ROUNDED);

}
