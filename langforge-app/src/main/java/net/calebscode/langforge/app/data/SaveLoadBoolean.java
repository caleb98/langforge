package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadBoolean extends SaveLoadValue<Boolean> {

	public SaveLoadBoolean(Supplier<Boolean> getter, Consumer<Boolean> setter) {
		super(getter, setter);
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveBoolean(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadBoolean(this, loadContext);
	}

}
