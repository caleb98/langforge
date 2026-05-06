package net.calebscode.langforge.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.app.data.SaveLoadString;
import net.calebscode.langforge.app.test.util.JsonAssertions;

public class PluginManagerTest {

	PluginManager manager;
	TestPlugin testPlugin;

	@BeforeEach
	void beforeEach() throws Exception {
		manager = new PluginManager(new LangforgeApplicationModel());
		manager.initializePlugins();
		testPlugin = manager.getApiProvider().getApi(TestPlugin.class).get();
	}

	@Test
	void pluginStateSavedWithInfo() throws Exception {
		testPlugin.setState(Optional.of(new SaveLoadString("state")));
		var output = new ByteArrayOutputStream();

		manager.savePluginStates(output);

		var result = output.toString(StandardCharsets.UTF_8);
		JsonAssertions.assertJsonEquals(
			"""
			{
				"langforge.test": {
					"version": "1.2.3",
					"state": "state"
				}
			}
			""",
			result
		);
	}

	@Test
	void pluginStateMigrated() throws Exception {
		var jsonSate =
			"""
			{
				"langforge.test": {
					"version": "2.0.0",
					"state": {
						"someKey": "somevalue"
					}
				}
			}
			""";
		var input = new ByteArrayInputStream(jsonSate.getBytes(StandardCharsets.UTF_8));

		manager.loadPluginStates(input);

		var migratedState = testPlugin.getState().get();
		assertEquals("SOMEVALUE", migratedState.asObject().get("someKey").asString().value());
	}

	@Test
	void pluginStateMigratedThroughMultipleVersions() throws Exception {
		var jsonSate =
			"""
			{
				"langforge.test": {
					"version": "1.0.0",
					"state": "somevalue"
				}
			}
			""";
		var input = new ByteArrayInputStream(jsonSate.getBytes(StandardCharsets.UTF_8));

		manager.loadPluginStates(input);

		var migratedState = testPlugin.getState().get();
		assertEquals("SOMEVALUE", migratedState.asObject().get("someKey").asString().value());
	}

}
