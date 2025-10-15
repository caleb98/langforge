package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonDataStore implements SaveLoadDataStore<JsonGenerator, JsonNode> {

	private ObjectMapper mapper;
	private JsonFactory factory;
	private PrettyPrinter prettyPrinter;

	public JsonDataStore() {
		mapper = new ObjectMapper();
		factory = mapper.getFactory();
		prettyPrinter = new CustomPrettyPrinter();
	}

	@Override
	public void save(Map<String, SaveLoadModel> models, OutputStream output) throws IOException {
		var json = factory.createGenerator(output, JsonEncoding.UTF8);
		json.setPrettyPrinter(prettyPrinter);
		json.writeStartObject();

		for (var modelEntry : models.entrySet()) {
			var modelName = modelEntry.getKey();
			var model = modelEntry.getValue();

			json.writeFieldName(modelName);
			saveModel(model, json);
		}

		json.writeEndObject();
		json.writeRaw(System.lineSeparator());
		json.close();
	}

	@Override
	public void load(Map<String, SaveLoadModel> models, InputStream input) throws IOException {
		var json = factory.createParser(input);

		var token = json.nextToken();
		if (token != JsonToken.START_OBJECT) {
			// TODO: log warning
			return;
		}

		String modelName;
		while ((modelName = json.nextFieldName()) != null) {
			var model = models.get(modelName);
			if (model == null) {
				// TODO: print warning
				continue;
			}

			var nextToken = json.nextToken();
			if (nextToken != JsonToken.START_OBJECT) {
				throw new JsonMappingException(json, "Expected JSON Object value for persistent model name.");
			}

			ObjectNode modelNode = json.readValueAsTree();
			loadModel(modelNode, model);
		}

		json.close();
	}

	private void saveModel(SaveLoadModel model, JsonGenerator json) throws IOException {
		json.writeStartObject();
		for (var entry : model.getValues().entrySet()) {
			var valueName = entry.getKey();
			var value = entry.getValue();

			json.writeFieldName(valueName);
			value.save(this, json);
		}
		json.writeEndObject();
	}

	private <T> void loadModel(ObjectNode modelNode, SaveLoadModel model) throws IOException {
		for (var entry : modelNode.properties()) {
			var valueName = entry.getKey();
			var valueJson = entry.getValue();

			SaveLoadValue<?> property = model.getValues().get(valueName);
			if (property == null) {
				// TODO: log warning
				continue;
			}

			property.load(this, valueJson);
		}
	}

	@Override
	public void saveBoolean(SaveLoadBoolean value, JsonGenerator json) throws IOException {
		json.writeBoolean(value.getValue());
	}

	@Override
	public void saveByte(SaveLoadByte value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveCharacter(SaveLoadCharacter value, JsonGenerator json) throws IOException {
		json.writeString(String.valueOf(value.getValue()));
	}

	@Override
	public void saveDouble(SaveLoadDouble value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveFloat(SaveLoadFloat value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveInteger(SaveLoadInteger value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveList(SaveLoadList<?> saveLoadList, JsonGenerator json) throws IOException {
		json.writeStartArray();
		for (SaveLoadValue<?> value : saveLoadList.getValue()) {
			value.save(this, json);
		}
		json.writeEndArray();
	}

	@Override
	public void saveLong(SaveLoadLong value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveShort(SaveLoadShort value, JsonGenerator json) throws IOException {
		json.writeNumber(value.getValue());
	}

	@Override
	public void saveString(SaveLoadString value, JsonGenerator json) throws IOException {
		json.writeString(value.getValue());
	}

	@Override
	public void loadBoolean(SaveLoadBoolean value, JsonNode json) throws IOException {
		value.setValue(json.asBoolean());
	}

	@Override
	public void loadByte(SaveLoadByte value, JsonNode json) throws IOException {
		value.setValue((byte) json.asLong());
	}

	@Override
	public void loadCharacter(SaveLoadCharacter value, JsonNode json) throws IOException {
		String stringValue = json.asText();
		if (stringValue.length() != 1) {
			throw new IllegalArgumentException("Character value is not a single character: " + stringValue);
		}
		value.setValue(stringValue.charAt(0));
	}

	@Override
	public void loadDouble(SaveLoadDouble value, JsonNode json) throws IOException {
		value.setValue(json.asDouble());
	}

	@Override
	public void loadFloat(SaveLoadFloat value, JsonNode json) throws IOException {
		value.setValue((float) json.asDouble());
	}

	@Override
	public void loadInteger(SaveLoadInteger value, JsonNode json) throws IOException {
		value.setValue(json.asInt());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void loadList(SaveLoadList<?> saveLoadList, JsonNode json) throws IOException {
		if (json instanceof ArrayNode array) {
			List<SaveLoadValue<?>> elements = new ArrayList<>();
			for (var element : array) {
				var value = saveLoadList.createElement();
				value.load(this, element);
				elements.add(value);
			}
			// Cast to raw list is necessary here to make javac happy. Otherwise
			// we wind up with errors about mismatching captures.
			saveLoadList.setValue((List)elements);
		}
	}

	@Override
	public void loadLong(SaveLoadLong value, JsonNode json) throws IOException {
		value.setValue(json.asLong());
	}

	@Override
	public void loadShort(SaveLoadShort value, JsonNode json) throws IOException {
		value.setValue(json.shortValue());
	}

	@Override
	public void loadString(SaveLoadString value, JsonNode json) throws IOException {
		value.setValue(json.asText());
	}

	private static class CustomPrettyPrinter implements PrettyPrinter {

		private int nesting = 0;

		@Override
		public void writeRootValueSeparator(JsonGenerator gen) throws IOException {
			gen.writeRaw(System.lineSeparator());
		}

		@Override
		public void writeStartObject(JsonGenerator gen) throws IOException {
			gen.writeRaw('{');
			nesting++;
		}

		@Override
		public void writeEndObject(JsonGenerator gen, int nrOfEntries) throws IOException {
			nesting--;
			if (nrOfEntries > 0) {
				indent(gen);
			}
			gen.writeRaw('}');
		}

		@Override
		public void writeObjectEntrySeparator(JsonGenerator gen) throws IOException {
			gen.writeRaw(',');
			gen.writeRaw(System.lineSeparator());
		}

		@Override
		public void writeObjectFieldValueSeparator(JsonGenerator gen) throws IOException {
			gen.writeRaw(": ");
		}

		@Override
		public void writeStartArray(JsonGenerator gen) throws IOException {
			gen.writeRaw('[');
			nesting++;
		}

		@Override
		public void writeEndArray(JsonGenerator gen, int nrOfValues) throws IOException {
			nesting--;
			if (nrOfValues > 0) {
				indent(gen);
			}
			gen.writeRaw(']');
		}

		@Override
		public void writeArrayValueSeparator(JsonGenerator gen) throws IOException {
			gen.writeRaw(',');
			indent(gen);
		}

		@Override
		public void beforeArrayValues(JsonGenerator gen) throws IOException {
			indent(gen);
		}

		@Override
		public void beforeObjectEntries(JsonGenerator gen) throws IOException {
			indent(gen);
		}

		private void indent(JsonGenerator gen) throws IOException {
			gen.writeRaw(System.lineSeparator());
			for (int i = 0; i < nesting; i++) {
				gen.writeRaw('\t');
			}
		}

	}

}
