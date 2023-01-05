package com.seo.pompages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.utility.TestUtil;

public class FrontEndAboutPageLocator
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
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_108 version\\chromedriver_win32\\chromedriver.exe");
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
	
	public String fontName(WebElement locator, String testData)
	{
		String fontNameStatus = "";
		String fontName = "";
		try
		{
			if(testData.contains("fontName"))
			{
				fontName = locator.getCssValue("font-family");
			}
			else
			{
				System.out.println("fontName is not available : " +fontName);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
				if(fontName.contains(list.get(i).toString()))
				{
					//System.out.println("fontName is same ");
					fontNameStatus = "true";
					break;
				}
				else
				{
				//	System.out.println("fontName is not same");
					//System.out.println("UI fontName from browser is : "+fontName);
					fontNameStatus = "false";
				}
			}
			if(!fontNameStatus.equals("true"))
			{
				System.out.println("UI fontName from browser is : "+fontName);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fontNameStatus;
	}
	public String paddingLeft(WebElement locator, String testData)
	{
		String paddingLeftStatus="";
		String paddingLeft="";
		try
		{
			if(testData.contains("paddingLeft"))
			{
				paddingLeft = locator.getCssValue("padding-left");
			}
			else
			{
				System.out.println("paddingLeft is not available : " +paddingLeft);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
				if(paddingLeft.contains(list.get(i).toString()))
				{
					//System.out.println("paddingLeft is same ");
					paddingLeftStatus = "true";
					break;
				}
				else
				{
				//	System.out.println("paddingLeft is not same");
					//System.out.println("UI paddingLeft from browser is : "+paddingLeft);
					paddingLeftStatus = "false";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paddingLeftStatus;
	}
	
	public String paddingRight(WebElement locator, String testData)
	{
		String paddingRightStatus="";
		String paddingRight="";
		try
		{
			if(testData.contains("paddingRight"))
			{
				paddingRight = locator.getCssValue("padding-Right");
			}
			else
			{
				System.out.println("paddingRight is not available : " +paddingRight);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(paddingRight.contains(splitWord[i]))
					{
						System.out.println("paddingRight is same ");
						paddingRightStatus = "true";
						break;
					}
					else
					{
						System.out.println("paddingRight is not same");
						System.out.println("UI paddingRight from browser is : "+paddingRight);
						paddingRightStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paddingRightStatus;
	}
	
	public String paddingTop(WebElement locator, String testData)
	{
		String paddingTopStatus="";
		String paddingTop="";
		try
		{
			if(testData.contains("paddingTop"))
			{
				paddingTop = locator.getCssValue("padding-Top");
			}
			else
			{
				System.out.println("paddingTop is not available : " +paddingTop);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(paddingTop.contains(splitWord[i]))
					{
						System.out.println("paddingTop is same ");
						paddingTopStatus = "true";
						break;
					}
					else
					{
						System.out.println("paddingTop is not same");
						System.out.println("UI paddingTop from browser is : "+paddingTop);
						paddingTopStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paddingTopStatus;
	}
	
	public String paddingBottom(WebElement locator, String testData)
	{
		String paddingBottomStatus="";
		String paddingBottom="";
		try
		{
			if(testData.contains("paddingBottom"))
			{
				paddingBottom = locator.getCssValue("padding-Bottom");
			}
			else
			{
				System.out.println("paddingBottom is not available : " +paddingBottom);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(paddingBottom.contains(splitWord[i]))
					{
						System.out.println("paddingBottom is same ");
						paddingBottomStatus = "true";
						break;
					}
					else
					{
						System.out.println("paddingBottom is not same");
						System.out.println("UI paddingBottom from browser is : "+paddingBottom);
						paddingBottomStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paddingBottomStatus;
	}
	public String marginLeft(WebElement locator, String testData)
	{
		String marginLeftStatus="";
		String marginLeft="";
		try
		{
			if(testData.contains("marginLeft"))
			{
				marginLeft = locator.getCssValue("margin-left");
			}
			else
			{
				System.out.println("marginLeft is not available : " +marginLeft);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(marginLeft.contains(splitWord[i]))
					{
						System.out.println("marginLeft is same ");
						marginLeftStatus = "true";
						break;
					}
					else
					{
						System.out.println("marginLeft is not same");
						System.out.println("UI marginLeft from browser is : "+marginLeft);
						marginLeftStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return marginLeftStatus;
	}
	
	public String marginTop(WebElement locator, String testData)
	{
		String marginTopStatus="";
		String marginTop="";
		try
		{
			if(testData.contains("marginTop"))
			{
				marginTop = locator.getCssValue("margin-top");
			}
			else
			{
				System.out.println("marginTop is not available : " +marginTop);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(marginTop.contains(splitWord[i]))
					{
						System.out.println("marginTop is same ");
						marginTopStatus = "true";
						break;
					}
					else
					{
						System.out.println("marginTop is not same");
						System.out.println("UI margin Top from browser is : "+marginTop);
						marginTopStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return marginTopStatus;
	}
	
	public String marginRight(WebElement locator, String testData)
	{
		String marginRightStatus="";
		String marginRight="";
		try
		{
			if(testData.contains("marginRight"))
			{
				marginRight = locator.getCssValue("margin-right");
			}
			else
			{
				System.out.println("marginRight is not available : " +marginRight);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(marginRight.contains(splitWord[i]))
					{
						System.out.println("margin Right is same ");
						marginRightStatus = "true";
						break;
					}
					else
					{
						System.out.println("margin Right is not same");
						System.out.println("UI margin Right from browser is : "+marginRight);
						marginRightStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return marginRightStatus;
	}
	
	public String marginBottom(WebElement locator, String testData)
	{
		String marginBottomStatus="";
		String marginBottom="";
		try
		{
			if(testData.contains("marginBottom"))
			{
				marginBottom = locator.getCssValue("margin-bottom");
			}
			else
			{
				System.out.println("marginBottom is not available : " +marginBottom);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(marginBottom.contains(splitWord[i]))
					{
						System.out.println("marginBottom is same ");
						marginBottomStatus = "true";
						break;
					}
					else
					{
						System.out.println("marginBottom is not same");
						System.out.println("UI margin Bottom from browser is : "+marginBottom);
						marginBottomStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return marginBottomStatus;
	}

	public String fontSize(WebElement locator, String testData)
	{
		String fontSizeStatus="";
		String fontSize="";
		try
		{
			if(testData.contains("fontSize"))
			{
				fontSize = locator.getCssValue("font-size");
			}
			else
			{
				System.out.println("fontSize is not available : "+fontSize);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(fontSize.contains(splitWord[i]))
					{
						System.out.println("fontSize is same ");
						fontSizeStatus = "true";
						break;
					}
					else
					{
						System.out.println("fontSize is not same");
						System.out.println("UI fontSize from browser is : "+fontSize);
						fontSizeStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fontSizeStatus;
	
	}
	
	public String fontColor(WebElement locator, String testData)
	{
		String fontColorStatus="";
		String fontColor="";
		String hexColor = "";
		try
		{
			if(testData.contains("fontColor"))
			{
				fontColor = locator.getCssValue("color");
				hexColor = Color.fromString(fontColor).asHex()+";";
			}
			else
			{
				System.out.println("fontColor is not available : "+hexColor);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
				if(hexColor.contains(list.get(i).toString()))
				{
					//System.out.println("fontColor is same ");
					fontColorStatus = "true";
					break;
				}
				else
				{
				//	System.out.println("fontColor is not same");
					//System.out.println("UI fontColor from browser is : "+hexColor);
					fontColorStatus = "false";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fontColorStatus;
	}
	
	public String fontAlign(WebElement locator, String testData)
	{
		String fontAlignStatus="";
		String fontAlign="";
		try
		{
			if(testData.contains("fontAlign"))
			{
				fontAlign = locator.getCssValue("text-align");
			}
			else
			{
				System.out.println("fontAlign is not available : "+fontAlign);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(fontAlign.contains(splitWord[i]))
					{
						System.out.println("fontAlign is same ");
						fontAlignStatus = "true";
						break;
					}
					else
					{
						System.out.println("fontAlign is not same");
						System.out.println("UI fontAlign from browser is : "+fontAlign);
						fontAlignStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fontAlignStatus;
	}
	
	public String fontStyle(WebElement locator, String testData)
	{
		String fontStyleStatus="";
		String fontStyle="";
		try
		{
			if(testData.contains("fontStyle"))
			{
				fontStyle = locator.getCssValue("font-style");
			}
			else
			{
				System.out.println("fontStyle is not available : "+fontStyle);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(fontStyle.contains(splitWord[i]))
					{
						System.out.println("fontStyle is same ");
						fontStyleStatus = "true";
						break;
					}
					else
					{
						System.out.println("fontStyle is not same");
						System.out.println("UI fontStyle from browser is : "+fontStyle);
						fontStyleStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fontStyleStatus;
	}
	
	public String textBgColor(WebElement locator, String testData)
	{
		String textBgColorStatus="";
		String textBgColor="";
		String hexTextBgColor = "";
		try
		{
			if(testData.contains("textBgColor"))
			{
				textBgColor = locator.getCssValue("background-color");
				hexTextBgColor = Color.fromString(textBgColor).asHex()+";";
				
			}
			else
			{
				System.out.println("text background color is not available : "+hexTextBgColor);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
					if(hexTextBgColor.contains(list.get(i).toString()))
					{
						//System.out.println("text background color is same ");
						textBgColorStatus = "true";
						break;
					}
					else
					{
					  //System.out.println("text background color is not same");
						//System.out.println("UI text background color from browser is : "+hexTextBgColor);
						textBgColorStatus = "false";
					}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return textBgColorStatus;
	}
	
	public String imageColor(WebElement locator, String testData)
	{
		String imageColorStatus="";
		String imageColor="";
		String hexImageColor = "";
		try
		{
			if(testData.contains("textBgColor"))
			{
				imageColor = locator.getCssValue("color");
				hexImageColor = Color.fromString(imageColor).asHex()+";";
			}
			else
			{
				System.out.println("imageColor is not available : "+hexImageColor);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
				if(hexImageColor.contains(list.get(i).toString()))
				{
					//System.out.println("imageColor is same ");
					imageColorStatus = "true";
					break;
				}
				else
				{
				//	System.out.println("imageColor is not same");
					//System.out.println("UI imageColor from browser is : "+hexImageColor);
					imageColorStatus = "false";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return imageColorStatus;
	}
	
	public String imageBackgroundColor(WebElement locator, String testData)
	{
		String imageBackgroundColorStatus="";
		String imageBackgroundColor="";
		String hexImageBackgroundColor = "";
		try
		{
			if(testData.contains("textBgColor"))
			{
				imageBackgroundColor = locator.getCssValue("border-top-color");
				hexImageBackgroundColor = Color.fromString(imageBackgroundColor).asHex()+";";
			}
			else
			{
				System.out.println("imageBackgroundColor is not available : "+hexImageBackgroundColor);
			}
			String splitWord[] = null ;
			String temp[] = null;
			List<String> list = new ArrayList<String>();
			
			if(!testData.contains("-split-"))
			{
				splitWord = testData.split("=");
				list=(Arrays.asList(splitWord));
			}
			else
			{
				splitWord = testData.split("-split-");
				for(int j = 0; j < splitWord.length; j++)
				{
					temp= splitWord[j].split("=");
					for(int k = 0; k < temp.length; k++)
					{
						if(k == 1)
						{
							list.add(temp[k]);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++)
			{
				if(hexImageBackgroundColor.contains(list.get(i).toString()))
				{
					//System.out.println("ImageBackgroundColor is same ");
					imageBackgroundColorStatus = "true";
					break;
				}
				else
				{
				//	System.out.println("ImageBackgroundColor is not same");
					//System.out.println("UI ImageBackgroundColor from browser is : "+hexImageBackgroundColor);
					imageBackgroundColorStatus = "false";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return imageBackgroundColorStatus;
	}
	public String checkObjectContain(WebElement locator, String testData)
	{
		String objectContainStatus="";
		String objectContain="";
		try
		{
			if(testData.contains("object-fit"))
			{
				objectContain = locator.getCssValue("object-fit");
			}
			else
			{
				System.out.println("Object is not available : "+objectContain);
			}
			String splitWord[] = testData.split("=");
			for(int i = 0; i < splitWord.length; i++)
			{
					if(objectContain.contains(splitWord[i]))
					{
						System.out.println("Object is same ");
						objectContainStatus = "true";
						break;
					}
					else
					{
						System.out.println("Object is not same");
						System.out.println("UI Object from browser is : "+objectContain);
						objectContainStatus = "false";
					}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return objectContainStatus;
	}
	public ArrayList<String> getPartnerOfCourse(ArrayList<String> partnerOfCourseFromExcel)
	{
			ArrayList<String> checkPartnerOfCourseStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = partnerOfCourseFromExcel;
			WebElement titleLocator = driver.findElement(By.cssSelector("div[class='col d-flex align-items-center justify-content-end'] img:nth-child(2)"));
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkPartnerOfCourseStatus.add("notProcessed");
					}
				}
				else if(getData.size() == 2)
				{
					if(j == 1)
					{
						System.out.println("Partner Of Course verification");
						checkPartnerOfCourseStatus.add(textBgColor(titleLocator, getDataFromExcel));
					}
				}
				else
				{
					System.out.println("Partner Of Course is not available in UI");
					checkPartnerOfCourseStatus.add("false");
				}
		}
		return checkPartnerOfCourseStatus;
	}
	
	public ArrayList<String> getCourseIcon(ArrayList<String> courseIconFromExcel)
	{
			ArrayList<String> checkCourseIconStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseIconFromExcel;
			
			WebElement CourseIconLocator = driver.findElement(By.cssSelector("h4[class='CourseDescription_courseLabel__C3MVe false']"));
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkCourseIconStatus.add("notProcessed");
						break;
					}
					else
					{
						System.out.println("Course Icon verification");
						checkCourseIconStatus.add(marginLeft(CourseIconLocator, getDataFromExcel));
						break;
					}
				}
				else
				{
					System.out.println("Course Icon is not available in UI");
					checkCourseIconStatus.add("false");
				}
	  	    }
		return checkCourseIconStatus;
	}
	public ArrayList<String> checkCourseTitle(ArrayList<String> courseTitleFromExcel)
	{
			ArrayList<String> checkCourseTitleStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseTitleFromExcel;
			WebElement titleLocator = null;
			boolean checkProcess = false;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
				if(locator.isDisplayed())
				{
					titleLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(Exception e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("course Title verification");
						checkCourseTitleStatus.add(fontName(titleLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseTitleStatus.add(fontSize(titleLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseTitleStatus.add(fontColor(titleLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseTitleStatus.add(fontAlign(titleLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseTitleStatus.add(fontStyle(titleLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkCourseTitleStatus.add(marginTop(titleLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("course title is not available in UI");
				checkCourseTitleStatus.add("false");
			}
		}
		return checkCourseTitleStatus;
	}
	
	public ArrayList<String> checkCourseDescription(ArrayList<String> courseDescriptionFromExcel)
	{
			ArrayList<String> checkCourseDescriptionStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseDescriptionFromExcel;
			boolean checkProcess = false;
			WebElement descriptionLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class*='col-12 CourseDescription_courseText'] p"));
				if(locator.isDisplayed())
				{
					descriptionLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("course Description verification");
						checkCourseDescriptionStatus.add(fontName(descriptionLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseDescriptionStatus.add(fontSize(descriptionLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseDescriptionStatus.add(fontColor(descriptionLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseDescriptionStatus.add(fontAlign(descriptionLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseDescriptionStatus.add(fontStyle(descriptionLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkCourseDescriptionStatus.add(marginTop(descriptionLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("course description is not available in UI");
				checkCourseDescriptionStatus.add("false");
			}
		}
		return checkCourseDescriptionStatus;
	}
	
	public ArrayList<String> checkCourseType(ArrayList<String> courseTypeFromExcel)
	{
			ArrayList<String> checkCourseTypeStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseTypeFromExcel;
			boolean checkProcess = false;
			WebElement courseTypeLocator = null;
			try
			{
				WebElement Locator = driver.findElement(By.cssSelector("h2[class*='text-uppercase']"));
				if(Locator.isDisplayed())
				{
					courseTypeLocator = Locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("course Type verification");
						checkCourseTypeStatus.add(fontName(courseTypeLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseTypeStatus.add(fontSize(courseTypeLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseTypeStatus.add(fontColor(courseTypeLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseTypeStatus.add(fontAlign(courseTypeLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseTypeStatus.add(fontStyle(courseTypeLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkCourseTypeStatus.add(textBgColor(courseTypeLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkCourseTypeStatus.add(paddingLeft(courseTypeLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkCourseTypeStatus.add(paddingRight(courseTypeLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkCourseTypeStatus.add(paddingTop(courseTypeLocator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkCourseTypeStatus.add(paddingBottom(courseTypeLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("course Type is not available in UI");
				checkCourseTypeStatus.add("false");
			}
		}
		return checkCourseTypeStatus;
	}
	public ArrayList<String> checkCourseLevel1(ArrayList<String> courseLevel1FromExcel)
	{
			ArrayList<String> checkcourseLevel1Status = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseLevel1FromExcel;
			boolean checkProcess = false;
			WebElement courseLevel1Locator = null;
			try
			{
				WebElement Locator = driver.findElement(By.xpath("(//h3[@class='text-uppercase CourseDescription_regularThemeH3__JESuT'])[1]"));
				if(Locator.isDisplayed())
				{
					courseLevel1Locator=Locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Course Level1 verification");
						checkcourseLevel1Status.add(fontName(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkcourseLevel1Status.add(fontSize(courseLevel1Locator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkcourseLevel1Status.add(fontColor(courseLevel1Locator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkcourseLevel1Status.add(fontAlign(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkcourseLevel1Status.add(fontStyle(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkcourseLevel1Status.add(textBgColor(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkcourseLevel1Status.add(paddingLeft(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkcourseLevel1Status.add(paddingRight(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkcourseLevel1Status.add(paddingTop(courseLevel1Locator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkcourseLevel1Status.add(paddingBottom(courseLevel1Locator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Course Level 1 is not available in UI");
				checkcourseLevel1Status.add("false");
			}
		}
		return checkcourseLevel1Status;
	}
	public ArrayList<String> checkCourseLevel2(ArrayList<String> courseLevel2FromExcel)
	{
			ArrayList<String> checkCourseLevel2Status = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseLevel2FromExcel;
			boolean checkProcess = false;
			WebElement courseLevel2Locator = null;
			try
			{
				WebElement Locator = driver.findElement(By.xpath("(//h3[@class='text-uppercase CourseDescription_regularThemeH3__JESuT'])[2]"));
				if(Locator.isDisplayed())
				{
					courseLevel2Locator = Locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println( "webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Course Level2 verification");
						checkCourseLevel2Status.add(fontName(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseLevel2Status.add(fontSize(courseLevel2Locator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseLevel2Status.add(fontColor(courseLevel2Locator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseLevel2Status.add(fontAlign(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseLevel2Status.add(fontStyle(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkCourseLevel2Status.add(textBgColor(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkCourseLevel2Status.add(paddingLeft(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkCourseLevel2Status.add(paddingRight(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkCourseLevel2Status.add(paddingTop(courseLevel2Locator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkCourseLevel2Status.add(paddingBottom(courseLevel2Locator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("CourseLevel2 is not available in UI");
				checkCourseLevel2Status.add("false");
			}
		}
		return checkCourseLevel2Status;
	}
	public ArrayList<String> checkStartsOnIcon(ArrayList<String> startsOnIconFromExcel)
	{
			ArrayList<String> checkStartsOnIconStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = startsOnIconFromExcel;
			boolean checkProcess = false;
			WebElement startsOnIconLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2'] img[alt='flag-icon']"));
				if(locator.isDisplayed())
				{
					startsOnIconLocator = locator;
					checkProcess = true;
				}
				else
				{
					System.out.println("starts on icon locator not found");
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("starts on Icon not available in UI"); 
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("StartsOn Icon verification");
						checkStartsOnIconStatus.add(imageColor(startsOnIconLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkStartsOnIconStatus.add(imageBackgroundColor(startsOnIconLocator, getDataFromExcel));	
					}
				}
			else
			{
				System.out.println("startsOn Icon is not available in UI");
				checkStartsOnIconStatus.add("notProcessed");
				break;
			}
		}
		return checkStartsOnIconStatus;
	}
	public ArrayList<String> checkStartsOnHeading(ArrayList<String> startsOnHeadingFromExcel)
	{
			ArrayList<String> checkStartsOnHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = startsOnHeadingFromExcel;
			boolean checkProcess = false;
			WebElement startsOnHeadingLocator = null;
			try
			{
				List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
				if(courseTimeAndFee.size()>0)
				{
					for(int i = 0; i < courseTimeAndFee.size(); i++)
					{
						WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
						if(getLocator.getText().equals("StartsOn"))
						{
							startsOnHeadingLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							System.out.println("locator not found");
							checkProcess = false;
						}
					}			
				}
				else
				{
					System.out.println("locator not found");
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("starts on Heading not available in UI"); 
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("StartsOn Heading verification");
						checkStartsOnHeadingStatus.add(fontName(startsOnHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkStartsOnHeadingStatus.add(fontSize(startsOnHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkStartsOnHeadingStatus.add(fontColor(startsOnHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkStartsOnHeadingStatus.add(fontAlign(startsOnHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkStartsOnHeadingStatus.add(fontStyle(startsOnHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkStartsOnHeadingStatus.add(imageColor(startsOnHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("startsOn Heading is not available in UI");
				checkStartsOnHeadingStatus.add("notProcessed");
				break;
			}
		}
		return checkStartsOnHeadingStatus;
	}
	
	public ArrayList<String> checkStartsOnContent(ArrayList<String> startsOnContentFromExcel)
	{
			ArrayList<String> checkstartsOnContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = startsOnContentFromExcel;
			boolean checkProcess = false;
			WebElement startsOnContentLocator = null;
			try
			{
				List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
				for(int i = 0; i < courseTimeAndFee.size(); i++)
				{
					WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
					if(null != getLocator)
					{
						if(getLocator.getText().equals("StartsOn"))
						{
							startsOnContentLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
							checkProcess = true;
							break;
						}
					}
					else
					{
						System.out.println("locator not found");
						checkProcess = true;
					}
				}			
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webelement not exists in UI");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess==true)
				{
					if(j == 1)
					{
						System.out.println("startsOn Contentverification");
						checkstartsOnContentStatus.add(fontName(startsOnContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkstartsOnContentStatus.add(fontSize(startsOnContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkstartsOnContentStatus.add(fontColor(startsOnContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkstartsOnContentStatus.add(fontAlign(startsOnContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkstartsOnContentStatus.add(fontStyle(startsOnContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("startsOn content is not available in UI");
				checkstartsOnContentStatus.add("notProcessed");
			}
		}
		return checkstartsOnContentStatus;
	}
	public ArrayList<String> checkDurationIcon(ArrayList<String> durationIconFromExcel)
	{
			ArrayList<String> checkDurationIconStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = durationIconFromExcel;
			boolean checkProcess = false;
			WebElement durationIconLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2'] img[alt='time-icon']"));
				if(locator.isDisplayed())
				{
					durationIconLocator = locator;
					checkProcess = true;
				}
				else
				{
					System.out.println("duration locator not found");
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("duration Icon not available in UI"); 
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("duration Icon verification");
						checkDurationIconStatus.add(imageColor(durationIconLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkDurationIconStatus.add(imageBackgroundColor(durationIconLocator, getDataFromExcel));	
					}
				}
			else
			{
				System.out.println("duration Icon is not available in UI");
				checkDurationIconStatus.add("notProcessed");
				break;
			}
		}
		return checkDurationIconStatus;
	}
	public ArrayList<String> checkDurationHeading(ArrayList<String> durationHeadingFromExcel)
	{
			boolean checkProcess = false;
			ArrayList<String> checkDurationHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = durationHeadingFromExcel;
			
			WebElement durationHeadingLocator = null;
			try
			{
				List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
				for(int i = 0; i < courseTimeAndFee.size(); i++)
				{
					WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
					if(getLocator.getText().equals("Duration"))
					{
						durationHeadingLocator = getLocator;
						checkProcess = true;
						break;
					}
					else
					{
						System.out.println("duration webElement not found");
						checkProcess = false;
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("duration webElement is not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Duration Heading verification");
						checkDurationHeadingStatus.add(fontName(durationHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkDurationHeadingStatus.add(fontSize(durationHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkDurationHeadingStatus.add(fontColor(durationHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkDurationHeadingStatus.add(fontAlign(durationHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkDurationHeadingStatus.add(fontStyle(durationHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkDurationHeadingStatus.add(imageColor(durationHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("duration Heading is not available in UI");
				checkDurationHeadingStatus.add("notProcessed");
			}
		}
		return checkDurationHeadingStatus;
	}
	
	public ArrayList<String> checkDurationContent(ArrayList<String> durationContentFromExcel)
	{
			ArrayList<String> checkDurationContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			boolean checkProcess = false;
			getData = durationContentFromExcel;
			WebElement durationContentLocator = null;
			try
			{
				List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
				for(int i = 0; i < courseTimeAndFee.size(); i++)
				{
					WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
					if(getLocator.getText().equals("Duration"))
					{
						WebElement durationContent = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
						durationContentLocator = durationContent;
						checkProcess = true;
						break;
					}
					else
					{
						System.out.println("webElement is not found");
						checkProcess = false;
					}
				}		
			}
			catch(NullPointerException e)
			{
				System.out.println("duration content not able to find");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Duration Content verification");
						checkDurationContentStatus.add(fontName(durationContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkDurationContentStatus.add(fontSize(durationContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkDurationContentStatus.add(fontColor(durationContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkDurationContentStatus.add(fontAlign(durationContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkDurationContentStatus.add(fontStyle(durationContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Duration Content is not available in UI");
				checkProcess = false;
				checkDurationContentStatus.add("notProcessed");
			}
		}
		return checkDurationContentStatus;
	}
	public ArrayList<String> checkFeeIcon(ArrayList<String> feeIconFromExcel)
	{
			ArrayList<String> checkFeeIconStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = feeIconFromExcel;
			boolean checkProcess = false;
			WebElement feeIconLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2'] img[alt='fee-icon']"));
				if(locator.isDisplayed())
				{
					feeIconLocator = locator;
					checkProcess = true;
				}
				else
				{
					System.out.println("fee icon locator not found");
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("fee Icon not available in UI"); 
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("fee Icon verification");
						checkFeeIconStatus.add(imageColor(feeIconLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFeeIconStatus.add(imageBackgroundColor(feeIconLocator, getDataFromExcel));	
					}
				}
			else
			{
				System.out.println("fee Icon is not available in UI");
				checkFeeIconStatus.add("notProcessed");
				break;
			}
		}
		return checkFeeIconStatus;
	}
	public ArrayList<String> checkFeeHeading(ArrayList<String> feeHeadingFromExcel)
	{
			ArrayList<String> checkFeeHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = feeHeadingFromExcel;
			WebElement FeeHeadingLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
				for(int i = 0; i < courseTimeAndFee.size(); i++)
				{
					WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
					if(getLocator.getText().equals("Fee"))
					{
						FeeHeadingLocator = getLocator;
						checkProcess = true;
						break;
					}
					else
					{
						System.out.println("webElement not found");
						checkProcess = false;
					}
				}
			}
			catch(NullPointerException e)
			{
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Fee Heading verification");
						checkFeeHeadingStatus.add(fontName(FeeHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFeeHeadingStatus.add(fontSize(FeeHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkFeeHeadingStatus.add(fontColor(FeeHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkFeeHeadingStatus.add(fontAlign(FeeHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkFeeHeadingStatus.add(fontStyle(FeeHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkFeeHeadingStatus.add(imageColor(FeeHeadingLocator, getDataFromExcel));
					}
				}
			else if(checkProcess == false)
			{
				System.out.println("Fee Heading is not available in UI");
				checkProcess = false;
				checkFeeHeadingStatus.add("notProcessed");
			}
		}
		return checkFeeHeadingStatus;
	}
	
	public ArrayList<String> checkFeeContent(ArrayList<String> feeContentFromExcel)
	{
			ArrayList<String> checkFeeContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = feeContentFromExcel;
			boolean checkProcess = false;
			WebElement feeContentLocator = null;
			List<WebElement> courseTimeAndFee = driver.findElements(By.cssSelector("div[class='col-12 CourseDescription_breakContent__EUpfp '] div[class='d-flex gap-2']"));
			for(int i = 0; i < courseTimeAndFee.size(); i++)
			{
				WebElement getLocator = courseTimeAndFee.get(i).findElement(By.cssSelector(" h2"));
				if(getLocator.getText().equals("Fee"))
				{
					WebElement feeContent = courseTimeAndFee.get(i).findElement(By.cssSelector(" p"));
					feeContentLocator = feeContent;
					checkProcess = true;
					break;
				}
				else
				{
					System.out.println("webElement not found");
					checkProcess = false;
				}
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Fee Content verification");
						checkFeeContentStatus.add(fontName(feeContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFeeContentStatus.add(fontSize(feeContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkFeeContentStatus.add(fontColor(feeContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkFeeContentStatus.add(fontAlign(feeContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkFeeContentStatus.add(fontStyle(feeContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Fee Content is not available in UI");
				checkProcess = false;
				checkFeeContentStatus.add("notProcessed");
			}
		}
		return checkFeeContentStatus;
	}
	
	public ArrayList<String> checkEnrollStartNow(ArrayList<String> enrollFromExcel)
	{
			ArrayList<String> checkEnrollStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = enrollFromExcel;
			WebElement enrollStartNowLocator = null;
			boolean checkProcess = false;
			WebElement EnrollStartNow = driver.findElement(By.xpath("//div[@class='CourseDescription_buttonsContent__qPhJg ']//button[@class='CourseDescription_enrollNowBtn__xyx0I']"));
 			if(EnrollStartNow.isDisplayed())
 			{
 				if(EnrollStartNow.getText().equalsIgnoreCase("Start Now") || EnrollStartNow.getText().equalsIgnoreCase("Enroll Now")||EnrollStartNow.getText().equalsIgnoreCase("Enrolled"))
 				{
 					enrollStartNowLocator = EnrollStartNow;
 					checkProcess = true;
 				}
 			}
 			else
 			{
 				System.out.println("webElement not found");
 				checkProcess = false;
 			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Enroll verification");
						checkEnrollStatus.add(fontName(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkEnrollStatus.add(fontSize(enrollStartNowLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkEnrollStatus.add(fontColor(enrollStartNowLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkEnrollStatus.add(fontAlign(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkEnrollStatus.add(fontStyle(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkEnrollStatus.add(textBgColor(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkEnrollStatus.add(paddingLeft(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkEnrollStatus.add(paddingRight(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkEnrollStatus.add(paddingTop(enrollStartNowLocator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkEnrollStatus.add(paddingBottom(enrollStartNowLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Enroll is not available in UI");
				checkEnrollStatus.add("false");
			}
		}
		return checkEnrollStatus;
	}
	
	public ArrayList<String> checkConsultation(ArrayList<String> consultationFromExcel)
	{
			ArrayList<String> checkConsultationStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = consultationFromExcel;
			WebElement consultationLocator = null;
			boolean checkProcess = false;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//div[@class='CourseDescription_buttonsContent__qPhJg ']//button[@class='CourseDescription_getFreeConsultationBtn__KkZ46']"));
				if(locator.isDisplayed())
				{
					consultationLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Get free Consultation verification");
						checkConsultationStatus.add(fontName(consultationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkConsultationStatus.add(fontSize(consultationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkConsultationStatus.add(fontColor(consultationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkConsultationStatus.add(fontAlign(consultationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkConsultationStatus.add(fontStyle(consultationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkConsultationStatus.add(textBgColor(consultationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkConsultationStatus.add(paddingLeft(consultationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkConsultationStatus.add(paddingRight(consultationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkConsultationStatus.add(paddingTop(consultationLocator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkConsultationStatus.add(paddingBottom(consultationLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Get a Free Consultation is not available in UI");
				checkConsultationStatus.add("notProcessed");
			}
		}
		return checkConsultationStatus;
	}
	public ArrayList<String> checkOverviewNavigation(ArrayList<String> overviewNavigationFromExcel)
	{
			ArrayList<String> checkOverviewNavigationStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = overviewNavigationFromExcel;
			boolean checkProcess = false;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 100)");
			WebElement OverviewNavigationLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Overview')]"));
				if(locator.isDisplayed())
				{
					OverviewNavigationLocator = locator;
					checkProcess = true;
				}
				else
				{
					System.out.println("webElement not found");
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("overview Navigation verification");
						checkOverviewNavigationStatus.add(fontName(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkOverviewNavigationStatus.add(fontSize(OverviewNavigationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkOverviewNavigationStatus.add(fontColor(OverviewNavigationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkOverviewNavigationStatus.add(fontAlign(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkOverviewNavigationStatus.add(fontStyle(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkOverviewNavigationStatus.add(paddingLeft(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkOverviewNavigationStatus.add(paddingRight(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkOverviewNavigationStatus.add(paddingTop(OverviewNavigationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkOverviewNavigationStatus.add(paddingBottom(OverviewNavigationLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("overview Navigation is not available in UI");
				checkOverviewNavigationStatus.add("false");
			}
		}
		return checkOverviewNavigationStatus;
	}
	public ArrayList<String> checkOutlineNavigation(ArrayList<String> courseOverviewNavigationFromExcel)
	{
			ArrayList<String> checkOutlineNavigationStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseOverviewNavigationFromExcel;
			boolean checkProcess = false;
			WebElement OutlineNavigationLocator = null;
			try
			{
				WebElement  locator = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Details')]"));
				OutlineNavigationLocator = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Outline Navigation verification");
						checkOutlineNavigationStatus.add(fontName(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkOutlineNavigationStatus.add(fontSize(OutlineNavigationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkOutlineNavigationStatus.add(fontColor(OutlineNavigationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkOutlineNavigationStatus.add(fontAlign(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkOutlineNavigationStatus.add(fontStyle(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkOutlineNavigationStatus.add(paddingLeft(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkOutlineNavigationStatus.add(paddingRight(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkOutlineNavigationStatus.add(paddingTop(OutlineNavigationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkOutlineNavigationStatus.add(paddingBottom(OutlineNavigationLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Outline Navigation is not available in UI");
				checkOutlineNavigationStatus.add("false");
			}
		}
		return checkOutlineNavigationStatus;
	}
	
	public ArrayList<String> checkSkillupOnlineNavigation(ArrayList<String> skillupOnlineNavigationFromExcel)
	{
			ArrayList<String> checkSkillupOnlineNavigationStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = skillupOnlineNavigationFromExcel;
			boolean checkProcess = false;
			WebElement SkillUpNavigationLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'Why SkillUp Online?')]"));
				if(locator.isDisplayed())
				{
					SkillUpNavigationLocator = locator;
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Skillup Online Navigation verification");
						checkSkillupOnlineNavigationStatus.add(fontName(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkSkillupOnlineNavigationStatus.add(fontSize(SkillUpNavigationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkSkillupOnlineNavigationStatus.add(fontColor(SkillUpNavigationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkSkillupOnlineNavigationStatus.add(fontAlign(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkSkillupOnlineNavigationStatus.add(fontStyle(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkSkillupOnlineNavigationStatus.add(paddingLeft(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkSkillupOnlineNavigationStatus.add(paddingRight(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkSkillupOnlineNavigationStatus.add(paddingTop(SkillUpNavigationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkSkillupOnlineNavigationStatus.add(paddingBottom(SkillUpNavigationLocator, getDataFromExcel));
					}
				}
			else
				
			{
				System.out.println("Skillup Online Navigation is not available in UI");
				checkSkillupOnlineNavigationStatus.add("false");
			}
		}
		return checkSkillupOnlineNavigationStatus;
	}
	
	public ArrayList<String> checkFAQNavigation(ArrayList<String> FAQNavigationFromExcel)
	{
			ArrayList<String> checkFAQNavigationFromExcelStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = FAQNavigationFromExcel;
			boolean checkProcess = false;
			WebElement FAQNavigation = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//div[@class='d-flex CourseDescription_navigationBar__Zg6b3']/button[contains(text(),'FAQs')]"));
				if(locator.isDisplayed())
				{
					FAQNavigation =	locator;	
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("FAQ Navigation verification");
						checkFAQNavigationFromExcelStatus.add(fontName(FAQNavigation, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFAQNavigationFromExcelStatus.add(fontSize(FAQNavigation, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkFAQNavigationFromExcelStatus.add(fontColor(FAQNavigation, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkFAQNavigationFromExcelStatus.add(fontAlign(FAQNavigation, getDataFromExcel));
					}
					if(j == 5)
					{
						checkFAQNavigationFromExcelStatus.add(fontStyle(FAQNavigation, getDataFromExcel));
					}
					if(j == 6)
					{
						checkFAQNavigationFromExcelStatus.add(paddingLeft(FAQNavigation, getDataFromExcel));
					}
					if(j == 7)
					{
						checkFAQNavigationFromExcelStatus.add(paddingRight(FAQNavigation, getDataFromExcel));
					}
					if(j == 8)
					{
						checkFAQNavigationFromExcelStatus.add(paddingTop(FAQNavigation, getDataFromExcel));
					}
					if(j == 9)
					{
						checkFAQNavigationFromExcelStatus.add(paddingBottom(FAQNavigation, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("FAQ Navigation is not available in UI");
				checkFAQNavigationFromExcelStatus.add("false");
			}
		}
		return checkFAQNavigationFromExcelStatus;
	}
	public ArrayList<String> checkEnrollNavigation(ArrayList<String> enrollNavigationFromExcel) throws InterruptedException
	{
			ArrayList<String> checkEnrollNavigationFromExcelStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = enrollNavigationFromExcel;
			WebElement EnrollNavigationLocator = null;
			boolean checkProcess = false;
			try
			{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0, 700)");
				Thread.sleep(300);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				WebElement locator = driver.findElement(By.cssSelector("div[class='d-flex justify-content-end FixedContentBar_buttonsContent__Skdy6 align-items-center'] button:nth-child(1)"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				if(locator.isDisplayed())
				{
					EnrollNavigationLocator = locator;
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Enroll Navigation verification");
						checkEnrollNavigationFromExcelStatus.add(fontName(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkEnrollNavigationFromExcelStatus.add(fontSize(EnrollNavigationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkEnrollNavigationFromExcelStatus.add(fontColor(EnrollNavigationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkEnrollNavigationFromExcelStatus.add(fontAlign(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkEnrollNavigationFromExcelStatus.add(fontStyle(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkEnrollNavigationFromExcelStatus.add(textBgColor(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkEnrollNavigationFromExcelStatus.add(paddingLeft(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkEnrollNavigationFromExcelStatus.add(paddingRight(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkEnrollNavigationFromExcelStatus.add(paddingTop(EnrollNavigationLocator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkEnrollNavigationFromExcelStatus.add(paddingBottom(EnrollNavigationLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Enroll Navigation is not available in UI");
				checkEnrollNavigationFromExcelStatus.add("notProcessed");
			}
		}
		return checkEnrollNavigationFromExcelStatus;
	}	
	public ArrayList<String> checkConsultationNavigation(ArrayList<String> consultationNavigationFromExcel)
	{
			ArrayList<String> checkconsultationNavigationFromExcelStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = consultationNavigationFromExcel;
			boolean checkProcess = false;
			WebElement consultationNavigationLocator = null;
			try
			{
				Thread.sleep(400);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				WebElement locator = driver.findElement(By.cssSelector("div[class='d-flex justify-content-end FixedContentBar_buttonsContent__Skdy6 align-items-center'] button:nth-child(2)"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				if(locator.isDisplayed())
				{
					consultationNavigationLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(Exception e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("consultation NavigationLocator verification");
						checkconsultationNavigationFromExcelStatus.add(fontName(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkconsultationNavigationFromExcelStatus.add(fontSize(consultationNavigationLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkconsultationNavigationFromExcelStatus.add(fontColor(consultationNavigationLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkconsultationNavigationFromExcelStatus.add(fontAlign(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkconsultationNavigationFromExcelStatus.add(fontStyle(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkconsultationNavigationFromExcelStatus.add(textBgColor(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkconsultationNavigationFromExcelStatus.add(paddingLeft(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 8)
					{
						checkconsultationNavigationFromExcelStatus.add(paddingRight(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 9)
					{
						checkconsultationNavigationFromExcelStatus.add(paddingTop(consultationNavigationLocator, getDataFromExcel));
					}
					if(j == 10)
					{
						checkconsultationNavigationFromExcelStatus.add(paddingBottom(consultationNavigationLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("consultation NavigationLocator is not available in UI");
				checkconsultationNavigationFromExcelStatus.add("notProcessed");
			}
		}
		return checkconsultationNavigationFromExcelStatus;
	}
	public ArrayList<String> checkProgramOffering(ArrayList<String> programOfferingFromExcel)
	{
			ArrayList<String> checkProgramOfferingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = programOfferingFromExcel;
			boolean checkProcess = false;
			WebElement ProgramOfferingLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='CourseOffering_mainSection__7IEXE']//h2[@class='CourseOffering_titleText__gSdn7  '])[2]"));
				if(locator.isDisplayed())
				{
					ProgramOfferingLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Program Offering verification");
						checkProgramOfferingStatus.add(fontName(ProgramOfferingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkProgramOfferingStatus.add(fontSize(ProgramOfferingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkProgramOfferingStatus.add(fontColor(ProgramOfferingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkProgramOfferingStatus.add(fontAlign(ProgramOfferingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkProgramOfferingStatus.add(fontStyle(ProgramOfferingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Program Offering is not available in UI");
				checkProgramOfferingStatus.add("false");
			}
		}
		return checkProgramOfferingStatus;
	}
	
	public ArrayList<String> checkTypeOfCertificateHeading(ArrayList<String> typeOfCertificateHeadingFromExcel)
	{
			ArrayList<String> checkTypeOfCertificateHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = typeOfCertificateHeadingFromExcel;
			WebElement typeOfCertificateHeadingLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> typeOfCertificate = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < typeOfCertificate.size(); k++)
				{
					String getTypeOfCertificateText = typeOfCertificate.get(k).getText();
					if(getTypeOfCertificateText.equalsIgnoreCase("Type of certificate"))
					{
						WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
						if(getLocator.isDisplayed())
						{
							typeOfCertificateHeadingLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("typeOfCertificate eHeading verification");
						checkTypeOfCertificateHeadingStatus.add(fontName(typeOfCertificateHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkTypeOfCertificateHeadingStatus.add(fontSize(typeOfCertificateHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkTypeOfCertificateHeadingStatus.add(fontColor(typeOfCertificateHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkTypeOfCertificateHeadingStatus.add(fontAlign(typeOfCertificateHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkTypeOfCertificateHeadingStatus.add(fontStyle(typeOfCertificateHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkTypeOfCertificateHeadingStatus.add(imageColor(typeOfCertificateHeadingLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkTypeOfCertificateHeadingStatus.add(marginTop(typeOfCertificateHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("typeOfCertificate Heading is not available in UI");
				checkTypeOfCertificateHeadingStatus.add("false");
			}
		}
		return checkTypeOfCertificateHeadingStatus;
	}
	public ArrayList<String> checkTypeOfCertificateContent(ArrayList<String> typeOfCertificateContentFromExcel)
	{
			ArrayList<String> checkTypeOfCertificateContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = typeOfCertificateContentFromExcel;
			WebElement typeOfCertificateContentLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> typeOfCertificate = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < typeOfCertificate.size(); k++)
				{
					String getTypeOfCertificateText = typeOfCertificate.get(k).getText();
					if(getTypeOfCertificateText.equalsIgnoreCase("Type of certificate"))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
						if(getContentLocator.isDisplayed())
						{
							typeOfCertificateContentLocator = getContentLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("TypeOfCertificateContent  verification");
						checkTypeOfCertificateContentStatus.add(fontName(typeOfCertificateContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkTypeOfCertificateContentStatus.add(fontSize(typeOfCertificateContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkTypeOfCertificateContentStatus.add(fontColor(typeOfCertificateContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkTypeOfCertificateContentStatus.add(fontAlign(typeOfCertificateContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkTypeOfCertificateContentStatus.add(fontStyle(typeOfCertificateContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("function name is not available in UI");
				checkTypeOfCertificateContentStatus.add("false");
			}
		}
		return checkTypeOfCertificateContentStatus;
	}
	
	public ArrayList<String> checkAboutCourseHeading(ArrayList<String> aboutCourseHeadingFromExcel)
	{
			ArrayList<String> checkaboutCourseHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = aboutCourseHeadingFromExcel;
			WebElement aboutCourseHeadingLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> aboutCourse = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < aboutCourse.size(); k++)
				{
					String AboutCourseText = aboutCourse.get(k).getText();
					if(AboutCourseText.equalsIgnoreCase("About this course"))
					{
						WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
						if(getLocator.isDisplayed())
						{
							aboutCourseHeadingLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("AboutCourse Heading verification");
						checkaboutCourseHeadingStatus.add(fontName(aboutCourseHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkaboutCourseHeadingStatus.add(fontSize(aboutCourseHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkaboutCourseHeadingStatus.add(fontColor(aboutCourseHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkaboutCourseHeadingStatus.add(fontAlign(aboutCourseHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkaboutCourseHeadingStatus.add(fontStyle(aboutCourseHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkaboutCourseHeadingStatus.add(imageColor(aboutCourseHeadingLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkaboutCourseHeadingStatus.add(marginTop(aboutCourseHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("aboutCourse Heading is not available in UI");
				checkaboutCourseHeadingStatus.add("false");
			}
		}
		return checkaboutCourseHeadingStatus;
	}
	
	public ArrayList<String> checkAboutCourseContent(ArrayList<String> aboutCourseContentFromExcel)
	{
			ArrayList<String> checkAboutCourseContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = aboutCourseContentFromExcel;
			boolean checkProcess = false;
			WebElement aboutCourseContentLocator = null;
			try
			{
				List<WebElement> aboutCourse = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < aboutCourse.size(); k++)
				{
					String AboutCourseText = aboutCourse.get(k).getText();
					if(AboutCourseText.equalsIgnoreCase("Type of certificate"))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
						if(getContentLocator.isDisplayed())
						{
							aboutCourseContentLocator = getContentLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("AboutCourse Content verification");
						checkAboutCourseContentStatus.add(fontName(aboutCourseContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkAboutCourseContentStatus.add(fontSize(aboutCourseContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkAboutCourseContentStatus.add(fontColor(aboutCourseContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkAboutCourseContentStatus.add(fontAlign(aboutCourseContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkAboutCourseContentStatus.add(fontStyle(aboutCourseContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("AboutCourse Content is not available in UI");
				checkAboutCourseContentStatus.add("false");
			}
		}
		return checkAboutCourseContentStatus;
	}
	
	public ArrayList<String> checkIncludesHeading(ArrayList<String> includesHeadingFromExcel)
	{
			ArrayList<String> checkIncludesHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = includesHeadingFromExcel;
			WebElement includesHeadingLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> includes = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < includes.size(); k++)
				{
					String includeText = includes.get(k).getText();
					if(includeText.equalsIgnoreCase("Includes"))
					{
						WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
						if(getLocator.isDisplayed())
						{
							includesHeadingLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElemet not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("course Type verification");
						checkIncludesHeadingStatus.add(fontName(includesHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkIncludesHeadingStatus.add(fontSize(includesHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkIncludesHeadingStatus.add(fontColor(includesHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkIncludesHeadingStatus.add(fontAlign(includesHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkIncludesHeadingStatus.add(fontStyle(includesHeadingLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkIncludesHeadingStatus.add(imageColor(includesHeadingLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkIncludesHeadingStatus.add(marginTop(includesHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Includes Heading is not available in UI");
				checkIncludesHeadingStatus.add("false");
			}
		}
		return checkIncludesHeadingStatus;
	}
	public ArrayList<String> checkIncludesContent(ArrayList<String> includesContentntFromExcel)
	{
			ArrayList<String> checkIncludesContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = includesContentntFromExcel;
			WebElement includesContentLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> includes = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < includes.size(); k++)
				{
					String getTypeOfCertificateText = includes.get(k).getText();
					if(getTypeOfCertificateText.equalsIgnoreCase("Includes"))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
						if(getContentLocator.isDisplayed())
						{
							includesContentLocator = getContentLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
							
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Includes Content verification");
						checkIncludesContentStatus.add(fontName(includesContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkIncludesContentStatus.add(fontSize(includesContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkIncludesContentStatus.add(fontColor(includesContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkIncludesContentStatus.add(fontAlign(includesContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkIncludesContentStatus.add(fontStyle(includesContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Includes Content is not available in UI");
				checkIncludesContentStatus.add("false");
			}
		}
		return checkIncludesContentStatus;
	}
	public ArrayList<String> checkCreateHeading(ArrayList<String> createHeadingFromExcel)
	{
			ArrayList<String> checkCreateHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = createHeadingFromExcel;
			WebElement createLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> create = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < create.size(); k++)
				{
					String createText = create.get(k).getText();
					if(createText.equalsIgnoreCase("Create"))
					{
						WebElement getLocator = create.get(k).findElement(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						if(getLocator.isDisplayed())
						{
							createLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("create Heading verification");
						checkCreateHeadingStatus.add(fontName(createLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCreateHeadingStatus.add(fontSize(createLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCreateHeadingStatus.add(fontColor(createLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCreateHeadingStatus.add(fontAlign(createLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCreateHeadingStatus.add(fontStyle(createLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkCreateHeadingStatus.add(imageColor(createLocator, getDataFromExcel));
					}
					if(j == 7)
					{
						checkCreateHeadingStatus.add(marginTop(createLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Create Heading is not available in UI");
				checkCreateHeadingStatus.add("false");
			}
		}
		return checkCreateHeadingStatus;
	}
	
	public ArrayList<String> checkCreateContent(ArrayList<String> createContentFromExcel)
	{
			ArrayList<String> checkCreateContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = createContentFromExcel;
			WebElement createContentLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> create = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < create.size(); k++)
				{
					String createText = create.get(k).getText();
					if(createText.equalsIgnoreCase("Create"))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"]/p"));
						if(getContentLocator.isDisplayed())
						{
							createContentLocator = getContentLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Create Content verification");
						checkCreateContentStatus.add(fontName(createContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCreateContentStatus.add(fontSize(createContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCreateContentStatus.add(fontColor(createContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCreateContentStatus.add(fontAlign(createContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCreateContentStatus.add(fontStyle(createContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("create Content is not available in UI");
				checkCreateContentStatus.add("false");
			}
		}
		return checkCreateContentStatus;
	}
	
	public ArrayList<String> checkExerciseToExploreHeading(ArrayList<String> exerciseToExploreHeadingFromExcel)
	{
			ArrayList<String> checkExerciseToExploreHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = exerciseToExploreHeadingFromExcel;
			WebElement exerciseToExploreLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> ExerciseToExplore = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < ExerciseToExplore.size(); k++)
				{
					String ExerciseToExploreText = ExerciseToExplore.get(k).getText();
					if(ExerciseToExploreText.equalsIgnoreCase("Exercises to explore"))
					{
						WebElement getLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/h2"));
						if(getLocator.isDisplayed())
						{
							exerciseToExploreLocator = getLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}
				
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("ExerciseToExploreHeading verification");
						checkExerciseToExploreHeadingStatus.add(fontName(exerciseToExploreLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExerciseToExploreHeadingStatus.add(fontSize(exerciseToExploreLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExerciseToExploreHeadingStatus.add(fontColor(exerciseToExploreLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExerciseToExploreHeadingStatus.add(fontAlign(exerciseToExploreLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExerciseToExploreHeadingStatus.add(fontStyle(exerciseToExploreLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkExerciseToExploreHeadingStatus.add(imageColor(exerciseToExploreLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("ExerciseToExplore Heading is not available in UI");
				checkExerciseToExploreHeadingStatus.add("false");
			}
		}
		return checkExerciseToExploreHeadingStatus;
	}
	
	public ArrayList<String> checkExerciseToExploreContent(ArrayList<String> exerciseToExploreContentFromExcel)
	{
			ArrayList<String> checkExerciseToExploreContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = exerciseToExploreContentFromExcel;
			WebElement exerciseToExploreContentLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> ExerciseToExploreContent = driver.findElements(By.xpath("(//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'] /h2"));
				for(int k = 0; k < ExerciseToExploreContent.size(); k++)
				{
					String ExerciseToExploreText = ExerciseToExploreContent.get(k).getText();
					if(ExerciseToExploreText.equalsIgnoreCase("Exercises to explore"))
					{
						WebElement getContentLocator = driver.findElement(By.xpath("((//div[contains(@class,'CourseOffering_coursePropertiesSection')])[2]//div[contains(@class,'CourseOffering_courseProperty')]//div[@class='CourseOffering_coursePropertyText__px0TE'])["+k+"+1]/p"));
						if(getContentLocator.isDisplayed())
						{
							exerciseToExploreContentLocator = getContentLocator;
							checkProcess = true;
							break;
						}
						else
						{
							checkProcess = false;
						}
					}
				}		
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("ExerciseToExploreContent verification");
						checkExerciseToExploreContentStatus.add(fontName(exerciseToExploreContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExerciseToExploreContentStatus.add(fontSize(exerciseToExploreContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExerciseToExploreContentStatus.add(fontColor(exerciseToExploreContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExerciseToExploreContentStatus.add(fontAlign(exerciseToExploreContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExerciseToExploreContentStatus.add(fontStyle(exerciseToExploreContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("ExerciseToExplore Content is not available in UI");
				checkExerciseToExploreContentStatus.add("false");
			}
		}
		return checkExerciseToExploreContentStatus;
	}
	
	public ArrayList<String> checkCourseOverviewHeading(ArrayList<String> courseOverviewHeadingFromExcel)
	{
			ArrayList<String> checkCourseOverviewHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseOverviewHeadingFromExcel;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 100)");
			WebElement CourseOverviewHeading = null;
			boolean checkProcess = false;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[1]/h2"));
				if(locator.isDisplayed())
				{
					CourseOverviewHeading = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println( "webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("CourseOverview Heading verification");
						checkCourseOverviewHeadingStatus.add(fontName(CourseOverviewHeading, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseOverviewHeadingStatus.add(fontSize(CourseOverviewHeading, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseOverviewHeadingStatus.add(fontColor(CourseOverviewHeading, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseOverviewHeadingStatus.add(fontAlign(CourseOverviewHeading, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseOverviewHeadingStatus.add(fontStyle(CourseOverviewHeading, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("CourseOverview Heading is not available in UI");
				checkCourseOverviewHeadingStatus.add("false");
			}
		}
		return checkCourseOverviewHeadingStatus;
	}
	
	public ArrayList<String> checkCourseOverviewContent(ArrayList<String> courseOverviewContentFromExcel)
	{
			ArrayList<String> checkCourseOverviewContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = courseOverviewContentFromExcel;
			boolean checkProcess = false;
			WebElement overViewInfo = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionBody__JmtF4 accordion-body'])[1]"));
				if(locator.isDisplayed())
				{
					
					overViewInfo = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("CourseOverview Content verification");
						checkCourseOverviewContentStatus.add(fontName(overViewInfo, getDataFromExcel));
					}
					if(j == 2)
					{
						checkCourseOverviewContentStatus.add(fontSize(overViewInfo, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkCourseOverviewContentStatus.add(fontColor(overViewInfo, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkCourseOverviewContentStatus.add(fontAlign(overViewInfo, getDataFromExcel));
					}
					if(j == 5)
					{
						checkCourseOverviewContentStatus.add(fontStyle(overViewInfo, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Course Overview content is not available in UI");
				checkCourseOverviewContentStatus.add("false");
			}
		}
		return checkCourseOverviewContentStatus;
	}
	
	public ArrayList<String> checkHowItWorks(ArrayList<String> howItWorksFromExcel)
	{
			ArrayList<String> checkHowItWorksStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = howItWorksFromExcel;
			boolean checkProcess = false;
			WebElement HowItWorksLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[2]"));
				if(locator.isDisplayed())
				{
					HowItWorksLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("HowItWorks verification");
						checkHowItWorksStatus.add(fontName(HowItWorksLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkHowItWorksStatus.add(fontSize(HowItWorksLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkHowItWorksStatus.add(fontColor(HowItWorksLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkHowItWorksStatus.add(fontAlign(HowItWorksLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkHowItWorksStatus.add(fontStyle(HowItWorksLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("HowItWorks is not available in UI");
				checkHowItWorksStatus.add("false");
			}
		}
		return checkHowItWorksStatus;
	}
	
	public ArrayList<String> checkSkillsYouWillGain(ArrayList<String> skillsYouWillGainFromExcel)
	{
			ArrayList<String> checkSkillsYouWillGainStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = skillsYouWillGainFromExcel;
			boolean checkProcess = false;
			WebElement SkillsYouWillGain = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[3]"));
				if(locator.isDisplayed())
				{
					
					SkillsYouWillGain = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("SkillsYouWillGain verification");
						checkSkillsYouWillGainStatus.add(fontName(SkillsYouWillGain, getDataFromExcel));
					}
					if(j == 2)
					{
						checkSkillsYouWillGainStatus.add(fontSize(SkillsYouWillGain, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkSkillsYouWillGainStatus.add(fontColor(SkillsYouWillGain, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkSkillsYouWillGainStatus.add(fontAlign(SkillsYouWillGain, getDataFromExcel));
					}
					if(j == 5)
					{
						checkSkillsYouWillGainStatus.add(fontStyle(SkillsYouWillGain, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("SkillsYouWillGain is not available in UI");
				checkSkillsYouWillGainStatus.add("false");
			}
		}
		return checkSkillsYouWillGainStatus;
	}
	public ArrayList<String> checkWhoShouldEnrollOnThisCourse(ArrayList<String> whoShouldEnrollOnThisCourseFromExcel)
	{
			ArrayList<String> checkWhoShouldEnrollOnThisCourseStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whoShouldEnrollOnThisCourseFromExcel;
			WebElement WhoShouldEnrollOnThisCourseLocator = null;
			boolean checkProcess = false;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[3]"));
				if(locator.isDisplayed())
				{
					
					WhoShouldEnrollOnThisCourseLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhoShouldEnrollOnThisCourse verification");
						checkWhoShouldEnrollOnThisCourseStatus.add(fontName(WhoShouldEnrollOnThisCourseLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhoShouldEnrollOnThisCourseStatus.add(fontSize(WhoShouldEnrollOnThisCourseLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhoShouldEnrollOnThisCourseStatus.add(fontColor(WhoShouldEnrollOnThisCourseLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhoShouldEnrollOnThisCourseStatus.add(fontAlign(WhoShouldEnrollOnThisCourseLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhoShouldEnrollOnThisCourseStatus.add(fontStyle(WhoShouldEnrollOnThisCourseLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("WhoShouldEnrollOnThisCourse is not available in UI");
				checkWhoShouldEnrollOnThisCourseStatus.add("false");
			}
		}
		return checkWhoShouldEnrollOnThisCourseStatus;
	}
	
	public ArrayList<String> checkPrerequisites(ArrayList<String> prerequisitesFromExcel)
	{
			ArrayList<String> checkPrerequisitesStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = prerequisitesFromExcel;
			boolean checkProcess = false;
			WebElement Prerequisite = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//div[@class='CourseOverview_accordionMain__V7M9O accordion']//div[@class='CourseOverview_accordionItem__9k4Ix accordion-item'])[4]"));
				if(locator.isDisplayed())
				{
					
					Prerequisite = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = true;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Prerequisites verification");
						checkPrerequisitesStatus.add(fontName(Prerequisite, getDataFromExcel));
					}
					if(j == 2)
					{
						checkPrerequisitesStatus.add(fontSize(Prerequisite, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkPrerequisitesStatus.add(fontColor(Prerequisite, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkPrerequisitesStatus.add(fontAlign(Prerequisite, getDataFromExcel));
					}
					if(j == 5)
					{
						checkPrerequisitesStatus.add(fontStyle(Prerequisite, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Prerequisite is not available in UI");
				checkPrerequisitesStatus.add("false");
			}
		}
		return checkPrerequisitesStatus;
	}
	
	public ArrayList<String> checkEarnYourCertificateHeading(ArrayList<String> earnYourCertificateHeadingFromExcel)
	{
			ArrayList<String> checkEarnYourCertificateHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = earnYourCertificateHeadingFromExcel;
			boolean checkProcess = false;
			WebElement EarnYourCertificateLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class='EarnCertificate_certificateText__B_vTA'] h2"));
				if(locator.isDisplayed())
				{
					
					checkProcess = true;
					EarnYourCertificateLocator = locator;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("EarnYourCertificate verification");
						checkEarnYourCertificateHeadingStatus.add(fontName(EarnYourCertificateLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkEarnYourCertificateHeadingStatus.add(fontSize(EarnYourCertificateLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkEarnYourCertificateHeadingStatus.add(fontColor(EarnYourCertificateLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkEarnYourCertificateHeadingStatus.add(fontAlign(EarnYourCertificateLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkEarnYourCertificateHeadingStatus.add(fontStyle(EarnYourCertificateLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("EarnYourCertificate is not available in UI");
				checkEarnYourCertificateHeadingStatus.add("false");
			}
		}
		return checkEarnYourCertificateHeadingStatus;
	}
	public ArrayList<String> checkEarnYourCertificateContent(ArrayList<String> earnYourCertificateContentFromExcel) throws InterruptedException
	{
			ArrayList<String> checkEarnYourCertificateContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = earnYourCertificateContentFromExcel;
			WebElement EarnYourCertificateContentLocator = null;
			boolean checkProcess = false;
			try
			{
				JavascriptExecutor js1 = (JavascriptExecutor)driver;
				js1.executeScript("window.scrollBy(0, 1500)");
				Thread.sleep(300);
				WebElement locator = driver.findElement(By.cssSelector("div[class='EarnCertificate_certificateText__B_vTA'] p"));
				if(locator.isDisplayed())
				{
					
					EarnYourCertificateContentLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("EarnYourCertificateContent verification");
						checkEarnYourCertificateContentStatus.add(fontName(EarnYourCertificateContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkEarnYourCertificateContentStatus.add(fontSize(EarnYourCertificateContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkEarnYourCertificateContentStatus.add(fontColor(EarnYourCertificateContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkEarnYourCertificateContentStatus.add(fontAlign(EarnYourCertificateContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkEarnYourCertificateContentStatus.add(fontStyle(EarnYourCertificateContentLocator, getDataFromExcel));
					}
					if(j == 6)
					{
						checkEarnYourCertificateContentStatus.add(marginTop(EarnYourCertificateContentLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("EarnYourCertificateContent is not available in UI");
				checkEarnYourCertificateContentStatus.add("false");
			}
		}
		return checkEarnYourCertificateContentStatus;
	}
	
	public ArrayList<String> checkExpertTitle(ArrayList<String> expertTitleFromExcel)
	{
			ArrayList<String> checkExpertTitleStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = expertTitleFromExcel;
			boolean checkPocess = false;
			WebElement expertTitleLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("div[class='col-12']:nth-child(2) section[class='LearnWithExperts_mainSection__ZB7fa'] h2[class='LearnWithExperts_titleText__SqJj9 false']"));
				if(locator.isDisplayed())
				{
					
					expertTitleLocator = locator;
					checkPocess = true;
				}
				else
				{
					checkPocess = false;
				}
			}
            catch(NullPointerException e)
			{
            	System.out.println("webElement not found");
            	checkPocess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkPocess == true)
				{
					if(j == 1)
					{
						System.out.println("Expert Title verification");
						checkExpertTitleStatus.add(fontName(expertTitleLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExpertTitleStatus.add(fontSize(expertTitleLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExpertTitleStatus.add(fontColor(expertTitleLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExpertTitleStatus.add(fontAlign(expertTitleLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExpertTitleStatus.add(fontStyle(expertTitleLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Expert Title is not available in UI");
				checkExpertTitleStatus.add("false");
			}
		}
		return checkExpertTitleStatus;
	}
	
	public ArrayList<String> checkExpertName(ArrayList<String> expertNameFromExcel)
	{
			ArrayList<String> checkExpertNameStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = expertNameFromExcel;
			boolean checkProcess = false;
			WebElement expertNameLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//h2"));
				if(locator.isDisplayed())
				{
					
					expertNameLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("course Type verification");
						checkExpertNameStatus.add(fontName(expertNameLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExpertNameStatus.add(fontSize(expertNameLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExpertNameStatus.add(fontColor(expertNameLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExpertNameStatus.add(fontAlign(expertNameLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExpertNameStatus.add(fontStyle(expertNameLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("expert Name is not available in UI");
				checkExpertNameStatus.add("false");
			}
		}
		return checkExpertNameStatus;
	}
	
	public ArrayList<String> checkExpertRole(ArrayList<String> expertRoleFromExcel)
	{
			ArrayList<String> checkExpertRoleStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = expertRoleFromExcel;
			boolean checkProcess = false;
			WebElement expertRoleLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//p"));
				if(locator.isDisplayed())
				{
					
					expertRoleLocator = locator;
					checkProcess = true;
				}
				else
				{
					checkProcess = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("expert Role verification");
						checkExpertRoleStatus.add(fontName(expertRoleLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExpertRoleStatus.add(fontSize(expertRoleLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExpertRoleStatus.add(fontColor(expertRoleLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExpertRoleStatus.add(fontAlign(expertRoleLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExpertRoleStatus.add(fontStyle(expertRoleLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("expert Role is not available in UI");
				checkExpertRoleStatus.add("false");
			}
		}
		return checkExpertRoleStatus;
	}
	
	public ArrayList<String> checkExpertLink(ArrayList<String> ExpertLinkFromExcel)
	{
			ArrayList<String> checkExpertLinkStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = ExpertLinkFromExcel;
			boolean checkProcess = false;
			WebElement ExpertLinkLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='LearnWithExperts_mainSection__ZB7fa'])[2]//div[@class='LearnWithExperts_expertInfo___z0Ug']//a"));
				ExpertLinkLocator = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Expert Link verification");
						checkExpertLinkStatus.add(fontName(ExpertLinkLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkExpertLinkStatus.add(fontSize(ExpertLinkLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkExpertLinkStatus.add(fontColor(ExpertLinkLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkExpertLinkStatus.add(fontAlign(ExpertLinkLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkExpertLinkStatus.add(fontStyle(ExpertLinkLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Expert Link is not available in UI");
				checkExpertLinkStatus.add("false");
			}
		}
		return checkExpertLinkStatus;
	}
	
	public ArrayList<String> checkNewsLetterUpdates(ArrayList<String> newsLetterUpdatesFromExcel)
	{
			ArrayList<String> checkNewsLetterUpdatesStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = newsLetterUpdatesFromExcel;
			boolean checkProcess = false;
			WebElement NewsLetterUpdatesLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/h2"));
				NewsLetterUpdatesLocator = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				checkProcess = false;
				System.out.println("webElement not found");
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("NewsLetterUpdates verification");
						checkNewsLetterUpdatesStatus.add(fontName(NewsLetterUpdatesLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkNewsLetterUpdatesStatus.add(fontSize(NewsLetterUpdatesLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkNewsLetterUpdatesStatus.add(fontColor(NewsLetterUpdatesLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkNewsLetterUpdatesStatus.add(fontAlign(NewsLetterUpdatesLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkNewsLetterUpdatesStatus.add(fontStyle(NewsLetterUpdatesLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("NewsLetterUpdates is not available in UI");
				checkNewsLetterUpdatesStatus.add("false");
			}
		}
		return checkNewsLetterUpdatesStatus;
	}
	public ArrayList<String> checkSubscribeContent(ArrayList<String> subscribeContentFromExcel)
	{
			ArrayList<String> checkSubscribeContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = subscribeContentFromExcel;
			boolean checkProcess = false;
			WebElement SubscribeContent = null;
			try 
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/p"));
				SubscribeContent = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("Subscribe Content verification");
						checkSubscribeContentStatus.add(fontName(SubscribeContent, getDataFromExcel));
					}
					if(j == 2)
					{
						checkSubscribeContentStatus.add(fontSize(SubscribeContent, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkSubscribeContentStatus.add(fontColor(SubscribeContent, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkSubscribeContentStatus.add(fontAlign(SubscribeContent, getDataFromExcel));
					}
					if(j == 5)
					{
						checkSubscribeContentStatus.add(fontStyle(SubscribeContent, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("Subscribe Content is not available in UI");
				checkSubscribeContentStatus.add("false");
			}
		}
		return checkSubscribeContentStatus;
	}
	public ArrayList<String> checkFullName(ArrayList<String> fullNameFromExcel)
	{
			ArrayList<String> checkFullNameStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			boolean checkProcess = false;
			WebElement fullName = null;
			getData = fullNameFromExcel;
			try
			{
				WebElement locator = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//input[@name='full_name']"));
				fullName = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("FullName verification");
						checkFullNameStatus.add(fontName(fullName, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFullNameStatus.add(fontSize(fullName, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkFullNameStatus.add(fontColor(fullName, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkFullNameStatus.add(fontAlign(fullName, getDataFromExcel));
					}
					if(j == 5)
					{
						checkFullNameStatus.add(fontStyle(fullName, getDataFromExcel));
					}
					if(j == 6)
					{
						checkFullNameStatus.add(paddingLeft(fullName, getDataFromExcel));
					}
					if(j == 7)
					{
						checkFullNameStatus.add(paddingRight(fullName, getDataFromExcel));
					}
					if(j == 8)
					{
						checkFullNameStatus.add(paddingTop(fullName, getDataFromExcel));
					}
					if(j == 9)
					{
						checkFullNameStatus.add(paddingBottom(fullName, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("FullName is not available in UI");
				checkFullNameStatus.add("false");
			}
		}
		return checkFullNameStatus;
	}
	public ArrayList<String> checkEmail(ArrayList<String> emailFromExcel)
	{
			ArrayList<String> checkEmailStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = emailFromExcel;
			WebElement email = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//input[@name='email']"));

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkEmailStatus.add("notProcessed");
					}
				}
				else if(getData.size() == 10)
				{
					if(j == 1)
					{
						System.out.println("email verification");
						checkEmailStatus.add(fontName(email, getDataFromExcel));
					}
					if(j == 2)
					{
						checkEmailStatus.add(fontSize(email, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkEmailStatus.add(fontColor(email, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkEmailStatus.add(fontAlign(email, getDataFromExcel));
					}
					if(j == 5)
					{
						checkEmailStatus.add(fontStyle(email, getDataFromExcel));
					}
					if(j == 6)
					{
						checkEmailStatus.add(paddingLeft(email, getDataFromExcel));
					}
					if(j == 7)
					{
						checkEmailStatus.add(paddingRight(email, getDataFromExcel));
					}
					if(j == 8)
					{
						checkEmailStatus.add(paddingTop(email, getDataFromExcel));
					}
					if(j == 9)
					{
						checkEmailStatus.add(paddingBottom(email, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("email is not available in UI");
				checkEmailStatus.add("false");
			}
		}
		return checkEmailStatus;
	}
	
	public ArrayList<String> checkSubscribeButton(ArrayList<String> subscribeButtonFromExcel)
	{
			ArrayList<String> checkSubscribeButtonStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = subscribeButtonFromExcel;
			WebElement subscribeButton = driver.findElement(By.xpath("(//section[@class='NewsAndUpdates_mainSection__tSkcR'])[2]/form//button[contains(@class,'NewsAndUpdates_subscribeBtn')]"));

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkSubscribeButtonStatus.add("notProcessed");
					}
				}
				else if(getData.size() == 10)
				{
					if(j == 1)
					{
						System.out.println("subscribe Button verification");
						checkSubscribeButtonStatus.add(fontName(subscribeButton, getDataFromExcel));
					}
					if(j == 2)
					{
						checkSubscribeButtonStatus.add(fontSize(subscribeButton, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkSubscribeButtonStatus.add(fontColor(subscribeButton, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkSubscribeButtonStatus.add(fontAlign(subscribeButton, getDataFromExcel));
					}
					if(j == 5)
					{
						checkSubscribeButtonStatus.add(fontStyle(subscribeButton, getDataFromExcel));
					}
					if(j == 6)
					{
						checkSubscribeButtonStatus.add(paddingLeft(subscribeButton, getDataFromExcel));
					}
					if(j == 7)
					{
						checkSubscribeButtonStatus.add(paddingRight(subscribeButton, getDataFromExcel));
					}
					if(j == 8)
					{
						checkSubscribeButtonStatus.add(paddingTop(subscribeButton, getDataFromExcel));
					}
					if(j == 9)
					{
						checkSubscribeButtonStatus.add(paddingBottom(subscribeButton, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("subscribe Button is not available in UI");
				checkSubscribeButtonStatus.add("false");
			}
		}
		return checkSubscribeButtonStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineHeading(ArrayList<String> whyLearnSkillupOnlineHeadingFromExcel)
	{
			ArrayList<String> checkwhyLearnSkillupOnlineHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineHeadingFromExcel;
			boolean checkProcess = true;
			WebElement WhyLearnSkillupOnlineTitle = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//section[@class='WhyLearnSkillUp_mainSection__pNbU3']//h2[@class='WhyLearnSkillUp_titleText__N8j59 false']"));
				WhyLearnSkillupOnlineTitle = locator;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnline Title verification");
						checkwhyLearnSkillupOnlineHeadingStatus.add(fontName(WhyLearnSkillupOnlineTitle, getDataFromExcel));
					}
					if(j == 2)
					{
						checkwhyLearnSkillupOnlineHeadingStatus.add(fontSize(WhyLearnSkillupOnlineTitle, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkwhyLearnSkillupOnlineHeadingStatus.add(fontColor(WhyLearnSkillupOnlineTitle, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkwhyLearnSkillupOnlineHeadingStatus.add(fontAlign(WhyLearnSkillupOnlineTitle, getDataFromExcel));
					}
					if(j == 5)
					{
						checkwhyLearnSkillupOnlineHeadingStatus.add(fontStyle(WhyLearnSkillupOnlineTitle, getDataFromExcel));
					}
					if(j == 6)
					{
						checkwhyLearnSkillupOnlineHeadingStatus.add(textBgColor(WhyLearnSkillupOnlineTitle, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("WhyLearnSkillupOnline Title is not available in UI");
				checkwhyLearnSkillupOnlineHeadingStatus.add("false");
			}
		}
		return checkwhyLearnSkillupOnlineHeadingStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineContent(ArrayList<String> whyLearnSkillupOnlineContentFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineContentFromExcel;
			boolean checkProcess = false;
			WebElement WhyLearnSkillupOnlineDescription = null;
			try
			{
				WebElement locator = driver.findElement(By.xpath("//section[@class='WhyLearnSkillUp_mainSection__pNbU3']//p[@class='WhyLearnSkillUp_descriptionText__iMmGu false']"));
				WhyLearnSkillupOnlineDescription = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnline Content verification");
						checkWhyLearnSkillupOnlineContentStatus.add(fontName(WhyLearnSkillupOnlineDescription, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineContentStatus.add(fontSize(WhyLearnSkillupOnlineDescription, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhyLearnSkillupOnlineContentStatus.add(fontColor(WhyLearnSkillupOnlineDescription, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhyLearnSkillupOnlineContentStatus.add(fontAlign(WhyLearnSkillupOnlineDescription, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhyLearnSkillupOnlineContentStatus.add(fontStyle(WhyLearnSkillupOnlineDescription, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("WhyLearnSkillupOnline Content is not available in UI");
				checkWhyLearnSkillupOnlineContentStatus.add("false");
			}
		}
		return checkWhyLearnSkillupOnlineContentStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineTickIcon(ArrayList<String> whyLearnSkillupOnlineTickIconFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineTickIconStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineTickIconFromExcel;
			boolean checkProcess = false;
			WebElement WhyLearnSkillupOnlineTickIconLocator = null;
			try
			{
				List<WebElement> subTitlesTickIcon = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_pointSection__t2So_'] div[class='WhyLearnSkillUp_point__XQHsy'] img[alt='tick']"));
				for(int i = 0; i < subTitlesTickIcon.size(); i++)
				{
					WhyLearnSkillupOnlineTickIconLocator = subTitlesTickIcon.get(i);
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnlineTickIcon verification");
						checkWhyLearnSkillupOnlineTickIconStatus.add(imageColor(WhyLearnSkillupOnlineTickIconLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineTickIconStatus.add(checkObjectContain(WhyLearnSkillupOnlineTickIconLocator, getDataFromExcel));	
					}
				}
			else
			{
				System.out.println("WhyLearnSkillupOnlineTickIcon is not available in UI");
				checkWhyLearnSkillupOnlineTickIconStatus.add("false");
			}
			}
		return checkWhyLearnSkillupOnlineTickIconStatus;
	}
	public ArrayList<String> checkWhyLearnSkillupOnlineTickIconContent(ArrayList<String> whyLearnSkillupOnlineTickIconContentFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineTickIconContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineTickIconContentFromExcel;
			WebElement subTitlesTickInfoLocator = null;
			boolean checkProcess = false;
			try 
			{
				List<WebElement> subTitlesTickInfo = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_pointSection__t2So_'] div[class='WhyLearnSkillUp_point__XQHsy'] h2"));
				for(int i = 0; i < subTitlesTickInfo.size(); i++)
				{
					subTitlesTickInfoLocator = subTitlesTickInfo.get(i);
					checkProcess = true;
				}	
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnlineTickIcon Content verification");
						checkWhyLearnSkillupOnlineTickIconContentStatus.add(fontName(subTitlesTickInfoLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineTickIconContentStatus.add(fontSize(subTitlesTickInfoLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhyLearnSkillupOnlineTickIconContentStatus.add(fontColor(subTitlesTickInfoLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhyLearnSkillupOnlineTickIconContentStatus.add(fontAlign(subTitlesTickInfoLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhyLearnSkillupOnlineTickIconContentStatus.add(fontStyle(subTitlesTickInfoLocator, getDataFromExcel));
					}
			    }
				else
				{
					System.out.println("WhyLearnSkillupOnlineTickIcon Content is not available in UI");
					checkWhyLearnSkillupOnlineTickIconContentStatus.add("false");
				}
			}
		return checkWhyLearnSkillupOnlineTickIconContentStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineProfitPoint(ArrayList<String> whyLearnSkillupOnlineProfitPointFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineProfitPointStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineProfitPointFromExcel;
			WebElement profitPointSectionLocator = null;
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("window.scrollBy(0, 100)");
			System.out.println("profitPointSection verification");
			boolean checkProcess = false;
			try
			{
				List<WebElement> profitPointSection = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] img[alt='icon']"));
				for(int i = 0; i < profitPointSection.size(); i++)
				{
					profitPointSectionLocator = profitPointSection.get(i);
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnline ProfitPoint verification");
						checkWhyLearnSkillupOnlineProfitPointStatus.add(fontName(profitPointSectionLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineProfitPointStatus.add(fontSize(profitPointSectionLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhyLearnSkillupOnlineProfitPointStatus.add(fontColor(profitPointSectionLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhyLearnSkillupOnlineProfitPointStatus.add(fontAlign(profitPointSectionLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhyLearnSkillupOnlineProfitPointStatus.add(fontStyle(profitPointSectionLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("profitPointSection  is not available in UI");
				checkWhyLearnSkillupOnlineProfitPointStatus.add("false");
			}
		}
		return checkWhyLearnSkillupOnlineProfitPointStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineProfitPointHeading(ArrayList<String> WhyLearnSkillupOnlineProfitPointHeadingFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineProfitPointHeadingStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = WhyLearnSkillupOnlineProfitPointHeadingFromExcel;
			WebElement WhyLearnSkillupOnlineProfitPointHeadingLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> profitPointSectionHeading = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] div[class='col-sm-4 col-12']"));
				for(int i = 0; i < profitPointSectionHeading.size(); i++)
				{
					WhyLearnSkillupOnlineProfitPointHeadingLocator = profitPointSectionHeading.get(i);
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(getData.size() == 2)
				{
					if(getData.get(j).equals("NA"))
					{
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add("notProcessed");
					}
				}
				else if(getData.size() == 6)
				{
					if(j == 1)
					{
						System.out.println("WhyLearnSkillupOnlineProfitPoint Heading verification");
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add(fontName(WhyLearnSkillupOnlineProfitPointHeadingLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add(fontSize(WhyLearnSkillupOnlineProfitPointHeadingLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add(fontColor(WhyLearnSkillupOnlineProfitPointHeadingLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add(fontAlign(WhyLearnSkillupOnlineProfitPointHeadingLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add(fontStyle(WhyLearnSkillupOnlineProfitPointHeadingLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("WhyLearnSkillupOnlineProfitPoint Heading is not available in UI");
				checkWhyLearnSkillupOnlineProfitPointHeadingStatus.add("false");
			}
		}
		return checkWhyLearnSkillupOnlineProfitPointHeadingStatus;
	}
	
	public ArrayList<String> checkWhyLearnSkillupOnlineProfitPointContent(ArrayList<String> whyLearnSkillupOnlineProfitPointContentFromExcel)
	{
			ArrayList<String> checkWhyLearnSkillupOnlineProfitPointContentStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = whyLearnSkillupOnlineProfitPointContentFromExcel;
			WebElement whyLearnSkillupOnlineProfitPointContentLocator = null;
			boolean checkProcess = false;
			try
			{
				List<WebElement> profitPointSectionDescription = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] div[class='col'] p"));
				for(int i = 0; i < profitPointSectionDescription.size(); i++)
				{
					whyLearnSkillupOnlineProfitPointContentLocator = profitPointSectionDescription.get(i);
					checkProcess = true;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("whyLearnSkillupOnlineProfitPoint Content verification");
						checkWhyLearnSkillupOnlineProfitPointContentStatus.add(fontName(whyLearnSkillupOnlineProfitPointContentLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkWhyLearnSkillupOnlineProfitPointContentStatus.add(fontSize(whyLearnSkillupOnlineProfitPointContentLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkWhyLearnSkillupOnlineProfitPointContentStatus.add(fontColor(whyLearnSkillupOnlineProfitPointContentLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkWhyLearnSkillupOnlineProfitPointContentStatus.add(fontAlign(whyLearnSkillupOnlineProfitPointContentLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkWhyLearnSkillupOnlineProfitPointContentStatus.add(fontStyle(whyLearnSkillupOnlineProfitPointContentLocator, getDataFromExcel));
					}
					
				}
			else
			{
				System.out.println("whyLearnSkillupOnlineProfitPoint Content is not available in UI");
				checkWhyLearnSkillupOnlineProfitPointContentStatus.add("false");
			}
		}
		return checkWhyLearnSkillupOnlineProfitPointContentStatus;
	}
	public ArrayList<String> checkFAQs(ArrayList<String> FAQsFromExcel)
	{
			ArrayList<String> checkFAQsStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			boolean checkProcess = false;
			getData = FAQsFromExcel;
			WebElement FAQsLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
				FAQsLocator = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webelement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("FAQs verification");
						checkFAQsStatus.add(fontName(FAQsLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkFAQsStatus.add(fontSize(FAQsLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkFAQsStatus.add(fontColor(FAQsLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkFAQsStatus.add(fontAlign(FAQsLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkFAQsStatus.add(fontStyle(FAQsLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("FAQs is not available in UI");
				checkFAQsStatus.add("false");
			}
		}
		return checkFAQsStatus;
	}
	public ArrayList<String> checkQuestion(ArrayList<String> questionFromExcel)
	{
			ArrayList<String> checkQuestionStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = questionFromExcel;
			boolean checkProcess = false;
			WebElement Question = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
				Question = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}
			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess == true)
				{
					if(j == 1)
					{
						System.out.println("question verification");
						checkQuestionStatus.add(fontName(Question, getDataFromExcel));
					}
					if(j == 2)
					{
						checkQuestionStatus.add(fontSize(Question, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkQuestionStatus.add(fontColor(Question, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkQuestionStatus.add(fontAlign(Question, getDataFromExcel));
					}
					if(j == 5)
					{
						checkQuestionStatus.add(fontStyle(Question, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("question is not available in UI");
				checkQuestionStatus.add("false");
			}
		}
		return checkQuestionStatus;
	}	
	
	public ArrayList<String> checkAnswer(ArrayList<String> answerFromExcel)
	{
			ArrayList<String> checkAnswerStatus = new ArrayList<String>();
			ArrayList<String> getData = new ArrayList<String>();
			getData = answerFromExcel;
			boolean checkProcess = false;
			WebElement answerLocator = null;
			try
			{
				WebElement locator = driver.findElement(By.cssSelector("section[class='CourseDescription_mainSection__WrO9h'] h1"));
				answerLocator = locator;
				checkProcess = true;
			}
			catch(NullPointerException e)
			{
				System.out.println("webElement not found");
				checkProcess = false;
			}

			for(int j = 0; j < getData.size(); j++)
			{
				String getDataFromExcel = getData.get(j);
				if(checkProcess)
				{
					if(j == 1)
					{
						System.out.println("answer verification");
						checkAnswerStatus.add(fontName(answerLocator, getDataFromExcel));
					}
					if(j == 2)
					{
						checkAnswerStatus.add(fontSize(answerLocator, getDataFromExcel));	
					}
					if(j == 3)
					{
						checkAnswerStatus.add(fontColor(answerLocator, getDataFromExcel));	
					}
					if(j == 4)
					{
						checkAnswerStatus.add(fontAlign(answerLocator, getDataFromExcel));
					}
					if(j == 5)
					{
						checkAnswerStatus.add(fontStyle(answerLocator, getDataFromExcel));
					}
				}
			else
			{
				System.out.println("answer is not available in UI");
				checkAnswerStatus.add("false");
			}
		}
		return checkAnswerStatus;
	}
}

