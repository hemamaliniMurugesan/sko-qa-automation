package com.seo.regression.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SignupSocialAccLocator 
{
	WebDriver driver;
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	public SignupSocialAccLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public ArrayList<String> checkFACEBOOK(ArrayList<String> dataFromExcel)
	{
		try
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> checkGOOGLE(ArrayList<String> dataFromExcel)
	{
		try
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	ArrayList<String> status = new ArrayList<String>();
	public ArrayList<String> LinkedINProcess(String uname, String pwd)
	{
		List<WebElement> enterEmailPhone = driver.findElements(By.cssSelector("div[id='error-for-username'][class='form__label--error']"));
		if(enterEmailPhone.size()>0) 
		{
			System.out.println("linked In Username error");
			status.add("fail");
		}
		else
		{
			enterEmailPhone.get(0).sendKeys(uname);
			status.add("pass");
		}
		List<WebElement> enterPassword = driver.findElements(By.cssSelector("div[id='error-for-password'][class='form__label--error']"));
		if(enterPassword.size()>0) 
		{
			System.out.println("linked In password error");
			status.add("fail");
		}
		else
		{
			enterPassword.get(0).sendKeys(pwd);
			status.add("pass");
		}
		WebElement clickSubmit = driver.findElement(By.cssSelector("button[type='submit']"));
		clickSubmit.click();
		return status;
	}
	public String signUP(ArrayList<String> signUPData)
	{
		String status = "fail";
		try
		{
			if(driver.getCurrentUrl().contains("register?"))
			{
				WebElement fullName = driver.findElement(By.cssSelector("input#name"));
				fullName.sendKeys(signUPData.get(3));
				WebElement email = driver.findElement(By.cssSelector("input#email"));
				email.sendKeys(signUPData.get(4));
				WebElement password = driver.findElement(By.cssSelector("input#password"));
				password.sendKeys(signUPData.get(5));
				WebElement country = driver.findElement(By.cssSelector("select#country"));
				Select countryName = new Select(country);
				countryName.selectByValue(signUPData.get(6));
				WebElement phone = driver.findElement(By.cssSelector("input#mobile_number"));
				phone.sendKeys(signUPData.get(7));
				WebElement submit = driver.findElement(By.cssSelector("input#register_in"));
				submit.click();
				List<WebElement> checkConfirmation = driver.findElements(By.cssSelector("//h3[contains(text(),'Let’s get started')]"));
				if(checkConfirmation.size()>0)
				{
					System.out.println(checkConfirmation.get(0).getText());
					status = "pass";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		return status;
	}
	public ArrayList<String> checkLINKEDIN(ArrayList<String> dataFromExcel)
	{
		try
		{
			WebElement clickSignUP = driver.findElement(By.cssSelector("//a[contains(text(),'SIGNUP ')]"));
			clickSignUP.click();
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String windows : allWindows)
			{
				driver.switchTo().window(windows);
				if(driver.getCurrentUrl().contains("register"))
				{
					driver.switchTo().window(windows);
					js.executeScript("window.scrollBy(0,300)", "");
					List<WebElement> socialAccount = driver.findElements(By.cssSelector("a[type='submit']"));
					for(int i = 0; i < socialAccount.size(); i++)
					{
						if(socialAccount.get(i).getAttribute("onclick").contains("linkedin"))
						{
							socialAccount.get(i).click();
							break;
						}
					}
					if(driver.getCurrentUrl().contains("linkedin.com"))
					{
						driver.switchTo().window(windows);
						status.addAll(this.LinkedINProcess(dataFromExcel.get(1), dataFromExcel.get(2)));
						status.add(this.signUP(dataFromExcel));
						this.signUP(dataFromExcel);
						driver.get(OpenWebsite.setURL);
					}
					
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status.add("fail");
		}
		return status;
	}
	public ArrayList<String> checkMICROSOFT(ArrayList<String> dataFromExcel)
	{
		try
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
