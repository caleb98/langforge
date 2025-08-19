package net.calebscode.langforge.phonology.phoneme;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhonemeSequenceRenderer {

	private PhonemeRepresentationMapper phonemeMapper;

	public PhonemeSequenceRenderer(PhonemeRepresentationMapper phonemeMapper) {
		this.phonemeMapper = phonemeMapper;
	}

	public String render(PhonemeSequence sequence) {
		return sequence
			.getPhonemes()
			.stream()
			.map(phonemeMapper::getRepresentation)
			.collect(Collectors.joining());
	}

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

				if (context.isWordStart()) {
					return "#" + phonemeMapper.getRepresentation(phoneme);
				} else if (context.isSyllableStart()) {
					return "." + phonemeMapper.getRepresentation(phoneme);
				} else {
					return phonemeMapper.getRepresentation(phoneme);
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
