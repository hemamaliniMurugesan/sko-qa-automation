package com.seo.regression.testing;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.seo.utility.TestUtil;

public class ViewCourseFeature extends ProcessLogin
{
	public String clickViewCourse() throws InterruptedException
	{
		
		String viewCourseStatus = "Failed";
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String window : windows)
		{
			if(driver.getCurrentUrl().equalsIgnoreCase("https://stagecourses-in.skillup.online/dashboard"))
			{
				driver.switchTo().window(window);
				List<WebElement> listOfCourse = driver.findElements(By.cssSelector("ul[class='listing-courses'] li[class='course-item'] section[class='details'] div[class='wrapper-course-details'] h3 a"));
				for(int i = 0; i < listOfCourse.size();i++)
				{
					System.out.println("List of course names in dashboard : "+listOfCourse.get(i).getText());
				}
				viewCourseStatus = "success";
			}
			else
			{
				WebElement clickDropDown = driver.findElement(By.cssSelector("li[class='SigNUP open'] img[class='dPaRoW']"));
				clickDropDown.click();
				WebElement clickDashboard = driver.findElement(By.cssSelector("ul[class='dropdown-menu Primary02_Blue'] li:nth-child(2) a"));
				clickDashboard.click();
				Thread.sleep(1000);
				WebElement clickViewCourse = driver.findElement(By.cssSelector("div[class='course-actions'] a[class*='enter-course']"));
				clickViewCourse.click();
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				if(driver.getCurrentUrl().contains("IBM"))
				{
					System.out.println("Course ID contains IBM");
					if(driver.getCurrentUrl().contains("-dev.skillsnetwork.site"))
					{
						viewCourseStatus = "Success";
						driver.close();
					}
					else
					{
						viewCourseStatus = "Failed";
						driver.close();
					}
				}
				else
				{
					viewCourseStatus = "Failed";
					driver.close();
				}
			}
		}
		return viewCourseStatus;
	}
	
}
