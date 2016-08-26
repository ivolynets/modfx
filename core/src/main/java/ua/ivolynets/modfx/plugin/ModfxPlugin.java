package ua.ivolynets.modfx.plugin;

import ua.ivolynets.modfx.GuiContext;

/**
 * General interface for ModFX plugins.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public interface ModfxPlugin {
	
	/**
	 * Returns plugin name.
	 * @return Plugin name.
	 */
	public String name();
	
	/**
	 * Returns plugin version.
	 * @return Plugin version.
	 */
	public String version();
	
	/**
	 * Initializes plugin. This method is called once plugin is loaded.
	 * @param context	Reference to the GUI context.
	 */
	public void init(GuiContext context);

}
