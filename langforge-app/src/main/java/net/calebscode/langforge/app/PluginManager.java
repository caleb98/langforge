package net.calebscode.langforge.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.calebscode.langforge.app.data.JsonDataStore;
import net.calebscode.langforge.app.util.VersionNumber;

public final class PluginManager {

	private final static Logger logger = LoggerFactory.getLogger(PluginManager.class);
	
	private Map<LangforgePlugin, LangforgePluginContext> pluginContexts = new HashMap<>();
	private boolean pluginsLoaded = false;
	private LangforgeApplicationModel appModel;
	private LangforgePluginApiProvider apiProvider = new LangforgePluginApiProvider();

	public PluginManager(LangforgeApplicationModel appModel) {
		this.appModel = appModel;
	}

	public void loadPlugins() throws DuplicatePluginIdException {
		if (pluginsLoaded) {
			throw new IllegalStateException("Cannot call loadPlugin() after plugins have already been loaded.");
		}

		ServiceLoader<LangforgePlugin> pluginLoader = ServiceLoader.load(LangforgePlugin.class);
		var plugins = pluginLoader.stream().map(Provider::get).toList();

		verifyNoDuplicatePluginIds(plugins);

		plugins.forEach(plugin -> {
			pluginContexts.put(plugin, new LangforgePluginContext(appModel, apiProvider));
		});

		var initResults = plugins.stream().collect(Collectors.partitioningBy(this::initPlugin));
		var initializedPlugins = initResults.get(true);

		loadPluginStates();
		apiProvider.setInitialized();

		var pluginDependencies = initializedPlugins.stream()
				.collect(Collectors.toMap(
					plugin -> plugin,
					plugin -> new HashMap<>(plugin.getDependencies())
				));

		// While computing the load order, the pluginDependencies map is updated and
		// dependencies that are marked for load are removed. The result is that all
		// successfully loaded plugins should have no more entries in the pluginDependencies
		// map. The presence of any dependencies indicates that the dependency could
		// not be satisfied, so log those.
		var loadOrder = computeLoadOrder(initializedPlugins, pluginDependencies);
		logPluginsWithUnsatisfiedDependencies(pluginDependencies);

		loadOrder.forEach(this::loadPlugin);
		pluginsLoaded = true;
	}

	public void savePluginStates() {
		try {
			var dataStore = new JsonDataStore();
			var saveDir = Path.of("test");

			Files.createDirectories(saveDir);

			for (var pluginEntry : pluginContexts.entrySet()) {
				var plugin = pluginEntry.getKey();
				var context = pluginEntry.getValue();
				var saveLoadObjects = context.getSaveLoadObjects();

				if (saveLoadObjects.isEmpty()) {
					continue;
				}

				var fileName = String.format("%s.json", plugin.getId());
				var filePath = saveDir.resolve(fileName);

				if (Files.notExists(filePath) ) {
					Files.createFile(filePath);
				}

				try (var outputStream = new FileOutputStream(filePath.toFile())) {
					dataStore.save(outputStream, saveLoadObjects);
					outputStream.flush();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void loadPluginStates() {
		try {
			var dataStore = new JsonDataStore();
			var saveDir = Path.of("test");

			Files.createDirectories(saveDir);

			for (var pluginEntry : pluginContexts.entrySet()) {
				var plugin = pluginEntry.getKey();
				var context = pluginEntry.getValue();
				var persistentModels = context.getSaveLoadObjects();

				if (persistentModels.isEmpty()) {
					continue;
				}

				var fileName = String.format("%s.json", plugin.getId());
				var filePath = saveDir.resolve(fileName);

				if (Files.notExists(filePath)) {
					continue;
				}

				try (var inputStream = new FileInputStream(filePath.toFile())) {
					dataStore.load(inputStream, persistentModels);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
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

	private ArrayList<LangforgePlugin> computeLoadOrder(
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

	private void verifyNoDuplicatePluginIds(List<LangforgePlugin> plugins) throws DuplicatePluginIdException {
		var grouped = plugins.stream().collect(Collectors.groupingBy(plugin -> plugin.getId()));

		for (var entry : grouped.entrySet()) {
			var pluginsForId = entry.getValue();

			if (pluginsForId.size() > 1) {
				throw new DuplicatePluginIdException(entry.getKey(), pluginsForId);
			}
		}
	}

	private boolean initPlugin(LangforgePlugin plugin) {
		try {
			plugin.init(pluginContexts.get(plugin));
			return true;
		}
		catch (Exception ex) {
			System.err.printf(
					"Unable to load plugin '%s' (%s): %s\n",
					plugin.getName(),
					plugin.getId(),
					ex.getMessage());

			return false;
		}
	}

	private void loadPlugin(LangforgePlugin plugin) {
		try {
			if (LangforgeApplication.VERSION.compareTo(plugin.getRequiredLangforgeVersion()) < 0) {
				throw new LangforgePluginException(String.format(
					"Plugin requires Langforge version %s; current version is %s.",
					plugin.getRequiredLangforgeVersion(),
					LangforgeApplication.VERSION
				));
			}

			var context = pluginContexts.get(plugin);
			plugin.load(context);
			appModel.registerPlugin(context);

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

}
