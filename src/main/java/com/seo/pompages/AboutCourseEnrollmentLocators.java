package com.seo.pompages;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;

public class AboutCourseEnrollmentLocators 
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Skillup 200\\Downloads\\chromedriver_win32_101.0.4951.41version\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	
	public void loginSEO() throws InterruptedException
	{
		
		driver.get(ConfigFileReader.getSEOLoginURL());//open login page 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		Thread.sleep(1000);
		WebElement clickLoginIcon = driver.findElement(By.xpath("//a[contains(text(), \"LOGIN\")]"));
		clickLoginIcon.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 200)");
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> itr = allWindows.iterator();
		while(itr.hasNext())
		{
			String childWindow = itr.next();
			driver.switchTo().window(childWindow);
			String checkLoginScreen = driver.findElement(By.xpath("//h4[contains(text(),\"Log\")]")).getText();
			System.out.println(checkLoginScreen);
			WebElement emailText = driver.findElement(By.id("email"));
			emailText.sendKeys(ConfigFileReader.getUsername());
			WebElement pwd = driver.findElement(By.id("password"));
			pwd.sendKeys(ConfigFileReader.getPassword());
			WebElement LoginButton = driver.findElement(By.id("login_in"));
			LoginButton.click();
			try
			{
				List<WebElement> checkAlert = driver.findElements(By.cssSelector("div[class=\"NotificationTypeError spacing-mb16 status message submission-error is-shown\"]"));
				if(checkAlert.size() != 0)
				{
					System.out.println("Validation message is displayed");
				}
				else
				{
					System.out.println("Login successfully");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public String getCourseCodeText(String code) throws InterruptedException
	{
		loginSEO();
		//String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		try
		{
			String getEnrollCourseURL = ConfigFileReader.getAboutCourseURL()+code+"about";
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.get(getEnrollCourseURL);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
			if(driver.getPageSource().contains(code))
			{
				CourseCodeStatus = "true";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CourseCodeStatus;
	}
	
	/**
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	@SuppressWarnings("deprecation")
	public String enrollCourse() throws InterruptedException
	{
		String payMentStatus = "";
		Thread.sleep(1000);
		try
		{
			if(driver.findElements(By.xpath("//a[contains(text(),\"Enrollment is Closed\")]")).size() > 0)
			{
				System.out.println("Unable to enroll this course, because enrollment is closed");
				payMentStatus = "fail";
			}
			else if(driver.findElements(By.xpath("//p[contains(text(),\"Enrolled\")]")).size() > 0)
			{
				System.out.println("Course is enrolled");
				payMentStatus = "fail";
			}
			//parent page
			else if(driver.findElements(By.xpath("//div[@class=\"ConTianer_ConTent\"]//a[contains(text(),\"Enroll now\")]")).size() > 0)// = dev, QA, Prod, US
			{	
				payMentStatus = "";
				Thread.sleep(1000);
				
				if(driver.getCurrentUrl().contains("-in.")||driver.getCurrentUrl().contains("in.")) //dev||QA||Prod
				{
					JavascriptExecutor scr = (JavascriptExecutor)driver;
					scr.executeScript("window.scrollBy(0, 400)");
					WebElement clickEnrollIcon = driver.findElement(By.xpath("//div[@class=\"ConTianer_ConTent\"]//a[contains(text(),\"Enroll now\")]"));
					System.out.println("Enrollment is present");
					if(clickEnrollIcon.isDisplayed())
					{
						clickEnrollIcon.click();
						Thread.sleep(1000);
						String parentCoursePage = driver.getWindowHandle();//1st page
						Set<String> popup = driver.getWindowHandles(); //it shows 2 popup
						Iterator<String> iterate1 = popup.iterator();
						while(iterate1.hasNext())
						{ //open iterate for 1st enroll button popup
							if(!parentCoursePage.equalsIgnoreCase(iterate1.next()))
							{ // open condition for navigating to next window
								driver.switchTo().window(iterate1.next());//1st popup
								JavascriptExecutor js = (JavascriptExecutor)driver;
								js.executeScript("window.scrollBy(0, 200)");//to scroll up for to view plans in the popup
							}
						}
					}
					if(driver.findElements(By.xpath("//h4[contains(text(),\"Select a plan to continue\")]")).size() != 0) //plan
					{
						System.out.println("select a plan to continue pop up");
						List<WebElement> plans = driver.findElements(By.cssSelector("div#custom-plans1 div[class=\"owl-item active\"]"));
						for(int i = 0; i < plans.size(); i++)
						{
							WebElement getplan = plans.get(i);
							WebElement planName = getplan.findElement(By.cssSelector(" div[class*=\"plan_heading\"]"));
							String getPlanName = planName.getText();
							System.out.println(getPlanName);
							if(getPlanName.equalsIgnoreCase(ConfigFileReader.getPlan()))//validating plan
							{
								WebElement planPrize = plans.get(i).findElement(By.cssSelector(" h5[class*=\"prize\"]:not([class=\"prize_color discount\"])"));
								String getPrizeText = planPrize.getText();
								System.out.println(getPrizeText);
								WebElement clickSelectPlanButton = plans.get(i).findElement(By.cssSelector(" div[class=\"bttn\"] a"));
								clickSelectPlanButton.click();
								Thread.sleep(1000);
							}//end of plan selection
							break;
						}
					}
					//razorayPayment
					JavascriptExecutor js2 = (JavascriptExecutor)driver;
					js2.executeScript("window.scrollBy(0, 500)");
					String planWindow = driver.getWindowHandle();
					Set<String> secondWindow = driver.getWindowHandles();
					Iterator<String> iterate2 = secondWindow.iterator();
					while(iterate2.hasNext())
					{
						if(!planWindow.equalsIgnoreCase(iterate2.next()))
						{
							driver.switchTo().window(iterate2.next());//2nd popup after plan selection from 1st popup page
							WebElement clickRazorayPayment = driver.findElement(By.cssSelector("#razorpay")); // this page is common to INR and US 
							clickRazorayPayment.click();
							Thread.sleep(1000);
						}
					}
					if(driver.getCurrentUrl().contains("https://api.razorpay.com/v1/checkout/embedded")) //payment without iframes
					{
						String razorayPaymentPage = driver.getWindowHandle();
						Set<String> thirdWindow = driver.getWindowHandles();
						Iterator<String> iterate3 = thirdWindow.iterator();
						while(iterate3.hasNext())
						{
							if(!razorayPaymentPage.equalsIgnoreCase(iterate3.next()))
							{
								driver.switchTo().window(iterate3.next());//1st pop up from parent page
								List<WebElement> selectPayment = driver.findElements(By.xpath("//div[@id=\"hostedpage-container\"]//li")); // this page is common to INR and US 
								for(int i = 0; i < selectPayment.size(); i++)
								{
									String paymentName = ConfigFileReader.getProdPaymentMode();//netbanking
									WebElement paymentMode = selectPayment.get(i).findElement(By.xpath("//span[contains(text(),\'"+paymentName+"')]"));
									if(paymentMode.getText().equalsIgnoreCase(paymentName))
									{
										paymentMode.click();
									}
									List<WebElement> selectBank = driver.findElements(By.xpath("//div[@class=\"netbanking-bank-list\"]//div[@class=\"title\"]"));
									for(int j = 0; j < selectBank.size(); j++)
									{
										if(selectBank.get(j).getText().contains("HDFC")); 
										{
											selectBank.get(j).click();
											driver.findElement(By.id("pay-now")).click();
											payMentStatus = "success";
										}
									}
								}
						    }
						}
					}
				if(driver.getCurrentUrl().equalsIgnoreCase("https://dev-ecomm-in.skillup.online/basket/?logged=true")) //payment with iframe popup
				{
					JavascriptExecutor js4 = (JavascriptExecutor)driver;
					js4.executeScript("window.scrollBy(0, 300)");
					Thread.sleep(1000);
					String afterPlanSelection = driver.getWindowHandle();
					Set<String> fourthWindow = driver.getWindowHandles();
					Iterator<String> iterate4 = fourthWindow.iterator();
					if(!afterPlanSelection.equalsIgnoreCase(iterate4.next()))
					{
						driver.switchTo().window(iterate4.next());//3rd = All payments type list out popup
						System.out.println(driver.getPageSource());
						driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class=\"razorpay-checkout-frame\"]")));//it switching into frame
						List<WebElement> listOfPayments = driver.findElements(By.xpath("//div[@id=\"form-common\"]//div[@role=\"list\"]//button[@type = \"button\" and @role=\"listitem\"]"));
						for(int i = 0; i < listOfPayments.size(); i++)
						{
							Thread.sleep(1000);
							if(listOfPayments.get(i).getText().contains("Netbanking"))//payment selection
							{
								listOfPayments.get(i).click();
								List<WebElement> listOfBanks = driver.findElements(By.cssSelector("body div#netb-banks > div[id*=\"bank-item\"] div div"));
								for(int j = 0; j < listOfBanks.size(); j++)
								{
									Thread.sleep(1000);
									if(listOfBanks.get(j).getText().contains("HDFC"))//bank selection
									{
										System.out.println(listOfBanks.get(j).getText());
										listOfBanks.get(j).click();// click bank
										Thread.sleep(1000);
										WebElement element = driver.findElement(By.cssSelector("body div#footer>span#footer-cta"));
										driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
										  JavascriptExecutor scrpt = (JavascriptExecutor)driver;
										  scrpt.executeScript("arguments[0].click();", element);
										 
											/*
											 * WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(70));
											 * wt.until(ExpectedConditions.invisibilityOf(element));
											 * wt.until(ExpectedConditions.elementToBeClickable(element)).click();
											 */
										Thread.sleep(400);
										String AfterFrame = driver.getWindowHandle();
										Set<String> st = driver.getWindowHandles();
										Iterator<String> it = st.iterator();
										while(it.hasNext())
										{
											driver.switchTo().window(it.next());
											driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
											payMentStatus = "success";
										}
									}
								}
							}
						}
					}
				}
		}	
		else if(driver.findElements(By.xpath("//a[contains(text(),\"Start Now\")]")).size() > 0)
		{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0, 200)");
				
				WebElement clickStartNowIcon = driver.findElement(By.xpath("(//a[contains(text(),\"Start Now\")])[1]"));
				if(clickStartNowIcon.isDisplayed())
				{
					clickStartNowIcon.click();
					Thread.sleep(1000);
					payMentStatus = "success";
				}
				
				JavascriptExecutor js5 = (JavascriptExecutor)driver;
				js5.executeScript("window.scrollBy(0, 200)");
				if(driver.getCurrentUrl().contains("-in.") || driver.getCurrentUrl().contains("in."))
				{
					if(driver.findElements(By.xpath("//h4[contains(text(),\"Select a plan to continue\")]")).size() != 0)
					{
						System.out.println("select a plan to continue pop up");
						List<WebElement> plans = driver.findElements(By.cssSelector("div#custom-plans1 div[class=\"owl-item active\"]"));
						for(int i = 0; i < plans.size(); i++)
						{
							WebElement getplan = plans.get(i);
							WebElement planName = getplan.findElement(By.cssSelector(" div[class*=\"plan_heading\"]"));
							String getPlanName = planName.getText();
							System.out.println(getPlanName);
								
							if(getPlanName.equalsIgnoreCase(ConfigFileReader.getPlan()))
							{
								WebElement planPrize = plans.get(i).findElement(By.cssSelector(" h5[class*=\"prize\"]:not([class=\"prize_color discount\"])"));
								String getPrizeText = planPrize.getText();
								System.out.println(getPrizeText);
								WebElement clickSelectPlanButton = plans.get(i).findElement(By.cssSelector(" div[class=\"bttn\"] a"));
								clickSelectPlanButton.click();
							 }
							break;
						 }
						JavascriptExecutor js8 = (JavascriptExecutor)driver;
						js8.executeScript("window.scrollBy(0, 400)");
						
						WebElement clickRazorayPayment = driver.findElement(By.cssSelector("#razorpay")); // this page is common to INR and US 
						clickRazorayPayment.click();
					}
					else
					{
						JavascriptExecutor js6 = (JavascriptExecutor)driver;
						js6.executeScript("window.scrollBy(0, 300)");
						
						WebElement clickRazorayPayment = driver.findElement(By.cssSelector("#razorpay")); // this page is common to INR and US 
						clickRazorayPayment.click();
					}
					
					Set<String> listOfPopups = driver.getWindowHandles();
					Iterator<String> itr = listOfPopups.iterator();
					while(itr.hasNext())
					{
						driver.switchTo().window(itr.next());
						List<WebElement> listOfPayments = driver.findElements(By.cssSelector("//button[@role=\"listitem\"]"));
						for(int i = 0; i < listOfPayments.size(); i++)
						{
							if(listOfPayments.get(i).getAttribute("textContext").contains("Netbanking"))
							{
								List<WebElement> listOfBanks = driver.findElements(By.cssSelector("div#netb-banks > div[id*=\"bank-item\"] div div"));
								for(int j = 0; j < listOfBanks.size(); j++)
								{
								if(listOfBanks.get(j).getAttribute("id").contains("HDFC"))
								{
									listOfBanks.get(j).findElement(By.cssSelector(" img")).click();// click bank
									WebElement clickPayButton = driver.findElement(By.cssSelector("#footer span"));
									String getPayText = clickPayButton.getText();
									System.out.println(getPayText);
									clickPayButton.click();
									driver.switchTo().window(itr.next());
									driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
								}
							    }
							}
						}
					 }	
				}
				else
				{
					System.out.println("US site");
				}
			}
			else
			{
				System.out.println("unable to enroll");
			}
		}
			}
	catch(Exception e)
	{
		e.printStackTrace();
	}

		return payMentStatus;
	}
	
	public void paymentProcess()
	{
		
	}
}
