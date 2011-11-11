package org.gnudok.playn.novice.clean.classpolymorphy;

import junit.framework.TestCase;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 11 nov. 2011.
 */
public class MultiplicationNodeTest extends TestCase {

	ValueNode two = new ValueNode(2);
	ValueNode six = new ValueNode(6);
	MultiplicationNode product = new MultiplicationNode(two, six);
	
	public void testEvaluate() {
		assertEquals(12, product.evaluate());
	}
	
	
}
