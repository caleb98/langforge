package net.calebscode.langforge.app.phonology;

import javafx.scene.layout.Pane;
import net.calebscode.langforge.app.LangforgePluginContext;

public class PhonologyPane extends Pane {

	private LangforgePluginContext context;
	private PhonemeFeatureDefinitions features;

	public PhonologyPane(LangforgePluginContext context) {
		this.context = context;

		features = PhonemeFeatureDefinitions.createStandardPhonemes();

		getChildren().add(new PhonemeEditor(features));
	}

}
