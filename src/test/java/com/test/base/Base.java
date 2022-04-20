package com.test.base;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

import com.seo.pompages.CourseDetailsPage;
import com.seo.utility.TestUtil;

public class Base 
{
	public static WebDriver driver;
	
	
	public Base()
	{
		
		initilization();
	}
	
	public static void initilization()
	{
//		String browserName = FileReaderManager.getInstance().getConfigReader().getBrowserName();
//		System.setProperty(FileReaderManager.getInstance().getConfigReader().getChromeDriver(), FileReaderManager.getInstance().getConfigReader().getDriverPath());
		
//		if(browserName.equalsIgnoreCase("chrome"))
//		{
//			System.setProperty(FileReaderManager.getInstance().getConfigReader().getChromeDriver(), FileReaderManager.getInstance().getConfigReader().getDriverPath());
//			driver = new ChromeDriver();
//		}
//		else
//		{
//			System.setProperty(FileReaderManager.getInstance().getConfigReader().getGeckoDriver(), FileReaderManager.getInstance().getConfigReader().getGeckoDriverPath());
//			driver = new FirefoxDriver();
//		}
		
		
	}
}
