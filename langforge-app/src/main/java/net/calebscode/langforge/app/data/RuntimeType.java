package net.calebscode.langforge.app.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RuntimeType<T> {

	private final Type type;
	
	protected RuntimeType() {
		var parent = getClass().getGenericSuperclass();
		if (!(parent instanceof ParameterizedType generic)) {
			throw new IllegalStateException("RuntimeType must be created using an anonymous sublass.");
		}
		type = generic.getActualTypeArguments()[0];
	}
	
	public Type getType() {
		return type;
	}

}
