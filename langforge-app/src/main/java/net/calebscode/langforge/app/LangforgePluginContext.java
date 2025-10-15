package net.calebscode.langforge.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.data.SaveLoadModel;
import net.calebscode.langforge.app.plugin.MenuDefinition;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;

/**
 * A {@code LangforgePluginContext} serves as the interface between a plugin and the rest of the Langforge Application.
 * Each plugin receives its own {@code LangforgePluginContext} through which it can perform common UI operations or interact
 * with other plugins.
 */
public class LangforgePluginContext {

	private final LangforgeApplicationModel appModel;
	private final LangforgePluginApiProvider apiProvider;
	private final Map<String, SaveLoadModel> persistentModels = new HashMap<>();

	private final ListProperty<MenuDefinition> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<MenuItemDefinition> menuItems = new SimpleListProperty<>(FXCollections.observableArrayList());

	public LangforgePluginContext(
		LangforgeApplicationModel appModel,
		LangforgePluginApiProvider apiProvider
	) {
		this.appModel = appModel;
		this.apiProvider = apiProvider;
	}

	/*
	 * Public Methods for Client Plugins
	 */

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

	public void registerSaveLoadModel(String key, SaveLoadModel model) {
		if (persistentModels.containsKey(key)) {
			throw new IllegalArgumentException("PersistentModel with key '" + key + "' already registered.");
		}
		persistentModels.put(key, model);
	}

	public <T> void registerApi(T api) {
		apiProvider.registerApi(api);
	}

	public <T> Optional<T> getApi(Class<T> apiClass) {
		return apiProvider.getApi(apiClass);
	}

	/*
	 * Package Protected Methods for Internal App
	 */

	ListProperty<MenuDefinition> menusProperty() {
		return menus;
	}

	ListProperty<MenuItemDefinition> menuItemsProperty() {
		return menuItems;
	}

	Map<String, SaveLoadModel> getPersistentModels() {
		return Collections.unmodifiableMap(persistentModels);
	}

}
