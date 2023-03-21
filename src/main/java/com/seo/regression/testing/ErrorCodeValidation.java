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
	
	public ErrorCodeValidation(ArrayList<ArrayList<String>> sheetData,WebDriver driver)
	{
		this.driver = driver;
		this.sheetData = sheetData;
		errorCodeLocator = new ErrorCodeLocator(driver);
		this.start();
		driver.quit();
	}
	
	public void start()
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
	}
	
	
	public void geturl(ArrayList<String> codeFromExcel)
	{
		String status = "Failed";
		ArrayList<String> checkURL = errorCodeLocator.checkCourseCode(codeFromExcel);
		for(int i = 0; i < checkURL.size(); i++)
		{
			if(checkURL.get(i).equalsIgnoreCase("Failed"))
			{
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("urlValidation").get(1).set(i, "url - failed");
				status = "Failed";
			}
		}
	}
		
}
