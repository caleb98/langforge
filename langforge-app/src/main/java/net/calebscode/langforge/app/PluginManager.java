package net.calebscode.langforge.app;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.calebscode.langforge.app.data.JsonBackend;
import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.PersistenceBackend;
import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadString;
import net.calebscode.langforge.app.util.VersionNumber;

public final class PluginManager {

	private final static Logger logger = LoggerFactory.getLogger(PluginManager.class);


	private final LangforgeApplicationModel appModel;
	private final LangforgePluginApiProvider apiProvider = createApiProvider();
	private final PersistenceBackend persistenceBackend = new JsonBackend();
	private final Map<LangforgePluginContext, LangforgePlugin> contexts = new HashMap<>();

	private boolean isInitialized = false;
	private List<LangforgePlugin> pluginDependencyOrder = new ArrayList<>();

	public PluginManager(LangforgeApplicationModel appModel) {
		this.appModel = appModel;
	}

	public void initializePlugins() throws LangforgePluginException {
		if (isInitialized) {
			throw new IllegalStateException("Plugins have alread been initialized.");
		}

		isInitialized = true;

		ServiceLoader<LangforgePlugin> pluginLoader = ServiceLoader.load(LangforgePlugin.class);
		var plugins = pluginLoader.stream().map(Provider::get).toList();
		verifyNoDuplicatePluginIds(plugins);
		verifyPluginsSupported(plugins);

		var pluginDependencies = plugins
			.stream()
			.collect(toMap(
				plugin -> plugin,
				plugin -> new HashMap<>(plugin.getDependencies())
			));

		// While computing the load order, the pluginDependencies map is updated and dependencies
		// that are marked for load are removed. The result is that all successfully loaded plugins
		// should have no more entries in the pluginDependencies map. The presence of any
		// dependencies indicates that the dependency could not be satisfied, so log those.
		pluginDependencyOrder = computeDependencyOrder(plugins, pluginDependencies);
		logPluginsWithUnsatisfiedDependencies(pluginDependencies);

		try {
			pluginDependencyOrder.forEach(this::initializePlugin);
		} catch (Exception ex) {
			if (ex instanceof LangforgePluginException) {
				throw ex;
			}
			throw new LangforgePluginException("Failed to initialize plugin.", ex);
		}
	}

	public void deinitializePlugins() {
		pluginDependencyOrder.reversed().forEach(this::deinitializePlugin);
		pluginDependencyOrder.clear();
		isInitialized = false;
	}

	public void loadPluginStates() {
		for (var plugin : pluginDependencyOrder) {
			plugin.load();
			logger.info(
				"Loaded plugin: {} {} ({}) - {}",
				plugin.getName(),
				plugin.getVersion(),
				plugin.getId(),
				plugin.getDescription()
			);
		}
	}

	public void loadPluginStates(InputStream input)
	throws IOException, LangforgePluginException {
		SaveLoadObject infos = persistenceBackend.load(input);

		var loadedPlugins = new HashSet<String>();
		for (var plugin : pluginDependencyOrder) {
			var pluginId = plugin.getId();

			if (infos.containsKey(pluginId)) {
				loadPluginFromState(plugin, infos.get(plugin.getId()).asObject());
			}
			else {
				plugin.load();
			}

			loadedPlugins.add(pluginId);
			logger.info(
				"Loaded plugin: {} {} ({}) - {}",
				plugin.getName(),
				plugin.getVersion(),
				plugin.getId(),
				plugin.getDescription()
			);
		}
	}

