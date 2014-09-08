package com.convert;

/**
 * This class represents description of new attributes.
 * @author Qi Hu
 *
 */
public class Description {
	public String stringValue = "Not a String Value"; // if new attribute is string type, for example gender is female, stringValue = "female"
	public int AttNumber;
	public String AttName;
	public double minValue = -1;
	public double maxValue = -1;
	
	public String getStringValue() {
		return stringValue;
	}
	
	public int getAttributeNumber() {
		return AttNumber;
	}
	
	public String getAttributeName() {
		return AttName;
	}
	
	public double getMin() {
		return minValue;
	}
	
	public double getMax() {
		return maxValue;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if(stringValue.equals("Not a String Value")){
			buffer.append("Attribute ");
			buffer.append(AttNumber);
			buffer.append(" name is ");
			buffer.append(AttName);
			buffer.append(" interval from ");
			buffer.append(minValue);
			buffer.append(" to ");
			buffer.append(maxValue);
		}
		else{
			buffer.append("Attribute ");
			buffer.append(AttNumber);
			buffer.append(" name is ");
			buffer.append(AttName);
			buffer.append(" string value is ");
			buffer.append(stringValue);
		}
		return buffer.toString();
	}
}
