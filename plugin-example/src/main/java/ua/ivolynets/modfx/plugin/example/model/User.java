package ua.ivolynets.modfx.plugin.example.model;

import ua.ivolynets.modfx.db.Key;

/**
 * Model class represents a user.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class User {

	/**
	 * User email.
	 */
	@Key
	private String email;
	
	/**
	 * User first name.
	 */
	private String firstName;
	
	/**
	 * User last name.
	 */
	private String lastName;

	/**
	 * @return User email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email User email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return User first name.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @param firstName User first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return User last name.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @param lastName User last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
