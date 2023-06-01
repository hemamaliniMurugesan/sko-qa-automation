package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ErrorCodeValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	ErrorCodeLocator errorCodeLocator;
	String sheetStatus = "Pass";
	
	public ErrorCodeValidation(ArrayList<ArrayList<String>> sheetData,WebDriver driver)
	{
		this.sheetData = sheetData;
		this.errorCodeLocator = new ErrorCodeLocator(driver);
		System.out.println("error code validation process started");
//		this.start();
	}
	
	public String start()
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case"url":
					geturl(row);
				break;
			}
		}
		return sheetStatus;
	}
	
	
	public void geturl(ArrayList<String> codeFromExcel)
	{
		ArrayList<String> checkURL = errorCodeLocator.checkCourseCode(codeFromExcel);
		for(int i = 0; i < checkURL.size(); i++)
		{
			if(codeFromExcel.contains(checkURL.get(i)))
			{
				sheetStatus = "Fail";
				int position = codeFromExcel.indexOf(checkURL.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("urlValidation").get(0).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("urlValidation").get(0).set(position, (cellValue + " - failed"));
			}
		}
	}
		
}
