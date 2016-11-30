package ua.ivolynets.modfx.db;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.h2.jdbcx.JdbcDataSource;

/**
 * Local database manager.
 * @author Igor Volynets
 */
public class DbManager {

	/**
	 * Database connection.
	 */
	private Connection connection;
	
	/**
	 * Constructs database manager. It creates database in the given namespace if one does not exist yet. If database 
	 * already exists then constructor simply creates connection to it.
	 * 
	 * @param namespace	Database namespace.
	 */
	public DbManager(String namespace) {
		
		final JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:~/.modfx/db/" + namespace);
		
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates table in the database for a given data type if it does not exist yet.
	 * 
	 * @param dataType	Data type.
	 * @throws SQLException	if error occurred.
	 */
	public void create(Class<?> dataType) throws SQLException {
		
		if (dataType == null) throw new IllegalArgumentException("Data type cannot be null");
		
		// Build query
		
		final StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + dataType.getSimpleName() + "` (");
		
		final Field[] fields = dataType.getDeclaredFields();
		Field field;
		String name, type;
		
		for (int i = 0; i < fields.length; i++) {
			
			field = fields[i];
			
			// Skip transient fields
			if (field.isAnnotationPresent(Transient.class)) continue;
			
			name = field.getName();
			type = this.determineType(field);
			
			if (i > 0) query.append(", ");
			
			query.append("`").append(name).append("` ").append(type);
			if (field.isAnnotationPresent(Key.class)) query.append(" PRIMARY KEY");
			
		}
		
		query.append(")");
		
		// Execute query
		
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(query.toString());
		
	}
	
	/**
	 * Puts object into the database. If object already exists (key matched) then it will be updated.
	 * 
	 * @param data	Data object.
	 * @param <T>	Data type.
	 * @throws SQLException	if error occurred.
	 * @see	#delete(Object)
	 */
	public <T> void put(T data) throws SQLException {
		
		if (data == null) throw new IllegalArgumentException("Data object cannot be null");
		
		// Build query
		
		final StringBuilder query = new StringBuilder("MERGE INTO `" + data.getClass().getSimpleName() + "` (");
		final StringBuilder placeholders = new StringBuilder();
		final List<String> keyFields = new ArrayList<>();
		
		Field field;
		String name;
		Object value;
		
		final Field[] declaredFields = data.getClass().getDeclaredFields();
		
		final List<Field> fields = new ArrayList<>();
		final List<Object> values = new ArrayList<>();
		
		for (Field declaredField : declaredFields) {
			
			field = declaredField;
			name = field.getName();
			
			// Skip transient fields
			if (field.isAnnotationPresent(Transient.class)) continue;
			
			if ( ! field.isAnnotationPresent(Identity.class)) {
				
				if (fields.size() > 0) {
					query.append(", ");
					placeholders.append(", ");
				}
				
				query.append("`").append(name).append("`");
				placeholders.append("?");
				
				fields.add(field);
				
				try {
					
					boolean accessible = field.isAccessible();
					if ( ! accessible) field.setAccessible(true);
					value = field.get(data);
					values.add(value);
					field.setAccessible(accessible);
					
				} catch (IllegalAccessException e) {
					
					values.add(null);
					e.printStackTrace();
					
				}
				
			}
			
			if (field.isAnnotationPresent(Key.class)) {
				keyFields.add(field.getName());
			}
			
		}
		
		query.append(")");
		
		if (keyFields.size() > 0) {
			
			query.append(" KEY (");
			
			for (int i = 0; i < keyFields.size(); i++) {
				if (i > 0) query.append(", ");
				query.append("`").append(keyFields.get(i)).append("`");
			}
			
			query.append(")");
			
		}
		
		query.append(" VALUES (").append(placeholders).append(")");
		
		// Execute query
		
		final PreparedStatement statement = this.connection.prepareStatement(query.toString());
		this.populateStatement(statement, fields, values);
		
		statement.executeUpdate();
		
	}
	
