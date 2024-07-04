module net.calebscode.langforge.app {
	requires transitive javafx.graphics;
	requires transitive javafx.controls;

	uses net.calebscode.langforge.app.plugin.LangforgePlugin;

	exports net.calebscode.langforge.app;
	exports net.calebscode.langforge.app.plugin;
}