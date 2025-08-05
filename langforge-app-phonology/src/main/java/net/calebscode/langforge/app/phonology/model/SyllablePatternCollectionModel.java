package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

public class SyllablePatternCollectionModel {

	private final ListProperty<SyllablePatternModel> patterns;

	public SyllablePatternCollectionModel() {
		patterns = new SimpleListProperty<>(observableArrayList());
	}

	public ListProperty<SyllablePatternModel> patternsProperty() {
		return patterns;
	}

}
