package entry;
import static choco.Choco.gt;
import static choco.Choco.implies;
import static choco.Choco.makeIntVar;
import static choco.Choco.not;
import expression.CBParser;
import gov.nasa.jpf.symbc.numeric.PCParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import choco.kernel.model.variables.integer.IntegerVariable;

import com.constraint.Constraint;
import com.constraint.ConstraintBuilder;
import com.rulemanager.DbRulesManager;
import com.rulemanager.TableRulesManager;

import solver.ProblemChocoInt;
import solver.UniversalImplication;
import soot.PackManager;
import soot.Transform;


public class Dacite {

	//source code constraint
	//it stores all constraints (DACITE format) by running the code analysis. 
	public static List<Constraint> SCConstraints = new ArrayList<Constraint>(); 

	public void run(String[] args){
		System.out.println("Run DACITE... ");

		//##### 1. source code analysis #######
		if(args.length == 0)
		{
			System.out.println("Syntax: java Main <classfile> [soot options]");
			System.exit(0);
		}
		PackManager.v().getPack("jtp").add(new Transform("jtp.StmtIteration", 
				IntraAnalysis.v()));
		soot.Main.main(args);

		//##### 2. association rule mining #######
		List<Constraint> DBConstraints = new ArrayList<Constraint> ();
		DbRulesManager db = new DbRulesManager();
		try {

			//import configuration
			File file = new File("C:\\Users\\Boyang\\git\\DatabaseRuleMining"
					+ "\\KmenasWithFpgrowth\\config.txt");
			String input = file.getAbsolutePath();

			db.ConnectDB("jdbc:mysql://localhost:3306/", "boyangtest", "root", "boyang", "com.mysql.jdbc.Driver");
			db.ImportConfig(input);
			db.Run(2, 0.1, 0.1);
			TableRulesManager trm = db.getTable("person");

			DBConstraints = trm.getConstraints();
			int i = 0;
			for(Constraint constraint : DBConstraints){
				System.out.println("Rule " + i++ + " : " + constraint.toString());
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//test
		printSCconstraints();
		Constraint c1 = SCConstraints.get(0);
		ConstraintBuilder c1L = c1.getLeft(); 
		ConstraintBuilder c1R = c1.getRight();


		for(Constraint c2 : DBConstraints){

			ConstraintBuilder c2L = c2.getLeft(); 
			ConstraintBuilder c2R = c2.getRight();


			//System.out.println("c1L : " + c1L.toString());  // ( , 30]
			//System.out.println("c2L : " + c2L.toString());  // [20  , 29]

			//UniversalImplication imp = new UniversalImplication(c1L, c2L);
			UniversalImplication imp = new UniversalImplication(c2L, c1L);
			System.out.println("VALID ?  " + imp.valid());

		}
		//		IntegerVariable age = makeIntVar("age", -100, 100);
		//		//IntegerVariable age2 = makeIntVar("age2", -100, 100);
		//
		//		Constraint c1 =  gt(age, 25);  // age > 25
		//		Constraint c2 =  gt(age, 35);  // age > 35
		//
		//		m.addConstraint(not(implies(c1, c2)));
		//		s.read(m);
		//		s.solve();
		//	
		//		//System.out.print(s.getVar(v).getVal());
		//		System.out.print("solve : " + !s.solve());



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
