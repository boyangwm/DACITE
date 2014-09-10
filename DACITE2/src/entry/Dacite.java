package entry;
import static choco.Choco.gt;
import static choco.Choco.implies;
import static choco.Choco.makeIntVar;
import static choco.Choco.not;


import expression.CBParser;
import gov.nasa.jpf.symbc.numeric.PCParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import choco.kernel.model.variables.integer.IntegerVariable;

import com.constraint.Constraint;
import com.constraint.ConstraintBuilder;
import com.rulemanager.DbRulesManager;
import com.rulemanager.TableRulesManager;

import solver.ProblemChocoInt;
import solver.UniversalImplication;
import static solver.Util.*;
import soot.PackManager;
import soot.Transform;


public class Dacite {

	public long totalTime;
	//source code constraint
	//it stores all constraints (DACITE format) by running the code analysis. 
	public static ArrayList<Constraint> SCConstraints = new ArrayList<Constraint>(); 
	public static Map<Constraint, String> SCCMap = new HashMap<Constraint, String>(); 
	
	public ArrayList <String> conflictFunctions = new ArrayList<String>();

	public void run(String[] args, int k, double minsupp, double minconf){
		System.out.println("Run DACITE... ");
		
		long startTime = System.currentTimeMillis();
		
		soot.G.reset();
	
		//##### 1. source code analysis #######
		if(args.length == 0)
		{
			System.out.println("Syntax: java Main <classfile> [soot options]");
			System.exit(0);
		}
		try{
		PackManager.v().getPack("jtp").add(new Transform("jtp.StmtIteration", 
				IntraAnalysis.v()));
		}catch(RuntimeException ex){
			
			System.out.println(ex.getMessage());
			System.out.println("stop");
			//if(ex.getMessage().equals(anObject))
			
		}
		
		
		soot.Main.main(args);

		//##### 2. association rule mining #######
		ArrayList<Constraint> DBConstraints = new ArrayList<Constraint> ();
		DbRulesManager db = new DbRulesManager();
		try {

			//import configuration
			File file = new File("C:\\Users\\Boyang\\git\\DatabaseRuleMining"
					+ "\\KmenasWithFpgrowth\\config.txt");
			String input = file.getAbsolutePath();

			//db.ConnectDB("jdbc:mysql://localhost:3306/", "boyangtest", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "sakila", "root", "boyang", "com.mysql.jdbc.Driver");
			//potholes
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "mockdata1", "root", "boyang", "com.mysql.jdbc.Driver");
			db.ConnectDB("jdbc:mysql://localhost:3306/", "mockdata2", "root", "boyang", "com.mysql.jdbc.Driver");
			
			
			db.ImportConfig(input);
			//db.Run(2, 0.1, 0.1);   original
			//db.Run(4, 0.10, 0.6);
			db.Run(k, minsupp, minconf);
			ArrayList<String> strTables = db.GetAllTableNames();
			for(String strName : strTables){
				TableRulesManager trm = db.getTable(strName);
				DBConstraints.addAll(trm.getConstraints());	
			}
			

			
			int i = 0;
			for(Constraint constraint : DBConstraints){
				System.out.println("DB Rule " + i++ + " : " + constraint.toString());
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//test___1  
		//back
		printSCconstraints();
		
		
		/*
		Constraint c1 = SCConstraints.get(0);
		ConstraintBuilder c1L = c1.getLeft(); 
		ConstraintBuilder c1R = c1.getRight();


		for(Constraint c2 : DBConstraints){

			ConstraintBuilder c2L = c2.getLeft(); 
			ConstraintBuilder c2R = c2.getRight();

			UniversalImplication imp = new UniversalImplication(c2L, c1L);
			//UniversalImplication imp = new UniversalImplication(c1L, c2L);
			System.out.println("VALID ?  " + imp.valid());

		}

		System.out.println("test 22222222222222222");
		*/
		//test__2
		//System.out.println("#### Find confliction? The result :" + findConfliction(this.SCConstraints, DBConstraints, true));

		boolean basedOnDB = true;
		if(basedOnDB){
			ArrayList<ReportRecord> conflictList = findConfliction(this.SCConstraints, DBConstraints, true);
			if(conflictList.size() != 0){
				System.out.println("\n\n#### Find conflicts !!! #######");
				int counter = 0;
				for(ReportRecord rr : conflictList){
					//System.out.println("======= record " + counter++ +  "========");
					Constraint c = rr.getConstraint();
					//System.out.println("In the function : " +  SCCMap.get(c)); 
					if(!conflictFunctions.contains(SCCMap.get(c)))
					{	
						conflictFunctions.add(SCCMap.get(c));
					}
					//System.out.println(rr.getRules());
					//System.out.println("===============");
				}
			}else{
				System.out.println("#### There is no conflict !!! ");
			}
		}else{
			
		}
		
		
		
		long endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
		System.out.println("time : " + totalTime/1000.0 + "s");
		
		
		for(String str: conflictFunctions){
			System.out.println(str);
		}

		
	}

	
	
	
	public void printSCconstraints(){
		System.out.println("size : " + SCConstraints.size());
		int i = 0;
		for(Constraint c : SCConstraints){
			System.out.println("rule " + i + ":" + c.toString());
			i++;
		}
	}


}
