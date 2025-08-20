package net.calebscode.langforge.app.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

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
			var message = new Label("Failed to load component " + getClass().getCanonicalName() + ": " + ex.getMessage());

			var stackTraceWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stackTraceWriter));
			var stackTrace = new TextArea(stackTraceWriter.toString());

			var errorDisplay = new VBox(message, stackTrace);
			getChildren().add(errorDisplay);
			return;
		}

		onSuccess.run();
	}

}
