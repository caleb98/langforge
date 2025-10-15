package net.calebscode.langforge.phonology.phoneme;

import java.util.Set;

/// This class contains [String] constants that represent standard phoneme feature names and values.
/// These constants are for ease of use and consistency for code which operates on standard IPA
/// phonemes. When working with novel features or feature values, you may want to provide your own
/// [String] constants.
public class StandardPhonemeFeatures {

	/*
	 * Feature Name Constants
	 */
	
	/// The category feature.
	/// Represents the category of a phoneme, such as "consonant" or "vowel".
	public static final String CATEGORY = "category";
	
	/// The voicing feature.
	/// Represents the voicing of a phoneme, such as "voiced" or "unvoiced".
	public static final String VOICING = "voicing";
	
	/// The place feature.
	/// Represents the place of a consonant, such as "bilabial" or "velar".
	public static final String PLACE = "place";
	
	/// The type feature.
	/// Represents the type of a consonant, such as "plosive" or "fricative".
	public static final String TYPE = "type";
	
	/// The openness feature.
	/// Represents the openness of a vowel, such as "open" or "closed".
	public static final String OPENNESS = "openness";
	
	/// The backness feature.
	/// Represents the backness of a vowel, such as "front" or "back".
	public static final String BACKNESS = "backness";
	
	/// The roundness feature.
	/// Represents the roundness of a vowel, such as "rounded" or "unrounded".
	public static final String ROUNDEDNESS = "roundedness";

	/// A constant set of all standard feature names. Includes:
	/// 1. [StandardPhonemeFeatures#CATEGORY]
	/// 1. [StandardPhonemeFeatures#VOICING]
	/// 1. [StandardPhonemeFeatures#PLACE]
	/// 1. [StandardPhonemeFeatures#TYPE]
	/// 1. [StandardPhonemeFeatures#OPENNESS]
	/// 1. [StandardPhonemeFeatures#BACKNESS]
	/// 1. [StandardPhonemeFeatures#ROUNDNESS]
	public static final Set<String> STANDARD_FEATURE_NAMES =
			Set.of(CATEGORY, VOICING, PLACE, TYPE, OPENNESS, BACKNESS, ROUNDEDNESS);

	/*
	 * Category Feature Values
	 */
	
	/// The consonant value for the [StandardPhonemeFeatures#CATEGORY] feature.
	public static final String CATEGORY_CONSONANT = "consonant";

	/// The vowel value for the [StandardPhonemeFeatures#CATEGORY] feature.
	public static final String CATEGORY_VOWEL = "vowel";

	/// A constant set of all standard values for the [StandardPhonemeFeatures#CATEGORY] feature.
	/// Includes:
	/// 1. [StandardPhonemeFeatures#CATEGORY_CONSONANT]
	/// 1. [StandardPhonemeFeatures#CATEGORY_VOWEL]
	public static final Set<String> STANDARD_CATEGORIES =
			Set.of(CATEGORY_CONSONANT, CATEGORY_VOWEL);

	/*
	 * Voicing Feature Values
	 */
	
	/// The voiced value for the [StandardPhonemeFeatures#VOICING] feature.
	public static final String VOICING_VOICED = "voiced";
	
	/// The voiceless value for the [StandardPhonemeFeatures#VOICING] feature.
	public static final String VOICING_VOICELESS = "voiceless";

	/// A constant set of all standard value for the [StandardPhonemeFeatures#VOICING] feature.
	/// Includes:
	/// 1. [StandardPhonemeFeatures#VOICING_VOICED]
	/// 1. [StandardPhonemeFeatures#VOICING_VOICELESS]
	public static final Set<String> STANDARD_VOICINGS =
			Set.of(VOICING_VOICED, VOICING_VOICELESS);

	/*
	 * Place Feature Values
	 */
	
	/// The bilabial value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_BILABIAL = "bilabial";
	
	/// The labiodental value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_LABIODENTAL = "labio-dental";
	
	/// The labiovelar value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_LABIOVELAR = "labio-velar";
	
	/// The dental value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_DENTAL = "dental";
	
	/// The alveolar value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_ALVEOLAR = "alveolar";
	
