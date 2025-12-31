package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualLongSupplier<T> extends Function<T, Long> {

}
