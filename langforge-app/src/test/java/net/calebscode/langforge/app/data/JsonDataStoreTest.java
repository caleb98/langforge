package net.calebscode.langforge.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonDataStoreTest {

	JsonDataStore store;
	DynamicModel testModel;

	@BeforeEach
	void beforeEach() {
		store = new JsonDataStore();
		testModel = new DynamicModel();
	}
	
	@Test
	void saveNullThrows() {
		var model = new SaveLoadModel() {{
			persist("null", null);
		}};
		var output = new ByteArrayOutputStream();
		
		assertThrows(NullPointerException.class, () -> store.save(output, Map.of("model", model)));
	}
	
	@Test
	void saveStringObject() throws Exception {
		testModel.add("value", "test");
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": "test"
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadStringObject() throws Exception {
		testModel.<String>add("value");
		var source =
			"""
			{
				"model": {
					"value": "bar"
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals("bar", testModel.<String>get("value"));
	}

	@Test
	void saveString() throws Exception {
		testModel.addString("value", "test");
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": "test"
				}
			}
			""";

		assertEquals(expected, json);
	}

	@Test
	void loadString() throws Exception {
		testModel.addString("value");
		var source =
			"""
			{
				"model": {
					"value": "bar"
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals("bar", testModel.<String>get("value"));
	}

	@Test
	void saveList() throws Exception {
		testModel.addList("strings", new RuntimeType<String>() {}, List.of("hello", "world"));
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"strings": [
						"hello",
						"world"
					]
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadList() throws Exception {
		testModel.addList("strings", new RuntimeType<String>() {});
		var source =
			"""
			{
				"model": {
					"strings": [
						"foo",
						"bar"
					]
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals(List.of("foo", "bar"), testModel.get("strings"));
	}
	
	@Test
	void saveModelList() throws Exception {
		testModel.addList("models", new RuntimeType<DynamicModel>() {}, List.of(
			new DynamicModel() {{ addString("value", "hello"); }},
			new DynamicModel() {{ addString("value", "world"); }}
		));
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"models": [
						{
							"value": "hello"
						},
						{
							"value": "world"
						}
					]
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadModelList() throws Exception {
		testModel.addList("models", new RuntimeType<DynamicModel>() {}, () -> new DynamicModel() {{
			addString("value");
		}});
		var source =
			"""
			{
				"model": {
					"models": [
						{
							"value": "foo"
						},
						{
							"value": "bar"
						}
					]
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		var expected = List.of(
			new DynamicModel() {{ addString("value", "foo"); }},
			new DynamicModel() {{ addString("value", "bar"); }}
		);
		assertEquals(expected, testModel.get("models"));
	}
	
	@Test
	void loadModelListThrowsIfArrayDoesNotContainObjects() {
		testModel.addList("models", new RuntimeType<DynamicModel>() {}, () -> new DynamicModel() {{
			addString("value");
		}});

		var source =
			"""
			{
				"model": {
					"models": [
						"not",
						"an",
						"object"
					]
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		assertThrows(RuntimeException.class, () -> store.load(input, Map.of("model", testModel)));
	}
	
	@Test
	void saveModelListNested() throws Exception {
		testModel.addList("outer", new RuntimeType<DynamicModel>() {}, List.of(
			new DynamicModel() {{
				addList("inner", new RuntimeType<DynamicModel>() {}, List.of(
					new DynamicModel() {{ addString("value", "hello"); }},
					new DynamicModel() {{ addString("value", "world"); }}
				));
			}}
		));
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"outer": [
						{
							"inner": [
								{
									"value": "hello"
								},
								{
									"value": "world"
								}
							]
						}
					]
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadModelListNested() throws Exception {
		testModel.addList("outer", new RuntimeType<DynamicModel>() {}, () -> new DynamicModel() {{
			addList("inner", new RuntimeType<DynamicModel>() {}, () -> new DynamicModel() {{
				addString("value");
			}});
		}});

		var source =
			"""
			{
				"model": {
					"outer": [
						{
							"inner": [
								{
									"value": "foo"
								},
								{
									"value": "bar"
								}
							]
						}
					]
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		var expected = new DynamicModel() {{
			addList("outer", new RuntimeType<DynamicModel>() {}, List.of(
				new DynamicModel() {{
					addList("inner", new RuntimeType<DynamicModel>() {}, List.of(
						new DynamicModel() {{ addString("value", "foo"); }},
						new DynamicModel() {{ addString("value", "bar"); }}
					));
				}}
			));
		}};
		assertEquals(expected, testModel);
	}
	
	static class DynamicModel extends SaveLoadModel {
		
		Map<String, Object> values = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		<T> T get(String name) {
			return (T) values.get(name);
		}
		
		<T> void add(String name) {
			add(name, null);
		}
		
		<T> void add(String name, T value) {
			values.put(name, value);
			persist(name, new RuntimeType<T>() {}, () -> (T) values.get(name), v -> values.put(name, v));
		}
		
		void addString(String name) {
			addString(name, null);
		}
		
		void addString(String name, String initial) {
			values.put(name, initial);
			persistString(name, () -> (String) values.get(name), s -> values.put(name, s));
		}
		
		<T> void addList(String name, RuntimeType<T> elementType) {
			addList(name, elementType, (List<T>)null);
		}
		
		@SuppressWarnings("unchecked")
		<T> void addList(String name, RuntimeType<T> elementType, List<T> list) {
			values.put(name, list);
			persistList(name, elementType, () -> (List<T>) values.get(name), l -> values.put(name, l));
		}
		
		@SuppressWarnings("unchecked")
		<T> void addList(String name, RuntimeType<T> elementType, Supplier<T> elementFactory) {
			var list = List.<T>of();
			values.put(name, list);
			persistList(name, elementType, () -> (List<T>) values.get(name), l -> values.put(name, l), elementFactory);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof DynamicModel other)) {
				return false;
			}
			return values.equals(other.values);
		}
		
		@Override
		public String toString() {
			var entries = new ArrayList<String>();
			for (var entry : values.entrySet()) {
				var key = entry.getKey();
				var value = entry.getValue();
				entries.add(String.format("%s=%s", key, value.toString()));
			}

			var sb = new StringBuilder();
			sb.append("{");
			sb.append(String.join(", ", entries));
			sb.append("}");
			return sb.toString();
		}
		
	}
	
}
