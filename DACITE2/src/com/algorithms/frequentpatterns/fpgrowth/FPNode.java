package com.algorithms.frequentpatterns.fpgrowth;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of a FPTree node as used by the FPGrowth algorithm.
 * Reference spmf
 * @author Qi Hu
 * @see FPTree
 * @see Itemset
 * @see AlgoFPGrowth
 */
public class FPNode {
	int itemID = -1;  // item id
	int counter = 1;  // frequency counter  (a.k.a. support)
	int columnN;
	
	// the parent node of that node or null if it is the root
	FPNode parent = null; 
	// the child nodes of that node
	List<FPNode> childs = new ArrayList<FPNode>();
	
	FPNode nodeLink = null; // link to next node with the same item id (for the header table).
	
	/**
	 * constructor
	 */
	FPNode(){
		
	}

	int getitemID(){
		return itemID;
	}
	int getcolumnN(){
		return columnN;
	}
	/**
	 * Return the immediate child of this node having a given ID.
	 * If there is no such child, return null;
	 */
	FPNode getChildWithID(int id, int Aid) {
		// for each child node
		for(FPNode child : childs){
			// if the id is the one that we are looking for
			if(child.itemID == id && child.columnN == Aid){
				// return that node
				return child;
			}
		}
		// if not found, return null
		return null;
	}
}
