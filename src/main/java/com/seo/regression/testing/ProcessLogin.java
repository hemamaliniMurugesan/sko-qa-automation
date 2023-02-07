package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;
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
	public String setEnvironment(String host) {
		return super.setEnvironment(host);
	}
	String url ;
	public String launchCourse() throws InterruptedException
	{
		this.openDriver();
		url = this.setEnvironment(RegressionTesting.ENV_TO_USE);
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		Thread.sleep(1000);
		return driver.getCurrentUrl();
	}
	
	public String loginFunction(String userName, String Pwd) throws InterruptedException
	{
		this.launchCourse();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		Thread.sleep(2000);
		WebElement clickLogin = driver.findElement(By.cssSelector("ul[class='list-unstyled navbar-nav nav Header_navButtons__3h4Rp'] li[class='Header_loginBtn__3Xv3A'] a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(clickLogin));
		clickLogin.click();
		Thread.sleep(2000);
		String loginStatus = "Failed";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 200)", "");
			WebElement userNameElement = driver.findElement(By.cssSelector("input#email"));
			userNameElement.clear();
			userNameElement.sendKeys(userName);
			WebElement passwordElement = driver.findElement(By.cssSelector("input#password"));
			passwordElement.clear();
			passwordElement.sendKeys(Pwd);
			js.executeScript("window.scrollBy(0, 100)", "");
			WebElement clickSubmit = driver.findElement(By.cssSelector("input[value='Log In']"));
			clickSubmit.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
			Thread.sleep(2000);
			String parentWindow = driver.getWindowHandle();
			Set<String> listOfWindow = driver.getWindowHandles();
			for(String windows : listOfWindow)
			{
				Thread.sleep(1000);
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
							Thread.sleep(1000);
						}
						else
						{
							loginStatus = "Failed";
						}
					}
				}
				else if(driver.getCurrentUrl().contains("dashboard"))
				{
					driver.switchTo().window(windows);
					WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP'] img[class='dPaRoW']"));
					clickDropDown.click();
					WebElement checkLoggedName = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(1) a"));
					if(checkLoggedName.getText().contains("Hello"))
					{
						loginStatus = "Success";
						Thread.sleep(1000);
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
	
	public String loginAndSignOut(String userName, String Pwd)
	{
		String loginStatus = null;
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 200)", "");
			WebElement userNameElement = driver.findElement(By.cssSelector("input#email"));
			userNameElement.clear();
			userNameElement.sendKeys(userName);
			WebElement passwordElement = driver.findElement(By.cssSelector("input#password"));
			passwordElement.clear();
			passwordElement.sendKeys(Pwd);
			js.executeScript("window.scrollBy(0, 100)", "");
			WebElement clickSubmit = driver.findElement(By.cssSelector("input[value='Log In']"));
			clickSubmit.click();
			this.loggedIn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			loginStatus = "Failed";
		}
		return loginStatus;
	}
	public void loggedIn() throws InterruptedException
	{
		String loginStatus = "";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		String parentWindow = driver.getWindowHandle();
		Set<String> listOfWindow = driver.getWindowHandles();
		for(String windows : listOfWindow)
		{
			if(driver.getCurrentUrl().equalsIgnoreCase("https://stage-in.skillup.online/personalized/"))
			{
				driver.switchTo().window(windows);
				WebElement clickBeginProfile = driver.findElement(By.cssSelector("div[class='col-md-12']:nth-child(2) div[class*='Personalized_PersContent'] a"));
				WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(clickBeginProfile));
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
						WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Header'] li:nth-child(5) a"));
						clickSignOut.click();
						Thread.sleep(1000);
					}
					else
					{
						loginStatus = "Failed";
					}
				}
			}
			else if(driver.getCurrentUrl().equalsIgnoreCase("https://stagecourses-in.skillup.online/dashboard"))
			{
				driver.switchTo().window(windows);
				WebElement checkLoggedName = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(1) a"));
				if(checkLoggedName.getText().contains("Hello"))
				{
					loginStatus = "Success";
					WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li:nth-child(5) a"));
					clickSignOut.click();
					Thread.sleep(1000);
				}
			}
		}
	}
	public String verifyLogOut()
	{
		String loginStatus = "";
		try
		{
			if(loginStatus.equalsIgnoreCase("Success"))
			{
				WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Header'] li:nth-child(5) a"));
				clickSignOut.click();
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
				Thread.sleep(300);
				driver.quit();
			}
			else
			{
				System.out.println("Log In is not done. so unable to do logOut");
				loginStatus = "Failed";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
}
