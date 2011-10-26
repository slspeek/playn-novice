package org.gnudok.playn.novice.tetris.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class ShapeTest extends TestCase {

	Shape lShape;
	
	protected void setUp() throws Exception {
		super.setUp();
	}



	public void testRotateLShapeLeft() {
		lShape = new Shape();
		lShape.setShape(Tetrominoes.LShape);
		Shape newShape = lShape.rotateLeft();
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1,1));
		list.add(new Point(-1,0));
		list.add(new Point(0,0));
		list.add(new Point(1,0));
		
		Iterator<Point> it = newShape.shapeIterator();
		while(it.hasNext()) {
			Point p = it.next();
			//System.out.println(p);
			assertTrue(list.contains(p));
		}
	}

	public void testRotateLShapeRight() {
		lShape = new Shape();
		lShape.setShape(Tetrominoes.LShape);
		Shape newShape = lShape.rotateRight();
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1,0));
		list.add(new Point(0,0));
		list.add(new Point(1,0));
		list.add(new Point(1,-1));
		
		Iterator<Point> it = newShape.shapeIterator();
		while(it.hasNext()) {
			Point p = it.next();
			//System.out.println("R: " + p);
			assertTrue(list.contains(p));
		}
	}

}
