package Test;

import static org.junit.Assert.*;

import org.junit.*;

import Model.Movie;

/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Anne Celestino
 * 
 *
 * @see Model.Movie
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */

public class Movie_Test {

	Integer id = 1;
	String title = "Frozen";
	Integer productionYear = 2013;
	Integer episodeOfId = 1;
	Integer seasonNumber = 1;
	Integer episodeNumber = 1;
	String seriesYears = "one";
	String description = "Fearless optimist Anna teams up with Kristoff in an epic journey, encountering Everest-like conditions, and a hilarious snowman named Olaf in a race to find Anna's sister Elsa, whose icy powers have trapped the kingdom in eternal winter.";
	
	Movie temp = new Movie(id, title, productionYear, episodeOfId, seasonNumber, episodeNumber, seriesYears, description);
	
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
	
		Movie fixture = new Movie(new Integer(1), "", (Integer) null, (Integer) null, new Integer(1), (Integer) null, "", "");

		int result = fixture.hashCode();
		assertEquals(887504642, result);
	}

	@Test(timeout=5000)
	public void testMovie() {
		assertNotNull(temp);
	}

	/**
	 * Get ID test
	 * Pass Condition = Movie.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(), id);
	}

	/**
	 * Set ID test
	 * Pass Condition = Movie.getId() returns the alternate ID for the object that was set using Movie.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		Integer testInt = 2;
		temp.setId(testInt);
		assertEquals(temp.getId(), testInt);
	}

	/**
	 * Get Title test
	 * Pass Condition = Movie.getTitle() returns correct Title for the object
	 */
	@Test(timeout=5000)
	public void testGetTitle() {
		assertEquals(temp.getTitle(), title);
	}

	/**
	 * Set Title test
	 * Pass Condition = Movie.getTitle() returns the alternate title for the object that was set using Movie.setTitle()
	 */
	@Test(timeout=5000)
	public void testSetTitle() {
		String tempTitle = "Frozen - The Disney Movie";
		temp.setTitle(tempTitle);
		assertEquals(temp.getTitle(), tempTitle);
	}

	/**
	 * Get Production Year test
	 * Pass Condition = Movie.getProductionYear() returns correct Production Year for the object
	 */
	@Test(timeout=5000)
	public void testGetProductionYear() {
		assertEquals(temp.getProductionYear(), productionYear);
	}

	/**
	 * Set Production Year test
	 * Pass Condition = Movie.getProductionYear() returns the alternate title for the object that was set using Movie.setProductionYear()
	 */
	@Test(timeout=5000)
	public void testSetProductionYear() {
		Integer testPY = 2013;
		temp.setProductionYear(testPY);
		assertEquals(temp.getProductionYear(), testPY);
	}

	/**
	 * Get Episode of ID test
	 * Pass Condition = Movie.getEpisodeOfId() returns correct EpsisodeOfId for the object
	 */
	@Test(timeout=5000)
	public void testGetEpisodeOfId() {
		assertEquals(temp.getEpisodeOfId(), episodeOfId);
	}

	/**
	 * Set Episode of Id test
	 * Pass Condition = Movie.getEpisodeOfId() returns the alternate EpsisodeOfId for the object that was set using Movie.setEpisodeOfId()
	 */
	@Test(timeout=5000)
	public void testSetEpisodeOfId() {
		Integer testEOI = 1;
		temp.setEpisodeOfId(testEOI);
		assertEquals(temp.getEpisodeOfId(), testEOI);
	}

	/**
	 * Get Season Number test
	 * Pass Condition = Movie.getSeasonNumber() returns correct Season Number for the object
	 */
	@Test(timeout=5000)
	public void testGetSeasonNumber() {
		assertEquals(temp.getSeasonNumber(), seasonNumber);
	}

	/**
	 * Set Season Number test
	 * Pass Condition = Movie.getSeasonNumber() returns the alternate season number for the object that was set using Movie.setSeasonNumber()
	 */
	@Test(timeout=5000)
	public void testSetSeasonNumber() {
		Integer testSN = 1;
		temp.setSeasonNumber(testSN);
		assertEquals(temp.getSeasonNumber(), testSN);
	}

	/**
	 * Get Episode Number test
	 * Pass Condition = Movie.getEpisodeNumber() returns correct Episode Number for the object
	 */
	@Test(timeout=5000)
	public void testGetEpisodeNumber() {
		assertEquals(temp.getEpisodeNumber(), episodeNumber);
	}

	/**
	 * Set Episode Number test
	 * Pass Condition = Movie.getEpisodeNumber() returns the alternate episode number for the object that was set using Movie.setEpisodeNumber()
	 */
	@Test(timeout=5000)
	public void testSetEpisodeNumber() {
		Integer testEN = 1;
		temp.setEpisodeNumber(testEN);
		assertEquals(temp.getEpisodeNumber(), testEN);
	}

	/**
	 * Get Series Year test
	 * Pass Condition = Movie.getSeriesYear() returns correct Series Year for the object
	 */
	@Test(timeout=5000)
	public void testGetSeriesYears() {
		assertEquals(temp.getSeriesYears(), seriesYears);
	}

	/**
	 * Set Series Year test
	 * Pass Condition = Movie.getSeriesYear() returns the alternate Series Year for the object that was set using Movie.setSeriesYear()
	 */
	@Test(timeout=5000)
	public void testSetSeriesYears() {
		String testSY = "one";
		temp.setSeriesYears(testSY);
		assertEquals(temp.getSeriesYears(), testSY);
	}

	/**
	 * Get Description test
	 * Pass Condition = Movie.getDescription() returns correct Description for the object
	 */
	@Test(timeout=5000)
	public void testGetDescription() {
		assertEquals(temp.getDescription(), description);
		
		Movie fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", (String) null);
		assertNull(fixture.getDescription());
		
		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertEquals("", fixture.getDescription());
	}

	/**
	 * Set Description test
	 * Pass Condition = Movie.getDescription() returns the alternate Description for the object that was set using Movie.setDescription()
	 */
	@Test(timeout=5000)
	public void testSetDescription() {
		String testDesc = "A story about two siblings who reunite.";
		temp.setDescription(testDesc);
		assertEquals(temp.getDescription(), testDesc);
	}

	/**
	 * Get Writers test
	 * Pass Condition = Movie.getWriters() returns correct Writers for the object
	 */
	@Test(timeout=5000)
	public void testGetWriters() {
		assertNotNull(temp.getWriters());
	}

	/**
	 * Get Producers test
	 * Pass Condition = Movie.getProducers() returns correct producers for the object
	 */
	@Test(timeout=5000)
	public void testGetProducers() {
		assertNotNull(temp.getProducers());
	}

	/**
	 * Get Directors test
	 * Pass Condition = Movie.getDirectors() returns correct directors for the object
	 */
	@Test(timeout=5000)
	public void testGetDirectors() {
		assertNotNull(temp.getDirectors());
	}

	/**
	 * Get Cast test
	 * Pass Condition = Movie.getCast() returns correct cast for the object
	 */
	@Test(timeout=5000)
	public void testGetCast() {
		assertNotNull(temp.getCast());
	}

	
	/*  
	 * commented out 
	 * DAL functionality required*/
	@Test(timeout=5000)
	public void testGetMovieData() {
		Movie fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertNotNull(fixture.getMovieData());
		
		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertNotNull(fixture.getMovieData());
	}

	/**
	 * Get Reviews test
	 * Pass Condition = Movie.getReviews() returns correct reviews for the object
	 */
	@Test(timeout=5000)
	public void testGetReviews() {
		assertNotNull(temp.getReviews());
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		
		Movie other = temp;
		assertEquals(other, temp);
		
		Movie fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		Movie obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = null;
		assertFalse(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		Object obj2 = new Object();
		assertFalse(fixture.equals(obj2));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		obj = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));
		
		fixture.setEpisodeNumber(null);
		obj.setEpisodeNumber(1);
		assertFalse(fixture.equals(obj));
		
		fixture.setEpisodeNumber(1);
		obj.setEpisodeNumber(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setEpisodeNumber(null);
		obj.setEpisodeNumber(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setEpisodeOfId(null);
		obj.setEpisodeOfId(1);
		assertFalse(fixture.equals(obj));
		
		fixture.setEpisodeOfId(1);
		obj.setEpisodeOfId(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setEpisodeOfId(null);
		obj.setEpisodeOfId(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setProductionYear(null);
		obj.setProductionYear(1);
		assertFalse(fixture.equals(obj));
		
		fixture.setProductionYear(1);
		obj.setProductionYear(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setProductionYear(null);
		obj.setProductionYear(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setSeasonNumber(null);
		obj.setSeasonNumber(1);
		assertFalse(fixture.equals(obj));
		
		fixture.setSeasonNumber(1);
		obj.setSeasonNumber(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setSeasonNumber(null);
		obj.setSeasonNumber(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setSeriesYears(null);
		obj.setSeriesYears("");
		assertFalse(fixture.equals(obj));
		
		fixture.setSeriesYears("");
		obj.setSeriesYears(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setSeriesYears(null);
		obj.setSeriesYears(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setTitle(null);
		obj.setTitle("");
		assertFalse(fixture.equals(obj));
		
		fixture.setTitle("");
		obj.setTitle(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setTitle(null);
		obj.setTitle(null);
		assertTrue(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = Movie.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
		
		Movie fixture = new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", "");
		String result = fixture.toString();
		assertNotNull(result);
	}

}
