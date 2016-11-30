package ua.ivolynets.modfx.plugin.example.event;

import java.util.List;

import ua.ivolynets.modfx.event.Event;
import ua.ivolynets.modfx.plugin.example.model.User;

/**
 * Event indicates that users were fetched from the database.
 * @author Igor Volynets
 */
public class UsersFetchedEvent implements Event {
	
	/**
	 * List of users.
	 */
	private List<User> users;

	/**
	 * Constructs event.
	 * @param users	List of users.
	 */
	public UsersFetchedEvent(List<User> users) {
		super();
		this.users = users;
	}

	/**
	 * @return List of users.
	 */
	public List<User> getUsers() {
		return this.users;
	}
	
}
