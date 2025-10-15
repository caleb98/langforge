package net.calebscode.langforge.app.lexicon;
import java.util.Map;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.LangforgePluginException;
import net.calebscode.langforge.app.core.LangforgeCorePlugin;
import net.calebscode.langforge.app.lexicon.controller.LexiconController;
import net.calebscode.langforge.app.lexicon.model.LexiconModel;
import net.calebscode.langforge.app.phonology.LangforgeCorePhonologyPlugin;
import net.calebscode.langforge.app.phonology.api.LangforgeCorePhonologyApi;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeCoreLexiconPlugin implements LangforgePlugin {

	private static final String ID = "langforge.lexicon";
	private static final String NAME = "Langforge Core - Lexicon";
	private static final String DESCRIPTION = "The core Langforge lexicon features.";

	private LangforgePluginContext context;
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
		return LangforgeApplication.VERSION;
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.VERSION;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return Map.of(
			LangforgeCorePlugin.ID, LangforgeApplication.VERSION,
			LangforgeCorePhonologyPlugin.ID, LangforgeApplication.VERSION
		);
	}

	@Override
	public void init(LangforgePluginContext context) throws LangforgePluginException {
		this.context = context;

		lexiconModel = new LexiconModel();
	}

	@Override
	public void load(LangforgePluginContext context) throws LangforgePluginException {
		var maybePhonologyApi =  context.getApi(LangforgeCorePhonologyApi.class);
		phonologyApi = maybePhonologyApi.orElseThrow(() -> new LangforgePluginException("Failed to retrieve phonology API."));

		var lexiconMenuItem = new MenuItem("Lexicon");
		lexiconMenuItem.setOnAction(event -> {
			showLexiconTab();
		});
		context.addMenuItem(new MenuItemDefinition("Edit", () -> lexiconMenuItem));
		showLexiconTab();
	}

	private void showLexiconTab() {
		if (lexiconTabVisible) {
			return;
		}

		var lexiconController = new LexiconController(lexiconModel, phonologyApi.getPhonologyModel());
		lexiconTabVisible = true;
		var tab = new Tab("Lexicon", lexiconController);
		tab.setOnClosed(event -> lexiconTabVisible = false);

		context.createTab(tab);
	}

}