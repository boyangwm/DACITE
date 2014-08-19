package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import Model.Account;
import Model.Movie;
import Model.Review;
import junit.framework.TestCase;


import java.sql.*;
import Annotation.DBAnnotation;


/**
 * CS 442 Project Potholes
 * Account Class - Unit Test Module
 * @version $Revision: 1.0 $
 * 
 * @author Anne Celestino / Arnold Lee
 * 
 *
 * @see Model.Account
 * 
 * 
 * Test Setup constructor 
 * main test object = temp
 * 
 * 
 */
public class Account_Test extends TestCase {

	Integer id = 1;
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
	
	Account temp = new Account(id, first, middle, last, street, street2, city, state, zip, user, pw);
	Account temp_so = new Account (first,middle,last,street,street2,city,state,zip,user,pw);
	
	/**
	 * 
	 * Hash code test
	 * Pass Condition = returns the correct hash code for the test object
	 * 
	 */
	@Test(timeout=5000)
	public final void testHashCode() {
		assertNotNull(temp.hashCode());
		assertEquals(temp.hashCode(),-43747681);
	}


	/**
	 * Account object creation test
	 * Pass Condition = constructor successfully creates the class object (object not equal to NULL)
	 */
	@Test(timeout=5000)
	public final void testAccount() {
		assertNotNull(temp);
	}

	
	/**
	 * Get ID test
	 * Pass Condition = Account.getId() returns correct ID for the object
	 */
	@Test(timeout=5000)
	public final void testGetId() {
	    assertEquals(temp.getId(),id);
	}

	/**
	 * Set ID test
	 * Pass Condition = Account.getId() returns the alternate ID for the object
	 * that was set using Account.setId()
	 */
	@Test(timeout=5000)
	public final void testSetId() {
		Integer x = 3;
		temp.setId(x);
		assertEquals(temp.getId(),x);
	}

	
	/**
	 * Get First Name  test
	 * Pass Condition = Account.getFirstName() returns correct first name for the object
	 */
	@Test(timeout=5000)
	public final void testGetFirstName() {
		assertEquals(temp.getFirstName(),first);
	}

	/**
	 * Set first name test
	 * Pass Condition = Account.getFirstName() returns the alternate first name for the object
	 * that was set using Account.setFirstName()
	 */
	@Test(timeout=5000)
	public final void testSetFirstName() {
		temp.setFirstName("abcd");
		assertEquals(temp.getFirstName(),"abcd");
	}

	/**
	 * Get Middle Name  test
	 * Pass Condition = Account.getMiddleName() returns correct middle name for the object
	 */
	@Test(timeout=5000)
	public final void testGetMiddleName() {
		assertEquals(temp.getMiddleName(),middle);
	}

	/**
	 * Set Middle name test
	 * Pass Condition = Account.getMiddleName() returns the alternate middle name for the object
	 * that was set using Account.setMiddleName()
	 */
	@Test(timeout=5000)
	public final void testSetMiddleName() {
		temp.setMiddleName("defg");
		assertEquals(temp.getMiddleName(),"defg");
	}

	/**
	 * Get Last Name test
	 * Pass Condition = Account.getLastName() returns correct last name for the object
	 */
	@Test(timeout=5000)
	public final void testGetLastName() {
		assertEquals(temp.getLastName(),last);
	}

	/**
	 * Set Last name test
	 * Pass Condition = Account.getLastName() returns the alternate last name for the object
	 * that was set using Account.setLastName()
	 */
	@Test(timeout=5000)
	public final void testSetLastName() {
		temp.setLastName("hijk");
		assertEquals(temp.getLastName(),"hijk"); 
	}

	/**
	 * Get Address test
	 * Pass Condition = Account.getAddress1() returns correct address line 1 for the object
	 */
	@Test(timeout=5000)
	public final void testGetAddress1() {
		assertEquals(temp.getAddress1(),street);
	}

	/**
	 * Set Address test
	 * Pass Condition = Account.getAddress1() returns the alternate address line 1 for the object
	 * that was set using Account.setAddress1()
	 */
	@Test(timeout=5000)
	public final void testSetAddress1() {
		temp.setAddress1("2345 main st");
		assertEquals(temp.getAddress1(),"2345 main st"); 
	}

	/**
	 * Get Address test
	 * Pass Condition = Account.getAddress2() returns correct address line 2 for the object
	 */
	@Test(timeout=5000)
	public final void testGetAddress2() {
		assertEquals(temp.getAddress2(),street2);
	}

	/**
	 * Set Address test
	 * Pass Condition = Account.getAddress2() returns the alternate address line 2 for the object
	 * that was set using Account.setAddress2()
	 */
	@Test(timeout=5000)
	public final void testSetAddress2() {
		temp.setAddress2("NONE");
		assertEquals(temp.getAddress2(),"NONE"); 
	}

