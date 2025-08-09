package net.calebscode.langforge.app.lexicon.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.calebscode.langforge.LexicalCategory;
import net.calebscode.langforge.Word;

public class LexiconEntryModel {

	private final ObjectProperty<Word> word = new SimpleObjectProperty<>();
	private final ObjectProperty<LexicalCategory> category = new SimpleObjectProperty<>();
	private final StringProperty definition = new SimpleStringProperty("No definition provided.");

	public ObjectProperty<Word> wordProperty() {
		return word;
	}

	public Word getWord() {
		return word.get();
	}

	public void setWord(Word word) {
		this.word.set(word);
	}

	public ObjectProperty<LexicalCategory> categoryProperty() {
		return category;
	}

	public LexicalCategory getCategory() {
		return category.get();
	}

	public void setCategory(LexicalCategory category) {
		this.category.set(category);
	}

	public StringProperty definitionProperty() {
		return definition;
	}

	public String getDefinition() {
		return definition.get();
	}

	public void setDefinition(String definition) {
		this.definition.set(definition);
	}

}
