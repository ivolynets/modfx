package ua.ivolynets.modfx.control.toolbar;

import javafx.scene.Parent;

/**
 * Class represents a separate bar on the tool bar.
 * @author Igor Volynets
 */
public class Bar {
	
	/**
	 * Reference to the root component.
	 */
	private Parent root;
	
	/**
	 * Bar controller.
	 */
	private Object controller;
	
	/**
	 * Constructs bar.
	 * 
	 * @param root			Reference to the root component.
	 * @param controller	Bar controller.
	 */
	public Bar(Parent root, Object controller) {
		this.root = root;
		this.controller = controller;
	}
	
	/**
	 * @return Reference to the root component.
	 */
	public Parent getRoot() {
		return this.root;
	}
	
	/**
	 * @return	Bar controller.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getController() {
		return (T) this.controller;
	}

}
