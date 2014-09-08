package com.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import com.dialog.LoginDatabaseEmpty;
import com.dialog.LoginDatabaseError;
import com.dialog.SetParameterError;
import com.run_algorithms.DataToRules;

/**
 * Receive inputs as parameters of function ConnectAndRun.
 * @author Qi Hu
 *
 */
public class submitAction implements ActionListener{
	public void actionPerformed(ActionEvent arg0){
		DataToRules.url = DataToRules.url + DataToRules.inputWindow.portText.getText()+"/";
		DataToRules.dbName = DataToRules.inputWindow.databaseText.getText();
		DataToRules.userName = DataToRules.inputWindow.userText.getText();
		StringBuffer password = new StringBuffer();
		for(int i = 0; i< DataToRules.inputWindow.passwordText.getPassword().length; i++){
			password.append(DataToRules.inputWindow.passwordText.getPassword()[i]);
		}
		DataToRules.password = password.toString();
		String k = DataToRules.inputWindow.kText.getText();
		String minsup = DataToRules.inputWindow.supText.getText();
		String minconf = DataToRules.inputWindow.conText.getText();
		boolean emptyTable = true;


		//connect
		try{
			emptyTable = DataToRules.DatabaseRulesManager.ConnectDB(DataToRules.url, DataToRules.dbName, DataToRules.userName, 
					DataToRules.password, DataToRules.driver);
		}catch(Exception e){
			System.out.println("Not connected");
			DataToRules.url = "jdbc:mysql://";
			DataToRules.password = "";
			new LoginDatabaseError (DataToRules.inputWindow).setVisible(true);
			return;
		}

		//empty table
		if(emptyTable){
			System.out.println("Database empty");
			DataToRules.url = "jdbc:mysql://";
			DataToRules.password = "";
			new LoginDatabaseEmpty (DataToRules.inputWindow).setVisible(true);
			return;
		}


		//import configuration
		File file = new File("config.txt");
		String input = file.getAbsolutePath();
		try{
			DataToRules.DatabaseRulesManager.ImportConfig(input);
		} catch(Exception e){
			System.out.println("Cannot import configuration information.");
		}


		try{
			DataToRules.k = Integer.valueOf(k);
			DataToRules.minsupp = Double.valueOf(minsup);
			DataToRules.minconf = Double.valueOf(minconf);
			//check k, support, and confidence
			if(!((DataToRules.minsupp <= 1.0 && DataToRules.minsupp >= 0.0) && 
					(DataToRules.minconf <= 1.0 && DataToRules.minconf >= 0.0) 
					&& (DataToRules.k >=1 && DataToRules.k <= 20))){
				System.out.println("Database empty");
				DataToRules.url = "jdbc:mysql://";
				System.out.println("Database succesfully connects, but k, minsupp, minconf may be wrongly input!");
				new SetParameterError(DataToRules.inputWindow).setVisible(true);
				return;
			}

		}
		catch (Exception e){
			DataToRules.url = "jdbc:mysql://";
			DataToRules.password = "";
			e.printStackTrace();
			new SetParameterError(DataToRules.inputWindow).setVisible(true);
			return;
		};

		//DataToRules.DatabaseRulesManager.ConnectAndRun(DataToRules.url, DataToRules.dbName, DataToRules.userName, 
		//	DataToRules.password, DataToRules.driver, DataToRules.k, DataToRules.minsupp, DataToRules.minconf);
		try {
			DataToRules.DatabaseRulesManager.Run(DataToRules.k, DataToRules.minsupp, DataToRules.minconf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> TableNames = DataToRules.DatabaseRulesManager.GetAllTableNames();
		for(String tableName : TableNames){
			DataToRules.resultWindow.table.add(tableName); // add table name to pull-down list in resultWindow
		}


		DataToRules.inputWindow.dispose();
		DataToRules.resultWindow.setVisible(true);
		DataToRules.result.setVisible(true);
		DataToRules.DatabaseRulesManager.closeConnection();
		System.out.println("==================================================================");
		System.out.println("Total time: "+DataToRules.DatabaseRulesManager.getRunningTime()+" ms");
		System.out.println("Total memory usage: "+DataToRules.DatabaseRulesManager.getTotalMemory()+" mb");

	}
}
