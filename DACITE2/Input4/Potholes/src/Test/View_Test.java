package Test;

import View.View;
import Model.*;
import Model.CreditCardType.Type;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * CS 442 Project Potholes
 * Review Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Walter Dworak 
 * 
 *
 * @see View
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */
public class View_Test {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    View.get().setOut(System.out);
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithEmptyArrays() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		ArrayList<Person> testPeople = new ArrayList<Person>();
		View.get().displayBothResults(testMovies, testPeople);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}

	@Test(timeout=5000)
	public void testDisplayBothResultsWithEmptyMovieArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));;
		View.get().displayBothResults(testMovies, testPeople);
		assertTrue("It should contain 'John Smith'", outContent.toString().contains("John Smith"));
		assertTrue("It should contain 'Jane Smith'", outContent.toString().contains("Jane Smith"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithEmptyPeopleArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(new Movie(2, "Wayne's World 2", 1993, 0 ,0, 0, "1993", "Another Movie"));
		ArrayList<Person> testPeople = new ArrayList<Person>();
		View.get().displayBothResults(testMovies, testPeople);
		assertTrue("It should contain Wayne's World", outContent.toString().contains("Wayne's World"));
		assertTrue("It should contain Wayne's World 2", outContent.toString().contains("Wayne's World 2"));
		assertEquals("", errContent.toString());
	}

	@Test(timeout=5000)
	public void testDisplayBothResultsWithMalformedArrays() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(null);
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(null);
		View.get().displayBothResults(testMovies, testPeople);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithMalformedMovieArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(null);
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));
		View.get().displayBothResults(testMovies, testPeople);
		assertTrue(outContent.toString().contains("John Smith"));
		assertTrue(outContent.toString().contains("Jane Smith"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithMalformedPeopleArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(new Movie(2, "Wayne's World 2", 1993, 0 ,0, 0, "1993", "Another Movie"));
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(null);
		View.get().displayBothResults(testMovies, testPeople);
		
		//assertEquals("1) Wayne's World\n2) Wayne's World 2\n", outContent.toString());
		
		assertTrue("It should contain Wayne's World", outContent.toString().contains("Wayne's World"));
		assertTrue("It should contain Wayne's World 2", outContent.toString().contains("Wayne's World 2"));
		
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithNullArrays() {
		View.get().displayBothResults(null, null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithNullMovieArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));
		View.get().displayBothResults(null, testPeople);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithNullPeopleArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(new Movie(2, "Wayne's World 2", 1993, 0 ,0, 0, "1993", "Another Movie"));
		View.get().displayBothResults(testMovies, null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayBothResultsWithPopulatedArrays() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(new Movie(2, "Wayne's World 2", 1993, 0 ,0, 0, "1993", "Another Movie"));
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));
		View.get().displayBothResults(testMovies, testPeople);
		assertTrue("It should contain Wayne's World", outContent.toString().contains("Wayne's World"));
		assertTrue("It should contain Wayne's World 2", outContent.toString().contains("Wayne's World 2"));
		assertTrue("It should contain 'John Smith'", outContent.toString().contains("John Smith"));
		assertTrue("It should contain 'Jane Smith'", outContent.toString().contains("Jane Smith"));
		assertEquals("", errContent.toString());
	}

	@Test(timeout=5000)
	public void testDisplayConfigurationSettingsWithEmptyArray() {
		ArrayList<Configuration> testConfigs = new ArrayList<Configuration>();
		View.get().displayConfigurationSettings(testConfigs);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());

	}
	
	@Test(timeout=5000)
	public void testDisplayConfigurationSettingsWithMalformedArray() {
		ArrayList<Configuration> testConfigs = new ArrayList<Configuration>();
		testConfigs.add(null);
		View.get().displayConfigurationSettings(testConfigs);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayConfigurationSettingsWithPopulatedArray() {
		ArrayList<Configuration> testConfigs = new ArrayList<Configuration>();
		testConfigs.add(new Configuration("testVal1", "1"));
		testConfigs.add(new Configuration("testVal2", "2"));
		testConfigs.add(new Configuration("testVal3", "3"));
		View.get().displayConfigurationSettings(testConfigs);
		
		assertTrue("It should contain testVal1", outContent.toString().contains("testVal1"));
		assertTrue("It should contain testVal2", outContent.toString().contains("testVal2"));
		assertTrue("It should contain testVal3", outContent.toString().contains("testVal3"));
		
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayConfiurationSettingsWithNullArray() {
		View.get().displayConfigurationSettings(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayCreditCardTypesWithMalformedArray() {
		ArrayList<CreditCardType> testCreditCards = new ArrayList<CreditCardType>();
		testCreditCards.add(new CreditCardType(Type.VISA));
		testCreditCards.add(new CreditCardType(Type.MASTERCARD));
		testCreditCards.add(null);
		View.get().displayCreditCardTypes(testCreditCards);
		String content = outContent.toString(); 
		assertTrue(content.toString().contains("VISA"));
		assertTrue(content.toString().contains("MASTERCARD"));
		assertEquals("", errContent.toString());
		
	}
	
	@Test(timeout=5000)
	public void testDisplayCreditCardTypesWithNullArray() {
		View.get().displayCreditCardTypes(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayCreditCardTypesWithPopulatedArray() {
		ArrayList<CreditCardType> testCreditCards = new ArrayList<CreditCardType>();
		testCreditCards.add(new CreditCardType(Type.VISA));
		testCreditCards.add(new CreditCardType(Type.MASTERCARD));
		testCreditCards.add(new CreditCardType(Type.AMERICAN_EXPRESS));
		View.get().displayCreditCardTypes(testCreditCards);
		assertTrue(outContent.toString().contains("VISA") && outContent.toString().contains("MASTERCARD"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayExistingPaymentMethodsWithMalformedArray() {
		ArrayList<PaymentMethod> testPaymentMethods = new ArrayList<PaymentMethod>();
		PaymentMethod testPay1 = new PaymentMethod(0, 12345, 1, "123456789", 8, 2020, 213, "John J Doe", "60634");
		testPay1.setCreditCardType(new CreditCardType(Type.VISA));
		testPaymentMethods.add(testPay1);
		PaymentMethod testPay2 = new PaymentMethod(1, 67890, 2, "987654321", 7, 2022, 213, "Jane M Doe", "60654");
		testPay2.setCreditCardType(new CreditCardType(Type.MASTERCARD));
		testPaymentMethods.add(testPay2);
		testPaymentMethods.add(null);
		View.get().displayExistingPaymentMethods(testPaymentMethods);
		assertTrue("1) Type: VISA, Card #: 123456789 2) Type: MASTERCARD, Card #: 987654321", outContent.toString().contains(testPay1.getCreditCardNumber()));
		assertEquals("", errContent.toString());
	} 
	
	@Test(timeout=5000)
	public void testDisplayExistingPaymentMethodsWithNullArray() {
		View.get().displayExistingPaymentMethods(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayExistingPaymentMethodsWithPopulatedArray() {
		ArrayList<PaymentMethod> testPaymentMethods = new ArrayList<PaymentMethod>();
		PaymentMethod testPay1 = new PaymentMethod(0, 12345, 1, "123456789", 8, 2020, 213, "John J Doe", "60634");
		testPay1.setCreditCardType(new CreditCardType(Type.VISA));
		testPaymentMethods.add(testPay1);
		PaymentMethod testPay2 = new PaymentMethod(1, 67890, 2, "987654321", 7, 2022, 213, "Jane M Doe", "60654");
		testPay2.setCreditCardType(new CreditCardType(Type.MASTERCARD));
		testPaymentMethods.add(testPay2);
		PaymentMethod testPay3 = new PaymentMethod(2, 01234, 3, "123459876", 6, 2018, 213, "Mike F Doe", "60994");
		testPay3.setCreditCardType(new CreditCardType(Type.AMERICAN_EXPRESS));
		testPaymentMethods.add(testPay3);
		View.get().displayExistingPaymentMethods(testPaymentMethods);
		
		//assertEquals("1) Type: VISA, Card #: 123456789\n2) Type: MASTERCARD, Card #: 987654321\n3) Type: AMERICAN EXPRESS, Card #: 123459876\n", outContent.toString());
		
		assertTrue(outContent.toString().contains("VISA"));
		assertTrue(outContent.toString().contains("123456789"));
		assertTrue(outContent.toString().contains("MASTERCARD"));
		assertTrue(outContent.toString().contains("987654321"));
		
		
		
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayFavoritesWithEmptyArray() {
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		ArrayList<Movie> testFavorites = new ArrayList<Movie>();
		testAccount.setFavorites(testFavorites);
		View.get().displayFavorites(testAccount);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}

	@Test(timeout=5000)
	public void testDisplayFavoritesWithMalformedArray() {
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		ArrayList<Movie> testFavorites = new ArrayList<Movie>();
		testFavorites.add(testMovie);
		testFavorites.add(null);
		testAccount.setFavorites(testFavorites);
		View.get().displayFavorites(testAccount);
		assertTrue(outContent.toString().contains("Wayne's World"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayFavoritesWithNullArray() {
		View.get().displayFavorites(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayFavoritesWithPopulatedArray() {
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		ArrayList<Movie> testFavorites = new ArrayList<Movie>();
		testFavorites.add(testMovie);
		testAccount.setFavorites(testFavorites);
		View.get().displayFavorites(testAccount);
		assertTrue(testMovie.getTitle(), outContent.toString().contains(testMovie.getTitle()));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieInformationWithEmptyPersonsAndReviews() {
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		String reviewString = "This movie was so da bomb, know what I'm sayin?";
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Review testReview = new Review(0, testAccount.getId(), testMovie.getId(), reviewString, 10);
		testReview.setAccount(testAccount);
		
		ArrayList<Person> testCast = new ArrayList<Person>();
		ArrayList<Person> testDirectors = new ArrayList<Person>();
		ArrayList<Person> testProducers = new ArrayList<Person>();
		ArrayList<Person> testWriters = new ArrayList<Person>();
		ArrayList<Review> testReviews = new ArrayList<Review>();

		testMovie.setCast(testCast);
		testMovie.setDirectors(testDirectors);
		testMovie.setProducers(testProducers);
		testMovie.setWriters(testWriters);
		testMovie.setReviews(testReviews);
		View.get().displayMovieInformation(testMovie);
		assertTrue(outContent.toString().contains(testMovie.getTitle()));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieInformationWithNullMovie() {
		View.get().displayMovieInformation(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieResultsWithEmptyArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		View.get().displayMovieResults(testMovies);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieResultsWithMalformedArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(null);
		View.get().displayMovieResults(testMovies);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieResultsWithNullArray() {
		View.get().displayMovieResults(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieResultsWithPopulatedArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		View.get().displayMovieResults(testMovies);

		assertTrue(outContent.toString().contains("Wayne's World"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieWithEmptyArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		View.get().displayMovie(testMovies);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieWithMalformedArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(null);
		View.get().displayMovie(testMovies);
		assertTrue(outContent.toString().contains("Wayne's World"));
		assertEquals("", errContent.toString());
	}

	@Test(timeout=5000)
	public void testDisplayMovieWithNullArray() {
		View.get().displayMovie(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayMovieWithPopulatedArray() {
		ArrayList<Movie> testMovies = new ArrayList<Movie>();
		testMovies.add(new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers"));
		testMovies.add(new Movie(2, "Wayne's World 2", 1993, 0 ,0, 0, "1993", "Another Movie"));
		View.get().displayMovie(testMovies);
		assertTrue("It should contain Wayne's World", outContent.toString().contains("Wayne's World"));
		assertTrue("It should contain Wayne's World 2", outContent.toString().contains("Wayne's World 2"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayOrdersWithEmptyArray() {
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayOrdersWithMalformedArray() {
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		PaymentMethod testPay1 = new PaymentMethod(0, 12345, 1, "123456789", 8, 2020, 213, "John J Doe", "60634");
		testPay1.setCreditCardType(new CreditCardType(Type.VISA));
		Order testOrder = new Order(0, 1, 2, 0);
		testOrder.setMovie(testMovie);
		testOrder.setAccount(testAccount);
		testOrder.setOrderType(new OrderType(OrderType.getTypeFromNumber(0)));
		testOrder.setPaymentMethod(testPay1);
		ArrayList<Order> testOrders = new ArrayList<Order>();
		testOrders.add(testOrder);
		testOrders.add(null);
		View.get().displayOrders(testOrders);
		assertTrue(outContent.toString().contains(testMovie.getTitle()));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayOrdersWithNullArray() {
		View.get().displayOrders(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}	

	@Test(timeout=5000)
	public void testDisplayOrdersWithPopulatedArray() {
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		PaymentMethod testPay1 = new PaymentMethod(0, 12345, 1, "123456789", 8, 2020, 213, "John J Doe", "60634");
		testPay1.setCreditCardType(new CreditCardType(Type.VISA));
		Order testOrder = new Order(0, 1, 2, 0);
		testOrder.setMovie(testMovie);
		testOrder.setAccount(testAccount);
		testOrder.setOrderType(new OrderType(OrderType.getTypeFromNumber(0)));
		testOrder.setPaymentMethod(testPay1);
		ArrayList<Order> testOrders = new ArrayList<Order>();
		testOrders.add(testOrder);
		View.get().displayOrders(testOrders);
		assertTrue("Movie: " + testMovie.getTitle() + "Order-Type: RENTAL" + "Payment-Method: VISA 123456789", outContent.toString().contains(testMovie.getTitle()));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleResultsWithEmptyArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		View.get().displayPeopleResults(testPeople);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleResultsWithMalformedArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(null);
		View.get().displayPeopleResults(testPeople);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleResultsWithNullArray() {
		View.get().displayPeopleResults(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleResultsWithPopulatedArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));
		View.get().displayPeopleResults(testPeople);
		assertTrue("It should contain 'John Smith'", outContent.toString().contains("John Smith"));
		assertTrue("It should contain 'Jane Smith'", outContent.toString().contains("Jane Smith"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleWithEmptyArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		View.get().displayPeople(testPeople);
		
		//assertEquals("outContent.toString());
		
		assertTrue("It should contain None.", outContent.toString().contains("None."));
		assertEquals("", errContent.toString()); 
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleWithMalformedArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(null);
		View.get().displayPeople(testPeople);
		assertTrue(outContent.toString().contains("John Smith"));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleWithNullArray() {
		View.get().displayPeople(null);
		assertTrue("It should contain None.", outContent.toString().contains("None."));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPeopleWithPopulatedArray() {
		ArrayList<Person> testPeople = new ArrayList<Person>();
		testPeople.add(new Person(1, "John", "Smith", 'm'));
		testPeople.add(new Person(2, "Jane", "Smith", 'f'));
		View.get().displayPeople(testPeople);
		
		//assertEquals("John Smith\nJane Smith\n", outContent.toString());
		
		  assertTrue("It should contain 'John Smith'", outContent.toString().contains("John Smith"));
		  assertTrue("It should contain 'Jane Smith'", outContent.toString().contains("Jane Smith"));
		
		
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPersonInformationWithBio() {
		Person testCastMember1 = new Person(1, "Cassy", "Caster", 'm');

		ArrayList<Movie> testMovies = new ArrayList<Movie>();

		testCastMember1.setBiography("This person was pretty chill.");
		testCastMember1.setMoviesAsACastmember(testMovies);
		testCastMember1.setMoviesAsADirector(testMovies);
		testCastMember1.setMoviesAsAProducer(testMovies);
		testCastMember1.setMoviesAsAWriter(testMovies);
		View.get().displayPersonInformation(testCastMember1);
		assertTrue(outContent.toString().contains(testCastMember1.getFirstName()));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayPersonInformationWithNullPerson() {
		View.get().displayPersonInformation(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayReviewsWithEmptyArray() {
		ArrayList<Review> testReviews = new ArrayList<Review>();
		View.get().displayReviews(testReviews);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayReviewsWithMalformedArray() {
		ArrayList<Review> testReviews = new ArrayList<Review>();
		testReviews.add(null);
		View.get().displayReviews(testReviews);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayReviewsWithNullArray() {
		View.get().displayReviews(null);
		assertEquals("", outContent.toString());
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testDisplayReviewsWithPopulatedArray() {
		String reviewString = "This movie was so da bomb, know what I'm sayin?";
		Account testAccount = new Account(0, "John", "James", "Doe", "1234 Fake St", "", "Chicago", "IL", "60634", "jdoe", "password");
		Movie testMovie = new Movie(1, "Wayne's World", 1985, 0, 0, 0, "1985", "Movie with Mike Myers");
		Review testReview = new Review(0, testAccount.getId(), testMovie.getId(), reviewString, 10);
		testReview.setAccount(testAccount);
		ArrayList<Review> testReviews = new ArrayList<Review>();
		testReviews.add(testReview);
		View.get().displayReviews(testReviews);
		assertTrue(outContent.toString().contains(reviewString));
		assertEquals("", errContent.toString());
	}
	
	@Test(timeout=5000)
	public void testGetReturnsNotNull() {
		assertNotNull(View.get());
	}
	
	@Test(timeout=5000)
	public void testGetReturnsNotNullWhenNotNull() {
		View.get();
		assertNotNull(View.get());
	}
}