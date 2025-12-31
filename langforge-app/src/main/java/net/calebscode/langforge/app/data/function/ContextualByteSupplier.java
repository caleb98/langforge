package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualByteSupplier<T> extends Function<T, Byte> {

}
