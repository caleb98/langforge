package net.calebscode.langforge.app.data;

import static net.calebscode.langforge.app.data.SaveLoadable.isTypeSaveLoadable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadListProperty;
import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadMapProperty;
import net.calebscode.langforge.app.data.SaveLoadProperty.SaveLoadObjectProperty;

public class JsonDataStore implements DataStore {

	private final Gson gson;

	public JsonDataStore() {
		gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
	}

	@Override
	public void save(OutputStream output, Map<String, SaveLoadObject<?>> objects) throws IOException {
		try (
			var writer = gson.newJsonWriter(new OutputStreamWriter(output))
		) {
			var json = new JsonObject();
			
			for (var entry : objects.entrySet()) {
				var name = entry.getKey();
				var object = entry.getValue();
				writeObject(json, name, object);
			}
			
			writer.setIndent("\t");
			gson.toJson(json, writer);
			writer.flush();
			output.write("\n".getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void load(InputStream input, Map<String, SaveLoadObject<?>> objects) throws IOException {
		try (
			var reader = gson.newJsonReader(new InputStreamReader(input))
		) {
			JsonObject store = gson.fromJson(reader, JsonObject.class);
			
			for (var objectName : store.keySet()) {
				if (!objects.containsKey(objectName)) {
					// TODO: error, the loaded json has an entry that does not map to a model in the code
					continue;
				}
				
				var modelObject = store.get(objectName);
				if (!modelObject.isJsonObject()) {
					// TODO: error, the value for the model entry was not an object
					continue;
				}
				
				var model = objects.get(objectName);
				readObject(modelObject.getAsJsonObject(), model);
			}
			
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private <T> void writeObject(JsonObject target, String name, SaveLoadObject<T> object) {
		var json = createJsonForSaveLoadObject(object);
		target.add(name, json);
	}
	
	private <T> void writeProperty(JsonObject target, String name, SaveLoadProperty<T> property, T context) {
		switch (property) {
			case SaveLoadObjectProperty<T, ?> object -> writeObjectProperty(target, name, object, context);
			case SaveLoadListProperty<T, ?> list -> writeListProperty(target, name, list, context);
			case SaveLoadMapProperty<T, ?, ?> map -> writeMapProperty(target, name, map, context);
		}
	}
	
	private <T, S> void writeObjectProperty(JsonObject target, String name, SaveLoadObjectProperty<T, S> property, T context) {
		var value = property.getValue(context);
		var type = property.type();
		
		if (value instanceof SaveLoadable<?> saveLoadable) {
			@SuppressWarnings("unchecked")
			var schema = (SaveLoadSchema<S>) saveLoadable.getSchema();
			var object = new SaveLoadObject<>(value, schema);
			writeObject(target, name, object);
		}
		else {
			var json = gson.toJsonTree(value, type);
			target.add(name, json);
		}
	}
	
	
	private <T, E> void writeListProperty(JsonObject target, String name, SaveLoadListProperty<T, E> property, T context) {
		var list = property.getValue(context);
		var type = property.elementType();
		
		var array = new JsonArray(list.size());
		for (var element : list) {
			array.add(createJsonForObject(element, type));
		}
		
		target.add(name, array);
	}
	
	private <T, K, V> void writeMapProperty(JsonObject target, String name, SaveLoadMapProperty<T, K, V> property, T context) {
		var map = property.getValue(context);
		var keyType = property.keyType();
		var valueType = property.valueType();
		
		var array = new JsonArray(map.size());
		for (var entry : map.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			
			JsonElement keyElement = createJsonForObject(key, keyType);
			JsonElement valueElement = createJsonForObject(value, valueType);
			
			var entryJson = new JsonObject();
			entryJson.add("key", keyElement);
			entryJson.add("value", valueElement);
			array.add(entryJson);
		}
		
		target.add(name, array);
	}

	private <T> JsonElement createJsonForObject(T value, Type type) {
		JsonElement element;
		
		if (value instanceof SaveLoadable<?> saveLoadable) {
			@SuppressWarnings("unchecked")
			var schema = (SaveLoadSchema<T>) saveLoadable.getSchema();
			var object = new SaveLoadObject<>(value, schema);
			element = createJsonForSaveLoadObject(object);
		}
		else {
			element = gson.toJsonTree(value, type);
		}
		
		return element;
	}

	private <T> JsonObject createJsonForSaveLoadObject(SaveLoadObject<T> object) {
		var context = object.context();
		var schema = object.schema();
		var json = new JsonObject();
		
		for (var entry : schema.getProperties().entrySet()) {
			var propertyName = entry.getKey();
			var property = entry.getValue();
			writeProperty(json, propertyName, property, context);
		}
		
		return json;
	}
	
	private <T> void readObject(JsonObject source, SaveLoadObject<T> object) {
		var context = object.context();
		var schema = object.schema();
		
		for (var entry : source.entrySet()) {
			var key = entry.getKey();
			var value = entry.getValue();
			
			var property = schema.getProperties().get(key);
			if (property == null) {
				// TODO: log error indicating missing property in schema
				continue;
			}
			
			readProperty(value, property, context);
		}
	}
	
	private <T> void readProperty(JsonElement source, SaveLoadProperty<T> property, T context) {
		switch (property) {
			case SaveLoadObjectProperty<T, ?> object -> readObjectProperty(source, object, context);
			case SaveLoadListProperty<T, ?> list -> readListProperty(source, list, context);
			case SaveLoadMapProperty<T, ?, ?> map -> readMapProperty(source, map, context);
		}
	}

	private <T, S> void readObjectProperty(JsonElement source, SaveLoadObjectProperty<T, S> property, T context) {
		var type = property.type();
		
		if (isTypeSaveLoadable(type)) {
			var value = property.getValue(context);
			@SuppressWarnings("unchecked")
			var saveLoadable = (SaveLoadable<S>) value;
			var schema = saveLoadable.getSchema();
			var object = new SaveLoadObject<>(value, schema);
			
			if (!source.isJsonObject()) {
				// TODO: log error
				return;
			}
			
			readObject(source.getAsJsonObject(), object);
		}
		else {
			S loaded = gson.fromJson(source, type);
			property.setValue(context, loaded);
		}
	}

	private <T, E> void readListProperty(JsonElement source, SaveLoadListProperty<T, E> property, T context) {
		if (!source.isJsonArray()) {
			// TODO: log error
			return;
		}
		
		var array = source.getAsJsonArray();
		var list = property.getValue(context);
		var type = property.elementType();
		
		list.clear();
		
		if (isTypeSaveLoadable(type)) {
			for (var element : array) {
				var value = property.elementFactory().apply(context);
				@SuppressWarnings("unchecked")
				var saveLoadable = (SaveLoadable<E>) value;
				var schema = saveLoadable.getSchema();
				var object = new SaveLoadObject<>(value, schema);
				
				if (!element.isJsonObject()) {
					// TODO: log error
					continue;
				}
				
				readObject(element.getAsJsonObject(), object);
				list.add(value);
			}
		}
		else {
			for (var element : array) {
				E value = gson.fromJson(element, type);
				list.add(value);
			}
		}
	}

	private <T, K, V> void readMapProperty(JsonElement source, SaveLoadMapProperty<T, K, V> property, T context) {
		if (!source.isJsonArray()) {
			// TODO: log error
			return;
		}
		
		var array = source.getAsJsonArray();
		
		var keyType = property.keyType();
		var valueType = property.valueType();
		var map = property.getValue(context);
		
		var areKeysSaveLoadable = isTypeSaveLoadable(keyType);
		var areValuesSaveLoadable = isTypeSaveLoadable(valueType);
		
		map.clear();
		
		for (var entry : array) {
			if (!entry.isJsonObject()) {
				// TODO: log error
				continue;
			}
			
			var entryObject = entry.getAsJsonObject();
			var keyElement = entryObject.get("key");
			var valueElement = entryObject.get("value");
			
			K key = null;
			V value = null;
			
			if (areKeysSaveLoadable) {
				key = property.keyFactory().apply(context);
				@SuppressWarnings("unchecked")
				var saveLoadable = (SaveLoadable<K>) key;
				var schema = saveLoadable.getSchema();
				var object = new SaveLoadObject<>(key, schema);
				
				if (!keyElement.isJsonObject()) {
					// TODO: log error
					continue;
				}
				
				readObject(keyElement.getAsJsonObject(), object);
			}
			else {
				key = gson.fromJson(keyElement, keyType);
			}
			
			if (key == null) {
				// TODO: log error
				continue;
			}
			
			if (areValuesSaveLoadable) {
				value = property.valueFactory().apply(context);
				@SuppressWarnings("unchecked")
				var saveLoadable = (SaveLoadable<V>) value;
				var schema = saveLoadable.getSchema();
				var object = new SaveLoadObject<>(value, schema);
				
				if (!valueElement.isJsonObject()) {
					// TODO: log error
					continue;
				}
				
				readObject(valueElement.getAsJsonObject(), object);
			}
			else {
				value = gson.fromJson(valueElement, valueType);
			}
			
			if (value == null) {
				// TODO: log error
				continue;
			}
			
			if (map.containsKey(key)) {
				// TODO: log warning, decide whether or not to overwrite
			}
			
			map.put(key, value);
		}
	}
	
}
