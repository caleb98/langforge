package net.calebscode.langtool.phonology.phoneme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.calebscode.langtool.phonology.IpaRenderable;

public class PhonemeSequence implements IpaRenderable {

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

	public List<Phoneme> getPhonemes() {
		return Collections.unmodifiableList(phonemes);
	}

	/**
	 * Returns a new PhonemeSequence with the phoneme at the given index replaced with the new
	 * phoneme. Metadata will be preserved from the phoneme that is already at the given index.
	 * @param index
	 * @param replacementPhoneme
	 * @return
	 */
	public PhonemeSequence replaceAt(int index, Phoneme replacementPhoneme) {
		return replaceAt(index, replacementPhoneme, metadata.get(index));
	}

	/**
	 * Returns a new PhonemeSequence with the phoneme at the given index replaced with the new
	 * phoneme and metadata.
	 * @param index
	 * @param replacementPhoneme
	 * @param replacementMetadata
	 * @return
	 */
	public PhonemeSequence replaceAt(int index, Phoneme replacementPhoneme, PhonemeMetadata replacementMetadata) {
		var newPhonemes = new ArrayList<>(phonemes);
		var newMetadata = new ArrayList<>(metadata);

		newPhonemes.set(index, replacementPhoneme);
		newMetadata.set(index, replacementMetadata);

		return new PhonemeSequence(newPhonemes, newMetadata);
	}

	public PhonemeSequence append(PhonemeSequence other) {
		var appendedPhonemes = new ArrayList<>(phonemes);
		var appendedMetadata = new ArrayList<>(metadata);

		appendedPhonemes.addAll(other.phonemes);
		appendedMetadata.addAll(other.metadata);

		return new PhonemeSequence(appendedPhonemes, appendedMetadata);
	}

	public Phoneme phonemeAt(int position) {
		return phonemes.get(position);
	}

	public int length() {
		return phonemes.size();
	}

	public PhonemeSequence subsequence(int beginIndex) {
		return subsequence(beginIndex, length());
	}

	public PhonemeSequence subsequence(int beginIndex, int endIndex) {
		return new PhonemeSequence(phonemes.subList(beginIndex, endIndex), metadata.subList(beginIndex, endIndex));
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
	public String render(IpaPhonemeMapper mapper) {
		if (phonemes.size() == 0) {
			return "";
		}

		var stringValue = IntStream.range(0, phonemes.size())
			.mapToObj(index -> {
				var phoneme = phonemes.get(index);
				var meta = metadata.get(index);

				if (meta.isWordStart) {
					return "#" + mapper.getIpa(phoneme);
				} else if (meta.isSyllableStart) {
					return "." + mapper.getIpa(phoneme);
				} else {
					return mapper.getIpa(phoneme);
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof PhonemeSequence other)) {
			return false;
		}

		return phonemes.equals(other.phonemes) && metadata.equals(other.metadata);
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
