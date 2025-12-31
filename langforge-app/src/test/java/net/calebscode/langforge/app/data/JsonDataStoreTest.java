package net.calebscode.langforge.app.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
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
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.app.data.JsonDataStoreTest.TestModel;

public class JsonDataStoreTest {

	JsonDataStore store;
	TestModel testModel;
	
	String saveJson(TestModel model) throws IOException {
		var output = new ByteArrayOutputStream();
		store.save(output, Map.of("testModel", new SaveLoadObject<>(model, model.getSchema())));
		return output.toString(StandardCharsets.UTF_8);
	}
	
	void loadJson(String json, TestModel model) throws IOException {
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		store.load(input, Map.of("testModel", new SaveLoadObject<>(model, model.getSchema())));
	}

	@BeforeEach
	void beforeEach() {
		store = new JsonDataStore();
		testModel = new TestModel();
	}
	
	@Test
	void saveString() throws Exception {
		testModel.addString("value", "test");
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": "bar"
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals("bar", testModel.<String>get("value"));
	}
	
	@Test
	void saveCharacter() throws Exception {
		testModel.addCharacter("value", 'a');
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": "b"
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals('b', testModel.<Character>get("value"));
	}
	
	@Test
	void saveByte() throws Exception {
		testModel.addByte("value", (byte) 5);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 10
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals((byte) 10, testModel.<Byte>get("value"));
	}
	
	@Test
	void saveShort() throws Exception {
		testModel.addShort("value", (short) 12);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 78
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals((short) 78, testModel.<Short>get("value"));
	}
	
	@Test
	void saveInteger() throws Exception {
		testModel.addInteger("value", 42);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 123
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals(123, testModel.<Integer>get("value"));
	}
	
	@Test
	void saveLong() throws Exception {
		testModel.addLong("value", 1234567891011121314L);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 1234567891011121314
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals(1234567891011121314L, testModel.<Long>get("value"));
	}
	
	@Test
	void saveFloat() throws Exception {
		testModel.addFloat("value", 12.5993f);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 12.5993
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals(12.5993f, testModel.<Float>get("value"));
	}
	
	@Test
	void saveDouble() throws Exception {
		testModel.addDouble("value", 184.848491001);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"value": 184.848491001
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals(184.848491001, testModel.<Double>get("value"));
	}
	
