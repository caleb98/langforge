package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualLongConsumer<T> extends BiConsumer<T, Long> {

}
