package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSocialAccLocator
{

	String url = "";
	String loginStatus="failed";
	WebDriver driver;
	public LoginSocialAccLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public String ErrorMessage()
	{
		String errorMsgStatus = "failed";
		try
		{
			List<WebElement> getMessage = driver.findElements(By.cssSelector("div[class='NotificationTypeError spacing-mb16 status message submission-error is-shown'] div[class*='fiederror message-title']"));
			if(getMessage.size()>0)
			{
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
				System.out.println("error shown in login");
				errorMsgStatus = "Failed";
			}
			else
			{
				System.out.println("no error in login");
				errorMsgStatus = "Success";
			}
			
		}
		catch(Exception e)
		{
			System.out.println("no error message");
			e.printStackTrace();
		}
		
		return errorMsgStatus;
	}
	
	public String clickLogin() throws InterruptedException
	{
		String clickLoginStatus = "failed";
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			Thread.sleep(2000);
			WebElement clickLogin = driver.findElement(By.cssSelector("ul[class='list-unstyled navbar-nav nav Header_navButtons__3h4Rp'] li[class='Header_loginBtn__3Xv3A'] a"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(clickLogin));
			clickLogin.click();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			Thread.sleep(2000);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
			if(driver.getCurrentUrl().contains("login"))
			{
				clickLoginStatus = "success";
			}
			Thread.sleep(2000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return clickLoginStatus;
	}
	public String loginFunction(String userName, String passWord) throws InterruptedException
	{
		String loginProcessStatus = "failed";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 200)", "");
			List<WebElement> clickLogin = driver.findElements(By.cssSelector("div[class='container-fluid'] ul li a"));
			for(int i = 0; i < clickLogin.size(); i++)
			{
				if(clickLogin.get(i).getAttribute("href").contains("login"))
				{
					clickLogin.get(i).click();
					Thread.sleep(1000);
					break;
				}
			}
			if(driver.getCurrentUrl().contains("success"))
			{
				WebElement clickAllow = driver.findElement(By.cssSelector("button#oauth__auth-form__submit-btn"));
			}
			else
			{
				WebElement userNameElement = driver.findElement(By.cssSelector("input#email"));
				userNameElement.clear();
				userNameElement.sendKeys(userName);
				WebElement passwordElement = driver.findElement(By.cssSelector("input#password"));
				passwordElement.clear();
				passwordElement.sendKeys(passWord);
				js.executeScript("window.scrollBy(0, 100)", "");
				WebElement clickSubmit = driver.findElement(By.cssSelector("input[value='Log In']"));
				clickSubmit.click();
				loginProcessStatus = this.ErrorMessage();
			}
			if(!loginProcessStatus.equalsIgnoreCase("failed"))
			{
				loginProcessStatus = this.checkUserAfterLoggedIn();
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
				WebElement checkLoggedName = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(5) a"));
				if(checkLoggedName.getText().contains("Sign Out"))
				{
					checkLoggedName.click();
					System.out.println("logout successfully");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginProcessStatus;
	}
	public ArrayList<String> clickFacebookIcon(String uName, String pwd) throws InterruptedException
	{
		ArrayList<String> facebookStatus = new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)", "");
		List<WebElement> clickSocialAcc = driver.findElements(By.cssSelector("div[class='social-section'] a"));
		for(int i = 0; i < clickSocialAcc.size(); i++)
		{
			if(clickSocialAcc.get(i).getAttribute("onclick").contains("facebook"))
			{
				clickSocialAcc.get(i).click();
				facebookStatus.add("success");
				Thread.sleep(1000);
				String parentWindow = driver.getWindowHandle();
				Set<String> allWindow = driver.getWindowHandles();
				for(String window : allWindow)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("facebook"))
					{
						driver.switchTo().window(window);
						facebookStatus.add("success");
						System.out.println("facebook page opened");
						WebElement email_phoneNumber = driver.findElement(By.cssSelector("input#email"));
						email_phoneNumber.sendKeys(uName);
						WebElement password = driver.findElement(By.cssSelector("input#pass"));
						password.sendKeys(pwd);
						WebElement clickLogin = driver.findElement(By.cssSelector("button#loginbutton"));
						clickLogin.click();
						Thread.sleep(1000);
						String parentWindow2 = driver.getWindowHandle();
						Set<String> allWindows = driver.getWindowHandles();
						for(String windows : allWindow)
						{
							driver.switchTo().window(windows);
							if(driver.getCurrentUrl().contains("/register#_=_"))
							{
								driver.switchTo().window(windows);
								facebookStatus.add("success");
								System.out.println("After logged in facebook page");
								Thread.sleep(1000);
								String parentWindow3 = driver.getWindowHandle();
								Set<String> allWindow_fb = driver.getWindowHandles();
								for(String windows3 : allWindow_fb)
								{
									driver.switchTo().window(windows3);
									if(driver.getCurrentUrl().contains("scope="))
									{
										driver.switchTo().window(windows3);
										driver.findElement(By.xpath("//span[contains(text(),'Continue as Hemamalini')]")).click();
									}
								}
							}
						}

					}
					
				}
				break;
			}
			else
			{
				facebookStatus.add("failed");
			}
		}
		
		return facebookStatus;
	}
	
	public String clickGoogleIcon() throws InterruptedException
	{
		String clickGoogleStatus = "failed";
		List<WebElement> clickSocialAcc = driver.findElements(By.cssSelector("div[class='social-section'] a"));
		for(int i = 0; i < clickSocialAcc.size(); i++)
		{
			if(clickSocialAcc.get(i).getAttribute("onclick").contains("google"))
			{
				clickSocialAcc.get(i).click();
				Thread.sleep(1000);
				clickGoogleStatus = "success";
				break;
			}
		}
		return clickGoogleStatus;
	}
	
	public String clickLinledInIcon()
	{
		String clickLinkedInStatus = "failed";
		try
		{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,200)", "");
			List<WebElement> clickSocialAcc = driver.findElements(By.cssSelector("div[class='social-section'] a"));
			for(int i = 0; i < clickSocialAcc.size(); i++)
			{
				if(clickSocialAcc.get(i).getAttribute("onclick").contains("linked"))
				{
					clickLinkedInStatus = "success";
					clickSocialAcc.get(i).click();
					Thread.sleep(1000);
					break;
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return clickLinkedInStatus;
	}
	public String clickMicrosoftIcon() throws InterruptedException
	{
		String clickMicrosoftStatus = "failed";
		List<WebElement> clickSocialAcc = driver.findElements(By.cssSelector("div[class='social-section'] a"));
		for(int i = 0; i < clickSocialAcc.size(); i++)
		{
			if(clickSocialAcc.get(i).getAttribute("onclick").contains("azuread"))//Microsoft
			{
				clickSocialAcc.get(i).click();
				clickMicrosoftStatus = "success";
				Thread.sleep(1000);
				break;
			}
		}
		ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
		for(String window : w)
		{
			driver.switchTo().window(window);
			if(driver.getCurrentUrl().contains("authorize"))
			{
				driver.switchTo().window(window);
				WebElement enterMail = driver.findElement(By.cssSelector("input[type='email']"));
				WebElement clickNext = driver.findElement(By.cssSelector("input[id='idSIButton9']"));
				
			}
		}
		
		return clickMicrosoftStatus;
	}
	
	public void facebookProcess(String email, String password)
	{
		WebElement facebookEmail = driver.findElement(By.cssSelector("input#email"));
		facebookEmail.sendKeys(email);
		WebElement facebookPwd = driver.findElement(By.cssSelector("input#pass"));
		facebookPwd.sendKeys(password);
		WebElement submit = driver.findElement(By.cssSelector("button#loginbutton"));
		submit.click();
	}
	public String googleProcess(String userName, String passWord)
	{
		String googleFunctionStatus = "failed";
		try
		{
			String parentWindow = driver.getWindowHandle();
			Set<String> windows = driver.getWindowHandles();
			for(String window : windows)
			{
				driver.switchTo().window(window);
				if(!driver.getCurrentUrl().contains("register"))
				{
					if(driver.getCurrentUrl().contains("accounts.google"))
					{
						driver.switchTo().window(window);
						List<WebElement> checkEmail = driver.findElements(By.cssSelector("div[data-email]"));
						if(checkEmail.size()>0)
						{
							checkEmail.get(0).click();
							WebElement googlePwd = driver.findElement(By.cssSelector("input#pass"));
							googlePwd.sendKeys(passWord);
							googleFunctionStatus = "success";
						}
						else //if(!driver.findElement(By.xpath("//div[contains(text(),'Use another account')]")).isDisplayed())
						{
							WebElement googleEmail = driver.findElement(By.cssSelector("input[type='email']"));
							googleEmail.sendKeys(userName);
							driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
							googleFunctionStatus = "success";
						}
					}
				}
				else
				{
					if(driver.getCurrentUrl().contains("accounts.google"))
					{
						driver.switchTo().window(window);
						List<WebElement> checkEmail = driver.findElements(By.cssSelector("div[data-email]"));
						if(checkEmail.size()>0)
						{
							checkEmail.get(0).click();
							WebElement googlePwd = driver.findElement(By.cssSelector("input#pass"));
							googlePwd.sendKeys(passWord);
							googleFunctionStatus = "success";
						}
						else//(!driver.findElement(By.xpath("//div[contains(text(),'Use another account')]")).isDisplayed())
						{
							//driver.findElement(By.xpath("//div[contains(text(),'Use another account')]")).click();
							WebElement googleEmail = driver.findElement(By.cssSelector("input[type='email']"));
							googleEmail.sendKeys(userName);
							driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
							googleFunctionStatus = "success";
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return googleFunctionStatus;
	}
	public String linkedInProcess(String userName, String passWord)
	{
		String linkedInFunctionStatus = "failed";
		try
		{
			WebElement linkedInEmail = driver.findElement(By.cssSelector("input#username"));
			linkedInEmail.sendKeys(userName);
			WebElement linkedInPwd = driver.findElement(By.cssSelector("input#password"));
			linkedInPwd.sendKeys(passWord);
			WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
			submit.click();
			linkedInFunctionStatus = "success";
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return linkedInFunctionStatus;
	}
	public void MicrosoftProcess(String userName, String passWord)
	{
		

	}
	public String pageValidation(String uName, String pwd)
	{
		String pageStatus = "failed";
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for(String window : allWindows)
		{
			driver.switchTo().window(window);
			if(driver.getCurrentUrl().contains("facebook"))
			{
				driver.switchTo().window(window);
				System.out.println("facebook page");
				this.facebookProcess(uName, pwd);
			}
			if(driver.getCurrentUrl().contains("google"))
			{
				driver.switchTo().window(window);
				System.out.println("google page");
				this.googleProcess(uName, parentWindow);
			}
			if(driver.getCurrentUrl().contains("linked"))
			{
				driver.switchTo().window(window);
				System.out.println("Linked In page");
				pageStatus = this.linkedInProcess(uName, pwd);
			}
			if(driver.getCurrentUrl().contains("azuread"))
			{
				driver.switchTo().window(window);
				System.out.println("Microsoft page");
				this.MicrosoftProcess(uName, parentWindow);
			}
		}
		return pageStatus;
	}
	public String checkUserAfterLoggedIn()
		{
			try
			{
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				Thread.sleep(2000);
				String parentWindow = driver.getWindowHandle();
				Set<String> listOfWindow = driver.getWindowHandles();
				for(String windows : listOfWindow)
				{
					Thread.sleep(1000);
					driver.switchTo().window(windows);
					if(driver.getCurrentUrl().contains("personalized/"))
					{
						driver.switchTo().window(windows);
						WebElement clickBeginProfile = driver.findElement(By.cssSelector("div[class='col-md-12']:nth-child(2) div[class*='Personalized_PersContent'] a"));
						WebDriverWait wait2 =  new WebDriverWait(driver, Duration.ofSeconds(30));
						wait2.until(ExpectedConditions.elementToBeClickable(clickBeginProfile));
						clickBeginProfile.click();
						WebElement clickDropdownIcon = driver.findElement(By.cssSelector("li[class='Header_SigNUP__cUzCw'] img[alt='icon']"));
						clickDropdownIcon.click();
						if(driver.getCurrentUrl().equalsIgnoreCase("https://stage-in.skillup.online/interested/"))
						{
							driver.switchTo().window(windows);
							WebElement checkLoggedName = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Header'] li:nth-child(1) a"));
							if(checkLoggedName.getText().contains("Hello"))
							{
								loginStatus = "Success";
								System.out.println("logged in successfully");
								Thread.sleep(1000);
							}
							else
							{
								loginStatus = "Failed";
								System.out.println("not logged in ");
							}
							WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(5) a"));
							clickSignOut.click();
							Thread.sleep(1000);
						}
					 }
					else if(driver.getCurrentUrl().contains("dashboard"))
					{
						driver.switchTo().window(windows);
						WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP'] img[class='dPaRoW']"));
						clickDropDown.click();
						Thread.sleep(1000);
						WebElement checkLoggedName = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(1) a"));
						if(checkLoggedName.getText().contains("Hello"))
						{
							loginStatus = "Success";
							System.out.println("logged in successfully");
							Thread.sleep(1000);
						}
						else
						{
							loginStatus = "Failed";
							System.out.println("not logged in ");
						}
					}
				}
			}
		catch(Exception e)
		{
			e.printStackTrace();
			loginStatus = "Failed";
		}
		return loginStatus;
	}
	public void logOutFunction() throws InterruptedException
	{
		WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(5) a"));
		clickSignOut.click();
		System.out.println("logout successfully");
		driver.quit();
		Thread.sleep(1000);
	}
	public String checkGoogle(String uName, String pwd) throws InterruptedException
	{
		System.out.println("InvalidPassword process started");
		ArrayList<String> statusOfLinkedInFunctionality = new ArrayList<String>();
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(w.get(1));
		OpenWebsite.openSite(driver);
		statusOfLinkedInFunctionality.add(this.clickLogin());
		statusOfLinkedInFunctionality.add(this.clickGoogleIcon());
		statusOfLinkedInFunctionality.add(pageValidation(uName, pwd));
		this.pageValidation(uName, pwd);
		driver.close();
		driver.switchTo().window(w.get(0));
		Thread.sleep(500);
		return loginStatus;
	}
	public ArrayList<String> checkFacebook(String uName, String pwd) throws InterruptedException
	{
		ArrayList<String> getFacebookProcessStatus = new ArrayList<String>();
		System.out.println("Facebook process started");
		getFacebookProcessStatus.add(this.clickLogin());
		getFacebookProcessStatus.addAll(this.clickFacebookIcon(uName, pwd));
		//this.pageValidation(uName, pwd);
		Thread.sleep(500);
		return getFacebookProcessStatus;
	}
	
	public ArrayList<String> checkLinkedIn(String uName, String pwd) throws InterruptedException
	{
		ArrayList<String> statusOfLinkedInFunctionality = new ArrayList<String>();
		try
		{
			System.out.println("Linked In Process started");
		//	((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			for(String window : w)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("online"))
				{
					driver.switchTo().window(window);
				}
			}
			statusOfLinkedInFunctionality.add(clickLogin());
			statusOfLinkedInFunctionality.add(clickLinledInIcon());
			statusOfLinkedInFunctionality.add(pageValidation(uName, pwd));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfLinkedInFunctionality;
	}
	public String checkMicrosoft() throws InterruptedException
	{
		ArrayList<String> statusOfMicrosoftFunctionality = new ArrayList<String>();
		try
		{
			System.out.println("Microsoft Process started");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			for(String window : w)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("online"))
				{
					driver.switchTo().window(window);
				}
			}
			this.clickLogin();
			statusOfMicrosoftFunctionality.add(clickMicrosoftIcon());
			statusOfMicrosoftFunctionality.add(checkUserAfterLoggedIn());
			driver.close();
			driver.switchTo().window(w.get(0));
			Thread.sleep(500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
}
