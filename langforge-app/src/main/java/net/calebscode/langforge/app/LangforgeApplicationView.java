package net.calebscode.langforge.app;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class LangforgeApplicationView {

	private Scene scene;
	private BorderPane root;

	private MenuBar menuBar;
	private Menu fileMenu;
	private Menu editMenu;
	private Menu helpMenu;

	private TabPane tabPane;

	public LangforgeApplicationView() {
		fileMenu = new Menu("File");
		editMenu = new Menu("Edit");
		helpMenu = new Menu("Help");
		menuBar = new MenuBar(fileMenu, editMenu, helpMenu);

		var tab1 = new Tab("Hello");
		var tab2 = new Tab("Hello");
		var tab3 = new Tab("Hello");
		tabPane = new TabPane(tab1, tab2, tab3);

		root = new BorderPane(tabPane, menuBar, null, null, null);

		scene = new Scene(root, 640, 480);
	}

	public Menu getFileMenu() {
		return fileMenu;
	}

	public Menu getEditMenu() {
		return editMenu;
	}

	public Menu getHelpMenu() {
		return helpMenu;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public BorderPane getRoot() {
		return root;
	}

	public Scene getScene() {
		return scene;
	}

}
