package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualBooleanConsumer<T> extends BiConsumer<T, Boolean> {

}
