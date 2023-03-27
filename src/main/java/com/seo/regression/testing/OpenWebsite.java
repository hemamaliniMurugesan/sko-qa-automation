
package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.utility.TestUtil;

public class OpenWebsite
{
	WebDriverWait wait;
	URL parentURL;
	String setLoginURL;
	String courseCode;
	WebDriver getDriverName; 
	
	
	public static WebDriver openDriver(String browserName)
	{
		WebDriver driver = null;
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\Doc\\ChromeDriver_111\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable notifications");
			DesiredCapabilities cp = new DesiredCapabilities();
			cp.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(cp);
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","D:\\Doc\\GeckoDriver\\geckodriver-v0.32.2-win32\\geckodriver.exe");
			driver = new FirefoxDriver(); 
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		}
		return driver;
	}
	public static String setEnvironment(String host)
	{
		String setHost = null;
		if(host.equalsIgnoreCase("prod-in"))
		{
			String convertURL = "in";
			setHost = "https://"+convertURL+".skillup.online";
		}
		else if(host.equalsIgnoreCase("stagecourses-in"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("stage-in"))
		{
			String converturl = "stage-in";
			setHost = "https://"+converturl+".skillup.online";
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
	
	public static String launchCourse(WebDriver driver, String urlFromExcel)
	{
		String setURL = setEnvironment(RegressionTesting.ENV_TO_USE)+urlFromExcel;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		driver.get(setURL);
		return setURL;
	}
	
	public static String openSite(WebDriver driver)
	{
			String setURL;
			setURL = setEnvironment(RegressionTesting.ENV_TO_USE);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
			driver.get(setURL);
		return setURL;
	}
	
}
