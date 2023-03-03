package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.seo.utility.TestUtil;

public class ViewCourseFeature extends ProcessLogin
{
	public ArrayList<String> clickViewCourse() throws InterruptedException
	{
		
		ArrayList<String> viewCourseStatus = new ArrayList<String>();
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String window : windows)
		{
			if(driver.getCurrentUrl().contains("dashboard"))
			{
				driver.switchTo().window(window);
				List<WebElement> listOfCourse = driver.findElements(By.cssSelector("ul[class='listing-courses'] li[class='course-item'] section[class='details'] div[class='wrapper-course-details'] h3 a"));
				for(int i = 0; i < listOfCourse.size();i++)
				{
					System.out.println("List of course names in dashboard : "+listOfCourse.get(i).getText());
				}
				viewCourseStatus.add("success");
			}
			else
			{
				WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP open'] img[class='dPaRoW']"));
				clickDropDown.click();
				WebElement clickDashboard = driver.findElement(By.cssSelector("ul[class='dropdown-menu Primary02_Blue'] li:nth-child(2) a"));
				if(clickDashboard.isDisplayed())
				{
					clickDashboard.click();
					Thread.sleep(1000);
				}
				WebElement clickViewCourse = driver.findElement(By.cssSelector("div[class='course-actions'] a[class*='enter-course']"));
				clickViewCourse.click();
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				if(driver.getCurrentUrl().contains("IBM"))
				{
					System.out.println("Course ID contains IBM");
					if(driver.getCurrentUrl().contains("-dev.skillsnetwork.site"))
					{
						viewCourseStatus.add("Success");
						driver.close();
					}
					else
					{
						viewCourseStatus.add("Failed");
						driver.close();
					}
				}
				else
				{
					viewCourseStatus.add("Failed");
					driver.close();
				}
			}
			driver.quit();
		}
		return viewCourseStatus;
	}
	
	public void courses() throws InterruptedException
	{
		List<WebElement> courses = driver.findElements(By.cssSelector("ul[class='listing-courses'] li[class='course-item']"));
		for(int i = 0; i < courses.size(); i++)
		{
			WebElement courseNameLocator = courses.get(i).findElement(By.cssSelector(" h3 a"));
			String getCourseName = courseNameLocator.getText();
			System.out.println("list of Course Name in user dashboard: "+getCourseName);
			List<WebElement> getActiveCourse = courseNameLocator.findElements(By.cssSelector(":not([class='disable-look'])"));
			if(getActiveCourse.size()>0)
			{
				for(int j = 0; j < getActiveCourse.size(); j++)
				{
					System.out.println("Name of Active courses in user dashboard : "+getActiveCourse.get(j).getText());
					getActiveCourse.get(j).click();
				}
				
			}
			Thread.sleep(1000);
		}
	}
	public void shareCourse()
	{
		
	}
}
