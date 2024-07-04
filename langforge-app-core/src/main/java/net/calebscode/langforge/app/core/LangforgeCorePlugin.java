package net.calebscode.langforge.app.core;

import java.util.Map;

import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.VersionNumber;

public class LangforgeCorePlugin implements LangforgePlugin {

	private static final String ID = "langforge.core";
	private static final String NAME = "Langforge Core";
	private static final String DESCRIPTION = "The core Langforge UI application features.";

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
	public void load(LangforgeApplication handle) throws LangforgePluginException {

	}

}
