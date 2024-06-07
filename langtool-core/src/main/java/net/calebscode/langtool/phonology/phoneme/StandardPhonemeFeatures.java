package net.calebscode.langtool.phonology.phoneme;

public class StandardPhonemeFeatures {

	public static final String FEATURE_PHONEME_CATEGORY = "category";
	public static final String FEATURE_VOICING = "voicing";
	public static final String FEATURE_ARTICULATION_PLACE = "place of articulation";
	public static final String FEATURE_ARTICULATION_TYPE = "type of articulation";
	public static final String FEATURE_OPENNESS = "openness";
	public static final String FEATURE_BACKNESS = "backness";
	public static final String FEATURE_ROUNDEDNESS = "roundedness";

	// Phoneme Category Features
	public static final PhonemeFeature CATEGORY_CONSONANT = new PhonemeFeature(FEATURE_PHONEME_CATEGORY, "consonant");
	public static final PhonemeFeature CATEGORY_VOWEL = new PhonemeFeature(FEATURE_PHONEME_CATEGORY, "vowel");

	// Voicing Features
	public static final PhonemeFeature VOICING_VOICED = new PhonemeFeature(FEATURE_VOICING, "voiced");
	public static final PhonemeFeature VOICING_VOICELESS = new PhonemeFeature(FEATURE_VOICING, "voiceless");

	// Place of Articulation Features
	public static final PhonemeFeature PLACE_BILABIAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "bilabial");
	public static final PhonemeFeature PLACE_LABIODENTAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "labio-dental");
	public static final PhonemeFeature PLACE_LABIOVELAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "labio-velar");
	public static final PhonemeFeature PLACE_DENTAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "dental");
	public static final PhonemeFeature PLACE_ALVEOLAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "alveolar");
	public static final PhonemeFeature PLACE_POST_ALVEOLAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "post-alveolar");
	public static final PhonemeFeature PLACE_PALATO_ALVEOLAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "palato-alveolar");
	public static final PhonemeFeature PLACE_ALVEOLO_PALATAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "alveolo-palatal");
	public static final PhonemeFeature PLACE_RETROFLEX = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "retroflex");
	public static final PhonemeFeature PLACE_PALATAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "palatal");
	public static final PhonemeFeature PLACE_VELAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "velar");
	public static final PhonemeFeature PLACE_UVULAR = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "uvular");
	public static final PhonemeFeature PLACE_PHARYNGEAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "pharyngeal");
	public static final PhonemeFeature PLACE_GLOTTAL = new PhonemeFeature(FEATURE_ARTICULATION_PLACE, "glottal");

	// Type of Articulation Features
	public static final PhonemeFeature TYPE_PLOSIVE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "plosive");
	public static final PhonemeFeature TYPE_NASAL = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "nasal");
	public static final PhonemeFeature TYPE_TRILL = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "trill");
	public static final PhonemeFeature TYPE_FLAP = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "flap");
	public static final PhonemeFeature TYPE_FRICATIVE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "fricative");
	public static final PhonemeFeature TYPE_AFFRICATE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "affricate");
	public static final PhonemeFeature TYPE_LATERAL_FRICATIVE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "lateral fricative");
	public static final PhonemeFeature TYPE_APPROXIMATE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "approximate");
	public static final PhonemeFeature TYPE_LATERAL_APPROXIMATE = new PhonemeFeature(FEATURE_ARTICULATION_TYPE, "lateral approxiamte");

	// Openness Features
	public static final PhonemeFeature OPENNESS_CLOSE = new PhonemeFeature(FEATURE_OPENNESS, "close");
	public static final PhonemeFeature OPENNESS_NEAR_CLOSE = new PhonemeFeature(FEATURE_OPENNESS, "near-close");
	public static final PhonemeFeature OPENNESS_CLOSE_MID = new PhonemeFeature(FEATURE_OPENNESS, "close-mid");
	public static final PhonemeFeature OPENNESS_MID = new PhonemeFeature(FEATURE_OPENNESS, "mid");
	public static final PhonemeFeature OPENNESS_OPEN_MID = new PhonemeFeature(FEATURE_OPENNESS, "open-mid");
	public static final PhonemeFeature OPENNESS_NEAR_OPEN = new PhonemeFeature(FEATURE_OPENNESS, "near-open");
	public static final PhonemeFeature OPENNESS_OPEN = new PhonemeFeature(FEATURE_OPENNESS, "open");

	// Backness Features
	public static final PhonemeFeature BACKNESS_FRONT = new PhonemeFeature(FEATURE_BACKNESS, "front");
	public static final PhonemeFeature BACKNESS_CENTRAL = new PhonemeFeature(FEATURE_BACKNESS, "central");
	public static final PhonemeFeature BACKNESS_BACK = new PhonemeFeature(FEATURE_BACKNESS, "back");

	// Roundedness Features
	public static final PhonemeFeature ROUNDEDNESS_UNROUNDED = new PhonemeFeature(FEATURE_ROUNDEDNESS, "unrounded");
	public static final PhonemeFeature ROUNDEDNESS_ROUNDED = new PhonemeFeature(FEATURE_ROUNDEDNESS, "rounded");

}
