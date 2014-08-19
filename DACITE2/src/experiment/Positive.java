package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class Positive {
	Set<String> array = new HashSet<String>();
	
	
	public Positive(){

	}
	
	
	
	public void add(String str){
		if(!array.contains(str)){
			array.add(str);
		}
	}
	
	
	public boolean contains(String str)
	{
		return array.contains(str);
	}
	
	
	public int size()
	{
		return array.size();
	}
	
	
	

}
