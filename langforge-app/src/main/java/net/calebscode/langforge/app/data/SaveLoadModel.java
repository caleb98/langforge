package net.calebscode.langforge.app.data;

import static net.calebscode.langforge.app.data.SaveLoadTypeMappings.createSaveLoadValueForType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.beans.property.ListProperty;

public class SaveLoadModel {

	private Map<String, SaveLoadValue<?>> values = new HashMap<>();

	public Map<String, SaveLoadValue<?>> getValues() {
		return Collections.unmodifiableMap(values);
	}

	protected void persist(String valueName, SaveLoadValue<?> value) {
		if (values.containsKey(valueName)) {
			throw new IllegalArgumentException("Value with name '" + valueName + "' already registered.");
		}

		values.put(valueName, value);
	}

	/*
	 * Basic Persistence Methods
	 */

	protected void persistBoolean(String valueName, Supplier<Boolean> getter, Consumer<Boolean> setter) {
		persist(valueName, new SaveLoadBoolean(getter, setter));
	}

	protected void persistByte(String valueName, Supplier<Byte> getter, Consumer<Byte> setter) {
		persist(valueName, new SaveLoadByte(getter, setter));
	}

	protected void persistCharacter(String valueName, Supplier<Character> getter, Consumer<Character> setter) {
		persist(valueName, new SaveLoadCharacter(getter, setter));
	}

	protected void persistDouble(String valueName, Supplier<Double> getter, Consumer<Double> setter) {
		persist(valueName, new SaveLoadDouble(getter, setter));
	}

	protected void persistFloat(String valueName, Supplier<Float> getter, Consumer<Float> setter) {
		persist(valueName, new SaveLoadFloat(getter, setter));
	}

	protected void persistInteger(String valueName, Supplier<Integer> getter, Consumer<Integer> setter) {
		persist(valueName, new SaveLoadInteger(getter, setter));
	}

	protected void persistLong(String valueName, Supplier<Long> getter, Consumer<Long> setter) {
		persist(valueName, new SaveLoadLong(getter, setter));
	}

	protected void persistShort(String valueName, Supplier<Short> getter, Consumer<Short> setter) {
		persist(valueName, new SaveLoadShort(getter, setter));
	}

	protected void persistString(String valueName, Supplier<String> getter, Consumer<String> setter) {
		persist(valueName, new SaveLoadString(getter, setter));
	}

	@SuppressWarnings("unchecked")
	protected <T> void persistList(String valueName, Supplier<T> elementCreator, TypeInfo<T> elementTypeInfo, Supplier<List<T>> getter, Consumer<List<T>> setter) {
		final Type elementType = elementTypeInfo.getType();

		Supplier<SaveLoadValue<T>> saveLoadValueCreator = () -> {
			var innerElement = elementCreator.get();
			var saveLoadValue = (SaveLoadValue<T>) createSaveLoadValueForType(elementType);
			saveLoadValue.setValue(innerElement);
			return saveLoadValue;
		};

		Supplier<List<SaveLoadValue<T>>> saveLoadListGetter = () -> {
			return getter
				.get()
				.stream()
				.map(realValue -> {
					SaveLoadValue<T> saveLoadValue = (SaveLoadValue<T>) createSaveLoadValueForType(elementType);
					saveLoadValue.setValue(realValue);
					return saveLoadValue;
				})
				.collect(Collectors.toCollection(() -> new ArrayList<>()));
		};

		Consumer<List<SaveLoadValue<T>>> saveLoadListSetter = (saveLoadList) -> {
			var realList = saveLoadList
				.stream()
				.map(SaveLoadValue::getValue)
				.toList();

			setter.accept(realList);
		};


		persist(valueName, new SaveLoadList<>(saveLoadValueCreator, saveLoadListGetter, saveLoadListSetter));
	}

	/*
	 * Property Persistence Methods
	 */

	@SuppressWarnings("unchecked")
	protected <T> void persistList(String valueName, Supplier<T> elementCreator, TypeInfo<T> elementTypeInfo, ListProperty<T> listProperty) {
		final Type elementType = elementTypeInfo.getType();

		Supplier<SaveLoadValue<T>> saveLoadValueCreator = () -> {
			var innerElement = elementCreator.get();
			var saveLoadValue = (SaveLoadValue<T>) createSaveLoadValueForType(elementType);
			saveLoadValue.setValue(innerElement);
			return saveLoadValue;
		};

		Supplier<List<SaveLoadValue<T>>> getter = () -> {
			return listProperty
				.stream()
				.map(realValue -> {
					SaveLoadValue<T> saveLoadValue = (SaveLoadValue<T>) createSaveLoadValueForType(elementType);
					saveLoadValue.setValue(realValue);
					return saveLoadValue;
				})
				.collect(Collectors.toCollection(() -> new ArrayList<>()));
		};

		Consumer<List<SaveLoadValue<T>>> setter = (saveLoadList) -> {
			var realList = saveLoadList
				.stream()
				.map(SaveLoadValue::getValue)
				.toList();

			listProperty.setAll(realList);
		};

		persist(valueName, new SaveLoadList<SaveLoadValue<T>>(saveLoadValueCreator, getter, setter));
	}

}
