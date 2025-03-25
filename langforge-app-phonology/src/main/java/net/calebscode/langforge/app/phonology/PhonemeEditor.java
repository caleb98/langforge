package net.calebscode.langforge.app.phonology;

import java.io.IOException;
import java.util.HashMap;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonemeEditor extends SplitPane {

	private record FeatureEntry(String name, String value) {}

	private ObservableList<FeatureEntry> currentFeatures = FXCollections.observableArrayList();
	private Phoneme originalSelection;

	@FXML private ListView<Phoneme> phonemesList;

	@FXML private ChoiceBox<String> featureNameSelect;
	@FXML private ChoiceBox<String> featureValueSelect;
	@FXML private Button addFeatureButton;

	@FXML private TableView<FeatureEntry> featuresTable;
	@FXML private TableColumn<FeatureEntry, String> featureNameColumn;
	@FXML private TableColumn<FeatureEntry, String> featureValueColumn;
	@FXML private TableColumn<FeatureEntry, String> featureDeleteColumn;

	public PhonemeEditor(ObservableValue<ConlangPhonologyModel> phonologyModel) {
		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/PhonemeEditor.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		// TODO: set this up so that if the value of the phonologyModel property changes, all the ui bindings are reconfigured.
		var features = phonologyModel.getValue().getFeatureDefinitions();
		var phonemes = phonologyModel.getValue().phonemesProperty();

		phonemesList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				System.out.println("Selection changed.");
				originalSelection = newValue;
				currentFeatures.setAll(newValue.features().entrySet().stream().map(e -> new FeatureEntry(e.getKey(), e.getValue())).toList());
			}
		});
		phonemesList.itemsProperty().bind(phonemes);

		currentFeatures.addListener((ListChangeListener<? super FeatureEntry>) change -> {
			if (originalSelection == null) {
				return;
			}

			var newFeatures = new HashMap<String, String>();
			for (var entry : currentFeatures) {
				newFeatures.put(entry.name(), entry.value());
			}
			var newPhoneme = new Phoneme(newFeatures);
			var index = phonemes.indexOf(originalSelection);
			phonemes.remove(index);
			phonemes.add(index, newPhoneme);
			phonemesList.getSelectionModel().select(index);
		});

		// Feature Editing Configuration
		featureNameSelect.itemsProperty().bind(features.featureNamesProperty());
		featureNameSelect.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			featureValueSelect.itemsProperty().unbind();
			featureValueSelect.itemsProperty().bind(features.featureValuesProperty(newValue));
			featureValueSelect.getSelectionModel().select(0);
		});

		// Features Table Configuration
		featuresTable.setItems(currentFeatures);

		featureNameColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().name()));
		featureValueColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().value()));
		featureDeleteColumn.setCellValueFactory(value -> new ReadOnlyStringWrapper(value.getValue().name()));

		featureDeleteColumn.setCellFactory(param -> {
			var button = new ButtonTableCell<FeatureEntry, String>("Delete");
			button.setButtonClicked((featureName, event) -> {
				currentFeatures.removeIf(feature -> feature.name().equals(featureName));
			});
			return button;
		});
	}

	@FXML
	private void addFeatureButtonClicked(MouseEvent event) {
		var featureName = featureNameSelect.getSelectionModel().getSelectedItem();
		var featureValue = featureValueSelect.getSelectionModel().getSelectedItem();

		if (featureName.isBlank() || featureValue.isBlank()) {
			return;
		}

		currentFeatures.removeIf(entry -> featureName.equals(entry.name()));
		currentFeatures.add(new FeatureEntry(featureName, featureValue));
	}

}