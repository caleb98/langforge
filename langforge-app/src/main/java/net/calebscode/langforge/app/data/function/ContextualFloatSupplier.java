package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualFloatSupplier<T> extends Function<T, Float> {

}
