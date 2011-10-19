package org.gnudok.playn.novice.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Shape {

	enum Tetrominoes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape, AndreasCrux
	};

	private Tetrominoes pieceShape;
	private List<Point> coordsNew = new ArrayList<Point>();
	static private int[][][] coordsTable;

	public Shape() {
		setShape(Tetrominoes.NoShape);
	}

	public void setShape(Tetrominoes shape) {

		coordsTable = new int[][][] {
				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 0 } },
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 }, { 0, 0 } },
				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 0 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 0 } },
				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 0 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, 0 }, { 0, 0 } }, };

		coordsNew.clear();
		for (int i = 0; i < 5; i++) {
			int x = coordsTable[shape.ordinal()][i][0];
			int y = coordsTable[shape.ordinal()][i][1];
			coordsNew.add(new Point(x, y));
		}
		pieceShape = shape;

	}

	private void addPoint(int x, int y) {
		coordsNew.add(new Point(x, y));
	}

	public Iterator<Point> shapeIterator() {
		return coordsNew.iterator();
	}

	public Tetrominoes getShape() {
		return pieceShape;
	}

	public void setRandomShape() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}

	public int minX() {
		int m = coordsNew.get(0).x;
		ListIterator<Point> it = coordsNew.listIterator();
		while (it.hasNext()) {
			Point current = it.next();
			m = Math.min(m, current.x);

		}
		return m;
	}

	public int minY() {
		int m = coordsNew.get(0).y;
		ListIterator<Point> it = coordsNew.listIterator();
		while (it.hasNext()) {
			Point current = it.next();
			m = Math.min(m, current.y);

		}
		return m;
	}

	public Shape rotateLeft() {
		Shape result = new Shape();
		result.pieceShape = pieceShape;

		ListIterator<Point> it = coordsNew.listIterator();
		while (it.hasNext()) {
			Point current = it.next();
			result.addPoint(current.y, -current.x);
		}
		return result;
	}

	public Shape rotateRight() {
		Shape result = new Shape();
		result.pieceShape = pieceShape;
		ListIterator<Point> it = coordsNew.listIterator();
		while (it.hasNext()) {
			Point current = it.next();
			result.addPoint(-current.y, current.x);
		}
		return result;
	}
}
