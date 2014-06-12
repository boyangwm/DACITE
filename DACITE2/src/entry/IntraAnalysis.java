package entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.constraint.ConstraintBuilder;

import soot.Body;
import soot.BodyTransformer;
import soot.Immediate;
import soot.Local;
import soot.SootMethodRef;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JBreakpointStmt;
import soot.jimple.internal.JCastExpr;
import soot.jimple.internal.JEnterMonitorStmt;
import soot.jimple.internal.JExitMonitorStmt;
import soot.jimple.internal.JGotoStmt;
import soot.jimple.internal.JIdentityStmt;
import soot.jimple.internal.JIfStmt;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JLookupSwitchStmt;
import soot.jimple.internal.JNopStmt;
import soot.jimple.internal.JRetStmt;
import soot.jimple.internal.JReturnStmt;
import soot.jimple.internal.JReturnVoidStmt;
import soot.jimple.internal.JStaticInvokeExpr;
import soot.jimple.internal.JTableSwitchStmt;
import soot.jimple.internal.JThrowStmt;
import soot.toolkits.graph.BriefUnitGraph;
import soot.util.Chain;
import expression.StmtCondition;
import expression.ExpressionUtil;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import static expression.ExpressionUtil.*;

public class IntraAnalysis extends BodyTransformer{

	private static IntraAnalysis instance = new IntraAnalysis();
	private IntraAnalysis() {}
	public static IntraAnalysis v() { return instance; }
	static String oldPath;
	



	Map<Unit, StatesOfUnit> mapState = new HashMap<Unit, StatesOfUnit>();

	protected void internalTransform(Body b, String phaseName, Map options)
	{

		System.out.println("\n**************************************\n");


		//refresh all state
		mapState = new HashMap<Unit, StatesOfUnit>();
		System.out.println("Intra-procedural analysis method name : " 
				+ b.getMethod().getName());
		oneVisitAnalysis(b);
	}


	/**
	 * initialize the precondition for all heads units.
	 * All local variables point to itself. 
	 * @param b
	 */
	protected void init(Chain<Unit> units, List<Unit> heads,  Chain<Local> locals)
	{


		for(Unit unit : heads){
			Iterator<Local> it =locals.iterator();
			Map<Local, IntegerExpression> map = new HashMap<Local, IntegerExpression>();
			while(it.hasNext()) {
				Local var = it.next();
				//System.out.println(var.toString());
				//ExprManager exprM = new ExprManager(transferSimpleValue(var, map));
				map.put(var, makeIntVar(var.getName()));
			}	
			StatesOfUnit sou = new StatesOfUnit(map);
			//start at True
			//sou.setPreCon(new StmtCondition()); 
			sou.setPreCon(new ConstraintBuilder()); 
			mapState.put(unit, sou);

			System.out.println("head : " + unit.toString() + " " + unit.hashCode()); 
		}

		Iterator<Unit> stmtIt = units.iterator();
		while(stmtIt.hasNext()) {
			Unit stmt = (Unit) stmtIt.next();
			//initialize pre-conditions for all statements
			if(!mapState.containsKey(stmt)){
				Map<Local, IntegerExpression> map = new HashMap<Local, IntegerExpression>();
				StatesOfUnit sou = new StatesOfUnit(map);
				mapState.put(stmt, sou);
			}
		}
	}


	/**
	 * 
	 * @param b
	 */
	protected void oneVisitAnalysis(Body b)	{
		//Initialize
		BriefUnitGraph bugraph = new BriefUnitGraph(b);
		ArrayList<Unit> visited = new ArrayList<Unit> ();
		ArrayList<Unit> worklist = new ArrayList<Unit> ();
		ArrayList<Unit> preWorklist = (ArrayList<Unit>) worklist.clone();

		worklist.addAll(bugraph.getHeads());
		init(b.getUnits(),  bugraph.getHeads(), b.getLocals());

		//Unit and the number of its unvisited predecessor
		Map<Unit, Integer> skipedUnits =  new HashMap<Unit, Integer>();


		while(worklist.size() > 0) {
			boolean isChange = false;
			if(!compWorkLists(preWorklist, worklist))
			{
				isChange = true;
			}
			preWorklist =  (ArrayList<Unit>) worklist.clone();


			System.out.println("================================="  ); 

			//Select the first node from the work list.
			Unit u = (Unit)worklist.remove(0);
			System.out.println("current u : " + u.toString() + u.hashCode()); 


			if (visited.contains(u)) {
				//we use one time visit strategy
				continue;
			}

			//Check if its all predecessors have been visited. We have 
			//to handle loops.
			List<Unit> preds = bugraph.getPredsOf(u);
			int nonVisisted = preds.size();
			for (int i = 0; i < preds.size(); i++) {
				Unit pred = preds.get(i);
				//System.out.println("pre u : " + pred.toString() + pred.hashCode() ); 
				if(visited.contains(pred))
					nonVisisted--;
			}
			//			int nonVisistedBefore = 0;
			//			if(skipedUnits.containsKey(u)) {
			//				nonVisistedBefore = skipedUnits.get(u);
			//			}

			if(nonVisisted == 0) {
				skipedUnits.remove(u);
			}else
			{
				//				if(nonVisistedBefore != nonVisisted) {
				//					skipedUnits.put(u, nonVisisted);
				//					worklist.add(u);
				//					continue;
				//				}
				if(isChange)
					continue;
			}

			if(!bugraph.getHeads().contains(u))
				updatePreStateAndCon(u , preds);

			printPreStateAndCon(u);

			System.out.println("statement : " + u.toString() + " " + u.hashCode());
			if (u instanceof Stmt){
				Stmt st = (Stmt) u;
				analyzeStmt(st);
			}
			else
			{
				System.out.println("Unexpacted error!!");
				System.exit(1);
			}
			visited.add(u);
			printPostState(u);

			List<Unit> succs = bugraph.getSuccsOf(u);

			for (int i = 0; i < succs.size(); i++) {
				Unit secc = succs.get(i);

				System.out.println("seccessor : " + secc.toString() + secc.hashCode() ); 
				if (!visited.contains(secc))
					worklist.add(secc);
			}
		}
	}



