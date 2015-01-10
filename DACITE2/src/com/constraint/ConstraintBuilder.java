package com.constraint;

import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.LinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.LinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.MixedConstraint;
import gov.nasa.jpf.symbc.numeric.NonLinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.RealConstraint;
import gov.nasa.jpf.symbc.numeric.RealExpression;

/**
 * Reference JPF
 * @see Constraint
 */
public class ConstraintBuilder implements Comparable<ConstraintBuilder>{
	public Constraint header;
    public int count = 0;
    private Integer hashCode = null;

    public ConstraintBuilder() {
    	header = null;
    }
    
    
    public ConstraintBuilder make_copy() {
		ConstraintBuilder pc_new = new ConstraintBuilder();
		pc_new.header = this.header;
	    pc_new.count = this.count;
		return pc_new;
	}
    
    public void _addDet(Comparator c, Expression l, Expression r) {
		if (l instanceof IntegerExpression && r instanceof IntegerExpression)
			_addDet(c,(IntegerExpression)l,(IntegerExpression)r);
		else
			//IntegerExpression only
			throw new RuntimeException("## Error: _addDet (type incompatibility real/integer) " + c + " " + l + " " + r);

	}

	// constraints on integers
	public void _addDet(Comparator c, IntegerExpression l, int r) {
		_addDet(c, l, new IntegerConstant(r));
	}

	public void _addDet(Comparator c, int l, IntegerExpression r) {
		_addDet(c, new IntegerConstant(l), r);
	}

	public void _addDet(Comparator c, IntegerExpression l, long r) {
		_addDet(c, l, new IntegerConstant((int)r));
		//_addDet(c, l, (int)r);
	}

	public void _addDet(Comparator c, long l, IntegerExpression r) {
		_addDet(c, new IntegerConstant((int)l), r);
		//_addDet(c, (int)l, r);
	}

	public void _addDet(Comparator c, IntegerExpression l, IntegerExpression r) {

		Constraint t;
	
		if ((l instanceof LinearIntegerExpression) && (r instanceof LinearIntegerExpression)) {
			t = new LinearIntegerConstraint(l, c, r);
		} else {
			t = new NonLinearIntegerConstraint(l, c, r);
		}
		prependUnlessRepeated(t);

	}


	// constraints on reals
	public void _addDet(Comparator c, RealExpression l, double r) {		
		_addDet(c, l, new RealConstant(r));
	}

	public void _addDet(Comparator c, double l, RealExpression r) {

		_addDet(c, new RealConstant(l), r);
	}

	public void _addDet(Comparator c, RealExpression l, RealExpression r) {
		Constraint t;

		t = new RealConstraint(l, c, r);

		prependUnlessRepeated(t);

	}

//	mixed real/integer constraints to handle cast bytecodes

	public void _addDet(Comparator c, RealExpression l, IntegerExpression r) {
		Constraint t;
		t = new MixedConstraint(l, c, r);

		prependUnlessRepeated(t);

	}

	public void _addDet(Comparator c, IntegerExpression l, RealExpression r) {
		Constraint t;

		t = new MixedConstraint(r, c, l);

		prependUnlessRepeated(t);

	}

   /**
     * Prepends the given constraint to this path condition, unless the constraint is already included
     * in this condition.
     *
     * Returns whether the condition was extended with the constraint.
     */
    public boolean prependUnlessRepeated(Constraint t) {
    	// if Green is used and slicing is on then we always add the constraint
    	// since we assume the last constraint added is always the header
        if (!hasConstraint(t)) {
            t.and = header;
            header = t;
            count++;
            return true;
        } else {
            return false;
        }
    }

    public void prependAllConjuncts(Constraint t) {
       t.last().and = header;
       header = t;
       count= length(header);
    }

    public void appendAllConjuncts(Constraint t) {
        Constraint tmp = header.last();
        tmp.and = t;
        count= length(header);
     }
    
    
    public void prependAllCBHeader(ConstraintBuilder cb) {
        cb.header.last().and = header;
        header = cb.header;
        count= length(header);
     }
    

    private static int length(Constraint c) {
        int x= 0;
        while (c != null) {
            x++;
            c = c.getTail();
        }
        return x;
    }

    /**
     * Returns the number of constraints in this path condition.
     */
	public int count() {
		return count;
	}

	/**
	 * Returns whether this path condition contains the constraint.
	 */
	public boolean hasConstraint(Constraint c) {
		Constraint t = header;

		while (t != null) {
			if (c.equals(t)) {
				return true;
			}

			t = t.and;
		}

		return false;
	}

	public Constraint last() {
		Constraint t = header;
		Constraint last = null;
		while (t != null) {
			last = t;
			t = t.and;
		}

		return last;
	}

	
	public String stringPC() {
		return "constraint # = " + count + ((header == null) ? "" : "\n" + header.stringPC());
	}

	public String toString() {
		return ((header == null) ? "True" : header.toString());
	}

	
	/**
	 * @param obj
	 * @return {@code true} if this object is the same as the obj argument;
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		ConstraintBuilder p = (ConstraintBuilder) obj;
		if (count != p.count) {
			return false;
		}
		Constraint c = header;
		Constraint pc = p.header;
		while (c != null) {
			if (pc == null) {
				return false;
			}
			if (!c.equals(pc)) {
				return false;
			}
			c = c.getTail();
			pc = pc.getTail();
		}
		if (pc != null) {
			return false;
		}
		return true;
	}

	/**
	 * Compare two path conditions for orderedness. The function is based on the
	 * hash codes of the constraint. In the event that the hash codes are
	 * equal, a lexicographic comparison is made between the constraints of the
	 * path conditions.
	 * @param pc
	 *            the path condition to compare to
	 * @return -1 if this path condition is less than the other, +1 if it is
	 *         greater, and 0 if they are equal
	 */
	@Override
	public int compareTo(ConstraintBuilder pc) {
		int hc1 = hashCode();
		int hc2 = pc.hashCode();
		if (hc1 < hc2) {
			return -1;
		} else if (hc1 > hc2) {
			return 1;
		} else {
			// perform a lexicographic comparison
			Constraint c1 = header;
			Constraint c2 = pc.header;
			while (c1 != null) {
				if (c2 == null) {
					return 1;
				}
				int r = c1.compareTo(c2);
				if (r != 0) {
					return r;
				}
				c1 = c1.getTail();
				c2 = c2.getTail();
			}
			return (c2 == null) ? 0 : -1;
		}
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * Note: Technically, this routine is incomplete and should take the string
	 * path condition stored in field {@code spc} into account.
	 * 
	 * @return a hash code value for this object
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = new Integer(0);
			Constraint c = header;
			while (c != null) {
				hashCode = hashCode ^ c.hashCode();
				c = c.getTail();
			}
		}
		return hashCode;
	}

	/**
	 * Sometimes we violate our abstraction and fiddle with the fields of a path
	 * condition. Whenever the list of constraints rooted in {@link #header} is
	 * modified in any way, this routine should be called to force the
	 * re-computation of the hash value of the path condition.
	 */
	public void resetHashCode() {
		hashCode = null;
	}

	/**
	 * Recompute the value of {@link #count}, based on the actual list of
	 * constraints.
	 */
	public void recomputeCount() {
		count = 0;
		for (Constraint c = header; c != null; c = c.getTail()) {
			count++;
		}
	}

	/**
	 * Remove the header of the path condition, update the count, and reset the
	 * hash code.
	 */
	public void removeHeader() {
		assert header != null;
		header = header.and;
		count--;
		resetHashCode();
	}
}
