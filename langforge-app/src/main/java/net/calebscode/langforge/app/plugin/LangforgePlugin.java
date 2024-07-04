package net.calebscode.langforge.app.plugin;

import java.util.Map;

import net.calebscode.langforge.app.LangforgeApplication;

public interface LangforgePlugin {

	public String getId();
	public String getName();
	public String getDescription();
	public VersionNumber getVersion();
	public VersionNumber getRequiredLangforgeVersion();
	public Map<String, VersionNumber> getDependencies();

	public void load(LangforgeApplication handle) throws LangforgePluginException;

}
