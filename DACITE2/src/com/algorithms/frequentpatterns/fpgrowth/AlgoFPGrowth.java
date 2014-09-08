package com.algorithms.frequentpatterns.fpgrowth;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.convert.Description;
import com.patterns.itemset_array_integers_with_count.Itemset;
import com.patterns.itemset_array_integers_with_count.Itemsets;
import com.tools.MemoryLogger;

/**
 * This is an implementation of the FPGROWTH algorithm.
 * Reference spmf
 * @author Qi Hu
 * @see FPtree
 * @see Itemset
 * @see Itemsets
 * @see Element
 */
public class AlgoFPGrowth {
	// for statistics
		private long startTimestamp; // start time of the latest execution
		private long endTime; // end time of the latest execution
		private int transactionCount = 0; // transaction count in the database
		private int itemsetCount; // number of freq. itemsets found
		private Map<Integer, String> mapDescription;
		private Map<Integer, Double> mapMax;
		private Map<Integer, Double> mapMin;
		private Map<Integer, String> mapStringValue;
		// parameter
		public int relativeMinsupp;// the relative minimum support
		
		BufferedWriter writer = null; // object to write the output file
		
		// The  patterns that are found 
		// (if the user want to keep them into memory)
		protected Itemsets patterns = null;
		
		
		private MemoryLogger memoryLogger = null;


		/**
		 * Constructor
		 */
		public AlgoFPGrowth() {
			
		}

