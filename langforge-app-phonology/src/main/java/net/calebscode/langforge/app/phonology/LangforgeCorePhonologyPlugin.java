package net.calebscode.langforge.app.phonology;

import static javafx.collections.FXCollections.observableArrayList;
import static net.calebscode.langforge.app.phonology.model.PhonologicalInventoryModel.createModelWithDefaultFeatures;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import net.calebscode.langforge.app.LangforgeApplication;
import net.calebscode.langforge.app.LangforgePlugin;
import net.calebscode.langforge.app.LangforgePluginException;
import net.calebscode.langforge.app.data.Migration;
import net.calebscode.langforge.app.data.SaveLoadValue;
import net.calebscode.langforge.app.phonology.api.LangforgeCorePhonologyApi;
import net.calebscode.langforge.app.phonology.controller.PhonologyController;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryMapModel;
import net.calebscode.langforge.app.plugin.MenuItemDefinition;
import net.calebscode.langforge.app.util.VersionNumber;

public final class LangforgeCorePhonologyPlugin extends LangforgePlugin {

	public static final String ID = "langforge.phonology";
	public static final String NAME = "Langforge Core - Phonology";
	public static final String DESCRIPTION = "The core Langforge phonology features.";

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
		return LangforgeApplication.VERSION_0_0_1;
	}

	@Override
	public VersionNumber getRequiredLangforgeVersion() {
		return LangforgeApplication.VERSION_0_0_1;
	}

	@Override
	public Map<String, VersionNumber> getDependencies() {
		return Map.of("langforge.core", LangforgeApplication.VERSION_0_0_1);
	}

	@Override
	public Optional<SaveLoadValue> getState() {
		return Optional.empty();
	}

	@Override
	public void setState(Optional<SaveLoadValue> maybeState) {
		phonologyModel = new LanguagePhonologyModel(
				createModelWithDefaultFeatures(),
				new SyllablePatternCategoryMapModel(),
				observableArrayList(),
				observableArrayList()
			);
	}

	@Override
	public Collection<Migration> getMigrations() {
		return List.of();
	}

	@Override
	public void initialize() throws LangforgePluginException {
		getContext().registerApi(new LangforgeCorePhonologyApi(phonologyModel));
		var phonologyMenuItem = new MenuItem("Phonology");
		phonologyMenuItem.setOnAction(_ -> {
			showPhonologyTab();
		});
		getContext().addMenuItem(new MenuItemDefinition("Edit", () -> phonologyMenuItem));

		showPhonologyTab();
	}

	private void showPhonologyTab() {
		if (phonologyTabVisible) {
			return;
		}

		var phonologyController = new PhonologyController(phonologyModel);

		phonologyTabVisible = true;
		var tab = new Tab("Phonology", phonologyController);
		tab.setOnClosed(_ -> phonologyTabVisible = false);

		getContext().createTab(tab);
	}

}
