package com.algorithms.frequentpatterns.fpgrowth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an implementation of a FPTree as used by the FPGrowth algorithm.
 * Reference spmf
 * @author Qi Hu
 * @see FPNode
 * @see Itemset
 * @see AlgoFPGrowth
 */
public class FPTree {
	// List of items in the header table
		List<Element> headerList = null;
		
		// List of pairs (item, frequency) of the header table
		Map<Element, FPNode> mapItemNodes = new HashMap<Element, FPNode>();
		
		// flag that indicate if the tree has more than one path
		boolean hasMoreThanOnePath = false;
		
		// root of the tree
		FPNode root = new FPNode(); // null node

		/**
		 * Constructor
		 */
		FPTree(){	
			
		}

		/**
		 * Method for adding a transaction to the fp-tree (for the initial construction
		 * of the FP-Tree).
		 * @param transaction
		 */
		public void addTransaction(List<Element> transaction) {
			FPNode currentNode = root;
			// For each item in the transaction
			for(int i = 0; i < transaction.size(); i++){
				// look if there is a node already in the FP-Tree
				Element item = transaction.get(i);
				FPNode child = currentNode.getChildWithID(item.getitemID(), item.getcolumnN());
				if(child == null){ 
					// there is no node, we create a new one
					FPNode newNode = new FPNode();
					newNode.itemID = item.getitemID();
					newNode.columnN = item.getcolumnN();
					newNode.parent = currentNode;
					// we link the new node to its parrent
					currentNode.childs.add(newNode);
					
					// check if more than one path
					if(!hasMoreThanOnePath && currentNode.childs.size() > 1) {
						hasMoreThanOnePath = true;
					}
					
					// we take this node as the current node for the next for loop iteration 
					currentNode = newNode;
					
					// We update the header table.
					// We check if there is already a node with this id in the header table
					FPNode headernode = mapItemNodes.get(item);
					if(headernode == null){  // there is not
						mapItemNodes.put(item, newNode);
					}else{ // there is
						// we find the last node with this id.
						while(headernode.nodeLink != null){
							headernode = headernode.nodeLink;
						}
						headernode.nodeLink  = newNode;
					}	
				}else{ 
					// there is a node already, we update it
					child.counter++;
					currentNode = child;
				}
			}
		}
		/**
		 * Method for adding a prefixpath to a fp-tree.
		 * @param prefixPath  The prefix path
		 * @param mapSupportBeta  The frequencies of items in the prefixpaths
		 * @param relativeMinsupp
		 */
		void addPrefixPath(List<FPNode> prefixPath, Map<Element, Integer> mapSupportBeta, int relativeMinsupp) {
			// the first element of the prefix path contains the path support
			int pathCount = prefixPath.get(0).counter;  
			
			FPNode currentNode = root;
			// For each item in the transaction  (in backward order)
			// (and we ignore the first element of the prefix path)
			for(int i= prefixPath.size()-1; i >=1; i--){ 
				FPNode pathItem = prefixPath.get(i);
				Element element = new Element();
				element.itemID = pathItem.getitemID();
				element.columnN = pathItem.getcolumnN();
				// if the item is not frequent we skip it
				if(mapSupportBeta.get(element) < relativeMinsupp){
					continue;
				}
				
				// look if there is a node already in the FP-Tree
				FPNode child = currentNode.getChildWithID(pathItem.itemID, pathItem.columnN);
				if(child == null){ 
					// there is no node, we create a new one
					FPNode newNode = new FPNode();
					newNode.itemID = pathItem.itemID;
					newNode.columnN = pathItem.columnN;
					newNode.parent = currentNode;
					newNode.counter = pathCount;  // set its support
					currentNode.childs.add(newNode);
					// check if more than one path
					if(!hasMoreThanOnePath && currentNode.childs.size() > 1) {
						hasMoreThanOnePath = true;
					}
					
					currentNode = newNode;
					// We update the header table.
					// We check if there is already a node with this id in the header table
					FPNode headernode = mapItemNodes.get(element);
					if(headernode == null){  // there is not
						mapItemNodes.put(element, newNode);
					}else{ // there is
						// we find the last node with this id.
						while(headernode.nodeLink != null){
							headernode = headernode.nodeLink;
						}
						headernode.nodeLink  = newNode;
					}	
				}else{ 
					// there is a node already, we update it
					child.counter += pathCount;
					currentNode = child;
				}
			}
		}

		/**
		 * Method for creating the list of items in the header table, 
		 *  in descending order of support.
		 * @param mapSupport the frequencies of each item (key: item  value: support)
		 */
		void createHeaderList(final Map<Element, Integer> mapSupport) {
			// create an array to store the header list with
			// all the items stored in the map received as parameter
			headerList =  new ArrayList<Element>(mapItemNodes.keySet());
			
			// sort the header table by decreasing order of support
			Collections.sort(headerList, new Comparator<Element>(){
				public int compare(Element node1, Element node2){
					// compare the support
					int compare = mapSupport.get(node2) - mapSupport.get(node1);
					// if the same frequency, we check the lexical ordering!
					if(compare ==0){ 
						return (node1.getitemID() - node2.getitemID());
					}
					// otherwise we use the support
					return compare;
				}
			});
		}
}
