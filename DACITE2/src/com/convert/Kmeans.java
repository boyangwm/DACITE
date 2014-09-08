package com.convert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.config.Config;

/**
 * An implementation of the K-means algorithm.
 * 
 * The K-means  algorithm steps are (text from Wikipedia) : 1) Choose the number of clusters, k.
 * 2) Randomly generate k clusters and determine the cluster centers, or directly
 * generate k random points as cluster centers. 3) Assign each point to the
 * nearest cluster center. 4) Recompute the new cluster centers. 5) Repeat the two
 * previous steps until some convergence criterion is met (usually that the
 * assignment hasn't changed).
 * Convert original data from a table of database to new dataset.
 * @author Qi Hu
 * @see Attribute
 * @see Cluster
 * @see DoubleArray
 * @see OrigAtt
 */
public class Kmeans {
	    //abc
	
		// A random number generator because K-Means is a randomized algorithm
		private final static Random random = new Random(System.currentTimeMillis());
		
		// The list of original attribute (no repeat)
		private List<List<OrigAtt>> OriginalAttribute = new ArrayList<List<OrigAtt>>();
		
		// The list of generated attribute
		private List<List<Attribute>> NewAttribute = new ArrayList<List<Attribute>>();
		
		private Map<Integer, String> AttributeName = new HashMap<Integer, String>();
		
		private Map<Integer, Integer> NotSkip = new HashMap<Integer, Integer>();
		/**
		 * Default constructor
		 */
		public Kmeans() { 
			
		}
		
