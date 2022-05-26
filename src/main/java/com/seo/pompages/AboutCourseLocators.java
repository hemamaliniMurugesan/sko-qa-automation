package com.seo.pompages;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;
import com.seo.utility.Utils;

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
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Skillup 200\\Downloads\\chromedriver_win32_101.0.4951.41version\\chromedriver.exe");
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
		String courseTitleText = "";
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
	
	public String getCourseOrganizationImgAltText()
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
			System.out.println("getEarnCertificateText from browser : "+getEarnCertificateText);
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
						System.out.println("certificateName from browser : "+certificateName);
						Thread.sleep(500);
						//WebElement closePopUp = driver.findElement(By.cssSelector("section[class=\"login\"] div#certificate-preview div[class=\"log-in-pop\"] div[class=\"modal-header\"] > button"));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class=\"login\"] div#certificate-preview div[class=\"log-in-pop\"] div[class=\"modal-header\"] > button"))).click();
						Thread.sleep(500);
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
		js.executeScript("window.scrollBy(0, 500)");
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
		js.executeScript("window.scrollBy(0, 800)");
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
	
	public String getAttributeOfTag(String selector, String attribute)
	{
		String attributeValue = null;
		try
		{
			WebElement tag = driver.findElement(By.cssSelector(selector));
			attributeValue = tag.getAttribute(attribute).replaceAll("\\s", "").replaceAll("\u00A0", "").trim();
			System.out.println(attributeValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return attributeValue;
	}
	
	public HashMap<String, HashMap<String, String>> getExperts()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 300)");
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
					String name = expertElement.findElement(By.cssSelector(".SideBarCoLMN_Right p")).getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					expert.put("name", name);
					List<WebElement> liTags = expertElement.findElements(By.cssSelector(".SideBarCoLMN_Right ul > li"));
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
					String styleOfImage = expertElement.findElement(By.cssSelector(".SideBarCoLMN_LeFT > span")).getAttribute("style");
					expert.put("style", styleOfImage);
					
					experts.put(name, expert);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return experts;
		
		
	}
	
	public String getStartsOn(String startsOnFromExcel)
	{
		String checkStartson = "";
		try
		{
			List<WebElement> checkStartsOnImg = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td img:not(td[width=\"2%\"]"));
			for(int i = 0; i < checkStartsOnImg.size(); i++)
			{
				if(checkStartsOnImg.get(i).getAttribute("alt").contains("Enrollment"))
				{
					System.out.println(checkStartsOnImg.get(i).getAttribute("alt"));
				}
			}
			List<WebElement> checkStartOnText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:not(td[width=\"2%\"]):not(td[class=\"PADDLFT\"])"));
			for(int j = 0; j < checkStartOnText.size(); j++)
			{
				WebElement getStartOnText = checkStartOnText.get(j).findElement(By.cssSelector(" [class=\"Sk-Currency evergreen-text\"]"));
				if(getStartOnText.getText().equalsIgnoreCase("Starts on"))
				{
					System.out.println(getStartOnText.getText());
					WebElement getStartOnValue = checkStartOnText.get(j).findElement(By.cssSelector(" [class=\"evergreen-text\"]"));
					String getStartsOn = getStartOnValue.getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					System.out.println("startsOn from Browser :"+getStartsOn);
					checkStartson = "success";
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
		String checkDuration = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, -1400)");
		try
		{
		List<WebElement> checkDurationImg = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td img:not(td[width=\"2%\"]"));
		for(int i = 0; i < checkDurationImg.size(); i++)
		{
			if(checkDurationImg.get(i).getAttribute("alt").contains("duration"))
			{
				System.out.println(checkDurationImg.get(i).getAttribute("alt"));
			}
		}
				List<WebElement> getDurationText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:not(td[width=\"2%\"]):not(td[class=\"PADDLFT\"]) [class=\"Sk-Currency evergreen-text\"]"));
				for(int k = 0; k < getDurationText.size(); k++)
				{
					if(getDurationText.get(k).getText().equalsIgnoreCase("duration"))
					{
						System.out.println(getDurationText.get(k).getText());
					}
				}
				List<WebElement> checkDurationText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:not(td[width=\"2%\"]):not(td[class=\"PADDLFT\"]) [class=\"evergreen-text\"]"));
				for(int j = 0; j < checkDurationText.size(); j++)
				{
				String getDuration = checkDurationText.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("duration from browser : "+getDuration);
				if(getDuration.equalsIgnoreCase(durationFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					checkDuration = "success";
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
		String checkPriceWOGST = "";
		
		try
		{
			if(driver.getCurrentUrl().contains("in."))
			{
				List<WebElement> checkPriceWithoutGSTValue = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td img:not(td[width=\"2%\"]"));
				for(int i = 0; i < checkPriceWithoutGSTValue.size(); i++)
				{
					if(checkPriceWithoutGSTValue.get(i).getAttribute("alt").contains("Course Fee"))
					{
						System.out.println(checkPriceWithoutGSTValue.get(i).getAttribute("alt"));
					}
				}	
				List<WebElement> checkFlatText = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td:not(td[width=\"2%\"]):not(td[class=\"PADDLFT\"])"));
				for(int j = 0; j < checkFlatText.size(); j++)
				{
					WebElement getFlatText = checkFlatText.get(j).findElement(By.cssSelector(" [class=\"Sk-Currency evergreen-text\"]"));
					if(getFlatText.getText().equalsIgnoreCase("Fee"))
					{
						System.out.println(getFlatText.getText());
						WebElement getFlatValue = checkFlatText.get(j).findElement(By.cssSelector(" [class=\"evergreen-text\"]"));
						String getFlat = getFlatValue.getAttribute("textContent").replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println("Flat price from excel : "+flatPriceWithoutGSTFromExcel);
						System.out.println("Flat price from Browser : "+getFlat);
						if(getFlat.equalsIgnoreCase(flatPriceWithoutGSTFromExcel))
						{
							checkPriceWOGST = "success";
						}
					}
					else
					{
						checkPriceWOGST = "fail";
					}
				}
				
			}
			else
			{
				checkPriceWOGST = "success";
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
					if(checkPriceUSDValue.get(i).getText().replaceAll("[^\\d.]", "").replaceAll("[^0-9?!\\.]","").replaceAll("\\s", "").equalsIgnoreCase(priceUSDFromExcel.replaceAll("[^\\d.]", "").replaceAll("\\s", "")))
					{
						String checkFlatPriceUSD = checkPriceUSDValue.get(i).getText().replaceAll("[^0-9?!\\.]","").replaceAll("\\s", "");
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
				checkUSDStatus = "success";
			}
		}
		catch(Exception e)
		{
			System.out.println("This is india site. so USD wont check");
		}
		return checkUSDStatus;
	}
	
}

