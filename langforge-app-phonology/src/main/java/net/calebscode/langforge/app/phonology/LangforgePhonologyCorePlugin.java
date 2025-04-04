package net.calebscode.langforge.app.phonology;

import java.util.Map;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryModel;
import net.calebscode.langforge.app.phonology.view.PhonologyView;
import net.calebscode.langforge.app.plugin.LangforgePlugin;
import net.calebscode.langforge.app.plugin.LangforgePluginException;
import net.calebscode.langforge.app.plugin.ui.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgePhonologyCorePlugin implements LangforgePlugin {

	private static final String ID = "langforge.phonology";
	private static final String NAME = "Langforge Core - Phonology";
	private static final String DESCRIPTION = "The core Langforge phonology features.";

	private LangforgePluginContext context;
	private PhonologicalInventoryModel phonologyModel;
	private SyllablePatternCategoryModel syllableModel;

	private boolean phonologyTabVisible = false;

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
		return LangforgeApplication.VERSION;
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.VERSION;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return Map.of("langforge.core", LangforgeApplication.VERSION);
	}

	@Override
	public void init(LangforgePluginContext context) throws LangforgePluginException {
		this.context = context;

		phonologyModel = PhonologicalInventoryModel.createModelWithIpaDefaults();
		syllableModel = new SyllablePatternCategoryModel();
	}

	@Override
	public void load(LangforgePluginContext context) throws LangforgePluginException {
		var phonologyMenuItem = new MenuItem("Phonology");
		phonologyMenuItem.setOnAction(event -> {
			showPhonologyTab();
		});
		context.addMenuItem(new MenuItemDefinition("Edit", () -> phonologyMenuItem));

		showPhonologyTab();
	}

	private void showPhonologyTab() {
		if (phonologyTabVisible) {
			return;
		}

		phonologyTabVisible = true;
		var tab = new Tab("Phonology", new PhonologyView(context, phonologyModel, syllableModel));
		tab.setOnClosed(event -> phonologyTabVisible = false);

		context.createTab(tab);
	}

}
