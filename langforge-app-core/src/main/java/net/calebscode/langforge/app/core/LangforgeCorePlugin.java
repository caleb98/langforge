package net.calebscode.langforge.app.core;

import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.LangforgePluginException;
import net.calebscode.langforge.app.plugin.MenuDefinition;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public class LangforgeCorePlugin implements LangforgePlugin {

	public static final String ID = "langforge.core";
	public static final String NAME = "Langforge Core";
	public static final String DESCRIPTION = "The core Langforge UI application features.";

	private static final int FILE_MENU_INDEX = 0;
	private static final int EDIT_MENU_INDEX = 100;
	private static final int HELP_MENU_INDEX = 1000;

	private AppInfoDisplay infoDisplay;

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public VersionNumber getVersion() {
		return LangforgeApplication.VERSION;
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.VERSION;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return Map.of();
	}

	@Override
	public void init(LangforgePluginContext context) throws LangforgePluginException {

	}

	@Override
	public void load(LangforgePluginContext context) throws LangforgePluginException {
		infoDisplay = new AppInfoDisplay();

		context.addMenus(
			new MenuDefinition("File", FILE_MENU_INDEX),
			new MenuDefinition("Edit", EDIT_MENU_INDEX),
			new MenuDefinition("Help", HELP_MENU_INDEX));

		var exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(event -> {
			Platform.exit();
		});

		var appInfoMenuItem = new MenuItem("Application Info");
		appInfoMenuItem.setOnAction(event -> {
			infoDisplay.show();
		});

		context.addMenuItems(
			new MenuItemDefinition("File", () -> exitMenuItem),
			new MenuItemDefinition("Help", () -> appInfoMenuItem));
	}

}
