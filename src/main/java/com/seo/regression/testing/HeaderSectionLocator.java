package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderSectionLocator
{
	WebDriver driver;
	public HeaderSectionLocator(WebDriver driver)
	{
		this.driver = driver;
		OpenWebsite.openSite(driver);
	}
	public String getDriverDetails()
	{
		String driverName =	OpenWebsite.openSite(driver)+"/";
		return driverName;
	}
	public String checkSkillupLogo() throws InterruptedException
	{
		String status = "fail";
		try
		{
			WebElement clickLogo = driver.findElement(By.cssSelector("div[class*='Header_headerLeft'] a img[alt='logo']"));
			//clickLogo.click();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", clickLogo);
			status = "pass";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		//String getHost = OpenWebsite.setURL;
		
		
		return status;
	}

	public String checkContactUs() throws InterruptedException 
	{
		String status = "fail";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement clickContactUs = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navLinks'] li:nth-child(2) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickContactUs));
		String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
		clickContactUs.sendKeys(n);
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		for(String window : nextWindow)
		{
			driver.switchTo().window(window);
			if(driver.getCurrentUrl().contains("contact/"))
			{
				driver.switchTo().window(window);
				System.out.println("contact window");
				status = "pass";
				driver.close();
				status = "success";
				break;
			}
			else if(driver.getCurrentUrl().contains("data"))
			{
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
		{
			status = "success";
		}
		return status;
	}

	public String checkBusiness() 
	{
		String status = "fail";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement clickBusiness = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navLinks'] li:nth-child(1) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickBusiness));
		String getBusinessURL = clickBusiness.getAttribute("href");
		this.checkURLStatus(getBusinessURL);
		clickBusiness.click();
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			driver.switchTo().window(childWindow);
			if(parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("enterprise"))
				{
					status = "success";
					driver.get(getDriverDetails());
					break;
				}
			}
			else if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("enterprise"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("business window");
					status = "success";
					driver.close();
					driver.switchTo().window(parentWindow);
				}
				break;
			}
		}
		if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
		{
			status = "success";
		}
		return status;
	}

	public String checkBlog() throws InterruptedException 
	{
		String status = "fail";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement clickBlog = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navLinks'] li:nth-child(3) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickBlog));
		String getBlogURL = clickBlog.getAttribute("href");
		this.checkURLStatus(getBlogURL);
		clickBlog.click();
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("blog"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("blog window");
					if(driver.getCurrentUrl().contains("blog"))
					{
						System.out.println("In blog window, url changed as stage to blog");
						status = "success";
						driver.get(getDriverDetails());
						Thread.sleep(1000);
						break;
					}
				}	
			}
			else if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("blog"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("blog window");
					status = "success";
					driver.close();
					driver.switchTo().window(parentWindow);
				}
				else if(driver.getCurrentUrl().contains("data"))
				{
					driver.close();
				}
			}
		}
		Thread.sleep(1000);
		return status;
	
	}

	public ArrayList<String> checkCategories(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			WebElement  clickCourseDropdown = driver.findElement(By.cssSelector("div[class=' Header_category__mr_e4']"));
			clickCourseDropdown.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			List<WebElement> selectCourse = driver.findElements(By.cssSelector("ul[aria-labelledby='navbarDropdown'] ul[class='categorylist dropdown-submenu'] li"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			for(int i = 0; i < selectCourse.size(); i++)
			{	
				if(i>0)
				{
					driver.findElement(By.cssSelector("div[class=' Header_category__mr_e4']")).click();
				}
				String categoryName = selectCourse.get(i).findElement(By.cssSelector(" a")).getText();
				if(categoryName.equalsIgnoreCase(data.get(i+1)))
				{
					//driver.findElement(By.cssSelector("div[class=' Header_category__mr_e4']")).click();
					String getCatagoriesURL = selectCourse.get(i).findElement(By.cssSelector(" a")).getAttribute("href");
					String urlLinkStatus = this.checkURLStatus(getCatagoriesURL);
					if(urlLinkStatus.equalsIgnoreCase("fail"))
					{
						status.add(selectCourse.get(i).getText());
					}
					else
					{
						status.add("pass");
					}
					String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
					selectCourse.get(i).findElement(By.cssSelector(" a")).sendKeys(n);
					String parentWindow = driver.getWindowHandle();
					Set<String> windows = driver.getWindowHandles();
					for(String allWindows : windows)
					{
						driver.switchTo().window(allWindows);
						if(!parentWindow.equalsIgnoreCase(allWindows))
						{
							driver.switchTo().window(allWindows);
							System.out.println(driver.getCurrentUrl());
							driver.close();
							driver.switchTo().window(parentWindow);
						}
						else if(driver.getCurrentUrl().contains("data"))
						{
							driver.close();
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

	public ArrayList<String> checkPopularCourses(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			System.out.println("popular course validation started");
			if(!driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
			{
				driver.close();
				driver.get(getDriverDetails());
			}
			else
			{
				System.out.println("host is present");
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
			WebElement clickDropdown = driver.findElement(By.cssSelector("div[class='Header_headerRight__RJT1X'] a#navbarDropdown"));
			clickDropdown.click();
			List<WebElement> popularCourses = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='PolularCourSE catcolumn'] ul[class='MegaMenu_PopularCourse'] li"));
			for(int i = 0; i < popularCourses.size(); i++)
			{
				if(i>0)
				{
					driver.findElement(By.cssSelector("div[class='Header_headerRight__RJT1X'] a#navbarDropdown")).click();
				}
				String popularCourseName = popularCourses.get(i).findElement(By.cssSelector(" p")).getText();
				if(popularCourseName.equalsIgnoreCase(data.get(i+1)))
				{
					
					String getPopularCourseURL = popularCourses.get(i).findElement(By.cssSelector(" a")).getAttribute("href");
					String urlLinkStatus = this.checkURLStatus(getPopularCourseURL);
					if(urlLinkStatus.equalsIgnoreCase("fail"))
					{
						status.add(popularCourses.get(i).getText());
					}
					else
					{
						status.add("pass");
					}
					 String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
					 popularCourses.get(i).findElement(By.cssSelector(" a")).sendKeys(n);
					 String parentWindow = driver.getWindowHandle();
					 Set<String> windows = driver.getWindowHandles();
					 for(String allWindows : windows)
					 {
						 if(!parentWindow.equalsIgnoreCase(allWindows))
						 {
							 driver.switchTo().window(allWindows);
							 System.out.println(driver.getCurrentUrl());
							 driver.close();
							 driver.switchTo().window(parentWindow);
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

	public String checkLogin()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement clickLogin = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navButtons'] li[class*='Header_loginBtn'] a"));
	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navButtons'] li[class*='Header_loginBtn'] a")));
		wait.until(ExpectedConditions.elementToBeClickable(clickLogin));
		String getLoginURL = clickLogin.getAttribute("href");
		this.checkURLStatus(getLoginURL);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", clickLogin);
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(parentWindow.equalsIgnoreCase(childWindow))
			{
				if(driver.getCurrentUrl().contains("login"))
				{
					System.out.println("login window");
					OpenWebsite.openSite(driver);
				}	
				break;
			}
			else if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("login"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("login window");
					driver.close();
					driver.switchTo().window(parentWindow);
				}
				break;
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return driver.getCurrentUrl();
	}

	public String checkSignUP() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement clickSignUp = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] ul[class*='Header_navButtons'] li[class*='Header_signupBtn'] a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickSignUp));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		clickSignUp.click();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
		Thread.sleep(3000);
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(parentWindow.equalsIgnoreCase(childWindow))
			{
				if(driver.getCurrentUrl().contains("register"))
				{
					System.out.println("signup window");
					driver.get(getDriverDetails());
				}	
				break;
			}
			else if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("register"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("contact window");
					driver.close();
					driver.switchTo().window(parentWindow);
				}
				break;
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return driver.getCurrentUrl();
	}
	
	public String checkURLStatus(String getURL)
	{
		String urlStatus = "fail";
		String addHosturl = getURL;
		HttpURLConnection huc = null;
		int respCode = 200;
		try
		{
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			System.out.println("status code : "+respCode + " " +addHosturl);
			if(respCode > 200)
			{
				System.out.println("broken link"+addHosturl);
				urlStatus = "fail";
			}
			else
			{
				System.out.println("un broken link"+addHosturl);
				urlStatus = "pass";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			urlStatus = "fail";
		}
		return urlStatus;
	}
	public ArrayList<String> verifyLearningPartner(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			System.out.println("learning partner validation started");
			WebElement clickDropdown = driver.findElement(By.cssSelector("div[class='Header_headerRight__RJT1X'] a#navbarDropdown"));
			clickDropdown.click();
			List<WebElement> learningPartners = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='LearningPartners catcolumn'] li a"));
			for(int i = 0; i < learningPartners.size();i++)
			{
				if(i>0)
				{
					WebElement clickDropdown1 = driver.findElement(By.cssSelector("div[class='Header_headerRight__RJT1X'] a#navbarDropdown"));
					clickDropdown1.click();
				}
				String learningPartnerName = learningPartners.get(i).getAttribute("href");
					if(learningPartnerName.contains(data.get(i+1)))
					{
						String getLearningPartnerURL = learningPartners.get(i).getAttribute("href");
						String urlLinkStatus = this.checkURLStatus(getLearningPartnerURL);
						if(urlLinkStatus.equalsIgnoreCase("fail"))
						{
							status.add(data.get(i+1));
						}
						else
						{
							status.add("pass");
						}
						String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
						learningPartners.get(i).sendKeys(n);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						String parentWindow = driver.getWindowHandle();
						Set<String> childWnidow = driver.getWindowHandles();
						for(String windows : childWnidow)
						{
							if(!parentWindow.equalsIgnoreCase(windows))
							{
								driver.switchTo().window(windows);
								System.out.println(driver.getCurrentUrl());
								driver.close();
								driver.switchTo().window(parentWindow);
								break;
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
	public String checkSearchCourses(String courseFromExcel)
	{
		String statusOfSearch = "fail";
		try
		{
			WebElement searchCourse = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] form#searchForm input#contentSearch"));
			searchCourse.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
			WebElement typeWord = driver.findElement(By.cssSelector(" ul[class='nav navbar-nav Header_headSearch___BeK7'] input#contentSearch"));
			typeWord.sendKeys(courseFromExcel);
			String search = typeWord.getAttribute("value");
			System.out.println("Entered in search field : "+search);
			WebElement clickSearchIcon = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] button#btnCheck"));
			clickSearchIcon.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
			Thread.sleep(1000);
			if(driver.getCurrentUrl().contains("Courses/?search="))
			{
				System.out.println("searched course displayed in new window");
				Thread.sleep(1000);
				List<WebElement> checkSearchedCourse = driver.findElements(By.cssSelector("div[class='RegularCourseCard_courseHeading__1Ohrn'] p"));
				for(int i = 0; i < checkSearchedCourse.size(); i++)
				{
					String displayedCourse = checkSearchedCourse.get(i).getText();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
					if(displayedCourse.equalsIgnoreCase(courseFromExcel))
					{
						checkSearchedCourse.get(i).click();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						Thread.sleep(1000);
						System.out.println("current url : "+driver.getCurrentUrl());
						statusOfSearch = "success";
						String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
						String subString = StringUtils.substringBefore(getURL, "online/");
						subString = subString+"online";
						driver.get(subString);
						Thread.sleep(1000);
						//driver.close();
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			statusOfSearch = "fail";
			e.printStackTrace();
		}
		return statusOfSearch;
	}
}
