package org.gnudok.playn.novice.dayofpackage;

/**
 * DayOfLife app
 *  Meaning is to calculate number of days since a BitrhDay
 *  
 *  Calcuate diff time and respresent in string form
 * To use the default format of the user's computer, you can apply the getDateInstance method in 
 *  the following way to create the appropriate DateFormat object:
 *  DateFormat df = DateFormat.getDateInstance();   
 * 
 *
 */
import java.util.*;

public class App 
{
	// get date in Date form or string form from user input
	public class BirthDate extends Date 
	{
		Date birthdate = new Date();		// stupid 	
		public Date getBirthDate()
		{
			return birthdate;
		}	
	}
	
	// represent Bithdate dat in string 
	public class BirthDateToString extends String 
	{
		String birthdateString = new String();		// stupid 	
		public Date getBirthDateString()
		{
			return birthdateString;
		}	
	}


	
	
    public static void main( String[] args )
    {
    	 Date now = new Date();
         long nowLong = now.getTime();
         System.out.println("Value is " + nowLong);
    	 //System.out.println( "Hello World!" );
    }
}
