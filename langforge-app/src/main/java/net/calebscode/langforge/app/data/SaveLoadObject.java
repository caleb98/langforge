package net.calebscode.langforge.app.data;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public non-sealed class SaveLoadObject implements SaveLoadValue, Map<String, SaveLoadValue> {

	private final Map<String, SaveLoadValue> fields;

	public SaveLoadObject() {
		fields = new HashMap<>();
	}

	public SaveLoadObject(Map<String, ? extends SaveLoadValue> fields) {
		this.fields = new HashMap<>(fields);
	}

	public <T> SaveLoadObject(Map<String, T> fields, Function<T, SaveLoadValue> mapper) {
		this.fields = new HashMap<>(
			fields
				.entrySet()
				.stream()
				.collect(toMap(
					Entry::getKey,
					e -> mapper.apply(e.getValue())
				))
		);
	}

	public final SaveLoadObject getObject(String name) {
		return fields.get(name).asObject();
	}

	public final SaveLoadInteger getInteger(String name) {
		return fields.get(name).asInteger();
	}

	public final SaveLoadDouble getFloat(String name) {
		return fields.get(name).asDouble();
	}

	public final SaveLoadString getString(String name) {
		return fields.get(name).asString();
	}

	public final SaveLoadList getList(String name) {
		return fields.get(name).asList();
	}

	public final SaveLoadValue putNull(String key) {
		return ((Map<String, SaveLoadValue>)this).put(key, null);
	}

	public final SaveLoadValue put(String key, String value) {
		return put(key, new SaveLoadString(value));
	}

	public final SaveLoadValue put(String key, int value) {
		return put(key, new SaveLoadInteger(value));
	}

	public final SaveLoadValue put(String key, float value) {
		return put(key, new SaveLoadDouble(value));
	}

	public final SaveLoadValue put(String key, double value) {
		return put(key, new SaveLoadDouble(value));
	}

	public final <T> SaveLoadValue put(String key, List<T> list, Function<T, SaveLoadValue> converter) {
		return put(key, new SaveLoadList(list, converter));
	}

	@Override
	public final boolean isObject() {
		return true;
	}

	@Override
	public final SaveLoadObject asObject() {
		return this;
	}

	@Override
	public final int size() {
		return fields.size();
	}

	@Override
	public final boolean isEmpty() {
		return fields.isEmpty();
	}

	@Override
	public final boolean containsKey(Object key) {
		return fields.containsKey(key);
	}

	@Override
	public final boolean containsValue(Object value) {
		return fields.containsValue(value);
	}

	@Override
	public final SaveLoadValue get(Object key) {
		return fields.get(key);
	}

	@Override
	public final SaveLoadValue put(String key, SaveLoadValue value) {
		return fields.put(key, value);
	}

	@Override
	public final SaveLoadValue remove(Object key) {
		return fields.remove(key);
	}

	@Override
	public final void putAll(Map<? extends String, ? extends SaveLoadValue> m) {
		fields.putAll(m);
	}

	@Override
	public final void clear() {
		fields.clear();
	}

	@Override
	public final Set<String> keySet() {
		return fields.keySet();
	}

	@Override
	public final Collection<SaveLoadValue> values() {
		return fields.values();
	}

	@Override
	public final Set<Entry<String, SaveLoadValue>> entrySet() {
		return fields.entrySet();
	}

}