		/**
		 * run kmeans algorithms without configuration information
		 * @param conn
		 * @param k
		 * @param table
		 * @throws NumberFormatException
		 * @throws IOException
		 * @throws SQLException
		 */
		public void runAlgorithm (Connection conn, int k, String table)throws NumberFormatException, IOException, SQLException {
			Statement st = conn.createStatement(); 
			String Tname = table;
			ResultSet res = st.executeQuery("SELECT * FROM "+Tname); 
			ResultSetMetaData rsmd = res.getMetaData();
			int columns = rsmd.getColumnCount();
			for(int i = 1; i <= columns; i++){
				AttributeName.put(i, rsmd.getColumnName(i).toUpperCase());
			}
			int lineN = 0;
			while(res.next()){
				lineN++;
				if(lineN == 1){
					for(int i = 1; i <= columns; i++){
						List<OrigAtt> Att = new ArrayList<OrigAtt>();
						try{ // see whether the value is numeric
							OrigAtt item = new OrigAtt();
							item.value = res.getDouble(i);
							Att.add(item);
						} catch (Exception e){ // value is string
							OrigAtt item = new OrigAtt();
							item.stringValue = res.getString(i);
							Att.add(item);
						}
						OriginalAttribute.add(Att);
					}
				}
				else{
					for(int i = 1; i <= columns; i++){
						OrigAtt item = new OrigAtt();
						try{
							item.value = res.getDouble(i);
						} catch(Exception e){
							item.stringValue = res.getString(i);
						}
						if(!OriginalAttribute.get(i-1).contains(item)){
							OriginalAttribute.get(i-1).add(item);
						}
					}
				}
			}
			
			// choose to run Kmeans algorithm if different value number is large than 10
			for(int column= 0; column< OriginalAttribute.size(); column++){
				boolean isStringValue = false;
				for(int i=0; i< OriginalAttribute.get(column).size(); i++){
					if(!OriginalAttribute.get(column).get(i).stringValue.equals("Not a String Value")){
						isStringValue = true;
						break;
					}
				}
				// if it is String value data
				if(isStringValue){
					if(OriginalAttribute.get(column).size() <= 60){
						NotSkip.put(column+1, 1); // 1 means not skip
						List<Attribute> Att = new ArrayList<Attribute>();
						for(int i=0; i< OriginalAttribute.get(column).size(); i++){
							Attribute item = new Attribute();
							if(!OriginalAttribute.get(column).get(i).getStringValue().equals("Not a String Value")){
								item.stringValue = OriginalAttribute.get(column).get(i).getStringValue();
							}
							else{
								item.stringValue = "null";
							}
							item.originalAttributeN = column+1;
							item.name = AttributeName.get(column+1);
							Att.add(item);
						}
						NewAttribute.add(Att);
					}
					else{
						NotSkip.put(column+1, 0); // 0 means skip
					}
				}
				else if(OriginalAttribute.get(column).size() <= 10){
					NotSkip.put(column+1, 1); // 1 means not skip
					List<Attribute> Att = new ArrayList<Attribute>();
					for(int i=0; i< OriginalAttribute.get(column).size(); i++){
						Attribute item = new Attribute();
						try{ // see if it is String value or not
							item.maxValue = OriginalAttribute.get(column).get(i).getValue();
							item.minValue = OriginalAttribute.get(column).get(i).getValue();
						} catch (Exception e){ //it is String value
							item.stringValue = OriginalAttribute.get(column).get(i).getStringValue();
						}
						item.originalAttributeN = column+1;
						item.name = AttributeName.get(column+1);
						Att.add(item);
					}
					NewAttribute.add(Att);
				}
				
				else{ // run Kmeans algorithm
					NotSkip.put(column+1, 1); // 1 means not skip
					List<Cluster> clusters = null; //The list of clusters generated
					List<DoubleArray> vectors = new ArrayList<DoubleArray>(); // Structure to store the vectors from the file
					// variables to store the minimum and maximum values in vectors
					double minValue = Double.MAX_VALUE;
					double maxValue = 0;
					for(int i=0; i< OriginalAttribute.get(column).size(); i++){
						double [] vector = new double[1];
						vector[0] = OriginalAttribute.get(column).get(i).getValue();
						if(vector[0] < minValue)
							minValue = vector[0];
						if(vector[0] > maxValue)
							maxValue = vector[0];
						vectors.add(new DoubleArray(vector));
					}
					// Create a list of clusters
					clusters = new ArrayList<Cluster>();
					// Get the size of vectors
					int vectorsSize = vectors.get(0).data.length;
					
					// (1) Randomly generate k empty clusters with a random mean (cluster
					// center)
					for(int i=0; i< k; i++){
						DoubleArray meanVector = generateRandomVector(minValue, maxValue, vectorsSize);
						Cluster cluster = new Cluster(vectorsSize);
						cluster.setMean(meanVector);
						clusters.add(cluster);
					}
					
					// (2) Repeat the two next steps until the assignment hasn't changed
					boolean changed;
					while(true){
						changed = false;
						// (2.1) Assign each point to the nearest cluster center.

						// / for each vector
						for (DoubleArray vector : vectors) {
							// find the nearest cluster and the cluster containing the item
							Cluster nearestCluster = null;
							Cluster containingCluster = null;
							double distanceToNearestCluster = Double.MAX_VALUE;

							// for each cluster
							for (Cluster cluster : clusters) {
								// calculate the distance of the cluster mean to the vector
								double distance = euclideanDistance(cluster.getmean(), vector);
								// if it is the smallest distance until now, record this cluster
								// and the distance
								if (distance < distanceToNearestCluster) {
									nearestCluster = cluster;
									distanceToNearestCluster = distance;
								}
								// if the cluster contain the vector already,
								// remember that too!
								if (cluster.contains(vector)) {
									containingCluster = cluster;
								}
							}

							// if the nearest cluster is not the cluster containing
							// the vector
							if (containingCluster != nearestCluster) {
								// remove the vector from the containing cluster
								if (containingCluster != null) {
									containingCluster.remove(vector);
								}
								// add the vector to the nearest cluster
								nearestCluster.addVector(vector);
								changed = true;
							}
						}
						if(!changed){     // exit condition
							break;
						}
					
						// (2.2) Recompute the new cluster means
						for (Cluster cluster : clusters) {
							cluster.recomputeClusterMean();
						}
					}
					
					List<Attribute> Att = new ArrayList<Attribute>();
					for(int i=0; i< clusters.size(); i++){
						double Max = -1; 
						double Min = Double.MAX_VALUE;
						List<DoubleArray> Vectors = clusters.get(i).getVectors();
						for(int j=0; j< Vectors.size(); j++){
							if(Vectors.get(j).data[0] < Min)
								Min = Vectors.get(j).data[0];
							if(Vectors.get(j).data[0] > Max)
								Max = Vectors.get(j).data[0];
						}
						if(Min <= Max){
							Attribute item = new Attribute();
							item.maxValue = Max;
							item.minValue = Min;
							item.originalAttributeN = column+1;
							item.name = AttributeName.get(column+1);
							Att.add(item);
						}
					}
					NewAttribute.add(Att);
				}
			}
			
		}
		
