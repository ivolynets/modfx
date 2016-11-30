package ua.ivolynets.modfx;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.ivolynets.modfx.control.PreloaderController;

/**
 * Application preloader.
 * @author Igor Volynets
 */
public class ModfxPreloader extends Preloader {

	/**
	 * Preloader stage.
	 */
	private Stage stage;
	
	/**
	 * Preloader controller.
	 */
	private PreloaderController controller;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage = stage;
		
		try {
			
			final FXMLLoader loader = new FXMLLoader();
			final Parent root = (Parent) loader.load(Objects.requireNonNull(ApplicationResources.FXML_PRELOADER, "Could not load preloader layout").openStream());
			
			this.controller = loader.getController();
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
			this.controller.message("Starting application ...");
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleApplicationNotification(PreloaderNotification preloaderNotification) {
		
		if (preloaderNotification instanceof ProgressNotification) {
			this.controller.progress(((ProgressNotification) preloaderNotification).getProgress());
		}
		
		if (preloaderNotification instanceof MessageNotification) {
			this.controller.message(((MessageNotification) preloaderNotification).getMessage());
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleProgressNotification(ProgressNotification progressNotification) {
		this.controller.progress(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		
		switch (stateChangeNotification.getType()) {
			case BEFORE_INIT:
				this.controller.progress(0d);
				break;
			case BEFORE_START:
				this.stage.hide();
				break;
			case BEFORE_LOAD:
				// do nothing
				break;
		}
		
	}
	
	/**
	 * Preloader notification message.
	 * @author Igor Volynets
	 */
	public static class MessageNotification implements PreloaderNotification {
		
		/**
		 * Notification message.
		 */
		private String message;

		/**
		 * Constructs notification message.
		 * @param message	Notification message.
		 */
		public MessageNotification(String message) {
			this.message = message;
		}

		/**
		 * @return Notification message.
		 */
		public String getMessage() {
			return this.message;
		}
		
	}
	
}
