package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadLong extends SaveLoadValue<Long> {

	public SaveLoadLong(Supplier<Long> getter, Consumer<Long> setter) {
		super(getter, setter);
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveLong(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadLong(this, loadContext);
	}

}
