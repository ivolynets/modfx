package ua.ivolynets.modfx.control.window;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Class represents application pop-up window.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class Window {
	
	/**
	 * Window stage.
	 */
	private Stage stage;
	
	/**
	 * Reference to the root component.
	 */
	private Parent root;
	
	/**
	 * Window controller.
	 */
	private Object controller;

	/**
	 * Constructs pop-up window.
	 * 
	 * @param stage			Window stage.
	 * @param root			Reference to the root component.
	 * @param controller	Window controller.
	 */
	public Window(Stage stage, Parent root, Object controller) {
		this.stage = stage;
		this.root = root;
		this.controller = controller;
	}

	/**
	 * @return	Window stage.
	 */
	public Stage getStage() {
		return this.stage;
	}

	/**
	 * @return	Reference to the root component.
	 */
	public Parent getRoot() {
		return this.root;
	}

	/**
	 * @return	Window controller.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getController() {
		return (T) this.controller;
	}
	
	/**
	 * Closes pop-up window.
	 */
	public void close() {
		this.stage.close();
	}

}
