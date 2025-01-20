package net.calebscode.langforge.app.phonology;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PhonemeEditor extends Pane {

	private ObservableList<FeatureEntry> currentFeatures = FXCollections.observableArrayList();

	public PhonemeEditor(PhonemeFeatureDefinitions features) {
		var featureNameLabel = new Label("Feature");
		var featureValueLabel = new Label("Value");

		var featureNameSelect = new ChoiceBox<String>();
		var featureValueSelect = new ChoiceBox<String>();

		var featureAddButton = new Button("Add");

		featureNameSelect.itemsProperty().bind(features.featureNamesProperty());
		featureNameSelect.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			featureValueSelect.itemsProperty().unbind();
			featureValueSelect.itemsProperty().bind(features.featureValuesProperty(newValue));
			featureValueSelect.getSelectionModel().select(0);
		});

		featureAddButton.setOnMouseClicked(event -> {
			var featureName = featureNameSelect.getSelectionModel().getSelectedItem();
			var featureValue = featureValueSelect.getSelectionModel().getSelectedItem();

			if (featureName.isBlank() || featureValue.isBlank()) {
				return;
			}

			currentFeatures.removeIf(entry -> featureName.equals(entry.name()));
			currentFeatures.add(new FeatureEntry(featureName, featureValue));
		});

		var featureAddLayout = new GridPane();
		featureAddLayout.add(featureNameLabel, 0, 0);
		featureAddLayout.add(featureNameSelect, 1, 0);
		featureAddLayout.add(featureValueLabel, 0, 1);
		featureAddLayout.add(featureValueSelect, 1, 1);
		featureAddLayout.add(featureAddButton, 0, 2, 2, 1);

		//

		var featuresTable = new TableView<FeatureEntry>();
		featuresTable.setItems(currentFeatures);

		var featureNameColumn = new TableColumn<FeatureEntry, String>("Feature");
		var featureValueColumn = new TableColumn<FeatureEntry, String>("Value");
		var featureDeleteColumn = new TableColumn<FeatureEntry, Button>();

		featureNameColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().name()));
		featureValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().value()));
		featureDeleteColumn.setCellValueFactory(value -> {
			var deleteButton = new Button("Delete");
			deleteButton.setOnMouseClicked(event -> {
				currentFeatures.removeIf(value.getValue()::equals);
			});
			return new ReadOnlyObjectWrapper<>(deleteButton);
		});

		featuresTable.getColumns().add(featureNameColumn);
		featuresTable.getColumns().add(featureValueColumn);
		featuresTable.getColumns().add(featureDeleteColumn);

		//

		var editorLayout = new HBox();
		editorLayout.getChildren().add(featureAddLayout);
		editorLayout.getChildren().add(new Separator(Orientation.VERTICAL));
		editorLayout.getChildren().add(featuresTable);

		getChildren().add(editorLayout);
	}

	private record FeatureEntry(String name, String value) {}

}