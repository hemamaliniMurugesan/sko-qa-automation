package com.seo.regression.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.seo.utility.Utils;

public class SEOGenericValidator 
{

	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private SEOGenericLocator seoGenericLocator;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public SEOGenericValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		seoGenericLocator = new SEOGenericLocator();
	}
	
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		seoGenericLocator.openDriver();

		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		seoGenericLocator.getDriver().quit();
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
			case "environment":
				environment(row.get(1));
				break;
			case "courseCode":
				courseCode(row.get(1));
				break;
			case "getFreeConsultation":
				getFreeConsultation(row.get(1));
				break;
			case "subscribe":
				subscribe(row.get(1));
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
				program(row.get(1));
				break;
			case "navigation":
				navigation();
				break;
			case "enroll":
				enroll(row);
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
	private void environment(String environmentFromExcel)
	{
		try
		{
			String checkEnvironment = seoGenericLocator.setEnvironment(environmentFromExcel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void courseCode(String courseCodeFromExcel)
	{
		String courseCodestatus = "true";
		try
		{
			String checkCourseCode = seoGenericLocator.getCourseCodeText(courseCodeFromExcel);
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
	
	private void getFreeConsultation(String getFreeConsultation)
	{
		String freeConsultationStatus = "fail";
		try
		{
			String checkFreeConsultation = seoGenericLocator.freeConsultationProcess(getFreeConsultation);
			if(checkFreeConsultation.equals(freeConsultationStatus))
			{
				markProcessFailed();
			}
			else if(checkFreeConsultation.equals("notProcessed"))
			{
				
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void enroll(ArrayList<String> enrollDataFromExcel)
	{
		try
		{
			ArrayList<String> verifyEnrollmentProcess = enrollDataFromExcel;
			if(!verifyEnrollmentProcess.contains("NA"))
			{
				verifyEnrollmentProcess = seoGenericLocator.enroll(enrollDataFromExcel);
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
	private void navigation()
	{
		String navigationStatus = "fail";
		try
		{
			String checkNavigation = seoGenericLocator.navigateProcess();
			if(checkNavigation.equalsIgnoreCase("notProcessed"))
			{
				markProcessIgnored();
			}
		else if(checkNavigation.equals(navigationStatus))
			{
				markProcessFailed();
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void subscribe(String subscribeFromExcel)
	{
		String checkSubscribeStatus = "fail";
		try
		{
			String checkSubscribe = seoGenericLocator.subscribeLocator(subscribeFromExcel);
			if(checkSubscribe.equals(checkSubscribeStatus))
			{
				markProcessFailed();
			}
			else if(checkSubscribe.equals("notProcessed"))
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
			ArrayList<String> checkSkillupOnline = seoGenericLocator.skillupOnlineLocator(skillupOnlineFromExcel);
			for(int i = 1; i < checkSkillupOnline.size(); i++)
			{
				if(i == 1)//question or NA
				{
					if(checkSkillupOnline.get(i).equals("notProcessed"))
					{
						markProcessIgnored();
					}
					else if(checkSkillupOnline.get(i).equals("fail"))
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
			String checkShare = seoGenericLocator.shareLocator(shareFromExcel);
			if(checkShare.equals(checkShareStatus))
			{
				markProcessFailed();
			}
			else if(checkShare.equals("notProcessed"))
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
			String checkDownload = seoGenericLocator.downloadLocator(downloadFromExcel);
			if(checkDownload.equals(checkDownloadStatus))
			{
				markProcessFailed();
			}
			else if(checkDownload.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void program(String programFromExcel)
	{
		String checkProgramProcess = "fail";
		try
		{
			String checkProgram = seoGenericLocator.programLocator(programFromExcel);
			if(checkProgram.equals(checkProgramProcess))
			{
				markProcessFailed();
			}
			else if(checkProgram.equals("notProcessed"))
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
		String cellValue = TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
	}
	
	private void markProcessIgnored()
	{
		String process = TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - ignored"));
	}
	
	private void markCellAsHeader()
	{
		String process = TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - header"));
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
		
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestSEOGeneric.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
}
