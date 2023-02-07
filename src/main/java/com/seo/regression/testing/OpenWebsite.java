
package com.seo.regression.testing;

import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.seo.utility.TestUtil;

public class OpenWebsite
{
	WebDriver driver;
	WebDriverWait wait;
	URL parentURL;
	String setHost;
	String setLoginURL;
	String courseCode;
	
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
			String convertURL = "courses-in";
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
		else if(host.equalsIgnoreCase("stagecourses"))
		{
			setHost = "https://"+host+".skillup.online";
		}
		else if(host.equalsIgnoreCase("prod"))
		{
			String convertURL = "stage.";
			setHost = "https://"+convertURL+"skillup.online";
		}
		return setHost;
	}
	
	public String launchCourse(String urlFromExcel)
	{
		String setURL = this.setEnvironment(RegressionTesting.ENV_TO_USE)+urlFromExcel;
		this.openDriver();
		driver.get(setURL);
		return setURL;
	}
}
