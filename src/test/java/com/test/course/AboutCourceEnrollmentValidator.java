package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.seo.pompages.AboutCourseEnrollmentLocators;
import com.seo.utility.Utils;

public class AboutCourceEnrollmentValidator
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private AboutCourseEnrollmentLocators aboutCourseEnrollmentLocators;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public AboutCourceEnrollmentValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		aboutCourseEnrollmentLocators = new AboutCourseEnrollmentLocators();
	}
	
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		aboutCourseEnrollmentLocators.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		aboutCourseEnrollmentLocators.getDriver().quit();
		endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		duration = Utils.findDifference(startTime, endTime);
		collectSheetResult();
		return sheetStatus;
	}
	
	private void executeProcess(String process, ArrayList<String> row)
	{

		try
		{
			switch (process) 
			{
				case "aboutCourseURL":
					aboutCourseURL(row.get(1));
					break;
				case "plan":
					checkCoursePayment(row.get(1), row.get(2), row.get(3), row.get(4), row.get(5));
					break;
				default:
					break;
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void aboutCourseURL(String courseCodeFromExcel)
	{
		String courseCodestatus = "true";
		try
		{
			String checkCourseCode = aboutCourseEnrollmentLocators.getCourseCodeText(courseCodeFromExcel);
			if(!(courseCodestatus).equals(checkCourseCode))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void checkCoursePayment(String plan, String paymentMode, String price, String priceWithTax, String paymentSuccessText)
	{
		try
		{
			List<Integer> errorCells = aboutCourseEnrollmentLocators.enrollCourse(plan, paymentMode, price, priceWithTax, paymentSuccessText);
			for(Integer cellIndex: errorCells)
			{
				markColumnFailed(cellIndex);
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
	}
	
	private void collectSheetResult()
	{
		ArrayList<String> emptyRow = new ArrayList<>();
		emptyRow.add("");
		
		ArrayList<String> sheetStatusRow = new ArrayList<>();
		sheetStatusRow.add("Status" + Utils.DELIMITTER + "bold" + 
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		sheetStatusRow.add(sheetStatus + 
				Utils.DELIMITTER + "backgroundLT" + 
				Utils.DELIMITTER + "color" + (sheetStatus.equalsIgnoreCase("Pass") ? "Green" : "Red") +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> startTimeRow = new ArrayList<>();
		startTimeRow.add("Started Time" + Utils.DELIMITTER + "bold" +
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		startTimeRow.add(startTime + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> endTimeRow = new ArrayList<>();
		endTimeRow.add("Ended Time" + Utils.DELIMITTER + "bold" +
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		endTimeRow.add(endTime + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> durationRow = new ArrayList<>();
		durationRow.add("Execution Time" + Utils.DELIMITTER + "bold" + 
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		durationRow.add(duration + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestAboutCourseEnrollment.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
}
