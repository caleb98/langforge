package net.calebscode.langforge.app.phonology.view;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class IpaConsonantPicker extends AnchorPane {

	private record TableKey(String place, String type) {}

	@FXML private TableView<String> consonantTable;
	@FXML private TableColumn<String, String> columnType;
	@FXML private TableColumn<String, List<Phoneme>> columnBilabial;
	@FXML private TableColumn<String, List<Phoneme>> columnLabiodental;
	@FXML private TableColumn<String, List<Phoneme>> columnDental;
	@FXML private TableColumn<String, List<Phoneme>> columnAlveolar;
	@FXML private TableColumn<String, List<Phoneme>> columnPostalveolar;
	@FXML private TableColumn<String, List<Phoneme>> columnRetroflex;
	@FXML private TableColumn<String, List<Phoneme>> columnPalatal;
	@FXML private TableColumn<String, List<Phoneme>> columnVelar;
	@FXML private TableColumn<String, List<Phoneme>> columnUvular;
	@FXML private TableColumn<String, List<Phoneme>> columnPharyngeal;
	@FXML private TableColumn<String, List<Phoneme>> columnGlottal;

	private Map<TableKey, List<Phoneme>> groupedPhonemes;

	public IpaConsonantPicker(PhonologicalInventoryModel model) {
		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/IpaConsonantPicker.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		// Not using StandardPhonemeFeatures.STANDARD_TYPES here so that the ordering matches the IPA chart
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_PLOSIVE);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_NASAL);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_TRILL);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_FLAP);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_FRICATIVE);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_LATERAL_FRICATIVE);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_APPROXIMATE);
		consonantTable.getItems().add(StandardPhonemeFeatures.TYPE_LATERAL_APPROXIMATE);

		consonantTable.setSelectionModel(null);

		groupedPhonemes = StandardPhonemes.IPA_PHONEMES.stream()
			.filter(phoneme -> phoneme.getFeatureValue(CATEGORY).orElse("").equals(CATEGORY_CONSONANT))
			.collect(Collectors.groupingBy(phoneme -> {
				var maybePlace = phoneme.getFeatureValue(PLACE);
				var maybeType = phoneme.getFeatureValue(TYPE);

				if (maybePlace.isEmpty() || maybeType.isEmpty()) {
					// TODO: Decide if this should be logged silently instead
					throw new RuntimeException("Expected all consonants to have place and type features.");
				}

				var place = maybePlace.get();
				var type = maybeType.get();

				return new TableKey(place, type);
			}));

		columnType.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue()));
		columnType.setCellFactory(data -> {
			@SuppressWarnings("unchecked")
			var cell = (TableCell<String, String>) TableColumn.DEFAULT_CELL_FACTORY.call(data);
			cell.getStyleClass().setAll("column-header", "table-column");
			cell.setStyle("-fx-alignment: BASELINE_LEFT;");
			return cell;
		});

		var cellFactory = new PhonemeButtonsCellFactory(model);

		columnBilabial.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_BILABIAL));
		columnBilabial.setCellFactory(cellFactory);

		columnLabiodental.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_LABIODENTAL));
		columnLabiodental.setCellFactory(cellFactory);

		columnDental.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_DENTAL));
		columnDental.setCellFactory(cellFactory);

		columnAlveolar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_ALVEOLAR));
		columnAlveolar.setCellFactory(cellFactory);

		columnPostalveolar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_POST_ALVEOLAR));
		columnPostalveolar.setCellFactory(cellFactory);

		columnRetroflex.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_RETROFLEX));
		columnRetroflex.setCellFactory(cellFactory);

		columnPalatal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_PALATAL));
		columnPalatal.setCellFactory(cellFactory);

		columnVelar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_VELAR));
		columnVelar.setCellFactory(cellFactory);

		columnUvular.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_UVULAR));
		columnUvular.setCellFactory(cellFactory);

		columnPharyngeal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_PHARYNGEAL));
		columnPharyngeal.setCellFactory(cellFactory);

		columnGlottal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_GLOTTAL));
		columnGlottal.setCellFactory(cellFactory);
	}

	private Callback<CellDataFeatures<String, List<Phoneme>>, ObservableValue<List<Phoneme>>> valueFactoryFor(String place) {
		return cell -> {
			var key = new TableKey(place, cell.getValue());
			return new ReadOnlyObjectWrapper<>(groupedPhonemes.get(key));
		};
	}

}