		/**
		 * Method to run the FPGRowth algorithm.
		 * @param input the path to an input file containing a transaction database.
		 * @param output the output file path for saving the result (if null, the result 
		 *        will be returned by the method instead of being saved).
		 * @param minsupp the minimum support threshold.
		 * @return the result if no output file path is provided.
		 * @throws IOException exception if error reading or writing files
		 */
		public Itemsets runAlgorithm(String output, double minsupp, 
				List<ArrayList<String>> NewMatrix, List<Description> NewMatrixDescription) throws FileNotFoundException, IOException {
			// record start time
			startTimestamp = System.currentTimeMillis();
			// number of itemsets found
			itemsetCount =0;
			
			//initialize tool to record memory usage
			memoryLogger = new MemoryLogger();
			memoryLogger.checkMemory();
			
			// if the user want to keep the result into memory
			if(output == null){
				writer = null;
				patterns =  new Itemsets("FREQUENT ITEMSETS");
		    }else{ // if the user want to save the result to a file
				patterns = null;
				writer = new BufferedWriter(new FileWriter(output)); 
			}
			// read new attribute description after Kmeans algorithm
			mapDescription = new HashMap<Integer, String>();
			mapMax = new HashMap<Integer, Double>();
			mapMin = new HashMap<Integer, Double>();
			mapStringValue = new HashMap<Integer, String>();

			for(int num = 0; num < NewMatrixDescription.size(); num++){
				Description description = NewMatrixDescription.get(num);
				if(description.getStringValue().equals("Not a String Value")){
					int i = description.getAttributeNumber();
					double min = description.getMin();
					double max = description.getMax();
					String name = description.getAttributeName();
					mapDescription.put(i, name);
					mapMin.put(i, min);
					mapMax.put(i, max);
					mapStringValue.put(i, "Not a String Value");
				}
				else{
					int i = description.getAttributeNumber();
					String name = description.getAttributeName();
					String stringValue = description.getStringValue();
					mapDescription.put(i, name);
					mapMin.put(i, 0.0);
					mapMax.put(i, 0.0);
					mapStringValue.put(i, stringValue);
				}
			}
			NewMatrixDescription = null; // To release memory
			// (1) PREPROCESSING: Initial database scan to determine the frequency of each item
			// The frequency is stored in a map:
			//    key: item   value: support
			////final Map<Integer, Integer> mapSupport = new HashMap<Integer, Integer>();
			final Map<Element, Integer> mapSupport = new HashMap<Element, Integer>();
			scanDatabaseToDetermineFrequencyOfSingleItems(mapSupport, NewMatrix);
			
			// convert the minimum support as percentage to a
			// relative minimum support
			this.relativeMinsupp = (int) Math.ceil(minsupp * transactionCount);
			
			// (2) Scan the database again to build the initial FP-Tree
			// Before inserting a transaction in the FPTree, we sort the items
			// by descending order of support.  We ignore items that
			// do not have the minimum support.
			FPTree tree = new FPTree();
			
			for(int num = 0; num < NewMatrix.size(); num++){
				ArrayList<String> lineSplited = NewMatrix.get(num);
				List<Element> transaction = new ArrayList<Element>();
				// for each item in the transaction
				int columnN = 0;
				String symbol = "N/A";
				for(int j = 0; j < lineSplited.size(); j++){
					if(!lineSplited.get(j).equals(symbol)){
						Integer item = (int) Double.valueOf(lineSplited.get(j)).doubleValue();
						Element element = new Element();
						element.itemID = item;
						element.columnN = columnN;
						if(mapSupport.get(element) >= relativeMinsupp){
							transaction.add(element);
						}
					}
					columnN++;
				}
				// sort item in the transaction by descending order of support
				Collections.sort(transaction, new Comparator<Element>(){
					public int compare(Element node1, Element node2){
						// compare the frequency
						int compare = mapSupport.get(node2) - mapSupport.get(node1);
						// if the same frequency, we check the lexical ordering!
						if(compare == 0){ 
							return (node1.itemID - node2.itemID);
						}
						// otherwise, just use the frequency
						return compare;
					}
				});
				// add the sorted transaction to the fptree.
				tree.addTransaction(transaction);
			}
			NewMatrix = null; // release memory
			// We create the header table for the tree
			tree.createHeaderList(mapSupport);
			
			// (5) We start to mine the FP-Tree by calling the recursive method.
			// Initially, the prefix alpha is empty.
			int[] prefixAlpha = new int[0];
			int[] prefixAlphaA = new int[0];
			String[] prefixAlphaD = new String[0];
			double[] prefixAlphaMin = new double[0];
			double[] prefixAlphaMax = new double[0];
			String[] prefixAlphaString = new String[0];
			fpgrowth(tree, prefixAlpha, prefixAlphaA, prefixAlphaD, prefixAlphaMin, prefixAlphaMax, prefixAlphaString, transactionCount, mapSupport);
			
			// close the output file if the result was saved to a file
			if(writer != null){
				writer.close();
			}
			// record the execution end time
			endTime= System.currentTimeMillis();
			
			// check the memory usage
			memoryLogger.checkMemory();
			
			// return the result (if saved to memory)
			return patterns;
		}

		/**
		 * This method scans the input database to calculate the support of single items
		 * @param input the path of the input file
		 * @param mapSupport a map for storing the support of each item (key: item, value: support)
		 * @throws IOException  exception if error while writing the file
		 */
		private void scanDatabaseToDetermineFrequencyOfSingleItems(
				 final Map<Element, Integer> mapSupport, List<ArrayList<String>> NewMatrix){
			
			for(int num = 0; num < NewMatrix.size(); num++){
				ArrayList<String> lineSplited = NewMatrix.get(num);
				int columnN = 0;
				String symbol = "N/A";
				for(int j = 0; j < lineSplited.size(); j++){
					if(!lineSplited.get(j).equals(symbol)){
						Integer item = (int) Double.valueOf(lineSplited.get(j)).doubleValue();
						Element element = new Element();
						element.itemID = item;
						element.columnN = columnN;
						// increase the support count of the item
						Integer count = mapSupport.get(element);
						if(count == null){
							mapSupport.put(element, 1);
						}else{
							mapSupport.put(element, ++count);
						}
					}
					columnN++;
				}
				// increase the transaction count
				transactionCount++;
			}	
		}


