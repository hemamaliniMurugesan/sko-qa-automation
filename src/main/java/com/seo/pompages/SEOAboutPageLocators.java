package com.seo.pompages;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;

public class SEOAboutPageLocators
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String courseTitleText = "";
	
	public WebDriver getDriver()
	{
		return driver;
	}

	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_105.0.5195.52 version\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	}
	
	public String launchCourseURL(String url)
	{
		try
		{
			//String addHostURL = ConfigFileReader.getURL()+url;
			//driver.get(addHostURL);
			/*
			 * ((JavascriptExecutor) driver).executeScript("window.open()");
			 * ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			 * driver.switchTo().window(tabs.get(1));
			 */
		//	openDriver();
			//driver.get(addHostURL);
			System.out.println("SEO Page Validation started");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}
	
	public String getCanonicalURL(String canonicalURL)
	{
		String checkVPNStatus = "Fail";
		try
		{
			if(!driver.getCurrentUrl().contains("in."))
			{
				String addHost = ConfigFileReader.getURL()+canonicalURL;
				WebElement canonicalLocator = driver.findElement(By.cssSelector("link[rel='canonical']"));
				String getCanonicalURLText = canonicalLocator.getAttribute("href");
				if(addHost.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").equalsIgnoreCase(getCanonicalURLText.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "")))
				{
					System.out.println(getCanonicalURLText);
					checkVPNStatus = "success";
				}
				else
				{
					System.out.println("It is not CanonicalURL");
				}
			}
			else
			{
				checkVPNStatus = "successInd";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkVPNStatus;
	}
	
	public String getAttributeOfTag(String selector, String attribute)
	{
		String attributeValue = null;
		try
		{
			List<WebElement> tags = driver.findElements(By.cssSelector(selector));
			if(tags.size() > 1)
			{
				for(WebElement element: tags)
				{
					String value = element.getAttribute(attribute).replaceAll("\\s", "").replaceAll("\u00A0", "").trim();
					if(value != null && value != "")
					{
						attributeValue = value;
						break;
					}
				}
			}
			else if(tags.size() == 1)
			{
				attributeValue = tags.get(0).getAttribute(attribute).replaceAll("\\s", "").replaceAll("\u00A0", "").trim();
			}
			
			System.out.println(attributeValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return attributeValue;
	}
	
	public WebElement getTagWithInnerText(String htmlTag, String innerText)
	{
		WebElement tagToReturn = null;
		try
		{
			List<WebElement> tags = driver.findElements(By.cssSelector(htmlTag));
			for(WebElement tag: tags)
			{
				String text = tag.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "");
				if(innerText.equalsIgnoreCase(text))
				{
					tagToReturn = tag;
					System.out.println(innerText);
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tagToReturn;
	}	
	
	public ArrayList<String> getAnswersForFAQQuestion(String questionFromExcel) throws InterruptedException
	{
		ArrayList<String> ans = new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 3000)");
		List<WebElement> listOfFAQ = driver.findElements(By.cssSelector("div#faqs div[class='border-0 my-1 accordion-item']"));
		if(listOfFAQ.size()>0)
		{
			for(int i = 0; i < listOfFAQ.size(); i++)
			{
				WebElement faq = listOfFAQ.get(i);
				WebElement question = faq.findElement(By.cssSelector(" h2[class='Accordion_accordionTitle__u92qv accordion-header'] button"));
				String questionText = question.getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "");
				if(questionText.equalsIgnoreCase(questionFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "")))
				{
					List<WebElement> FAQAnswers = faq.findElements(By.cssSelector(" div[class*='accordion-collapse collapse'] div[class='Accordion_accordionBody__cK_Px accordion-body'] div"));
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", question);
					for(int j = 0; j < FAQAnswers.size(); j++)
					{
						String answer = FAQAnswers.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println(questionText);
						System.out.println(answer);
						ans.add(answer);
					}
				}
			}
		}
		else
		{
			System.out.println("Excel Data of FAQ is not same as UI");
		}
		return ans;
	}
	
	public void checkElementExist(String selector) throws Exception
	{
		try
		{
			driver.findElement(By.xpath(selector)).getText();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void launchValidator()
	{
		try
		{
			String currentURL = driver.getCurrentUrl();
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.get("https://validator.schema.org/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			String getCaptchaURL = driver.getCurrentUrl();
			String addTextCaptchaURL = getCaptchaURL+"?invisible=true";
			driver.get(addTextCaptchaURL);
			Set<String> s1 = driver.getWindowHandles();
			
			 
			 
	        System.out.println("Clicked the checkbox");
			Iterator<String> itr = s1.iterator();
			while(itr.hasNext())
			{
				String childwindowOfTableValidator = itr.next();
				if(!childwindowOfTableValidator.equalsIgnoreCase(tabs.get(1)))
				{
					String runUrlPopupText = driver.findElement(By.xpath("//span[contains(text(),\"Test your structured data\")]")).getText();
					System.out.println(runUrlPopupText);
					WebElement enterURLInRunTest = driver.findElement(By.cssSelector("input#new-test-url-input"));
					Thread.sleep(500);   
					//JavascriptExecutor executor = (JavascriptExecutor) driver;
					//executor.executeScript("arguments[0].scrollIntoView(true);", enterURLInRunTest);
					enterURLInRunTest.sendKeys(currentURL);
					WebElement clickRunTest = driver.findElement(By.cssSelector("button#new-test-submit-button"));
					clickRunTest.click();
					String checkTablePageURL = driver.findElement(By.cssSelector("div#fetch-url")).getText();
					System.out.println(checkTablePageURL);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String validateHeading(String heading)
	{
		String status = "Success";
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			WebElement tableElement = driver.findElement(By.cssSelector("li.mdl-list__item:first-child"));
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", tableElement);
			if(tableElement.isDisplayed())
			{
				try
				{
					tableElement.click();
					WebElement headingFromTableElement = driver.findElement(By.cssSelector("table.mdl-data-table tr:last-child td:last-child > div"));
					String headingFromTable = headingFromTableElement.getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim();
					if(!headingFromTable.replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim().contains(heading.replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim()))
					{
						status = "Failed";
					}
					WebElement back = driver.findElement(By.cssSelector("#results-cell button.mdl-button:not([id])"));
					wait.until(ExpectedConditions.visibilityOf(back)).click();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Heading is not available in schema validator");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return status;
	}
	
	public HashMap<String, String> getFAQFromValidator()
	{
		HashMap<String, String> faqFromValidator = new HashMap<String, String>();
		try
		{
		WebElement faqElement = driver.findElement(By.cssSelector("li.mdl-list__item:last-child"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", faqElement);
		if(faqElement.isDisplayed())
		{
			faqElement.click();
			List<WebElement> tableRows = driver.findElements(By.cssSelector("table.mdl-data-table tr"));
			String questionFromValidator = "";
			for(WebElement row: tableRows)
			{
				List<WebElement> rowCells = row.findElements(By.cssSelector(" td div"));
				if(rowCells.size() == 2)
				{
						WebElement firstColumn = rowCells.get(0);
						((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", firstColumn);
						String key = firstColumn.getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim();
						if(key.equals("name"))
						{
							questionFromValidator = rowCells.get(1).getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim();
						}
						else if(key.equals("text") && !questionFromValidator.isEmpty())
						{
							String answerFromValidtor = rowCells.get(1).getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").trim();
							faqFromValidator.put(questionFromValidator, answerFromValidtor);
							questionFromValidator = "";
						}
					}
			}
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", faqElement);
		}
		else
		{
			System.out.println("FAQ not available in SchemaValidator");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return faqFromValidator;
		}
	
	public String checkTableValidator(String tableValidatorURL, String tableHeading) throws MalformedURLException
	{
		String currentURL = driver.getCurrentUrl();
		String checkTablePageURL = null;
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		driver.get("https://validator.schema.org/");
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> itr = s1.iterator();
		while(itr.hasNext())
		{
			String childwindowOfTableValidator = itr.next();
			if(!childwindowOfTableValidator.equalsIgnoreCase(tabs.get(1)))
			{
				/*
				 * wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(
				 * "//iframe[starts-with(@name,'a-')]"))); WebElement reCaptchaElement =
				 * wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
				 * "div.recaptcha-checkbox-checkmark"))); reCaptchaElement.click();
				 */
				String runUrlPopupText = driver.findElement(By.xpath("//span[contains(text(),\"Test your structured data\")]")).getText();
				System.out.println(runUrlPopupText);
				WebElement enterURLInRunTest = driver.findElement(By.cssSelector("input#new-test-url-input"));
				enterURLInRunTest.sendKeys(currentURL);
				WebElement clickRunTest = driver.findElement(By.cssSelector("button#new-test-submit-button"));
				clickRunTest.click();
				checkTablePageURL = driver.findElement(By.cssSelector("div#fetch-url")).getText();
				System.out.println(checkTablePageURL);
				WebElement detectedItems = driver.findElement(By.cssSelector("div[class=\"sKfxWe-BeDmAc sKfxWe-BeDmAc-AHe6Kc\"] div[class=\"sKfxWe-BeDmAc-qJTHM-haAclf\"] ul"));
				List<WebElement> listOfDetectedItem = detectedItems.findElements(By.cssSelector(" li"));
				for(int i = 0; i < listOfDetectedItem.size(); i++)
				{
					WebElement getTextFromDetectedItem = listOfDetectedItem.get(i).findElement(By.cssSelector(" span"));
					System.out.println(getTextFromDetectedItem.getText());
					if(getTextFromDetectedItem.getText().equalsIgnoreCase("Table"))//1.table 2. faq
					{
						getTextFromDetectedItem.click();//1.table 2. faq 
						WebElement table = driver.findElement(By.cssSelector("table[class=\"mdl-data-table mdl-js-data-table aVTXAb-BeDmAc-LJTIlf-jyrRxf\"]"));
						List<WebElement> tableCell = table.findElements(By.cssSelector(" tbody tr td div"));
						for(int j = 0; j < tableCell.size(); j++)
						{
							if(tableCell.get(j).getText().replaceAll("\\s", "").equalsIgnoreCase(tableHeading.replaceAll("\\s", "")))
							{
								System.out.println("Table heading is same :"+tableCell.get(j).getText().replaceAll("\\s", ""));
							}
						}
						driver.findElement(By.cssSelector("button[class=\"mdl-button mdl-js-button mdl-js-ripple-effect \"]")).click();
					}
					else if(getTextFromDetectedItem.getText().equalsIgnoreCase("FAQPage"))
					{
						getTextFromDetectedItem.click();
						
					}
				}
			}
		}
		return tableHeading;
	}
	
	public String[] checkRedirectStatus(String url, String statusCode1, String statusCode2)
	{
		String[] status = new String[2];
		status[0] = "success";
		status[1] = "success";
		
		String host = "";
		try 
		{
			parentURL = new URL(driver.getCurrentUrl());
			host = parentURL.getHost();
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get("https://www.redirect-checker.org/index.php");
		WebElement CheckRedirect = driver.findElement(By.id("redirecturl"));
		CheckRedirect.sendKeys(host + url);
		WebElement clickAnalysisButton = driver.findElement(By.id("sitemapsubmit"));
		clickAnalysisButton.click();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 200)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String statusResult1 = checkStatusCode(statusCode1);
		if(!statusResult1.equalsIgnoreCase("Success"))
		{
			status[0] = "failed";
			//driver.switchTo().window(tabs.get(1));
		}
		
		String statusResult2 = checkStatusCode(statusCode2);
		if(!statusResult2.equalsIgnoreCase("Success"))
		{
			status[1] = "failed";
			//driver.switchTo().window(tabs.get(0));
		}
		driver.close();
		driver.switchTo().window(tabs.get(0));
		return status;
	}
	
	private String checkStatusCode(String statusCode)
	{
		String status = "Failure";
		
		try
		{
			driver.findElement(By.xpath("//p//strong[contains(text(),'"+statusCode+"')]"));
			status = "Success";
		}
		catch(Exception e)
		{
			System.out.println("status of Redirected URL: " + status);
		}
		return status;
	}
	public String getCourseCodeText(String code)
	{
		String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		try
		{
			String addHosturl = ConfigFileReader.getAboutCourseURL()+code+"/about";
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(addHosturl);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			WebElement checkCourseCode = driver.findElement(By.xpath("(//script[contains(text(),\""+code+"\")])[1]"));
			courseIDFromBrowser = checkCourseCode.getAttribute("textContent");
			System.out.println("course ID from Browser : "+courseIDFromBrowser);
			System.out.println("courseIDFrom Excel: "+code);
			if(courseIDFromBrowser.contains(code))
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
	
	public String getCourseTitleText()
	{
		try
		{
			WebElement title = driver.findElement(By.xpath("//h1"));
			courseTitleText = title.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("title from browser: "+courseTitleText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseTitleText;
	}
	
	public String getCourseOrganizationImgAltText(String courseOrganizationFromExcel)
	{
		String courseOrg = "";
		try
		{
			WebElement orgImg = driver.findElement(By.cssSelector("div.ConTianer_ConTent > div:first-child > div:last-child > a > img"));
			courseOrg = orgImg.getAttribute("alt");
			System.out.println("course org From Browser : "+courseOrg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseOrg;
	}
	
	public String getCourseDescription()
	{
		String courseContent = "";
		try
		{
			WebElement description = driver.findElement(By.cssSelector("div.Sk_Content p"));
			courseContent = description.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("course Description from browser : "+courseContent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseContent;
	}
	
	public String getCourseType1()
	{
		String courseType1Text = "";
		try
		{
			WebElement courseType1Name = driver.findElement(By.cssSelector("div.Sk_Content > ul li:nth-child(1) a"));
			courseType1Text = courseType1Name.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("getCourseType1 from browser : "+courseType1Text);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseType1Text;
	}
	
	public String getCourseType2()
	{
		String courseType2Text = "";
		try
		{
			WebElement coursetype2Name = driver.findElement(By.cssSelector("div.Sk_Content > ul li:nth-child(2) a"));
			courseType2Text = coursetype2Name.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("Course Type 2 from browser : "+courseType2Text);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseType2Text;
		
	}
	
	public String getCourseLevel()
	{
		String courseLevelText = "";
		try
		{
			WebElement courseLevelName = driver.findElement(By.cssSelector("div.Sk_Content > ul li:nth-child(3) a"));
			courseLevelText = courseLevelName.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("courseLevelText from browser : "+courseLevelText);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 400)");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseLevelText;
	}
	
	public ArrayList<String> getInfoContentFromCourse(String courseInfoHeadingFromExcel)
	{
		ArrayList<String> courseInfoContent = new ArrayList<String>();
		List<WebElement> listOfCourseInfo = driver.findElements(By.cssSelector("div#accordion div#accordion3 div[class='panel panel-default ibm-v2-accordion']"));
		if(listOfCourseInfo.size() > 0)
		{
			for(int i = 0; i < listOfCourseInfo.size(); i++)
			{
				WebElement info = listOfCourseInfo.get(i);
				WebElement infoHeading = info.findElement(By.cssSelector(" div a"));
				String infoHeadingText = infoHeading.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(infoHeadingText.equalsIgnoreCase(courseInfoHeadingFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "")))
				{
					List<WebElement> infoContent =  info.findElements(By.cssSelector(" div.panel-collapse > div"));
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", infoHeading);
					for(int j = 0; j < infoContent.size(); j++)
					{
						String content = infoContent.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0","").replaceAll("[^\\p{ASCII}]", "")
															 ;
						System.out.println(infoHeadingText);
						System.out.println(content);
						courseInfoContent.add(content);
					}
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				}
			}
		}
		else
		{
			System.out.println("Excel Data of FAQ is not same as UI");
		}
		return courseInfoContent;
	}
	
	public String processCourseOutLineSection(List<WebElement> accordions)
	{
		String status = "Success";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 700)");
		
		if(accordions == null)
		{
			accordions = driver.findElements(By.cssSelector("div.dynamic_outline div.SkoMulTISection > div.panel-group > div.panel"));
		}
		for(WebElement accordion: accordions)
		{
			if(!accordion.findElement(By.cssSelector("div.panel-heading")).getAttribute("class").contains("arrowactive"))
			{
				//open accordion
				//executor.executeScript("arguments[0].click();", question);
				js.executeScript("arguments[0].click();", accordion.findElement(By.cssSelector("div.panel-heading a")));
				//accordion.findElement(By.cssSelector("div.panel-heading a")).click();
			}
			
			if(accordion.findElement(By.cssSelector("div.panel-heading")).getAttribute("class").contains("arrowactive") && 
					accordion.findElement(By.cssSelector("div.panel-collapse")).getAttribute("class").contains("in"))
			{
				try
				{
					List<WebElement> subAccordians = accordion.findElements(By.cssSelector(" > div.panel-collapse > div.panel-body > div.panel-group > div.panel"));
					status = processCourseOutLineSection(subAccordians);
				}
				catch(Exception e)
				{
					// Ignore because it is a text node or empty node
				}
			}
			else
			{
				status = "Failed";
			}
			
			//close accordion
			//accordion.findElement(By.cssSelector("div.panel-heading a")).click();
			js.executeScript("arguments[0].click();", accordion.findElement(By.cssSelector("div.panel-heading a")));
		}
		return status;
	}
	
	public ArrayList<Integer> getEarnCertificateText(String earnYourCertificateContentFromExcel, String titleName , String formatOfCertificate, String org)
	{
		ArrayList<Integer> errorCells = new ArrayList<Integer>();
		String statusOfCertificate = "fail";
		String certificateName = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 500)");
		String getEarnCertificateText = "";
		String parentWindow = null;
		try
		{
			WebElement earnCertify = driver.findElement(By.cssSelector("div[class='certificate_wrap'] p:nth-child(2)"));
			getEarnCertificateText = earnCertify.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("getEarnCertificateText from browser : "+getEarnCertificateText);
			if(getEarnCertificateText.equalsIgnoreCase(earnYourCertificateContentFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				System.out.println(getEarnCertificateText);
			}
			else
			{
				System.out.println("Earn certificate is not same");
				errorCells.add(1);
			}
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement clickEarnCertificate = driver.findElement(By.cssSelector("div[class='certificate_wrap'] a[id='certificate-preview-btn']"));
			js.executeScript("arguments[0].click();", clickEarnCertificate);
		//	wait.until(ExpectedConditions.visibilityOf(clickEarnCertificate)).click();
			//((JavascriptExecutor)driver).executeScript("arguments[0].click();", clickEarnCertificate);
			Thread.sleep(1000);
			String AboutCourseWindow = driver.getWindowHandle(); 
			Set<String> certificatePopup = driver.getWindowHandles(); 
			Iterator<String> iterator = certificatePopup.iterator(); 
			if(iterator.hasNext()) 
			{ 
					String popupWindow = iterator.next(); 
					driver.switchTo().window(popupWindow);
					if(driver.findElement(By.xpath("//h5[contains(text(),\"Certificate Preview\")]")).isDisplayed())
					{
						WebElement checkCourseNameFromImage = driver.findElement(By.xpath("(//div[@id=\"social-icons-conatainer\"])[2]//img"));
						certificateName = checkCourseNameFromImage.getAttribute("alt");
						String removeWord = "Certificate Preview";
						certificateName = certificateName.replaceAll(removeWord, "");
						certificateName = certificateName.trim();
						System.out.println("certificateName from browser : "+certificateName);
						if(certificateName.equalsIgnoreCase(titleName))
						{
							System.out.println("certificate name and title is same");
							statusOfCertificate = "success";
						}
						else
						{
							System.out.println("certificate name and title is not same");
							statusOfCertificate = "fail";
							errorCells.add(2);
						}
						WebElement getCertificateCaption = driver.findElement(By.xpath("//h5[contains(text(),\"Certificate Preview\")]"));
						System.out.println(getCertificateCaption.getText());
						parentWindow = driver.getWindowHandle();
						TakesScreenshot scrShot =((TakesScreenshot)driver);
						//Call getScreenshotAs method to create image file
						File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
						//Move image file to new destination
						File DestFile=new File("D:\\AutomationTestingWorkspace\\com.practice.Automation\\img\\test.png");
						//Copy file at destination
						FileUtils.copyFile(SrcFile, DestFile);
						((JavascriptExecutor) driver).executeScript("window.open()");
						ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
						driver.switchTo().window(tabs.get(1));
						driver.get("https://www.utilities-online.info/image-to-text");
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						WebElement clickUploadPic = driver.findElement(By.cssSelector("#uploadFile"));
						//clickUploadPic.click();
						
						clickUploadPic.sendKeys("D:\\AutomationTestingWorkspace\\com.practice.Automation\\img\\test.png");
						js.executeScript("window.scrollBy(0, 100)");
						Thread.sleep(1000);
						WebElement clickConvertToText = driver.findElement(By.cssSelector("button#checkBtn"));
						Thread.sleep(500);
						if(clickConvertToText.isDisplayed())
						{
							//clickConvertToText.click();
							Actions action =new Actions(driver);
							action.moveToElement(clickConvertToText).click().build().perform();
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						}
						WebElement getImageText = driver.findElement(By.cssSelector("textArea#output"));
						String ImageText = getImageText.getText();
						System.out.println(ImageText);
						if(formatOfCertificate.equalsIgnoreCase("NA")||ImageText.contains(formatOfCertificate))
						{ 
							statusOfCertificate = "success";
						}
						else
						{
						  System.out.println("Format is not same");
						  statusOfCertificate = "fail";
						  errorCells.add(3);
						}
						if(ImageText.contains(org) || org.equalsIgnoreCase("NA"))
						{
							System.out.println("Logo name is available :"+org);
							statusOfCertificate = "success";
						} 
						else
						{ 
							System.out.println("Logo is not available");
							statusOfCertificate = "fail";
							errorCells.add(4);
						}
						/*
						 * ITesseract img = new Tesseract(); img.setDatapath(
						 * "D:\\AutomationTestingWorkspace\\com.practice.Automation\\tesseract"); String
						 * result = img.doOCR(DestFile);
						 * 
						 * 
						 * System.out.println(result);
						 * 
						 * WebElement orgImg = driver.findElement(By.
						 * cssSelector("div.ConTianer_ConTent > div:first-child > div:last-child > a > img"
						 * )); String courseOrg = orgImg.getAttribute("alt");
						 * 
						 * 
						 * int orgText = logo.lastIndexOf(" "); String getOrgText = logo.substring(0,
						 * orgText); if(formatOfCertificate.equalsIgnoreCase("NA")||result.contains(
						 * formatOfCertificate)) { statusOfCertificate = "success"; } else {
						 * System.out.println("Format is not same"); statusOfCertificate = "fail";
						 * errorCells.add(3); } if(result.contains(getOrgText)) {
						 * System.out.println("Logo name is available :"+logo); statusOfCertificate =
						 * "success"; } else { System.out.println("Logo is not available");
						 * statusOfCertificate = "fail"; errorCells.add(4); } WebElement
						 * closeCertificate = driver.findElement(By.
						 * cssSelector("div#certificate-preview button[class=\"close\"]"));
						 * wait.until(ExpectedConditions.visibilityOf(closeCertificate));
						 */
						driver.close();
						driver.switchTo().window(parentWindow);
						WebElement closeCertificate = driver.findElement(By.cssSelector("div#certificate-preview button[class=\"close\"]"));
						if(closeCertificate.isDisplayed())
						{
						 js.executeScript("arguments[0].click();", closeCertificate);
						 Thread.sleep(1000);
						}
					}
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.close();
			driver.switchTo().window(parentWindow);
		}
		return errorCells;
	}
	
	public String getTypeofCertificate()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, -2200)");
		String checkCertificateContent = "";
		try
		{
			WebElement checkCertificate =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(1)"));
			WebElement checkCourseIcon = checkCertificate.findElement(By.cssSelector(" img"));
			checkCourseIcon.getAttribute("alt");
			System.out.println("checkCourseIcon from browser :"+checkCourseIcon.getAttribute("alt"));
			WebElement checkCourseCertificateText = checkCertificate.findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
			checkCertificateContent = checkCourseCertificateText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkCertificateContent;
	}
	
	public String getAboutCourse()
	{
		String checkAboutCourseContent = "";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 100)");
			WebElement checkAboutCourse =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(2)"));
			WebElement checkAboutIcon = checkAboutCourse.findElement(By.cssSelector(" img"));
			checkAboutIcon.getAttribute("alt");
			System.out.println("About Icon from browser :" +checkAboutIcon.getAttribute("alt"));
			WebElement checkAboutCourseText = checkAboutCourse.findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
			checkAboutCourseContent = checkAboutCourseText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkAboutCourseContent;
	}
	
	public String getIncludes()
	{
		String checkIncludesContent = "";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0, 100)");
			WebElement checkIncludes =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(3)"));
			WebElement checkIncludesIcon = checkIncludes.findElement(By.cssSelector(" img"));
			checkIncludesIcon.getAttribute("alt");
			System.out.println("Includes from browser : "+checkIncludesIcon.getAttribute("alt"));
			WebElement checkIncludesText = checkIncludes.findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
			checkIncludesContent = checkIncludesText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkIncludesContent;
	}
	
	public String getCreate()
	{
		String checkCreateContent = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 100)");
		try
		{
			WebElement checkCreate =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(4)"));
			WebElement checkCreateIcon = checkCreate.findElement(By.cssSelector(" img"));
			checkCreateIcon.getAttribute("alt");
			System.out.println("create from browser : "+checkCreateIcon.getAttribute("alt"));
			WebElement checkCreateText = checkCreate.findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
			
			checkCreateContent = checkCreateText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkCreateContent;
	}
	
	public String getExerciseToExplore()
	{
		String checkExerciseToExploreContent = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 100)");
		try
		{
			List<WebElement> checkExerciseToExplore =  driver.findElements(By.cssSelector("div.SideBarMain > div"));
			if(checkExerciseToExplore.size() > 0)
			{
				for(int i = 0; i < checkExerciseToExplore.size(); i++)
				{
					String checkExerciseIcon = checkExerciseToExplore.get(i).findElement(By.cssSelector(" div img")).getAttribute("alt");
					if(checkExerciseIcon.contains("Exercises"))
					{
						System.out.println(checkExerciseIcon);
						WebElement checkExerciseToExploreText = checkExerciseToExplore.get(i).findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
						checkExerciseToExploreContent = checkExerciseToExploreText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println("ExerciseToExplore from browser :"+checkExerciseToExploreContent);
						break;
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkExerciseToExploreContent;
	}
	
	/*
	 * public String getAttributeOfTag(String selector, String attribute) { String
	 * attributeValue = null; try { WebElement tag =
	 * driver.findElement(By.cssSelector(selector)); attributeValue =
	 * tag.getAttribute(attribute).replaceAll("\\s", "").replaceAll("\u00A0",
	 * "").trim(); System.out.println(attributeValue); } catch(Exception e) {
	 * e.printStackTrace(); } return attributeValue; }
	 */
	
	public HashMap<String, HashMap<String, String>> getExperts()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 100)");
		HashMap<String, HashMap<String, String>> experts = null;
		try
		{
			List<WebElement> expertsList = driver.findElements(By.cssSelector("div.SkRiGhTSeCTIoN_WhTBG > .SideBarMain > .SideBar_TwoCoLMN"));
			if(expertsList.size() > 0)
			{
				experts = new HashMap<>();
				for(int i = 0; i < expertsList.size(); i++)
				{
					HashMap<String, String> expert = new HashMap<>();
					WebElement expertElement = expertsList.get(i);
					String name = expertElement.findElement(By.cssSelector(" .SideBarCoLMN_Right p")).getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					expert.put("name", name);
					List<WebElement> liTags = expertElement.findElements(By.cssSelector(" .SideBarCoLMN_Right ul > li"));
					for(WebElement li: liTags)
					{
						//which means it has children node probably <a> tag
						if(li.findElements(By.cssSelector("*")).size() > 0)
						{
							expert.put("linkedInUrl", li.findElement(By.cssSelector("a")).getAttribute("href").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", ""));
						}
						else
						{
							expert.put("role", li.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", ""));
						}
					}
					String styleOfImage = expertElement.findElement(By.cssSelector(" .SideBarCoLMN_LeFT > span")).getAttribute("style");
					expert.put("style", styleOfImage);
					
					experts.put(name, expert);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0, -1100)");
		return experts;
	}
	
	public String getStartsOn(String startsOnFromExcel)
	{
		String checkStartson = "";
		try
		{
			if(startsOnFromExcel.equalsIgnoreCase("NA"))
			{
				checkStartson = "successIND";
			}
			else
			{
				List<WebElement> listOfRows = driver.findElements(By.cssSelector("td p[class=\"Sk-Currency evergreen-text\"]"));
				for(int k = 0; k < listOfRows.size(); k++)
				{
					if(listOfRows.get(k).getText().equalsIgnoreCase("Starts On"))
					{
						List<WebElement> checkStartsOnImg = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td img"));
						for(int i = 0; i < checkStartsOnImg.size(); i++)
						{
							if(checkStartsOnImg.get(i).getAttribute("alt").contains("Enrollment"))
							{
								System.out.println("Starts on Icon is displayed : "+checkStartsOnImg.get(i).getAttribute("alt"));
								break;
							}
						}
						List<WebElement> checkStartOnText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td .evergreen-text"));
						for(int j = 0; j < checkStartOnText.size(); j++)
						{
							String getText = checkStartOnText.get(j).getText();
							if(getText.equalsIgnoreCase("Starts on"))
							{
								WebElement getStartsOnLocator = driver.findElement(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:nth-child(2)"));
								String getStartsOnText = getStartsOnLocator.getText();
								String startsOnTextFromBrowser = getStartsOnText.replaceAll("Starts on", "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
								if(startsOnTextFromBrowser.equalsIgnoreCase(startsOnFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
								{
									System.out.println(" Starts on value :"+getStartsOnText);
									checkStartson = "success";
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkStartson;
	}
	
	public String getDurationInfo(String durationFromExcel)
	{
		String checkDuration = "success";
		try
		{
			if(durationFromExcel.equalsIgnoreCase("NA"))
			{
				checkDuration = "successIND";
			}
			else
			{
				List<WebElement> listOfRows = driver.findElements(By.cssSelector("td p[class=\"Sk-Currency evergreen-text\"]"));
				for(int k = 0; k < listOfRows.size(); k++)
				{
					if(listOfRows.get(k).getText().equalsIgnoreCase("Duration"))
					{
						List<WebElement> checkDurationImg = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td>img"));
						for(int i = 0; i < checkDurationImg.size(); i++)
						{
							if(checkDurationImg.get(i).getAttribute("alt").contains("duration"))
							{
								System.out.println("Duration icon is displayed :"+checkDurationImg.get(i).getAttribute("alt"));
							}
						}
						List<WebElement> checkDurationText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td .evergreen-text"));
						for(int j = 0; j < checkDurationText.size(); j++)
						{
							String getDuration = checkDurationText.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
							System.out.println("duration from browser : "+getDuration);
							if(getDuration.equalsIgnoreCase(durationFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
							{
								checkDuration = "success";
								break;
							}
							else
							{
								checkDuration = "fail";
							}
						}
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkDuration;
	}
	
	public String getflatPrice(String flatPriceWithoutGSTFromExcel)
	{
		String checkPriceWOGST = "success";
		
		try
		{
			if(driver.getCurrentUrl().contains("in."))
			{
				if(flatPriceWithoutGSTFromExcel.equalsIgnoreCase("NA"))
				{
					checkPriceWOGST = "successIND";
				}
				else
				{
					WebElement currencyIcon = driver.findElement(By.cssSelector(".DESKTOPTABCOLUMN td:nth-last-child(2) > img"));
					System.out.println("Fee is displayed : "+currencyIcon.getAttribute("alt"));
					WebElement flatPrice = driver.findElement(By.cssSelector(".DESKTOPTABCOLUMN td:last-child > p.evergreen-text:last-child"));
					System.out.println("Flat price without GST value :"+flatPrice.getText());
					
					NumberFormat format = NumberFormat.getCurrencyInstance();
					Number number = format.parse(flatPrice.getText());
					String flatPriceFromBrowser = number.toString();
					
					String flatPriceFromExcel = flatPriceWithoutGSTFromExcel.replaceAll(",", "");
					
					System.out.println("Flat price from browser : "+flatPriceFromBrowser);
					
					if(!flatPriceFromBrowser.equalsIgnoreCase(flatPriceFromExcel))
					{
						checkPriceWOGST = "fail";
					}
				}
				
				
				
				/*
				 * List<WebElement> listOfRows = driver.findElements(By.
				 * cssSelector("td p[class=\"Sk-Currency evergreen-text\"]")); for(int k = 0; k
				 * < listOfRows.size(); k++) {
				 * if(listOfRows.get(k).getText().equalsIgnoreCase("Fee")) { List<WebElement>
				 * checkPriceWithoutGSTValue = driver.findElements(By.
				 * cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td img:not(td[width=\"2%\"]"
				 * )); for(int i = 0; i < checkPriceWithoutGSTValue.size(); i++) {
				 * if(checkPriceWithoutGSTValue.get(i).getAttribute("alt").contains("Course Fee"
				 * )) {
				 * System.out.println(checkPriceWithoutGSTValue.get(i).getAttribute("alt")); } }
				 * List<WebElement> checkFlatText = driver.findElements(By.
				 * cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:not(td[width=\"2%\"]):not(td[class=\"PADDLFT\"]) [class=\"evergreen-text\"]"
				 * )); for(int j = 0; j < checkFlatText.size(); j++) {
				 * if(checkFlatText.get(j).getText().equalsIgnoreCase(
				 * flatPriceWithoutGSTFromExcel)) { String getFlat =
				 * checkFlatText.get(j).getText().replaceAll("\\s",
				 * "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0",
				 * "").replaceAll("[^\\p{ASCII}]", "");
				 * System.out.println("Flat price from excel : "+flatPriceWithoutGSTFromExcel);
				 * System.out.println("Flat price from Browser : "+getFlat); checkPriceWOGST =
				 * "success"; break; } else { checkPriceWOGST = "fail"; } } } else {
				 * checkPriceWOGST = "successIND"; } }
				 */
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkPriceWOGST;
	}
	
	public String getUSDPrice(String priceUSDFromExcel)
	{
		String checkUSDStatus = "Fail";
		try
		{
			if(!driver.getCurrentUrl().contains("in."))
			{
				if(priceUSDFromExcel.equalsIgnoreCase("NA"))
				{
					checkUSDStatus = "successIND";
				}
				else
				{
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("window.scrollBy(0, -1300)");
					List<WebElement> checkPriceUSDValue = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td > p"));
					for(int i = 0; i < checkPriceUSDValue.size(); i++)
					{
						if(checkPriceUSDValue.get(i).getText().replaceAll("[^\\d.]", "").replaceAll("[^0-9?!\\.]","").replaceAll("\\s", "").equalsIgnoreCase(priceUSDFromExcel.replaceAll("[^\\d.]", "").replaceAll("\\s", "")))
						{
							String checkFlatPriceUSD = checkPriceUSDValue.get(i).getText().replaceAll("[^0-9?!\\.]","").replaceAll("\\s", "");
							System.out.println(checkFlatPriceUSD);
							checkUSDStatus = "Success";
						}
						else
						{
							System.out.println("not same price");
							checkUSDStatus = "fail";
						}
					}
				}
			}
			else
			{
				checkUSDStatus = "successIND";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("This is india site. so USD wont check");
		}
		return checkUSDStatus;
	}

	public ArrayList<String> checkCategory(ArrayList<String> categoryName)
	{
		String aboutPage = null;
		ArrayList<String> categoryStatus = new ArrayList<String>();
		try
		{
			for(int j = 1; j < categoryName.size(); j++)
			{
				if(categoryName.get(j).equalsIgnoreCase("NA"))
				{
					//categoryStatus = "successIND";
					categoryStatus.add("successIND");
				}
				else
				{
					aboutPage = driver.getWindowHandle(); 
					((JavascriptExecutor) driver).executeScript("window.open()");
					ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs.get(1));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					driver.get(ConfigFileReader.getSEOLoginURL());
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					boolean isFoundCourse = false;
					WebElement clickCourseDropdown = driver.findElement(By.cssSelector("a#navbarDropdowna"));
					clickCourseDropdown.click();
					List<WebElement> dropdownList = driver.findElements(By.cssSelector("ul[class='categorylist dropdown-submenu'] li"));
					for(int i = 0; (!isFoundCourse && i < dropdownList.size()); i++)
					{
						WebElement categoryNameFromList = dropdownList.get(i);
						String getCategoryNameFromList = categoryNameFromList.getText();
						System.out.println("Category Name : "+getCategoryNameFromList);
						if(getCategoryNameFromList.equalsIgnoreCase(categoryName.get(j)))
						{
							categoryNameFromList.click();
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
							Thread.sleep(500);
							List<WebElement> listOfCourseCard = driver.findElements(By.cssSelector("div[class=\"skills-card-filter-bx\"] div.CoLCOMN div[class=\"CoursHeadingSection\"] p"));
							for(int k = 0; (!isFoundCourse && k < listOfCourseCard.size()); k++)
							{
								WebElement courseCard = listOfCourseCard.get(k);
								JavascriptExecutor js = (JavascriptExecutor)driver;
								js.executeScript("arguments[0].scrollIntoView(true);", courseCard);
								String courseCardName = courseCard.getText();
								System.out.println(courseCardName);
								if(courseCardName.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase(courseTitleText))
								{
									isFoundCourse = true;
									//categoryStatus = "success";
									categoryStatus.add("success");
									System.out.println(""+courseCardName+" available in this category "+categoryName+" ");
									driver.close();
									driver.switchTo().window(aboutPage);
								}
							}
						}
					}
					//driver.switchTo().window(aboutPage);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.close();
			driver.switchTo().window(aboutPage);
		}
				return categoryStatus;
	}
	
	public static void main(String[] args)
	{
		String keyValue = "content=\"Blockchain Essentials | Learn Blockchain?org=nasscom\"";
		int startIndex = keyValue.indexOf("\"") + 1;
		int lastIndex = keyValue.lastIndexOf("\"");
		System.out.println(keyValue.substring(startIndex, lastIndex));
	}
}
