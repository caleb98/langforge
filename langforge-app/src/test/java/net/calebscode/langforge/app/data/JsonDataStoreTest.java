package net.calebscode.langforge.app.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
	void saveCharacter() throws Exception {
		testModel.addCharacter("value", 'a');
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": "a"
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadCharacter() throws Exception {
		testModel.addCharacter("value");
		var source =
			"""
			{
				"model": {
					"value": "b"
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals('b', testModel.<Character>get("value"));
	}
	
	@Test
	void saveByte() throws Exception {
		testModel.addByte("value", (byte) 5);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 5
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadByte() throws Exception {
		testModel.addByte("value");
		var source =
			"""
			{
				"model": {
					"value": 10
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals((byte) 10, testModel.<Byte>get("value"));
	}
	
	@Test
	void saveShort() throws Exception {
		testModel.addShort("value", (short) 12);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 12
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadShort() throws Exception {
		testModel.addShort("value");
		var source =
			"""
			{
				"model": {
					"value": 78
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals((short) 78, testModel.<Short>get("value"));
	}
	
	@Test
	void saveInteger() throws Exception {
		testModel.addInteger("value", 42);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 42
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadInteger() throws Exception {
		testModel.addInteger("value");
		var source =
			"""
			{
				"model": {
					"value": 123
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals(123, testModel.<Integer>get("value"));
	}
	
	@Test
	void saveLong() throws Exception {
		testModel.addLong("value", 1234567891011121314L);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 1234567891011121314
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadLong() throws Exception {
		testModel.addLong("value");
		var source =
			"""
			{
				"model": {
					"value": 1234567891011121314
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals(1234567891011121314L, testModel.<Long>get("value"));
	}
	
	@Test
	void saveFloat() throws Exception {
		testModel.addFloat("value", 12.5993f);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 12.5993
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadFloat() throws Exception {
		testModel.addFloat("value");
		var source =
			"""
			{
				"model": {
					"value": 12.5993
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals(12.5993f, testModel.<Float>get("value"));
	}
	
	@Test
	void saveDouble() throws Exception {
		testModel.addDouble("value", 184.848491001);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"value": 184.848491001
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadDouble() throws Exception {
		testModel.addDouble("value");
		var source =
			"""
			{
				"model": {
					"value": 184.848491001
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		assertEquals(184.848491001, testModel.<Double>get("value"));
	}
	
	@Test
	void saveModel() throws Exception {
		var innerModel = new DynamicModel();
		innerModel.addString("name", "san");
		innerModel.addInteger("age", 21);
		
		testModel.addModel("user", innerModel);
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		var expected =
			"""
			{
				"model": {
					"user": {
						"name": "san",
						"age": 21
					}
				}
			}
			""";

		assertEquals(expected, json);
	}
	
	@Test
	void loadModel() throws Exception {
		testModel.addModel("user", userModel -> {
			userModel.addString("name");
			userModel.addInteger("age");
		});
		var source =
			"""
			{
				"model": {
					"user": {
						"name": "ashitaka",
						"age": 20
					}
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		var user = testModel.<DynamicModel>get("user");
		assertEquals("ashitaka", user.<String>get("name"));
		assertEquals(20, user.<Integer>get("age"));
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
	void saveMap() throws Exception {
		testModel.addMap("users", new RuntimeType<String>() {}, new RuntimeType<DynamicModel>() {}, Map.of(
			"john1234", new DynamicModel() {{
				addInteger("id", 1);
			}},
			"sadako99", new DynamicModel() {{
				addInteger("id", 2);
			}}
		));
		var output = new ByteArrayOutputStream();
		
		store.save(output, Map.of("model", testModel));
		var json = output.toString(StandardCharsets.UTF_8);

		// Entry ordering isn't guaranteed for maps, so there's two valid outputs here.
		var validOutputs = List.of(
			"""
			{
				"model": {
					"users": [
						{
							"key": "john1234",
							"value": {
								"id": 1
							}
						},
						{
							"key": "sadako99",
							"value": {
								"id": 2
							}
						}
					]
				}
			}
			""",
			"""
			{
				"model": {
					"users": [
						{
							"key": "sadako99",
							"value": {
								"id": 2
							}
						},
						{
							"key": "john1234",
							"value": {
								"id": 1
							}
						}
					]
				}
			}
			"""
		);
		
		assertThat(json, is(in(validOutputs)));
	}
	
	@Test
	void loadMap() throws Exception {
		testModel.addMap("users", new RuntimeType<String>() {}, new RuntimeType<DynamicModel>() {}, () -> "", () -> new DynamicModel() {{
			addInteger("id");
		}});
		var source =
			"""
			{
				"model": {
					"users": [
						{
							"key": "caleb2",
							"value": {
								"id": 5
							}
						},
						{
							"key": "netguy",
							"value": {
								"id": 99
							}
						}
					]
				}
			}
			""";
		
		var input = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("model", testModel));

		var expected = new DynamicModel() {{
			addMap("users", new RuntimeType<String>() {}, new RuntimeType<DynamicModel>() {}, Map.of(
				"caleb2", new DynamicModel() {{ addInteger("id", 5); }},
				"netguy", new DynamicModel() {{ addInteger("id", 99); }}
			));
		}};
		assertEquals(expected, testModel);
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
		
		void addString(String name) {
			addString(name, null);
		}
		
		void addString(String name, String value) {
			values.put(name, value);
			persist(name, () -> (String) values.get(name), v -> values.put(name, v));
		}
		
		void addCharacter(String name) {
			addCharacter(name, null);
		}
		
		void addCharacter(String name, Character value) {
			values.put(name, value);
			persist(name, () -> (Character) values.get(name), v -> values.put(name, v));
		}
		
		void addByte(String name) {
			addByte(name, null);
		}
		
		void addByte(String name, Byte value) {
			values.put(name, value);
			persist(name, () -> (Byte) values.get(name), v -> values.put(name, v));
		}
		
		void addShort(String name) {
			addShort(name, null);
		}
		
		void addShort(String name, Short value) {
			values.put(name, value);
			persist(name, () -> (Short) values.get(name), v -> values.put(name, v));
		}
		
		void addInteger(String name) {
			addInteger(name, null);
		}
		
		void addInteger(String name, Integer value) {
			values.put(name, value);
			persist(name, () -> (Integer) values.get(name), v -> values.put(name, v));
		}
		
		void addLong(String name) {
			addLong(name, null);
		}
		
		void addLong(String name, Long value) {
			values.put(name, value);
			persist(name, () -> (Long) values.get(name), v -> values.put(name, v));
		}
		
		void addFloat(String name) {
			addFloat(name, null);
		}
		
		void addFloat(String name, Float value) {
			values.put(name, value);
			persist(name, () -> (Float) values.get(name), v -> values.put(name, v));
		}
		
		void addDouble(String name) {
			addDouble(name, null);
		}
		
		void addDouble(String name, Double value) {
			values.put(name, value);
			persist(name, () -> (Double) values.get(name), v -> values.put(name, v));
		}
		
		void addModel(String name, Consumer<DynamicModel> configurer) {
			var model = new DynamicModel();
			configurer.accept(model);
			addModel(name, model);
		}
		
		void addModel(String name, SaveLoadModel model) {
			values.put(name, model);
			persist(name, () -> (SaveLoadModel) values.get(name));
		}
		
		<T> void addList(String name, RuntimeType<T> elementType) {
			addList(name, elementType, new ArrayList<>());
		}
		
		@SuppressWarnings("unchecked")
		<T> void addList(String name, RuntimeType<T> elementType, List<T> list) {
			values.put(name, list);
			persistList(name, elementType, () -> (List<T>) values.get(name));
		}
		
		@SuppressWarnings("unchecked")
		<T> void addList(String name, RuntimeType<T> elementType, Supplier<T> elementFactory) {
			values.put(name, new ArrayList<>());
			persistList(name, elementType, elementFactory, () -> (List<T>) values.get(name));
		}
		
		<T> void addMap(String name, RuntimeType<T> keyType, RuntimeType<T> valueType) {
			addMap(name, keyType, valueType, new HashMap<>());
		}
		
		@SuppressWarnings("unchecked")
		<K, V> void addMap(String name, RuntimeType<K> keyType, RuntimeType<V> valueType, Map<K, V> map) {
			values.put(name, map);
			persistMap(name, keyType, valueType, () -> (Map<K, V>) values.get(name));
		}
		
		@SuppressWarnings("unchecked")
		<K, V> void addMap(String name, RuntimeType<K> keyType, RuntimeType<V> valueType, Supplier<K> keyFactory, Supplier<V> valueFactory) {
			values.put(name, new HashMap<K, V>());
			persistMap(name, keyType, valueType, keyFactory, valueFactory, () -> (Map<K, V>) values.get(name));
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
