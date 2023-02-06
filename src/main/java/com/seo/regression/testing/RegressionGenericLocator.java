package com.seo.regression.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\DownloadFiles\\chromedriver_108\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}
	
	public String setEnvironment(String host)
	{
		if(host.equalsIgnoreCase("prod-in"))
		{
			setHost = "https://"+host+".skillup.online";
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
			System.out.println(respCode);
			if(respCode > 200 && (!(respCode == 308)))
			{
				System.out.println("broken link");
				System.exit(0);
			}
			else
			{
				System.out.println("un broken link");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				driver.get(addHosturl);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(80));
				WebElement checkCourseCode = driver.findElement(By.cssSelector("div[class='CourseDescription_buttonsContent__qPhJg '] button[class*='enrollNowBtn']"));
				String getCourseID = checkCourseCode.getAttribute("href");
				courseIDFromBrowser = getCourseID;
				System.out.println("course ID from Browser : "+courseIDFromBrowser);
				System.out.println("courseIDFrom Excel: "+code);
				if(courseIDFromBrowser.contains(code))
				{
					CourseCodeStatus = "true";
				}
			}
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
	String amountWithTax;
	String amountWithOutTax;
	public String checkOutRazorpay(String amountFromExcel)
	{
		String checkRazorpay = "false";
		amountWithTax = amountFromExcel;
		try
		{
			if(driver.getCurrentUrl().contains("https://stage-ecomm-in.skillup.online/basket/"))
			{
				WebElement checkoutAmount = driver.findElement(By.cssSelector("div[class=\"selected-plan\"] span"));
				String getCheckoutAmount = checkoutAmount.getText();
				System.out.println("checkout Razorpay amount :"+getCheckoutAmount);
				WebElement getAmountWithGST = driver.findElement(By.cssSelector("div[class='selected-plan'] span[class='h5-regular24 Accent_Red02-text']"));
				String amountWithGSTFromBrowser = getAmountWithGST.getText();
				System.out.println(amountWithGSTFromBrowser);
				if(amountWithGSTFromBrowser.equalsIgnoreCase(amountWithTax))
				{
					System.out.println("both amount are same");
					checkRazorpay = "true";
				}
				else
				{
					checkRazorpay = "false";
				}
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,400)");
				WebElement completePayment = driver.findElement(By.xpath("//button[@id='razorpay' or @id='stripecheckout']"));
				completePayment.click();
			}
			else
			{
				checkRazorpay = "false";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkRazorpay = "false";
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
	
	public String selectPlan(String plan)
	{
		String checkPlanStatus = "false";
		try
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,300)");
			if(driver.getCurrentUrl().contains("choose-subscription"))
			{
				List<WebElement> listOfPlan = driver.findElements(By.cssSelector("div[id='custom-plans1'] div[class*='owl-item']"));// div[class=\"bttn\"] a
				for(int i = 0; i < listOfPlan.size(); i++)
				{
					WebElement selectPlan = listOfPlan.get(i).findElement(By.cssSelector(" div[class*='plan_heading']"));
					String getPlanText = selectPlan.getText();
					if(getPlanText.contains(plan))
					{
						WebElement clickPlan = listOfPlan.get(i).findElement(By.cssSelector(" div[class='bttn']"));
						if(clickPlan.isDisplayed())
						{
							clickPlan.click();
							checkPlanStatus = "true";
							break;
						}
						else
						{
							if(listOfPlan.get(i).findElement(By.cssSelector(" button[disabled]")).isDisplayed())
							{
								System.out.println("button is disabled");
								checkPlanStatus = "false";
							}
						}
						
					}
				}
			}
			else
			{
				System.out.println("select plan page is not displayed");
				checkPlanStatus = "false";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkPlanStatus = "false";
		}
		return checkPlanStatus;
	}
	
	public String netBanking()
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
					List<WebElement> listOfNetBanking = driver.findElements(By.cssSelector("div#netb-banks label[for*='bank-radio']"));
					for(int j = 0; j < listOfNetBanking.size(); j++)
					{
						System.out.println(listOfNetBanking.get(j).getText());
						if(listOfNetBanking.get(j).getText().equalsIgnoreCase("HDFC"))
						{
							WebElement clickBank = listOfNetBanking.get(j);
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
						    wait.until(ExpectedConditions.elementToBeClickable(clickBank)).click(); 
						    WebElement clickPayNow = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						    clickPayNow.click();
						    checkPaymentProcess = "true";
						}
					}
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
	
	public String card()
	{
		try
		{
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
					label.click();
					if(driver.findElement(By.cssSelector("button#otp-sec")).isDisplayed())
					{
						WebElement skipOTP = driver.findElement(By.cssSelector("button#otp-sec"));
						skipOTP.click();
						WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
						cardNumber.sendKeys("5267 3181 8797 5449");
						WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
						cardExpiry.sendKeys("12/22");
						WebElement cardName = driver.findElement(By.cssSelector("input#card_name"));
						cardName.sendKeys("testing");
						WebElement cvv = driver.findElement(By.cssSelector("input#card_cvv"));
						cvv.sendKeys("234");
						WebElement payNowButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						payNowButton.click();
						WebElement payWOSavingCard = driver.findElement(By.cssSelector("button[class='later-button btn svelte-1j4aabt']"));
						payWOSavingCard.click();
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
						checkPaymentProcess = "true";
					}
					else
					{
						WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
						cardNumber.sendKeys("5267 3181 8797 5449");
						WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
						cardExpiry.sendKeys("12/22");
						WebElement cardName = driver.findElement(By.cssSelector("input#card_name"));
						cardName.sendKeys("testing");
						WebElement cvv = driver.findElement(By.cssSelector("input#card_cvv"));
						cvv.sendKeys("234");
						WebElement payNowButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
						payNowButton.click();
						/*
						 * WebElement payWOSavingCard = driver.findElement(By.
						 * cssSelector("button[class='later-button btn svelte-1j4aabt']"));
						 * payWOSavingCard.click();
						 */
						checkPaymentProcess = "true";
					}
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
	
	public String indiaOrderDetails(String priceWOGSTFromExcel)
	{
		String checkOrderDetails = "false";
		try
		{
			String paymentSelection = driver.getWindowHandle();
			Set<String> nextWindow = driver.getWindowHandles();
			Iterator<String> windows = nextWindow.iterator();
			String parentWindow = "";
			while(windows.hasNext())
			{
				String currentWindow = windows.next();
				driver.switchTo().window(currentWindow);
				if(driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS"))
				{
					driver.findElement(By.cssSelector("button[class='success']")).click();
				}
				else
				{
					parentWindow = currentWindow;
				}
			}
			driver.switchTo().window(parentWindow);
			System.out.println(driver.getCurrentUrl());
			System.out.println("checkout page");
			WebElement getOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td"));
			System.out.println(getOrderDetails.getText());
			String amountOrderDetails = driver.findElement(By.cssSelector("table[class=' spacing-pl8 spacing-pr8 spacing-pl20 spacing-pr20 mb table_border'] tr td[class='spacing-pl8 spacing-pr20 spacing-pl20  spacing-pt8 table_lastrow last_prize table_border_bottom text_table']")).getText();
			System.out.println(amountOrderDetails);
			WebElement getOrderNumber = driver.findElement(By.cssSelector("div[class*='order_info']:nth-child(2) div:nth-child(3) h4"));
			orderNumber = getOrderNumber.getText();
			System.out.println("OrderNumber: "+orderNumber);
			if(priceWOGSTFromExcel.equalsIgnoreCase(amountOrderDetails))
			{
				System.out.println("both are same amount ");
				checkOrderDetails = "true";
			}
			else
			{
				checkOrderDetails = "false";
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return checkOrderDetails;
	}
	
	public void USOrderDetails()
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
	
	public String emi()
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
				if(label.getText().equalsIgnoreCase("Pay using EMI"))
				{
					label.click();
					WebElement clickHDFCBank = driver.findElement(By.cssSelector("div#bank-item-HDFC"));
					clickHDFCBank.click();
					WebElement clickContinue = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					clickContinue.click();
					WebElement selectEMIPlan = driver.findElement(By.cssSelector("div[class='expandable-card expandable-card--expanded svelte-1jlvym9'] div[class='radio-display']"));
					selectEMIPlan.click();
					WebElement clickSelectPalnButton = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					clickSelectPalnButton.click();
					WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
					cardNumber.sendKeys("5267 3181 8797 5449");
					WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
					cardExpiry.sendKeys("12/22");
					WebElement cardName = driver.findElement(By.cssSelector("input#card_name"));
					cardName.sendKeys("testing");
					WebElement cvv = driver.findElement(By.cssSelector("input#card_cvv"));
					cvv.sendKeys("234");
					WebElement payViaEMI = driver.findElement(By.cssSelector("button#redesign-v15-cta"));
					payViaEMI.click();
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
					checkPaymentProcess = "true";
					try 
					{
						Thread.sleep(500);
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					break;
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
	String checkPaymentProcess;
	public String indiaPaymentProcess(String paymentModeFromExcel)
	{
		checkPaymentProcess = "false";
		try
		{
			if(driver.getCurrentUrl().equalsIgnoreCase("https://stage-ecomm-in.skillup.online/basket/"))
			{
				switch(paymentModeFromExcel)
				{
				case "card":
					card();
					break;
				case "netBanking":
					netBanking();
					break;
				case "wallet":
					wallet();
					break;
				case "upi":
					upi();
					break;
				case "emi":
					emi();
					break;
				}
			}
			else
			{
				System.out.println("Payment page not displayed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkPaymentProcess;
	}
	ArrayList<Integer> errorCells;
	boolean checkEnrollStatus;
	public ArrayList<String> enroll(ArrayList<String> enrollDataFromExcel)
	{
		ArrayList<String> verifyEnrollmentProcess = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		try
		{
			ArrayList<String> getData = new ArrayList<String>();
			getData = enrollDataFromExcel;
			for(int i = 0; i < getData.size(); i++)
			{
				if(i == 1)
				{
					js.executeScript("window.scrollBy(0,-1100)");
					String getCurrentURL = driver.getCurrentUrl();
					if(getCurrentURL.contains("-in"))//india site
					{
						WebElement checkEnrollButton = driver.findElement(By.cssSelector("div[class='col-12'] button[class='CourseDescription_enrollNowBtn__xyx0I']"));
						if(checkEnrollButton.isDisplayed())
						{
							if(checkEnrollButton.getText().equalsIgnoreCase("Enroll Now"))
							{
								System.out.println("Enroll Button is displayed");
								wait.until(ExpectedConditions.elementToBeClickable(checkEnrollButton));
								js.executeScript("window.scrollBy(0,100)");
								JavascriptExecutor jse = (JavascriptExecutor)driver;
								jse.executeScript("arguments[0].click()", checkEnrollButton);
								driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
								driver.navigate().refresh();
								if(driver.getCurrentUrl().contains("register?"))
								{
									System.out.println("login page displayed");
									checkEnrollStatus = true;
									verifyEnrollmentProcess.add(login(getData.get(i))); // column 1
								}
								else
								{
									System.out.println("login page not displayed");
									checkEnrollStatus = false;
								}
							}
							else
							{
								System.out.println("Enroll Button webElement not found");
								checkEnrollStatus = false;
								if(checkEnrollButton.getText().equalsIgnoreCase("Start Now"))
								{
									checkEnrollButton.click();	
									driver.close();
								}
							}
						}
					}
					else
					{
						System.out.println("US site enrollment Process started");
						if(!getCurrentURL.contains("-in"))
						{

							WebElement checkEnrollButton = driver.findElement(By.cssSelector("div[class='col-12'] button[class='CourseDescription_enrollNowBtn__xyx0I']"));
							if(checkEnrollButton.isDisplayed())
							{
				
								if(checkEnrollButton.getText().equalsIgnoreCase("Enroll Now"))
								{
									System.out.println("Enroll Button is displayed");
									wait.until(ExpectedConditions.elementToBeClickable(checkEnrollButton));
									js.executeScript("window.scrollBy(0,100)");
									JavascriptExecutor jse = (JavascriptExecutor)driver;
									jse.executeScript("arguments[0].click()", checkEnrollButton);
									driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
									driver.navigate().refresh();
									if(driver.getCurrentUrl().contains("register?"))
									{
										System.out.println("login page displayed");
										checkEnrollStatus = true;
										verifyEnrollmentProcess.add(login(getData.get(i))); // column 1
									}
									else
									{
										System.out.println("login page not displayed");
										checkEnrollStatus = false;
									}
								}
								else
								{
									System.out.println("Enroll Button webElement not found");
									checkEnrollStatus = false;
									if(checkEnrollButton.getText().equalsIgnoreCase("Start Now"))
									{
										checkEnrollButton.click();	
										driver.close();
									}
								}
							}

						}
					}
				}
				if(i == 2)
				{
					 if(!driver.findElements(By.cssSelector("div[class=\"owl-item active\"]")).isEmpty()) 
					  {
						  verifyEnrollmentProcess.add(selectPlan(getData.get(i))); 
					  }
					  else
					  {
						  System.out.println("plan is not available");
						  checkEnrollStatus = false;
					  }
				}
				if(i == 3)
				{
					 verifyEnrollmentProcess.add(checkOutRazorpay(getData.get(i)));
				}
				if(i == 4)
				{
					verifyEnrollmentProcess.add(indiaPaymentProcess(getData.get(i))); 
				}
				if(i == 5)
				{
					verifyEnrollmentProcess.add(indiaOrderDetails(getData.get(i)));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			checkEnrollStatus = false;
		}
		return verifyEnrollmentProcess;
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
				clickShareLink.click();
				WebElement copyLink = driver.findElement(By.cssSelector("button[class='btn shadow-none ShareCourse_copyButton___ztrR']"));
				String getLinkText = copyLink.getText();
				copyLink.click();
				List<WebElement> share = driver.findElements(By.cssSelector("ul[class='social_sharing-sko'] li i"));
				for(int i = 0; i < share.size(); i++)
				{
					if(share.get(i).getAttribute("class").contains(shareFromExcel))
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
									System.out.println("twitter screen");
								}
								else if(driver.getCurrentUrl().equalsIgnoreCase("facebook"))
								{
									System.out.println("facebook screen");
								}
								else if(driver.getCurrentUrl().equalsIgnoreCase("linked"))
								{
									System.out.println("linkedIn Screen");
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
		if(driver.findElement(By.cssSelector("p[class='mt-2  NewsAndUpdates_inputMessage___Y1G_ mt-1']")).isDisplayed())
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
					if(driver.findElement(By.cssSelector("p[class='mt-2  NewsAndUpdates_inputMessage___Y1G_ mt-1']")).isDisplayed())
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
