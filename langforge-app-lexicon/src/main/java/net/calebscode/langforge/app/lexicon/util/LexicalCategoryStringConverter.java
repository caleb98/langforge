package net.calebscode.langforge.app.lexicon.util;

import javafx.util.StringConverter;
import net.calebscode.langforge.LexicalCategory;

public class LexicalCategoryStringConverter extends StringConverter<LexicalCategory> {

	@Override
	public String toString(LexicalCategory category) {
		return category == null ? "" : category.name();
	}

	@Override
	public LexicalCategory fromString(String string) {
		return string == null ? null : new LexicalCategory(string);
	}

}
