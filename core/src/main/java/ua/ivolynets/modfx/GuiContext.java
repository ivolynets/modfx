package ua.ivolynets.modfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ua.ivolynets.modfx.control.ApplicationController;
import ua.ivolynets.modfx.control.event.EventService;
import ua.ivolynets.modfx.plugin.PluginManager;
import ua.ivolynets.modfx.task.TaskService;

/**
 * Singleton contains context of the application.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class GuiContext {
	
	/**
	 * Instance of the default application context.
	 */
	private static final GuiContext defaultContext = new GuiContext();
	
	/**
	 * Reference to the parent context.
	 */
	private final GuiContext parent;
	
	/**
	 * Context namespace.
	 */
	private final String namespace;
	
	/**
	 * Reference to the plugin manager.
	 */
	private final PluginManager pluginManager;
	
	/**
	 * Reference to the task service.
	 */
	private final TaskService taskService;
	
	/**
	 * Reference to the event service.
	 */
	private final EventService eventService;
	
	/**
	 * Application main controller.
	 */
	private final ApplicationController controller;
	
	/**
	 * Application main scene.
	 */
	private Scene scene;
	
	/**
	 * Loader of the FXML elements.
	 */
	private FXMLLoader loader;
	
	/**
	 * @return	GUI controls.
	 */
	public ApplicationController controls() {
		return this.controller;
	}
	
	/**
	 * @return	Main application scene.
	 */
	public Scene scene() {
		
		// We need lazy late initialization of scene since it must be running only in JavaFX thread.
		
		if (this.parent == null) {
			
			if (this.scene == null) {
				this.scene = new Scene((Parent) this.loader().getRoot());
			}
			
			return this.scene;
			
		} else {
			return this.parent.scene();
		}
		
	}
	
	/**
	 * @return	Loader of the FXML elements.
	 */
	private FXMLLoader loader() {
		return this.loader;
	}

}
