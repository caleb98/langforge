package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualStringSupplier<T> extends Function<T, String> {

}
