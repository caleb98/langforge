package net.calebscode.langforge.app;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.util.VersionNumber;

public class TestPlugin extends LangforgePlugin {

	private static final String ID = "langforge.test";
	private static final String NAME = "Langforge Test";
	private static final String DESCRIPTION = "A plugin for the Langforge Unit tests.";

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
		return new VersionNumber(1, 2, 3);
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
		return Optional.empty();
	}

	@Override
	public void setState(Optional<SaveLoadValue> maybeState) {

	}

	@Override
	public Collection<Migration> getMigrations() {
		return emptyList();
	}

	@Override
	public void initialize() throws LangforgePluginException {

	}

}
