package com.seo.regression.testing;

import java.util.ArrayList;

public class UserDropdownValidation
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	UserDropdownList userDropdownList = new UserDropdownList();
	String userName;
	
	public UserDropdownValidation(ArrayList<ArrayList<String>> sheetData)
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
	
	public String url(String urlFromExcel)
	{
		return userDropdownList.launchCourse(urlFromExcel);
	}
	
	public void login(String userName, String password)
	{
		this.userName = userName;
		String status = userDropdownList.login(userName, password);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "Login - failed");
		}
	}
	
	public void userProfile()
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
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("userProfile").get(0).set(0, "userProfile - failed");
		}
	}
	
	public void orderHistory(String orderNumberFromExcel)
	{
		String status = "Failed";
		String verifyOrderHistory = userDropdownList.orderHistory(orderNumberFromExcel);
	}
}
