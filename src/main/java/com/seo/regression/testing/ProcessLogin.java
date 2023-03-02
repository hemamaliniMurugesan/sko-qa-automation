package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.utility.TestUtil;

public class ProcessLogin extends OpenWebsite
{
	@Override
	public WebDriver getDriver() {
		return super.getDriver();
	}
	@Override
	public void openDriver() {
		super.openDriver();
	}
	@Override
	public String setEnvironment(String host)
	{
		return super.setEnvironment(host);
	}
	String url = "";
	String loginStatus;
	
	public String launchCourse() throws InterruptedException
	{
		url = this.setEnvironment(RegressionTesting.ENV_TO_USE);
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		Thread.sleep(1000);
		return driver.getCurrentUrl();
	}
	
	public String ErrorMessage()
	{
		try
		{
			List<WebElement> getMessage = driver.findElements(By.cssSelector("div[class='NotificationTypeError spacing-mb16 status message submission-error is-shown'] div[class*='fiederror message-title']"));
			if(getMessage.size()>0)
			{
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
				System.out.println("error shown in login");
				loginStatus = "Failed";
			}
			else
			{
				System.out.println("no error in login");
				loginStatus = "Success";
			}
			
		}
		catch(Exception e)
		{
			System.out.println("no error message");
			e.printStackTrace();
		}
		
		return loginStatus;
	}
	
	public String loginFunction(String userName, String passWord) throws InterruptedException
	{
		try
		{
			this.launchCourse();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			Thread.sleep(2000);
			WebElement clickLogin = driver.findElement(By.cssSelector("ul[class='list-unstyled navbar-nav nav Header_navButtons__3h4Rp'] li[class='Header_loginBtn__3Xv3A'] a"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(clickLogin));
			if(clickLogin.isDisplayed())
			{
				clickLogin.click();
			}
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 200)", "");
			WebElement userNameElement = driver.findElement(By.cssSelector("input#email"));
			userNameElement.clear();
			userNameElement.sendKeys(userName);
			WebElement passwordElement = driver.findElement(By.cssSelector("input#password"));
			passwordElement.clear();
			passwordElement.sendKeys(passWord);
			js.executeScript("window.scrollBy(0, 100)", "");
			WebElement clickSubmit = driver.findElement(By.cssSelector("input[value='Log In']"));
			clickSubmit.click();
			loginStatus = this.ErrorMessage();
			if(!loginStatus.equalsIgnoreCase("Failed"))
			{
				loginStatus = this.checkUserAfterLoggedIn();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
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
	Thread.sleep(1000);
}
	public String checkInvalidUsername(String uName, String pwd) throws InterruptedException
	{
		System.out.println("Invalid Email Process started");
		this.loginFunction(uName, pwd);
		return loginStatus;
	}
	public String checkInvalidPassword(String uName, String pwd) throws InterruptedException
	{
		System.out.println("InvalidPassword process started");
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(w.get(1));
		this.loginFunction(uName, pwd);
		driver.close();
		driver.switchTo().window(w.get(0));
		Thread.sleep(500);
		return loginStatus;
	}
	public String checkInvalidUserNameAndPassword(String uName, String pwd) throws InterruptedException
	{
		System.out.println("InvalidEmail and Password process started");
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(w.get(1));
		this.loginFunction(uName, pwd);
		driver.close();
		driver.switchTo().window(w.get(0));
		Thread.sleep(500);
		return loginStatus;
	}
	public String checkValidCredentials(String uName, String pwd) throws InterruptedException
	{
		try
		{
			System.out.println("valid data process started");
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(1));
			this.loginFunction(uName, pwd);
			this.logOutFunction();
			driver.close();
			driver.switchTo().window(w.get(0));
			driver.quit();
			Thread.sleep(500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
}
