package org.gnudok.playn.novice.dayoflife;

import java.util.Date;
import java.util.GregorianCalendar;

import org.gnudok.playn.novice.dayoflife.DateDiff;

import junit.framework.TestCase;

public class DateDiffTest extends TestCase {


public void testParse() throws Exception {
	DateDiff diff = new DateDiff();
	Date birthdate;
	Date today;
	
	GregorianCalendar cal = new GregorianCalendar(2011, 7, 2);
	birthdate = cal.getTime();
	today = birthdate;
	assertEquals(1, diff.getDiff(today, birthdate));
	
}
	
	
	
}
