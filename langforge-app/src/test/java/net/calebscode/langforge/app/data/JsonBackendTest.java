package net.calebscode.langforge.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.ObjectMapper;

public class JsonBackendTest {

	private static ObjectMapper mapper;

	JsonBackend backend;

	@BeforeAll
	static void beforeAll() {
		mapper = new ObjectMapper();
	}

	@BeforeEach
	void beforeEach() {
		backend = new JsonBackend();
	}

	@Test
	void testSaveDouble() throws Exception {
		var output = new ByteArrayOutputStream();

		backend.save(
			output,
			new SaveLoadObject(Map.of("testDouble", new SaveLoadDouble(54321.12345)))
		);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testDouble": 54321.12345
			}
			""",
			result
		);
	}

	@Test
	void testSaveInteger() throws Exception {
		var output = new ByteArrayOutputStream();

		backend.save(
			output,
			new SaveLoadObject(Map.of("testInt", new SaveLoadInteger(641927837)))
		);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testInt": 641927837
			}
			""",
			result
		);
	}

	@Test
	void testSaveList() throws Exception {
		var output = new ByteArrayOutputStream();
		var list = new SaveLoadList();
		list.add(new SaveLoadInteger(10));
		list.add(new SaveLoadString("hello"));

		backend.save(
			output,
			new SaveLoadObject(Map.of("testList", list))
		);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testList": [
					10,
					"hello"
				]
			}
			""",
			result
		);
	}

	@Test
	void testSaveObject() throws Exception {
		var output = new ByteArrayOutputStream();
		var object = new SaveLoadObject();
		object.put("age", new SaveLoadInteger(23));
		object.put("name", new SaveLoadString("Takeshi"));

		backend.save(
			output,
			new SaveLoadObject(Map.of("testObject", object))
		);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testObject": {
					"age": 23,
					"name": "Takeshi"
				}
			}
			""",
			result
		);
	}

	@Test
	void testSaveString() throws Exception {
		var output = new ByteArrayOutputStream();

		backend.save(
			output,
			new SaveLoadObject(Map.of(
				"testString",
				new SaveLoadString("This is some text!\nAnd more.")
			))
		);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testString": "This is some text!\\nAnd more."
			}
			""",
			result
		);
	}

	@Test
	void testSaveNull() throws Exception {
		var output = new ByteArrayOutputStream();
		var object = new SaveLoadObject();
		object.put("testNull", null);

		backend.save(output, object);

		var result = output.toString(StandardCharsets.UTF_8);
		assertJsonEquals(
			"""
			{
				"testNull": null
			}
			""",
			result
		);
	}

	@Test
	void testLoadDouble() throws Exception {
		String json =
			"""
			{
				"testDouble": 1234.7891
			}
			""";
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		var result = backend.load(input);
		assertEquals(new SaveLoadDouble(1234.7891), result.get("testDouble"));
	}

	@Test
	void testLoadInteger() throws Exception {
		String json =
			"""
			{
				"testInteger": 999111444
			}
			""";
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		var result = backend.load(input);
		assertEquals(new SaveLoadInteger(999111444), result.get("testInteger"));
	}

	@Test
	void testLoadList() throws Exception {
		String json =
			"""
			{
				"testList": [1, "hello"]
			}
			""";
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		var result = backend.load(input);

		assertTrue(result.get("testList").isList());
		var list = result.get("testList").asList();
		assertEquals(new SaveLoadInteger(1), list.get(0));
		assertEquals(new SaveLoadString("hello"), list.get(1));
	}

	@Test
	void testLoadObject() throws Exception {
		String json =
			"""
			{
				"testObject": {
					"name": "Quentin",
					"age": 67
				}
			}
			""";
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		var result = backend.load(input);

		assertTrue(result.get("testObject").isObject());
		var object = result.get("testObject").asObject();
		assertEquals(new SaveLoadString("Quentin"), object.get("name"));
		assertEquals(new SaveLoadInteger(67), object.get("age"));
	}

	@Test
	void testLoadString() throws Exception {
		String json =
			"""
			{
				"testString": "hello world!"
			}
			""";
		var input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		var result = backend.load(input);

		assertEquals(new SaveLoadString("hello world!"), result.get("testString"));
	}

	private static void assertJsonEquals(String expected, String actual) {
		var expectedNode = mapper.readTree(expected);
		var actualNode = mapper.readTree(actual);
		assertEquals(expectedNode, actualNode);
	}

}
