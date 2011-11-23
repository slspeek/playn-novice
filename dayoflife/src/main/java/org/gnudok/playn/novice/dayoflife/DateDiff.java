package org.gnudok.playn.novice.dayoflife;

import java.util.Date;

public class DateDiff {
	
	static final long MILLIS_IN_A_DAY = 3600 * 1000 * 24; 
	
	public int getDiff(Date now,Date birth){
		
		long millisBirth = birth.getTime();
		long millisNow = now.getTime();
		long millisDiff = millisNow - millisBirth;
		int result = (int) ( millisDiff/MILLIS_IN_A_DAY);
		result ++;
		return result;
		
	}

}

