package net.calebscode.langforge.app.phonology;

import java.util.Map;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.AnchorPane;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.phonology.phoneme.Phoneme;

public class PhonologyView extends AnchorPane {

	private LangforgePluginContext context;
	private final ReadOnlyObjectWrapper<ConlangPhonologyModel> phonologyModel;

	public PhonologyView(LangforgePluginContext context) {
		this.context = context;
		this.phonologyModel = new ReadOnlyObjectWrapper<>(new ConlangPhonologyModel());

		phonologyModel.getValue().phonemesProperty().add(new Phoneme(Map.of()));

		var featureDefinitionsView = new PhonemeFeatureDefinitionsView(phonologyModel.get().getFeatureDefinitions());
		var phonemeEditor = new PhonemeEditor(phonologyModel.getReadOnlyProperty());
		var inventoryEditor = new PhonologicalInventoryEditor();

//		getChildren().add(featureDefinitionsView);
		getChildren().add(phonemeEditor);
//		getChildren().add(inventoryEditor);

		AnchorPane.setTopAnchor(featureDefinitionsView, 0.0);
		AnchorPane.setBottomAnchor(featureDefinitionsView, 0.0);
		AnchorPane.setLeftAnchor(featureDefinitionsView, 0.0);
		AnchorPane.setRightAnchor(featureDefinitionsView, 0.0);

		AnchorPane.setTopAnchor(phonemeEditor, 0.0);
		AnchorPane.setBottomAnchor(phonemeEditor, 0.0);
		AnchorPane.setLeftAnchor(phonemeEditor, 0.0);
		AnchorPane.setRightAnchor(phonemeEditor, 0.0);

		AnchorPane.setTopAnchor(inventoryEditor, 0.0);
		AnchorPane.setBottomAnchor(inventoryEditor, 0.0);
		AnchorPane.setLeftAnchor(inventoryEditor, 0.0);
		AnchorPane.setRightAnchor(inventoryEditor, 0.0);
	}

}
