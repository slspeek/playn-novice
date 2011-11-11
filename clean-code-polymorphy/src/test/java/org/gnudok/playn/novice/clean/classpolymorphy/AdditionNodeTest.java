package org.gnudok.playn.novice.clean.classpolymorphy;

import junit.framework.TestCase;

/**
 * @author jan.
 *         Created 11 nov. 2011.
 */
public class AdditionNodeTest extends TestCase {

	/**
	 * Test method for {@link org.gnudok.playn.novice.clean.classpolymorphy.AdditionNode#evaluate()}.
	 */
	ValueNode four = new ValueNode(4);
	ValueNode six = new ValueNode(6);
	AdditionNode add = new AdditionNode(four, six);
	
	public void testEvaluate() {
		assertEquals(10d, add.evaluate());
	}
	

}
