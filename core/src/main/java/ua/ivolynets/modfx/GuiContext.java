package ua.ivolynets.modfx;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ua.ivolynets.modfx.configuration.ConfigurationManager;
import ua.ivolynets.modfx.control.ApplicationController;
import ua.ivolynets.modfx.db.DbManager;
import ua.ivolynets.modfx.event.EventService;
import ua.ivolynets.modfx.plugin.PluginManager;
import ua.ivolynets.modfx.task.TaskService;

/**
 * Class contains context of the application.
 * @author Igor Volynets
 */
public class GuiContext {
	
	public static final String DEFAULT_CONTEXT_NAME = "default";
	
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
	 * Reference to the database manager.
	 */
	private final DbManager dbManager;
	
	/**
	 * Reference to the configuration manager.
	 */
	private final ConfigurationManager configurationManager;
	
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
	 * Constructs GUI context from parent context for a given namespace.
	 * 
	 * @param namespace	Context namespace.
	 * @param parent	Parent context.
	 */
	public GuiContext(String namespace, GuiContext parent) {
		
		this.namespace = namespace;
		this.parent = parent;
		
		this.controller = parent.controls();
		this.pluginManager = parent.pluginManager();
		this.taskService = parent.taskService();
		this.eventService = parent.eventService();
		
		this.dbManager = new DbManager(this.namespace);
		this.configurationManager = new ConfigurationManager(this.namespace);
		
		this.loader = new FXMLLoader();
		
	}
	
	/**
	 * Constructs default GUI context.
	 */
	public GuiContext() {
		
		this.namespace = DEFAULT_CONTEXT_NAME;
		this.parent = null;
		
		this.pluginManager = new PluginManager();
		this.taskService = new TaskService(this);
		this.eventService = new EventService();
		
		this.dbManager = new DbManager(this.namespace);
		this.configurationManager = new ConfigurationManager(this.namespace);
		
		this.loader = new FXMLLoader();
		
		try {
			
			this.loader.setLocation(ApplicationResources.FXML_APPLICATION);
			this.loader.load(Objects.requireNonNull(ApplicationResources.FXML_APPLICATION.openStream(), "Could not load application layout descriptor"));
			
			this.controller = this.loader.getController();
			this.controller.init(this);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * @return	GUI controls.
	 */
	public ApplicationController controls() {
		return this.controller;
	}

	/**
	 * @return Plugin manager.
	 */
	public PluginManager pluginManager() {
		return this.pluginManager;
	}

	/**
	 * @return Task service.
	 */
	public TaskService taskService() {
		return this.taskService;
	}

	/**
	 * @return Event service.
	 */
	public EventService eventService() {
		return this.eventService;
	}

	/**
	 * @return Database manager.
	 */
	public DbManager db() {
		return this.dbManager;
	}

	/**
	 * @return Configuration manager.
	 */
	public ConfigurationManager configuration() {
		return this.configurationManager;
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
