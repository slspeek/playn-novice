package org.gnudok.playn.novice.clean.classpolymorphy;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 11 nov. 2011.
 */
public class MultiplicationNode extends OpNode {

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param left
	 * @param right
	 */
	public MultiplicationNode(AbstractNode left, AbstractNode right) {
		super(left, right, new MultiplicationOperator());
	}

	@Override
	public int evaluate() {
			return (this.left.evaluate() * this.right.evaluate());
	}

	@Override
	public String prettyString() {
		return left.prettyString() + "*" + right.prettyString();
	}

}
