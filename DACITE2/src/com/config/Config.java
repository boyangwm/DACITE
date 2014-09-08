package com.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Save attributes which are skipped, because they are useless. Such as ID, name etc.
 * @author Qi Hu
 * @see com.rulemanager.DbRulesManager
 */
public class Config {
	public List<String> SkipAttributes;
	
	public Config() {
		SkipAttributes = new ArrayList<String>();
	}
	
	public boolean contains(String AttributeName) {
		if(SkipAttributes.contains(AttributeName))
			return true;
		else
			return false;
	}
	
	public void add(String AttributeName) {
		SkipAttributes.add(AttributeName);
	}
}
