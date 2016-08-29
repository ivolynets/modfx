package ua.ivolynets.modfx.control.window;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.ivolynets.modfx.GuiContext;

/**
 * Class intended for pop-up windows management.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class WindowController {
	
	/**
	 * Application context.
	 */
	private GuiContext context;
	
	/**
	 * Constructs window controller.
	 * @param context	Application context.
	 */
	public WindowController(GuiContext context) {
		this.context = context;
	}
	
	/**
	 * Opens a new pop-up window.
	 * 
	 * @param title		Window title.
	 * @param icon		Window icon.
	 * @param layout	Window layout URL.
	 * @param modal		Whether to open a window in modal mode.
	 * @return	Reference to the window object.
	 */
	public Window openWindow(String title, Image icon, URL layout, boolean modal) {
		
		Stage stage = new Stage();
		
		stage.initOwner(this.context.scene().getWindow());
		stage.initStyle(StageStyle.DECORATED);
		stage.setResizable(false);
		
		if (modal) {
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		
		stage.setTitle(title);
		stage.getIcons().add(icon);
		
		try {
			
			final FXMLLoader loader = new FXMLLoader(layout);
			loader.load();
			
			final Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
			return new Window(stage, root, loader.getController());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Opens a new pop-up window.
	 * 
	 * @param title		Window title.
	 * @param icon		Window icon.
	 * @param layout	Window layout resource.
	 * @param modal		Whether to open a window in modal mode.
	 * @return	Reference to the window object.
	 */
	public Window openWindow(String title, Image icon, String layout, boolean modal) {
		
		final URL descriptor = Thread.currentThread().getContextClassLoader().getResource(layout);
		return this.openWindow(title, icon, descriptor, modal);
		
	}

}
