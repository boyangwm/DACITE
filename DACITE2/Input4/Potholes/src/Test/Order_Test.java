package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.*;
/**
 * CS 442 Project Potholes
 * Order - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author Arnold Lee
 * 
 *
 * @see Model.Order
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */
public class Order_Test {

	Integer id = 1;
	Integer paymentMethodId = 2;
	Integer orderTypeId = 3;
	Integer accountId = 4;
	Integer movieId = 5;
	Integer transactionSecond = 8;
	Integer transactionMinute = 8;
	Integer transactionHour = 8;
	Integer transactionDay = 8;
	Integer transactionMonth = 8;
	Integer transactionYear = 2028;
	
	
	Order temp = new Order (id, paymentMethodId, orderTypeId, accountId, movieId, transactionSecond, 
			transactionSecond, transactionHour, transactionDay, transactionMonth, transactionYear);
	

	Integer pmid = 3;
	Integer creditCardTypeId = 4;
	CreditCardType creditCardType = new CreditCardType (2,"MC");
	String creditCardNumber = "1111 1111 1111 1111";
	Integer expirationMonth = 4;
	Integer expirationYear = 2012;
	Integer cvv = 888;
	String nameOnCard =" hello kitty ";
	String zipCode = "90210";

	

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

	PaymentMethod dummy_PM = new PaymentMethod (pmid, accountId, creditCardTypeId , creditCardNumber, expirationMonth, expirationYear, cvv, nameOnCard, zipCode);

	
	Integer mid = 1;
	String title = "Frozen";
	Integer productionYear = 2013;
	Integer episodeOfId = 0;
	Integer seasonNumber = 0;
	Integer episodeNumber = 0;
	String seriesYears = "zero";
	String description = "Fearless optimist Anna teams up with Kristoff in an epic journey, encountering Everest-like conditions, and a hilarious snowman named Olaf in a race to find Anna's sister Elsa, whose icy powers have trapped the kingdom in eternal winter.";
	
	Movie t_movie = new Movie(mid, title, productionYear, episodeOfId, seasonNumber, episodeNumber, seriesYears, description);
	OrderType t_ot = new OrderType(1, "PURCHASE");
	
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
		
