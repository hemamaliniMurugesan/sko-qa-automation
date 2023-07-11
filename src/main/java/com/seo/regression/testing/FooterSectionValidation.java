package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class FooterSectionValidation
{
	WebDriver driver;
	ArrayList<ArrayList<String>> sheetData = null;
	FooterSectionLocator footerSectionLocator;
	String sheetStatus = "Pass";
	public FooterSectionValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.footerSectionLocator = new FooterSectionLocator(driver);
		System.out.println("footer section process started");
		//this.start();
	}
	
	public String start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "skillupLogo":
					checkSkillupLogo();
					break;
				case "twitter":
					twitter();
					break;
				case "facebook":
					facebook();
					break;
				case "linkedIn":
					linkedIn();
					break;
				case "instagram":
					instagram();
					break;
				case "contactUs":
					contactUs();
					break;
				case "aboutSkillupOnline":
					aboutSkillupOnline();
					break;
				case "business":
					business();
					break;
				case "faq":
					faq();
					break;
				case "privacyPolicy":
					privacyPolicy();
					break;
				case "termsOfservice":
					verifyTermsofService();
					break;
				case "blog":
					verifyBlog();
					break;
				case "popularCategories":
					verifyPopularCategories(row);
					break;
				case "popularCourses":
					verifyPopularCourses(row);
					break;
				case "latestBlogs":
					verifyLatestBlogs(row);
					break;
			}
		}
		return sheetStatus;
	}
	String footerSectionURL ="success";
	
	public void checkSkillupLogo() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifySkillupLogo();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(0).set(0, "skillupLogo - failed");
		}
	}
	
	public void twitter() throws InterruptedException
	{
		String status = "failed";
		String twitterProcess = footerSectionLocator.verifyTwitter();
		if(twitterProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(1).set(0, "twitter - failed");
		}
	}
	public void facebook() throws InterruptedException
	{
		String status = "failed";
		String facebookProcess = footerSectionLocator.verifyFacebook();
		if(facebookProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(2).set(0, "facebook - failed");

		}
	}
	public void instagram() throws InterruptedException
	{
		String status = "failed";
		String instagramProcess = footerSectionLocator.verifyInstagram();
		if(instagramProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(3).set(0, "instagram - failed");

		}
	}
	public void linkedIn() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyLinkedIn();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(4).set(0, "linkedIn - failed");

		}
	}
	
	public void contactUs() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyContactUs();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(5).set(0, "contactUs - failed");

		}
	}
	public void aboutSkillupOnline() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyAboutSkillupOnline();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(6).set(0, "aboutSkillupOnline - failed");

		}
	}
	public void business() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyBusiness();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(7).set(0, "business - failed");

		}
	}
	public void faq() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyFaq();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(8).set(0, "faq - failed");

		}
	}
	public void privacyPolicy() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyPrivacyPolicy();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(9).set(0, "privacyPolicy - failed");

		}
	}
	public void verifyTermsofService() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyTermsofService();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(10).set(0, "termsOfservice - failed");
		}
	}
	public void verifyBlog() throws InterruptedException
	{
		String status = "failed";
		String skillupLogoProcess = footerSectionLocator.verifyBlog();
		if(skillupLogoProcess.equalsIgnoreCase(status))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(11).set(0, "blog - failed");
		}
	}
	public void verifyPopularCategories(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> categories = footerSectionLocator.verifyPopularCategories(data);
		for(int i = 0; i < categories.size(); i++)
		{
			if(data.contains(categories.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(categories.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(12).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(12).set(position, (cellValue + " - failed"));
			}
		}
	}
	
	public void verifyPopularCourses(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> popularCoursesStatus = footerSectionLocator.verifyPopularCourses(data);
		for(int i = 0; i < popularCoursesStatus.size(); i++)
		{
			if(data.contains(popularCoursesStatus.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(popularCoursesStatus.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(13).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(13).set(position, (cellValue + " - failed"));
			}
		}
	}
	
	public void verifyLatestBlogs(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> latestBlogsStatus = footerSectionLocator.verifyLatestBlogs(data);
		for(int i = 0; i < latestBlogsStatus.size(); i++)
		{
			if(data.contains(latestBlogsStatus.get(i)))
			{
				sheetStatus = "Fail";
				int position = data.indexOf(latestBlogsStatus.get(i));
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(14).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("FooterSection").get(14).set(position, (cellValue + " - failed"));
			}
		}
	}
}
