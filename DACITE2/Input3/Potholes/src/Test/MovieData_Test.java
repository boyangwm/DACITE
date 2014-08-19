package Test;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;

import Model.MovieData;


/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Anne Celestino
 * 
 *
 * @see Model.MovieData
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */

public class MovieData_Test {
	
	Integer id = 1;
	String data = "Sample Movie Data!";
	MovieData temp = new MovieData(id, data);

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 */
	@Test(timeout=5000)
	public void testHashCode() {
		MovieData obj = new MovieData(null, null);
		
		assertNotNull(obj.hashCode());
		
		assertNotNull(temp.hashCode());
	}

	/**
	 * MovieData object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testMovieData() {
		assertNotNull(temp);
	}

	/**
	 * Get ID test
	 * Pass Condition = MovieData.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(), id);
	}

	/**
	 * Set ID test
	 * Pass Condition = MovieData.getId() returns the alternate ID for the object that was set using MovieDat.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		Integer testSID = 2;
		temp.setId(testSID);
		assertEquals(temp.getId(), testSID);
	}

	/**
	 * Get ID test
	 * Pass Condition = MovieData.getData() returns correct data for the object
	 */
	@Test(timeout=5000)
	public void testGetData() {
		assertEquals(temp.getData(), data);
	}

	/**
	 * Set ID test
	 * Pass Condition = MovieData.getData() returns the alternate data for the object that was set using MovieDat.setData()
	 */
	@Test(timeout=5000)
	public void testSetData() {
		String testSD = "Sample Sample!";
		temp.setData(testSD);
		assertEquals(temp.getData(), testSD);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		MovieData other = temp;
		assertEquals(other, temp);
		
		MovieData fixture = new MovieData(new Integer(1), "");
		Object obj = new MovieData(new Integer(1), "");
		assertTrue(fixture.equals(obj));

		fixture = new MovieData(new Integer(1), "");
		Object obj2 = null;
		assertFalse(fixture.equals(obj2));

		fixture = new MovieData(new Integer(1), "");
		Object obj3 = new Object();
		assertFalse(fixture.equals(obj3));

		fixture = new MovieData(new Integer(1), "");
		obj = new MovieData(new Integer(1), "");
		assertTrue(fixture.equals(obj));

		fixture = new MovieData((Integer) null, "");
		obj = new MovieData((Integer) null, "");
		assertTrue(fixture.equals(obj));
		
		fixture.setId(null);
		((MovieData) obj).setId(1);
		assertFalse(fixture.equals(obj));
		
		fixture.setId(1);
		((MovieData) obj).setId(null);
		assertFalse(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = MovieData.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}

}
