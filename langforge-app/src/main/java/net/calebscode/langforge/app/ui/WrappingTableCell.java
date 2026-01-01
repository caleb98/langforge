package net.calebscode.langforge.app.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class WrappingTableCell<S, T> extends TableCell<S, T> {

    private final static StringConverter<?> defaultStringConverter = new StringConverter<>() {
        @Override public String toString(Object t) {
            return t == null ? null : t.toString();
        }

        @Override public Object fromString(String string) {
            return string;
        }
    };

	private final Text label;

	private final ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<>(this, "converter");
	private StringProperty stringProperty;

	@SuppressWarnings("unchecked")
	public WrappingTableCell() {
		this((StringConverter<T>) defaultStringConverter);
	}

	public WrappingTableCell(StringConverter<T> converter) {
		this.converter.set(converter);

		getStyleClass().add("wrapping-table-cell");

		label = new Text();
		label.wrappingWidthProperty().bind(widthProperty().subtract(2));
		label.setTextAlignment(TextAlignment.LEFT);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setText(null);
		setGraphic(null);
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setGraphic(null);
		}
		else {
			if (stringProperty instanceof StringProperty prop) {
				label.textProperty().unbindBidirectional(prop);
			}

			ObservableValue<?> obsValue = getTableColumn().getCellObservableValue(getIndex());
			if (obsValue instanceof StringProperty prop) {
				stringProperty = prop;
				label.textProperty().bind(prop);
			}

			setGraphic(label);
		}

	}

	public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn() {
		return _ -> new WrappingTableCell<>();
	}
}
