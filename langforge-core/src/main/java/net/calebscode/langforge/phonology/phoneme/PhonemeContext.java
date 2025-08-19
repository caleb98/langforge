package net.calebscode.langforge.phonology.phoneme;

public record PhonemeContext(
	boolean isSyllableStart,
	boolean isSyllableEnd,
	boolean isWordStart,
	boolean isWordEnd
) {}
