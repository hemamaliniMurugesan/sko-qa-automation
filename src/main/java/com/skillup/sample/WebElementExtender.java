package com.skillup.sample;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.seo.utility.TestUtil;

public class WebElementExtender 
{
	  public static void main(String args[])
	  {
			
			/*
			 * System.setProperty("webdriver.chrome.driver",
			 * "C:\\Users\\Skillup 200\\Downloads\\chromedriver_103.0.5060.53 version\\chromedriver_win32\\chromedriver.exe"
			 * ); WebDriver driver = new ChromeDriver();
			 * driver.manage().window().maximize();
			 * driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.
			 * PAGE_LOAD_TIMEOUT));
			 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.
			 * IMPLICIT_WAIT)); driver.get(
			 * "https://stage-in.skillup.online/courses/course-v1:IBM+DA0101EN+v1");
			 * WebElement title = driver.findElement(By.
			 * xpath("//div//h2[contains(text(),'Data Analysis with Python')]"));
			 * System.out.println("title from browser: "+title.getAttribute("innerText").
			 * trim()); JavascriptExecutor js = (JavascriptExecutor)driver;
			 * js.executeScript("window.scrollBy(0, 500)"); List<WebElement>
			 * overviewNavigation = driver.findElements(By.
			 * cssSelector("div[class*=\"CourseDescription_navigationBar\"] button"));
			 * for(int i = 0; i < overviewNavigation.size(); i++) {
			 * if(overviewNavigation.get(i).getText().equals("FAQs")) {
			 * overviewNavigation.get(i).click(); break; } }
			 */
		  
		  String str = "/courses/course-v1:IBM+AI0101EN-Skillup+2020?/about#";
		  String test[] = str.split("/");
		  for(String word:test)
		  System.out.println(word);
	  }
}
