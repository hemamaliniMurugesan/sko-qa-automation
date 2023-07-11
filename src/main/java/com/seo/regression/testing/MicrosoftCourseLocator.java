package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MicrosoftCourseLocator 
{
	WebDriver driver;
	int respCode = 200;
	HttpURLConnection huc = null;
	
	public MicrosoftCourseLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public ArrayList<String> verifyMicrosftPage()
	{
		ArrayList<String> processStatus = new ArrayList<String>();
		try
		{
			WebElement  clickCourseDropdown = driver.findElement(By.cssSelector("div[class=' Header_category__mr_e4']"));
			clickCourseDropdown.click();
			List<WebElement> learningPartners = driver.findElements(By.cssSelector("ul[class='dropdown-menu dropdown-cat Header_dropdownMenu__oDZ7V show'] div[class='LearningPartners catcolumn'] li a"));
			for(int i = 0; i < learningPartners.size();i++)
			{
				String getLearningPartnerURL = learningPartners.get(i).getAttribute("href");
				if(getLearningPartnerURL.contains("microsoft"))
				{
					String url = this.checkURLStatus(getLearningPartnerURL);
					String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
					learningPartners.get(i).sendKeys(n);
					if(url.equalsIgnoreCase("fail"))
					{
						processStatus.add("fail");
					}
					String parentWindow = driver.getWindowHandle();
					Set<String> childWnidow = driver.getWindowHandles();
					for(String windows : childWnidow)
					{
						driver.switchTo().window(windows);
						if(driver.getCurrentUrl().contains("microsoft"))
						{
							driver.switchTo().window(windows);
							System.out.println("microsoft page : "+driver.getCurrentUrl());
							processStatus.add("pass");
							break;
						}
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			processStatus.add("fail");
		}
		return processStatus;
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
	public ArrayList<String> verifyMicrosoftScourses(ArrayList<String> data)
	{
		ArrayList<String> processStatus = new ArrayList<String>();
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 1100)", "");
			WebElement clickShowMore = driver.findElement(By.cssSelector("div[class*='ManageCardsLimit_showMoreSection'] button"));
			js.executeScript("arguments[0].click()", clickShowMore);
			List<WebElement> listOfCourses = driver.findElements(By.cssSelector("div[class*='LearningCatalog_cardRow'] div[class*='LearningCatalog_customCard']"));
			for(int i = 0; i < listOfCourses.size(); i++)
			{
				String courseURL = listOfCourses.get(i).findElement(By.cssSelector(" div a[href]")).getAttribute("href");
				if(data.get(i+1).equalsIgnoreCase(listOfCourses.get(i).findElement(By.cssSelector(" div[class='RegularCourseCard_courseHeading__1Ohrn'] p")).getText()))
				{
					String urlLink = this.checkURLStatus(courseURL);
					if(urlLink.equalsIgnoreCase("fail"))
					{
						processStatus.add(data.get(i+1));
					}
					else
					{
						processStatus.add("pass");
					}
					JavascriptExecutor js1 = (JavascriptExecutor) driver; js1. executeScript(
							"window. open('"+urlLink+"');" );
					String parentWindow = driver.getWindowHandle();
					Set<String> childWnidow = driver.getWindowHandles();
					for(String windows : childWnidow)
					{
						driver.switchTo().window(windows);
						if(!parentWindow.equalsIgnoreCase(windows))
						{
							driver.switchTo().window(windows);
							driver.close();
							driver.switchTo().window(parentWindow);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			processStatus.add("MicrosoftScourses");
		}
		return processStatus;
	}
}