		/**
		 * This method mines pattern from a Prefix-Tree recursively
		 * @param tree  The Prefix Tree
		 * @param prefix  The current prefix "alpha"
		 * @param mapSupport The frequency of each item in the prefix tree.
		 * @throws IOException  exception if error writing the output file
		 */
		private void fpgrowth(FPTree tree, int[] prefixAlpha, int[] prefixAlphaA, String[] prefixAlphaD, double[] prefixAlphaMin, double[] prefixAlphaMax, String[] prefixAlphaString, int prefixSupport, Map<Element, Integer> mapSupport) throws IOException {
			// We need to check if there is a single path in the prefix tree or not.
			if(tree.hasMoreThanOnePath == false){
				// That means that there is a single path, so we 
				// add all combinations of this path, concatenated with the prefix "alpha", to the set of patterns found.
				if(tree.root.childs.size() != 0)
				addAllCombinationsForPathAndPrefix(tree.root.childs.get(0), prefixAlpha, prefixAlphaA, prefixAlphaD, prefixAlphaMin, prefixAlphaMax, prefixAlphaString); // CORRECT?
				
			}else{ // There is more than one path
				fpgrowthMoreThanOnePath(tree, prefixAlpha, prefixAlphaA, prefixAlphaD, prefixAlphaMin, prefixAlphaMax, prefixAlphaString, prefixSupport, mapSupport);
			}
		}
		
