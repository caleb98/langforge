package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

public class PhonologicalRuleCollectionModel {

	private final ListProperty<PhonologicalRuleModel> rules = new SimpleListProperty<>(observableArrayList());

	public ListProperty<PhonologicalRuleModel> rulesProperty() {
		return rules;
	}

}
