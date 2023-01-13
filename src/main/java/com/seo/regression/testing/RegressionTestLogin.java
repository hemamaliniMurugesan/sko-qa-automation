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

	public RegressionTestLogin(ArrayList<ArrayList<String>> sheetData)
	{
		this.sheetData = sheetData;
		this.start();
	}
	public void start()
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
				case "logOut":
					checkLogout();
					break;
			}
		}
	}
	public void url(String urlFromExcel)
	{
		processLogin.launchCourse(urlFromExcel);
	}
	
	private void InvalidUsername()
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(1);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = processLogin.login(userName, passWord);
		if(!status.equalsIgnoreCase("success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(1).set(0, "InvalidUsername - failed");
		}
	}
	
	private void InvalidPassword()
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(2);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = processLogin.login(userName, passWord);
		if(!status.equalsIgnoreCase("Success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(2).set(0, "InvalidPassword - failed");
		}
	}
	
	private void InvalidUserNameAndPassword()
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(3);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = processLogin.login(userName, passWord);
		if(!status.equalsIgnoreCase("Success"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "InvalidUserNameAndPassword - failed");
		}
	}
	
	private void ValidCredentials()
	{
		String status = "Failed";
		ArrayList<String> credsRow = sheetData.get(4);
		String userName = credsRow.get(1);
		String passWord = credsRow.get(2);
		status = processLogin.login(userName, passWord);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(4).set(0, "ValidCredentials - failed");
		}
	}
	
	public void checkLogout()
	{
		String status = "Failed";
		status = processLogin.verifyLogOut();
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(5).set(0, "checkLogOut - failed");
		}
	}
}