	/**
	 * Deletes object from the database.
	 * 
	 * @param data	Data object.
	 * @param <T>	Data type.
	 * @throws SQLException	if error occurred.
	 * @see	#put(Object)
	 */
	public <T> void delete(T data) throws SQLException {
		
		if (data == null) throw new IllegalArgumentException("Data object cannot be null");
		
		// Build query
		
		final StringBuilder query = new StringBuilder("DELETE FROM `" + data.getClass().getSimpleName() + "`");
		
		final Field[] fields = this.determineKeyFields(data.getClass());
		final Object[] values = new Object[fields.length];
		
		query.append(" WHERE ");
		
		Field field;
		String name;
		Object value;
		
		for (int i = 0; i < fields.length; i++) {
			
			if (i > 0) query.append(" AND ");
			
			field = fields[i];
			name = field.getName();
			
			query.append("`").append(name).append("` = ?");
			
			try {
				
				boolean accessible = field.isAccessible();
				if ( ! accessible) field.setAccessible(true);
				value = field.get(data);
				values[i] = value;
				field.setAccessible(accessible);
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		
		// Execute query
		
		final PreparedStatement statement = this.connection.prepareStatement(query.toString());
		this.populateStatement(statement, Arrays.asList(fields), Arrays.asList(values));
		
		statement.executeUpdate();
		
	}
	
	/**
	 * Lists all records of a given data type.
	 * 
	 * @param dataType	Data type class object.
	 * @param <T>		Data type.
	 * @return	List of all records of a given type.
	 * @throws SQLException if error occurred.
	 * @see	#filter(Class, Map)
	 * @see	#find(Class, Object...)
	 */
	public <T> List<T> list(Class<T> dataType) throws SQLException {
		
		if (dataType == null) throw new IllegalArgumentException("Data type cannot be null");
		
		final String query = "SELECT * FROM `" + dataType.getSimpleName() + "`";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		
		return this.parse(result, dataType);
		
	}
	
	/**
	 * Lists records of a given data type filtered by given criteria.
	 * 
	 * @param dataType	Data type class object.
	 * @param criteria	Filter criteria.
	 * @param <T>		Data type.
	 * @return	List of filtered records of a given type.
	 * @throws SQLException	if error occurred.
	 * @see	#list(Class)
	 * @see	#find(Class, Object...)
	 */
	public <T> List<T> filter(Class<T> dataType, Map<String, Object> criteria) throws SQLException {
		
		if (dataType == null) throw new IllegalArgumentException("Data type cannot be null");
		
		// Build query
		
		final StringBuilder query = new StringBuilder("SELECT * FROM `" + dataType.getSimpleName() + "`");
		final List<Field> fields = new ArrayList<>();
		
		if (criteria != null && ! criteria.isEmpty()) {
			
			query.append(" WHERE ");
			boolean first = true;
			
			for (String field : criteria.keySet()) {
				
				if ( ! first) query.append(" AND ");
				else first = false;
				
				query.append("`").append(field).append("` = ?");
				
				try {
					
					Field declaredField = dataType.getDeclaredField(field);
					
					// Check if field is transient
					if ( ! declaredField.isAnnotationPresent(Transient.class)) fields.add(declaredField);
					else throw new NoSuchFieldException("Field " + field + " is transient and cannot be used as criteria");
					
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
					throw new SQLException(e);
				}
				
			}
			
		}
		
		// Execute query
		
		final PreparedStatement statement = this.connection.prepareStatement(query.toString());
		if (criteria != null && ! criteria.isEmpty()) this.populateStatement(statement, fields, new ArrayList<>(criteria.values()));
		
		final ResultSet result = statement.executeQuery();
		return this.parse(result, dataType);
		
	}
	
	/**
	 * Finds a record of a given type for a given key values.
	 * 
	 * @param dataType	Data type class object.
	 * @param key		Key values.
	 * @param <T>		Data type.
	 * @return	Record instance.
	 * @throws SQLException	if error occurred.
	 * @see	#list(Class)
	 * @see	#filter(Class, Map)
	 */
	public <T> T find(Class<T> dataType, Object ... key) throws SQLException {
		
		if (dataType == null) throw new IllegalArgumentException("Data type cannot be null");
		if (key.length == 0) throw new IllegalArgumentException("Key must be provided");
		
		// Build key
		
		final Field[] fields = this.determineKeyFields(dataType);
		final Map<String, Object> criteria = new HashMap<>();
		
		for (int i = 0; i < fields.length; i++) {
			criteria.put(fields[i].getName(), key[i]);
		}
		
		// Execute query
		
		final List<T> list = this.filter(dataType, criteria);
		
		if ( ! list.isEmpty()) return list.get(0);
		else return null;
		
	}
	
	/**
	 * Determines SQL type of field corresponding to field Java type and returns its string representation.
	 * 
	 * @param field	Field object.
	 * @return	SQL type.
	 * @throws SQLException	if field type is not supported.
	 */
	private String determineType(Field field) throws SQLException {
		
		// TODO: cover this method by unit tests, it may contain issues
		
		if (Boolean.TYPE.equals(field.getType()) || Boolean.class.equals(field.getType())) return "BOOLEAN";
		
		if (Integer.TYPE.equals(field.getType()) || Integer.class.equals(field.getType())) return "INT";
		if (Byte.TYPE.equals(field.getType()) || Byte.class.equals(field.getType())) return "TINYINT";
		if (Short.TYPE.equals(field.getType()) || Short.class.equals(field.getType())) return "SMALLINT";
		
		if (Long.TYPE.equals(field.getType()) || Long.class.equals(field.getType())) {
			if (field.isAnnotationPresent(Identity.class)) return "IDENTITY";
			return "BIGINT";
		}
		
		if (BigDecimal.class.equals(field.getType())) return "DECIMAL";
		if (Double.TYPE.equals(field.getType()) || Double.class.equals(field.getType())) return "DOUBLE";
		if (Float.TYPE.equals(field.getType()) || Float.class.equals(field.getType())) return "REAL";
		
		if (String.class.equals(field.getType())) return "VARCHAR";
		if (Character.TYPE.equals(field.getType()) || Character.class.equals(field.getType())) return "CHAR";
		
		if (InputStream.class.equals(field.getType())) return "BLOB";
		if (Reader.class.equals(field.getType())) return "CLOB";
		
		if (Byte[].class.equals(field.getType()) || byte[].class.equals(field.getType())) return "BINARY";
		if (UUID.class.equals(field.getType())) return "UUID";
		if (Object.class.equals(field.getType())) return "OTHER";
		
		if (Calendar.class.equals(field.getType()) || java.util.Date.class.equals(field.getType())) {
			if (field.isAnnotationPresent(Time.class)) return "TIME";
			if (field.isAnnotationPresent(Date.class)) return "DATE";
			return "TIMESTAMP";
		}
		
		if (field.getType().isArray()) return "ARRAY";
		
		throw new SQLException("Field type " + field.getType().getSimpleName() + " is not supported");
		
	}
	
	/**
	 * Parses result set and returns a list of instances of a given data type which represents a row.
	 * 
	 * @param result	Result set.
	 * @param dataType	Data type class object.
	 * @param <T>		Data type.
	 * @return	Result list of instances of a given data type.
	 * @throws SQLException	if error occurred.
	 */
	private <T> List<T> parse(ResultSet result, Class<T> dataType) throws SQLException {
		
		final List<T> list = new ArrayList<>();
		
		while (result.next()) {
			list.add(this.parseRow(result, dataType));
		}
		
		return list;
		
	}
	
	/**
	 * Parses result set and returns a single instance of a given data type which represents a row.
	 * 
	 * @param result	Result set.
	 * @param dataType	Data type class object.
	 * @param <T>		Data type.
	 * @return	Result instance of a given data type.
	 * @throws SQLException	if error occurred.
	 */
	private <T> T parseRow(ResultSet result, Class<T> dataType) throws SQLException {
		
		T instance = null;
		
		try {
			
			instance = dataType.newInstance();
			
			Object value;
			int index = 1;
			
			Field[] fields = dataType.getDeclaredFields();
			
			for (Field field : fields) {
				
				// Skip transient fields
				if (field.isAnnotationPresent(Transient.class)) continue;
				
				value = result.getObject(index);
				
				boolean accessible = field.isAccessible(); 
				if ( ! accessible) field.setAccessible(true);
				field.set(instance, value);
				field.setAccessible(accessible);
				
				index++;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instance;
		
	}
	
	/**
	 * Populates prepared statement with values of corresponding types.
	 * 
	 * @param statement	Prepared statement.
	 * @param fields	List of fields.
	 * @param values	List of values.
	 * @throws SQLException	if field type is not supported.
	 */
	private void populateStatement(PreparedStatement statement, List<Field> fields, List<Object> values) throws SQLException {
		
		// TODO: cover this method by unit tests, it contains issues
		
		String type;
		
		for (int i = 0; i < fields.size(); i++) {
			
			type = this.determineType(fields.get(i));
			
			switch (type) {
				case "BOOLEAN":
					if (values.get(i) != null) statement.setBoolean(i + 1, (Boolean) values.get(i));
					else statement.setNull(i + 1, Types.BOOLEAN);
					break;
				case "INT":
					if (values.get(i) != null) statement.setInt(i + 1, (Integer) values.get(i));
					else statement.setNull(i + 1, Types.INTEGER);
					break;
				case "TINYINT":
					if (values.get(i) != null) statement.setByte(i + 1, (Byte) values.get(i));
					else statement.setNull(i + 1, Types.BOOLEAN);
					break;
				case "SMALLINT":
					if (values.get(i) != null) statement.setShort(i + 1, (Short) values.get(i));
					else statement.setNull(i + 1, Types.SMALLINT);
					break;
				case "IDENTITY":
				case "BIGINT":
					if (values.get(i) != null) statement.setLong(i + 1, (Long) values.get(i));
					else statement.setNull(i + 1, Types.BIGINT);
					break;
				case "DECIMAL":
					if (values.get(i) != null) statement.setBigDecimal(i + 1, (BigDecimal) values.get(i));
					else statement.setNull(i + 1, Types.DECIMAL);
					break;
				case "DOUBLE":
					if (values.get(i) != null) statement.setDouble(i + 1, (Double) values.get(i));
					else statement.setNull(i + 1, Types.DOUBLE);
					break;
				case "REAL":
					if (values.get(i) != null) statement.setFloat(i + 1, (Float) values.get(i));
					else statement.setNull(i + 1, Types.REAL);
					break;
				case "VARCHAR":
					if (values.get(i) != null) statement.setString(i + 1, (String) values.get(i));
					else statement.setNull(i + 1, Types.VARCHAR);
					break;
				case "CHAR":
					if (values.get(i) != null) statement.setString(i + 1, (String) values.get(i));
					else statement.setNull(i + 1, Types.CHAR);
					break;
				case "BLOB":
					if (values.get(i) != null) statement.setBlob(i + 1, (InputStream) values.get(i));
					else statement.setNull(i + 1, Types.BLOB);
					break;
				case "CLOB":
					if (values.get(i) != null) statement.setClob(i + 1, (Reader) values.get(i));
					else statement.setNull(i + 1, Types.CLOB);
					break;
				case "BINARY":
					if (values.get(i) != null) statement.setBytes(i + 1, (byte[]) values.get(i));
					else statement.setNull(i + 1, Types.BINARY);
					break;
				case "UUID":
				case "OTHER":
					if (values.get(i) != null) statement.setObject(i + 1, values.get(i));
					else statement.setNull(i + 1, Types.OTHER);
					break;
				case "TIME":
					if (values.get(i) != null) statement.setTime(i + 1, new java.sql.Time(((Calendar) values.get(i)).getTimeInMillis()));
					else statement.setNull(i + 1, Types.TIME);
					break;
				case "DATE":
					if (values.get(i) != null) statement.setDate(i + 1, new java.sql.Date(((Calendar) values.get(i)).getTimeInMillis()));
					else statement.setNull(i + 1, Types.DATE);
					break;
				case "TIMESTAMP":
					if (values.get(i) != null) statement.setTimestamp(i + 1, new java.sql.Timestamp(((Calendar) values.get(i)).getTimeInMillis()));
					else statement.setNull(i + 1, Types.TIMESTAMP);
					break;
				case "ARRAY":
					if (values.get(i) != null) statement.setArray(i + 1, (Array) values.get(i));
					else statement.setNull(i + 1, Types.ARRAY);
					break;
				default:
					throw new SQLException("Field type " + type + " is not supported");
			}
			
		}
		
	}
	
	/**
	 * Determines all key fields for a given data type and returns them as array.
	 * 
	 * @param dataType	Data type class object.
	 * @return	Array of key fields.
	 */
	private Field[] determineKeyFields(Class<?> dataType) {
		
		final List<Field> result = new ArrayList<>();
		final Field[] fields = dataType.getDeclaredFields();
		
		for (Field field : fields) {
			if (field.isAnnotationPresent(Key.class)) result.add(field);
		}
		
		return result.toArray(new Field[result.size()]);
		
	}
	
}
