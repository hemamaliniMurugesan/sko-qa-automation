package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ViewCourseValidator
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	ProcessLogin processLogin = null;
	public ViewCourseValidator(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.driver = driver;
		processLogin = new ProcessLogin(driver);
		this.sheetData = sheetData;
		System.out.println(driver);
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
				case "login":
					login(row);
				break;
				case "verifyViewCourse":
					verifyViewCourse(row);
				break;
			}
		}
	}
	
	String getURL;
	ViewCourseFeature viewCourseFeature = null;
	public void login(ArrayList<String> row) throws InterruptedException
	{
		String status = "Failed";
		String userName = row.get(1);
		String passWord = row.get(2);
		OpenWebsite.openSite(driver);
		viewCourseFeature = new ViewCourseFeature(driver);
		status = viewCourseFeature.login(userName, passWord);
		if(status.equalsIgnoreCase("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "ValidCredentials - failed");
		}
	}
	
	public void verifyViewCourse(ArrayList<String> row) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		viewCourseFeature = new ViewCourseFeature(driver);
		status.addAll(viewCourseFeature.Dashboard());
		status.addAll(viewCourseFeature.shareCourse(row));
		status.addAll(viewCourseFeature.viewCoursePage());
		if(status.contains("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Login").get(3).set(0, "verifyViewCourse - failed");
		}
	}
}
