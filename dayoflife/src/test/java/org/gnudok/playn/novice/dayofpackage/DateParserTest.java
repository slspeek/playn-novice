package org.gnudok.playn.novice.dayofpackage;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class DateParserTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testParse() throws Exception {
		DateParser parser = new DateParser();
		String isoString = "20110802";

		GregorianCalendar cal = new GregorianCalendar(2011, 7, 2);
		Date wantedDate = cal.getTime();

		assertEquals(wantedDate, parser.parse(isoString));

	}

	public void testParseWithGarbage() throws Exception {
		DateParser parser = new DateParser();
		String isoString = "3.1415";

		try {
			parser.parse(isoString);
			fail();
		} catch (ParseException e) {
			// ok
		}

	}

}
