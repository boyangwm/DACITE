package Test;

import static org.junit.Assert.*;

import org.junit.*;

import Model.*;


/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Anne Celestino
 * 
 *
 * @see Model.CurrencyType
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */

public class CurrencyType_Test {

	String name = "USD";
	Integer id = 1;
	CurrencyType temp = new CurrencyType(id, name);
	
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
		
		CurrencyType fixture = new CurrencyType(new Integer(1), "");
		assertEquals(31, fixture.hashCode());
		
		fixture = new CurrencyType(new Integer(1), (String) null);
		assertEquals(31, fixture.hashCode());
	}

	/**
	 * CreditCardType object creation test with a string paramater
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testCurrencyTypeString()
	{
		String name = "";
		CurrencyType result = new CurrencyType(name);
		assertNotNull(result);
	}
	
	/**
	 * CreditCardType object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testCurrencyType() {
		assertNotNull(temp);
	}

	/**
	 * Get ID test
	 * Pass Condition = CurrencyType.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(), id);
	}

	/**
	 * Set ID test
	 * Pass Condition = CurrencyType.getId() returns the alternate ID for the object that was set using CurrencyType.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		Integer tempId = 2;
		temp.setId(tempId);
		assertEquals(temp.getId(), tempId);
	}

	/**
	 * Get Name test
	 * Pass Condition = CurrencyType.getName() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetName() {
		assertEquals(temp.getName(), name);
	}

	/**
	 * Set Name test
	 * Pass Condition = CurrencyType.getName() returns the alternate attribute for the object that was set using CurrencyType.setName()
	 */
	@Test(timeout=5000)
	public void testSetName() {
		String tempName = "test";
		temp.setName(tempName);
		assertEquals(temp.getName(), tempName);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		CurrencyType other = temp;
		assertEquals(temp, other);
		
		CurrencyType fixture = new CurrencyType(new Integer(1), "");
		Object obj = new CurrencyType(new Integer(1), "");
		assertTrue(fixture.equals(obj));
		
		fixture = new CurrencyType(new Integer(1), "");
		obj = null;
		assertFalse(fixture.equals(obj));
		
		fixture = new CurrencyType(new Integer(1), "");
		obj = new Object();
		assertFalse(fixture.equals(obj));
		
		fixture = new CurrencyType(new Integer(1), "");
		obj = new CurrencyType("");
		assertTrue(fixture.equals(obj));
		
		fixture = new CurrencyType(new Integer(1), "");
		obj = new CurrencyType("");
		assertTrue(fixture.equals(obj));
		
		fixture = new CurrencyType(new Integer(1), (String) null);
		obj = new CurrencyType((String) null);
		assertTrue(fixture.equals(obj));
		
		fixture.setName("");
		((CurrencyType) obj).setName(null);
		assertFalse(fixture.equals(obj));
		
		fixture.setName(null);
		((CurrencyType) obj).setName("");
		assertFalse(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = CurrencyType.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}
}