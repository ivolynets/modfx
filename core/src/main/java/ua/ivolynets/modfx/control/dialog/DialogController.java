package ua.ivolynets.modfx.control.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.ivolynets.modfx.ApplicationResources;
import ua.ivolynets.modfx.GuiContext;

/**
 * Controller of alerts.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class DialogController implements Initializable {
	
	@FXML
	private ImageView icon;
	
	@FXML
	private Label message;
	
	@FXML
	private TextArea details;
	
	@FXML
	private Button positiveButton;
	
	@FXML
	private Button negativeButton;
	
	@FXML
	private Button cancelButton;
	
	/**
	 * Result status of alert window.
	 */
	private DialogStatus status;
	
	/**
	 * Reference to the parent stage.
	 */
	private static Stage stage;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.positiveButton.setOnAction((actionEvent) -> {
			status = DialogStatus.POSITIVE;
			stage.close();
		});
		
		this.negativeButton.setOnAction((actionEvent) -> {
			status = DialogStatus.NEGATIVE;
			stage.close();
		});
		
		this.cancelButton.setOnAction((actionEvent) -> {
			status = DialogStatus.CANCEL;
			stage.close();
		});
		
	}
	
	/**
	 * Performs initialization of dialog controller and returns its instance.
	 * 
	 * @param context	Application context.
	 * @return	Reference to the dialog controller.
	 */
	public static DialogController getInstance(GuiContext context) {
		
		stage = new Stage();
		
		stage.initOwner(context.scene().getWindow());
		stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		
		try {
			
			final FXMLLoader loader = new FXMLLoader(ApplicationResources.FXML_DIALOG);
			loader.load();
			
			final Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			
			DialogController controller = loader.getController();
			stage.setScene(scene);
			
			return controller;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Displays information dialog. This dialog blocks thread and waits until user pressed OK button. It returns no 
	 * result as it's responsible only for displaying significant information and actually not a dialog.
	 * 
	 * @param text	Information message.
	 */
	public void information(String text) {
		this.dialog("Information", ApplicationResources.ICON_DIALOG_INFORMATION, text, null, "Ok", null, null);
	}
	
	/**
	 * Displays warning dialog. This dialog intended for involving user in making decision if something important has 
	 * happened and there is no clarity how to deal. This dialog blocks thread and waits for user's decision. It 
	 * returns result depending on which button was pressed.
	 * 
	 * @param text				Warning message.
	 * @param positiveButton	Positive button label. If {@code null} then button is not displayed.
	 * @param negativeButton	Negative button label. If {@code null} then button is not displayed.
	 * @param cancelButton		Cancel button label. If {@code null} then button is not displayed.
	 * @return	Result depending on user choice.
	 */
	public DialogStatus warning(String text, String positiveButton, String negativeButton, String cancelButton) {
		return this.dialog("Warning", ApplicationResources.ICON_DIALOG_WARNING, text, null, positiveButton, negativeButton, cancelButton);
	}
	
	/**
	 * Displays error dialog. Additionally to error message, it optionally can show details of the error. This dialog 
	 * blocks thread and waits until user pressed OK button. It returns no result as it's responsible only for 
	 * displaying information about error occurred and actually not a dialog.
	 * 
	 * @param text		Error message.
	 * @param details	Error details.
	 */
	public void error(String text, String details) {
		this.dialog("Error", ApplicationResources.ICON_DIALOG_ERROR, text, details, "Ok", null, null);
	}
	
	/**
	 * Displays a question dialog. It behaves similarly to warning dialog with only difference, it provides choice only
	 * between two options, like "to do or not to do". This dialog blocks thread and waits for user's decision. It 
	 * returns result depending on which button was pressed.
	 * 
	 * @param text				Text of question.
	 * @param positiveButton	Positive button label. If {@code null} then button is not displayed.
	 * @param negativeButton	Negative button label. If {@code null} then button is not displayed.
	 * @return	Result depending on user choice.
	 */
	public DialogStatus promt(String text, String positiveButton, String negativeButton) {
		return this.dialog("Promt", ApplicationResources.ICON_DIALOG_PROMT, text, null, positiveButton, negativeButton, null);
	}
	
	/**
	 * Shows dialog window.
	 * 
	 * @param title				Window title.
	 * @param icon				Window icon.
	 * @param message			Dialog message.
	 * @param details			Dialog details.
	 * @param positiveButton	Positive button label. If {@code null} then button is not displayed.
	 * @param negativeButton	Negative button label. If {@code null} then button is not displayed.
	 * @param cancelButton		Cancel button label. If {@code null} then button is not displayed.
	 * @return	Dialog result status.
	 */
	private DialogStatus dialog(String title, Image icon, String message, String details, String positiveButton, String negativeButton, String cancelButton) {
		
		stage.setTitle(title);
		this.icon.setImage(icon);
		this.message.setText(message);
		
		if (details != null && ! details.isEmpty()) {
			this.details.setText(details);
			this.details.setVisible(true);
			this.details.setManaged(true);
		} else {
			this.details.setText(null);
			this.details.setVisible(false);
			this.details.setManaged(false);
		}
		
		if (positiveButton != null && ! positiveButton.isEmpty()) {
			this.positiveButton.setText(positiveButton);
			this.positiveButton.setVisible(true);
			this.positiveButton.setManaged(true);
		} else {
			this.positiveButton.setText(null);
			this.positiveButton.setVisible(false);
			this.positiveButton.setManaged(false);
		}
		
		if (negativeButton != null && ! negativeButton.isEmpty()) {
			this.negativeButton.setText(negativeButton);
			this.negativeButton.setVisible(true);
			this.negativeButton.setManaged(true);
		} else {
			this.negativeButton.setText(null);
			this.negativeButton.setVisible(false);
			this.negativeButton.setManaged(false);
		}
		
		if (cancelButton != null && ! cancelButton.isEmpty()) {
			this.cancelButton.setText(cancelButton);
			this.cancelButton.setVisible(true);
			this.cancelButton.setManaged(true);
		} else {
			this.cancelButton.setText(null);
			this.cancelButton.setVisible(false);
			this.cancelButton.setManaged(false);
		}
		
		stage.showAndWait();
		return this.status;
		
	}

}
