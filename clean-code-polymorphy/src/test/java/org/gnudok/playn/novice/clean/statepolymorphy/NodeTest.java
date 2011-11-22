package org.gnudok.playn.novice.clean.statepolymorphy;

import org.gnudok.playn.novice.clean.statepolymorphy.Node;

import junit.framework.TestCase;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 9 nov. 2011.
 */
public class NodeTest extends TestCase {

	Node one = new Node(1);
	Node two = new Node(2);
	Node addition = new Node(one, two, '+');
	Node three = new Node(3);
	Node four = new Node(two, two, '+');
	Node six = new Node(two, three, '*');
	Node seven = new Node(one, six, '+');
	Node eight = new Node(four, four, '+');
	Node twelve = new Node(four, three, '*');
	Node twentyfour = new Node(three, eight, '*');
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testOne() {
		assertEquals(3d, addition.evaluate());
	}
	
	public void testSeven() {
		assertEquals(7d, seven.evaluate());
	}
	
	public void testTwelve() {
		assertEquals(12d, twelve.evaluate());
	}
	
	public void testFailure() {
		Node fail = new Node(one, null, '+');
		try {
			fail.evaluate();
			fail();
			
		} catch (NullPointerException npe) {
				//
		}
	}
	
}
