package net.calebscode.langforge.app.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SaveLoadObject implements SaveLoadValue, Map<String, SaveLoadValue> {

	private final Map<String, SaveLoadValue> fields;

	public SaveLoadObject() {
		this(new HashMap<>());
	}

	public SaveLoadObject(Map<String, SaveLoadValue> fields) {
		this.fields = fields;
	}

	public SaveLoadObject getObject(String name) {
		return fields.get(name).asObject();
	}

	public SaveLoadInteger getInteger(String name) {
		return fields.get(name).asInteger();
	}

	public SaveLoadDouble getFloat(String name) {
		return fields.get(name).asDouble();
	}

	public SaveLoadString getString(String name) {
		return fields.get(name).asString();
	}

	public SaveLoadList getList(String name) {
		return fields.get(name).asList();
	}

	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public SaveLoadObject asObject() {
		return this;
	}

	@Override
	public int size() {
		return fields.size();
	}

	@Override
	public boolean isEmpty() {
		return fields.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return fields.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return fields.containsValue(value);
	}

	@Override
	public SaveLoadValue get(Object key) {
		return fields.get(key);
	}

	@Override
	public SaveLoadValue put(String key, SaveLoadValue value) {
		return fields.put(key, value);
	}

	@Override
	public SaveLoadValue remove(Object key) {
		return fields.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends SaveLoadValue> m) {
		fields.putAll(m);
	}

	@Override
	public void clear() {
		fields.clear();
	}

	@Override
	public Set<String> keySet() {
		return fields.keySet();
	}

	@Override
	public Collection<SaveLoadValue> values() {
		return fields.values();
	}

	@Override
	public Set<Entry<String, SaveLoadValue>> entrySet() {
		return fields.entrySet();
	}

}