package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPageLocator 
{
	WebDriver driver;
	int respCode = 200;
	HttpURLConnection huc = null;
	public SearchPageLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	public void checkURL(String urlLink)
	{
		try 
		{
			String addHosturl = urlLink;
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			System.out.println("status code : "+respCode + " " +addHosturl);
			if(respCode > 200)
			{
				System.out.println("broken link"+addHosturl);
				System.exit(0);
			}
			else
			{
				System.out.println("un broken link"+addHosturl);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public ArrayList<String> validDataSearchProcess(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfProcess = new ArrayList<String>();
		try
		{
			System.out.println("valid data search started");
			for(int i = 1; i < dataFromExcel.size(); i++)
			{
				WebElement searchBox = driver.findElement(By.cssSelector("ul[class*='Header_headSearch_'] input#contentSearch[value]"));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", searchBox);
				WebDriverWait wb = new WebDriverWait(driver, Duration.ofSeconds(30));
				wb.until(ExpectedConditions.elementToBeClickable(searchBox)).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
				Thread.sleep(1000);
				searchBox.sendKeys(dataFromExcel.get(i));
				//searchBox.clear();
				Thread.sleep(1000);
				if(driver.findElements(By.xpath("//ul[contains(@class,'Header_headSearch_')]/descendant::ul/li")).size()>0)
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
					List<WebElement> checkListOfCourse = driver.findElements(By.xpath("//ul[contains(@class,'Header_headSearch_')]/descendant::ul/li"));
					if(checkListOfCourse.size()>0)
					{
						for(int j = 0; j < checkListOfCourse.size(); j++)
						{
							if(checkListOfCourse.get(j).getText().equalsIgnoreCase(dataFromExcel.get(1)))
							{
								System.out.println("Entered course available");
								//checkListOfCourse.get(j).click();
								((JavascriptExecutor)driver).executeScript("arguments[0].click();", checkListOfCourse.get(j));
								WebElement clickSearchIcon = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] button#btnCheck"));
								clickSearchIcon.click();
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
								String parent = driver.getWindowHandle();
								Set<String> windows = driver.getWindowHandles();
								for(String window : windows)
								{
									driver.switchTo().window(window);
									if(driver.getCurrentUrl().contains("search="))
									{
										System.out.println("results found window");
										this.verifyHeader();
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
										this.verifyFooter();
										JavascriptExecutor js = (JavascriptExecutor) driver;
										js.executeScript("window.scrollBy(0, -700)", "");
										WebElement getCourse = driver.findElement(By.cssSelector("div[class*='RegularCourseCard_courseHeading__1Ohrn'] p"));
										if(getCourse.getText().equalsIgnoreCase(dataFromExcel.get(i)))
										{
											getCourse.click();
											driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
											Thread.sleep(1000);
											String parent1 = driver.getWindowHandle();
											Set<String> windows1 = driver.getWindowHandles();
											for(String window1 : windows1)
											{
												driver.switchTo().window(window1);
												if(driver.getCurrentUrl().contains("/courses/"))
												{
													driver.switchTo().window(window1);
													System.out.println("course is opened");
													Thread.sleep(1000);
													String courseURL = driver.getCurrentUrl();
													System.out.println("course Link : "+courseURL);
													String result = OpenWebsite.setHost;//.substring(0, courseURL.indexOf("/Courses"));
													System.out.println("homepage url : "+result);
													driver.get(result);
													Thread.sleep(1000);
													driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
													break;
												}
											}
										}
									}
								}
							}
							else
							{
								System.out.println("course is not available");
								statusOfProcess.add("fail");
							}
						}
					}
				}
				else
				{
					WebElement clickSearchIcon = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] button#btnCheck"));
					clickSearchIcon.click();
					String getResultText = driver.findElement(By.cssSelector("div[class*='LearningCatalog_titleContent']")).getText();
					if(getResultText.contains("0"))
					{
						System.out.println("course is not available");
						statusOfProcess.add("fail");
					}
					else
					{
						List<WebElement> courseCards = driver.findElements(By.cssSelector("div[class*='RegularCourseCard_RegularcardLinks'] div[class*='RegularCourseCard_courseHeading'] p"));
						for(int j = 0; j < courseCards.size(); j++)
						{
							if(courseCards.get(j).getText().equalsIgnoreCase(dataFromExcel.get(1)))
							{
								courseCards.get(j).click();
								String parent1 = driver.getWindowHandle();
								Set<String> windows1 = driver.getWindowHandles();
								for(String window1 : windows1)
								{
									driver.switchTo().window(window1);
									if(driver.getCurrentUrl().contains("/courses/"))
									{
										driver.switchTo().window(window1);
										System.out.println("course is opened");
										statusOfProcess.add("sucess");
										Thread.sleep(1000);
										String courseURL = driver.getCurrentUrl();
										System.out.println("search course url : "+ courseURL);
										String result = OpenWebsite.setHost;//courseURL.substring(0, courseURL.indexOf("/Courses"));
										System.out.println("homepage url : "+result);
										driver.get(result);
										Thread.sleep(1000);
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
										statusOfProcess.add("sucess");
										break;
									}
								}
							}
						}
					}
				}
			}
			System.out.println("valid data search completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusOfProcess.add("fail");
		}
		return statusOfProcess;
	}
	public ArrayList<String> invalidDataSearchProcess(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfProcess = new ArrayList<String>();
		try
		{
			System.out.println("In valid data search started");
			for(int i = 1; i < dataFromExcel.size(); i++)
			{
				WebElement searchBox = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] form#searchForm input#contentSearch"));
				WebDriverWait wb = new WebDriverWait(driver, Duration.ofSeconds(30));
				wb.until(ExpectedConditions.elementToBeClickable(searchBox)).click();
				searchBox.sendKeys(dataFromExcel.get(i));
				String getValue = searchBox.getAttribute("value");
				System.out.println(getValue);
				WebElement clickSearchIcon = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] button#btnCheck"));
				clickSearchIcon.click();
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String window : windows)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("?search"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						if(driver.findElements(By.cssSelector("div[class='Course_NotiFicaTIon__Anb5F'] li")).size()>0)
						{
							WebElement checkListOfCourse = driver.findElement(By.cssSelector("div[class='Course_NotiFicaTIon__Anb5F'] li"));
							if(checkListOfCourse.isDisplayed())
							{
								System.out.println(checkListOfCourse.getText());
							}
						}
						else
						{
							System.out.println("zero result found");
							break;
						}
					}
				}
				this.verifyHeader();
				/*
				 * List<WebElement> exploreAll = driver.findElements(By.
				 * cssSelector("div[class='Collaborate_excollaborationInner__m_SiL'] ul li a"));
				 * for(int j = 0; j < exploreAll.size(); j++) { if(exploreAll.size() == 5) {
				 * System.out.println("explore All link : "+exploreAll.get(j).getAttribute(
				 * "href")); } else {
				 * System.out.println("explore All size is not same : "+exploreAll.get(j).
				 * getAttribute("href")); } }
				 */
				this.verifyFooter();
				Thread.sleep(1000);
			//	String courseURL = driver.getCurrentUrl();
				String result = OpenWebsite.setHost;//courseURL.substring(0, courseURL.indexOf("/Courses"));
				System.out.println("homepage url : "+result);
				driver.get(result);
				Thread.sleep(1000);
			}
			System.out.println("Invalid data search completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfProcess;
	}
	public ArrayList<String> emptySearchProcess(String dataFromExcel)
	{
		ArrayList<String> statusOfProcess = new ArrayList<String>();
		try
		{
			System.out.println("empty search started");
			WebElement searchBox = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] form#searchForm input#contentSearch"));
			WebDriverWait wb = new WebDriverWait(driver, Duration.ofSeconds(30));
			wb.until(ExpectedConditions.elementToBeClickable(searchBox)).click();
			Thread.sleep(3000);
			searchBox.sendKeys(dataFromExcel);
			String getValue = searchBox.getAttribute("value");
			System.out.println(getValue);
			if(dataFromExcel.equalsIgnoreCase("empty"))
			{
				searchBox.sendKeys("");
				WebElement clickSearchIcon = driver.findElement(By.cssSelector("ul[class='nav navbar-nav Header_headSearch___BeK7'] button#btnCheck"));
				clickSearchIcon.click();
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String window : windows)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("?search"))
					{
						driver.switchTo().window(window);
						if(driver.findElements(By.cssSelector("div[class='Course_NotiFicaTIon__Anb5F'] li")).size()>0)
						{
							WebElement checkListOfCourse = driver.findElement(By.cssSelector("div[class='Course_NotiFicaTIon__Anb5F'] li"));
							if(checkListOfCourse.isDisplayed())
							{
								System.out.println(checkListOfCourse.getText());
							}
						}
						else
						{
							System.out.println("zero result found");
							break;
						}
					}
				}
			}
			this.verifyHeader();
			/*
			 * List<WebElement> exploreAll = driver.findElements(By.
			 * cssSelector("div[class='Collaborate_excollaborationInner__m_SiL'] ul li a"));
			 * for(int j = 0; j < exploreAll.size(); j++) { if(exploreAll.size() == 5) {
			 * System.out.println("explore All link : "+exploreAll.get(j).getAttribute(
			 * "href")); } else {
			 * System.out.println("explore All size is not same : "+exploreAll.get(j).
			 * getAttribute("href")); } }
			 */
			this.verifyFooter();
			Thread.sleep(1000);
			//String courseURL = driver.getCurrentUrl();
			String result = OpenWebsite.setHost;//courseURL.substring(0, courseURL.indexOf("/Courses"));
			System.out.println("homepage url : "+result);
			driver.get(result);
			Thread.sleep(1000);
			System.out.println("empty data search completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfProcess;
	}
	
	public void verifyHeader()
	{
		WebElement dropDown = driver.findElement(By.cssSelector("a#navbarDropdown img[alt=icon]"));
		dropDown.click();
		List<WebElement> categories = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] ul[class='categorylist dropdown-submenu'] li a"));
		for(int i = 0; i < categories.size(); i++)
		{
			if(categories.size() == 14)
			{
				System.out.println(" categories "+categories.get(i).getText());
			}
			else
			{
				System.out.println("categories size is not 14 : "+categories.get(i).getText());
			}
		}
		List<WebElement> learningPartners = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='LearningPartners catcolumn'] ul[class='learning-Partners'] li a"));
		for(int j = 0; j < learningPartners.size(); j++)
		{
			if(learningPartners.size() == 5)
			{
				System.out.println(" learningPartners "+learningPartners.get(j).getAttribute("href"));
			}
			else
			{
				System.out.println("learningPartners size is not 5 : "+learningPartners.get(j).getText());
			}
		}
		
		List<WebElement> popularCourses = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='PolularCourSE catcolumn'] ul[class='MegaMenu_PopularCourse'] li a"));
		for(int k = 0; k < popularCourses.size(); k++)
		{
			if(popularCourses.size() == 4)
			{
				System.out.println(" popularCourses "+popularCourses.get(k).getText());
			}
			else
			{
				System.out.println("popularCourses size is not 4 : "+popularCourses.get(k).getText());
			}
		}
		dropDown.click();
	}
	
	public void verifyFooter()
	{
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		List<WebElement> shareSocialLink = driver.findElements(By.cssSelector("ul[class=' Footer_socialIconsSection__5DztA d-flex'] li a"));
		for(int l = 0; l < shareSocialLink.size(); l++)
		{
			if(shareSocialLink.size() == 5)
			{
				System.out.println("social links : "+shareSocialLink.get(l).getAttribute("href"));
			}
			else
			{
				System.out.println("social links size not match: "+shareSocialLink.get(l).getText());
			}
		}
		
		List<WebElement> company = driver.findElements(By.cssSelector("div[class='Footer_FootMenu__4fwEE'] ul li a"));
		for(int m = 0; m < company.size(); m++)
		{
			if(company.size() == 6)
			{
				System.out.println("company links : "+company.get(m).getText());
			}
			else
			{
				System.out.println("company links size not match: "+company.get(m).getText());
			}
		}
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
	     js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		List<WebElement> popularCategory = driver.findElements(By.cssSelector("div[class='Footer_PopularCategories__23uL0'] ul li a"));
		for(int n = 0; n < popularCategory.size(); n++)
		{
			if(popularCategory.size() == 14)
			{
				System.out.println("popularCategory links : "+popularCategory.get(n).getText());
			}
			else
			{
				System.out.println("popularCategory links size not match: "+popularCategory.get(n).getText());
			}
		}
		
		List<WebElement> popularCoursesLink = driver.findElements(By.cssSelector("div[class='Footer_PopularCourses__Yc9Ft'] ul li"));
		for(int o = 0; o < popularCoursesLink.size(); o++)
		{
			if(popularCoursesLink.size() == 4)
			{
				System.out.println("popularCategory links : "+popularCoursesLink.get(o).getText());
			}
			else
			{
				System.out.println("popularCategory links size not match: "+popularCoursesLink.get(o).getText());
			}
		}
		
		List<WebElement> blog = driver.findElements(By.cssSelector("div[class='Footer_LatestBlogs__QZ7i4'] div[class='Footer_LatestBlogsRepT__F2CHs'] a"));
		for(int p = 0; p < blog.size(); p++)
		{
			if(blog.size() == 3)
			{
				System.out.println("blog links : "+blog.get(p).getAttribute("href"));
			}
			else
			{
				System.out.println("blog links size not match: "+blog.get(p).getText());
			}
		}
	}
}
