package com.itemlistener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import com.rulemanager.TableRulesManager;
import com.run_algorithms.DataToRules;

/**
 * 
 * @author Qi Hu
 *
 */
public class TableNameSelected implements ItemListener{
	public void itemStateChanged(ItemEvent e){
		DataToRules.resultWindow.attribute.removeAll();
		String tableName = DataToRules.resultWindow.table.getSelectedItem();
		DataToRules.resultWindow.attribute.add("ALL");
		TableRulesManager tableRuleManager = DataToRules.DatabaseRulesManager.manager.get(tableName);
		tableRuleManager.getAttributeOfTable();
	    Iterator<String> itl = tableRuleManager.Attribute.keySet().iterator();
		while(itl.hasNext()){
			DataToRules.resultWindow.attribute.add(itl.next());
		}
	}
}

