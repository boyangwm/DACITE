package entry;
import java.util.ArrayList;
import java.util.List;

import com.constraint.Constraint;

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
