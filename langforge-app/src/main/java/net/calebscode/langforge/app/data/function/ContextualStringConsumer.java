package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualStringConsumer<T> extends BiConsumer<T, String> {

}
