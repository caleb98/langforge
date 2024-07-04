package net.calebscode.langforge.app;

import java.util.ServiceLoader;

import javafx.application.Application;
import javafx.stage.Stage;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.VersionNumber;

public final class LangforgeApplication extends Application {

	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 0;
	public static final int VERSION_PATCH = 1;

	public static final VersionNumber VERSION = new VersionNumber(VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH);

	private LangforgeApplicationView ui;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ui = new LangforgeApplicationView();

		loadPlugins();

		primaryStage.setScene(ui.getScene());
		primaryStage.show();
	}

	public LangforgeApplicationView getUI() {
		return ui;
	}

	private void loadPlugins() {
		ServiceLoader<LangforgePlugin> pluginLoader = ServiceLoader.load(LangforgePlugin.class);
		pluginLoader.forEach(this::loadPlugin);
	}

	private void loadPlugin(LangforgePlugin plugin) {
		try {
			if (VERSION.compareTo(plugin.getRequiredLangforgeVersion()) < 0) {
				throw new LangforgePluginException(String.format(
					"Plugin requires Langforge version %s; current version is %s.",
					plugin.getRequiredLangforgeVersion(),
					VERSION
				));
			}

			plugin.load(this);

			System.out.printf(
				"Loaded plugin: %s %s (%s) - %s%n",
				plugin.getName(),
				plugin.getVersion(),
				plugin.getId(),
				plugin.getDescription()
			);
		}
		catch (LangforgePluginException ex) {
			System.err.printf(
				"Unable to load plugin '%s' (%s): %s\n",
				plugin.getName(),
				plugin.getId(),
				ex.getMessage());
		}
	}

}
