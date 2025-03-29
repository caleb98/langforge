package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

public class PhonemeFeatureModel {

	private BooleanProperty isDeleted;
	private ReadOnlyStringProperty name;
	private ListProperty<String> values = new SimpleListProperty<>(observableArrayList());

	public PhonemeFeatureModel(String featureName) {
		isDeleted = new SimpleBooleanProperty(false);
		name = new SimpleStringProperty(featureName);
	}

	public ReadOnlyBooleanProperty isDeletedProperty() {
		return isDeleted;
	}

	public boolean isDeleted() {
		return isDeleted.get();
	}

	public void markAsDeleted() {
		isDeleted.set(true);
	}

	public ReadOnlyStringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public ListProperty<String> valuesProperty() {
		return values;
	}

}
