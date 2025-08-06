package net.calebscode.langforge.phonology.syllable;

import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;

public class SyllableUtils {

	private static final RandomGenerator DEFAULT_RANDOM = new Random();
	private static final SyllablePatternCompiler PATTERN_COMPILER = new SyllablePatternCompiler();

	private SyllableUtils() {}

	public static Set<String> expandSyllablePatterns(String pattern) {
		return PATTERN_COMPILER.compile(pattern);
	}

}
