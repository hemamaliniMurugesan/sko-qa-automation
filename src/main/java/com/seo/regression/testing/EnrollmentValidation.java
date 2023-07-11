package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class EnrollmentValidation 
{
	ArrayList<ArrayList<String>> sheetData = null;
	ContactInfoLocator contactInfoLocator;
	WebDriver driver;
	
	public EnrollmentValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.contactInfoLocator = new ContactInfoLocator(driver);
		System.out.println("contact information process started");
		this.start();
		driver.quit();
	}
	
	public void start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "enroll":
					verifyEnroll(row);
					break;
			}
		}
	}
	
	public void verifyEnroll(ArrayList<String> dataFromExcel)
	{
		
	}
}
