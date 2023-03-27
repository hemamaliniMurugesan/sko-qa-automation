package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserDropdownValidation
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	UserDropdownList userDropdownList;
	ProcessLogin processLogin;
	String userName;
	WebDriver driver;
	public UserDropdownValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		try
		{
			this.sheetData = sheetData;
			//userDropdownList.openDriver();
			this.driver = driver;
			processLogin = new ProcessLogin(driver);
			userDropdownList = new UserDropdownList(driver);
			this.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void start() throws InterruptedException
	{
		try
		{
			for(int i = 0; i < this.sheetData.size(); i++)
			{
				ArrayList<String> row = this.sheetData.get(i);
				String firstColumn = row.get(0);
				switch(firstColumn)
				{
				case "login":
					login(row.get(1), row.get(2));
					break;
				case "userProfile":
					userProfile();
					break;
				case "orderHistory":
					orderHistory(row.get(1));
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void login(String userName, String password) throws InterruptedException
	{
		this.userName = userName;
		String status = processLogin.loginFunction(userName, password);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(1).set(0, "Login - failed");
		}
	}
	
	public void userProfile() throws InterruptedException
	{
		String status = "Failed";
		userName = userName.replace("@skillup.tech", "");
		String verifyUserProfile = userDropdownList.userProfile();
		if(verifyUserProfile.contains(userName))
		{
			status = "Success";
		}
		else
		{
			status = "Failed";
		}
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("userProfile").get(2).set(0, "userProfile - failed");
		}
	}
	
	public void orderHistory(String orderNumberFromExcel) throws InterruptedException
	{
		String status = "Failed";
		String verifyOrderHistory = userDropdownList.orderHistory(orderNumberFromExcel);
		if(verifyOrderHistory.equalsIgnoreCase(status))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("userProfile").get(3).set(0, "orderHistory - failed");
		}
	}
}
