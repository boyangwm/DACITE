package entry;
import static choco.Choco.gt;
import static choco.Choco.implies;
import static choco.Choco.makeIntVar;
import static choco.Choco.not;
import expression.CBParser;
import gov.nasa.jpf.symbc.numeric.PCParser;

import java.util.ArrayList;
import java.util.List;

import choco.kernel.model.variables.integer.IntegerVariable;

import com.constraint.Constraint;
import com.constraint.ConstraintBuilder;

import solver.ProblemChocoInt;
import solver.UniversalImplication;
import soot.PackManager;
import soot.Transform;


public class Dacite {
	
	public static List<Constraint> SCConstraints = new ArrayList<Constraint>(); 
	
	public void run(String[] args){
		System.out.println("Run DACITE... ");
		
		// source code analysis
		if(args.length == 0)
		{
			System.out.println("Syntax: java Main <classfile> [soot options]");
			System.exit(0);
		}
		PackManager.v().getPack("jtp").add(new Transform("jtp.StmtIteration", 
				IntraAnalysis.v()));
		soot.Main.main(args);
		
		printSCconstraints();
		
		//test
		Constraint c1 = SCConstraints.get(0);
		ConstraintBuilder c1L = c1.getLeft(); 
		ConstraintBuilder c1R = c1.getRight();
		
		
		
		
		UniversalImplication imp = new UniversalImplication(c1L, c1R);
		
		
	
		
		
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
