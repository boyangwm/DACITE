package expression;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import soot.*;
import soot.jimple.*;

public class ExprManager implements Cloneable{

	// should be a set of value with conditions. 
	IntegerExpression exp; 

	public boolean initialized = false;

	public ExprManager(IntegerExpression v){
		this.exp = v;
	}

	public void update(IntegerExpression v){
		this.exp = v;
	}


	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(exp.toString());
		return result.toString();
	}

}
