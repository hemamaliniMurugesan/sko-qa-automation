package com.seo.regression.testing;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.utility.TestUtil;

public class RegressionGenericLocator
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setLoginURL;
	String courseCode;
	String orderNumber = "";
	String amountWithTax;
	String amountWithOutTax;
	String checkPaymentProcess;
	public RegressionGenericLocator(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Doc\\chromedriver_113\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	
	public String setEnvironment(String host)
	{
		if(host.equalsIgnoreCase("prod-in"))
		{
			setHost = "https://in.skillup.online";
		}
		else if(host.equalsIgnoreCase("stage-in"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("qa-in"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("qa"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("stage"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("prod"))
		{
			setHost = "https://skillup.online";
		}
		return setHost;
	}
	
	public String getCourseCodeText(String code)
	{
		this.courseCode = code;
		String courseIDFromBrowser = "";
		String CourseCodeStatus = "false";
		HttpURLConnection huc = null;
		int respCode = 200;
		String addHosturl = this.setEnvironment(RegressionTesting.ENV_TO_USE)+code;
		try
		{
			huc = (HttpURLConnection)(new URL(addHosturl).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
			System.out.println(respCode);
			if(respCode > 200 && (!(respCode == 308)))
			{
				System.out.println("broken link");
				System.exit(0);
			}
			else
			{
				String getCourseID = null;
				System.out.println("un broken link");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
				driver.get(addHosturl);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
				List<WebElement> checkCourseCode = driver.findElements(By.cssSelector("div[class='CourseDescription_buttonsContent__qPhJg '] button[class*='enrollNowBtn']"));
				List<WebElement> checkCourseContent = driver.findElements(By.cssSelector("meta[property='og:title']"));
				if(checkCourseCode.size()>0)
				{
					getCourseID = checkCourseCode.get(0).getAttribute("href");
				}
				else
				{
					checkCourseContent.get(0).getText();
				}
				courseIDFromBrowser = getCourseID;
				System.out.println("course ID from Browser : "+courseIDFromBrowser);
				System.out.println("courseIDFrom Excel: "+code);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
				if(courseIDFromBrowser.contains(code))
				{
					CourseCodeStatus = "true";
				}
				else if(checkCourseContent.get(0).getText().contains(code))
				{
					CourseCodeStatus = "true";
				}
			}
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CourseCodeStatus;
	}
	
	public String navigateProcess()
	{
		String navigationStatus = "fail";
		try
		{
			JavascriptExecutor jse = (JavascriptExecutor) driver; //div[@class='d-flex CourseDescription_navigationBar__Zg6b3']//button[contains(text(),'Overview')]
			jse.executeScript("window.scrollBy(0,800)");
			List<WebElement> navigateFunctions = driver.findElements(By.cssSelector("div[class='d-flex FixedContentBar_navigationBar__GFCDl'] button"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[class='d-flex FixedContentBar_navigationBar__GFCDl'] button")));
			for(int i = 0; i < navigateFunctions.size(); i++)
			{
				if(i == 0)
				{
					WebElement overview = driver.findElement(By.cssSelector("div[class='d-flex FixedContentBar_navigationBar__GFCDl'] button:nth-child(1)"));
					wait.until(ExpectedConditions.elementToBeClickable(overview));
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", overview);
					System.out.println("Overview is displayed");
					navigationStatus = "pass";
				}
				driver.navigate().refresh();
				Thread.sleep(1000);
				if(i == 1)
				{
					WebElement whySkillupNavigation = driver.findElement(By.cssSelector("div[class='d-flex FixedContentBar_navigationBar__GFCDl'] button:nth-child(2)"));
					wait.until(ExpectedConditions.elementToBeClickable(whySkillupNavigation));
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", whySkillupNavigation);
					System.out.println("WhySkillUpOnline? content is displayed");
					navigationStatus = "pass";
				}
				if(i == 2)
				{
					WebElement FAQNavigation = driver.findElement(By.cssSelector("div[class='d-flex FixedContentBar_navigationBar__GFCDl'] button:nth-child(3)"));
					wait.until(ExpectedConditions.elementToBeClickable(FAQNavigation));
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", FAQNavigation);
					System.out.println("FAQ is displayed");
					navigationStatus = "pass";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return navigationStatus;
	}
	
	public ArrayList<String> freeConsultationProcess(ArrayList<String> getFreeConsultation)
	{
		ArrayList<String> freeConsultationStatus = new ArrayList<String>();
		try
		{
				LinkedHashMap<String, String> kv = new LinkedHashMap<String, String>();
				String key = null, value = null;
				String data = getFreeConsultation.get(1);
				String[] separateData = data.split("-split-");
				for(int i = 0; i < separateData.length; i++)
				{
					System.out.println("data stored in array : "+separateData[i]);
					String[] keyValue = separateData[i].split("=");
					for(int j = 0; j < keyValue.length; j++)
					{

						if(j == 0)
						{
							key = keyValue[j];
						}
						else if(j == 1)
						{
							value = keyValue[j];
						}
						kv.put(key, value);
					}
				}
				System.out.println(kv);
				System.out.println(kv.get("name"));
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,400)");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				WebElement clickbutton = driver.findElement(By.cssSelector("div[class='CourseDescription_buttonsContent__qPhJg '] button[class='CourseDescription_getFreeConsultationBtn__KkZ46']"));
				if(clickbutton.isDisplayed())
				{
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
					wait.until(ExpectedConditions.elementToBeClickable(clickbutton));
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", clickbutton);
					WebElement fullName = driver.findElement(By.cssSelector("input[name='fullname']"));
					fullName.clear();
					fullName.sendKeys(kv.get("name"));
					WebElement email = driver.findElement(By.cssSelector("div[class='GetConsultationForm_formContent__Q7Cwa'] input[name='email']"));
					email.clear();
					email.sendKeys(kv.get("mail"));
					Select select = new Select(driver.findElement(By.cssSelector("select[name='country']")));
					select.selectByVisibleText(kv.get("country"));
					WebElement mbl = driver.findElement(By.cssSelector("div[class='GetConsultationForm_formContent__Q7Cwa'] input[name='contactnumber']"));
					mbl.clear();
					mbl.sendKeys(kv.get("mbl"));
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].scrollIntoView(true);", mbl);
					Select currentStatus = new Select(driver.findElement(By.cssSelector("select[name*='user']")));
					currentStatus.selectByVisibleText(kv.get("status"));
					WebElement message = driver.findElement(By.cssSelector("#message"));
					message.clear();
					message.sendKeys(kv.get("msg"));
					List<WebElement> shareConsultationForm = driver.findElements(By.cssSelector("div[class='row gy-3'] div[class='col-12 GetConsultationForm_bySharing__ztrLr'] a"));
					for(int i = 0; i < shareConsultationForm.size(); i++)
					{
						WebElement clickConsultation = shareConsultationForm.get(i);
						Thread.sleep(300);
						if(clickConsultation.isDisplayed())
						{
							clickConsultation.click();
							String parentwindow = driver.getWindowHandle();
							Set<String> allWindows = driver.getWindowHandles();
							for(String handle : allWindows)
							{
								if(!handle.equals(parentwindow))
								{
									driver.switchTo().window(handle);
									System.out.println(driver.getCurrentUrl());
									if(driver.getCurrentUrl().equalsIgnoreCase(""+setHost+"/privacy/"))
									{
										driver.switchTo().window(handle);
										System.out.println("privacy policy window");
										driver.close();
										driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
										driver.switchTo().window(parentwindow);
									}
									else if(driver.getCurrentUrl().equals(""+setHost+"/tos/"))
									{
										driver.switchTo().window(handle);
										System.out.println("terms of service");
										driver.close();
										driver.switchTo().window(parentwindow);
									}
								}
							}
						}
					}
					WebElement submit = driver.findElement(By.cssSelector("div[class*='col-12 GetConsultationForm_ButtonSection'] button[type='submit']"));
					submit.click();
					try
					{
						List<WebElement> checkValidation = driver.findElements(By.cssSelector("p[class='text-danger mb-0 mt-2']"));
						if(checkValidation.size() > 0)
						{
							freeConsultationStatus.add("Fail");
							WebElement closePopUp = driver.findElement(By.xpath("(//button[@class='btn-close shadow-none'])[2]"));
							if(closePopUp.isDisplayed())
							{
								closePopUp.click();
							}
						}
						else
						{
							freeConsultationStatus.add("pass");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return freeConsultationStatus;
	}
	
	public String checkOutRazorpay(String amountFromExcel)
	{
		String checkRazorpay = "fail";
		String amountWithTax[] = amountFromExcel.split("=");
		String amt = amountWithTax[1].replaceAll("\\s", "");
		String value = amt.toString();
		int excelValue = Integer.parseInt(value);
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(500));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("/basket/"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(500));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					WebElement checkoutAmount = driver.findElement(By.cssSelector("div[class='selected-plan'] span"));
					String getCheckoutAmount = checkoutAmount.getText();
					System.out.println("checkout Razorpay amount from Browser :"+getCheckoutAmount);
					//WebElement getAmountWithGST = driver.findElement(By.cssSelector("div[class='selected-plan'] span[class='h5-regular24 Accent_Red02-text']"));
					//String amountWithGSTFromBrowser = getAmountWithGST.getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "");
					String val[] = getCheckoutAmount.split("\\.");
					String editVal = val[0].replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").toString();
					
					int browserValue = Integer.parseInt(editVal);
					
					System.out.println("amountWithGST From Browser in Razorpay : " + browserValue);
					if(browserValue == excelValue)
					{
						System.out.println("both Razorpay amount and excel amount are same");
						checkRazorpay = "pass";
					}
					else
					{
						System.out.println(" amount with GST from Excel : "+amountWithTax[1]);
						checkRazorpay = "fail";
					}
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("window.scrollBy(0,200)");
					/*
					 * WebElement enterPromo =
					 * driver.findElement(By.cssSelector("a[id*='ui-collapse']"));
					 * enterPromo.click();
					 */
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(200));
					WebElement completePayment = driver.findElement(By.xpath("//button[@id='razorpay' or @id='stripecheckout']"));
					completePayment.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(150));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(700));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkRazorpay = "fail";
		}
		return checkRazorpay;
	}
	
	public String login(String loginCredential)
	{
		String checkLoginProcess = "false";
		try
		{
			String[] getData = loginCredential.split("-split-");
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,200)");
			WebElement clickLoginIcon = driver.findElement(By.cssSelector("li#signinlink"));
			if(clickLoginIcon.isDisplayed())
			{
				clickLoginIcon.click();
				System.out.println("After clicking login icon : "+driver.getCurrentUrl());
				WebElement enterEmail = driver.findElement(By.cssSelector("input#email"));
				enterEmail.sendKeys(getData[0]);
				WebElement enterPassword = driver.findElement(By.cssSelector("input#password"));
				enterPassword.sendKeys(getData[1]);
				WebElement clickLogin = driver.findElement(By.cssSelector("input#login_in"));
				if(clickLogin.isDisplayed())
				{
					clickLogin.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
					checkLoginProcess = "true";
				}
				else
				{
					checkLoginProcess = "false";
				}
			}
			else
			{
				checkLoginProcess = "false";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkLoginProcess = "false";
		}
		return checkLoginProcess;
	}
	
	public String netBanking(String bankName)
	{
		try
		{
			WebElement iFrame = driver.findElement(By.cssSelector(".razorpay-checkout-frame"));
			driver.switchTo().frame(iFrame);
			List<WebElement> buttons = driver.findElements(By.cssSelector(".border-list button"));
			for(WebElement button: buttons)
			{
				WebElement label = button.findElement(By.cssSelector("div.title > div"));
				System.out.println(label.getText());
				if(label.getText().equalsIgnoreCase("Pay using Netbanking"))
				{
					label.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					List<WebElement> listOfNetBanking = driver.findElements(By.cssSelector("div#netb-banks label[for*='bank-radio']"));
					for(int j = 0; j < listOfNetBanking.size(); j++)
					{
						System.out.println(listOfNetBanking.get(j).getText());
						if(listOfNetBanking.get(j).getText().equalsIgnoreCase(bankName))
						{
							System.out.println("netbanking name is : "+listOfNetBanking.get(j).getText());
							WebElement clickBank = listOfNetBanking.get(j);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
						    wait.until(ExpectedConditions.elementToBeClickable(clickBank)).click(); 
						    WebElement clickPayNow = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						    clickPayNow.click();
						    System.out.println("netbanking paid ");
						    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
							driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(170));
						    checkPaymentProcess = "pass";
						    break;
						}
					}
				}
			}
			if(!checkPaymentProcess.equalsIgnoreCase("pass"))
			{
				checkPaymentProcess = this.diffBank(bankName);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
			System.out.println("netbanking is fail");
		}
		
		return checkPaymentProcess;
	}
	public String diffBank(String bank)
	{
		try
		{
			WebElement selectBank = driver.findElement(By.cssSelector("button#bank-select"));
			selectBank.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(170));
			List<WebElement> enterBank = driver.findElements(By.cssSelector("div[id='netbanking_bank_select_search_results'] div[class='list svelte-15q0kle'] img[alt]"));
			for(int i = 0; i < enterBank.size(); i++)
			{
				if(enterBank.get(i).getAttribute("alt").equalsIgnoreCase(bank))
				{
					System.out.println("bank name is : "+enterBank.get(i).getAttribute("alt"));
					enterBank.get(i).click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(370));
					WebElement clickPayNow = driver.findElement(By.cssSelector("button[id*='redesign']"));
					clickPayNow.click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(370));
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
			System.out.println("problem in diffBank selection in the netbanking ");
		}
		return bank;
	}
	
	public String moreBank(String bank)
	{
		try
		{
			WebElement clickMoreBank = driver.findElement(By.cssSelector("div[class='emi-bank-item'] div[class*='more-bank-icon']"));
			clickMoreBank.click();
			List<WebElement> bankName = driver.findElements(By.cssSelector("div[id='emi_bank_select_search_results'] div[id*='emi_bank'] div[class*='search-bank-name']"));
			for(int i = 0; i < bankName.size(); i++)
			{
				if(bankName.get(i).getText().contains(bank))
				{
					System.out.println("bank name is : "+bankName.get(i).getText());
					bankName.get(i).click();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return bank;
	}
	public String card()
	{
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			WebElement iFrame = driver.findElement(By.cssSelector(".razorpay-checkout-frame"));
			driver.switchTo().frame(iFrame);
			List<WebElement> buttons = driver.findElements(By.cssSelector(".border-list button"));
			for(WebElement button: buttons)
			{
				WebElement label = button.findElement(By.cssSelector("div.title > div"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				System.out.println(label.getText());
				if(label.getText().equalsIgnoreCase("Pay using Card"))
				{
					System.out.println("card name is : "+label.getText());
					label.click();
					if(driver.findElement(By.cssSelector("button#otp-sec")).isDisplayed())
					{
						WebElement skipOTP = driver.findElement(By.cssSelector("button#otp-sec"));
						skipOTP.click();
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
						cardNumber.sendKeys("5267 3181 8797 5449");
						WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
						cardExpiry.sendKeys("12/22");
						WebElement cardName = driver.findElement(By.cssSelector("input#card_name"));
						cardName.sendKeys("testing");
						WebElement cvv = driver.findElement(By.cssSelector("input#card_cvv"));
						cvv.sendKeys("234");
						WebElement payNowButton = driver.findElement(By.cssSelector("form#form button[id='redesign-v15-cta']"));
						payNowButton.click();driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(270));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(370));
						System.out.println("pay now button clicked");
						WebElement payWOSavingCard = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						payWOSavingCard.click();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(470));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						checkPaymentProcess = "pass";
					}
					else
					{
						WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
						cardNumber.sendKeys("5267 3181 8797 5449");
						WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
						cardExpiry.sendKeys("12/23");
						WebElement cardName = driver.findElement(By.cssSelector("input#card_name"));
						cardName.sendKeys("testing");
						WebElement cvv = driver.findElement(By.cssSelector("input#card_cvv"));
						cvv.sendKeys("234");
						WebElement payNowButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						payNowButton.click();
						System.out.println("Pay now button is clicked");
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(470));
						checkPaymentProcess = "pass";
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
			System.out.println("card payment is fail");
		}
		
		return checkPaymentProcess;
	}
	
	public String indiaOrderDetails(String priceWOGSTFromExcel)
	{
		String checkOrderDetails = "fail";
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(570));
			String parentWindow = driver.getWindowHandle();
			Set<String> nextWindow = driver.getWindowHandles();
			for(String window : nextWindow)
			{
				driver.switchTo().window(window);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				if(driver.getCurrentUrl().contains("mocksharp/paymen"))
				{
					driver.switchTo().window(window);
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(370));
					driver.findElement(By.cssSelector("button[class='success']")).click();
					System.out.println("success button clicked");
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(370));
					checkOrderDetails = "pass";
					break;
				}
				else if(driver.getCurrentUrl().contains("data"))
				{
					driver.close();
				}
			}
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(500));
			//driver.switchTo().window(parentWindow);
			String parentWindow1 = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String win : allWindows)
			{
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(570));
				driver.switchTo().window(win);
				if(driver.getCurrentUrl().contains("receipt"))
				{
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(570));
					driver.switchTo().window(win);
					System.out.println(driver.getCurrentUrl());
					System.out.println("checkout page");
					WebElement getOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td"));
					System.out.println("order details : "+getOrderDetails.getText());
					String amountOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td[class='spacing-pl8 spacing-pr20 spacing-pl20  spacing-pt8 table_lastrow last_prize table_border_bottom text_table']")).getText();
					System.out.println("amount order details : "+amountOrderDetails);
					WebElement getOrderNumber = driver.findElement(By.cssSelector("div[class*='order_info']:nth-child(2) div:nth-child(3) h4"));
					orderNumber = getOrderNumber.getText();
					System.out.println("OrderNumber: "+orderNumber);
					if(priceWOGSTFromExcel.equalsIgnoreCase(amountOrderDetails))
					{
						checkOrderDetails = "pass";
						System.out.println("both price without GST from excel and ordered amount values are same : "+amountOrderDetails);
					}
					else
					{
						checkOrderDetails = "fail";
					}	
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkOrderDetails = "fail";
		}
		
		return checkOrderDetails;
	}
	
	public void USOrderDetails()
	{
		try
		{
			String currentWindow = driver.getWindowHandle();
			Set<String> windows = driver.getWindowHandles();
			for(String win : windows)
			{
				driver.switchTo().window(win);
				System.out.println(driver.getCurrentUrl());
				System.out.println("checkout page");
				WebElement clickDashBoard = driver.findElement(By.xpath("//a[contains(text(),'Continue to your Dashboard')]"));
				clickDashBoard.click();
				WebElement getOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td"));
				System.out.println(getOrderDetails.getText());
				String amountOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td[class='spacing-pl8 spacing-pr20 spacing-pl20  spacing-pt8 table_lastrow last_prize table_border_bottom text_table']")).getText();
				System.out.println(amountOrderDetails);
				if(amountWithTax.equalsIgnoreCase(amountOrderDetails))
				{
					System.out.println("both are same amount ");
				}
				else
				{
					errorCells.add(5);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public String wallet()
	{
		try
		{
			WebElement iFrame = driver.findElement(By.cssSelector(".razorpay-checkout-frame"));
			driver.switchTo().frame(iFrame);
			List<WebElement> buttons = driver.findElements(By.cssSelector(".border-list button"));
			for(WebElement button: buttons)
			{
				WebElement label = button.findElement(By.cssSelector("div.title > div"));
				System.out.println(label.getText());
				if(label.getText().equalsIgnoreCase("Pay using Wallet"))
				{
					label.click();
					WebElement phonePe = driver.findElement(By.cssSelector("div#wallet-radio-phonepe"));
					phonePe.click();
					WebElement payNowButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					payNowButton.click();
					checkPaymentProcess = "true";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "false";
		}
		return checkPaymentProcess;
	}
	
	public String emi(String bankName)
	{
		String getData[] = bankName.split("_");
		try
		{
			WebElement iFrame = driver.findElement(By.cssSelector(".razorpay-checkout-frame"));
			driver.switchTo().frame(iFrame);
			List<WebElement> buttons = driver.findElements(By.cssSelector(".border-list button"));
			for(WebElement button: buttons)
			{
				WebElement label = button.findElement(By.cssSelector("div.title > div"));
				System.out.println(label.getText());
				if(label.getText().equalsIgnoreCase("Pay using EMI"))
				{
					label.click();
					List<WebElement> clickEMIBank = driver.findElements(By.cssSelector("div[id*='bank-item']"));
					for(int i = 0; i < clickEMIBank.size(); i++)
					{
						if(clickEMIBank.get(i).getText().contains(getData[0]))
						{
							clickEMIBank.get(i).click();
							WebElement clickContinue = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
							clickContinue.click();
							checkPaymentProcess = "pass";
							break;
						}
					}
					if(checkPaymentProcess != "pass")
					{
						this.moreBank(getData[0]);
					}
					List<WebElement> selectEMIPlan = driver.findElements(By.cssSelector("div[class='plan-amount svelte-1ooipi6']"));
					for(int j = 0; j < selectEMIPlan.size(); j++)
					{
							selectEMIPlan.get(j).click();
							break;
					}
					
					WebElement selectPlanButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					selectPlanButton.click();
					WebElement enterCardNumber = driver.findElement(By.cssSelector("div#container div[tab='emi'] div[id='root'] div[id='add-card-container'] input#card_number"));
					enterCardNumber.sendKeys("5241 8100 0000 0000");
					WebElement enterExpiry = driver.findElement(By.cssSelector("div#container div[tab='emi'] div[id='root'] div[id='add-card-container'] input#card_expiry"));
					enterExpiry.sendKeys("12/23");
					WebElement enterCardName = driver.findElement(By.cssSelector("div#container div[tab='emi'] div[id='root'] div[id='add-card-container'] input#card_name"));
					enterCardName.sendKeys("hemamalini-murugesan");
					WebElement enterCVV = driver.findElement(By.cssSelector("div#container div[tab='emi'] div[id='root'] div[id='add-card-container'] input#card_cvv"));
					enterCVV.sendKeys("234");
					WebElement payViaEMI = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					payViaEMI.click();
					WebElement clickContinue = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					clickContinue.click();
					checkPaymentProcess = "pass";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
			System.out.println("problem in emi process");
		}
		return checkPaymentProcess;
	}
	
	public String upi()
	{
		try
		{
			WebElement iFrame = driver.findElement(By.cssSelector(".razorpay-checkout-frame"));
			driver.switchTo().frame(iFrame);
			List<WebElement> buttons = driver.findElements(By.cssSelector(".border-list button"));
			for(WebElement button: buttons)
			{
				WebElement label = button.findElement(By.cssSelector("div.title > div"));
				System.out.println(label.getText());
				if(label.getText().equalsIgnoreCase("Pay using UPI"))
				{
					label.click();
					WebElement clickUPITab = driver.findElement(By.cssSelector("div#new-vpa-field-upi"));
					clickUPITab.click();
					WebElement upiID = driver.findElement(By.cssSelector("input#vpa-upi"));
					upiID.sendKeys("success@razorpay");
					WebElement payNow = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					payNow.click();
					checkPaymentProcess = "pass";
					try 
					{
						Thread.sleep(500);
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
						checkPaymentProcess = "fail";
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
			System.out.println("UPI payment fail");
		}
		return checkPaymentProcess;
	}
	
	public String indiaPaymentProcess(String paymentModeFromExcel)
	{
		String getData[] = paymentModeFromExcel.split("_");
		try
		{
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String window : allWindows)
			{
				driver.switchTo().window(window);
				if(driver.getCurrentUrl().contains("basket"))
				{
					driver.switchTo().window(window);
					switch(getData[0])
					{
					case "card":
						card();
						break;
					case "netBanking":
						netBanking(getData[1]);
						break;
					case "wallet":
						wallet();
						break;
					case "upi":
						upi();
						break;
					case "emi":
						emi(getData[1]);
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPaymentProcess = "fail";
		}
		return checkPaymentProcess;
	}
	ArrayList<Integer> errorCells;
	boolean checkEnrollStatus;
	public String loginProcess(String getData)
	{
		String status = "fail";
		try
		{
			String[] splitData = getData.split("-split-",2);
			String parentWindow1 = driver.getWindowHandle();
			Set<String> allWindows1 = driver.getWindowHandles();
			for(String window1 : allWindows1)
			{
				driver.switchTo().window(window1);
				if(driver.getCurrentUrl().contains("login?"))
				{
					driver.switchTo().window(window1);
					status = "pass";
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
					WebElement email = driver.findElement(By.cssSelector("input#email"));
					email.sendKeys(splitData[0]);
					WebElement pwd = driver.findElement(By.cssSelector("input#password"));
					pwd.sendKeys(splitData[1]);
					WebElement loginButton = driver.findElement(By.cssSelector("input#login_in"));
					loginButton.click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(400));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
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
	public String choosePlan(String plan)
	{
		String checkPlanStatus = "fail";
		try
		{
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
			String parentWindow2 = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			for(String windows : allWindows)
			{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,300)");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
				if(driver.getCurrentUrl().contains("choose-subscription"))
				{
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					List<WebElement> listOfPlan = driver.findElements(By.cssSelector("div[id='custom-plans1'] div[class*='owl-item']"));// div[class=\"bttn\"] a
					for(int i = 0; i < listOfPlan.size(); i++)
					{
						WebElement selectPlan = listOfPlan.get(i).findElement(By.cssSelector(" div[class*='plan_heading']"));
						String getPlanText = selectPlan.getText();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
						if(getPlanText.contains(plan))
						{
							System.out.println("Plan is : "+getPlanText);
							WebElement clickPlan = listOfPlan.get(i).findElement(By.cssSelector(" div[class='bttn'] a"));
							if(clickPlan.isDisplayed())
							{
								js.executeScript("arguments[0].click()", clickPlan);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
								driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(800));
								checkPlanStatus = "pass";
								break;
							}
							else
							{
								if(listOfPlan.get(i).findElement(By.cssSelector(" button[disabled]")).isDisplayed())
								{
									System.out.println("button is disabled");
									checkPlanStatus = "fail";
								}
							}
							
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return checkPlanStatus;
	}	
	public ArrayList<String> enroll(ArrayList<String> enrollDataFromExcel)
	{

		ArrayList<String> statusOfProcess = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		try
		{
			
			js.executeScript("window.scrollBy(0,200)");
			String getCurrentURL = driver.getCurrentUrl();
			if(getCurrentURL.contains("in"))//india site
			{
				WebElement checkEnrollButton = driver.findElement(By.cssSelector("button[class*='CourseDescription_enrollNowBtn']"));
				if(checkEnrollButton.isDisplayed())
				{
					if(checkEnrollButton.getText().equalsIgnoreCase("Enroll Now"))
					{
						System.out.println("Enroll Button is displayed");
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
						wait.until(ExpectedConditions.elementToBeClickable(checkEnrollButton));
						js.executeScript("arguments[0].click()", checkEnrollButton);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
					}
				}
				String parentWindow = driver.getWindowHandle();
				Set<String> allWindows = driver.getWindowHandles();
				for(String window : allWindows)
				{
					driver.switchTo().window(window);
					if(driver.getCurrentUrl().contains("register?"))
					{
						driver.switchTo().window(window);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						WebElement clickLoginIcon = driver.findElement(By.cssSelector("li#signinlink"));
						clickLoginIcon.click();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(70));
						break;
					}
				}
				statusOfProcess.add(this.loginProcess(enrollDataFromExcel.get(1)));
				statusOfProcess.add(this.choosePlan(enrollDataFromExcel.get(2)));
				statusOfProcess.add(this.checkOutRazorpay(enrollDataFromExcel.get(3)));
				statusOfProcess.add(this.indiaPaymentProcess(enrollDataFromExcel.get(4)));
				statusOfProcess.add(this.indiaOrderDetails(enrollDataFromExcel.get(5)));
			}
			else
			{
				System.out.println("US Enroll Process");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			statusOfProcess.add("fail");
		}
		return statusOfProcess;
	}
			
	
	
	
	public ArrayList<String> skillupOnlineLocator(ArrayList<String> skillupOnlineFromExcel)
	{
		String checkSkillupOnlineText = "";
		ArrayList<String> status = new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,1300)");
		try
		{
			for(int i = 1; i < skillupOnlineFromExcel.size(); i++)
			{
					if(i == 1)
					{
						WebElement navigationButton = driver.findElement(By.cssSelector("div#why-skill-up h2[class*='_titleText']"));
						String question = navigationButton.getText().replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(question.equals(skillupOnlineFromExcel.get(i).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
						{
							System.out.println("Why skill up from Browser : "+question);
							System.out.println("question is same");
							checkSkillupOnlineText = "pass";
						}
						else
						{
							System.out.println("question is not same");
							checkSkillupOnlineText = "fail";
						}
						status.add(checkSkillupOnlineText);
					}
					else if(i == 2)
					{
						WebElement answer = driver.findElement(By.cssSelector("section[class='WhyLearnSkillUp_mainSection__pNbU3'] div[class='WhyLearnSkillUp_mainContent__x3c7x']:not([class='WhyLearnSkillUp_titleText__N8j59'])"));
						js.executeScript("window.scrollBy(0,100)");
						String removeText = "Why Learn with SkillUp Online?";
						String answerText = answer.getText().replaceAll("[^a-zA-Z0-9]", " ").replaceAll(removeText, "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println(answerText);
						if(answerText.equals(skillupOnlineFromExcel.get(i).replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
						{
							System.out.println("answer is same");
							checkSkillupOnlineText = "pass";
						}
						else
						{
							System.out.println("answer is not same");
							checkSkillupOnlineText = "fail";
						}
						status.add(checkSkillupOnlineText);
					}
				}
				List<WebElement> listOfImages = driver.findElements(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] img[alt='icon']"));
				if(listOfImages.size() == 4)
				{
					System.out.println("image icons are available");
				}
				else
				{
					System.out.println("image icon is not available");
				}
				WebElement fontColor = driver.findElement(By.cssSelector("div[class='WhyLearnSkillUp_profitPointsSection__YTA82'] h2"));
				String color = fontColor.getCssValue("color");
				String str = Color.fromString(color).asHex();
				if(str.equals("#8f191f"))
				{
					System.out.println("Dark red color");
				}
				else
				{
					System.out.println("invalid color");
				}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	public String shareLocator(String shareFromExcel)
	{
		String checkShareProcess = "";
		try
		{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,-2000)", "");
				WebElement clickShareLink = driver.findElement(By.cssSelector("button[class='CourseDescription_shareBtn__0vLDp ']"));
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
				wait.until(ExpectedConditions.elementToBeClickable(clickShareLink));
				if(clickShareLink.isDisplayed())
				{
					Thread.sleep(2000);
					clickShareLink.click();
				}
				Thread.sleep(2000);
				WebElement copyLink = driver.findElement(By.cssSelector("button[class='btn shadow-none ShareCourse_copyButton___ztrR']"));
				String getLinkText = copyLink.getText();
				copyLink.click();
				List<WebElement> share = driver.findElements(By.cssSelector("a[class='ShareCourse_socialIcon__f7x_3']"));
				for(int i = 0; i < share.size(); i++)
				{
					if(share.get(i).getAttribute("href").contains(shareFromExcel))
					{
						share.get(i).click();
						String parentWindow = driver.getWindowHandle();
						Set<String> allWindow = driver.getWindowHandles();
						Iterator<String> itr = allWindow.iterator();
						while(itr.hasNext())
						{
							String childWindow = itr.next();
							if(!parentWindow.equalsIgnoreCase(childWindow))
							{
								driver.switchTo().window(childWindow);
								if(driver.getCurrentUrl().contains("twitter"))
								{
									driver.switchTo().window(childWindow);
									System.out.println("twitter screen");
									driver.close();
									driver.switchTo().window(parentWindow);
								}
								if(driver.getCurrentUrl().equalsIgnoreCase("facebook"))
								{
									driver.switchTo().window(childWindow);
									System.out.println("facebook screen");
									driver.close();
									driver.switchTo().window(parentWindow);
								}
								if(driver.getCurrentUrl().equalsIgnoreCase("linked"))
								{
									driver.switchTo().window(childWindow);
									System.out.println("linkedIn Screen");
									driver.close();
									driver.switchTo().window(parentWindow);
								}
							}
						}
					}
				}
				driver.findElement(By.cssSelector("div#share-course button[class='btn-close shadow-none']")).click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkShareProcess;
	}
	
	public String downloadLocator(String downloadFromExcel)
	{
		String checkDownloadProcess = "";
		try
		{
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkDownloadProcess;
	}
	
	public ArrayList<String> programLocator(ArrayList<String> programFromExcel)
	{
		ArrayList<String> checkProgramProcess = new ArrayList<String>();
		try
		{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				//js.executeScript("window.scrollBy(0,-150)");
				String programText = driver.findElement(By.cssSelector("div[class='CourseDescription_infoBoxText__w49c3'] a[href]")).getAttribute("href");
				if(!programText.isEmpty()||programText.isBlank())
				{
					WebElement programLocator = driver.findElement(By.cssSelector("div[class='CourseDescription_infoBoxText__w49c3'] a"));
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
					if(programLocator.isDisplayed())
					{
						Thread.sleep(400);
						JavascriptExecutor jse2 = (JavascriptExecutor)driver;
						wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='CourseDescription_infoBoxText__w49c3'] a")));
					//	jse2.executeScript("arguments[0].scrollIntoView()", programLocator); 
						((JavascriptExecutor)driver).executeScript("arguments[0].click();", programLocator);
						String parentWindow = driver.getWindowHandle();
						Set<String> nextWindow = driver.getWindowHandles();
						for(String handle : nextWindow)
						{
							driver.switchTo().window(handle);
							if(driver.getCurrentUrl().contains(programText))
							{
								driver.switchTo().window(handle);
								System.out.println("Program window");
								checkProgramProcess.add("pass");
								break;
							}
						}
						driver.close();
						driver.switchTo().window(parentWindow);
					}
				}
				else
				{
					System.out.println("program is not available");
					checkProgramProcess.add("fail");
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkProgramProcess.add("fail");
		}
		return checkProgramProcess;
	}
	public void validationProcess()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		List<WebElement> errorMsg = driver.findElements(By.cssSelector("p[class='mt-2 NewsAndUpdates_inputMessage___Y1G_ ']"));
		if(errorMsg.size()>0)
		{
				System.out.println("validation message shown");
		}
		else
		{
			System.out.println("no validation message is displayed");
		}
	}
	public ArrayList<String> subscribeLocator(ArrayList<String> subscribeFromExcel)
	{
		ArrayList<String> checkSubscribeProcess = new ArrayList<String>();
		try
		{
				String getValue = subscribeFromExcel.get(1);
				String splitDetails[] = getValue.split("-split-");
				String key = splitDetails[0];
				System.out.println(key);
				String value = splitDetails[1];
				System.out.println(value);
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,800)");
				WebElement fullName = driver.findElement(By.xpath("(//input[@name='full_name'])[2]"));
				fullName.clear();
				fullName.sendKeys(key);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				validationProcess();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
				js.executeScript("window.scrollBy(0,-100)");
				WebElement email = driver.findElement(By.xpath("(//input[@name='email'])[3]"));
				email.clear();
				email.sendKeys(value);
				validationProcess();
				WebElement clickSubscribe = driver.findElement(By.xpath("(//button[contains(text(),'Subscribe')])[2]"));
				if(clickSubscribe.isDisplayed())
				{
					try
					{
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						js.executeScript("arguments[0].scrollIntoView()", clickSubscribe); 
						WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
				        wait.until(ExpectedConditions.elementToBeClickable(clickSubscribe));
						//clickSubscribe.click();
						((JavascriptExecutor)driver).executeScript("arguments[0].click();", clickSubscribe);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
					}
					catch(Exception e)
					{
						e.printStackTrace();
						checkSubscribeProcess.add("fail");
					}
					List<WebElement> checkValidationLocator = driver.findElements(By.cssSelector("p[class='mt-2  NewsAndUpdates_inputMessage___Y1G_ mt-1']"));
					if(checkValidationLocator.size()>0)
					{
						System.out.println("validation message shown");
						checkSubscribeProcess.add("fail");
					}
					else
					{
						checkSubscribeProcess.add("pass");
					}
				}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkSubscribeProcess;
	}
}
