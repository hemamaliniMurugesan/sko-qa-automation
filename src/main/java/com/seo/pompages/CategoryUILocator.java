
package com.seo.pompages;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
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


public class CategoryUILocator
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
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_105.0.5195.52 version\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	public String launchURL()
	{
		try
		{
			String addHostURL = ConfigFileReader.getURL();
			driver.get(addHostURL);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}
	
	public String selectCategoryFromSkillUp(String categoryName)
	{
		launchURL();
		String status = "fail";
		WebElement clickCourseDropdown = driver.findElement(By.cssSelector("a#NavMegamenu"));
		clickCourseDropdown.click();
		List<WebElement> dropdownList = driver.findElements(By.cssSelector("div#MMDroPDoWNMAiN ul[class=\"categorylist dropdown-submenu\"] li"));
		for(int i = 0; i < dropdownList.size(); i++)
		{
			String getCategoryName = dropdownList.get(i).getText();
			if(categoryName.equalsIgnoreCase(getCategoryName))
			{
				dropdownList.get(i).click();
				break;
			}
		}
		
		WebElement checkCatalogText = driver.findElement(By.xpath("//h2[contains(text(),'Browse Our Artificial Intelligence Learning Catalog ')]"));
		if(checkCatalogText.isDisplayed())
		{
			List<WebElement> listOfCourseCard = driver.findElements(By.cssSelector("div[class=\"skills-card-filter-bx\"] div.CoLCOMN"));
			for(int k = 0; k < listOfCourseCard.size(); k++)
			{
				System.out.println(listOfCourseCard.get(k).findElement(By.cssSelector(" a[href]")).getAttribute("href"));
			}
			List<WebElement> listOfCourse = driver.findElements(By.cssSelector("div[class=\"skills-card-filter-bx\"] div.CoLCOMN"));
			for(int i = 0; i < listOfCourse.size(); i++)
			{
				try
				{
					WebElement clickCourse = listOfCourse.get(i).findElement(By.cssSelector(" a[href]"));
					String courseCode = clickCourse.getAttribute("href");
					System.out.println("course code "+i+" : " +courseCode);
					WebElement courseImage = listOfCourse.get(i).findElement(By.cssSelector(" div[class=\"CourseCArdmain\"] img[alt*=\"course\"]"));
					System.out.println("Verified course image "+i+" : "+courseImage.getAttribute("alt"));
					WebElement typeOfCatalog = listOfCourse.get(i).findElement(By.cssSelector(" div[class=\"CourseCArd-right\"] div[class=\"BadGE_LeFT LTBlaK\"]"));
					System.out.println("Verified course or program "+i+" : "+typeOfCatalog.getText());
					WebElement courseName = listOfCourse.get(i).findElement(By.cssSelector("div[class=\"CourseCArd-right\"] p[class=\"Accent_BlueGreen01-text\"]"));
					System.out.println("Course Name "+i+" : "+courseName.getText());
					List<WebElement> courseHeadingSection = listOfCourse.get(i).findElements(By.cssSelector("div[class=\"CourseCArd-right\"] div[class=\"CoRS_DEsCPT\"] div[class=\"CoursHeadingSection\"] ul li"));
					for(int j = 0; j < courseHeadingSection.size(); j++)
					{
						System.out.println(" "+i+" Course Heading Section "+j+": " +courseHeadingSection.get(j).getText());
					}
					WebElement typeOfCourse = listOfCourse.get(i).findElement(By.cssSelector("div[class=\"CourseCArd-right\"] div[class=\"CoRS_DEsCPT\"] div[class=\"UnivercityBrand\"]"));
					System.out.println("Type Of course "+i+" : "+typeOfCourse.getText());
					((JavascriptExecutor) driver).executeScript("window.open()");
					ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs.get(1));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
					driver.get(courseCode);
					Thread.sleep(700);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
					driver.close();
					driver.switchTo().window(tabs.get(0));
					status = "success";
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		}
		checkPopularCategory();
		return status;
	}
	
	public void checkPopularCategory()
	{
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get("https://dev-in.skillup.online/");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 1500)");
		List<WebElement> clickPopular = driver.findElements(By.cssSelector("section[class=\"Excollaboration TOPCAtGoRY\"] div[class=\"Excollaboration-inner\"] ul li"));
		for(int i = 0; i < clickPopular.size(); i++)
		if(clickPopular.get(i).getText().equalsIgnoreCase("Popular"))
		{
			clickPopular.get(i).click();
			System.out.println("popular category : "+driver.getCurrentUrl());
		}
	}
}
	