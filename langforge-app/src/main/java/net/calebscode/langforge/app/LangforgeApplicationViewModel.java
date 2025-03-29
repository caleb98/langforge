package net.calebscode.langforge.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.beans.binding.ListBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;

class LangforgeApplicationViewModel {

	private final LangforgeApplicationModel model;

	private final Map<String, Integer> existingMenuIndexes = new HashMap<>();
	private final Map<String, Menu> existingMenus = new HashMap<>();
	public final ListProperty<Menu> menus = new SimpleListProperty<Menu>(FXCollections.observableArrayList());

	public final ListProperty<Tab> tabs = new SimpleListProperty<Tab>(FXCollections.observableArrayList());
	public final ObjectProperty<Node> leftPanel = new SimpleObjectProperty<Node>(null);
	public final ObjectProperty<Node> rightPanel = new SimpleObjectProperty<Node>(null);
	public final StringProperty statusText = new SimpleStringProperty("");

	public LangforgeApplicationViewModel(LangforgeApplicationModel model) {
		this.model = model;

		bindModel();
	}

	private void bindModel() {
		menus.bind(new MenuListBinding());
		tabs.bindBidirectional(model.tabs);
	}

	private final class MenuListBinding extends ListBinding<Menu> {

		MenuListBinding() {
			bind(model.menus);
			bind(model.menuItems);
		}

		@Override
		protected ObservableList<Menu> computeValue() {
			// Identify any duplicate menu definitions (cases of the same menu name
			// with different indexes). We want to "squash" these into the lowest
			// index.
			var groupedDefinitions = model.menus.stream().collect(Collectors.groupingBy(def -> def.name()));
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

				var menuItemDefs = model.menuItems.stream()
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
