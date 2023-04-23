package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class RegressionTestLogin
{
	WebDriver driver;
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	ProcessLogin processLogin;
	public RegressionTestLogin(WebDriver driver, String host, ArrayList<ArrayList<String>> sheetData) throws Exception
	{
		try
		{
			this.driver = driver;
			this.sheetData = sheetData;
			this.processLogin = new ProcessLogin(this.driver);
			OpenWebsite.openSite(driver);
			System.out.println("login process started");
			this.start();
			driver.quit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "InvalidUsername":
					InvalidUsername();
					break;
				case "InvalidPassword":
					InvalidPassword();
					break;
				case "InvalidUserNameAndPassword":
					InvalidUserNameAndPassword();
					break;
				case "ValidCredentials":
					ValidCredentials();
					break;
			}
		}
	}
	
	private void InvalidUsername() throws InterruptedException
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(0);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = this.processLogin.checkInvalidUsername(userName, passWord);
		if(status.equalsIgnoreCase("success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(0).set(0, "InvalidUsername - failed");
		}
	}
	
	private void InvalidPassword() throws InterruptedException
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(1);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = this.processLogin.checkInvalidPassword(userName, passWord);
		if(status.equalsIgnoreCase("Success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(1).set(0, "InvalidPassword - failed");
		}
	}
	
	private void InvalidUserNameAndPassword() throws InterruptedException
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(2);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = this.processLogin.checkInvalidUserNameAndPassword(userName, passWord);
		if(status.equalsIgnoreCase("Success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(2).set(0, "InvalidUserNameAndPassword - failed");
		}
	}
	
	private void ValidCredentials() throws InterruptedException
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(3);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = this.processLogin.checkValidCredentials(userName, passWord);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "ValidCredentials - failed");
		}
	}
	
	private void checkLogout()throws InterruptedException
	{
		this.processLogin.logOutFunction();
	}
}