	/// The post-alveolar value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_POST_ALVEOLAR = "post-alveolar";
	
	/// The palato-alveolar value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_PALATO_ALVEOLAR = "palato-alveolar";
	
	/// The alveolo-palatal value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_ALVEOLO_PALATAL = "alveolo-palatal";
	
	/// The retroflex value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_RETROFLEX = "retroflex";
	
	/// The palatal value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_PALATAL = "palatal";
	
	/// The velar value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_VELAR = "velar";
	
	/// The uvular value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_UVULAR = "uvular";
	
	/// The pharyngeal value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_PHARYNGEAL = "pharyngeal";
	
	/// The glottal value for the [StandardPhonemeFeatures#PLACE] feature.
	public static final String PLACE_GLOTTAL = "glottal";

	/// A constant set of all standard values for the [StandardPhonemeFeatures#PLACE] feature.
	/// Includes:
	/// 1. [StandardPhonemeFeatures#PLACE_BILABIAL]
	/// 1. [StandardPhonemeFeatures#PLACE_LABIODENTAL]
	/// 1. [StandardPhonemeFeatures#PLACE_LABIOVELAR]
	/// 1. [StandardPhonemeFeatures#PLACE_DENTAL]
	/// 1. [StandardPhonemeFeatures#PLACE_ALVEOLAR]
	/// 1. [StandardPhonemeFeatures#PLACE_POST_ALVEOLAR]
	/// 1. [StandardPhonemeFeatures#PLACE_PALATO_ALVEOLAR]
	/// 1. [StandardPhonemeFeatures#PLACEALVEOLO_PALATAL]
	/// 1. [StandardPhonemeFeatures#PLACE_RETROFLEX]
	/// 1. [StandardPhonemeFeatures#PLACE_PALATAL]
	/// 1. [StandardPhonemeFeatures#PLACE_VELAR]
	/// 1. [StandardPhonemeFeatures#PLACE_UVULAR]
	/// 1. [StandardPhonemeFeatures#PLACE_PHARYNGEAL]
	/// 1. [StandardPhonemeFeatures#PLACE_GLOTTAL]
	public static final Set<String> STANDARD_PLACES =
			Set.of(PLACE_BILABIAL, PLACE_LABIODENTAL, PLACE_LABIOVELAR, PLACE_DENTAL,
					PLACE_ALVEOLAR, PLACE_POST_ALVEOLAR, PLACE_PALATO_ALVEOLAR,
					PLACE_ALVEOLO_PALATAL, PLACE_RETROFLEX, PLACE_PALATAL, PLACE_VELAR,
					PLACE_UVULAR, PLACE_PHARYNGEAL, PLACE_GLOTTAL);

	/*
	 * Type Feature Values
	 */
	
	/// The plosive value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_PLOSIVE = "plosive";
	
	/// The nasal value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_NASAL = "nasal";
	
	/// The trill value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_TRILL = "trill";
	
	/// The flap value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_FLAP = "flap";
	
	/// The fricative value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_FRICATIVE = "fricative";
	
	/// The affricate value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_AFFRICATE = "affricate";
	
	/// The lateral fricative value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_LATERAL_FRICATIVE = "lateral fricative";
	
	/// The approximate value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_APPROXIMATE = "approximate";
	
	/// The lateral approximate value for the [StandardPhonemeFeatures#TYPE] feature.
	public static final String TYPE_LATERAL_APPROXIMATE = "lateral approximate";

	/// A constant set of all the standard values for the [StandardPhonemeFeatures#TYPE] feature.
	/// Includes:
	/// 1. [StandardPhonemeFeatures#TYPE_PLOSIVE]
	/// 1. [StandardPhonemeFeatures#TYPE_NASAL]
	/// 1. [StandardPhonemeFeatures#TYPE_TRILL]
	/// 1. [StandardPhonemeFeatures#TYPE_FLAP]
	/// 1. [StandardPhonemeFeatures#TYPE_FRICATIVE]
	/// 1. [StandardPhonemeFeatures#TYPE_AFFRICATE]
	/// 1. [StandardPhonemeFeatures#TYPE_LATERAL_FRICATIVE]
	/// 1. [StandardPhonemeFeatures#TYPE_APPROXIMATE]
	/// 1. [StandardPhonemeFeatures#TYPE_LATERAL_APPROXIMATE]
	public static final Set<String> STANDARD_TYPES =
			Set.of(TYPE_PLOSIVE, TYPE_NASAL, TYPE_TRILL, TYPE_FLAP, TYPE_FRICATIVE,
					TYPE_AFFRICATE, TYPE_LATERAL_FRICATIVE, TYPE_APPROXIMATE,
					TYPE_LATERAL_APPROXIMATE);

