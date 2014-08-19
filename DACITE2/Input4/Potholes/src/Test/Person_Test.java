package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Model.*;
/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Arnold Lee
 * 
 *
 * @see Model.Person
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */
public class Person_Test {

	Integer id = 2;
	String firstName = "Hello";
	String lastName = "Kitty";
	Character gender = 'T';
	String biography = "Nothing to see here, move along.";
	Person temp = new Person (id, firstName, lastName, gender);
	
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
		
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		assertEquals(32, fixture.hashCode());
		
	    fixture = new Person((Integer) null, "", "", new Character(''));
		fixture.setBiography("");
		assertEquals(31, fixture.hashCode());
	}


	/**
	 * Person object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testPerson() {
		assertNotNull(temp);
		
		Integer id = new Integer(1);
		String firstName = "";
		String lastName = "";
		Character gender = new Character('');
		Person result = new Person(id, firstName, lastName, gender);
		assertNotNull(result);
		assertEquals("Person \n{\nid=1, \nfirstName=, \nlastName=, \ngender=, \nbiography=null\n}", result.toString());
		assertEquals(new Integer(1), result.getId());
		assertEquals("", result.getLastName());
		assertEquals("", result.getFirstName());
		assertNull(result.getBiography());
		assertEquals(new Character(''), result.getGender());
	}
	/**
	 * Get ID test
	 * Pass Condition = Person.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(),id);
	}
	
	/**
	 * Set ID test
	 * Pass Condition = Person.getId() returns the alternate ID for the object
	 * that was set using Person.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		Integer x = 1337;
		temp.setId(x);
		assertEquals(temp.getId(),x);
	}

	/**
	 * Get First Name test
	 * Pass Condition = Person.getfirstname() returns correct first name for the object
	 */
	@Test(timeout=5000)
	public void testGetFirstName() {
		assertEquals(temp.getFirstName(),firstName);
	}

	/**
	 * Set First Name test
	 * Pass Condition = Person.getFirstName() returns the alternate for the object
	 * that was set using Person.setFirstName()
	 */
	@Test(timeout=5000)
	public void testSetFirstName() {
		String x = "Harry";
		temp.setFirstName(x);
		assertEquals(temp.getFirstName(),x);
	}
	/**
	 * Get Last Name test
	 * Pass Condition = Person.getLastName() returns correct last name  for the object
	 */
	@Test(timeout=5000)
	public void testGetLastName() {
		assertEquals(temp.getLastName(),lastName);
	}

	/**
	 * Set Last Name test
	 * Pass Condition = Person.getLastName() returns the alternate for the object
	 * that was set using Person.setLastName()
	 */
	@Test(timeout=5000)
	public void testSetLastName() {
		String x = "Potter";
		temp.setLastName(x);
		assertEquals(temp.getLastName(),x);
	}

	/**
	 * Get gender test
	 * Pass Condition = Person.getGender() returns gender for the object
	 */
	@Test(timeout=5000)
	public void testGetGender() {
		assertEquals(temp.getGender(),gender);
	}

	/**
	 * Set Gender test
	 * Pass Condition = Person.getGender() returns the alternate for the object
	 * that was set using Person.setGender()
	 */
	@Test(timeout=5000)
	public void testSetGender() {
		gender = 'U';
		temp.setGender(gender);
		assertEquals(temp.getGender(),gender);
	}
 
	/**
	 * Get biography test
	 * Pass Condition = Person.getBiograpgy() returns correct biographyfor the object
	 */
	@Test(timeout=5000)
	public void testGetBiography() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography((String) null);
		String result = fixture.getBiography();
		assertNull(result);

		fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		result = fixture.getBiography();
		assertEquals("", result);		
	}
	/**
	 * Get movie as cast member test
	 * Pass Condition = Person.getMovieasCastMember() returns correct Movie for the object
	 * where the person object in question is part of movie cast member list
	 */
	@Test(timeout=5000)
	public void testGetMoviesAsACastmember() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		ArrayList<Movie> result = fixture.getMoviesAsACastmember();
		assertNotNull(result);
		assertEquals(1, result.size());		
	}
	/**
	 * Get movie as director test
	 * Pass Condition = Person.getMovieasDirector() returns correct Movie for the object
	 * where the person object in question is part of movie director list
	 */
	@Test(timeout=5000)
	public void testGetMoviesAsADirector() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		ArrayList<Movie> result = fixture.getMoviesAsADirector();
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	/**
	 * Get movie as cast member test
	 * Pass Condition = Person.getMovieasProducer() returns correct Movie for the object
	 * where the person object in question is part of movie producer list
	 */
	@Test(timeout=5000)
	public void testGetMoviesAsAProducer() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		ArrayList<Movie> result = fixture.getMoviesAsAProducer();
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	/**
	 * Get movie as writer test
	 * Pass Condition = Person.getMovieasWriter() returns correct Movie for the object
	 * where the person object in question is part of movie writer list
	 */
	@Test(timeout=5000)
	public void testGetMoviesAsAWriter() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		ArrayList<Movie> result = fixture.getMoviesAsAWriter();
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	/**
	 * Object equality test
	 *
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		Person obj = new Person(new Integer(1), "", "", new Character(''));
		obj.setBiography("");
		assertTrue(fixture.equals(obj));

		fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		assertFalse(fixture.equals(null));

		fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		assertFalse(fixture.equals(new Object()));

		fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		obj = new Person(new Integer(1), "", "", new Character(''));
		assertTrue(fixture.equals(obj));

		fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		obj = new Person(new Integer(1), "", "", new Character(''));
		assertTrue(fixture.equals(obj));
	}
	/**
	 * String generation test
	 * Pass Condition = Person.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		Person fixture = new Person(new Integer(1), "", "", new Character(''));
		fixture.setBiography("");
		String result = fixture.toString();
		assertEquals("Person \n{\nid=1, \nfirstName=, \nlastName=, \ngender=, \nbiography=\n}", result);
	}
}
