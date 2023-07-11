package com.seo.regression.testing;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordLocator
{
	WebDriver driver;
	public ForgotPasswordLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void openLoginSite(String urlFromExcel)
	{
		WebElement clickLogin = driver.findElement(By.xpath("//li[contains(@class,'Header_loginBtn')]//a[contains(@href,'/login?')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", clickLogin);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String allWindows : windows)
		{
			driver.switchTo().window(allWindows);
			if(driver.getCurrentUrl().contains("/login?"))
			{
				driver.switchTo().window(allWindows);	
				js.executeScript("window.scrollBy(0,200)", "");
				WebElement clickForgot = driver.findElement(By.xpath("//a[contains(@href,'password')]"));
				clickForgot.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(200));
	}
	
	public String forgotPassword(String emailFromExcel) throws InterruptedException
	{
		String status = "Failed";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String allWindows : windows)
		{
			driver.switchTo().window(allWindows);
			if(driver.getCurrentUrl().contains("/password_reset/"))
			{
				driver.switchTo().window(allWindows);
				System.out.println("reset password page");
				WebElement enterEmail = driver.findElement(By.cssSelector("input[id='email']"));
				enterEmail.sendKeys(emailFromExcel);
				WebElement clickSendemail = driver.findElement(By.cssSelector("input#reset_password"));
				clickSendemail.click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		String parentScreen = driver.getWindowHandle();
		Set<String> nextWindows = driver.getWindowHandles();
		for(String allWindow : nextWindows)
		{
			driver.switchTo().window(allWindow);
			if(driver.getCurrentUrl().contains("/password_reset/done/"))
			{
				driver.switchTo().window(allWindow);
				System.out.println("password reset page");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				WebElement checkScreen = driver.findElement(By.xpath("//h4[contains(text(),'Please check your email')]"));
				String getText = checkScreen.getText();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				if(getText.equalsIgnoreCase("Please check your email"))
				{
					System.out.println("check your mail is displayed");
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
		WebElement clickHomePage = driver.findElement(By.xpath("//a[contains(text(),'Go to Homepage')]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.elementToBeClickable(clickHomePage));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", clickHomePage);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));

		String homePageWindow = driver.getWindowHandle();
		Set<String> windows1 = driver.getWindowHandles();
		for(String allWindow1 : windows1)
		{
			driver.switchTo().window(allWindow1);
			if(driver.getCurrentUrl().contains(OpenWebsite.setHost))
			{
				driver.switchTo().window(allWindow1);
				System.out.println("home page is displayed");
				status = "Success";
			}
			else
			{
				driver.get(OpenWebsite.setHost);
				status = "Success";
			}
		}
		System.out.println("forget password process done");
		return status;
	}
}
