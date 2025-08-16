package net.calebscode.langforge.app.lexicon.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import net.calebscode.langforge.LexicalCategory;
import net.calebscode.langforge.app.lexicon.model.LexiconEntryModel;
import net.calebscode.langforge.app.lexicon.model.LexiconModel;
import net.calebscode.langforge.app.lexicon.ui.LexiconWordTableCell;
import net.calebscode.langforge.app.lexicon.ui.RemoveLexicalCategoryDialog;
import net.calebscode.langforge.app.lexicon.util.LexicalCategoryStringConverter;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class LexiconController extends HBox implements FXMLController {

	@FXML private TableView<LexiconEntryModel> wordsTable;

	@FXML private TableColumn<LexiconEntryModel, PhonemeSequence> wordColumn;
	@FXML private TableColumn<LexiconEntryModel, LexicalCategory> categoryColumn;
	@FXML private TableColumn<LexiconEntryModel, String> definitionColumn;

	private final LexiconModel lexiconModel;

	public LexiconController(LexiconModel lexiconModel, LanguagePhonologyModel phonologyModel) {
		this.lexiconModel = lexiconModel;

		load(() -> {
			wordsTable.itemsProperty().bind(lexiconModel.entriesProperty());

			wordColumn.setCellFactory(column -> {
				var cell = new LexiconWordTableCell<LexiconEntryModel>(
					StandardPhonemes.IPA_MAPPER,
					phonologyModel
				);
				cell.setEditable(true);
				return cell;
			});
			wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());

			categoryColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(
				new LexicalCategoryStringConverter(),
				lexiconModel.categoriesProperty()));
			categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

			definitionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			definitionColumn.setCellValueFactory(cellData -> cellData.getValue().definitionProperty());
		});
	}

	@FXML
	private void onAddWordClicked() {
		var model = new LexiconEntryModel();
		model.categoryProperty().set(lexiconModel.categoriesProperty().getFirst());
		lexiconModel.entriesProperty().add(model);
	}

	static int count = 0;
	static List<LexicalCategory> categories = new ArrayList<>();

	@FXML
	private void onAddLexicalCategoryClicked() {
		var createButton = new ButtonType("Create", ButtonData.OK_DONE);
		var cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		var dialog = new TextInputDialog();
		dialog.setTitle("Add Lexical Category");
		dialog.setHeaderText("Enter the lexical category name:");
		dialog.getDialogPane().getButtonTypes().setAll(createButton, cancelButton);
		dialog.setGraphic(null);

		dialog
			.showAndWait()
			.map(LexicalCategory::new)
			.filter(category -> !lexiconModel.categoriesProperty().contains(category))
			.ifPresent(category -> lexiconModel.categoriesProperty().add(category));
	}

	@FXML
	private void onRemoveLexicalCategoryClicked() {
		var dialog = new RemoveLexicalCategoryDialog(lexiconModel);
		dialog
			.showAndWait()
			.ifPresent(result -> {
				lexiconModel.removeCategory(result.removeCategory(), result.replacementCategory());
			});
	}

}
