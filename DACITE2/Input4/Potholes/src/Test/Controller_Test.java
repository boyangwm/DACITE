package Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import Model.*;
import Controller.*;
import DAL.*;


public class Controller_Test {

	Controller controller;
	Account account;
	final Movie purchasedMovie = new Movie(2721328,
			"Trust, Honour and Reputation: The Making of Othello: The Tragedy of the Moor",
			2008,
			0,
			0,
			0,
			null,
			null);
	final Movie notpurchasedMovie = new Movie(2721327,
			"Trust, Greed, Bullets & Bourbon",
			2013,
			0,
			0,
			0,
			null,
			null);
	final Movie favorite = new Movie(2041519, 
			"Die Hard 2", 
			1990, 
			0, 
			0, 
			0, 
			null, 
			null);
	final Movie nonFavorite = new Movie(-1, 
			"Die Hard 18", 
			1990, 
			0, 
			0, 
			0, 
			null, 
			null);
	final Movie rentedMovie = new Movie(2665627,
			"The Perilous Persuits of Mr. Bean",
			1992,
			0,
			0,
			0,
			null,
			null);
	@Mock BufferedReader mockReader;
	@Mock PrintStream mockOut;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new Controller();
		Controller.setIn(mockReader);
		Controller.setOut(mockOut);
		
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
		
