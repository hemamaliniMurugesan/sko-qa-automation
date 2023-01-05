
package com.seo.pompages;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;


public class NewAboutCourseLocator
{

	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setImageHostURL;
	String setLoginURL ;
	String setMetaHost;
	String imageHost;
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_108\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	
	public String setEnvironment(String host)
	{
		if(host.equalsIgnoreCase("prod-in"))
		{
			setHost = "https://in.skillup.online";
		}
		else if(!host.isEmpty())
		{
			setHost = "https://"+host+".skillup.online";
		}
		else
		{
			setHost = "https://skillup.online";
		}
		return setHost;
	}
	public String setImageEndpoint(String imageEndpoint)
	{
		if(imageEndpoint.equalsIgnoreCase("stagecourses-in"))
		{
			imageHost = "https://stagecourses-in.skillup.online";
		}
		else if(imageEndpoint.equalsIgnoreCase("qa-in"))
		{
			imageHost = "https://qa-in.skillup.online";
		}
		else if(imageEndpoint.equalsIgnoreCase("qa"))
		{
			imageHost = "https://qa.skillup.online";
		}
		else if(imageEndpoint.equalsIgnoreCase("stage"))
		{
			imageHost = "https://stagecourses.skillup.online";
		}
		else if(imageEndpoint.equalsIgnoreCase("prod-in"))
		{
			imageHost = "https://in.skillup.online";
		}
		else
		{
			imageHost = "https://skillup.online";
		}
		return imageHost;
	}
	public String setMetaHostURL()
	{
		setMetaHost = setHost;
		setLoginURL = setMetaHost;
		return setMetaHost;
	}
	
