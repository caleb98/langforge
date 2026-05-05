package net.calebscode.langforge.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.calebscode.langforge.app.data.JsonBackend;
import net.calebscode.langforge.app.data.PersistenceBackend;
import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.util.VersionNumber;

public final class PluginManager {

	private final static Logger logger = LoggerFactory.getLogger(PluginManager.class);

	private boolean pluginsInitialized = false;
	private LangforgeApplicationModel appModel;
	private LangforgePluginApiProvider apiProvider = createApiProvider();
	private Map<LangforgePluginContext, LangforgePlugin> contexts = new HashMap<>();
	private PersistenceBackend persistenceBackend = new JsonBackend();

	public PluginManager(LangforgeApplicationModel appModel) {
		this.appModel = appModel;
	}

	public void initializePlugins() throws DuplicatePluginIdException {
		if (pluginsInitialized) {
			throw new IllegalStateException(
				"Cannot call loadPlugin() after plugins have already been loaded."
			);
		}

		ServiceLoader<LangforgePlugin> pluginLoader = ServiceLoader.load(LangforgePlugin.class);
		var plugins = pluginLoader.stream().map(Provider::get).toList();
		verifyNoDuplicatePluginIds(plugins);

		var pluginDependencies = plugins.stream()
				.collect(Collectors.toMap(
					plugin -> plugin,
					plugin -> new HashMap<>(plugin.getDependencies())
				));

		// While computing the initialize order, the pluginDependencies map is updated and
		// dependencies that are marked for load are removed. The result is that all successfully
		// loaded plugins should have no more entries in the pluginDependencies map. The presence of
		// any dependencies indicates that the dependency could not be satisfied, so log those.
		var initializeOrder = computeInitializeOrder(plugins, pluginDependencies);
		logPluginsWithUnsatisfiedDependencies(pluginDependencies);

		initializeOrder.forEach(this::initializePlugin);
		pluginsInitialized = true;
	}

	public void savePluginStates() {
		var saveState = new SaveLoadObject();
		for (var plugin : contexts.values()) {
			var pluginState = plugin.getState();
			if (pluginState.isPresent()) {
				saveState.put(plugin.getId(), pluginState.get());
			}
		}

		try (var output = new FileOutputStream("./save.json")) {
			persistenceBackend.save(output, saveState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void logPluginsWithUnsatisfiedDependencies(
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

	private ArrayList<LangforgePlugin> computeInitializeOrder(
		List<LangforgePlugin> initializedPlugins,
		Map<LangforgePlugin, HashMap<String, VersionNumber>> pluginDependencies
	) {
		var loadOrder = new ArrayList<LangforgePlugin>();
		var check = new ArrayList<LangforgePlugin>();

		for (var plugin : initializedPlugins) {
			if (pluginDependencies.get(plugin).isEmpty()) {
				check.add(plugin);
				pluginDependencies.remove(plugin);
			}
		}

		while (!check.isEmpty()) {
			var currentPlugin = check.removeFirst();
			loadOrder.add(currentPlugin);

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

		return loadOrder;
	}

	private void verifyNoDuplicatePluginIds(List<LangforgePlugin> plugins)
	throws DuplicatePluginIdException {
		var grouped = plugins.stream().collect(Collectors.groupingBy(LangforgePlugin::getId));

		for (var entry : grouped.entrySet()) {
			var pluginsForId = entry.getValue();

			if (pluginsForId.size() > 1) {
				throw new DuplicatePluginIdException(entry.getKey(), pluginsForId);
			}
		}
	}

	private void initializePlugin(LangforgePlugin plugin) {
		try {
			VersionNumber pluginRequiredVersion = plugin.getRequiredLangforgeVersion();
			if (LangforgeApplication.CURRENT_VERSION.compareTo(pluginRequiredVersion) < 0) {
				throw new LangforgePluginException(String.format(
					"Plugin requires Langforge version %s; current version is %s.",
					pluginRequiredVersion,
					LangforgeApplication.CURRENT_VERSION
				));
			}

			var context = new LangforgePluginContext(appModel, apiProvider);
			plugin.setContext(context);
			plugin.initialize();
			appModel.registerPlugin(context);
			contexts.put(context, plugin);

			logger.info(
				"Loaded plugin: {} {} ({}) - {}",
				plugin.getName(),
				plugin.getVersion(),
				plugin.getId(),
				plugin.getDescription()
			);
		}
		catch (LangforgePluginException ex) {
			logger.error(
				"Unable to load plugin '{}' ({}): {}\n",
				plugin.getName(),
				plugin.getId(),
				ex.getMessage());
		}
	}

	private static LangforgePluginApiProvider createApiProvider() {
		var provider = new LangforgePluginApiProvider();

		provider.registerApi(new LangforgeAppInfoApi());

		return provider;
	}

}
