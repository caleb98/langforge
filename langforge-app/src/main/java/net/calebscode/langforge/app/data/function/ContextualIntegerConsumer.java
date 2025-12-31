package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualIntegerConsumer<T> extends BiConsumer<T, Integer> {

}
