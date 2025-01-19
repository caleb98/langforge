package net.calebscode.langforge.app;


import java.util.TreeSet;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.plugin.PropertyDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.AggregateSet;

public final class LangforgeApplicationModel {

	private AggregateSet<MenuDefinition> menusInternal = new AggregateSet<>(new TreeSet<>());
	private AggregateSet<MenuItemDefinition> menuItemsInternal = new AggregateSet<>();

	public final SetProperty<MenuDefinition> menus = new SimpleSetProperty<>(menusInternal.getAggregate());
	public final SetProperty<MenuItemDefinition> menuItems = new SimpleSetProperty<>(menuItemsInternal.getAggregate());
	public final ListProperty<Tab> tabs = new SimpleListProperty<Tab>(FXCollections.observableArrayList());
	public final MapProperty<String, MapProperty<String, PropertyDefinition<?>>> exposedPluginProperties = new SimpleMapProperty<>(FXCollections.observableHashMap());

	public void registerPlugin(LangforgePluginContext context) {
		menusInternal.aggregate(context.menus);
		menuItemsInternal.aggregate(context.menuItems);

		exposedPluginProperties.put(context.plugin.getId(), context.exposedProperties);
	}

	public void unregisterPlugin(LangforgePluginContext context) {
		menusInternal.remove(context.menus);
		menuItemsInternal.remove(context.menuItems);

		exposedPluginProperties.remove(context.plugin.getId());
	}

}
