package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.StagecategoryLocator;
import com.seo.utility.Utils;

public class StagecategoryValidator
{

	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private StagecategoryLocator stagecategoryLocator;
	private String sheetStatus = "Pass";
	private HashMap<String, String> faqFromValidator = null;
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public StagecategoryValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		stagecategoryLocator = new StagecategoryLocator();
	}
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		stagecategoryLocator.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		stagecategoryLocator.getDriver().quit();
		endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		duration = Utils.findDifference(startTime, endTime);
		collectSheetResult();
		return sheetStatus;
	}
	private void executeProcess(String process, ArrayList<String> row)
	{

		try
		{
			switch (process) 
			{
			case "environment":
				environment(row.get(1));
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
			case "validateSchemaHeading":
				validateSchemaHeading(row);
				break;
			case "validateFAQ":
				if(null == faqFromValidator)
				{
					faqFromValidator = stagecategoryLocator.getFAQFromValidator();
				}
				validateSchemaFAQ(row, faqFromValidator);
				break;
			case "checkRedirectStatus": 
				System.out.println("checkRedirectStatus Method");
				checkRedirectStatus(row.get(1), row.get(2), row.get(3));
				break;
			case "programCard_TC1":
				showMoreIcon_Program();
				break;
			case "programCard_TC2":
				compareCardDetailsInProgramPage(row);
				break;
			case "courseCard_TC1":
				showMoreIcon_Course();
				break;
			case "courseCard_TC2":
				compareCardDetailsInCoursePage(row);
				break;
			case "courses":
				verifyCourses(row);
				break;
			default:
				break;
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	String getMetaHost;
	private void environment(String environmentFromExcel)
	{
		try
		{
			String checkEnvironment = stagecategoryLocator.setEnvironment(environmentFromExcel);
			getMetaHost = stagecategoryLocator.setMetaHostURL(environmentFromExcel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void launchUrlAndTestRedirection(String currentURL, String redirectURLFromData) 
	{
		try
		{
			String redirectedURL = stagecategoryLocator.launchCourseURL(currentURL);
			String getURL = stagecategoryLocator.getCourseURL(currentURL);
			if(!(getURL.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()).equals(redirectedURL.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
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
			String checkCanonicalURL = stagecategoryLocator.getCanonicalURL(canonicalURL);
			if(!getCanonicalStatus.equalsIgnoreCase(checkCanonicalURL))
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
				String content = stagecategoryLocator.getAttributeOfTag("meta[name='" + attributes.get("name") + "']", "content").trim();
				if(!(content.replaceAll("\\s", "").replaceAll("\\P{InBasic_Latin}", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").trim()).equals(attributes.get("content").replaceAll("\\P{InBasic_Latin}", "").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").trim()))
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
	
	
	private void verifyCourses(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> courseStatus = stagecategoryLocator.checkCourses(dataFromExcel);
		{
			for(int i = 0; i < courseStatus.size(); i++)
			{
				if(courseStatus.contains("fail"))
				{
					markProcessFailed();
				}
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
				String content = stagecategoryLocator.getAttributeOfTag("meta[property='" + attributes.get("property") + "']", "content");
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
			String tag = tags.get(i).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
			if (tag.equalsIgnoreCase("checkTagWithInnerText"))
				continue;
			String[] tagAndText = tag.split("-split-");
			try {
				stagecategoryLocator.getTagWithInnerText(tagAndText[0], tagAndText[1]);
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
				String altText = stagecategoryLocator.getAttributeOfTag("img[src='" + attributes.get("src") + "']", "alt");
				
				if(!altText.replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "").replaceAll("\u00A0", "").trim().equals(attributes.get("alt").replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
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
			ArrayList<String> answersFromParagraphs = stagecategoryLocator.getAnswersForFAQQuestion(question);
			if(faqRow.size() - 2 == answersFromParagraphs.size())
			{
				if(faqRow.size() > 2)
				{
					for (int j = 2; j < faqRow.size(); j++) 
					{
						String answerParagraphFromExcel = faqRow.get(j).replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println("Answer from excel : "+answerParagraphFromExcel);
						String answerParagraphFromBrowser = answersFromParagraphs.get(j - 2).replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						System.out.println("Answer from Browser : "+answerParagraphFromBrowser);
						if(!answerParagraphFromExcel.equals(answerParagraphFromBrowser))
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
		stagecategoryLocator.launchValidator();
		try
		{
			String headingStatus = stagecategoryLocator.validateHeading(row.get(1));
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
		String[] status = stagecategoryLocator.checkRedirectStatus(url, statusCode1, statusCode2);
		if(status[0].equalsIgnoreCase("failed"))
		{
			markColumnFailed(2);
		}
		if(status[1].equalsIgnoreCase("failed"))
		{
			markColumnFailed(3);
		}
	}
	
	private void showMoreIcon_Program()
	{
		String getProgramIconStatus = stagecategoryLocator.verifyProgramShowMoreIconStatus();
		if(getProgramIconStatus.equalsIgnoreCase("fail"))
		{
			markProcessFailed();
		}
	}
	
	private void compareCardDetailsInProgramPage(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> getCardDEtailsStatus = stagecategoryLocator.verifyProgramCardDetailsInProgramPage(dataFromExcel);
		for(int i = 0; i < getCardDEtailsStatus.size(); i++)
		{
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("fail"))
			{
				markColumnFailed(1);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("icon"))
			{
				markColumnFailed(2);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("level"))
			{
				markColumnFailed(3);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("enrollStatus"))
			{
				markColumnFailed(4);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("amount"))
			{
				markColumnFailed(5);
			}
		}
	}
	
	private void showMoreIcon_Course()
	{
		String getCourseIconStatus = stagecategoryLocator.verifyCourseShowMoreIconStatus();
		if(getCourseIconStatus.equalsIgnoreCase("fail"))
		{
			markProcessFailed();
		}
	}
	
	private void compareCardDetailsInCoursePage(ArrayList<String> dataFromExcel)
	{
		ArrayList<String> getCardDEtailsStatus = stagecategoryLocator.verifyCourseCardDetailsInCoursePage(dataFromExcel);
		for(int i = 0; i < getCardDEtailsStatus.size(); i++)
		{
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("fail"))
			{
				markColumnFailed(1);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("icon"))
			{
				markColumnFailed(2);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("level"))
			{
				markColumnFailed(3);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("enrollStatusClosed"))
			{
				markColumnFailed(4);
			}
			if(getCardDEtailsStatus.get(i).equalsIgnoreCase("amount"))
			{
				markColumnFailed(5);
			}
		}
	}
	
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
	}
	
	private void collectSheetResult()
	{
		ArrayList<String> emptyRow = new ArrayList<>();
		emptyRow.add("");
		
		ArrayList<String> sheetStatusRow = new ArrayList<>();
		sheetStatusRow.add("Status" + Utils.DELIMITTER + "bold" + 
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		sheetStatusRow.add(sheetStatus + 
				Utils.DELIMITTER + "backgroundLT" + 
				Utils.DELIMITTER + "color" + (sheetStatus.equalsIgnoreCase("Pass") ? "Green" : "Red") +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> startTimeRow = new ArrayList<>();
		startTimeRow.add("Started Time" + Utils.DELIMITTER + "bold" +
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		startTimeRow.add(startTime + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> endTimeRow = new ArrayList<>();
		endTimeRow.add("Ended Time" + Utils.DELIMITTER + "bold" +
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		endTimeRow.add(endTime + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		ArrayList<String> durationRow = new ArrayList<>();
		durationRow.add("Execution Time" + Utils.DELIMITTER + "bold" + 
				Utils.DELIMITTER + "backgroundlime" +
				Utils.DELIMITTER + "border");
		durationRow.add(duration + Utils.DELIMITTER + "backgroundLT" +
				Utils.DELIMITTER + "border");
		
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestStageCategory.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
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
				
				if(checkurl == true && (!(value.contains("og:url") || value.contains("og:image") || value.contains("twitter:url") || value.contains("twitter:image"))))
				{
					value = this.getMetaHost+value.replaceAll("\"", "");
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
