package net.calebscode.langtool.phonology.phoneme;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedyPhonemeParser {

	private int maxPhonemeLength = 0;
	private Map<String, Phoneme> registeredPhonemes = new HashMap<>();

	public void registerPhoneme(Phoneme phoneme) {
		String repr = phoneme.representation();
		if (registeredPhonemes.containsKey(repr)) {
			throw new RuntimeException("Cannot register two phonemes with the same representation.");
		}

		registeredPhonemes.put(repr, phoneme);

		if (repr.length() > maxPhonemeLength) {
			maxPhonemeLength = repr.length();
		}
	}

	public void registerPhoneme(Phoneme... phonemes) {
		for (var phoneme : phonemes) {
			registerPhoneme(phoneme);
		}
	}

	public List<Phoneme> parse(String raw) {
		int start = 0;
		int end = 1;

		var parsed = new ArrayList<Phoneme>();
		String test;
		String currentMatch;
		while (start < raw.length()) {
			currentMatch = null;

			while (end - start <= maxPhonemeLength && end <= raw.length()) {
				test = raw.substring(start, end);
				if (registeredPhonemes.containsKey(test)) {
					currentMatch = test;
				}
				end++;
			}

			if (currentMatch == null) {
				throw new RuntimeException(
						String.format("Error parsing phoneme string at character %d: %s", start, raw));
			}

			start += currentMatch.length();
			end = start + 1;
			parsed.add(registeredPhonemes.get(currentMatch));
		}

		return parsed;
	}

	public static GreedyPhonemeParser createGreedyIPAParser() {
		GreedyPhonemeParser ipaParser = new GreedyPhonemeParser();
		ipaParser.registerPhoneme(new Phoneme[] { CLOSE_FRONT_UNROUNDED_VOWEL, CLOSE_FRONT_ROUNDED_VOWEL,
				CLOSE_CENTRAL_UNROUNDED_VOWEL, CLOSE_CENTRAL_ROUNDED_VOWEL, CLOSE_BRACK_UNROUNDED_VOWEL,
				CLOSE_BRACK_ROUNDED_VOWEL, NEAR_CLOSE_FRONT_UNROUNDED_VOWEL, NEAR_CLOSE_FRONT_ROUNDED_VOWEL,
				NEAR_CLOSE_BRACK_ROUNDED_VOWEL, CLOSE_MID_FRONT_UNROUNDED_VOWEL, CLOSE_MID_FRONT_ROUNDED_VOWEL,
				CLOSE_MID_CENTRAL_UNROUNDED_VOWEL, CLOSE_MID_CENTRAL_ROUNDED_VOWEL, CLOSE_MID_BRACK_UNROUNDED_VOWEL,
				CLOSE_MID_BRACK_ROUNDED_VOWEL, MID_FRONT_UNROUNDED_VOWEL, MID_FRONT_ROUNDED_VOWEL, MID_CENTRAL_VOWEL,
				MID_BRACK_UNROUNDED_VOWEL, MID_BRACK_ROUNDED_VOWEL, OPEN_MID_FRONT_UNROUNDED_VOWEL,
				OPEN_MID_FRONT_ROUNDED_VOWEL, OPEN_MID_CENTRAL_UNROUNDED_VOWEL, OPEN_MID_CENTRAL_ROUNDED_VOWEL,
				OPEN_MID_BRACK_UNROUNDED_VOWEL, OPEN_MID_BRACK_ROUNDED_VOWEL, NEAR_OPEN_FRONT_UNROUNDED_VOWEL,
				NEAR_OPEN_CENTRAL_VOWEL, OPEN_FRONT_UNROUNDED_VOWEL, OPEN_FRONT_ROUNDED_VOWEL,
				OPEN_CENTRAL_UNROUNDED_VOWEL, OPEN_BRACK_UNROUNDED_VOWEL, OPEN_BRACK_ROUNDED_VOWEL,
				VOICELESS_BILABIAL_PLOSIVE, VOICED_BILABIAL_PLOSIVE, VOICELESS_DENTAL_PLOSIVE, VOICED_DENTAL_PLOSIVE,
				VOICELESS_ALVEOLAR_PLOSIVE, VOICED_ALVEOLAR_PLOSIVE, VOICELESS_RETROFLEX_PLOSIVE,
				VOICED_RETROFLEX_PLOSIVE, VOICELESS_PALATAL_PLOSIVE, VOICED_PALATAL_PLOSIVE, VOICELESS_VELAR_PLOSIVE,
				VOICED_VELAR_PLOSIVE, VOICELESS_UVULAR_PLOSIVE, VOICED_UVULAR_PLOSIVE, VOICELESS_GLOTTAL_PLOSIVE,
				VOICED_BILABIAL_NASAL, VOICED_LABIODENTAL_NASAL, VOICED_ALVEOLAR_NASAL, VOICED_RETROFLEX_NASAL,
				VOICED_PALATAL_NASAL, VOICED_VELAR_NASAL, VOICED_UVULAR_NASAL, VOICED_BILABIAL_TRILL,
				VOICED_ALVEOLAR_TRILL, VOICED_UVULAR_TRILL, VOICED_LABIODENTAL_FLAP, VOICED_ALVEOLAR_FLAP,
				VOICED_RETROFLEX_FLAP, VOICELESS_BILABIAL_FRICATIVE, VOICED_BILABIAL_FRICATIVE,
				VOICELESS_LABIODENTAL_FRICATIVE, VOICED_LABIODENTAL_FRICATIVE, VOICELESS_DENTAL_FRICATIVE,
				VOICED_DENTAL_FRICATIVE, VOICELESS_ALVEOLAR_FRICATIVE, VOICED_ALVEOLAR_FRICATIVE,
				VOICELESS_POST_ALVEOLAR_FRICATIVE, VOICED_POST_ALVEOLAR_FRICATIVE, VOICELESS_RETROFLEX_FRICATIVE,
				VOICED_RETROFLEX_FRICATIVE, VOICELESS_PALATAL_FRICATIVE, VOICED_PALATAL_FRICATIVE,
				VOICELESS_VELAR_FRICATIVE, VOICED_VELAR_FRICATIVE, VOICELESS_UVULAR_FRICATIVE, VOICED_UVULAR_FRICATIVE,
				VOICELESS_PHARYNGEAL_FRICATIVE, VOICED_PHARYNGEAL_FRICATIVE, VOICELESS_GLOTTAL_FRICATIVE,
				VOICED_GLOTTAL_FRICATIVE, VOICELESS_ALVEOLAR_LATERAL_FRICATIVE, VOICED_ALVEOLAR_LATERAL_FRICATIVE,
				VOICED_LABIODENTAL_APPROXIMATE, VOICED_ALVEOLAR_APPROXIMATE, VOICED_RETROFLEX_APPROXIMATE,
				VOICED_PALATAL_APPROXIMATE, VOICED_VELAR_APPROXIMATE, VOICED_LABIOVELAR_APPROXIXIMATE,
				VOICED_ALVEOLAR_LATERAL_APPROXIMATE, VOICED_RETROFLEX_LATERAL_APPROXIMATE,
				VOICED_PALATAL_LATERAL_APPROXIMATE, VOICED_VELAR_LATERAL_APPROXIMATE, VOICELESS_ALVEOLAR_AFFRICATE,
				VOICED_ALVEOLAR_AFFRICATE, VOICELESS_PALATO_ALVEOLAR_AFFRICATE, VOICED_PALATO_ALVEOLAR_AFFRICATE,
				VOICELESS_ALVEOLO_PALATAL_AFFRICATE, VOICED_ALVEOLO_PALATAL_AFFRICATE, VOICELESS_RETROFLEX_AFFRICATE,
				VOICED_RETROFLEX_AFFRICATE });

		return ipaParser;
	}

}
