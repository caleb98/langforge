package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

@FunctionalInterface
public interface ContextualCharacterSupplier<T> extends Function<T, Character> {

}
