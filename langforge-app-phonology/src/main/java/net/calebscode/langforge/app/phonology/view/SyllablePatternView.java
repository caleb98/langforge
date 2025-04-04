package net.calebscode.langforge.app.phonology.view;

import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryModel;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class SyllablePatternView extends AnchorPane {

	private IpaPhonemeMapper phonemeMapper = StandardPhonemes.IPA_MAPPER;

	private PhonologicalInventoryModel phonologyModel;
	private SyllablePatternCategoryModel syllableModel;

	@FXML private ListView<Character> categoriesList;
	@FXML private ListView<Phoneme> phonemesList;
	@FXML private ListView<Phoneme> inventoryList;

	public SyllablePatternView(PhonologicalInventoryModel phonologyModel, SyllablePatternCategoryModel syllableModel) {
		this.phonologyModel = phonologyModel;
		this.syllableModel = syllableModel;

		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SyllablePatternView.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		categoriesList.itemsProperty().bind(Bindings.createObjectBinding(() -> {
			return observableArrayList(syllableModel.categoryMapProperty().keySet());
		}, syllableModel.categoryMapProperty()));

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
				return observableArrayList(syllableModel.categoryMapProperty().get(category));
			},
			categoriesList.getSelectionModel().selectedItemProperty(),
			syllableModel.categoryMapProperty()));

		// The inventory list should display all phonemes that are not currently present
		// in the selected category's phonemes list.
		inventoryList.itemsProperty().bind(createObjectBinding(
			() -> {
				var phonemes = observableArrayList(phonologyModel.phonemesProperty());
				phonemes.removeAll(phonemesList.itemsProperty().get());
				return phonemes;
			},
			phonologyModel.phonemesProperty(),
			phonemesList.itemsProperty()));

		// If elements are removed from the language's phonology, they should also be removed from
		// any syllable categories.
		phonologyModel.phonemesProperty().addListener(this::phonemesUpdated);
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
			syllableModel.addCategory(category.charAt(0));
		}
	}

	@FXML
	private void removeCategory(ActionEvent event) {
		var category = categoriesList.getSelectionModel().getSelectedItem();
		if (category != null) {
			syllableModel.removeCategory(category);
		}
	}

	@FXML
	private void addToCategory(ActionEvent event) {
		var toAdd = new ArrayList<>(inventoryList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toAdd) {
			syllableModel.addPhoneme(category, phoneme);
		}
	}

	@FXML
	private void removeFromCategory(ActionEvent event) {
		var toRemove = new ArrayList<>(phonemesList.getSelectionModel().getSelectedItems());
		var category = categoriesList.getSelectionModel().getSelectedItem();

		for (var phoneme : toRemove) {
			syllableModel.removePhoneme(category, phoneme);
		}
	}

	private void phonemesUpdated(Change<? extends Phoneme> change) {
		while (change.next()) {
			for (var removed : change.getRemoved()) {
				syllableModel.removePhonemeForAllCategories(removed);
			}
		}
	}

	private ListCell<Phoneme> mappedPhonemeCellFactory(ListView<Phoneme> list) {
		return new ListCell<Phoneme>() {
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
