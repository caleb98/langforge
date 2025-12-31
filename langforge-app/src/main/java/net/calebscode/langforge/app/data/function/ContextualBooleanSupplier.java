package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualBooleanSupplier<T> extends Function<T, Boolean> {

}
