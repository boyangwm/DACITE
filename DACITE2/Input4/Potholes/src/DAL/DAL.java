package DAL;

import Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.sql.PreparedStatement;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang.StringEscapeUtils;

public class DAL {
	/** The Singleton object */
	private static DAL dal;
	
	/**
	 * Gets the Singleton, creating a new one if one did not exist
	 * @return The Singleton
	 */
	public static DAL get() {
		if (dal == null)
			dal = new DAL();
		
		return dal;
	}
	
	/** The connection string for the DB */
	//protected String connectionString = "jdbc:mysql://coe9000.ed.uic.edu:3306/imdb";
	protected String connectionString = "jdbc:mysql://localhost:3306/imdb";
	
	/** The database name */
	//protected String dbname = "imdb";
	protected String dbname = "imdb";
	
	
	/** The user-name for the database
	 * 	If this was for real, we would read in from a configuration file, 
	 *  but we are constrained to no file I/O for this project. 
	 */
	//String user = "guest";
	String user = "root";
	
	/** The password for the database.  
	 *  If this was for real, we would read in from a configuration file, 
	 *  but we are constrained to no file I/O for this project.
	 */
	//String pass = "123456";
	String pass = "boyang";
	
	/** The database driver string */
	static final String driver = "com.mysql.jdbc.Driver";
	
	/** The database connection */
	Connection conn = null;
	
	/** Default constructor */
	protected DAL() {
		
	}
	
	/** Cleans up SQL Connection. Only call at application exit! */
	public boolean closeDAL() {
		boolean succeeded = true; 
				
		// Close the connection
		if (conn != null) {
			try {
				conn.close();
			}
			catch (Exception ex){
				succeeded = false;
			}
		}
		else {
			succeeded = false;
		}
		
		conn = null;
		
		return succeeded;
	}
	
	/**
	 * Creates an account from an account object
	 * @param account
	 */
	public Account createAccount(@NotNull Account account) {
		account = createAccount(account.getFirstName(), 
							 account.getMiddleName(), 
							 account.getLastName(),
							 account.getAddress1(),
							 account.getAddress2(),
							 account.getCity(),
							 account.getState(),
							 account.getZipCode(),
							 account.getUsername(),
							 account.getPassword());
		
		return account;
	}
	
