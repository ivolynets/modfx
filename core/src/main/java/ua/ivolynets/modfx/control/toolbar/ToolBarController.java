package ua.ivolynets.modfx.control.toolbar;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * Controller class for tool bars.
 * @author Igor Volynets
 */
public class ToolBarController {
	
	/**
	 * Tool bar component.
	 */
	private ToolBar toolBar;
	
	/**
	 * Constructs tool bar controller.
	 * @param toolBar	Tool bar component.
	 */
	public ToolBarController(ToolBar toolBar) {
		this.toolBar = toolBar;
	}
	
	/**
	 * Adds a new separate bar to the tool bar and returns reference to it.
	 * 
	 * @param layout	Bar layout URL.
	 * @return	Reference to the Bar object.
	 */
	public Bar addBar(URL layout) {
		
		try {
			
			final FXMLLoader loader = new FXMLLoader(layout);
			loader.load();
			
			Separator separator = new Separator();
			separator.setOrientation(Orientation.VERTICAL);
			
			this.toolBar.getItems().add(separator);
			
			final Parent root = loader.getRoot();
			this.toolBar.getItems().add(root);
			
			return new Bar(root, loader.getController());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a new separate bar to the tool bar and returns reference to it.
	 * 
	 * @param layout	Bar layout resource.
	 * @return	Reference to the Bar object.
	 */
	public Bar addBar(String layout) {
		
		final URL descriptor = Thread.currentThread().getContextClassLoader().getResource(layout);
		return this.addBar(descriptor);
		
	}
	
	/**
	 * @return	Tool bar component.
	 */
	public ToolBar actions() {
		return this.toolBar;
	}

}
