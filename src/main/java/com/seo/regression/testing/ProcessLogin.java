package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcessLogin
{
	WebDriver driver;
	String loginStatus;
	String url = "";
	public ProcessLogin(WebDriver driver) 
	{
		this.driver = driver;
	}
	public String loginFunction(String userName, String passWord) throws InterruptedException
	{
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			Thread.sleep(2000);
			WebElement clickLogin = driver.findElement(By.cssSelector("ul[class='list-unstyled navbar-nav nav Header_navButtons__3h4Rp'] li[class='Header_loginBtn__3Xv3A'] a"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(clickLogin));
			if(clickLogin.isDisplayed())
			{
				String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
				clickLogin.sendKeys(n);
			}
			String parentWindow = driver.getWindowHandle();
			Set<String> nextWindow = driver.getWindowHandles();
			for(String window : nextWindow)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("login?"))
				{
					driver.switchTo().window(window);
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
					Thread.sleep(2000);
					loginStatus = this.ErrorMessage();
					if(loginStatus.equalsIgnoreCase("Failed"))
					{
						loginStatus = "Failed";
					}
					else
					{
						this.checkUserAfterLoggedIn();
					}
					driver.close();
					break;
				}
			}
			driver.switchTo().window(parentWindow);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
	public String ErrorMessage()
	{
		try
		{
			List<WebElement> errorMsg2 = driver.findElements(By.xpath("//div[@class='NotificationTypeError spacing-mb16 status message submission-error is-shown']//div[@class='fiederror message-title']"));
			if(errorMsg2.size()>0)
			{
				if(errorMsg2.get(0).getText().equalsIgnoreCase("Email or password is incorrect."))
				{
					loginStatus = "Failed";
				}
				else
				{
					loginStatus = "Success";
				}
			}
			else
			{
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
						Thread.sleep(1000);
						WebElement clickDropdownIcon = driver.findElement(By.cssSelector("li[class='Header_SigNUP__cUzCw'] img[alt='icon']"));
						clickDropdownIcon.click();
						Thread.sleep(1000);
						String parentWindow1 = driver.getWindowHandle();
						Set<String> listOfWindow1 = driver.getWindowHandles();
						for(String windows1 : listOfWindow1)
						{
							Thread.sleep(1000);
							driver.switchTo().window(windows1);
							if(driver.getCurrentUrl().equalsIgnoreCase("https://stage-in.skillup.online/interested/"))
							{
								driver.switchTo().window(windows1);
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
							WebElement clickSignOut = driver.findElement(By.cssSelector("ul[class*='dropdown-menu Header'] li:nth-child(5) a"));
							clickSignOut.click();
							Thread.sleep(1000);
						}
						}
					 }
					else if(driver.getCurrentUrl().contains("dashboard"))
					{
						Thread.sleep(1000);
						driver.switchTo().window(windows);
						WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP'] img[class='dPaRoW']"));
						clickDropDown.click();
						Thread.sleep(1000);
						WebElement checkLoggedName = driver.findElement(By.cssSelector("li[class*='SigNUP'] ul[class*='dropdown-menu'] li:nth-child(1) a"));
						String checkText = checkLoggedName.getText();
						if(checkText.contains("Hello"))
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		Thread.sleep(2000);
		WebElement clickDropdown = driver.findElement(By.cssSelector("img[class='dPaRoW']"));
		wait.until(ExpectedConditions.elementToBeClickable(clickDropdown));
		clickDropdown.click();
		Thread.sleep(2000);
		WebElement clickSignOut = driver.findElement(By.cssSelector("li[class*='SigNUP'] ul[class*='dropdown-menu']:nth-child(2) li:nth-child(5) a"));
		wait.until(ExpectedConditions.elementToBeClickable(clickSignOut));
		clickSignOut.click();
		System.out.println("logout successfully");
		Thread.sleep(1000);
	}
	
	public String checkInvalidUsername(String uName, String pwd) throws InterruptedException
	{
		try
		{
			System.out.println("Invalid Email Process started");
			System.out.println(driver);
			this.loginFunction(uName, pwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
	public String checkInvalidPassword(String uName, String pwd) throws InterruptedException
	{
		try
		{
			System.out.println("Invalid password Process started");
			/*
			 * System.out.println("InvalidPassword process started"); ((JavascriptExecutor)
			 * driver).executeScript("window.open()"); ArrayList<String> w = new
			 * ArrayList<String>(driver.getWindowHandles());
			 * driver.switchTo().window(w.get(1));
			 */
			this.loginFunction(uName, pwd);
			/*
			 * if(!checkBrowserName.toString().contains("Firefox")||
			 * !checkBrowserName.toString().contains("firefox")) { driver.close(); }
			 */
			//driver.switchTo().window(w.get(0));
			Thread.sleep(500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
	public String checkInvalidUserNameAndPassword(String uName, String pwd) throws InterruptedException
	{
		try
		{
			System.out.println("InvalidEmail and Password process started");
			/*
			 * ((JavascriptExecutor) driver).executeScript("window.open()");
			 * ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			 * driver.switchTo().window(w.get(1));
			 */
			this.loginFunction(uName, pwd);
			/*
			 * if(!checkBrowserName.toString().contains("Firefox")||
			 * !checkBrowserName.toString().contains("firefox")) { driver.close(); }
			 */
			//driver.switchTo().window(w.get(0));
			Thread.sleep(500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
	public String checkValidCredentials(String uName, String pwd) throws InterruptedException
	{
		try
		{
			System.out.println("valid data process started");
			/*
			 * ((JavascriptExecutor) driver).executeScript("window.open()");
			 * ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			 * driver.switchTo().window(w.get(1));
			 */
			this.loginFunction(uName, pwd);
			/*
			 * if(!checkBrowserName.toString().contains("Firefox")||
			 * !checkBrowserName.toString().contains("firefox")) { driver.close(); }
			 */
			//driver.switchTo().window(w.get(0));
			Thread.sleep(500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginStatus;
	}
}
