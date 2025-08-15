package net.calebscode.langforge.app.phonology.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.app.util.FXMLController;

public class PhonologicalRuleEditorController extends VBox implements FXMLController {

	@FXML private TextField ruleSourceInput;
	@FXML private Label compileErrorLabel;

	public PhonologicalRuleEditorController(PhonologicalRuleModel model) {
		load(() -> {
			ruleSourceInput.textProperty().set(model.getSource());
			ruleSourceInput.textProperty().bindBidirectional(model.sourceProperty());

			compileErrorLabel.textProperty().bind(model.compileErrorProperty());
			compileErrorLabel.visibleProperty().bind(model.compileErrorProperty().isEmpty().not());
			compileErrorLabel.managedProperty().bind(model.compileErrorProperty().isEmpty().not());
		});
	}

}
