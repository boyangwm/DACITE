package com.convert;

import java.util.ArrayList;
import java.util.List;

/**
* This class represents a cluster found by a clustering algorithm such as K-Means.
* A cluster is a list of vectors of doubles.
*  @author Qi Hu
*  @see DoubleArray
 */
public class Cluster {
	private List<DoubleArray> vectors; // the vectors contained in this cluster
	private DoubleArray mean;  // the mean of the vectors in this cluster
	
	private DoubleArray sum; // the sum of all vectors in this clusters 
	// (used to calculate the mean efficiently)
	
	/**
	 * Constructor
	 * @param vectorsSize the size of the vectors to be stored in this cluster
	 */
	public Cluster(int vectorsSize){
		this.vectors = new ArrayList<DoubleArray>();
		sum = new DoubleArray(new double[vectorsSize]);
	}
	
	/**
	 * Setter for the mean of this cluster.
	 * @param mean A vector of double that will be set as the mean of this cluster.
	 */
	public void setMean(DoubleArray mean){
		this.mean = mean;
	}
	
	/**
	 * Add a vector of doubles to this cluster.
	 * @param vector The vector of doubles to be added.
	 */
	public void addVector(DoubleArray vector) {
		vectors.add(vector);
		for(int i=0; i < vector.data.length; i++){
			sum.data[i] += vector.data[i];
		}
	}

	/**
	 * Getter for the mean
	 * @return return the mean of this cluster
	 */
	public DoubleArray getmean() {
		return mean;
	}
	
	/**
	 * Return a string representing this cluster.
	 */
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		if(vectors.size() >=1){
			for(DoubleArray vector : vectors){
				buffer.append("[");
				buffer.append(vector.toString());
				buffer.append("]");
			}
		}
		return buffer.toString();
	}
	

	/**
	 * This method is called by clustering algorithms to recompute the mean
	 * of the cluster.
	 */
	public void recomputeClusterMean() {
		for(int i=0; i < sum.data.length; i++){
			mean.data[i] = sum.data[i] / vectors.size();
		}
	}

	/**
	 * Method to get the vectors in this cluster
	 * @return the vectors.
	 */
	public List<DoubleArray> getVectors(){
		return vectors;
	}
	
	/**
	 * Method to remove a vector from this cluster and update
	 * the internal sum of vectors at the same time.
	 * @param vector  the vector to be removed
	 */
	public void remove(DoubleArray vector) {
		vectors.remove(vector);
		// remove from sum
		for(int i=0; i < vector.data.length; i++){
			sum.data[i] -= vector.data[i];
		}
		
	}
	
	/**
	 * Method to remove a vector from this cluster without updating internal
	 * structures.
	 * @param vector  the vector to be removed
	 */
	public void removeVector(DoubleArray vector) {
		vectors.remove(vector);
	}
	
	/**
	 * Check if a vector is contained in this cluster.
	 * @param vector A vector of doubles
	 * @return true if the vector is contained in this cluster.
	 */
	public boolean contains(DoubleArray vector) {
		return vectors.contains(vector);
	}
}
