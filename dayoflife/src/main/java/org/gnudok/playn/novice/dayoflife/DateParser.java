package org.gnudok.playn.novice.dayoflife;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

	DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	Date parse(String text) throws ParseException {
		Date result = format.parse(text);
		return result;
	}
	
}