		/**
		 * run kmeans algorithm when there is configuration information
		 * @param conn
		 * @param k
		 * @param table
		 * @param config
		 * @throws NumberFormatException
		 * @throws IOException
		 * @throws SQLException
		 */
		public void runAlgorithm (Connection conn, int k, String table, Config config)throws NumberFormatException, IOException, SQLException {
			Statement st = conn.createStatement(); 
			String Tname = table;
			ResultSet res = st.executeQuery("SELECT * FROM "+Tname); 
			ResultSetMetaData rsmd = res.getMetaData();
			int columns = rsmd.getColumnCount();
			List<Integer> ConfigAttributeIndex = new ArrayList<Integer>();
			int index = 1;
			for(int i = 1; i <= columns; i++){
				if(!config.contains(rsmd.getColumnName(i).toUpperCase())){ //attributes which are not in configuration
					AttributeName.put(index, rsmd.getColumnName(i).toUpperCase());
					index++;
				}
				else{ //attributes in configuration
					ConfigAttributeIndex.add(i);
				}
			}
			int lineN = 0;
			while(res.next()){
				lineN++;
				if(lineN == 1){
					for(int i = 1; i <= columns; i++){
						if(!ConfigAttributeIndex.contains(i)){ //attributes which are not in configuration
							List<OrigAtt> Att = new ArrayList<OrigAtt>();
							try{ // see whether the value is numeric
								OrigAtt item = new OrigAtt();
								item.value = res.getDouble(i);
								Att.add(item);
							} catch (Exception e){ // value is string
								OrigAtt item = new OrigAtt();
								item.stringValue = res.getString(i);
								Att.add(item);
							}
							OriginalAttribute.add(Att);
						}
					}
				}
				else{
					index = 1;
					for(int i = 1; i <= columns; i++){
						if(!ConfigAttributeIndex.contains(i)){ //attributes which are not in configuration
							OrigAtt item = new OrigAtt();
							try{
								item.value = res.getDouble(i);
							} catch(Exception e){
								item.stringValue = res.getString(i);
							}
							if(!OriginalAttribute.get(index-1).contains(item)){
								OriginalAttribute.get(index-1).add(item);
							}
							index++;
						}
					}
				}
			}
			
			// choose to run Kmeans algorithm if different value number is large than 10
			for(int column= 0; column< OriginalAttribute.size(); column++){
				boolean isStringValue = false;
				for(int i=0; i< OriginalAttribute.get(column).size(); i++){
					if(!OriginalAttribute.get(column).get(i).stringValue.equals("Not a String Value")){
						isStringValue = true;
						break;
					}
				}
				// if it is String value data
				if(isStringValue){
					if(OriginalAttribute.get(column).size() <= 60){
						NotSkip.put(column+1, 1); // 1 means not skip
						List<Attribute> Att = new ArrayList<Attribute>();
						for(int i=0; i< OriginalAttribute.get(column).size(); i++){
							Attribute item = new Attribute();
							if(!OriginalAttribute.get(column).get(i).getStringValue().equals("Not a String Value")){
								item.stringValue = OriginalAttribute.get(column).get(i).getStringValue();
							}
							else{
								item.stringValue = "null";
							}
							item.originalAttributeN = column+1;
							item.name = AttributeName.get(column+1);
							Att.add(item);
						}
						NewAttribute.add(Att);
					}
					else{
						NotSkip.put(column+1, 0); // 0 means skip
					}
				}
				//else if(OriginalAttribute.get(column).size() <= 10){
				//boyang test
				else if(OriginalAttribute.get(column).size() <= 5){
					NotSkip.put(column+1, 1); // 1 means not skip
					List<Attribute> Att = new ArrayList<Attribute>();
					for(int i=0; i< OriginalAttribute.get(column).size(); i++){
						Attribute item = new Attribute();
						try{ // see if it is String value or not
							item.maxValue = OriginalAttribute.get(column).get(i).getValue();
							item.minValue = OriginalAttribute.get(column).get(i).getValue();
						} catch (Exception e){ //it is String value
							item.stringValue = OriginalAttribute.get(column).get(i).getStringValue();
						}
						item.originalAttributeN = column+1;
						item.name = AttributeName.get(column+1);
						Att.add(item);
					}
					NewAttribute.add(Att);
				}
				
				else{ // run Kmeans algorithm
					NotSkip.put(column+1, 1); // 1 means not skip
					List<Cluster> clusters = null; //The list of clusters generated
					List<DoubleArray> vectors = new ArrayList<DoubleArray>(); // Structure to store the vectors from the file
					// variables to store the minimum and maximum values in vectors
					double minValue = Double.MAX_VALUE;
					double maxValue = 0;
					for(int i=0; i< OriginalAttribute.get(column).size(); i++){
						double [] vector = new double[1];
						vector[0] = OriginalAttribute.get(column).get(i).getValue();
						if(vector[0] < minValue)
							minValue = vector[0];
						if(vector[0] > maxValue)
							maxValue = vector[0];
						vectors.add(new DoubleArray(vector));
					}
					// Create a list of clusters
					clusters = new ArrayList<Cluster>();
					// Get the size of vectors
					int vectorsSize = vectors.get(0).data.length;
					
					// (1) Randomly generate k empty clusters with a random mean (cluster
					// center)
					for(int i=0; i< k; i++){
						DoubleArray meanVector = generateRandomVector(minValue, maxValue, vectorsSize);
						Cluster cluster = new Cluster(vectorsSize);
						cluster.setMean(meanVector);
						clusters.add(cluster);
					}
					
					// (2) Repeat the two next steps until the assignment hasn't changed
					boolean changed;
					while(true){
						changed = false;
						// (2.1) Assign each point to the nearest cluster center.

						// / for each vector
						for (DoubleArray vector : vectors) {
							// find the nearest cluster and the cluster containing the item
							Cluster nearestCluster = null;
							Cluster containingCluster = null;
							double distanceToNearestCluster = Double.MAX_VALUE;

							// for each cluster
							for (Cluster cluster : clusters) {
								// calculate the distance of the cluster mean to the vector
								double distance = euclideanDistance(cluster.getmean(), vector);
								// if it is the smallest distance until now, record this cluster
								// and the distance
								if (distance < distanceToNearestCluster) {
									nearestCluster = cluster;
									distanceToNearestCluster = distance;
								}
								// if the cluster contain the vector already,
								// remember that too!
								if (cluster.contains(vector)) {
									containingCluster = cluster;
								}
							}

							// if the nearest cluster is not the cluster containing
							// the vector
							if (containingCluster != nearestCluster) {
								// remove the vector from the containing cluster
								if (containingCluster != null) {
									containingCluster.remove(vector);
								}
								// add the vector to the nearest cluster
								nearestCluster.addVector(vector);
								changed = true;
							}
						}
						if(!changed){     // exit condition
							break;
						}
					
						// (2.2) Recompute the new cluster means
						for (Cluster cluster : clusters) {
							cluster.recomputeClusterMean();
						}
					}
			
					List<Attribute> Att = new ArrayList<Attribute>();
					for(int i=0; i< clusters.size(); i++){
						double Max = -1; 
						double Min = Double.MAX_VALUE;
						List<DoubleArray> Vectors = clusters.get(i).getVectors();
						for(int j=0; j< Vectors.size(); j++){
							if(Vectors.get(j).data[0] < Min)
								Min = Vectors.get(j).data[0];
							if(Vectors.get(j).data[0] > Max)
								Max = Vectors.get(j).data[0];
						}
						if(Min <= Max){
							Attribute item = new Attribute();
							item.maxValue = Max;
							item.minValue = Min;
							item.originalAttributeN = column+1;
							item.name = AttributeName.get(column+1);
							Att.add(item);
						}
					}
					NewAttribute.add(Att);
				}
			}
			
		}
		
		
		/**
		 * Generate a random vector.
		 * @param minValue  the minimum value allowed
		 * @param maxValue  the maximum value allowed
		 * @param vectorsSize the desired vector size
		 * @return the random vector
		 */
		private DoubleArray generateRandomVector(double minValue, double maxValue,
				int vectorsSize) {
			// create a new vector
			double[] vector = new double[vectorsSize];
			// for each position generate a random number
			for(int i=0; i < vectorsSize; i++){
				vector[i] = (random.nextDouble() * (maxValue - minValue)) + minValue;
			}
			// return the vector
			return new DoubleArray(vector);
		}
		
