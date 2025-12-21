package net.calebscode.langforge.app.phonology.model;

import static javafx.collections.FXCollections.observableArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.calebscode.langforge.app.data.RuntimeType;
import net.calebscode.langforge.app.data.SaveLoadModel;

public class PhonemeFeatureModel extends SaveLoadModel {

	private BooleanProperty isDeleted;
	private StringProperty name;
	private ListProperty<String> values = new SimpleListProperty<>(observableArrayList());

	public PhonemeFeatureModel() {
		this("<unloaded>");
	}
	
	public PhonemeFeatureModel(String featureName) {
		isDeleted = new SimpleBooleanProperty(false);
		name = new SimpleStringProperty(featureName);

		persist("name", name::get, name::set);
		persistList("values", new RuntimeType<String>() {}, values::get);
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
