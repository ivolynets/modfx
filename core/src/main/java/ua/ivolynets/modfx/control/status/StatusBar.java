package ua.ivolynets.modfx.control.status;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ua.ivolynets.modfx.ApplicationResources;
import ua.ivolynets.modfx.control.toolbox.Tool;
import ua.ivolynets.modfx.control.toolbox.ToolBoxController;

/**
 * Application status bar.
 * @author Igor Volynets
 */
public class StatusBar {
	
	/**
	 * Status icon component.
	 */
	private ImageView statusIcon;
	
	/**
	 * Status message component.
	 */
	private Label statusMessage;
	
	/**
	 * Status message controller.
	 */
	private StatusMessageController messageController;
	
	/**
	 * Constructs status bar.
	 * 
	 * @param statusIcon		Status icon component.
	 * @param statusMessage		Status message component.
	 * @param systemToolBox		Reference to the system tool box.
	 */
	public StatusBar(ImageView statusIcon, Label statusMessage, ToolBoxController systemToolBox) {
		
		this.statusIcon = statusIcon;
		this.statusMessage = statusMessage;
		
		Tool messages = systemToolBox.addTool("Messages", ApplicationResources.ICON_MESSAGES, ApplicationResources.FXML_MESSAGES);
		this.messageController = messages.getController();
		
	}
	
	/**
	 * Prints information message to the status bar.
	 * @param message	Message text.
	 */
	public void information(String message) {
		this.message(ApplicationResources.ICON_MESSAGE_INFORMATION, message);
		this.messageController.information(message);
	}
	
	/**
	 * Prints warning message to the status bar.
	 * @param message	Message text.
	 */
	public void warning(String message) {
		this.message(ApplicationResources.ICON_MESSAGE_WARNING, message);
		this.messageController.warning(message);
	}
	
	/**
	 * Prints error message to the status bar.
	 * @param message	Message text.
	 */
	public void error(String message) {
		this.message(ApplicationResources.ICON_MESSAGE_ERROR, message);
		this.messageController.error(message);
	}
	
	/**
	 * Displays message in the status bar.
	 * 
	 * @param icon	Status icon.
	 * @param text	Status message.
	 */
	private void message(final Image icon, final String text) {
		
		Platform.runLater(() -> {
			
			// Display only the first row
			String line = text.split("\n")[0];
			
			statusIcon.setImage(icon);
			statusMessage.setText(line);
			
		});
		
	}

}
