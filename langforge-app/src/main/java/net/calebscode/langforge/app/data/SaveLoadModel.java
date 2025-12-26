package net.calebscode.langforge.app.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadListValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadMapValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadModelValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadObjectValue;
import net.calebscode.langforge.app.functional.ByteConsumer;
import net.calebscode.langforge.app.functional.ByteSupplier;
import net.calebscode.langforge.app.functional.CharacterConsumer;
import net.calebscode.langforge.app.functional.CharacterSupplier;
import net.calebscode.langforge.app.functional.DoubleConsumer;
import net.calebscode.langforge.app.functional.DoubleSupplier;
import net.calebscode.langforge.app.functional.FloatConsumer;
import net.calebscode.langforge.app.functional.FloatSupplier;
import net.calebscode.langforge.app.functional.IntegerConsumer;
import net.calebscode.langforge.app.functional.IntegerSupplier;
import net.calebscode.langforge.app.functional.LongConsumer;
import net.calebscode.langforge.app.functional.LongSupplier;
import net.calebscode.langforge.app.functional.SaveLoadModelSupplier;
import net.calebscode.langforge.app.functional.ShortConsumer;
import net.calebscode.langforge.app.functional.ShortSupplier;
import net.calebscode.langforge.app.functional.StringConsumer;
import net.calebscode.langforge.app.functional.StringSupplier;

public class SaveLoadModel implements SaveLoadable {

	private Map<String, SaveLoadValue> values = new HashMap<>();

	Map<String, SaveLoadValue> getValues() {
		return Collections.unmodifiableMap(values);
	}
	
	@Override
	public SaveLoadModel getModel() {
		return this;
	}

	private void persist(String valueName, SaveLoadValue value) {
		if (values.containsKey(valueName)) {
			throw new IllegalArgumentException("Value with name '" + valueName + "' already registered.");
		}

		values.put(valueName, value);
	}
	
	protected <T> void persist(String valueName, RuntimeType<T> runtimeType, Supplier<T> getter, Consumer<T> setter) {
		persist(valueName, new SaveLoadObjectValue<T>(runtimeType.getType(), getter, setter));
	}
	
	protected void persist(String valueName, StringSupplier getter, StringConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<String>(String.class, getter, setter));
	}
	
	protected void persist(String valueName, CharacterSupplier getter, CharacterConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Character>(Character.class, getter, setter));
	}
	
	protected void persist(String valueName, ByteSupplier getter, ByteConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Byte>(Byte.class, getter, setter));
	}
	
	protected void persist(String valueName, ShortSupplier getter, ShortConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Short>(Short.class, getter, setter));
	}
	
	protected void persist(String valueName, IntegerSupplier getter, IntegerConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Integer>(Integer.class, getter, setter));
	}
	
	protected void persist(String valueName, LongSupplier getter, LongConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Long>(Long.class, getter, setter));
	}
	
	protected void persist(String valueName, FloatSupplier getter, FloatConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Float>(Float.class, getter, setter));
	}
	
	protected void persist(String valueName, DoubleSupplier getter, DoubleConsumer setter) {
		persist(valueName, new SaveLoadObjectValue<Double>(Double.class, getter, setter));
	}
	
	protected void persist(String valueName, SaveLoadModelSupplier getter) {
		persist(valueName, new SaveLoadModelValue(getter));
	}
	
	protected <T> void persistList(String listName, RuntimeType<T> type, Supplier<List<T>> getter) {
		Supplier<T> elementFactory = null;
		
		if (isTypeSaveLoadModel(type.getType())) {
			elementFactory = createFactoryForType(type);
		}
		
		persistList(listName, type, elementFactory, getter);
	}
	
	protected <T> void persistList(String listName, RuntimeType<T> type, Supplier<T> elementFactory, Supplier<List<T>> getter) {
		persist(listName, new SaveLoadListValue<>(type.getType(), elementFactory, getter));
	}
	
	protected <V> void persistMap(String mapName, RuntimeType<V> valueType, Supplier<Map<String, V>> getter) {
		persistMap(mapName, new RuntimeType<String>() {}, valueType, getter);
	}
	
	protected <K, V> void persistMap(String mapName, RuntimeType<K> keyType, RuntimeType<V> valueType, Supplier<Map<K, V>> getter) {
		Supplier<K> keyFactory = null;
		if (isTypeSaveLoadModel(keyType.getType())) {
			keyFactory = createFactoryForType(keyType);
		}
		
		Supplier<V> valueFactory = null;
		if (isTypeSaveLoadModel(valueType.getType())) {
			valueFactory = createFactoryForType(valueType);
		}
		
		persistMap(mapName, keyType, valueType, keyFactory, valueFactory, getter);
	}
	
	protected <K, V> void persistMap(
		String mapName,
		RuntimeType<K> keyType,
		RuntimeType<V> valueType,
		Supplier<K> keyFactory,
		Supplier<V> valueFactory,
		Supplier<Map<K, V>> getter
	) {
		persist(mapName, new SaveLoadMapValue<>(keyType.getType(), valueType.getType(), keyFactory, valueFactory, getter));
	}

	public static boolean isTypeSaveLoadModel(Type type) {
		if (type instanceof Class<?> c) {
			return SaveLoadModel.class.isAssignableFrom(c);
		}
		else if (type instanceof ParameterizedType p) {
			return isTypeSaveLoadModel(p.getRawType());
		}
		
		return false;
	}
	
	private static <T> Supplier<T> createFactoryForType(RuntimeType<T> type) {
		Constructor<T> typeConstructor;
		
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) type.getType();
			typeConstructor = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException ex) {
			var msg = String.format("Failed to create factory for type %s. No default constructor available.", type.getType().getTypeName());
			throw new RuntimeException(msg, ex);
		}
		
		if (!typeConstructor.canAccess(null)) {
			var msg = String.format("Failed to create factory for type %s. Default constructor exists, but is not public.", type.getType().getTypeName());
			throw new RuntimeException(msg);
		}
		
		Supplier<T> factory = () -> {
			try {
				return typeConstructor.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException("Failed to instantiate object.", ex);
			}
		};
		return factory;
	}
	
}
