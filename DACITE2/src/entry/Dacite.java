package entry;
import static choco.Choco.gt;
import static choco.Choco.implies;
import static choco.Choco.makeIntVar;
import static choco.Choco.not;


import expression.CBParser;
import gov.nasa.jpf.symbc.numeric.PCParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public void run(String[] args, int k, double minsupp, double minconf) throws IOException{
		
		
		Runtime r = Runtime.getRuntime();
		long t1 = r.totalMemory()/1024;
		long f1 = r.freeMemory()/1024;
		
		
		
		System.out.println("Run DACITE... ");
		
		boolean basedOnDB = true;
		File file = new File("Outputs.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); //CHENGL
		BufferedWriter bw = new BufferedWriter(fw); //CHENGL
		
		long startTime = System.currentTimeMillis();
		
		soot.G.reset();
		SCCMap.clear(); 
		SCConstraints.clear();
		
		
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
		
		long P1Time   = System.currentTimeMillis();
		System.out.println("phase1 time : " + (P1Time-startTime)/1000.0 + "s");
		

		//##### 2. association rule mining #######
		ArrayList<Constraint> DBConstraints = new ArrayList<Constraint> ();
		DbRulesManager db = new DbRulesManager();
		try {

			//import configuration
			File conf = new File("config.txt");
			String input = conf.getAbsolutePath();

			//db.ConnectDB("jdbc:mysql://localhost:3306/", "boyangtest", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "sakila", "root", "boyang", "com.mysql.jdbc.Driver");
			//potholes
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "imdb", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "university", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "natbroke_db", "root", "2543120", "com.mysql.jdbc.Driver");  //CHENGL
			
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "durbodax", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "broker", "root", "boyang", "com.mysql.jdbc.Driver");
			//db.ConnectDB("jdbc:mysql://localhost:3306/", "verse", "root", "boyang", "com.mysql.jdbc.Driver");
			db.ConnectDB("jdbc:mysql://localhost:3306/", "a2s", "root", "boyang", "com.mysql.jdbc.Driver");
			
			
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

		
		long P2Time   = System.currentTimeMillis();
		System.out.println("phase2 time : " + (P2Time-P1Time)/1000.0 + "s");
		
		
		
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

		
		
		if(basedOnDB){
			ArrayList<ReportRecord> conflictList = findConfliction(this.SCConstraints, DBConstraints, true);
			
			if(conflictList.size() != 0){
				System.out.println("\n\n#### Find conflicts !!! #######");
				bw.write("\n\n#### Find conflicts !!! #######\n"); //CHENGL
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
				bw.write("\n#### There is no conflict !!! \n"); //CHENGL
			}
		}else{
			
		}
		
		
		
		long P3Time   = System.currentTimeMillis();
		System.out.println("phase3 time : " + (P3Time-P2Time)/1000.0 + "s");
		
		bw.write("\nphase1 time : " + (P1Time-startTime)/1000.0 + "s\n"); //CHENGL
		bw.write("phase2 time : " + (P2Time-P1Time)/1000.0 + "s\n"); //CHENGL
		bw.write("phase3 time : " + (P3Time-P2Time)/1000.0 + "s\n"); //CHENGL
		totalTime = P3Time - startTime;
		
		System.out.println("total time : " + totalTime/1000.0 + "s");
		bw.write("total time : " + totalTime/1000.0 + "s\n"); //CHENGL
		bw.write("DB rule size : " + DBConstraints.size() + "\n");
		bw.write("SC rule size : " + SCConstraints.size() + "\n");
		
		long t2 = r.totalMemory()/1024;      
		long f2 = r.freeMemory()/1024;  
		System.out.println(t1+"KB");    //                           9356KB
        System.out.println(f1+"KB");    //   
		System.out.println(t2+"KB");    //                           9356KB
	    System.out.println(f2+"KB");    //                           4917KB
		System.out.println("size of memory : " + ((t2-f2)-(t1-f1))  +"kb");
		bw.write("size of memory : " + ((t2-f2)-(t1-f1))); //CHENGL
		
		
		for(String str: conflictFunctions){
			System.out.println(str);
			bw.write(str+"\n"); //CHENGL
		}
		bw.close(); //CHENGL
		
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
