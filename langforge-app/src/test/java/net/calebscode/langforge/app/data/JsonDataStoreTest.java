package net.calebscode.langforge.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonDataStoreTest {

	private ByteArrayOutputStream output;
	private JsonDataStore dataStore;

	@BeforeEach
	void beforeEach() {
		dataStore = new JsonDataStore();
		output = new ByteArrayOutputStream();
	}

	@AfterEach
	void afterEach() throws IOException {
		output.close();
	}

	@Test
	void testSaveBoolean() throws IOException {
		var boolModel = new TestModel<>(false);
		boolModel.persistBoolean("isSunny", boolModel::getValue, boolModel::setValue);
		var models = Map.<String, SaveLoadModel>of("boolModel", boolModel);
		var expected =
		"""
		{
			"boolModel": {
				"isSunny": false
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveByte() throws IOException {
		var byteModel = new TestModel<>((byte) 42);
		byteModel.persistByte("answer", byteModel::getValue, byteModel::setValue);
		var models = Map.<String, SaveLoadModel>of("byteModel", byteModel);
		var expected =
		"""
		{
			"byteModel": {
				"answer": 42
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveCharacter() throws IOException {
		var characterModel = new TestModel<>('!');
		characterModel.persistCharacter("punctuation", characterModel::getValue, characterModel::setValue);
		var models = Map.<String, SaveLoadModel>of("characterModel", characterModel);
		var expected =
		"""
		{
			"characterModel": {
				"punctuation": "!"
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveDouble() throws IOException {
		var doubleModel = new TestModel<>(1234.56789);
		doubleModel.persistDouble("number", doubleModel::getValue, doubleModel::setValue);
		var models = Map.<String, SaveLoadModel>of("doubleModel", doubleModel);
		var expected =
		"""
		{
			"doubleModel": {
				"number": 1234.56789
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveFloat() throws IOException {
		var floatModel = new TestModel<>((float) 2.5);
		floatModel.persistFloat("number", floatModel::getValue, floatModel::setValue);
		var models = Map.<String, SaveLoadModel>of("floatModel", floatModel);
		var expected =
		"""
		{
			"floatModel": {
				"number": 2.5
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveInteger() throws IOException {
		var intModel = new TestModel<>(42);
		intModel.persistInteger("answer", intModel::getValue, intModel::setValue);
		var models = Map.<String, SaveLoadModel>of("intModel", intModel);
		var expected =
		"""
		{
			"intModel": {
				"answer": 42
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveLong() throws IOException {
		var longModel = new TestModel<>(Long.MAX_VALUE);
		longModel.persistLong("bigNumber", longModel::getValue, longModel::setValue);
		var models = Map.<String, SaveLoadModel>of("longModel", longModel);
		var expected =
		"""
		{
			"longModel": {
				"bigNumber": %d
			}
		}
		""";
		expected = String.format(expected, Long.MAX_VALUE);

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveShort() throws IOException {
		var shortModel = new TestModel<>((short) 42);
		shortModel.persistShort("answer", shortModel::getValue, shortModel::setValue);
		var models = Map.<String, SaveLoadModel>of("shortModel", shortModel);
		var expected =
		"""
		{
			"shortModel": {
				"answer": 42
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveString() throws IOException {
		var stringModel = new TestModel<>("Creative AI");
		stringModel.persistString("selectedCard", stringModel::getValue, stringModel::setValue);
		var models = Map.<String, SaveLoadModel>of("stringModel", stringModel);
		var expected =
		"""
		{
			"stringModel": {
				"selectedCard": "Creative AI"
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveList() throws IOException {
		var listModel = new TestModel<List<String>>(List.of("Hello", "World"));
		listModel.persistList("messages", String::new, new TypeInfo<String>(){}, listModel::getValue, listModel::setValue);
		var models = Map.<String, SaveLoadModel>of("listModel", listModel);
		var expected =
		"""
		{
			"listModel": {
				"messages": [
					"Hello",
					"World"
				]
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	@Test
	void testSaveListOfLists() throws IOException {
		var listModel = new TestModel<List<List<Integer>>>(List.of(List.of(1, 2), List.of(3, 4)));
		listModel.persistList("numbers", () -> List.of(), new TypeInfo<List<Integer>>(){}, listModel::getValue, listModel::setValue);
		var models = Map.<String, SaveLoadModel>of("listModel", listModel);
		var expected =
		"""
		{
			"listModel": {
				"numbers": [
					[
						1,
						2
					],
					[
						3,
						4
					]
				]
			}
		}
		""";

		dataStore.save(models, output);
		var actual = output.toString();

		assertStringsEqualNewLineInsensitive(expected, actual);
	}

	private static void assertStringsEqualNewLineInsensitive(String expected, String actual) {
		expected = expected.replace("\r\n", "\n");
		actual = actual.replace("\r\n", "\n");

		assertEquals(expected, actual);
	}

	private static class TestModel<T> extends SaveLoadModel {

		T value;

		TestModel(T initialValue) {
			value = initialValue;
		}

		T getValue() {
			return value;
		}

		void setValue(T newValue) {
			value = newValue;
		}

	}

}