		Controller.setAccount(account);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(timeout=5000)
	public void testRun() {
		try {
			when(mockReader.readLine()).thenReturn("Jeff", "password", "4");
		} catch (IOException e) {}		
		controller.run();		
		verify(mockOut).println(contains("Goodbye"));
	}
	
	
	@Test(timeout=5000)
	public void testSettingsAndAccount() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "2", "1", "3", "4");
				
			} catch (IOException e) {}
			
			controller.run();
			
			verify(mockOut).println(contains("Die Hard 2"));
			verify(mockOut).println(contains("Currency US DOLLAR"));
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

	@Test(timeout=5000)
	public void testDoMenu() {
		// If an invalid number is entered, it should
		// loop until a valid number is entered.
		try {
			when(mockReader.readLine()).thenReturn("6", "-1", "4");
		} catch (IOException e) {}
		controller.doMenu();
		verify(mockOut, times(2)).println(contains("Incorrect selection."));
		
		
		// The program should exit when 4 is entered
		try {
			when(mockReader.readLine()).thenReturn("4");
		} catch (IOException e) {}
		controller.doMenu();
		verify(mockOut, times(2)).println(contains("Goodbye."));
		
		
	}

	@Test(timeout=5000)
	public void testViewSettings() {
		controller.viewSettings();
		verify(mockOut, atLeast(2)).println(anyString());
	}

	@Test(timeout=5000)
	public void testViewAccount() {
		// The user should be able to exit immediately
		try {
			when(mockReader.readLine()).thenReturn("0");
		} catch (IOException e) {}
		
		controller.viewAccount();
		verify(mockOut).println(contains("This is the account page."));
		verify(mockOut, times(6)).println(anyString());
		
		// The user should be able to enter values outside of the expected
		// values
		try {
			when(mockReader.readLine()).thenReturn("-1", "3", "0");
		} catch (IOException e) {}
		
		controller.viewAccount();
		verify(mockOut, times(2)).println(contains("Incorrect selection!"));
	}

	@Test(timeout=30000)
	public void testDoSearch() {
		// A search should be able to be aborted
		try {
			when(mockReader.readLine()).thenReturn(null, "  \n", "SomeInput", "-1", "4", "0");
		} catch (IOException e) {}
		controller.doSearch();
		verify(mockOut, atLeast(5)).println(anyString());

		reset(mockOut);
		// A movie should be able to be searched
		try {
			when(mockReader.readLine()).thenReturn("Die Hard", "1", "0");
		} catch (IOException e) {}
		controller.doSearch();
		verify(mockOut, atLeast(1)).println(contains("Die Hard 2"));
		
		reset(mockOut);
		// A person should be able to be searched
		try {
			when(mockReader.readLine()).thenReturn("Bruce Willis", "2", "0");
		} catch (IOException e) {}
		controller.doSearch();
		verify(mockOut, atLeast(1)).println(contains("Bruce Willis"));
		reset(mockOut);

		// A person and a movie should be able to be searched
		try {
			when(mockReader.readLine()).thenReturn("Bill", "3", "0");
		} catch (IOException e) {}
		controller.doSearch();
		verify(mockOut, atLeast(1)).println(contains("Kill Bill"));
		verify(mockOut, atLeast(1)).println(contains("Bill Murray"));
		
	}

	@Test(timeout=5000)
	public void testDoMoviesSearch() {
		// A search should be able to be aborted
		try {
			when(mockReader.readLine()).thenReturn("-1", String.valueOf(Integer.MAX_VALUE), "0");
		} catch (IOException e) {}
		controller.doMoviesSearch("Banana");
		verify(mockOut, times(2)).println("Entry out of range!");

		reset(mockOut);
		// A search with no results should notify the user
		try {
			when(mockReader.readLine()).thenReturn("0");
		} catch (IOException e) {}
		controller.doMoviesSearch("BananaLamaLlama");
		verify(mockOut).println("No results. Sorry.");

	}

	@Test(timeout=10000)
	public void testDoPeopleSearch() {
		// A search for a non-existent person should not return any results
		try {
			when(mockReader.readLine()).thenReturn("\n", "4");
		} catch (IOException e) {}
		controller.doPeopleSearch("BadMamaJamma");
		verify(mockOut).println(contains("No results"));

		// A successful search followed by bad selections should print
		// out "No Results"
		reset(mockOut);
		try {
			when(mockReader.readLine()).thenReturn("-1", String.valueOf(Integer.MAX_VALUE), "1");
		} catch (IOException e) {}
		controller.doPeopleSearch("Bruce");
		verify(mockOut, atLeast(5000)).println(contains("Bruce"));
		verify(mockOut, times(2)).println(contains("Entry out of range!"));

		// A successful search followed by a good selection should
		// print out the results
		reset(mockOut);
		try {
			when(mockReader.readLine()).thenReturn("0");
		} catch (IOException e) {}
		controller.doPeopleSearch("Bruce");
		verify(mockOut, atLeast(5000)).println(contains("Bruce"));
		verify(mockOut, never()).println(contains("Entry out of range!"));		
	}

	@Test(timeout=5000)
	public void testDoBothSearch() {
		// When a search is performed, the user should be
		// able to exit the search
		try {
			when(mockReader.readLine()).thenReturn("0", "4");
		} catch (IOException e) {}
		controller.doBothSearch("Die Hard 2");
		Controller.setOut(mockOut);
		
		// When a search for "Die Hard 2" is performed
		// a "Die Hard 2" should be in the results 
		try {
			when(mockReader.readLine()).thenReturn("1", "0", "4");
		} catch (IOException e) {}
		controller.doBothSearch("Die Hard");
		verify(mockOut, atLeast(1)).println(contains("Die Hard 2"));
		
		// When a search for "Spacey" is performed and we view a person.
		// We should see "American Beauty" in the result
		// at least 3 times.
		try {
			when(mockReader.readLine()).thenReturn("32", "0", "4");
		} catch (IOException e) {}
		controller.doBothSearch("Spacey");
		verify(mockOut, atLeast(3)).println(contains("Kevin Spacey"));

	}

	@Test(timeout=5000)
	public void testFavoritesContains() {
		assertTrue(controller.favoritesContains(favorite));
		assertFalse(controller.favoritesContains(nonFavorite));
		
	}

	@Test(timeout=5000)
	public void testIsPurchased() {
		assertTrue(controller.isPurchased(purchasedMovie));
//		assertTrue(controller.isPurchased(rentedMovie));
		assertFalse(controller.isPurchased(notpurchasedMovie));
	}

	@Test(timeout=10000)
	public void testDoMovie() {
		// A purchased movie should be displayed
		try {
			when(mockReader.readLine()).thenReturn("0", "4");
		} catch (IOException e) {}
		controller.doMovie(purchasedMovie);
		verify(mockOut).println(contains("Production year: 2008"));
		
		// A non purchased movie should be displayed
		try {
			when(mockReader.readLine()).thenReturn("0", "4");
		} catch (IOException e) {}
		controller.doMovie(notpurchasedMovie);
		verify(mockOut).println(contains("Production year: 2013"));
	}

	@Test(timeout=10000)
	public void testDoOwnedMovie() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction

			// The user should be able to go to the main menu
			try {
				when(mockReader.readLine()).thenReturn("0", "4");
			} catch (IOException e) {}
			controller.doOwnedMovie(purchasedMovie, false);
			verify(mockOut, times(5)).println(anyString());
			
			// The user should be able to view the movie
			try {
				when(mockReader.readLine()).thenReturn("1", "4");
			} catch (IOException e) {}
			controller.doOwnedMovie(purchasedMovie, false);
			verify(mockOut).println(contains("eae572d5a4ba141887644e75253d7b8e"));

			// The user should be able to view the movie
			try {
				when(mockReader.readLine()).thenReturn("2", "4");
			} catch (IOException e) {}
			controller.doOwnedMovie(purchasedMovie, false);
			verify(mockOut).println(contains("has been added to your favorites"));
			
			// The user might enter a value outside of the range
			try {
				when(mockReader.readLine()).thenReturn("-1", "3", "0");
			} catch (IOException e) {}
			controller.doOwnedMovie(purchasedMovie, false);
			verify(mockOut, times(2)).println(contains("Incorrect selection!"));
						
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

	@Test(timeout=5000)
	public void testDoUnownedMovie() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction

			// The user should be able to go to the main menu
			try {
				when(mockReader.readLine()).thenReturn("0", "4");
			} catch (IOException e) {}
			controller.doUnownedMovie(notpurchasedMovie, false);
			verify(mockOut, times(6)).println(anyString());
			
			// The user should be able to rent the movie
			try {
				when(mockReader.readLine()).thenReturn("1", "0", "4");
			} catch (IOException e) {}
			controller.doUnownedMovie(notpurchasedMovie, false);
			verify(mockOut).println(contains("Use existing payment method"));
			
			// The user should be able to purchase the movie
			try {
				when(mockReader.readLine()).thenReturn("2", "0", "4");
			} catch (IOException e) {}
			controller.doUnownedMovie(notpurchasedMovie, false);
			verify(mockOut, times(2)).println(contains("Use existing payment method"));

			// The user should be able to view the movie
			try {
				when(mockReader.readLine()).thenReturn("3", "0", "4");
			} catch (IOException e) {}
			controller.doUnownedMovie(notpurchasedMovie, false);
			verify(mockOut).println(contains("has been added to your favorites"));
			
			// The user might enter a value outside of the range
			try {
				when(mockReader.readLine()).thenReturn("-1", "4", "0");
			} catch (IOException e) {}
			controller.doUnownedMovie(purchasedMovie, false);
			verify(mockOut, times(2)).println(contains("Incorrect selection!"));
						
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


	@Test(timeout=10000)
	public void testDoPaymentMethod() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction

			// The user should be able to go to the main menu
			try {
				when(mockReader.readLine()).thenReturn("0", "4");
			} catch (IOException e) {}
			controller.doPaymentMethod();
			verify(mockOut, times(5)).println(anyString());

			// The user should be able to use an existing payment method
			try {
				when(mockReader.readLine()).thenReturn("1", "1", "0", "4");
			} catch (IOException e) {}
			assertTrue(controller.doPaymentMethod() instanceof PaymentMethod);
			verify(mockOut).println(contains("4111111111111111"));
			
			// The user should be able to use an new payment method
			try {
				when(mockReader.readLine()).thenReturn("2", "0", "4");
			} catch (IOException e) {}
			assertNull(controller.doPaymentMethod());
			verify(mockOut).println(contains("Please choose from the following credit card types"));
		
			// The user should be able to enter a wrong selection
			try {
				when(mockReader.readLine()).thenReturn("-1", "0");
			} catch (IOException e) {}
			assertNull(controller.doPaymentMethod());
			verify(mockOut).println(contains("Incorrect selection!"));

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

	@Test(timeout=10000)
	public void testDoExistingPaymentMethod() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction

			// The user should be able to go to the main menu
			try {
				when(mockReader.readLine()).thenReturn("0");
			} catch (IOException e) {}
			controller.doExistingPaymentMethod();
			verify(mockOut, atLeast(5)).println(anyString());

			// The user should be able to go to the main menu
			try {
				when(mockReader.readLine()).thenReturn("1", "0");
			} catch (IOException e) {}
			assertTrue(controller.doExistingPaymentMethod() instanceof PaymentMethod);

			// The user should be able enter a value out of the range
			try {
				when(mockReader.readLine()).thenReturn("-1", "0");
			} catch (IOException e) {}
			controller.doExistingPaymentMethod();
			verify(mockOut).println("Entry out of range!");

			// The user should be able enter a value out of the range
			try {
				when(mockReader.readLine()).thenReturn(String.valueOf((Integer.MAX_VALUE)), "0");
			} catch (IOException e) {}
			controller.doExistingPaymentMethod();
			verify(mockOut, times(2)).println("Entry out of range!");

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

	@Test(timeout=5000)
	public void testDoNewPaymentMethod() {
		Connection connection = DAL.get().getConnection();
		try{
			connection.setAutoCommit(false);    //begin transaction

			// The user should be able enter a value below the range
			try {
				when(mockReader.readLine()).thenReturn("-1", "0");
			} catch (IOException e) {}
			controller.doNewPaymentMethod();
			verify(mockOut).println("Entry out of range!");
			
			// The user should be able enter a value above of the range
			try {
				when(mockReader.readLine()).thenReturn("5", "0");
			} catch (IOException e) {}
			controller.doNewPaymentMethod();
			verify(mockOut, times(2)).println("Entry out of range!");
			
			// The user should be able credit card values out of the range
			try {
				when(mockReader.readLine()).thenReturn("1", "4111", "41111111111111111", "4111111111111111", "-1", "13", "1", "2000", "2020", "0");
			} catch (IOException e) {}
			controller.doNewPaymentMethod();
			verify(mockOut, times(5)).println(contains("Incorrect value entered."));

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

	@Test(timeout=5000)
	public void testPrintAlreadyPurchasedMovieMenu() {
		controller.printAlreadyPurchasedMovieMenu(true);
		verify(mockOut).println(contains("Remove Movie from favorites."));
		
		controller.printAlreadyPurchasedMovieMenu(false);
		verify(mockOut).println(contains("Add Movie to favorites."));
	}
	
	@Test(timeout=5000)
	public void testInputIsWithinRange() {
		
		assertTrue("A value equal to the lowerbound is within the range", controller.inputIsWithinRange(0, 0, 2));	
		assertTrue("A value in the middle is within the range", controller.inputIsWithinRange(1, 0, 2));	
		assertTrue("A value equal to the upperbound is within the range", controller.inputIsWithinRange(2, 0, 2));	
		
		assertFalse("A value below the lowerbounde should return false", controller.inputIsWithinRange(-1, 0, 2));
		assertFalse("A value above the upperbound should return false", controller.inputIsWithinRange(3, 0, 2));
		
		assertFalse("Switched values of begin and end should return false", controller.inputIsWithinRange(1, 1, 0));
		assertFalse("Switched values of begin and end should return false", controller.inputIsWithinRange(0, 1, 0));
		
	}
	
	@Test(timeout=5000)
	public void testPrintMovieMenu() {
		controller.printMovieMenu(true);
		verify(mockOut).println(contains("Remove Movie from favorites."));
		
		controller.printMovieMenu(false);
		verify(mockOut).println(contains("Add Movie to favorites."));
	}

}
