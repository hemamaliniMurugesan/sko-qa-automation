package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class SearchPageValidation
{
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	SearchPageLocator searchPageLocator;
	String sheetStatus = "Pass";
	public SearchPageValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver)
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.searchPageLocator = new SearchPageLocator(driver);
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
				case "validDataSearch":
					validDataSearch(row);
					break;
				case "invalidDataSearch":
					invalidDataSearch(row);
					break;
				case "emptySeach":
					emptySeach(row.get(1));
					break;
			}
		}
		return sheetStatus;
	}
	
	public void validDataSearch(ArrayList<String> dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = searchPageLocator.validDataSearchProcess(dataFromExcel);
			if(getStatus.contains("fail"))
			{sheetStatus="Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SearchProcess").get(0).set(0, "validDataSearch - failed");
			}
		}
	}
	
	public void invalidDataSearch(ArrayList<String> dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = searchPageLocator.invalidDataSearchProcess(dataFromExcel);
			if(getStatus.contains("fail"))
			{sheetStatus="Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SearchProcess").get(1).set(0, "invalidDataSearch - failed");
			}
		}
	}
	
	public void emptySeach(String dataFromExcel)
	{
		if(!dataFromExcel.contains("NA"))
		{
			ArrayList<String> getStatus = searchPageLocator.emptySearchProcess(dataFromExcel);
			if(getStatus.contains(""))
			{sheetStatus="Fail";
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SearchProcess").get(2).set(0, "emptySeach - failed");
			}
		}
	}
}