		/**
		 * Mine an FP-Tree having more than one path.
		 * @param tree  the FP-tree
		 * @param prefix  the current prefix, named "alpha"
		 * @param mapSupport the frequency of items in the FP-Tree
		 * @throws IOException  exception if error writing the output file
		 */
		private void fpgrowthMoreThanOnePath(FPTree tree, int [] prefixAlpha, int [] prefixAlphaA, String [] prefixAlphaD, double[] prefixAlphaMin, double[] prefixAlphaMax, String[] prefixAlphaString, int prefixSupport, Map<Element, Integer> mapSupport) throws IOException {
			// For each frequent item in the header table list of the tree in reverse order.
			for(int i= tree.headerList.size()-1; i>=0; i--){
				// get the item
				Element item = tree.headerList.get(i);
				
				// get the support of the item
				int support = mapSupport.get(item);
				// if the item is not frequent, we skip it
				if(support <  relativeMinsupp){
					continue;
				}
				// Create Beta by concatening Alpha with the current item
				// and add it to the list of frequent patterns
				int [] beta = new int[prefixAlpha.length+1];
				int [] betaA = new int[prefixAlphaA.length+1];
				String [] betaD = new String[prefixAlphaD.length+1];
				double [] betaMin = new double[prefixAlphaMin.length+1];
				double [] betaMax = new double[prefixAlphaMax.length+1];
				String [] betaString = new String[prefixAlphaString.length+1];
				System.arraycopy(prefixAlpha, 0, beta, 0, prefixAlpha.length);
				System.arraycopy(prefixAlphaA, 0, betaA, 0, prefixAlphaA.length);
				System.arraycopy(prefixAlphaD, 0, betaD, 0, prefixAlphaD.length);
				System.arraycopy(prefixAlphaMin, 0, betaMin, 0, prefixAlphaMin.length);
				System.arraycopy(prefixAlphaMax, 0, betaMax, 0, prefixAlphaMax.length);
				System.arraycopy(prefixAlphaString, 0, betaString, 0, prefixAlphaString.length);
				beta[prefixAlpha.length] = item.getitemID();
				betaA[prefixAlphaA.length] = item.getcolumnN() + 1;
				betaD[prefixAlphaD.length] = mapDescription.get(betaA[prefixAlphaA.length]);
				betaMin[prefixAlphaMin.length] = mapMin.get(betaA[prefixAlphaA.length]);
				betaMax[prefixAlphaMax.length] = mapMax.get(betaA[prefixAlpha.length]);
				betaString[prefixAlphaString.length] = mapStringValue.get(betaA[prefixAlphaA.length]);
				
				// calculate the support of beta
				int betaSupport = (prefixSupport < support) ? prefixSupport: support;
				// save beta to the output file
				saveItemset(beta, betaA, betaD, betaMin, betaMax, betaString, betaSupport);
				
				// === Construct beta's conditional pattern base ===
				// It is a subdatabase which consists of the set of prefix paths
				// in the FP-tree co-occuring with the suffix pattern.
				List<List<FPNode>> prefixPaths = new ArrayList<List<FPNode>>();
				FPNode path = tree.mapItemNodes.get(item);
				while(path != null){
					// if the path is not just the root node
					if(path.parent.itemID != -1){
						// create the prefixpath
						List<FPNode> prefixPath = new ArrayList<FPNode>();
						// add this node.
						prefixPath.add(path);   // NOTE: we add it just to keep its support,
						// actually it should not be part of the prefixPath
						
						//Recursively add all the parents of this node.
						FPNode parent = path.parent;
						while(parent.itemID != -1){
							prefixPath.add(parent);
							parent = parent.parent;
						}
						// add the path to the list of prefixpaths
						prefixPaths.add(prefixPath);
					}
					// We will look for the next prefixpath
					path = path.nodeLink;
				}
				
				// (A) Calculate the frequency of each item in the prefixpath
				// The frequency is stored in a map such that:
				// key:  item   value: support
				Map<Element, Integer> mapSupportBeta = new HashMap<Element, Integer>();
				// for each prefixpath
				for(List<FPNode> prefixPath : prefixPaths){
					// the support of the prefixpath is the support of its first node.
					int pathCount = prefixPath.get(0).counter;  
					 // for each node in the prefixpath,
					// except the first one, we count the frequency
					for(int j=1; j<prefixPath.size(); j++){ 
						FPNode node = prefixPath.get(j);
						Element element = new Element();
						element.itemID = node.getitemID();
						element.columnN = node.getcolumnN();
						// if the first time we see that node id
						if(mapSupportBeta.get(element) == null){
							// just add the path count
							mapSupportBeta.put(element, pathCount);
						}else{
							// otherwise, make the sum with the value already stored
							mapSupportBeta.put(element, mapSupportBeta.get(element) + pathCount);
						}
					}
				}
				
				// (B) Construct beta's conditional FP-Tree
				// Create the tree.
				FPTree treeBeta = new FPTree();
				// Add each prefixpath in the FP-tree.
				for(List<FPNode> prefixPath : prefixPaths){
					treeBeta.addPrefixPath(prefixPath, mapSupportBeta, relativeMinsupp); 
				}  
				// Create the header list.
				treeBeta.createHeaderList(mapSupportBeta); 
				
				// Mine recursively the Beta tree if the root as child(s)
				if(treeBeta.root.childs.size() > 0){
					// recursive call
					fpgrowth(treeBeta, beta, betaA, betaD, betaMin, betaMax, betaString, betaSupport, mapSupportBeta);
				}
			}
			
		}

