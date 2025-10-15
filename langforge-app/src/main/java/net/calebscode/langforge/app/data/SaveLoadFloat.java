package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadFloat extends SaveLoadValue<Float> {

	public SaveLoadFloat(Supplier<Float> getter, Consumer<Float> setter) {
		super(getter, setter);
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveFloat(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadFloat(this, loadContext);
	}

}
