package net.calebscode.langforge.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeApplication extends Application {

	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 0;
	public static final int VERSION_PATCH = 1;

	public static final VersionNumber VERSION = new VersionNumber(VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH);

	private PluginManager pluginManager;

	private LangforgeApplicationModel appModel;
	private LangforgeApplicationController ui;

	@Override
	public void start(Stage primaryStage) throws Exception {
		appModel = new LangforgeApplicationModel();
		ui = new LangforgeApplicationController(appModel);

		pluginManager = new PluginManager(appModel);

		try {
			pluginManager.loadPlugins();
		} catch (DuplicatePluginIdException duplicate) {
			displayDuplicatePluginIdAlert(duplicate);
			return;
		}

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
		pluginManager.savePluginStates();
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
