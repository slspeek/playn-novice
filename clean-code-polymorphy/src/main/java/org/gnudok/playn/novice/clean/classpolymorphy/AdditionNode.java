package org.gnudok.playn.novice.clean.classpolymorphy;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author jan. Created 11 nov. 2011.
 */
public class AdditionNode extends OpNode {
	/**
	 * 
	 * @param left
	 * @param right
	 */
	public AdditionNode(AbstractNode left, AbstractNode right) {
		super(left, right, new AdditionOperator());
	}

	@Override
	public int evaluate() {
		return (this.left.evaluate() + this.right.evaluate());
	}

	@Override
	public String prettyString() {
		if (parent != null) {	// there is a parent operation
			if (operator.compareTo(parent.operator) < 0) {
				return "(" + left.prettyString() + "+" + right.prettyString()
						+ ")";
			} else {					// no parent operation
				return left.prettyString() + "+" + right.prettyString();
			}
		} else {
			return left.prettyString() + "+" + right.prettyString();
		}
	}

}