	/**
	 * Get city test
	 * Pass Condition = Account.getCity() returns correct city for the object
	 */
	@Test(timeout=5000)
	public final void testGetCity() {
		assertEquals(temp.getCity(),city);
	}

	/**
	 * Set City test
	 * Pass Condition = Account.getCity() returns the alternate City for the object
	 * that was set using Account.setCity()
	 */
	@Test(timeout=5000)
	public final void testSetCity() {
		temp.setCity("zzzzz");
		assertEquals(temp.getCity(),"zzzzz"); 
	}

	/**
	 * Get state test
	 * Pass Condition = Account.getState() returns correct state for the object
	 */
	@Test(timeout=5000)
	public final void testGetState() {
		assertEquals(temp.getState(),state);
	}

	/**
	 * Set State test
	 * Pass Condition = Account.getState() returns the alternate state for the object
	 * that was set using Account.setState()
	 */
	@Test(timeout=5000)
	public final void testSetState() {
		temp.setState("xx");
		assertEquals(temp.getState(),"xx"); 
	}

	/**
	 * Get Zip Code test
	 * Pass Condition = Account.getZipCode() returns correct zipcode for the object
	 */
	@Test(timeout=5000)
	public final void testGetZipCode() {
		assertEquals(temp.getZipCode(),zip);
	}

	/**
	 * Set Zip Code test
	 * Pass Condition = Account.getZipCode() returns the alternate zip code for the object
	 * that was set using Account.setZipCode()
	 */
	@Test(timeout=5000)
	public final void testSetZipCode() {
		temp.setZipCode("60607");
		assertEquals(temp.getZipCode(),"60607"); 
	}
	/**
	 * Get user name test
	 * Pass Condition = Account.getUserName() returns correct user name for the object
	 */
	@Test(timeout=5000)
	public final void testGetUsername() {
		assertEquals(temp.getUsername(),user);
	}
	/**
	 * Set user name test
	 * Pass Condition = Account.getUserName() returns the alternate User Name for the object
	 * that was set using Account.setUserName()
	 */
	@Test(timeout=5000)
	public final void testSetUsername() {
		temp.setUsername("anonymous");
		assertEquals(temp.getUsername(),"anonymous"); 
	}

	/**
	 * Get password test
	 * Pass Condition = Account.getPassword() returns correct password for the object
	 */
	@Test(timeout=5000)
	public final void testGetPassword() {
		assertEquals(temp.getPassword(),pw);
	}

	/**
	 * Set Password test
	 * Pass Condition = Account.getPassword() returns the alternate password for the object
	 * that was set using Account.setPassword()
	 */
	@Test(timeout=5000)
	public final void testSetPassword() {
		
		temp.setPassword("12345");
		assertEquals(temp.getPassword(),"12345"); 
	}

