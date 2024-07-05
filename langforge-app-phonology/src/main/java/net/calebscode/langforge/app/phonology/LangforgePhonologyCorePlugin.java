package net.calebscode.langforge.app.phonology;

import java.util.Map;

import javafx.application.Platform;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgeApplicationHandle;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgePhonologyCorePlugin implements LangforgePlugin {

	private static final String ID = "langforge.core";
	private static final String NAME = "Langforge Core - Phonology";
	private static final String DESCRIPTION = "The core Langforge phonology features.";

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
		return Map.of(
			"langforge.core", new VersionNumber(2, 5, 1)
		);
	}

	@Override
	public void init(LangforgeApplicationHandle handle) throws LangforgePluginException {

	}

	@Override
	public void load(LangforgeApplicationHandle handle) throws LangforgePluginException {
		handle.addMenuItem(new MenuItemDefinition("Help", ID, "Test"));

		new Thread(() -> {
			try {
				Thread.sleep(7000);
				Platform.runLater(() -> {
					handle.addMenu(new MenuDefinition("File", 50));
					handle.addMenu(new MenuDefinition("Help", -10));
				});
			} catch (InterruptedException e) {}
		}).start();
	}

}
