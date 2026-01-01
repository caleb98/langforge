package net.calebscode.langforge.app;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import net.calebscode.langforge.app.ui.FXMLController;

public class LangforgeApplicationController extends BorderPane implements FXMLController {

	@FXML private Label statusText;
	@FXML private MenuBar menuBar;
	@FXML private TabPane tabPane;

	public LangforgeApplicationController(LangforgeApplicationModel model) {
		load(() -> {
			Bindings.bindContentBidirectional(model.menus, menuBar.getMenus());
			Bindings.bindContentBidirectional(model.tabs, tabPane.getTabs());

			leftProperty().bindBidirectional(model.leftPanel);
			rightProperty().bindBidirectional(model.rightPanel);

			statusText.setText("Langforge " + LangforgeApplication.VERSION);
		});
	}

}
