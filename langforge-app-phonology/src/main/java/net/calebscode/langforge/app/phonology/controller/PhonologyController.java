package net.calebscode.langforge.app.phonology.controller;

import java.util.Objects;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.util.FXMLController;

public class PhonologyController extends AnchorPane implements FXMLController {

	@FXML private ListView<String> viewSelector;
	@FXML private AnchorPane contentContainer;

	private PhonologicalInventoryController inventoryView;
	private SyllableManagementController syllableView;
	private PhonologicalRuleManagementController ruleView;

	public PhonologyController(LanguagePhonologyModel phonologyModel) {
		load(() -> {
			inventoryView = new PhonologicalInventoryController(phonologyModel);
			syllableView = new SyllableManagementController(phonologyModel);
			ruleView = new PhonologicalRuleManagementController(phonologyModel);

			viewSelector.getSelectionModel().selectedItemProperty().addListener(this::viewSelectionChanged);
			viewSelector.getSelectionModel().select(0);
		});
	}

	private void setActiveView(Node view) {
		Objects.requireNonNull(view, "Active view must not be null");
		contentContainer.getChildren().setAll(view);
	}

	private void viewSelectionChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		switch (newValue) {

		case "Phonological Inventory":
			setActiveView(inventoryView);
			break;

		case "Syllable Patterns":
			setActiveView(syllableView);
			break;

		case "Phonological Rules":
			setActiveView(ruleView);
			break;

		default:
			System.err.println("Unexpected view selected: " + newValue);
			break;

		}
	}

}
