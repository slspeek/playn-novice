package org.gnudok.playn.novice.clean.classpolymorphy;

public class ValueNode extends AbstractNode {

	private int value;

	public ValueNode(int value){
		this.value = value;
	}
	
	@Override
	public int evaluate() {
		return value;
	}

	@Override
	public String prettyString() {
		return String.valueOf(value);
	}
	
	
}