	/**
	 * @param u current condition
	 * @param preds its predecessor.
	 */
	private void updatePreStateAndCon(Unit u, List<Unit> preds){
		try{
			for(Unit pre : preds) {
				StatesOfUnit souPre = mapState.get(pre);
				Map<Local, IntegerExpression> prePostState = souPre.getPost();

				//has to change latter. This is meet operation.
				//For now, we only consider on predecessor. 
				StatesOfUnit sou = mapState.get(u);
				sou.updatePreState(prePostState);	
				if(souPre.isBranch()){
					if(pre instanceof JIfStmt){
						JIfStmt ifStmt = (JIfStmt)pre;
						if(ifStmt.getTarget() == u){
							sou.setPreCon(souPre.getPostCon());
						}else {
							sou.setPreCon(souPre.getPostBranchCon());
						}
					}else if(pre instanceof JGotoStmt){
						JGotoStmt ifStmt = (JGotoStmt)pre;
						assert(ifStmt.getTarget() == u); //see line 311
						sou.setPreCon(souPre.getPostCon());
					}else{
						assert(false);
					}
				}else
				{
					sou.setPreCon(souPre.getPostCon());
				}
			}
		}catch(Exception e){
			System.out.println("updatePreState ERROR!!" );
			e.printStackTrace();
		}
	}

