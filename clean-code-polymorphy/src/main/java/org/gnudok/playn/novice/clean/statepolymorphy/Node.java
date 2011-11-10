package org.gnudok.playn.novice.clean.statepolymorphy;

/**
 * TODO Put here a description of what this class does.
 *
 * @author jan.
 *         Created 9 nov. 2011.
 */
public class Node {
	
	char operator;
	Node left;
	Node right;
	double value;
	
	public Node(Node left, Node right, char operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public Node(double value) {
		this.value = value;
		this.operator = '#';
	}

	public double evaluate() {
		switch( this.operator) {
		case '#':
			return value;
		case '+':
			return left.evaluate() + right.evaluate();
		case '*':
			return left.evaluate() * right.evaluate();	
		
		}
		throw new IllegalStateException();
	}
	
}
