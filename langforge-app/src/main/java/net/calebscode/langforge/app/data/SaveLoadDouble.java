package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class SaveLoadDouble extends SaveLoadValue<Double> {

	public SaveLoadDouble(Supplier<Double> getter, Consumer<Double> setter) {
		super(getter, setter);
	}

	@Override
	public <SaveContext> void save(SaveLoadDataStore<SaveContext, ?> dataStore, SaveContext saveContext) throws IOException {
		dataStore.saveDouble(this, saveContext);
	}

	@Override
	public <LoadContext> void load(SaveLoadDataStore<?, LoadContext> dataStore, LoadContext loadContext) throws IOException {
		dataStore.loadDouble(this, loadContext);
	}

}
