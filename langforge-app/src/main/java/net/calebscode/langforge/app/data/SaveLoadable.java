package net.calebscode.langforge.app.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface SaveLoadable<T> {

	T getValue();
	SaveLoadSchema<T> getSchema();
	
	static boolean isTypeSaveLoadable(Type type) {
		if (type instanceof Class<?> clazz) {
			return SaveLoadable.class.isAssignableFrom(clazz);
		}
		else if (type instanceof ParameterizedType parameterized) {
			return isTypeSaveLoadable(parameterized.getRawType());
		}
		
		return false;
	}
	
}
