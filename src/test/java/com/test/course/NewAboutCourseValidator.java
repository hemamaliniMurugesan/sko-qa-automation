package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.NewAboutCourseLocator;
import com.seo.utility.Utils;

public class NewAboutCourseValidator 
{

	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private HashMap<String, String> faqFromValidator = null;
	private NewAboutCourseLocator newAboutCourseLocators;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	WebDriver driver;
	public NewAboutCourseValidator(String sheetName, ArrayList<ArrayList<String>> rows) {
		this.SHEET_NAME = sheetName;
		this.ROWS = rows;
		newAboutCourseLocators = new NewAboutCourseLocator(driver);
	}

	public String processSheetData() {
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		newAboutCourseLocators.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++) {
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		newAboutCourseLocators.getDriver().quit();
		endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		duration = Utils.findDifference(startTime, endTime);
		collectSheetResult();
		return sheetStatus;
	}
	String getMetaHost;
	public void executeProcess(String process, ArrayList<String> row) {

		try 
		{
			switch (process)
			{
			case "environment":
				environment(row.get(1));
				break;
			case "courseCode":
				courseCode(row.get(1));
				break;
			case "courseTitle":
				courseTitle(row.get(1));
				break;
			case "courseOrganization":
				courseOrganization(row.get(1));
				break;
			case "courseDescription":
				courseDescription(row.get(1));
				break;
			case "courseType1":
				courseType1(row.get(1));
				break;
			case "courseType2":
				courseType2(row.get(1));
				break;
			case "courseLevel ":
				courseLevel(row.get(1));
				break;
			case "courseInformation":
				courseInformation(row);
				break;
			case "typeofCertificate":
				typeofCertificate(row.get(1));
				break;
			case "aboutCourse":
				aboutCourse(row.get(1));
				break;
			case "includes":
				includes(row.get(1));
				break;
			case "create":
				create(row.get(1));
				break;
			case "exercisesToExplore":
				exercisesToExplore(row.get(1));
				break;
			case "experts":
				experts(row);
				break;
			case "startsOn":
				startsOn(row.get(1));
				break;
			case "duration":
				duration(row.get(1));
				break;
			case "flatPriceINRWithoutGST":
				flatPriceINRWithoutGST(row.get(1));
				break;
			case "flatPriceUSD":
				flatPriceUSD(row.get(1));
				break;
			case "category":
				category(row);
				break;
			case "currentURL":
				launchUrlAndTestRedirection(row.get(1), row.get(2));
				break;
			case "canonical":
				canonical(row.get(1));
				break;
			case "checkMetaTagContentByName":
				checkMetaTagContentByName(row);
				break;
			case "checkMetaTagContentByProperty":
				checkMetaTagContentByProperty(row); 
				break;
			case "checkTagWithInnerText":
				checkTagWithInnerText(row); 
				break;
			case "checkImgSrcAndAlt": 
				checkImgSrcAndAlt(row); 
				break;
			case "FAQ": 
				faq(row); 
				break; 
			case "ValidateSchemaHeading":
				validateSchemaHeading(row);
				break;
			case "ValidateFAQ":
				if(null == faqFromValidator)
				{
					faqFromValidator = newAboutCourseLocators.getFAQFromValidator();
				}
				validateSchemaFAQ(row, faqFromValidator);
					break;
			case "CheckRedirectStatus": 
					System.out.println("checkRedirectStatus Method");
					checkRedirectStatus(row.get(1), row.get(2), row.get(3));
				break;
			default:
				markCellAsHeader();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			markProcessFailed();
		}
	}

	String loginURL, getImageHost;

	private void environment(String environmentFromExcel)
	{
		try
		{
			String checkEnvironment = newAboutCourseLocators.setEnvironment(environmentFromExcel);
			getMetaHost = newAboutCourseLocators.setMetaHostURL();
			getImageHost = newAboutCourseLocators.setImageEndpoint(environmentFromExcel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void courseCode(String courseCodeFromExcel) {
		String courseCodestatus = "true";
		try {
			String checkCourseCode = newAboutCourseLocators.getCourseCodeText(courseCodeFromExcel);
			if (!(courseCodestatus).equals(checkCourseCode)) {
				markProcessFailed();
			}
		} catch (Exception e) {
			markProcessFailed();
		}
	}

	private void courseTitle(String courseTitleFromExcel)
	{
		String currentTitlestatus = "fail";
		try
		{
			String checkCourseTitleStatus = newAboutCourseLocators.getCourseTitleText(courseTitleFromExcel);
			System.out.println("Title from excel : " + courseTitleFromExcel);
			if (checkCourseTitleStatus.equals(currentTitlestatus.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
			else if (checkCourseTitleStatus.equalsIgnoreCase("notProcessed"))
			{
				markProcessIgnored();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void courseOrganization(String courseOrganizationFromExcel) {
		String currentOrgStatus = "fail";
		try
		{
			String checkCourseOrgStatus = newAboutCourseLocators.getCourseOrganizationFormatText(courseOrganizationFromExcel);
			System.out.println("course org from Excel : " + courseOrganizationFromExcel);
			if (checkCourseOrgStatus.equals(currentOrgStatus))
			{
				markProcessFailed();
			}
			else if(checkCourseOrgStatus.equalsIgnoreCase("notProcessed"))
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void courseDescription(String courseDescriptionFromExcel) {
		String checkCourseDesStatus = "fail";
		try {
			String checkCourseDes = newAboutCourseLocators.getCourseDescription(courseDescriptionFromExcel);
			System.out.println("Course description from excel : " + courseDescriptionFromExcel);
			if (checkCourseDes.equals(checkCourseDesStatus.replaceAll("\\s", "").replaceAll("\u00A0", "")
					.replaceAll("[^\\p{ASCII}]", ""))) {
				markProcessFailed();
			} else if (checkCourseDes.equalsIgnoreCase("notProcessed")) {
				markProcessIgnored();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void courseType1(String courseType1FromExcel) {
		String courseType1Status = "fail";
		try {
			String checkCourseType1Status = newAboutCourseLocators.getCourseType1(courseType1FromExcel);
			System.out.println("Course Type1 from excel : " + courseType1FromExcel);
			if (courseType1Status.equals(checkCourseType1Status.replaceAll("\\s", "").replaceAll("\u00A0", "")
					.replaceAll("[^\\p{ASCII}]", ""))) {
				markProcessFailed();
			} else if (checkCourseType1Status.equalsIgnoreCase("notProcessed")) {
				markProcessIgnored();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void courseType2(String courseType2FromExcel) {
		String courseType2Status = "fail";
		try {
			String checkCourseType = newAboutCourseLocators.getCourseType2(courseType2FromExcel);
			System.out.println("courseType2 from Excel : " + courseType2FromExcel);
			if (courseType2Status.equals(
					checkCourseType.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", ""))) {
				markProcessFailed();
			} else if (checkCourseType.equals("notProcessed")) {
				markProcessIgnored();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void courseLevel(String courseLevelFromExcel) {
		String courseLevelStatus = "fail";
		try {
			String checkCourseLevel = newAboutCourseLocators.getCourseLevel(courseLevelFromExcel);
			System.out.println("Course level from excel : " + courseLevelFromExcel);
			if (courseLevelStatus.equals(
					checkCourseLevel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", ""))) {
				markProcessFailed();
			} else if (checkCourseLevel.equals("notProcessed")) {
				markProcessIgnored();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void courseInformation(ArrayList<String> infoValues)
	{
		String infoHeadingFromExcel = infoValues.get(1).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		try 
		{
			ArrayList<String> infoContentFromCourse = newAboutCourseLocators.getInfoContentFromCourse(infoHeadingFromExcel);
			if (infoValues.size() - 2 == infoContentFromCourse.size())
			{
				if (infoValues.size() > 2)
				{
					for (int j = 2; j < infoValues.size(); j++)
					{
						String getCourseInfoFromExcel = infoValues.get(j);
						String getCourseInfoFromBrowser = infoContentFromCourse.get(0);
						if (getCourseInfoFromExcel.equalsIgnoreCase("NA"))
						{
							markProcessIgnored();
						}
						else if(getCourseInfoFromExcel.equals(getCourseInfoFromBrowser))
						{
							markColumnFailed(j);
						}
					}
				}
			}
		} 
		catch (Exception e)
		{
			markProcessFailed();
		}
	}

	private void courseProgramOutline()
	{
		ArrayList<String> status = new ArrayList<String>();
		try
		{
			status = newAboutCourseLocators.processCourseOutLineSection();
		} 
		catch (Exception e)
		{
			markProcessFailed();
		}
	}

	private void earnYourCertificate(String earnYourCertificateContentFromExcel, String titleName, String formatOfCertificate, String org)
	{
		try
		{
			
		}
		catch (Exception e)
		{
			markProcessFailed();
		}
	}

	private void typeofCertificate(String typeOfCertificateFromExcel)
	{
		String typeOfCertificateStatus = "fail";
		try
		{
			String checkEarnYourCertificate = newAboutCourseLocators.getTypeofCertificate(typeOfCertificateFromExcel);
			if (checkEarnYourCertificate.equals(typeOfCertificateStatus))
			{
				markProcessFailed();
			}
			else if(checkEarnYourCertificate.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void aboutCourse(String aboutCourseFromExcel)
	{
		String aboutCourseStatus = "fail";
		try
		{
			String checkAbout = newAboutCourseLocators.getAboutCourse(aboutCourseFromExcel);
			if (checkAbout.equals("notProcessed"))
			{
				markProcessIgnored();
			}
			else if(checkAbout.equals(aboutCourseStatus))
			{
				markProcessFailed();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void includes(String includesFromExcel)
	{
		String includeStatus = "fail";
		try
		{
			String checkIncludes = newAboutCourseLocators.getIncludes(includesFromExcel);
			System.out.println("Includes from excel : " + includesFromExcel);
			if(checkIncludes.equals(includeStatus))
			{
				markProcessFailed();
			}
			else if(checkIncludes.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void create(String createFromExcel)
	{
		String checkCreateStatus = "fail";
		try
		{
			String checkCreate = newAboutCourseLocators.getCreate(createFromExcel);
			if(checkCreateStatus.equals(checkCreate))
			{
				markProcessFailed();
			}
			else if(checkCreate.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exercisesToExplore(String exerciseFromExcel)
	{
		String checkExerciseToExploreStatus = "fail";
		try 
		{
			String checkExerciseToExplore = newAboutCourseLocators.getExerciseToExplore(exerciseFromExcel);
			if(checkExerciseToExplore.equals(checkExerciseToExploreStatus))
			{
				
				markProcessFailed();
			}
			else if(checkExerciseToExplore.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void experts(ArrayList<String> expertsFromExcel)
	{
		HashMap<String, HashMap<String, String>> experts = newAboutCourseLocators.getExperts();
		System.out.println("expertsFromExcel : " + expertsFromExcel);
		if (experts != null) {
			for (int i = 0; i < expertsFromExcel.size(); i++) {
				String cellData = expertsFromExcel.get(i).replaceAll("\\s", "").replaceAll("\u00A0", "")
						.replaceAll("[^\\p{ASCII}]", "");
				try {
					if (cellData.equalsIgnoreCase("experts"))
						continue;
					String[] expertData = cellData.split("-split-");
					String name = expertData[0].replaceAll("\\s", "").replaceAll("\u00A0", "")
							.replaceAll("[^\\p{ASCII}]", "");
					HashMap<String, String> expertReadFromBrowser = experts.get(name);
					if (expertReadFromBrowser != null)
					{
						for (int j = 0; j < expertData.length; j++)
						{
							String expertInfo = expertData[j];
							System.out.println("expert from browser :" + expertInfo);
							boolean isExpertInfoFound = false;
							for (Entry<String, String> entry : expertReadFromBrowser.entrySet())
							{
								if (entry.getValue().equalsIgnoreCase(expertInfo))
								{
									isExpertInfoFound = true;
									break;
								}
							}
							if (!isExpertInfoFound) {
								markColumnFailed(i);
							}
						}
					} 
					else 
					{
						markColumnFailed(i);
					}
				}
				catch (Exception e)
				{
					markProcessFailed();
					e.printStackTrace();
				}
			}
		} else {
			markProcessFailed();
		}
	}

	private void duration(String durationFromExcel)
	{
		String checkDuration = "pass";
		try {
			String checkDurationDetails = newAboutCourseLocators.getDurationInfo(durationFromExcel);
			System.out.println("duration from excel : " + durationFromExcel);
			if (checkDurationDetails.equalsIgnoreCase("successIND")) {
				markProcessIgnored();
			} else if (!checkDurationDetails.equals(checkDuration)) {
				markProcessFailed();
			}
		} catch (Exception e) {
			markProcessFailed();
		}
	}

	private void startsOn(String startsOnFromExcel)
	{
		String checkStartOn = "success";
		try
		{
			String checkStartsOnDetails = newAboutCourseLocators.getStartsOn(startsOnFromExcel);
			if (checkStartsOnDetails.equalsIgnoreCase("successIND")) 
			{
				markProcessIgnored();
			} 
			else if (!checkStartsOnDetails.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equals(checkStartOn))
			{
				markProcessFailed();
			}
		}
		catch (Exception e) 
		{
			markProcessFailed();
		}
	}

	private void flatPriceINRWithoutGST(String flatPriceWithoutGSTFromExcel) {
		String checkPrice = "pass";
		try {
			String checkFlatPrice = newAboutCourseLocators.getflatPrice(flatPriceWithoutGSTFromExcel);
			if (checkFlatPrice.equalsIgnoreCase("successIND")) {
				markProcessIgnored();
			} else if (!checkFlatPrice.equals(checkPrice)) {
				markProcessFailed();
			}
		} catch (Exception e) {
			markProcessFailed();
		}
	}

	private void flatPriceUSD(String priceUSDFromExcel) {
		String checkUSDPrice = "success";
		try {
			String checkUSDPriceStatus = newAboutCourseLocators.getUSDPrice(priceUSDFromExcel);
			if (checkUSDPriceStatus.equalsIgnoreCase("successIND")) {
				markProcessIgnored();
			} else if (!checkUSDPriceStatus.equalsIgnoreCase(checkUSDPrice)) {
				markProcessFailed();
			}
		} catch (Exception e) {
			markProcessFailed();
		}
	}

	private void category(ArrayList<String> categoryName)
	{
		String checkCategoryStatus = "success";
		try {
			ArrayList<String> verifyCategory = newAboutCourseLocators.checkCategory(categoryName);
			for (int i = 1; i < verifyCategory.size(); i++) {
				String getCategoryStatus = verifyCategory.get(i);
				if (getCategoryStatus.equalsIgnoreCase("successIND")) {
					markProcessIgnored();
				} else if (!getCategoryStatus.equalsIgnoreCase(checkCategoryStatus)) {
					markColumnFailed(i);
				}
			}
		} catch (Exception e) {
			markProcessFailed();
		}
	}

	private void launchUrlAndTestRedirection(String currentURL, String redirectURLFromExcel) 
	{
		try
		{
			String redirectedURL = newAboutCourseLocators.launchCourseURL(currentURL);
			if(!(redirectURLFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()).equals(redirectedURL.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
			{
				markProcessFailed();
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void canonical(String canonicalURL)
	{
		String getCanonicalStatus = "success";
		try
		{
			String checkCanonicalURL = newAboutCourseLocators.getCanonicalURL(canonicalURL);
			if(checkCanonicalURL.equalsIgnoreCase("successInd"))
			{
				markProcessIgnored();
			}
			else if(!getCanonicalStatus.equalsIgnoreCase(checkCanonicalURL))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	private void checkMetaTagContentByName(ArrayList<String> tags) 
	{
		for(int i = 0; i < tags.size(); i++)
		{
			String tag = tags.get(i);
			if (tag.equalsIgnoreCase("checkMetaTagContentByName"))
				continue;
			HashMap<String, String> attributes = extractAttributesFromString(tag);
			// meta[name='title']
			try
			{
				String content = newAboutCourseLocators.getAttributeOfTag("meta[name='" + attributes.get("name") + "']", "content").trim();
				if(!(content.replaceAll("\\s", "").replaceAll("\\P{InBasic_Latin}", "").replaceAll("\u00A0", "").trim()).equals(attributes.get("content").replaceAll("\\P{InBasic_Latin}", "").replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
				{
					markColumnFailed(i);
				}
			}
			catch(Exception e)
			{
				markColumnFailed(i);
			}
			
		}
	}
	
	private void checkMetaTagContentByProperty(ArrayList<String> tags)
	{
		for (int i = 0; i < tags.size(); i++)
		{
			String tag = tags.get(i);
			try
			{
				if(tag.equalsIgnoreCase("checkMetaTagContentByProperty"))
					continue;
				HashMap<String, String> attributes = extractAttributesFromString(tag);
				//property="og:url", content="https://qa.skillup.online/courses/sc-300-microsoft-identity-and-access-administrator/"
				String content = newAboutCourseLocators.getAttributeOfTag("meta[property='" + attributes.get("property") + "']", "content");
				if(!content.replaceAll("\\s", "").replaceAll("\u00A0", "").trim().equals(attributes.get("content").replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
				{
					markColumnFailed(i);
				}
			}
			catch(Exception e)
			{
				markColumnFailed(i);
			}
			
		}
	}
	
	private void checkTagWithInnerText(ArrayList<String> tags) {
		for (int i = 0; i < tags.size(); i++)
		{
			String tag = tags.get(i).replaceAll("\\s", "").replaceAll("\u00A0", "");
			if (tag.equalsIgnoreCase("checkTagWithInnerText"))
				continue;
			String[] tagAndText = tag.split("-split-");
			try {
				newAboutCourseLocators.getTagWithInnerText(tagAndText[0], tagAndText[1]);
			} catch (Exception e) {
				markColumnFailed(i);
			}
		}
	}
	
	private void checkImgSrcAndAlt(ArrayList<String> tags) 
	{
		for (int i = 0; i < tags.size(); i++)
		{
			String tag = tags.get(i);
			try
			{
				if(tag.equalsIgnoreCase("checkImgSrcAndAlt"))
					continue;
				HashMap<String, String> attributes = extractAttributesFromString(tag);
				String altText = newAboutCourseLocators.getAttributeOfTag("img[src='" + attributes.get("src") + "']", "alt");
				
				if(!altText.replaceAll("\\s", "").replaceAll("\u00A0", "").trim().equals(attributes.get("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
				{
					markColumnFailed(i);
				}
			}
			catch(Exception e)
			{
				markColumnFailed(i);
			}
		}
	}
	
	private void faq(ArrayList<String> faqRow)
	{
		String question = faqRow.get(1).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		try
		{
			ArrayList<String> answersFromParagraphs = newAboutCourseLocators.getAnswersForFAQQuestion(question);
			if(faqRow.size() - 2 == answersFromParagraphs.size())
			{
				if(faqRow.size() > 2)
				{
					for (int j = 2; j < faqRow.size(); j++) 
					{
						String answerParagraphFromExcel = faqRow.get(j).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String answerParagraphFromBrowser = answersFromParagraphs.get(j - 2);
						
						if(!answerParagraphFromExcel.equals(answerParagraphFromBrowser.replaceAll("[^\\p{ASCII}]", "")))
						{
							markColumnFailed(j);
						}
					}
				}
			}
			else
			{
				if(faqRow.size() - 2 > answersFromParagraphs.size())
				{
					System.out.println("Excel sheets has more faq answers for the question " + question);
				}
				else if(faqRow.size() - 2 < answersFromParagraphs.size())
				{
					System.out.println("Web site has has more faq answers for the question " + question);
				}
				for (int j = 2; j < faqRow.size(); j++) 
				{
					markColumnFailed(j);
				}
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void validateSchemaHeading(ArrayList<String> row)
	{
		newAboutCourseLocators.launchValidator();
		try
		{
			String headingStatus = newAboutCourseLocators.validateHeading(row.get(1));
			if(!headingStatus.equals("Success"))
			{
				markColumnFailed(1);
			}
		}
		catch(Exception e)
		{
			markColumnFailed(1);
		}
	}
	
	private void validateSchemaFAQ(ArrayList<String> row, HashMap<String, String> faqFromValidator)
	{
		try
		{
			String question = row.get(1).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			String answer = row.get(2).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			if(faqFromValidator.containsKey(question.replaceAll("\\s", "").replaceAll("\u00A0", "")))
			{
				if(!(answer.trim()).equalsIgnoreCase(faqFromValidator.get(question).trim()))
				{
					markColumnFailed(1);
				}
			}
			else
			{
				markColumnFailed(1);
			}
		}
		catch(Exception e)
		{
			markColumnFailed(1);
		}
	}
	
	private void checkRedirectStatus(String url, String statusCode1, String statusCode2)
	{
		String[] status = newAboutCourseLocators.checkRedirectStatus(url, statusCode1, statusCode2);
		if(status[0].equalsIgnoreCase("failed"))
		{
			markColumnFailed(2);
		}
		if(status[1].equalsIgnoreCase("failed"))
		{
			markColumnFailed(3);
		}
	}
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW)
				.get(columnIndex);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex,
				(cellValue + " - failed"));
		markProcessFailed();
	}

	private void markProcessFailed() {
		sheetStatus = "Fail";
		String process = TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - failed"));
	}

	private void markProcessIgnored() {
		String process = TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - ignored"));
	}

	private void markCellAsHeader() {
		String process = TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - header"));
	}

	private void collectSheetResult() {
		ArrayList<String> emptyRow = new ArrayList<>();
		emptyRow.add("");

		ArrayList<String> sheetStatusRow = new ArrayList<>();
		sheetStatusRow.add("Status" + Utils.DELIMITTER + "bold" + Utils.DELIMITTER + "backgroundlime" + Utils.DELIMITTER
				+ "border");
		sheetStatusRow.add(sheetStatus + Utils.DELIMITTER + "backgroundLT" + Utils.DELIMITTER + "color"
				+ (sheetStatus.equalsIgnoreCase("Pass") ? "Green" : "Red") + Utils.DELIMITTER + "border");

		ArrayList<String> startTimeRow = new ArrayList<>();
		startTimeRow.add("Started Time" + Utils.DELIMITTER + "bold" + Utils.DELIMITTER + "backgroundlime"
				+ Utils.DELIMITTER + "border");
		startTimeRow.add(startTime + Utils.DELIMITTER + "backgroundLT" + Utils.DELIMITTER + "border");

		ArrayList<String> endTimeRow = new ArrayList<>();
		endTimeRow.add("Ended Time" + Utils.DELIMITTER + "bold" + Utils.DELIMITTER + "backgroundlime" + Utils.DELIMITTER
				+ "border");
		endTimeRow.add(endTime + Utils.DELIMITTER + "backgroundLT" + Utils.DELIMITTER + "border");

		ArrayList<String> durationRow = new ArrayList<>();
		durationRow.add("Execution Time" + Utils.DELIMITTER + "bold" + Utils.DELIMITTER + "backgroundlime"
				+ Utils.DELIMITTER + "border");
		durationRow.add(duration + Utils.DELIMITTER + "backgroundLT" + Utils.DELIMITTER + "border");

		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestNewAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
	private HashMap<String, String> extractAttributesFromString(String str) 
	{
		HashMap<String, String> stringProps = new HashMap<String, String>();
		try
		{
			String[] arr = str.split("-split-");
			boolean checkurl = false;
			for (String keyValue : arr)
			{
				int startIndex = keyValue.indexOf("\"");
				int lastIndex = keyValue.lastIndexOf("\"");
				String key = keyValue.substring(0, startIndex - 1);
				String value = keyValue.substring(startIndex + 1, lastIndex);
				
				if(value.contains(":url") || value.contains(":image")||value.contains(".svg")||value.contains(".jpg"))
				{
					checkurl = true;
				}
				else
				{
					value = value.replaceAll("\"", "");
				}
				if(key.contains("alt") &&(checkurl == true))
				{
					checkurl = false;
				}
				
				if(checkurl == true && (!(value.contains("og:url") || value.contains("twitter:url"))))
				{
					value = this.getMetaHost+value.replaceAll("\"", "");
				}
				else if(checkurl == true && (!(value.contains("og:image") || value.contains("twitter:image"))))
				{
					value = getImageHost+value.replaceAll("\"", "");
				}
				stringProps.put(key, value);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return stringProps;
	}
}
