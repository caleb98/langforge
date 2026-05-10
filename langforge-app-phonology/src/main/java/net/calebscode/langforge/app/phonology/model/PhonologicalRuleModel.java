package net.calebscode.langforge.app.phonology.model;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.calebscode.langforge.phonology.rules.PhonologicalRule;

public class PhonologicalRuleModel {

	private StringProperty name = new SimpleStringProperty("");
	private StringProperty source = new SimpleStringProperty("");
	private ObjectProperty<Optional<PhonologicalRule>> rule = new SimpleObjectProperty<>(Optional.empty());
	private StringProperty compileError = new SimpleStringProperty("");

	public StringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty sourceProperty() {
		return source;
	}

	public String getSource() {
		return source.get();
	}

	public void setSource(String source) {
		this.source.set(source);
	}

	public ObjectProperty<Optional<PhonologicalRule>> ruleProperty() {
		return rule;
	}

	public Optional<PhonologicalRule> getRule() {
		return rule.get();
	}

	public void setRule(Optional<PhonologicalRule> rule) {
		this.rule.set(rule);
	}

	public ReadOnlyStringProperty compileErrorProperty() {
		return compileError;
	}

	public String getCompileError() {
		return compileError.get();
	}

	public void setCompileError(String compileError) {
		this.compileError.set(compileError);
	}

}
