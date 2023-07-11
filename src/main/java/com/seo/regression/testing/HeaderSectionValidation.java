package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class HeaderSectionValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	HeaderSectionLocator headerSectionLocator;
	String sheetStatus = "Pass";
	WebDriver driver;
	OpenWebsite openWebsite;
	public HeaderSectionValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.headerSectionLocator = new HeaderSectionLocator(driver);
		System.out.println("header process started");
	}
	
	public String start() throws InterruptedException
	{
		int j = 1;
		while(j<=1)
		{
			System.out.println("Time of execution : "+j);
			for(int i = 0; i < this.sheetData.size(); i++)
			{
				ArrayList<String> row = this.sheetData.get(i);
				String firstColumn = row.get(0);
				switch(firstColumn)
				{
					case "skillupLogo":
						checkSkillupLogo(row.get(1));
						break;
					case "contactUs":
						checkContactUs();
						break;
					case "business":
						checkBusiness();
						break;
					case "blog":
						checkBlog();
						break;
					case "categories":
						checkCategories(row);
						break;
					case "popularCourses":
						checkPopularCourses(row);
						break;
					case "login":
						checkLogin();
						break;
					case "learningPartner":
						checkLearningPartner(row);
						break;
					case "signUP":
						checkSignUP(row.get(1));
						break;
					case "searchCourses":
						checkSearchCourses(row);
						break;
				}
			}
			j++;
		}
		return sheetStatus;
		
	}
	public void checkSkillupLogo(String data) throws InterruptedException
	{
		if(!data.contains("NA"))
		{
			String skillupLogoProcess = headerSectionLocator.checkSkillupLogo();
			if(skillupLogoProcess.equalsIgnoreCase("fail"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(0).set(0, "skillupLogo - failed");
			}
		}
	}
	
	public void checkLearningPartner(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> learningPartner = headerSectionLocator.verifyLearningPartner(data);
		for(int i = 0; i < learningPartner.size(); i++)
		{
			if(data.contains(learningPartner.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(learningPartner.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(6).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(6).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void checkContactUs() throws InterruptedException
	{
		String contactUSProcess = headerSectionLocator.checkContactUs();
		if(contactUSProcess.equalsIgnoreCase("fail"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(1).set(0, "contactUs - failed");
		}
	}
	public void checkBusiness()
	{
		String businessProcess = headerSectionLocator.checkBusiness();
		if(businessProcess.equalsIgnoreCase("fail"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(2).set(0, "business - failed");
		}
	}
	public void checkBlog() throws InterruptedException
	{
		String blogProcess = headerSectionLocator.checkBlog();
		if(blogProcess.equalsIgnoreCase("fail"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(3).set(0, "blog - failed");
		}
	}
	public void checkCategories(ArrayList<String> data)
	{
		ArrayList<String> categoriesProcess = headerSectionLocator.checkCategories(data);
		for(int i = 0; i < categoriesProcess.size(); i++)
		{
			if(data.contains(categoriesProcess.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(categoriesProcess.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(4).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(4).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void checkPopularCourses(ArrayList<String> data)
	{
		ArrayList<String> popularCouseProcess = headerSectionLocator.checkPopularCourses(data);
		for(int i = 0; i < popularCouseProcess.size(); i++)
		{
			if(data.contains(popularCouseProcess.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(popularCouseProcess.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(5).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(5).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void checkLogin()
	{
		String loginProcess = headerSectionLocator.checkLogin();
		if(loginProcess.equalsIgnoreCase("fail"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(7).set(0, "login - failed");
		}
	}
	public void checkSignUP(String data) throws InterruptedException
	{
		if(!data.contains("NA"))
		{
			String signUpProcess = headerSectionLocator.checkSignUP();
			if(signUpProcess.equalsIgnoreCase("fail"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(8).set(0, "signUP - failed");
			}
		}
	}
	public void checkSearchCourses(ArrayList<String> data)
	{
		if(!data.contains("NA"))
		{
			String searchProcess = headerSectionLocator.checkSearchCourses(data.get(1));
			if(searchProcess.equalsIgnoreCase("fail"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("HeaderSection").get(9).set(0, "searchCourses - failed");
			}
		}
	}
}
