package net.calebscode.langforge.app.phonology;

import static javafx.collections.FXCollections.observableArrayList;
import static net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel.createModelWithDefaultFeatures;

import java.util.Map;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.LangforgePluginContext;
import net.calebscode.langforge.app.LangforgePluginException;
import net.calebscode.langforge.app.phonology.api.LangforgeCorePhonologyApi;
import net.calebscode.langforge.app.phonology.controller.PhonologyController;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryMapModel;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeCorePhonologyPlugin implements LangforgePlugin {

	public static final String ID = "langforge.phonology";
	public static final String NAME = "Langforge Core - Phonology";
	public static final String DESCRIPTION = "The core Langforge phonology features.";

	private LangforgePluginContext context;
	private LanguagePhonologyModel phonologyModel;

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

		phonologyModel = new LanguagePhonologyModel(
			createModelWithDefaultFeatures(),
			new SyllablePatternCategoryMapModel(),
			observableArrayList(),
			observableArrayList()
		);

		context.registerApi(new LangforgeCorePhonologyApi(phonologyModel));
		context.registerSaveLoadModel("phonologyModel", phonologyModel);
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

		var phonologyController = new PhonologyController(phonologyModel);

		phonologyTabVisible = true;
		var tab = new Tab("Phonology", phonologyController);
		tab.setOnClosed(event -> phonologyTabVisible = false);

		context.createTab(tab);
	}

}
