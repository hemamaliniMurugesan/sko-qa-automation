package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class PLUValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	PLULocators PLUPageLocators;
	String sheetStatus = "Pass";
	WebDriver driver;
	OpenWebsite openWebsite;
	
	public PLUValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.PLUPageLocators = new PLULocators(driver);
		System.out.println("PLU process started");
	}
	
	public String start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "courseTitle":
					courseTitle(row.get(1));
					break;
				case "topBannerContent":
					topBannerContent(row.get(1));
					break;
				case "description":
					description(row.get(1));
					break;
				case "techPrograms":
					techPrograms(row);
					break;
				case "PLUCourses":
					PLUCourses(row);
					break;
				case "FAQ":
					FAQ(row, i);
					break;
				case "BackGroundColor_certificateLeftContent":
				BackGroundColor_certificateLeftContent(row);
				break;
			}
		}
		return sheetStatus;
	}
	
	public void courseTitle(String titleName)
	{
		String titleStatus = this.PLUPageLocators.verifyTitle(titleName);
		if(!titleStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("PLU").get(0).set(0, "courseTitle - failed");
		}
	}
	public void topBannerContent(String bannerData)
	{
		String bannerStatus = this.PLUPageLocators.verifyBanner(bannerData);
		if(!bannerStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("PLU").get(1).set(0, "topBannerContent - failed");
		}

	}
	public void description(String data)
	{
		String descriptionStatus = this.PLUPageLocators.verifyDescription(data);
		if(!descriptionStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("PLU").get(2).set(0, "description - failed");
		}
	}
	public void techPrograms(ArrayList<String> programs)
	{
		ArrayList<String> failTechPgm = this.PLUPageLocators.verifyPrograms(programs);
		if(failTechPgm.size()>0)
		{
			for(int i = 0; i < failTechPgm.size(); i++)
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Pacific").get(3).add(i+1, (failTechPgm.get(i) + " - failed"));
			}
		}
	}
	public void PLUCourses(ArrayList<String> courses)
	{
		ArrayList<String> failedUrls = this.PLUPageLocators.verifyPLUCourse(courses);
		for(int i = 0; i < failedUrls.size(); i++)
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Pacific").get(4).add(i+1, (failedUrls.get(i) + " - failed"));
		}
	}
	public void FAQ(ArrayList<String> faq, int rowIndex)
	{
		try
		{
			ArrayList<String> status = this.PLUPageLocators.verifyFAQ(faq);
			int i = -1;
			if(status.contains("questionFail"))
			{
				sheetStatus = "Fail";
				i = 1;
			}
			else if(status.contains("answerFail"))
			{
				sheetStatus = "Fail";
				i = 2;
			}
			if( i > -1 )
			{
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Pacific").get(rowIndex).get(i);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Pacific").get(rowIndex).set(i, (cellValue + " - failed"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void BackGroundColor_certificateLeftContent(ArrayList<String> data)
	{
		ArrayList<String> status = this.PLUPageLocators.verfyCss(data);
		if(status.contains("fail"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("Pacific").get(10).set(0, "BackGroundColor_certificateLeftContent - failed");
		}
	}
}
