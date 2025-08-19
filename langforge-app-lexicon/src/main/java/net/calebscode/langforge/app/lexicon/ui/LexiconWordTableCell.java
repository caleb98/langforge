package net.calebscode.langforge.app.lexicon.ui;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_REPRESENTATION_MAPPER;
import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.IPA_PHONEME_SEQUENCE_RENDERER;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.calebscode.langforge.app.phonology.model.LanguagePhonologyModel;
import net.calebscode.langforge.app.phonology.model.PhonologicalRuleModel;
import net.calebscode.langforge.phonology.phoneme.PhonemeRepresentationMappingException;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeString;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeStringBuilder;
import net.calebscode.langforge.phonology.phoneme.string.PhonemeStringValidationException;
import net.calebscode.langforge.phonology.phoneme.string.SyllablePatternValidator;
import net.calebscode.langforge.phonology.rules.PhonologicalRuleApplicationException;
import net.calebscode.langforge.phonology.rules.PhonologicalRuleApplicator;

public class LexiconWordTableCell<S> extends TableCell<S, PhonemeString> {

	private final Text label;
	private final TextField edit;

	private final LanguagePhonologyModel phonologyModel;

	public LexiconWordTableCell(LanguagePhonologyModel phonologyModel) {
		this.phonologyModel = phonologyModel;

		label = new Text();
		edit = new TextField();
		edit.setOnAction(this::onEditAction);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setText(null);
	}

	@Override
	public void startEdit() {
		super.startEdit();

		if (!isEditing()) {
			return;
		}

		var item = getItem();
		if (item != null) {
			var rendered = IPA_PHONEME_SEQUENCE_RENDERER.render(item);
			edit.setText(rendered);
		}
		else {
			edit.setText("");
		}

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
	public void commitEdit(PhonemeString newValue) {
		super.commitEdit(newValue);
	}

	@Override
	protected void updateItem(PhonemeString item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setGraphic(null);
			return;
		}

		var rendered = IPA_PHONEME_SEQUENCE_RENDERER.render(item);
		var renderedContext = IPA_PHONEME_SEQUENCE_RENDERER.renderWithContext(item);
		var text = String.format("%s (%s)", rendered, renderedContext);
		label.setText(text);
		setGraphic(label);
	}

	private void onEditAction(ActionEvent event) {
		PhonemeString sequence;

		try {
			var ipa = edit.getText();
			sequence = new PhonemeStringBuilder()
				.append(ipa, IPA_PHONEME_REPRESENTATION_MAPPER)
				.build();
		} catch (PhonemeRepresentationMappingException ex) {
			var errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("IPA Error");
			errorAlert.setHeaderText("Failed to parse IPA.");
			errorAlert.setContentText(ex.getMessage());
			errorAlert.showAndWait();
			return;
		}

		var validator = new SyllablePatternValidator(
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

			PhonemeString originalSequence;
			do {
				originalSequence = sequence;
				for (var ruleApplicator : activeRules) {
					sequence = ruleApplicator.apply(sequence, validator, true);
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
		} catch (PhonemeStringValidationException ex) {
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
