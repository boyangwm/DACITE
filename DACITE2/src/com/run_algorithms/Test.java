package com.run_algorithms;

import java.io.File;
import java.util.List;

import com.constraint.Constraint;
import com.rulemanager.DbRulesManager;
import com.rulemanager.TableRulesManager;

public class Test {

	public static void main(String[] args)
	{
		DbRulesManager db = new DbRulesManager();
		try {

			//import configuration
			File file = new File("config.txt");
			String input = file.getAbsolutePath();
			
			db.ConnectDB("jdbc:mysql://localhost:3306/", "test", "root", "boyang", "com.mysql.jdbc.Driver");
			db.ImportConfig(input);
			db.Run(3, 0.4, 0.4);

			TableRulesManager trm = db.getTable("informationtest");



//			db.ConnectDB("jdbc:mysql://localhost:3306/", "Durbodax", "root", "boyang", "com.mysql.jdbc.Driver");
//			db.Run(5, 0.9, 0.9);
//			TableRulesManager trm = db.getTable("main");
	
			List<Constraint> lst = trm.getConstraints();
			int i = 0;
			for(Constraint constraint : lst){
				System.out.println("Rule " + i++ + " : " + constraint.toString());
			}


			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
