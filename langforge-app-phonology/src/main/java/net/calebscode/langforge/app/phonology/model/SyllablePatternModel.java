package net.calebscode.langforge.app.phonology.model;

import java.util.Optional;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener.Change;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.phonology.syllable.SyllablePattern;

public class SyllablePatternModel {

	private final StringProperty patternString;
	private final ObjectProperty<Optional<SyllablePattern>> pattern;
	private final BooleanProperty hasCompileError;
	private final StringProperty compileError;

	private SyllablePatternCategoryMapModel patternModel;

	public SyllablePatternModel(SyllablePatternCategoryMapModel patternModel) {
		patternString = new SimpleStringProperty("");
		pattern = new SimpleObjectProperty<Optional<SyllablePattern>>(Optional.empty());
		hasCompileError = new SimpleBooleanProperty(false);
		compileError = new SimpleStringProperty("");

		this.patternModel = patternModel;

		patternModel.categoryMapProperty().addListener(this::onPatternModelChanged);
		patternString.addListener(this::onPatternStringChanged);
	}

	public StringProperty patternStringProperty() {
		return patternString;
	}

	public ObjectProperty<Optional<SyllablePattern>> patternProperty() {
		return pattern;
	}

	public BooleanProperty hasCompileErrorProperty() {
		return hasCompileError;
	}

	public StringProperty compileErrorProperty() {
		return compileError;
	}

	private void onPatternModelChanged(Change<? extends Character, ? extends Set<Phoneme>> change) {
		recompile();
	}

	private void onPatternStringChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		recompile();
	}

	private void recompile() {
		hasCompileError.set(false);
		compileError.set("");
		pattern.set(Optional.empty());

		try {
			var compiled = patternModel.getCompiler().compile(patternString.getValue());
			pattern.set(Optional.of(compiled));
		} catch (RuntimeException compileEx) {
			hasCompileError.set(true);
			compileError.set(compileEx.getMessage());
		}
	}

}
