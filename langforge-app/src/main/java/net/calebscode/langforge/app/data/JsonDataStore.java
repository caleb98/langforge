package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadListValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadMapValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadModelValue;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadObjectValue;

public class JsonDataStore implements DataStore {

	private final Gson gson;

	public JsonDataStore() {
		gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
	}

	@Override
	public void save(OutputStream output, Map<String, SaveLoadModel> models) throws IOException {
		try (
			var writer = gson.newJsonWriter(new OutputStreamWriter(output))
		) {
			var storeObject = new JsonObject();
			
			for (var entry : models.entrySet()) {
				var modelName = entry.getKey();
				var model = entry.getValue();
				writeModel(storeObject, modelName, model);
			}
			
			writer.setIndent("\t");
			gson.toJson(storeObject, writer);
			writer.flush();
			output.write("\n".getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void load(InputStream input, Map<String, SaveLoadModel> models) throws IOException {
		try (
			var reader = gson.newJsonReader(new InputStreamReader(input))
		) {
			JsonObject storeObject = gson.fromJson(reader, JsonObject.class);
			
			for (var modelName : storeObject.keySet()) {
				if (!models.containsKey(modelName)) {
					// TODO: error, the loaded json has an entry that does not map to a model in the code
					continue;
				}
				
				var modelObject = storeObject.get(modelName);
				if (!modelObject.isJsonObject()) {
					// TODO: error, the value for the model entry was not an object
					continue;
				}
				
				var model = models.get(modelName);
				readModel(modelObject.getAsJsonObject(), model);
			}
			
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeValue(JsonObject output, String name, SaveLoadValue value) {
		switch (value) {
			case SaveLoadObjectValue<?> object -> writeObject(output, name, object);
			case SaveLoadModelValue model -> writeModel(output, name, model.getValue());
			case SaveLoadListValue<?> list -> writeList(output, name, list);
			case SaveLoadMapValue<?, ?> map -> writeMap(output, name, map);
		}
	}

	private void writeObject(JsonObject output, String name, SaveLoadObjectValue<?> object) {
		var value = object.getValue();
		var type = object.type();
		output.add(name, gson.toJsonTree(value, type));
	}
	
	private void writeModel(JsonObject output, String name, SaveLoadModel model) {
		var object = createJsonObjectForModel(model);
		output.add(name, object);
	}
	
	private void writeList(JsonObject output, String name, SaveLoadListValue<?> saveLoadList) {
		var list = saveLoadList.getValue();
		var type = saveLoadList.elementType();
		
		var array = new JsonArray(list.size());
		for (var element : list) {
			if (element instanceof SaveLoadModel model) {
				array.add(createJsonObjectForModel(model));
			}
			else {
				array.add(gson.toJsonTree(element, type));
			}
			
		}
		output.add(name, array);
	}
	
	private <K, V> void writeMap(JsonObject output, String name, SaveLoadMapValue<K, V> saveLoadMap) {
		var map = saveLoadMap.getValue();
		var keyType = saveLoadMap.keyType();
		var valueType = saveLoadMap.valueType();

		var array = new JsonArray(map.size());
		for (K key : map.keySet()) {
			V value = map.get(key);
			
			var keyElement = (key instanceof SaveLoadModel keyModel) ?
				createJsonObjectForModel(keyModel) :
				gson.toJsonTree(key, keyType);
			
			var valueElement = (value instanceof SaveLoadModel valueModel) ?
				createJsonObjectForModel(valueModel) :
				gson.toJsonTree(value, valueType);
			
			var entryObject = new JsonObject();
			entryObject.add("key", keyElement);
			entryObject.add("value", valueElement);
			array.add(entryObject);
		}
		
		output.add(name, array);
	}
	
	private void readValue(JsonElement input, SaveLoadValue value) {
		switch (value) {
			case SaveLoadObjectValue<?> object -> readObject(input, object);
			case SaveLoadModelValue model -> readModel(input.getAsJsonObject(), model.getValue());
			case SaveLoadListValue<?> list -> readList(input.getAsJsonArray(), list);
			case SaveLoadMapValue<?, ?> map -> readMap(input.getAsJsonArray(), map);
		}
	}

	private void readObject(JsonElement input, SaveLoadObjectValue<?> object) {
		var type = object.type();
		var loaded = gson.fromJson(input, type);
		object.setValueUnchecked(loaded);
	}
	
	private void readModel(JsonObject input, SaveLoadModel model) {
		for (var entry : input.entrySet()) {
			var key = entry.getKey();
			var value = entry.getValue();

			var modelProperty = model.getValues().get(key);
			if (modelProperty == null) {
				// TODO: log or throw
				continue;
			}

			readValue(value, modelProperty);
		}
	}
	
	private <T> void readList(JsonArray input, SaveLoadListValue<T> list) {
		var type = list.elementType();
		list.getValue().clear();
		
		var isListOfModels = SaveLoadModel.isTypeSaveLoadModel(type);
		if (isListOfModels) {
			for (var element : input) {
				if (!(element instanceof JsonObject elementObject)) {
					throw new RuntimeException("Expected array of objects");
				}
				
				T newElement = list.elementFactory().get();
				if (!(newElement instanceof SaveLoadModel model)) {
					throw new RuntimeException("SaveLoadListValue element factory produced object not of type SaveLoadModel");
				}
				
				readModel(elementObject, model);
				list.getValue().add(newElement);
			}
		}
		else {
			for (var element : input) {
				list.getValue().add(gson.fromJson(element, type));
			}
		}
	}
	
	private <K, V> void readMap(JsonArray input, SaveLoadMapValue<K, V> map) {
		var keyType = map.keyType();
		var valueType = map.valueType();
		map.getValue().clear();
		
		var areKeysModels = SaveLoadModel.isTypeSaveLoadModel(keyType);
		var areValuesModels = SaveLoadModel.isTypeSaveLoadModel(valueType);
		
		for (var entry : input) {
			var entryObject = entry.getAsJsonObject();
			var keyElement = entryObject.get("key");
			var valueElement = entryObject.get("value");
			
			K key = null;
			V value = null;
			
			if (areKeysModels) {
				if (!(keyElement instanceof JsonObject keyObject)) {
					throw new RuntimeException("Expected map key to be an object.");
				}
				
				key = map.keyFactory().get();
				if (!(key instanceof SaveLoadModel keyModel)) {
					throw new RuntimeException("SaveLoadMapValue key factory produced object not of type SaveLoadModel");
				}
				
				readModel(keyObject, keyModel);
			}
			else {
				key = gson.fromJson(keyElement, keyType);
			}
			
			if (areValuesModels) {
				if (!(valueElement instanceof JsonObject valueObject)) {
					throw new RuntimeException("Expected map value to be an object.");
				}
				
				value = map.valueFactory().get();
				if (!(value instanceof SaveLoadModel valueModel)) {
					throw new RuntimeException("SaveLoadMapValue value factory produced object not of type SaveLoadModel");
				}
				
				readModel(valueObject, valueModel);
			}
			else {
				value = gson.fromJson(valueElement, valueType);
			}
			
			if (key == null || value == null) {
				throw new RuntimeException("Invalid entry when loading SaveLoadMapValue: null key or value.");
			}
			
			map.getValue().put(key, value);
		}
	}

	private JsonObject createJsonObjectForModel(SaveLoadModel model) {
		var object = new JsonObject();
		for (var entry : model.getValues().entrySet()) {
			var key = entry.getKey();
			var value = entry.getValue();
			writeValue(object, key, value);
		}
		return object;
	}
	
}
