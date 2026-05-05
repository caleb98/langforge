package net.calebscode.langforge.app;

import org.junit.jupiter.api.Test;

public class PluginManagerTest {

	@Test
	void initializePlugins() throws Exception {
		var manager = new PluginManager(new LangforgeApplicationModel());
		manager.initializePlugins();
	}

}
