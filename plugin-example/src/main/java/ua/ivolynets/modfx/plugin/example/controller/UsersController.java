package ua.ivolynets.modfx.plugin.example.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import ua.ivolynets.modfx.GuiContext;
import ua.ivolynets.modfx.event.EventListener;
import ua.ivolynets.modfx.plugin.example.PluginResources;
import ua.ivolynets.modfx.plugin.example.event.UsersFetchedEvent;
import ua.ivolynets.modfx.plugin.example.model.User;
import ua.ivolynets.modfx.task.Task;

/**
 * Controller class for users component.
 * @author Igor Volynets
 */
public class UsersController implements Initializable {

	@FXML
	private ListView<User> userList;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			
			@Override
			public ListCell<User> call(ListView<User> param) {
				return new ListCell<User>() {

					@Override
					protected void updateItem(User user, boolean empty) {
						
						super.updateItem(user, empty);
						
						if ( ! empty && user != null) {
							this.setText(user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
							this.setGraphic(new ImageView(PluginResources.ICON_USER));
						}
						
					}
					
				};
			}
			
		});
		
	}
	
	/**
	 * Performs explicit initialization of the users component.
	 * @param context	Application context.
	 */
	public void init(final GuiContext context) {
		
		// Add event listeners
		
		context.eventService().listen(UsersFetchedEvent.class, (EventListener<UsersFetchedEvent>) (event) -> {
			
			Platform.runLater(() -> {
				userList.getItems().addAll(event.getUsers());
			});
			
		});
		
		// Fetch users in the background
		
		context.taskService().execute("Fetch users", new Task() {
			
			@Override
			public void execute() {
				
				context.controls().statusBar().information("Fetching users");
				
				try {
					
					List<User> users = context.db().list(User.class);
					context.eventService().notify(new UsersFetchedEvent(users));
					
					context.controls().statusBar().information("Users fetched");
					
				} catch (SQLException e) {
					context.controls().statusBar().error("Error fetching users");
				}
				
			}
			
		});
		
	}
	
}
