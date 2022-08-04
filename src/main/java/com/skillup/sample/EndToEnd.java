package com.skillup.sample;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class EndToEnd
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium jar\\chromedriver.exe");	
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		String CurrentUrl ="https://skillup.online/courses/course-v1:IBM+BC0101EN+v1/about";
		driver.get(CurrentUrl);
		String ExpectedUrl = "https://in.skillup.online/courses/blockchain-essentials/";
		System.out.println(driver.getCurrentUrl());
		
		String ActualUrl = driver.getCurrentUrl();
		Assert.assertEquals(ActualUrl, ExpectedUrl);
		
		String RedirectChecker = "https://www.redirect-checker.org/index.php";
		driver.get(RedirectChecker);
		WebElement CheckRedirect = driver.findElement(By.cssSelector("input#redirecturl"));
		CheckRedirect.sendKeys(ActualUrl);
		WebElement ClickAnalyse = driver.findElement(By.cssSelector("input#sitemapsubmit"));
		ClickAnalyse.click();
		
		String StatusCode = driver.findElement(By.xpath("//p//strong[text()='200 OK']")).getText();
		System.out.println("Actual status code : "+StatusCode);	
		String ExpectedStatusCode = "200 OK";
		Assert.assertEquals(StatusCode, ExpectedStatusCode);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		driver.get(CurrentUrl);
		
		String metaTag = "Blockchain Essentials - SkillUp Online";
		String metaTagSelector = "meta[name='title'][content]";
		String Text_MetaTag = driver.findElement(By.cssSelector(metaTagSelector)).getAttribute("content");
		System.out.println("Meta Tag Text : "+Text_MetaTag);
		Assert.assertEquals(Text_MetaTag, metaTag);
		
		String ExpectedMetaDescription = "Blockchain Essentials - Take this Blockchain Essentials course by SkillUp Online.";
		String metaDescriptionSelector = "meta[name=\"description\"][content]";
		String Text_MetaDescription = driver.findElement(By.cssSelector(metaDescriptionSelector)).getAttribute("content");
		System.out.println("Meta Description : "+Text_MetaDescription);
		Assert.assertEquals(Text_MetaDescription, ExpectedMetaDescription);
		
		String Expected_Heading1 = "Blockchain Essentials";
		String Heading1Selector = "//h1[text()='Blockchain Essentials']";
		String Text_Heading1 = driver.findElement(By.xpath(Heading1Selector)).getText();
		System.out.println("Suggested H1 heading : "+Text_Heading1);
		Assert.assertEquals(Text_Heading1, Expected_Heading1);
		
		/*
		 * String Expected_Heading2 = "About this course"; String Heading2Selector =
		 * "//h2[contains(text(),'Course Outline')]"; String Text_Heading2 =
		 * driver.findElement(By.xpath(Heading2Selector)).getText();
		 * System.out.println("Suggested H2 heading : " + Text_Heading2);
		 * Assert.assertEquals(Text_Heading2, Expected_Heading2);
		 * 
		 * String ExpectedHeading3 = "Course Syllabus"; String Heading3Selector =
		 * "//h2[text()='Course details']"; String Text_Heading3 =
		 * driver.findElement(By.xpath(Heading3Selector)).getText();
		 * System.out.println("Suggested H3 heading : " + Text_Heading3);
		 * Assert.assertEquals(Text_Heading3, ExpectedHeading3);
		 * 
		 * String ExpectedHeading4 = "Meet Your Instructors"; String Heading4Selector =
		 * "//h2[text()='Meet Your Instructors']"; String Text_Heading4 =
		 * driver.findElement(By.xpath(Heading4Selector)).getText();
		 * System.out.println("Suggested H4 heading : " + Text_Heading4);
		 * Assert.assertEquals(Text_Heading4, ExpectedHeading4);
		 */
		
		/*
		 * String img1Selector = "(//img[contains(@alt,'Blockchain Essentials')])[2]";
		 * String ExpectedImg1 = "Blockchain Essentials"; String Text_Img1 =
		 * driver.findElement(By.xpath(img1Selector)).getAttribute("alt");
		 * System.out.println("Image 1 Text : "+Text_Img1);
		 * Assert.assertEquals(Text_Img1, ExpectedImg1);
		 * 
		 * String img2Selector = "//img[contains(@alt, 'Course Highlights')]"; String
		 * ExpectedImg2 = "Course Highlights"; String Text_Img2 =
		 * driver.findElement(By.xpath(img2Selector)).getAttribute("alt");
		 * System.out.println("Image 2 Text : "+Text_Img2);
		 * Assert.assertEquals(Text_Img2, ExpectedImg2);
		 * 
		 * String img3Selector = "(//img[contains(@alt, 'Course duration')])[1]"; String
		 * ExpectedImg3 = "Course duration"; String Text_Img3 =
		 * driver.findElement(By.xpath(img3Selector)).getAttribute("alt");
		 * System.out.println("Image 3 Text : "+Text_Img3);
		 * Assert.assertEquals(Text_Img3,ExpectedImg3);
		 * 
		 * String img4Selector = "(//img[contains(@alt, 'Course Fee')])[1]"; String
		 * ExpectedImg4 = "Course Fee"; String Text_Img4 =
		 * driver.findElement(By.xpath(img4Selector)).getAttribute("alt");
		 * System.out.println("Image 4 Text : "+Text_Img4);
		 * Assert.assertEquals(Text_Img4, ExpectedImg4);
		 * 
		 * String img5Selector =
		 * "//img[contains(@alt, 'Matt Lucas - Blockchain instructors')]"; String
		 * ExpectedImg5 = "Matt Lucas - Blockchain instructors"; String Text_Img5 =
		 * driver.findElement(By.xpath(img5Selector)).getAttribute("alt");
		 * System.out.println("Image 5 Text : "+Text_Img5);
		 * Assert.assertEquals(Text_Img5, ExpectedImg5);
		 * 
		 * String img6Selector =
		 * "//img[contains(@alt, 'Liam Grace - Blockchain educators')]"; String
		 * ExpectedImg6 = "Liam Grace - Blockchain educators"; String Text_Img6 =
		 * driver.findElement(By.xpath(img6Selector)).getAttribute("alt");
		 * System.out.println("Image 6 Text : "+Text_Img6);
		 * Assert.assertEquals(Text_Img6, ExpectedImg6);
		 * 
		 * String img7Selector =
		 * "//img[contains(@alt, 'Andrew Hurt - Blockchain educators')]"; String
		 * ExpectedImg7 = "Andrew Hurt - Blockchain educators"; String Text_Img7 =
		 * driver.findElement(By.xpath(img7Selector)).getAttribute("alt");
		 * System.out.println("Image 7 Text : "+Text_Img7);
		 * Assert.assertEquals(Text_Img7, ExpectedImg7);
		 */
		
		String Selector_ogType = "//meta[@property=\"og:type\" and @content=\"website\"]";
		String Expected_ogType = "website";
		String Text_ogType = driver.findElement(By.xpath(Selector_ogType)).getAttribute("content");
		System.out.println("ogType content : "+Text_ogType);
		Assert.assertEquals(Text_ogType, Expected_ogType);
		
		String Selector_ogUrl = "//meta[@property=\"og:url\" and @content]";
		String Expected_ogUrl = "https://skillup.online/courses/blockchain-essentials";
		String Text_ogUrl = driver.findElement(By.xpath(Selector_ogUrl)).getAttribute("content");
		System.out.println("ogUrl content : "+Text_ogUrl);
		Assert.assertEquals(Text_ogUrl, Expected_ogUrl);
		
		String Selector_ogTitle = "//meta[@property=\"og:title\" and @content]";
		String Expected_ogTitle = "Blockchain Essentials | Learn Blockchain Essentials Course Online - SkillUp Online";
		String Text_ogTitle = driver.findElement(By.xpath(Selector_ogTitle)).getAttribute("content");
		System.out.println("ogTitle content : "+Text_ogTitle);
		Assert.assertEquals(Text_ogTitle, Expected_ogTitle);
		
		String Selector_ogDescription = "//meta[@property=\"og:description\" and @content]";
		String Expected_ogDescription = "Learn the essentials of Blockchain, what is blockchain, its example and networks, IBM and blockchain, with SkillUp Online. Enrol now and earn a badge after completion of course/program.";
		String Text_ogDescription = driver.findElement(By.xpath(Selector_ogDescription)).getAttribute("content");
		System.out.println("ogDescription content : "+Text_ogDescription);
		Assert.assertEquals(Text_ogDescription, Expected_ogDescription);
		
		String Selector_ogImage = "//meta[@property=\"og:image\" and @content]";
		String Expected_ogImage = "https://skillup.online/asset-v1:IBM+BC0101EN+v1+type@asset+block@FS-Prime-CO-Blockchain-Essentials.jpg";
		String Text_ogImage = driver.findElement(By.xpath(Selector_ogImage)).getAttribute("content");
		System.out.println("OgImage content : "+Text_ogImage);
		Assert.assertEquals(Text_ogImage, Expected_ogImage);
		
		String Selector_twitterCard = "//meta[@name=\"twitter:card\" and @content]";
		String Expected_twitterCard = "summary_large_image";
		String Text_twitterCard = driver.findElement(By.xpath(Selector_twitterCard)).getAttribute("content");
		System.out.println("Twitter card content : "+Text_twitterCard);
		Assert.assertEquals(Text_twitterCard, Expected_twitterCard);
		
		String Selector_twitterUrl = "//meta[@property=\"twitter:url\" and @content]";
		String Expected_twitterUrl = "https://skillup.online/courses/blockchain-essentials";
		String Text_twitterUrl = driver.findElement(By.xpath(Selector_twitterUrl)).getAttribute("content");
		System.out.println("Twitter url content : "+Text_twitterUrl);
		Assert.assertEquals(Text_twitterUrl, Expected_twitterUrl);
		
		String Selector_twitterTitle = "//meta[@name=\"twitter:title\" and @content]";
		String Expected_twitterTitle = "Blockchain Essentials - SkillUp Online";
		String Text_twitterTitle = driver.findElement(By.xpath(Selector_twitterTitle)).getAttribute("content");
		System.out.println("twitterTitle content : "+Text_twitterTitle);
		Assert.assertEquals(Text_twitterTitle, Expected_twitterTitle);
		
		String Selector_twitterDescription = "//meta[@name=\"twitter:description\" and @content]";
		String Expected_twitterDescription = "Learn the essentials of Blockchain, what is blockchain, its example and networks, IBM and blockchain, with SkillUp Online. Enrol now and earn a badge after completion of course/program.";
		String Text_twitterDescription = driver.findElement(By.xpath(Selector_twitterDescription)).getAttribute("content");
		System.out.println("twitterDescription content : "+Text_twitterDescription);
		Assert.assertEquals(Text_twitterDescription, Expected_twitterDescription);
		
		String Selector_twitterImage = "//meta[@name=\"twitter:image\" and @content]";
		String Expected_twitterImage = "https://skillup.online/asset-v1:IBM+BC0101EN+v1+type@asset+block@FS-Prime-CO-Blockchain-Essentials.jpg";
		String Text_twitterImage = driver.findElement(By.xpath(Selector_twitterImage)).getAttribute("content");
		System.out.println("twitterDescription content : "+Text_twitterImage);
		Assert.assertEquals(Text_twitterImage, Expected_twitterImage);
		
		WebElement Selector_FAQ1 = driver.findElement(By.cssSelector("div#accordion6 div#headingOne6 h4 a"));
		String Text_Selector_FAQ1 = Selector_FAQ1.getText();
		
		
		System.out.println(Text_Selector_FAQ1);
		Selector_FAQ1.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,300)");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		String[] Expected_FAQ1Answers = {"Blockchain is a system of recording information in a way that makes it difficult, if not impossible, to change, hack, or cheat the system.","A blockchain is essentially a digital ledger of transactions that is duplicated and distributed across the entire network of computer systems on the blockchain. Each block in the chain contains a number of transactions, and every time a new transaction occurs on the blockchain, a record of that transaction is added to every participant’s ledger.","Blockchain technology provides a dynamic shared ledger that can be applied to save time when recording transactions between parties. It also removes costs associated with intermediaries, and reduces risk of fraud and tampering."};
		List<WebElement> Selector_FAQ1Answer = driver.findElements(By.cssSelector("div#collapseOne6 div p"));
		String [] arr = new String[Selector_FAQ1Answer.size()];
		int i;
		for(i = 0; i < Selector_FAQ1Answer.size(); i++)
		{
			arr[i] = Selector_FAQ1Answer.get(i).getText();
			System.out.println(arr[i]);
			Thread.sleep(3000);
			Assert.assertEquals(arr[i], Expected_FAQ1Answers[i]);
		}
		Selector_FAQ1.click();
		
		WebElement Selector_FAQ2 = driver.findElement(By.cssSelector("div#headingTwo6")); 
		String Text_Selector_FAQ2 = Selector_FAQ2.getText();
		System.out.println(Text_Selector_FAQ2);
		Thread.sleep(3000);
		Selector_FAQ2.click();
		String Selector_FAQ2Answer = driver.findElement(By.cssSelector("div#collapseTwo6 div p")).getText();
		String Expected_FAQ2Answer = "There are no prerequisite requirements for taking this course. However, it is beneficial for learners to have knowledge of at least one modern, high-level programming language.";
		System.out.println(Selector_FAQ2Answer);
		Assert.assertEquals(Selector_FAQ2Answer, Expected_FAQ2Answer);
		Thread.sleep(3000);
		Selector_FAQ2.click();
		
		WebElement Selector_FAQ3 = driver.findElement(By.cssSelector("div#headingThree6 h4 a"));
		String Text_Selector_FAQ3 = Selector_FAQ3.getText();
		System.out.println(Text_Selector_FAQ3);
		Thread.sleep(3000);
		Selector_FAQ3.click();
		List<WebElement> Selector_FAQ3Answer = driver.findElements(By.cssSelector("div#collapseThree6 div p"));
		String[] Expected_FAQ3Answer = {"The grading scheme for Blockchain Essentials is as follows:", "The minimum pass mark for the course is 70%, where the review questions are worth 40% and the final exam is worth 60% of the course mark."};
		String [] Ans3 = new String[Selector_FAQ3Answer.size()];
		for(int j = 0; j < Selector_FAQ3Answer.size(); j++)
		{
			Ans3[j] = Selector_FAQ3Answer.get(j).getText();
			System.out.println(Ans3[j]);
			Thread.sleep(3000);
			Assert.assertEquals(Ans3[j], Expected_FAQ3Answer[j]);
		}
		Selector_FAQ3.click();
		
		WebElement Selector_FAQ4 = driver.findElement(By.cssSelector("div#headingFour6 h4 a"));
		String Text_Selector_FAQ4 = Selector_FAQ4.getText();
		System.out.println(Text_Selector_FAQ4);
		Thread.sleep(3000);
		Selector_FAQ4.click();
		String Selector_FAQ4Answer = driver.findElement(By.cssSelector("div#collapseFour6 div p")).getText();
		String Expected_FAQ4Answer = "Blockchain Essentials is provided 100% online. You will therefore need access to the internet to be able to use the course materials. When you enroll for this course, you be able to access the course materials from the course link in your dashboard immediately.";
		System.out.println(Expected_FAQ4Answer);
		Assert.assertEquals(Expected_FAQ4Answer, Selector_FAQ4Answer);
		Thread.sleep(3000);
		Selector_FAQ4.click();
	}
}
