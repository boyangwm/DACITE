package com.rulemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algorithms.associationrules.agrawal94_association_rules.Rule;
import com.algorithms.associationrules.agrawal94_association_rules.Rules;
import com.constraint.Constraint;
import com.patterns.itemset_array_integers_with_count.Itemset;
import com.run_algorithms.DataToRules;

/**
 * This shows the implementation of TableRulesManager
 * @author Qi Hu
 * @see DbRulesManager
 */
public class TableRulesManager {
	public String tableName;
	public Rules Rules;
	public int tableSize;
	public Map<String, String> Attribute;
	public ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	
	/**
	 * Constructor
	 * @param tableName
	 * @param rules
	 * @param tableSize
	 */
	public TableRulesManager (String tableName, Rules rules, int tableSize){
		this.tableName = tableName;
		this.Rules = rules;
		this.tableSize = tableSize;
		this.Attribute = new HashMap<String, String>();
		constraints = transform(rules);
	}
	
	public String getTableName() {
		return this.tableName;
	}
	
	public int gettableSize() {
		return this.tableSize;
	}
	
	/*
	 * Get every attribute appearing in rules of this table.
	 */
	public void getAttributeOfTable() {
		for(Rule rule : this.Rules.rules){
			Itemset itemset1 = rule.getItemset1();
			for(int i = 0; i < itemset1.itemsetD.length; i++){
				if(!this.Attribute.containsKey(itemset1.itemsetD[i])){
					this.Attribute.put(itemset1.itemsetD[i], itemset1.itemsetD[i]);
				}
			}
			Itemset itemset2 = rule.getItemset2();
			for(int i = 0; i < itemset2.itemsetD.length; i++){
				if(!this.Attribute.containsKey(itemset2.itemsetD[i])){
					this.Attribute.put(itemset2.itemsetD[i], itemset2.itemsetD[i]);
				}
			}
		}
	}
	
	/**
	 * transform Rules to corresponding constraints
	 * @param Rules
	 * @return a list of constraints
	 */
	public ArrayList<Constraint> transform(Rules Rules) {
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		try{
			for(Rule rule : Rules.rules){
				Constraint constraint = new Constraint(tableName, rule);
				constraints.add(constraint);
			}
		} catch (Exception e) { e.printStackTrace();} 
		return constraints;
	}
	
	public ArrayList<Constraint> getConstraints() {
		return this.constraints;
	}
	
	public String toString (){
		// create a string buffer
		StringBuffer buffer = new StringBuffer(" -------Association Rules of Table ");
		buffer.append(tableName);
		buffer.append(" -------\n");
		buffer.append(" ------- k="+DataToRules.k+", minsup="+DataToRules.minsupp+", minconf="+DataToRules.minconf+" -------\n");
		int i = 1;
		for(Rule rule : this.Rules.rules){
			// append the rule, its support and confidence.
			buffer.append("   rule ");
			buffer.append(i);
			buffer.append(":  ");
			buffer.append(rule.toString());
	//		buffer.append("support :  ");
	//		buffer.append(rule.getRelativeSupport(this.tableSize));
	//		buffer.append(" (");
	//		buffer.append(rule.getAbsoluteSupport());
	//		buffer.append("/");
	//		buffer.append(this.tableSize);
	//		buffer.append(") ");
	//		buffer.append("confidence :  " );
	//		buffer.append(rule.getConfidence());
			buffer.append("\n");
			i++;
		}
		buffer.append(" ------- Constraints ------- \n");
		int count = 1;
		for(Constraint constraint : constraints){
			buffer.append("constraint "+count+": ");
			buffer.append(constraint.toString());
			buffer.append("\n");
			count++;
		}
	//	buffer.append("==================================================================");
	//	buffer.append("\n");
	//	buffer.append("Total time: "+DataToRules.TotalTime+" ms");
	//	buffer.append("\n");
	//	buffer.append("Total memory usage: "+DataToRules.TotalMemory+" mb\n");
		return buffer.toString(); // return the string
	}
	
