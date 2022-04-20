package com.seo.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils 
{
	public static final String DEFAULT_DATA_FORMAT = "dd-MMM-yyyy_HH:mm:ss";
	public static final String HEXA_RED_COLOR = "#ff0000";
	public static final String HEXA_GREEN_COLOR = "#008000";
	public static final String STYLE_DELIMITTER = "-style-";
	
	public static String findDifference(String start_date, String end_date) {

		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
		String duration = "";
		try {

			Date d1 = sdf.parse(start_date);
			Date d2 = sdf.parse(end_date);

			// Calucalte time difference
			// in milliseconds
			long difference_In_Time = d2.getTime() - d1.getTime();

			long difference_In_Seconds = (difference_In_Time / 1000) % 60;

			long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

			duration = difference_In_Hours
					+ " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return duration;
	}
}
