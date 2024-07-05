package net.calebscode.langforge.app.core;

import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgeApplicationHandle;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public class LangforgeCorePlugin implements LangforgePlugin {

	private static final String ID = "langforge.core";
	private static final String NAME = "Langforge Core";
	private static final String DESCRIPTION = "The core Langforge UI application features.";

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
	public void init(LangforgeApplicationHandle handle) throws LangforgePluginException {

	}

	@Override
	public void load(LangforgeApplicationHandle handle) throws LangforgePluginException {
		infoDisplay = new AppInfoDisplay();

		handle.addMenus(
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

		handle.addMenuItems(
			new MenuItemDefinition("File", () -> exitMenuItem),
			new MenuItemDefinition("Help", () -> appInfoMenuItem));
	}

}
