package org.gnudok.playn.novice.clean.classpolymorphy;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 11 nov. 2011.
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
	public double evaluate() {
		return (this.left.evaluate() + this.right.evaluate());
	}
	
	@Override
	public String prettyString() {
		return "(" + left.prettyString() + "+" + right.prettyString() + ")";
	}

}
