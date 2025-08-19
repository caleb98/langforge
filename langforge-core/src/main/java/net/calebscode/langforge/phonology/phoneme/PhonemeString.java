package net.calebscode.langforge.phonology.phoneme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.calebscode.langforge.phonology.PhonemeSequence;

public class PhonemeString implements PhonemeSequence {

	public static final PhonemeString EMPTY = new PhonemeString();

	private List<Phoneme> phonemes;
	private List<PhonemeContext> contexts;

	public PhonemeString() {
		phonemes = List.of();
		contexts = List.of();
	}

	public PhonemeString(List<Phoneme> phonemes, List<PhonemeContext> contexts) {
		if (phonemes.size() != contexts.size()) {
			throw new IllegalArgumentException("PhonemeString phonemes and contexts must be same size.");
		}

		this.phonemes = Collections.unmodifiableList(phonemes);
		this.contexts = Collections.unmodifiableList(contexts);
	}

	@Override
	public List<Phoneme> getPhonemes() {
		return phonemes;
	}

	@Override
	public List<PhonemeContext> getPhonemeContexts() {
		return contexts;
	}

	@Override
	public boolean hasContexts() {
		return true;
	}

	/**
	 * Returns a new PhonemeString with the phoneme at the given index replaced with the new
	 * phoneme. Context data will be preserved from the phoneme that is already at the given index.
	 * @param index
	 * @param replacementPhoneme
	 * @return
	 */
	public PhonemeString replaceAt(int index, Phoneme replacementPhoneme) {
		return replaceAt(index, replacementPhoneme, contexts.get(index));
	}

	/**
	 * Returns a new PhonemeString with the phoneme at the given index replaced with the new
	 * phoneme and context.
	 * @param index
	 * @param replacementPhoneme
	 * @param replacementContext
	 * @return
	 */
	public PhonemeString replaceAt(int index, Phoneme replacementPhoneme, PhonemeContext replacementContext) {
		var newPhonemes = new ArrayList<>(phonemes);
		var newContexts = new ArrayList<>(contexts);

		newPhonemes.set(index, replacementPhoneme);
		newContexts.set(index, replacementContext);

		return new PhonemeString(newPhonemes, newContexts);
	}

	public PhonemeString append(PhonemeString other) {
		var appendedPhonemes = new ArrayList<>(phonemes);
		var appendedContexts = new ArrayList<>(contexts);

		appendedPhonemes.addAll(other.phonemes);
		appendedContexts.addAll(other.contexts);

		return new PhonemeString(appendedPhonemes, appendedContexts);
	}

	public Phoneme phonemeAt(int position) {
		return phonemes.get(position);
	}

	public PhonemeContext contextAt(int position) {
		return contexts.get(position);
	}

	public int length() {
		return phonemes.size();
	}

	public PhonemeString substring(int beginIndex) {
		return substring(beginIndex, length());
	}

	public PhonemeString substring(int beginIndex, int endIndex) {
		return new PhonemeString(phonemes.subList(beginIndex, endIndex), contexts.subList(beginIndex, endIndex));
	}

	public PhonemeTransition getTransition(int from) {
		if (phonemes.isEmpty()) {
			return new PhonemeTransition(false, false, false);
		}

		int to = from + 1;

		if (from == -1) {
			var toContext = contexts.get(to);
			return new PhonemeTransition(toContext.isWordStart(), toContext.isSyllableStart(), true);
		}
		else {
			var fromContext = contexts.get(from);
			return new PhonemeTransition(fromContext.isWordEnd(), fromContext.isSyllableEnd(), from == phonemes.size() - 1);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof PhonemeString other)) {
			return false;
		}

		return phonemes.equals(other.phonemes) && contexts.equals(other.contexts);
	}

	public record PhonemeTransition (
		boolean crossedWordBoundary,
		boolean crossedSyllableBoundary,
		boolean crossedSequenceBoundary
	) {}

}
