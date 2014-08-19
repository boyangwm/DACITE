package entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.constraint.Constraint;
import com.constraint.ConstraintBuilder;

public class ReportRecord {
	Constraint c;
	String rules;
	
	public ReportRecord(Constraint c, String rules){
		this.c = c;
		this.rules = rules;
		
	}
	
	
	public Constraint getConstraint()
	{
		return c;
	}
	
	public String getRules()
	{
		return rules;
	}
	
	

}
