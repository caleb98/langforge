package net.calebscode.langforge.app;

import static javafx.collections.FXCollections.observableArrayList;

import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.MenuDefinition;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;

/// A {@code LangforgePluginContext} serves as the interface between a plugin and the rest of the
/// Langforge Application. Each plugin receives its own {@code LangforgePluginContext} through which
/// it can perform common UI operations or interact with other plugins.
public class LangforgePluginContext {

	private final ApplicationManager appManager;
	private final LangforgeApplicationModel appModel;
	private final LangforgePluginApiProvider apiProvider;

	private final ListProperty<MenuDefinition> menus =
		new SimpleListProperty<>(observableArrayList());

	private final ListProperty<MenuItemDefinition> menuItems =
		new SimpleListProperty<>(observableArrayList());

	LangforgePluginContext(
		ApplicationManager appManager,
		LangforgeApplicationModel appModel,
		LangforgePluginApiProvider apiProvider
	) {
		this.appManager = appManager;
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

	public <T> void registerApi(T api) {
		apiProvider.registerApi(api);
	}

	public <T> Optional<T> getApi(Class<T> apiClass) {
		return apiProvider.getApi(apiClass);
	}

	public void requestSave() {
		appManager.requestSave();
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

}
