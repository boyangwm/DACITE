import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import java.sql.*;


public class Testannotation {
	public void updateStore_IDToOne(String ssn){
		try {

			Connection conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/simpletest",
					"root", "boyang");

			Statement stmt = (Statement) conn.createStatement();
			String query = "select AGE, SSN FROM mock_data where ssn = " + ssn;	
			ResultSet rs = stmt.executeQuery(query);

			//while(rs.next()){



			int addressid = rs.getInt("ADDRESS_ID");	
			DBAnnotation.annoate("addressid","MOCK_DATA5000","ADDRESS_ID",true);
			
			
			assert(addressid == 2);

			int storeid = 2; // should be 1
			DBAnnotation.annoate("storeid","MOCK_DATA5000","STORE_ID",false);



			String updateS = "UPDATE PERSON SET STORE_ID = ?  WHERE SSN = ?";

			PreparedStatement preparedStmt =  (PreparedStatement) conn.prepareStatement(updateS);
			preparedStmt.setDouble(1, storeid);
			preparedStmt.setString(2, ssn);

			preparedStmt.executeUpdate();
			//DBAnnotation.annoate("bonus","PERSON","BONUS",false);

			conn.close();
			System.out.println("Done!");
		}
		catch (Exception e) {
			System.out.println("exception");
		}
	}

	/*
	public void setQuantity(int itemID, int quantity) throws Exception {
		try {

			Connection conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/simpletest",
					"root", "boyang");

			Statement stmt = (Statement) conn.createStatement();
			String query = "select SSN, SALARY, AGE from PERSON;";	
			ResultSet rs = stmt.executeQuery(query);

//			String ssn = rs.getString("SSN");


//			double salary = rs.getDouble("SALARY");
//			DBAnnotation.annoate("salary","PERSON","SALARY",true);


			double age = rs.getInt("AGE");	
			DBAnnotation.annoate("age","PERSON","AGE",true);
			assert(age < 30);

			int salary = 1000;
			int house = 3;
			//house = 1;
			DBAnnotation.annoate("house","PERSON","HOUSE",false);

			//Testing, it not runnable.
//			int house;
//
//			double  bonus = 0;
//			if(age > 30)
//			{
////				bonus = salary - 500;
////				DBAnnotation.annoate("bonus","PERSON","BONUS",false);
//				house = 2;
//				//DBAnnotation.annoate("house","PERSON","HOUSE",false);
//			}else
//			{
////				bonus = salary - 200;
////				DBAnnotation.annoate("bonus","PERSON","BONUS",false);
//				house = 4;
//				//house = 1;
//				DBAnnotation.annoate("house","PERSON","HOUSE",false);
//			}
//
//			String updateS = "UPDATE PERSON SET BONUS = ?  WHERE SSN = ?";
//
//			PreparedStatement preparedStmt =  (PreparedStatement) conn.prepareStatement(updateS);
//			preparedStmt.setDouble(1, bonus);
//			preparedStmt.setString(2, ssn);
//
//			preparedStmt.executeUpdate();
//			//DBAnnotation.annoate("bonus","PERSON","BONUS",false);
//
//			conn.close();
//			System.out.println("Done!");
		}
		catch (Exception e) {
			System.out.println("exception");
		}


		//			DBAnnotation.annoate("myVariable","theTable","theColumn",true);
		//			String updateTableSQL = "UPDATE item SET quantity = quantity - ?  WHERE itemid = ?";

	}

	 */
}
