package expression;

import static expression.ExpressionUtil.makeIntConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import soot.Immediate;
import soot.Local;
import soot.Value;
import soot.jimple.CmpExpr;
import soot.jimple.ConditionExpr;
import soot.jimple.DoubleConstant;
import soot.jimple.FloatConstant;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Ref;
import soot.jimple.StringConstant;
import soot.jimple.internal.*;
import gov.nasa.jpf.symbc.numeric.BinaryNonLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;




/**
 * Created on: May 1, 2014 
 * Reference JPF
 * @author Boyang
 * 
 */
public class ExpressionUtil {


	/**
	 * @param str
	 * @return a symbolic integer variable, such as INT_x, INT_y
	 */
	public static IntegerExpression makeIntVar(String str) {
		SymbolicInteger sInt = new SymbolicInteger(str);
		return sInt;
	}


	//	public static IntegerExpression makeIntVar(String str, Map<String,Expression> expressionMap) {
	//		if(expressionMap.containsKey(str)) {
	//			try{
	//				return (IntegerExpression)expressionMap.get(str);
	//			}catch(Exception ex){
	//				throw new RuntimeException("## Error: type confliction");
	//			}
	//		}else
	//		{
	//			SymbolicInteger sInt = new SymbolicInteger(str);
	//			expressionMap.put(str, sInt);
	//			return sInt;
	//		}
	//	}


	/**
	 * @param i
	 * @return a integer constant
	 */
	public static IntegerExpression makeIntConst(int i) {
		return new IntegerConstant(i);
	}




