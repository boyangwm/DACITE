package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.Account;
import Model.Movie;
import Model.Review;

/**
 * CS 442 Project Potholes
 * Review Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Arnold Lee
 * 
 *
 * @see Model.Review
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */

public class Review_Test {

	Integer u_id = 2;
	String first = "blah";
	String middle = "blahblah";
	String last = "blahblahblah";
	String street = "123 main street";
	String street2 = "";
	String city = "Beverly Hills";
	String state = "CA";
	String zip = "90210";
	String user = "whatever";
	String pw = "123456";

	Account t_account = new Account(u_id, first, middle, last, street, street2, city, state, zip, user, pw);

	Integer t_id = 1;
	Integer t_accountId = u_id;
	Integer t_movieId = 88;


	Movie t_movie = new Movie(t_accountId, city, t_accountId, t_accountId, t_accountId, t_accountId, city, city);

	String t_review = "You Suck!";
	Integer t_rating = 1;

	Review temp = new Review (t_id,t_accountId,t_movieId,t_review,t_rating);


	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 */
	@Test(timeout=5000)
	public void testHashCode() {
		assertNotNull(temp.hashCode());
	}

	/**
	 * Review object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testReview() {
		//constructor
		assertNotNull(temp);
	}

	
	/**
	 * Get ID test
	 * Pass Condition = Review.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(),t_id);
	}

	/**
	 * Set ID test
	 * Pass Condition = Review.getId() returns the alternate ID for the object
	 * that was set using Review.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		t_id = 5;
		temp.setId(t_id);
		assertEquals(temp.getId(),t_id);
	}
	/**
	 * Get Account ID test
	 * Pass Condition = Review.AccountId() returns correct account ID for the object
	 */
	@Test(timeout=5000)
	public void testGetAccountId() {
		assertEquals(temp.getAccountId(),t_accountId);
	}

	/**
	 * Set Account ID test
	 * Pass Condition = Review.getAccountId() returns the alternate ID for the object
	 * that was set using Review.setAccountId()
	 */
	@Test(timeout=5000)
	public void testSetAccountId() {
		t_accountId = 6;
		temp.setAccountId(t_accountId);
		assertEquals(temp.getAccountId(),t_accountId);
	}
	/**
	 * Get Movie ID test
	 * Pass Condition = Review.getMovieId() returns correct Movie ID for the object
	 */
	@Test(timeout=5000)
	public void testGetMovieId() {
		assertEquals(temp.getMovieId(),t_movieId);
	}

	/**
	 * Set Movie ID test
	 * Pass Condition = Review.getMovieId() returns the alternate movie ID for the object
	 * that was set using Review.setMovieId()
	 */
	@Test(timeout=5000)
	public void testSetMovieId() {
		t_movieId = 7;
		temp.setMovieId(t_movieId);
		assertEquals(temp.getMovieId(),t_movieId);
	}
	/**
	 * Get Account test
	 * Pass Condition = Review.getAcccount() returns correct account sub-object for the object
	 */
	@Test(timeout=5000)
	public void testGetAccount() {
		Review fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		Account result = fixture.getAccount();
		assertNotNull(result);
		assertEquals("Account \n{\nid=null, \nfirstName=, \nmiddleName=, \nlastName=, \naddress1=, \naddress2=, \ncity=, \nstate=, \nzipCode=, \nusername=, \npassword=, \nfavorites=null\n}", result.toString());
		assertEquals(null, result.getId());
		assertEquals("", result.getState());
		assertEquals("", result.getPassword());
		assertEquals("", result.getMiddleName());
		assertEquals("", result.getLastName());
		assertEquals("", result.getFirstName());
		assertEquals("", result.getCity());
		assertEquals("", result.getAddress1());
		assertEquals("", result.getZipCode());
		assertEquals("", result.getAddress2());
		assertEquals("", result.getUsername());

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		result = fixture.getAccount();
		assertNotNull(result);
		assertEquals("Account \n{\nid=1, \nfirstName=, \nmiddleName=, \nlastName=, \naddress1=, \naddress2=, \ncity=, \nstate=, \nzipCode=, \nusername=, \npassword=, \nfavorites=null\n}", result.toString());
		assertEquals(new Integer(1), result.getId());
		assertEquals("", result.getState());
		assertEquals("", result.getPassword());
		assertEquals("", result.getMiddleName());
		assertEquals("", result.getLastName());
		assertEquals("", result.getFirstName());
		assertEquals("", result.getCity());
		assertEquals("", result.getAddress1());
		assertEquals("", result.getZipCode());
		assertEquals("", result.getAddress2());
		assertEquals("", result.getUsername());
	}

