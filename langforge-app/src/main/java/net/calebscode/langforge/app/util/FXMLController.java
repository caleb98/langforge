package net.calebscode.langforge.app.util;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

public interface FXMLController {

	ObservableList<Node> getChildren();

	default void load(Runnable onSuccess) {
		var clazz = this.getClass();
		var fxmlFile = "fxml/" + clazz.getSimpleName().replace("Controller", "View") + ".fxml";
		var loader = new FXMLLoader(clazz.getClassLoader().getResource(fxmlFile));

		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			getChildren().add(new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage()));
			return;
		}

		onSuccess.run();
	}

}
