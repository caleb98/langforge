package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadList<T extends SaveLoadValue<?>> extends SaveLoadValue<List<T>> {

	private final Supplier<T> elementCreator;

	public SaveLoadList(Supplier<T> elementCreator, Supplier<List<T>> getter, Consumer<List<T>> setter) {
		super(getter, setter);
		this.elementCreator = elementCreator;
	}

	public T createElement() {
		return elementCreator.get();
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveList(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadList(this, loadContext);
	}

}
