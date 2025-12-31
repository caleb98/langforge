package net.calebscode.langforge.app.data.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ContextualCharacterConsumer<T> extends BiConsumer<T, Character> {

}
