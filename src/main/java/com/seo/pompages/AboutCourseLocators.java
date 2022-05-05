package com.seo.pompages;
import java.awt.Robot;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;

public class AboutCourseLocators
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
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Skillup 200\\Downloads\\chromedriver_win32_101 version\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));

	}
	
	public String getCourseCodeText(String code)
	{
		String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		try
		{
			String addHosturl = ConfigFileReader.getAboutCourseURL()+code+"/about";
			driver.get(addHosturl);
			//WebElement CheckCourseCode = driver.findElement(By.xpath("//*[contains(@value, 'course-v1:SkillUp+SKOPL200ILT+2022_Q1')]"));
			WebElement checkCourseCode = driver.findElement(By.xpath("//input[@id=\"social_input\"]"));
			courseIDFromBrowser = checkCourseCode.getAttribute("value");
			System.out.println(courseIDFromBrowser);
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
		String courseTitleText = "";
		try
		{
			WebElement title = driver.findElement(By.xpath("//h1"));
			courseTitleText = title.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseTitleText;
	}
	
	public String getCourseOrganizationImgAltText()
	{
		String courseOrg = "";
		try
		{
			WebElement orgImg = driver.findElement(By.cssSelector("div.ConTianer_ConTent > div:first-child > div:last-child > a > img"));
			courseOrg = orgImg.getAttribute("alt");
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
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 400)");
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
						String content = infoContent.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
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
		js.executeScript("window.scrollBy(0, -600)");
		
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
	
	public String getEarnCertificateText(String earnYourCertificateContentFromExcel)
	{
		String certificateName = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 700)");
		String getEarnCertificateText = "";
		try
		{
			WebElement earnCertify = driver.findElement(By.cssSelector("div[class='certificate_wrap'] p:nth-child(2)"));
			getEarnCertificateText = earnCertify.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			if(getEarnCertificateText.equalsIgnoreCase(earnYourCertificateContentFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				System.out.println(getEarnCertificateText);
			}
			WebElement clickEarnCertificate = driver.findElement(By.cssSelector("div[class='certificate_wrap'] a[id='certificate-preview-btn']"));
			clickEarnCertificate.click();
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
					certificateName = checkCourseNameFromImage.getAttribute("alt").replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					System.out.println(certificateName);
					  //WebElement closePopUp = driver.findElement(By.cssSelector("section[class=\"login\"] div#certificate-preview div[class=\"log-in-pop\"] div[class=\"modal-header\"] > button"));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class=\"login\"] div#certificate-preview div[class=\"log-in-pop\"] div[class=\"modal-header\"] > button"))).click();
						/*
						 * JavascriptExecutor js2 = (JavascriptExecutor) driver;
						 * js2.executeScript("arguments[0].click()", closePopUp);
						 */
						/*
						 * Actions action = new Actions(driver);
						 * action.moveToElement(closePopUp).click().build().perform();
						 */
				  }
			  }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return certificateName;
	}
	
	public String getTypeofCertificate()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, -2400)");
		String checkCertificateContent = "";
		try
		{
			WebElement checkCertificate =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(1)"));
			WebElement checkCourseIcon = checkCertificate.findElement(By.cssSelector(" img"));
			checkCourseIcon.getAttribute("alt");
			System.out.println(checkCourseIcon.getAttribute("alt"));
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
			WebElement checkAboutCourse =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(2)"));
			WebElement checkAboutIcon = checkAboutCourse.findElement(By.cssSelector(" img"));
			checkAboutIcon.getAttribute("alt");
			System.out.println(checkAboutIcon.getAttribute("alt"));
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
			WebElement checkIncludes =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(3)"));
			WebElement checkIncludesIcon = checkIncludes.findElement(By.cssSelector(" img"));
			checkIncludesIcon.getAttribute("alt");
			System.out.println(checkIncludesIcon.getAttribute("alt"));
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
		js.executeScript("window.scrollBy(0, 500)");
		try
		{
			WebElement checkCreate =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(4)"));
			WebElement checkCreateIcon = checkCreate.findElement(By.cssSelector(" img"));
			checkCreateIcon.getAttribute("alt");
			System.out.println(checkCreateIcon.getAttribute("alt"));
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
		js.executeScript("window.scrollBy(0, 800)");
		try
		{
			WebElement checkExerciseToExplore =  driver.findElement(By.cssSelector("div.SideBarMain > div:nth-child(5)"));
			WebElement checkExerciseToExploreIcon = checkExerciseToExplore.findElement(By.cssSelector(" img"));
			checkExerciseToExploreIcon.getAttribute("alt");
			System.out.println(checkExerciseToExploreIcon.getAttribute("alt"));
			WebElement checkExerciseToExploreText = checkExerciseToExplore.findElement(By.cssSelector(" div.SideBarCoLMN_Right ul"));
			checkExerciseToExploreContent = checkExerciseToExploreText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkExerciseToExploreContent;
	}
	
	public String getExperts()
	{
		String checkExpertsContent = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, -1000)");
		try
		{
			WebElement checkExerciseToExploreText = driver.findElement(By.xpath("(//div[@class=\"SideBarMain\"])[2]//div[@class=\"SideBarCoLMN_Right\"]//ul"));
			checkExpertsContent = checkExerciseToExploreText.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			System.out.println(checkExpertsContent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkExpertsContent;
	}
	
	public String getflatPrice(String flatPriceWithoutGSTFromExcel)
	{
		String checkPriceWOGST = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, -1400)");
		try
		{
			List<WebElement> checkPriceWithoutGSTValue = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td > p"));
			for(int i = 0; i < checkPriceWithoutGSTValue.size(); i++)
			{
				 Thread.sleep(500);
				if(checkPriceWithoutGSTValue.get(i).getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").equalsIgnoreCase(flatPriceWithoutGSTFromExcel))
				{
					String PriceWOGST = checkPriceWithoutGSTValue.get(i).getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "");
					Thread.sleep(2000);
					System.out.println(PriceWOGST);
					checkPriceWOGST = "Success";
				}
				else
				{
					System.out.println("not same price");
					checkPriceWOGST = "Fail";
				}
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
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0, -1300)");
				List<WebElement> checkPriceUSDValue = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td > p"));
				for(int i = 0; i < checkPriceUSDValue.size(); i++)
				{
					if(checkPriceUSDValue.get(i).getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").equalsIgnoreCase(priceUSDFromExcel))
					{
						String checkFlatPriceUSD = checkPriceUSDValue.get(i).getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "");
						System.out.println(checkFlatPriceUSD);
						checkUSDStatus = "Success";
					}
					else
					{
						System.out.println("not same price");
					}
				}
			}
			else
			{
				checkUSDStatus = "Success";
			}
		}
		catch(Exception e)
		{
			System.out.println("This is india site. so USD wont check");
		}
		return checkUSDStatus;
		
	}
}
