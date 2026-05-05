package net.calebscode.langforge.app.lexicon;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.LangforgePluginException;
import net.calebscode.langforge.app.core.LangforgeCorePlugin;
import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.lexicon.controller.LexiconController;
import net.calebscode.langforge.app.lexicon.model.LexiconModel;
import net.calebscode.langforge.app.phonology.LangforgeCorePhonologyPlugin;
import net.calebscode.langforge.app.phonology.api.LangforgeCorePhonologyApi;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeCoreLexiconPlugin extends LangforgePlugin {

	private static final String ID = "langforge.lexicon";
	private static final String NAME = "Langforge Core - Lexicon";
	private static final String DESCRIPTION = "The core Langforge lexicon features.";

	private LexiconModel lexiconModel;
	private LangforgeCorePhonologyApi phonologyApi;

	private boolean lexiconTabVisible = false;

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
		return Map.of(
			LangforgeCorePlugin.ID, LangforgeApplication.VERSION_0_0_1,
			LangforgeCorePhonologyPlugin.ID, LangforgeApplication.VERSION_0_0_1
		);
	}

	@Override
	public Optional<SaveLoadValue> getState() {
		return Optional.empty();
	}

	@Override
	public void setState(Optional<SaveLoadValue> maybeState) {
		if (maybeState.isEmpty()) {
			lexiconModel = new LexiconModel();
		}
		else {
			// TODO: configure from state
		}
	}

	@Override
	public Collection<Migration> getMigrations() {
		return List.of();
	}

	@Override
	public void initialize() throws LangforgePluginException {
		var maybePhonologyApi =  getContext().getApi(LangforgeCorePhonologyApi.class);
		phonologyApi = maybePhonologyApi.orElseThrow(() -> new LangforgePluginException("Failed to retrieve phonology API."));

		var lexiconMenuItem = new MenuItem("Lexicon");
		lexiconMenuItem.setOnAction(_ -> {
			showLexiconTab();
		});
		getContext().addMenuItem(new MenuItemDefinition("Edit", () -> lexiconMenuItem));
		showLexiconTab();
	}

	private void showLexiconTab() {
		if (lexiconTabVisible) {
			return;
		}

		var lexiconController = new LexiconController(lexiconModel, phonologyApi.getPhonologyModel());
		lexiconTabVisible = true;
		var tab = new Tab("Lexicon", lexiconController);
		tab.setOnClosed(_ -> lexiconTabVisible = false);

		getContext().createTab(tab);
	}

}