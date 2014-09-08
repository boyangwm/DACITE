package com.patterns.itemset_array_integers_with_count;

import com.patterns.AbstractOrderedItemset;

/**
 * This class represents an itemset (a set of items) implemented as an array of integers with
 * a variable to store the support count of the itemset.
 * Reference spmf
 * @author Qi Hu
 *
 */
public class Itemset extends AbstractOrderedItemset {
	/** the array of items **/
	public int[] itemset; 
	public int[] itemsetA; /* the description of items, such as Attribute1*/
	public String[] itemsetD; /** the name of items, such as name of Attribute1 is Height**/
	public double[] itemsetMax;
	public double[] itemsetMin;
	public String[] itemsetString;
	/**  the support of this itemset */
	public int support = 0; 
	
	/**
	 * Get the items as array
	 * @return the items
	 */
	public int[] getItems() {
		return itemset;
	}
	public int[] getItemsA() {
		return itemsetA;
	}
	public String[] getItemsD(){
		return itemsetD;
	}
	public double[] getItemsMax(){
		return itemsetMax;
	}
	public double[] getItemsMin(){
		return itemsetMin;
	}
	public String[] getItemsString(){
		return itemsetString;
	}
	/**
	 * Constructor
	 */
	public Itemset(){
		itemset = new int[]{};
		itemsetA = new int[]{};
	}
	
	/**
	 * Constructor 
	 * @param item an item that should be added to the new itemset
	 */
	public Itemset(int item, int itemA, String itemD, double itemMin, double itemMax, String itemString){
		itemset = new int[]{item};
		itemsetA = new int[]{itemA};
		itemsetD = new String[]{itemD};
		itemsetMin = new double[]{itemMin};
		itemsetMax = new double[]{itemMax};
		itemsetString = new String[]{itemString};
	}

	/**
	 * Constructor 
	 * @param items an array of items that should be added to the new itemset
	 */
	public Itemset(int [] items, int [] itemsA, String [] itemsD, double [] itemsMin, double [] itemsMax, String [] itemsString){
		this.itemset = items;
		this.itemsetA = itemsA;
		this.itemsetD = itemsD;
		this.itemsetMin = itemsMin;
		this.itemsetMax = itemsMax;
		this.itemsetString = itemsString;
	}
	
	/**
	 * Get the support of this itemset
	 */
	public int getAbsoluteSupport(){
		return support;
	}
	
	/**
	 * Get the size of this itemset 
	 */
	public int size() {
		return itemset.length;
	}

	/**
	 * Get the item at a given position in this itemset
	 */
	public Integer get(int position) {
		return itemset[position];
	}
	public Integer getA(int position) {
		return itemsetA[position];
	}
	public String getD(int position){
		return itemsetD[position];
	}
	public Double getMax(int position){
		return itemsetMax[position];
	}
	public Double getMin(int position){
		return itemsetMin[position];
	}
	public String getString(int position){
		return itemsetString[position];
	}

	/**
	 * Set the support of this itemset
	 * @param support the support
	 */
	public void setAbsoluteSupport(Integer support) {
		this.support = support;
	}

	/**
	 * Increase the support of this itemset by 1
	 */
	public void increaseTransactionCount() {
		this.support++;
	}


	/**
	 * Make a copy of this itemset but exclude a given item
	 * @param itemsetToRemove the given item
	 * @return the copy
	 */
	public Itemset cloneItemSetMinusOneItem(Integer itemsetToRemove) {
		// create the new itemset
		int[] newItemset = new int[itemset.length -1];
		int[] newItemsetA = new int[itemset.length -1];
		String[] newItemsetD = new String[itemset.length -1];
		double[] newItemsetMin = new double[itemset.length -1];
		double[] newItemsetMax = new double[itemset.length -1];
		String[] newItemsetString = new String[itemset.length -1];
		int i=0;
		// for each item in this itemset
		for(int j =0; j < itemset.length; j++){
			// copy the item except if it is the item that should be excluded
			if(itemset[j] != itemsetToRemove){
				newItemset[i] = itemset[j];
				newItemsetA[i] = itemsetA[j];
				newItemsetD[i] = itemsetD[j];
				newItemsetMin[i]= itemsetMin[j];
				newItemsetMax[i] = itemsetMax[j];
				newItemsetString[i++] = itemsetString[j];
			}
		}
		return new Itemset(newItemset, newItemsetA, newItemsetD, newItemsetMin, newItemsetMax, newItemsetString); // return the copy
	}

