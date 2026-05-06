package net.calebscode.langforge.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeApplication extends Application {

	public static final VersionNumber VERSION_0_0_1 = new VersionNumber(0, 0, 1);

	static final VersionNumber CURRENT_VERSION = VERSION_0_0_1;

	private PluginManager pluginManager;

	private LangforgeApplicationModel appModel;
	private LangforgeApplicationController ui;

	@Override
	public void start(Stage primaryStage) throws Exception {
		appModel = new LangforgeApplicationModel();
		ui = new LangforgeApplicationController(appModel);

		pluginManager = new PluginManager(appModel);

		try {
			pluginManager.initializePlugins();
		} catch (DuplicatePluginIdException duplicate) {
			displayDuplicatePluginIdAlert(duplicate);
			return;
		} catch (LangforgePluginException ex) {
			displayPluginInitializationErrorAlert(ex);
			return;
		}

		pluginManager.loadPluginStates();

		primaryStage.setOnCloseRequest(this::onApplicationClose);

		primaryStage.setScene(new Scene(ui, 650, 480));
		primaryStage.setTitle("Langforge");
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.show();
		primaryStage.requestFocus();

		// Force the window to open in front of other windows at launch.
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setAlwaysOnTop(false);
	}

	private void onApplicationClose(WindowEvent event) {
		try (var output = new FileOutputStream("./save.json")) {
			pluginManager.savePluginStates(output);
			pluginManager.unloadPlugins();
			pluginManager.deinitializePlugins();
		} catch (IOException ex) {
			var cancelButton = new ButtonType("Cancel");
			var exitButton = new ButtonType("Exit Anyway");

			Alert alert = new Alert(
				AlertType.ERROR,
				"Failed to save project: " + ex.getMessage(),
				cancelButton,
				exitButton
			);

			var selectedButton = alert.showAndWait().orElse(null);
			if (selectedButton == cancelButton) {
				event.consume();
			}
		}
	}

	private void displayPluginInitializationErrorAlert(LangforgePluginException ex) {
		Alert alert = new Alert(
			AlertType.ERROR,
			String.format("An exception occurred while initializing plugins:")
		);

		alert.setHeaderText("Plugin Exception");

		var stringWriter = new StringWriter();
		var printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		var textArea = new TextArea(stringWriter.toString());

		alert.getDialogPane().setExpandableContent(textArea);

		alert.showAndWait();
		Platform.exit();
	}

	private void displayDuplicatePluginIdAlert(DuplicatePluginIdException duplicate) {
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
		Platform.exit();
	}

}
