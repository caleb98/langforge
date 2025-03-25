package net.calebscode.langforge.app.phonology;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonologicalInventoryEditor extends VBox {

	@FXML private TableView<Phoneme> phonemesTable;

	public PhonologicalInventoryEditor() {
		var loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/PhonologicalInventoryEditor.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}


	}

}
