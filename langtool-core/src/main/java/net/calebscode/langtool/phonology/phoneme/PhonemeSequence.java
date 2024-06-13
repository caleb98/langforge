package net.calebscode.langtool.phonology.phoneme;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhonemeSequence {

	public static final PhonemeSequence EMPTY = new PhonemeSequence();

	private Phoneme[] phonemes;
	private PhonemeMetadata[] metadata;

	public PhonemeSequence() {
		phonemes = new Phoneme[0];
		metadata = new PhonemeMetadata[0];
	}

	public PhonemeSequence(Phoneme[] phonemes, PhonemeMetadata[] metadata) {
		this.phonemes = phonemes;
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		if (phonemes.length == 0) {
			return "";
		}

		var stringValue = IntStream.range(0, phonemes.length)
			.mapToObj(index -> {
				var phoneme = phonemes[index];
				var meta = metadata[index];

				if (meta.isWordStart) {
					return "#" + phoneme.representation();
				} else if (meta.isSyllableStart) {
					return "." + phoneme.representation();
				} else {
					return phoneme.representation();
				}
			})
			.collect(Collectors.joining());

		var lastMeta = metadata[metadata.length - 1];
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

	public record PhonemeMetadata(
		boolean isSyllableStart,
		boolean isSyllableEnd,
		boolean isWordStart,
		boolean isWordEnd
	) {

		private PhonemeMetadata() {
			this(false, false, false, false);
		}

	}

	public record PhonemeTransition(
		boolean crossedWordBoundary,
		boolean crossedSyllableBoundary,
		boolean crossedSequenceBoundary
	) {}

	public class PhonemeSequenceReader {

		private int position;

		private PhonemeSequenceReader() {
			this(0);
		}

		private PhonemeSequenceReader(int position) {
			this.position = position;
		}

		public Optional<Phoneme> current() {
			return Optional.ofNullable(position < 0 || position >= phonemes.length ? null : phonemes[position]);
		}

		public PhonemeMetadata currentMetadata() {
			return position < 0 || position >= phonemes.length ? new PhonemeMetadata() : metadata[position];
		}

		public PhonemeTransition prev() {
			var fromPhoneme = current();
			var fromMeta = currentMetadata();

			position--;

			var toPhoneme = current();
			var toMeta = currentMetadata();

			// If we're moving from an empty position, we're already outside the sequence so
			// there's no need to check specific transition flags.
			if (fromPhoneme.isEmpty()) {
				return new PhonemeTransition(false, false, false);
			}

			boolean crossedWordBoundary = fromMeta.isWordStart && toMeta.isWordEnd;
			boolean crossedSyllableBoundary = fromMeta.isSyllableStart && toMeta.isSyllableEnd;
			boolean crossedSequenceBoundary = toPhoneme.isEmpty();

			return new PhonemeTransition(crossedWordBoundary, crossedSyllableBoundary, crossedSequenceBoundary);
		}

		public PhonemeTransition next() {
			var fromPhoneme = current();
			var fromMeta = currentMetadata();

			position++;

			var toPhoneme = current();
			var toMeta = currentMetadata();

			// If we're moving from an empty position, we're already outside the sequence so
			// there's no need to check specific transition flags.
			if (fromPhoneme.isEmpty()) {
				return new PhonemeTransition(false, false, false);
			}

			boolean crossedWordBoundary = fromMeta.isWordEnd && toMeta.isWordStart;
			boolean crossedSyllableBoundary = fromMeta.isSyllableEnd && toMeta.isSyllableStart;
			boolean crossedSequenceBoundary = toPhoneme.isEmpty();

			return new PhonemeTransition(crossedWordBoundary, crossedSyllableBoundary, crossedSequenceBoundary);
		}

	}

}
