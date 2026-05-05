package net.calebscode.langforge.app.core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppInfoDisplay {

	private Stage window;

	public AppInfoDisplay(String langforgeVersion) {
		Label appName = new Label("Langforge " + langforgeVersion);
		appName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

		Label javaInfo = new Label(String.format(
			"Running Java %s and JavaFX %s",
			System.getProperty("java.version"),
			System.getProperty("javafx.version")
		));

		VBox layout = new VBox(appName, javaInfo);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(5));
		layout.setMinSize(280, 100);

		Scene scene = new Scene(layout);

		window = new Stage();
		window.setScene(scene);
		window.setResizable(false);
		window.setTitle("Application Info");
	}

	public void show() {
		window.show();
	}

	public void hide() {
		window.hide();
	}

}
