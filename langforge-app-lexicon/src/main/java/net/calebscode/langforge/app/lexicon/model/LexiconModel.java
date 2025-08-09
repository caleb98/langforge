package net.calebscode.langforge.app.lexicon.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import net.calebscode.langforge.LexicalCategory;

public class LexiconModel {

	private final ListProperty<LexiconEntryModel> entries = new SimpleListProperty<>(observableArrayList());
	private final ListProperty<LexicalCategory> categories = new SimpleListProperty<>(observableArrayList());

	public ListProperty<LexiconEntryModel> entriesProperty() {
		return entries;
	}

	public ListProperty<LexicalCategory> categoriesProperty() {
		return categories;
	}

}
