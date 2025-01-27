package com.seo.pompages;
import java.io.File;
import java.net.HttpURLConnection;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.utility.TestUtil;

public class AboutCourseLocators
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setLoginURL;
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_105.0.5195.52 version\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	
	public String setEnvironment(String host)
	{
		if(!host.equalsIgnoreCase("prod"))
		{
			setHost = "https://"+host+"-in.skillup.online/courses/";
		}
		else
		{
			setHost = "https://"+host+".skillup.online";
		}
		return setHost;
	}
	
	public String setSEOLoginURL(String host)
	{
		if(!host.equalsIgnoreCase("prod"))
		{
			setLoginURL = "https://"+host+"-in.skillup.online";
		}
		else
		{
			setLoginURL = "https://"+host+".skillup.online";
		}
		return setLoginURL;
	}
	
	public String getCourseCodeText(String code)
	{
		String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		HttpURLConnection huc = null;
		int respCode = 200;
		String addHosturl = this.setHost+code+"/about";
		try
		{
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			System.out.println(respCode);
			if(respCode > 200)
			{
				System.out.println("broken link");
				System.exit(0);
			}
			else
			{
				System.out.println("un broken link");
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CourseCodeStatus;
	}
	
	String courseTitleText = "";
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
			List<WebElement> listOfCourseInfo = driver.findElements(By.cssSelector("div#overview div[class=\"CourseOverview_accordionItem__9k4Ix accordion-item\"]"));
			//js.executeScript("arguments[0].scrollIntoView();", listOfCourseInfo);
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
							String content = infoContent.get(j).getAttribute("textContent").replaceAll("\\s", "").replaceAll("\u00A0","").replaceAll("[^\\p{ASCII}]", "");
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
				System.out.println("Excel Data of course overview is not same as UI");
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
						System.out.println("Duration is available");
					}
				}
				List<WebElement> checkDurationImg = driver.findElements(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td>img"));
				for(int i = 0; i < checkDurationImg.size(); i++)
				{
					if(checkDurationImg.get(i).getAttribute("alt").contains("duration"))
					{
						System.out.println("Duration icon is displayed :"+checkDurationImg.get(i).getAttribute("alt"));
					}
				}
				WebElement checkDurationText = driver.findElement(By.cssSelector("div[class='TabLEColUN DESKTOPTABCOLUMN'] > table > tbody > tr > td ul"));
				String getDuration = checkDurationText.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				System.out.println("duration from browser : "+getDuration);
				if(getDuration.equalsIgnoreCase(durationFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					checkDuration = "success";
				}
				else
				{
					checkDuration = "fail";
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
					WebElement clickCourseDropdown = driver.findElement(By.cssSelector("a#NavMegamenu"));
					clickCourseDropdown.click();
					List<WebElement> dropdownList = driver.findElements(By.cssSelector("div#MMDroPDoWNMAiN ul[class=\"categorylist dropdown-submenu\"] li"));
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
		}
				return categoryStatus;
	}
	
}
