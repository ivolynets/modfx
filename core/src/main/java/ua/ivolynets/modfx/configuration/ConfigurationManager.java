package ua.ivolynets.modfx.configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.h2.jdbcx.JdbcDataSource;

/**
 * Application configuration manager based on the H2 local database.
 * @author Igor Volynets <ig.volynets@gmail.com>
 */
public class ConfigurationManager {
	
	/**
	 * Database connection.
	 */
	private Connection connection;
	
	/**
	 * Constructs connection manager.
	 * @param namespace	Configuration namespace.
	 */
	public ConfigurationManager(String namespace) {
		
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:~/.modfx/db/" + namespace);
		
		try {
			
			this.connection = dataSource.getConnection();
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS configuration (" + 
						"key    VARCHAR(255) PRIMARY KEY, " + 
						"value  VARCHAR(255)" + 
					")"
			);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Puts new configuration value or overwrites existing one.
	 * 
	 * @param key		Configuration key.
	 * @param value		Configuration value.
	 */
	public void put(String key, String value) {
		
		if (key == null || key.isEmpty()) throw new IllegalArgumentException("Key cannot be null or empty");
		
		try {
			
			PreparedStatement statement = this.connection.prepareStatement(
					"MERGE INTO configuration (key, value) " +
					"KEY (key) " +
					"VALUES (?, ?)"
			);
			
			statement.setString(1, key);
			statement.setString(2, value);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets configuration value by its key.
	 * 
	 * @param key	Configuration key.
	 * @return	Configuration value or {@code null} if value does not exist.
	 * @see	#find(String)
	 */
	public String get(String key) {
		
		if (key == null || key.isEmpty()) throw new IllegalArgumentException("Key cannot be null or empty");
		
		try {
			
			PreparedStatement statement = this.connection.prepareStatement(
					"SELECT value " +
					"FROM configuration " +
					"WHERE key = ?"
			);
			
			statement.setString(1, key);
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				return result.getString("value");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Trying to find all configuration keys which start from a given prefix with their values.
	 * 
	 * @param prefix	Configuration key prefix.
	 * @return	Map of key-value pairs. If nothing found then empty map is returned.
	 * @see	#get(String)
	 */
	public Map<String, String> find(String prefix) {
		
		if (prefix == null || prefix.isEmpty()) throw new IllegalArgumentException("Prefix cannot be null or empty");
		
		Map<String, String> config = new HashMap<>();
		
		try {
			
			PreparedStatement statement = this.connection.prepareStatement(
					"SELECT key, value " +
					"FROM configuration " +
					"WHERE key LIKE ?"
			);
			
			statement.setString(1, prefix + "%");
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				config.put(result.getString("key"), result.getString("value"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return config;
		
	}

}
