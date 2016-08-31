package ua.ivolynets.modfx.plugin.example;

import java.sql.SQLException;

import ua.ivolynets.modfx.GuiContext;
import ua.ivolynets.modfx.control.toolbox.Tool;
import ua.ivolynets.modfx.plugin.ModfxPlugin;
import ua.ivolynets.modfx.plugin.example.controller.UsersController;
import ua.ivolynets.modfx.plugin.example.model.User;

/**
 * Example plugin main class.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class ExamplePlugin implements ModfxPlugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String name() {
		return "Example Plugin";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String version() {
		return "0.1";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(GuiContext context) {
		
		// Init database
		
		try {
			
			context.db().create(User.class);
			
			User user = new User();
			user.setEmail("john.doe@example.com");
			user.setFirstName("John");
			user.setLastName("Doe");
			
			context.db().put(user);
			
			user = new User();
			user.setEmail("jane.doe@example.com");
			user.setFirstName("Jane");
			user.setLastName("Doe");
			
			context.db().put(user);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		// Init controls
		
		Tool users = context.controls().leftToolBox().addTool("Users", PluginResources.ICON_USERS, PluginResources.FXML_USERS);
		
		UsersController controller = users.getController();
		controller.init(context);
		
	}
	
}
