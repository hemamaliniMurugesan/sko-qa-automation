package com.seo.regression.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;

import com.seo.utility.Utils;

public class RegressionGenericValidator 
{

	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private RegressionGenericLocator regressionGenericLocator;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	WebDriver driver;
	public RegressionGenericValidator(WebDriver driver, String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.driver = driver;
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		this.regressionGenericLocator = new RegressionGenericLocator(driver);
		
	}
	
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
	//	regressionGenericLocator.openDriver();

		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
	//	regressionGenericLocator.getDriver().quit();
		endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		duration = Utils.findDifference(startTime, endTime);
		collectSheetResult();
		return sheetStatus;
	}
	
	public void executeProcess(String process, ArrayList<String> row)
	{
		try
		{
			switch (process) 
			{
			case "courseCode":
				courseCode(row.get(1));
				break;
			case "getFreeConsultation":
				getFreeConsultation(row);
				break;
			case "subscribe":
				subscribe(row);
				break;
			case "whySkillupOnline":
				whySkillupOnline(row);
				break;
			case "share":
				share(row.get(1));
				break;
			case "download":
				download(row.get(1));
				break;
			case "program":
				program(row);
				break;
			case "navigation":
				navigation(row.get(1));
				break;
			case "enrollment":
				enrollment(row);
				break;
			default:
				markCellAsHeader();
				break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			markProcessFailed();
		}
	}
	String loginURL;
	
	private void courseCode(String courseCodeFromExcel)
	{
		String courseCodestatus = "true";
		try
		{
			String checkCourseCode = regressionGenericLocator.getCourseCodeText(courseCodeFromExcel);
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
	
	private void getFreeConsultation(ArrayList<String> getFreeConsultation)
	{
		try
		{
			if(!getFreeConsultation.contains("NA"))
			{
				ArrayList<String> checkFreeConsultation = regressionGenericLocator.freeConsultationProcess(getFreeConsultation);
				if(checkFreeConsultation.equals("Fail"))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void enrollment(ArrayList<String> enrollDataFromExcel)
	{
		String statusOfEnrollment = "true";
		try
		{
			ArrayList<String> verifyEnrollmentProcess = enrollDataFromExcel;
			if(!verifyEnrollmentProcess.contains("NA"))
			{
				verifyEnrollmentProcess = regressionGenericLocator.enroll(enrollDataFromExcel);
				for(int i = 0; i < verifyEnrollmentProcess.size(); i++)
				{
					if(!verifyEnrollmentProcess.get(i).equalsIgnoreCase(statusOfEnrollment))
					{
						markColumnFailed(i);
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	private void navigation(String dataFromExcel)
	{
		try
		{
			if(!dataFromExcel.contains("NA"))
			{
				String checkNavigation = regressionGenericLocator.navigateProcess();
				if(checkNavigation.equalsIgnoreCase("fail"))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void subscribe(ArrayList<String> subscribeFromExcel)
	{
		try
		{
			if(!subscribeFromExcel.contains("NA"))
			{
				ArrayList<String> checkSubscribe = regressionGenericLocator.subscribeLocator(subscribeFromExcel);
				if(checkSubscribe.contains("fail"))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void whySkillupOnline(ArrayList<String> skillupOnlineFromExcel)
	{
		try
		{
			if(!skillupOnlineFromExcel.contains("NA"))
			{
				ArrayList<String> checkSkillupOnline = regressionGenericLocator.skillupOnlineLocator(skillupOnlineFromExcel);
				for(int i = 1; i < checkSkillupOnline.size(); i++)
				{
					if(i == 1)//question or NA
					{
						if(checkSkillupOnline.get(i).equals("fail"))
						{
							markColumnFailed(i);
						}
					}
					else if(i == 2)//answer
					{
						if(checkSkillupOnline.get(i).equals("fail"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void share(String shareFromExcel)
	{
		String checkShareStatus = "fail";
		try
		{
			if(!shareFromExcel.contains("NA"))
			{
				String checkShare = regressionGenericLocator.shareLocator(shareFromExcel);
				if(checkShare.equals(checkShareStatus))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void download(String downloadFromExcel)
	{
		String checkDownloadStatus = "fail";
		try
		{
			if(!downloadFromExcel.contains("NA"))
			{
				String checkDownload = regressionGenericLocator.downloadLocator(downloadFromExcel);
				if(checkDownload.equals(checkDownloadStatus))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void program(ArrayList<String> programFromExcel)
	{
		ArrayList<String> checkProgramProcess = new ArrayList<String>();
		try
		{
			if(!programFromExcel.contains("NA"))
			{
				ArrayList<String> checkProgram = regressionGenericLocator.programLocator(programFromExcel);
				if(checkProgram.equals(checkProgramProcess))
				{
					markProcessFailed();
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		if(null != TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
		}
		else if(null != RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
					(process + " - failed"));
		}
	}
	
	private void markProcessIgnored()
	{
		if(null != TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - ignored"));
		}
		else if(null != RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
					(process + " - ignored"));
		}
	}
	
	private void markCellAsHeader()
	{
		if(null != TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - header"));
		}
		else if(null != RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			String process = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
					(process + " - header"));
		}
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
		if(null != TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
			TestRegressionGenericProcess.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
		}
		else if(null != RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP)
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
		}
	}
}
