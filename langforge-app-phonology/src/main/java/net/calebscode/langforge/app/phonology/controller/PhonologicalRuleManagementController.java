package net.calebscode.langforge.app.phonology.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleCollectionModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.app.ui.FXMLController;
import net.calebscode.langforge.app.ui.WrappingTableCell;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class PhonologicalRuleManagementController extends VBox implements FXMLController {

	@FXML private TableView<PhonologicalRuleModel> rulesTable;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleNameColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleSourceColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleStatusColumn;

	private PhonologicalRuleCollectionModel rules;

	public PhonologicalRuleManagementController(LanguagePhonologyModel phonologyModel) {
		rules = phonologyModel.getPhonologicalRules();

		load(() -> {
			rulesTable.itemsProperty().bindBidirectional(rules.rulesProperty());
			rulesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			ruleNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			ruleNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

			ruleSourceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			ruleSourceColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());

			ruleStatusColumn.setCellFactory(WrappingTableCell.forTableColumn());
			ruleStatusColumn.setCellValueFactory(cellData -> cellData.getValue().compileErrorProperty());
		});
	}

	@FXML
	private void addNewRule() {
		var model = new PhonologicalRuleModel(StandardPhonemes.IPA_PHONEME_REPRESENTATION_MAPPER);
		var ruleNumber = rules.rulesProperty().size() + 1;
		model.setName("Rule " + ruleNumber);
		rules.rulesProperty().add(model);
	}

	@FXML
	private void deleteSelectedRules() {
		var rulesToRemove = rulesTable.getSelectionModel().getSelectedItems().stream().toList();
		for (var rule : rulesToRemove) {
			rules.rulesProperty().remove(rule);
		}
	}

}
