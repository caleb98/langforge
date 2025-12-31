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
import net.calebscode.langforge.app.data.SaveLoadSchema;
import net.calebscode.langforge.app.data.SaveLoadable;

public class PhonemeFeatureModel implements SaveLoadable<PhonemeFeatureModel> {

	private static final SaveLoadSchema<PhonemeFeatureModel> schema = new SaveLoadSchema<>();
	
	static {
		schema.addProperty("name", m -> m.name);
		schema.addList("values", new RuntimeType<String>() {}, PhonemeFeatureModel::valuesProperty);
	}
	
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
	
	@Override
	public PhonemeFeatureModel getValue() {
		return this;
	}
	
	@Override
	public SaveLoadSchema<PhonemeFeatureModel> getSchema() {
		return schema;
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