		/**
		 * This method is for adding recursively all combinations of nodes in a path, concatenated with a given prefix,
		 * to the set of patterns found.
		 * @param nodeLink the first node of the path
		 * @param prefix  the prefix
		 * @param minsupportForNode the support of this path.
		 * @throws IOException exception if error while writing the output file
		 */
		private void addAllCombinationsForPathAndPrefix(FPNode node, int[] prefix, int[] prefixA, String[] prefixD, double[] prefixMin, double[] prefixMax, String[] prefixString) throws IOException {
			// Concatenate the node item to the current prefix
			int [] itemset = new int[prefix.length+1];
			int [] itemsetA = new int[prefixA.length+1];
			String [] itemsetD = new String[prefixD.length+1];
			double [] itemsetMin = new double[prefixMin.length+1];
			double [] itemsetMax = new double[prefixMax.length+1];
			String [] itemsetString = new String[prefixString.length+1];
			System.arraycopy(prefix, 0, itemset, 0, prefix.length);
			System.arraycopy(prefixA, 0, itemsetA, 0, prefixA.length);
			System.arraycopy(prefixD, 0, itemsetD, 0, prefixD.length);
			System.arraycopy(prefixMin, 0, itemsetMin, 0, prefixMin.length);
			System.arraycopy(prefixMax, 0, itemsetMax, 0, prefixMax.length);
			System.arraycopy(prefixString, 0, itemsetString, 0, prefixString.length);
			itemset[prefix.length] = node.itemID;
			itemsetA[prefixA.length] = node.columnN + 1;
			itemsetD[prefixD.length] = mapDescription.get(itemsetA[prefixA.length]);
			itemsetMin[prefixMin.length] = mapMin.get(itemsetA[prefixA.length]);
			itemsetMax[prefixMax.length] = mapMax.get(itemsetA[prefixA.length]);
			itemsetString[prefixString.length] = mapStringValue.get(itemsetA[prefixA.length]);
			
			// save the resulting itemset to the file with its support
			saveItemset(itemset, itemsetA, itemsetD, itemsetMin, itemsetMax, itemsetString, node.counter);
			
			// recursive call if there is a node link
			if(node.childs.size() != 0) {
				addAllCombinationsForPathAndPrefix(node.childs.get(0), itemset, itemsetA, itemsetD, itemsetMin, itemsetMax, itemsetString);
				addAllCombinationsForPathAndPrefix(node.childs.get(0), prefix, prefixA, prefixD, prefixMin, prefixMax, prefixString);
			}
		}

		/**
		 * Write a frequent itemset that is found to the output file or
		 * keep into memory if the user prefer that the result be saved into memory.
		 */
		private void saveItemset(int [] itemset, int [] itemsetA, String [] itemsetD, double[] itemsetMin, double[] itemsetMax, String[] itemsetString, int support) throws IOException {
			// increase the number of itemsets found for statistics purpose
			itemsetCount++;
			
			// We sort the itemset before showing it to the user so that it is
			// in lexical order.
			//Arrays.sort(itemset);
			
			// if the result should be saved to a file
			if(writer != null){
				// Create a string buffer
				StringBuffer buffer = new StringBuffer();
				// write the items of the itemset
				for(int i=0; i< itemset.length; i++){
					buffer.append(itemset[i]);
					if(i != itemset.length-1){
						buffer.append(' ');
					}
				}
				// Then, write the support
				buffer.append(" #SUP: ");
				buffer.append(support);
				// write to file and create a new line
				writer.write(buffer.toString());
				writer.newLine();
			}// otherwise the result is kept into memory
			else{
				// create an object Itemset and add it to the set of patterns 
				// found.
				Itemset itemsetObj = new Itemset(itemset, itemsetA, itemsetD, itemsetMin, itemsetMax, itemsetString);
				itemsetObj.setAbsoluteSupport(support);
				patterns.addItemset(itemsetObj, itemsetObj.size());
			}
		}

		/**
		 * Print statistics about the algorithm execution to System.out.
		 */
		public void printStatus() {
			System.out
					.println("=============  FP-GROWTH - STATS =============");
			long temps = endTime - startTimestamp;
			System.out.println(" Transactions count from database : " + transactionCount);
			System.out.print(" Max memory usage: " + memoryLogger.getMaxMemory() + " mb \n");
			System.out.println(" Frequent itemsets count : " + itemsetCount); 
			System.out.println(" Total time ~ " + temps + " ms");
			System.out
					.println("===================================================");
		}

		/**
		 * Get the number of transactions in the last transaction database read.
		 * @return the number of transactions.
		 */
		public int getTableSize() {
			return transactionCount;
		}
}
