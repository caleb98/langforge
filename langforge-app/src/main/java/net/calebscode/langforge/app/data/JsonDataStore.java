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

import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadList;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadValue.SaveLoadString;


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
				var modelObject = new JsonObject();
				var modelName = entry.getKey();
				var model = entry.getValue();
				
				writeModel(modelObject, model);
				
				storeObject.add(modelName, modelObject);
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
	
	private void writeValue(JsonObject output, String name, SaveLoadValue<?> value) {
		switch (value) {
			case SaveLoadString(var getter, _) -> output.addProperty(name, getter.get());
			
			case SaveLoadObject<?> saveLoadObject -> {
				var object = saveLoadObject.getter().get();
				
				switch (object) {
					
					case String string -> output.addProperty(name, string);
					default -> throw new IllegalArgumentException("Unexpected value: " + object);
					
				}
			}
	
			case SaveLoadList<?> saveLoadList -> {
				var list = saveLoadList.getValue();
				var type = saveLoadList.elementType();
				
				var array = new JsonArray(list.size());
				for (var element : list) {
					if (element instanceof SaveLoadModel model) {
						var obj = new JsonObject();
						writeModel(obj, model);
						array.add(obj);
					}
					else {
						array.add(gson.toJsonTree(element, type));
					}
					
				}
				output.add(name, array);
			}
	
			case null -> throw new NullPointerException("SaveLoadModel value may not be null.");
		}
	}
	
	private void readValue(JsonElement input, SaveLoadValue<?> value) {
		switch (value) {
			case SaveLoadString(_, var setter) -> setter.accept(input.getAsString());
			
			case SaveLoadObject<?> saveLoadObject -> {
				
			}
	
			case SaveLoadList<?> saveLoadList -> {
				var array = input.getAsJsonArray();
				readList(array, saveLoadList);
			}
	
			case null -> {}
		}
	}
	
	private <E> void readList(JsonArray array, SaveLoadList<E> list) {
		var type = list.elementType();
		var result = list.newTypedList();
		
		var isListOfModels = type instanceof Class<?> clazz && SaveLoadModel.class.isAssignableFrom(clazz);
		if (isListOfModels) {
			for (var element : array) {
				if (!(element instanceof JsonObject elementObject)) {
					throw new RuntimeException("Expected array of objects");
				}
				
				var newElement = list.elementFactory().get();
				if (!(newElement instanceof SaveLoadModel model)) {
					throw new RuntimeException("SaveLoadList element factory produced object not of type SaveLoadModel");
				}
				
				readModel(elementObject, model);
				result.add(newElement);
			}
		}
		else {
			for (var element : array) {
				result.add(gson.fromJson(element, type));
			}
		}
		
		list.setValue(result);
	}
	
	private void writeModel(JsonObject output, SaveLoadModel model) {
		for (var entry : model.getValues().entrySet()) {
			var key = entry.getKey();
			var value = entry.getValue();
			writeValue(output, key, value);
		}
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

}
