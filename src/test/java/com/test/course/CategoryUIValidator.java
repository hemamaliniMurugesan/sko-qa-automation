package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.CategoryUILocator;
import com.seo.utility.Utils;

public class CategoryUIValidator
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private CategoryUILocator categoryUILocator;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public CategoryUIValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		categoryUILocator = new CategoryUILocator();
	}
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		categoryUILocator.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		categoryUILocator.getDriver().quit();
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
			case "selectCategory":
				selectCategory(row.get(1));
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
	
	private void selectCategory(String categoryName) 
	{
		String statusOfCategory = "success";
		try
		{
			String getCategoryName = categoryUILocator.selectCategoryFromSkillUp(categoryName);
			if(!(statusOfCategory.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()).equals(getCategoryName.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
			{
				markProcessFailed();
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void validatePopularCategory()
	{
		
	}
	
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
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
		
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestCategoryUI.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
}
