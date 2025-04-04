package net.calebscode.langforge.app.phonology.view;

import java.io.IOException;
import java.util.Objects;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryModel;

public class PhonologyView extends AnchorPane {

	private LangforgePluginContext context;
	private PhonologicalInventoryModel phonologyModel;
	private SyllablePatternCategoryModel syllableModel;

	@FXML private ListView<String> viewSelector;
	@FXML private AnchorPane contentContainer;

	private PhonologicalInventoryView inventoryView;
	private SyllablePatternView syllableView;

	public PhonologyView(
			LangforgePluginContext context,
			PhonologicalInventoryModel phonologyModel,
			SyllablePatternCategoryModel syllableModel
	) {
		this.context = context;
		this.phonologyModel = phonologyModel;
		this.syllableModel = syllableModel;

		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/PhonologyView.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		inventoryView = new PhonologicalInventoryView(phonologyModel);
		AnchorPane.setTopAnchor(inventoryView, 0.0);
		AnchorPane.setBottomAnchor(inventoryView, 0.0);
		AnchorPane.setLeftAnchor(inventoryView, 0.0);
		AnchorPane.setRightAnchor(inventoryView, 0.0);

		syllableView = new SyllablePatternView(phonologyModel, syllableModel);
		AnchorPane.setTopAnchor(syllableView, 0.0);
		AnchorPane.setBottomAnchor(syllableView, 0.0);
		AnchorPane.setLeftAnchor(syllableView, 0.0);
		AnchorPane.setRightAnchor(syllableView, 0.0);

		viewSelector.getSelectionModel().selectedItemProperty().addListener(this::viewSelectionChanged);
		viewSelector.getSelectionModel().select(0);
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
