package com.run_algorithms;

import java.sql.SQLException;
import com.itemlistener.TableNameSelected;
import com.rulemanager.DbRulesManager;
import com.userinterface.*;
import com.actionlistener.*;

/**
 * Show inputWindow allowing to enter input parameters.
 * @author Qi Hu
 * @see com.rulemanager.DbRulesManager
 * @see com.userinterface.InputWindow
 */
public class DataToRules {
	public static String url;
	public static String dbName;
	public static String driver;
	public static String userName;
	public static String password;
	public static int k;
	public static double minsupp;
	public static double minconf;
	public static String tablename;
	
	public static InputWindow inputWindow;
	public static Result resultWindow;
	public static ResultShown result;
	
	public static DbRulesManager DatabaseRulesManager;
	
	public static long TotalTime; // total time used from beginning to the end
	public static double TotalMemory; // total memory used
	
	public DataToRules(){
		DataToRules.url = "jdbc:mysql://";
		DataToRules.dbName = "";
		DataToRules.driver = "com.mysql.jdbc.Driver";
		DataToRules.userName = "";
		DataToRules.password = "";
		DataToRules.k =0;
		DataToRules.minsupp = 1.0;
		DataToRules.minconf = 1.0;
		DataToRules.tablename = "";
		DataToRules.inputWindow = new InputWindow();
		DataToRules.resultWindow = new Result();
		DataToRules.result = new ResultShown();

		DataToRules.DatabaseRulesManager = new DbRulesManager();
		DataToRules.TotalTime = 0;
		DataToRules.TotalMemory = 0.0;
	}
	
	public static void main(String [] arg) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		final DataToRules test = new DataToRules();
		DataToRules.driver = "com.mysql.jdbc.Driver"; 
		DataToRules.inputWindow.exitButton.addActionListener(new exitAction());
		DataToRules.inputWindow.submitButton.addActionListener(new submitAction());
		DataToRules.resultWindow.table.addItemListener(new TableNameSelected());
		DataToRules.resultWindow.exitButton.addActionListener(new exitAction());
		DataToRules.resultWindow.searchButton.addActionListener(new searchAction());		
	}
	
}
