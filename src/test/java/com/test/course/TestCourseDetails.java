package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.CourseDetailsPage;
import com.seo.utility.ProcessExcel;
import com.seo.utility.Utils;

public class TestCourseDetails
{

	WebDriverWait wait;
	CourseDetailsPage courseDetails;
	String CURRENT_SHEET = "";
	int CURRENT_ROW = 0;
	String startTime = "";
	String endTime = "";
	String duration = "";
	public static LinkedHashMap<String, ArrayList<ArrayList<String>>> EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP;
	public static SoftAssert softAssert = null;
	private HashMap<String, String> sheetsResult = new HashMap<String, String>();

	public TestCourseDetails(String excelPath)
	{
		softAssert = new SoftAssert();
		courseDetails = new CourseDetailsPage();
		excelPath = "D:\\SEO_InputData_FromTeam\\mahak\\ViewSourse\\IBM Applied AI Professional Certification.xlsx";
		this.testCourseDetails(excelPath);
	}
	
	public void testCourseDetails(String excelPath)
	{
		EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		try 
		{
			LinkedHashMap<String, ArrayList<ArrayList<String>>> data = ProcessExcel.readExcelFileAsRows(excelPath);
			EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP = ProcessExcel.readExcelFileAsRows(excelPath);
			for (Entry<String, ArrayList<ArrayList<String>>> entry : data.entrySet()) 
			{
				String sheetName = entry.getKey();
				ArrayList<ArrayList<String>> sheetData = entry.getValue();
				try
				{
					CourseDetailsValidator courseDetailsValidator = new CourseDetailsValidator(sheetName, sheetData);
					String sheetStatus = courseDetailsValidator.processSheetData();
					sheetsResult.put(sheetName, sheetStatus);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
			duration = Utils.findDifference(startTime, endTime);
			prepareConsolidatedSheet();
			ProcessExcel.writeExcelFileAsRows(EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP, "D:\\", "result.xlsx");
		}
	}

	@AfterMethod
	public void resultStatus(ITestResult result) {}
	
	private void prepareConsolidatedSheet()
	{
		ArrayList<ArrayList<String>> consolidatedSheedData = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> testEnvRow = new ArrayList<>();
		testEnvRow.add("Test environment" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		testEnvRow.add(ConfigFileReader.getURL() +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> executionStartsOn = new ArrayList<>();
		executionStartsOn.add("Test execution starts on" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		executionStartsOn.add(startTime +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> executionEndsOn = new ArrayList<>();
		executionEndsOn.add("Test execution ends on" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		executionEndsOn.add(endTime +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> executionDuration = new ArrayList<>();
		executionDuration.add("Execution time" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		executionDuration.add(duration +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> emptyRow = new ArrayList<>();
		emptyRow.add("");
		
		ArrayList<String> courseResultHeader = new ArrayList<>();
		courseResultHeader.add("Courses" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		courseResultHeader.add("Result" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		consolidatedSheedData.add(testEnvRow);
		consolidatedSheedData.add(executionStartsOn);
		consolidatedSheedData.add(executionEndsOn);
		consolidatedSheedData.add(executionDuration);
		consolidatedSheedData.add(emptyRow);
		consolidatedSheedData.add(courseResultHeader);
		boolean hasFailedSheets = false;
		for(Entry<String, String> entry: sheetsResult.entrySet())
		{
			String sheetName = entry.getKey();
			String sheetStatus = entry.getValue();
			ArrayList<String> sheetResult = new ArrayList<String>();
			sheetResult.add(sheetName +
					Utils.STYLE_DELIMITTER + "backgroundlime" +
					Utils.STYLE_DELIMITTER + "border");
			
			if(sheetStatus.equalsIgnoreCase("Fail"))
			{
				hasFailedSheets = true;
			}
			
			sheetResult.add(sheetStatus + Utils.STYLE_DELIMITTER + "color" + (sheetStatus.equalsIgnoreCase("Pass") ? "Green" : "Red") +
					Utils.STYLE_DELIMITTER + "backgroundlime" +
					Utils.STYLE_DELIMITTER + "border");
			
			consolidatedSheedData.add(sheetResult);
		}
		EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.put("Consolidated Result" + Utils.STYLE_DELIMITTER +
				(hasFailedSheets ? "red" : "green"), consolidatedSheedData);
	}
}
