package net.calebscode.langforge.app;

import java.util.Map;

import net.calebscode.langforge.app.util.VersionNumber;

public interface LangforgePlugin {

	public String getId();
	public String getName();
	public String getDescription();
	public VersionNumber getVersion();
	public VersionNumber getRequiredLangforgeVersion();
	public Map<String, VersionNumber> getDependencies();

	public void init(LangforgePluginContext context) throws LangforgePluginException;
	public void load(LangforgePluginContext context) throws LangforgePluginException;

}
