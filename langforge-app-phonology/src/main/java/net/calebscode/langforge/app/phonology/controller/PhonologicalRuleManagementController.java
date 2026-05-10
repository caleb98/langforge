package net.calebscode.langforge.app.phonology.controller;

import java.util.Optional;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.app.ui.FXMLController;
import net.calebscode.langforge.app.ui.WrappingTableCell;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMapper;
import net.calebscode.langforge.phonology.rules.PhonologicalRuleCompiler;

public class PhonologicalRuleManagementController extends VBox implements FXMLController {

	@FXML private TableView<PhonologicalRuleModel> rulesTable;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleNameColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleSourceColumn;
	@FXML private TableColumn<PhonologicalRuleModel, String> ruleStatusColumn;

	private ObjectProperty<PhonemeRepresentationMapper> phonemeMapper;
	private ListProperty<PhonologicalRuleModel> rules;

	public PhonologicalRuleManagementController(LanguagePhonologyModel phonologyModel) {
		phonemeMapper = phonologyModel.phonemeMapperProperty();
		rules = phonologyModel.phonologicalRulesProperty();

		load(() -> {
			rulesTable.itemsProperty().bindBidirectional(rules);
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
		var model = new PhonologicalRuleModel();
		var ruleNumber = rules.size() + 1;
		model.setName("Rule " + ruleNumber);
		rules.add(model);

		model
			.sourceProperty()
			.addListener((_, _, newValue) -> onRuleSourceChanged(model, newValue));
	}

	@FXML
	private void deleteSelectedRules() {
		var rulesToRemove = rulesTable.getSelectionModel().getSelectedItems().stream().toList();
		for (var rule : rulesToRemove) {
			rules.remove(rule);
		}
	}

	private void onRuleSourceChanged(PhonologicalRuleModel model, String newSource) {
		model.setRule(Optional.empty());
		model.setCompileError("");

		if (newSource.isBlank()) {
			return;
		}

		var compiler = new PhonologicalRuleCompiler(phonemeMapper.get());

		try {
			var result = compiler.compile(newSource);
			model.setRule(Optional.of(result));
		} catch (RuntimeException compileEx) {
			model.setCompileError(compileEx.getMessage());
		}
	}

}
