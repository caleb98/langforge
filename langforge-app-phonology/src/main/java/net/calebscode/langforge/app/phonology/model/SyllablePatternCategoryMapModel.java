package net.calebscode.langforge.app.phonology.model;

import static java.util.Collections.unmodifiableSet;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableHashMap;
import static javafx.collections.FXCollections.unmodifiableObservableMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langforge.phonology.syllable.SyllablePatternCompiler;

public class SyllablePatternCategoryMapModel extends SyllablePatternCategoryMap {

	private final MapProperty<Character, Set<Phoneme>> categoryMap;
	private final ReadOnlyMapWrapper<Character, Set<Phoneme>> categoryWrapper;

	private final SyllablePatternCompiler compiler;

	public SyllablePatternCategoryMapModel() {
		categoryMap = new SimpleMapProperty<>(observableHashMap());

		categoryWrapper = new ReadOnlyMapWrapper<>();
		categoryWrapper.bind(createObjectBinding(() -> {
			ObservableMap<Character, Set<Phoneme>> mapCopy = FXCollections.observableHashMap();
			for (var category : categoryMap.keySet()) {
				mapCopy.put(category, unmodifiableSet(categoryMap.get(category)));
			}
			return unmodifiableObservableMap(mapCopy);
		}, categoryMap));

		compiler = new SyllablePatternCompiler(this);
	}

	public ReadOnlyMapProperty<Character, Set<Phoneme>> categoryMapProperty() {
		return categoryWrapper;
	}

	public SyllablePatternCompiler getCompiler() {
		return compiler;
	}

	@Override
	public void addCategory(Character category) {
		super.addCategory(category);
		categoryMap.putIfAbsent(category, new HashSet<>());
	}

	@Override
	public Set<Character> getCategories() {
		return Collections.unmodifiableSet(super.getCategories());
	}

	@Override
	public Set<Phoneme> getPhonemes(Character category) {
		return unmodifiableSet(super.getPhonemes(category));
	}

	@Override
	public void addPhoneme(Character category, Phoneme phoneme) {
		super.addPhoneme(category, phoneme);

		var set = categoryMap.computeIfAbsent(category, c -> new HashSet<>());
		set = new HashSet<>(set);
		if (set.add(phoneme)) {
			// Calling put is necessary to trigger listener callbacks
			categoryMap.put(category, set);
		}
	}

	@Override
	public void removeCategory(Character category) {
		super.removeCategory(category);
		categoryMap.remove(category);
	}

	@Override
	public void removePhoneme(Character category, Phoneme phoneme) {
		super.removePhoneme(category, phoneme);

		var set = categoryMap.get(category);
		set = new HashSet<>(set);
		if (set != null && set.remove(phoneme)) {
			// Calling put is necessary to trigger listener callbacks
			categoryMap.put(category, new HashSet<>(set));
		}
	}

	@Override
	public void removePhonemeForAllCategories(Phoneme phoneme) {
		super.removePhonemeForAllCategories(phoneme);

		for (var entry : categoryMap.entrySet()) {
			var set = new HashSet<>(entry.getValue());
			if (set.remove(phoneme)) {
				categoryMap.put(entry.getKey(), set);
			}
		}
	}

}