	/**
	 * Get Movie test
	 * Pass Condition = Review.getMovie() returns correct Movie sub-object for the object
	 */

	@Test(timeout=5000)
	public void testGetMovie() {
		Review fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Review temp =  new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		Movie result = fixture.getMovie();
		Movie temp_movie = temp.getMovie();
		assertNotNull(result);
		assertEquals(temp_movie.toString(), result.toString());
		assertEquals(temp_movie.getId(), result.getId());
		assertEquals(temp_movie.getDescription(), result.getDescription());
		assertEquals(temp_movie.getTitle(), result.getTitle());
		assertEquals(temp_movie.getMovieData(), result.getMovieData());
		assertEquals(temp_movie.getEpisodeOfId(), result.getEpisodeOfId());
		assertEquals(temp_movie.getProductionYear(), result.getProductionYear());
		assertEquals(temp_movie.getSeasonNumber(), result.getSeasonNumber());
		assertEquals(temp_movie.getSeriesYears(), result.getSeriesYears());
		assertEquals(temp_movie.getEpisodeNumber(), result.getEpisodeNumber());
	}
	/**
	 * Get Review test
	 * Pass Condition = Review.getReview() returns correct Review for the object
	 */
	@Test(timeout=5000)
	public void testGetReview() {
		assertEquals(temp.getReview(),t_review);
	}

	/**
	 * Set Review test
	 * Pass Condition = Review.getReview() returns the alternate Review for the object
	 * that was set using Review.setReview()
	 */
	@Test(timeout=5000)
	public void testSetReview() {
		t_review = "You Rock!";
		temp.setReview(t_review);
		assertEquals(temp.getReview(),t_review);
	}
	/**
	 * Get Rating test
	 * Pass Condition = Review.getRating() returns correct rating for the object
	 */
	@Test(timeout=5000)
	public void testGetRating() {
		assertEquals(temp.getRating(),t_rating);
	}

	/**
	 * Set Rating test
	 * Pass Condition = Review.getRating() returns the alternate rating for the object
	 * that was set using Review.setRating()
	 */
	@Test(timeout=5000)
	public void testSetRating() {
		t_rating = 5;
		temp.setRating(t_rating);
		assertEquals(temp.getRating(),t_rating);
	}

	
	/**
	 * Object equality test
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		Review temp2 = temp;
		assertTrue(temp.equals(temp2) );

		Review fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Review objtemp = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		objtemp.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertTrue(fixture.equals(objtemp));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertFalse(fixture.equals(null));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertFalse(fixture.equals(new Object()));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Review obj = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		assertTrue(fixture.equals(obj));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		assertTrue(fixture.equals(obj));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		assertTrue(fixture.equals(obj));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		assertTrue(fixture.equals(obj));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Review(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1));
		assertTrue(fixture.equals(obj));

		fixture = new Review(new Integer(1), new Integer(1), new Integer(1), (String) null, new Integer(1));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Review(new Integer(1), new Integer(1), new Integer(1), (String) null, new Integer(1));
		assertTrue(fixture.equals(obj));
	}

	
	/**
	 * String generation test
	 * Pass Condition = Review.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}
}
