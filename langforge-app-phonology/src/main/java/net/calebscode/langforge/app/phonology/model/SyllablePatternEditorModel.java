package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SyllablePatternEditorModel {

	private final BooleanProperty isEditing = new SimpleBooleanProperty(false);
	private final StringProperty pattern = new SimpleStringProperty("");

	public BooleanProperty isEditingProperty() {
		return isEditing;
	}

	public StringProperty patternProperty() {
		return pattern;
	}

}
