package Test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import Model.Configuration;

/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Anne Celestino
 * 
 *
 * @see Model.Configuration
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */


public class Configuration_Test {
	
	String attribute = "Currency Settings";
	String value = "USD";
	Configuration temp = new Configuration(attribute, value);
	
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 */
	@Test(timeout=5000)
	public void testHashCode() {
		
		Configuration fixture = new Configuration((String) null, "");
		assertEquals(961, fixture.hashCode());
		
		fixture = new Configuration("", (String) null);
		assertEquals(961, fixture.hashCode());
	}

	/**
	 * Configuration object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testConfiguration() {
		assertNotNull(temp);
	}

	/**
	 * Get Attribute test
	 * Pass Condition = Configuration.getAttribute() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetAttribute() {
		assertEquals(temp.getAttribute(), attribute);
	}

	/**
	 * Set Attribute test
	 * Pass Condition = Configuration.setAttribute() returns the alternate attribute for the object that was set using Configuration.setAttribute()
	 */
	@Test(timeout=5000)
	public void testSetAttribute() {
		String s = "Currency Settings";
		temp.setAttribute(s);
		assertEquals(temp.getAttribute(), s);
	}

	/**
	 * Get Value test
	 * Pass Condition = Configuration.getValue() returns correct value for the object
	 */
	@Test(timeout=5000)
	public void testGetValue() {
		assertEquals(temp.getValue(), value);
	}


	/**
	 * Set Value test
	 * Pass Condition = Configuration.getValue() returns the alternate attribute for the object that was set using Configuration.setValue()
	 */
	@Test(timeout=5000)
	public void testSetValue() {
		String s = "US";
		temp.setValue(s);
		assertEquals(temp.getValue(), s);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		Configuration other = temp;
		
		assertEquals(other, temp);
		
		Configuration fixture = new Configuration("", "");
		Object obj = new Configuration("", "");
		assertTrue(fixture.equals(obj));
		
		fixture = new Configuration("", "");
		obj = null;
		assertFalse(fixture.equals(obj));
		
		fixture = new Configuration("", "");
		obj = new Object();
		assertFalse(fixture.equals(obj));
		
		fixture = new Configuration("", "");
		obj = new Configuration("", "");
		assertTrue(fixture.equals(obj));
		
		fixture = new Configuration("", "");
		obj = new Configuration("", "");
		assertTrue(fixture.equals(obj));
		
		fixture = new Configuration("", "");
		obj = new Configuration("", "");
		assertTrue(fixture.equals(obj));
		
		fixture.setAttribute(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setAttribute(null);
		((Configuration) obj).setAttribute(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setValue(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setValue(null);
		((Configuration) obj).setValue(null);
		assertTrue(fixture.equals(obj));
		
		fixture.setValue("");
		((Configuration) obj).setValue(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setAttribute("");
		((Configuration) obj).setAttribute(null);
		assertFalse(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = Configuration.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}
}
