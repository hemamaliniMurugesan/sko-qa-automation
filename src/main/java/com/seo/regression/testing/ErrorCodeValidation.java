package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ErrorCodeValidation extends OpenWebsite
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	@Override
	public WebDriver getDriver() {
		return super.getDriver();
	}
	@Override
	public void openDriver() {
		super.openDriver();
	}
	public ErrorCodeValidation(ArrayList<ArrayList<String>> sheetData)
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
					getCourseCodeText(row);
				break;
			}
		}
	}
	
	public String getCourseCodeText(ArrayList<String> codeFromExcel)
	{
		this.openDriver();
		String statusOfErrorCode = "Failed";
		ArrayList<String> urlForCourse = codeFromExcel;
		for(int i = 0; i < urlForCourse.size(); i++)
		{
			String url = this.setEnvironment(RegressionTesting.ENV_TO_USE)+codeFromExcel;
			this.openDriver();
			HttpURLConnection huc = null;
			int respCode = 200;
			String addHosturl = url;
			try
			{
				huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				System.out.println("status code : "+respCode + " " +addHosturl);
				if(respCode > 200)
				{
					System.out.println("broken link"+addHosturl);
					statusOfErrorCode = "Failed";
					System.exit(0);
				}
				else
				{
					System.out.println("un broken link"+addHosturl);
					driver.get(addHosturl);
					statusOfErrorCode = "Success";
					driver.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return statusOfErrorCode;
	}
		
}
