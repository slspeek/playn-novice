package org.gnudok.playn.novice.clean.classpolymorphy;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 11 nov. 2011.
 */
public abstract class OpNode extends AbstractNode {

	protected AbstractNode left;
	protected AbstractNode right;
	
	public OpNode(AbstractNode left, AbstractNode right) {
		this.left = left;
		this.right = right;
		left.parent = this;
		right.parent = this;
	}
}
