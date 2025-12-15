package net.calebscode.langforge.app.data;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadList;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadObject;
import net.calebscode.langforge.app.functional.IntegerConsumer;
import net.calebscode.langforge.app.functional.IntegerSupplier;
import net.calebscode.langforge.app.functional.StringConsumer;
import net.calebscode.langforge.app.functional.StringSupplier;

public class SaveLoadModel {

	private Map<String, SaveLoadValue<?>> values = new HashMap<>();

	Map<String, SaveLoadValue<?>> getValues() {
		return Collections.unmodifiableMap(values);
	}

	private void persist(String valueName, SaveLoadValue<?> value) {
		if (values.containsKey(valueName)) {
			throw new IllegalArgumentException("Value with name '" + valueName + "' already registered.");
		}

		values.put(valueName, value);
	}
	
	protected <T> void persist(String valueName, RuntimeType<T> runtimeType, Supplier<T> getter, Consumer<T> setter) {
		persist(valueName, new SaveLoadObject<T>(runtimeType.getType(), getter, setter));
	}
	
	protected void persist(String valueName, StringSupplier getter, StringConsumer setter) {
		persist(valueName, new SaveLoadObject<String>(String.class, getter, setter));
	}
	
	protected void persist(String valueName, IntegerSupplier getter, IntegerConsumer setter) {
		persist(valueName, new SaveLoadObject<Integer>(Integer.class, getter, setter));
	}

	protected <T> void persistList(String valueName, RuntimeType<T> type, Supplier<List<T>> getter, Consumer<List<T>> setter) {
		Constructor<T> elementConstructor;
		try {
			Class<T> clazz = (Class<T>) type.getType();
			elementConstructor = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException ex) {
			var msg = String.format("Failed to persist list of type %s. No default constructor available.", type.getType().getTypeName());
			throw new RuntimeException(msg, ex);
		}
		
		if (!elementConstructor.canAccess(null)) {
			var msg = String.format("Failed to persist list of type %s. Default constructor exists, but is not public.", type.getType().getTypeName());
			throw new RuntimeException(msg);
		}
		
		Supplier<T> elementFactory = () -> {
			try {
				return elementConstructor.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException("Failed to instantiate object for persisted list.", ex);
			}
		};
		
		persistList(valueName, type, elementFactory, getter, setter);
	}
	
	protected <T> void persistList(String valueName, RuntimeType<T> type, Supplier<T> elementFactory, Supplier<List<T>> getter, Consumer<List<T>> setter) {
		persist(valueName, new SaveLoadList<>(type.getType(), elementFactory, getter, setter));
	}

}