	/**
	 * Transfer value from Soot to Expression in JPF
	 * @param v
	 * @return
	 */
	public static IntegerExpression transferValueToExp(Value v, Map<Local,IntegerExpression> expressionMap){

		if(v instanceof IntConstant || v instanceof JimpleLocal){
			return transferSimpleValue(v, expressionMap);
		}	
		//all BinopExpr cases
		else if(v instanceof JAddExpr){
			//a+b
			JAddExpr jExpr = (JAddExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._plus(ie2);			
		} else if(v instanceof JAndExpr){
			//a&b
			JAndExpr jExpr = (JAndExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._and(ie2);
		} else if(v instanceof JCmpExpr){
			//<==>
			throw new RuntimeException("## Exception: JCmpExpr type doesn't handle");
		} else if(v instanceof JCmpgExpr){
			throw new RuntimeException("## Exception: JCmpgExpr type doesn't handle");
		} else if(v instanceof JCmplExpr){
			throw new RuntimeException("## Exception: JCmplExpr type doesn't handle");
		} else if(v instanceof ConditionExpr){
			//EQ
			//Eg
			//...
			throw new RuntimeException("## Exception: ConditionExpr type doesn't handle");
		} else if(v instanceof JDivExpr){
			//a/b
			JDivExpr jExpr = (JDivExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._div(ie2);
		} else if(v instanceof JMulExpr){
			//a*b
			JMulExpr jExpr = (JMulExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._mul(ie2);
		} else if(v instanceof JOrExpr){
			//a|b
			JOrExpr jExpr = (JOrExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._or(ie2);
		} else if(v instanceof JRemExpr){
			throw new RuntimeException("## Exception: JRemExpr type doesn't handle");
		} else if(v instanceof JShlExpr){
			//a<<b
			JShlExpr jExpr = (JShlExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._shiftL(ie2);
		} else if(v instanceof JShrExpr){
			//a>>b
			JShrExpr jExpr = (JShrExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._shiftR(ie2);
		} else if(v instanceof JSubExpr){
			//a-b
			JSubExpr jExpr = (JSubExpr) v;
			Value op1 = jExpr.getOp1();
			Value op2 = jExpr.getOp2();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			return ie1._minus(ie2);
		} else if(v instanceof JUshrExpr){
			throw new RuntimeException("## Exception: JUshrExpr type doesn't handle");
		} else if(v instanceof JXorExpr){
			throw new RuntimeException("## Exception: JXorExpr type doesn't handle");
		}

		else if(v instanceof InvokeExpr){
			throw new RuntimeException("## Exception: InvokeExpr type doesn't handle");
		}
		
		else if(v instanceof Ref){
			throw new RuntimeException("## Exception: Ref type doesn't handle");
		}
		else if(v instanceof Immediate){
			throw new RuntimeException("## Exception: Ref type doesn't handle");
		}
		else if(v instanceof Local){
			throw new RuntimeException("## Exception: Ref type doesn't handle");
		}
		throw new RuntimeException("## Exception: type doesn't handle");

	}



	/**
	 * Op value is either IntConstant or JimpleLocal which can be fund in the expressionMap  
	 * @param v
	 * @param expressionMap
	 * @return
	 */
	public static IntegerExpression transferSimpleValue(Value v, Map<Local,IntegerExpression> expressionMap)
	//change to Local
	{
		if(v instanceof IntConstant){
			IntConstant ic = (IntConstant)v;
			IntegerExpression ie = makeIntConst(ic.value);
			return ie; 
		}
		if(v instanceof JimpleLocal){
			JimpleLocal jl = (JimpleLocal)v;
			if(expressionMap.containsKey(jl)) {
				return expressionMap.get(jl);
			}else{
				return makeIntVar(jl.getName());
				//throw new RuntimeException("## Exception: should be in the map");
			}
		}
		if(v instanceof DoubleConstant){
			DoubleConstant dc = (DoubleConstant)v;
			int i = (int)(dc.value);
			IntegerExpression ie = makeIntConst(i);
			return ie; 
		}
		if(v instanceof FloatConstant){
			FloatConstant fc = (FloatConstant)v;
			int i = (int)(fc.value);
			IntegerExpression ie = makeIntConst(i);
			return ie; 
		}
		System.out.println("v : " + v.toString());
		
		
		throw new RuntimeException(
				"## Exception: transferSimpleValue type only handle simple cases");
		
	}

	public static void transferConditionExp(Value condition, 
			Map<Local,IntegerExpression> expressionMap, 
			StmtCondition preCondition, boolean isElse)
	{
		//System.out.println("condition : " + condition.toString());
		if(condition instanceof ConditionExpr){
			ConditionExpr cExp = (ConditionExpr)condition;
			Value op1 = cExp.getOp1();
			IntegerExpression ie1 = transferSimpleValue(op1, expressionMap);
			Value op2 = cExp.getOp2();
			IntegerExpression ie2 = transferSimpleValue(op2, expressionMap);
			if(!isElse){
				if(cExp instanceof JLtExpr){
					preCondition._addDet(Comparator.LT, ie1, ie2);
				} else if(cExp instanceof JGeExpr){
					preCondition._addDet(Comparator.GE, ie1, ie2);
				} else if(cExp instanceof JGtExpr){
					preCondition._addDet(Comparator.GT, ie1, ie2);
				} else if(cExp instanceof JLeExpr){
					preCondition._addDet(Comparator.LE, ie1, ie2);
				} else if(cExp instanceof JEqExpr){
					preCondition._addDet(Comparator.EQ, ie1, ie2);
				} else if(cExp instanceof JNeExpr){
					preCondition._addDet(Comparator.NE, ie1, ie2);
				}
			}else{
				if(cExp instanceof JLtExpr){
					preCondition._addDet(Comparator.GE, ie1, ie2);
				} else if(cExp instanceof JGeExpr){
					preCondition._addDet(Comparator.LT, ie1, ie2);
				} else if(cExp instanceof JGtExpr){
					preCondition._addDet(Comparator.LE, ie1, ie2);
				} else if(cExp instanceof JLeExpr){
					preCondition._addDet(Comparator.GT, ie1, ie2);
				} else if(cExp instanceof JEqExpr){
					preCondition._addDet(Comparator.NE, ie1, ie2);
				} else if(cExp instanceof JNeExpr){
					preCondition._addDet(Comparator.EQ, ie1, ie2);
				}
			}
		}else{
			throw new RuntimeException(
					"## Exception: type is not found");
		}
	}
	
	
	
	public static void updateAnnotationInMap(List <Value> args, Map<Local,IntegerExpression> expressionMap){
		
		assert(args.get(0) instanceof StringConstant &&
				args.get(1) instanceof StringConstant &&
				args.get(2) instanceof StringConstant &&
				args.get(3) instanceof IntConstant);
		
		
		int isSource = ((IntConstant)args.get(3)).value; 
		System.out.println("isSource : " +  isSource);
		
		String varName = ((StringConstant)args.get(0)).value; 
		System.out.println("Variable Name : " +  varName);
		String tableName = ((StringConstant)args.get(1)).value; 
		System.out.println("Table Name : " +  tableName);
		String colName = ((StringConstant)args.get(2)).value; 
		System.out.println("Column Name : " +  colName);
		
		
		
		if(isSource == 1){
			DbSourceInteger dbs = new DbSourceInteger(tableName, colName);
			Set<Entry<Local, IntegerExpression>> set_temp = expressionMap.entrySet();
			for (Entry<Local, IntegerExpression> e : set_temp){
				if(e.getKey().getName().equals(varName))
				{
					expressionMap.put(e.getKey(), dbs);
				}
				
			}
			
			System.out.println("Done updateAnnotationInMap");
		}else{
			
		}
		
	}



}
