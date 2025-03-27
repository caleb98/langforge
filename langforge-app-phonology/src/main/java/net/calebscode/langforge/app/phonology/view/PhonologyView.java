package net.calebscode.langforge.app.phonology.view;

import java.io.IOException;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.ButtonTableCell;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonemeFeatureModel;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class PhonologyView extends AnchorPane {

	private LangforgePluginContext context;
	private LanguagePhonologyModel phonologyModel;
	private IpaPhonemeMapper phonemeMapper = StandardPhonemes.STANDARD_IPA_PHONEMES;

	@FXML private TableView<Phoneme> phonemesTable;

	public PhonologyView(LangforgePluginContext context) {
		this.context = context;
		this.phonologyModel = new LanguagePhonologyModel();

		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/PhonologyView.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		phonologyModel.phonemesProperty().add(StandardPhonemes.VOICED_VELAR_PLOSIVE);
		phonologyModel.phonemesProperty().add(StandardPhonemes.CLOSE_MID_BRACK_UNROUNDED_VOWEL);
		phonologyModel.phonemesProperty().add(StandardPhonemes.VOICED_ALVEOLAR_FLAP);
		phonologyModel.phonemesProperty().add(StandardPhonemes.VOICELESS_RETROFLEX_AFFRICATE);

		phonologyModel.featuresProperty().addListener((Change<? extends PhonemeFeatureModel> c) -> {
			if (c.wasAdded() || c.wasRemoved()) {
				this.updatePhonemesTable();
			}
		});

		phonemesTable.itemsProperty().bind(phonologyModel.phonemesProperty());

		updatePhonemesTable();
	}

	private void updatePhonemesTable() {
		phonemesTable.getColumns().clear();

		var representationColumn = new TableColumn<Phoneme, String>("Representation");
		representationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().render(phonemeMapper)));
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
}
