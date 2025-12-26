package net.calebscode.langforge.phonology.phoneme;

import java.util.List;

/// Represents an object which has a natural representation as a sequence of individual [Phoneme]s.
///
/// Implementations of this interface must ensure that the list returned by
/// [PhonemeSequence#getPhonemeContexts] always has the same size as the list returned by
/// [PhonemeSequence#getPhonemes].
public interface PhonemeSequence {

	/// @return the list of [Phoneme]s that constitute this sequence
	public List<Phoneme> getPhonemes();

	/// Gets the list of [PhonemeContext]s for the [Phoneme]s of this sequence, if any.
	///
	/// This function may return an empty list both to indicate that this phoneme sequence is a
	/// sequence of length zero, and to indicate that no contextual information is available.
	/// In any situation where it is important to distinguish between these two cases, the result
	/// of [PhonemeSequence#hasContexts] should be checked first.
	///
	/// The default implementation returns an empty list.
	///
	/// @return the list of [PhonemeContext]s for the [Phoneme]s of this sequence
	public default List<PhonemeContext> getPhonemeContexts() {
		return List.of();
	}

	/// Checks whether or not this phoneme sequence has contextual information on its phonemes.
	///
	/// The default implementation returns `false`.
	///
	/// @return `true` if contextual information is available; `false` otherwise
	public default boolean hasContexts() {
		return false;
	}

}
