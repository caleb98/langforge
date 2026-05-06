package net.calebscode.langforge.app;

import static java.util.Collections.emptyMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadObject;
import net.calebscode.langforge.app.data.SaveLoadString;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.util.VersionNumber;

public class TestPlugin extends LangforgePlugin {

	private static final String ID = "langforge.test";
	private static final String NAME = "Langforge Test";
	private static final String DESCRIPTION = "A plugin for the Langforge Unit tests.";

	private Optional<SaveLoadValue> state;

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public VersionNumber getVersion() {
		return new VersionNumber(3, 0, 0);
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.CURRENT_VERSION;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return emptyMap();
	}

	@Override
	public Optional<SaveLoadValue> getState() {
		return state;
	}

	@Override
	public void setState(Optional<SaveLoadValue> maybeState) {
		state = maybeState;
	}

	@Override
	public Collection<Migration> getMigrations() {
		return List.of(
			Migration.forVersion(new VersionNumber(2, 0, 0), TestPlugin::migrateToVersionTwo),
			Migration.forVersion(new VersionNumber(3, 0, 0), TestPlugin::migrateToVersionThree)
		);
	}

	@Override
	public void initialize() throws LangforgePluginException {
		getContext().registerApi(this);
	}

	private static SaveLoadValue migrateToVersionTwo(SaveLoadValue oldState) {
		return new SaveLoadObject(Map.of(
			"someKey", oldState
		));
	}

	private static SaveLoadValue migrateToVersionThree(SaveLoadValue oldState) {
		var object = oldState.asObject();
		object.put(
			"someKey",
			new SaveLoadString(object.get("someKey").asString().value().toUpperCase())
		);
		return object;
	}

}
