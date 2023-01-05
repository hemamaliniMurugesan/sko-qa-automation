package com.seo.pompages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;

public class FrontEndAboutCourseLocator
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setHostURL;
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_107 version\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	public void getHostValue()
	{
		this.setEnvironment(setHost);
	}
	public String setEnvironment(String host)
	{
		if(host.equalsIgnoreCase("stage-in"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("qa-in"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("qa"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("stage"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("prod-in"))
		{
			setHost = "https://in.skillup.online";
		}
		else
		{
			setHost = "https://skillup.online";
		}
		return setHost;
	}
	
	public String getCourseCodeText(String code)
	{
		String courseIDFromBrowser = "";
		String CourseCodeStatus = null;
		HttpURLConnection huc = null;
		int respCode = 200;
		String addHosturl = this.setHost+"/courses/"+code;
		try
		{
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			System.out.println(respCode);
			if(respCode > 200 && !(respCode == 308))
			{
				System.out.println("broken link");
				System.exit(0);
			}
			else
			{
				System.out.println("un broken link");
				driver.get(addHosturl);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
				WebElement checkCourseCode = driver.findElement(By.xpath("(//*[contains(@href,'"+code+"')])[1]"));
				courseIDFromBrowser = checkCourseCode.getAttribute("href");
				/*
				 * int index = courseIDFromBrowser.lastIndexOf("/"); String getCourseID =
				 * courseIDFromBrowser.substring(index+0); getCourseID =
				 * getCourseID.replace("courses/", "");
				 */
				String[] word = courseIDFromBrowser.split("/");
				String lastWord = word[word.length-1];
				System.out.println("course ID from Browser : "+lastWord);
				System.out.println("courseIDFrom Excel: "+code);
				if(lastWord.contains(code))
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
	public String UIValidation(WebElement locator, String testData)
	{
		String checkStatus = "";
		String fontName="";
		String fontColor="";
		String hexColor="";
		String fontAlign ="";
		String fontStyle="";
		String fontSize="";
		String textBgColor="";
		String imageColor="";
		String hexBgColor="";
		try
		{
		if(testData.contains("fontName"))
		{
			fontName = locator.getCssValue("font-family");
		}
		else
		{
			System.out.println("fontName is not available ");
		}
		if(testData.contains("fontColor"))
		{
			fontColor = locator.getCssValue("color");
			hexColor = Color.fromString(fontColor).asHex()+";";
		}
		else
		{
			System.out.println("fontColor is not available");
		}
		if(testData.contains("fontAlign"))
		{
			fontAlign = locator.getCssValue("text-align");
		}
		else
		{
			System.out.println("fontAlign is not available");
		}
		if(testData.contains("fontStyle"))
		{
			fontStyle = locator.getCssValue("font-style");
		}
		else
		{
			System.out.println("fontStyle is not available");
		}
		if(testData.contains("fontSize"))
		{
			fontSize = locator.getCssValue("font-size");
		}
		else
		{
			System.out.println("fontSize is not available");
		}
		if(testData.contains("textBgColor"))
		{
			textBgColor = locator.getCssValue("background-color");
			hexBgColor = Color.fromString(textBgColor).asHex()+";";
		}
		else
		{
			System.out.println("textBgcolor is not available");
		}
		if(testData.contains("imageColor"))
		{
			imageColor = locator.getCssValue("imageColor");
		}
		else
		{
			System.out.println("imageColor is not available");
		}
		String key = null, value = null;
		String splitWord[] = testData.split("-split-");
		LinkedHashMap<String, String> kv = new LinkedHashMap<String, String>();
		for(int i = 0; i < splitWord.length; i++)
		{
			String word[] = splitWord[i].split("=");
			for(int j = 0; j < word.length; j++)
			{
				if(j == 0)
				{
					key = word[j];
				}
				else if(j == 1)
				{
					value = word[j];
				}
				kv.put(key, value);
			}
		}
		if(null != kv.get("fontName"))
		{
			if(fontName.contains(kv.get("fontName")))
			{
				System.out.println("fontName is same ");
				checkStatus = "true";
			}
			else
			{
				System.out.println("fontName is not same");
				System.out.println("UI fontName from browser is : "+fontName);
				checkStatus = "false";
			}	
		}
		if(null != kv.get("fontColor"))
		{
			if(hexColor.contains(kv.get("fontColor")))
			{
				System.out.println("fontColor is same");
				checkStatus = "true";
			}
			else
			{
				System.out.println("fontColor is not same");
				System.out.println("UI fontColor from browser is : "+fontColor);
				checkStatus = "false";
			}
		}
		if(null != kv.get("fontAlign"))
		{
			if(fontAlign.contains(kv.get("fontAlign")))
			{
				System.out.println("fontAlign is same");
				checkStatus = "true";
			}
			else
			{
				System.out.println("fontAlign is not same");
				System.out.println("UI fontAlign from browser is : "+fontAlign);
				checkStatus = "false";
			}	
		}
		if(null != kv.get("fontStyle"))
		{
			if(fontStyle.contains(kv.get("fontStyle")))
			{
				System.out.println("fontStyle is same");
				checkStatus = "true";
			}
			else
			{
				System.out.println("fontStyle is not same");
				System.out.println("UI fontStyle from browser is : "+fontStyle);
				checkStatus = "false";
			}
		}
		if(null != kv.get("fontSize"))
		{
			if(fontSize.contains(kv.get("fontSize")))
			{
				System.out.println("fontSize is same");
				checkStatus = "true";
			}
			else
			{
				System.out.println("fontSize is not same");
				System.out.println("UI fontSize from browser is : "+fontSize);
				checkStatus = "false";
			}
		}
		
		if(null != kv.get("textBgColor"))
		{
			if(hexBgColor.contains(kv.get("textBgColor")))
			{
				System.out.println("textBgColor is same");
				checkStatus = "true";	
			}
			else
			{
				System.out.println("textBgColor is not same");
				System.out.println("UI textBgColor from browser is : "+hexBgColor);
				checkStatus = "false";
			}
		}
		if(null != kv.get("imageColor"))
		{
			if(imageColor.contains(kv.get("imageColor")))
			{
				System.out.println("imageColor is same");
				checkStatus = "true";	
			}
			else
			{
				System.out.println("imageColor is not same");
				System.out.println("UI imageColor from browser is : "+imageColor);
				checkStatus = "false";
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkStatus;
	}
	
	public String checkCourseTitle(String courseTitleFromExcel)
	{
		String CourseTitleStatus = null;
		if(courseTitleFromExcel.equals("NA"))
		{
			CourseTitleStatus = "notProcessed";
		}
		else
		{
			System.out.println("course Title verification : ");
			WebElement title = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
			CourseTitleStatus = UIValidation(title, courseTitleFromExcel);
		}
		return CourseTitleStatus;
	}
	
	public String checkCourseDescription(String courseDescriptionFromExcel)
	{
		String checkDescriptionStatus = null;
		if(courseDescriptionFromExcel.equals("NA"))
		{
			courseDescriptionFromExcel = "notProcessed";
		}
		else
		{
			System.out.println("course Description verification : ");
			WebElement description = driver.findElement(By.cssSelector("div[class*='col-12 CourseDescription_courseText'] p"));
			checkDescriptionStatus = UIValidation(description, courseDescriptionFromExcel);
		}
		return checkDescriptionStatus;
	}
	
	public String checkCourseType(String courseTypeFromExcel)
	{
		String checkCourseTypeStatus = null;
		if(courseTypeFromExcel.equals("NA"))
		{
			checkCourseTypeStatus = "notProcessed";
		}
		else
		{
			System.out.println("course Type verification : ");
			WebElement courseType = driver.findElement(By.cssSelector("h2[class='text-uppercase']"));
			if(courseType.isDisplayed())
			{
				checkCourseTypeStatus = UIValidation(courseType, courseTypeFromExcel);
			}
			else
			{
				System.out.println("course type is not available in UI");
				checkCourseTypeStatus = "false";
			}
		}
		return checkCourseTypeStatus;
	}
	
	public String checkCourseLevel1(String courseLevel1FromExcel)
	{
		String checkCourseLevel1Status = null;
		if(courseLevel1FromExcel.equals("NA"))
		{
			checkCourseLevel1Status = "notProcessed";
		}
		else
		{
			System.out.println("course level 1 verification : ");
			WebElement courseLevel1 = driver.findElement(By.xpath("(//h3[@class='text-uppercase'])[1]"));
			if(courseLevel1.isDisplayed())
			{
				checkCourseLevel1Status = UIValidation(courseLevel1, courseLevel1FromExcel);
			}
			else
			{
				System.out.println("course level 1 is not available in UI");
				checkCourseLevel1Status = "false";
			}
		}
		return checkCourseLevel1Status;
	}
	public String checkCourseLevel2(String courseLevel2FromExcel)
	{
		String checkCourseLevel2Status = null;
		if(courseLevel2FromExcel.equals("NA"))
		{
			checkCourseLevel2Status = "notProcessed";
		}
		else
		{
			System.out.println("course level 2 verification");
			WebElement courseLevel2 = driver.findElement(By.xpath("(//h3[@class='text-uppercase'])[2]"));
			if(courseLevel2.isDisplayed())
			{
				checkCourseLevel2Status = UIValidation(courseLevel2, courseLevel2FromExcel);
			}
			else
			{
				System.out.println("course level 2 is not available in UI");
				checkCourseLevel2Status = "false";
			}
		}
		return checkCourseLevel2Status;
	}
	public ArrayList<String> checkStartsOn(ArrayList<String> courseStartsOnFromExcel)
	{
			ArrayList<String> checkStartsOnStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseStartsOnFromExcel;
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkStartsOnStatus.add("notProcessed");
					}
				}
				else if(getData.size() == 3)
				{
					if(j == 1)
					{
						System.out.println("starts on verification");
						List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
						for(int i = 0; i < courseTimeAndFee.size(); i++)
						{
							WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
							if(getLocator.getText().equals("StartsOn"))
							{
								checkStartsOnStatus.add(UIValidation(getLocator, getDataFromExcel));
							}
						}
					}
					if(j == 2)
					{
						System.out.println("starts on content verification");
						List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
						for(int i = 0; i < courseTimeAndFee.size(); i++)
						{
							WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
							if(getLocator.getText().equals("StartsOn"))
							{
								WebElement startsOnContent = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
								checkStartsOnStatus.add(UIValidation(startsOnContent, getDataFromExcel));
							}
						}
					}	
				}
				else
				{
					System.out.println("StartsOn is not available in UI");
					checkStartsOnStatus.add("false");
				}
			}
		return checkStartsOnStatus;
	}
	
	public ArrayList<String> checkDuration(ArrayList<String> courseDurationFromExcel)
	{
		ArrayList<String> checkDurationStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseDurationFromExcel;
		int j;
		for(j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkDurationStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				if(j == 1)
				{
					System.out.println("duration verification");
					List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
					for(int i = 0; i < courseTimeAndFee.size(); i++)
					{
						WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
						if(getLocator.getText().equals("Duration"))
						{
							checkDurationStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("duration content verification");
					List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
					for(int i = 0; i < courseTimeAndFee.size(); i++)
					{
						WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
						if(getLocator.getText().equals("Duration"))
						{
							WebElement durationContent = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
							checkDurationStatus.add(UIValidation(durationContent, getDataFromExcel));
						}
					}
				}	
			}
			else
			{
				System.out.println("duration is not available in UI");
				checkDurationStatus.add("false");
			}
		}
		return checkDurationStatus;
	}
	
	
	public ArrayList<String> checkFee(ArrayList<String> courseFeeFromExcel)
	{
		ArrayList<String> checkFeeStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseFeeFromExcel;
		int j;
		for(j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkFeeStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				if(j == 1)
				{
					System.out.println("fee verification");
					List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
					for(int i = 0; i < courseTimeAndFee.size(); i++)
					{
						WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
						if(getLocator.getText().equals("Fee"))
						{
							checkFeeStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("Fee content verification");
					List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp'] div[class='d-flex gap-2']"));
					for(int i = 0; i < courseTimeAndFee.size(); i++)
					{
						WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
						if(getLocator.getText().equals("Fee"))
						{
							WebElement durationContent = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
							checkFeeStatus.add(UIValidation(durationContent, getDataFromExcel));
						}
					}
				}	
			}
			else
			{
				System.out.println("Fee is not available in UI");
				checkFeeStatus.add("false");
			}
		}
		return checkFeeStatus;
	}
	
	public String checkEnroll(String courseEnrollFromExcel)
	{
		String checkCourseEnroll = null;
		if(courseEnrollFromExcel.equals("NA"))
		{
			checkCourseEnroll = "notProcessed";
		}
		else
		{
			System.out.println("enroll verification : ");
			WebElement Enroll = driver.findElement(By.xpath("//button[@class='CourseDescription_enrollNowBtn__xyx0I']"));
 			if(Enroll.getText().equalsIgnoreCase("Start Now") || Enroll.getText().equalsIgnoreCase("Enroll Now"))
			{
				checkCourseEnroll = UIValidation(Enroll, courseEnrollFromExcel);
			}
		}
		return checkCourseEnroll;
	}

	public String checkOverviewNavigation(String courseOverviewNavigationFromExcel)
	{
		String checkOverviewNavigationStatus = null;
		if(courseOverviewNavigationFromExcel.equals("NA"))
		{
			checkOverviewNavigationStatus = "notProcessed";
		}
		else
		{
			System.out.println("OverviewNavigation verification : ");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 100)");
			WebElement OverviewNavigation = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Overview')]"));
			checkOverviewNavigationStatus = UIValidation(OverviewNavigation, courseOverviewNavigationFromExcel);
		}
		return checkOverviewNavigationStatus;
	}
	public String checkOutlineNavigation(String courseOutlineNavigationFromExcel)
	{
		String checkOutlineNavigationStatus = null;
		if(courseOutlineNavigationFromExcel.equals("NA"))
		{
			checkOutlineNavigationStatus = "notProcessed";
		}
		else
		{
			System.out.println("OutlineNavigation verification : ");
			WebElement OutlineNavigation = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Details')]"));
			checkOutlineNavigationStatus = UIValidation(OutlineNavigation, courseOutlineNavigationFromExcel);
		}
		return checkOutlineNavigationStatus;
	}
	public String checkSkillupOnlineNavigation(String courseSkillUpNavigationFromExcel)
	{
		String checkSkillUpNavigationStatus = null;
		if(courseSkillUpNavigationFromExcel.equals("NA"))
		{
			checkSkillUpNavigationStatus = "notProcessed";
		}
		else
		{
			System.out.println("SkillUpNavigation verification : ");
			WebElement SkillUpNavigation = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Why SkillUp Online?')]"));
			checkSkillUpNavigationStatus = UIValidation(SkillUpNavigation, courseSkillUpNavigationFromExcel);
		}
		return checkSkillUpNavigationStatus;
	}
	public String checkFAQNavigation(String courseFAQNavigationFromExcel)
	{
		String checkFAQNavigationStatus = null;
		if(courseFAQNavigationFromExcel.equals("NA"))
		{
			checkFAQNavigationStatus = "notProcessed";
		}
		else
		{
			System.out.println("FAQNavigation verification : ");
			WebElement FAQNavigation = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'FAQs')]"));
			checkFAQNavigationStatus = UIValidation(FAQNavigation, courseFAQNavigationFromExcel);
		}
		return checkFAQNavigationStatus;
	}
	public String checkProgramOffering(String courseProgramOfferingFromExcel)
	{
		String checkProgramOfferingStatus = null;
		if(courseProgramOfferingFromExcel.equals("NA"))
		{
			checkProgramOfferingStatus = "notProcessed";
		}
		else
		{
			System.out.println("ProgramOffering verification : ");
			WebElement ProgramOffering = driver.findElement(By.xpath("(//section[@class='CourseOffering_mainSection__7IEXE']//h2[@class='CourseOffering_titleText__gSdn7'])[2]"));
			checkProgramOfferingStatus = UIValidation(ProgramOffering, courseProgramOfferingFromExcel);
		}
		return checkProgramOfferingStatus;
	}
	public ArrayList<String> checkTypeOfCertificate(ArrayList<String> courseTypeOfCertificateFromExcel)
	{
		ArrayList<String> checkTypeOfCertificateStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseTypeOfCertificateFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkTypeOfCertificateStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("Type of certificate Title verification");
				if(j == 1)
				{
					List<WebElement> typeOfCertificate = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < typeOfCertificate.size(); k++)
					{
						String getTypeOfCertificateText = typeOfCertificate.get(k).getText();
						if(getTypeOfCertificateText.equalsIgnoreCase("Type of certificate"))
						{
							WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
							checkTypeOfCertificateStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("Type of certificate content verification");
					List<WebElement> typeOfCertificate = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < typeOfCertificate.size(); k++)
					{
						String getTypeOfCertificateText = typeOfCertificate.get(k).getText();
						if(getTypeOfCertificateText.equalsIgnoreCase("Type of certificate"))
						{
							WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
							checkTypeOfCertificateStatus.add(UIValidation(getContentLocator, getDataFromExcel));
						}
					}
				}
				
			}			
					else
					{
						System.out.println("TypeOfCertificate is not available in UI");
						checkTypeOfCertificateStatus.add("false");
					}
				}
		return checkTypeOfCertificateStatus;
	}
	
	public ArrayList<String> checkAboutCourse(ArrayList<String> courseAboutCourseFromExcel)
	{
		ArrayList<String> checkAboutCourseStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseAboutCourseFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkAboutCourseStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("AboutCourse Title verification");
				if(j == 1)
				{
					List<WebElement> aboutCourse = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < aboutCourse.size(); k++)
					{
						String AboutCourseText = aboutCourse.get(k).getText();
						if(AboutCourseText.equalsIgnoreCase("About this program"))
						{
							WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
							checkAboutCourseStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("AboutCourse content verification");
					List<WebElement> aboutCourse = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < aboutCourse.size(); k++)
					{
						String AboutCourseText = aboutCourse.get(k).getText();
						if(AboutCourseText.equalsIgnoreCase("Type of certificate"))
						{
							WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
							checkAboutCourseStatus.add(UIValidation(getContentLocator, getDataFromExcel));
						}
					}
				}
				
			}			
					else
					{
						System.out.println("aboutCourse is not available in UI");
						checkAboutCourseStatus.add("false");
					}
				}
		return checkAboutCourseStatus;
	}
	
	public ArrayList<String> checkIncludes(ArrayList<String> courseIncludesFromExcel)
	{
		ArrayList<String> checkIncludesStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseIncludesFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkIncludesStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("Includes Title verification");
				if(j == 1)
				{
					List<WebElement> includes = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < includes.size(); k++)
					{
						String includeText = includes.get(k).getText();
						if(includeText.equalsIgnoreCase("Includes"))
						{
							WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
							checkIncludesStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("include content verification");
					List<WebElement> includes = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < includes.size(); k++)
					{
						String getTypeOfCertificateText = includes.get(k).getText();
						if(getTypeOfCertificateText.equalsIgnoreCase("Includes"))
						{
							WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
							checkIncludesStatus.add(UIValidation(getContentLocator, getDataFromExcel));
						}
					}
				}
				
			}			
					else
					{
						System.out.println("includes is not available in UI");
						checkIncludesStatus.add("false");
					}
				}
		return checkIncludesStatus;
	}
	
	public ArrayList<String> checkCreate(ArrayList<String> courseCreateFromExcel)
	{
		ArrayList<String> checkCreateStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseCreateFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkCreateStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("create Title verification");
				if(j == 1)
				{
					List<WebElement> create = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < create.size(); k++)
					{
						String createText = create.get(k).getText();
						if(createText.equalsIgnoreCase("Create"))
						{
							WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
							checkCreateStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("create content verification");
					List<WebElement> create = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < create.size(); k++)
					{
						String createText = create.get(k).getText();
						if(createText.equalsIgnoreCase("Create"))
						{
							WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
							checkCreateStatus.add(UIValidation(getContentLocator, getDataFromExcel));
						}
					}
				}
				
			}			
					else
					{
						System.out.println("create is not available in UI");
						checkCreateStatus.add("false");
					}
				}
		return checkCreateStatus;
	
	}

	public ArrayList<String> checkExerciseToExplore(ArrayList<String> courseExerciseToExploreFromExcel)
	{
		ArrayList<String> checkExerciseToExploreStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseExerciseToExploreFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkExerciseToExploreStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("ExerciseToExplore Title verification");
				if(j == 1)
				{
					List<WebElement> ExerciseToExplore = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < ExerciseToExplore.size(); k++)
					{
						String ExerciseToExploreText = ExerciseToExplore.get(k).getText();
						if(ExerciseToExploreText.equalsIgnoreCase("Exercises to explore"))
						{
							WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
							checkExerciseToExploreStatus.add(UIValidation(getLocator, getDataFromExcel));
						}
					}
				}
				if(j == 2)
				{
					System.out.println("ExerciseToExplore content verification");
					List<WebElement> ExerciseToExplore = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
					for(int k = 0; k < ExerciseToExplore.size(); k++)
					{
						String ExerciseToExploreText = ExerciseToExplore.get(k).getText();
						if(ExerciseToExploreText.equalsIgnoreCase("Exercises to explore"))
						{
							WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
							checkExerciseToExploreStatus.add(UIValidation(getContentLocator, getDataFromExcel));
						}
					}
				}
				
			}			
					else
					{
						System.out.println("ExerciseToExplore is not available in UI");
						checkExerciseToExploreStatus.add("false");
					}
				}
		return checkExerciseToExploreStatus;
	
	}
	
	public ArrayList<String> checkCourseOverview(ArrayList<String> courseOverviewFromExcel)
	{
		ArrayList<String> checkCourseOverviewStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = courseOverviewFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkCourseOverviewStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 3)
			{
				System.out.println("CourseOverview Title verification");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0, 100)");
				if(j == 1)
				{
					WebElement CourseOverview = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[1]"));
					checkCourseOverviewStatus.add(UIValidation(CourseOverview, getDataFromExcel));	
				}
				if(j == 2)
				{
					WebElement overViewContent = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[1]//button"));
					//overViewContent.click();
					WebElement overViewInfo = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionBody__JmtF4 accordion-body'])[1]"));
					checkCourseOverviewStatus.add(UIValidation(overViewInfo, getDataFromExcel));
					break;
				}
			}
			else
			{
				System.out.println("CourseOverview is not available in UI");
				checkCourseOverviewStatus.add("false");
			}
		}
		return checkCourseOverviewStatus;
	}
	
	public String checkHowItWorks(String HowItWorksFromExcel)
	{
		String checkHowItWorks = null;
		if(HowItWorksFromExcel.equals("NA"))
		{
			checkHowItWorks = "notProcessed";
		}
		else
		{
			System.out.println("HowItWorks verification");
			WebElement HowItWorksLocator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[2]"));
			checkHowItWorks = UIValidation(HowItWorksLocator, HowItWorksFromExcel);
		}
		return checkHowItWorks;
	}
	public String checkSkillsYouWillGain(String SkillsYouWillGainFromExcel)
	{
		String checkHowItWorks = null;
		if(SkillsYouWillGainFromExcel.equals("NA"))
		{
			checkHowItWorks = "notProcessed";
		}
		else
		{
			System.out.println("SkillsYouWillGain verification");
			WebElement SkillsYouWillGain = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[3]"));
			checkHowItWorks = UIValidation(SkillsYouWillGain, SkillsYouWillGainFromExcel);
		}
		return checkHowItWorks;
	}
	public String checkWhoShouldEnrollOnThisCourse(String whoShouldEnrollOnThisCourseFromExcel)
	{
		String checkWhoShouldEnrollOnThisCourse = null;
		if(whoShouldEnrollOnThisCourseFromExcel.equals("NA"))
		{
			checkWhoShouldEnrollOnThisCourse = "notProcessed";
		}
		else
		{
			System.out.println("WhoShouldEnrollOnThisCourse verification");
			WebElement WhoShouldEnrollOnThisCourse = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[3]"));
			checkWhoShouldEnrollOnThisCourse = UIValidation(WhoShouldEnrollOnThisCourse, whoShouldEnrollOnThisCourseFromExcel);
		}
		return checkWhoShouldEnrollOnThisCourse;
	}
	
	public String checkPrerequisites(String prerequisitesFromExcel)
	{
		String PrerequisitesStatus = null;
		if(prerequisitesFromExcel.equals("NA"))
		{
			PrerequisitesStatus = "notProcessed";
		}
		else
		{
			System.out.println("Prerequisite verification");
			WebElement Prerequisite = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[4]"));
			PrerequisitesStatus = UIValidation(Prerequisite, prerequisitesFromExcel);
		}
		return PrerequisitesStatus;
	}
	
	public String checkEarnYourCertificate(String EarnYourCertificateFromExcel)
	{
		String EarnYourCertificateStatus = null;
		if(EarnYourCertificateFromExcel.equals("NA"))
		{
			EarnYourCertificateStatus = "notProcessed";
		}
		else
		{
			System.out.println("EarnYourCertificate verification");
			WebElement EarnYourCertificate = driver.findElement(By.cssSelector("div[class='EarnCertificate_certificateText__B_vTA'] h2"));
			EarnYourCertificateStatus = UIValidation(EarnYourCertificate, EarnYourCertificateFromExcel);
		}
		return EarnYourCertificateStatus;
	}
	
	public String checkEarnYourCertificateContent(String EarnYourCertificateFromExcel)
	{
		String EarnYourCertificateStatus = null;
		if(EarnYourCertificateFromExcel.equals("NA"))
		{
			EarnYourCertificateStatus = "notProcessed";
		}
		else
		{
			System.out.println("EarnYourCertificate content verification");
			WebElement EarnYourCertificateContent = driver.findElement(By.cssSelector("div[class='EarnCertificate_certificateText__B_vTA'] p"));
			EarnYourCertificateStatus = UIValidation(EarnYourCertificateContent, EarnYourCertificateFromExcel);
		}
		return EarnYourCertificateStatus;
	}
	public String checkExpertTitle(String courseTitleFromExcel)
	{
		String checkExpertTitleStatus = null;
		if(courseTitleFromExcel.equals("NA"))
		{
			checkExpertTitleStatus = "notProcessed";
		}
		else
		{
			System.out.println("expertTitle verification");
			WebElement expertTitle = driver.findElement(By.cssSelector("section[class='LearnWithExperts_mainSection__ZB7fa'] h2[class='LearnWithExperts_titleText__SqJj9']"));
			checkExpertTitleStatus = UIValidation(expertTitle, courseTitleFromExcel);
		}
		return checkExpertTitleStatus;
	}
	
	public String checkExpertName(String courseExpertNameFromExcel)
	{
		String checkExpertNameStatus = null;
		if(courseExpertNameFromExcel.equals("NA"))
		{
			checkExpertNameStatus = "notProcessed";
		}
		else
		{
			System.out.println("expertName verification");
			WebElement expertName = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//h2"));
			checkExpertNameStatus = UIValidation(expertName, courseExpertNameFromExcel);
		}
		return checkExpertNameStatus;
	}
	
	public String checkExpertRole(String courseExpertRoleFromExcel)
	{
		String checkExpertRoleStatus = null;
		if(courseExpertRoleFromExcel.equals("NA"))
		{
			checkExpertRoleStatus = "notProcessed";
		}
		else
		{
			System.out.println("expertRole verification");
			WebElement expertRole = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//p"));
			checkExpertRoleStatus = UIValidation(expertRole, courseExpertRoleFromExcel);
		}
		return checkExpertRoleStatus;
	}
	
	public String checkExpertLink(String courseExpertLinkFromExcel)
	{
		String checkExpertLinkStatus = null;
		if(courseExpertLinkFromExcel.equals("NA"))
		{
			checkExpertLinkStatus = "notProcessed";
		}
		else
		{
			System.out.println("ExpertLink verification");
			WebElement ExpertLink = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//a"));
			checkExpertLinkStatus = UIValidation(ExpertLink, courseExpertLinkFromExcel);
		}
		return checkExpertLinkStatus;
	}
	
	public String checkNewsLetterUpdates(String NewsLetterUpdatesFromExcel)
	{
		String checkNewsLetterUpdates = null;
		if(NewsLetterUpdatesFromExcel.equals("NA"))
		{
			checkNewsLetterUpdates = "notProcessed";
		}
		else
		{
			System.out.println("NewsLetterUpdates verification");
			WebElement NewsLetterUpdates = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/h2"));
			checkNewsLetterUpdates = UIValidation(NewsLetterUpdates, NewsLetterUpdatesFromExcel);
		}
		return checkNewsLetterUpdates;
	}
	
	public String checkSubscribeContent(String SubscribeContentFromExcel)
	{
		String checkSubscribeContentStatus = null;
		if(SubscribeContentFromExcel.equals("NA"))
		{
			checkSubscribeContentStatus = "notProcessed";
		}
		else
		{
			System.out.println("SubscribeContent verification");
			WebElement SubscribeContent = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/p"));
			checkSubscribeContentStatus = UIValidation(SubscribeContent, SubscribeContentFromExcel);
		}
		return checkSubscribeContentStatus;
	}
	
	public String checkFullName(String courseTitleFromExcel)
	{
		String checkfullNameStatus = null;
		if(courseTitleFromExcel.equals("NA"))
		{
			checkfullNameStatus = "notProcessed";
		}
		else
		{
			System.out.println("fullName verification");
			WebElement fullName = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//input[@name='full_name']"));
			checkfullNameStatus = UIValidation(fullName, courseTitleFromExcel);
		}
		return checkfullNameStatus;
	}
	
	public String checkEmail(String courseEmailFromExcel)
	{
		String checkEmailStatus = null;
		if(courseEmailFromExcel.equals("NA"))
		{
			checkEmailStatus = "notProcessed";
		}
		else
		{
			System.out.println("email verification");
			WebElement email = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//input[@name='email']"));
			checkEmailStatus = UIValidation(email, courseEmailFromExcel);
		}
		return checkEmailStatus;
	}
	
	public String checkSubscribeButton(String courseSubscribeButtonFromExcel)
	{
		String checkSubscribeButtonStatus = null;
		if(courseSubscribeButtonFromExcel.equals("NA"))
		{
			checkSubscribeButtonStatus = "notProcessed";
		}
		else
		{
			System.out.println("subscribeButton verification");
			WebElement subscribeButton = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//button[contains(@class,'NewsAndUpdates_subscribeBtn')]"));
			checkSubscribeButtonStatus = UIValidation(subscribeButton, courseSubscribeButtonFromExcel);
		}
		return checkSubscribeButtonStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnline(ArrayList<String> WhyLearnSkillupOnlineFromExcel)
	{
		ArrayList<String> checkWhyLearnSkillupOnlineStatus = new ArrayList<String>();
		ArrayList<String> getData = new ArrayList<String>();
		getData = WhyLearnSkillupOnlineFromExcel;
		for(int j = 0; j < getData.size(); j++)
		{
			String getDataFromExcel = getData.get(j);
			if(getData.size() == 2)
			{
				if(getData.get(j).equals("NA"))
				{
					checkWhyLearnSkillupOnlineStatus.add("notProcessed");
				}
			}
			else if(getData.size() == 8)
			{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0, 300)");
				if(j == 1)
				{
					System.out.println("WhyLearnSkillupOnlineTitle verification");
					WebElement WhyLearnSkillupOnlineTitle = driver.findElement(By.xpath("//section[@class='WhyLearnSkillUp_mainSection__pNbU3']//h2[@class='WhyLearnSkillUp_titleText__N8j59']"));
					checkWhyLearnSkillupOnlineStatus.add(UIValidation(WhyLearnSkillupOnlineTitle, getDataFromExcel));
				}
				if(j == 2)
				{
					System.out.println("WhyLearnSkillupOnlineTitle Description");
					WebElement WhyLearnSkillupOnlineDescription = driver.findElement(By.xpath("//section[@class='WhyLearnSkillUp_mainSection__pNbU3']//p[@class='WhyLearnSkillUp_descriptionText__iMmGu']"));
					checkWhyLearnSkillupOnlineStatus.add(UIValidation(WhyLearnSkillupOnlineDescription, getDataFromExcel));
				}
				if(j == 3)
				{
					System.out.println("subTitlesTickIcon verification");
					List<WebElement> subTitlesTickIcon = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_pointSection__t2So_'] div[class='WhyLearnSkillUp_point__XQHsy'] img[alt='tick']"));
					for(int i = 0; i < subTitlesTickIcon.size(); i++)
					{
						checkWhyLearnSkillupOnlineStatus.add(UIValidation(subTitlesTickIcon.get(i), getDataFromExcel));
					}
				}
				if(j == 4)
				{
					System.out.println("subTitlesTickInfo verification");
					List<WebElement> subTitlesTickInfo = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_pointSection__t2So_'] div[class='WhyLearnSkillUp_point__XQHsy'] h2"));
					for(int i = 0; i < subTitlesTickInfo.size(); i++)
					{
						checkWhyLearnSkillupOnlineStatus.add(UIValidation(subTitlesTickInfo.get(i), getDataFromExcel));
					}
				}
				if(j == 5)
				{
					JavascriptExecutor js1 = (JavascriptExecutor)driver;
					js1.executeScript("window.scrollBy(0, 100)");
					System.out.println("profitPointSection verification");
					List<WebElement> profitPointSection = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] img[alt='icon']"));
					for(int i = 0; i < profitPointSection.size(); i++)
					{
						profitPointSection.get(i);
						checkWhyLearnSkillupOnlineStatus.add(UIValidation(profitPointSection.get(i), getDataFromExcel));
					}
				}
				if(j == 6)
				{
					System.out.println("profitPointSectionHeading verification");
					List<WebElement> profitPointSectionHeading = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] div[class='col-sm-4 col-12']"));
					for(int i = 0; i < profitPointSectionHeading.size(); i++)
					{
						profitPointSectionHeading.get(i);
						checkWhyLearnSkillupOnlineStatus.add(UIValidation(profitPointSectionHeading.get(i), getDataFromExcel));
					}
				}
				if(j == 7)
				{
					System.out.println("profitPointSectionDescription verification");
					List<WebElement> profitPointSectionDescription = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] div[class='col'] p"));
					for(int i = 0; i < profitPointSectionDescription.size(); i++)
					{
						profitPointSectionDescription.get(i);
						checkWhyLearnSkillupOnlineStatus.add(UIValidation(profitPointSectionDescription.get(i), getDataFromExcel));
					}
				}
			}
			else
			{
				System.out.println("WhyLearnSkillupOnline is not available in UI");
				checkWhyLearnSkillupOnlineStatus.add("false");
			}
		}
		return checkWhyLearnSkillupOnlineStatus;
	
	}
	
	public String checkFAQs(String FAQsFromExcel)
	{
		String checkFAQsStatus = null;
		if(FAQsFromExcel.equals("NA"))
		{
			checkFAQsStatus = "notProcessed";
		}
		else
		{
			System.out.println("FAQs verification");
			WebElement FAQs = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
			checkFAQsStatus = UIValidation(FAQs, FAQsFromExcel);
		}
		return checkFAQsStatus;
	}
	
	public String checkQuestion(String QuestionFromExcel)
	{
		String checkQuestionStatus = null;
		if(QuestionFromExcel.equals("NA"))
		{
			checkQuestionStatus = "notProcessed";
		}
		else
		{
			System.out.println("Question verification");
			WebElement Question = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
			checkQuestionStatus = UIValidation(Question, QuestionFromExcel);
		}
		return checkQuestionStatus;
	}
	
	public String checkAnswer(String courseAnswerFromExcel)
	{
		String checkAnswerStatus = null;
		if(courseAnswerFromExcel.equals("NA"))
		{
			checkAnswerStatus = "notProcessed";
		}
		else
		{
			System.out.println("Answer verification");
			WebElement answer = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
			checkAnswerStatus = UIValidation(answer, courseAnswerFromExcel);
		}
		return checkAnswerStatus;
	}
	
}
