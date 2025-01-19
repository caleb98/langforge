package net.calebscode.langforge.app;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.PropertyDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;

/**
 * A {@code LangforgePluginContext} serves as the interface between a plugin and the rest of the Langforge Application.
 * Each plugin receives its own {@code LangforgePluginContext} through which it can perform common UI operations or interact
 * with other plugins.
 */
public class LangforgePluginContext {

	final LangforgePlugin plugin;
	final LangforgeApplicationModel appModel;

	final ListProperty<MenuDefinition> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	final ListProperty<MenuItemDefinition> menuItems = new SimpleListProperty<>(FXCollections.observableArrayList());
	final MapProperty<String, PropertyDefinition<?>> exposedProperties = new SimpleMapProperty<>(FXCollections.observableHashMap());

	public LangforgePluginContext(LangforgePlugin plugin, LangforgeApplicationModel appModel) {
		this.plugin = plugin;
		this.appModel = appModel;
	}

	public void addMenu(MenuDefinition menuDefinition) {
		menus.add(menuDefinition);
	}

	public void addMenus(MenuDefinition... menuDefinitions) {
		menus.addAll(menuDefinitions);
	}

	public void removeMenu(String menuName) {
		menus.removeIf(defn -> defn.name().equals(menuName));
	}

	public void addMenuItem(MenuItemDefinition menuItemDefinition) {
		menuItems.add(menuItemDefinition);
	}

	public void addMenuItems(MenuItemDefinition... menuItemDefinitions) {
		menuItems.addAll(menuItemDefinitions);
	}

	public void removeMenuItem(MenuItemDefinition menuItemDefinition) {
		menuItems.remove(menuItemDefinition);
	}

	public void createTab(Tab tab) {
		appModel.tabs.add(tab);
	}

	/**
	 * Exposes a property for consumption by other plugins.
	 * @param <T>
	 * @param path
	 * @param description
	 * @param property
	 * @param propertyClass
	 */
	public <T> void exposeProperty(String path, String description, Property<T> property, Class<T> propertyClass) {
		exposedProperties.put(path, new PropertyDefinition<>(path, description, property, propertyClass));
	}

	/**
	 * Retrieves an exposed property of a given plugin with a given path. If the property
	 * cannot be retrieved for any reason, returns an empty optional. (Plugin does not exist,
	 * property does not exist, property type incorrect, etc.)
	 * @param pluginId
	 * @param path
	 * @return
	 */
	public <T> Optional<Property<T>> getProperty(String pluginId, String path) {
		var pluginProperties = appModel.exposedPluginProperties.get(pluginId);
		if (pluginProperties == null) {
			return Optional.empty();
		}

		var definition = pluginProperties.get(path);
		if (definition == null) {
			return Optional.empty();
		}

		try {
			@SuppressWarnings("unchecked")
			Property<T> typedProperty = (Property<T>) definition.property;
			return Optional.of(typedProperty);
		} catch (ClassCastException e) {
			return Optional.empty();
		}
	}

	/**
	 * Retrieves an exposed property of the current plugin with the given path. If the
	 * property cannot be retrieved for any reason, returns an empty optional. (Property
	 * does not exist, property type incorrect, etc.)
	 * @param path
	 * @return
	 */
	public <T> Optional<Property<T>> getProperty(String path) {
		return getProperty(plugin.getId(), path);
	}

	/**
	 * Retrieves a collection of all property definitions for a given plugin. If the plugin
	 * does not exist, returns an empty optional.
	 * @param pluginId
	 * @return
	 */
	public Optional<Collection<PropertyDefinition<?>>> listExposedProperties(String pluginId) {
		var pluginProperties = appModel.exposedPluginProperties.get(pluginId);

		if (pluginProperties == null) {
			return Optional.empty();
		}

		return Optional.of(Collections.unmodifiableCollection(pluginProperties.values()));
	}

}
