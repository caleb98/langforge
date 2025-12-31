package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualIntegerSupplier<T> extends Function<T, Integer> {

}
