package org.gnudok.playn.novice.dayoflife;

import java.text.ParseException;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		String birthString = args[0];
		DateParser parser = new DateParser();
		DateDiff diff = new DateDiff();
		Date now = new Date();

		try {
			Date birthdate = parser.parse(birthString);
			int result = diff.getDiff(now, birthdate);
			System.out.println(result);
		} catch (ParseException e) {
			System.out.println("tl;dr check the source code." + e.getMessage());
		}

	}

}
