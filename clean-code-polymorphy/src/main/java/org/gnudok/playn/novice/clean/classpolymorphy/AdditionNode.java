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
		super(left, right);
	}

	@Override
	public int evaluate() {
		return (this.left.evaluate() + this.right.evaluate());
	}

	@Override
	public String prettyString() {
		if (parent instanceof MultiplicationNode) {
			return "(" + left.prettyString() + "+" + right.prettyString() + ")";
		} else {
			return left.prettyString() + "+" + right.prettyString();
		}
	}

}
