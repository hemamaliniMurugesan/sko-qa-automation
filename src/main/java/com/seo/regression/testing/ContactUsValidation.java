package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class ContactUsValidation
{

	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	ContactUSLocator contactUSLocator;
	String sheetStatus = "Pass";
	public ContactUsValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		OpenWebsite.openSite(driver);
		this.sheetData = sheetData;
		this.driver = driver;
		this.contactUSLocator = new ContactUSLocator(driver);
		
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
				case "InvalidFullname":
					InvalidFullname(row);
					break;
				case "InvalidEmail":
					InvalidEmail(row);
					break;
				case "InvalidMobile":
					InvalidMobile(row);
					break;
				case"WithoutData":
					WithoutData(row);
					break;
				case "WithoutContactAbout":
					WithoutContactAbout(row);
					break;
				case"WithoutFullname":
					WithoutFullname(row);
					break;
				case"WithoutEmail":
					WithoutEmail(row);
					break;
				case"WithoutMobile":
					WithoutMobile(row);
					break;
				case"WithoutSkills":
					WithoutSkills(row);
					break;
				case"ValidData":
					ValidData(row);
					break;
			}
		}
		return sheetStatus;
	}
	
	public void InvalidFullname(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> fullnameValidation = contactUSLocator.checkInvalidFullname(dataFromExcel);
			if(fullnameValidation.contains("contacting us"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!fullnameValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(fullnameValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(fullnameValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(fullnameValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(0).set(0, "InvalidFullname - failed");
			}
		}
	public void InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> emailValidation = contactUSLocator.checkInvalidEmail(dataFromExcel);
			if(emailValidation.contains("contacting us"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(emailValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!emailValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(emailValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(emailValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
		}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(1).set(0, "InvalidEmail - failed");
			}
		}
	public void InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> mobileValidation = contactUSLocator.checkInvalidMobile(dataFromExcel);
				if(mobileValidation.contains("contacting us"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(1, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(mobileValidation.contains("full name"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(2, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(mobileValidation.contains("email"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(3, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(!mobileValidation.contains("contact"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(5, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(mobileValidation.contains("skills"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).get(8);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(8, (cellValue + " - failed"));
					status.add("Failed");
				}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(2).set(0, "InvalidMobile - failed");
			}
		}
	public void WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutDataValidation = contactUSLocator.checkWithoutData(dataFromExcel);
			if(WithoutDataValidation.contains("value"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutDataValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutDataValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutDataValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutDataValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(3).set(0, "WithoutData - failed");
			}
		}
	public void WithoutContactAbout(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutContactAboutValidation = contactUSLocator.checkWithoutContactAbout(dataFromExcel);
				
			if(WithoutContactAboutValidation.contains("value"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutContactAboutValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutContactAboutValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutContactAboutValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutContactAboutValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(4).set(0, "WithoutContactAbout - failed");
			}
		}
	public void WithoutFullname(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutFullnameValidation = contactUSLocator.checkWithoutFullname(dataFromExcel);
			if(WithoutFullnameValidation.contains("value"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutFullnameValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutFullnameValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutFullnameValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutFullnameValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(5).set(0, "WithoutFullname - failed");
			}
		}
	public void WithoutEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutEmailValidation = contactUSLocator.checkWithoutEmail(dataFromExcel);
			if(WithoutEmailValidation.contains("value"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutEmailValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutEmailValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutEmailValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutEmailValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(6).set(0, "WithoutEmail - failed");
			}
		}
	public void WithoutMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutMobileValidation = contactUSLocator.checkWithoutMobile(dataFromExcel);
			if(WithoutMobileValidation.contains("value"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutMobileValidation.contains("full name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutMobileValidation.contains("email"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).get(3);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(3, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!WithoutMobileValidation.contains("contact"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(WithoutMobileValidation.contains("skills"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).get(8);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(8, (cellValue + " - failed"));
				status.add("Failed");
			}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(7).set(0, "WithoutMobile - failed");
			}
		}
	public void WithoutSkills(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> WithoutSkillsValidation = contactUSLocator.checkWithoutSkills(dataFromExcel);
			for(int i = 0; i < WithoutSkillsValidation.size(); i++)
				if(WithoutSkillsValidation.contains("value"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(1, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(WithoutSkillsValidation.contains("full name"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(2, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(WithoutSkillsValidation.contains("email"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(3, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(WithoutSkillsValidation.contains("contact"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(5, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(!WithoutSkillsValidation.contains("skills"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).get(8);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(8, (cellValue + " - failed"));
					status.add("Failed");
				}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(8).set(0, "WithoutSkills - failed");
			}
		}
	public void ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> ValidDataValidation = contactUSLocator.checkValidData(dataFromExcel);
				if(ValidDataValidation.contains("value"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(1, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(ValidDataValidation.contains("full name"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(2, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(ValidDataValidation.contains("email"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(3, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(ValidDataValidation.contains("contact"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(5, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(ValidDataValidation.contains("skills"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).get(8);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(8, (cellValue + " - failed"));
					status.add("Failed");
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactUSForm").get(9).set(0, "ValidData - failed");
			}
		}
	}
	

