package net.calebscode.langforge.app;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LangforgeApplicationView {

	private final LangforgeApplicationViewModel viewModel;

	private Label statusText = new Label("Langforge " + LangforgeApplication.VERSION);
	private MenuBar menuBar = new MenuBar();
	private TabPane tabPane = new TabPane();

	private BorderPane root = new BorderPane(tabPane, menuBar, null, null, null);
	private Scene scene = new Scene(root, 640, 480);

	public LangforgeApplicationView(LangforgeApplicationViewModel viewModel) {
		this.viewModel = viewModel;

		buildView();
		bindViewModel();
	}

	private void buildView() {
		HBox box = new HBox(statusText);
		box.setPadding(new Insets(2, 5, 2, 5));
		box.setStyle(
				"""
				-fx-border-width: 1 0 0 0;
				-fx-border-color: darkgrey;
				-fx-background-color: lightgrey;
				""");

		root.setBottom(box);
	}

	private void bindViewModel() {
		Bindings.bindContentBidirectional(viewModel.menus, menuBar.getMenus());
		Bindings.bindContentBidirectional(viewModel.tabs, tabPane.getTabs());

		root.leftProperty().bindBidirectional(viewModel.leftPanel);
		root.rightProperty().bindBidirectional(viewModel.rightPanel);
		statusText.textProperty().bindBidirectional(viewModel.statusText);
	}

	public Scene getScene() {
		return scene;
	}

}
