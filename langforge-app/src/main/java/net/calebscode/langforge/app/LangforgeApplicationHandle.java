package net.calebscode.langforge.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;

public class LangforgeApplicationHandle {

	final ObservableList<MenuDefinition> menus = FXCollections.observableArrayList();
	final ObservableList<MenuItemDefinition> menuItems = FXCollections.observableArrayList();

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

}
