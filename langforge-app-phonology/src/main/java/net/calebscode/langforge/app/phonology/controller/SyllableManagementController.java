package net.calebscode.langforge.app.phonology.controller;

import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;
import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_SEQUENCE_RENDERER;

import java.util.ArrayList;

import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DefaultStringConverter;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class SyllableManagementController extends AnchorPane implements FXMLController {

	private LanguagePhonologyModel phonologyModel;

	@FXML private ListView<Character> categoriesList;
	@FXML private ListView<Phoneme> phonemesList;
	@FXML private ListView<Phoneme> inventoryList;

	@FXML private ListView<String> patternsList;

	public SyllableManagementController(LanguagePhonologyModel phonologyModel) {
		this.phonologyModel = phonologyModel;

		load(() -> {
			var phonologicalInventory = phonologyModel.getPhonologicalInventory();
			var syllablePatternCategories = phonologyModel.getSyllablePatternCategories();

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

			patternsList.setCellFactory(this::syllablePatternListCellFactory);
			patternsList.itemsProperty().bind(phonologyModel.syllablePatternsProperty());
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
			phonologyModel.getSyllablePatternCategories().addCategory(category.charAt(0));
		}
	}

	@FXML
	private void removeCategory(ActionEvent event) {
		var category = categoriesList.getSelectionModel().getSelectedItem();
		if (category != null) {
			phonologyModel.getSyllablePatternCategories().removeCategory(category);
		}
	}

	@FXML
	private void addToCategory(ActionEvent event) {
		var toAdd = new ArrayList<>(inventoryList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toAdd) {
			phonologyModel.getSyllablePatternCategories().addPhoneme(category, phoneme);
		}
	}

	@FXML
	private void removeFromCategory(ActionEvent event) {
		var toRemove = new ArrayList<>(phonemesList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toRemove) {
			phonologyModel.getSyllablePatternCategories().removePhoneme(category, phoneme);
		}
	}

	@FXML
	private void createPattern(ActionEvent event) {
		phonologyModel
			.getSyllablePatterns()
			.add("");
	}

	@FXML
	private void deletePattern(ActionEvent event) {
		var toRemove = new ArrayList<>(patternsList.getSelectionModel().getSelectedItems());

		for (var pattern : toRemove) {
			phonologyModel.getSyllablePatterns().remove(pattern);
		}

	}

	private ListCell<String> syllablePatternListCellFactory(ListView<String> list) {
		return new TextFieldListCell<>(new DefaultStringConverter()) {
			@Override
			public void startEdit() {
				super.startEdit();
				setStyle("");
			}

			@Override
			public void cancelEdit() {
				super.cancelEdit();
				updatePlaceholderText();
			}

			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				updatePlaceholderText();
			}

			private void updatePlaceholderText() {
				if (isEmpty()) {
					return;
				}

				var item = getItem();
				if (item == null || item.isEmpty()) {
					setText("Enter pattern...");
					setStyle("-fx-font-style: italic;");
				}
				else {
					setText(item);
					setStyle("");
				}
			}
		};
	}

	private void phonemesUpdated(Change<? extends Phoneme> change) {
		while (change.next()) {
			for (var removed : change.getRemoved()) {
				phonologyModel.getSyllablePatternCategories().removePhonemeForAllCategories(removed);
			}
		}
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
					setText(IPA_PHONEME_SEQUENCE_RENDERER.render(item));
				}
			}
		};
	}

}
