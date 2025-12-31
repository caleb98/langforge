package net.calebscode.langforge.app.data.function;

import java.util.function.Function;

import net.calebscode.langforge.app.data.SaveLoadable;

@FunctionalInterface
public interface ContextualSaveLoadableSupplier<T, S> extends Function<T, SaveLoadable<S>> {

}
