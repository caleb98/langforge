package net.calebscode.langforge.app.phonology.controller;

import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryMapModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCollectionModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternEditorModel;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class SyllableManagementController extends AnchorPane implements FXMLController {

	private IpaPhonemeMapper phonemeMapper = StandardPhonemes.IPA_MAPPER;

	private SyllablePatternCategoryMapModel syllablePatternCategories;
	private SyllablePatternCollectionModel syllablePatterns;

	@FXML private ListView<Character> categoriesList;
	@FXML private ListView<Phoneme> phonemesList;
	@FXML private ListView<Phoneme> inventoryList;

	@FXML private ListView<StringProperty> patternsList;

	public SyllableManagementController(LanguagePhonologyModel phonologyModel) {
		syllablePatternCategories = phonologyModel.getSyllablePatternCategories();
		syllablePatterns = phonologyModel.getSyllablePatterns();

		load(() -> {
			var phonologicalInventory = phonologyModel.getPhonologicalInventory();

			categoriesList.itemsProperty().bind(createObjectBinding(() -> {
				return observableArrayList(syllablePatternCategories.categoryMapProperty().keySet());
			}, syllablePatternCategories.categoryMapProperty()));

			categoriesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			phonemesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			inventoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			phonemesList.setCellFactory(this::mappedPhonemeCellFactory);
			inventoryList.setCellFactory(this::mappedPhonemeCellFactory);

			// The phonemes list should be loaded based on the currently selected category.
			phonemesList.itemsProperty().bind(createObjectBinding(
				() -> {
					var category = categoriesList.getSelectionModel().getSelectedItem();
					if (category == null) {
						return observableArrayList();
					}
					return observableArrayList(syllablePatternCategories.categoryMapProperty().get(category));
				},
				categoriesList.getSelectionModel().selectedItemProperty(),
				syllablePatternCategories.categoryMapProperty()));

			// The inventory list should display all phonemes that are not currently present
			// in the selected category's phonemes list.
			inventoryList.itemsProperty().bind(createObjectBinding(
				() -> {
					var phonemes = observableArrayList(phonologicalInventory.phonemesProperty());
					phonemes.removeAll(phonemesList.itemsProperty().get());
					return phonemes;
				},
				phonologyModel.getPhonologicalInventory().phonemesProperty(),
				phonemesList.itemsProperty()));

			// If elements are removed from the language's phonology, they should also be removed from
			// any syllable categories.
			phonologicalInventory.phonemesProperty().addListener(this::phonemesUpdated);

			patternsList.setCellFactory(this::syllablePatternEditorModelCellFactory);
			patternsList.itemsProperty().bind(syllablePatterns.patternsProperty());
		});
	}

	@FXML
	private void createCategory(ActionEvent event) {
		var categoryDialog = new TextInputDialog();
		categoryDialog.setTitle("New Category");
		categoryDialog.setContentText("Category Character:");
		categoryDialog.setGraphic(null);
		categoryDialog.setHeaderText(null);

		categoryDialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			String corrected = newValue.toUpperCase().replaceAll("[^A-Za-z]*", "");

			if (!newValue.equals(corrected)) {
				categoryDialog.getEditor().setText(corrected);
			}

			if (corrected.length() > 1) {
				categoryDialog.getEditor().setText("" + corrected.charAt(0));
			}
		});

		var maybeResult = categoryDialog.showAndWait();

		if (maybeResult.isPresent()) {
			var category = maybeResult.get();
			syllablePatternCategories.addCategory(category.charAt(0));
		}
	}

	@FXML
	private void removeCategory(ActionEvent event) {
		var category = categoriesList.getSelectionModel().getSelectedItem();
		if (category != null) {
			syllablePatternCategories.removeCategory(category);
		}
	}

	@FXML
	private void addToCategory(ActionEvent event) {
		var toAdd = new ArrayList<>(inventoryList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toAdd) {
			syllablePatternCategories.addPhoneme(category, phoneme);
		}
	}

	@FXML
	private void removeFromCategory(ActionEvent event) {
		var toRemove = new ArrayList<>(phonemesList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toRemove) {
			syllablePatternCategories.removePhoneme(category, phoneme);
		}
	}

	@FXML
	private void createPattern(ActionEvent event) {
		syllablePatterns
			.patternsProperty()
			.add(new SimpleStringProperty(""));
	}

	@FXML
	private void deletePattern(ActionEvent event) {
		var toRemove = new ArrayList<>(patternsList.getSelectionModel().getSelectedItems());

		for (var pattern : toRemove) {
			syllablePatterns.patternsProperty().remove(pattern);
		}

	}

	private void phonemesUpdated(Change<? extends Phoneme> change) {
		while (change.next()) {
			for (var removed : change.getRemoved()) {
				syllablePatternCategories.removePhonemeForAllCategories(removed);
			}
		}
	}

	private ListCell<StringProperty> syllablePatternEditorModelCellFactory(ListView<StringProperty> list) {
		return new ListCell<>() {
			SyllablePatternEditorModel model;
			SyllablePatternEditorController editor;

			{
				model = new SyllablePatternEditorModel();
				editor = new SyllablePatternEditorController(model);

				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				setText(null);
				prefWidthProperty().bind(list.widthProperty().subtract(2));
			}

			@Override
			protected void updateItem(StringProperty item, boolean empty) {
				super.updateItem(item, empty);
				model.patternProperty().unbind();
				System.out.println("updated!");

				if (empty || item == null) {
					setGraphic(null);
				} else {
					model.patternProperty().bindBidirectional(item);
					setGraphic(editor);
				}
			}
		};
	}

	private ListCell<Phoneme> mappedPhonemeCellFactory(ListView<Phoneme> list) {
		return new ListCell<>() {
			@Override
			protected void updateItem(Phoneme item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item.render(phonemeMapper));
				}
			}
		};
	}

}
