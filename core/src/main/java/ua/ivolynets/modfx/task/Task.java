package ua.ivolynets.modfx.task;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * Class represents a background task.
 * @author Igor Volynets
 */
public abstract class Task implements Runnable {
	
	/**
	 * Progress bar component.
	 */
	private ProgressBar progressBar = new ProgressBar(ProgressIndicator.INDETERMINATE_PROGRESS);
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void run() {
		
		// Override thread class loader with application class loader
		Thread.currentThread().setContextClassLoader(FXMLLoader.getDefaultClassLoader());
		
		this.execute();
		this.updateProgres(100d);
		
	}
	
	/**
	 * @return	Reference to the progress bar component.
	 */
	public ProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	/**
	 * Task execution entry point.
	 */
	public abstract void execute();
	
	/**
	 * Updates task progress.
	 * @param progress	Current task progress.
	 */
	protected void updateProgres(final double progress) {
		Platform.runLater(() -> { progressBar.setProgress(progress); });
	}

}
