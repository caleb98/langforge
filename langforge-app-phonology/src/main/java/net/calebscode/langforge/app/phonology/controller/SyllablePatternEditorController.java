package net.calebscode.langforge.app.phonology.controller;

import static javafx.beans.binding.Bindings.bindBidirectional;
import static javafx.beans.binding.Bindings.not;

import java.util.Objects;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.SyllablePatternEditorModel;
import net.calebscode.langforge.app.util.FXMLController;

public class SyllablePatternEditorController extends VBox implements FXMLController {

	@FXML private Label patternDisplay;
	@FXML private TextField patternEdit;
	@FXML private Label patternError;

	public SyllablePatternEditorController(SyllablePatternEditorModel model) {
		load(() -> {
			var patternModel = model.getPatternModel();

			patternEdit.textProperty().set(patternModel.patternStringProperty().get());
			patternDisplay.textProperty().set(patternModel.patternStringProperty().get());

			bindBidirectional(patternEdit.textProperty(), patternModel.patternStringProperty());

			patternEdit.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!Objects.equals(oldValue, newValue)) {
					patternEdit.setText(newValue.toUpperCase());
				}
			});

			patternDisplay.textProperty().bind(Bindings.createStringBinding(
					() -> patternModel.patternStringProperty().get().isEmpty() ?
						"<empty pattern>" :
						patternModel.patternStringProperty().get(),
					patternModel.patternStringProperty()));

			patternDisplay.visibleProperty().bind(not(model.isEditingProperty()));
			patternDisplay.managedProperty().bind(patternDisplay.visibleProperty());
			patternDisplay.prefWidthProperty().bind(widthProperty().subtract(2));

			patternEdit.visibleProperty().bind(model.isEditingProperty());
			patternEdit.managedProperty().bind(patternEdit.visibleProperty());
			patternEdit.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
					model.isEditingProperty().set(false);
					event.consume();
				}
			});

			patternDisplay.setOnMouseClicked(event -> {
				model.isEditingProperty().set(true);
				patternEdit.requestFocus();
			});

			patternEdit.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
				if (!isFocused) {
					model.isEditingProperty().set(false);
				}
			});

			patternError.textProperty().bind(patternModel.compileErrorProperty());
			patternError.visibleProperty().bind(patternModel.hasCompileErrorProperty());
			patternError.managedProperty().bind(patternError.visibleProperty());
		});
	}

}
