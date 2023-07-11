package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class EditProfileValidation 
{
	ArrayList<ArrayList<String>> sheetData = null;
	WebDriver driver;
	EditProfileLocator editProfileLocator;
	String sheetStatus = "Pass";
	
	public EditProfileValidation(ArrayList<ArrayList<String>> sheetData, WebDriver driver)
	{
		this.sheetData = sheetData;
		this.driver = driver;
		OpenWebsite.openSite(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		this.editProfileLocator = new EditProfileLocator(driver);
		System.out.println("Edit profile Process started");
	}
	
	public String start()
	{
		for(int i = 0; i < this.sheetData.size(); i++)
		{
			ArrayList<String> row = this.sheetData.get(i);
			String firstColumn = row.get(0);
			switch(firstColumn)
			{
				case "login":
					verifyLogin(row);
					break;
				case "ProfileIcon":
					verifyProfileIcon();
					break;
				case "contacts_updateIcon":
					//verifyUpdateIcon();
					break;
				case "contacts_submitWithoutData":
					//verifySubmitWithoutDataForMobile();
					break;
				case "contacts_submitInvalidData_mobile":
					//verifySubmitInvalidDataForMobile(row.get(1));
					break;
				case "contacts_cancelIcon":
					//verifyCancelIcon();
					break;
				case "contacts_Alert_close":
					//verifyContactsAlertClose();
					break;
				case "contacts_submitValidData_mobile":
					//verifySubmitValidDataForMobile(row.get(1));
					break;
				case "contacts_Alert_yesButton":
				//	verifyAlertYesButton();
					break;
				case "contacts_Alert_goBackButton":
					//verifyAlertGoBackButton();
					break;
				case "AreasOfInterest_updateIcon":
					//verifyAreasOfInterestUpdateIcon();
					break;
				case "AreasOfInterest_cancelIcon":
					//verifyAreasOfInterestCancelIcon();
					break;
				case "AreasOfInterest_Alert_close":
					//verifyAreasOfInterestAlertClose();
					break;
				case "AreasOfInterest_submitValidData":
					// verifyAreasOfInterestSubmitValidData(row);
					  break;
				case "AreasOfInterest_Alert_yesButton":
					//verifyAreasOfInterestAlertyesButton();
					break;
				case "AreasOfInterest_Alert_goBackButton":
				//	verifyAreasOfInterestAlertGoBackButton();
					break;
				case "CurrentWorkStatus_updateIcon":
					CurrentWorkStatus_updateIcon();
					break;
				case "CurrentWorkStatus_cancelIcon":
					CurrentWorkStatus_cancelIcon();
					break;
				case "CurrentWorkStatus_Alert_close":
					CurrentWorkStatus_Alert_close();
					break;	
				case "CurrentWorkStatus_submitValidData":
					CurrentWorkStatus_submitValidData(row);
					break;
				case "CurrentWorkStatus_Alert_yesButton":
					CurrentWorkStatus_Alert_yesButton();
					break;
				case "CurrentWorkStatus_Alert_goBackButton":
					CurrentWorkStatus_Alert_goBackButton();
					break;
				case "WorkExperience_updateIcon":
					WorkExperience_updateIcon();
					break;
				case "WorkExperience_cancelIcon":
					WorkExperience_cancelIcon();
					break;
				case "WorkExperience_Alert_close":
					WorkExperience_Alert_close();
					break;	
				case "WorkExperience_submitValidData":
					WorkExperience_submitValidData(row);
					break;
				case "WorkExperience_Alert_yesButton":
					WorkExperience_Alert_yesButton();
					break;
				case "WorkExperience_Alert_goBackButton":
					WorkExperience_Alert_goBackButton();
					break;
				case "PersonalDetails_updateIcon":
					verifyAlertGoBackButton();
					break;
				case "PersonalDetails_cancelIcon":
					verifyAlertGoBackButton();
					break;
				case "PersonalDetails_Alert_close":
					verifyAlertGoBackButton();
					break;	
				case "PersonalDetails_submitValidData":
					verifyAlertGoBackButton();
					break;
				case "PersonalDetails_Alert_yesButton":
					verifyAlertGoBackButton();
					break;
				case "PersonalDetails_Alert_goBackButton":
					verifyAlertGoBackButton();
					break;
				case "Education_updateIcon":
					//verifyAlertGoBackButton();
					break;
				case "Education_cancelIcon":
				//	verifyAlertGoBackButton();
					break;
				case "Education_Alert_close":
					//verifyAlertGoBackButton();
					break;	
				case "Education_submitValidData":
					//verifyAlertGoBackButton();
					break;
				case "Education_Alert_yesButton":
					//verifyAlertGoBackButton();
					break;
				case "Education_Alert_goBackButton":
					//verifyAlertGoBackButton();
					break;
			}
		}
		return sheetStatus;
	}
	
	public void verifyLogin(ArrayList<String> data)
	{
		ArrayList<String> getStatus = editProfileLocator.checkLogin(data);
		if(!getStatus.contains("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(0).set(0, "login - failed");
		}
	}
	public void verifyProfileIcon()
	{
		String getStatus = editProfileLocator.checkProfile();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(1).set(0, "ProfileIcon - failed");
		}
	}
	public void verifyUpdateIcon() throws InterruptedException
	{
		String getStatus = editProfileLocator.checkUpdateIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(2).set(0, "contacts_updateIcon - failed");
		}
		
	}
	public void verifySubmitWithoutDataForMobile() throws InterruptedException
	{
		String getStatus = editProfileLocator.checkSubmitWithoutDataForMobile();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(3).set(0, "contacts_submitWithoutData - failed");
		}
	}
	public void verifySubmitInvalidDataForMobile(String data) throws InterruptedException
	{
		String getStatus = editProfileLocator.checkSubmitInvalidDataForMobile(data);
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(4).set(0, "contacts_submitInvalidData_mobile - failed");
		}
	}
	public void verifyCancelIcon() throws InterruptedException
	{
		String getStatus = editProfileLocator.checkCancelIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(5).set(0, "contacts_cancelIcon - failed");
		}
	}
	public void verifyContactsAlertClose()
	{
		String getStatus =  editProfileLocator.checkContactsAlertClose();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(6).set(0, "contacts_Alert_close - failed");
		}
	}
	public void verifySubmitValidDataForMobile(String data) throws InterruptedException
	{
		String getStatus = editProfileLocator.checkSubmitValidDataForMobile(data);
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(7).set(0, "contacts_submitValidData_mobile - failed");
		}
	}
	public void verifyAlertYesButton() throws InterruptedException
	{
		String getStatus = editProfileLocator.checkAlertYesButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(8).set(0, "contacts_Alert_yesButton - failed");
		}
	}
	public void verifyAlertGoBackButton()
	{
		String getStatus = editProfileLocator.checkAlertGoBackButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(9).set(0, "contacts_Alert_goBackButton - failed");
		}
	}
	public void verifyAreasOfInterestUpdateIcon()
	{
		String getStatus = editProfileLocator.checkAreasOfInterestUpdateIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(10).set(0, "AreasOfInterest_updateIcon - failed");
		}
	}
	public void verifyAreasOfInterestCancelIcon()
	{
		String getStatus = editProfileLocator.checkAreasOfInterestCancelIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(11).set(0, "contacts_Alert_goBackButton - failed");
		}
	}
	public void verifyAreasOfInterestAlertClose()
	{
		String getStatus = editProfileLocator.checkAreasOfInterestAlertClose();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(12).set(0, "contacts_Alert_goBackButton - failed");
		}
	}
	public void verifyAreasOfInterestSubmitValidData(ArrayList<String> data)
	{
		ArrayList<String> getStatus = editProfileLocator.checkAreasOfInterestSubmitValidData(data);
		for(int i = 0; i < getStatus.size(); i++)
		{
			if(!data.contains(getStatus.get(i)))
			{
				int position = data.indexOf(getStatus.get(i));
				sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(13).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(13).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void verifyAreasOfInterestAlertGoBackButton()
	{
		String getStatus = editProfileLocator.checkAreasOfInterestAlertGoBackButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(14).set(0, "AreasOfInterest_Alert_yesButton - failed");
		}
	}
	public void verifyAreasOfInterestAlertyesButton()
	{
		String getStatus = editProfileLocator.checkAreasOfInterestAlertyesButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(15).set(0, "AreasOfInterest_Alert_goBackButton - failed");
		}
	}

	public void CurrentWorkStatus_updateIcon()
	{
		String getStatus = editProfileLocator.checkCurrentWorkUpdateIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(16).set(0, "CurrentWorkStatus_updateIcon - failed");
		}
	}
	public void CurrentWorkStatus_cancelIcon()
	{
		String getStatus = editProfileLocator.checkCurrentWorkCancelIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(17).set(0, "CurrentWorkStatus_cancelIcon - failed");
		}
	}
	public void CurrentWorkStatus_Alert_close()
	{
		String getStatus = editProfileLocator.checkCurrentWorkAlertClose();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(18).set(0, "CurrentWorkStatus_Alert_close - failed");
		}
	}
	public void CurrentWorkStatus_submitValidData(ArrayList<String> data)
	{
		ArrayList<String> getStatus = editProfileLocator.checkCurrentWorkSubmitValidData(data);
		for(int i = 0; i < getStatus.size(); i++)
		{
			if(!data.contains(getStatus.get(i)))
			{
				int position = data.indexOf(getStatus.get(i));
				sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(19).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(19).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void CurrentWorkStatus_Alert_goBackButton()
	{
		String getStatus = editProfileLocator.checkCurrentWorkAlertGoBackButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(21).set(0, "CurrentWorkStatus_Alert_goBackButton - failed");
		}
	}
	public void CurrentWorkStatus_Alert_yesButton()
	{
		String getStatus = editProfileLocator.checkCurrentWorkAlertYesButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(20).set(0, "checkCurrentWorkAlertYesButton - failed");
		}
	}
	public void WorkExperience_updateIcon()
	{
		String getStatus = editProfileLocator.checkworkExperienceUpdateIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(22).set(0, "WorkExperience_updateIcon - failed");
		}
	}
	public void WorkExperience_cancelIcon()
	{
		String getStatus = editProfileLocator.checkworkExperienceCancelIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(23).set(0, "WorkExperience_cancelIcon - failed");
		}
	}
	public void WorkExperience_Alert_close()
	{
		String getStatus = editProfileLocator.checkworkExperienceAlertClose();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(24).set(0, "WorkExperience_Alert_close - failed");
		}
	}
	public void WorkExperience_submitValidData(ArrayList<String> data)
	{
		ArrayList<String> getStatus = editProfileLocator.checkworkExperienceSubmitValidData(data);
		for(int i = 0; i < getStatus.size(); i++)
		{
			if(!data.contains(getStatus.get(i)))
			{
				int position = data.indexOf(getStatus.get(i));
				sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(25).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(25).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void WorkExperience_Alert_goBackButton()
	{
		String getStatus = editProfileLocator.checkworkExperienceAlertGoBackButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(27).set(0, "WorkExperience_Alert_goBackButton - failed");
		}
	}
	public void WorkExperience_Alert_yesButton()
	{
		String getStatus = editProfileLocator.checkWorkExperienceAlertYesButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(26).set(0, "WorkExperience_Alert_yesButton - failed");
		}
	}

	public void PersonalDetails_updateIcon()
	{
		String getStatus = editProfileLocator.checkPersonalDetailsUpdateIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(28).set(0, "PersonalDetails_updateIcon - failed");
		}
	}
	public void PersonalDetails_cancelIcon()
	{
		String getStatus = editProfileLocator.checkPersonalDetailsCancelIcon();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(29).set(0, "PersonalDetails_cancelIcon - failed");
		}
	}
	public void PersonalDetails_Alert_close()
	{
		String getStatus = editProfileLocator.checkPersonalDetailsAlertClose();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(30).set(0, "PersonalDetails_Alert_close - failed");
		}
	}
	public void PersonalDetails_submitValidData(ArrayList<String> data)
	{
		ArrayList<String> getStatus = editProfileLocator.checkPersonalDetailsSubmitValidData(data);
		for(int i = 0; i < getStatus.size(); i++)
		{
			if(!data.contains(getStatus.get(i)))
			{
				int position = data.indexOf(getStatus.get(i));
				sheetStatus = "Fail";
				String cellValue = RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(31).get(position);
				RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(31).set(position, (cellValue + " - failed"));
			}
		}
	}
	public void PersonalDetails_Alert_goBackButton()
	{
		String getStatus = editProfileLocator.checkPersonalDetailsAlertGoBackButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(33).set(0, "PersonalDetails_Alert_goBackButton - failed");
		}
	}
	public void PersonalDetails_Alert_yesButton()
	{
		String getStatus = editProfileLocator.checkPersonalDetailsAlertYesButton();
		if(!getStatus.equalsIgnoreCase("pass"))
		{
			sheetStatus = "Fail";
			RegressionTesting.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get("EditProfile").get(32).set(0, "PersonalDetails_Alert_yesButton - failed");
		}
	}


}
