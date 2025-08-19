package net.calebscode.langforge.phonology.syllable;

import java.util.Set;

public class SyllableUtils {

	private static final SyllablePatternCompiler PATTERN_COMPILER = new SyllablePatternCompiler();

	private SyllableUtils() {}

	public static Set<String> expandSyllablePatterns(String pattern) {
		return PATTERN_COMPILER.compile(pattern);
	}

}
