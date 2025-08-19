package net.calebscode.langforge.app.phonology.model;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMapper;
import net.calebscode.langforge.phonology.rules.PhonologicalRule;
import net.calebscode.langforge.phonology.rules.PhonologicalRuleCompiler;

public class PhonologicalRuleModel {

	private PhonologicalRuleCompiler compiler;

	private StringProperty name = new SimpleStringProperty("");
	private StringProperty source = new SimpleStringProperty("");
	private ObjectProperty<Optional<PhonologicalRule>> rule = new SimpleObjectProperty<>(Optional.empty());
	private StringProperty compileError = new SimpleStringProperty("");

	public PhonologicalRuleModel(PhonemeRepresentationMapper ipaMapper) {
		compiler = new PhonologicalRuleCompiler(ipaMapper);
		source.addListener(this::onSourceChanged);
	}

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

	public ReadOnlyObjectProperty<Optional<PhonologicalRule>> ruleProperty() {
		return rule;
	}

	public Optional<PhonologicalRule> getRule() {
		return rule.get();
	}

	public ReadOnlyStringProperty compileErrorProperty() {
		return compileError;
	}

	public String getCompileError() {
		return compileError.get();
	}

	private void onSourceChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		rule.set(Optional.empty());
		compileError.set("");

		if (newValue.isBlank()) {
			return;
		}

		try {
			var result = compiler.compile(newValue);
			rule.set(Optional.of(result));
		} catch (RuntimeException compileEx) {
			compileError.set(compileEx.getMessage());
		}
	}

}
