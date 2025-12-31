package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualDoubleConsumer<T> extends BiConsumer<T, Double> {

}
