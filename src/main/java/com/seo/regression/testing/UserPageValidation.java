package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class UserPageValidation 
{
	ArrayList<ArrayList<String>> sheetData = null;
	UserPageLocator userPageLocator;
	WebDriver driver;
	public UserPageValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.userPageLocator = new UserPageLocator(this.driver);
		System.out.println("dashboard process started");
		this.start();
	}
	
	public void start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
			case "dotsLink":
				dashboard(row);
				break;
			}
		}
	}

	public void dashboard(ArrayList<String> dataFromExcel)
	{
		userPageLocator.verifyDashboard(dataFromExcel);
	}
}