		/**
		 * Calculate the eucledian distance between two vectors of doubles.
		 * @param vector1 the first vector
		 * @param vector2 the second vector
		 * @return the distance
		 */
		private double euclideanDistance(DoubleArray vector1, DoubleArray vector2) {
			double sum =0;	
			for(int i=0; i< vector1.data.length; i++){
				sum += Math.pow(vector1.data[i] - vector2.data[i], 2);
			}
			return Math.sqrt(sum);
		}
		
		/**
		 * Save the corresponding values of original attributes' values to New Attributes into List data structure without configuration information
		 * @param conn
		 * @param table
		 * @return
		 * @throws SQLException
		 */
		public List<ArrayList<String>> saveData(Connection conn, String table) throws SQLException {
			List<ArrayList<String>> NewMatrix = new ArrayList<ArrayList<String>> ();
			Statement st = conn.createStatement(); 
			String Tname = table;
			ResultSet res = st.executeQuery("SELECT * FROM "+Tname); //table to be execute
			ResultSetMetaData rsmd = res.getMetaData();
			int columns = rsmd.getColumnCount();//get number of column in the table
			while(res.next()){
				ArrayList<String> NewLine = new ArrayList<String>();
				int IndexOfNewAttribute = 0;
				//for those categorical attributes whose number of values is less than 60.
				//In other words, those categorical attributes that are not skipped. 
				//And all the quantitative attributes.
				for(int i = 1; i <= columns; i++){
					if(NewAttribute.size() >= 1 && NotSkip.get(i) == 1){
						IndexOfNewAttribute++;
						try{
							double value = res.getDouble(i);
							//if it is quantitative attribute.
							//But there may be some errors since missing value in categorical attributes can also be read.
							//So we use stringValue to make a distinction between real quantitative attributes and not real quantitative attributes.
							for(int j=0; j< NewAttribute.get(IndexOfNewAttribute-1).size(); j++){
								Attribute item = NewAttribute.get(IndexOfNewAttribute-1).get(j);
								if(item.stringValue.equals("Not a String Value")){
									//for those data are real quantitative attributes.
									if(value <= item.maxValue && value >= item.minValue){
										NewLine.add("1.0");
										//for original data is quantitative attribute
										//if it is in the range of new attribute, adds 1.0.
									}
									else{
										NewLine.add("N/A");
										//for original data is quantitative attribute
										//if it is not in the range of new attribute, adds N/A.
									}
								}
								else{ //for exception that there is null in database, but when reading from database null changes to 0
									  //which means this data are not real quantitative attributes.
									if(item.stringValue.equals("null")){
										NewLine.add("1.0");
										//it means this is a categorical attribute
										//If there is an attribute equals to null, then mark it as "1.0".
									}
									else{
										NewLine.add("N/A");
										//it means this is a categorical attribute. 
										//If no attribute equals to null, then mark it as "N/A".
									}
								}
							}
						} catch (Exception e){
							//if it is categorical attribute.
							String value = res.getString(i);
							for(int j=0; j< NewAttribute.get(IndexOfNewAttribute-1).size(); j++){
								Attribute item = NewAttribute.get(IndexOfNewAttribute-1).get(j);
								if(value.equals(item.stringValue)){
									NewLine.add("1.0");
									//if original data is categorical attribute
									//and if it is equal to a new attribute, add 1.0.
								}
								else{
									NewLine.add("N/A");
									//if original data is categorical attribute
									//and is not equal to any new attribute, add N/A.
								}
							}
						}
					}
				}
				NewMatrix.add(NewLine);
			}
			return NewMatrix;
		}
		
