package net.calebscode.langforge.app.data;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

sealed interface SaveLoadProperty<T> {
	
	interface Gettable<T, S> {
		
		Function<T, S> getter();
		
		default S getValue(T context) {
			return getter().apply(context);
		}
		
	}
	
	interface Settable<T, S> {
		
		BiConsumer<T, S> setter();
		
		default void setValue(T context, S value) {
			setter().accept(context, value);
		}
		
	}
	
	public record SaveLoadObjectProperty<T, S>(Type type, Function<T, S> getter, BiConsumer<T, S> setter)
	implements SaveLoadProperty<T>, Gettable<T, S>, Settable<T, S> {}
	
	public record SaveLoadListProperty<T, E>(Type elementType, Function<T, E> elementFactory, Function<T, List<E>> getter)
	implements SaveLoadProperty<T>, Gettable<T, List<E>> {}
	
	public record SaveLoadMapProperty<T, K, V>(
		Type keyType,
		Type valueType,
		Function<T, K> keyFactory,
		Function<T, V> valueFactory,
		Function<T, Map<K, V>> getter)
	implements SaveLoadProperty<T>, Gettable<T, Map<K, V>> {}

}
