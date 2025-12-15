package net.calebscode.langforge.app.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

sealed interface SaveLoadValue<T> {

	Supplier<T> getter();
	Consumer<T> setter();

	default T getValue() {
		return getter().get();
	}
	
	default void setValue(T value) {
		setter().accept(value);
	}
	
	@SuppressWarnings("unchecked")
	default void setValueUnchecked(Object value) {
		setValue((T) value);
	}

	public record SaveLoadObject<T>(
		Type type,
		Supplier<T> getter,
		Consumer<T> setter
	) implements SaveLoadValue<T> {}
	
	public record SaveLoadList<E>(
		Type elementType,
		Supplier<E> elementFactory,
		Supplier<List<E>> getter,
		Consumer<List<E>> setter
	) implements SaveLoadValue<List<E>> {
		
		List<E> newTypedList() {
			return new ArrayList<E>();
		}
		
	}

}
