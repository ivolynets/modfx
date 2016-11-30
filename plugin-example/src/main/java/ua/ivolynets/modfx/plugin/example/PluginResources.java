package ua.ivolynets.modfx.plugin.example;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * Example plugin resource bundle.
 * @author Igor Volynets
 */
public class PluginResources {
	
	public static final URL FXML_USERS = Thread.currentThread().getContextClassLoader().getResource("plugins/example/fxml/users.fxml");
	
	public static final Image ICON_USERS = new Image("plugins/example/icons/users.png");
	public static final Image ICON_USER = new Image("plugins/example/icons/user.png");

}
