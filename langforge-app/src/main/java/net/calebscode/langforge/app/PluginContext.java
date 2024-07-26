package net.calebscode.langforge.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;

/**
 * A {@code PluginContext} serves as the interface between a plugin and the rest of the Langforge Application.
 * Each plugin receives its own {@code PluginContext} through which it can perform common UI operations or interact
 * with other plugins.
 */
public class PluginContext {

	final LangforgeApplicationModel appModel;
	final ObservableList<MenuDefinition> menus = FXCollections.observableArrayList();
	final ObservableList<MenuItemDefinition> menuItems = FXCollections.observableArrayList();

	public PluginContext(LangforgeApplicationModel appModel) {
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

}