	/**
	 * The pre condition is ready to use in mapState.
	 * Use the precondition to get the post condition for stmt
	 * @param stmt
	 */
	private void analyzeStmt(Stmt stmt){
		//System.out.println("************* analyzeStmt **************");
		if (stmt instanceof JAssignStmt)	{



			JAssignStmt as =  (JAssignStmt)stmt;
			Value vLeft = as.getLeftOp();
			Value vRight = as.getRightOp();
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			// Cast
			if(vRight instanceof JCastExpr){
				JCastExpr castR = (JCastExpr)vRight;
				Value orgValue = castR.getOp();
				IntegerExpression newExp = ExpressionUtil.transferValueToExp(orgValue, preState);
				Map<Local, IntegerExpression> postState = preState;
				postState.put((Local)vLeft, newExp);
				sou.updatePostState(postState);
			} 
			else if(vRight instanceof Ref || 
					vRight instanceof InvokeExpr){
				sou.updatePostState(preState);
			}else if (vRight instanceof Immediate)
			{
				System.out.println("Right is Immediate :" + stmt.toString());
				if(vRight instanceof StringConstant)
				{
					sou.updatePostState(preState);
				}else
				{
					IntegerExpression newExp = ExpressionUtil.transferValueToExp(vRight, preState);
					Map<Local, IntegerExpression> postState = preState;
					postState.put((Local)vLeft, newExp);
					sou.updatePostState(postState);
				}
			}else
			{

				IntegerExpression newExp = ExpressionUtil.transferValueToExp(vRight, preState);
				Map<Local, IntegerExpression> postState = preState;
				postState.put((Local)vLeft, newExp);
				sou.updatePostState(postState);
			}
			//condition part
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(false);

		}else if(stmt instanceof JIdentityStmt) {
			//throw new RuntimeException("## Error: does not handle IdentityStmt");
			//all ids have been initialized to point itself
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			sou.updatePostState(preState);
			//condition part
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(false);
		}else if(stmt instanceof JReturnVoidStmt){
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			sou.updatePostState(preState);
			//condition part
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(false);
		}else if(stmt instanceof JInvokeStmt){
			//such as "specialinvoke this.<java.lang.Object: void <init>()>()"
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			ConstraintBuilder stmtCondition = sou.getPreCon();

			System.out.println("JinvokeStmt");
			JInvokeStmt invStmt = (JInvokeStmt)stmt;
			InvokeExpr invokeExp = (InvokeExpr) invStmt.getInvokeExpr();
			if(invokeExp instanceof JStaticInvokeExpr)
			{
				JStaticInvokeExpr gNewInv = (JStaticInvokeExpr) invokeExp;
				System.out.println("JStaticInvokeExpr to string : " + gNewInv.toString());
				SootMethodRef mRef = gNewInv.getMethodRef();
				System.out.println( "signiture : " + mRef.getSignature());
				if(mRef.getSignature().equals("<DBAnnotation: void annoate(java.lang.String,java.lang.String,java.lang.String,boolean)>"))
				{
					System.out.println( "annotation invoke !!!");
					List <Value> AArgs = gNewInv.getArgs();
					// update annotation map if it's necessary. (Source)
					// or store the constraint. (Sink) 
					ExpressionUtil.annotationUtilize(AArgs, preState, stmtCondition);
					assert(AArgs.size() == 4);
					for(Value vb : AArgs)
					{
						System.out.println( "vb.getValue() : " + vb.toString());
					}

				}

				sou.updatePostState(preState);
			}else{
				sou.updatePostState(preState);
			}
			//condition part
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(false);
		}else if(stmt instanceof JIfStmt){
			JIfStmt ifStmt = (JIfStmt)stmt;
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			sou.updatePostState(preState);

			//condition part
			ConstraintBuilder newCon = sou.getPreCon();
			Value curCon = ifStmt.getCondition();
			//System.out.println("preCon 1-1  :" + newCon.toString());
			transferConditionExp(curCon, preState, newCon, false);
			sou.setPostCon(newCon);
			sou.setIsBranch(true);
			//System.out.println("preCon 1-2  :" + newCon.toString());

			ConstraintBuilder newBranchCon = sou.getPreCon();
			//System.out.println("preCon 2-1  :" + newBranchCon.toString());
			transferConditionExp(curCon, preState, newBranchCon, true);
			sou.setPostBranchCon(newBranchCon);
			//System.out.println("preCon 2-2  :" + newBranchCon.toString());
		}else if(stmt instanceof JLookupSwitchStmt){
			throw new RuntimeException("## Error: does not handle LookupSwitchStmt");
		}else if(stmt instanceof JEnterMonitorStmt){
			throw new RuntimeException("## Error: does not handle JEnterMonitorStmt");
		}else if(stmt instanceof JExitMonitorStmt){
			throw new RuntimeException("## Error: does not handle JExitMonitorStmt");
		}else if(stmt instanceof JNopStmt){
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			sou.updatePostState(preState);
			//condition part
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(false);
		}else if(stmt instanceof JRetStmt){
			throw new RuntimeException("## Error: does not handle RetStmt");
		}else if(stmt instanceof JReturnStmt){
			throw new RuntimeException("## Error: does not handle ReturnStmt");
		}else if(stmt instanceof JTableSwitchStmt){
			throw new RuntimeException("## Error: does not handle TableSwitchStmt");
		}else if(stmt instanceof JThrowStmt){
			throw new RuntimeException("## Error: does not handle ThrowStmt");
		}else if(stmt instanceof JBreakpointStmt){
			throw new RuntimeException("## Error: does not handle JBreakpointStmt");
		}else if(stmt instanceof JGotoStmt){
			//JGotoStmt gotoStmt = (JGotoStmt)stmt;
			StatesOfUnit sou = mapState.get(stmt);
			Map<Local, IntegerExpression> preState = sou.getPre();
			sou.updatePostState(preState);
			sou.setPostCon(sou.getPreCon());
			sou.setIsBranch(true);
			//may have potential problem, but usually doesn't use in this way. 
			//goto : A
			// a=2;
			// A: b= 2
		}else {
			throw new RuntimeException("## Error: does not handle the stmt");
		}



		//System.out.println("************* analyzeStmt end **************");
	}


	public void printPreStateAndCon(Unit u){
		StatesOfUnit sou = mapState.get(u);
		sou.printPreState();
		sou.printPreCondition();
	}


	public void printPostState(Unit u){
		StatesOfUnit sou = mapState.get(u);
		sou.printPostState();
	}

	private boolean compWorkLists(ArrayList<Unit> first, ArrayList<Unit> second)
	{
		if (first==null && second==null) return true;
		if (first!=null && second==null) return false;
		if (first==null && second!=null) return false;

		if ( first.size()!=second.size() ) return false;
		for(Unit uf : first)
		{
			if(!second.contains(uf))
				return false;
		}
		return true;
	}


}




