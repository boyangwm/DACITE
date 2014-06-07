package expression;

import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.LinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.MinMax;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

import java.util.Map;
import java.util.Random;

public class DbSourceInteger extends LinearIntegerExpression{
	public static int UNDEFINED = Integer.MIN_VALUE;;
	public int _min = 0;
	public int _max = 0;
	public int solution = UNDEFINED; // C

	//private String name;
	private String table;
	private String column;


	int unique_id;

	public static String SYM_INTDB_SUFFIX = "_DB";


	//	public DbInteger () {
	//		super();
	//		unique_id = MinMax.UniqueId++;
	//		PathCondition.flagSolved=false;
	//		name = "INTDB_" + hashCode();
	//		_min = MinMax.getVarMinInt(name);
	//		_max = MinMax.getVarMaxInt(name);
	//	}

	public DbSourceInteger (String table, 
			String column) {
		super();
		unique_id = MinMax.UniqueId++;
		//PathCondition.flagSolved=false;
		//name = s;
		this.table = table;
		this.column = column;
		_min = MinMax.getVarMinInt(column);
		_max = MinMax.getVarMaxInt(column);
		//trackedSymVars.add(fixName(name));

	}

	public String getName() {
		//return (name != null) ? name : "INTDB_" + hashCode();
		return (column != null) ? column : "INTDB_" + hashCode();
	}


	public String stringPC () {
		//return (name != null) ? name : "INTDB_" + hashCode();
		return (column != null) ? column : "INTDB_" + hashCode();
	}


	public String toString () {
		//return (name != null) ? name + "[" + solution + "]" :
		//	"INTDB_" + hashCode() + "[" + solution + "]";
		return //" [name] " + name + 
				//" [table] " + table +  
				//" [column] " + column;
				table + "." + column;
	}



	public void getVarsVals(Map<String,Object> varsVals) {
		//varsVals.put(fixName(name), solution);
		varsVals.put(fixName(column), solution);
	}

	private String fixName(String name) {
		if (name.endsWith(SYM_INTDB_SUFFIX)) {
			name = name.substring(0, name.lastIndexOf(SYM_INTDB_SUFFIX));
		}
		return name;
	}

	public boolean equals (Object o) {
		return (o instanceof DbSourceInteger) &&
				(this.equals((DbSourceInteger) o));
	}

	private boolean equals (DbSourceInteger s) {
		return this.unique_id == s.unique_id;
	}

	public int hashCode() {
		//return Integer.toHexString(_min ^ _max).hashCode();
		return unique_id;
	}

	protected void finalize() throws Throwable {
		//System.out.println("Finalized " + this);
	}

	@Override
	public void accept(ConstraintExpressionVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

	@Override
	public int compareTo(Expression expr) {
		if (expr instanceof SymbolicInteger) {
			DbSourceInteger e = (DbSourceInteger) expr;
			int a = unique_id;
			int b = e.unique_id;
			return (a < b) ? -1 : (a > b) ? 1 : 0;
		} else {
			return getClass().getCanonicalName().compareTo(expr.getClass().getCanonicalName());
		}
	}
	
	public boolean isSource()
	{
		return true;
	}

}
