package net.calebscode.langforge.app.phonology.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleCollectionModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.app.ui.WrappingTableCell;
import net.calebscode.langforge.app.util.FXMLController;
import net.calebscode.langforge.phonology.phoneme.StandardPhonemes;

public class PhonologicalRuleManagementController extends VBox implements FXMLController {

	@FXML private TableView<PhonologicalRuleModel> rulesTable;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleNameColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleSourceColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleStatusColumn;

	PhonologicalRuleCollectionModel rules;

	public PhonologicalRuleManagementController(PhonologicalRuleCollectionModel rules) {
		this.rules = rules;

		load(() -> {
			rulesTable.itemsProperty().bindBidirectional(rules.rulesProperty());

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
		rules.rulesProperty().add(new PhonologicalRuleModel(StandardPhonemes.IPA_MAPPER));
	}

}
