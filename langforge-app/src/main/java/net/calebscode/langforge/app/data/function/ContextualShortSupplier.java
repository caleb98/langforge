package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualShortSupplier<T> extends Function<T, Short> {

}