		Order fixture = new Order(new Integer(1), new Integer(1), new Integer(1), (Integer) null, (Integer) null, new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertEquals(954305, fixture.hashCode());
		
		 fixture = new Order(new Integer(1), (Integer) null, (Integer) null, new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertEquals(954273, fixture.hashCode());
	}

	/**
	 * Review object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public void testOrder() {
		assertNotNull(temp);
		
		Integer paymentMethodId = new Integer(1);
		Integer orderTypeId = new Integer(1);
		Integer accountId = new Integer(1);
		Integer movieId = new Integer(1);

		Order result = new Order(paymentMethodId, orderTypeId, accountId, movieId);
		
		assertNotNull(result);
		assertEquals("Order \n{\nid=null, \npaymentMethodId=1, \norderTypeId=1, \naccountId=1, \nmovieId=1, \npaymentMethod=null, \norderType=null, \naccount=null, \nmovie=null, \ntransactionSecond=null, \ntransactionMinute=null, \ntransactionHour=null, \ntransactionDay=null, \ntransactionMonth=null, \ntransactionYear=null\n}", result.toString());
		assertNull(result.getId());
		assertNotNull(result.getAccount());
		assertNotNull(result.getOrderType());
		assertNotNull(result.getMovie());
		
		assertEquals(new Integer(1), result.getAccountId());
		assertEquals(new Integer(1), result.getMovieId());
		
		assertNull(result.getTransactionSecond());
		assertNull(result.getTransactionMinute());
		assertNull(result.getTransactionHour());
		assertNull(result.getTransactionMonth());
		assertNull(result.getTransactionDay());
		assertNull(result.getTransactionYear());
		
		assertNotNull(result.getPaymentMethod());

		assertEquals(new Integer(1), result.getOrderTypeId());
		assertEquals(new Integer(1), result.getPaymentMethodId());
		
		id = new Integer(1);
		paymentMethodId = new Integer(1);
		orderTypeId = new Integer(1);
		accountId = new Integer(1);
	    movieId = new Integer(1);
		Integer transactionSecond = new Integer(1);
		Integer transactionMinute = new Integer(1);
		Integer transactionHour = new Integer(1);
		Integer transactionDay = new Integer(1);
		Integer transactionMonth = new Integer(1);
		Integer transactionYear = new Integer(1);

		result = new Order(id, paymentMethodId, orderTypeId, accountId, movieId, transactionSecond, transactionMinute, transactionHour, transactionDay, transactionMonth, transactionYear);

	
		assertNotNull(result);
		assertEquals("Order \n{\nid=1, \npaymentMethodId=1, \norderTypeId=1, \naccountId=1, \nmovieId=1, \npaymentMethod=null, \norderType=null, \naccount=null, \nmovie=null, \ntransactionSecond=1, \ntransactionMinute=1, \ntransactionHour=1, \ntransactionDay=1, \ntransactionMonth=1, \ntransactionYear=1\n}", result.toString());
		assertEquals(new Integer(1), result.getId());
		
		assertNotNull(result.getAccount());
		assertNotNull(result.getOrderType());
		assertNotNull(result.getMovie());
		
		assertEquals(new Integer(1), result.getAccountId());
		assertEquals(new Integer(1), result.getMovieId());
		
		assertEquals(new Integer(1), result.getTransactionSecond());
		assertEquals(new Integer(1), result.getTransactionMinute());
		assertEquals(new Integer(1), result.getTransactionHour());
		assertEquals(new Integer(1), result.getTransactionMonth());
		assertEquals(new Integer(1), result.getTransactionDay());

		assertNotNull(result.getPaymentMethod());
		
		assertEquals(new Integer(1), result.getTransactionYear());
		assertEquals(new Integer(1), result.getOrderTypeId());
		assertEquals(new Integer(1), result.getPaymentMethodId());		
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
		Integer x = 99;
		temp.setId(x);
		assertEquals(temp.getId(),x);
	}

	/**
	 * Get paymentmethodID test
	 * Pass Condition = GetPaymentMethodId() returns correct payment method ID for the object
	 */
	@Test(timeout=5000)
	public void testGetPaymentMethodId() {
		assertEquals(temp.getPaymentMethodId(),paymentMethodId);
	}

	/**
	 * Set paymentmethodID test
	 * Pass Condition = GetPaymentMethodId() returns the  paymentMethodID for the object
	 * that was set using SetPaymentMethodId()
	 */
	@Test(timeout=5000)
	public void testSetPaymentMethodId() {
		Integer x = 4;
		temp.setPaymentMethodId(x);
		assertEquals(temp.getPaymentMethodId(),x);
	}

	/**
	 * Get getOrderTypeID test
	 * Pass Condition = GetOrderTypeId() returns correct payment method ID for the object
	 */
	@Test(timeout=5000)
	public void testGetOrderTypeId() {
		assertEquals(temp.getOrderTypeId(),orderTypeId);
	}
	/**
	 * Set OrderTypeID test
	 * Pass Condition = GetOrderTypeId() returns the alternate ordertypeID for the object
	 * that was set using SetOrderTypeId()
	 */
	@Test(timeout=5000)
	public void testSetOrderTypeId() {
		Integer x = 5;
		temp.setOrderTypeId(x);
		assertEquals(temp.getOrderTypeId(),x);
	}

	/**
	 * Get AccountID test
	 * Pass Condition = AccountId() returns correct AccountID for the object
	 */
	@Test(timeout=5000)
	public void testGetAccountId() {
		assertEquals(temp.getAccountId(), accountId);
	}

	/**
	 * Set AccountID test
	 * Pass Condition = getAccountId() returns the alternate AccountID for the object
	 * that was set using setAccountId()
	 */
	@Test(timeout=5000)
	public void testSetAccountId() {
		Order fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Integer Z_accountId = new Integer(1);

		fixture.setAccountId(Z_accountId);
		
		assertEquals (fixture.getAccountId(),Z_accountId);
	}

	/**
	 * Get MovieID test
	 * Pass Condition = MovieId() returns correct MovieID for the object
	 */
	@Test(timeout=5000)
	public void testGetMovieId() {
		assertEquals(temp.getMovieId(), movieId);
	}

	/**
	 * Set MovieID test
	 * Pass Condition = getMovieId() returns the alternate MovieID for the object
	 * that was set using setMovieId()
	 */
	@Test(timeout=5000)
	public void testSetMovieId() {
		Order fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Integer Z_movieId = new Integer(1);
		fixture.setMovieId(Z_movieId);
		assertEquals(Z_movieId, fixture.getMovieId());
	}

	/**
	 * Get Payment Method test
	 * Pass Condition = GetPaymentMethod() returns correct payment method for the object
	 */
	@Test(timeout=5000)
	public void testGetPaymentMethod() {
		temp.setPaymentMethod(dummy_PM);
		assertEquals(temp.getPaymentMethod(), dummy_PM);
	}
	
	/**
	 * Get Order Type test
	 * Pass Condition = GetOrderType() returns correct order type for the object
	 */

	@Test(timeout=5000)
	public void testGetOrderType() {
		temp.setOrderType(t_ot);
		assertEquals(temp.getOrderType(), t_ot);
	}

	/**
	 * Get Account test
	 * Pass Condition = GetAccount() returns correct account associated w
	 * with the accountId for the object
	 */
	@Test(timeout=5000)
	public void testGetAccount() {
		temp.setAccount(t_account);
		assertEquals(temp.getAccount(), t_account);
	}
	
	/**
	 * Get Movie test
	 * Pass Condition = GetMovie() returns correct movie  associated w
	 * with the movieId for the object
	 */
	@Test(timeout=5000)
	public void testGetMovie() {
		temp.setMovie(t_movie);
		assertEquals(temp.getMovie(), t_movie);
	}

	/**
	 * Set Payment Method test
	 * Pass Condition = GetPaymentMethod() returns the payment method for the object
	 * that was set using SetPaymentMethod()
	 */
	@Test(timeout=5000)
	public void testSetPaymentMethod() {
	        PaymentMethod dummy_PM2 = dummy_PM;
	        dummy_PM2.setCreditCardNumber("9999 9999 9999 1111");
	        temp.setPaymentMethod(dummy_PM2);
	        assertEquals(temp.getPaymentMethod(), dummy_PM2);
	}

	/**
	 * Set Order Type test
	 * Pass Condition = GetOrderType() returns the Order Type for the object
	 * that was set using SetOrderType()
	 */
	@Test(timeout=5000)
	public void testSetOrderType() {
		OrderType t_ot2 = new OrderType (2,"RENTAL");
		temp.setOrderType(t_ot2);
		assertEquals(temp.getOrderType(), t_ot2);
	}

	/**
	 * Set Account test
	 * Pass Condition = GetAccount() returns the Account 
	 * set by loading a dummy account into the object using
	 * SetAccount()
	 */
	@Test(timeout=5000)
	public void testSetAccount() {
		Account t_account2 = t_account;
		t_account2.setId(7);
		t_account2.setFirstName("LOLOL");
		temp.setAccount(t_account2);
		assertEquals(temp.getAccount(), t_account2);
	}

	/**
	 * Set Movie test
	 * Pass Condition = GetMovie() returns the alternate Movie for the object
	 * that was set using SetMovie()
	 */
	@Test(timeout=5000)
	public void testSetMovie() {
		Movie t_movie2 = t_movie;
		t_movie2.setId(88);
		t_movie2.setTitle("WHISKEY TANGO FOXTROT");
		temp.setMovie(t_movie2);
		assertEquals(temp.getMovie(), t_movie2);
	}

	

	/**
	 * Get transactionsecond test
	 * Pass Condition = GetTransactionSecond() returns correct Tx seconds
	 * for the object
	 */
	
	
	@Test(timeout=5000)
	public void testGetTransactionSecond() {
		assertEquals(temp.getTransactionSecond(), transactionSecond);
	}

	
	/**
	 * Set TransactionSecond test
	 * Pass Condition = getTransactionSecond() returns the correct Tx Seconds
	 * given a specified input to setTransactionSecond()
	 */
	@Test(timeout=5000)
	public void testSetTransactionSecond() {
		Integer t = 6;
		temp.setTransactionSecond(t);
		assertEquals(temp.getTransactionSecond(), t);
	}

	/**
	 * Get TransactionMinute test
	 * Pass Condition = GetTransactionMinute() returns correct Tx minute
	 * for the object
	 */
	
	@Test(timeout=5000)
	public void testGetTransactionMinute() {
		assertEquals(temp.getTransactionMinute(), transactionMinute);
	}

	/**
	 * Set TransactionMinute test
	 * Pass Condition = getTransactionMinute() returns the correct Tx Minute
	 * given a specified input to setTransactionMinute()
	 */
	@Test(timeout=5000)
	public void testSetTransactionMinute() {
		Integer t = 6;
		temp.setTransactionMinute(t);
		assertEquals(temp.getTransactionMinute(),t);
	}

	/**
	 * Get transactionhour test
	 * Pass Condition = GetTransactionHour() returns correct Tx hour
	 * for the object
	 */
	@Test(timeout=5000)
	public void testGetTransactionHour() {
		assertEquals(temp.getTransactionHour(), transactionHour);
	}

	/**
	 * Set TransactionHour test
	 * Pass Condition = getTransactionHour() returns the correct Tx Hour
	 * given a specified input to setTransactionHour()
	 */
	@Test(timeout=5000)
	public void testSetTransactionHour() {
		Integer t = 6;
		temp.setTransactionHour(t);
		assertEquals(temp.getTransactionHour(), t);
	}

	/**
	 * Get transactionday test
	 * Pass Condition = GetTransactionDay() returns correct Tx day
	 * for the object
	 */
	@Test(timeout=5000)
	public void testGetTransactionDay() {
		assertEquals(temp.getTransactionDay(), transactionDay);
	}

	/**
	 * Set TransactionDay test
	 * Pass Condition = getTransactionDay() returns the correct Tx Daye
	 * given a specified input to setTransactionDay()
	 */
	@Test(timeout=5000)
	public void testSetTransactionDay() {
		Integer t = 21;
		temp.setTransactionDay(t);
		assertEquals(temp.getTransactionDay(), t);
	}

	/**
	 * Get transactionmonth Test
	 * Pass Condition = GetTransactionMonth() returns correct Tx month
	 * for the object
	 */
	@Test(timeout=5000)
	public void testGetTransactionMonth() {
		assertEquals(temp.getTransactionMonth(), transactionMonth);
	}

	/**
	 * Set TransactionMonth test
	 * Pass Condition = getTransactionMonth() returns the correct Tx Month
	 * given a specified input to setTransactionMonth()
	 */
	
	@Test(timeout=5000)
	public void testSetTransactionMonth() {
		Integer t = 12;
		temp.setTransactionMonth(t);
		assertEquals(temp.getTransactionMonth(), t);
	}

	/**
	 * Get TransactionYear test
	 * Pass Condition = GetTransactionYear() returns correct Tx year
	 * for the object
	 */
	@Test(timeout=5000)
	public void testGetTransactionYear() {
		assertEquals(temp.getTransactionYear(), transactionYear);
	}

	/**
	 * Set TransactionYeartest
	 * Pass Condition = getTransactionYear() returns the correct Tx Year
	 * given a specified input to setTransactionYear()
	 */
	@Test(timeout=5000)
	public void testSetTransactionYear() {
		Integer t = 2012;
		temp.setTransactionYear(t);
		assertEquals(temp.getTransactionYear(), t);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public void testEqualsObject() {
		Order fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Object obj = null;
		assertFalse(fixture.equals(obj));
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		Order obj2 = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		obj2.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		obj2.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		obj2.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		obj2.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		assertTrue(fixture.equals(obj2));
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Object();
		assertFalse(fixture.equals(obj));
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		

		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		
		
		fixture = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order(new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		
		
		fixture = new Order(new Integer(1), (Integer) null, new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1), new Integer(1));
		fixture.setOrderType(new OrderType(OrderType.Type.PURCHASE));
		fixture.setMovie(new Movie(new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setPaymentMethod(new PaymentMethod(new Integer(1), new Integer(1), new Integer(1), "", new Integer(1), new Integer(1), new Integer(1), "", ""));
		fixture.setAccount(new Account(new Integer(1), "", "", "", "", "", "", "", "", "", ""));
		obj = new Order((Integer) null, new Integer(1), new Integer(1), new Integer(1));
		assertTrue(fixture.equals(obj));		
	}

	/**
	 * String generation test
	 * Pass Condition = toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public void testToString() {
		assertNotNull(temp.toString());
	}
}
