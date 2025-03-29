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
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;

public class PhonologyView extends AnchorPane {

	private LangforgePluginContext context;
	private LanguagePhonologyModel phonologyModel;

	@FXML private ListView<String> viewSelector;
	@FXML private VBox contentContainer;

	private PhonologicalInventoryView inventoryView;

	public PhonologyView(LangforgePluginContext context, LanguagePhonologyModel model) {
		this.context = context;
		phonologyModel = model;

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

		default:
			System.err.println("Unexpected view selected: " + newValue);
			break;

		}
	}

}
