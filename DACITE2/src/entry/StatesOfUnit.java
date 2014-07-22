package entry;
import expression.ExprManager;
import expression.StmtCondition;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.constraint.ConstraintBuilder;

import soot.Local;
import soot.Value;
public class StatesOfUnit {

	/**
	 * map all local variables to their expressions. 
	 */
	private Map<Value, IntegerExpression> mapPreState;
	ConstraintBuilder preCon;
	private Map<Value, IntegerExpression> mapPostState;
	private ConstraintBuilder postCon;
	//store else branch condition 
	private ConstraintBuilder postBranchCon;

	private boolean isBranch = false;

	
	public ConstraintBuilder getPreCon (){
		return preCon.make_copy();
	}
	
	public ConstraintBuilder getPostCon (){
		return postCon.make_copy();
	}
	
	public ConstraintBuilder getPostBranchCon (){
		return postBranchCon.make_copy();
	}
	
	
	public void setPreCon (ConstraintBuilder sc){
		preCon = sc;
	}
	
	public void setPostCon (ConstraintBuilder sc){
		postCon = sc;
	}
	
	public void setPostBranchCon (ConstraintBuilder sc){
		postBranchCon = sc;
	}
	
	

	/**
	 * GotoStmt and IfStmt will both return true. 
	 * @return
	 */
	public boolean isBranch(){
		return isBranch;
	}

	public void setIsBranch(boolean b){
		isBranch = b;
	}


	public StatesOfUnit(Map<Value, IntegerExpression> map)
	{
		Set<Entry<Value, IntegerExpression>> set_temp = map.entrySet();
		mapPreState = new HashMap<Value, IntegerExpression>();
		for (Entry<Value, IntegerExpression> e : set_temp){
			mapPreState.put(e.getKey(), e.getValue());
		}
		mapPostState = null;
	}



	/**
	 * update the precondition 
	 * @param map
	 */
	public void updatePreState(Map<Value, IntegerExpression> map)
	{
		mapPreState = map;
	}


	/**
	 * update the postcondition 
	 * @param map
	 */
	public void updatePostState(Map<Value, IntegerExpression> map)
	{
		mapPostState = map;
	}


	/**
	 * make a copy of the pre state map and return
	 * @return
	 */
	public Map<Value, IntegerExpression> getPre()
	{
		Set<Entry<Value, IntegerExpression>> set_temp = mapPreState.entrySet();
		Map<Value, IntegerExpression> mapReturn = new HashMap<Value, IntegerExpression>();
		for (Entry<Value, IntegerExpression> e : set_temp){
			mapReturn.put(e.getKey(), e.getValue());
		}
		return mapReturn;
	}



	/**
	 * make a copy of the post state map and return
	 * @return
	 */
	public Map<Value, IntegerExpression> getPost()
	{
		Set<Entry<Value, IntegerExpression>> set_temp = mapPostState.entrySet();
		Map<Value, IntegerExpression> mapReturn = new HashMap<Value, IntegerExpression>();
		for (Entry<Value, IntegerExpression> e : set_temp){
			mapReturn.put(e.getKey(), e.getValue());
		}
		return mapReturn;
	}


	public void printPreState(){
		System.out.print("\nPRE: ");
		for (Map.Entry<Value, IntegerExpression> entry : mapPreState.entrySet())
		{
			//System.out.print(entry.getKey().getName() + " -> " + entry.getValue() + " | ");
			System.out.print(entry.getKey().toString() + " -> " + entry.getValue() + " | ");
		}
		System.out.println("");
	}


	public void printPostState(){
		System.out.print("POST: ");
		for (Map.Entry<Value, IntegerExpression> entry : mapPostState.entrySet())
		{
			//System.out.print (entry.getKey().getName() + " -> " + entry.getValue() + " | ");
			System.out.print (entry.getKey().toString() + " -> " + entry.getValue() + " | ");
		}
		System.out.println("");
	}
	
	public void printPreCondition(){
		System.out.println ("IN The CONDITION: " + preCon.toString());
	}
}
