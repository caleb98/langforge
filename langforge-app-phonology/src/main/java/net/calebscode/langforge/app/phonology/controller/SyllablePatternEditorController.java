package net.calebscode.langforge.app.phonology.controller;

import static javafx.beans.binding.Bindings.bindBidirectional;
import static javafx.beans.binding.Bindings.not;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import net.calebscode.langforge.app.phonology.model.SyllablePatternEditorModel;
import net.calebscode.langforge.app.util.FXMLController;

public class SyllablePatternEditorController extends StackPane implements FXMLController {

	@FXML private Label patternDisplay;
	@FXML private TextField patternEdit;

	public SyllablePatternEditorController(SyllablePatternEditorModel editorModel) {
		load(() -> {
			patternEdit.textProperty().set(editorModel.patternProperty().get());
			patternDisplay.textProperty().set(editorModel.patternProperty().get());

			bindBidirectional(patternEdit.textProperty(), editorModel.patternProperty());

			patternEdit.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!Objects.equals(oldValue, newValue)) {
					patternEdit.setText(newValue.toUpperCase());
				}
			});

			patternDisplay.textProperty().bind(editorModel.patternProperty().isEmpty().map(empty -> empty ? "<empty pattern>" : editorModel.patternProperty().get()));

			patternDisplay.visibleProperty().bind(not(editorModel.isEditingProperty()));
			patternDisplay.managedProperty().bind(patternDisplay.visibleProperty());
			patternDisplay.prefWidthProperty().bind(widthProperty().subtract(2));

			patternEdit.visibleProperty().bind(editorModel.isEditingProperty());
			patternEdit.managedProperty().bind(patternEdit.visibleProperty());
			patternEdit.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
					editorModel.isEditingProperty().set(false);
					event.consume();
				}
			});

			patternDisplay.setOnMouseClicked(event -> {
				editorModel.isEditingProperty().set(true);
				patternEdit.requestFocus();
			});

			patternEdit.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
				if (!isFocused) {
					editorModel.isEditingProperty().set(false);
				}
			});
		});
	}

}
