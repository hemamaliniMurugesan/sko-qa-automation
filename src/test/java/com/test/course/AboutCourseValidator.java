package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
					earnYourCertificate(row.get(1), row.get(2) , row.get(3), row.get(4));
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
				default:
					markCellAsHeader();
					break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
			System.out.println("Title from excel : "+courseTitleFromExcel);
			if(!checkCourseTitle.equals(courseTitleFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			String checkCourseOrg = aboutCourseLocators.getCourseOrganizationImgAltText(courseOrganizationFromExcel);
			System.out.println("course org from Excel : "+courseOrganizationFromExcel);
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
			System.out.println("Course description from excel : "+courseDescriptionFromExcel);
			if(!checkCourseDes.equals(courseDescriptionFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("Course Type1 from excel : "+courseType1FromExcel);
			if(!checkCourseType.equals(courseType1FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("courseType2 from Excel : " +courseType2FromExcel);
			if(!checkCourseType.equals(courseType2FromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("Course level from excel : "+courseLevelFromExcel);
			if(!checkCourseLevel.equals(courseLevelFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
						String infoContentFromExcel = infoValues.get(j)/*
																		 * .replaceAll("\\s", "").replaceAll("\u00A0",
																		 * "").replaceAll("[^\\p{ASCII}]", "")
																		 */;
						System.out.println("answer from excel : "+infoContentFromExcel);
						String infoContentFromBrowser = infoContentFromCourse
								.get(j - 2);
						System.out.println("answer from Browser : "+infoContentFromBrowser);
						if(!infoContentFromExcel.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase(infoContentFromBrowser.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
	
	private void earnYourCertificate(String earnYourCertificateContentFromExcel,
			String titleName , String formatOfCertificate, String org)
	{// validating logo, format of certificate
		try
		{
			List<Integer> errorCells = aboutCourseLocators.getEarnCertificateText(earnYourCertificateContentFromExcel,
					titleName , formatOfCertificate, org);
			System.out.println("earnYourCertificateContentFromExcel : "+titleName);
			for(Integer cellIndex: errorCells)
			{
				markColumnFailed(cellIndex);
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
			System.out.println("typeofCertificate from excel : "+typeOfCertificateFromExcel);
			if(!checkEarnYourCertificate.equals(typeOfCertificateFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("About Icon from Excel : "+aboutCourseFromExcel);
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
			System.out.println("Includes from excel : "+includesFromExcel);
			if(!checkIncludes.equals(includesFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("create from Excel :"+checkCreate);
			if(!checkCreate.equals(createFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
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
			System.out.println("exercisesToExplore from excel : "+exerciseFromExcel);
			if(!checkExerciseToExplore.equals(exerciseFromExcel.replaceAll("\\s","").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "")))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void experts(ArrayList<String> expertsFromExcel)
	{
		HashMap<String, HashMap<String, String>> experts = aboutCourseLocators.getExperts();
		System.out.println("expertsFromExcel : "+expertsFromExcel);
		if(experts != null)
		{
			for(int i = 0; i < expertsFromExcel.size(); i++)
			{
				String cellData = expertsFromExcel.get(i).replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
				try
				{
					if(cellData.equalsIgnoreCase("experts"))
						continue;
					String[] expertData = cellData.split("-split-");
					String name = expertData[0].replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "");
					HashMap<String, String> expertReadFromBrowser = experts.get(name);
					if(expertReadFromBrowser != null)
					{
						for(int j = 0; j < expertData.length; j++)
						{
							String expertInfo = expertData[j];
							System.out.println("expert from browser :"+expertInfo);
							boolean isExpertInfoFound = false;
							for(Entry<String, String> entry: expertReadFromBrowser.entrySet())
							{
								if(entry.getValue().equalsIgnoreCase(expertInfo))
								{
									isExpertInfoFound = true;
									break;
								}
							}
							if(!isExpertInfoFound)
							{
								markColumnFailed(i);
							}
						}
					}
					else
					{
						markColumnFailed(i);
					}
				}
				catch(Exception e)
				{
					markProcessFailed();
					e.printStackTrace();
				}
			}
		}
		else
		{
			markProcessFailed();
		}
	}
	
	private void duration(String durationFromExcel)
	{
		String checkDuration = "success";
		try
		{
			String checkDurationDetails = aboutCourseLocators.getDurationInfo(durationFromExcel);
			System.out.println("duration from excel : "+durationFromExcel);
			if(checkDurationDetails.equalsIgnoreCase("successIND"))
			{
				markProcessIgnored();
			}
			else if(!checkDurationDetails.equals(checkDuration))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void startsOn(String startsOnFromExcel)
	{
		String checkStartOn = "success";
		try
		{
			String checkStartsOnDetails = aboutCourseLocators.getStartsOn(startsOnFromExcel);
			if(checkStartsOnDetails.equalsIgnoreCase("successIND"))
			{
				markProcessIgnored();
			}
			else if(!checkStartsOnDetails.replaceAll("\\s", "").replaceAll("\u00A0", "").replaceAll("[^\\p{ASCII}]", "").equals(checkStartOn))
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
			if(checkFlatPrice.equalsIgnoreCase("successIND"))
			{
				markProcessIgnored();
			}
			else if(!checkFlatPrice.equals(checkPrice))
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
			if(checkUSDPriceStatus.equalsIgnoreCase("successIND"))
			{
				markProcessIgnored();
			}
			else if(!checkUSDPriceStatus.equalsIgnoreCase(checkUSDPrice))
			{
				markProcessFailed();
			}
		}
		catch(Exception e)
		{
			markProcessFailed();
		}
	}
	
	private void category(ArrayList<String> categoryName)
	{
		String checkCategoryStatus = "success";
		try
		{
			ArrayList<String> verifyCategory = aboutCourseLocators.checkCategory(categoryName);
			for(int i = 1; i < verifyCategory.size(); i++)
			{
				String getCategoryStatus = verifyCategory.get(i);
				if(getCategoryStatus.equalsIgnoreCase("successIND"))
				{
					markProcessIgnored();
				}
				else if(!getCategoryStatus.equalsIgnoreCase(checkCategoryStatus))
				{
				  markProcessFailed();
				}
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
	
	private void markProcessIgnored()
	{
		String process = TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - ignored"));
	}
	
	private void markCellAsHeader()
	{
		String process = TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0, (process + " - header"));
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
		
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}
}
