package net.calebscode.langforge.app.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import net.calebscode.langforge.app.DuplicatePluginIdException;

public class AlertHelper {

	public static void showExceptionAlert(Exception ex) {
		showExceptionAlert(ex, "Error", ex.getMessage());
	}

	public static void showExceptionAlert(Exception ex, String title) {
		showExceptionAlert(ex, title, ex.getMessage());
	}

	public static void showExceptionAlert(Exception ex, String title, String message) {
		var alert = new Alert(
			AlertType.ERROR,
			message
		);

		alert.setHeaderText("Error");

		var stringWriter = new StringWriter();
		var printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		var textArea = new TextArea(stringWriter.toString());
		alert.getDialogPane().setContent(textArea);

		alert.showAndWait();
	}

	public static void displayDuplicatePluginIdAlert(DuplicatePluginIdException duplicate) {
		var messageBuilder = new StringBuilder();
		messageBuilder.append(String.format(
			"""
			Multiple plugins found with the same plugin id.

			Conflicting id: %s
			""",
			duplicate.getPluginId()
		));

		for (var plugin : duplicate.getPlugins()) {
			messageBuilder.append(String.format(
				"\t%s (%s)%n",
				plugin.getName(),
				plugin.getVersion()
			));
		}

		messageBuilder.append("\nPlease address these conflicts then relaunch the application.");

		Alert alert = new Alert(AlertType.ERROR, messageBuilder.toString());
		alert.showAndWait();
	}

}
