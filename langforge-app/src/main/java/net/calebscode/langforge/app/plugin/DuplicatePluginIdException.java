package net.calebscode.langforge.app.plugin;

import java.util.Collections;
import java.util.List;

public class DuplicatePluginIdException extends LangforgePluginException {

	private static final long serialVersionUID = -4055138759481316854L;

	private String pluginId;
	private List<LangforgePlugin> plugins;

	public DuplicatePluginIdException(String pluginId, List<LangforgePlugin> plugins) {
		super("Multiple plugins found with id \"" + pluginId + "\".");
		this.pluginId = pluginId;
		this.plugins = Collections.unmodifiableList(plugins);
	}

	public String getPluginId() {
		return pluginId;
	}

	public List<LangforgePlugin> getPlugins() {
		return plugins;
	}

}
