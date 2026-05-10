package net.calebscode.langforge.app;

import static net.calebscode.langforge.app.ui.AlertHelper.displayDuplicatePluginIdAlert;
import static net.calebscode.langforge.app.ui.AlertHelper.showExceptionAlert;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeApplication extends Application {

	public static final VersionNumber VERSION_0_0_1 = new VersionNumber(0, 0, 1);

	static final VersionNumber CURRENT_VERSION = VERSION_0_0_1;

	private ApplicationManager appManager;

	private LangforgeApplicationModel appModel;
	private LangforgeApplicationController ui;

	@Override
	public void start(Stage primaryStage) throws Exception {
		appModel = new LangforgeApplicationModel();
		ui = new LangforgeApplicationController(appModel);

		appManager = new ApplicationManager(primaryStage, appModel);

		try {
			appManager.initializePlugins();
		} catch (DuplicatePluginIdException duplicate) {
			displayDuplicatePluginIdAlert(duplicate);
			Platform.exit();
		} catch (LangforgePluginException ex) {
			showExceptionAlert(
				ex,
				"Plugin Error",
				"An exception occurred while initializing plugins."
			);
			Platform.exit();
		}

		appManager.loadPluginStates();

		primaryStage.setOnCloseRequest(appManager::onApplicationClose);

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

}
