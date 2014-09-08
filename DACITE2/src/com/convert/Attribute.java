package com.convert;

/**
 * This represents an attribute of new dataset.
 * @author Qi Hu
 *
 */
public class Attribute {
	public Double minValue = new Double(-1);
	public Double maxValue = new Double(-1);
	public int originalAttributeN = -1;
	public String name = "N/A";
	public String stringValue = "Not a String Value";
	
	public double getMax(){
		return this.maxValue;
	}
	
	public double getMin(){
		return this.minValue;
	}
	
	public int getOriginalAttributeN(){
		return this.originalAttributeN;
	}
	
	public String getName(){
		return this.name;
	}
	public String getStringValue(){
		return this.stringValue;
	}
}
