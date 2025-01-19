package net.calebscode.langforge.app.plugin;

import java.util.Objects;

import javafx.beans.property.Property;

public class PropertyDefinition<T> {

	public final String path;
	public final String description;
	public final Property<T> property;
	public final Class<T> propertyClass;

	public PropertyDefinition(String path, String description, Property<T> property, Class<T> propertyClass) {
		Objects.requireNonNull(path);
		Objects.requireNonNull(description);
		Objects.requireNonNull(property);
		Objects.requireNonNull(propertyClass);

		this.path = path;
		this.description = description;
		this.property = property;
		this.propertyClass = propertyClass;
	}

}
