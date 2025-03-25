package net.calebscode.langforge.app.phonology;

import java.util.function.BiConsumer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ButtonTableCell<S, T> extends TableCell<S, T> {

	private final Button button;
	private final ObjectProperty<BiConsumer<T, ? super MouseEvent>> buttonClicked = new SimpleObjectProperty<>();

	public ButtonTableCell(String text) {
		getStyleClass().add("button-table-cell");
		setGraphic(null);

		button = new Button(text);
		button.setOnMouseClicked(event -> {
			var buttonClickedFunc = buttonClicked.getValue();
			if (buttonClickedFunc != null) {
				buttonClickedFunc.accept(getItem(), event);
			}
		});
	}

	public final StringProperty buttonTextProperty() {
		return button.textProperty();
	}

	public final ObjectProperty<BiConsumer<T, ? super MouseEvent>> buttonClickedProperty() {
		return buttonClicked;
	}

	public final void setButtonClicked(BiConsumer<T, ? super MouseEvent> clickedCallback) {
		buttonClicked.set(clickedCallback);
	}

	public BiConsumer<T, ? super MouseEvent> getButtonClicked() {
		return buttonClicked.getValue();
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setGraphic(null);
		}
		else {
			setGraphic(button);
		}
	}

	public static <S> Callback<TableColumn<S, String>, ButtonTableCell<S, String>> forTableColumn() {
		return column -> new ButtonTableCell<>("");
	}

}
