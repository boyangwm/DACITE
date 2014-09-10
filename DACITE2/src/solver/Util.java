package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.constraint.Constraint;
import com.constraint.ConstraintBuilder;

import entry.ReportRecord;

public class Util {

	/**
	 * @param SCConstraints
	 * @param DBConstraints
	 * @param basedOnList2  true if DB is what we believe
	 */
	public static ArrayList<ReportRecord> findConfliction(ArrayList<Constraint> CList1, 
			ArrayList<Constraint> CList2,  boolean basedOnList2){

		//ArrayList<Constraint> conflictList = new ArrayList<Constraint>();
		ArrayList<ReportRecord> conflictRecord = new ArrayList<ReportRecord>();
		if(basedOnList2){
			for(Constraint cDB : CList2){
				for(Constraint cCS : CList1){
					boolean b = c1Againstc2(cCS, cDB);
					if(b){
						//						System.out.println("123 *****************");
						//						return true;

						String str = "SC rule: " + cCS.toString() +"\n";
						str += "DB rule: " + cDB.toString() + "\n";

						ReportRecord rr = new ReportRecord(cCS, str);
						conflictRecord.add(rr);
					}
				}
			}
		}else
		{
			assert(false);

			for(Constraint cCS : CList1){
				for(Constraint cDB : CList2){
					boolean b = c1Againstc2(cDB, cCS);
					if(b){}
					//return true;
					//conflictList.add(cDB);
				}
			}
		}
		return conflictRecord;
	}

	public static boolean c1Againstc2(Constraint c1, Constraint c2)
	{

		ConstraintBuilder c1L = c1.getLeft(); 
		ConstraintBuilder c1R = c1.getRight();

		ConstraintBuilder c2L = c2.getLeft(); 
		ConstraintBuilder c2R = c2.getRight();

		try{

			UniversalImplication imp1 = new UniversalImplication(c2L, c1L);
			UniversalImplication imp2 = new UniversalImplication(c2R, c1R);
			
			if(imp1.hasCommonVars() && imp2.hasCommonVars()){
				if(imp1.valid() && !imp2.valid()){
					//System.out.println("11**************");
					return true;
				}
			}
			
		}catch(RuntimeException ex){
			if(ex.getMessage().equals("## Error: Not LinearIntegerConstraint !!!!! "))
				return false;
		}



		

		return false;
	}

}
