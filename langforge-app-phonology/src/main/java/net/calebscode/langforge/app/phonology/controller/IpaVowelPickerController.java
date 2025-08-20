package net.calebscode.langforge.app.phonology.controller;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.calebscode.langforge.app.phonology.factory.PhonemeButtonsCellFactory;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.ui.FXMLController;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class IpaVowelPickerController extends AnchorPane implements FXMLController {

	private record TableKey(String backness, String openness) {}

	@FXML private TableView<String> vowelTable;
	@FXML private TableColumn<String, String> columnOpenness;
	@FXML private TableColumn<String, List<Phoneme>> columnFront;
	@FXML private TableColumn<String, List<Phoneme>> columnCentral;
	@FXML private TableColumn<String, List<Phoneme>> columnBack;

	private Map<TableKey, List<Phoneme>> groupedPhonemes;

	public IpaVowelPickerController(PhonologicalInventoryModel model) {
		load(() -> {
			// Not using StandardPhonemeFeatures.STANDARD_OPENNESSES here so that the ordering matches the IPA chart
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_CLOSE);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_NEAR_CLOSE);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_CLOSE_MID);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_MID);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_OPEN_MID);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_NEAR_OPEN);
			vowelTable.getItems().add(StandardPhonemeFeatures.OPENNESS_OPEN);

			vowelTable.setSelectionModel(null);

			groupedPhonemes = StandardPhonemes.IPA_PHONEMES.stream()
					.filter(phoneme -> phoneme.getFeatureValue(CATEGORY).orElse("").equals(CATEGORY_VOWEL))
					.collect(Collectors.groupingBy(phoneme -> {
						var maybeOpenness = phoneme.getFeatureValue(OPENNESS);
						var maybeBackness = phoneme.getFeatureValue(BACKNESS);

						if (maybeOpenness.isEmpty() || maybeBackness.isEmpty()) {
							// TODO: Decide if this should be logged silently instead
							throw new RuntimeException("Expected all vowels to have openness and backness features.");
						}

						var openness = maybeOpenness.get();
						var backness = maybeBackness.get();

						return new TableKey(backness, openness);
					}));

			columnOpenness.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue()));
			columnOpenness.setCellFactory(data -> {
				@SuppressWarnings("unchecked")
				var cell = (TableCell<String, String>) TableColumn.DEFAULT_CELL_FACTORY.call(data);
				cell.getStyleClass().setAll("column-header", "table-column");
				cell.setStyle("-fx-alignment: BASELINE_LEFT;");
				return cell;
			});

			var cellFactory = new PhonemeButtonsCellFactory(model);

			columnFront.setCellValueFactory(valueFactoryFor(BACKNESS_FRONT));
			columnFront.setCellFactory(cellFactory);

			columnCentral.setCellValueFactory(valueFactoryFor(BACKNESS_CENTRAL));
			columnCentral.setCellFactory(cellFactory);

			columnBack.setCellValueFactory(valueFactoryFor(BACKNESS_BACK));
			columnBack.setCellFactory(cellFactory);
		});
	}

	private Callback<CellDataFeatures<String, List<Phoneme>>, ObservableValue<List<Phoneme>>> valueFactoryFor(String backness) {
		return cell -> {
			var key = new TableKey(backness, cell.getValue());
			return new ReadOnlyObjectWrapper<>(groupedPhonemes.get(key));
		};
	}

}
