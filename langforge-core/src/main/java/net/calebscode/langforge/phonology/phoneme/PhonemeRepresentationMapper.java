package net.calebscode.langforge.phonology.phoneme;

import java.util.Optional;

/// Maps [Phoneme]s to a [String] representation, and vice versa.
/// 
/// All implmentations of [PhonemeRepresentationMapper] must ensure that for any [Phoneme] or
/// [String] *x* which has a valid mapping, the following conditions are true:
///
/// 1. `getIpa(getPhoneme(x)).equals(x)`
/// 1. `getPhoneme(getIpa(x)).equals(x)`
public interface PhonemeRepresentationMapper {

	/// Checks whether or not this mapper is able to map the provided [Phoneme] into a [String].
	/// @param phoneme the [Phoneme] to map
	/// @return `true` if this mapper can produce a [String] representation; `false` otherwise
	public boolean canMap(Phoneme phoneme);

	/// Checks whether or not this mapper is able to map the provided [String] to a single
	/// [Phoneme].
	/// @param string the [String] to map
	/// @return `true` if this mapper can produce a [Phoneme] representation; `false` otherwise
	public boolean canMap(String string);

	/// Gets the [String] representation of a given phoneme. Will return an empty [Optional] if this
	/// mapper cannot map the provided [Phoneme].
	/// @param phoneme the [Phoneme] to map
	/// @return an [Optional] containing the [String] representation; an empty [Optional] if no
	/// mapping is present
	public Optional<String> getRepresentation(Phoneme phoneme);

	/// Gets the [Phoneme] representation for the given [String]. Will return an empty [Optional]
	/// if this mapper cannot map the provided string.
	/// @param string the [String] to map
	/// @return an [Optional] containing the [Phoneme] representation; an empty [Optional] if no
	/// mapping is present
	public Optional<Phoneme> getPhoneme(String string);

}
