
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
import org.openqa.selenium.remote.DesiredCapabilities;

public class OpenWebsite
{
	public WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setLoginURL;
	String courseCode;
	
	public OpenWebsite(WebDriver driver)
	{
		driver = this.driver;
	}
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public WebDriver openDriver(String browserName)
	{
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver_110\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable notifications");
			DesiredCapabilities cp = new DesiredCapabilities();
			cp.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(cp);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
			/*
			 * ChromeOptions options = new ChromeOptions();
			 * options.addArguments("--no-sandbox");
			 * options.addArguments("--disable-dev-shm-usage");
			 * options.addArguments("--disable-notifications"); wait = new
			 * WebDriverWait(driver, Duration.ofSeconds(5000));
			 */
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","D:\\GeckoDriver\\geckodriver-v0.32.2-win32\\geckodriver.exe");
			driver = new FirefoxDriver(); 
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		}
		return driver;
	}
	
	public String setEnvironment(String host)
	{
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
	
	public String launchCourse(String urlFromExcel)
	{
		String setURL = this.setEnvironment(RegressionTesting.ENV_TO_USE)+urlFromExcel;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		driver.get(setURL);
		return setURL;
	}
	
	public String launchCourse()
	{
		String setURL = this.setEnvironment(RegressionTesting.ENV_TO_USE);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(70));
		driver.get(setURL);
		
		return setURL;
	}
	public void closeBrowser()
	{
		driver.quit();
	}
	public WebDriver openDriver() {
		// TODO Auto-generated method stub
		return null;
	}
}
