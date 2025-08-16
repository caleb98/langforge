package net.calebscode.langforge.app.lexicon.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.PhonemeSequence;

public class LexiconWordTableCell<S> extends TableCell<S, PhonemeSequence> {

	private final Text label;
	private final TextField edit;

	private ObjectProperty<IpaPhonemeMapper> ipaMapper;

	public LexiconWordTableCell(IpaPhonemeMapper ipaMapper) {
		this.ipaMapper = new SimpleObjectProperty<>(ipaMapper);

		label = new Text();
		edit = new TextField();
//		edit.setOnAction(this::onEditAction);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setText(null);
	}

	public ObjectProperty<IpaPhonemeMapper> ipaMapperProperty() {
		return ipaMapper;
	}

	public IpaPhonemeMapper getIpaMapper() {
		return ipaMapper.get();
	}

	public void setIpaMapper(IpaPhonemeMapper ipaMapper) {
		this.ipaMapper.set(ipaMapper);
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

//	private void onEditAction(ActionEvent event) {
//		var value = edit.getText();
//
//		commitEdit();
//	}
}
