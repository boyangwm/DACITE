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
import Controller.*;
import DAL.*;

public class System_Test {

	Controller controller;
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
	public void testMenuSearch() {
		try {
			when(mockReader.readLine()).thenReturn("Jeff", "password", "2", "0", "3", "0", "1", "hello", "1", "0", "4");
		} catch (IOException e) {}
		
		controller.run();
		
		verify(mockOut, atLeast(1)).println(contains("Goodbye"));
	}
	
	@Test(timeout=5000)
	public void testAddToFavorites() {
		try {
			when(mockReader.readLine()).thenReturn("Jeff", "password", "2", "0", "3", "0", "1", "hello", "1", "22", "3", "2", "1", "4");
		} catch (IOException e) {}
		
		controller.run();
		
		verify(mockOut, atLeast(1)).println(contains("Iowan Caucus: Hello Mister Obama!"));
	}
	
	@Test(timeout=5000)
	public void testMovieSearch() {
		try {
			when(mockReader.readLine()).thenReturn("Jeff", "password", "4");
		} catch (IOException e) {}
		controller.run();
		verify(mockOut).println(contains("Goodbye"));
	}
	
	@Test(timeout=5000)
	public void testMoviePurchase() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "1", "hello", "1", "710", "2", "2", "1", "3434343434343434", "2", "2014", "234", "jeffrey grandt", "60034", "1", "hello", "1", "710", "1", "4");
				
			} catch (IOException e) {}
			controller.run();
			
			verify(mockOut).println(contains("View Movie."));
			verify(mockOut, times(2)).println(contains("0457a17b1d9804660fd88219e390a6c7"));
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
	public void testMoviePurchaseWithErrors() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			
			try {
				when(mockReader.readLine()).thenReturn("Jeffr", "password", "Jeff", "password", "1", "hello", "1", "710", "2", "2", "1", "34343434344", "3434343434343434", "22", "2", "2010", "2014", "234", "jeffrey grandt", "60034", "1", "hello", "1", "710", "1", "4");
				
			} catch (IOException e) {}
			//Controller.setOut(System.out);
			controller.run();
			
			verify(mockOut).println(contains("View Movie."));
			verify(mockOut, times(2)).println(contains("0457a17b1d9804660fd88219e390a6c7"));
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
	
	@Test(timeout=8000)
	public void testMovieRental() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "1", "hello", "1", "710", "1", "2", "1", "3434343434343434", "2", "2014", "234", "jeffrey grandt", "60034", "1", "hello", "1", "710", "1", "4");
				
			} catch (IOException e) {}
			controller.run();
			
			verify(mockOut).println(contains("View Movie."));
			verify(mockOut, times(2)).println(contains("0457a17b1d9804660fd88219e390a6c7"));
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
	
	@Test(timeout=8000)
	public void testMovieRentalExpired() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "1", "die hard", "1", "1", "0", "4");
				
			} catch (IOException e) {}
			controller.run();
			
			verify(mockOut).println(contains("Rent Movie"));
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
	public void testSettingsAndAccount() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "2", "1", "3", "4");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			controller.run();
			
			verify(mockOut, atLeast(1)).println(contains("Amen"));
//			verify(mockOut).println(contains("Hard"));
//			verify(mockOut).println(contains("Currency US DOLLAR"));
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
	public void testAccountViewOrders() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "2", "1", "2", "2", "4");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			controller.run();
			
			verify(mockOut, atLeast(1)).println(contains("Hello, Goodbye, Amen"));
			verify(mockOut, atLeast(1)).println(contains("VISA 3434343434343434"));
			//VISA 3434343434343434
//			verify(mockOut).println(contains("Hard"));
//			verify(mockOut).println(contains("Currency US DOLLAR"));
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
	public void testPeopleSearch() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "1", "bruce wills", "2", "1", "4");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Controller.setOut(System.out);
			
			controller.run();
			
			verify(mockOut, times(2)).println(contains("Bruce Wills"));
//			verify(mockOut).println(contains("Hard"));
//			verify(mockOut).println(contains("Currency US DOLLAR"));
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
	public void testBothSearch() {
		Connection connection = DAL.get().getConnection();
		
		if (connection == null) {
			fail("Could not connect to the database. Sorry.");
		}
		
		try{
			connection.setAutoCommit(false);    //begin transaction
			
			try {
				when(mockReader.readLine()).thenReturn("Jeff", "password", "1", "bruce wills", "3", "1", "4");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Controller.setOut(System.out);
			
			controller.run();
			
			verify(mockOut, times(2)).println(contains("Bruce Wills"));
//			verify(mockOut).println(contains("Hard"));
//			verify(mockOut).println(contains("Currency US DOLLAR"));
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
}
