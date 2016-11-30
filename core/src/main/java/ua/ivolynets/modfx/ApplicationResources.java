package ua.ivolynets.modfx;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * Application resources bundle.
 * @author Igor Volynets
 */
public class ApplicationResources {
	
	public static final URL FXML_APPLICATION = Thread.currentThread().getContextClassLoader().getResource("fxml/application.fxml");
	public static final URL FXML_PRELOADER = Thread.currentThread().getContextClassLoader().getResource("fxml/preloader.fxml");
	public static final URL FXML_MESSAGES = Thread.currentThread().getContextClassLoader().getResource("fxml/messages.fxml");
	public static final URL FXML_TASKS = Thread.currentThread().getContextClassLoader().getResource("fxml/tasks.fxml");
	public static final URL FXML_DIALOG = Thread.currentThread().getContextClassLoader().getResource("fxml/dialog.fxml");
	
	public static final Image ICON_APPLICATION = new Image("icons/modfx.png");
	public static final Image ICON_MESSAGES = new Image("icons/messages.png");
	public static final Image ICON_TASKS = new Image("icons/tasks.png");
	public static final Image ICON_MESSAGE_INFORMATION = new Image("icons/message_information.png");
	public static final Image ICON_MESSAGE_WARNING = new Image("icons/message_warning.png");
	public static final Image ICON_MESSAGE_ERROR = new Image("icons/message_error.png");
	public static final Image ICON_DIALOG_INFORMATION = new Image("icons/dialog_information.png");
	public static final Image ICON_DIALOG_WARNING = new Image("icons/dialog_warning.png");
	public static final Image ICON_DIALOG_ERROR = new Image("icons/dialog_error.png");
	public static final Image ICON_DIALOG_PROMT = new Image("icons/dialog_promt.png");

}
