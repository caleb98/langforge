package net.calebscode.langforge.app.phonology.controller;

import java.util.Objects;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryMapModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCollectionModel;
import net.calebscode.langforge.app.util.FXMLController;

public class PhonologyController extends AnchorPane implements FXMLController {

	private LangforgePluginContext context;
	private PhonologicalInventoryModel phonologicalInventoryModel;
	private SyllablePatternCategoryMapModel syllablePatternCategoryModel;
	private SyllablePatternCollectionModel syllablePatternCollectionModel;

	@FXML private ListView<String> viewSelector;
	@FXML private AnchorPane contentContainer;

	private PhonologicalInventoryController inventoryView;
	private SyllableManagementController syllableView;

	public PhonologyController(
			LangforgePluginContext context,
			PhonologicalInventoryModel phonologicalInventoryModel,
			SyllablePatternCategoryMapModel syllablePatternCategoryModel,
			SyllablePatternCollectionModel syllablePatternCollectionModel
	) {
		this.context = context;
		this.phonologicalInventoryModel = phonologicalInventoryModel;
		this.syllablePatternCategoryModel = syllablePatternCategoryModel;

		load(() -> {
			inventoryView = new PhonologicalInventoryController(phonologicalInventoryModel);
			AnchorPane.setTopAnchor(inventoryView, 0.0);
			AnchorPane.setBottomAnchor(inventoryView, 0.0);
			AnchorPane.setLeftAnchor(inventoryView, 0.0);
			AnchorPane.setRightAnchor(inventoryView, 0.0);

			syllableView = new SyllableManagementController(
					phonologicalInventoryModel,
					syllablePatternCategoryModel,
					syllablePatternCollectionModel);
			AnchorPane.setTopAnchor(syllableView, 0.0);
			AnchorPane.setBottomAnchor(syllableView, 0.0);
			AnchorPane.setLeftAnchor(syllableView, 0.0);
			AnchorPane.setRightAnchor(syllableView, 0.0);

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

		default:
			System.err.println("Unexpected view selected: " + newValue);
			break;

		}
	}

}
