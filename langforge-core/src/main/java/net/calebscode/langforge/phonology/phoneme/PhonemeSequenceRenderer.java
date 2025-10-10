package net.calebscode.langforge.phonology.phoneme;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// Renders [PhonemeSequence]s into [String] representations using a [PhonemeRepresentationMapper].
/// This class exists to support different rendering options such as rendering phonemes only, or
/// rendering with additional contextual markings such as word/syllable boundaries.
public class PhonemeSequenceRenderer {

	private PhonemeRepresentationMapper phonemeMapper;

	/// Creates a new [PhonemeSequenceRenderer].
	/// @param phonemeMapper the [PhonemeRepresentationMapper] that this [PhonemeSequenceRenderer]
	/// will use to convert [PhonemeSequence]s into [String]s.
	public PhonemeSequenceRenderer(PhonemeRepresentationMapper phonemeMapper) {
		this.phonemeMapper = phonemeMapper;
	}

	/// Renders a given [PhonemeSequence] as a [String].
	/// @param sequence the sequence to render
	/// @return a [String] representation for the entire sequence
	public String render(PhonemeSequence sequence) {
		return sequence
			.getPhonemes()
			.stream()
			.map(phonemeMapper::getRepresentation)
			.map(s -> s.orElse("?"))
			.collect(Collectors.joining());
	}

	/// Renders a given [PhonemeSequence] as a [String] with additional contextual markings for word
	/// and syllable boundaries. Syllable boundaries are indicated using a period `.` character, and
	/// word boundaries are indicated with a pound `#` character.
	/// @param sequence the sequence to render
	/// @return a [String] representation for the entire sequence, including additional contextual
	/// characters
	public String renderWithContext(PhonemeSequence sequence) {
		if (!sequence.hasContexts()) {
			return render(sequence);
		}

		var phonemes = sequence.getPhonemes();
		var contexts = sequence.getPhonemeContexts();

		if (phonemes.size() == 0) {
			return "";
		}

		var stringValue = IntStream.range(0, phonemes.size())
			.mapToObj(index -> {
				var phoneme = phonemes.get(index);
				var context = contexts.get(index);
				
				var representation = phonemeMapper.getRepresentation(phoneme).orElse("?");

				if (context.isWordStart()) {
					return "#" + representation;
				} else if (context.isSyllableStart()) {
					return "." + representation;
				} else {
					return representation;
				}
			})
			.collect(Collectors.joining());

		var lastMeta = contexts.getLast();

		if (lastMeta.isWordEnd()) {
			return stringValue + "#";
		}
		else if (lastMeta.isSyllableEnd()) {
			return stringValue + ".";
		}
		else {
			return stringValue;
		}
	}

}
