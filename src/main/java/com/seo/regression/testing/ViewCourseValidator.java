package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.By;

public class ViewCourseValidator extends OpenWebsite
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	ViewCourseFeature viewCourseFeature = new ViewCourseFeature();
	
	public ViewCourseValidator(ArrayList<ArrayList<String>> sheetData) throws InterruptedException
	{
		this.sheetData = sheetData;
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
				case "url":
					url(row.get(1));
				break;
				case "login":
					login(row);
				break;
				case "verifyViewCourse":
					verifyViewCourse();
				break;
			}
		}
	}
	public String url(String urlFromExcel)
	{
		return viewCourseFeature.launchCourse(urlFromExcel);
	}
	
	public void login(ArrayList<String> row)
	{
		String status = "Failed";
		String userName = row.get(1);
		String passWord = row.get(2);
		status = viewCourseFeature.login(userName, passWord);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "ValidCredentials - failed");
		}
	}
	
	public void verifyViewCourse() throws InterruptedException
	{
		String status = "Failed";
		status = viewCourseFeature.clickViewCourse();
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "verifyViewCourse - failed");
		}
	}
}
