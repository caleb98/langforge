package net.calebscode.langforge.app.lexicon.ui;

import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.calebscode.langforge.LexicalCategory;
import net.calebscode.langforge.app.lexicon.model.LexiconModel;
import net.calebscode.langforge.app.lexicon.ui.RemoveLexicalCategoryDialog.RemoveLexicalCategoryParameters;
import net.calebscode.langforge.app.lexicon.util.LexicalCategoryStringConverter;

public class RemoveLexicalCategoryDialog extends Dialog<RemoveLexicalCategoryParameters> {

	public static record RemoveLexicalCategoryParameters(LexicalCategory removeCategory, LexicalCategory replacementCategory) {}

	private final ComboBox<LexicalCategory> removeCategoryEntry;
	private final ComboBox<LexicalCategory> replacementCategoryEntry;

	public RemoveLexicalCategoryDialog(LexiconModel lexiconModel) {
		setTitle("Remove Lexical Category");
		setGraphic(null);

		var removeCategoryLabel = new Label("Remove:");
		removeCategoryLabel.setMaxWidth(Double.MAX_VALUE);
		removeCategoryLabel.setAlignment(Pos.BASELINE_RIGHT);
		removeCategoryEntry = new ComboBox<>();
		removeCategoryEntry.setMaxWidth(Double.MAX_VALUE);
		removeCategoryEntry.itemsProperty().bind(lexiconModel.categoriesProperty());
		removeCategoryEntry.setConverter(new LexicalCategoryStringConverter());

		var replacementCategoryLabel = new Label("Replacement:");
		replacementCategoryLabel.setMaxWidth(Double.MAX_VALUE);
		replacementCategoryLabel.setAlignment(Pos.BASELINE_RIGHT);
		replacementCategoryEntry = new ComboBox<>();
		replacementCategoryEntry.setMaxWidth(Double.MAX_VALUE);
		replacementCategoryEntry.itemsProperty().bind(createObjectBinding(
			() -> {
				var list = new ArrayList<>(lexiconModel.categoriesProperty());
				list.remove(removeCategoryEntry.getSelectionModel().getSelectedItem());
				return observableArrayList(list);
			},
			lexiconModel.categoriesProperty(),
			removeCategoryEntry.getSelectionModel().selectedItemProperty()
		));
		replacementCategoryEntry.setConverter(new LexicalCategoryStringConverter());

		var grid = new GridPane(5, 5);
		grid.setPadding(new Insets(5));
		grid.add(removeCategoryLabel, 0, 0);
		grid.add(removeCategoryEntry, 1, 0);
		grid.add(replacementCategoryLabel, 0, 1);
		grid.add(replacementCategoryEntry, 1, 1);

		GridPane.setHgrow(removeCategoryEntry, Priority.ALWAYS);
		GridPane.setFillWidth(removeCategoryEntry, true);
		GridPane.setHgrow(replacementCategoryEntry, Priority.ALWAYS);
		GridPane.setFillWidth(replacementCategoryEntry, true);

		getDialogPane().setContent(grid);

		final var deleteButton = new ButtonType("Delete", ButtonData.OK_DONE);
		final var cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().setAll(deleteButton, cancelButton);
		getDialogPane()
			.lookupButton(deleteButton)
			.disableProperty()
			.bind(removeCategoryEntry.getSelectionModel().selectedItemProperty().isNull()
				.or(replacementCategoryEntry.getSelectionModel().selectedItemProperty().isNull())
			);

		setResultConverter(buttonType -> deleteButton.equals(buttonType) ?
			new RemoveLexicalCategoryParameters(
				removeCategoryEntry.getValue(),
				replacementCategoryEntry.getValue()
			) :
			null
		);
	}

}
