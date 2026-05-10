package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.NumericFPNode;
import tools.jackson.databind.node.NumericIntNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.StringNode;

public class JsonBackend implements PersistenceBackend {

	private ObjectMapper mapper;

	public JsonBackend() {
		mapper = JsonMapper.builder()
			.enable(SerializationFeature.INDENT_OUTPUT)
			.build();
	}

	@Override
	public void save(OutputStream output, SaveLoadObject values) throws IOException {
		ObjectNode root = mapper.createObjectNode();

		for (var entry : values.entrySet()) {
			var valueName = entry.getKey();
			var value = entry.getValue();

			writeToObject(root, valueName, value);
		}

		mapper.writeValue(output, root);
	}

	@Override
	public SaveLoadObject load(InputStream input) {
		var maybeRoot = mapper.readTree(input);

		if (!maybeRoot.isObject()) {
			throw new RuntimeException("Save data JSON was not a JSON object.");
		}

		var root = new SaveLoadObject();

		for (var property : maybeRoot.asObject().properties()) {
			var propertyName = property.getKey();
			var propertyValue = property.getValue();

			readToObject(root, propertyName, propertyValue);
		}

		return root;
	}

	private void writeToObject(ObjectNode target, String name, SaveLoadValue value) {
		switch (value) {
			case SaveLoadObject object -> {
				var node = target.putObject(name);
				for (var entry : object.entrySet()) {
					writeToObject(node, entry.getKey(), entry.getValue());
				}
			}

			case SaveLoadInteger(int intValue) -> {
				target.put(name, intValue);
			}

			case SaveLoadDouble(double doubleValue) -> {
				target.put(name, doubleValue);
			}

			case SaveLoadString(String stringValue) -> {
				target.put(name, stringValue);
			}

			case SaveLoadList list -> {
				var node = target.putArray(name);
				for (var element : list) {
					writeToArray(node, element);
				}
			}

			case null -> {
				target.putNull(name);
			}
		}
	}

	private void writeToArray(ArrayNode target, SaveLoadValue value) {
		switch (value) {
			case SaveLoadObject object -> {
				var node = target.objectNode();
				for (var entry : object.entrySet()) {
					writeToObject(node, entry.getKey(), entry.getValue());
				}
				target.add(node);
			}

			case SaveLoadInteger(int intValue) -> {
				target.add(intValue);
			}

			case SaveLoadDouble(double doubleValue) -> {
				target.add(doubleValue);
			}

			case SaveLoadString(String stringValue) -> {
				target.add(stringValue);
			}

			case SaveLoadList list -> {
				var node = target.arrayNode();
				for (var element : list) {
					writeToArray(node, element);
				}
				target.add(node);
			}

			case null -> {
				target.addNull();
			}
		}
	}

	private void readToObject(SaveLoadObject target, String name, JsonNode node) {
		switch (node) {
			case NumericFPNode fpNode -> {
				target.put(name, new SaveLoadDouble(fpNode.asDouble()));
			}

			case NumericIntNode intNode -> {
				target.put(name, new SaveLoadInteger(intNode.asInt()));
			}

			case StringNode stringNode -> {
				target.put(name, new SaveLoadString(stringNode.asString()));
			}

			case ObjectNode objectNode -> {
				var object = new SaveLoadObject();
				for (var entry : objectNode.properties()) {
					var propertyName = entry.getKey();
					var propertyValue = entry.getValue();
					readToObject(object, propertyName, propertyValue);
				}
				target.put(name, object);
			}

			case ArrayNode arrayNode -> {
				var list = new SaveLoadList();
				for (var element : arrayNode) {
					readToList(list, element);
				}
				target.put(name, list);
			}

			case NullNode _ -> {
				target.putNull(name);
			}

			default -> {
				var msg = String.format(
					"Unsupported JSON node type: %s. This is a bug in Langforge, please report it!",
					node.getClass().getName()
				);
				throw new RuntimeException(msg);
			}
		}
	}

	private void readToList(SaveLoadList target, JsonNode node) {
		switch (node) {
			case NumericFPNode fpNode -> {
				target.add(new SaveLoadDouble(fpNode.asDouble()));
			}

			case NumericIntNode intNode -> {
				target.add(new SaveLoadInteger(intNode.asInt()));
			}

			case StringNode stringNode -> {
				target.add(new SaveLoadString(stringNode.asString()));
			}

			case ObjectNode objectNode -> {
				var object = new SaveLoadObject();
				for (var entry : objectNode.properties()) {
					var propertyName = entry.getKey();
					var propertyValue = entry.getValue();
					readToObject(object, propertyName, propertyValue);
				}
				target.add(object);
			}

			case ArrayNode arrayNode -> {
				var list = new SaveLoadList();
				for (var element : arrayNode) {
					readToList(list, element);
				}
				target.add(list);
			}

			case NullNode _ -> {
				target.add(null);
			}

			default -> {
				var msg = String.format(
					"Unsupported JSON node type: %s. This is a bug in Langforge, please report it!",
					node.getClass().getName()
				);
				throw new RuntimeException(msg);
			}
		}
	}

}
