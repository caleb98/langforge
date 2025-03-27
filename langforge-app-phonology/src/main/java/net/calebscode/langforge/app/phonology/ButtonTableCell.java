package net.calebscode.langforge.app.phonology;

import java.util.function.BiConsumer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ButtonTableCell<S, T> extends TableCell<S, T> {

	private final Button button;
	private final ObjectProperty<BiConsumer<T, ? super MouseEvent>> buttonClicked = new SimpleObjectProperty<>();

	public ButtonTableCell(String text) {
		getStyleClass().add("button-table-cell");
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setGraphic(null);
		setPadding(new Insets(0));

		button = new Button(text);
		button.setOnMouseClicked(event -> {
			var buttonClickedFunc = buttonClicked.getValue();
			if (buttonClickedFunc != null) {
				buttonClickedFunc.accept(getItem(), event);
			}
		});

		button.maxWidth(Double.MAX_VALUE);
		button.maxHeight(Double.MAX_VALUE);

		// Ensure that the button fills the cell area. Subtracting 2
		// is necessary here to ensure that the button doesn't force
		// a resize of the cell. Otherwise, the button will grow out
		// of control.
		button.prefWidthProperty().bind(widthProperty().subtract(2));
		button.prefHeightProperty().bind(heightProperty().subtract(2));
	}

	public final Button getButton() {
		return button;
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
