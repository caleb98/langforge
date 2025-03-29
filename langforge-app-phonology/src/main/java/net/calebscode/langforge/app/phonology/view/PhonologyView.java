package net.calebscode.langforge.app.phonology.view;

import java.io.IOException;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonemeFeatureModel;
import net.calebscode.langforge.app.ui.ButtonTableCell;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class PhonologyView extends AnchorPane {

	private LangforgePluginContext context;
	private LanguagePhonologyModel phonologyModel;
	private IpaPhonemeMapper phonemeMapper = StandardPhonemes.IPA_MAPPER;

	private Optional<Stage> consonantPicker = Optional.empty();
	private Optional<Stage> vowelPicker = Optional.empty();

	@FXML private TableView<Phoneme> phonemesTable;

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

	@FXML
	private void showIpaConsonantPicker(MouseEvent event) {
		if (consonantPicker.isPresent()) {
			return;
		}

		var ipaSelector = new IpaConsonantPicker(phonologyModel);
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

		var ipaSelector = new IpaVowelPicker(phonologyModel);
		var scene = new Scene(ipaSelector);
		var stage = new Stage();
		stage.setTitle("IPA Vowels");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(e -> vowelPicker = Optional.empty());

		vowelPicker = Optional.of(stage);
	}
}
