package net.calebscode.langforge.app;

import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginApiProvider;
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
	final LangforgePluginApiProvider apiProvider;

	final ListProperty<MenuDefinition> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
	final ListProperty<MenuItemDefinition> menuItems = new SimpleListProperty<>(FXCollections.observableArrayList());

	public LangforgePluginContext(
		LangforgePlugin plugin,
		LangforgeApplicationModel appModel,
		LangforgePluginApiProvider apiProvider
	) {
		this.plugin = plugin;
		this.appModel = appModel;
		this.apiProvider = apiProvider;
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

	public <T> void registerApi(T api) {
		apiProvider.registerApi(api);
	}

	public <T> Optional<T> getApi(Class<T> apiClass) {
		return apiProvider.getApi(apiClass);
	}

}
