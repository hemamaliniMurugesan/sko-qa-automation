package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ErrorCodeLocator
{
	WebDriver driver;
	public ErrorCodeLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	ArrayList<String> statusOfErrorCode = new ArrayList<String>();
	
	public ArrayList<String> checkCourseCode(ArrayList<String> codeFromExcel)
	{
		String url;
		int respCode = 200;
		HttpURLConnection huc = null;
		for(int i = 1; i < codeFromExcel.size(); i++)
		{
			String endURL = codeFromExcel.get(i);
			url = OpenWebsite.setEnvironment(RegressionTesting.ENV_TO_USE)+endURL;
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
						statusOfErrorCode.add(endURL);
					}
					else
					{
						System.out.println("un broken link"+addHosturl);
						((JavascriptExecutor) driver).executeScript("window.open('"+addHosturl+"')");
						ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
						for(String windows : w)
						{
							driver.switchTo().window(windows);
							if(driver.getCurrentUrl().contains(endURL))
							{
								driver.switchTo().window(windows);
								System.out.println("current url : "+driver.getCurrentUrl());
								driver.close();
							}
						}
						driver.switchTo().window(w.get(0));
						statusOfErrorCode.add("Success");
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
