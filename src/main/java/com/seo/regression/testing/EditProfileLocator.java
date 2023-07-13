package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class EditProfileLocator
{
	WebDriver driver;
	
	public EditProfileLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	public ArrayList<String> checkLogin(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			WebElement clickLogin = driver.findElement(By.cssSelector("li[class*='Header_loginBtn'] a"));
			clickLogin.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
			Thread.sleep(1000);
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("login?"))
				{
					driver.switchTo().window(window);
					Thread.sleep(1000);
					WebElement uname = driver.findElement(By.cssSelector("input#email"));
					uname.sendKeys(data.get(1));
					WebElement pwd = driver.findElement(By.cssSelector("input#password"));
					pwd.sendKeys(data.get(2));
					WebElement submit = driver.findElement(By.cssSelector("input#login_in"));
					submit.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(150));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("dashboard"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(150));
						Thread.sleep(1000);
						System.out.println("dashboard page");//dashboard page (1)
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(150));
						status.add("pass");
						Thread.sleep(2000);
					}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status.add("fail");
		}
		return status;
	}
	public String checkProfile()
	{
		String dataFromExcel = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/dashboard"))
				{
					WebElement clickDropDown = driver.findElement(By.cssSelector("div[class='container-fluid container-inner'] ul[class='nav navbar-nav LoGInMeNU']>li:nth-child(3)>a"));
					clickDropDown.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(150));
					Thread.sleep(1000);
					WebElement clickProfile = driver.findElement(By.cssSelector("ul[class='dropdown-menu Primary02_Blue'] li:nth-child(3)"));
					clickProfile.click();
					Thread.sleep(1000);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(150));
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("stagecourses-in"))
					{
						driver.switchTo().window(window);//profile page(1)
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						System.out.println("profile page");
						dataFromExcel = "pass";
						Thread.sleep(2000);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dataFromExcel = "fail";
		}
		System.out.println("profile page navigated ");
		
		return dataFromExcel;
	}
	public String checkUpdateIcon() throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					Thread.sleep(1000);
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='ProfileContent_main'] div[class='EditUpdateButton'] a button"));
					//String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
					//clickUpdateFromContacts.sendKeys(selectLinkOpeninNewTab); //new tab for update screen opened
					//System.out.println("new tab");
					clickUpdateFromContacts.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						if(driver.getCurrentUrl().contains("update/"))
						{
							driver.switchTo().window(window1);//(2)
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							System.out.println("update page");
							status = "pass";
							break;
							
						}
					}
					break;
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("contact update page process done ");
		Thread.sleep(2000);
		return status;
	}
	public String checkSubmitWithoutDataForMobile() throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("button#update_profile"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					int errorSize = driver.findElements(By.cssSelector("div#mobileErr")).size();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					if(errorSize>0)
					{
						System.out.println("error shown for mobile number");
						status = "pass";
					}
					else
					{
						status = "fail";
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("contact without data process done");
		Thread.sleep(2000);
		return status;
	}
	public String checkSubmitInvalidDataForMobile(String data) throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					WebElement enterMbl = driver.findElement(By.cssSelector("input#mobile_number"));
					enterMbl.sendKeys(data);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("button#update_profile"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					int errorSize = driver.findElements(By.cssSelector("div#mobileErr")).size();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					if(errorSize>0)
					{
						System.out.println("error shown for mobile number");
						status = "pass";
					}
					else
					{
						status = "fail";
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("contact with invalid data process done");
		Thread.sleep(2000);
		return status;
	}
	public String checkCancelIcon() throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					driver.switchTo().window(window);
					WebElement clickCancel = driver.findElement(By.cssSelector("p[class='CancelButton']"));
					clickCancel.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Set<String> a = driver.getWindowHandles();
					for(String win : a)
					{
						driver.switchTo().window(win);
						if(driver.getCurrentUrl().contains("/update/"))
						{
							driver.switchTo().window(win);//pop up
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement getTextFromAlert = driver.findElement(By.cssSelector("div[class='modal-body']"));
							System.out.println(getTextFromAlert.getText());
						}
					}
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("cancel icon process done for contact");
		Thread.sleep(2000);
		return status;
	}
	public String checkContactsAlertClose()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					WebElement closeAlert = driver.findElement(By.cssSelector("button[class='close']"));
					closeAlert.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					status = "pass";
					System.out.println(driver.getCurrentUrl());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("contact alert close process done");
		return status;
	}
	public String checkSubmitValidDataForMobile(String data) throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					driver.switchTo().window(window);
					WebElement enterMobileNum = driver.findElement(By.cssSelector("input#mobile_number"));
					enterMobileNum.clear();
					enterMobileNum.sendKeys(data);
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("button#update_profile"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/u/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
						Thread.sleep(1000);
						WebElement mblNum = driver.findElement(By.cssSelector("div[class='UserProfilemain'] div[class='userProfileDetails'] p:nth-child(3)"));
						String modifiedData = mblNum.getText();
						Thread.sleep(1000);
						if(modifiedData.replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim().equalsIgnoreCase(data))
						{
							System.out.println("mbl number updated correctly");							
							status = "pass";
						}
						Thread.sleep(1000);
						System.out.println(driver.getCurrentUrl());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		Thread.sleep(2000);
		System.out.println("contact with valid process done");
		return status;
	}
	public String checkAlertYesButton() throws InterruptedException
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, -100)","");
					
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='ProfileContent_main'] div[class='EditUpdateButton'] a button"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickUpdateFromContacts);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("/update/"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						jse1.executeScript("window.scrollBy(0, 100)","");
						WebElement clickCancel = driver.findElement(By.cssSelector("p[class='CancelButton']"));
						clickCancel.click();
						driver.switchTo().window(window1);
						if(driver.getCurrentUrl().contains("/update/"))
						{
							driver.switchTo().window(window1);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement clickYesFromAlert = driver.findElement(By.cssSelector("button[class='btn updateButton']"));
							clickYesFromAlert.click();
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							if(driver.getCurrentUrl().contains("/update/"))
							{
								status = "pass";
							}
						}
					}
				}
			}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("alert yes process done for contact");
		Thread.sleep(2000);
		return status;
	}
	public String checkAlertGoBackButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("update/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickCancel = driver.findElement(By.cssSelector("p[class='CancelButton']"));
					clickCancel.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/update/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						WebElement clickGoBackFromAlert = driver.findElement(By.cssSelector("a[class='btn cancelButton']"));
						clickGoBackFromAlert.click();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						driver.switchTo().window(window);
						if(driver.getCurrentUrl().contains("/u/"))
						{
							driver.switchTo().window(window);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							System.out.println("Profile page");
							Thread.sleep(1000);
							status = "pass";
							driver.switchTo().window(window);
						}
					}
					}
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("alert go back process done for conatct");
		return status;
	}
	public String checkAreasOfInterestUpdateIcon()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					Thread.sleep(1000);
					WebElement updateIcon = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(2) div[class='EditUpdate'] a"));
					updateIcon.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
						driver.switchTo().window(window1);
						Thread.sleep(1000);
						if(driver.getCurrentUrl().contains("interestedUpdate/"))
						{
							Thread.sleep(1000);
							driver.switchTo().window(window1);
							System.out.println("update page");
							status = "pass";
							break;
						}
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("AreasOfInterestUpdateIcon process done");
		return status;
	}
	public String checkAreasOfInterestCancelIcon()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("interestedUpdate/"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, 400)","");
					Thread.sleep(1000);
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3']>div[class='col-md-12']:nth-child(2) div[class*='Interested_buttonBottom'] div[class='Interested_skipButonDesk__sc5lk']>button"));
					clickCancel.click();
					Thread.sleep(1000);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Set<String> a = driver.getWindowHandles();
					for(String win : a)
					{
						driver.switchTo().window(win);
						if(driver.getCurrentUrl().contains("interestedUpdate/"))
						{
							driver.switchTo().window(win);
							Thread.sleep(1000);
							WebElement getTextFromAlert = driver.findElement(By.cssSelector("div[class='modelPopup_popupmain__Rs7vT'] div[class='modelPopup_popupTop__yPF_N'] p"));
							System.out.println(getTextFromAlert.getText());
							System.out.println("Alert from Area of interest");
						}
					}
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			status = "fail";
		}
		System.out.println("AreasOfInterestCancelIcon process done");
		return status;
	}
	public String checkAreasOfInterestAlertClose()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("interestedUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					WebElement closeAlert = driver.findElement(By.cssSelector("button[class='btn-close']"));
					closeAlert.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					status = "pass";
					System.out.println(driver.getCurrentUrl());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		return status;
	}
	public ArrayList<String> checkAreasOfInterestSubmitValidData(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("interestedUpdate"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, -200)","");
					List<WebElement> selectInterestedTopics = driver.findElements(By.cssSelector("div[class='Interested_navmenuDiv__5amle']>ul>li>input"));
					for(int i = 0; i < selectInterestedTopics.size(); i++)
					{
						String getDataFromBrowser = selectInterestedTopics.get(i).getAttribute("id");
						for(int k = 0 ; k < data.size(); k++)
						{
							String getDataFromExcel = data.get(k);
							if(getDataFromBrowser.equalsIgnoreCase(getDataFromExcel))
							{
								selectInterestedTopics.get(i).click();
								System.out.println(selectInterestedTopics.get(i)+" is selected");
								Thread.sleep(1000);
								if(k == data.size()-1)
								{
									break;
								}
							}
						}
					}
					js.executeScript("window.scrollBy(0, 400)","");
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='row gy-3']>div[class='col-md-12']:nth-child(2) div[class*='Interested_buttonBottom'] button[type='submit']"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					Thread.sleep(2000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/u/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						Thread.sleep(1000);
						List<WebElement> selectInterestedTopicsFromHome = driver.findElements(By.cssSelector("div[class='profileheadLeft']>div:nth-child(2) div[class='ProfileUserDetails']>ul>li>a"));
						for(int j = 0; j < selectInterestedTopicsFromHome.size(); j++)
						{
							for(int k = 0; k < data.size(); k++)
							{
								if(selectInterestedTopicsFromHome.get(j).getText().replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim().equalsIgnoreCase(data.get(k).replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim()))
								{
									status.add(data.get(k));
								}
							}
					    }
				  }
			}
		
		}
		}
		catch(Exception e)
		{
			status.add("fail");
		}
		System.out.println("AreasOfInterestSubmitValidData process done");
		return status;
	}
	public String checkAreasOfInterestAlertGoBackButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("interestedUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3']>div[class='col-md-12']:nth-child(2) div[class*='Interested_buttonBottom'] div[class='Interested_skipButonDesk__sc5lk']>button"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickCancel);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("interestedUpdate/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						WebElement clickGoBackFromAlert = driver.findElement(By.cssSelector("div[class*='modelPopup_popupBottom'] a"));
						js.executeScript("arguments[0].click()", clickGoBackFromAlert);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						driver.switchTo().window(window);
						if(driver.getCurrentUrl().contains("/u/"))
						{
							driver.switchTo().window(window);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							System.out.println("Profile page");
							Thread.sleep(1000);
							status = "pass";
							driver.switchTo().window(window);
							System.out.println("AreasOfInterest_Alert_yesBackButton process done");
						}
					}
					}
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	}
	public String checkAreasOfInterestAlertyesButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, -100)","");
					
					WebElement clickUpdateFromAreaOfInterest = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(2) div[class='EditUpdate'] a"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickUpdateFromAreaOfInterest);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("interestedUpdate"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						jse1.executeScript("window.scrollBy(0, 100)","");
						WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3']>div[class='col-md-12']:nth-child(2) div[class*='Interested_buttonBottom'] div[class='Interested_skipButonDesk__sc5lk']>button"));
						JavascriptExecutor js2 = (JavascriptExecutor) driver;
						js2.executeScript("arguments[0].click()", clickCancel);
						driver.switchTo().window(window1);
						if(driver.getCurrentUrl().contains("interestedUpdate"))
						{
							driver.switchTo().window(window1);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement clickYesFromAlert = driver.findElement(By.cssSelector("button[class='modelPopup_continueButton__aGBui']"));
							js2.executeScript("arguments[0].click()", clickYesFromAlert);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							if(driver.getCurrentUrl().contains("interestedUpdate"))
							{
								status = "pass";
								System.out.println("AreasOfInterest_Alert_goBackButton process done");
							}
						}
					}
				}
			}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	
	}
	public String checkCurrentWorkUpdateIcon()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,100)", "");

		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					Thread.sleep(1000);
					WebElement updateIcon = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(3) div[class='EditUpdate'] a"));
					updateIcon.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
						driver.switchTo().window(window1);
						Thread.sleep(1000);
						if(driver.getCurrentUrl().contains("workstatusUpdate/"))
						{
							Thread.sleep(1000);
							driver.switchTo().window(window1);
							System.out.println("workstatusUpdate page");
							status = "pass";
							break;
						}
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("current work UpdateIcon process done");
		return status;
	
	}
	public String checkCurrentWorkCancelIcon()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workstatusUpdate/"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, 400)","");
					Thread.sleep(2000);
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workstatus_buttonBottom__gah5Y'] div[class='Workstatus_skipButonDesk__2yAQW']>button[class='Workstatus_skipButton__vZu4F']\r\n"
							+ ""));
					clickCancel.click();
					Thread.sleep(2000);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					Set<String> a = driver.getWindowHandles();
					for(String win : a)
					{
						driver.switchTo().window(win);
						if(driver.getCurrentUrl().contains("workstatusUpdate/"))
						{
							driver.switchTo().window(win);
							Thread.sleep(1000);
							WebElement getTextFromAlert = driver.findElement(By.cssSelector("div[class='Workstatus_sectionContent__kQ07c'] p"));
							System.out.println(getTextFromAlert.getText());
							System.out.println("Alert from work experience");
						}
					}
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			status = "fail";
		}
		System.out.println("current work cancelIcon process done");
		return status;
	}
	
	public String checkCurrentWorkAlertClose()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workstatusUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					WebElement closeAlert = driver.findElement(By.cssSelector("button[class='btn-close']"));
					closeAlert.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					status = "pass";
					System.out.println(driver.getCurrentUrl());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("alert close from current work");
		return status;
	}
	public ArrayList<String> checkCurrentWorkSubmitValidData(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workstatusUpdate"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, -200)","");
					List<WebElement> selectWorkExperience = driver.findElements(By.cssSelector("div[class='Workstatus_currentWork__9e8wr'] ul li input"));
					for(int i = 0; i < selectWorkExperience.size(); i++)
					{
						String getDataFromBrowser = selectWorkExperience.get(i).getAttribute("id");
						for(int k = 0 ; k < data.size(); k++)
						{
							String getDataFromExcel = data.get(k);
							if(getDataFromBrowser.equalsIgnoreCase(getDataFromExcel))
							{
								selectWorkExperience.get(i).click();
								Thread.sleep(1000);
								System.out.println(selectWorkExperience.get(i)+" is selected");
								if(k == data.size()-1)
								{
									break;
								}
							}
						}
					}
					js.executeScript("window.scrollBy(0, 500)","");
					Thread.sleep(1000);
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workstatus_buttonBottom__gah5Y'] button[type='submit']"));
					js.executeScript("arguments[0].click()", clickUpdateFromContacts);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					Thread.sleep(2000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/u/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						Thread.sleep(1000);
						List<WebElement> selectInterestedTopicsFromHome = driver.findElements(By.cssSelector("div[class='profileheadLeft'] div[class='ProfileJourny_main']:nth-child(3) div[class='ProfileUserDetails'] ul>li a"));
						for(int j = 0; j < selectInterestedTopicsFromHome.size(); j++)
						{
							for(int k = 0; k < data.size(); k++)
							{
								if(selectInterestedTopicsFromHome.get(j).getText().replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim().equalsIgnoreCase(data.get(k).replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim()))
								{
									status.add(data.get(k));
									System.out.println("current work SubmitValidData process done");
								}
							}
					    }
				  }
			}
		
		}
		}
		catch(Exception e)
		{
			status.add("fail");
		}
		return status;
	}
	public String checkCurrentWorkAlertGoBackButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workstatusUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workstatus_buttonBottom__gah5Y'] div[class='Workstatus_skipButonDesk__2yAQW']>button[class='Workstatus_skipButton__vZu4F']\r\n"
							+ ""));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickCancel);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("workstatusUpdate/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						WebElement clickGoBackFromAlert = driver.findElement(By.cssSelector("a[class='modelPopup_skipButton__6eM0K']"));
						js.executeScript("arguments[0].click()", clickGoBackFromAlert);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						driver.switchTo().window(window);
						if(driver.getCurrentUrl().contains("/u/"))
						{
							driver.switchTo().window(window);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							System.out.println("Profile page");
							Thread.sleep(1000);
							status = "pass";
							driver.switchTo().window(window);
							System.out.println("current work_Alert_goBackButton process done");
						}
					}
					}
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	}
	public String checkCurrentWorkAlertYesButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, -100)","");
					
					WebElement clickUpdateFromAreaOfInterest = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(3) div[class='EditUpdate'] a"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickUpdateFromAreaOfInterest);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("workstatusUpdate"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						jse1.executeScript("window.scrollBy(0, 100)","");
						WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workstatus_buttonBottom__gah5Y'] div[class='Workstatus_skipButonDesk__2yAQW']>button[class='Workstatus_skipButton__vZu4F']\r\n"
								+ ""));
						JavascriptExecutor js2 = (JavascriptExecutor) driver;
						js2.executeScript("arguments[0].click()", clickCancel);
						driver.switchTo().window(window1);
						if(driver.getCurrentUrl().contains("workstatusUpdate"))
						{
							driver.switchTo().window(window1);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement clickYesFromAlert = driver.findElement(By.cssSelector("button[class='modelPopup_continueButton__aGBui']"));
							js2.executeScript("arguments[0].click()", clickYesFromAlert);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							if(driver.getCurrentUrl().contains("workstatusUpdate"))
							{
								status = "pass";
								System.out.println("Alert yes from current work process done");
							}
						}
					}
				}
			}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	
	}
	public String checkworkExperienceUpdateIcon()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)", "");

		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					Thread.sleep(1000);
					WebElement updateIcon = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(4) div[class='EditUpdate'] a"));
					updateIcon.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
						driver.switchTo().window(window1);
						Thread.sleep(1000);
						if(driver.getCurrentUrl().contains("workexperienceUpdate/"))
						{
							Thread.sleep(1000);
							driver.switchTo().window(window1);
							System.out.println("workstatusUpdate page");
							status = "pass";
							break;
						}
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("work experience UpdateIcon process done");
		return status;
	
	}
	public String checkworkExperienceCancelIcon()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workexperienceUpdate/"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, 400)","");
					Thread.sleep(2000);
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workexperience_buttonBottom__5NssD'] div[class='Workexperience_skipButonDesk__5hyrY']>button[class='Workexperience_skipButton__Lmt9c']\r\n"
							+ ""
							+ ""));
					clickCancel.click();
					Thread.sleep(2000);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Set<String> a = driver.getWindowHandles();
					for(String win : a)
					{
						driver.switchTo().window(win);
						if(driver.getCurrentUrl().contains("interestedUpdate/"))
						{
							driver.switchTo().window(win);
							Thread.sleep(1000);
							WebElement getTextFromAlert = driver.findElement(By.cssSelector("div[class='modelPopup_popupTop__yPF_N'] p"));
							System.out.println(getTextFromAlert.getText());
							System.out.println("Alert from work experience");
							System.out.println("WorkExperience cancelIcon process done");
						}
					}
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			status = "fail";
		}
		return status;
	}
	
	public String checkworkExperienceAlertClose()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workexperienceUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("window.scrollBy(0, 100)", "");
					WebElement closeAlert = driver.findElement(By.cssSelector("button[class='btn-close']"));
					js.executeScript("arguments[0].click()", closeAlert);
					Thread.sleep(1000);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					status = "pass";
					System.out.println(driver.getCurrentUrl());
				}
			}
			System.out.println("alert close from work experience");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		return status;
	}
	public ArrayList<String> checkworkExperienceSubmitValidData(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workexperienceUpdate"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, -200)","");
					List<WebElement> selectWorkExperience = driver.findElements(By.cssSelector("div[class='Workexperience_currentWork__btBja'] ul li input = get from value"));
					for(int i = 0; i < selectWorkExperience.size(); i++)
					{
						String getDataFromBrowser = selectWorkExperience.get(i).getAttribute("id");
						for(int k = 0 ; k < data.size(); k++)
						{
							String getDataFromExcel = data.get(k);
							if(getDataFromBrowser.equalsIgnoreCase(getDataFromExcel))
							{
								selectWorkExperience.get(i).click();
								Thread.sleep(1000);
								System.out.println(selectWorkExperience.get(i)+" is selected");
								Thread.sleep(1000);
								if(k == data.size()-1)
								{
									break;
								}
							}
						}
					}
					js.executeScript("window.scrollBy(0, 400)","");
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workstatus_buttonBottom__gah5Y'] button[type='submit']"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					Thread.sleep(2000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/u/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						Thread.sleep(1000);
						List<WebElement> selectInterestedTopicsFromHome = driver.findElements(By.cssSelector("div[class='profileheadLeft'] div[class='ProfileJourny_main']:nth-child(4) div[class='ProfileUserDetails'] ul>li a"));
						for(int j = 0; j < selectInterestedTopicsFromHome.size(); j++)
						{
							for(int k = 0; k < data.size(); k++)
							{
								if(selectInterestedTopicsFromHome.get(j).getText().replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim().equalsIgnoreCase(data.get(k).replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim()))
								{
									status.add(data.get(k));
								}
							}
					    }
						System.out.println("work experience SubmitValidData process done");
				  }
			}
		
		}
		}
		catch(Exception e)
		{
			status.add("fail");
		}
		return status;
	}
	public String checkworkExperienceAlertGoBackButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("workexperienceUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workexperience_buttonBottom__5NssD'] div[class='Workexperience_skipButonDesk__5hyrY']>button[class='Workexperience_skipButton__Lmt9c']\r\n"
							+ ""));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickCancel);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("workexperienceUpdate/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						WebElement clickGoBackFromAlert = driver.findElement(By.cssSelector("a[class='modelPopup_skipButton__6eM0K']"));
						js.executeScript("arguments[0].click()", clickGoBackFromAlert);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						driver.switchTo().window(window);
						if(driver.getCurrentUrl().contains("/u/"))
						{
							driver.switchTo().window(window);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							System.out.println("Profile page");
							Thread.sleep(1000);
							status = "pass";
							driver.switchTo().window(window);
							System.out.println("WorkExperience_Alert_goBackButton process done");
						}
					}
					}
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	}
	public String checkWorkExperienceAlertYesButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, -100)","");
					
					WebElement clickUpdateFromAreaOfInterest = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(4) div[class='EditUpdate'] a"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickUpdateFromAreaOfInterest);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("workexperienceUpdate"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						jse1.executeScript("window.scrollBy(0, 100)","");
						WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workexperience_buttonBottom__5NssD'] div[class='Workexperience_skipButonDesk__5hyrY']>button[class='Workexperience_skipButton__Lmt9c']\r\n"
								+ ""));
						JavascriptExecutor js2 = (JavascriptExecutor) driver;
						js2.executeScript("arguments[0].click()", clickCancel);
						driver.switchTo().window(window1);
						if(driver.getCurrentUrl().contains("workexperienceUpdate"))
						{
							driver.switchTo().window(window1);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement clickYesFromAlert = driver.findElement(By.cssSelector("button[class='modelPopup_continueButton__aGBui']"));
							js2.executeScript("arguments[0].click()", clickYesFromAlert);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							if(driver.getCurrentUrl().contains("workexperienceUpdate"))
							{
								status = "pass";
								System.out.println("Alert yes from work experience process done");
							}
						}
					}
				}
			}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	
	}
	public String checkPersonalDetailsUpdateIcon()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)", "");

		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					Thread.sleep(1000);
					WebElement updateIcon = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(5) div[class='EditUpdate'] a"));
					updateIcon.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
						driver.switchTo().window(window1);
						Thread.sleep(1000);
						if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
						{
							Thread.sleep(1000);
							driver.switchTo().window(window1);
							System.out.println("personal details update page");
							status = "pass";
							break;
						}
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("personal detail UpdateIcon process done");
		return status;
	
	}
	public String checkPersonalDetailsCancelIcon()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, 500)","");
					Thread.sleep(1000);
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Aboutyou_buttonBottom___Uhqg']>div[class='Aboutyou_skipButonDesk__x4MVZ'] button[type='button']\r\n"
							+ ""));
					clickCancel.click();
					Thread.sleep(1000);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Set<String> a = driver.getWindowHandles();
					for(String win : a)
					{
						driver.switchTo().window(win);
						if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
						{
							driver.switchTo().window(win);
							Thread.sleep(1000);
							WebElement getTextFromAlert = driver.findElement(By.cssSelector("div[class='Aboutyou_sectionContent__GBuEF'] p"));
							System.out.println(getTextFromAlert.getText());
							System.out.println("Alert from personal detail");
						}
					}
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			status = "fail";
		}
		System.out.println("personal details cancelIcon process done");
		return status;
	}
	
	public String checkPersonalDetailsAlertClose()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					WebElement closeAlert = driver.findElement(By.cssSelector("button[class='btn-close']"));
					closeAlert.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					status = "pass";
					System.out.println(driver.getCurrentUrl());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		System.out.println("alert close from personal detail");
		return status;
	}
	public ArrayList<String> checkPersonalDetailsSubmitValidData(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("aboutyouUpdate"))
				{
					driver.switchTo().window(window);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, -200)","");
					List<WebElement> selectPersonalDetails = driver.findElements(By.cssSelector("div[class='Aboutyou_currentWork__RD6fb'] ul li input"));
					for(int i = 0; i < selectPersonalDetails.size(); i++)
					{
						String getDataFromBrowser = selectPersonalDetails.get(i).getAttribute("name");
						for(int k = 0 ; k < data.size(); k++)
						{
							String getDataFromExcel = data.get(k);
							if(getDataFromBrowser.equalsIgnoreCase(getDataFromExcel))
							{
								selectPersonalDetails.get(i).click();
								System.out.println(selectPersonalDetails.get(i)+" is selected");
								Thread.sleep(1000);
								if(k == data.size()-1)
								{
									break;
								}
							}
						}
					}
					Select select = new Select(driver.findElement(By.cssSelector("select#year_of_birth")));
					select.selectByValue(data.get(2));
					js.executeScript("window.scrollBy(0, 400)","");
					WebElement clickUpdateFromContacts = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Aboutyou_buttonBottom___Uhqg'] button[type='submit']"));
					clickUpdateFromContacts.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					Thread.sleep(2000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("/u/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						Thread.sleep(1000);
						List<WebElement> selectInterestedTopicsFromHome = driver.findElements(By.cssSelector("div[class='profileheadLeft'] div[class='ProfileJourny_main']:nth-child(5) div[class='PersonalDetails'] ul li span"));
						for(int j = 0; j < selectInterestedTopicsFromHome.size(); j++)
						{
							for(int k = 0; k < data.size(); k++)
							{
								if(selectInterestedTopicsFromHome.get(j).getText().replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim().equalsIgnoreCase(data.get(k).replaceAll("[^a-zA-Z0-9]", " ").replace(" ","").trim()))
								{
									status.add(data.get(k));
								}
							}
					    }
				  }
			}
		
		}
		}
		catch(Exception e)
		{
			status.add("fail");
		}
		System.out.println("personal details SubmitValidData process done");
		return status;
	}
	public String checkPersonalDetailsAlertGoBackButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, 100)","");
					WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Workexperience_buttonBottom__5NssD'] div[class='Workexperience_skipButonDesk__5hyrY']>button[class='Workexperience_skipButton__Lmt9c']\r\n"
							+ ""));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickCancel);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("aboutyouUpdate/"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						WebElement clickGoBackFromAlert = driver.findElement(By.cssSelector("div[class='modelPopup_popupBottom__HjuXS'] a"));
						js.executeScript("arguments[0].click()", clickGoBackFromAlert);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						driver.switchTo().window(window);
						if(driver.getCurrentUrl().contains("/u/"))
						{
							driver.switchTo().window(window);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							System.out.println("Profile page");
							Thread.sleep(1000);
							status = "pass";
							driver.switchTo().window(window);
							System.out.println("personal details_Alert_goBackButton process done");
						}
					}
					}
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	}
	public String checkPersonalDetailsAlertYesButton()
	{
		String status = "";
		try
		{
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/u/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0, -100)","");
					
					WebElement clickUpdateFromAreaOfInterest = driver.findElement(By.cssSelector("div[class='profileheadLeft']>div[class='ProfileJourny_main']:nth-child(5) div[class='EditUpdate'] a"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", clickUpdateFromAreaOfInterest);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					Thread.sleep(1000);
					Set<String> allWindows1 = driver.getWindowHandles();
					for(String window1 : allWindows1)
					{
					driver.switchTo().window(window1);
					if(driver.getCurrentUrl().contains("aboutyouUpdate"))
					{
						driver.switchTo().window(window1);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
						Thread.sleep(1000);
						jse1.executeScript("window.scrollBy(0, 100)","");
						WebElement clickCancel = driver.findElement(By.cssSelector("div[class='row gy-3'] div[class='col-md-12']:nth-child(2) div[class='Aboutyou_buttonBottom___Uhqg']>div[class='Aboutyou_skipButonDesk__x4MVZ'] button[type='button']\r\n"
								+ ""));
						JavascriptExecutor js2 = (JavascriptExecutor) driver;
						js2.executeScript("arguments[0].click()", clickCancel);
						driver.switchTo().window(window1);
						if(driver.getCurrentUrl().contains("aboutyouUpdate"))
						{
							driver.switchTo().window(window1);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							WebElement clickYesFromAlert = driver.findElement(By.cssSelector("div[class='modelPopup_popupBottom__HjuXS'] a"));
							js2.executeScript("arguments[0].click()", clickYesFromAlert);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
							Thread.sleep(1000);
							if(driver.getCurrentUrl().contains("aboutyouUpdate"))
							{
								status = "pass";
								System.out.println("Alert yes from personal details process done");
							}
						}
					}
				}
			}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		
		return status;
	
	}
	
	
	
}
