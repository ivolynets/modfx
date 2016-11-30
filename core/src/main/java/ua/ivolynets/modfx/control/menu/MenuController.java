package ua.ivolynets.modfx.control.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * Main menu controller.
 * @author Igor Volynets
 */
public class MenuController {
	
	/**
	 * Reference to the menu component.
	 */
	private MenuBar menu;
	
	/**
	 * Constructs menu controller.
	 * @param menu	Reference to the menu component.
	 */
	public MenuController(MenuBar menu) {
		this.menu = menu;
	}
	
	/**
	 * Returns reference to the File section of the main menu.
	 * @return	Menu component.
	 */
	public Menu file() {
		return this.menu.getMenus().get(0);
	}
	
	/**
	 * Returns reference to the Edit section of the main menu.
	 * @return	Menu component.
	 */
	public Menu edit() {
		return this.menu.getMenus().get(1);
	}
	
	/**
	 * Returns reference to the Tools section of the main menu.
	 * @return	Menu component.
	 */
	public Menu tools() {
		return this.menu.getMenus().get(2);
	}

}
