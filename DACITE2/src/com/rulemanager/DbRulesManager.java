package com.rulemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algorithms.associationrules.agrawal94_association_rules.AlgoAgrawalFaster94;
import com.algorithms.associationrules.agrawal94_association_rules.Rules;
import com.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import com.config.Config;
import com.convert.Description;
import com.convert.Kmeans;
import com.patterns.itemset_array_integers_with_count.Itemsets;
import com.tools.MemoryLogger;

/**
 * This represents the implementation of DbRulesManager.
 * @author Qi Hu
 * @see TableRulesManager
 */
public class DbRulesManager {
	public Map<String, TableRulesManager> manager;
	public String databaseName;
	public Map<String, Config> configuration;

	Connection conn = null;

	long runningTime = 0;
	double totalMemory = 0;


	/*
	 * Constructor
	 */
	public DbRulesManager () {
		this.manager = new HashMap<String, TableRulesManager>();
		this.databaseName = "N/A";
		this.configuration = new HashMap<String, Config>();
	}

	public String getName() {
		return this.databaseName;
	}


	public long getRunningTime(){
		return this.runningTime;
	}

	public double getTotalMemory(){
		return this.totalMemory;
	}


	public void closeConnection(){
		try {
			if(!this.conn.isClosed())
				this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param tableName
	 * @return TableRulesManager whose name is equal to tableName
	 */
	public TableRulesManager getTable(String tableName){
		if (manager.containsKey(tableName.toUpperCase())){
			return manager.get(tableName.toUpperCase());
		}
		else{
			System.out.println("the table " + tableName.toUpperCase() + 
					" does not exist. ");
			return null;
		}
	}

	/**
	 * Put trm into manager with name equal to tableName
	 * @param tableName
	 * @param trm
	 */
	public void putTable (String tableName, TableRulesManager trm) {
		manager.put(tableName, trm);
	}


	/**
	 * Run Kmeans, Fpgrowth and AgrawalFaster94 algorithms.
	 * Save results of association rules into TableRulesManager and 
	 * put TableRulesManager into manager of DbRulesManager.
	 * @param k
	 * @param minsupp
	 * @param minconf
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void Run(int k, double minsupp, double minconf) throws SQLException, NumberFormatException, IOException {
		// inputs of k, min supp and min conf are valid
		assert((minsupp <= 1.0 && minsupp >= 0.0) && (minconf <= 1.0 && minconf >= 0.0) && (k >=1 && k <= 20));

		long startTime = 0;
		long endTime = 0;
		MemoryLogger memoryLogger = new MemoryLogger(); //initialize tool to record memory usage
		memoryLogger.checkMemory();
		startTime = System.currentTimeMillis();
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet Rs = meta.getTables(null, null, null, new String[] {"TABLE"});

		boolean b = false;
		while (Rs.next()) // see how many tables are in the chosen database
		{

			String tablename = Rs.getString(3).toUpperCase(); // get table name from database
			System.out.println("Running "+tablename+" table");

			//debug
			//			try {
			//				Thread.sleep(20);
			//			} catch (InterruptedException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
			//			if(b == false){
			//				if(!tablename.equals("SITE_PARAMETERS_FINANCES"))
			//				{
			//					continue;
			//				}else
			//				{
			//					b = true;
			//					continue;
			//				}
			//			}
			//debug end

			//ignore large table size
			Statement st = conn.createStatement(); 
			ResultSet res = st.executeQuery("SELECT Count(*) FROM "+tablename);
			if(res.next()){
				int numOfRow = res.getInt(1);
				System.out.println("numOfRow : " + numOfRow);
				if(numOfRow > 100000)
				{
					TableRulesManager tableRulesManager = new TableRulesManager(tablename, new Rules(), numOfRow); // save association rules information of one table
					putTable(tablename, tableRulesManager); //save TableRulesManager into DbRulesManager
					continue;
					
				}
				//				 try {
				//					Thread.sleep(200);
				//				} catch (InterruptedException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}
			}

			Kmeans kmeans = new Kmeans();
			Config config = this.configuration.get(tablename);
			List<ArrayList<String>> NewMatrix;
			if(config != null){ //there is configuration information
				kmeans.runAlgorithm(conn, k, tablename, config);
				NewMatrix = kmeans.saveData(conn, tablename, config);
			}else{ //no configuration information
				kmeans.runAlgorithm(conn, k, tablename);
				NewMatrix = kmeans.saveData(conn, tablename);
			}
			List<Description> NewMatrixDescription = kmeans.saveDescription();
			if(NewMatrix.size() > 40){
				//Boyang - do analysis if the size is greater than 50
				/******************************************************************************************/
				// STEP 1: Applying the FP-GROWTH algorithm to find frequent itemsets
				AlgoFPGrowth fpgrowth = new AlgoFPGrowth();
				Itemsets patterns = fpgrowth.runAlgorithm(null, minsupp, NewMatrix, NewMatrixDescription);
				//					fpgrowth.printStatus();
				int tableSize = fpgrowth.getTableSize(); // it means the size of table on which is executed
				patterns.printItemsets(tableSize);

				// STEP 2: Generating all rules from the set of frequent itemsets (based on Agrawal & Srikant, 94)
				AlgoAgrawalFaster94 algoAgrawal = new AlgoAgrawalFaster94();
				// the next line run the algorithm.
				// Note: we pass null as output file path, because we don't want
				// to save the result to a file, but keep it into memory.
				Rules rules = algoAgrawal.runAlgorithm(patterns, null, tableSize, minconf);
				rules.printRules(tableSize);
				System.out.println("TABLE SIZE " + tableSize);
				TableRulesManager tableRulesManager = new TableRulesManager(tablename, rules, tableSize); // save association rules information of one table
				putTable(tablename, tableRulesManager); //save TableRulesManager into DbRulesManager
			}else{
				TableRulesManager tableRulesManager = new TableRulesManager(tablename, new Rules(), NewMatrix.size()); // save association rules information of one table
				putTable(tablename, tableRulesManager); //save TableRulesManager into DbRulesManager

			}
		}
		memoryLogger.checkMemory();
		totalMemory = memoryLogger.getMaxMemory();
		endTime= System.currentTimeMillis();
		this.runningTime = endTime - startTime;
	}



	/**
	 * returns all table names
	 * @return
	 */
	public ArrayList<String> GetAllTableNames () {
		ArrayList<String> TableNames = new ArrayList<String>();
		for ( String key : manager.keySet() ) {
			TableNames.add(key);
		}
		return TableNames;
	}


	/**
	 * Connect database
	 * @param url
	 * @param dbName
	 * @param userName
	 * @param password
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public boolean ConnectDB(String url, String dbName, String userName, String password, String driver) throws Exception{
		this.databaseName = dbName;
		DatabaseMetaData Meta;
		boolean tableEmpty = true;
		try{
			Class.forName(driver).newInstance(); 
			conn = DriverManager.getConnection(url+dbName, userName, password);
			Meta = conn.getMetaData();
			ResultSet Rs = Meta.getTables(null, null, null, new String[] {"TABLE"});
			while(Rs.next()){
				tableEmpty = false;
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		return tableEmpty;
	}

	/**
	 * Import configuration information from config.txt
	 * @param input
	 * @throws IOException
	 */
	public void ImportConfig(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		boolean found = false; // it means whether find this database configuration information in the config.txt
		String tableName = "";
		while(((line = reader.readLine())!= null)){ 
			String[] lineSplited = line.split("\t");			
			if(lineSplited[0].equals("//"))
				continue;
			if(!found){
				if(lineSplited[0].equals("*")){ //find database name information
					if(lineSplited[1].toUpperCase().equals(this.databaseName.toUpperCase())) // find this database configuration information
						found = true;
				}
			}
			else{
				if(lineSplited[0].equals("*")){ //finish importing configuration information of this database
					reader.close();
					return;
				}
				else if(lineSplited[0].equals("**")){ //find table name of this database
					tableName = lineSplited[1].toUpperCase();
				}
				else{ // find attribute names of "tableName" table of this database
					Config config = new Config();
					for(int i = 1; i < lineSplited.length; i++){
						config.add(lineSplited[i].toUpperCase());
					}
					this.configuration.put(tableName, config);
				}
			}
		}
		reader.close();
	}
}
