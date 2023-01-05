package com.seo.regression.testing;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.seo.utility.TestUtil;

public class ViewCourseFeature extends ProcessLogin
{
	public String clickViewCourse()
	{
		String viewCourseStatus = "Failed";
		WebElement clickDashboard = driver.findElement(By.cssSelector("ul[class='dropdown-menu Primary02_Blue'] li:nth-child(2) a"));
		clickDashboard.click();
		WebElement clickViewCourse = driver.findElement(By.cssSelector("div[class='course-actions'] a[class*='enter-course']"));
		clickViewCourse.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		/*String parentwindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for(String handle : allWindows)
		{
			if(!handle.equals(parentwindow))
			{*/
			//	driver.switchTo().window(handle);
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
				/*
				 * } }
				 */
		return viewCourseStatus;
	}
	
}
