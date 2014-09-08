package com.constraint;

import com.algorithms.associationrules.agrawal94_association_rules.Rule;
import com.patterns.itemset_array_integers_with_count.Itemset;

import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

/**
 * This class represents a constraint of an association rule.
 * 
 * @author Qi Hu
 * @see Constraints
 * @see ConstraintBuilder
 */
public class Constraint {
	private ConstraintBuilder lefthand = new ConstraintBuilder();
	private ConstraintBuilder righthand = new ConstraintBuilder();

	/**
	 * @param rule
	 * @return corresponding constraint of rule 
	 */
	public Constraint(String tableName, Rule rule) {
		lefthand = convert(tableName, rule.getItemset1());
		righthand = convert(tableName, rule.getItemset2());
	}



	public Constraint(ConstraintBuilder left, ConstraintBuilder right) {
		lefthand = left;
		righthand = right;
	}


	/**
	 * 
	 * @return left side constraint
	 */	
	public ConstraintBuilder getLeft() {
		return lefthand;
	}

	/**
	 * 
	 * @return right side constraint
	 */
	public ConstraintBuilder getRight() {
		return righthand;
	}

	/**
	 * @param itemset
	 * @return corresponding constraint of itemset
	 */
	public ConstraintBuilder convert(String tableName, Itemset itemset){
		ConstraintBuilder halfside = new ConstraintBuilder();
		for (int i=0; i<itemset.size(); i++){
			if(itemset.getMin(i).equals(itemset.getMax(i))){ // min value is equal to max value
				if(itemset.getString(i).equals("Not a String Value")){ // output x=value
					IntegerExpression name = makeIntVar(tableName + "." + itemset.getD(i));
					int value = (int) itemset.getMin(i).doubleValue();
					IntegerExpression min = makeIntConst(value);
					halfside._addDet(Comparator.EQ, name, min);
				}
				else{ // output x=stringvalue
					IntegerExpression name = makeIntVar(tableName + "." + itemset.getD(i));
					IntegerExpression value = makeIntVar(itemset.getString(i));
					halfside._addDet(Comparator.EQ, name, value);
				}
			}
			else{ //min value is different from max value, output x>=min value and x<=max value
				IntegerExpression name = makeIntVar(tableName + "." + itemset.getD(i));
				int value = (int) itemset.getMin(i).doubleValue();
				IntegerExpression min = makeIntConst(value);
				halfside._addDet(Comparator.GE, name, min);
				value = (int) itemset.getMax(i).doubleValue();
				IntegerExpression max = makeIntConst(value);
				halfside._addDet(Comparator.LE, name, max);
			}
		}
		return halfside;
	}

	public String toString() {
		try{
			return lefthand.toString()+" ==> "+righthand.toString();
		}catch(NullPointerException ex){
			return "Non Linear Integer Constraint. ";
		}
	}

	/**
	 * @param str
	 * @return a symbolic integer variable, such as INT_x, INT_y
	 */
	public IntegerExpression makeIntVar(String str) {
		SymbolicInteger sInt = new SymbolicInteger(str);
		return sInt;
	}

	/**
	 * @param i
	 * @return a integer constant
	 */
	public IntegerExpression makeIntConst(int i) {
		return new IntegerConstant(i);
	}
}
