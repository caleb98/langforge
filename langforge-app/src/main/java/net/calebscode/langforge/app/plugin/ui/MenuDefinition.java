package net.calebscode.langforge.app.plugin.ui;

public record MenuDefinition(String name, int index) implements Comparable<MenuDefinition> {

	public static final int DEFAULT_INDEX = 100;

	public MenuDefinition(String name) {
		this(name, DEFAULT_INDEX);
	}

	@Override
	public int compareTo(MenuDefinition other) {
		return Integer.compare(index, other.index);
	}

}
