package com.seo.regression.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactInfoLocator
{
	String launchDashboard;
	WebDriver driver;
	OpenWebsite openWebsite;
	
	public ContactInfoLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	public String individualFunction(ArrayList<String> getValuesFromExcel) throws InterruptedException
	{
		String status = "pass";
		try
		{
			WebElement focusContact = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", focusContact);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			WebElement selectIndividual = driver.findElement(By.cssSelector("input#individual"));
			//js.executeScript("arguments[0].scrollIntoView(true);", selectIndividual);
			if(selectIndividual.isDisplayed())
			{
				if(!selectIndividual.isEnabled())
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					selectIndividual.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					System.out.println("Individual selected");
				}
			}
			WebElement checkIndividualContent = driver.findElement(By.cssSelector("div[class*='ContactForm_leftContent']"));
			checkIndividualContent.getText();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			if(checkIndividualContent.getText().contains("assist you."))
			{
				System.out.println("individual Information");
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			WebElement fullName = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer'] form div[class*='col-12'] input[name='fullname']"));
			if(!getValuesFromExcel.get(1).equalsIgnoreCase("empty"))
			{
				fullName.sendKeys(getValuesFromExcel.get(1));
			}
			else
			{
				fullName.sendKeys("");
			}
			WebElement email = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer'] form div[class*='col-12'] input[name='email']"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(email));
			if(!getValuesFromExcel.get(2).equalsIgnoreCase("empty"))
			{
				email.sendKeys(getValuesFromExcel.get(2));
			}
			else
			{
				email.sendKeys("");
			}
			WebElement country = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer'] form div[class*='col-12'] select[name='country']"));
			Select selectCountry = new Select(country);
			if(!getValuesFromExcel.get(3).equalsIgnoreCase("empty"))
			{
				selectCountry.selectByValue(getValuesFromExcel.get(3));
			}
			else
			{
				selectCountry.selectByValue(getValuesFromExcel.get(3));
			}
			WebElement mobileNumber = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer'] form div[class*='col-12'] input[name='contactnumber']"));
			if(!getValuesFromExcel.get(4).equalsIgnoreCase("empty"))
			{
				mobileNumber.sendKeys(getValuesFromExcel.get(4));
			}
			else
			{
				mobileNumber.sendKeys("");
			}
			WebElement currentStatus = driver.findElement(By.cssSelector("div[class*='ContactForm_formContainer'] form div[class*='col-12'] select[name='userpersona']"));
			Select selectCurrentStatus = new Select(currentStatus);
			if(!getValuesFromExcel.get(5).equalsIgnoreCase("empty"))
			{
				selectCurrentStatus.selectByVisibleText(getValuesFromExcel.get(5));
			}
			else
			{
				selectCurrentStatus.selectByVisibleText(getValuesFromExcel.get(5));
			}
			js.executeScript("window.scrollBy(0, 200)", "");
			List<WebElement> skills = driver.findElements(By.cssSelector("div[class*='ContactForm_SkillsMain'] > div[class*='ContactForm_CheckboxMain'] > input[type=checkbox]"));
			ArrayList<String> skillsFromExcel = new ArrayList<String>( Arrays.asList(getValuesFromExcel.get(6).split("_")) );
			for(int i = 0; i < skills.size(); i++)
			{
				WebElement skill = skills.get(i);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(90));
				wait1.until(ExpectedConditions.presenceOfElementLocated (By.cssSelector("div[class*='ContactForm_SkillsMain'] > div[class*='ContactForm_CheckboxMain']:nth-child("+(i+1)+")  > input[type=checkbox]")));
				String id = skill.getAttribute("id");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
				if(skillsFromExcel.contains(id))
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", skill);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					if(!skill.isSelected())
					{
						((JavascriptExecutor)driver).executeScript("arguments[0].click();", skill);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					}
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
	public String academicFunction(ArrayList<String> getValuesFromExcel) throws InterruptedException
	{
		String status = "pass";
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			WebElement focusContact = driver.findElement(By.cssSelector("section#go-to-contact div[class='ContactForm_formContainer__5ygOx'] div[class='ContactForm_radioSection__sNxR8 mt-3']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", focusContact);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			js.executeScript("window.scrollBy(0,-200)", "");
			WebElement selectAcademic = driver.findElement(By.cssSelector("input#academic"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			if(selectAcademic.isDisplayed())
			{
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", selectAcademic);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				//selectAcademic.click();
				if(!selectAcademic.isSelected())
				{
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", selectAcademic);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				}
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
				System.out.println("Academic selected");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			}
			WebElement checkAcademicContent = driver.findElement(By.cssSelector("div[class='ContactForm_leftContent__VlhaM']"));
			checkAcademicContent.getText();
			if(checkAcademicContent.getText().contains("academic institutions"))
			{
				System.out.println("Academic Information");
			}
			js.executeScript("window.scrollBy(0, 300)", "");
		//	WebElement focusContactInfo = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12']"));
			WebElement fullName = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='fullname']"));
			if(!getValuesFromExcel.get(1).equalsIgnoreCase("empty"))
			{
				fullName.sendKeys(getValuesFromExcel.get(1));
			}
			else
			{
				fullName.sendKeys("");
			}
			WebElement email = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='email']"));
			if(!getValuesFromExcel.get(2).equalsIgnoreCase("empty"))
			{
				email.sendKeys(getValuesFromExcel.get(2));
			}
			else
			{
				email.sendKeys("");
			}
			WebElement country = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] select[name='country']"));
			Select selectCountry = new Select(country);
			if(!getValuesFromExcel.get(3).equalsIgnoreCase("empty"))
			{
				selectCountry.selectByValue(getValuesFromExcel.get(3));
			}
			else
			{
				selectCountry.selectByValue(getValuesFromExcel.get(3));
			}
			WebElement mobileNumber = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='contactnumber']"));
			if(!getValuesFromExcel.get(4).equalsIgnoreCase("empty"))
			{
				mobileNumber.sendKeys(getValuesFromExcel.get(4));
			}
			else
			{
				mobileNumber.sendKeys("");
			}
			WebElement academicUniversity = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='university']"));
			if(!getValuesFromExcel.get(5).equalsIgnoreCase("empty"))
			{
				academicUniversity.sendKeys(getValuesFromExcel.get(5));
			}
			else
			{
				academicUniversity.sendKeys("");
			}
			WebElement jobTitle = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='jobtitle']"));
			if(!getValuesFromExcel.get(6).equalsIgnoreCase("empty"))
			{
				jobTitle.sendKeys(getValuesFromExcel.get(6));
			}
			else
			{
				jobTitle.sendKeys("");
			}
			WebElement message = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] textarea#message"));
			if(!getValuesFromExcel.get(7).equalsIgnoreCase("empty"))
			{
				message.sendKeys(getValuesFromExcel.get(7));
			}
			else
			{
				message.sendKeys("");
			}
		}
		catch(Exception e)
		{
			status="fail";
			e.printStackTrace();
		}
		return status;
	}
	public String businessFunction(ArrayList<String> getValuesFromExcel) throws InterruptedException
	{
		String status = "pass";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement focusContact = driver.findElement(By.cssSelector("section#go-to-contact div[class='ContactForm_formContainer__5ygOx']"));
			js.executeScript("arguments[0].scrollIntoView(true);", focusContact);
			WebElement selectBusiness = driver.findElement(By.cssSelector("input#corporate"));
			js.executeScript("arguments[0].scrollIntoView(true);", selectBusiness);
			js.executeScript("window.scrollBy(0, -200)", "");
			if(selectBusiness.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", selectBusiness);
				if(!selectBusiness.isEnabled())
				{
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", selectBusiness);
					System.out.println("business selected");
				}
			}
			WebElement checkBusinessContent = driver.findElement(By.cssSelector("div[class='ContactForm_leftContent__VlhaM']"));
			checkBusinessContent.getText();
			if(checkBusinessContent.getText().contains("businesses"))
			{
				System.out.println("Business Information");
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			WebElement focusContactInfo = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12']"));
			WebElement fullName = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='fullname']"));
			if(!getValuesFromExcel.get(1).equalsIgnoreCase("empty"))
			{
				fullName.sendKeys(getValuesFromExcel.get(1));
			}
			else
			{
				fullName.sendKeys("");
			}
			WebElement email = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='email']"));
			if(!getValuesFromExcel.get(2).equalsIgnoreCase("empty"))
			{
				email.sendKeys(getValuesFromExcel.get(2));
			}
			else
			{
				email.sendKeys("");
			}
			WebElement country = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] select[name='country']"));
			Select selectCountry = new Select(country);
			if(!getValuesFromExcel.get(3).equalsIgnoreCase("empty"))
			{
				selectCountry.selectByValue(getValuesFromExcel.get(3));
			}
			else
			{
				selectCountry.selectByValue("");
			}
			WebElement mobileNumber = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='contactnumber']"));
			if(!getValuesFromExcel.get(4).equalsIgnoreCase("empty"))
			{
				mobileNumber.sendKeys(getValuesFromExcel.get(4));
			}
			else
			{
				mobileNumber.sendKeys("");
			}
			WebElement organization = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='organization']"));
			if(!getValuesFromExcel.get(5).equalsIgnoreCase("empty"))
			{
				organization.sendKeys(getValuesFromExcel.get(5));
			}
			else
			{
				organization.sendKeys("");
			}
			js.executeScript("window.scrollBy(0, 500)", "");
			WebElement jobTitle = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] input[name='jobtitle']"));
			if(!getValuesFromExcel.get(6).equalsIgnoreCase("empty"))
			{
				jobTitle.sendKeys(getValuesFromExcel.get(6));
			}
			else
			{
				jobTitle.sendKeys("");
			}
			WebElement message = driver.findElement(By.cssSelector("div[class='ContactForm_formContainer__5ygOx'] form div[class*='col-12'] textarea#message"));
			if(!getValuesFromExcel.get(7).equalsIgnoreCase("empty"))
			{
				message.sendKeys(getValuesFromExcel.get(7));
			}
			else
			{
				message.sendKeys("");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = "fail";
		}
		return status;
	}
	public ArrayList<String> businessValidation()
	{
		ArrayList<String> statusOfFunctionality = new ArrayList<String>();
		try
		{
			int errorsSize = driver.findElements(By.cssSelector("p[class*='text-danger']")).size();
			if(errorsSize>0)
			{
				List<WebElement> getErrorText = driver.findElements(By.cssSelector("p[class='text-danger mb-0 mt-2']"));
				{
					for(int i = 0; i < getErrorText.size(); i++)
					{
						if(getErrorText.get(i).getText().contains("full name"))
						{
							statusOfFunctionality.add("full name");
						}
						if(getErrorText.get(i).getText().contains("email address"))
						{
							statusOfFunctionality.add("email address");
						}
						if(getErrorText.get(i).getText().contains("contact number"))
						{
							statusOfFunctionality.add("contact number");
						}
						if(getErrorText.get(i).getText().contains("job title"))
						{
							statusOfFunctionality.add("job title");
						}
						if(getErrorText.get(i).getText().contains("organization name"))
						{
							statusOfFunctionality.add("organization name");
						}
					}
				}
			}
			else
			{
				System.out.println("no error message");
				statusOfFunctionality.add("no msg");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusOfFunctionality.add("fail");
		}
		return statusOfFunctionality;
	}
	public ArrayList<String> individualValidation()
	{
		ArrayList<String> statusOfFunctionality = new ArrayList<String>();
		try
		{
			int errorsSize = driver.findElements(By.cssSelector("p[class*='text-danger']")).size();
			if(errorsSize>0)
			{
				List<WebElement> getErrorText = driver.findElements(By.cssSelector("p[class='text-danger mb-0 mt-2']"));
				{
					for(int i = 0; i < getErrorText.size(); i++)
					{
						if(getErrorText.get(i).getText().contains("full name"))
						{
							statusOfFunctionality.add("full name");
						}
						if(getErrorText.get(i).getText().contains("email address"))
						{
							statusOfFunctionality.add("email address");
						}
						if(getErrorText.get(i).getText().contains("contact number"))
						{
							statusOfFunctionality.add("contact number");
						}
						if(getErrorText.get(i).getText().contains("current status"))
						{
							statusOfFunctionality.add("current status");
						}
						if(getErrorText.get(i).getText().contains("Please select atleast 1 skills."))
						{
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
							statusOfFunctionality.add("skills");
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
						}
						}
					}
				}
				
			else
			{
				System.out.println("no error message");
				statusOfFunctionality.add("no msg");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusOfFunctionality.add("fail");
		}
		return statusOfFunctionality;
	}
	public ArrayList<String> acedamicValidation()
	{
		ArrayList<String> statusOfFunctionality = new ArrayList<String>();
		try
		{
			int errorsSize = driver.findElements(By.cssSelector("p[class*='text-danger']")).size();
			if(errorsSize>0)
			{
				List<WebElement> getErrorText = driver.findElements(By.cssSelector("p[class='text-danger mb-0 mt-2']"));
				{
				for(int i = 0; i < getErrorText.size(); i++)
				{
					if(getErrorText.get(i).getText().contains("full name"))
					{
						statusOfFunctionality.add("full name");
					}
					if(getErrorText.get(i).getText().contains("email address"))
					{
						statusOfFunctionality.add("email address");
					}
					if(getErrorText.get(i).getText().contains("contact number"))
					{
						statusOfFunctionality.add("contact number");
					}
					if(getErrorText.get(i).getText().contains("university Name"))
					{
						statusOfFunctionality.add("university Name");
					}
					if(getErrorText.get(i).getText().contains("job title"))
					{
						statusOfFunctionality.add("job title");
					}
				}
				}
			}
			else
			{
				System.out.println("no error message");
				statusOfFunctionality.add("no msg");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusOfFunctionality.add("fail");
		}
		return statusOfFunctionality;
	}
	
	public String agreePolicyTerms()
	{
		String status = "pass";
		try
		{
			List<WebElement> agreeDetails = driver.findElements(By.cssSelector("div[class='col-12 ContactForm_bySharing__7DL9G'] a"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 100)", "");
			for(int i = 0; i < agreeDetails.size(); i++)
			{
				String n = Keys.chord(Keys.CONTROL, Keys.ENTER);
				agreeDetails.get(i).sendKeys(n);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				String parentWindow = driver.getWindowHandle();
				Set<String> windows = driver.getWindowHandles();
				for(String window : windows)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("privacy"))
					{
						driver.switchTo().window(window);
						System.out.println("Privacy policy window");
						driver.close();
						driver.switchTo().window(parentWindow);
					}
					if(driver.getCurrentUrl().contains("tos"))
					{
						driver.switchTo().window(window);
						System.out.println("terms of service window");
						driver.close();
						driver.switchTo().window(parentWindow);
					}
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
	
	public ArrayList<String> checkIndividual_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> invalidNameStatus = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Individual_InvalidName process started");
			invalidNameStatus.add(this.individualFunction(dataFromExcel));
			invalidNameStatus.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,200)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMsg.addAll(this.individualValidation());
			if(invalidNameStatus.contains("fail"))
			{
				validationMsg.add("fail");
			}
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");
		}
		return validationMsg;
	}
	public ArrayList<String> checkIndividual_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Individual_InvalidEmail = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			driver.close();
			System.out.println("Individual_InvalidEmail process started");
			driver.switchTo().window(w.get(1));
			Individual_InvalidEmail.add(this.individualFunction(dataFromExcel));
			Individual_InvalidEmail.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMsg.addAll(this.individualValidation());
			if(Individual_InvalidEmail.contains("fail"))
			{
				validationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		validationMsg.add("fail");
		}
		return validationMsg;
	}
	public ArrayList<String> checkIndividual_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Individual_InvalidMobile = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Individual_InvalidMobile process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Individual_InvalidMobile.add(this.individualFunction(dataFromExcel));
			Individual_InvalidMobile.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMsg.addAll(this.individualValidation());
			if(Individual_InvalidMobile.contains("fail"))
			{
				validationMsg.add("fail");	
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		validationMsg.add("fail");
		}
		
		return validationMsg;
	}
	public ArrayList<String> checkIndividual_WithoutSkill(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Individual_WithoutSkill = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Individual_Without Skill process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Individual_WithoutSkill.add(this.individualFunction(dataFromExcel));
			Individual_WithoutSkill.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMSg.addAll(this.individualValidation());
			if(Individual_WithoutSkill.contains("fail"))
			{
				validationMSg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	
	public ArrayList<String> checkIndividual_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Individual_WithoutData = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Individual_Without Data process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Individual_WithoutData.add(this.individualFunction(dataFromExcel));
			Individual_WithoutData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
		//	js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMsg.addAll(this.individualValidation());
			if(Individual_WithoutData.contains("fail"))
			{
				validationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");
		}
		
		return validationMsg;
	}
	public ArrayList<String> checkIndividual_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> verifyValidationMsg = new ArrayList<String>();
		ArrayList<String> Individual_ValidData = new ArrayList<String>();
		try
		{
			System.out.println("Individual_ valid data process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			if(w.size()>2)
			{
				driver.switchTo().window(w.get(0));
				{
					driver.close();
				}
				driver.switchTo().window(w.get(1));
				driver.close();
				driver.switchTo().window(w.get(2));
			}
			else
			{
				driver.switchTo().window(w.get(0));
				{
					driver.close();
				}
				driver.switchTo().window(w.get(1));
			}
			Individual_ValidData.add(this.individualFunction(dataFromExcel));
			Individual_ValidData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				if(driver.findElements(By.cssSelector("div[class='col-12 undefined'] h2")).size()>0)
				{
					System.out.println("succes msg shown : "/* +checkSuccessMsgLocator.get(0).getText() */);
				}
			}
			
			verifyValidationMsg.addAll(this.individualValidation());
			if(Individual_ValidData.contains("fail"))
			{
				verifyValidationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			verifyValidationMsg.add("fail");
		}
		
		return verifyValidationMsg;
	}
	public ArrayList<String> checkAcademic_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_InvalidName = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_InvalidName process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			Academic_InvalidName.add(this.academicFunction(dataFromExcel));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			Academic_InvalidName.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_InvalidName.contains("fail"))
			{
				validationMSg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_InvalidEmail = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_InvalidEmail process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_InvalidEmail.add(this.academicFunction(dataFromExcel));
			Academic_InvalidEmail.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
		//	js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_InvalidEmail.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_InvalidMobile = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_InvalidMobile process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_InvalidMobile.add(this.academicFunction(dataFromExcel));
			Academic_InvalidMobile.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
		//	js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_InvalidMobile.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_InvalidInstitution(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_InvalidInstitution = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_Invalid institution process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_InvalidInstitution.add(this.academicFunction(dataFromExcel));
			Academic_InvalidInstitution.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_InvalidInstitution.contains("fail"))
			{
				validationMSg.add("fail");
			}
		//	driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_WithoutJob(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_WithoutJob = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_without job process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_WithoutJob.add(this.academicFunction(dataFromExcel));
			Academic_WithoutJob.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_WithoutJob.contains("fail"))
			{
				validationMSg.add("fail");	
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_WithoutMessage(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_WithoutMessage = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		
		try
		{
			System.out.println("Academic without message process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_WithoutMessage.add(this.academicFunction(dataFromExcel));
			Academic_WithoutMessage.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			//WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
			}
			if(driver.findElements(By.cssSelector("div[class='col-12 undefined'] h2")).size()>0)
			{
				System.out.println("succes msg shown : "/* +checkSuccessMsgLocator.get(0).getText() */);
			}
			
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_WithoutMessage.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Academic_WithoutData = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Academic_without data process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_WithoutData.add(this.academicFunction(dataFromExcel));
			Academic_WithoutData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMSg.addAll(this.acedamicValidation());
			if(Academic_WithoutData.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkAcademic_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> verifyValidationMsg = new ArrayList<String>();
		ArrayList<String> Academic_ValidData = new ArrayList<String>();
		try
		{
			System.out.println("Academic_valid data process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Academic_ValidData.add(this.academicFunction(dataFromExcel));
			Academic_ValidData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
			}
			if(driver.findElements(By.cssSelector("div[class='col-12 undefined'] h2")).size()>0)
			{
				System.out.println("succes msg shown : "/* +checkSuccessMsgLocator.get(0).getText() */);
			}
			verifyValidationMsg.addAll(this.acedamicValidation());
			if(Academic_ValidData.contains("fail"))
			{
				verifyValidationMsg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			verifyValidationMsg.add("fail");
		}
		
		return verifyValidationMsg;
	}
	public ArrayList<String> checkBusiness_InvalidName(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_InvalidName = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Business_InvalidName process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_InvalidName.add(this.businessFunction(dataFromExcel));
			Business_InvalidName.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMSg.addAll(this.businessValidation());
			if(Business_InvalidName.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkBusiness_InvalidEmail(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_InvalidEmail = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Business_InvalidEmail process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_InvalidEmail.add(this.businessFunction(dataFromExcel));
			Business_InvalidEmail.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMSg.addAll(this.businessValidation());
			if(Business_InvalidEmail.contains("fail"))
			{
				validationMSg.add("fail");
			}
		//	driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		return validationMSg;
	}
	public ArrayList<String> checkBusiness_InvalidMobile(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_InvalidMobile = new ArrayList<String>();
		ArrayList<String> validationMSg = new ArrayList<String>();
		try
		{
			System.out.println("Business_InvalidMobile process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_InvalidMobile.add(this.businessFunction(dataFromExcel));
			Business_InvalidMobile.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMSg.addAll(this.businessValidation());
			if(Business_InvalidMobile.contains("fail"))
			{
				validationMSg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMSg.add("fail");
		}
		
		return validationMSg;
	}
	public ArrayList<String> checkBusiness_WithoutOrganization(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_WithoutOrganization = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Business_Without Organization process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_WithoutOrganization.add(this.businessFunction(dataFromExcel));
			Business_WithoutOrganization.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);

			}
			validationMsg.addAll(this.businessValidation());
			if(Business_WithoutOrganization.contains("fail"))
			{
				validationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");			
		}
		
		return validationMsg;
	}
	
	public ArrayList<String> checkBusiness_WithoutJob(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_WithoutJob = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Business_without job process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_WithoutJob.add(this.businessFunction(dataFromExcel));
			Business_WithoutJob.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			js.executeScript("window.scrollBy(0,100)");
			validationMsg.addAll(this.businessValidation());
			//driver.switchTo().window(w.get(0));
			if(Business_WithoutJob.contains("fail"))
			{
				validationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");
		}
		
		return validationMsg;
	}
	public ArrayList<String> checkBusiness_WithoutMessage(ArrayList<String> dataFromExcel) throws InterruptedException
	{

		ArrayList<String> Business_WithoutMessage = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Business_without message process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			
			Business_WithoutMessage.add(this.businessFunction(dataFromExcel));
			Business_WithoutMessage.add(this.agreePolicyTerms());
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)");
			
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);

			}
			
			if(driver.findElements(By.cssSelector("div[class='col-12 undefined'] h2")).size()>0)
			{
				System.out.println("succes msg shown : "/* +checkSuccessMsgLocator.get(0).getText() */);
			}
		
			
			validationMsg.addAll(this.businessValidation());
			
			
			if(Business_WithoutMessage.contains("fail"))
			{
				validationMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");
		}
		
		return validationMsg;
	}
	public ArrayList<String> checkBusiness_WithoutData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_WithoutData = new ArrayList<String>();
		ArrayList<String> validationMsg = new ArrayList<String>();
		try
		{
			System.out.println("Business_Without data process started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_WithoutData.add(this.businessFunction(dataFromExcel));
			Business_WithoutData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			}
			validationMsg.addAll(this.businessValidation());
			if(Business_WithoutData.contains("fail"))
			{
				validationMsg.add("fail");
			}
			//driver.switchTo().window(w.get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			validationMsg.add("fail");
		}
		
		return validationMsg;
	}
	public ArrayList<String> checkBusiness_ValidData(ArrayList<String> dataFromExcel) throws InterruptedException
	{
		ArrayList<String> Business_ValidData = new ArrayList<String>();
		ArrayList<String> verifyErrorMsg = new ArrayList<String>();
		try
		{
			System.out.println("Business_valid data process is started");
			((JavascriptExecutor) driver).executeScript("window.open('"+OpenWebsite.openSite(driver)+"')");
			ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(w.get(0));
			{
				driver.close();
			}
			driver.switchTo().window(w.get(1));
			Business_ValidData.add(this.businessFunction(dataFromExcel));
			Business_ValidData.add(this.agreePolicyTerms());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)");
			WebElement submit = driver.findElement(By.cssSelector("div[class='col-12'] button[type='submit']"));
			if(submit.isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.elementToBeClickable(submit));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", submit);

			}
			if(driver.findElements(By.cssSelector("div[class='col-12 undefined'] h2")).size()>0)
			{
				System.out.println("succes msg shown : "/* +checkSuccessMsgLocator.get(0).getText() */);
			}
			verifyErrorMsg.addAll(this.businessValidation());
			if(Business_ValidData.contains("fail"))
			{
				verifyErrorMsg.add("fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			verifyErrorMsg.add("fail");
		}
		
		return verifyErrorMsg;
	}
}
