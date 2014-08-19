package Test;

import static org.junit.Assert.*;

import org.junit.*;

import Model.*;
import Model.CreditCardType.*;

/**
 * CS 442 Project Potholes
 * Person Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Anne Celestino
 * 
 *
 * @see Model.CreditCardType
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */

public class CreditCardType_Test {
	
	Integer id = 1;
	String name = "VISA";
	CreditCardType temp = new CreditCardType(id, name);

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * CreditCardType object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testCreditCardType() {
		CreditCardType tempCCT = new CreditCardType(CreditCardType.Type.VISA);
		assertNotNull(tempCCT);
		
		tempCCT = new CreditCardType(CreditCardType.Type.MASTERCARD);
		assertNotNull(tempCCT);
		
		tempCCT= new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS);
		assertNotNull(tempCCT);
	}	
	
	/**
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 */
	@Test(timeout=5000)
	public void testHashCode() {
		assertNotNull(temp.hashCode());
		
		CreditCardType fixture = new CreditCardType(new Integer(1), "");
		assertEquals(31, fixture.hashCode());
		
		fixture = new CreditCardType(new Integer(1), (String) null);
		assertEquals(31, fixture.hashCode());
	}

	/**
	 * Get type from number test
	 * Pass Condition = given a proper type number, yield the proper type string
	 */
	@Test(timeout=5000)
	public void testGetTypeFromNumber() {
		Integer number = 1;
		assertEquals(CreditCardType.getTypeFromNumber(number), CreditCardType.Type.VISA);
		
		number = 2;
		assertEquals(CreditCardType.getTypeFromNumber(number), CreditCardType.Type.MASTERCARD);

		number = 3;
		assertEquals(CreditCardType.getTypeFromNumber(number), CreditCardType.Type.AMERICAN_EXPRESS);
	}

	/**
	 * Get number from type test
	 * Pass Condition = given a proper type string, yield the proper type number
	 */
	@Test(timeout=5000)
	public void testGetNumberFromType() {
		Type test = Type.VISA;
		Integer testNum = 1;
		assertEquals(CreditCardType.getNumberFromType(test), testNum);
		
		test = Type.MASTERCARD;
		testNum = 2;
		assertEquals(CreditCardType.getNumberFromType(test), testNum);
		
		test = Type.AMERICAN_EXPRESS;
		testNum = 3;
		assertEquals(CreditCardType.getNumberFromType(test), testNum);
	}

	/**
	 * Get credit card type test
	 * Pass Condition = CreditCardType.getType() returns correct CC type for the object
	 */
	@Test(timeout=5000)
	public void testGetType() {
		temp.setId(1);
		assertEquals(temp.getType(), Type.VISA);
		
		temp.setId(2);
		assertEquals(temp.getType(), Type.MASTERCARD);
		
		temp.setId(3);
		assertEquals(temp.getType(), Type.AMERICAN_EXPRESS);
	}

	/**
	 * Set credit card type test
	 * Pass Condition = CreditCardType.getType() returns correct CC type for the object that was set using CreditCard.setType()
	 */
	@Test(timeout=5000)
	public void testSetType() {
		temp.setType(Type.VISA);
		assertEquals(temp.getType(), Type.VISA);
		
		temp.setType(Type.MASTERCARD);
		assertEquals(temp.getType(), Type.MASTERCARD);
		
		temp.setType(Type.AMERICAN_EXPRESS);
		assertEquals(temp.getType(), Type.AMERICAN_EXPRESS);
	}

	/**
	 * Get name from type test
	 * Pass Condition = CreditCardType.GetNameFromType() returns correct credit card type for the object
	 */
	@Test(timeout=5000)
	public void testGetNameFromType() {
		assertEquals(temp.getNameFromType(Type.VISA),"VISA");
		
		assertEquals(temp.getNameFromType(Type.MASTERCARD),"MASTERCARD");
		
		assertEquals(temp.getNameFromType(Type.AMERICAN_EXPRESS), "AMERICAN EXPRESS");
	}

	/**
	 * Get name from number test
	 * Pass Condition = CreditCardType.GetNameFromNumber() returns correct credit card type for the object
	 */
	@Test(timeout=5000)
	public void testGetNameFromNumber() {
		assertEquals(temp.getNameFromNumber(1),"VISA");
		
		assertEquals(temp.getNameFromNumber(2),"MASTERCARD");
		
		assertEquals(temp.getNameFromNumber(3), "AMERICAN EXPRESS");
	}


	/**
	 * Get credit card type number from string test
	 * Pass Condition = CreditCardTypeIntegerString() returns correct credit card name string from the credit card type Id for the object
	 */
	@Test(timeout=5000)
	public void testCreditCardTypeIntegerString() {
		Integer testOneInt = 1;
		CreditCardType testOne = new CreditCardType(1, "A");
		
		assertEquals(testOne.getId(), testOneInt);
		assertEquals(testOne.getName(), "A");
	}

	/*
	 * 
	 * CreditCardType enumeration test
	 * Pass condition: enumeration of all possible ordertypes
	 * 
	 */
	@Test(timeout=5000)
	public void testCreditCardTypeType() {
		CreditCardType other = temp;
		assertEquals(other, temp);		
	}

	/**
	 * Get ID test
	 * Pass Condition = CreditCardType.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertNotNull(temp.getId());
	}

	/**
	 * Set ID test
	 * Pass Condition = CreditCardType.getId() returns the alternate ID for the object that was set using CreditCardType.setId()
	 */
	@Test(timeout=5000)
	public void testSetId() {
		Integer tempId = 2;
		temp.setId(tempId);
		assertEquals(temp.getId(), tempId);
	}

	/**
	 * Get Name test
	 * Pass Condition = CreditCardType.getName() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetName() {
		assertNotNull(temp.getName());
	}

	/**
	 * Set Name test
	 * Pass Condition = CreditCardType.getName() returns the alternate attribute for the object that was set using CreditCardType.setName()
	 */
	@Test(timeout=5000)
	public void testSetName() {
		String tempName = "B";
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
		CreditCardType other = temp;
		assertEquals(temp, other);
		
		CreditCardType fixture = new CreditCardType(new Integer(1), "");
		Object obj = new CreditCardType(new Integer(1), "");
		assertTrue(fixture.equals(obj));
		
		fixture = new CreditCardType(new Integer(1), "");
		obj = null;
		assertFalse(fixture.equals(obj));
		
		fixture = new CreditCardType(new Integer(1), "");
		obj = new Object();
		assertFalse(fixture.equals(obj));
		
		fixture = new CreditCardType(new Integer(1), "");
		obj = new CreditCardType(new Integer(1), "");
		assertTrue(fixture.equals(obj));
		
		fixture = new CreditCardType(new Integer(1), "");
		obj = new CreditCardType(new Integer(1), "");
		assertTrue(fixture.equals(obj));
		
		fixture = new CreditCardType(new Integer(1), (String) null);
		obj = new CreditCardType(new Integer(1), (String) null);
		assertTrue(fixture.equals(obj));
		
		((CreditCardType) obj).setName("");
		assertFalse(fixture.equals(obj));
		
		fixture.setName("");
		((CreditCardType) obj).setName(null);
		assertFalse(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = CreditCardType.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}

}
