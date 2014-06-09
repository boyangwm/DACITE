package expression;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class temp {
	public static void foo(){
		try {
			Connection conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/simpletest",
					"root", "boyang");

			Statement stmt = (Statement) conn.createStatement();
			String query = "select SSN, SALARY, AGE from PERSON;";	
			ResultSet rs = stmt.executeQuery(query);

			//while (rs.next()) {
			String ssn = rs.getString("SSN");

			DBAnnotation.annoate("salary","PERSON","SALARY",true);
			double salary = rs.getDouble("SALARY");

			DBAnnotation.annoate("age","PERSON","AGE",true);
			double age =  rs.getInt("AGE");


			double s = salary * 0.2;
			double  bonus = s + 10 * age;

			String updateS = "UPDATE PERSON SET BONUS = ?  WHERE SSN = ?";

			PreparedStatement preparedStmt =  (PreparedStatement) conn.prepareStatement(updateS);
			preparedStmt.setDouble(1, bonus);
			preparedStmt.setString(2, ssn);

			DBAnnotation.annoate("bonus","PERSON","BONUS",false);
			preparedStmt.executeUpdate();

			conn.close();
			System.out.println("Done!");
		}
		catch (Exception e) {
			System.out.println("exception");
		}
	}

}
