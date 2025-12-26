package net.calebscode.langforge.app.lexicon.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import net.calebscode.langforge.LexicalCategory;

public class LexiconModel {

	private final ListProperty<LexiconEntryModel> entries = new SimpleListProperty<>(observableArrayList());
	private final ListProperty<LexicalCategory> categories = new SimpleListProperty<>(observableArrayList());

	public LexiconModel() {
		categories.add(new LexicalCategory("noun"));
		categories.add(new LexicalCategory("verb"));
	}

	public ListProperty<LexiconEntryModel> entriesProperty() {
		return entries;
	}

	public ReadOnlyListProperty<LexicalCategory> categoriesProperty() {
		return categories;
	}

	public void addCategory(LexicalCategory newCategory) {
		if (!categories.contains(newCategory)) {
			categories.add(newCategory);
		}
	}

	public void removeCategory(LexicalCategory category, LexicalCategory replacement) {
		if (!categories.contains(replacement)) {
			throw new IllegalArgumentException("Replacement category must exist in the lexicon model when removing a lexical category.");
		}

		categories.remove(category);

		entries
			.parallelStream()
			.filter(entry -> entry.getCategory().equals(category))
			.forEach(entry -> entry.setCategory(replacement));
	}

}
