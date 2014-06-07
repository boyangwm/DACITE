package expression;
import gov.nasa.jpf.Config;
//import soot.PackManager;
//import soot.Transform;
//import soot.jimple.StringConstant;


import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.string.StringSymbolic;



public class Test2 {
	public static void main(String[] args)
	{
		
		PathCondition pc = new PathCondition();
		StringSymbolic var1 = new StringSymbolic("var1");
		SymbolicInteger si =  new SymbolicInteger("sym1");
		pc._addDet(Comparator.EQ, var1._charAt(si), new IntegerConstant(50));
		//var[si] == d

		pc._addDet(Comparator.LT, si, new IntegerConstant(3));
		//si < 3
		
		System.out.print(pc.toString());

		
		

		/*
		String[] options = {"+symbolic.dp=choco",
				"+symbolic.string_dp=automata",
				"+symbolic.string_dp_timeout_ms=0",
		"+symbolic.string_preprocess_only=true"};
		Config cfg = new Config(options);
		new SymbolicInstructionFactory(cfg);
		PathCondition pc = new PathCondition();
		StringPathCondition stringCurrentPC = new StringPathCondition(pc);
		StringSymbolic var1 = new StringSymbolic("var1");
		SymbolicInteger si =  new SymbolicInteger("sym1");
		pc._addDet(Comparator.EQ, var1._charAt(si), new IntegerConstant('d'));
		//var[si] == d

		pc._addDet(Comparator.LT, si, new IntegerConstant(3));
		//si < 3
		stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("abc"), var1);
		System.out.println(stringCurrentPC);
		boolean result = stringCurrentPC.simplify();
		//assertTrue(!result);
		System.out.print(result);// = stringCurrentPC.simplify();

//		boolean result = stringCurrentPC.simplify();
//		assertTrue(!result);

		 */

		//		System.out.print("Hello");
		//		ExprManager em = new ExprManager(StringConstant.v("abc"));
		//		System.out.println(em.toString());
		//		try {
		//			ExprManager em_clone = (ExprManager)em.clone();
		//			System.out.println(em_clone.toString());
		//			em.update(StringConstant.v("abc2"));
		//			System.out.println(em.toString());
		//			System.out.println(em_clone.toString());
		//		} catch (CloneNotSupportedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	
	}

}
