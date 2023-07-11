package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;

public class PLULocators
{
	WebDriver driver;
	
	public PLULocators(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	public String verifyTitle(String titleName)
	{
		String status="";
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			WebElement clickDropdown = driver.findElement(By.cssSelector("li[class*='nav-item dropdown Header_dropdown']>a[id='navbarDropdown']"));
			clickDropdown.click();
			List<WebElement> learningPartners = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='LearningPartners catcolumn'] li a"));
			for(int i = 0; i < learningPartners.size(); i++)
			{
				if(learningPartners.get(i).getAttribute("href").contains("pacific"))
				{
					learningPartners.get(i).click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				}
			}
			WebElement name = driver.findElement(By.cssSelector("h1[class*='TopBanner_universityName']"));
			String nameContent = name.getText().trim();
			if(nameContent.equalsIgnoreCase(titleName.trim()))
			{
				status = "pass";
			}
			else
			{
				status = "fail";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	public String verifyBanner(String bannerData)
	{
		String status="";
		WebElement banner = driver.findElement(By.cssSelector("p[class*='TopBanner_coursesName']"));
		String bannerText = banner.getText().trim();
		if(bannerText.equalsIgnoreCase(bannerData.trim()))
		{
			status = "pass";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	
	public String verifyDescription(String data)
	{
		String status="";
		WebElement description = driver.findElement(By.cssSelector("div[class*='PluIntroduction_introductionContainer'] p"));
		String descriptionText = description.getText().trim();
		if(data.equalsIgnoreCase(descriptionText.trim()))
		{
			status = "pass";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public ArrayList<String> verifyPrograms(ArrayList<String>  programs)
	{
		ArrayList<String> failedUrls = new ArrayList<String>();
		
		ArrayList<String> overallFail = new ArrayList<String>();
		String enrollProcessStatus = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try
		{
			js.executeScript("window.scrollBy(0, 500)", "");
			List<WebElement> techProgram = driver.findElements(By.cssSelector("section[class*='container-fluid TechPrograms_mainContainer'] div[class*='TechPrograms_cardWrapper'] a"));
			for(int i = 0; i < techProgram.size(); i++)
			{
				String url = techProgram.get(i).getAttribute("href");
				System.out.println("tech program starts execution : "+url);
				String urlLinkStatus = this.checkURLStatus(url);
				if(urlLinkStatus.equalsIgnoreCase("fail"))
				{
					failedUrls.add(url);
				}
			}
			Thread.sleep(3000);
			System.out.println("tech pgms card validation started");
			ArrayList<String> statusOfPgmCard = new ArrayList<String>();
			
			List<WebElement> pgms = driver.findElements(By.cssSelector("section[class='container-fluid TechPrograms_mainContainer__7XUwz'] div[class='row g-3']>div"));
			for(int i = 0; i < pgms.size(); i++)
			{
				ArrayList<String> failCase = new ArrayList<String>();
				boolean testingStatus=true;
				WebElement eachPgmsFromCard = pgms.get(i);
				String getPgmName = eachPgmsFromCard.getAttribute("href");
				WebElement pgmIconFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='row TechPrograms_cardContent__f0QGv']>div[class='col-12 gy-2'] img[src*='images']"));
				if(pgmIconFromCard.isDisplayed())
				{
					statusOfPgmCard.add("pass");
				}
				else 
				{
					failCase.add("issue in program icon in "+getPgmName+"");
					testingStatus = false;
				}
				//WebElement pluFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='row TechPrograms_cardContent__f0QGv']>div[class='col-12 gy-2'] div[class*='TechPrograms_plu'] p"));
				WebElement pluPgmNameFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='col-12 gy-2']:nth-child(2) h3"));
				if(pluPgmNameFromCard.isDisplayed())
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("issue in pgm name in "+getPgmName+"");
					testingStatus = false;
				}
				WebElement plulevelFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='col-12 gy-2']:nth-child(2) p[class='TechPrograms_mentoredBe__1oodQ']"));
				String getTextFromPLULevel = plulevelFromCard.getText();
				WebElement pluAmountFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='col-12 gy-4'] div[class='row TechPrograms_cardFooterContent__gtt1W']>div:nth-child(2)>p"));
				WebElement pluEnrollmentStatusFromCard = pgms.get(i).findElement(By.cssSelector(" div[class='col-12 gy-4'] div[class='row TechPrograms_cardFooterContent__gtt1W']>div:nth-child(2)>div>p"));
				String enrollStatusFromCard = pluEnrollmentStatusFromCard.getText();
				if(enrollStatusFromCard.equals("ENROLLMENT OPEN"))
				{
					enrollProcessStatus = "open";
				}
				else if(enrollStatusFromCard.equals("ENROLLMENT CLOSE"))
				{
					enrollProcessStatus = "close";
					
				}
				WebElement launchPgm = pgms.get(i).findElement(By.cssSelector(" a"));
				
				String parentwindow = driver.getWindowHandle();
				
				String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); //Keys.chord(Keys.CONTROL,Keys.RETURN)
				launchPgm.sendKeys(selectLinkOpeninNewTab); 
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				for(String winHandle : driver.getWindowHandles()){
				    driver.switchTo().window(winHandle);
				}
				JavascriptExecutor js2 = (JavascriptExecutor) driver;
				String enrollProcessFromPgm = "";
				String programName = driver.findElement(By.xpath("//h1")).getText();
				WebElement IconFromProgram = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center']>div[class='col d-flex align-items-center']:nth-child(1)>span"));
				if(IconFromProgram.isDisplayed())
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("issue in program icon in "+programName+"");
					testingStatus = false;
				}
				WebElement titleFromProgram = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center']>div[class='col-12 CourseDescription_courseText__7vJLl'] h1"));
				if(titleFromProgram.isDisplayed())
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("issue in title in "+programName+"");
					testingStatus = false;
				}
				js2.executeScript("window.scrollBy(0,300)", "");
				Thread.sleep(1000);
				WebElement level1FromProgram = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='CourseDescription_levelSection__BiiUm']>[class*='uppercase CourseDescription_pluTheme']:nth-child(1)"));
				if(getTextFromPLULevel.contains(level1FromProgram.getText()))
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("level 1 not same in "+programName+"");
					testingStatus = false;
				}
				WebElement level2FromProgram = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='CourseDescription_levelSection__BiiUm']>[class*='uppercase CourseDescription_pluTheme']:nth-child(2)"));
				String level2 = level2FromProgram.getText().toLowerCase();
						
				if(getTextFromPLULevel.toLowerCase().contains(level2))
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("level 2 not same in "+programName+"");
					testingStatus = false;
				}
				WebElement level3FromProgram = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='CourseDescription_levelSection__BiiUm']>[class*='uppercase CourseDescription_pluTheme']:nth-child(3)"));
				if(getTextFromPLULevel.toLowerCase().contains(level3FromProgram.getText().toLowerCase()))
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("level 3 not same in "+programName+"");
					testingStatus = false;
				}
				WebElement amountFromPgm = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='d-flex gap-2']:nth-child(3) div[class='CourseDescription_courseAboutTextSection__8_6ac'] p"));
				if(!amountFromPgm.getText().equals("null"))
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("amount is null in "+programName+"");
					testingStatus = false;
				}
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				int size = driver.findElements(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='col-12'] button[class*='CourseDescription_enrollNowBtnPLU']")).size();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				if(size>0)
				{
					WebElement enrollStatus = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='col-12'] button[class*='CourseDescription_enrollNowBtnPLU']"));
					if(enrollStatus.getText().equals("Enroll Now"))
					{
						enrollProcessFromPgm = "open";
					}
				}
				else
				{
					WebElement enrollStatus = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] div[class='row align-items-center'] div[class='col-12'] h6"));
					if(enrollStatus.getText().equals("Enrollment is Closed"))
					{
						enrollProcessFromPgm = "close";
					}
				}
				
				if(enrollProcessStatus.equalsIgnoreCase(enrollProcessFromPgm))
				{
					statusOfPgmCard.add("pass");
				}
				else
				{
					failCase.add("issue in enrollstatus, its not match in "+programName+"");
					testingStatus = false;
				}
				driver.close();
				Thread.sleep(1000);
				driver.switchTo().window(parentwindow);
				System.out.println("tech program card verification done for "+getPgmName);
				Thread.sleep(1000);
				if(testingStatus == false)
				{
					overallFail.addAll(failCase);
				}
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			overallFail.addAll(failedUrls);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return overallFail;
	}
	public ArrayList<String> verifyPLUCourse(ArrayList<String> course)
	{
		ArrayList<String> failedUrls = new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try
		{

			js.executeScript("window.scrollBy(0, 800)", "");
			List<WebElement> techProgram = driver.findElements(By.cssSelector("section[class*='HumanSkills_mainContainer'] div[class*='HumanSkills_mainContent'] a"));
			for(int i = 0; i < techProgram.size(); i++)
			{
				String url = techProgram.get(i).getAttribute("href");
				System.out.println("tech program starts execution : "+url);
				String urlLinkStatus = this.checkURLStatus(url);
				if(urlLinkStatus.equalsIgnoreCase("fail"))
				{
					failedUrls.add(url);
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("window.scrollBy(0,1500)", "");
		return failedUrls;
	}
	public ArrayList<String> verifyFAQ(ArrayList<String>  faq)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			boolean checkQuestionStatus = false;
			String answerStatus = "answerPass";
			List<WebElement> question = driver.findElements(By.cssSelector("div[class='accordion'] div[class*='accordion-item'] button[class*='accordion-button']"));
			if(question.size()>0)
			{
				for(int i = 0; i < question.size(); i++)
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
					if(faq.get(1).trim().equalsIgnoreCase(question.get(i).getText().trim()))
					{
						checkQuestionStatus = true;
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].click()", question.get(i));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						System.out.println("question is correct");
						List<WebElement> answer = driver.findElements(By.cssSelector("div[class='accordion'] div[class*='accordion-item'] div[class*='accordion-collapse'] div[class*='Accordion_accordionBody']"));
						for(int j = 0; j < answer.size(); j++)
						{
							if(answer.get(j).getText().equalsIgnoreCase(faq.get(2)))
							{
								System.out.println("answer is correct");
								answerStatus = "answerPass";
								break;
							}
						}
					  }
				}
				if(!answerStatus.equalsIgnoreCase("answerPass"))
				{
					status.add("answerFail");
				}
				if(!checkQuestionStatus == true)
				{
					status.add("questionFail");
					System.out.println("question is not correct");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public String checkURLStatus(String getURL)
	{
		String status = "fail";
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
				status = "fail";
			}
			else
			{
				System.out.println("un broken link"+addHosturl);
				status = "pass";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public ArrayList<String> programContent()
	{
		ArrayList<String> cardContentStatus = new ArrayList<String>();
		ArrayList<String> cardContent = new ArrayList<String>();
		
		try
		{
			List<WebElement> pgmCards = driver.findElements(By.cssSelector("div[class*='TechPrograms_cardWrapper'] div[class*='TechPrograms_cardContent']"));
			for(int i = 0; i < pgmCards.size(); i++)
			{
				WebElement prgIcon = pgmCards.get(i).findElement(By.cssSelector(" img[alt='PLU Medal']"));
				WebElement prgPartner = pgmCards.get(i).findElement(By.cssSelector(" div[class*='TechPrograms_plu']"));
				WebElement prgType = pgmCards.get(i).findElement(By.cssSelector(" p[class*='TechPrograms_mentoredBe']"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cardContentStatus;
	}
	
	public ArrayList<String> verfyCss(ArrayList<String> data)
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			WebElement BackGroundColor_certificateLeftContent = driver.findElement(By.cssSelector("div[class='CertificatesEarn_leftContent__t2X6r']"));
			String bgColor = BackGroundColor_certificateLeftContent.getCssValue("background-color");
			if(bgColor.equalsIgnoreCase(data.get(1)))
			{
				System.out.println("PLU bg color is yellow");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();;
		}
		return status;
	}
}
