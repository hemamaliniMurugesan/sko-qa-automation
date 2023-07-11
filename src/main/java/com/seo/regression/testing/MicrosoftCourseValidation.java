package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class MicrosoftCourseValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	MicrosoftCourseLocator microsoftCourseLocator;
	String sheetStatus = "Pass";
	
	public MicrosoftCourseValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver)
	{
		OpenWebsite.openSite(driver);
		this.sheetData = sheetData;
		this.driver = driver;
		this.microsoftCourseLocator = new MicrosoftCourseLocator(driver);
		System.out.println("Microsoft validation Process started");
		//this.start();
	}
	
	public String start()
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "MicrosftPage":
					MicrosftPage();
					break;
				case "MicrosoftScourses":
					MicrosoftScourses(row);
					break;
			}
		}
		return sheetStatus;
	}
	
	public void MicrosftPage()
	{
		ArrayList<String> getStatus = microsoftCourseLocator.verifyMicrosftPage();
		if(getStatus.contains("fail"))
		{
			if(getStatus.contains("fail"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("MicrosoftPage").get(0).set(0, "MicrosftPage - failed");
			}
		}
	}
	
	public void MicrosoftScourses(ArrayList<String> data)
	{
		ArrayList<String> getStatus = microsoftCourseLocator.verifyMicrosoftScourses(data);
		for(int i = 0; i < getStatus.size(); i++)
		{
			if(data.contains(getStatus.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(getStatus.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("MicrosoftPage").get(1).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("MicrosoftPage").get(1).set(position, (cellValue + " - failed"));

			}
		}
	}
}
