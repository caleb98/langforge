package net.calebscode.langforge.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.beans.binding.ListBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.AggregateSet;

public class LangforgeApplicationModel {

	private AggregateSet<MenuDefinition> menusInternal = new AggregateSet<>(new TreeSet<>());
	private final SetProperty<MenuDefinition> menuDefinitions = new SimpleSetProperty<>(menusInternal.getAggregate());

	private AggregateSet<MenuItemDefinition> menuItemsInternal = new AggregateSet<>();
	private final SetProperty<MenuItemDefinition> menuItemDefinitions = new SimpleSetProperty<>(menuItemsInternal.getAggregate());

	private final Map<String, Integer> existingMenuIndexes = new HashMap<>();
	private final Map<String, Menu> existingMenus = new HashMap<>();
	public final ListProperty<Menu> menus = new SimpleListProperty<Menu>(FXCollections.observableArrayList());

	public final ListProperty<Tab> tabs = new SimpleListProperty<Tab>(FXCollections.observableArrayList());
	public final ObjectProperty<Node> leftPanel = new SimpleObjectProperty<Node>(null);
	public final ObjectProperty<Node> rightPanel = new SimpleObjectProperty<Node>(null);
	public final StringProperty statusText = new SimpleStringProperty("");

	public LangforgeApplicationModel() {
		menus.bind(new MenuListBinding());
	}

	public void registerPlugin(LangforgePluginContext context) {
		menusInternal.aggregate(context.menus);
		menuItemsInternal.aggregate(context.menuItems);
	}

	public void unregisterPlugin(LangforgePluginContext context) {
		menusInternal.remove(context.menus);
		menuItemsInternal.remove(context.menuItems);
	}

	private final class MenuListBinding extends ListBinding<Menu> {

		MenuListBinding() {
			bind(menuDefinitions);
			bind(menuItemDefinitions);
		}

		@Override
		protected ObservableList<Menu> computeValue() {
			// Identify any duplicate menu definitions (cases of the same menu name
			// with different indexes). We want to "squash" these into the lowest
			// index.
			var groupedDefinitions = menuDefinitions.stream().collect(Collectors.groupingBy(def -> def.name()));
			var resolvedDefinitions = new HashMap<String, MenuDefinition>();

			for (var menuName : groupedDefinitions.keySet()) {
				var definitions = groupedDefinitions.get(menuName);
				if (definitions.size() > 1) {
					definitions.sort((defA, defB) -> Integer.compare(defA.index(), defB.index()));
				}
				resolvedDefinitions.put(menuName, definitions.get(0));
			}

			// Create menus from menu definitions.
			for (var menuEntry : resolvedDefinitions.entrySet()) {
				var menuName = menuEntry.getKey();
				var menuDef = menuEntry.getValue();

				var menu = existingMenus.computeIfAbsent(menuName, (name) -> new Menu(name));
				existingMenuIndexes.put(menuName, menuDef.index());

				var menuItems = menu.getItems();
				menuItems.clear();

				var menuItemDefs = menuItemDefinitions.stream()
						.filter(defn -> Objects.equals(menuDef.name(), defn.menuName()))
						.sorted((defA, defB) -> defA.group().compareTo(defB.group()))
						.toList();

				for (int i = 0; i < menuItemDefs.size(); i++) {
					var itemDef = menuItemDefs.get(i);
					var item = itemDef.supplier().get();
					menuItems.add(item);

					if (i < menuItemDefs.size() - 1 && !menuItemDefs.get(i + 1).group().equals(itemDef.group())) {
						menuItems.add(new SeparatorMenuItem());
					}
				}
			}

			// Sort the menus based on their indexes
			var orderedMenus = existingMenus.keySet().stream()
					.sorted((menuNameA, menuNameB) -> Integer.compare(existingMenuIndexes.get(menuNameA), existingMenuIndexes.get(menuNameB)))
					.map(menuName -> existingMenus.get(menuName))
					.toList();

			return FXCollections.observableArrayList(orderedMenus);
		}
	}

}
