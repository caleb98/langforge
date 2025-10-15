package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadInteger extends SaveLoadValue<Integer> {

	public SaveLoadInteger(Supplier<Integer> getter, Consumer<Integer> setter) {
		super(getter, setter);
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveInteger(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadInteger(this, loadContext);
	}

}
