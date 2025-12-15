package net.calebscode.langforge.app.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public sealed interface SaveLoadValue<T> {

	Supplier<T> getter();
	Consumer<T> setter();

	default T getValue() {
		return getter().get();
	}

	default void setValue(T value) {
		setter().accept(value);
	}

	public record SaveLoadString(Supplier<String> getter, Consumer<String> setter) implements SaveLoadValue<String> {}
	public record SaveLoadObject<T>(Type type, Supplier<T> getter, Consumer<T> setter) implements SaveLoadValue<T> {}
	
	public record SaveLoadList<E>(
		Supplier<List<E>> getter,
		Consumer<List<E>> setter,
		Type elementType,
		Supplier<E> elementFactory
	) implements SaveLoadValue<List<E>> {
		
		List<E> newTypedList() {
			return new ArrayList<E>();
		}
		
	}

}
