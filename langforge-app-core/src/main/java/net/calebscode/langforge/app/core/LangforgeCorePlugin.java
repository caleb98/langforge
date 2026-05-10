package net.calebscode.langforge.app.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import net.calebscode.langforge.app.LangforgeAppInfoApi;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.plugin.MenuDefinition;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeCorePlugin extends LangforgePlugin {

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
		return LangforgeApplication.VERSION_0_0_1;
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.VERSION_0_0_1;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return Map.of();
	}

	@Override
	public void initialize() {
		var maybeInfo = getContext().getApi(LangforgeAppInfoApi.class);
		if (maybeInfo.isEmpty()) {
			throw new IllegalStateException("Couldn't get the LangforgeAppInfoApi.");
		}

		var appInfo = maybeInfo.get();
		infoDisplay = new AppInfoDisplay(appInfo.getCurrentVersion().toString());

		getContext().addMenus(
			new MenuDefinition("File", FILE_MENU_INDEX),
			new MenuDefinition("Edit", EDIT_MENU_INDEX),
			new MenuDefinition("Help", HELP_MENU_INDEX));

		var saveAsMenuItem = new MenuItem("Save As...");
		saveAsMenuItem.setOnAction(_ -> {
			getContext().requestSave();
		});

		var exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(_ -> {
			Platform.exit();
		});

		var appInfoMenuItem = new MenuItem("Application Info");
		appInfoMenuItem.setOnAction(_ -> {
			infoDisplay.show();
		});

		getContext().addMenuItems(
			new MenuItemDefinition("File", () -> saveAsMenuItem),
			new MenuItemDefinition("File", () -> exitMenuItem),
			new MenuItemDefinition("Help", () -> appInfoMenuItem));
	}

	@Override
	public void deinitialize() {

	}

	@Override
	public void load() {

	}

	@Override
	public void load(SaveLoadValue state) {

	}

	@Override
	public Optional<SaveLoadValue> save() {
		return Optional.empty();
	}

	@Override
	public void unload() {

	}

	@Override
	public Collection<Migration> getMigrations() {
		return List.of();
	}

}
