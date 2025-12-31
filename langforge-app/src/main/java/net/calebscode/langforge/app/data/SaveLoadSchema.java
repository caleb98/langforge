package net.calebscode.langforge.app.data;

import static net.calebscode.langforge.app.data.SaveLoadable.isTypeSaveLoadable;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadListProperty;
import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadMapProperty;
import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadObjectProperty;
import net.calebscode.langforge.app.data.function.ContextualByteConsumer;
import net.calebscode.langforge.app.data.function.ContextualByteSupplier;
import net.calebscode.langforge.app.data.function.ContextualCharacterConsumer;
import net.calebscode.langforge.app.data.function.ContextualCharacterSupplier;
import net.calebscode.langforge.app.data.function.ContextualDoubleConsumer;
import net.calebscode.langforge.app.data.function.ContextualDoubleSupplier;
import net.calebscode.langforge.app.data.function.ContextualFloatConsumer;
import net.calebscode.langforge.app.data.function.ContextualFloatSupplier;
import net.calebscode.langforge.app.data.function.ContextualIntegerConsumer;
import net.calebscode.langforge.app.data.function.ContextualIntegerSupplier;
import net.calebscode.langforge.app.data.function.ContextualLongConsumer;
import net.calebscode.langforge.app.data.function.ContextualLongSupplier;
import net.calebscode.langforge.app.data.function.ContextualSaveLoadableSupplier;
import net.calebscode.langforge.app.data.function.ContextualShortConsumer;
import net.calebscode.langforge.app.data.function.ContextualShortSupplier;
import net.calebscode.langforge.app.data.function.ContextualStringConsumer;
import net.calebscode.langforge.app.data.function.ContextualStringPropertySupplier;
import net.calebscode.langforge.app.data.function.ContextualStringSupplier;

public final class SaveLoadSchema<T> {

	private Map<String, SaveLoadProperty<T>> properties = new HashMap<>();

	Map<String, SaveLoadProperty<T>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	private void add(String name, SaveLoadProperty<T> value) {
		if (properties.containsKey(name)) {
			throw new IllegalArgumentException("Schema already contains property with name '" + name + "'.");
		}

		properties.put(name, value);
	}
	
	public <S> void add(String name, RuntimeType<S> runtimeType, Function<T, S> getter, BiConsumer<T, S> setter) {
		add(name, new SaveLoadObjectProperty<T, S>(runtimeType.getType(), getter, setter));
	}
	
	public void add(String name, ContextualStringSupplier<T> getter, ContextualStringConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, String>(String.class, getter, setter));
	}

	public void addProperty(String name, ContextualStringPropertySupplier<T> propertySupplier) {
		add(
			name,
			(T context) -> propertySupplier.apply(context).get(),
			(context, value) -> propertySupplier.apply(context).set(value)
		);
	}
	
	public void add(String name, ContextualCharacterSupplier<T> getter, ContextualCharacterConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Character>(Character.class, getter, setter));
	}
	
	public void add(String name, ContextualByteSupplier<T> getter, ContextualByteConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Byte>(Byte.class, getter, setter));
	}
	
	public void add(String name, ContextualShortSupplier<T> getter, ContextualShortConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Short>(Short.class, getter, setter));
	}
	
	public void add(String name, ContextualIntegerSupplier<T> getter, ContextualIntegerConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Integer>(Integer.class, getter, setter));
	}
	
	public void add(String name, ContextualLongSupplier<T> getter, ContextualLongConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Long>(Long.class, getter, setter));
	}
	
	public void add(String name, ContextualFloatSupplier<T> getter, ContextualFloatConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Float>(Float.class, getter, setter));
	}
	
	public void add(String name, ContextualDoubleSupplier<T> getter, ContextualDoubleConsumer<T> setter) {
		add(name, new SaveLoadObjectProperty<T, Double>(Double.class, getter, setter));
	}
	
	public <S> void add(String name, ContextualSaveLoadableSupplier<T, S> getter) {
		add(name, new SaveLoadObjectProperty<T, SaveLoadable<S>>(SaveLoadable.class, getter, (_, _) -> {
			throw new RuntimeException("Invalid call to SaveLoadProperty setter function. This SaveLoadProperty holds"
					+ " a SaveLoadable, which should have its value updated by calling setters of its properties, not"
					+ " by calling the setter function on this property. (If you're seeing this, it's a bug in the "
					+ " currently active " + DataStore.class.getSimpleName() + " implementation.)");
		}));
	}
	
	public <E> void addList(String name, RuntimeType<E> type, Function<T, List<E>> getter) {
		Function<T, E> elementFactory = null;
		
		if (isTypeSaveLoadable(type.getType())) {
			elementFactory = createFactoryForType(type);
		}
		
		addList(name, type, elementFactory, getter);
	}
	
	public <E> void addList(String name, RuntimeType<E> type, Function<T, E> elementFactory, Function<T, List<E>> getter) {
		add(name, new SaveLoadListProperty<>(type.getType(), elementFactory, getter));
	}
	
	public <V> void addMap(String name, RuntimeType<V> valueType, Function<T, Map<String, V>> getter) {
		addMap(name, new RuntimeType<String>() {}, valueType, getter);
	}
	
	public <K, V> void addMap(String name, RuntimeType<K> keyType, RuntimeType<V> valueType, Function<T, Map<K, V>> getter) {
		Function<T, K> keyFactory = null;
		if (isTypeSaveLoadable(keyType.getType())) {
			keyFactory = createFactoryForType(keyType);
		}
		
		Function<T, V> valueFactory = null;
		if (isTypeSaveLoadable(valueType.getType())) {
			valueFactory = createFactoryForType(valueType);
		}
		
		addMap(name, keyType, valueType, keyFactory, valueFactory, getter);
	}
	
	public <K, V> void addMap(
		String name,
		RuntimeType<K> keyType,
		RuntimeType<V> valueType,
		Function<T, K> keyFactory,
		Function<T, V> valueFactory,
		Function<T, Map<K, V>> getter
	) {
		add(name, new SaveLoadMapProperty<>(keyType.getType(), valueType.getType(), keyFactory, valueFactory, getter));
	}
	
	private static <T, S> Function<T, S> createFactoryForType(RuntimeType<S> type) {
		Constructor<S> typeConstructor;
		
		try {
			@SuppressWarnings("unchecked")
			Class<S> clazz = (Class<S>) type.getType();
			typeConstructor = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException ex) {
			var msg = String.format("Failed to create factory for type %s. No default constructor available.", type.getType().getTypeName());
			throw new RuntimeException(msg, ex);
		}
		
		if (!typeConstructor.canAccess(null)) {
			var msg = String.format("Failed to create factory for type %s. Default constructor exists, but is not public.", type.getType().getTypeName());
			throw new RuntimeException(msg);
		}
		
		Function<T, S> factory = _ -> {
			try {
				return typeConstructor.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException("Failed to instantiate object.", ex);
			}
		};
		return factory;
	}
	
}