	/**
	 * Creates an account in the database
	 * @param firstName The person's first name
	 * @param middleName The person's middle name
	 * @param lastName The person's last name
	 * @param address1 The first part of the person's address
	 * @param address2 The second part of the person's address
	 * @param city The person's city
	 * @param state The person's state
	 * @param zipCode The person's zip code
	 * @param username The username
	 * @param password The password
	 * @return The created Account
	 */
	public Account createAccount(String firstName, String middleName, String lastName, 
			String address1, String address2, 
			String city, String state, String zipCode, 
			String username, String password) {
		Account account = null;
		
		@DBAnnotation (
			attribute = {"first_name", "middle_name", "last_name", 
					"address_1", "address_2", "city", "state", "zip_code",
					"username", "password"},
			table = {"accounts", "accounts", "accounts",
					"accounts", "accounts", "accounts", "accounts", "accounts",
					"accounts", "accounts"},
			variable = {"firstName", "middleName", "lastName", 
					"address1", "address2", "city", "state", "zipCode", 
					"username", "password"},
			isSource = {false, false, false,
					false, false, false, false, false,
					false, false}
				)
		
		String sql = 
			"INSERT INTO imdb.accounts\n" + 
			"(first_name,\n" + 
			"middle_name,\n" + 
			"last_name,\n" + 
			"address_1,\n" + 
			"address_2,\n" + 
			"city,\n" + 
			"state,\n" + 
			"zip_code,\n" + 
			"username,\n" + 
			"password)\n" + 
			"VALUES\n" + 
			"(\n" + 
			"'" + StringEscapeUtils.escapeSql(firstName) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(middleName) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(lastName) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(address1) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(address2) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(city) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(state) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(zipCode) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(username) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(password) + "')";
	
		try {
			int id = executeUpdateStatement(sql);

			if (id > 0)
			{
				account = new Account(id, 
						firstName, 
						middleName, 
						lastName, 
						address1, 
						address2, 
						city, 
						state, 
						zipCode, 
						username, 
						password);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return account;
	}

	/**
	 * Creates a favorite record in the database
	 * @param accountId The id of the account
	 * @param movieId The id of the movie
	 * @return The created id, or -1 if failure
	 */
	public int createFavorite(@NotNull Integer accountId, 
			@NotNull Integer movieId) {
		
		@DBAnnotation (
				attribute = {"accounts_id", "movies_id"},
				table = {"favorites", "favorites"},
				variable = {"accountID", "movieId"},
				isSource = {false, false}
				)

		String sql = "INSERT INTO imdb.favorites\n" + 
				"(\n" + 
				"movies_id,\n" + 
				"accounts_id)\n" + 
				"VALUES\n" + 
				"(\n" + 
				"" + movieId + ",\n" + 
				"" + accountId + ")";
		int id = -1;
		
		try {
			id = executeUpdateStatement(sql);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Creates an order
	 * @param paymentMethodId The ID of the payment method
	 * @param orderTypeId The ID of the order type
	 * @param accountId The ID of the account
	 * @param movieId The ID of the movie
	 * @return The created order
	 */
	public Order createOrder(@NotNull Integer paymentMethodId, @NotNull Integer orderTypeId, 
			@NotNull Integer accountId, @NotNull Integer movieId) {
		Calendar calendar = new GregorianCalendar();
		Integer second = calendar.get(Calendar.SECOND);
		Integer minute = calendar.get(Calendar.MINUTE);
		Integer hour = calendar.get(Calendar.HOUR);
		Integer date = calendar.get(Calendar.DATE);
		Integer month = calendar.get(Calendar.MONTH);
		Integer year = calendar.get(Calendar.YEAR);
		
		Order order = null;
		int id = -1;
		
		@DBAnnotation (
				attribute = {"payment_methods_id", "order_types_id", "accounts_id", "movies_id",
						"transaction_second", "transaction_minute", "transaction_hour", 
						"transaction_day", "transaction_month", "transaction_year"},
				table = {"orders", "orders", "orders", "orders",
						"orders", "orders", "orders",
						"orders", "orders", "orders"},
				variable = {"paymentMethodId", "orderTypeId", "accountId", "movieId",
						"second", "minute", "hour",
						"date", "month", "year"},
				isSource = {false, false, false, false, 
						false, false, false, 
						false, false, false}
				)
		
		String sql = 
			"INSERT INTO imdb.orders\n" + 
			"(\n" + 
			"payment_methods_id,\n" + 
			"order_types_id,\n" + 
			"accounts_id,\n" + 
			"movies_id,\n" + 
			"transaction_second,\n" + 
			"transaction_minute,\n" + 
			"transaction_hour,\n" + 
			"transaction_day,\n" + 
			"transaction_month,\n" + 
			"transaction_year)\n" + 
			"VALUES\n" + 
			"(\n" + 
			"" + paymentMethodId + ",\n" + 
			"" + orderTypeId + ",\n" + 
			"" + accountId + ",\n" + 
			"" + movieId + ",\n" + 
			"" + second + ",\n" + 
			"" + minute + ",\n" + 
			"" + hour + ",\n" + 
			"" + date + ",\n" + 
			"" + month + ",\n" + 
			"" + year + ")\n";
		
		try {
			//Executes the query
			id = this.executeUpdateStatement(sql);
			
			if (id > 0) {
				// This gets the generated key for the inserted record and creates a 
				// Order from it.
				order = new Order(id,
						paymentMethodId,
						orderTypeId,
						accountId,
						movieId,
						second,
						minute,
						hour,
						date,
						month,
						year);
	        }
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return order;
	}
	
	/**
	 * Creates a payment method
	 * @param accountId The account id
	 * @param creditCardTypeId The credit card type id
	 * @param creditCardNumber The credit card number
	 * @param expirationMonth The expiration month
	 * @param expirationYear The expiration year
	 * @param cvv The CVV
	 * @param nameOnCard The name on the card
	 * @param zipCode The zip code
	 * @return The created payment method
	 */
	public PaymentMethod createPaymentMethod(@NotNull Integer accountId,
			@NotNull Integer creditCardTypeId,
			@NotNull String creditCardNumber, @NotNull Integer expirationMonth,
			@NotNull Integer expirationYear, @NotNull Integer cvv, 
			@NotNull String nameOnCard, @NotNull String zipCode) {
		PaymentMethod method = null;
		
		@DBAnnotation (
				attribute = {"account_id", "credit_card_types_id", "credit_card_number", 
						"expiration_month", "expiration_year", "cvv",
						"name_on_card", "zip_code"},
				table = {"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods"},
				variable = {"accountID", "creditCardTypeId", "creditCardNumber",
						"expirationMonth", "expirationYear", "cvv",
						"nameOnCard", "zipCode"},
				isSource = {false, false, false, false, 
						false, false, false, 
						false, false}
				)
		
		String sql = 
			"INSERT INTO imdb.payment_methods\n" + 
			"(\n" + 
			"account_id,\n" + 
			"credit_card_types_id,\n" + 
			"credit_card_number,\n" + 
			"expiration_month,\n" + 
			"expiration_year,\n" + 
			"cvv,\n" + 
			"name_on_card,\n" + 
			"zip_code)\n" + 
			"VALUES\n" + 
			"(\n" + 
			"" + accountId + ",\n" + 
			"" + creditCardTypeId + ",\n" + 
			"'" + StringEscapeUtils.escapeSql(creditCardNumber) + "',\n" + 
			"" + expirationMonth + ",\n" + 
			"" + expirationYear + ",\n" + 
			"" + cvv + ",\n" + 
			"'" + StringEscapeUtils.escapeSql(nameOnCard) + "',\n" + 
			"'" + StringEscapeUtils.escapeSql(zipCode) + "')";
				
		try {
			int id = executeUpdateStatement(sql);
			if (id > 0)
			{
				method = new PaymentMethod(id,
										   accountId,
										   creditCardTypeId,
										   creditCardNumber,
										   expirationMonth,
										   expirationYear,
										   cvv,
										   nameOnCard,
										   zipCode);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
				
		return method;
	}
	
	/**
	 * Executes a SQL query.  The query should be sanitized before calling this method.
	 * @param sql The SQL statement. 
	 * @return A ResultSet containing the results of the query.
	 * @throws SQLException
	 */
	private ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = null;
		PreparedStatement statement = this.getStatement(sql);
		
		if (statement != null) {
			try {
					rs = statement.executeQuery();
				}
			catch (Exception ex) {
				System.out.println(sql);
				ex.printStackTrace();
			}
		}
		return rs;
	}
	
	/**
	 * Executes an insert statement.  The statement should be sanitized before 
	 * calling this method.
	 * @param sql The SQL statement
	 * @return The id of the inserted record
	 * @throws SQLException
	 */
	private int executeUpdateStatement(String sql) throws SQLException {
		Statement statement = this.getStatement(sql);
		int insertedID = -1;
		
		if (statement != null) {
			try {
				int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				
				if (affectedRows == 0) {
					throw new SQLException("Update failed.");
				}
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					insertedID = generatedKeys.getInt(1);
				}
			}
			catch (Exception ex) {
				System.out.println(sql);
				ex.printStackTrace();
			}
		}
		return insertedID;
	}

	/**
	 * Gets an account from an account ID
	 * @param accountId The ID of the account
	 * @return The account object
	 */
	public Account getAccount(@NotNull Integer accountId) {
		Account account = null;

		@DBAnnotation (
				attribute = {"id", "first_name", "middle_name", "last_name",  
						"address_1", "address_2", 
						"city", "state", "zip_code", 
						"username", "password"},
				table = {"accounts", "accounts", "accounts", "accounts", 
						"accounts", "accounts", 
						"accounts", "accounts", "accounts", 
						"accounts", "accounts"},
				variable = {"account.id", "account.firstName", "account.middleName", "account.lastName",
						"account.address1", "account.address2",
						"account.city", "account.state", "account.zipCode",
						"account.username", "account.password"},
				isSource = {true, true, true, true, 
						true, true, 
						true, true, true, 
						true, true}
				)
		
		// Sets up the SQL query
		String sql = 
			"SELECT accounts.id,\n" + 
			"    accounts.first_name,\n" + 
			"    accounts.middle_name,\n" + 
			"    accounts.last_name,\n" + 
			"    accounts.address_1,\n" + 
			"    accounts.address_2,\n" + 
			"    accounts.city,\n" + 
			"    accounts.state,\n" + 
			"    accounts.zip_code,\n" + 
			"    accounts.username,\n" + 
			"    accounts.password\n" + 
			"FROM accounts\n" + 
			"WHERE accounts.id = "+ accountId + "\n" + 
			"LIMIT 1;";

		// Executes the query
		try {
			ResultSet rs = executeQuery(sql);
			if (rs.next()) {
				account = new Account(rs.getInt("id"), 
						rs.getString("first_name"), 
						rs.getString("middle_name"), 
						rs.getString("last_name"), 
						rs.getString("address_1"), 
						rs.getString("address_2"), 
						rs.getString("city"), 
						rs.getString("state"), 
						rs.getString("zip_code"), 
						rs.getString("username"), 
						rs.getString("password"));
				
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return account;
	}
	
	/**
	 * Gets an account from a user name and a password.
	 * @param userName The user name
	 * @param password The password
	 * @return The account object
	 */
	public Account getAccount(@NotNull String userName, @NotNull String password) {
		Account account = null;
		
		@DBAnnotation (
				attribute = {"id", "first_name", "middle_name", "last_name",  
						"address_1", "address_2", 
						"city", "state", "zip_code", 
						"username", "password"},
				table = {"accounts", "accounts", "accounts", "accounts", 
						"accounts", "accounts", 
						"accounts", "accounts", "accounts", 
						"accounts", "accounts"},
				variable = {"account.id", "account.firstName", "account.middleName", "account.lastName",
						"account.address1", "account.address2",
						"account.city", "account.state", "account.zipCode",
						"account.username", "account.password"},
				isSource = {true, true, true, true, 
						true, true, 
						true, true, true, 
						true, true}
				)
				
		// Sets up the SQL string
		String sql = 
			"SELECT accounts.id,\n" + 
			"    accounts.first_name,\n" + 
			"    accounts.middle_name,\n" + 
			"    accounts.last_name,\n" + 
			"    accounts.address_1,\n" + 
			"    accounts.address_2,\n" + 
			"    accounts.city,\n" + 
			"    accounts.state,\n" + 
			"    accounts.zip_code,\n" + 
			"    accounts.username,\n" + 
			"    accounts.password\n" + 
			"FROM accounts\n" + 
			"WHERE accounts.username = '" + StringEscapeUtils.escapeSql(userName) + "'\n" + 
			"	AND accounts.password = '" + StringEscapeUtils.escapeSql(password) + "'\n" + 
			"LIMIT 1;";

		// Executes the query
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				account = new Account(rs.getInt("id"), 
						rs.getString("first_name"), 
						rs.getString("middle_name"), 
						rs.getString("last_name"), 
						rs.getString("address_1"), 
						rs.getString("address_2"), 
						rs.getString("city"), 
						rs.getString("state"), 
						rs.getString("zip_code"), 
						rs.getString("username"), 
						rs.getString("password"));
				
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return account;
	}

	/**
	 * Gets the configuration settings for a specified key
	 * @param attribute The key (attribute)
	 * @return The stored value
	 */
	public String getConfigurationSetting(String attribute) {
		
		@DBAnnotation (
				attribute = {"value", "attribute"},
				table = {"config", "config"},
				variable = {"value", "attribute"},
				isSource = {true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT config.value\n" + 
			"FROM config\n" + 
			"WHERE config.attribute = '" + StringEscapeUtils.escapeSql(attribute) + "'";

		return getStringFromQuery(sql, "value");
	}
	
	/**
	 * Gets the configuration settings
	 * @return A list of settings
	 */
	public ArrayList<Configuration> getConfigurationSettings() {
		
		@DBAnnotation (
				attribute = {"value", "attribute"},
				table = {"config", "config"},
				variable = {},
				isSource = {true, true}
				)	
		
		// Sets up the SQL string
		String sql = 
			"SELECT config.attribute, config.value\n" + 
			"FROM config;";

		ArrayList<Configuration> settings = new ArrayList<Configuration>();
		ResultSet rs = null;
		try {
			// Executes the query
			rs = executeQuery(sql);
			while (rs.next()) {
				Configuration setting = new Configuration(rs.getString("attribute"), rs.getString("value"));
				settings.add(setting);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return settings;
	}
	
	/**
	 * Gets a connection to the database
	 * @return The connection or null if unavailable
	 */
	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(connectionString, user, pass);
				this.setupConnection();
			}
			catch(Exception ex) {
				conn = null;
			}
		}
		
		return conn;
	}
	
	/**
	 * Gets a credit card type
	 * @param creditCardTypeId The ID of the credit card type
	 * @return The credit card type
	 */
	public CreditCardType getCreditCardType(Integer creditCardTypeId) {
		@DBAnnotation (
				attribute = {"id", "name"},
				table = {"credit_card_types", "credit_card_types"},
				variable = {},
				isSource = {true, true}
				)	
		
		// Sets up the SQL string
		String sql = 
				"SELECT credit_card_types.id,\n" + 
				"    credit_card_types.name\n" + 
				"FROM credit_card_types\n" + 
				"WHERE credit_card_types.id = " + creditCardTypeId;

		// Executes the query
		CreditCardType type = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				type = new CreditCardType(rs.getInt("id"),
									 rs.getString("name"));
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return type;
	}

	/**
	 * Gets a list of credit card types
	 * @return A list of credit card types
	 */
	public ArrayList<CreditCardType> getCreditCardTypes() {
		@DBAnnotation (
				attribute = {"id", "name"},
				table = {"credit_card_types", "credit_card_types"},
				variable = {},
				isSource = {true, true}
				)	
		
		// Sets up the SQL string
		String sql = 
			"SELECT credit_card_types.id,\n" + 
			"    credit_card_types.name\n" + 
			"FROM credit_card_types;";

		// Executes the query
		ArrayList<CreditCardType> types = new ArrayList<CreditCardType>();
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				CreditCardType type = new CreditCardType(rs.getInt("id"),
									 rs.getString("name"));
				types.add(type);
			}
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return types;
	}
	
	/**
	 * Gets a list of currency types
	 * @return A list of currency types
	 */
	public ArrayList<CurrencyType> getCurrencyTypes() {
		@DBAnnotation (
				attribute = {"id", "name"},
				table = {"currency_types", "currency_types"},
				variable = {},
				isSource = {true, true}
				)	
		
		// Sets up the SQL string
		String sql = 
			"SELECT currency_types.id,\n" + 
			"    currency_types.name\n" + 
			"FROM currency_types\n";

		// Executes the query
		ArrayList<CurrencyType> types = new ArrayList<CurrencyType>();
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				CurrencyType type = new CurrencyType(rs.getInt("id"),
									 rs.getString("name"));
				types.add(type);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return types;
	}
	
	/**
	 * Gets a list of a user's favorite movies from the user's account ID
	 * @param accountId The ID of the account
	 * @return A list of movies
	 */
	public ArrayList<Movie> getFavorites(Integer accountId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT movies.id,\n" + 
			"    movies.title,\n" + 
			"    movies.production_year,\n" + 
			"    movies.episode_of_id,\n" + 
			"    movies.season_nr,\n" + 
			"    movies.episode_nr,\n" + 
			"    movies.series_years\n" + 
			"FROM movies\n" + 
			"INNER JOIN favorites\n" + 
			"	ON movies.id = favorites.movies_id\n" + 
			"WHERE favorites.accounts_id = " + accountId;
			
		// Executes the query
		return getListOfMoviesFromQuery(sql);
	}
	
	/**
	 * Gets a list of movies from a query
	 * @param sql The SQL query
	 * @return The list of movies
	 */
	private ArrayList<Movie> getListOfMoviesFromQuery(String sql) {
		ResultSet rs;
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				
				Movie movie = new Movie(rs.getInt("id"), 
						rs.getString("title"), 
						rs.getInt("production_year"), 
						rs.getInt("episode_of_id"), 
						rs.getInt("season_nr"), 
						rs.getInt("episode_nr"), 
						rs.getString("series_years"), 
						"");
				movies.add(movie);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return movies;
	}

	/**
	 * Gets a list of people from a query
	 * @param sql The SQL query
	 * @return The list of people
	 */
	private ArrayList<Person> getListOfPeopleFromQuery(String sql) {
		// Executes the query
		ResultSet rs = null;
		ArrayList<Person> results = new ArrayList<Person>();
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				Person person = new Person(rs.getInt("id"), 
										   rs.getString("first_name"), 
										   rs.getString("last_name"), 
										   'M');
				results.add(person);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
	
	/**
	 * Gets a list of reviews from a query
	 * @param sql The SQL query
	 * @return The list of reviews
	 */
	private ArrayList<Review> getListOfReviewsFromQuery(String sql) {
		// Executes the query
		ArrayList<Review> reviews = new ArrayList<Review>();
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				Review review = new Review(rs.getInt("id"), 
										  rs.getInt("accounts_id"),
										  rs.getInt("movies_id"),
										  rs.getString("review"),
										  rs.getInt("rating"));
						
				reviews.add(review);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return reviews;
	}
	
	/**
	 * Gets a list of movies from a movie id
	 * @param movieId The movie id 
	 * @return The list of movies
	 */
	public Movie getMovie(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)
		
		// Sets up the SQL string
		String sql = 
				"SELECT movies.id,\n" + 
				"    movies.title,\n" + 
				"    movies.production_year,\n" + 
				"    movies.episode_of_id,\n" + 
				"    movies.season_nr,\n" + 
				"    movies.episode_nr,\n" + 
				"    movies.series_years\n" + 
				"FROM movies\n" + 
				"WHERE movies.id = "+ movieId;

		return getMovieFromQuery(sql);
	}
	
	/**
	 * Gets the cast of a movie.
	 * @param movieId The ID of the movie
	 * @return A list of the people in the movie
	 */
	public ArrayList<Person> getMovieCast(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "first_name", "last_name", "gender"},
				table = {"movies", "movies", "movies", "movies"},
				variable = {},
				isSource = {true, true, true, true}
				)

		// Sets up the SQL string
		String sql = 
				"SELECT people.id, people.first_name, " +
				"people.last_name, people.gender\n" + 
				"FROM movies\n" + 
				"INNER JOIN movies_cast\n" + 
				"	ON movies.id = movies_cast.movies_id\n" + 
				"INNER JOIN people\n" + 
				"	ON movies_cast.people_id = people.id\n" + 
				"WHERE movies.id = " + movieId;

		return getListOfPeopleFromQuery(sql);
	}

	/**
	 * Gets data about a movie
	 * @param movieId The ID of the movie
	 * @return The movie's data
	 */
	public MovieData getMovieData(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "md5sum"},
				table = {"movie_data", "movie_data"},
				variable = {},
				isSource = {true, true}
				)

		// Sets up the SQL string
		String sql = 
			"SELECT id, md5sum \n" + 
			"FROM movie_data\n" + 
			"WHERE id = " + movieId + "\n" + 
			"LIMIT 1";

		// Executes the query
		MovieData movieData = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				movieData = new MovieData(rs.getInt("id"), rs.getString("md5sum"));
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return movieData;
	}
	
	/**
	 * Gets a movie description from a movie ID
	 * @param movieId The movieID
	 * @return A concatenated description of the movie
	 */
	public String getMovieDescription(Integer movieId) {	
		@DBAnnotation (
				attribute = {"info", "info_data"},
				table = {"movies", "movies"},
				variable = {},
				isSource = {true, true}
				)

		// Sets up the SQL string
		String sql = 
			"SELECT movies_info.info AS info_data\n" + 
			"FROM movies\n" + 
			"	INNER JOIN movies_info\n" + 
			"		ON movies.id = movies_info.movies_id\n" + 
			"WHERE movies.id = " + movieId + "\n" + 
			"	AND movies_info.info_type_id = 98\n" + 
			"LIMIT 1";

		return getStringFromQuery(sql, "info_data");
	}

	/**
	 * Gets the director(s) of a movie
	 * @param movieId The ID of the movie
	 * @return A list of directors
	 */
	public ArrayList<Person> getMovieDirectors(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "first_name", "last_name", "gender"},
				table = {"movies", "movies", "movies", "movies"},
				variable = {},
				isSource = {true, true, true, true}
				)

		// Sets up the SQL string
		String sql = 
				"SELECT people.id, people.first_name, people.last_name, people.gender\n" + 
				"FROM movies\n" + 
				"INNER JOIN movies_directors\n" + 
				"	ON movies.id = movies_directors.movies_id\n" + 
				"INNER JOIN people\n" + 
				"	ON movies_directors.people_id = people.id\n" + 
				"WHERE movies.id = " + movieId;

		return getListOfPeopleFromQuery(sql);
	}
	
	/**
	 * Gets a movie from a query.  If there are multiple movies in the search,
	 * the first is returned.
	 * @param sql The SQL query
	 * @return The movie if one matches, or null
	 */
	private Movie getMovieFromQuery(String sql) {
		// Executes the query
		Movie movie = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				movie = new Movie(rs.getInt("id"), 
									rs.getString("title"), 
									rs.getInt("production_year"), 
									rs.getInt("episode_of_id"), 
									rs.getInt("season_nr"), 
									rs.getInt("episode_nr"), 
									rs.getString("series_years"), 
									"");
			
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return movie;
	}
		
	/**
	 * Gets the producer(s) of a movie
	 * @param movieId The ID of the movie
	 * @return A list of producers
	 */
	public ArrayList<Person> getMovieProducers(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "first_name", "last_name", "gender"},
				table = {"movies", "movies", "movies", "movies"},
				variable = {},
				isSource = {true, true, true, true}
				)