	@Test
	void saveNested() throws Exception {
		var innerModel = new TestModel();
		innerModel.addString("name", "san");
		innerModel.addInteger("age", 21);
		
		testModel.addNested("user", innerModel);
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
	void loadNested() throws Exception {
		testModel.addNested("user", userModel -> {
			userModel.addString("name");
			userModel.addInteger("age");
		});
		var source =
			"""
			{
				"testModel": {
					"user": {
						"name": "ashitaka",
						"age": 20
					}
				}
			}
			""";
		
		loadJson(source, testModel);

		var user = testModel.<TestModel>get("user");
		assertEquals("ashitaka", user.<String>get("name"));
		assertEquals(20, user.<Integer>get("age"));
	}

	@Test
	void saveList() throws Exception {
		testModel.addList("strings", new RuntimeType<String>() {}, List.of("hello", "world"));
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
				"testModel": {
					"strings": [
						"foo",
						"bar"
					]
				}
			}
			""";
		
		loadJson(source, testModel);

		assertEquals(List.of("foo", "bar"), testModel.get("strings"));
	}
	
	@Test
	void saveMap() throws Exception {
		testModel.addMap("users", new RuntimeType<String>() {}, new RuntimeType<TestModel>() {}, Map.of(
			"john1234", new TestModel() {{
				addInteger("id", 1);
			}},
			"sadako99", new TestModel() {{
				addInteger("id", 2);
			}}
		));
		
		var json = saveJson(testModel);

		// Entry ordering isn't guaranteed for maps, so there's two valid outputs here.
		var validOutputs = List.of(
			"""
			{
				"testModel": {
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
				"testModel": {
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
		testModel.addMap("users", new RuntimeType<String>() {}, new RuntimeType<TestModel>() {}, _ -> "", _ -> new TestModel() {{
			addInteger("id");
		}});
		var source =
			"""
			{
				"testModel": {
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
		
		loadJson(source, testModel);

		var expected = new TestModel() {{
			addMap("users", new RuntimeType<String>() {}, new RuntimeType<TestModel>() {}, Map.of(
				"caleb2", new TestModel() {{ addInteger("id", 5); }},
				"netguy", new TestModel() {{ addInteger("id", 99); }}
			));
		}};
		assertEquals(expected, testModel);
	}
	
	@Test
	void saveListOfNestedModels() throws Exception {
		testModel.addList("schemas", new RuntimeType<TestModel>() {}, List.of(
			new TestModel() {{ addString("value", "hello"); }},
			new TestModel() {{ addString("value", "world"); }}
		));
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
					"schemas": [
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
	void loadListOfNestedModels() throws Exception {
		testModel.addList("schemas", new RuntimeType<TestModel>() {}, _ -> new TestModel() {{
			addString("value");
		}});
		var source =
			"""
			{
				"testModel": {
					"schemas": [
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
		
		loadJson(source, testModel);

		var expected = List.of(
			new TestModel() {{ addString("value", "foo"); }},
			new TestModel() {{ addString("value", "bar"); }}
		);
		assertEquals(expected, testModel.get("schemas"));
	}
	
	@Test
	void saveListOfDoublyNestedModels() throws Exception {
		testModel.addList("outer", new RuntimeType<TestModel>() {}, List.of(
			new TestModel() {{
				addList("inner", new RuntimeType<TestModel>() {}, List.of(
					new TestModel() {{ addString("value", "hello"); }},
					new TestModel() {{ addString("value", "world"); }}
				));
			}}
		));
		
		var json = saveJson(testModel);

		var expected =
			"""
			{
				"testModel": {
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
	void loadListOfDoublyNestedModels() throws Exception {
		testModel.addList("outer", new RuntimeType<TestModel>() {}, _ -> new TestModel() {{
			addList("inner", new RuntimeType<TestModel>() {}, _ -> new TestModel() {{
				addString("value");
			}});
		}});
		var source =
			"""
			{
				"testModel": {
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
		
		loadJson(source, testModel);

		var expected = new TestModel() {{
			addList("outer", new RuntimeType<TestModel>() {}, List.of(
				new TestModel() {{
					addList("inner", new RuntimeType<TestModel>() {}, List.of(
						new TestModel() {{ addString("value", "foo"); }},
						new TestModel() {{ addString("value", "bar"); }}
					));
				}}
			));
		}};
		assertEquals(expected, testModel);
	}
	
	static class TestModel implements SaveLoadable<TestModel> {
		
		SaveLoadSchema<TestModel> schema = new SaveLoadSchema<>();
		Map<String, Object> values = new HashMap<>();
		
		@Override
		public TestModel getValue() {
			return this;
		}
		
		@Override
		public SaveLoadSchema<TestModel> getSchema() {
			return schema;
		}
		
		@SuppressWarnings("unchecked")
		<T> T get(String name) {
			return (T) values.get(name);
		}
		
		<T> void put(String name, T value) {
			values.put(name, value);
		}
		
		void addString(String name) {
			addString(name, null);
		}
		
		void addString(String name, String value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, String v) -> m.put(name, v));
		}
		
		void addCharacter(String name) {
			addCharacter(name, null);
		}
		
		void addCharacter(String name, Character value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Character v) -> m.put(name, v));
		}
		
		void addByte(String name) {
			addByte(name, null);
		}
		
		void addByte(String name, Byte value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Byte v) -> m.put(name, v));
		}
		
		void addShort(String name) {
			addShort(name, null);
		}
		
		void addShort(String name, Short value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Short v) -> m.put(name, v));
		}
		
		void addInteger(String name) {
			addInteger(name, null);
		}
		
		void addInteger(String name, Integer value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Integer v) -> m.put(name, v));
		}
		
		void addLong(String name) {
			addLong(name, null);
		}
		
		void addLong(String name, Long value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Long v) -> m.put(name, v));
		}
		
		void addFloat(String name) {
			addFloat(name, null);
		}
		
		void addFloat(String name, Float value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Float v) -> m.put(name, v));
		}
		
		void addDouble(String name) {
			addDouble(name, null);
		}
		
		void addDouble(String name, Double value) {
			values.put(name, value);
			schema.add(name, m -> m.get(name), (TestModel m, Double v) -> m.put(name, v));
		}
		
		void addNested(String name, Consumer<TestModel> configurer) {
			var model = new TestModel();
			configurer.accept(model);
			addNested(name, model);
		}
		
		void addNested(String name, TestModel model) {
			values.put(name, model);
			schema.addProperty(name, m -> m.get(name));
		}
		
		<T> void addList(String name, RuntimeType<T> elementType) {
			addList(name, elementType, new ArrayList<>());
		}
		
		<T> void addList(String name, RuntimeType<T> elementType, List<T> list) {
			values.put(name, list);
			schema.addList(name, elementType, m -> m.get(name));
		}
		
		<T> void addList(String name, RuntimeType<T> elementType, Function<TestModel, T> elementFactory) {
			values.put(name, new ArrayList<>());
			schema.addList(name, elementType, elementFactory, m -> m.get(name));
		}
		
		<T> void addMap(String name, RuntimeType<T> keyType, RuntimeType<T> valueType) {
			addMap(name, keyType, valueType, new HashMap<>());
		}
		
		<K, V> void addMap(String name, RuntimeType<K> keyType, RuntimeType<V> valueType, Map<K, V> map) {
			values.put(name, map);
			schema.addMap(name, keyType, valueType, m -> m.get(name));
		}
		
		<K, V> void addMap(
			String name,
			RuntimeType<K> keyType,
			RuntimeType<V> valueType,
			Function<TestModel, K> keyFactory,
			Function<TestModel, V> valueFactory
		) {
			values.put(name, new HashMap<K, V>());
			schema.addMap(name, keyType, valueType, keyFactory, valueFactory, m -> m.get(name));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof TestModel other)) {
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
