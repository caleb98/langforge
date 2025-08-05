package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SyllablePatternEditorModel {

	private final BooleanProperty isEditing;
	private final ObjectProperty<SyllablePatternModel> patternModel;

	public SyllablePatternEditorModel(SyllablePatternCategoryMapModel categoryModel) {
		isEditing = new SimpleBooleanProperty(false);
		this.patternModel = new SimpleObjectProperty<>(new SyllablePatternModel(categoryModel));
	}

	public SyllablePatternEditorModel(SyllablePatternModel patternModel) {
		isEditing = new SimpleBooleanProperty(false);
		this.patternModel = new SimpleObjectProperty<>(patternModel);
	}

	public BooleanProperty isEditingProperty() {
		return isEditing;
	}

	public ObjectProperty<SyllablePatternModel> patternModelProperty() {
		return patternModel;
	}

	public SyllablePatternModel getPatternModel() {
		return patternModel.get();
	}

}
