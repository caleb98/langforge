package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class SaveLoadValue<T> {

	private final Supplier<T> getter;
	private final Consumer<T> setter;

	public SaveLoadValue(Supplier<T> getter, Consumer<T> setter) {
		this.getter = getter;
		this.setter = setter;
	}

	public T getValue() {
		return getter.get();
	}

	public void setValue(T value) {
		setter.accept(value);
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	public abstract <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException;
	public abstract <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException;

}
