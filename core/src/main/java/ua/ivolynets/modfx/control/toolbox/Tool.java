package ua.ivolynets.modfx.control.toolbox;

import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

/**
 * Class represents a tool component.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class Tool {
	
	/**
	 * Tool container.
	 */
	private AnchorPane window;
	
	/**
	 * Reference to the button responsible for showing/hiding a tool window.
	 */
	private ToggleButton button;
	
	/**
	 * Reference to the parent container.
	 */
	private Parent root;
	
	/**
	 * Component controller.
	 */
	private Object controller;

	/**
	 * Constructs tool component.
	 * 
	 * @param window		Tool container.
	 * @param button		Reference to the button responsible for showing/hiding a tool window.
	 * @param root			Reference to the parent container.
	 * @param controller	Component controller.
	 */
	public Tool(AnchorPane window, ToggleButton button, Parent root, Object controller) {
		this.window = window;
		this.button = button;
		this.root = root;
		this.controller = controller;
	}

	/**
	 * @return	Tool container.
	 */
	public AnchorPane getWindow() {
		return window;
	}

	/**
	 * @return	Reference to the button responsible for showing/hiding a tool window.
	 */
	public ToggleButton getButton() {
		return button;
	}

	/**
	 * @return	Reference to the parent container.
	 */
	public Parent getRoot() {
		return root;
	}

	/**
	 * @return	Component controller.
	 */
	public Object getController() {
		return controller;
	}
	
}
