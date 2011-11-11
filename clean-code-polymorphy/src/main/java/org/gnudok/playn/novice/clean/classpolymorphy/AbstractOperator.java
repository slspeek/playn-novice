package org.gnudok.playn.novice.clean.classpolymorphy;

public abstract class AbstractOperator implements Comparable<AbstractOperator> {
	abstract int combine(int x, int y);
	
	abstract String prettyString();

	public int compareTo(AbstractOperator o) {
		if (this instanceof AdditionOperator) {
			if(o instanceof MultiplicationOperator) {
				return -1;
			} else {
				return 0;
			}
		} else {
			if(o instanceof MultiplicationOperator) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	
	
}
