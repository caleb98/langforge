package net.calebscode.langforge.app.phonology.view;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemeFeatures;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class IpaConsonantPicker extends AnchorPane {

	private record TableKey(String place, String type) {}

	@FXML private TableView<String> ipaTable;
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

	private LanguagePhonologyModel model;
	private Map<TableKey, List<Phoneme>> groupedPhonemes;

	public IpaConsonantPicker(LanguagePhonologyModel model) {
		var loader = new FXMLLoader(IpaConsonantPicker.class.getResource("IpaConsonantPicker.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		this.model = model;

		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_PLOSIVE);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_NASAL);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_TRILL);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_FLAP);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_FRICATIVE);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_LATERAL_FRICATIVE);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_APPROXIMATE);
		ipaTable.getItems().add(StandardPhonemeFeatures.TYPE_LATERAL_APPROXIMATE);

		ipaTable.setSelectionModel(null);

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

		columnBilabial.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_BILABIAL));
		columnBilabial.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_BILABIAL));

		columnLabiodental.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_LABIODENTAL));
		columnLabiodental.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_LABIODENTAL));

		columnDental.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_DENTAL));
		columnDental.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_DENTAL));

		columnAlveolar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_ALVEOLAR));
		columnAlveolar.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_ALVEOLAR));

		columnPostalveolar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_POST_ALVEOLAR));
		columnPostalveolar.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_POST_ALVEOLAR));

		columnRetroflex.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_RETROFLEX));
		columnRetroflex.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_RETROFLEX));

		columnPalatal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_PALATAL));
		columnPalatal.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_PALATAL));

		columnVelar.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_VELAR));
		columnVelar.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_VELAR));

		columnUvular.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_UVULAR));
		columnUvular.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_UVULAR));

		columnPharyngeal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_PHARYNGEAL));
		columnPharyngeal.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_PHARYNGEAL));

		columnGlottal.setCellValueFactory(valueFactoryFor(StandardPhonemeFeatures.PLACE_GLOTTAL));
		columnGlottal.setCellFactory(cellFactoryFor(StandardPhonemeFeatures.PLACE_GLOTTAL));
	}

	private Callback<CellDataFeatures<String, List<Phoneme>>, ObservableValue<List<Phoneme>>> valueFactoryFor(String place) {
		return cell -> {
			var key = new TableKey(place, cell.getValue());
			return new ReadOnlyObjectWrapper<>(groupedPhonemes.get(key));
		};
	}

	private Callback<TableColumn<String, List<Phoneme>>, TableCell<String, List<Phoneme>>> cellFactoryFor(String place) {
		return cell -> new TableCell<>() {

			HBox container;

			{
				container = new HBox();
				container.setAlignment(Pos.CENTER);

				setGraphic(container);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}

			@Override
			protected void updateItem(List<Phoneme> item, boolean empty) {
				if (item == null) {
					container.getChildren().clear();
					return;
				}

				var newButtons = new ArrayList<Node>();
				for (var phoneme : item) {
					var button = new Button(phoneme.render(StandardPhonemes.IPA_MAPPER));
					button.setOnMouseClicked(event -> {
						model.phonemesProperty().add(phoneme);
					});
					newButtons.add(button);
				}
				container.getChildren().setAll(newButtons);
			};

		};
	}

}
