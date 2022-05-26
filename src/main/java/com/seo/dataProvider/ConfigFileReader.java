package com.seo.dataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class ConfigFileReader 
{
	private static Properties properties;
	private static final String PROPERTY_FILE_PATH = "D:\\AutomationTestingWorkspace\\sko-qa-automation\\src\\main\\java\\com\\seo\\utility\\config.properties";
	static
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(PROPERTY_FILE_PATH));
			properties = new Properties();
			try
			{
				properties.load(reader);
				reader.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + PROPERTY_FILE_PATH);
		}
	}
	
	public static String getProperty(String property)
	{
		return properties.getProperty(property);
	}
	
	public static String getchromeDriverPath()
	{
		String chromeDriverPath = properties.getProperty("chromeDriverPath");
		if(chromeDriverPath != null)
			return chromeDriverPath;
		else
			throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
	}
	
	public static String getURL()
	{
		String url = properties.getProperty("url");
		if(url != null)
		{
			return url;
		}
		else
		{
			throw new RuntimeException("url not found in property file");
		}
	}
	
	public static String getMetaURL()
	{
		String metaURL = properties.getProperty("metaURL");
		if(metaURL != null)
		{
			return metaURL;
		}
		else
		{
			throw new RuntimeException("metaURL is not found in property file");
		}
	}
	
	public static String getExcelPath()
	{
		String excelPath = properties.getProperty("excelPath");
		if(excelPath != null)
		{
			return excelPath;
		}
		else
		{
			throw new RuntimeException("excelPath is not found in property file");
		}
	}
	
	public static String getAboutCourseURL()
	{
		String aboutCourseURL = properties.getProperty("aboutCourseURL");
		if(aboutCourseURL != null)
		{
			return aboutCourseURL;
		}
		else
		{
			throw new RuntimeException("AboutCourse URL is not found in property file");
		}
	}
	
	public static String getSEOLoginURL()
	{
		String seoLoginURL = properties.getProperty("seoLoginURL");
		if(seoLoginURL != null)
		{
			return seoLoginURL;
		}
		else
		{
			throw new RuntimeException("AboutCourse URL is not found in property file");
		}
	}
	
	public static String getUsername()
	{
		String username = properties.getProperty("username");
		if(username != null)
		{
			return username;
		}
		else
		{
			throw new RuntimeException("username is not in property file");
		}
	}
	
	public static String getPassword()
	{
		String password = properties.getProperty("password");
		if(password != null)
		{
			return password;
		}
		else
		{
			throw new RuntimeException("password is not in property file");
		}
	}
	
	public static String getPlan()
	{
		String plan = properties.getProperty("enrollPlan");
		if(plan != null)
		{
			return plan;
		}
		else
		{
			throw new RuntimeException("enroll plan is not in property file");
		}
	}
	
	public static String getProdPaymentMode()
	{
		String prodPaymentMode = properties.getProperty("prodPaymentMode");
		if(prodPaymentMode != null)
		{
			return prodPaymentMode;
		}
		else
		{
			throw new RuntimeException("prodPaymentMode is not available in property file");
		}
	}
}
