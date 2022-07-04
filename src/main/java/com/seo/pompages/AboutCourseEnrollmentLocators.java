package com.seo.pompages;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.utility.TestUtil;

public class AboutCourseEnrollmentLocators {
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String getEnrollCourseURL;
	String courseText;
	String prizeWithoutGST;
	String prizeWithGST;
	

	public WebDriver getDriver() {
		return driver;
	}

	public void openDriver()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Skillup 200\\Downloads\\chromedriver_win32_101.0.4951.41version\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
	}

	public boolean loginSEO() throws InterruptedException
	{
		boolean loginStatus = false;
		driver.get(ConfigFileReader.getSEOLoginURL());// open login page
		WebElement clickLoginIcon = driver.findElement(By.xpath("//a[contains(text(), \"LOGIN\")]"));
		wait.until(ExpectedConditions.visibilityOf(clickLoginIcon)).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 200)");
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> itr = allWindows.iterator();
		while (itr.hasNext())
		{
			String childWindow = itr.next();
			driver.switchTo().window(childWindow);
			String checkLoginScreen = driver.findElement(By.xpath("//h4[contains(text(),\"Log\")]")).getText();
			System.out.println(checkLoginScreen);
			WebElement emailText = driver.findElement(By.id("email"));
			emailText.sendKeys(ConfigFileReader.getUsername());
			WebElement pwd = driver.findElement(By.id("password"));
			pwd.sendKeys(ConfigFileReader.getPassword());
			WebElement LoginButton = driver.findElement(By.id("login_in"));
			LoginButton.click();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			Thread.sleep(1000);
			try
			{
				List<WebElement> checkAlert = driver.findElements(By.cssSelector(
						"div[class=\"NotificationTypeError spacing-mb16 status message submission-error is-shown\"]"));
				if (checkAlert.size() != 0)
				{
					System.out.println("Validation message is displayed");
					loginStatus = true;
				} else {
					System.out.println("Login successfully");
					loginStatus= true;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		Thread.sleep(1000);
		return loginStatus;
	}

	public String getCourseCodeText(String code) throws InterruptedException
	{
		boolean checkLoginstatus = loginSEO();
		String CourseCodeStatus = "false";
		if(checkLoginstatus == true)
		{
			try {
				getEnrollCourseURL = ConfigFileReader.getAboutCourseURL() + code + "about";
				((JavascriptExecutor) driver).executeScript("window.open()");
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs.get(1));
				driver.get(getEnrollCourseURL);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
				Thread.sleep(2000);
				JavascriptExecutor scr = (JavascriptExecutor) driver;
				scr.executeScript("window.scrollBy(0, 400)");
				if (driver.getPageSource().contains(code))
				{
					CourseCodeStatus = "true";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CourseCodeStatus;
		// String courseIDFromBrowser = "";
	}

	public ArrayList<Integer> enrollCourse(String plan, String paymentMode, String price, String priceWithTax, String paymentSuccessText) throws InterruptedException
	{
		ArrayList<Integer> errorCells = new ArrayList<Integer>();
		try 
		{
			Thread.sleep(1000);
			if (driver.findElements(By.xpath("//a[contains(text(),\"Enrollment is Closed\")]")).size() > 0)
			{
				System.out.println("Unable to enroll this course, because enrollment is closed");
				errorCells.add(0);
			}
			else if (driver.findElements(By.xpath("//a[contains(text(),\"Enroll now\")]")).size() > 0)// = dev, QA,
			{
				if (driver.getCurrentUrl().contains("qa"))// prod
				{
					WebElement clickEnrollIcon = driver.findElement(By.xpath("//div[@class=\"ConTianer_ConTent\"]//a[contains(text(),\"Enroll now\")]"));
					wait.until(ExpectedConditions.visibilityOf(clickEnrollIcon));
					System.out.println("Enrollment is present");

					if (clickEnrollIcon.isDisplayed())
					{
						wait.until(ExpectedConditions.visibilityOf(clickEnrollIcon)).click();
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
					}
					String parentCoursePage = driver.getWindowHandle();// 1st page
					Set<String> popup = driver.getWindowHandles(); // it shows 2 popup
					Iterator<String> iterate1 = popup.iterator();
					while (iterate1.hasNext())
					{
						if (!parentCoursePage.equalsIgnoreCase(iterate1.next()))
						{
							driver.switchTo().window(iterate1.next());// 1st popup
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("window.scrollBy(0, 200)");// to scroll up for to view plans in the
						}
					}
					boolean isPlanAvailable = planSelection(plan);
					if(isPlanAvailable)
					{
						System.out.println("plan selected");
					}
					else
					{
						System.out.println("plan is not available");
					}
					boolean isRazorpayProcess = razorpayProcess();
					if(isRazorpayProcess)
					{
						Thread.sleep(1000);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
						if (driver.getCurrentUrl().contains("https://api.razorpay.com/v1/checkout/embedded")) // payment
						{
							String razorayPaymentPage = driver.getWindowHandle();
							Set<String> thirdWindow = driver.getWindowHandles();
							Iterator<String> iterate3 = thirdWindow.iterator();
							while (iterate3.hasNext())
							{
								if (!razorayPaymentPage.equalsIgnoreCase(iterate3.next()))
								{
									driver.switchTo().window(iterate3.next());
									if (driver.getCurrentUrl()
											.equalsIgnoreCase("https://api.razorpay.com/v1/checkout/embedded"))
										{
											String paymentModeForQA = paymentMode;
											switch (paymentModeForQA)
											{
											case "card":
												card(price, priceWithTax, paymentSuccessText, errorCells);
												break;
											case "netbanking":
												netbanking(price, priceWithTax, paymentSuccessText, errorCells);
												break;
											case "wallet":
												wallet(price, priceWithTax, paymentSuccessText, errorCells);
												break;
											case "upi":
												upi(price, priceWithTax, paymentSuccessText, errorCells);
												break;
											}
										}
									}
								}
							}
						else
						{
							payWithCard(price, priceWithTax, paymentSuccessText, errorCells);
						}
					}
					else
					{
						errorCells.add(0);
					}
					
					}
				else
				{
					
				}
				}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return errorCells;
	}
	public String payWithCard(String price, String priceWithTax, String paymentSuccessText, ArrayList<Integer> errorCells)
	{
		System.out.println("US payment site");
		String status = "success";
		if(driver.getCurrentUrl().contains("checkout.stripe.com"))// us site
		{
			WebElement cardNumber = driver.findElement(By.cssSelector("input#cardNumber"));
			cardNumber.sendKeys("4242424242424242");
			WebElement cardExpiry = driver.findElement(By.cssSelector("input#cardExpiry"));
			cardExpiry.sendKeys("09/23");
			WebElement cvc = driver.findElement(By.cssSelector("input#cardCvc"));
			cvc.sendKeys("123");
			WebElement nameOfCard = driver.findElement(By.cssSelector("input#billingName"));
			nameOfCard.sendKeys("test");
			WebElement zipcode = driver.findElement(By.cssSelector("input#billingPostalCode"));
			zipcode.sendKeys("12345");
			WebElement payNow = driver.findElement(By.cssSelector("div[class=\"SubmitButton-IconContainer\"]"));
			payNow.click();
			Set<String> allwindows = driver.getWindowHandles();
			Iterator<String> iterator = allwindows.iterator();
			while(iterator.hasNext())
			{
				driver.switchTo().window(iterator.next());
				if(driver.getCurrentUrl().contains("checkout"))
				{
					WebElement getCourseTextFromOrderDetails = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td p"));
					wait.until(ExpectedConditions.visibilityOf(getCourseTextFromOrderDetails));
					courseText = getCourseTextFromOrderDetails.getText();
					System.out.println(courseText);
					if(courseText.contains(paymentSuccessText))
					{
						System.out.println("completion certificate is available in receipt details : "+courseText);
					}
					else
					{
						errorCells.add(5);
					}
					WebElement getPrizeWithoutGST = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td[class*=\"row_text\"]"));
					prizeWithoutGST = getPrizeWithoutGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					if(prizeWithoutGST.contains(price.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
					{
						System.out.println(prizeWithoutGST);
					}
					else
					{
						errorCells.add(3);
					}
				}
			}
		}
		return status;
	}
	
	public String card(String price, String priceWithTax, String paymentSuccessText, ArrayList<Integer> errorCells) throws InterruptedException
	{
		String status = "success";
		List<WebElement> allPaymenets = driver.findElements(By.cssSelector("div[class=\"content payment-form\"] li strong span"));
		for (int i = 0; i < allPaymenets.size(); i++) 
		{
			if (allPaymenets.get(i).getText().replaceAll("\\s", "").trim().contains("Card"))
			{
				allPaymenets.get(i).click();
				WebElement cardNumber = driver.findElement(By.cssSelector("input[name=\"card[number]\"]"));
				cardNumber.sendKeys("5267 3181 8797 5449");
				WebElement expiry = driver.findElement(By.cssSelector("input[name=\"card[expiry]\"]"));
				expiry.sendKeys("12/22");
				WebElement cvv = driver.findElement(By.cssSelector("input[name=\"card[cvv]\"]"));
				cvv.sendKeys("234");
				WebElement cardName = driver.findElement(By.cssSelector("input[name=\"card[name]\"]"));
				cardName.sendKeys("testing");
				WebElement payNow = driver.findElement(By.cssSelector("button#pay-now"));
				wait.until(ExpectedConditions.visibilityOf(payNow)).click();
				Set<String> allwind = driver.getWindowHandles();
				Iterator<String> itr = allwind.iterator();
				while(itr.hasNext())
				{
					driver.switchTo().window(itr.next());
					if (driver.getCurrentUrl()
							.equalsIgnoreCase("https://api.razorpay.com/v1/payments/JcXhoGam1C1Zwn/dcc_info#"))
					{
						WebElement currency = driver.findElement(By.cssSelector("input#INR"));
						wait.until(ExpectedConditions.visibilityOf(currency)).click();
						WebElement payAmount = driver.findElement(By.cssSelector("form#dccForm button#submit-action"));
						wait.until(ExpectedConditions.visibilityOf(payAmount)).click();
					}
					else if(driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/payments/create/checkout"))
					{
						driver.findElement(By.cssSelector("form#otpForm input")).sendKeys("1234");
						driver.findElement(By.cssSelector("form#otpForm button")).click();
						Thread.sleep(1000);
					}
				}
				Set<String> allwindows = driver.getWindowHandles();
				Iterator<String> iterator = allwindows.iterator();
				while(iterator.hasNext())
				{
					driver.switchTo().window(iterator.next());
					if(driver.getCurrentUrl().contains("checkout"))
					{
						WebElement getCourseTextFromOrderDetails = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td p"));
						courseText = getCourseTextFromOrderDetails.getText();
						System.out.println(getCourseTextFromOrderDetails);
						if(courseText.contains(paymentSuccessText))
						{
							System.out.println("completion certificate is available in receipt details : "+courseText);
						}
						else
						{
							errorCells.add(5);
						}
						WebElement getPrizeWithoutGST = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td[class*=\"row_text\"]"));
						prizeWithoutGST = getPrizeWithoutGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(prizeWithoutGST.contains(price.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
						{
							System.out.println(prizeWithoutGST);
						}
						else
						{
							errorCells.add(3);
						}
						WebElement gtePrizeWithGST = driver.findElement(By.cssSelector("table tr[class=\"table_lastrow\"] td[class*=\"last_prize\"]"));
						prizeWithGST = gtePrizeWithGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						if(prizeWithGST.contains(priceWithTax.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
						{
							System.out.println(prizeWithGST);
						}
						else
						{
							errorCells.add(4);
						}
					}
				}
				break;
			}
		}
		
		  Set<String> allwinds = driver.getWindowHandles(); Iterator<String> iterat =
		  allwinds.iterator(); while(iterat.hasNext())
		  {
			  driver.switchTo().window(iterat.next());
			  if (driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS"))
			  {
				  driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
			  } 
		  }
		return status; 
	}

	public void netbanking(String price, String priceWithTax, String paymentSuccessText, ArrayList<Integer> errorCells) throws InterruptedException
	{
		String courseText;
		String prizeWithoutGST;
		String prizeWithGST; 
		
		List<WebElement> allPaymenets = driver
				.findElements(By.cssSelector("div[class=\"content payment-form\"] li strong span"));
		for (int i = 0; i < allPaymenets.size(); i++) {
			if (allPaymenets.get(i).getText().replaceAll("\\s", "").trim().equalsIgnoreCase(ConfigFileReader.getPaymentMode()))
			{
				allPaymenets.get(i).click();
			}
		}
		List<WebElement> allBanks = driver
				.findElements(By.cssSelector("div[class=\"netbanking-bank-list\"] label div"));
		for (int j = 0; j < allBanks.size(); j++) {
			if (allBanks.get(j).getText().contains(ConfigFileReader.getNetBanking())) {
				allBanks.get(j).click();
			}
		}
		WebElement payNow = driver.findElement(By.cssSelector("button#pay-now"));
		payNow.click();
		Set<String> allwinds = driver.getWindowHandles();
		Iterator<String> iterat =  allwinds.iterator(); while(iterat.hasNext())
		{
		  driver.switchTo().window(iterat.next());
		  if (driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS"))
		  {
			  driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
		  } 
		}
		Set<String> allwind = driver.getWindowHandles();
		Iterator<String> itr = allwind.iterator();
		while(itr.hasNext())
		{
			driver.switchTo().window(itr.next());
			if (driver.getCurrentUrl()
					.equalsIgnoreCase("https://api.razorpay.com/v1/payments/JcXhoGam1C1Zwn/dcc_info#"))
			{
				WebElement currency = driver.findElement(By.cssSelector("input#INR"));
				currency.click();
				WebElement payAmount = driver.findElement(By.cssSelector("form#dccForm button#submit-action"));
				payAmount.click();
			}
			else if(driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/payments/create/checkout"))
			{
				driver.findElement(By.cssSelector("form#otpForm input")).sendKeys("1234");
				driver.findElement(By.cssSelector("form#otpForm button")).click();
				Thread.sleep(1000);
			}
		}
		Set<String> allwindows = driver.getWindowHandles();
		Iterator<String> iterator = allwindows.iterator();
		while(iterator.hasNext())
		{
			driver.switchTo().window(iterator.next());
			if(driver.getCurrentUrl().contains("checkout"))
			{
				WebElement getCourseTextFromOrderDetails = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td p"));
				courseText = getCourseTextFromOrderDetails.getText();
				System.out.println(getCourseTextFromOrderDetails);
				if(courseText.contains(paymentSuccessText))
				{
					System.out.println("completion certificate is available in receipt details : "+courseText);
				}
				WebElement getPrizeWithoutGST = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td[class*=\"row_text\"]"));
				prizeWithoutGST = getPrizeWithoutGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithoutGST.contains(price.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithoutGST);
				}
				WebElement gtePrizeWithGST = driver.findElement(By.cssSelector("table tr[class=\"table_lastrow\"] td[class*=\"last_prize\"]"));
				prizeWithGST = gtePrizeWithGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithGST.contains(priceWithTax.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithGST);
				}
				else
				{
					System.out.println("completion certificate is not available : "+getCourseTextFromOrderDetails);
				}
			}
		}
	}

	public void wallet(String price, String priceWithTax, String paymentSuccessText, ArrayList<Integer> errorCells) throws InterruptedException
	{
		List<WebElement> allPaymenets = driver
				.findElements(By.cssSelector("div[class=\"content payment-form\"] li strong span"));
		for (int i = 0; i < allPaymenets.size(); i++) 
		{
			if (allPaymenets.get(i).getText().replaceAll("\\s", "").trim().equalsIgnoreCase("wallet"))
			{
				allPaymenets.get(i).click();
			}
		}
		WebElement nameOfWallet = driver.findElement(By.cssSelector("div[class=\"title\"]"));
		nameOfWallet.click();
		WebElement payNow = driver.findElement(By.cssSelector("button#pay-now"));
		payNow.click();
		Set<String> allwind = driver.getWindowHandles();
		Iterator<String> itr = allwind.iterator();
		while(itr.hasNext())
		{
			driver.switchTo().window(itr.next());
			if (driver.getCurrentUrl().contains("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS")) {
				driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
				break;
			}
			else if(driver.getCurrentUrl()
					.equalsIgnoreCase("https://api.razorpay.com/v1/payments/JcXhoGam1C1Zwn/dcc_info#"))
			{
				WebElement currency = driver.findElement(By.cssSelector("input#INR"));
				currency.click();
				WebElement payAmount = driver.findElement(By.cssSelector("form#dccForm button#submit-action"));
				payAmount.click();
			}
			else if(driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/payments/create/checkout"))
			{
				driver.findElement(By.cssSelector("form#otpForm input")).sendKeys("1234");
				driver.findElement(By.cssSelector("form#otpForm button")).click();
				Thread.sleep(1000);
			}
		}
		Set<String> allwindows = driver.getWindowHandles();
		Iterator<String> iterator = allwindows.iterator();
		while(iterator.hasNext())
		{
			driver.switchTo().window(iterator.next());
			if(driver.getCurrentUrl().contains("checkout"))
			{
				WebElement getCourseTextFromOrderDetails = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td p"));
				courseText = getCourseTextFromOrderDetails.getText();
				System.out.println(getCourseTextFromOrderDetails);
				if(courseText.contains(paymentSuccessText))
				{
					System.out.println("completion certificate is available in receipt details : "+courseText);
				}
				WebElement getPrizeWithoutGST = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td[class*=\"row_text\"]"));
				prizeWithoutGST = getPrizeWithoutGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithoutGST.contains(price.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithoutGST);
				}
				WebElement gtePrizeWithGST = driver.findElement(By.cssSelector("table tr[class=\"table_lastrow\"] td[class*=\"last_prize\"]"));
				prizeWithGST = gtePrizeWithGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithGST.contains(priceWithTax.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithGST);
				}
				else
				{
					System.out.println("completion certificate is not available : "+getCourseTextFromOrderDetails);
				}
			}
		}
	}

	public void upi(String price, String priceWithTax, String paymentSuccessText, ArrayList<Integer> errorCells) throws InterruptedException
	{
		List<WebElement> allPaymenets = driver
				.findElements(By.cssSelector("div[class=\"content payment-form\"] li strong span"));
		for (int i = 0; i < allPaymenets.size(); i++)
		{
			if (allPaymenets.get(i).getText().replaceAll("\\s", "").trim().equalsIgnoreCase("upi"))
			{
				allPaymenets.get(i).click();
			}
		}
		WebElement upi = driver.findElement(By.cssSelector("input[name=\"vpa\"]"));
		upi.sendKeys("success@razorpay");
		WebElement payNow = driver.findElement(By.cssSelector("button#pay-now"));
		payNow.click();
		Set<String> allwind = driver.getWindowHandles();
		Iterator<String> itr = allwind.iterator();
		while(itr.hasNext())
		{
			driver.switchTo().window(itr.next());
			if (driver.getCurrentUrl().contains("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS")) {
				driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
				break;
			}
			else if(driver.getCurrentUrl()
					.equalsIgnoreCase("https://api.razorpay.com/v1/payments/JcXhoGam1C1Zwn/dcc_info#"))
			{
				WebElement currency = driver.findElement(By.cssSelector("input#INR"));
				currency.click();
				WebElement payAmount = driver.findElement(By.cssSelector("form#dccForm button#submit-action"));
				payAmount.click();
			}
			else if(driver.getCurrentUrl().equalsIgnoreCase("https://api.razorpay.com/v1/payments/create/checkout"))
			{
				driver.findElement(By.cssSelector("form#otpForm input")).sendKeys("1234");
				driver.findElement(By.cssSelector("form#otpForm button")).click();
				Thread.sleep(1000);
			}
		}
		Set<String> allwindows = driver.getWindowHandles();
		Iterator<String> iterator = allwindows.iterator();
		while(iterator.hasNext())
		{
			driver.switchTo().window(iterator.next());
			if(driver.getCurrentUrl().contains("checkout"))
			{
				WebElement getCourseTextFromOrderDetails = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td p"));
				courseText = getCourseTextFromOrderDetails.getText();
				System.out.println(getCourseTextFromOrderDetails);
				if(courseText.contains(paymentSuccessText))
				{
					System.out.println("completion certificate is available in receipt details : "+courseText);
				}
				WebElement getPrizeWithoutGST = driver.findElement(By.cssSelector("table tr[class=\"first_3_rows\"] td[class*=\"row_text\"]"));
				prizeWithoutGST = getPrizeWithoutGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithoutGST.contains(price.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithoutGST);
				}
				WebElement gtePrizeWithGST = driver.findElement(By.cssSelector("table tr[class=\"table_lastrow\"] td[class*=\"last_prize\"]"));
				prizeWithGST = gtePrizeWithGST.getText().replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				if(prizeWithGST.contains(priceWithTax.replaceAll("\\s", "").replaceAll("[^0-9?!\\.]","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
				{
					System.out.println(prizeWithGST);
				}
				else
				{
					System.out.println("completion certificate is not available : "+getCourseTextFromOrderDetails);
				}
			}
		}
	}

	public void paymentProcessForUPI() {
		List<WebElement> listOfPayments = driver.findElements(By.cssSelector("button div[slot=\"title\"]"));
		for (int i = 0; i < listOfPayments.size(); i++) {
			String getPaymentModeText = listOfPayments.get(i).getText();
			if (getPaymentModeText.equalsIgnoreCase("Pay using UPI")) // UPI
			{
				listOfPayments.get(i).click();
				WebElement upiID = driver.findElement(By.cssSelector("input#vpa-upi"));
				// upiID.sendKeys("success@razorpay");
				driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
				JavascriptExecutor scrpt = (JavascriptExecutor) driver;
				scrpt.executeScript("arguments[0].value='success@razorpay';", upiID);
				WebElement payButton = driver.findElement(By.cssSelector("#footer-cta"));
				scrpt.executeScript("arguments[0].click();", payButton);
			}
		}
	}

	public void paymentProcessForCard() {
		List<WebElement> listOfPayments = driver.findElements(By.cssSelector("button div[slot=\"title\"]"));
		for (int i = 0; i < listOfPayments.size(); i++) {
			if (listOfPayments.get(i).getText().equalsIgnoreCase("Pay using Card")) {
				listOfPayments.get(i).click();
				String AfterFrame = driver.getWindowHandle();
				Set<String> st = driver.getWindowHandles();
				Iterator<String> it = st.iterator();
				while (it.hasNext()) {
					driver.switchTo().window(it.next());
					if (driver.getCurrentUrl().contains("https://dev-ecomm-in.skillup.online/basket/?logged=true")) {
						JavascriptExecutor scrpt = (JavascriptExecutor) driver;
						WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
						scrpt.executeScript("arguments[0].value='5104 0600 0000 0008';", cardNumber);
						// cardNumber.sendKeys("5104 0600 0000 0008");
						WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
						// cardExpiry.sendKeys("09/23");
						scrpt.executeScript("arguments[0].value='09/23';", cardExpiry);
						WebElement cardCVV = driver.findElement(By.cssSelector("input#card_cvv"));
						// cardCVV.sendKeys("123");
						scrpt.executeScript("arguments[0].value='123';", cardCVV);
						WebElement payButton = driver.findElement(By.cssSelector("#footer-cta"));
						scrpt.executeScript("arguments[0].click();", payButton);
						break;
					}
				}
			}
		}
	}

	public void paymentProcessForWallet() {
		List<WebElement> listOfPayments = driver.findElements(By.cssSelector("button div[slot=\"title\"]"));
		for (int i = 0; i < listOfPayments.size(); i++) {
			if (listOfPayments.get(i).getText().equalsIgnoreCase("Pay using Wallet")) {
				listOfPayments.get(i).click();
				String AfterFrame = driver.getWindowHandle();
				Set<String> st = driver.getWindowHandles();
				Iterator<String> it = st.iterator();
				while (it.hasNext()) {
					driver.switchTo().window(it.next());

					if (driver.getCurrentUrl().contains("https://dev-ecomm-in.skillup.online/basket/?logged=true")) {
						WebElement wallet = driver.findElement(By.cssSelector("div#wallet-radio-amazonpay"));
						wallet.click();
						driver.findElement(By.id("footer-cta")).click();
						break;
					}
				}
			}
		}
	}

	public void paymentProcessForNetBanking() {
		List<WebElement> listOfPayments = driver.findElements(By.cssSelector("button div[slot=\"title\"]"));
		for (int i = 0; i < listOfPayments.size(); i++) {
			if (listOfPayments.get(i).getText().equalsIgnoreCase(ConfigFileReader.getNetBanking())) {
				listOfPayments.get(i).click();
				List<WebElement> listOfBanks = driver
						.findElements(By.cssSelector("body div#netb-banks > div[id*=\"bank-item\"] div div"));
				for (int j = 0; j < listOfBanks.size(); j++) {
					if (listOfBanks.get(j).getText().equalsIgnoreCase("Pay using Netbanking"))// bank selection from
																								// netbanking
					{
						System.out.println(listOfBanks.get(j).getText());
						listOfBanks.get(j).click();// click bank
					}
				}
			}
			WebElement element = driver.findElement(By.cssSelector("body div#footer>span#footer-cta"));
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
			JavascriptExecutor scrpt = (JavascriptExecutor) driver;
			scrpt.executeScript("arguments[0].click();", element);
			String AfterFrame = driver.getWindowHandle();
			Set<String> st = driver.getWindowHandles();
			Iterator<String> it = st.iterator();
			while (it.hasNext()) {
				driver.switchTo().window(it.next());
				if (driver.getCurrentUrl().contains("https://api.razorpay.com/v1/gateway/mocksharp/payment?key_id=rzp_test_bnV0zjfXCrrGmS")) {
					driver.findElement(By.cssSelector("button[class=\"success\"]")).click();
					break;
				}
			}
		}
	}

	public void paymentProcessForEMI() {
		List<WebElement> listOfPayments = driver.findElements(By.cssSelector("button div[slot=\"title\"]"));
		for (int i = 0; i < listOfPayments.size(); i++) {
			if (listOfPayments.get(i).getText().equalsIgnoreCase("Pay using EMI"))
				listOfPayments.get(i).click();
		}
		String AfterFrame = driver.getWindowHandle();
		Set<String> st = driver.getWindowHandles();
		Iterator<String> it = st.iterator();
		while (it.hasNext()) {
			driver.switchTo().window(it.next());

			if (driver.getCurrentUrl().contains("https://dev-ecomm-in.skillup.online/basket/?logged=true")) {
				JavascriptExecutor scrpt = (JavascriptExecutor) driver;
				WebElement cardNumber = driver.findElement(By.cssSelector("input#card_number"));
				scrpt.executeScript("arguments[0].value='5104 0600 0000 0008';", cardNumber);
				// cardNumber.sendKeys("5104 0600 0000 0008");
				WebElement cardExpiry = driver.findElement(By.cssSelector("input#card_expiry"));
				scrpt.executeScript("arguments[0].value='09/23';", cardExpiry);
				// cardExpiry.sendKeys("09/23");
				WebElement cardCVV = driver.findElement(By.cssSelector("input#card_cvv"));
				// cardCVV.sendKeys("123");
				scrpt.executeScript("arguments[0].value='123';", cardCVV);
				// driver.findElement(By.id("footer-cta")).click();
				WebElement element = driver.findElement(By.cssSelector("body div#footer>span#footer-cta"));
				scrpt.executeScript("arguments[0].click();", element);
				break;
			}

		}
	}
	
	public boolean planSelection(String plan) throws InterruptedException
	{
		boolean isPlanAvailable = false;
		if(plan.equalsIgnoreCase("NA"))
		{
			isPlanAvailable = true;
		}
		else
		{
			if (driver.findElements(By.xpath("//h4[contains(text(),\"Select a plan to continue\")]"))
					.size() != 0) // plan
			{
				System.out.println("select a plan to continue pop up");
				List<WebElement> plans = driver
						.findElements(By.cssSelector("div#custom-plans1 div[class=\"owl-item active\"]"));
				for (int i = 0; i < plans.size(); i++)
				{
					WebElement getplan = plans.get(i);
					WebElement planName = getplan.findElement(By.cssSelector(" div[class*=\"plan_heading\"]"));
					wait.until(ExpectedConditions.visibilityOf(planName)).click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
					String getPlanName = planName.getText();
					System.out.println(getPlanName);
					if (getPlanName.equalsIgnoreCase(plan))// validating plan
					{
						WebElement planPrize = plans.get(i).findElement(By.cssSelector(
								" h5[class*=\"prize\"]:not([class=\"prize_color discount\"])"));
						String getPrizeText = planPrize.getText();
						System.out.println(getPrizeText);
						WebElement clickSelectPlanButton = plans.get(i)
								.findElement(By.cssSelector(" div[class=\"bttn\"] a"));
						wait.until(ExpectedConditions.visibilityOf(clickSelectPlanButton));
						clickSelectPlanButton.click();
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
						Thread.sleep(1000);
						isPlanAvailable = true;
						JavascriptExecutor js2 = (JavascriptExecutor) driver;
						js2.executeScript("window.scrollBy(0, 500)");
						break;
					} // end of plan selection
				}
			}
			
		}
		return isPlanAvailable;
	}
	
	public boolean razorpayProcess() throws InterruptedException
	{
		boolean isRazorpayProcess = false;
		String planWindow = driver.getWindowHandle();
		Set<String> secondWindow = driver.getWindowHandles();
		Iterator<String> iterate2 = secondWindow.iterator();
		while(iterate2.hasNext())
		{
			if(!planWindow.equalsIgnoreCase(iterate2.next()))
			{
				driver.switchTo().window(iterate2.next());
				if(driver.getCurrentUrl().equalsIgnoreCase("https://qa-ecomm-in.skillup.online/basket/?logged=true"))
				{
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
					WebElement checkAmountWithGST = driver.findElement(By.cssSelector("div[class=\"selected-plan\"] div"));
					String amountWithGSTFromBrowser = checkAmountWithGST.getText();
					System.out.println("Amount in razoray Payment : "+amountWithGSTFromBrowser);
					//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(80));
					Thread.sleep(5000);
					WebElement clickRazorayPayment = driver.findElement(By.cssSelector("#razorpay"));
					wait.until(ExpectedConditions.visibilityOf(clickRazorayPayment));
					clickRazorayPayment.click();
					System.out.println("after clicking razorpay button");
					isRazorpayProcess = true;
					Thread.sleep(7000);
				}
				else
				{
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
					WebElement checkAmountWithGST = driver.findElement(By.cssSelector("div[class=\"selected-plan\"] div"));
					String amountWithGSTFromBrowser = checkAmountWithGST.getText();
					System.out.println("Amount in razoray Payment : "+amountWithGSTFromBrowser);
					//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(80));
					Thread.sleep(5000);
					WebElement clickRazorayPayment = driver.findElement(By.cssSelector("button[class*=\"payment\"]"));
					wait.until(ExpectedConditions.visibilityOf(clickRazorayPayment));
					clickRazorayPayment.click();
					System.out.println("after clicking razorpay button");
					isRazorpayProcess = true;
					Thread.sleep(7000);
				}
			}
		}
				return isRazorpayProcess;
	}
}
