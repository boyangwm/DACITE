package entry;

import soot.PackManager;
import soot.Transform;



public class Test {
	public static void main(String[] args)
	{
		System.out.println("Hello  ");
		if(args.length == 0)
		{
			System.out.println("Syntax: java Main <classfile> [soot options]");
			System.exit(0);
		}
		PackManager.v().getPack("jtp").add(new Transform("jtp.StmtIteration", 
				IntraAnalysis.v()));
		
		soot.Main.main(args);
	}
}
