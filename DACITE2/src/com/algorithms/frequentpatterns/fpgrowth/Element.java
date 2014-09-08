package com.algorithms.frequentpatterns.fpgrowth;

/**
 * This saves information of each element's attribute number and value.
 * @author Qi Hu
 * @see AlgoFPGrowth
 */
public class Element {
	public int itemID = -1;
	public int columnN = -1;
	
	Element(){
		
	}
	
	/**
	 * @return value of an element
	 */
	public int getitemID(){
		return itemID;
	}
	
	/**
	 * @return attribute number of an element
	 */
	public int getcolumnN(){
		return columnN;
	}
	public int hashCode(){
		return itemID*100 + columnN;
	}
	public boolean equals(Object o){
		if((this.itemID == ((Element)o).getitemID()) && (this.columnN == ((Element)o).getcolumnN()))
			return true;
		return false;
	}
}