	public String getCourseCodeText(String code)
	{
		String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		HttpURLConnection huc = null;
		int respCode = 200;
		String addHosturl = this.setHost+"/courses/"+code;
		try
		{
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			System.out.println("status code : "+respCode + " " +addHosturl);
			if(respCode > 200 && !(respCode == 308))
			{
				System.out.println("broken link");
				System.exit(0);
			}
			else
			{
				System.out.println("un broken link");
				driver.get(addHosturl);
				WebElement checkCourseCode = driver.findElement(By.xpath("(//*[contains(@href,'"+code+"')])[1]"));
				courseIDFromBrowser = checkCourseCode.getAttribute("href");
				int index = courseIDFromBrowser.lastIndexOf("/");
				String getCourseID = courseIDFromBrowser.substring(index+0);
				getCourseID = getCourseID.replace("/", "");
				System.out.println("course ID from Browser : "+getCourseID);
				System.out.println("courseIDFrom Excel: "+code);
				if(getCourseID.contains(code))
				{
					CourseCodeStatus = "true";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CourseCodeStatus;
	}
	
	String courseTitleText = "";
	public String getCourseTitleText(String courseTitleFromExcel)
	{
		String titleStatus = "";
		courseTitleText = courseTitleFromExcel;
		try
		{
			if(courseTitleFromExcel.equalsIgnoreCase("NA"))
			{
				titleStatus = "notProcessed";
			}
			else
			{
				WebElement title = driver.findElement(By.cssSelector("div[class*='CourseDescription_courseText'] h1"));
				String getTitleFromBrowser = title.getAttribute("innerText").trim();
				System.out.println("title from browser: "+title.getAttribute("innerText").trim());
				if(courseTitleText.trim().equalsIgnoreCase(getTitleFromBrowser))
				{
					titleStatus = "pass";
				}
				else
				{
					titleStatus = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return titleStatus;
	}
	
	public String getCourseOrganizationFormatText(String courseOrganizationFromExcel)
	{
		String courseOrgStatus = "";
		try
		{
			if(courseOrganizationFromExcel.equalsIgnoreCase("NA"))
			{
				courseOrgStatus = "notProcessed";
			}
			else
			{
				WebElement orgImg = driver.findElement(By.cssSelector("div[class='col d-flex align-items-center justify-content-end'] img:nth-child(2)"));
				String courseOrgFromBrowser = orgImg.getAttribute("alt");
				System.out.println("course org From Browser : "+courseOrgFromBrowser);
				if(courseOrgFromBrowser.equals(courseOrganizationFromExcel))
				{
					courseOrgStatus = "pass";
				}
				else
				{
					courseOrgStatus = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseOrgStatus;
	}
	
	public String getCourseDescription(String courseDescriptionFromExcel)
	{
		String courseContentStatus = "";
		try
		{
			if(courseDescriptionFromExcel.equalsIgnoreCase("NA"))
			{
				courseContentStatus = "notProcessed";
			}
			else
			{
				WebElement description = driver.findElement(By.cssSelector("section[class*=\"CourseDescription_mainSection\"] div[class*=\"CourseDescription_courseText\"] p"));
				String courseContent = description.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("course Description from browser : "+courseContent);
				if(courseContent.equals(courseDescriptionFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					courseContentStatus = "pass";
				}
				else
				{
					courseContentStatus = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseContentStatus;
	}
	
String addHosturl;
	
	public String getCourseType1(String courseType1FromExcel)
	{
		String courseType1Status = "";
		try
		{
			if(courseType1FromExcel.equalsIgnoreCase("NA"))
			{
				courseType1Status = "notProcessed";
			}
			else
			{
				WebElement courseType1Name = driver.findElement(By.cssSelector("section[class*=\"CourseDescription_mainSection\"] div[class*=\"CourseDescription_levelSection\"] h2"));
				String courseType1Text = courseType1Name.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("getCourseType1 from browser : "+courseType1Text);
				if(courseType1FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equals(courseType1Text))
				{
					courseType1Status = "pass";
				}
				else
				{
					courseType1Status = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseType1Status;
	}
	
	public String getCourseType2(String courseType2FromExcel)
	{
		String courseType2Status = "";
		try
		{
			if(courseType2FromExcel.equals("NA"))
			{
				courseType2Status = "notProcessed";
			}
			else
			{
				WebElement coursetype2Name = driver.findElement(By.xpath("(//div[@class='CourseDescription_levelSection__BiiUm']//h3)[1]"));
				String courseType2Text = coursetype2Name.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("Course Type 2 from browser : "+courseType2Text);
				if(courseType2FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equals(courseType2Text))
				{
					courseType2Status = "pass";
				}
				else
				{
					courseType2Status = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseType2Status;
		
	}
	
	public String getCourseLevel(String courseLevelFromExcel)
	{
		String courseLevelStatus = "";
		try
		{
			if(courseLevelFromExcel.equals("NA"))
			{
				courseLevelStatus = "notProcessed";
			}
			else
			{
				WebElement courseLevelName = driver.findElement(By.xpath("(//div[@class='CourseDescription_levelSection__BiiUm']//h3)[2]"));
				String courseLevelText = courseLevelName.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("courseLevelText from browser : "+courseLevelText);
				if(courseLevelText.equals(courseLevelFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					courseLevelStatus = "pass";
				}
				else
				{
					courseLevelStatus = "fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseLevelStatus;
	}
	
	public ArrayList<String> getInfoContentFromCourse(String courseInfoHeadingFromExcel)
	{
		ArrayList<String> getCourseInfoFromExcel = new ArrayList<String>();
		if(courseInfoHeadingFromExcel.equalsIgnoreCase("NA"))
		{
			getCourseInfoFromExcel.add(courseInfoHeadingFromExcel);
		}
		else
		{
			List<WebElement> listOfCourseInfo = driver.findElements(By.cssSelector("div#overview div[class=\"CourseOverview_accordionItem__9k4Ix accordion-item\"]"));
			//js.executeScript("arguments[0].scrollIntoView();", listOfCourseInfo);
			if(listOfCourseInfo.size() > 0)
			{
				for(int i = 0; i < listOfCourseInfo.size(); i++)
				{
					WebElement info = listOfCourseInfo.get(i);
					WebElement infoHeading = info.findElement(By.cssSelector(" button"));
					String infoHeadingText = infoHeading.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					if(infoHeadingText.equalsIgnoreCase(courseInfoHeadingFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "")))
					{
						System.out.println("course overview heading text from browser :"+i+" "+infoHeadingText);
						System.out.println("course overview heading text from excel : "+courseInfoHeadingFromExcel);
						List<WebElement> infoContent =  info.findElements(By.cssSelector("div[class*='accordion-collapse collapse'] div div"));
						JavascriptExecutor executor = (JavascriptExecutor)driver;
						executor.executeScript("arguments[0].click();", infoHeading);
						for(int j = 0; j < infoContent.size(); j++)
						{
							String content = infoContent.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0","").replaceAll("[^\\p{ASCII}]", "");
							System.out.println("Overview answer :"+j+" "+content);
							getCourseInfoFromExcel.add(content);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
						}
					}
				}
			}
			else
			{
				System.out.println("Excel Data of course overview is not same as UI");
			}
		}
		return getCourseInfoFromExcel;
	}
	
	public String processCourseOutLineSection(List<WebElement> accordions)
	{
		List<WebElement> overviewNavigation = driver.findElements(By.cssSelector("div[class*=\"CourseDescription_navigationBar\"] button"));
		  for(int i = 0; i < overviewNavigation.size(); i++)
		  {
			  if(overviewNavigation.get(i).getText().equals("Details"))
			  {
				  overviewNavigation.get(i).click();
				  break;
			  }
		  }
		  List<WebElement> overviewNavigation2 = driver.findElements(By.cssSelector("div[class*=\"CourseDescription_navigationBar\"] button"));
		  for(int i = 0; i < overviewNavigation2.size(); i++)
		  {
			  if(overviewNavigation2.get(i).getText().equals("FAQs"))
			  {
				  overviewNavigation2.get(i).click();
				  break;
			  }
		  }
		  //below is old one
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
		List<WebElement> overviewNavigation = driver.findElements(By.cssSelector("div[class*=\"CourseDescription_navigationBar\"] button"));
		  for(int i = 0; i < overviewNavigation.size(); i++)
		  {
			  if(overviewNavigation.get(i).getText().equals("Details"))
			  {
				  overviewNavigation.get(i).click();
				  break;
			  }
		  }
		  List<WebElement> overviewNavigation2 = driver.findElements(By.cssSelector("div[class*=\"CourseDescription_navigationBar\"] button"));
		  for(int i = 0; i < overviewNavigation.size(); i++)
		  {
			  if(overviewNavigation2.get(i).getText().equals("Why SkillUp Online?"))
			  {
				  overviewNavigation2.get(i).click();
				  break;
			  }
		  }
		  //old one
		ArrayList<Integer> errorCells = new ArrayList<Integer>();
		ArrayList<Integer> inProgressCells = new ArrayList<Integer>();
		
		String statusOfCertificate = "fail";
		String certificateName = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 500)");
		String getEarnCertificateText = "";
		try
		{
			WebElement earnCertify = driver.findElement(By.cssSelector("div[class='certificate_wrap'] p:nth-child(2)"));
			getEarnCertificateText = earnCertify.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println("getEarnCertificateText from browser : "+getEarnCertificateText);
			if(getEarnCertificateText.equalsIgnoreCase(earnYourCertificateContentFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				System.out.println(getEarnCertificateText);
			}
			else if(earnYourCertificateContentFromExcel.equals("NA"))
			{
				inProgressCells.add(1);
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
						else if(earnYourCertificateContentFromExcel.equals("NA"))
						{
							inProgressCells.add(2);
						}
						else
						{
							System.out.println("certificate name and title is not same");
							statusOfCertificate = "fail";
							errorCells.add(2);
						}
						WebElement getCertificateCaption = driver.findElement(By.xpath("//h5[contains(text(),\"Certificate Preview\")]"));
						System.out.println(getCertificateCaption.getText());
						String parentWindow = driver.getWindowHandle();
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
						else if(earnYourCertificateContentFromExcel.equals("NA"))
						{
							inProgressCells.add(3);
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
						else if(earnYourCertificateContentFromExcel.equals("NA"))
						{
							inProgressCells.add(4);
						}
						else
						{ 
							System.out.println("Logo is not available");
							statusOfCertificate = "fail";
							errorCells.add(4);
						}
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
		}
		if(earnYourCertificateContentFromExcel.equals("NA"))
		{
			return inProgressCells;
		}
		else
		{
			return errorCells;
		}
	}
	
	public String getTypeofCertificate(String typeOfCertificateFromExcel)
	{
		String checkCertificateContent = "";
		try
		{
			String courseOffering = "Type of certificate";
			if(typeOfCertificateFromExcel.equals("NA"))
			{
				checkCertificateContent = "notProcessed";
			}
			else
			{
				List<WebElement> typeOfCertificateImage = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection__GqLBS')])[2]/div[contains(@class,'CourseOffering_courseProperty')]/div[@class='d-block']//img"));
				for(int i = 0; i < typeOfCertificateImage.size(); i++)
				{
					if(typeOfCertificateImage.get(i).getAttribute("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase("certificate"))
					{
						System.out.println("type of certificate image is displayed");
						break;
					}
					else
					{
						System.out.println("type of certificate imgae icon not found");
					}
				}
				
				List<WebElement> typeOfCertificate = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int j = 0; j < typeOfCertificate.size(); j++)
				{
					String getTypeOfCertificateText = typeOfCertificate.get(j).getText();
					if(getTypeOfCertificateText.equalsIgnoreCase(courseOffering))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+j+"+1]"));
						String getContent = getContentLocator.getAttribute("innerText").replaceAll(courseOffering, "");
						getContent = getContent.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String contentFromExcel = typeOfCertificateFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getContent.equals(contentFromExcel))
						{
							checkCertificateContent = "pass";
							System.out.println("type of certificate from browser:"+j+""+getContent);
							System.out.println("type of certificate from Excel:"+contentFromExcel);
							break;
						}
						else
						{
							checkCertificateContent = "fail";
							System.out.println("type of certificate from browser:"+j+""+getContent);
							System.out.println("type of certificate from Excel:"+contentFromExcel);
						}
					}
					
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
		return checkCertificateContent;
	}
	
	public String getAboutCourse(String aboutCourseFromExcel)
	{
		String checkAboutCourseContent = "";
		try
		{
			if(aboutCourseFromExcel.equals("NA"))
			{
				checkAboutCourseContent = "notProcessed";
			}
			else
			{
				List<WebElement> aboutCourseImage = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection__GqLBS')])[2]/div[contains(@class,'CourseOffering_courseProperty')]/div[@class='d-block']//img"));
				for(int i = 0; i < aboutCourseImage.size(); i++)
				{
					if(aboutCourseImage.get(i).getAttribute("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase("course"))
					{
						System.out.println("about course image is displayed");
						break;
					}
					else
					{
						System.out.println("about course imgae icon not found");
					}
				}
				List<WebElement> aboutCourseLocator = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int j = 0; j < aboutCourseLocator.size(); j++)
				{
					String aboutCourseText = aboutCourseLocator.get(j).getText();
					String courseOffering = "About this course";
					if(aboutCourseText.equalsIgnoreCase(courseOffering))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+j+"+1]"));
						String getContent = getContentLocator.getAttribute("innerText").replaceAll(courseOffering, "");
						getContent = getContent.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getContentFromExcel = aboutCourseFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getContent.equals(getContentFromExcel))
						{
							checkAboutCourseContent = "pass";
							System.out.println("about course from browser:"+j+""+getContent);
							System.out.println("about course from Excel:"+getContentFromExcel);
							break;
						}
						else
						{
							System.out.println("about course from browser:"+j+""+getContent);
							System.out.println("about course from Excel:"+getContentFromExcel);
							checkAboutCourseContent = "fail";
						}
					}
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkAboutCourseContent;
	}
	
	public String getIncludes(String includesFromExcel)
	{
		String checkIncludesContentStatus = "";
		
		try
		{
			if(includesFromExcel.equals("NA"))
			{
				checkIncludesContentStatus = "notProcessed";
			}
			else
			{
				List<WebElement> includesImage = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection__GqLBS')])[2]/div[contains(@class,'CourseOffering_courseProperty')]/div[@class='d-block']//img"));
				for(int i = 0; i < includesImage.size(); i++)
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					if(includesImage.get(i).getAttribute("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase("includes"))
					{
						System.out.println("include image is displayed");
						break;
					}
					else
					{
						System.out.println("include imgae icon not found");
					}
				}
				List<WebElement> includesLocator = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int j = 0; j < includesLocator.size(); j++)
				{
					String includesText = includesLocator.get(j).getText();
					String courseOffering = "Includes";
					if(includesText.equalsIgnoreCase(courseOffering))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+j+"+1]"));
						String getContent = getContentLocator.getAttribute("innerText").replaceAll(courseOffering, "");
						getContent = getContent.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getContentFromExcel = includesFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getContent.equals(getContentFromExcel))
						{
							checkIncludesContentStatus = "pass";
							System.out.println("Includes from browser:"+j+""+getContent);
							System.out.println("Includes from Excel:"+getContentFromExcel);
							break;
						}
						else
						{
							checkIncludesContentStatus = "fail";
							System.out.println("Includes from browser:"+j+""+getContent);
							System.out.println("Includes from Excel:"+getContentFromExcel);
						}
					}
					
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkIncludesContentStatus;
	}
	
	public String getCreate(String createFromExcel)
	{
		String checkCreateContentStatus = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 100)");
		try
		{
			if(createFromExcel.equals("NA"))
			{
				checkCreateContentStatus = "notProcessed";
			}
			else
			{
				List<WebElement> createImage = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection__GqLBS')])[2]/div[contains(@class,'CourseOffering_courseProperty')]/div[@class='d-block']//img"));
				for(int i = 0; i < createImage.size(); i++)
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					if(createImage.get(i).getAttribute("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase("create"))
					{
						System.out.println("create image is displayed");
						break;
					}
					else
					{
						System.out.println("create imgae icon not found");
					}
				}
				List<WebElement> createLocator = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int j = 0; j < createLocator.size(); j++)
				{
					String createText = createLocator.get(j).getText();
					String courseOffering = "Create";
					if(createText.equalsIgnoreCase(courseOffering))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+j+"+1]"));
						String getContent = getContentLocator.getAttribute("innerText");
						System.out.println(getContent);
						getContent = getContent.replaceAll(courseOffering, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getContentFromExcel = createFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getContent.equals(getContentFromExcel))
						{
							checkCreateContentStatus = "pass";
							System.out.println("create from browser:"+j+""+getContent);
							System.out.println("create from Excel:"+getContentFromExcel);
							break;
						}
						else
						{
							checkCreateContentStatus = "fail";
							System.out.println("create from browser:"+j+""+getContent);
							System.out.println("create from Excel:"+getContentFromExcel);
						}
					}
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkCreateContentStatus;
	}
	
	public String getExerciseToExplore(String exerciseFromExcel)
	{
		String checkExploreContentStatus = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 100)");
		try
		{
			if(exerciseFromExcel.equals("NA"))
			{
				checkExploreContentStatus = "notProcessed";
			}
			else
			{
				List<WebElement> exerciseToExploreImage = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection__GqLBS')])[2]/div[contains(@class,'CourseOffering_courseProperty')]/div[@class='d-block']//img"));
				for(int i = 0; i < exerciseToExploreImage.size(); i++)
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					if(exerciseToExploreImage.get(i).getAttribute("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase("exercises"))
					{
						System.out.println("exercise to Explore image is displayed");
						break;
					}
					else
					{
						System.out.println("exercise to Explore imgae icon not found");
					}
				}
				List<WebElement> exerciseToExploreLocator = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int j = 0; j < exerciseToExploreLocator.size(); j++)
				{
					String exerciseToExploreText = exerciseToExploreLocator.get(j).getText();
					String courseOffering = "Exercises to explore";
					if(exerciseToExploreText.equalsIgnoreCase(courseOffering))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+j+"+1]"));
						String getContent = getContentLocator.getAttribute("innerText");
						System.out.println(getContent);
						getContent = getContent.replaceAll(courseOffering, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getContentFromExcel = exerciseFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getContent.equals(getContentFromExcel))
						{
							checkExploreContentStatus = "pass";
							System.out.println("create from browser:"+j+""+getContent);
							System.out.println("create from Excel:"+getContentFromExcel);
							break;
						}
						else
						{
							checkExploreContentStatus = "fail";
							System.out.println("create from browser:"+j+""+getContent);
							System.out.println("create from Excel:"+getContentFromExcel);
						}
					}
				}
				}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkExploreContentStatus;
	}
	
	public HashMap<String, HashMap<String, String>> getExperts()
	{
		HashMap<String, HashMap<String, String>> experts = null;
		try
		{
			Thread.sleep(2000);
			List<WebElement> expertsList = driver.findElements(By.cssSelector("div#stickyContent section[class='LearnWithExperts_mainSection__ZB7fa'] div[class='LearnWithExperts_expertInfoSection__2WO0a d-flex']"));
			if(expertsList.size() > 0)
			{
				experts = new HashMap<>();
				for(int i = 0; i < expertsList.size(); i++)
				{
					HashMap<String, String> expert = new HashMap<>();
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					WebElement expertElement = expertsList.get(i);
					WebElement nameLocator = expertElement.findElement(By.cssSelector(" div[class='LearnWithExperts_expertInfo___z0Ug'] h2"));
					jse.executeScript("arguments[0].scrollIntoView()", nameLocator);
					String name = nameLocator.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					expert.put("name", name);
					WebElement roleLocator = expertElement.findElement(By.cssSelector(" div[class='LearnWithExperts_expertInfo___z0Ug'] p"));
					String role = roleLocator.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					expert.put(name, role);
					WebElement linkedURLLocator = expertElement.findElement(By.cssSelector(" div[class='LearnWithExperts_expertInfo___z0Ug']  a"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					Thread.sleep(300);
					jse.executeScript("arguments[0].scrollIntoView()", linkedURLLocator);
					jse.executeScript("window.scrollBy(0,-100)", "");
					//WebElement expertSection = driver.findElement(By.cssSelector("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]"));
					if(linkedURLLocator.isDisplayed())
					{
						jse.executeScript("window.scrollBy(0,-80)", "");
						linkedURLLocator.click();
						String parentWindow = driver.getWindowHandle();
						Set<String> windows = driver.getWindowHandles();
						for(String handle : windows)
						{
							if(!handle.equals(parentWindow))
							{
								driver.switchTo().window(handle);
								System.out.println("LinkedIn URL for Experts: "+driver.getCurrentUrl());
								driver.close();
								driver.switchTo().window(parentWindow);
							}
						}
					}
    				String linkedUrl = expertElement.findElement(By.cssSelector(" div[class='LearnWithExperts_expertInfo___z0Ug']  a")).getAttribute("href").replaceAll("\\s", "").replaceAll("\u00A0","").replaceAll("[^\\p{ASCII}]", ""); 
    				expert.put("linkedInUrl", linkedUrl);
					String styleOfImage = expertElement.findElement(By.cssSelector(" img[alt='profile-image']")).getAttribute("style");
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
		jse.executeScript("window.scrollBy(0, -600)");
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
				List<WebElement> listOfRows = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
				for(int k = 0; k < listOfRows.size(); k++)
				{
					WebElement startsOnImage = listOfRows.get(k).findElement(By.cssSelector(" img[alt='Enrollment']"));
					if(startsOnImage.isDisplayed())
					{
						System.out.println("Starts on image is available");
					}
					else
					{
						System.out.println("Starts on image is not available");
					}
					
					WebElement startsOnText = listOfRows.get(k).findElement(By.cssSelector(" div[class='CourseDescription_courseAboutTextSection__8_6ac']"));
					String getStartsOnText = startsOnText.getText();
					String remove = "Duration";
					getStartsOnText = getStartsOnText.replaceAll(remove, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					String getStartsOnFromExcel = startsOnFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					if(getStartsOnText.equals(getStartsOnFromExcel))
					{
						checkStartson = "pass";
						System.out.println("start on from browser"+getStartsOnText);
						System.out.println("start on from Excel"+getStartsOnFromExcel);
						break;
					}
					else
					{
						checkStartson = "fail";
						System.out.println("start on from browser"+getStartsOnText);
						System.out.println("start on from Excel"+getStartsOnFromExcel);
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
		String checkDurationStatus = "success";
		try
		{
			if(durationFromExcel.equalsIgnoreCase("NA"))
			{
				checkDurationStatus = "successIND";
			}
			else
			{
				List<WebElement> listOfRows = driver.findElements(By.cssSelector("div[class*='CourseDescription_durationAndPriceSection']"));
				for(int k = 0; k < listOfRows.size(); k++)
				{
					WebElement durationImage = listOfRows.get(k).findElement(By.cssSelector(" img[alt='time-icon']"));
					if(durationImage.isDisplayed())
					{
						System.out.println("duration image is available");
					}
					else
					{
						System.out.println("duration image is not available");
					}
					
					WebElement durationText = listOfRows.get(k).findElement(By.cssSelector(" div[class='CourseDescription_courseAboutTextSection__8_6ac'] p"));
					String getDurationText = durationText.getText();
					String remove = "Duration";
				//	getDurationText = getDurationText.replaceAll(remove, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					String getDurationFromExcel = durationFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					if(getDurationText.equals(getDurationFromExcel))
					{
						checkDurationStatus = "pass";
						System.out.println("duration from browser : "+getDurationText);
						System.out.println("duration from Excel : "+getDurationFromExcel);
						break;
					}
					else
					{
						checkDurationStatus = "fail";
						System.out.println("duration from browser : "+getDurationText);
						System.out.println("duration from Excel : "+getDurationFromExcel);
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkDurationStatus;
	}
	
	public String getflatPrice(String flatPriceWithoutGSTFromExcel)
	{
		String checkPriceWOGSTStatus = "success";
		
		try
		{
			if(driver.getCurrentUrl().contains("in."))
			{
				if(flatPriceWithoutGSTFromExcel.equalsIgnoreCase("NA"))
				{
					checkPriceWOGSTStatus = "successIND";
				}
				else
				{
					WebElement feeImage = driver.findElement(By.cssSelector("div[class='d-flex gap-2'] img"));
					if(feeImage.getAttribute("alt").equalsIgnoreCase("fee-icon"))
					{
						System.out.println("fee image is available");
					}
					else
					{
						System.out.println("fee image is not available");
					}
					List<WebElement> listOfRows = driver.findElements(By.cssSelector("div[class='CourseDescription_courseAboutTextSection__8_6ac']"));
					for(int k = 0; k < listOfRows.size(); k++)
					{
						WebElement flatPriceLocator = listOfRows.get(k).findElement(By.cssSelector(" p"));
						String getFlatPriceText = flatPriceLocator.getText();
						String remove = "Fee";
						//getFlatPriceText = getFlatPriceText.replaceAll(remove, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getFlatPriceTextFromExcel = flatPriceWithoutGSTFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getFlatPriceText.equals(getFlatPriceTextFromExcel))
						{
							checkPriceWOGSTStatus = "pass";
							System.out.println("flat price from browser :"+getFlatPriceText);
							System.out.println("flat price from excel :"+getFlatPriceTextFromExcel);
						}
						else
						{
							checkPriceWOGSTStatus = "fail";
							System.out.println("flat price from browser :"+getFlatPriceText);
							System.out.println("flat price from excel :"+getFlatPriceTextFromExcel);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkPriceWOGSTStatus;
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
					List<WebElement> listOfRows = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
					for(int k = 0; k < listOfRows.size(); k++)
					{
						WebElement priceUSDImage = listOfRows.get(k).findElement(By.cssSelector(" img[alt='time-icon']"));
						if(priceUSDImage.isDisplayed())
						{
							System.out.println("priceUSDImage image is available");
						}
						else
						{
							System.out.println("priceUSDImage image is not available");
						}
						WebElement priceUSDText = listOfRows.get(k).findElement(By.cssSelector(" div[class='CourseDescription_courseAboutTextSection__8_6ac']"));
						String getpriceUSDText = priceUSDText.getText();
						String remove = "Duration";
						getpriceUSDText = getpriceUSDText.replaceAll(remove, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String getpriceUSDFromExcel = priceUSDFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(getpriceUSDText.equals(getpriceUSDFromExcel))
						{
							checkUSDStatus = "pass";
							System.out.println("price from browser :"+getpriceUSDText);
							System.out.println("price from excel :"+priceUSDFromExcel);
							break;
						}
						else
						{
							checkUSDStatus = "fail";
							System.out.println("price from browser :"+getpriceUSDText);
							System.out.println("price from excel :"+priceUSDFromExcel);
							break;
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
					String aboutPage = driver.getWindowHandle(); 
					((JavascriptExecutor) driver).executeScript("window.open()");
					ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs.get(1));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					driver.get(this.setLoginURL);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					boolean isFoundCourse = false;
					WebElement clickCourseDropdown = driver.findElement(By.cssSelector("a#navbarDropdown"));
					clickCourseDropdown.click();
					List<WebElement> dropdownList = driver.findElements(By.xpath("(//div[@class='MainCatE catcolumn'])[2]/ul[@class='categorylist dropdown-submenu']/li/a"));
					JavascriptExecutor js = (JavascriptExecutor)driver;
					for(int i = 0; (!isFoundCourse && i < dropdownList.size()); i++)
					{
						WebElement categoryNameFromList = dropdownList.get(i);
						String getCategoryNameFromList = categoryNameFromList.getText();
						System.out.println("Category Name : "+getCategoryNameFromList);
						if(getCategoryNameFromList.equalsIgnoreCase(categoryName.get(j)))
						{
							categoryNameFromList.click();
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
							js.executeScript("window.scrollBy(0, 200)", "");
							List<WebElement> listOfCourseCard = driver.findElements(By.cssSelector("div[class*='LearningCatalog_browserCard'] div[class='RegularCourseCard_courseDes__0h9WE'] p"));
							for(int k = 0; (!isFoundCourse && k < listOfCourseCard.size()); k++)
							{
								WebElement courseCard = listOfCourseCard.get(k);
								js.executeScript("arguments[0].scrollIntoView(true);", courseCard);
								String courseCardName = courseCard.getText();
								System.out.println(courseCardName);
								if(courseCardName.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase(courseTitleText.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
								{
									isFoundCourse = true;
									categoryStatus.add("success");
									System.out.println(""+courseCardName+" available in this category "+categoryName+" ");
									driver.close();
									driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
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
		}
				return categoryStatus;
	}
	
	public String launchCourseURL(String url)
	{
		try
		{
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
				String addHost = setMetaHost+canonicalURL;
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
			if(attributeValue == null)
			{
				System.out.println("value is not available in browser : "+attributeValue);
			}
			else
			{
				System.out.println("value in browser : "+attributeValue);
			}
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
		List<WebElement> listOfFAQ = driver.findElements(By.cssSelector("div[class='border-0 my-1 accordion-item']"));
		if(listOfFAQ.size()>0)
		{
			for(int i = 0; i < listOfFAQ.size(); i++)
			{
				WebElement faq = listOfFAQ.get(i);
				WebElement question = faq.findElement(By.cssSelector(" h2[class*='Accordion_accordionTitle'] button"));
				String questionText = question.getText().replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "");
				if(questionText.equalsIgnoreCase(questionFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "")))
				{
					List<WebElement> FAQAnswers = faq.findElements(By.cssSelector(" div[class*='accordion-collapse'] div[class*='Accordion_accordionBody'] div"));
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
}
