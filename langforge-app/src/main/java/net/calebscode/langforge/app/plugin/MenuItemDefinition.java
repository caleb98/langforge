package net.calebscode.langforge.app.plugin;

import java.util.function.Supplier;

import javafx.scene.control.MenuItem;

public record MenuItemDefinition(String menuName, String group, Supplier<MenuItem> supplier) {

	public static final String DEFAULT_GROUP = "default";

	public MenuItemDefinition(String menuName, String text) {
		this(menuName, DEFAULT_GROUP, () -> new MenuItem(text));
	}

	public MenuItemDefinition(String menuName, String group, String text) {
		this(menuName, group, () -> new MenuItem(text));
	}

	public MenuItemDefinition(String menuName, Supplier<MenuItem> item) {
		this(menuName, DEFAULT_GROUP, item);
	}

}
