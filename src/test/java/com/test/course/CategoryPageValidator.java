package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.CategoryPageLocators;
import com.seo.utility.Utils;

public class CategoryPageValidator
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private CategoryPageLocators categoryPage;
	private String sheetStatus = "Pass";
	private HashMap<String, String> faqFromValidator = null;
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public CategoryPageValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		categoryPage = new CategoryPageLocators();
	}
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		categoryPage.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		categoryPage.getDriver().quit();
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
					faqFromValidator = categoryPage.getFAQFromValidator();
				}
				validateSchemaFAQ(row, faqFromValidator);
				break;
			case "checkRedirectStatus": System.out.println("checkRedirectStatus Method");
				checkRedirectStatus(row.get(1), row.get(2), row.get(3));
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
	
	private void launchUrlAndTestRedirection(String url, String redirectURLFromData) 
	{
		try
		{
			String redirectedURL = categoryPage.launchCourseURL(url);
			if(!(ConfigFileReader.getURL()+redirectURLFromData.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()).equals(redirectedURL.replaceAll("\\s", "").replaceAll("\u00A0", "").trim()))
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
			String checkCanonicalURL = categoryPage.getCanonicalURL(canonicalURL);
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
				String content = categoryPage.getAttributeOfTag("meta[name='" + attributes.get("name") + "']", "content").trim();
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
				String content = categoryPage.getAttributeOfTag("meta[property='" + attributes.get("property") + "']", "content");
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
				categoryPage.getTagWithInnerText(tagAndText[0], tagAndText[1]);
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
				String altText = categoryPage.getAttributeOfTag("img[src='" + attributes.get("src") + "']", "alt");
				
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
			ArrayList<String> answersFromParagraphs = categoryPage.getAnswersForFAQQuestion(question);
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
		categoryPage.launchValidator();
		try
		{
			String headingStatus = categoryPage.validateHeading(row.get(1));
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
		String[] status = categoryPage.checkRedirectStatus(url, statusCode1, statusCode2);
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
		String cellValue = TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
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
		
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestCategoryPage.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}

	private HashMap<String, String> extractAttributesFromString(String str) 
	{
		HashMap<String, String> stringProps = new HashMap<String, String>();
		try
		{
			// str = name="title"-split-content="Blockchain Essentials | Learn Blockchain?org=nasscom"
			// Essentials Course Online - SkillUp Online"
			// arr = [name="title", content="Blockchain Essentials | Learn Blockchain Essentials
			// Course Online - SkillUp Online"];
			String[] arr = str.split("-split-");
			boolean checkurl = false;
			for (String keyValue : arr) 
			{
				int startIndex = keyValue.indexOf("\"");
				int lastIndex = keyValue.lastIndexOf("\"");
				String key = keyValue.substring(0, startIndex - 1);
				String value = keyValue.substring(startIndex + 1, lastIndex);
				
				if(value.contains(":url") || value.contains(":image"))
				{
					checkurl = true;
				}
				else
				{
					value = value.replaceAll("\"", "");
				}
				if(checkurl == true && (!(value.contains("og:url") || value.contains("og:image") || value.contains("twitter:url") || value.contains("twitter:image"))))
				{
					value = ConfigFileReader.getMetaURL()+value.replaceAll("\"", "");
				}
				stringProps.put(key, value);
				
//				// keyValue = "name="title""
//				String[] pair = keyValue.split("=");
//				// pari = [name, ""title""]
//				String value = null;
//				for(int j = 0; j < pair.length; j++)
//				{
//					
//					String attribute = pair[0]; //name
//					//value = pair[1].replaceAll("\"", "");
//					if(j == 1)
//					{
//						Thread.sleep(1000);
//						value = pair[j].replaceAll("\"", "");//title
//						if(value.contains(":url") || value.contains(":image"))
//						{
//							checkurl = true;
//						}
//						else
//						{
//							value = pair[j].replaceAll("\"", "");
//						}
//						if(checkurl == true && (!(value.contains("og:url") || value.contains("og:image") || value.contains("twitter:url") || value.contains("twitter:image"))))
//						{
//							value = ConfigFileReader.getMetaURL()+pair[j].replaceAll("\"", "");
//						}
//					}
//					stringProps.put(attribute, value);//(name,"title")
//				 }
				// attribute = name
				// pari[1] = ""title""
				// pair[1].replaceAll("\"", "") = "title"
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return stringProps;
	}
}
