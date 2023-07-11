package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class ContactInfoValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	ContactInfoLocator contactInfoLocator;
	String sheetStatus = "Pass";
	WebDriver driver;
	public ContactInfoValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.contactInfoLocator = new ContactInfoLocator(driver);
		System.out.println("contact information process started");
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
				case "individual_InvalidName":
					verifyIndividual_InvalidName(row);
					break;
				case "individual_InvalidEmail":
					verifyIndividual_InvalidEmail(row); 
					break;
				case "individual_InvalidMobile": 
					verifyIndividual_InvalidMobile(row); 
					break; 
				case "individual_WithoutSkill": 
					verifyIndividual_WithoutSkill(row); 
					break;
				case "individual_WithoutData": 
					verifyIndividual_WithoutData(row); 
					break;
				case "individual_ValidData": 
					verifyIndividual_ValidData(row); 
					break; 
				case "academic_InvalidName":
					verifyAcademic_InvalidName(row);
					break;
				case "academic_InvalidEmail":
					verifyAcademic_InvalidEmail(row); 
					break;
				case "academic_InvalidMobile": 
					verifyAcademic_InvalidMobile(row); 
					break; 
				case "academic_InvalidInstitution": 
					verifyAcademic_InvalidInstitution(row); 
					break;
				case "academic_WithoutJob": 
					verifyAcademic_WithoutJob(row); 
					break;
				case "academic_WithoutMessage": 
					verifyAcademic_WithoutMessage(row); 
					break; 
				case "academic_WithoutData":
					verifyAcademic_WithoutData(row);
					break;
				case "academic_ValidData":
					verifyAcademic_ValidData(row);
					break;
				case "business_InvalidName":
					verifyBusiness_InvalidName(row);
					break;
				case "business_InvalidEmail":
					verifyBusiness_InvalidEmail(row); 
					break;
				case "business_InvalidMobile": 
					verifyBusiness_InvalidMobile(row); 
					break; 
				case "business_WithoutOrganization": 
					verifyBusiness_WithoutOrganization(row); 
					break;
				case "business_WithoutJob": 
					verifyBusiness_WithoutJob(row); 
					break;
				case "business_WithoutMessage": 
					verifyBusiness_WithoutMessage(row); 
					break; 
				case "business_WithoutData":
					verifyBusiness_WithoutData(row);
					break;
				case "business_ValidData":
					verifyBusiness_ValidData(row);
					break;
			}
		}
		return sheetStatus;
	}
	
	public void verifyIndividual_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_InvalidName = contactInfoLocator.checkIndividual_InvalidName(dataFromExcel);
			for(int i = 0; i < Individual_InvalidName.size(); i++)
			{
					if(!Individual_InvalidName.get(i).contains("name"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidName.get(i).contains("email"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidName.get(i).contains("contact"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidName.get(i).contains("current"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidName.get(i).contains("skills"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidName.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			}
			if(status.contains("Failed"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(0).set(0, "individual_InvalidName - failed");
			}
	}
	public void verifyIndividual_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_InvalidEmail = contactInfoLocator.checkIndividual_InvalidEmail(dataFromExcel);
			for(int i = 0; i < Individual_InvalidEmail.size(); i++)
			{
					if(Individual_InvalidEmail.get(i).contains("name"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_InvalidEmail.get(i).contains("email"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidEmail.get(i).contains("contact"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidEmail.get(i).contains("current"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidEmail.get(i).contains("skills"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidEmail.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			}
			if(status.contains("Failed"))
			{
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(1).set(0, "individual_InvalidEmail - failed");
			}
		}
	public void verifyIndividual_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_InvalidMobile = contactInfoLocator.checkIndividual_InvalidMobile(dataFromExcel);
			for(int i = 0; i < Individual_InvalidMobile.size(); i++)
			{
					if(Individual_InvalidMobile.get(i).contains("name"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidMobile.get(i).contains("email"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_InvalidMobile.get(i).contains("contact"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidMobile.get(i).contains("current"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidMobile.get(i).contains("skills"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_InvalidMobile.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			}
			if(status.contains("Failed"))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(2).set(0, "individual_InvalidMobile - failed");
			}
		}
	public void verifyIndividual_WithoutSkill(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_WithoutSkill = contactInfoLocator.checkIndividual_WithoutSkill(dataFromExcel);
			for(int i = 0; i < Individual_WithoutSkill.size(); i++)
			{
					if(Individual_WithoutSkill.get(i).contains("name"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_WithoutSkill.get(i).contains("email"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_WithoutSkill.get(i).contains("contact"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_WithoutSkill.get(i).contains("current"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_WithoutSkill.get(i).contains("skills"))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Individual_WithoutSkill.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("fail");
					}
			}
			if((status.contains("Failed"))||(status.contains("fail")))
			{
				sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(3).set(0, "individual_WithoutSkill - failed");
			}
		}
	}
	public void verifyIndividual_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_WithoutData = contactInfoLocator.checkIndividual_WithoutData(dataFromExcel);
				if(!Individual_WithoutData.contains("full name"))
				{
					sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(1, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(!Individual_WithoutData.contains("email address"))
				{
					sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(2, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(!Individual_WithoutData.contains("contact number"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).get(4);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(4, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(Individual_WithoutData.contains("current status"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(5, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(!Individual_WithoutData.contains("skills"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).get(6);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(6, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(Individual_WithoutData.contains("fail"))
				{
					sheetStatus = "Fail";
					status.add("fail");
				}
			}
		
		if(status.contains("Failed"))
		{
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(4).set(0, "individual_WithoutData - failed");
		}
	}
	public void verifyIndividual_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Individual_WithoutData = contactInfoLocator.checkIndividual_ValidData(dataFromExcel);
			for(int i = 0; i < Individual_WithoutData.size(); i++)
			{
				if(Individual_WithoutData.size()==1)
				{
					if(Individual_WithoutData.get(i).equalsIgnoreCase("no msg"))
					{
						System.out.println("no error message for valid data");
					}
				}
				else
				{
					if(!Individual_WithoutData.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_WithoutData.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_WithoutData.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_WithoutData.get(i).contains("current"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Individual_WithoutData.get(i).contains("skills"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(5).set(0, "individual_WithoutData - failed");
			}
		}
	public void verifyAcademic_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_InvalidName = contactInfoLocator.checkAcademic_InvalidName(dataFromExcel);
			for(int i = 0; i < Academic_InvalidName.size(); i++)
			{
					if(!Academic_InvalidName.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidName.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("fail");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(6).set(0, "academic_InvalidName - failed");
			}
		}
	public void verifyAcademic_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_InvalidEmail = contactInfoLocator.checkAcademic_InvalidEmail(dataFromExcel);
			for(int i = 0; i < Academic_InvalidEmail.size(); i++)
			{
					if(Academic_InvalidEmail.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_InvalidEmail.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidEmail.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidEmail.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidEmail.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidEmail.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidEmail.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("fail");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(7).set(0, "academic_InvalidEmail - failed");
			}
		}
	public void verifyAcademic_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_InvalidMobile = contactInfoLocator.checkAcademic_InvalidMobile(dataFromExcel);
			for(int i = 0; i < Academic_InvalidMobile.size(); i++)
			{
					if(Academic_InvalidMobile.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidMobile.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_InvalidMobile.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidMobile.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidMobile.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidMobile.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidMobile.get(i).contains("fail"))
					{sheetStatus = "Fail";
					status.add("fail");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(8).set(0, "academic_InvalidMobile - failed");
			}
		}
	public void verifyAcademic_InvalidInstitution(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_InvalidInstitution = contactInfoLocator.checkAcademic_InvalidInstitution(dataFromExcel);
			for(int i = 0; i < Academic_InvalidInstitution.size(); i++)
			{
					if(Academic_InvalidInstitution.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidInstitution.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidInstitution.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_InvalidInstitution.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidInstitution.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidInstitution.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_InvalidInstitution.get(i).contains("fail"))
					{sheetStatus = "Fail";
					status.add("fail");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(9).set(0, "academic_InvalidInstitution - failed");
			}
		}
	public void verifyAcademic_WithoutJob(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_WithoutJob = contactInfoLocator.checkAcademic_WithoutJob(dataFromExcel);
			for(int i = 0; i < Academic_WithoutJob.size(); i++)
			{
					if(Academic_WithoutJob.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(Academic_WithoutJob.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(Academic_WithoutJob.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(Academic_WithoutJob.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(!Academic_WithoutJob.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(Academic_WithoutJob.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
				else if(Academic_WithoutJob.get(i).contains("fail"))
				{sheetStatus = "Fail";
				status.add("fail");
				}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(10).set(0, "academic_WithoutJob - failed");
			}
		}
	public void verifyAcademic_WithoutMessage(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_WithoutMessage = contactInfoLocator.checkAcademic_WithoutMessage(dataFromExcel);
			for(int i = 0; i < Academic_WithoutMessage.size(); i++)
			{
					if(Academic_WithoutMessage.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_WithoutMessage.get(i).contains("fail"))
					{sheetStatus = "Fail";
					status.add("fail");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(11).set(0, "academic_WithoutMessage - failed");
			}
		}
	public void verifyAcademic_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_WithoutData = contactInfoLocator.checkAcademic_WithoutData(dataFromExcel);
				
				if(!Academic_WithoutData.contains("full name"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(1, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(!Academic_WithoutData.contains("email address"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(2, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(!Academic_WithoutData.contains("contact number"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).get(4);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(4, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(!Academic_WithoutData.contains("university Name"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(5, (cellValue + " - failed"));
				status.add("Failed");
				}
				if(!Academic_WithoutData.contains("job title"))
				{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).get(6);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(6, (cellValue + " - failed"));
				status.add("Failed");
				}
				else if(Academic_WithoutData.contains("fail"))
				{sheetStatus = "Fail";
				status.add("fail");
				}
			}
			if(status.contains("Failed"))
			{	sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(12).set(0, "academic_WithoutData - failed");
			}
		}
	public void verifyAcademic_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Academic_ValidData = contactInfoLocator.checkAcademic_ValidData(dataFromExcel);
			for(int i = 0; i < Academic_ValidData.size(); i++)
			{
				if(Academic_ValidData.size()==1)
				{
					if(Academic_ValidData.get(i).equalsIgnoreCase("no msg"))
					{
						System.out.println("no error msg for valid data");
					}
				}
				else
				{
					if(!Academic_ValidData.get(i).contains("name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_ValidData.get(i).contains("email"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_ValidData.get(i).contains("contact"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_ValidData.get(i).contains("university"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_ValidData.get(i).contains("job"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(!Academic_ValidData.get(i).contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Academic_ValidData.get(i).contains("fail"))
					{sheetStatus = "Fail";
					status.add("fail");
					}
				}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(13).set(0, "academic_ValidData - failed");
			}
		}
	
	public void verifyBusiness_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_InvalidName = contactInfoLocator.checkBusiness_InvalidName(dataFromExcel);
			for(int i = 0; i < Business_InvalidName.size(); i++)
			{
					if(!Business_InvalidName.contains("full name"))
					{	sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidName.contains("email address"))
					{	sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidName.contains("contact number"))
					{	sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidName.contains("organization name"))
					{	sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidName.contains("job title"))
					{	sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidName.contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(0, "business_InvalidName - failed");
			}
		}
	public void verifyBusiness_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_InvalidEmail = contactInfoLocator.checkBusiness_InvalidEmail(dataFromExcel);
			if(Business_InvalidEmail.contains("full name"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(!Business_InvalidEmail.contains("email address"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_InvalidEmail.contains("contact number"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).get(4);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).set(4, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_InvalidEmail.contains("organization name"))
			{sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_InvalidEmail.contains("job title"))
			{
				sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).get(6);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(14).set(6, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_InvalidEmail.contains("fail"))
			{
				sheetStatus = "Fail";
				status.add("Failed");
			}
		}
		if(status.contains("Failed"))
		{	sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(15).set(0, "business_InvalidEmail - failed");
		}
		}
	public void verifyBusiness_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_InvalidMobile = contactInfoLocator.checkBusiness_InvalidMobile(dataFromExcel);
					if(Business_InvalidMobile.contains("full name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidMobile.contains("email address"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_InvalidMobile.contains("contact number"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidMobile.contains("organization name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidMobile.contains("job title"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidMobile.contains("message"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).get(7);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(7, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_InvalidMobile.contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(16).set(0, "business_InvalidMobile - failed");
			}
		}
	public void verifyBusiness_WithoutOrganization(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_WithoutOrganization = contactInfoLocator.checkBusiness_WithoutOrganization(dataFromExcel);
				if(Business_WithoutOrganization.contains("full name"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(1, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(Business_WithoutOrganization.contains("email address"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(2, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(Business_WithoutOrganization.contains("contact number"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).get(4);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(4, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(!Business_WithoutOrganization.contains("organization name"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(5, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(Business_WithoutOrganization.contains("job title"))
				{sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).get(6);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(6, (cellValue + " - failed"));
					status.add("Failed");
				}
				if(Business_WithoutOrganization.contains("fail"))
				{
					sheetStatus = "Fail";
					status.add("Failed");
				}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(17).set(0, "business_WithoutOrganization - failed");
			}
		}
	public void verifyBusiness_WithoutJob(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_WithoutJob = contactInfoLocator.checkBusiness_WithoutJob(dataFromExcel);
					if(Business_WithoutJob.contains("full name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_WithoutJob.contains("email address"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_WithoutJob.contains("contact number"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_WithoutJob.contains("organization name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_WithoutJob.contains("job title"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_WithoutJob.contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(18).set(0, "business_WithoutJob - failed");
			}
		}
	public void verifyBusiness_WithoutMessage(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_WithoutMessage = contactInfoLocator.checkBusiness_WithoutMessage(dataFromExcel);
			if(Business_WithoutMessage.contains("full name"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(1);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(1, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("email address"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(2);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(2, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("contact number"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(4);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(4, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("organization name"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(5);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(5, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("job title"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(6);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(6, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("message"))
			{	sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).get(7);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(7, (cellValue + " - failed"));
				status.add("Failed");
			}
			if(Business_WithoutMessage.contains("fail"))
			{
				sheetStatus = "Fail";
				status.add("Failed");
			}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(19).set(0, "business_WithoutMessage - failed");
			}
		}
	public void verifyBusiness_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_WithoutData = contactInfoLocator.checkBusiness_WithoutData(dataFromExcel);
					if(!Business_WithoutData.contains("full name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_WithoutData.contains("email address"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_WithoutData.contains("contact number"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_WithoutData.contains("organization name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(!Business_WithoutData.contains("job title"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(Business_WithoutData.contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).set(0, "business_WithoutData - failed");
			}
		}
	public void verifyBusiness_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> Business_ValidData = contactInfoLocator.checkBusiness_ValidData(dataFromExcel);
			for(int i = 0; i < Business_ValidData.size(); i++)
			{
				if(Business_ValidData.size() == 1)
				{
					if(Business_ValidData.get(i).equalsIgnoreCase("no msg"))
					{
						System.out.println("no error msg for valid data");
					}
				}
				else
				{
					if(Business_ValidData.get(i).contains("full name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_ValidData.get(i).contains("email address"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_ValidData.get(i).contains("contact number"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_ValidData.get(i).contains("organization name"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_ValidData.get(i).contains("job title"))
					{sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(20).get(6);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(6, (cellValue + " - failed"));
						status.add("Failed");
					}
					if(Business_ValidData.get(i).contains("fail"))
					{
						sheetStatus = "Fail";
						status.add("Failed");
					}
				}
			}
			
			}
			if(status.contains("Failed"))
			{sheetStatus = "Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("ContactInfo").get(21).set(0, "business_ValidData - failed");
			}
		}
	}
