import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import java.sql.*;

public class Testannotation {
	

	public static void foo(int c){
		
		int a;
		
		int b = 2;
		
		if(c<b)
			a = 3;
		else
			a = 4;
		
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

			
			double age =  rs.getInt("AGE");
			DBAnnotation.annoate("age","PERSON","AGE",true);


			double s = salary * 2;
			double  bonus = s + 10 * age;

			String updateS = "UPDATE PERSON SET BONUS = ?  WHERE SSN = ?";

			PreparedStatement preparedStmt =  (PreparedStatement) conn.prepareStatement(updateS);
			preparedStmt.setDouble(1, bonus);
			preparedStmt.setString(2, ssn);

			
			preparedStmt.executeUpdate();
			DBAnnotation.annoate("bonus","PERSON","BONUS",false);

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
