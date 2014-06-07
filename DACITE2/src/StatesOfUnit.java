import expression.ExprManager;
import expression.StmtCondition;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import soot.Local;
public class StatesOfUnit {

	/**
	 * map all local variables to their expressions. 
	 */
	private Map<Local, IntegerExpression> mapPreState;
	StmtCondition preCon;
	private Map<Local, IntegerExpression> mapPostState;
	private StmtCondition postCon;
	//store else branch condition 
	private StmtCondition postBranchCon;

	private boolean isBranch = false;

	
	public StmtCondition getPreCon (){
		return preCon.make_copy();
	}
	
	public StmtCondition getPostCon (){
		return postCon.make_copy();
	}
	
	public StmtCondition getPostBranchCon (){
		return postBranchCon.make_copy();
	}
	
	
	public void setPreCon (StmtCondition sc){
		preCon = sc;
	}
	
	public void setPostCon (StmtCondition sc){
		postCon = sc;
	}
	
	public void setPostBranchCon (StmtCondition sc){
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


	public StatesOfUnit(Map<Local, IntegerExpression> map)
	{
		Set<Entry<Local, IntegerExpression>> set_temp = map.entrySet();
		mapPreState = new HashMap<Local, IntegerExpression>();
		for (Entry<Local, IntegerExpression> e : set_temp){
			mapPreState.put(e.getKey(), e.getValue());
		}
		mapPostState = null;
	}



	/**
	 * update the precondition 
	 * @param map
	 */
	public void updatePreState(Map<Local, IntegerExpression> map)
	{
		mapPreState = map;
	}


	/**
	 * update the postcondition 
	 * @param map
	 */
	public void updatePostState(Map<Local, IntegerExpression> map)
	{
		mapPostState = map;
	}


	/**
	 * make a copy of the pre state map and return
	 * @return
	 */
	public Map<Local, IntegerExpression> getPre()
	{
		Set<Entry<Local, IntegerExpression>> set_temp = mapPreState.entrySet();
		Map<Local, IntegerExpression> mapReturn = new HashMap<Local, IntegerExpression>();
		for (Entry<Local, IntegerExpression> e : set_temp){
			mapReturn.put(e.getKey(), e.getValue());
		}
		return mapReturn;
	}



	/**
	 * make a copy of the post state map and return
	 * @return
	 */
	public Map<Local, IntegerExpression> getPost()
	{
		Set<Entry<Local, IntegerExpression>> set_temp = mapPostState.entrySet();
		Map<Local, IntegerExpression> mapReturn = new HashMap<Local, IntegerExpression>();
		for (Entry<Local, IntegerExpression> e : set_temp){
			mapReturn.put(e.getKey(), e.getValue());
		}
		return mapReturn;
	}


	public void printPreState(){
		System.out.print("\nPRE: ");
		for (Map.Entry<Local, IntegerExpression> entry : mapPreState.entrySet())
		{
			System.out.print(entry.getKey().getName() + " -> " + entry.getValue() + " | ");
		}
		System.out.println("");
	}


	public void printPostState(){
		System.out.print("POST: ");
		for (Map.Entry<Local, IntegerExpression> entry : mapPostState.entrySet())
		{
			System.out.print (entry.getKey().getName() + " -> " + entry.getValue() + " | ");
		}
		System.out.println("");
	}
	
	public void printPreCondition(){
		System.out.println ("IN The CONDITION: " + preCon.toString());
	}
}