	public String getRulesWithSelectedAttribute (String AttributeName){
		StringBuffer buffer = new StringBuffer(" -------Association Rules of Table ");
		buffer.append(tableName);
		buffer.append(" -------\n");
		buffer.append(" ------- k="+DataToRules.k+", minsup="+DataToRules.minsupp+", minconf="+DataToRules.minconf+" -------\n");
		int i = 1;
		Rules selectedRules = new Rules("Selected Rules");
		for(Rule rule : this.Rules.rules){
			boolean found = false;
			Itemset itemset1 = rule.getItemset1();
			for (int j = 0; j < itemset1.itemsetD.length; j++){
				if(itemset1.itemsetD[j].equals(AttributeName)){ //find rule including selected attribute, output this rule
					found = true;
					break;
				}
			}
			Itemset itemset2 = rule.getItemset2();
			for(int j = 0; j < itemset2.itemsetD.length; j++){
				if(itemset2.itemsetD[j].equals(AttributeName)){ //find rule including selected attribute, output this rule
					found = true;
					break;
				}
			}
			if(found){
				selectedRules.rules.add(rule);
				// append the rule, its support and confidence.
				buffer.append("   rule ");
				buffer.append(i);
				buffer.append(":  ");
				buffer.append(rule.toString());
	//			buffer.append("support :  ");
	//			buffer.append(rule.getRelativeSupport(this.tableSize));
	//			buffer.append(" (");
	//			buffer.append(rule.getAbsoluteSupport());
	//			buffer.append("/");
	//			buffer.append(this.tableSize);
	//			buffer.append(") ");
	//			buffer.append("confidence :  " );
	//			buffer.append(rule.getConfidence());
				buffer.append("\n");
				i++;
			}
		}
		List<Constraint> selectedConstraints = transform(selectedRules);
		buffer.append(" ------- Constraints ------- \n");
		int count = 1;
		for(Constraint constraint : selectedConstraints){
			buffer.append("constraint "+count+": ");
			buffer.append(constraint.toString());
			buffer.append("\n");
			count++;
		}
	//	buffer.append("==================================================================");
	//	buffer.append("\n");
	//	buffer.append("Total time: "+DataToRules.TotalTime+" ms");
	//	buffer.append("\n");
	//	buffer.append("Total memory usage: "+DataToRules.TotalMemory+" mb");
		return buffer.toString(); // return the string
	}
	
	/**
	 * 
	 * @param AttributeName
	 * @return constraints having attribute whose name is AttributeName
	 */
	public List<Constraint> getConstraintsWithSelectedAttribute (String AttributeName) {
		Rules selectedRules = new Rules("Selected Rules");
		for(Rule rule : this.Rules.rules){
			boolean found = false;
			Itemset itemset1 = rule.getItemset1();
			for (int j = 0; j < itemset1.itemsetD.length; j++){
				if(itemset1.itemsetD[j].equals(AttributeName)){ //find rule including selected attribute, output this rule
					found = true;
					break;
				}
			}
			Itemset itemset2 = rule.getItemset2();
			for(int j = 0; j < itemset2.itemsetD.length; j++){
				if(itemset2.itemsetD[j].equals(AttributeName)){ //find rule including selected attribute, output this rule
					found = true;
					break;
				}
			}
			if(found){
				selectedRules.rules.add(rule);		
			}
		}
		return transform(selectedRules);
	}
	
	/**
	 * 
	 * @param top
	 * @return top constraints
	 */
	public List<Constraint> getTopConstraints (int top) {
		if(top >= this.Rules.rules.size()){
			return this.constraints;
		}
		else if(top > 0 && top < this.Rules.rules.size()){
			Rules topRules = new Rules("Top Rules");
			for(int i = 0; i < top; i++){
				topRules.rules.add(this.Rules.rules.get(i));
			}
			return transform(topRules);
		}
		else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param conf
	 * @return constraints whose confidence is larger or equal to conf
	 */
	public List<Constraint> getConstraintsWithSelectedConf (double conf){
		Rules selectedRules = new Rules("Selected confidence Rules");
		for(Rule rule : this.Rules.rules){
			if(rule.getConfidence() >= conf){
				selectedRules.rules.add(rule);
			}
		}
		return transform(selectedRules);
	}
	
}
