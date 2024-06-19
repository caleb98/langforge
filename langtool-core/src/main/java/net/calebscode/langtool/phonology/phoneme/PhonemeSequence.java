package net.calebscode.langtool.phonology.phoneme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhonemeSequence {

	public static final PhonemeSequence EMPTY = new PhonemeSequence();

	private List<Phoneme> phonemes;
	private List<PhonemeMetadata> metadata;

	public PhonemeSequence() {
		phonemes = new ArrayList<>();
		metadata = new ArrayList<>();
	}

	public PhonemeSequence(List<Phoneme> phonemes, List<PhonemeMetadata> metadata) {
		if (phonemes.size() != metadata.size()) {
			throw new IllegalArgumentException("PhonemeSequence phonemes and metadata must be same size.");
		}

		this.phonemes = phonemes;
		this.metadata = metadata;
	}

	public Phoneme phonemeAt(int position) {
		return phonemes.get(position);
	}

	public int length() {
		return phonemes.size();
	}

	public PhonemeTransition getTransition(int from) {
		if (phonemes.isEmpty()) {
			return new PhonemeTransition(false, false, false);
		}

		int to = from + 1;

		if (from == -1) {
			var toMeta = metadata.get(to);
			return new PhonemeTransition(toMeta.isWordStart, toMeta.isSyllableStart, true);
		}
		else {
			var fromMeta = metadata.get(from);
			return new PhonemeTransition(fromMeta.isWordEnd, fromMeta.isSyllableEnd, from == phonemes.size());
		}
	}

	@Override
	public String toString() {
		if (phonemes.size() == 0) {
			return "";
		}

		var stringValue = IntStream.range(0, phonemes.size())
			.mapToObj(index -> {
				var phoneme = phonemes.get(index);
				var meta = metadata.get(index);

				if (meta.isWordStart) {
					return "#" + phoneme.representation();
				} else if (meta.isSyllableStart) {
					return "." + phoneme.representation();
				} else {
					return phoneme.representation();
				}
			})
			.collect(Collectors.joining());

		var lastMeta = metadata.getLast();

		if (lastMeta.isWordEnd) {
			return stringValue + "#";
		}
		else if (lastMeta.isSyllableEnd) {
			return stringValue + ".";
		}
		else {
			return stringValue;
		}
	}

	public record PhonemeMetadata (
		boolean isSyllableStart,
		boolean isSyllableEnd,
		boolean isWordStart,
		boolean isWordEnd
	) {}

	public record PhonemeTransition (
		boolean crossedWordBoundary,
		boolean crossedSyllableBoundary,
		boolean crossedSequenceBoundary
	) {}

}
