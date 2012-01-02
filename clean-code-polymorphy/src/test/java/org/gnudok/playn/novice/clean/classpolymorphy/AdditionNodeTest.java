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
		assertEquals(10, add.evaluate());
	}
	
	public void testPrettyPrint() {
		AbstractNode one = new ValueNode(1);
		AbstractNode two = new ValueNode(2);
		AbstractNode five = new ValueNode(5);
		AbstractNode seven = new ValueNode(7);
		
		
		AbstractNode leftPlus = new AdditionNode(one, two);
		AbstractNode rightProduct = new MultiplicationNode(five, seven);
		AbstractNode topPlus = new AdditionNode(leftPlus, rightProduct);
		
		assertEquals("1+2+5*7", topPlus.prettyString());
	}
	

}
