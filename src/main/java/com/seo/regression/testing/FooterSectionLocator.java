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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.core.backend.Status;

public class FooterSectionLocator
{
	WebDriver driver;
	public FooterSectionLocator(WebDriver driver)
	{
		this.driver = driver;
		//OpenWebsite.openSite(driver);
	}
	public String getDriverDetails()
	{
		String driverName =	OpenWebsite.openSite(driver)+"/";
		return driverName;
	}
	public String verifySkillupLogo() throws InterruptedException
	{
		String status = "failed";
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
			Thread.sleep(2000);
			js.executeScript("window.scrollBy(0, -300)", "");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
			//String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
			WebElement clickLogo = driver.findElement(By.cssSelector("div[class*='Footer_footertopmenu'] a[class*='Footer_FTLogo']"));
			wait.until(ExpectedConditions.elementToBeClickable(clickLogo));
		//	clickLogo.click();
			js.executeScript("arguments[0].click()", clickLogo);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
			Thread.sleep(1000);
			status = "pass";
		}
		catch(Exception e)
		{
			status="fail";
			e.printStackTrace();
		}
		
		return status;
	}
	
	public String verifyTwitter() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		Thread.sleep(2000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		WebElement clickTwitter = driver.findElement(By.cssSelector("ul[class*=' Footer_socialIconsSection'] li:nth-child(1) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickTwitter));
		//clickTwitter.click();
		js.executeScript("arguments[0].click()", clickTwitter);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		Thread.sleep(800);
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("https://twitter.com/"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("twitter window");
					status = "success";
					driver.close();
					Thread.sleep(1000);
					driver.switchTo().window(parentWindow);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";	
						break;
					}
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return status;
	}
	
	public String verifyInstagram() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		Thread.sleep(2000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -100)", "");
		WebElement clickInstagram = driver.findElement(By.cssSelector("ul[class*=' Footer_socialIconsSection'] li:nth-child(4) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickInstagram));
		//clickInstagram.click();
		js.executeScript("arguments[0].click()", clickInstagram);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		Thread.sleep(1000);
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("https://www.instagram.com/"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("instagram window");
					driver.close();
					driver.switchTo().window(parentWindow);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";
						break;
					}
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return status;
	}
	
	public String verifyFacebook() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -160)", "");
		Thread.sleep(2000);
		WebElement clickFacebook = driver.findElement(By.cssSelector("ul[class*=' Footer_socialIconsSection'] li:nth-child(2) a"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickFacebook));
	//	clickFacebook.click();
	js.executeScript("arguments[0].click()", clickFacebook);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
	driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("https://www.facebook.com/"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("facebook window");
					driver.close();
					driver.switchTo().window(parentWindow);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";
						break;
					}
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return status;
	}
	
	public String verifyLinkedIn() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		Thread.sleep(2000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		WebElement clickLinkedIn = driver.findElement(By.cssSelector("ul[class*=' Footer_socialIconsSection'] li:nth-child(3) a"));
		Thread.sleep(800);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickLinkedIn));
	//	clickLinkedIn.click();
		js.executeScript("arguments[0].click()", clickLinkedIn);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("https://www.linkedin.com/"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("linkedIn window");
					driver.close();
					driver.switchTo().window(parentWindow);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";
						break;
					}
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return status;
	}
	
	public String verifyContactUs() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		WebElement clickContactUs = driver.findElement(By.cssSelector("div[class='Footer_ContActUsIn__ywIhS'] span img[alt='icon']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickContactUs));
		Thread.sleep(800);
		js.executeScript("arguments[0].click()", clickContactUs);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		String parentWindow = driver.getWindowHandle();
		Set<String> nextWindow = driver.getWindowHandles();
		Iterator<String> iterator = nextWindow.iterator();
		while (iterator.hasNext()) 
		{
			String childWindow = iterator.next();
			if(parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("contact"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("contact window");
					String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
					String subString = StringUtils.substringBefore(getURL, "online/");
					subString = subString+"online";
					driver.get(subString);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";
						break;
					}
				}	
			}
			else if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(driver.getCurrentUrl().contains("contact"))
				{
					driver.switchTo().window(childWindow);
					System.out.println("contact window");
					driver.close();
					driver.switchTo().window(parentWindow);
					if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
					{
						status = "success";
						break;
					}
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		return status;
	}
	
	public String verifyAboutSkillupOnline() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		List<WebElement> clickAboutSkillupOnline= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickAboutSkillupOnline.size(); i++)
		{
			String getText = clickAboutSkillupOnline.get(i).getText();
			if(getText.equalsIgnoreCase("About SkillUp Online"))
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(clickAboutSkillupOnline.get(i)));
				Thread.sleep(800);
				js.executeScript("arguments[0].click()", clickAboutSkillupOnline.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("about"))
						{
							System.out.println("about window");
							String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
							String subString = StringUtils.substringBefore(getURL, "online/");
							subString = subString+"online";
							driver.get(subString);
							if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
							{
								status = "success";
							}
						}	
					}
				}
				break;
			}
		}
		return status;
	}
	
	public String verifyBusiness() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		List<WebElement> clickBusiness= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickBusiness.size(); i++)
		{
			String getText = clickBusiness.get(i).getText();
			if(getText.equalsIgnoreCase("SkillUp Online for Business"))
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
				wait.until(ExpectedConditions.elementToBeClickable(clickBusiness.get(i)));
				js.executeScript("arguments[0].click()", clickBusiness.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("enterprise"))
						{
							System.out.println("business window");
							String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
							String subString = StringUtils.substringBefore(getURL, "online/");
							subString = subString+"online";
							driver.get(subString);
							if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
							{
								status = "success";
							}
						}	
					}
				}
				break;
				
			}
		}
		return status;
	}
	
	public String verifyFaq() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		List<WebElement> clickFaq= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickFaq.size(); i++)
		{
			String getText = clickFaq.get(i).getText();
			if(getText.equalsIgnoreCase("FAQs"))
			{
				Thread.sleep(1000);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(clickFaq.get(i)));
//				clickFaq.get(i).click();
				js.executeScript("arguments[0].click()", clickFaq.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(400));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("faq"))
						{
							System.out.println("FAQ's window");
							String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
							String subString = StringUtils.substringBefore(getURL, "online/");
							subString = subString+"online";
							driver.get(subString);
							if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
							{
								status = "success";
							}
						}	
					}
				}
				break;
				
			}
		}
		return status;
	}
	
	public String verifyPrivacyPolicy() throws InterruptedException
	{
		String status = "failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(80));
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		List<WebElement> clickPrivacyPolicy= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickPrivacyPolicy.size(); i++)
		{
			String getText = clickPrivacyPolicy.get(i).getText();
			if(getText.equalsIgnoreCase("Privacy Policy"))
			{
				Thread.sleep(500);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(clickPrivacyPolicy.get(i)));
				js.executeScript("arguments[0].click()", clickPrivacyPolicy.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				Thread.sleep(1000);
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("privacy"))
						{
							System.out.println("Privacy Policy window");
							String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
							String subString = StringUtils.substringBefore(getURL, "online/");
							subString = subString+"online";
							driver.get(subString);
							if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
							{
								status = "success";
							}
						}	
					}
				}
				break;
			}
		}
		return status;
	}
	
	public String verifyTermsofService() throws InterruptedException
	{
		//this.scrollToFooterSection();
		String status = "failed";
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -100)", "");
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		List<WebElement> clickTermsofService= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickTermsofService.size(); i++)
		{
			String getText = clickTermsofService.get(i).getText();
			if(getText.equalsIgnoreCase("Terms of Service"))
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(clickTermsofService.get(i)));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				js.executeScript("arguments[0].click()", clickTermsofService.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("tos"))
						{
							System.out.println("Terms of service window");
							String getURL = driver.getCurrentUrl();//https://stage-in.skillup.online/
							String subString = StringUtils.substringBefore(getURL, "online/");
							subString = subString+"online";
							driver.get(subString);
							if(driver.getCurrentUrl().equalsIgnoreCase(getDriverDetails()))
							{
								status = "success";
							}
						}	
					}
				}
				break;
			}
		}
		return status;
	}
	
	public String verifyBlog() throws InterruptedException
	{
		String status = "failed";
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		List<WebElement> clickBlog= driver.findElements(By.cssSelector("div[class*='Footer_FootMenu'] ul li a"));
		for(int i = 0; i < clickBlog.size(); i++)
		{
			String getText = clickBlog.get(i).getText();
			if(getText.equalsIgnoreCase("Blog"))
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(clickBlog.get(i)));
				Thread.sleep(500);
				//clickBlog.get(i).click();
				js.executeScript("arguments[0].click()", clickBlog.get(i));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> nextWindow = driver.getWindowHandles();
				Iterator<String> iterator = nextWindow.iterator();
				while (iterator.hasNext()) 
				{
					String childWindow = iterator.next();
					if(parentWindow.equalsIgnoreCase(childWindow))
					{
						if(driver.getCurrentUrl().contains("blog"))
						{
							System.out.println("blog window");
							status = "success";
						}	
					}
				}
				break;
			}
		}
		return status;
	}
	
	public ArrayList<String> verifyPopularCategories(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		String getHost = OpenWebsite.setHost;
		try
		{
			if(!driver.getCurrentUrl().equalsIgnoreCase(getHost))
			{
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.open()");
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs.get(1));
				OpenWebsite.openSite(driver);
			}
			else
			{
				System.out.println("host is present");
			}
			((JavascriptExecutor) driver)
		     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("window.scrollBy(0, 500)", "");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
			List<WebElement> popularCategories = driver.findElements(By.cssSelector("div[class='Footer_PopularCategories__23uL0'] ul li a"));
			for(int i = 0; i < popularCategories.size(); i++)
			{
				if(popularCategories.size() == 14)
				{
					String categoriesName = popularCategories.get(i).getText();
					if(categoriesName.equalsIgnoreCase(data.get(i+1)))
					{
						String getCatagoriesURL =  popularCategories.get(i).getAttribute("href");
						String urlstatus=this.checkURLStatus(getCatagoriesURL);
						if(urlstatus.equalsIgnoreCase("failed"))
						{
							status.add(categoriesName);
						}
						else
						{
							status.add("pass");
						}
						String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
						popularCategories.get(i).sendKeys(n);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						String parentWindow = driver.getWindowHandle();
						Set<String> windows = driver.getWindowHandles();
						for(String allWindows : windows)
						{
							if(!parentWindow.equalsIgnoreCase(allWindows))
							{
								driver.switchTo().window(allWindows);
								System.out.println(driver.getCurrentUrl());
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
								driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
								driver.close();
								driver.switchTo().window(parentWindow);
								((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
							}
						}
					}
				}
			}	
		}
		catch(StaleElementReferenceException e)
		{
			e.printStackTrace();
			status.add("failed");
		}
		return status;
	}
	
	public String checkURLStatus(String getURL)
	{
		String URLStatus = "failed";
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
				URLStatus = "failed";
			}
			else
			{
				System.out.println("un broken link"+addHosturl);
				URLStatus = "pass";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			URLStatus = "failed";
		}
		return URLStatus;
	}
	public ArrayList<String> verifyPopularCourses(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
		if(!driver.getCurrentUrl().equalsIgnoreCase(OpenWebsite.setHost))
		{
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.open()");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			OpenWebsite.openSite(driver);
		}
		else
		{
			System.out.println("host is present");
		}
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		List<WebElement> popularCourses = driver.findElements(By.cssSelector("div[class*='Footer_PopularCourses'] ul li a"));
		for(int i = 0; i < popularCourses.size(); i++)
		{
			if(data.get(i+1).equalsIgnoreCase(popularCourses.get(i).getText()))
			{
				String URLStatus=this.checkURLStatus(popularCourses.get(i).getAttribute("href")); 
				if(URLStatus.equalsIgnoreCase("failed"))
				{
					status.add(popularCourses.get(i).getText());
				}
				else
				{
					status.add("pass");
				}
				String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
				popularCourses.get(i).sendKeys(n);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String allWindows : windows)
				{
					if(!parentWindow.equalsIgnoreCase(allWindows))
					{
						driver.switchTo().window(allWindows);
						System.out.println(driver.getCurrentUrl());
						if(i == 3)
						{
							ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
							driver.switchTo().window(tabs.get(1));
							OpenWebsite.openSite(driver); 
							driver.switchTo().window(tabs.get(0));
							driver.close();
							driver.switchTo().window(tabs.get(1));
							((JavascriptExecutor) driver)
							.executeScript("window.scrollTo(0, document.body.scrollHeight)");
							break;
						}
						else
						{
							driver.close();
							driver.switchTo().window(parentWindow);
							((JavascriptExecutor) driver)
							.executeScript("window.scrollTo(0, document.body.scrollHeight)");
						}
					}
				}
			}
		}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		status.add("failed");
		}
		return status;
	}
	
	public ArrayList<String> verifyLatestBlogs(ArrayList<String> data) throws InterruptedException
	{
		ArrayList<String> status = new ArrayList<String>();
		String getHost = OpenWebsite.setHost;
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for(int i = 0; i < tabs.size(); i++)
		{
			if(tabs.size()>1)
			{
				if(driver.getCurrentUrl().contains("about:blank"))
				{
					driver.close();
					driver.switchTo().window(tabs.get(0));
				}
				else
				{
					System.out.println(driver.getCurrentUrl());
				}
			}
			else if(driver.getCurrentUrl().equalsIgnoreCase(getHost+"/"))
			{
				driver.switchTo().window(tabs.get(i));
			}
			else
			{
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.open()");
				driver.switchTo().window(tabs.get(0));
				OpenWebsite.openSite(driver);
			}
		}
		
		Thread.sleep(3000);
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0, 500)", "");
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -200)", "");
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		List<WebElement> blogs = driver.findElements(By.cssSelector("div[class*='Footer_LatestBlogsRepT']"));
		for(int i = 0; i < blogs.size(); i++)
		{
			if(data.get(i+1).equalsIgnoreCase(blogs.get(i).findElement(By.cssSelector(" h3")).getText()))
			{
				String urlStatus = this.checkURLStatus(blogs.get(i).findElement(By.cssSelector("div[class*='Footer_LatestBlogsRepT'] a")).getAttribute("href"));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				if(urlStatus.equalsIgnoreCase("failed"))
				{
					status.add(data.get(i+1));
				}
				else
				{
					status.add("pass");
				}
				String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
				blogs.get(i).findElement(By.cssSelector(" a")).sendKeys(n);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String allWindows : windows)
				{
					if(!parentWindow.equalsIgnoreCase(allWindows))
					{
						driver.switchTo().window(allWindows);
						System.out.println(driver.getCurrentUrl());
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(80));
						driver.close();
						driver.switchTo().window(parentWindow);
						((JavascriptExecutor) driver)
						.executeScript("window.scrollTo(0, document.body.scrollHeight)");
						}
					}
				}
			}
		return status;
	}
}
