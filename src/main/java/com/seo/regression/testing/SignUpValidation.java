package com.seo.regression.testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class SignUpValidation
{
	String result = "failed";
	ArrayList<ArrayList<String>> sheetData = null;
	String userName;
	WebDriver driver;
	SignUpLocator signUpLocator;
	String sheetStatus = "Pass";
	
	public SignUpValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver) throws InterruptedException
	{
		this.driver = driver;
		OpenWebsite.openSite(driver);
		this.sheetData = sheetData;
		this.signUpLocator = new SignUpLocator(this.driver);
		System.out.println("Sign up validation begins");
	//	this.start();
	}
	
	public String start() throws InterruptedException
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
			  case "invalidFullname": 
				  invalidFullname(row); 
				  break; 
			  case "invalidEmail":
				  emailValidation(row); 
				  break; 
			  case "invalidPassword": 
				  passwordValidation(row);
				  break; 
				/*
				 * case "invalidMobile": mobileValidation(row); break;
				 */
			  case "validData":
					validData(row);
					break;
			  case "addUser":
					addUser(row, i);
					break;
					
			}
		}
		return sheetStatus;
	}
	
	
	public void invalidFullname(ArrayList<String> dataFromExcel)
	{
		ArrayList<Integer> fieldValidationValue;
		try
		{
			ArrayList<Integer> verifyFullName = signUpLocator.checkFullName(sheetData.get(0));
			fieldValidationValue = verifyFullName;
			ArrayList<String> status = new ArrayList<String>();
			for(int i = 0; i < fieldValidationValue.size(); i++)
			{
				if(i == 0)
				{
					if(!fieldValidationValue.get(i).equals(1))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 1)
				{
					if(fieldValidationValue.get(i).equals(2))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 2)
				{
					if(fieldValidationValue.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(3);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(3, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 3)
				{
					if(fieldValidationValue.get(i).equals(4))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 4)
				{
					if(fieldValidationValue.get(i).equals(5))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(status.contains("Failed"))
				{
					sheetStatus = "Fail";
					String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).get(0);
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(0).set(0, (cellValue + " - failed"));
				}
				 
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void emailValidation(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			ArrayList<Integer> verifyEmail = signUpLocator.checkEmail(sheetData.get(1));
			for(int i = 0; i < verifyEmail.size(); i++)
			{
				if(i == 0)
				{
					if(verifyEmail.get(i).equals(1))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 1)
				{
					if(!verifyEmail.get(i).equals(2))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(i == 2)
				{
					if(verifyEmail.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).get(3);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).set(3, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				if(status.contains("Failed"))
				{
					sheetStatus = "Fail";
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(1).set(0, "invalidEmail - failed"); 
				}
				 
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void passwordValidation(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			ArrayList<Integer> verifyPassword = signUpLocator.checkPassword(sheetData.get(2));
			for(int i = 0; i < verifyPassword.size(); i++)
			{
				if(i == 0)
				{
					if(verifyPassword.get(i).equals(1))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 1)
				{
					if(verifyPassword.get(i).equals(2))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 2)
				{
					if(!verifyPassword.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).get(3);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(3, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 3)
				{
					if(verifyPassword.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 4)
				{
					if(verifyPassword.get(i).equals(4))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}	
				}
				if(status.contains("Failed"))
				{
					sheetStatus = "Fail";
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(2).set(0, "invalidPassword - failed"); 
				}
				 
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void mobileValidation(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			ArrayList<Integer> verifyMobileField = signUpLocator.checkMobileNumber(sheetData.get(3));
			for(int i = 0; i < verifyMobileField.size(); i++)
			{
				if(i == 0)
				{
					if(verifyMobileField.get(i).equals(1))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(4).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 1)
				{
					if(verifyMobileField.get(i).equals(2))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(4).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 2)
				{
					if(verifyMobileField.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(4).get(3);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(3, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 3)
				{
					if(verifyMobileField.get(i).equals(4))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(4).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
				}
				else if(i == 4)
				{
					if(!verifyMobileField.get(i).equals(4))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(4).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}	
				}
				if(status.contains("Failed"))
				{sheetStatus = "Fail";
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(3).set(0, "invalidMobileNumber - failed"); 
				}
				 
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void verifyInsertedData(ArrayList<String> data, int index)
	{
		try
		{
			ArrayList<String> status = new ArrayList<String>();
			ArrayList<Integer> verifyValidData = signUpLocator.checkSignupWithValidData(data);
			for(int i = 0; i < verifyValidData.size(); i++)
			{
					if(verifyValidData.get(i).equals(1))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).get(1);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(1, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(verifyValidData.get(i).equals(2))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).get(2);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(2, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(verifyValidData.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).get(3);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(3, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(verifyValidData.get(i).equals(3))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).get(4);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(4, (cellValue + " - failed"));
						status.add("Failed");
					}
					else if(verifyValidData.get(i).equals(4))
					{
						sheetStatus = "Fail";
						String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).get(5);
						RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(5, (cellValue + " - failed"));
						status.add("Failed");
					}	
				}
				if(status.contains("Failed"))
				{
					sheetStatus = "Fail";
					RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("SignUp").get(index).set(0, "validData - failed"); 
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void validData(ArrayList<String> dataFromExcel)
	{
		this.verifyInsertedData(sheetData.get(3), 3);
	}
	
	public void addUser(ArrayList<String> dataFromExcel, int index)
	{
		this.verifyInsertedData(dataFromExcel, index);
	}
}
