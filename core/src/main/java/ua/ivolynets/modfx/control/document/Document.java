package ua.ivolynets.modfx.control.document;

import javafx.scene.Parent;
import javafx.scene.control.Tab;

/**
 * Class represents application document.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class Document {
	
	/**
	 * Document tab component.
	 */
	private Tab tab;
	
	/**
	 * Reference to the root component.
	 */
	private Parent root;
	
	/**
	 * Document controller.
	 */
	private Object controller;
	
	/**
	 * Constructs document object.
	 * 
	 * @param tab			Document tab component.
	 * @param root			Reference to the root component.
	 * @param controller	Document controller.
	 */
	public Document(Tab tab, Parent root, Object controller) {
		this.tab = tab;
		this.root = root;
		this.controller = controller;
	}
	
	/**
	 * @return	Reference to the document tab component.
	 */
	public Tab getTab() {
		return this.tab;
	}
	
	/**
	 * @return	Reference to the root component.
	 */
	public Parent getRoot() {
		return this.root;
	}
	
	/**
	 * @return	Document controller.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getController() {
		return (T) this.controller;
	}

}
