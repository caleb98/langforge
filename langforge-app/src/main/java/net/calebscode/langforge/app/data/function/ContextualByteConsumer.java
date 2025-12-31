package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualByteConsumer<T> extends BiConsumer<T, Byte> {

}
