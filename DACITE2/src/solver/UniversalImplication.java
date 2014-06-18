package solver;

import static choco.Choco.gt;
import static choco.Choco.lt;
import static choco.Choco.implies;
import static choco.Choco.makeIntVar;
import static choco.Choco.not;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

import com.constraint.ConstraintBuilder;

import expression.CBParser;

public class UniversalImplication {
	
	ConstraintBuilder left;
	ConstraintBuilder right;
	
	public UniversalImplication(){}
	
	public UniversalImplication(ConstraintBuilder left, ConstraintBuilder right){
		this.left = left;
		this.right = right;
		
		
		Object leftO = CBParser.parse(left);
		assert(leftO instanceof Constraint);
		Constraint leftC = (Constraint) leftO;
		
		System.out.println("implication left : " + leftC.pretty());
		
		
		System.out.println("symStringToVar size : " + CBParser.symStringToVar.get("PERSON.AGE"));
		
		
		
		Model m = new CPModel();
		Solver s = new CPSolver();

		Object pAge = CBParser.symStringToVar.get("PERSON.AGE");
		
		assert(pAge instanceof IntegerVariable);
		IntegerVariable iage = (IntegerVariable) pAge;
		//IntegerVariable age2 = makeIntVar("age2", -100, 100);

		
		//Constraint c2 =  gt( 20, iage);  //  20 > age
		Constraint c2 = lt(iage, 20); // age < 20

		//leftC age <=30
		//c2 age < 20

		System.out.println(" leftC: " + leftC.pretty());
		System.out.println(" c2: " + c2.pretty());
		
		
		
		m.addConstraint(not(implies( c2, leftC)));
		//m.addConstraint(not(implies( leftC, c2)));
		s.read(m);
		//s.solve();
		
		
		s.setTimeLimit(10);
		
		
		Boolean solved = s.solve();
		boolean feasible = s.isFeasible();

		System.out.println("Solved: " + solved);
		System.out.println("Feasible: " + feasible);
		
	
		//System.out.print(s.getVar(v).getVal());
		System.out.print("solve : " + !solved);
		
		
	}
	
	
	
	public boolean solve(){
		
		
		
		//change
		return false;
		
	}

}
