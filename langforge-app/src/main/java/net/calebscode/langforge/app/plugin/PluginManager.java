package net.calebscode.langforge.app.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgeApplicationHandle;
import net.calebscode.langforge.app.LangforgeApplicationModel;

public class PluginManager {

	private Map<LangforgePlugin, LangforgeApplicationHandle> pluginHandles = new HashMap<>();
	private LangforgeApplicationModel appModel;

	public PluginManager(LangforgeApplicationModel appModel) {
		this.appModel = appModel;
	}

	public void loadPlugins() throws DuplicatePluginIdException {
		ServiceLoader<LangforgePlugin> pluginLoader = ServiceLoader.load(LangforgePlugin.class);
		var plugins = pluginLoader.stream()
			.map(provider -> provider.get())
			.toList();

		verifyNoDuplicatePluginIds(plugins);

		plugins.forEach(plugin -> {
			pluginHandles.put(plugin, new LangforgeApplicationHandle());
		});

		var initResults = plugins.stream().collect(Collectors.partitioningBy(this::initPlugin));
		var succeeded = initResults.get(true);

		var dependencies = succeeded.stream()
				.collect(Collectors.toMap(
					plugin -> plugin,
					plugin -> new HashMap<>(plugin.getDependencies())
				));

		var loadOrder = new ArrayList<LangforgePlugin>();
		var check = new ArrayList<LangforgePlugin>();

		for (var plugin : succeeded) {
			if (dependencies.get(plugin).isEmpty()) {
				check.add(plugin);
				dependencies.remove(plugin);
			}
		}

		while (!check.isEmpty()) {
			var adding = check.removeFirst();
			loadOrder.add(adding);

			var iter = dependencies.entrySet().iterator();
			while(iter.hasNext()) {
				var entry = iter.next();
				var plugin = entry.getKey();
				var deps = entry.getValue();

				if (deps.containsKey(adding.getId())) {
					var requiredVersion = deps.get(adding.getId());
					var actualVersion = adding.getVersion();
					if (actualVersion.compareTo(requiredVersion) >= 0) {
						deps.remove(adding.getId());
					}
				}

				if (deps.isEmpty()) {
					iter.remove();
					check.add(plugin);
				}
			}
		}

		// If there's still plugins with unsatisfied dependencies, alert the user.
		if (!dependencies.isEmpty()) {
			System.out.println("WARNING: The following plugins have dependencies that are not present or which failed to initialize. They will not be loaded.");
			for (var entry : dependencies.entrySet()) {
				var plugin = entry.getKey();
				var deps = entry.getValue();

				System.out.printf(
					"\t%s (%s) [%s] missing:%n",
					plugin.getName(),
					plugin.getVersion(),
					plugin.getId()
				);

				for (var dep : deps.entrySet()) {
					System.out.printf(
						"\t\t> %s with minimum version %s%n",
						dep.getKey(),
						dep.getValue()
					);
				}
			}
		}

		loadOrder.forEach(this::loadPlugin);
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
			plugin.init(pluginHandles.get(plugin));
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

			var context = new LangforgeApplicationHandle();
			plugin.load(context);
			appModel.registerPlugin(context);

			System.out.printf(
				"Loaded plugin: %s %s (%s) - %s%n",
				plugin.getName(),
				plugin.getVersion(),
				plugin.getId(),
				plugin.getDescription()
			);
		}
		catch (LangforgePluginException ex) {
			System.err.printf(
				"Unable to load plugin '%s' (%s): %s\n",
				plugin.getName(),
				plugin.getId(),
				ex.getMessage());
		}
	}

}
