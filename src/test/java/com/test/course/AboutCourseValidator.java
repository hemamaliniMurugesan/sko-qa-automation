package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.AboutCourseLocators;
import com.seo.utility.Utils;

public class AboutCourseValidator 
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private AboutCourseLocators aboutCourseLocators;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";
	
	public AboutCourseValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName; 
		this.ROWS = rows;
		aboutCourseLocators = new AboutCourseLocators();
	}
	
	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		aboutCourseLocators.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++)
		{
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		aboutCourseLocators.getDriver().quit();
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
				case "courseOutline":
					courseOutline();
					break;
				case "earnYourCertificate":
					earnYourCertificate(row.get(1), row.get(2));
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
					experts(row.get(1));
					break;
				case "flatPriceINRWithoutGST":
					flatPriceINRWithoutGST(row.get(1));
					break;
				case "flatPriceUSD":
					flatPriceUSD(row.get(1));
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
	
	private void courseCode(String courseCodeFromExcel)
	{
		String courseCodestatus = "true";
		try
		{
			String checkCourseCode = aboutCourseLocators.getCourseCodeText(courseCodeFromExcel);
			if(!(courseCodestatus).equals(checkCourseCode))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void courseTitle(String courseTitleFromExcel)
	{
		try
		{
			String checkCourseTitle = aboutCourseLocators.getCourseTitleText();
			if(!checkCourseTitle.equalsIgnoreCase(courseTitleFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void courseOrganization(String courseOrganizationFromExcel)
	{
		try
		{
			String checkCourseOrg = aboutCourseLocators.getCourseOrganizationImgAltText();
			if(!checkCourseOrg.contains(courseOrganizationFromExcel))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void courseDescription(String courseDescriptionFromExcel)
	{
		try
		{
			String checkCourseDes = aboutCourseLocators.getCourseDescription();
			if(!checkCourseDes.equalsIgnoreCase(courseDescriptionFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void courseType1(String courseType1FromExcel)
	{
		try
		{
			String checkCourseType = aboutCourseLocators.getCourseType1();
			if(!checkCourseType.equalsIgnoreCase(courseType1FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void courseType2(String courseType2FromExcel)
	{
		try
		{
			String checkCourseType = aboutCourseLocators.getCourseType2();
			if(!checkCourseType.equalsIgnoreCase(courseType2FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void courseLevel(String courseLevelFromExcel)
	{
		try
		{
			String checkCourseLevel = aboutCourseLocators.getCourseLevel();
			if(!checkCourseLevel.equalsIgnoreCase(courseLevelFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
			
		}
	}
	
	private void courseInformation(ArrayList<String> infoValues)
	{
		String infoHeadingFromExcel = infoValues.get(1).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
		try
		{
			ArrayList<String> infoContentFromCourse = aboutCourseLocators.getInfoContentFromCourse(infoHeadingFromExcel);
			if(infoValues.size() - 2 == infoContentFromCourse.size())
			{
				if(infoValues.size() > 2)
				{
					for(int j = 2; j < infoValues.size(); j++)
					{
						String infoContentFromExcel = infoValues.get(j).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
						String infoContentFromBrowser = infoContentFromCourse.get(j - 2);
						
						if(!infoContentFromExcel.equals(infoContentFromBrowser.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
						{
							markColumnFailed(j);
						}
					}
				}
			}
			else
			{
				if(infoValues.size() - 2 > infoContentFromCourse.size())
				{
					System.out.println("Excel sheets has more faq answers for the question " + infoHeadingFromExcel);
				}
				else if(infoValues.size() - 2 < infoContentFromCourse.size())
				{
					System.out.println("Web site has has more faq answers for the question " + infoHeadingFromExcel);
				}
				for (int j = 2; j < infoValues.size(); j++) 
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
	
	private void courseOutline()
	{
		try
		{
			aboutCourseLocators.processCourseOutLineSection(null);
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void earnYourCertificate(String earnYourCertificateContentFromExcel, String titleName)
	{
		try
		{
			String checkEarnYourCertificate = aboutCourseLocators.getEarnCertificateText(earnYourCertificateContentFromExcel);
			if(!checkEarnYourCertificate.contains(titleName.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void typeofCertificate(String typeOfCertificateFromExcel)
	{
		try
		{
			String checkEarnYourCertificate = aboutCourseLocators.getTypeofCertificate();
			if(!checkEarnYourCertificate.equalsIgnoreCase(typeOfCertificateFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void aboutCourse(String aboutCourseFromExcel)
	{
		try
		{
			String checkAbout = aboutCourseLocators.getAboutCourse();
			if(!checkAbout.contains(aboutCourseFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void includes(String includesFromExcel)
	{
		try
		{
			String checkIncludes = aboutCourseLocators.getIncludes();
			if(!checkIncludes.equalsIgnoreCase(includesFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void create(String createFromExcel)
	{
		try
		{
			String checkCreate = aboutCourseLocators.getCreate();
			if(!checkCreate.equalsIgnoreCase(createFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void exercisesToExplore(String exerciseFromExcel)
	{
		try
		{
			String checkExerciseToExplore = aboutCourseLocators.getExerciseToExplore();
			if(!checkExerciseToExplore.equalsIgnoreCase(exerciseFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void experts(String expertsFromExcel)
	{
		try
		{
			String checkExperts = aboutCourseLocators.getExperts();
			if(!checkExperts.equalsIgnoreCase(expertsFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void flatPriceINRWithoutGST(String flatPriceWithoutGSTFromExcel)
	{
		String checkPrice = "success";
		try
		{
			String checkFlatPrice = aboutCourseLocators.getflatPrice(flatPriceWithoutGSTFromExcel);
			if(!checkFlatPrice.equalsIgnoreCase(checkPrice))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void flatPriceUSD(String priceUSDFromExcel)
	{
		String checkUSDPrice = "success";
		try
		{
			String checkUSDPriceStatus = aboutCourseLocators.getUSDPrice(priceUSDFromExcel);
			if(!checkUSDPriceStatus.equalsIgnoreCase(checkUSDPrice))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	private void markColumnFailed(int columnIndex)
	{
		String cellValue = TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(columnIndex);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex, (cellValue + " - failed"));
		
		markProcessFailed();
	}
	
	private void markProcessFailed()
	{
		sheetStatus = "Fail";
		String process = TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - failed"));
	}
	private void collectSheetResult()
	{
		ArrayList<String> emptyRow = new ArrayList<>();
		emptyRow.add("");
		
		ArrayList<String> sheetStatusRow = new ArrayList<>();
		sheetStatusRow.add("Status" + Utils.STYLE_DELIMITTER + "bold" + 
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		sheetStatusRow.add(sheetStatus + 
				Utils.STYLE_DELIMITTER + "backgroundLT" + 
				Utils.STYLE_DELIMITTER + "color" + (sheetStatus.equalsIgnoreCase("Pass") ? "Green" : "Red") +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> startTimeRow = new ArrayList<>();
		startTimeRow.add("Started Time" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		startTimeRow.add(startTime + Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> endTimeRow = new ArrayList<>();
		endTimeRow.add("Ended Time" + Utils.STYLE_DELIMITTER + "bold" +
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		endTimeRow.add(endTime + Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		ArrayList<String> durationRow = new ArrayList<>();
		durationRow.add("Execution Time" + Utils.STYLE_DELIMITTER + "bold" + 
				Utils.STYLE_DELIMITTER + "backgroundlime" +
				Utils.STYLE_DELIMITTER + "border");
		durationRow.add(duration + Utils.STYLE_DELIMITTER + "backgroundLT" +
				Utils.STYLE_DELIMITTER + "border");
		
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
}