	/**
	 * Make a copy of this itemset but exclude a set of items
	 * @param itemsetToNotKeep the set of items to be excluded
	 * @return the copy
	 */
	public Itemset cloneItemSetMinusAnItemset(Itemset itemsetToNotKeep) {
		int equalsize = 0;
		for(int j=0; j<itemset.length; j++){
			if(itemsetToNotKeep.contains(itemset[j], itemsetA[j]) == true)
				equalsize++;
		}
		// create a new itemset
		int[] newItemset = new int[itemset.length - equalsize];
		int[] newItemsetA = new int[itemset.length - equalsize];
		String[] newItemsetD = new String[itemset.length - equalsize];
		double[] newItemsetMin = new double[itemset.length - equalsize];
		double[] newItemsetMax = new double[itemset.length - equalsize];
		String[] newItemsetString = new String[itemset.length - equalsize];
		int i=0;
		// for each item of this itemset
		for(int j =0; j < itemset.length; j++){
			// copy the item except if it is not an item that should be excluded
			if(itemsetToNotKeep.contains(itemset[j], itemsetA[j]) == false){
				newItemset[i] = itemset[j];
				newItemsetA[i] = itemsetA[j];
				newItemsetD[i] = itemsetD[j];
				newItemsetMin[i] = itemsetMin[j];
				newItemsetMax[i] = itemsetMax[j];
				newItemsetString[i++] = itemsetString[j];
			}
		}
		return new Itemset(newItemset, newItemsetA, newItemsetD, newItemsetMin, newItemsetMax, newItemsetString); // return the copy
	}
	
	public String toString(){
		// use a string buffer for more efficiency
		StringBuffer r = new StringBuffer ();
		// for each item, append it to the stringbuffer
		for(int i=0; i< size(); i++){
			if(getMin(i).equals(getMax(i))){
				if(getString(i).equals("Not a String Value")){ // it is not a String Value
					r.append("("+getD(i)+"="+getMin(i)+")");
					r.append(' ');
				}
				else{
					r.append("("+getD(i)+"="+getString(i)+")");
					r.append(' ');
				}
			}
			else{
				//r.append("Attribute"+getA(i)+"="+getD(i)+",");
				r.append("("+getMin(i)+"<="+getD(i)+"<="+getMax(i)+")");
				r.append(' ');
			}
		}
		return r.toString(); // return the string
	}
	public boolean contains(Integer item, Integer itemA) {
		for (int i=0; i< size(); i++) {
			if (get(i).equals(item) && getA(i).equals(itemA)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method compare this itemset with another itemset to see if they are
	 * equal. The method assume that the two itemsets are lexically ordered.
	 * 
	 * @return true or false
	 */
	public boolean isEqualTo(Itemset itemset2) {
		// If they don't contain the same number of items, we return false
		if (this.size() != itemset2.size()) {
			return false;
		}
		boolean equal = true;
		boolean[] item = new boolean [itemset2.size()];
		for (int i = 0; i < itemset2.size(); i++){
			item[i] = false;
		}
		// We compare each item one by one from i to size - 1.
		for (int i = 0; i < itemset2.size(); i++) {
			for(int j = 0; j< this.size(); j++){
				if(itemset2.get(i).equals(this.get(j)) && itemset2.getA(i).equals(this.getA(j)))
					item[i] = true;
			}
		}
		for (int i = 0; i < item.length; i++){
			equal = equal && item[i];
		}
		// All the items are the same, we return true.
		return equal;
	}
}
