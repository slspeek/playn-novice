package org.gnudok.playn.novice.clean.classpolymorphy;

public class AdditionOperator extends AbstractOperator {

	
	@Override
	int combine(int x, int y) {
		return x + y;
	}

	@Override
	String prettyString() {
		return "+";
	}

}
