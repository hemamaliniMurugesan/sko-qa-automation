package com.seo.regression.testing;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserDropdownList
{
	WebDriver driver;
	String checkUserOrderDetails;
	WebDriver checkBrowserName;
	ProcessLogin processLogin;
	public UserDropdownList(WebDriver driver)
	{
		this.driver = driver;
		checkUserOrderDetails="";
		checkBrowserName = driver;
		processLogin = new ProcessLogin(driver);
	}
	public String userProfile() throws InterruptedException
	{
		String checkUsernameFromBrowser = "";
		try
		{
			List<WebElement> listOfValues = driver.findElements(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li a"));
			for(int i = 0; i < listOfValues.size(); i++)
			{
				if(listOfValues.get(i).getText().equalsIgnoreCase(("Profile")))
				{
					listOfValues.get(i).click();
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
					Thread.sleep(2000);
					String parentWindow = driver.getWindowHandle();
					Set<String> windows = driver.getWindowHandles();
					for(String window : windows)
					{
						if(driver.getCurrentUrl().contains("u"))
						{
							driver.switchTo().window(window);
							Thread.sleep(1000);
							checkUsernameFromBrowser = driver.findElement(By.cssSelector("div[class='userProfileDetails'] h4")).getText();
							Thread.sleep(2000);
						}
					}
					break;
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return checkUsernameFromBrowser;
	}
	
	String orderNumberFromMyOrders;
	String amountFromMyOrders;
	
	public String orderHistory(String orderNumberFromExcel) throws InterruptedException
	{
		try
		{
			WebElement clickDropdownList = driver.findElement(By.cssSelector("li[class='SigNUP'] img[class='dPaRoW']"));
			clickDropdownList.click();
			Thread.sleep(1000);
			String accountPage = driver.getWindowHandle();
			List<WebElement> listOfValues = driver.findElements(By.cssSelector("ul[class*='dropdown-menu Primary02_Blue'] li a"));
			for(int i = 0; i < listOfValues.size(); i++)
			{
				if(listOfValues.get(i).getText().equalsIgnoreCase("Account"))
				{
					Thread.sleep(2000);
					listOfValues.get(i).click();
					Thread.sleep(1000);
					String parentWindow = driver.getWindowHandle();
					Set<String> windows = driver.getWindowHandles();
					for(String window : windows)
					{
						if(driver.getCurrentUrl().contains("account"))
						{
							driver.switchTo().window(window);
							((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)","");
							JavascriptExecutor js = ((JavascriptExecutor) driver);
							js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
							WebElement clickOrderButton = driver.findElement(By.cssSelector("button#orders-tab"));
							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
							wait.until(ExpectedConditions.elementToBeClickable(clickOrderButton));
							clickOrderButton.click();
							WebElement orderNumber = driver.findElement(By.cssSelector("div[class='u-field u-field-orderHistory u-field-order-"+orderNumberFromExcel+"']"));
							orderNumberFromMyOrders = orderNumber.getAttribute("class");
							List<WebElement> orderInformation = driver.findElements(By.cssSelector("div[class='u-field u-field-orderHistory u-field-order-"+orderNumberFromExcel+"'] span[class='u-field-order-link'] a"));
							for(int j = 0; j < orderInformation.size(); j++)
							{
								if(j == 0)
								{
									orderInformation.get(j).click();
									Thread.sleep(2000);
									String parent = driver.getWindowHandle();
									Set<String> listOfWindows = driver.getWindowHandles();
									for(String handle : listOfWindows)
									{
										driver.switchTo().window(handle);
											if(driver.getCurrentUrl().contains("checkout/receipt"))
											{
												driver.switchTo().window(handle);
												System.out.println("order details page");
												List<WebElement> verifyOrderNumber = driver.findElements(By.cssSelector("div[class='col-md-6 col-sm-12 order_info']:nth-child(2) div[class='col-md-12 order mg_2 spacing_20'] h4"));
												for(int l = 0; l < verifyOrderNumber.size(); l++)
												{
													if(verifyOrderNumber.get(0).getText().equalsIgnoreCase(orderNumberFromExcel))
													{
														System.out.println("order number matched in order details");
														checkUserOrderDetails="Success";
														break;
													}
													else
													{
														checkUserOrderDetails="Failed";
													}
												}
											}
										}
									/*if(!checkBrowserName.toString().contains("Firefox")|| !checkBrowserName.toString().contains("firefox"))
									{
										driver.close();
										Thread.sleep(1000);
									 } */
									driver.switchTo().window(parent);
								}
								if(j == 1)
								{
									orderInformation.get(j).click();
									Thread.sleep(2000);
									String parent2 = driver.getWindowHandle();
									Set<String> listOfWindows = driver.getWindowHandles();
									for(String handle : listOfWindows)
									{
											driver.switchTo().window(handle);
											if(driver.getCurrentUrl().contains("checkout/invoice"))
											{
												driver.switchTo().window(handle);
												System.out.println("Invoice details page");
												WebElement validateOrderNumber = driver.findElement(By.cssSelector("table[class='table table-borderless'] div[class='tax_invoice text-right'] p[class='tax_invoiceNum']"));
												if(validateOrderNumber.getText().contains(orderNumberFromExcel))
												{
													System.out.println("order number matched in invoice details");
													Thread.sleep(1000);
													checkUserOrderDetails="Success";
												}
												else
												{
													checkUserOrderDetails="Failed";
												}
												
											}
										}
										/*
										 * if(!checkBrowserName.toString().contains("Firefox")||
										 * !checkBrowserName.toString().contains("firefox")) { driver.close(); }
										 */
									}
							}
						}
					}
					break;
				}
			}
			processLogin.logOutFunction();
			driver.switchTo().window(accountPage);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		driver.quit();
		return checkUserOrderDetails;
	}
}


