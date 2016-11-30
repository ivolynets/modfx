package ua.ivolynets.modfx.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Controller class of the application preloader.
 * @author Igor Volynets
 */
public class PreloaderController implements Initializable {
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label progressMessage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// no initialization required
	}
	
	/**
	 * Updates loading progress.
	 * @param progress	Current progress.
	 */
	public void progress(double progress) {
		this.progressBar.setProgress(progress);
	}
	
	/**
	 * Updates loading status message.
	 * @param message	Status message.
	 */
	public void message(String message) {
		this.progressMessage.setText(message);
	}

}
