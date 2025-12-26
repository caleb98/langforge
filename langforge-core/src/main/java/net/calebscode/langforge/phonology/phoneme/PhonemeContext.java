package net.calebscode.langforge.phonology.phoneme;

/// Represents contextual information about a single phoneme.
public record PhonemeContext(
	boolean isSyllableStart,
	boolean isSyllableEnd,
	boolean isWordStart,
	boolean isWordEnd
) {}
