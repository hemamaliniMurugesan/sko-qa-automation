package com.seo.regression.testing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExploreAllLocator
{
	WebDriver driver;
	public ExploreAllLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public ArrayList<String>  checkExploreAll()
	{
		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 800)", "");
			WebElement clickExploreAllIcon = driver.findElement(By.cssSelector("div[class*='Courses_circleBgContainer'] button[class='btn shadow-none LearningCatalog_exploreAllButton__KElSJ false']"));
			wait.until(ExpectedConditions.visibilityOf(clickExploreAllIcon));
			js.executeScript("arguments[0].click()", clickExploreAllIcon);
			String parentWindow = driver.getWindowHandle();
			Set<String> window = driver.getWindowHandles();
			for(String allwindows : window)
			{
				driver.switchTo().window(allwindows);
				if(driver.getCurrentUrl().contains("explore"))
				{
					driver.switchTo().window(allwindows);
					System.out.println("Explore all from home page");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status.add("home");
		}
		try
		{
			WebElement clickMegaMenu = driver.findElement(By.cssSelector("div[class*='Header_headerRight'] a[id='navbarDropdown'] div[class*=' Header_category'] img[alt='icon']"));
			clickMegaMenu.click();
			WebElement clickExploreAll = driver.findElement(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='MainCatE'] li[class='exploreAll'] a"));
			clickExploreAll.click();
			String parentWindow = driver.getWindowHandle();
			Set<String> window = driver.getWindowHandles();
			for(String allwindows : window)
			{
				driver.switchTo().window(allwindows);
				if(driver.getCurrentUrl().contains("explore"))
				{
					driver.switchTo().window(allwindows);
					System.out.println("Explore all from home page");
					driver.get(OpenWebsite.setURL);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status.add("mega");
		}
		return status;
	}
	
	public String verifyActiveCategoriesOnHomePage()
	{
		String status = "";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
		try
		{
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 800)", "");
			
			
			WebElement activeCategory = driver.findElement(By.cssSelector("div[class*='LearningCatalog_courseList'] button[class*='btn shadow-none LearningCatalog_activeButton']"));
			String activeCategoryName = activeCategory.getText();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			WebElement clickExploreAllIcon = driver.findElement(By.cssSelector("div[class*='Courses_circleBgContainer'] button[class='btn shadow-none LearningCatalog_exploreAllButton__KElSJ false']"));
			wait.until(ExpectedConditions.visibilityOf(clickExploreAllIcon));
			js.executeScript("arguments[0].click()", clickExploreAllIcon);
			
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String windows : allWindows)
			{
				driver.switchTo().window(windows);
				if(driver.getCurrentUrl().contains("/explore-our-catalog/?"))
				{
					driver.switchTo().window(windows);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
					System.out.println("Explore all catalog page");
					WebElement baseLocatorForResult = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer'] section[class*='CourseSection_topFilterSection']"));
					WebElement locateSelectedCategory = baseLocatorForResult.findElement(By.cssSelector(" div[class*='CourseSection_resultsItemsBox'] p"));
					String getCategoryName = locateSelectedCategory.getText();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
					if(getCategoryName.equalsIgnoreCase(activeCategoryName))
					{
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
						System.out.println("active category is same as in Explore All page");
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
						status = "pass";
					}
					else
					{
						status = "fail";
					}
				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
			
		return status;
	}
		
	public ArrayList<String> nextPageCourses()
	{
		ArrayList<String> status = new ArrayList<String>();
		int repeat = 0;
		String getURL = null;
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			List<WebElement> clickNextPage = driver.findElements(By.cssSelector("div[class='col-12 d-flex justify-content-center mt-5'] ul[class='pagination justify-content-center'] li[class='page-item false'] a[aria-label*='Page']"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			if(clickNextPage.size()>0)
			{
				Thread.sleep(2000);
				for(int i = 0; i < clickNextPage.size(); i++)
				{
					Thread.sleep(1000);
					if(clickNextPage.get(i).isDisplayed())
					{
						Thread.sleep(1000);
						clickNextPage.get(i).click();
						Thread.sleep(1000);
						List<WebElement> listOfNextPageCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class='CourseSection_courseResult__byBMX ps-3'] a"));
						for(int k = 0; k < listOfNextPageCourses.size(); k++)
						{
							while(repeat <= 3)
								try
							{
									getURL = listOfNextPageCourses.get(k).getAttribute("href");
									break;
							}
							catch(StaleElementReferenceException e)
							{
								e.printStackTrace();
							}
							String getURLLinkStatus = this.checkURLStatus(getURL);
							if(getURLLinkStatus.equalsIgnoreCase("fail"))
							{
								status.add(listOfNextPageCourses.get(k).getAttribute("href"));
							}
							Thread.sleep(1000);
						}
					}
				}
				
			}
			else
			{
				System.out.println("next page is not shown");
			}
		}
		catch(Exception e)
		{
			System.out.println("Next Page not availables");
		}
		return status;
	}
	
	public String checkURLStatus(String getURL) throws IOException
	{
		String status = "fail";
		String addHosturl = getURL;
		HttpURLConnection huc = null;
		int respCode = 200;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
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
				status = "fail";
			}
			else
			{
				//System.out.println("un broken link"+addHosturl);
				status = "pass";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public ArrayList<String> verifyClearAllAtExploreAllPage()
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			WebElement baseLocatorForClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
			WebElement clickClearAll = baseLocatorForClearAll.findElement(By.cssSelector(" div[class*='CourseSection_filterSection'] div[class*='flex CourseSection_filterTop'] button"));
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
			js.executeScript("arguments[0].scrollIntoView(true);", clickClearAll);
			
			if(clickClearAll.isDisplayed())
			{
				System.out.println("clear All icon is shown");
				Thread.sleep(2000);
				js.executeScript("arguments[0].click()", clickClearAll);
				Thread.sleep(2000);
				WebElement baseLocatorForResult = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer']"));
				WebElement checkResult = baseLocatorForResult.findElement(By.cssSelector(" div[class*='CourseSection_topFilterResults'] p"));
				
				if(checkResult.isDisplayed())
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
					String getTextFromResult = checkResult.getText();
					System.out.println("REsult : "+getTextFromResult);
					if(getTextFromResult.contains("Results"))
					status.add("pass");
				}
				else
				{
					status.add("result");
				}
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

				WebElement alertContent = baseLocatorForResult.findElement(By.cssSelector(" div[class*='CourseSection_alertContent'] h3"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
				
				if(alertContent.isDisplayed())
				{ 
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
					String getTextFromAlertContent = alertContent.getText();
					System.out.println("help content : "+getTextFromAlertContent);
					if(getTextFromAlertContent.contains("Help"))
					status.add("pass");
				}
				else
				{
					status.add("content");
				}
				 
				System.out.println("clear all icon is clicked");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status.add("fail");
		}
		return status;
	}
	
	public ArrayList<String> verify_CategoriesFromExploreAllPage()
	{
		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try
		{
			try
			{
				WebElement baseLocatorForClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
				List<WebElement> listOfCategories = baseLocatorForClearAll.findElements(By.cssSelector(" div[class='accordion']:nth-child(1) div[class='accordion-collapse collapse show'] div[class*='CourseSection_checkbox'] input"));
				wait.until(ExpectedConditions.visibilityOfAllElements(listOfCategories));
				
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(" div[class='accordion']:nth-child(1) div[class='accordion-collapse collapse show'] div[class*='CourseSection_checkbox'] input"))));
				for(int i = 0; i < listOfCategories.size(); i++)
				{
					
					JavascriptExecutor jse3 =  (JavascriptExecutor) driver;
					
					WebElement category = listOfCategories.get(i);
					
					if(i >= 4)
					{
						jse3.executeScript("window.scrollBy(0, 200)", "");
					}
					Point position = category.getLocation();
					
					jse3.executeScript("window.scrollBy("+position.x+", "+(position.y -100)+")","");
					Thread.sleep(2000);
					System.out.println(position.x + " -- " + (position.y-100));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					
					while(!category.isDisplayed())
					{
						jse3.executeScript("window.scrollBy(0, 100)","");
						wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(category)));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
						wait.until(ExpectedConditions.elementToBeClickable(category));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					}
					
					jse3.executeScript("arguments[0].click()", category);
					
					Thread.sleep(1000);
					
					if(!category.isSelected())
					{
						category.click();
						Thread.sleep(1000);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					System.out.println(category.getAttribute("id"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					status.add("pass");
					try
					{
						List<WebElement> listOfCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] a"));
						
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
						
						int j;
						for(j = 0; j < listOfCourses.size(); j++)
						{
							System.out.println("course name :   "+listOfCourses.get(j).getAttribute("href"));
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
							if(j == listOfCourses.size()-1)
							{
								Thread.sleep(2000);
								JavascriptExecutor jse1 = (JavascriptExecutor) driver;
								jse1.executeScript("window.scrollBy(0, 1700)","");
								Thread.sleep(3000);
								
								//jse1.executeScript("window.scrollBy(0, -100)","");
								
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
								List<WebElement> clickNextPage = driver.findElements(By.cssSelector("div[class='col-12 d-flex justify-content-center mt-5'] ul[class='pagination justify-content-center'] li[class='page-item false'] a[aria-label*='Page']"));
								
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
								
								if(clickNextPage.size()>0)
								{
									Thread.sleep(2000);
									wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[class='col-12 d-flex justify-content-center mt-5'] ul[class='pagination justify-content-center'] li[class='page-item false'] a[aria-label*='Page']")));
									for(int l = 0; l < clickNextPage.size(); l++)
									{
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
										while(!clickNextPage.get(l).isDisplayed())
										{
//											driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
											wait.until(ExpectedConditions.elementToBeClickable(clickNextPage.get(l)));
											jse1.executeScript("window.scrollBy(0,150)","");
											jse1.executeScript("arguments[0].scrollIntoView(true);", clickNextPage.get(l));
											driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
										}
										Thread.sleep(4000);
										clickNextPage.get(l).click();
										Thread.sleep(3000);
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
										System.out.println("next page : " +l);
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
										
										List<WebElement> listOfNextPageCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class='CourseSection_courseResult__byBMX ps-3'] a"));
										for(int k = 0; k < listOfNextPageCourses.size(); k++)
										{
											JavascriptExecutor js = (JavascriptExecutor) driver;
											js.executeScript("arguments[0].scrollIntoView(true);", listOfNextPageCourses.get(k));
											wait.until(ExpectedConditions.visibilityOf(listOfNextPageCourses.get(k)));
											
											System.out.println("next page courses : "+listOfNextPageCourses.get(k).getAttribute("href"));
										}
										Thread.sleep(1000);
									}
									
								}
								else
								{
									System.out.println("no next page");
								}
						    }
							status.add("pass");
					  }
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("issue in courses");
						status.add("course");
					}
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0, -document.body.scrollHeight)","");
					
					Thread.sleep(1000);
					
					WebElement baseLocator_ClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
					WebElement click_ClearAll = baseLocator_ClearAll.findElement(By.cssSelector(" div[class*='CourseSection_filterSection'] div[class*='flex CourseSection_filterTop'] button"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					
					if(click_ClearAll.isDisplayed())
					{
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						wait.until(ExpectedConditions.elementToBeClickable(click_ClearAll));
						Thread.sleep(1000);

						click_ClearAll.click();
						
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("issue in category");
				status.add("category");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public ArrayList<String> verify_LevelFromExploreAll()
	{
		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try
		{
			try
			{	JavascriptExecutor jse3 =  (JavascriptExecutor) driver;
				WebElement baseLocator = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
				Thread.sleep(2000);
				jse3.executeScript("window.scrollBy(0, 700)", "");
				Thread.sleep(2000);
				WebElement clickLevels = baseLocator.findElement(By.cssSelector(" div[class='accordion']:nth-child(2) button"));
				clickLevels.click();
				Thread.sleep(2000);
				while(!clickLevels.isDisplayed())
				{
					clickLevels.click();
				}
				
				List<WebElement> listOfLevels = baseLocator.findElements(By.cssSelector(" div[class='accordion']:nth-child(2) div[class*='CourseSection_checkbox'] input"));
				wait.until(ExpectedConditions.visibilityOfAllElements(listOfLevels));
				for(int i = 0; i < listOfLevels.size(); i++)
				{
					WebElement level = listOfLevels.get(i);
					
					Point position = level.getLocation();
					
					jse3.executeScript("window.scrollBy("+position.x+", "+(position.y -100)+")","");
					Thread.sleep(2000);
					System.out.println(position.x + " -- " + (position.y-100));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					
					Thread.sleep(2000);
					jse3.executeScript("arguments[0].click()", level);
					
					Thread.sleep(1000);
					
					if(!level.isSelected())
					{
						level.click();
						Thread.sleep(1000);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					System.out.println(level.getAttribute("id"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					status.add("pass");
					try
					{
						Thread.sleep(3000);
						List<WebElement> listOfCourses = driver.findElements(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] div[class*='RegularCourseCard_courseHeading'] p"));
						System.out.println("number of courses from levels : "+listOfCourses.size());
						
						List<WebElement> clickNextPage = driver.findElements(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li"));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
						if(clickNextPage.size()>0)
						{
							Thread.sleep(2000);
							wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li")));
							
							for(int l = 0; l < clickNextPage.size(); l++)
							{
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("arguments[0].scrollIntoView(true);", clickNextPage.get(l));
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("window.scrollBy(0,-200)","");
								Thread.sleep(3000);
								jse3.executeScript("arguments[0].click()", clickNextPage.get(l));
								Thread.sleep(3000);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
								System.out.println("next page : " +l);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								Thread.sleep(3000);
								List<WebElement> listOfNextPageCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class='CourseSection_courseResult__byBMX ps-3'] a"));
								System.out.println("next page number of courses : "+listOfNextPageCourses.size());
								Thread.sleep(3000);
							}
									
						}
						else
						{
							System.out.println("no next page");
						}
						status.add("pass");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("issue in courses");
						status.add("course");
					}
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0, -document.body.scrollHeight)","");
					
					Thread.sleep(1000);
					
					WebElement baseLocator_ClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
					WebElement click_ClearAll = baseLocator_ClearAll.findElement(By.cssSelector(" div[class*='CourseSection_filterSection'] div[class*='flex CourseSection_filterTop'] button"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					
					if(click_ClearAll.isDisplayed())
					{
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						wait.until(ExpectedConditions.elementToBeClickable(click_ClearAll));
						Thread.sleep(1000);

						click_ClearAll.click();
						
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("issue in levels");
				status.add("level");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public ArrayList<String> verify_learningPartnersFromExploreAll()
	{

		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try
		{
			try
			{	JavascriptExecutor jse3 =  (JavascriptExecutor) driver;
				WebElement baseLocator = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
				Thread.sleep(2000);
				jse3.executeScript("window.scrollBy(0, 700)", "");
				Thread.sleep(2000);
				WebElement clickLevels = baseLocator.findElement(By.cssSelector(" div[class='accordion']:nth-child(3) button"));
				clickLevels.click();
				Thread.sleep(2000);
				while(!clickLevels.isDisplayed())
				{
					clickLevels.click();
				}
				
				List<WebElement> listOfLevels = baseLocator.findElements(By.cssSelector(" div[class='accordion']:nth-child(3) div[class*='CourseSection_checkbox'] input"));
				wait.until(ExpectedConditions.visibilityOfAllElements(listOfLevels));
				for(int i = 0; i < listOfLevels.size(); i++)
				{
					WebElement level = listOfLevels.get(i);
					
					Point position = level.getLocation();
					
					jse3.executeScript("window.scrollBy("+position.x+", "+(position.y -100)+")","");
					Thread.sleep(2000);
					System.out.println(position.x + " -- " + (position.y-100));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					
					Thread.sleep(2000);
					jse3.executeScript("arguments[0].click()", level);
					
					Thread.sleep(1000);
					
					if(!level.isSelected())
					{
						level.click();
						Thread.sleep(1000);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					System.out.println(level.getAttribute("id"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					status.add("pass");
					try
					{
						Thread.sleep(3000);
						List<WebElement> listOfCourses = driver.findElements(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] div[class*='RegularCourseCard_courseHeading'] p"));
						System.out.println("number of courses from levels : "+listOfCourses.size());
						
						List<WebElement> clickNextPage = driver.findElements(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li"));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
						if(clickNextPage.size()>0)
						{
							Thread.sleep(2000);
							wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li")));
							
							for(int l = 0; l < clickNextPage.size(); l++)
							{
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("arguments[0].scrollIntoView(true);", clickNextPage.get(l));
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("window.scrollBy(0,-200)","");
								Thread.sleep(3000);
								jse3.executeScript("arguments[0].click()", clickNextPage.get(l));
								Thread.sleep(3000);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
								System.out.println("next page : " +l);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								Thread.sleep(3000);
								List<WebElement> listOfNextPageCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class='CourseSection_courseResult__byBMX ps-3'] a"));
								System.out.println("next page number of courses : "+listOfNextPageCourses.size());
								Thread.sleep(3000);
							}
									
						}
						else
						{
							System.out.println("no next page");
						}
						status.add("pass");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("issue in courses");
						status.add("course");
					}
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0, -document.body.scrollHeight)","");
					
					Thread.sleep(1000);
					
					WebElement baseLocator_ClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
					WebElement click_ClearAll = baseLocator_ClearAll.findElement(By.cssSelector(" div[class*='CourseSection_filterSection'] div[class*='flex CourseSection_filterTop'] button"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					
					if(click_ClearAll.isDisplayed())
					{
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						wait.until(ExpectedConditions.elementToBeClickable(click_ClearAll));
						Thread.sleep(1000);

						click_ClearAll.click();
						
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("issue in partner");
				status.add("partner");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	
		
	}
	public ArrayList<String> verify_learningStylesFromExploreAll()
	{

		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try
		{
			try
			{	JavascriptExecutor jse3 =  (JavascriptExecutor) driver;
				WebElement baseLocator = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
				Thread.sleep(2000);
				jse3.executeScript("window.scrollBy(0, 700)", "");
				Thread.sleep(2000);
				WebElement clickLevels = baseLocator.findElement(By.cssSelector(" div[class='accordion']:nth-child(4) button"));
				clickLevels.click();
				Thread.sleep(2000);
				while(!clickLevels.isDisplayed())
				{
					clickLevels.click();
				}
				
				List<WebElement> listOfLevels = baseLocator.findElements(By.cssSelector(" div[class='accordion']:nth-child(4) div[class*='CourseSection_checkbox'] input"));
				wait.until(ExpectedConditions.visibilityOfAllElements(listOfLevels));
				for(int i = 0; i < listOfLevels.size(); i++)
				{
					WebElement level = listOfLevels.get(i);
					
					Point position = level.getLocation();
					
					jse3.executeScript("window.scrollBy("+position.x+", "+(position.y -100)+")","");
					Thread.sleep(2000);
					System.out.println(position.x + " -- " + (position.y-100));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					
					Thread.sleep(2000);
					jse3.executeScript("arguments[0].click()", level);
					
					Thread.sleep(1000);
					
					if(!level.isSelected())
					{
						level.click();
						Thread.sleep(1000);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					System.out.println(level.getAttribute("id"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					status.add("pass");
					try
					{
						Thread.sleep(3000);
						List<WebElement> listOfCourses = driver.findElements(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] div[class*='RegularCourseCard_courseHeading'] p"));
						System.out.println("number of courses from levels : "+listOfCourses.size());
						
						List<WebElement> clickNextPage = driver.findElements(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li"));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
						if(clickNextPage.size()>0)
						{
							Thread.sleep(2000);
							wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[class*='col-12 d-flex justify-content-center'] ul[class='pagination justify-content-center'] li")));
							
							for(int l = 0; l < clickNextPage.size(); l++)
							{
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("arguments[0].scrollIntoView(true);", clickNextPage.get(l));
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								jse3.executeScript("window.scrollBy(0,-200)","");
								Thread.sleep(3000);
								jse3.executeScript("arguments[0].click()", clickNextPage.get(l));
								Thread.sleep(3000);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
								System.out.println("next page : " +l);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
								Thread.sleep(3000);
								List<WebElement> listOfNextPageCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class='CourseSection_courseResult__byBMX ps-3'] a"));
								System.out.println("next page number of courses : "+listOfNextPageCourses.size());
								Thread.sleep(3000);
							}
									
						}
						else
						{
							System.out.println("no next page");
						}
						status.add("pass");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("issue in courses");
						status.add("course");
					}
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0, -document.body.scrollHeight)","");
					
					Thread.sleep(1000);
					
					WebElement baseLocator_ClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
					WebElement click_ClearAll = baseLocator_ClearAll.findElement(By.cssSelector(" div[class*='CourseSection_filterSection'] div[class*='flex CourseSection_filterTop'] button"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					
					if(click_ClearAll.isDisplayed())
					{
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						wait.until(ExpectedConditions.elementToBeClickable(click_ClearAll));
						Thread.sleep(1000);

						click_ClearAll.click();
						
						Thread.sleep(500);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("issue in style");
				status.add("style");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	
		
	}
	public ArrayList<String> verify_sortByFromExploreAll()
	{
		ArrayList<String> status = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor jse3 =  (JavascriptExecutor) driver;
		try
		{
			WebElement baseLocatorForClearAll = driver.findElement(By.cssSelector("section#scrollToTop div[class*='CourseSection_filterMain']:not([id='mobileFilter'])"));
			List<WebElement> listOfCategories = baseLocatorForClearAll.findElements(By.cssSelector(" div[class='accordion']:nth-child(1) div[class='accordion-collapse collapse show'] div[class*='CourseSection_checkbox'] input"));
			
			wait.until(ExpectedConditions.visibilityOfAllElements(listOfCategories));
			
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(" div[class='accordion']:nth-child(1) div[class='accordion-collapse collapse show'] div[class*='CourseSection_checkbox'] input"))));

			for(int i = 0; i < listOfCategories.size(); i++)
			{
				if(i == 0)
				{
					listOfCategories.get(i).click();
					break;
				}
			}
			
			WebElement clickSortBy = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='dropdown CourseSection_topFilterSorting'] button"));
			clickSortBy.click();
			try
			{
				WebElement selectHigh = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='dropdown CourseSection_topFilterSorting'] ul[class*='dropdown-menu'] li[value='DESC']"));
				selectHigh.click();
				List<WebElement> listOfCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] a"));
				for(int j = 0; j < listOfCourses.size(); j++)
				{
					System.out.println("list of courses from high to low : "+listOfCourses.get(j).getAttribute("href"));
				}
			}
			catch(Exception e)
			{
				status.add("high");
			}
			try
			{
				WebElement clickSortByIcon = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='dropdown CourseSection_topFilterSorting'] button"));
				clickSortByIcon.click();
				WebElement selectHigh = driver.findElement(By.cssSelector("div[class*='CourseSection_courseResultContainer'] div[class*='dropdown CourseSection_topFilterSorting'] ul[class*='dropdown-menu'] li[value='ASC']"));
				selectHigh.click();
				List<WebElement> listOfCourses = driver.findElements(By.cssSelector("section#scrollToTop div[class*='CourseSection_courseResultContainer'] div[class*='CourseSection_courseResult'] a"));
				for(int j = 0; j < listOfCourses.size(); j++)
				{
					System.out.println("list of courses from low to high : "+listOfCourses.get(j).getAttribute("href"));
				}
			}
			catch(Exception e)
			{
				status.add("low");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
}
