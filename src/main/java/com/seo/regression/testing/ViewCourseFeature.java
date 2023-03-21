package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewCourseFeature
{
	WebDriver driver;
	public ViewCourseFeature(WebDriver driver) throws InterruptedException
	{
		this.driver= driver; 
	}
	ArrayList<String> dashboardStatus;
	public ArrayList<String> Dashboard() throws InterruptedException
	{
		dashboardStatus = new ArrayList<String>();
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String window : windows)
		{
			if(driver.getCurrentUrl().contains("dashboard"))
			{
				driver.switchTo().window(window);
				this.courses();
				/*
				 * List<WebElement> listOfCourse = driver.findElements(By.
				 * cssSelector("ul[class='listing-courses'] li[class='course-item'] section[class='details'] div[class='wrapper-course-details'] h3 a"
				 * )); for(int i = 0; i < listOfCourse.size();i++) {
				 * System.out.println("List of course names in dashboard : "+listOfCourse.get(i)
				 * .getText()); }
				 */
				dashboardStatus.add("success");
			}
			else if(driver.getCurrentUrl().contains("interested"))
			{
				driver.switchTo().window(window);
				Thread.sleep(1000);
				WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='Header_SigNUP__cUzCw'] img[alt='icon']"));
				clickDropDown.click();
				Thread.sleep(1000);
				WebElement clickDashboard = driver.findElement(By.cssSelector("li[class*='Header_SigNUP'] ul[class*='dropdown-menu'] li:nth-child(2) a"));
				if(clickDashboard.isDisplayed())
				{
					clickDashboard.click();
					Thread.sleep(1000);
				}
				WebElement clickViewCourse = driver.findElement(By.cssSelector("div[class='course-actions'] a[class*='enter-course']"));
				clickViewCourse.click();
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				if(driver.getCurrentUrl().contains("IBM"))
				{
					System.out.println("Course ID contains IBM");
					if(driver.getCurrentUrl().contains("-dev.skillsnetwork.site"))
					{
						dashboardStatus.add("Success");
						driver.close();
					}
					else
					{
						dashboardStatus.add("Failed");
						driver.close();
					}
				}
				else
				{
					dashboardStatus.add("Failed");
					driver.close();
				}
			}
		}
		return dashboardStatus;
	}
	public void courses() throws InterruptedException
	{
		try
		{
			List<WebElement> courses = driver.findElements(By.cssSelector("ul[class='listing-courses'] li[class='course-item']"));
			for(int i = 0; i < courses.size(); i++)
			{
				WebElement courseNameLocator = courses.get(i).findElement(By.cssSelector(" h3 a"));
				String getCourseName = courseNameLocator.getText();
				System.out.println("list of Course Name in user dashboard: "+getCourseName);
			}
			Thread.sleep(1000);
			List<WebElement> getActiveCourse = driver.findElements(By.xpath("//ul[@class='listing-courses']//li[@class='course-item']//a[contains(@class,'course-target-link enter-course color54 btn btn-primary ')]//preceding::h3"));
			if(getActiveCourse.size()>0)
			{
				for(int j = 0; j < getActiveCourse.size(); j++)
				{
					WebElement activeCourselocator = getActiveCourse.get(j);
					String getActiveCoursesName = getActiveCourse.get(j).getText();
					if(activeCourselocator.isDisplayed())
					{
						System.out.println("Name of Active courses in user dashboard : " + getActiveCoursesName);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dashboardStatus.add("Failed");
		}
		
	}
	public ArrayList<String> shareCourse(ArrayList<String> socialNetwork)
	{
		ArrayList<String> shareCourseStatus = new ArrayList<String>();
		try
		{
			String shareAccount;
			String word ;
			HashMap<String, String> map = new HashMap<String, String>();
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < socialNetwork.size(); i++)//facebook=9487996723_Hema@2020, linkedIn="malinim282@gmail.com"_"LinkedIn@22"
			{
				if(i>0)
				{
					shareAccount = socialNetwork.get(i);//facebook=9487996723_Hema@2020
					String subStringData[] = shareAccount.split("=");
					word = 	subStringData[1];
					String get[] = word.split("_");
					//list = Arrays.asList(get);
					Collections.addAll(list, get);
				}
			}
			System.out.println(list);
			
			System.out.println("social networks :" +list);
			String fuserName = list.get(0);
			String fpwd = list.get(1);
			this.shareFacebook(fuserName, fpwd);
			String luserName =  list.get(2);
			String lpwd =  list.get(3);
			this.shareLinkedIn(luserName, lpwd);
//			this.twitter(userName, pwd);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return shareCourseStatus;
	}
	public void twitter(String userName, String pwd)
	{
		List<WebElement> shareSocialAcc = driver.findElements(By.xpath("//ul[@class='listing-courses']//li[@class='course-item']//div[@class='wrapper-course-actions']//div[@class='course-actions']//a[contains(@class,'course-target-link enter-course')]//following-sibling::a"));
		for(int l = 0; l < shareSocialAcc.size(); l++)
		{
			if(shareSocialAcc.get(l).getAttribute("data-tooltip").contains("Twitter"))
			{
				shareSocialAcc.get(l).click();
			}
		}	
	}
	public void shareFacebook(String userName, String pwd) throws InterruptedException
	{
		List<WebElement> shareSocialAcc = driver.findElements(By.xpath("//ul[@class='listing-courses']//li[@class='course-item']//div[@class='wrapper-course-actions']//div[@class='course-actions']//a[contains(@class,'course-target-link enter-course')]//following-sibling::a"));
		for(int l = 0; l < shareSocialAcc.size(); l++)
		{
			if(shareSocialAcc.get(l).getAttribute("data-tooltip").contains("Facebook"))
			{
				shareSocialAcc.get(l).click();
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				for(String window : nextWindow)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("facebook"))
					{
						driver.switchTo().window(window);
						driver.manage().window().maximize();
						WebElement enterEmail = driver.findElement(By.cssSelector("input#email"));
						enterEmail.sendKeys(userName);
						Thread.sleep(1000);
						WebElement enterPassword = driver.findElement(By.cssSelector("input#pass"));
						enterPassword.sendKeys(pwd);
						WebElement clickSubmit = driver.findElement(By.cssSelector("input[name='login']"));
						clickSubmit.click();
						String parentWindow1 = driver.getWindowHandle();
						Set<String> nextWindow1 = driver.getWindowHandles();
						for(String windows : nextWindow1)
						{
							driver.switchTo().window(windows);
							if(driver.getCurrentUrl().contains("sharer"))
							{
								driver.switchTo().window(windows);
								WebElement clickPost = driver.findElement(By.xpath("//button[@name='__CONFIRM__']"));
								clickPost.click();
								String parentWindow3 = driver.getWindowHandle();
								Set<String> allWindows = driver.getWindowHandles();
								for(String windows3 : allWindows)
								{
									driver.switchTo().window(windows3);
									if(driver.getCurrentUrl().contains("/close_window"))
									{
										driver.switchTo().window(windows3);
										break;
									}
								}
							}
						}
					}
					else if(driver.getCurrentUrl().contains("sharer"))
					{
						driver.switchTo().window(window);
						driver.manage().window().maximize();
						WebElement clickPost = driver.findElement(By.xpath("//button[@name='__CONFIRM__']"));
						clickPost.click();
						break;
					}
				}
				driver.switchTo().window(parentWindow);
			}
		}
	}
	public void shareLinkedIn(String userName, String pwd)
{
	List<WebElement> shareSocialAcc = driver.findElements(By.xpath("//ul[@class='listing-courses']//li[@class='course-item']//div[@class='wrapper-course-actions']//div[@class='course-actions']//a[contains(@class,'course-target-link enter-course')]//following-sibling::a"));
	for(int l = 0; l < shareSocialAcc.size(); l++)
	{
		if(shareSocialAcc.get(l).getAttribute("data-tooltip").contains("linkedin"))
		{
			shareSocialAcc.get(l).click();
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindow = driver.getWindowHandles();
			for(String windows : allWindow)
			{
				driver.switchTo().window(windows);
				if(driver.getCurrentUrl().contains("linkedin"))
				{
					driver.switchTo().window(windows);
					List<WebElement> sharePost = driver.findElements(By.xpath("//span[contains(@class, artdeco-button__text)]"));
					for(int i = 0; i < sharePost.size(); i++)
					{
						if(sharePost.get(i).getText().equalsIgnoreCase("Share in a post"))
						{
							sharePost.get(i).click();
							String parentWindow1 = driver.getWindowHandle();
							Set<String> allWindow1 = driver.getWindowHandles();
							for(String windows1 : allWindow1)
							{
								driver.switchTo().window(windows1);
								if(driver.getCurrentUrl().contains("sharing"))
								{
									driver.switchTo().window(windows1);
									List<WebElement> sharePostInLinkedIn = driver.findElements(By.xpath("//span[contains(@class,'artdeco-button__text')]")); 
									for(int j = 0; j < sharePostInLinkedIn.size(); j++)
									{
										if(sharePostInLinkedIn.get(j).getText().equalsIgnoreCase("Post"))
										{
											sharePostInLinkedIn.get(j).click();
											WebElement checkSuccessMsg = driver.findElement(By.xpath("//div[@class='inshare-success-state__content']//h2"));
											if(checkSuccessMsg.getText().equalsIgnoreCase("Post successful!"))
											{
												System.out.println("course shared successfully");
												//driver.close();
												driver.switchTo().window(parentWindow);
											}
										}
									}
								}
							}
						}
						if(sharePost.get(i).getText().equalsIgnoreCase("Send as private message"))
						{
							sharePost.get(i).click();
						}
					}
				}
			}
		}
	}
}
	public String login(String userName, String passWord) throws InterruptedException
	{
		String loginStatus = "failed";
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
		ProcessLogin processLogin = new ProcessLogin(driver);
		loginStatus = processLogin.ErrorMessage();
		if(!loginStatus.equalsIgnoreCase("Failed"))
		{
			loginStatus = "success";
			//loginStatus = this.checkUserAfterLoggedIn();
		}
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
				break;
			}
			else if(driver.getCurrentUrl().contains("dashboard"))
			{
				driver.switchTo().window(windows);
				WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP'] img[class='dPaRoW']"));
				clickDropDown.click();
				Thread.sleep(1000);
				WebElement checkLoggedName = driver.findElement(By.cssSelector("li[class*='SigNUP'] ul[class*='dropdown-menu'] li:nth-child(1) a"));
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
		String parentWindow1 = driver.getWindowHandle();
		Set<String> listOfWindow1 = driver.getWindowHandles();
		for(String windows1 : listOfWindow1)
		{
			driver.switchTo().window(windows1);
			if(driver.getCurrentUrl().contains("interested"))
			{
				driver.switchTo().window(windows1);
				WebElement checkLoggedName = driver.findElement(By.cssSelector("li[class*='SigNUP'] ul[class*='dropdown-menu'] li:nth-child(1) a"));
				if(checkLoggedName.getText().contains("Hello"))
				{
					loginStatus = "Success";
					System.out.println("logged in successfully");
				}
				else
				{
					loginStatus = "Failed";
					System.out.println("not logged in ");
				}
				/*
				 * WebElement clickSignOut = driver.findElement(By.
				 * cssSelector("ul[class*='dropdown-menu Header'] li:nth-child(5) a"));
				 * clickSignOut.click();
				 */
			Thread.sleep(1000);
			break;
		   }
		}
		return loginStatus;
	}
	public ArrayList<String> viewCoursePage()
	{
		ArrayList<String> viewCourseStatus = new ArrayList<String>();
		return null;
		
	}
}
