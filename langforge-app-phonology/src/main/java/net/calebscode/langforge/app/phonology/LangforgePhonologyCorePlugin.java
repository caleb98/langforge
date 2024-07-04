package net.calebscode.langforge.app.phonology;

import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.VersionNumber;

public final class LangforgePhonologyCorePlugin implements LangforgePlugin {

	private static final String ID = "langforge.phonology";
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
		return Map.of();
	}

	@Override
	public void load(LangforgeApplication app) throws LangforgePluginException {
		var ui = app.getUI();
		var menu = new Menu();
		var fileMenu = ui.getFileMenu();

		MenuItem save = new MenuItem("Save");
		fileMenu.getItems().add(save);

		var string = new SimpleStringProperty("Save");
		save.textProperty().bindBidirectional(string);
	}

}
