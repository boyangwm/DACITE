package com.convert;

/**
 * This represents original data from table of database. 
 * @author Qi Hu
 *
 */
public class OrigAtt {
	public Double value;
	public String stringValue;
	
	public OrigAtt(){
		value = -1.0;
		stringValue = "Not a String Value";
	}
	
	/**
	 * @return data value if not a string, such as age (40)
	 */
	public Double getValue() {
		return value;
	}
	
	/**
	 * @return string value if is a string, such as gender (male, female)
	 */
	public String getStringValue() {
		return stringValue;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof OrigAtt)){
			return false;
		}
		
		OrigAtt att = (OrigAtt) o;
		return stringValue.equals(att.stringValue) && value.equals(att.value);
	}
}
