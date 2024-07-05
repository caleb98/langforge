package net.calebscode.langforge.app;


import java.util.TreeSet;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import net.calebscode.langforge.app.plugin.ui.MenuDefinition;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.AggregateSet;

public final class LangforgeApplicationModel {

	private AggregateSet<MenuDefinition> menusInternal = new AggregateSet<>(new TreeSet<>());
	private AggregateSet<MenuItemDefinition> menuItemsInternal = new AggregateSet<>();

	public final SetProperty<MenuDefinition> menus = new SimpleSetProperty<>(menusInternal.getAggregate());
	public final SetProperty<MenuItemDefinition> menuItems = new SimpleSetProperty<>(menuItemsInternal.getAggregate());

	public void registerPlugin(LangforgeApplicationHandle context) {
		menusInternal.aggregate(context.menus);
		menuItemsInternal.aggregate(context.menuItems);
	}

	public void unregisterPlugin(LangforgeApplicationHandle context) {
		menusInternal.remove(context.menus);
		menuItemsInternal.remove(context.menuItems);
	}

}
