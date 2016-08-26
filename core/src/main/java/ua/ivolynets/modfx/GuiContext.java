package ua.ivolynets.modfx;

import ua.ivolynets.modfx.plugin.PluginManager;

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
	

}
