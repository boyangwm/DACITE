package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.OrderType;
import Model.OrderType.Type;


/**
 * CS 442 Project Potholes
 * OrderType - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author Arnold Lee
 * 
 *
 * @see Model.OrderType
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */
public class OrderType_Test {

	
	OrderType temp = new OrderType(1, "PURCHASE");
	

	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test(timeout=5000)
	public void testOrderType() {
		OrderType.Type type = OrderType.Type.PURCHASE;
		OrderType result = new OrderType(type);
		assertNotNull(result);
		assertEquals("OrderType \n{\nid=1, \nname=PURCHASE\n}", result.toString());
		assertEquals("PURCHASE", result.getName());
		assertEquals(new Integer(1), result.getId());
		
		type = OrderType.Type.RENTAL;
		result = new OrderType(type);
		assertNotNull(result);
		assertEquals("OrderType \n{\nid=2, \nname=RENTAL\n}", result.toString());
		assertEquals("RENTAL", result.getName());
		assertEquals(new Integer(2), result.getId());
		
		Integer id = new Integer(1);
		String name = "";
		result = new OrderType(id, name);
		assertNotNull(result);
		assertEquals("OrderType \n{\nid=1, \nname=\n}", result.toString());
		assertEquals("", result.getName());
		assertEquals(new Integer(1), result.getId());
	}
	
	/**
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 */
	@Test(timeout=5000)
	public void testHashCode() {
		assertNotNull(temp);
		
		OrderType fixture = new OrderType(new Integer(1), "");
		assertEquals(32, fixture.hashCode());
		
		fixture = new OrderType((Integer) null, "");
		assertEquals(31, fixture.hashCode());
	}

	
	/**
	 * Get type from number test
	 * Pass Condition = given a proper type number, yield the proper type string
	 */
	@Test(timeout=5000)
	public void testGetTypeFromNumber() {
		// TODO check this later
		Integer number = 1;
		assertEquals(OrderType.getTypeFromNumber(number), OrderType.Type.PURCHASE);
		
        number = 2;
		assertEquals(OrderType.getTypeFromNumber(number), OrderType.Type.RENTAL);
	}

	/**
	 * Get number from type test
	 * Pass Condition = given a proper type string, yield the proper type number
	 */
	@Test(timeout=5000)
	public void testGetNumberFromType() {
		Type x = Type.PURCHASE;
		Integer y = 1;
		assertEquals( OrderType.getNumberFromType(x),y );
		
		x = Type.RENTAL;
		y = 2;
		assertEquals( OrderType.getNumberFromType(x),y );	
	}

	/**
	 * Get transaction type test
	 * Pass Condition = getType() returns correct CC type for the object
	 */
	@Test(timeout=5000)
	public void testGetType() {
		temp.setId(1);
		assertEquals(temp.getType(),Type.PURCHASE);
		
		temp.setId(2);
		assertEquals(temp.getType(),Type.RENTAL);
	}

	
	/**
	 * Set transaction type test
	 * Pass Condition = setType() returns correct CC type for the object
	 */
	@Test(timeout=5000)
	public void testSetType() {
		temp.setType(Type.PURCHASE);
		assertEquals(temp.getType(),Type.PURCHASE);
		
		temp.setType(Type.RENTAL);
		assertEquals(temp.getType(),Type.RENTAL);
	}

	/**
	 * Get name from type test
	 * Pass Condition = GetNameFromType() returns correct trans type for the object
	 */
	@Test(timeout=5000)
	public void testGetNameFromType() {
		assertEquals(temp.getNameFromType(Type.PURCHASE),"PURCHASE");
		
		assertEquals(temp.getNameFromType(Type.RENTAL),"RENTAL");
	}

	/**
	 * Get transaction type name from number test
	 * Pass Condition = Account.getUserName() returns correct transaction name string from the Tx type Id for the object
	 */
	@Test(timeout=5000)
	public void testGetNameFromNumber() {
		assertEquals (temp.getNameFromNumber(1),"PURCHASE");
	
		assertEquals (temp.getNameFromNumber(2),"RENTAL");
	}

	/**
	 * Get transaction type number from string test
	 * Pass Condition = OrderTypeIntegerString() returns correct transaction name string from the Tx type Id for the object
	 */
	
	@Test(timeout=5000)
	public void testOrderTypeIntegerString() {
		Integer a = 1;
		OrderType temp1 = new OrderType (a,"z");
		
		assertEquals(temp1.getId(),a);
		assertEquals(temp1.getName(),"z");
	}

	
	/*
	 * OrderType enumeration test
	 * Pass condition: enumeration of all possible ordertypes
	 */
	
	@Test(timeout=5000)
	public void testOrderTypeType() {
		OrderType temp2 = temp;
		assertEquals(temp2,temp);
	}

	/**
	 * Get ID test
	 * Pass Condition = getID() returns correct Id for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertNotNull(temp.getId());
	}

	
	/**
	 * set ID test
	 * Pass Condition = getID() returns correct Id input by setID
	 */
	
	@Test(timeout=5000)
	public void testSetId() {
		Integer z = 3;
		temp.setId(z);
		
		assertEquals(temp.getId(),z);
	}

	/**
	 * Get name test
	 * Pass Condition = getName() returns correct name for the object
	 */
	@Test(timeout=5000)
	public void testGetName() {
		assertNotNull(temp.getName());
	}

	
	/**
	 * Set name test
	 * Pass Condition = getName() returns correct name that was entered by setName()
	 */
	@Test(timeout=5000)
	public void testSetName() {
		String z = "BLAH";
		temp.setName(z);
		
		assertEquals(temp.getName(),z);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */	
	@Test(timeout=5000)
	public void testEqualsObject() {
		OrderType temp2 = temp;
		assertTrue(temp.equals(temp2));
	}

	/**
	 * String generation test
	 * Pass Condition = toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());

		OrderType fixture = new OrderType(new Integer(1), "");
		Object obj = new OrderType(new Integer(1), "");
		assertTrue(fixture.equals(obj));

		fixture = new OrderType(new Integer(1), "");
		obj = null;
		assertFalse(fixture.equals(obj));

		fixture = new OrderType(new Integer(1), "");
		obj = new Object();
		assertFalse(fixture.equals(obj));

		fixture = new OrderType(new Integer(1), "");
		obj = new OrderType(new Integer(1), "");
		assertTrue(fixture.equals(obj));

		fixture = new OrderType(new Integer(1), "");
		obj = new OrderType(new Integer(1), "");
		assertTrue(fixture.equals(obj));

		fixture = new OrderType((Integer) null, "");
		obj = new OrderType((Integer) null, "");
		assertTrue(fixture.equals(obj));
	}
}
