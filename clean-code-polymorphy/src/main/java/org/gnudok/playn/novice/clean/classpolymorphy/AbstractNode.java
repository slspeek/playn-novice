package org.gnudok.playn.novice.clean.classpolymorphy;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 9 nov. 2011.
 */
public abstract class AbstractNode {
	
	
	protected OpNode parent;
		
	public abstract int evaluate();
	
	public abstract String prettyString();
}
