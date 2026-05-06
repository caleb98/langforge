package net.calebscode.langforge.app;

import static java.util.Comparator.comparing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

	private boolean pluginsInitialized = false;
	private LangforgeApplicationModel appModel;
	private LangforgePluginApiProvider apiProvider = createApiProvider();
	private PersistenceBackend persistenceBackend = new JsonBackend();

	private final Map<String, LangforgePlugin> plugins = new HashMap<>();
	private final Map<LangforgePluginContext, LangforgePlugin> contexts = new HashMap<>();

	public PluginManager(LangforgeApplicationModel appModel) {
		this.appModel = appModel;
	}

	public void initializePlugins() throws DuplicatePluginIdException {
		if (pluginsInitialized) {
			throw new IllegalStateException(
				"Cannot call initializePlugins() after plugins have already been initialized."
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

	public void loadPluginStates(InputStream input) throws IOException {
		SaveLoadObject infos = persistenceBackend.load(input);

		for (var plugin : plugins.values()) {
			plugin.setState(Optional.empty());
		}

		for (var info : infos.entrySet()) {
			var pluginId = info.getKey();

			if (!plugins.containsKey(pluginId)) {
				// TODO: warn and ask if we should continue
				continue;
			}

			var pluginInfo = info.getValue().asObject();
			var stateVersion = VersionNumber.parse(pluginInfo.get("version").asString().value());
			var state = pluginInfo.get("state");

			var plugin = plugins.get(pluginId);
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
						pluginId,
						stateVersion,
						migration.targetVersion()
					);
					state = migration.migrate(state);
					stateVersion = migration.targetVersion();
				}
			}

			plugin.setState(Optional.of(state));
		}
	}

	public void savePluginStates(OutputStream output) {
		var saveState = new SaveLoadObject();
		for (var plugin : contexts.values()) {
			var pluginState = plugin.getState();

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

	public LangforgePluginApiProvider getApiProvider() {
		return apiProvider;
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

			plugins.put(plugin.getId(), plugin);
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
