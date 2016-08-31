package ua.ivolynets.modfx.control.toolbox;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * General interface for tool box controllers.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public interface ToolBoxController {
	
	/**
	 * Adds a new tool to the tool box and returns reference to it.
	 * 
	 * @param caption	Tool caption.
	 * @param icon		Tool icon.
	 * @param layout	Tool layout URL.
	 * @return	Reference to the tool object.
	 */
	public Tool addTool(String caption, Image icon, URL layout);
	
	/**
	 * Adds a new tool to the tool box and returns reference to it.
	 * 
	 * @param caption	Tool caption.
	 * @param icon		Tool icon resource.
	 * @param layout	Tool layout resource.
	 * @return	Reference to the tool object.
	 */
	public Tool addTool(String caption, String icon, String layout);
	
	/**
	 * Adds a new tool to the tool box and returns reference to it.
	 * 
	 * @param caption	Tool caption.
	 * @param layout	Tool layout URL.
	 * @return	Reference to the tool object.
	 */
	public Tool addTool(String caption, URL layout);
	
	/**
	 * Adds a new tool to the tool box and returns reference to it.
	 * 
	 * @param caption	Tool caption.
	 * @param layout	Tool layout resource.
	 * @return	Reference to the tool object.
	 */
	public Tool addTool(String caption, String layout);

}
