package net.calebscode.langforge.app.data;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class SaveLoadTypeMappings {

	private SaveLoadTypeMappings() {}

	static SaveLoadValue<?> createSaveLoadValueForType(Type type) {
		if (type instanceof Class<?> clazz) {
			return createSaveLoadValueForClass(clazz);
		}
		else if (type instanceof ParameterizedType parameterizedType) {
			return createSaveLoadValueForParameterizedType(parameterizedType);
		}
		else if (type instanceof GenericArrayType genericArrayType) {
			return createSaveLoadValueForGenericArrayType(genericArrayType);
		}
		else {
			var message = String.format("Cannot map type '%s' to SaveLoadValue.", type);
			throw new IllegalArgumentException(message);
		}
	}

	static SaveLoadValue<?> createSaveLoadValueForClass(Class<?> clazz) {
		if (clazz == Boolean.class || clazz == boolean.class) {
			var wrapper = new Wrapper<Boolean>();
			return new SaveLoadBoolean(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Byte.class || clazz == byte.class) {
			var wrapper = new Wrapper<Byte>();
			return new SaveLoadByte(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Character.class || clazz == char.class) {
			var wrapper = new Wrapper<Character>();
			return new SaveLoadCharacter(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Double.class || clazz == double.class) {
			var wrapper = new Wrapper<Double>();
			return new SaveLoadDouble(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Float.class || clazz == float.class) {
			var wrapper = new Wrapper<Float>();
			return new SaveLoadFloat(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Integer.class || clazz == int.class) {
			var wrapper = new Wrapper<Integer>();
			return new SaveLoadInteger(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Long.class || clazz == long.class) {
			var wrapper = new Wrapper<Long>();
			return new SaveLoadLong(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == Short.class || clazz == short.class) {
			var wrapper = new Wrapper<Short>();
			return new SaveLoadShort(wrapper::getValue, wrapper::setValue);
		}
		else if (clazz == String.class) {
			var wrapper = new Wrapper<String>();
			return new SaveLoadString(wrapper::getValue, wrapper::setValue);
		}
		else {
			var message = String.format("Cannot map class '%s' to SaveLoadValue.", clazz);
			throw new IllegalArgumentException(message);
		}
	}

	static SaveLoadValue<?> createSaveLoadValueForParameterizedType(ParameterizedType type) {
		var rawType = type.getRawType();
		if (!(rawType instanceof Class<?> clazz)) {
			var message = String.format("Cannot map parameterized type '%s' to SaveLoadValue.", type);
			throw new IllegalArgumentException(message);
		}

		if (clazz == List.class || clazz == Set.class) {
			return createSaveLoadList(type.getActualTypeArguments()[0]);
		} else if (clazz == Map.class) {
			var message = "SaveLoadValue mapping for Map<K, V> is not yet implemented.";
			throw new UnsupportedOperationException(message);
		} else {
			var message = String.format("Cannot map parameterized type '%s' to SaveLoadValue.", type);
			throw new IllegalArgumentException(message);
		}
	}

	static SaveLoadValue<?> createSaveLoadValueForGenericArrayType(GenericArrayType type) {
		return createSaveLoadList(type.getGenericComponentType());
	}

	private static SaveLoadList<SaveLoadValue<?>> createSaveLoadList(Type elementType) {
		// We call this here and ignore the result to ensure that the element type can be successfully
		// mapped to a SaveLoadValue. This ensure that if calling this method would throw an exception,
		// it happens now instead of at some undetermined point later.
		createSaveLoadValueForType(elementType);
		var wrapper = new Wrapper<List<SaveLoadValue<?>>>();
		return new SaveLoadList<SaveLoadValue<?>>(() -> createSaveLoadValueForType(elementType), wrapper::getValue, wrapper::setValue);
	}

	private static class Wrapper<T> {

		T value = null;

		T getValue() {
			return value;
		}

		void setValue(T newValue) {
			value = newValue;
		}

	}


}
