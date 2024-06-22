package net.calebscode.langtool.phonology;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.syllable.SyllablePattern;

public class PhonemeSequenceValidator {

	public Optional<PhonemeSequence> revalidate(SyllablePattern... patterns) {
		var allPatterns = Arrays.stream(patterns)
			.flatMap(pattern -> pattern.allPatterns().stream())
			.collect(Collectors.toSet());

		return Optional.ofNullable(null);
	}

}
