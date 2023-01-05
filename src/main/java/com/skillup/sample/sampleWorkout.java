package com.skillup.sample;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.asprise.ocr.Ocr;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class sampleWorkout 
{
	public static void main(String args[])
	{
		/*
		 * WebDriver driver; HttpURLConnection huc = null; int respCode = 200;
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\Skillup 200\\Downloads\\chromedriver_103.0.5060.53 version\\chromedriver_win32\\chromedriver.exe"
		 * ); driver = new ChromeDriver(); driver.manage().window().maximize();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); String url
		 * =
		 * "https://qa-in.skillup.online/courses/course-v1:NASSCOM+NOS901+2020_Q3/about";
		 * try { huc = (HttpURLConnection)(new URL(url).openConnection());
		 * huc.setRequestMethod("HEAD"); huc.connect(); respCode =
		 * huc.getResponseCode(); System.out.println(respCode); if(respCode > 200) {
		 * System.out.println("broken link"); } else {
		 * System.out.println("un broken link"); driver.get(url); } } catch (IOException
		 * e) { e.printStackTrace(); }}
		 */	
	LinkedHashMap<String, String> kv = new LinkedHashMap<String, String>();
	String key = null, value = null;
	String data = "name=hema-split-mail=hema@gmail.com-split-country=india-split-mbl=123456789-split-status=Working full time-split-msg=welcome";
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
}
}
