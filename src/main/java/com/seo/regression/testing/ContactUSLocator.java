package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactUSLocator
{
	
	WebDriver driver;
	String url;
	
	public ContactUSLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	public ArrayList<String> clickContactUs()
	{
		ArrayList<String> statusOfFunction = new ArrayList<String>();
		try
		{
			WebElement contactUs = driver.findElement(By.cssSelector("div[class='Header_headerRight__RJT1X'] ul[class='list-unstyled navbar-nav nav Header_navLinks__aS6_P'] li:nth-child(2) a"));
			contactUs.click();
			statusOfFunction.add("success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfFunction;
	}
	
	public ArrayList<String> contactUsFunction(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> statusOfFunction = new ArrayList<String>();
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)", "");
			WebElement contactAboutLocator = driver.findElement(By.cssSelector("select[name='enquirytype']"));
			Select selectContact = new Select(contactAboutLocator);
			if(!dataFromExcel.get(1).equalsIgnoreCase("empty"))
			{
				selectContact.selectByVisibleText(dataFromExcel.get(1));
			}
			else
			{
				selectContact.selectByVisibleText("Business Enquiry");
			}
			WebElement enterFullname = driver.findElement(By.cssSelector("input[name='fullname']"));
			if(!dataFromExcel.get(2).equalsIgnoreCase("empty"))
			{
				enterFullname.sendKeys(dataFromExcel.get(2));
			}
			else
			{
				enterFullname.sendKeys("");
			}
			WebElement enterEmail = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12']:nth-child(4) input[name='email']"));
			if(!dataFromExcel.get(3).equalsIgnoreCase("empty"))
			{
				enterEmail.sendKeys(dataFromExcel.get(3));
			}
			else
			{
				enterEmail.sendKeys("");
			}
			
			WebElement countryLocator = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12']:nth-child(5) select[name='country']"));
			Select selectCountry = new Select(countryLocator);
			if(!dataFromExcel.get(4).equalsIgnoreCase("empty"))
			{
				selectCountry.selectByVisibleText(dataFromExcel.get(4));
			}
			else
			{
				selectCountry.selectByVisibleText("India");
			}
			
			WebElement mblLocator = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12']:nth-child(6) input[name='contactnumber']"));
			if(!dataFromExcel.get(5).equalsIgnoreCase("empty"))
			{
				mblLocator.sendKeys(dataFromExcel.get(5));
			}
			else
			{
				mblLocator.sendKeys("");
			}
			WebElement orgLocator = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12']:nth-child(7) input[name='organization']"));
			if(!dataFromExcel.get(6).equalsIgnoreCase("empty"))
			{
				orgLocator.sendKeys(dataFromExcel.get(6));
			}
			else
			{
				orgLocator.sendKeys("");
			}
			
			js.executeScript("window.scrollBy(0,900)", "");
			Thread.sleep(2000);
			WebElement jobTitleLocator = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12']:nth-child(8) input[name='jobtitle']"));
			if(!dataFromExcel.get(7).equalsIgnoreCase("empty"))
			{
				jobTitleLocator.sendKeys(dataFromExcel.get(7));
			}
			else
			{
				jobTitleLocator.sendKeys("");
			}
			
			List<WebElement> selectSkills = driver.findElements(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-md-6 ContactForm_CheckboxMain__HvP5j'] input[name='skills']"));
			if(!dataFromExcel.get(8).equalsIgnoreCase("empty"))
			{
				String getData[] = dataFromExcel.get(8).split("_");
				for(String skillFromExcel : getData)
				{
					for(int i = 0; i < selectSkills.size(); i++)
					{
						String skillFromBrowser = selectSkills.get(i).getAttribute("id");
						if(skillFromExcel.equalsIgnoreCase(skillFromBrowser))
						{
							System.out.println("skill to be select : "+skillFromBrowser);
							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
							wait.until(ExpectedConditions.elementToBeClickable(selectSkills.get(i)));
							js.executeScript("arguments[0].click()", selectSkills.get(i));
							if(!selectSkills.get(i).isEnabled())
							{
								selectSkills.get(i).click();
							}
						}
					}
				}
			}
			else
			{
				System.out.println("skill not selected");
				/*
				 * String getData[] =
				 * {"Artificial Intelligence","Blockchain","Cloud Computing"}; for(String
				 * skillFromExcel : getData) { for(int i = 0; i < selectSkills.size(); i++) {
				 * String skillFromBrowser = selectSkills.get(i).getAttribute("id");
				 * if(skillFromExcel.equalsIgnoreCase(skillFromBrowser)) {
				 * System.out.println("skill to be select : "+skillFromBrowser); WebDriverWait
				 * wait = new WebDriverWait(driver, Duration.ofSeconds(60));
				 * wait.until(ExpectedConditions.elementToBeClickable(selectSkills.get(i)));
				 * //selectSkills.get(i).click(); js.executeScript("arguments[0].click()",
				 * selectSkills.get(i)); Thread.sleep(1000);
				 * if(!selectSkills.get(i).isEnabled()) { selectSkills.get(i).click(); } } } }
				 */
			}
			js.executeScript("window.scrollBy(0,300)", "");
			WebElement subject = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12 col-md-12'] input[name='subject']"));
			if(!dataFromExcel.get(9).equalsIgnoreCase("empty"))
			{
				subject.sendKeys(dataFromExcel.get(9));
			}
			else
			{
				subject.sendKeys("");
			}
			WebElement message = driver.findElement(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12'] textarea[name='message']"));
			if(!dataFromExcel.get(9).equalsIgnoreCase("empty"))
			{
				message.sendKeys(dataFromExcel.get(10));
			}
			else
			{
				message.sendKeys("");
			}
			js.executeScript("window.scrollBy(0,300)", "");
			statusOfFunction.add("success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return statusOfFunction;
	}
	
	public ArrayList<String> termsAndService()
	{
		
		ArrayList<String> statusOfFunction = new ArrayList<String>();
		try
		{
			List<WebElement> links = driver.findElements(By.cssSelector("section[class='ContactForm_mainContent__zj_tL'] div[class*='col-12']:nth-child(13) a"));
			for(int i = 0; i < links.size(); i++)
			{
				//links.get(i).click();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", links.get(i));
				Thread.sleep(2000);
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String window : windows)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("privacy"))
					{
						driver.switchTo().window(window);
						System.out.println("privacy policy window");
						statusOfFunction.add("success");
						driver.close();
						driver.switchTo().window(parentWindow);
					}
					if(driver.getCurrentUrl().contains("tos"))
					{
						driver.switchTo().window(window);
						System.out.println("terms of service window");
						statusOfFunction.add("success");
						driver.close();
						driver.switchTo().window(parentWindow);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfFunction;
		
	}
	
	public ArrayList<String> validationMessage()
	{
		ArrayList<String> nameOfError = new ArrayList<String>();
		try
		{
			List<WebElement> getErrorMessage = driver.findElements(By.cssSelector("p[class='text-danger mb-0 mt-2']"));//p[class='ContactForm_Selectup__nCwVP ContactForm_Hint__2QivB'],
			for(int i = 0; i < getErrorMessage.size(); i++)
			{
				if(getErrorMessage.get(i).getText().contains("value")||getErrorMessage.get(i).getText().contains("choose"))
				{
					System.out.println("Please select value displayed for contacting us");
					nameOfError.add("value");
				}
				if(getErrorMessage.get(i).getText().contains("full name"))
				{
					System.out.println("Please enter your full name.");
					nameOfError.add("full name");
				}
				if(getErrorMessage.get(i).getText().contains("email"))
				{
					System.out.println("Please enter an email address.");
					nameOfError.add("email");
				}
				if(getErrorMessage.get(i).getText().contains("contact"))
				{
					System.out.println("Please enter contact number.");
					nameOfError.add("contact");
				}
				if(getErrorMessage.get(i).getText().contains("skills"))
				{
					System.out.println("Please select atleast 1 skills.");
					nameOfError.add("skills");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return nameOfError;
	}
	public void clickSubmit()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement clickSubmit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
		js.executeScript("arguments[0].click()", clickSubmit);
	}
	public ArrayList<String> checkInvalidFullname(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("Invalid fullname validation");
			 statusOfInvalidFullname.addAll(this.clickContactUs());
			 statusOfInvalidFullname.addAll(this.contactUsFunction(dataFromExcel));
			 statusOfInvalidFullname.addAll(this.termsAndService());
			this.clickSubmit();
			statusOfInvalidFullname.addAll(this.validationMessage());
			url = driver.getCurrentUrl();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkInvalidEmail(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("Invalid email validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			if(driver.getCurrentUrl().contains("data"))
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(2));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkInvalidMobile(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("Invalid mobile validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(1));
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutData(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("without Data validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutContactAbout(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("without ContactAbout validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutFullname(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{

			System.out.println("without Fullname validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutEmail(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("without email validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutMobile(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("without mobile validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkWithoutSkills(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("without skills validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
	public ArrayList<String> checkValidData(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> statusOfInvalidFullname = new ArrayList<String>();
		try
		{
			System.out.println("valid data validation");
			((JavascriptExecutor) driver).executeScript("window.open('"+url+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			/* statusOfInvalidFullname = */this.clickContactUs();
			/* statusOfInvalidFullname = */ this.contactUsFunction(dataFromExcel);
			/* statusOfInvalidFullname = */this.termsAndService();
			this.clickSubmit();
			WebElement verifySuccessMsg = driver.findElement(By.xpath("//h2[contains(text(),'Thank you for reaching out to us. We’ll be in touch shortly.')]"));
			if(verifySuccessMsg.isDisplayed())
			{
				System.out.println("success Msg : "+verifySuccessMsg.getText());
			}
			statusOfInvalidFullname = this.validationMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return statusOfInvalidFullname;
	}
}
