package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;

public class SyllablePatternCollectionModel {

	private final ListProperty<StringProperty> patterns;

	public SyllablePatternCollectionModel() {
		patterns = new SimpleListProperty<>(observableArrayList());
	}

	public ListProperty<StringProperty> patternsProperty() {
		return patterns;
	}

}
