package net.calebscode.langforge.app.phonology.controller;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_MAPPER;

import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.calebscode.langforge.app.phonology.model.PhonemeFeatureModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.ui.ButtonTableCell;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonologicalInventoryController extends VBox implements FXMLController {

	private PhonologicalInventoryModel phonologyModel;

	private Optional<Stage> consonantPicker = Optional.empty();
	private Optional<Stage> vowelPicker = Optional.empty();

	@FXML private TableView<Phoneme> phonemesTable;

	public PhonologicalInventoryController(PhonologicalInventoryModel phonologyModel) {
		this.phonologyModel = phonologyModel;

		load(() -> {
			phonologyModel.featuresProperty().addListener((Change<? extends PhonemeFeatureModel> c) -> {
				if (c.wasAdded() || c.wasRemoved()) {
					this.updatePhonemesTable();
				}
			});

			phonemesTable.itemsProperty().bind(phonologyModel.phonemesProperty());

			updatePhonemesTable();
		});
	}

	private void updatePhonemesTable() {
		phonemesTable.getColumns().clear();

		var representationColumn = new TableColumn<Phoneme, String>("Representation");
		representationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().render(IPA_MAPPER)));
		representationColumn.setStyle("-fx-alignment: CENTER;");
		phonemesTable.getColumns().add(representationColumn);

		for (var feature : phonologyModel.featuresProperty()) {
			var column = new TableColumn<Phoneme, String>(feature.getName());
			column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().features().get(feature.getName())));
			phonemesTable.getColumns().add(column);
		}

		var deleteColumn = new TableColumn<Phoneme, Phoneme>();
		deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
		deleteColumn.setCellFactory(column -> {
			var button = new ButtonTableCell<Phoneme, Phoneme>("Delete");
			button.setButtonClicked((phoneme, p) -> {
				phonologyModel.phonemesProperty().remove(phoneme);
			});
			return button;
		});
		phonemesTable.getColumns().add(deleteColumn);
	}

	@FXML
	private void showIpaConsonantPicker(MouseEvent event) {
		if (consonantPicker.isPresent()) {
			return;
		}

		var ipaSelector = new IpaConsonantPickerController(phonologyModel);
		var scene = new Scene(ipaSelector);
		var stage = new Stage();
		stage.setTitle("IPA Consonants");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(e -> consonantPicker = Optional.empty());

		consonantPicker = Optional.of(stage);
	}

	@FXML
	private void showIpaVowelPicker(MouseEvent event) {
		if (vowelPicker.isPresent()) {
			return;
		}

		var ipaSelector = new IpaVowelPickerController(phonologyModel);
		var scene = new Scene(ipaSelector);
		var stage = new Stage();
		stage.setTitle("IPA Vowels");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(e -> vowelPicker = Optional.empty());

		vowelPicker = Optional.of(stage);
	}

}
