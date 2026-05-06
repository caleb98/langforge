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

	/// Called once when the application first starts. The order in which each plugin has this
	/// method called is determined by the plugin's dependencies. If this plugin is dependent on
	/// another plugin, it is guaranteed that that plugin will have its
	/// [initialize()][LangforgePlugin#initialize()] method called before this one.
	public abstract void initialize();

	/// Called once when the application exits. Plugins are deinitialized in reverse order of how
	/// they were initialized.
	public abstract void deinitialize();

	/// Called whenever a project is loaded and no existing save state is available for this plugin.
	/// This could occur when a new project is created, or if a project is loaded for the first time
	/// while this plugin is installed. Plugins are loaded in dependency order.
	public abstract void load();

	/// Called whenever a project is loaded and a save state is available for this plugin. The state
	/// passed to this method will already have been migrated using the required migrations that are
	/// provided by this plugin. Plugins are loaded in dependency order.
	public abstract void load(SaveLoadValue state);

	/// Called whenever a project is saved. This method should return the state of this plugin in
	/// the form of a [SaveLoadValue], or [Optional#empty()] if it has no state to persist.
	public abstract Optional<SaveLoadValue> save();

	/// Called whenever a project is unloaded. Plugins are loaded in reverse dependency order.
	public abstract void unload();

	/// Returns a list of [Migration]s that should be used to migrate save data for this plugin.
	public abstract Collection<Migration> getMigrations();

}
