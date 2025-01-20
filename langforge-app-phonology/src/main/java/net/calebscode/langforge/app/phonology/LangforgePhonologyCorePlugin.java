package net.calebscode.langforge.app.phonology;

import java.util.Map;

import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.util.VersionNumber;

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
		return Map.of("langforge.core", LangforgeApplication.VERSION);
	}

	@Override
	public void init(LangforgePluginContext context) throws LangforgePluginException {

	}

	@Override
	public void load(LangforgePluginContext context) throws LangforgePluginException {
		var tab = new Tab("Phonology", new PhonologyPane(context));
		context.createTab(tab);
	}

}
