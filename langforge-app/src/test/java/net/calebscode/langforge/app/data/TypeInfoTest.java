package net.calebscode.langforge.app.data;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class TypeInfoTest {

	@Test
	void typeInfoForClass() {
		var info = new TypeInfo<String>(){};

		assertEquals(String.class, info.getType());
		assertTrue(info.getType() instanceof Class);
	}

	@Test
	void typeInfoForGenericArrayType() {
		var info = new TypeInfo<List<String>[]>(){};
		var type = info.getType();

		assertTrue(type instanceof GenericArrayType);
		var inner = ((GenericArrayType) type).getGenericComponentType();
		assertTrue(inner instanceof ParameterizedType);
		var param = (ParameterizedType) inner;
		assertEquals(List.class, param.getRawType());
		assertEquals(1, param.getActualTypeArguments().length);
		assertEquals(String.class, param.getActualTypeArguments()[0]);
	}

	@Test
	void typeInfoForMap() {
		var info = new TypeInfo<Map<String, Integer>>(){};
		var type = info.getType();

		assertTrue(type instanceof ParameterizedType);
		var param = (ParameterizedType) type;
		assertEquals(Map.class, param.getRawType());
		assertEquals(2, param.getActualTypeArguments().length);
		assertEquals(String.class, param.getActualTypeArguments()[0]);
		assertEquals(Integer.class, param.getActualTypeArguments()[1]);
	}

	@Test
	void typeInfoForGenericSublassWithWrappingGeneric() {
		var info = new TypeInfoList<String>();
		var type = info.getType();

		assertTrue(type instanceof ParameterizedType);
		var param = (ParameterizedType) type;
		assertEquals(List.class, param.getRawType());
		assertEquals(1, param.getActualTypeArguments().length);
		assertEquals(String.class, param.getActualTypeArguments()[0]);
	}

	@SuppressWarnings("rawtypes")
	@Test
	void instantiatingNonGenericThrows() {
		assertThrows(IllegalStateException.class, () -> new TypeInfo(){});
	}

	@Test
	void instantiatingAnonymousSubclassOfSubclassThrows() {
		assertThrows(IllegalStateException.class, () -> new TypeInfoSubclass(){});
	}

	@Test
	void instantiatingGenericThrows() {
		assertThrows(IllegalStateException.class, () -> createTypeInfoGeneric());
	}

	@Test
	void instantiatingWithNestedGenericThrows() {
		assertThrows(IllegalStateException.class, () -> createTypeInfoNestedGeneric());
	}

	@Test
	void instantiatingWithNestedWildcardThrows() {
		assertThrows(IllegalStateException.class, () -> new TypeInfo<Map<String, List<Set<Map<?, String>>>>>(){});
	}

	static <T> TypeInfo<T> createTypeInfoGeneric() {
		return new TypeInfo<T>(){};
	}

	static <T> TypeInfo<?> createTypeInfoNestedGeneric() {
		return new TypeInfo<Map<List<String>, Map<Set<String>, T>>>(){};
	}

	private static class TypeInfoSubclass extends TypeInfo<String> {}
	private static class TypeInfoList<T> extends TypeInfo<List<String>> {}

}