	public void savePluginStates(OutputStream output) {
		var saveState = new SaveLoadObject();
		for (var plugin : contexts.values()) {
			var pluginState = plugin.save();

			if (pluginState.isEmpty()) {
				continue;
			}

			var pluginInfo = new SaveLoadObject();
			pluginInfo.put("state", pluginState.get());
			pluginInfo.put("version", new SaveLoadString(plugin.getVersion().toString()));
			saveState.put(plugin.getId(), pluginInfo);
		}

		try {
			persistenceBackend.save(output, saveState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unloadPlugins() {
		pluginDependencyOrder.reversed().forEach(LangforgePlugin::unload);
	}

	public LangforgePluginApiProvider getApiProvider() {
		return apiProvider;
	}

	private void initializePlugin(LangforgePlugin plugin) {
		var context = new LangforgePluginContext(appModel, apiProvider);
		plugin.setContext(context);
		appModel.registerPlugin(context);
		contexts.put(context, plugin);
		plugin.initialize();
	}

	private void deinitializePlugin(LangforgePlugin plugin) {
		plugin.deinitialize();
		var context = plugin.getContext();
		plugin.setContext(null);
		appModel.unregisterPlugin(context);
		contexts.remove(context);
	}

	private static void loadPluginFromState(LangforgePlugin plugin, SaveLoadObject stateInfo) {
		var stateVersion = VersionNumber.parse(stateInfo.get("version").asString().value());
		var state = stateInfo.get("state");

		var migrations = plugin
			.getMigrations()
			.stream()
			.filter(m -> m.targetVersion().compareTo(plugin.getVersion()) <= 0)
			.sorted(comparing(Migration::targetVersion))
			.toList();

		for (var migration : migrations) {
			if (stateVersion.compareTo(migration.targetVersion()) < 0) {
				logger.info(
					"Migrating save data for {} from version {} to {}",
					plugin.getId(),
					stateVersion,
					migration.targetVersion()
				);
				state = migration.migrate(state);
				stateVersion = migration.targetVersion();
			}
		}

		plugin.load(state);
	}

	private static void logPluginsWithUnsatisfiedDependencies(
		Map<LangforgePlugin, HashMap<String, VersionNumber>> pluginDependencies
	) {
		if (!pluginDependencies.isEmpty()) {
			var errorMessage = new StringBuilder();
			errorMessage.append(
				"The following plugins have dependencies that are not present"
				+ " or which failed to initialize. They will not be loaded.\n"
			);

			for (var entry : pluginDependencies.entrySet()) {
				var plugin = entry.getKey();
				var deps = entry.getValue();

				errorMessage.append(String.format(
					"\t%s (%s) [%s] missing:%n",
					plugin.getName(),
					plugin.getVersion(),
					plugin.getId()
				));

				for (var dep : deps.entrySet()) {
					errorMessage.append(String.format(
						"\t\t> %s with minimum version %s%n",
						dep.getKey(),
						dep.getValue()
					));
				}
			}

			logger.warn(errorMessage.toString());
		}
	}

	private static ArrayList<LangforgePlugin> computeDependencyOrder(
		List<LangforgePlugin> plugins,
		Map<LangforgePlugin, HashMap<String, VersionNumber>> pluginDependencies
	) {
		var dependencyOrder = new ArrayList<LangforgePlugin>();
		var check = new ArrayList<LangforgePlugin>();

		for (var plugin : plugins) {
			if (pluginDependencies.get(plugin).isEmpty()) {
				check.add(plugin);
				pluginDependencies.remove(plugin);
			}
		}

		while (!check.isEmpty()) {
			var currentPlugin = check.removeFirst();
			dependencyOrder.add(currentPlugin);

			var iter = pluginDependencies.entrySet().iterator();
			while(iter.hasNext()) {
				var entry = iter.next();
				var plugin = entry.getKey();
				var deps = entry.getValue();

				if (deps.containsKey(currentPlugin.getId())) {
					var requiredVersion = deps.get(currentPlugin.getId());
					var actualVersion = currentPlugin.getVersion();
					if (actualVersion.compareTo(requiredVersion) >= 0) {
						deps.remove(currentPlugin.getId());
					}
				}

				if (deps.isEmpty()) {
					iter.remove();
					check.add(plugin);
				}
			}
		}

		return dependencyOrder;
	}

	private static void verifyNoDuplicatePluginIds(List<LangforgePlugin> plugins)
	throws DuplicatePluginIdException {
		var grouped = plugins.stream().collect(Collectors.groupingBy(LangforgePlugin::getId));

		for (var entry : grouped.entrySet()) {
			var pluginsForId = entry.getValue();

			if (pluginsForId.size() > 1) {
				throw new DuplicatePluginIdException(entry.getKey(), pluginsForId);
			}
		}
	}

	private static void verifyPluginsSupported(List<LangforgePlugin> plugins)
	throws LangforgePluginException {
		for (var plugin : plugins) {
			VersionNumber pluginRequiredVersion = plugin.getRequiredLangforgeVersion();
			if (LangforgeApplication.CURRENT_VERSION.compareTo(pluginRequiredVersion) < 0) {
				throw new LangforgePluginException(String.format(
					"Plugin requires Langforge version %s; current version is %s.",
					pluginRequiredVersion,
					LangforgeApplication.CURRENT_VERSION
				));
			}
		}
	}

	private static LangforgePluginApiProvider createApiProvider() {
		var provider = new LangforgePluginApiProvider();

		provider.registerApi(new LangforgeAppInfoApi());

		return provider;
	}

}
