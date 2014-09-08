package com.algorithms.associationrules.agrawal94_association_rules;

import com.patterns.itemset_array_integers_with_count.Itemset;

/**
 * This class represent an association rule used by the Agrawal algorithm.
 * Reference spmf
 * @author Qi Hu
 * @see Rules
 * @see AlgoAgrawalFaster94
 */
public class Rule {
	private Itemset itemset1; // antecedent
	private Itemset itemset2; // consequent
	private int transactionCount; // relative support
	private double confidence; // confidence of the rule
	private double lift; // lift of the rule

	/**
	 * Constructor
	 * 
	 * @param itemset1
	 *            the antecedent of the rule (an itemset)
	 * @param itemset2
	 *            the consequent of the rule (an itemset)
	 * @param transactionCount
	 *            the absolute support of the rule (integer)
	 * @param confidence
	 *            the confidence of the rule
	 * @param lift   the lift of the rule
	 */
	public Rule(Itemset itemset1, Itemset itemset2,
			int transactionCount, double confidence, double lift) {
		this.itemset1 = itemset1;
		this.itemset2 = itemset2;
		this.transactionCount = transactionCount;
		this.confidence = confidence;
		this.lift = lift;
	}

	/**
	 * Get the relative support of the rule (percentage)
	 * 
	 * @param databaseSize
	 *            the number of transactions in the database where this rule was
	 *            found.
	 * @return the support (double)
	 */
	public double getRelativeSupport(int databaseSize) {
		return ((double) transactionCount) / ((double) databaseSize);
	}

	/**
	 * Get the absolute support of this rule (integer).
	 * 
	 * @return the absolute support.
	 */
	public int getAbsoluteSupport() {
		return transactionCount;
	}

	/**
	 * Get the confidence of this rule.
	 * 
	 * @return the confidence
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * Get the lift of this rule.
	 * 
	 * @return the lift.
	 */
	public double getLift() {
		return lift;
	}

	/**
	 * Print this rule to System.out.
	 */
	public void print() {
		System.out.println(toString());
	}

	/**
	 * Return a String representation of this rule
	 * 
	 * @return a String
	 */
	public String toString() {
		return itemset1.toString() + " ==> " + itemset2.toString();
	}

	/**
	 * Get the left itemset of this rule (antecedent).
	 * 
	 * @return an itemset.
	 */
	public Itemset getItemset1() {
		return itemset1;
	}

	/**
	 * Get the right itemset of this rule (consequent).
	 * 
	 * @return an itemset.
	 */
	public Itemset getItemset2() {
		return itemset2;
	}
}