		/**
		 * Save the corresponding values of original attributes' values to New Attributes into List data structure with configuration information
		 * @param conn
		 * @param table
		 * @return
		 * @throws SQLException
		 */
		public List<ArrayList<String>> saveData(Connection conn, String table, Config config) throws SQLException {
			List<ArrayList<String>> NewMatrix = new ArrayList<ArrayList<String>> ();
			Statement st = conn.createStatement(); 
			String Tname = table;
			ResultSet res = st.executeQuery("SELECT * FROM "+Tname); //table to be execute
			ResultSetMetaData rsmd = res.getMetaData();
			int columns = rsmd.getColumnCount();//get number of column in the table
			List<Integer> ConfigAttributeIndex = new ArrayList<Integer>();
			for(int i = 1; i <= columns; i++){
				if(config.contains(rsmd.getColumnName(i).toUpperCase())){ //attributes in configuration
					ConfigAttributeIndex.add(i);
				}
			}
			while(res.next()){
				ArrayList<String> NewLine = new ArrayList<String>();
				int IndexOfNewAttribute = 0;
				//for those categorical attributes whose number of values is less than 60.
				//In other words, those categorical attributes that are not skipped. 
				//And all the quantitative attributes.
				int index = 1;
				for(int i = 1; i <= columns; i++){
					if(!ConfigAttributeIndex.contains(i)){ //attributes which are not in configuration
						if(NewAttribute.size() >= 1 && NotSkip.get(index) == 1){
							IndexOfNewAttribute++;
							try{
								double value = res.getDouble(i);
								//if it is quantitative attribute.
								//But there may be some errors since missing value in categorical attributes can also be read.
								//So we use stringValue to make a distinction between real quantitative attributes and not real quantitative attributes.
								for(int j=0; j< NewAttribute.get(IndexOfNewAttribute-1).size(); j++){
									Attribute item = NewAttribute.get(IndexOfNewAttribute-1).get(j);
									if(item.stringValue.equals("Not a String Value")){
										//for those data are real quantitative attributes.
										if(value <= item.maxValue && value >= item.minValue){
											NewLine.add("1.0");
											//for original data is quantitative attribute
											//if it is in the range of new attribute, adds 1.0.
										}
										else{
											NewLine.add("N/A");
											//for original data is quantitative attribute
											//if it is not in the range of new attribute, adds N/A.
										}
									}
									else{ //for exception that there is null in database, but when reading from database null changes to 0
										//which means this data are not real quantitative attributes.
										if(item.stringValue.equals("null")){
											NewLine.add("1.0");
											//it means this is a categorical attribute
											//If there is an attribute equals to null, then mark it as "1.0".
										}
										else{
											NewLine.add("N/A");
											//it means this is a categorical attribute. 
											//If no attribute equals to null, then mark it as "N/A".
										}
									}
								}
							} catch (Exception e){
								//if it is categorical attribute.
								String value = res.getString(i);
								for(int j=0; j< NewAttribute.get(IndexOfNewAttribute-1).size(); j++){
									Attribute item = NewAttribute.get(IndexOfNewAttribute-1).get(j);
									if(value.equals(item.stringValue)){
										NewLine.add("1.0");
										//if original data is categorical attribute
										//and if it is equal to a new attribute, add 1.0.
									}
									else{
										NewLine.add("N/A");
										//if original data is categorical attribute
										//and is not equal to any new attribute, add N/A.
									}
								}
							}
						}
						index++;
					}
				}
				NewMatrix.add(NewLine);
			}
			return NewMatrix;
		}
		
		/**
		 * Save description of new attributes into List data structure
		 * @return
		 */
		public List<Description> saveDescription(){
			List<Description> NewMatrixDescription = new ArrayList<Description>();
			int AttN = 1;
			for(int i=0; i< NewAttribute.size(); i++){
				for(int j=0; j< NewAttribute.get(i).size(); j++){
					Description description = new Description();
					if(NewAttribute.get(i).get(j).stringValue.equals("Not a String Value")){
						description.stringValue = "Not a String Value";
						description.AttNumber = AttN;
						description.AttName = NewAttribute.get(i).get(j).name;
						description.minValue = NewAttribute.get(i).get(j).minValue.doubleValue();
						description.maxValue = NewAttribute.get(i).get(j).maxValue.doubleValue();
					}
					else{
						description.stringValue = NewAttribute.get(i).get(j).stringValue;
						description.AttNumber = AttN;
						description.AttName = NewAttribute.get(i).get(j).name;
					}
					AttN++;
					NewMatrixDescription.add(description);
				}
			}
			return NewMatrixDescription;
		}
}
