package net.calebscode.langforge.app.phonology;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PhonemeFeatureDefinitionsView extends HBox {

	public PhonemeFeatureDefinitionsView(PhonemeFeatureDefinitions definitions) {
		// Feature Names
		var featureNamesLabel = new Label("Features");
		var featureNamesList = new ListView<String>(definitions.featureNamesProperty());
		VBox.setVgrow(featureNamesList, Priority.ALWAYS);

		var featureNamesAddButton = new Button("Add");
		featureNamesAddButton.setOnMouseClicked(event -> {

		});
		var featureNamesRemoveButton = new Button("Remove");
		featureNamesRemoveButton.setOnMouseClicked(event -> definitions.removeFeature(featureNamesList.getSelectionModel().getSelectedItem()));
		var featureNamesButtons = new GridPane();

		var featureNamesColumn1 = new ColumnConstraints();
		featureNamesColumn1.setPercentWidth(50);
		var featureNamesColumn2 = new ColumnConstraints();
		featureNamesColumn2.setPercentWidth(50);
		featureNamesButtons.getColumnConstraints().addAll(featureNamesColumn1, featureNamesColumn2);

		featureNamesButtons.add(featureNamesAddButton, 0, 0);
		featureNamesButtons.add(featureNamesRemoveButton, 1, 0);
		featureNamesAddButton.setMaxWidth(Double.MAX_VALUE);
		featureNamesRemoveButton.setMaxWidth(Double.MAX_VALUE);

		var featureNamesLayout = new VBox(featureNamesLabel, featureNamesList, featureNamesButtons);
		featureNamesLayout.setPadding(new Insets(5));

		// Feature Values
		var featureValuesLabel = new Label();
		featureValuesLabel.textProperty().bind(Bindings.createStringBinding(
			() -> {
				var selectedItem = featureNamesList.getSelectionModel().getSelectedItem();
				return selectedItem == null ? "No Feature Selected" : "Values for " + selectedItem;
			},
			featureNamesList.getSelectionModel().selectedItemProperty()
		));
		var featureValuesList = new ListView<String>();
		VBox.setVgrow(featureValuesList, Priority.ALWAYS);

		featureNamesList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			featureValuesList.itemsProperty().unbind();
			featureValuesList.getItems().clear();
			if (newValue != null) {
				featureValuesList.itemsProperty().bind(definitions.featureValuesProperty(newValue));
			}
		});

		var featureValuesAddButton = new Button("Add");
		var featureValuesRemoveButton = new Button("Remove");
		featureValuesRemoveButton.setOnMouseClicked(event -> definitions.removeFeatureValue(
				featureNamesList.getSelectionModel().getSelectedItem(),
				featureValuesList.getSelectionModel().getSelectedItem()
		));
		var featureValuesButtons = new GridPane();

		var featureValuesColumn1 = new ColumnConstraints();
		featureValuesColumn1.setPercentWidth(50);
		var featureValuesColumn2 = new ColumnConstraints();
		featureValuesColumn2.setPercentWidth(50);
		featureValuesButtons.getColumnConstraints().addAll(featureValuesColumn1, featureValuesColumn2);

		featureValuesButtons.add(featureValuesAddButton, 0, 0);
		featureValuesButtons.add(featureValuesRemoveButton, 1, 0);
		featureValuesAddButton.setMaxWidth(Double.MAX_VALUE);
		featureValuesRemoveButton.setMaxWidth(Double.MAX_VALUE);

		var featureValuesLayout = new VBox(featureValuesLabel, featureValuesList, featureValuesButtons);
		featureValuesLayout.setPadding(new Insets(5));

		// HBox Setup
		HBox.setHgrow(featureNamesLayout, Priority.ALWAYS);
		HBox.setHgrow(featureValuesLayout, Priority.ALWAYS);

		getChildren().add(featureNamesLayout);
		getChildren().add(new Separator(Orientation.VERTICAL));
		getChildren().add(featureValuesLayout);
	}
}
