package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class ForgotPasswordValidation 
{
	ArrayList<ArrayList<String>> sheetData = null;
	ForgotPasswordLocator forgotPasswordLocator;
	String sheetStatus = "Pass";
	WebDriver driver;
	
	public ForgotPasswordValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		OpenWebsite.openSite(driver);
		this.sheetData = sheetData;
		this.driver = driver;
		this.forgotPasswordLocator = new ForgotPasswordLocator(driver);
		System.out.println("forget password begins");
		//this.start();
	}
	public String start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "url":
					url(row.get(1));
				break;
				case "forgotPwd":
					checkForgotPwd(row.get(1));
					break;
			}
		}
		return sheetStatus;
	}
	public void url(String urlFromExcel)
	{
		forgotPasswordLocator.openLoginSite(urlFromExcel);
	}
	
	public void checkForgotPwd(String emailFromExcel) throws InterruptedException
	{
		String status = "Failed";
		status = forgotPasswordLocator.forgotPassword(emailFromExcel);
		if(!status.equalsIgnoreCase("Success"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ForGotPwd").get(1).set(1, "forgotPwd - failed");
		}
	}
}
