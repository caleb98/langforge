package net.calebscode.langforge.app.phonology.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleCollectionModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class PhonologicalRuleManagementController extends VBox implements FXMLController {

	@FXML private TableView<PhonologicalRuleModel> rulesTable;

	PhonologicalRuleCollectionModel rules;

	public PhonologicalRuleManagementController(PhonologicalRuleCollectionModel rules) {
		this.rules = rules;

		load(() -> {
			rulesTable.itemsProperty().bindBidirectional(rules.rulesProperty());
		});
	}

	@FXML
	private void addNewRule() {
		rules.rulesProperty().add(new PhonologicalRuleModel(StandardPhonemes.IPA_MAPPER));
	}

}
