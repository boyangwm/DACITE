package expression;


//import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
//import gov.nasa.jpf.symbc.numeric.solvers.ProblemCoral;
//import gov.nasa.jpf.symbc.numeric.solvers.ProblemGeneral;
import solver.ProblemChocoInt;




import gov.nasa.jpf.symbc.numeric.*;
import static choco.Choco.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import choco.Choco;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;

import com.constraint.ConstraintBuilder;


// parses PCs

public class CBParser {

	static ProblemChocoInt pb = new ProblemChocoInt();
	//static public Map<SymbolicReal, Object>	symRealVar; // a map between symbolic real variables and DP variables
	public static Map<String, Object>	symStringToVar; // a map between symbolic variables and DP variables
	public static ArrayList<String> varList;

	//static Boolean result; // tells whether result is satisfiable or not
	static int tempVars = 0; //Used to construct "or" clauses

	//	 Converts IntegerExpression's into DP's IntExp's
	static Object getExpression(IntegerExpression eRef) {
		assert eRef != null;
		assert !(eRef instanceof IntegerConstant);

		if (eRef instanceof SymbolicInteger) {
			String name = ((SymbolicInteger)eRef).getName();
			Object dp_var = symStringToVar.get(name);
			if (dp_var == null) {
				dp_var = pb.makeIntVar(name, ((SymbolicInteger)eRef)._min, ((SymbolicInteger)eRef)._max);
				symStringToVar.put(name, dp_var);
			}
			if(!varList.contains(name))
				varList.add(name);
			return dp_var;
		}

		if (eRef instanceof DbInteger){
			String name = ((DbInteger)eRef).getName();
			Object dp_var = symStringToVar.get(name);
			if (dp_var == null) {
				dp_var = pb.makeIntVar(name, ((DbInteger)eRef)._min, ((DbInteger)eRef)._max);

				//System.out.println("dp_Var hashCode : " + dp_var.hashCode());
				symStringToVar.put(name, dp_var);
			}
			if(!varList.contains(name))
				varList.add(name);
			return dp_var;
		}


		Operator    opRef;
		IntegerExpression	e_leftRef;
		IntegerExpression	e_rightRef;

		if(eRef instanceof BinaryLinearIntegerExpression) {
			//System.out.println("BinaryLinearIntegerExpression true");
			opRef = ((BinaryLinearIntegerExpression)eRef).op;
			e_leftRef = ((BinaryLinearIntegerExpression)eRef).left;
			e_rightRef = ((BinaryLinearIntegerExpression)eRef).right;
		} else { // bin non lin expr
			throw new RuntimeException("## Error: Binary Non Linear Expression " + eRef);
		}
		switch(opRef){
		case PLUS:
			if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.plus(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.plus(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else
				return pb.plus(getExpression(e_leftRef),getExpression(e_rightRef));
		case MINUS:
			//System.out.println("Minus true");
			if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.minus(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.minus(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else{
				//System.out.println("Minus true e_leftRef " + e_leftRef);
				//System.out.println("Minus true e_rightRef " + e_rightRef);
				//System.out.println("Minus true return " +  pb.minus(getExpression(e_leftRef),getExpression(e_rightRef)));
				return pb.minus(getExpression(e_leftRef),getExpression(e_rightRef));
			}
		case MUL:
			if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.mult(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.mult(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
			else {

				throw new RuntimeException("## Error: Binary Non Linear Operation");
			}
		case DIV:
			if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant) // TODO: this might not be linear
				return pb.div(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.div(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else {

				throw new RuntimeException("## Error: Binary Non Linear Operation");
			}
		case AND:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.and(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.and(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
			else
				return pb.and(getExpression(e_leftRef),getExpression(e_rightRef));
		case OR:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.or(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.or(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
			else
				return pb.or(getExpression(e_leftRef),getExpression(e_rightRef));
		case XOR:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.xor(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.xor(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
			else
				return pb.xor(getExpression(e_leftRef),getExpression(e_rightRef));
		case SHIFTR:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.shiftR(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.shiftR(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else
				return pb.shiftR(getExpression(e_leftRef),getExpression(e_rightRef));
		case SHIFTUR:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.shiftUR(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.shiftUR(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else
				return pb.shiftUR(getExpression(e_leftRef),getExpression(e_rightRef));
		case SHIFTL:
			if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
				throw new RuntimeException("## Error: this is not a symbolic expression"); //
			else if (e_leftRef instanceof IntegerConstant)
				return pb.shiftL(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
			else if (e_rightRef instanceof IntegerConstant)
				return pb.shiftL(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
			else
				return pb.shiftL(getExpression(e_leftRef),getExpression(e_rightRef));
		default:
			throw new RuntimeException("## Error: Binary Non Linear Operation");
		}


	}




	static public Object createDPLinearIntegerConstraint(LinearIntegerConstraint cRef) {

		Comparator c_compRef = cRef.getComparator();

		IntegerExpression c_leftRef = (IntegerExpression)cRef.getLeft();
		IntegerExpression c_rightRef = (IntegerExpression)cRef.getRight();
		
		//System.out.println("c_leftRef : " + c_leftRef);
		//System.out.println("c_rightRef : " + c_rightRef);

		switch(c_compRef){
		case EQ:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value == ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.eq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.eq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else{
				//System.out.println("case 4");
				Object o1 = getExpression(c_leftRef);
				Object o2 = getExpression(c_rightRef);
				//System.out.println("case 4 left : "+ (IntegerExpressionVariable)o1);
				//System.out.println("case 4 right : "+ (IntegerExpressionVariable)o2);
				choco.kernel.model.constraints.Constraint o =  (choco.kernel.model.constraints.Constraint)pb.eq(o1,2);
	
				
				//System.out.println("case 4 return : "+ o.toString());
				Variable[] varray = o.extractVariables();
				//for(Variable var : varray){
				//	System.out.println("var: "+ var.toString());
				//}
				return pb.eq(getExpression(c_rightRef),getExpression(c_leftRef));
			}
			//break;
		case NE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value != ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.neq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.neq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else
				return pb.neq(getExpression(c_leftRef),getExpression(c_rightRef));
			//break;
		case LT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value < ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.lt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.lt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else
				return pb.lt(getExpression(c_leftRef),getExpression(c_rightRef));
			//break;
		case GE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value >= ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.geq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.geq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else
				return pb.geq(getExpression(c_leftRef),getExpression(c_rightRef));
			//break;
		case LE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value <= ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.leq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.leq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else
				return pb.leq(getExpression(c_leftRef),getExpression(c_rightRef));
			//break;
		case GT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value > ((IntegerConstant) c_rightRef).value))
					return Choco.FALSE;
				else
					return Choco.TRUE;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				return pb.gt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				return pb.gt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value);
			}
			else
				return pb.gt(getExpression(c_leftRef),getExpression(c_rightRef));
			//break;
		}

		return Choco.TRUE;
	}

	// result is in pb
	//public static ArrayList<Object> parse(ConstraintBuilder cb,  Map<String, Object> parasymStringToVar) {
	public static Object parse(ConstraintBuilder cb,  Map<String, Object> parasymStringToVar, 
			ArrayList<String> paraVarList) {


		Object returnC = Choco.TRUE;
		//ArrayList<Object> returnC = new ArrayList<Object>();

		if (cb == null || cb.count == 0) {
			return null;
		}

		//symStringToVar = new HashMap<String,Object>();
		symStringToVar = parasymStringToVar;
		varList = paraVarList;
		//result = null;
		tempVars = 0;

		Constraint cRef = cb.header;

		while (cRef != null) {

			if (cRef instanceof LinearIntegerConstraint){
				//System.out.println("cRef  :" + cRef);
				Object CurC = createDPLinearIntegerConstraint((LinearIntegerConstraint)cRef);// create choco linear integer constraint
				//returnC = pb.logicAnd(returnC, CurC);
				//returnC.add(CurC);
				//System.out.println("CurC  :" + CurC);
				if(returnC == null)
					returnC = CurC;
				else
					returnC = pb.logicAnd(returnC, CurC);
			}
			else {
				throw new RuntimeException("## Error: Not LinearIntegerConstraint !!!!! ");
			}

			cRef = cRef.and;
		}

		//parasymStringToVar = symStringToVar;
		//System.out.println("returnC  :" + returnC);
		return returnC;
	}
}