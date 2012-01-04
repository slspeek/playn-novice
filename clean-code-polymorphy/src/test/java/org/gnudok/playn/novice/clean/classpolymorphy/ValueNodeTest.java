package org.gnudok.playn.novice.clean.classpolymorphy;

import junit.framework.TestCase;

public class ValueNodeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testEvaluate() {
		AbstractNode node = new ValueNode(5);
		assertEquals(5, node.evaluate());
	}

}
