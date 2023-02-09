package com.seo.regression.testing;

import java.sql.Driver;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegressionTestLogin 
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	ProcessLogin processLogin = new ProcessLogin();

	public RegressionTestLogin(ArrayList<ArrayList<String>> sheetData) throws InterruptedException
	{
		this.sheetData = sheetData;
		processLogin.openDriver();
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
				case "checkLogout":
					checkLogout();
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
		status = processLogin.checkInvalidUsername(userName, passWord);
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
		status = processLogin.checkInvalidPassword(userName, passWord);
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
		status = processLogin.checkInvalidUserNameAndPassword(userName, passWord);
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
		status = processLogin.checkValidCredentials(userName, passWord);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "ValidCredentials - failed");
		}
	}
	
	private void checkLogout()throws InterruptedException
	{
		processLogin.logOutFunction();
	}
}
