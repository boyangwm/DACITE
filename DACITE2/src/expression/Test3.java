package expression;

import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.string.StringSymbolic;
import static expression.ExpressionUtil.*;

public class Test3 {
	public static void main(String[] args)
	{
//		PathCondition pc = new PathCondition();
//		StringSymbolic var1 = new StringSymbolic("var1");
//		SymbolicInteger si =  new SymbolicInteger("sym1");
//		pc._addDet(Comparator.EQ, var1._charAt(si), new IntegerConstant(50));
//		//var[si] == d
//
//		pc._addDet(Comparator.LT, si, new IntegerConstant(3));
//		//si < 3
//		
//		System.out.println(pc.toString());
		
		Map<String,IntegerExpression> expressionMap = new HashMap<String, IntegerExpression>();
		IntegerExpression ie6 = makeIntConst(6);
		IntegerExpression ie5 = makeIntConst(5);
		IntegerExpression iea = makeIntVar("a");
		IntegerExpression ie2 = makeIntConst(2);
		IntegerExpression ie7 = makeIntConst(7);
		IntegerExpression ieb = makeIntVar("b");
		IntegerExpression iea2 = makeIntVar("a");
		
		

		System.out.println(iea.hashCode());
		System.out.println(ieb.hashCode());
		System.out.println(iea2.hashCode());
		
		//-1 < 2 * b - a < 4 
		IntegerExpression new_ie1 = ie2._mul(ieb)._minus(iea); //2 * b - a 
		StmtCondition cb = new StmtCondition();
		cb._addDet(Comparator.LT,new_ie1 , 4);
		cb._addDet(Comparator.GT,new_ie1 , -1);
		System.out.println(cb.toString());
		
		//5*a + b == 7 && a > b
		IntegerExpression new_ie3 = ie5._mul(iea)._and(ieb); //5*a + b
		StmtCondition cb2 = new StmtCondition();
		cb2._addDet(Comparator.EQ,new_ie3 , 7);
		cb2._addDet(Comparator.GT,iea , ieb);
		System.out.println(cb2.toString());
		
		
		
		//cb.appendAllConjuncts(t);
		
		

	}
}
