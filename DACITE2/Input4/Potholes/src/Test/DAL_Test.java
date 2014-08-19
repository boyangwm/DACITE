package Test;


import java.sql.SQLException;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.ArrayList;

import DAL.*;
import Model.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DAL_Test {
	
	static DAL dal;
	Account account;
	Order order;
	CreditCardType creditCardType;
	CurrencyType currencyType;
	Movie movie;
	Movie favorite;
	Person person;
	Person tooShort;
	PaymentMethod paymentMethod;
	Review review;
	@Mock Connection mockConn;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		dal = DAL.get();
		dal.setConn(null);
		
		account = new Account(
				"Jeffrey", 
				"M", 
				"Grandt", 
				"122 West Fake Way", 
				"", 
				"Mount Prospect", 
				"IL", 
				"60056", 
				"jeff", 
				"password");
		account.setId(1);
		
		creditCardType = new CreditCardType(1, "VISA");
		
		movie = new Movie(null, 
				"!Next?", 
				1994, 
				0, 
				0, 
				0, 
				"1994-1995",
				null);
		movie.setId(1);
		
		order = new Order(1, 1, 1, 1904754);
		order.setId(1);
		
		currencyType = new CurrencyType("US DOLLAR");
		
		favorite = new Movie(2041519, 
				"Die Hard 2", 
				1990, 
				0, 
				0, 
				0, 
				null, 
				null);
		
		person = new Person(1454098, 
				"Jai", 
				"Rodriguez", 
				'm');
		
		tooShort = new Person(4, "Too", "$hort", 'm');
		
		paymentMethod = new PaymentMethod(
				1,
				1,
				2,
				"4111111111111111",
				5,
				2016,
				530,
				"",
				"");
		
		review = new Review(1, 1, 2266712, "This movie is a revelation. " +
				"Kevin Spacey is wonderful! " +
				"I loved the dynamic characters! Great film!", 4);
		
	}

	@After
	public void tearDown() throws Exception {
		dal.closeDAL();
		dal.setConn(null);
	}

	@Test
	public void testGet() {
		assertNotNull(DAL.get());
		assertEquals("It should return an instance of DAL", DAL.get().getClass(), DAL.class);
		assertEquals("Subsequent calls should return the same instance", DAL.get(), DAL.get());
	}

	@Test
	public void testCloseDAL() {
		dal.setupDALSingleton("jdbc:mysql://coe9000.ed.uic.edu:3306/", "imdb", "guest", "123456");
		dal.getConnection();
		assertTrue("Closing an open connection should succeed.", dal.closeDAL());
		
		assertFalse("Closing a connection after already closing it should return false", dal.closeDAL());
		
		try {
			doThrow(new SQLException()).when(mockConn).close();
			dal.setConn(mockConn);
			dal.closeDAL();
		} 
		catch (Exception ex) {
			fail("closeDAL should catch this error");
		}
		dal.setConn(null);
	}


	@Test
	public void testCreateAccountAccount() {		
		Connection connection = dal.getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction
			Account newAccount = dal.createAccount(account);
			assertEquals("A new account should be created", newAccount, account);
			assertTrue("The generated id should be greater than 0.", newAccount.getId() > 0);
			assertFalse("The generated id should not be less than or equal to 0", newAccount.getId() <= 0);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			fail("This should not fail");
		}	
		finally
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
	}

	@Test
	public void testCreateAccountStringStringStringStringStringStringStringStringStringString() {
		Connection connection = dal.getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction
			Account newAccount = dal.createAccount(account.getFirstName(),
					account.getMiddleName(),
					account.getLastName(),
					account.getAddress1(),
					account.getAddress2(),
					account.getCity(),
					account.getState(),
					account.getZipCode(),
					account.getUsername(),
					account.getPassword());
			assertEquals("A new account should be created", newAccount, account);
			assertTrue("The generated id should be greater than 0.", newAccount.getId() > 0);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			fail("This should not fail");
		}	
		finally
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
	}

	@Test
	public void testCreateOrderIntegerIntegerIntegerInteger() {
		Connection connection = dal.getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction
			Order newOrder = dal.createOrder(order.getPaymentMethodId(), 
					order.getOrderTypeId(), 
					order.getAccountId(),
					order.getMovieId());
			assertEquals("A new order should be created", newOrder, order);
			assertTrue("The generated id should be greater than 0.", newOrder.getId() > 0);
			assertNotNull("The generated seconds should not be null", newOrder.getTransactionSecond());
			assertNotNull("The generated minutes should not be null", newOrder.getTransactionMinute());
			assertNotNull("The generated hours should not be null", newOrder.getTransactionHour());
			assertNotNull("The generated days should not be null", newOrder.getTransactionDay());
			assertNotNull("The generated months should not be null", newOrder.getTransactionMonth());
			assertNotNull("The generated years should not be null", newOrder.getTransactionYear());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			fail("This should not fail");
		}	
		finally
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
	}

	@Test
	public void testCreatePaymentMethodIntegerIntegerStringIntegerIntegerIntegerStringString() {
		Connection connection = dal.getConnection();
		
		String ccNumber = "4567891234567890";
		Integer month = 12;
		Integer year = 2014;
		Integer cvv = 123;
		String nameOnCard = "Bob Barker";
		String zip = "60606";
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			PaymentMethod newPaymentMethod = dal.createPaymentMethod(account.getId(), 
					creditCardType.getId(), 
					ccNumber,
					month,
					year,
					cvv,
					nameOnCard,
					zip);
			assertNotNull("A new payment method should be created", newPaymentMethod);
			assertTrue("The generated id should be greater than 0.", newPaymentMethod.getId() > 0);
			assertEquals("The generated month should match", newPaymentMethod.getExpirationMonth(), month);
			assertEquals("The generated year should match", newPaymentMethod.getExpirationYear(), year);
			assertEquals("The generated cvv should match", newPaymentMethod.getCvv(), cvv);
			assertEquals("The generated name on card should match", newPaymentMethod.getNameOnCard(), nameOnCard);
			assertEquals("The generated zip should match", newPaymentMethod.getZipCode(), zip);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			fail("This should not fail");
		}	
		finally
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
	}

	@Test
	public void testCreateFavoriteIntegerInteger() {
		Connection connection = dal.getConnection();

		try{
			connection.setAutoCommit(false);    //begin transaction
			int id = dal.createFavorite(account.getId(), 
					movie.getId());
			assertTrue("The generated id should be greater than 0.", id > 0);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			fail("This should not fail");
		}	
		finally
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
	}

	@Test
	public void testGetAccountInteger() {
		Account newAccount = dal.getAccount(1);
		assertEquals("The new account should equal the account.", newAccount, account);
		
		newAccount = dal.getAccount(-1);
		assertNull("No account should be found for a bad id.", newAccount);
	}

	@Test
	public void testGetAccountStringString() {
		Account newAccount = dal.getAccount("Jeff", "password");
		assertEquals("The new account should equal the account.", newAccount, account);
		
		newAccount = dal.getAccount("Jeff", "SmellsKindOfFunny");
		assertNull("There should be no account with a bogus username and password.", newAccount);
	}

	@Test
	public void testGetConfigurationSetting() {
		String value = dal.getConfigurationSetting("Currency");
		assertEquals("The value of the currency should be 'US DOLLAR'", value, "US DOLLAR");

		value = dal.getConfigurationSetting("Monkey Power");
		assertNull("There value for a random configuration should be null", value);
	}

	@Test
	public void testGetConfigurationSettings() {
		Configuration configuration = new Configuration("Currency", "US DOLLAR");
		ArrayList<Configuration> configurations = dal.getConfigurationSettings();
		assertTrue("The ArrayList should contain a configuration with" +
				" a key = 'Currency' and a value = 'US DOLLAR'", configurations.contains(configuration));
		
		configuration = new Configuration("Bananas", "YUMM!");
		assertFalse("The ArrayList should not contain a random configuration", configurations.contains(configuration));
	}

	@Test
	public void testGetCreditCardType() {
		CreditCardType newCreditCardType = dal.getCreditCardType(creditCardType.getId());
		assertEquals("The credit card types should match", newCreditCardType, creditCardType);
		
		newCreditCardType = dal.getCreditCardType(-1);
		assertNull("The credit card type should be null when there is no match", newCreditCardType);
	}

	@Test
	public void testGetCreditCardTypes() {
		ArrayList<CreditCardType> creditCardTypes = dal.getCreditCardTypes();
		assertTrue("The credit card types should include the credit card type.", 
				creditCardTypes.contains(creditCardType));
	}

	@Test
	public void testGetCurrencyTypes() {
		ArrayList<CurrencyType> currencyTypes = dal.getCurrencyTypes();
		assertTrue("The currency types should include 'US DOLLAR'", currencyTypes.contains(currencyType));
	}

	@Test
	public void testGetFavoritesInteger() {
		ArrayList<Movie> favorites = dal.getFavorites(account.getId());
		assertTrue("The favorites should contain this movie", favorites.contains(favorite));
	}

	@Test
	public void testGetMovie() {
		Movie newMovie = dal.getMovie(movie.getId());
		assertEquals("The movies should match.", newMovie, movie);
	}

	@Test
	public void testGetMovieCast() {
		ArrayList<Person> cast = dal.getMovieCast(3);
		assertTrue("The movie with ID 3 should have Jai Rodriguez in the cast.", 
				cast.contains(person));
	}

	@Test
	public void testGetMovieDataInteger() {
		MovieData movieData = dal.getMovieData(movie.getId());
		assertEquals("The movie data should equal the string", movieData.getData(), "5bf0e2f53291d5e2ca195ccab0a84fef");
	
		movieData = dal.getMovieData(-1);
		assertNull("A bad movie id should return null", movieData);
	
	}

	@Test
	public void testGetMovieDescription() {
		String description = dal.getMovieDescription(favorite.getId());
		assertTrue("The description should match the description in the database",
				description.contains("After the terrifying events in LA, John McClane (Willis) is about to go through it all again.")); 
	}

	@Test
	public void testGetMovieDirectors() {
		Person director = new Person(411931, "Ton", "Def", 'm');
		ArrayList<Person> directors = dal.getMovieDirectors(2764641);
		assertTrue("The list of directors should include Ton Def", directors.contains(director));
	}

	@Test
	public void testGetMovieProducers() {
		ArrayList<Person> producers = dal.getMovieProducers(1880696);
		assertTrue("The list of directors should include Too $short", producers.contains(tooShort));
	}

	@Test
	public void testGetMovieReviewsInteger() {
		ArrayList<Review> reviews = dal.getMovieReviews(2266712);
		assertTrue("The list of reviews should contain the given review.", reviews.contains(review));
	}

	@Test
	public void testGetMovies() {
		ArrayList<Movie> movies = dal.getMovies("Die Hard");
		assertTrue("The list of movies should include 'Die Hard 2'", movies.contains(favorite));
		assertFalse("The list of movies should not include '!Next?'", movies.contains(movie));
	}

	@Test
	public void testGetMoviesFromCastmember() {
		Movie movie = new Movie(1695348, 
				"Ultimate Style",
				2007,
				0,
				0,
				0,
				"2007-????",
				"");
		ArrayList<Movie> movies = dal.getMoviesFromCastmember(person.getId());
		assertTrue("The movies should include 'Ultimate Style'", movies.contains(movie));
	}

	@Test
	public void testGetMoviesFromDirector() {
		Movie movie = new Movie(781137, 
				"Let Them Eat Cupcakes", 
				2010, 
				781112, 
				2, 
				7, 
				null, 
				"");
		ArrayList<Movie> movies = dal.getMoviesFromDirector(tooShort.getId());
		assertTrue("The movies should include 'Let Them Eat Cupcakes'", movies.contains(movie));
	}

	@Test
	public void testGetMoviesFromProducer() {
		Movie movie = new Movie(1880696, 
				"American Pimp", 
				1999, 
				0, 
				0, 
				0, 
				null, 
				"");
		
		ArrayList<Movie> movies = dal.getMoviesFromProducer(tooShort.getId());
		assertTrue("The movies should include 'American Pimp'", movies.contains(movie));	
	}

	@Test
	public void testGetMoviesFromWriter() {
		Movie movie = new Movie(2350230, 
				"Menace II Society", 
				1993, 
				0, 
				0, 
				0, 
				null, 
				"");
		
		ArrayList<Movie> movies = dal.getMoviesFromWriter(tooShort.getId());
		assertTrue("The movies should include 'Menace II Society'", movies.contains(movie));	
	}

	@Test
	public void testGetMovieWriters() {
		ArrayList<Person> people = dal.getMovieWriters(2350230);
		assertTrue("The writers of the movie with ID 2350230 (Menace II Society) " +
				"should include Too $hort", people.contains(tooShort));
	}

	@Test
	public void testGetOrder() {
		Order newOrder = dal.getOrder(order.getId());
		assertEquals("The order fetched from the database should match the order.",
				newOrder, order);
		
		newOrder = dal.getOrder(-1);
		assertNull("A bad orderId should return null",
				newOrder);

	}

	@Test
	public void testGetOrdersInteger() {
		ArrayList<Order> orders = dal.getOrders(account.getId());
		assertTrue("The orders should contain the order.", orders.contains(order));
	}

	@Test
	public void testGetOrderTypeInteger() {
		OrderType orderType = new OrderType(1, "PURCHASE");
		OrderType newOrderType = dal.getOrderType(orderType.getId());
		assertEquals("The new order type should equal the old one.", 
				newOrderType, orderType);
		
		newOrderType = dal.getOrderType(-1);
		assertNull("A bad orderTypeId should return null.", 
				newOrderType);
		
	}

	@Test
	public void testGetPaymentMethodInteger() {
		PaymentMethod newPaymentMethod = dal.getPaymentMethod(paymentMethod.getId());
		assertEquals("The new payment method should equal the given payment method.", 
				newPaymentMethod, paymentMethod);

		newPaymentMethod = dal.getPaymentMethod(-1);
		assertNull("A bad payment method id should return null.", 
				newPaymentMethod);

	}

	@Test
	public void testGetPaymentMethodsInteger() {
		ArrayList<PaymentMethod> paymentMethods = dal.getPaymentMethods(paymentMethod.getAccountId());
		assertTrue("The payment method should be included in the results.",
				paymentMethods.contains(paymentMethod));
		
		paymentMethods = dal.getPaymentMethods(-1);
		assertTrue("The payment method should be empty when a bad id is given.",
				paymentMethods.isEmpty());
		
	}

	@Test
	public void testGetPeople() {
		ArrayList<Person> people = dal.getPeople("$hort");
		assertTrue("The list of people should include 'Die Hard 2'", people.contains(tooShort));
		assertFalse("The list of people should not include '!Next?'", people.contains(person));
	}

	@Test
	public void testGetPersonBiography() {
		String biography = dal.getPersonBiography(person.getId());
		assertTrue("The selected biography should include the text.",
				biography.contains("Jai is a talented actor, singer and dancer who most " +
						"recently starred in the lead role of Zanna in the off-Broadway musical hit Zanna, Don't!"));
	}

	@Test
	public void testGetUserReviewsInteger() {
		ArrayList<Review> reviews = dal.getUserReviews(account.getId());
		assertTrue("The reviews should contain the review.", reviews.contains(review));
	}

	@Test
	public void testSetupDALSingleton() {

		dal.setupDALSingleton("jdbc:mysql://banana:3306/", "imdb", "guest", "123456");
		assertNull("If we set the wrong server, the connection should be null.", dal.getConnection());
		
		dal.setupDALSingleton("jdbc:mysql://coe9000.ed.uic.edu:3306/", "imdb", "guest", "123456");
		assertNotNull("If we set the right server, the connection should connect.", dal.getConnection());
		
	}

}
