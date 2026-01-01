package net.calebscode.langforge.app.phonology.factory;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_SEQUENCE_RENDERER;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonemeButtonsCellFactory implements Callback<TableColumn<String, List<Phoneme>>, TableCell<String, List<Phoneme>>> {

	private PhonologicalInventoryModel model;

	public PhonemeButtonsCellFactory(PhonologicalInventoryModel model) {
		this.model = model;
	}

	@Override
	public TableCell<String, List<Phoneme>> call(TableColumn<String, List<Phoneme>> param) {
		return new TableCell<>() {

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
					var button = new Button(IPA_PHONEME_SEQUENCE_RENDERER.render(phoneme));
					button.setOnMouseClicked(_ -> {
						model.phonemesProperty().add(phoneme);
					});
					newButtons.add(button);
				}
				container.getChildren().setAll(newButtons);
			};

		};
	}

}
