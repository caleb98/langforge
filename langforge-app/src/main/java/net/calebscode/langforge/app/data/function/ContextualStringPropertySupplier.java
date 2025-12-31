package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

import javafx.beans.property.StringProperty;

@FunctionalInterface
public interface ContextualStringPropertySupplier<T> extends Function<T, StringProperty> {

}
