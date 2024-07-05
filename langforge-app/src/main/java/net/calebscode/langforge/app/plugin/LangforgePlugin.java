package net.calebscode.langforge.app.plugin;

import java.util.Map;

import net.calebscode.langforge.app.LangforgeApplicationHandle;
import net.calebscode.langforge.app.util.VersionNumber;

public interface LangforgePlugin {

	public String getId();
	public String getName();
	public String getDescription();
	public VersionNumber getVersion();
	public VersionNumber getRequiredLangforgeVersion();
	public Map<String, VersionNumber> getDependencies();

	public void init(LangforgeApplicationHandle handle) throws LangforgePluginException;
	public void load(LangforgeApplicationHandle handle) throws LangforgePluginException;

}
