package net.calebscode.langforge.app.data;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.calebscode.langforge.app.functional.ByteConsumer;
import net.calebscode.langforge.app.functional.ByteSupplier;
import net.calebscode.langforge.app.functional.CharacterConsumer;
import net.calebscode.langforge.app.functional.CharacterSupplier;
import net.calebscode.langforge.app.functional.DoubleConsumer;
import net.calebscode.langforge.app.functional.DoubleSupplier;
import net.calebscode.langforge.app.functional.FloatConsumer;
import net.calebscode.langforge.app.functional.FloatSupplier;
import net.calebscode.langforge.app.functional.IntegerConsumer;
import net.calebscode.langforge.app.functional.IntegerSupplier;
import net.calebscode.langforge.app.functional.LongConsumer;
import net.calebscode.langforge.app.functional.LongSupplier;
import net.calebscode.langforge.app.functional.SaveLoadModelSupplier;
import net.calebscode.langforge.app.functional.ShortConsumer;
import net.calebscode.langforge.app.functional.ShortSupplier;
import net.calebscode.langforge.app.functional.StringConsumer;
import net.calebscode.langforge.app.functional.StringSupplier;

public class DynamicSaveLoadModel extends SaveLoadModel {

	@Override
	public void persist(String valueName, ByteSupplier getter, ByteConsumer setter) {
		super.persist(valueName, getter, setter);
	}
	
	@Override
	public void persist(String valueName, CharacterSupplier getter, CharacterConsumer setter) {
		super.persist(valueName, getter, setter);
	}
	
	@Override
	public void persist(String valueName, DoubleSupplier getter, DoubleConsumer setter) {
		super.persist(valueName, getter, setter);
	}
	
	@Override
	public void persist(String valueName, FloatSupplier getter, FloatConsumer setter) {
		super.persist(valueName, getter, setter);
	}
	
	@Override
	public void persist(String valueName, IntegerSupplier getter, IntegerConsumer setter) {
		super.persist(valueName, getter, setter);
	}

	@Override
	public <T> void persist(String valueName, RuntimeType<T> runtimeType, Supplier<T> getter, Consumer<T> setter) {
		super.persist(valueName, runtimeType, getter, setter);
	}

	@Override
	public void persist(String valueName, StringSupplier getter, StringConsumer setter) {
		super.persist(valueName, getter, setter);
	}

	@Override
	public void persist(String valueName, ShortSupplier getter, ShortConsumer setter) {
		super.persist(valueName, getter, setter);
	}

	@Override
	public void persist(String valueName, LongSupplier getter, LongConsumer setter) {
		super.persist(valueName, getter, setter);
	}

	@Override
	public void persist(String valueName, SaveLoadModelSupplier getter) {
		super.persist(valueName, getter);
	}

	@Override
	public <T> void persistList(String listName, RuntimeType<T> type, Supplier<List<T>> getter) {
		super.persistList(listName, type, getter);
	}

	@Override
	public <T> void persistList(String listName, RuntimeType<T> type, Supplier<T> elementFactory, Supplier<List<T>> getter) {
		super.persistList(listName, type, elementFactory, getter);
	}

	@Override
	public <T> void persistMap(String mapName, RuntimeType<T> type, Supplier<Map<String, T>> getter) {
		super.persistMap(mapName, type, getter);
	}

	@Override
	public <K, V> void persistMap(String mapName, RuntimeType<K> keyType, RuntimeType<V> valueType, Supplier<Map<K, V>> getter) {
		super.persistMap(mapName, keyType, valueType, getter);
	}

	@Override
	public <K, V> void persistMap(String mapName, RuntimeType<K> keyType, RuntimeType<V> valueType, Supplier<K> keyFactory, Supplier<V> valueFactory, Supplier<Map<K, V>> getter) {
		super.persistMap(mapName, keyType, valueType, keyFactory, valueFactory, getter);
	}
	
}