	/*
	 * Openness Feature Values
	 */
	
	/// The close value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_CLOSE = "close";
	
	/// The near-close value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_NEAR_CLOSE = "near-close";
	
	/// The close-mid value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_CLOSE_MID = "close-mid";

	/// The mid value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_MID = "mid";
	
	/// The open-mid value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_OPEN_MID = "open-mid";
	
	/// The near-open value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_NEAR_OPEN = "near-open";
	
	/// The open value for the [StandardPhonemeFeatures#OPENNESS] feature.
	public static final String OPENNESS_OPEN = "open";

	/// A constant set of all the standard values for the [StandardPhonemeFeatures#OPENNESS]
	/// feature. Includes:
	/// 1. [StandardPhonemeFeatures#OPENNESS_CLOSE]
	/// 1. [StandardPhonemeFeatures#OPENNESS_NEAR_CLOSE]
	/// 1. [StandardPhonemeFeatures#OPENNESS_CLOSE_MID]
	/// 1. [StandardPhonemeFeatures#OPENNESS_MID]
	/// 1. [StandardPhonemeFeatures#OPENNESS_OPEN_MID]
	/// 1. [StandardPhonemeFeatures#OPENNESS_NEAR_OPEN]
	/// 1. [StandardPhonemeFeatures#OPENNESS_OPEN]
	public static final Set<String> STANDARD_OPENNESSES =
			Set.of(OPENNESS_CLOSE, OPENNESS_NEAR_CLOSE, OPENNESS_CLOSE_MID, OPENNESS_MID,
					OPENNESS_OPEN_MID, OPENNESS_NEAR_OPEN, OPENNESS_OPEN);

	/*
	 * Backness Feature Values
	 */
	
	/// The front value for the [StandardPhonemeFeatures#BACKNESS] feature.
	public static final String BACKNESS_FRONT = "front";
	
	/// The central value for the [StandardPhonemeFeatures#BACKNESS] feature.
	public static final String BACKNESS_CENTRAL = "central";

	/// The back value for the [StandardPhonemeFeatures#BACKNESS] feature.
	public static final String BACKNESS_BACK = "back";

	/// A constant set of all the standard values for the [StandardPhonemeFeatures#BACKNESS]
	/// feature. Includes:
	/// 1. [StandardPhonemeFeatures#BACKNESS_FRONT]
	/// 1. [StandardPhonemeFeatures#BACKNESS_CENTRAL]
	/// 1. [StandardPhonemeFeatures#BACKNESS_BACK]
	public static final Set<String> STANDARD_BACKNESSES =
			Set.of(BACKNESS_FRONT, BACKNESS_CENTRAL, BACKNESS_BACK);

	/*
	 * Roundedness Feature Values
	 */
	
	/// The unrounded value for the [StandardPhonemeFeatures#ROUNDEDNESS] feature.
	public static final String ROUNDEDNESS_UNROUNDED = "unrounded";

	/// The rounded value for the [StandardPhonemeFeatures#ROUNDEDNESS] feature.
	public static final String ROUNDEDNESS_ROUNDED = "rounded";

	/// A constant set of all the standard values for the [StandardPhonemeFeatures#ROUNDEDNESS]
	/// feature. Includes:
	/// 1. [StandardPhonemeFeatures#ROUNDEDNESS_UNROUNDED]
	/// 1. [StandardPhonemeFeatures#ROUNDEDNESS_ROUNDED]
	public static final Set<String> STANDARD_ROUNDEDNESSES =
			Set.of(ROUNDEDNESS_UNROUNDED, ROUNDEDNESS_ROUNDED);

}
