package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualDoubleSupplier<T> extends Function<T, Double> {

}
