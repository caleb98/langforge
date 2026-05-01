package net.calebscode.langforge.app.phonology.model;

import javafx.beans.property.*;

import static javafx.collections.FXCollections.observableArrayList;

public class PhonemeFeatureModel {

	private BooleanProperty isDeleted;
	private StringProperty name;
	private ListProperty<String> values;

	public PhonemeFeatureModel() {
		this("<unloaded>");
	}
	
	public PhonemeFeatureModel(String featureName) {
		isDeleted = new SimpleBooleanProperty(false);
		name = new SimpleStringProperty(featureName);
		values = new SimpleListProperty<>(observableArrayList());
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
