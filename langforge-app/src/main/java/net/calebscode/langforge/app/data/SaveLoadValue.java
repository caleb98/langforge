package net.calebscode.langforge.app.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

sealed interface SaveLoadValue {
	
	interface Gettable<T> {
		
		Supplier<T> getter();
		
		default T getValue() {
			return getter().get();
		}
		
	}
	
	interface Settable<T> {
		
		Consumer<T> setter();
		
		default void setValue(T value) {
			setter().accept(value);
		}
		
		@SuppressWarnings("unchecked")
		default void setValueUnchecked(Object value) {
			setter().accept((T) value);
		}
		
	}
	
	public record SaveLoadModelValue(Supplier<SaveLoadModel> getter)
	implements SaveLoadValue, Gettable<SaveLoadModel> {}

	public record SaveLoadObjectValue<T>(Type type, Supplier<T> getter, Consumer<T> setter)
	implements SaveLoadValue, Gettable<T>, Settable<T> {}
	
	public record SaveLoadListValue<T>(Type elementType, Supplier<T> elementFactory, Supplier<List<T>> getter)
	implements SaveLoadValue, Gettable<List<T>> {}
	
	public record SaveLoadMapValue<K, V>(
		Type keyType,
		Type valueType,
		Supplier<K> keyFactory,
		Supplier<V> valueFactory,
		Supplier<Map<K, V>> getter)
	implements SaveLoadValue, Gettable<Map<K, V>> {}

}
