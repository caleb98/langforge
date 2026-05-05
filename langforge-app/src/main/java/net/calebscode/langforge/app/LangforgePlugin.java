package net.calebscode.langforge.app;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.util.VersionNumber;

public abstract class LangforgePlugin {

	private LangforgePluginContext context;

	protected LangforgePluginContext getContext() {
		return context;
	}

	void setContext(LangforgePluginContext context) {
		this.context = context;
	}

	public abstract String getId();
	public abstract String getName();
	public abstract String getDescription();
	public abstract VersionNumber getVersion();
	public abstract VersionNumber getRequiredLangforgeVersion();
	public abstract Map<String, VersionNumber> getDependencies();

	public abstract Optional<SaveLoadValue> getState();
	public abstract void setState(Optional<SaveLoadValue> maybeState);
	public abstract Collection<Migration> getMigrations();

	public abstract void initialize() throws LangforgePluginException;

}