	/**
	 * Get Favorites test
	 * Pass Condition = Account.getFavorites() returns correct favorite movie object
	 * associated for the account object
	 */
	@Test(timeout=5000)
	public final void testGetFavorites() {
		Integer id = 1;
		String title = "Frozen";
		Integer productionYear = 2013;
		Integer episodeOfId = 0;
		Integer seasonNumber = 0;
		Integer episodeNumber = 0;
		String seriesYears = "zero";
		String description = "Fearless optimist Anna teams up with Kristoff in an epic journey, encountering Everest-like conditions, and a hilarious snowman named Olaf in a race to find Anna's sister Elsa, whose icy powers have trapped the kingdom in eternal winter.";
		
		Movie dummy1 = new Movie (id, title, productionYear, episodeOfId, seasonNumber, episodeNumber, seriesYears, description);
		
		ArrayList<Movie> dummylist = new ArrayList<Movie>();
		
		dummylist.add(dummy1);
		temp.setFavorites(dummylist);
		assertEquals(temp.getFavorites(),dummylist);
		
		temp.setFavorites(null);
		assertNotNull(temp.getFavorites());
	}

	
	/**
	 * Get Reviews test
	 * Pass Condition = Account.getReviews() returns correct favorite Review object
	 * associated for the account object
	 */
	@Test(timeout=5000)
	public final void testGetReviews() {
		Account fixture = new Account(1, "", "", "", "", "", "", "", "", "", "");
		ArrayList<Review> reviews = new ArrayList<Review>();
		fixture.setReviews(reviews);
		ArrayList<Review> result = fixture.getReviews();	
		assertNotNull(result);
		assertTrue(result == reviews);
		
		fixture.setReviews(null);
		result = fixture.getReviews();	
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	/**
	 * Object equality test
	 * 
	 * Pass Condition = Fails the test if any single attribute between the two objects are not equal 
	 * Otherwise Pass
	 */
	@Test(timeout=5000)
	public final void testEqualsObject() {
		
		Account temp2 = temp;
		
		Integer x = 2;
		
		assertTrue(temp.equals(temp2));
		
		assertFalse(temp.equals(null));
		
		assertFalse(temp.equals(x));
		
		Integer id = 5;
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
		
		Account temp3 = new Account(id, first, middle, last, street, street2, city, state, zip, user, pw);
		id=id+1;
		Account temp4 = new Account(id, first, middle, last, street, street2, city, state, zip, user, pw);
		
		assertTrue(temp3.equals(temp4));
		
		temp4.setZipCode("60607");
		
		assertFalse(temp3.equals(temp4));
		
		temp4.setState("IL");
		temp4.setCity("Chicago");
		
		assertFalse(temp3.equals(temp4));
		
		temp4.setZipCode("90210");
		temp4.setState("CA");
		temp4.setCity("Beverly Hills");
		
		temp4.setFirstName("zzzz");	
		assertFalse(temp3.equals(temp4));
		temp4.setFirstName(temp3.getFirstName());	
		
		temp4.setLastName("dsigjasdoig");
		assertFalse(temp3.equals(temp4));
		temp4.setLastName(temp3.getLastName());
		
		temp4.setMiddleName("fdgdsfh");
		assertFalse(temp3.equals(temp4));
		temp4.setMiddleName(temp3.getMiddleName());
		
		
		temp4.setUsername("Phucker");
		assertFalse(temp3.equals(temp4));
		temp4.setUsername(temp3.getLastName());
		
		temp4.setZipCode("00000");
		assertFalse(temp3.equals(temp4));
		temp4.setUsername(temp3.getZipCode());
		
		
		temp4.setPassword("zzzzzzzzz");
		assertFalse(temp3.equals(temp4));
		temp4.setUsername(temp3.getPassword());
		
		
		temp4.setState("FU");
		assertFalse(temp3.equals(temp4));
		temp4.setUsername(temp3.getState());
		
		temp4.setCity("BLAHHHHH");
		assertFalse(temp3.equals(temp4));
		temp4.setUsername(temp3.getCity());
	}

	/**
	 * String generation test
	 * Pass Condition = Account.toString() returns the string representation of the current object
	 */
	@Test(timeout=5000)
	public final void testToString() {
		
		assertNotNull(temp.toString());
		
		Account fixture = new Account(new Integer(1), "", "", "", "", "", "", "", "", "", "");
		fixture.setFavorites(null);
		
		String result = fixture.toString();

		
		assertEquals("Account \n{\nid=1, \nfirstName=, \nmiddleName=, \nlastName=, \naddress1=, \naddress2=, \ncity=, \nstate=, \nzipCode=, \nusername=, \npassword=, \nfavorites=null\n}", result);
	}
	
	
	
	public void setQuantity(int itemID, int quantity) throws Exception {
		try {
			
			Connection conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/simpletest",
					"root", "boyang");

			Statement stmt = (Statement) conn.createStatement();
			String query = "select SSN, SALARY, AGE from PERSON;";	
			ResultSet rs = stmt.executeQuery(query);

			//while (rs.next()) {
			String ssn = rs.getString("SSN");

			
			double salary = rs.getDouble("SALARY");
			DBAnnotation.annoate("salary","PERSON","SALARY",true);

			
			double age = rs.getInt("AGE");
			DBAnnotation.annoate("age","PERSON","AGE",true);
			
			
			//Testing, it not runnable.
			int house;

			double  bonus = 0;
			if(age > 30)
			{
//				bonus = salary - 500;
//				DBAnnotation.annoate("bonus","PERSON","BONUS",false);
				house = 2;
				//DBAnnotation.annoate("house","PERSON","HOUSE",false);
			}else
			{
//				bonus = salary - 200;
//				DBAnnotation.annoate("bonus","PERSON","BONUS",false);
				house = 4;
				//house = 1;
				DBAnnotation.annoate("house","PERSON","HOUSE",false);
			}

			String updateS = "UPDATE PERSON SET BONUS = ?  WHERE SSN = ?";

			PreparedStatement preparedStmt =  (PreparedStatement) conn.prepareStatement(updateS);
			preparedStmt.setDouble(1, bonus);
			preparedStmt.setString(2, ssn);

			preparedStmt.executeUpdate();
			//DBAnnotation.annoate("bonus","PERSON","BONUS",false);

			conn.close();
			System.out.println("Done!");
		}
		catch (Exception e) {
			System.out.println("exception");
		}


		//			DBAnnotation.annoate("myVariable","theTable","theColumn",true);
		//			String updateTableSQL = "UPDATE item SET quantity = quantity - ?  WHERE itemid = ?";

	}

}
