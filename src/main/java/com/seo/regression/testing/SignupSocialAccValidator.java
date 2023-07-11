package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class SignupSocialAccValidator
{
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	SignupSocialAccLocator signupSocialAccLocator;
	String sheetStatus="Pass";
	public SignupSocialAccValidator(ArrayList<ArrayList<String>> sheetData, WebDriver driver)
	{
		OpenWebsite.openSite(driver);
		this.sheetData = sheetData;
		this.driver = driver;
		this.signupSocialAccLocator = new SignupSocialAccLocator(driver);
		//this.start();
	}
	
	public String start()
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "FACEBOOK":
					verifyFACEBOOK(row);
					break;
				case "GOOGLE":
					verifyGOOGLE(row);
					break;
				case "LINKEDIN":
					verifyLINKEDIN(row);
					break;
				case "MICROSOFT":
					verifyMICROSOFT(row);
					break;
			}
		}
		return sheetStatus;
	}
	
	public void verifyFACEBOOK(ArrayList<String> dataFromExcel)
	{
		if(dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = new ArrayList<String>();
			getStatus = signupSocialAccLocator.checkFACEBOOK(dataFromExcel);
			for(int i = 0; i < getStatus.size(); i++)
			{
				if(getStatus.get(0).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(1, (cellValue + " - failed"));
				}
				if(getStatus.get(1).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(2, (cellValue + " - failed"));

				}
				if(getStatus.get(2).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(3, (cellValue + " - failed"));

				}
				if(getStatus.get(3).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(4);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(4, (cellValue + " - failed"));

				}
				if(getStatus.get(4).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(5, (cellValue + " - failed"));

				}
				if(getStatus.get(5).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(6);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(6, (cellValue + " - failed"));

				}
				if(getStatus.get(6).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).get(7);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(0).set(7, (cellValue + " - failed"));
				}
			}
		}
	}
	public void verifyGOOGLE(ArrayList<String> dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = new ArrayList<String>();
			getStatus = signupSocialAccLocator.checkGOOGLE(dataFromExcel);
			for(int i = 0; i < getStatus.size(); i++)
			{
				if(getStatus.get(0).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(1, (cellValue + " - failed"));
				}
				if(getStatus.get(1).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(2, (cellValue + " - failed"));

				}
				if(getStatus.get(2).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(3, (cellValue + " - failed"));

				}
				if(getStatus.get(3).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(4);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(4, (cellValue + " - failed"));

				}
				if(getStatus.get(4).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(5, (cellValue + " - failed"));

				}
				if(getStatus.get(5).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(6);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(6, (cellValue + " - failed"));

				}
				if(getStatus.get(6).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).get(7);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(1).set(7, (cellValue + " - failed"));
				}
			}
		}
	}
	public void verifyLINKEDIN(ArrayList<String> dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = new ArrayList<String>();
			getStatus = signupSocialAccLocator.checkLINKEDIN(dataFromExcel);
			for(int i = 0; i < getStatus.size(); i++)
			{
				if(getStatus.get(0).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(1, (cellValue + " - failed"));
				}
				if(getStatus.get(1).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(2, (cellValue + " - failed"));

				}
				if(getStatus.get(2).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(3, (cellValue + " - failed"));

				}
				if(getStatus.get(3).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(4);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(4, (cellValue + " - failed"));

				}
				if(getStatus.get(4).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(5, (cellValue + " - failed"));

				}
				if(getStatus.get(5).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(6);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(6, (cellValue + " - failed"));

				}
				if(getStatus.get(6).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(7);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(7, (cellValue + " - failed"));

				}
			}	
		}
	}
	public void verifyMICROSOFT(ArrayList<String> dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = new ArrayList<String>();
			getStatus = signupSocialAccLocator.checkMICROSOFT(dataFromExcel);
			for(int i = 0; i < getStatus.size(); i++)
			{
				if(getStatus.get(0).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(1);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(1, (cellValue + " - failed"));
				}
				if(getStatus.get(1).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(2);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(2, (cellValue + " - failed"));

				}
				if(getStatus.get(2).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(3);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(3, (cellValue + " - failed"));

				}
				if(getStatus.get(3).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(4);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(4, (cellValue + " - failed"));

				}
				if(getStatus.get(4).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(5);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(5, (cellValue + " - failed"));

				}
				if(getStatus.get(5).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(6);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(6, (cellValue + " - failed"));

				}
				if(getStatus.get(6).contains("fail"))
				{sheetStatus="Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).get(7);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("GenericProcess").get(2).set(7, (cellValue + " - failed"));

				}
			}	
		}
	}
}
