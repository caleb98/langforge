package net.calebscode.langforge.app.core;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.calebscode.langforge.app.LangforgeApplication;

public class AppInfoDisplay {

	private Stage window;
	private Label test;

	public AppInfoDisplay() {
		Label appName = new Label("Langforge");
		appName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");

		Label appVersion = new Label("Version: " + LangforgeApplication.VERSION);

		test = new Label("foo");

		VBox layout = new VBox(appName, appVersion, test);
		layout.setPadding(new Insets(5));
		layout.setMinSize(280, 100);

		Scene scene = new Scene(layout);

		window = new Stage();
		window.setScene(scene);
		window.setResizable(false);
		window.setTitle("Application Info");
	}

	public StringProperty getTestProperty() {
		return test.textProperty();
	}

	public void show() {
		window.show();
	}

	public void hide() {
		window.hide();
	}

}
