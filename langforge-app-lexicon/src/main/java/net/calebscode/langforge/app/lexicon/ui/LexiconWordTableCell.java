package net.calebscode.langforge.app.lexicon.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import net.calebscode.langforge.Word;
import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;

public class LexiconWordTableCell<S> extends TableCell<S, Word> {

	private ObjectProperty<IpaPhonemeMapper> ipaMapper;
	private final Text label;

	public LexiconWordTableCell(IpaPhonemeMapper ipaMapper) {
		this.ipaMapper = new SimpleObjectProperty<>(ipaMapper);
		label = new Text();

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
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
	protected void updateItem(Word item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setGraphic(null);
			return;
		}

		var rendered = item.render(ipaMapper.get());
		label.setText(rendered);
		setGraphic(label);
	}

}
