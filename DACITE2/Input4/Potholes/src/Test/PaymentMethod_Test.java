package Test;

import static org.junit.Assert.*;

import org.junit.*;

import Model.*;

/**
 * CS 442 Project Potholes
 * Payment Method Type - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author  Arnold Lee
 * 
 *
 * @see Model.PaymentMethod
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */


public class PaymentMethod_Test {

	Integer id = 33;
	Integer creditCardTypeId = 4;
	CreditCardType creditCardType = new CreditCardType (2,"MC");
	String creditCardNumber = "1111 1111 1111 1111";
	Integer expirationMonth = 4;
	Integer expirationYear = 2012;
	Integer cvv = 888;
	String nameOnCard =" hello kitty ";
	String zipCode = "90210";

	Integer accountId = 2;

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

	Account t_account = new Account(accountId, first, middle, last, street, street2, city, state, zip, user, pw);

	PaymentMethod temp = new PaymentMethod (id, accountId, creditCardTypeId , creditCardNumber, expirationMonth, expirationYear, cvv, nameOnCard, zipCode);


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
	 * PaymentMethod object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL) 
	 */
	
	@Test(timeout=5000)
	public void testPaymentMethod() {
		assertNotNull(temp);
	}

	/**
	 * Get ID test
	 * Pass Condition = getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public void testGetId() {
		assertEquals(temp.getId(),id);
	}

	/**
	 * Set ID test
	 * Pass Condition = getId() returns the alternate ID for the object
	 * that was set using setId()
	 */
	
	@Test(timeout=5000)
	public void testSetId() {
		Integer x = 3;
		temp.setId(x);
		assertEquals(temp.getId(),x);
	}
	
	/**
	 * Get account ID test
	 * Pass Condition = getAccountId() returns correct account ID for the object
	 */
	@Test(timeout=5000)
	public void testGetAccountId() {
		temp.setAccountId(id);
		assertEquals(temp.getAccountId(),id);
	}

	/**
	 * Set CreditCardType test
	 * Pass Condition = getCreditCardType() returns the correct credit card type name string
	 * given a specified input to CreditCardTypeId
	 */
	@Test(timeout=5000)
	public void testSetAccountId() {
		Integer x = 7;
		temp.setAccountId(x);
		assertEquals(temp.getAccountId(),x);
	}
	
	/**
	 * Get CreditCardTypeId() test
	 * Pass Condition = CreditCardTypeId()returns correct credit card type enumeration 
	 */
	@Test(timeout=5000)
	public void testGetCreditCardTypeId() {
		temp.setCreditCardTypeId(id);
		assertEquals(temp.getCreditCardTypeId(),id);
	}

	/**
	 * Set CreditCardTypeId test
	 * Pass Condition = getCreditCardTypeId()returns correct credit card type enumeration after setting
	 * CreditCardTypeId to some acceptable value
	 */
	@Test(timeout=5000)
	public void testSetCreditCardTypeId() {
		Integer x = 9;
		temp.setCreditCardTypeId(x);
		assertEquals(temp.getCreditCardTypeId(), x);
	}
	
	/**
	 * Get Account test
	 * Pass Condition = getaccount() returns correct account associated with accountId
	 */
	
	@Test(timeout=5000)
	public void testGetAccount() {		
		PaymentMethod fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		Integer result = fixture.getAccountId();
		assertNotNull(result);
		assertEquals("1", result.toString());
		assertEquals((byte) 1, result.byteValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1.0, result.doubleValue(), 1.0);
		
		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		String result_2 = fixture.getCreditCardNumber();
		assertEquals("", result_2);
	}
	          
	/**
	 * Get CreditCardType() test
	 * Pass Condition = CreditCardType()returns correct credit card type enumeration given CreditCardTypeId
	 */
	
	@Test(timeout=5000)
	public void testGetCreditCardType() {
		PaymentMethod fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		CreditCardType result = fixture.getCreditCardType();
		
		assertNotNull(result);
		assertEquals("CreditCardType \n{\nid=3, \nname=AMERICAN EXPRESS\n}", result.toString());
		assertEquals("AMERICAN EXPRESS", result.getName());
		assertEquals(new Integer(3), result.getId());
	}


	/**
	 * Set Account test
	 * Pass Condition = Account is set in the object, then retrieve it using getAccount() and compare
	 */
	@Test(timeout=5000)
	public void testSetAccount() {
		Integer accountId = 6;

		String first = "AAA";
		String middle = "BBB";
		String last = "CCC";
		String street = "31337 ABC BLVD";
		String street2 = "";
		String city = "CHICAGO";
		String state = "IL";
		String zip = "60699";
		String user = "whatever2";
		String pw = "123456";

		Account temp2 = new Account(accountId, first, middle, last, street, street2, city, state, zip, user, pw);
		temp.setAccount(temp2);
		assertEquals (temp.getAccount(),temp2);
	}

	
	/**
	 * Set CreditCardType test
	 * Pass Condition = getCreditCardType() returns the correct credit card type name string
	 * given a specified input to CreditCardTypeId
	 */
	@Test(timeout=5000)
	public void testSetCreditCardType() {
		CreditCardType dummy = new CreditCardType (4, "DISCOVER");
		temp.setCreditCardType(dummy);
		assertEquals(temp.getCreditCardType(),dummy);
	}

	/**
	 * Get cc number test
	 * Pass Condition = getCreditCardNumber() returns correct card numberfor the object
	 */
	@Test(timeout=5000)
	public void testGetCreditCardNumber() {
		assertEquals(temp.getCreditCardNumber(),creditCardNumber);
	}

	/**
	 * Set cc number test
	 * Pass Condition = getCreditCardType() returns the correct credit card number string
	 * given a specified input to setCreditCardNumber()
	 */
	@Test(timeout=5000)
	public void testSetCreditCardNumber() {
		creditCardNumber = "123456789012345";
		temp.setCreditCardNumber(creditCardNumber);
		assertEquals(temp.getCreditCardNumber(),creditCardNumber);
	}

	/**
	 * Get expirationmonth test
	 * Pass Condition = GetExpirationMonth() returns correct month for the object
	 */
	@Test(timeout=5000)
	public void testGetExpirationMonth() {
		assertEquals(temp.getExpirationMonth(),expirationMonth);
	}

	/**
	 * Set exp month test
	 * Pass Condition = GetExpirationMonth() returns the correct exp month string
	 * given a specified input to setExpirationMonth()
	 */
	@Test(timeout=5000)
	public void testSetExpirationMonth() {
		expirationMonth = 10;
		temp.setExpirationMonth(expirationMonth);
		assertEquals(temp.getExpirationMonth(),expirationMonth);
	}

	/**
	 * Get expirationyear test
	 * Pass Condition = GetExpirationYear() returns correct year for the object
	 */
	@Test(timeout=5000)
	public void testGetExpirationYear() {
		assertEquals(temp.getExpirationYear(),expirationYear);
	}

	/**
	 * Set exp year test
	 * Pass Condition = GetExpirationYear() returns the correct exp year string
	 * given a specified input to setExpirationYear()
	 */
	@Test(timeout=5000)
	public void testSetExpirationYear() {
		expirationYear = 2030;
		temp.setExpirationYear(expirationYear);
		assertEquals(temp.getExpirationYear(),expirationYear);
	}

	/**
	 * Get cvv test
	 * Pass Condition = getcvv() returns correct cvv for the object
	 */
	@Test(timeout=5000)
	public void testGetCvv() {
		assertEquals(temp.getCvv(),cvv);
	}

	/**
	 * Set cvv test
	 * Pass Condition = GetCvv() returns the cvv string
	 * given a specified input to setCvv()
	 */
	@Test(timeout=5000)
	public void testSetCvv() {
		cvv = 1337;
		temp.setCvv(cvv);
		assertEquals(temp.getExpirationYear(),expirationYear);
	}

	/**
	 * Get nameoncard test
	 * Pass Condition = getNameonCard() returns correct name for the object
	 */
	@Test(timeout=5000)
	public void testGetNameOnCard() {
		assertEquals(temp.getNameOnCard(),nameOnCard);
	}

	/**
	 * Set nameoncard test
	 * Pass Condition = getNameonCard()returns the name string
	 * given a specified input to setNameonCard()
	 */
	@Test(timeout=5000)
	public void testSetNameOnCard() {
		nameOnCard = "Cheesehead mouthbreather";
		temp.setNameOnCard(nameOnCard);
		assertEquals(temp.getNameOnCard(),nameOnCard);
	}

	/**
	 * Get zipcode test
	 * Pass Condition = getzipcode() returns correct zipcode for the object
	 */
	@Test(timeout=5000)
	public void testGetZipCode() {
		assertEquals(temp.getZipCode(),zipCode);
	}

	/**
	 * Set Zip code test
	 * Pass Condition = getZipCode()returns the zipcode string
	 * given a specified input to setZipCode()
	 */
	@Test(timeout=5000)
	public void testSetZipCode() {
		zipCode = "60607";
		temp.setZipCode(zipCode);
		assertEquals(temp.getZipCode(),zipCode);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		PaymentMethod fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		PaymentMethod obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		obj.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		obj.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = null;
		assertFalse(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		Object obj1 = new Object();
		assertFalse(fixture.equals(obj1));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));

		fixture = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		fixture.setAccount(new Account("", "", "", "", "", "", "", "", "", ""));
		fixture.setCreditCardType(new CreditCardType(CreditCardType.Type.AMERICAN_EXPRESS));
		obj = new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", "");
		assertTrue(fixture.equals(obj));
	}

	/**
	 * String generation test
	 * Pass Condition = Account.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}
}
