package net.calebscode.langforge.app.lexicon.ui;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.phonology.PhonemeSequenceValidationException;
import net.calebscode.langforge.phonology.PhonologicalRuleApplicationException;
import net.calebscode.langforge.phonology.SyllablePatternPhonemeSequenceValidator;
import net.calebscode.langforge.phonology.phoneme.IpaMappingException;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langforge.phonology.rules.PhonologicalRuleApplicator;

public class LexiconWordTableCell<S> extends TableCell<S, PhonemeSequence> {

	private final Text label;
	private final TextField edit;

	private final ObjectProperty<IpaPhonemeMapper> ipaMapper;
	private final LanguagePhonologyModel phonologyModel;

	public LexiconWordTableCell(
		IpaPhonemeMapper ipaMapper,
		LanguagePhonologyModel phonologyModel
	) {
		this.ipaMapper = new SimpleObjectProperty<>(ipaMapper);
		this.phonologyModel = phonologyModel;

		label = new Text();
		edit = new TextField();
		edit.setOnAction(this::onEditAction);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setText(null);
	}

	public ObjectProperty<IpaPhonemeMapper> ipaMapperProperty() {
		return ipaMapper;
	}

	public IpaPhonemeMapper getIpaMapper() {
		return ipaMapper.get();
	}

	@Override
	public void startEdit() {
		super.startEdit();

		if (!isEditing()) {
			return;
		}

		edit.setText("");
		setGraphic(edit);
		edit.selectAll();
		edit.requestFocus();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setGraphic(label);
	}

	@Override
	public void commitEdit(PhonemeSequence newValue) {
		// TODO Auto-generated method stub
		super.commitEdit(newValue);
	}

	@Override
	protected void updateItem(PhonemeSequence item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setGraphic(null);
			return;
		}

		var rendered = item.render(ipaMapper.get());
		label.setText(rendered);
		setGraphic(label);
	}

	private void onEditAction(ActionEvent event) {
		PhonemeSequence sequence;

		try {
			var ipa = edit.getText();
			sequence = new PhonemeSequenceBuilder()
				.append(ipa, getIpaMapper())
				.build();
		} catch (IpaMappingException ex) {
			var errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("IPA Error");
			errorAlert.setHeaderText("Failed to parse IPA.");
			errorAlert.setContentText(ex.getMessage());
			errorAlert.showAndWait();
			return;
		}

		var validator = new SyllablePatternPhonemeSequenceValidator(
			phonologyModel.getSyllablePatternCategories(),
			phonologyModel.getSyllablePatterns());

		try {
			var activeRules = phonologyModel
				.getPhonologicalRules()
				.rulesProperty()
				.stream()
				.map(PhonologicalRuleModel::getRule)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(PhonologicalRuleApplicator::new)
				.toList();

			PhonemeSequence originalSequence;
			do {
				originalSequence = sequence;
				for (var ruleApplicator : activeRules) {
					sequence = ruleApplicator.apply(sequence, validator, false);
				}
			} while (!sequence.equals(originalSequence));

		} catch (PhonologicalRuleApplicationException ex) {
			var errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Phonological Rule Error");
			errorAlert.setHeaderText("An error occurred while applying phonological rules.");
			errorAlert.setContentText(ex.getMessage());
			errorAlert.showAndWait();
			return;
		}

		try {
			sequence = validator.validate(sequence);
		} catch (PhonemeSequenceValidationException ex) {
			var errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Syllable Validation Error");
			errorAlert.setHeaderText("Word did not match a valid syllable structure.");
			errorAlert.setContentText(ex.getMessage());
			errorAlert.showAndWait();
			return;
		}

		commitEdit(sequence);
	}
}