		// Sets up the SQL string
		String sql = 
				"SELECT people.id, people.first_name, people.last_name, people.gender\n" + 
				"FROM movies\n" + 
				"INNER JOIN movies_producers\n" + 
				"	ON movies.id = movies_producers.movies_id\n" + 
				"INNER JOIN people\n" + 
				"	ON movies_producers.people_id = people.id\n" + 
				"WHERE movies.id = " + movieId;

		return getListOfPeopleFromQuery(sql);
	}
	
	/**
	 * Gets the reviews of a movie
	 * @param movieId The ID of the movie
	 * @return A list of reviews
	 */
	public ArrayList<Review> getMovieReviews(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "accounts_id", "movies_id", "review", "rating"},
				table = {"reviews", "reviews", "reviews", "reviews", "reviews", },
				variable = {},
				isSource = {true, true, true, true, true}
				)

		// Sets up the SQL string
		String sql = 
			"SELECT reviews.id,\n" + 
			"    reviews.accounts_id,\n" + 
			"    reviews.movies_id,\n" + 
			"    reviews.review,\n" + 
			"    reviews.rating\n" + 
			"FROM reviews\n" + 
			"WHERE reviews.movies_id = " + movieId;
			
		// Executes the query
		ArrayList<Review> reviews = new ArrayList<Review>();
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				Review review = new Review(rs.getInt("id"), 
										  rs.getInt("accounts_id"),
										  rs.getInt("movies_id"),
										  rs.getString("review"),
										  rs.getInt("rating"));
						
				reviews.add(review);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return reviews;
	}
	
	/**
	 * Get a list of movies where the title matches the search string
	 * @param searchString The search string
	 * @return The list of movies
	 */
	public ArrayList<Movie> getMovies(String searchString) {
		String temp = "";
		searchString = StringEscapeUtils.escapeSql(searchString);
		String[] results = searchString.split(" ");
		
		for (int i = 0; i < results.length; i++) {
			temp = temp + " +" + results[i];
		}
		
		searchString = temp;
		
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)	
		
		// Sets up the SQL string
		String sql = "SELECT m.id, m.title, m.production_year, m.episode_of_id, " +
				"m.season_nr, m.episode_nr, m.series_years " +
				"FROM movies AS m " +
				"WHERE MATCH (title)\n" + 
				"AGAINST ('" + searchString + "' IN BOOLEAN MODE)";

		return getListOfMoviesFromQuery(sql);
	}
	
	/**
	 * Gets the movies that a person acted in 
	 * @param personId The person's id
	 * @return The list of movies
	 */
	public ArrayList<Movie> getMoviesFromCastmember(Integer personId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)	

		// Sets up the SQL string
		String sql = "SELECT m.id, m.title, m.production_year, m.episode_of_id, m.season_nr, m.episode_nr, m.series_years " +
				"FROM movies AS m JOIN " +
				"     movies_cast AS mc ON m.id = mc.movies_id JOIN " +
				"     people AS p ON mc.people_id = p.id " +
				"WHERE p.id = "+ personId;
			
		// Executes the query
		return getListOfMoviesFromQuery(sql);
	}
	
	/**
	 * Gets a list of movies that a person directed
	 * @param personId The person's id
	 * @return The list of movies
	 */
	public ArrayList<Movie> getMoviesFromDirector(Integer personId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)	
		
		// Sets up the SQL string
		String sql = "SELECT m.id, m.title, m.production_year, m.episode_of_id, m.season_nr, m.episode_nr, m.series_years " +
				"FROM movies AS m JOIN " +
				"     movies_directors AS md ON m.id = md.movies_id JOIN " +
				"     people AS p ON md.people_id = p.id " +
				"WHERE p.id = "+ personId;
			
		// Executes the query
		return getListOfMoviesFromQuery(sql);
	}
	
	/**
	 * Gets a list of movies a person produced
	 * @param personId The person's id
	 * @return The list of movies
	 */
	public ArrayList<Movie> getMoviesFromProducer(Integer personId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)	
		
		// Sets up the SQL string
		String sql = "SELECT m.id, m.title, m.production_year, m.episode_of_id, m.season_nr, m.episode_nr, m.series_years " +
				"FROM movies AS m JOIN " +
				"     movies_producers AS mp ON m.id = mp.movies_id JOIN " +
				"     people AS p ON mp.people_id = p.id " +
				"WHERE p.id = "+ personId;
			
		// Executes the query
		return getListOfMoviesFromQuery(sql);
	}
	
	/**
	 * Gets a list of movies that a person wrote
	 * @param personId The person's id
	 * @return The list of movies
	 */
	public ArrayList<Movie> getMoviesFromWriter(Integer personId) {
		@DBAnnotation (
				attribute = {"id", "title", "production_year",
						"episode_of_id", "season_nr", "episode_nr",
						"series_years"},
				table = {"movies", "movies", "movies", 
						"movies", "movies", "movies", 
						"movies"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true}
				)	
		
		// Sets up the SQL string
		String sql = "SELECT m.id, m.title, m.production_year, m.episode_of_id, m.season_nr, m.episode_nr, m.series_years " +
				"FROM movies AS m JOIN " +
				"     movies_writers AS mw ON m.id = mw.movies_id JOIN " +
				"     people AS p ON mw.people_id = p.id " +
				"WHERE p.id = "+ personId;
			
		// Executes the query
		return getListOfMoviesFromQuery(sql);
	}
		
	/**
	 * Gets the writers for a movie. 
	 * @param movieId The ID for the movie
	 * @return A list of people
	 */
	public ArrayList<Person> getMovieWriters(Integer movieId) {
		@DBAnnotation (
				attribute = {"id", "first_name", "last_name", "gender"},
				table = {"movies", "movies", "movies", "movies"},
				variable = {},
				isSource = {true, true, true, true}
				)
		
		// Sets up the SQL string
		String sql = 
				"SELECT people.id, people.first_name, people.last_name, people.gender\n" + 
				"FROM movies\n" + 
				"INNER JOIN movies_writers\n" + 
				"	ON movies.id = movies_writers.movies_id\n" + 
				"INNER JOIN people\n" + 
				"	ON movies_writers.people_id = people.id\n" + 
				"WHERE movies.id = " + movieId;

		return getListOfPeopleFromQuery(sql);
	}
	
	/**
	 * Gets a specific order from an order ID
	 * @param orderId The ID of the order
	 * @return The order
	 */
	public Order getOrder(Integer orderId) {
		@DBAnnotation (
				attribute = {"id", "payment_methods_id", "order_types_id", "accounts_id", "movies_id",
						"transaction_second", "transaction_minute", "transaction_hour",
						"transaction_day", "transaction_month", "transaction_year"},
				table = {"orders", "orders", "orders", "orders", 
						"orders", "orders", "orders", 
						"orders", "orders", "orders"},
				variable = {},
				isSource = {true, true, true, true,
						true, true, true, 
						true, true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT orders.id,\n" + 
			"    orders.payment_methods_id,\n" + 
			"    orders.order_types_id,\n" + 
			"    orders.accounts_id,\n" + 
			"    orders.movies_id,\n" + 
			"    orders.transaction_second,\n" + 
			"    orders.transaction_minute,\n" + 
			"    orders.transaction_hour,\n" + 
			"    orders.transaction_day,\n" + 
			"    orders.transaction_month,\n" + 
			"    orders.transaction_year\n" + 
			"FROM orders\n" + 
			"WHERE orders.id = " + orderId;
	
		// Executes the query
		Order order = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {				
				order = new Order(rs.getInt("id"),
							rs.getInt("payment_methods_id"),
							rs.getInt("order_types_id"),
							rs.getInt("accounts_id"),
							rs.getInt("movies_id"),
							rs.getInt("transaction_second"),
							rs.getInt("transaction_minute"),
							rs.getInt("transaction_hour"),
							rs.getInt("transaction_day"),
							rs.getInt("transaction_month"),
							rs.getInt("transaction_year"));
						
			}			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}		
		return order;
	}
	
	
	/**
	 * Gets the orders from an account ID
	 * @param accountId The ID of the account
	 * @return A list of orders
	 */
	public ArrayList<Order> getOrders(Integer accountId) {	
		@DBAnnotation (
				attribute = {"id", "payment_methods_id", "order_types_id", "accounts_id", "movies_id",
						"transaction_second", "transaction_minute", "transaction_hour",
						"transaction_day", "transaction_month", "transaction_year"},
				table = {"orders", "orders", "orders", "orders", 
						"orders", "orders", "orders", 
						"orders", "orders", "orders"},
				variable = {},
				isSource = {true, true, true, true,
						true, true, true, 
						true, true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT orders.id,\n" + 
			"    orders.payment_methods_id,\n" + 
			"    orders.order_types_id,\n" + 
			"    orders.accounts_id,\n" + 
			"    orders.movies_id,\n" + 
			"    orders.transaction_second,\n" + 
			"    orders.transaction_minute,\n" + 
			"    orders.transaction_hour,\n" + 
			"    orders.transaction_day,\n" + 
			"    orders.transaction_month,\n" + 
			"    orders.transaction_year\n" + 
			"FROM orders\n" + 
			"WHERE orders.accounts_id = " + accountId;
	
		// Executes the query
		ArrayList<Order> orders = new ArrayList<Order>();
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				Order order = new Order(rs.getInt("id"),
										rs.getInt("payment_methods_id"),
										rs.getInt("order_types_id"),
										rs.getInt("accounts_id"),
										rs.getInt("movies_id"),
										rs.getInt("transaction_second"),
										rs.getInt("transaction_minute"),
										rs.getInt("transaction_hour"),
										rs.getInt("transaction_day"),
										rs.getInt("transaction_month"),
										rs.getInt("transaction_year"));
						
				orders.add(order);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return orders;
	}
	
	/**
	 * Gets an order type from an order type id
	 * @param orderTypeId The order type id
	 * @return The order type
	 */
	public OrderType getOrderType(Integer orderTypeId) {
		@DBAnnotation (
				attribute = {"id", "name"},
				table = {"order_types", "order_types"},
				variable = {},
				isSource = {true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT order_types.id,\n" + 
			"    order_types.name\n" + 
			"FROM order_types\n" + 
			"WHERE order_types.id = " + orderTypeId;
			
		// Executes the query
		OrderType type = null;
		ResultSet rs = null;
		
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				type = new OrderType(rs.getInt("id"),
									 rs.getString("name"));
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return type;
	}
	
	/**
	 * Gets a payment method from a payment method ID
	 * @param paymentMethodId The ID of the payment method
	 * @return The payment method
	 */
	public PaymentMethod getPaymentMethod(Integer paymentMethodId) {
		@DBAnnotation (
				attribute = {"id", "account_id", "credit_card_types_id",  
						"credit_card_number", "expiration_month", "expiration_year", 
						"cvv", "name_on_card", "zip_code"
						},
				table = {"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods", "payment_methods"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true, true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT payment_methods.id,\n" + 
			"    payment_methods.account_id,\n" + 
			"    payment_methods.credit_card_types_id,\n" + 
			"    payment_methods.credit_card_number,\n" + 
			"    payment_methods.expiration_month,\n" + 
			"    payment_methods.expiration_year,\n" + 
			"    payment_methods.cvv,\n" + 
			"    payment_methods.name_on_card,\n" + 
			"    payment_methods.zip_code\n" + 
			"FROM payment_methods\n" + 
			"WHERE payment_methods.id = " + paymentMethodId + "\n" +
			"Limit 1;";

		// Executes the query
		PaymentMethod method = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				method = new PaymentMethod(rs.getInt("id"),
										   rs.getInt("account_id"),
										   rs.getInt("credit_card_types_id"),
										   rs.getString("credit_card_number"),
										   rs.getInt("expiration_month"),
										   rs.getInt("expiration_year"),
										   rs.getInt("cvv"),
										   rs.getString("name_on_card"),
										   rs.getString("zip_code"));
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return method;
	}

	/**
	 * Gets a list of payment methods for a specific account
	 * @param accountId The person's id
	 * @return The list of payment methods
	 */
	public ArrayList<PaymentMethod> getPaymentMethods(Integer accountId) {
		@DBAnnotation (
				attribute = {"id", "account_id", "credit_card_types_id",  
						"credit_card_number", "expiration_month", "expiration_year", 
						"cvv", "name_on_card", "zip_code"
						},
				table = {"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods", "payment_methods", 
						"payment_methods", "payment_methods", "payment_methods"},
				variable = {},
				isSource = {true, true, true, 
						true, true, true, 
						true, true, true}
				)

		String sql = 
				"SELECT payment_methods.id,\n" + 
				"    payment_methods.account_id,\n" + 
				"    payment_methods.credit_card_types_id,\n" + 
				"    payment_methods.credit_card_number,\n" + 
				"    payment_methods.expiration_month,\n" + 
				"    payment_methods.expiration_year,\n" + 
				"    payment_methods.cvv,\n" + 
				"    payment_methods.name_on_card,\n" + 
				"    payment_methods.zip_code\n" + 
				"FROM payment_methods\n" + 
				"WHERE payment_methods.account_id = " + accountId;
		
		// Executes the query
		ArrayList<PaymentMethod> methods = new ArrayList<PaymentMethod>();

		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			while (rs.next()) {
				PaymentMethod method = new PaymentMethod(rs.getInt("id"),
										   rs.getInt("account_id"),
										   rs.getInt("credit_card_types_id"),
										   rs.getString("credit_card_number"),
										   rs.getInt("expiration_month"),
										   rs.getInt("expiration_year"),
										   rs.getInt("cvv"),
										   rs.getString("name_on_card"),
										   rs.getString("zip_code"));
				methods.add(method);
			}
			
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return methods;
	}

	/**
	 * Gets a list of people whose first name or last name matches
	 * the search string
	 * @param searchString The search string
	 * @return The list of people
	 */
	public ArrayList<Person> getPeople(String searchString) {
		
		searchString = StringEscapeUtils.escapeSql(searchString);
		
		// Sets up the SQL string
		String first = "", last = "";
		
		if (searchString.contains(" ")) {
			String[] list = searchString.split(" ");
			first = list[0];
			last = list[1];
		}
		else {
			first = searchString;
			last = searchString;
		}
		
		@DBAnnotation (
				attribute = {"id", "first_name", "last_name", "gender"},
				table = {"movies", "movies", "movies", "movies"},
				variable = {},
				isSource = {true, true, true, true}
				)
		
		String sql = "SELECT id, first_name, last_name, gender "
				+ "FROM people "
				+ "WHERE MATCH (first_name, last_name)\n" + 
		 		"AGAINST ('+" + first + " " + (searchString.contains(" ") ? "+" : "") + last + "' IN BOOLEAN MODE)";

		return getListOfPeopleFromQuery(sql);
	}

	/**
	 * Gets a person's biography
	 * @param peopleId The person's ID
	 * @return A string representing the biography
	 */
	public String getPersonBiography(Integer peopleId) {
		@DBAnnotation (
				attribute = {"info"},
				table = {"people_info"},
				variable = {},
				isSource = {true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT people_info.info AS biography\n" + 
			"FROM people\n" + 
			"INNER JOIN people_info\n" + 
			"	ON people.id = people_info.people_id\n" + 
			"INNER JOIN info_type \n" + 
			"	ON people_info.info_type_id = info_type.id\n" + 
			"WHERE people.id = " + peopleId + "\n" + 
			"	AND (info_type.info = 'mini biography');";

		return getStringFromQuery(sql, "biography");
	}

	/**
	 * Gets a prepared statement from a SQL query
	 * @param sql The sql query
	 * @return The prepared statement
	 */
	private PreparedStatement getStatement(String sql) {
		PreparedStatement statement = null;
	    try {
	    	if (this.getConnection() == null) {
	    		System.out.println("Cannot connect to the database. Sorry. Goodbye.");
	    		System.exit(-1);
	    	}
	    	
	        statement = this.getConnection().prepareStatement(sql);
	    }
	    catch (Exception ex){
	    	ex.printStackTrace();
	    }
	    return statement;
	}
	
	/**
	 * Gets a string from a database query
	 * @param sql The SQL query
	 * @param column The column that the result is in
	 * @return The string in the specified column or null
	 */
	private String getStringFromQuery(String sql, String column) {
		// Executes the query
		String result = null;
		ResultSet rs = null;
		try {
			rs = executeQuery(sql);
			if (rs.next()) {
				result = rs.getString(column);
			}
			rs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}	
		
		return result;
	}
	
	/**
	 * Gets the user reviews of a movie
	 * @param accountId The ID of an account
	 * @return A list of reviews
	 */
	public ArrayList<Review> getUserReviews(Integer accountId) {
		@DBAnnotation (
				attribute = {"id", "accounts_id", "movies_id", "review", "rating"},
				table = {"reviews", "reviews", "reviews", "reviews", "reviews"},
				variable = {},
				isSource = {true, true, true, true, true}
				)
		
		// Sets up the SQL string
		String sql = 
			"SELECT reviews.id,\n" + 
			"    reviews.accounts_id,\n" + 
			"    reviews.movies_id,\n" + 
			"    reviews.review,\n" + 
			"    reviews.rating\n" + 
			"FROM reviews\n" + 
			"WHERE reviews.accounts_id = " + accountId;
			
		return getListOfReviewsFromQuery(sql);
	}

	/**
	 * Sets the connection.  Used primarily for testing.
	 * @param conn The connection
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Sets up a connection
	 */
	private void setupConnection() {
		try {
			Class.forName(driver).newInstance();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Sets up the Singleton.  Should be called before get()
	 * @param connectionString The connection string
	 * @param dbname The database name
	 * @param user The user name
	 * @param pass The password
	 */
	public void setupDALSingleton(String connectionString, String dbname, String user, String pass) {
		this.connectionString = connectionString + dbname;
		this.dbname = dbname;
		this.user = user;
		this.pass = pass;
	}
}
