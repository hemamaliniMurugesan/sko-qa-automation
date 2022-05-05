package com.test.base;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.CourseDetailsPage;

public class Login 
{
	WebDriver driver;
	WebDriverWait wait;
	private CourseDetailsPage courseDetails;
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public boolean loginSEO(String username, String password)
	{
		boolean status = false;
		courseDetails.openDriver();
		driver.get(ConfigFileReader.getProperty("seoLoginURL"));
		WebElement clickLogin = driver.findElement(By.xpath("//a[contains(text(),\"LOGIN\")]"));
		clickLogin.click();
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> itr = allWindows.iterator();
		while(itr.hasNext())
		{
			String childWindow = itr.next();
			if(!parentWindow.equalsIgnoreCase(childWindow))
			{
				driver.switchTo().window(childWindow);
				String checkLoginScreen = driver.findElement(By.xpath("//h4[contains(text(),\"Log\")]")).getText();
				System.out.println(checkLoginScreen);
				WebElement email = driver.findElement(By.id("email"));
				email.clear();
				email.sendKeys(username);
				WebElement pwd = driver.findElement(By.id("password"));
				pwd.clear();
				pwd.sendKeys(password);
				WebElement LoginButton = driver.findElement(By.id("login_in"));
				LoginButton.click();
				WebElement checkUserName = driver.findElement(By.xpath("(//a[contains(text(),\"Hello\")])[2]"));
				System.out.println(checkUserName.getText());
				if(checkUserName.isDisplayed())
				{
					status = true;
				}
				else
				{
					status = false;
				}
			}
		}
		return status;
	}
}
