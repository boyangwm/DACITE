package com.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.run_algorithms.DataToRules;

/**
 * Extract results from TableRulesManager, and display them on ResultShown interface.
 * @author Qi Hu
 *
 */
public class searchAction implements ActionListener{
	public void actionPerformed(ActionEvent arg0){
		String tableName = DataToRules.resultWindow.table.getSelectedItem();
		String AttributeName = DataToRules.resultWindow.attribute.getSelectedItem();
		if(AttributeName.equals("ALL")){
			String AssociationRules = DataToRules.DatabaseRulesManager.manager.get(tableName).toString();
			DataToRules.result.resultText.setText(AssociationRules);
		}
		else{
			String AssociationRules = DataToRules.DatabaseRulesManager.manager.get(tableName).getRulesWithSelectedAttribute(AttributeName);
			DataToRules.result.resultText.setText(AssociationRules);
		}
	}
}
