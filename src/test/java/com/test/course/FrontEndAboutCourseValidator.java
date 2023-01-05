package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.seo.pompages.FrontEndAboutCourseLocator;
import com.seo.pompages.NewAboutCourseLocator;
import com.seo.utility.Utils;

public class FrontEndAboutCourseValidator
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private FrontEndAboutCourseLocator frontEndAboutCourseLocator;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";

	public FrontEndAboutCourseValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName;
		this.ROWS = rows;
		frontEndAboutCourseLocator = new FrontEndAboutCourseLocator();
	}

	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		frontEndAboutCourseLocator.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++) {
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		frontEndAboutCourseLocator.getDriver().quit();
		endTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		duration = Utils.findDifference(startTime, endTime);
		collectSheetResult();
		return sheetStatus;
	}
	private void executeProcess(String process, ArrayList<String> row) 
	{
		try {
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
			case "courseDescription":
				courseDescription(row.get(1));
				break;
			case "courseType":
				courseType(row.get(1));
				break;
			case "courseLevel1":
				courseLevel1(row.get(1));
				break;
			case "courseLevel2":
				courseLevel2(row.get(1));
				break;
			case "startsOn":
				startsOn(row);
				break;
			case "duration":
				duration(row);
				break;
			case "fee":
				fee(row);
				break;
			case "enroll":
				enroll(row.get(1));
				break;
			case "overviewNavigation":
				overviewNavigation(row.get(1));
				break;
			case "outlineNavigation":
				outlineNavigation(row.get(1));
				break;
			case "skillupOnlineNavigation":
				skillupOnlineNavigation(row.get(1));
				break;
			case "FAQNavigation":
				FAQNavigation(row.get(1));
				break;
			case "programOffering":
				programOffering(row.get(1));
				break;
			case "typeOfCertificate":
				typeOfCertificate(row);
				break;
			case "aboutCourse":
				aboutCourse(row);
				break;
			case "includes":
				includes(row);
				break;
			case "create":
				create(row);
				break;
			case "exerciseToExplore":
				exerciseToExplore(row);
				break;
			case "courseOverview":
				courseOverview(row);
				break;
			case "howItWorks":
				howItWorks(row.get(1));
				break;
			case "skillsYouWillGain":
				skillsYouWillGain(row.get(1));
				break;
			case "whoShouldEnrollOnThisCourse":
				whoShouldEnrollOnThisCourse(row.get(1));
				break;
			case "prerequisites":
				prerequisites(row.get(1));
				break;
			case "earnYourCertificate":
				earnYourCertificate(row.get(1));
				break;
			case "earnYourCertificateContent":
				earnYourCertificateContent(row.get(1));
				break;
			case "expertTitle":
				expertTitle(row.get(1));
				break;
			case "expertName":
				expertName(row.get(1));
				break;
			case "expertRole":
				expertRole(row.get(1));
				break;
			case "expertLink":
				expertLink(row.get(1));
				break;
			case "NewsLetterUpdates":
				NewsLetterUpdates(row.get(1));
				break;
			case "subscribeContent":
				subscribeContent(row.get(1));
				break;
			case "fullName":
				fullName(row.get(1));
				break;
			case "email":
				email(row.get(1));
				break;
			case "subscribeButton":
				subscribeButton(row.get(1));
				break;
			case "WhyLearnSkillupOnline":
				WhyLearnSkillupOnline(row);
				break;
			case "FAQs":
				FAQs(row.get(1));
				break;
			case "question":
				question(row.get(1));
				break;
			case "answer":
				answer(row.get(1));
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
	private void environment(String environmentFromExcel)
	{
		try
		{
			String checkEnvironment = frontEndAboutCourseLocator.setEnvironment(environmentFromExcel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void courseCode(String courseCodeFromExcel)
	{
		
		String courseCodestatus = "true";
		try
		{
			String checkCourseCode = frontEndAboutCourseLocator.getCourseCodeText(courseCodeFromExcel);
			if (!(courseCodestatus).equals(checkCourseCode))
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
		String courseTitlestatus = "true";
		try
		{
			String verifyCourseCode = frontEndAboutCourseLocator.checkCourseTitle(courseTitleFromExcel);
			if (!(courseTitlestatus).equals(verifyCourseCode))
			{
				markProcessFailed();
			}
			else if(verifyCourseCode.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseDescription(String courseDescriptionFromExcel)
	{
		String courseDescriptionStatus = "true";
		try
		{
			String verifyCourseDescription = frontEndAboutCourseLocator.checkCourseDescription(courseDescriptionFromExcel);
			if (!(verifyCourseDescription).equals(courseDescriptionStatus))
			{
				markProcessFailed();
			}
			else if(verifyCourseDescription.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void courseType(String courseTypeFromExcel)
	{
		String courseTypeStatus = "true";
		try
		{
			String verifyCourseType = frontEndAboutCourseLocator.checkCourseType(courseTypeFromExcel);
			if (!(verifyCourseType).equals(courseTypeStatus))
			{
				markProcessFailed();
			}
			else if(verifyCourseType.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void courseLevel1(String courseLevelFromExcel)
	{
		String courseLevelStatus = "true";
		try
		{
			String verifyCourseLevel1 = frontEndAboutCourseLocator.checkCourseLevel1(courseLevelFromExcel);
			if (!(verifyCourseLevel1).equals(courseLevelStatus))
			{
				markProcessFailed();
			}
			else if(verifyCourseLevel1.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseLevel2(String courseLevel2FromExcel)
	{
		String courseLevel2Status = "true";
		try
		{
			String verifyCourseLevel2 = frontEndAboutCourseLocator.checkCourseLevel2(courseLevel2FromExcel);
			if (!(verifyCourseLevel2).equals(courseLevel2Status))
			{
				markProcessFailed();
			}
			else if(verifyCourseLevel2.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void startsOn(ArrayList<String> startsOnFromExcel)
	{
		try
		{
			ArrayList<String> verifyStartsOn = frontEndAboutCourseLocator.checkStartsOn(startsOnFromExcel);
			for(int i = 0; i < verifyStartsOn.size(); i++)
			{
				if(verifyStartsOn.size() == 1)
				{
					if(i == 0)
					{
						if(verifyStartsOn.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyStartsOn.size()  == 2)
				{
					if(i == 0)
					{
						if(!(verifyStartsOn.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyStartsOn.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void duration(ArrayList<String> durationFromExcel)
	{
		try
		{
			ArrayList<String> verifyDuration = frontEndAboutCourseLocator.checkDuration(durationFromExcel);
			for(int i = 0; i < verifyDuration.size(); i++)
			{
				if(verifyDuration.size() == 1)
				{
					if(i == 0)
					{
						if(verifyDuration.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyDuration.size()  == 2)
				{
					if(i == 0)
					{
						if(!(verifyDuration.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyDuration.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void fee(ArrayList<String> feeFromExcel)
	{
		try
		{
			ArrayList<String> verifyFee = frontEndAboutCourseLocator.checkFee(feeFromExcel);
			for(int i = 0; i < verifyFee.size(); i++)
			{
				if(verifyFee.size() == 1)
				{
					if(i == 0)
					{
						if(verifyFee.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyFee.size()  == 2)
				{
					if(i == 0)
					{
						if(!(verifyFee.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyFee.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void enroll(String enrollFromExcel)
	{
		String enrollStatus = "true";
		try
		{
			String verifyEnroll = frontEndAboutCourseLocator.checkEnroll(enrollFromExcel);
			if (!(verifyEnroll).equals(enrollStatus))
			{
				markProcessFailed();
			}
			else if(verifyEnroll.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void overviewNavigation(String overviewNavigationFromExcel)
	{
		String overviewNavigationStatus = "true";
		try
		{
			String verifyOverviewNavigation = frontEndAboutCourseLocator.checkOverviewNavigation(overviewNavigationFromExcel);
			if (!(verifyOverviewNavigation).equals(overviewNavigationStatus))
			{
				markProcessFailed();
			}
			else if(verifyOverviewNavigation.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void outlineNavigation(String outlineNavigationFromExcel)
	{
		String outlineNavigationStatus = "true";
		try
		{
			String verifyoutlineNavigation = frontEndAboutCourseLocator.checkOutlineNavigation(outlineNavigationFromExcel);
			if (!(verifyoutlineNavigation).equals(outlineNavigationStatus))
			{
				markProcessFailed();
			}
			else if(verifyoutlineNavigation.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void skillupOnlineNavigation(String skillupOnlineNavigationFromExcel)
	{
		String skillupOnlineNavigationStatus = "true";
		try
		{
			String verifySkillupOnlineNavigation = frontEndAboutCourseLocator.checkSkillupOnlineNavigation(skillupOnlineNavigationFromExcel);
			if (!(verifySkillupOnlineNavigation).equals(skillupOnlineNavigationStatus))
			{
				markProcessFailed();
			}
			else if(verifySkillupOnlineNavigation.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void FAQNavigation(String FAQNavigationFromExcel)
	{
		String FAQNavigationStatus = "true";
		try
		{
			String verifyFAQNavigation = frontEndAboutCourseLocator.checkFAQNavigation(FAQNavigationFromExcel);
			if (!(verifyFAQNavigation).equals(FAQNavigationStatus))
			{
				markProcessFailed();
			}
			else if(verifyFAQNavigation.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void programOffering(String programOfferingFromExcel)
	{
		String programOfferingStatus = "true";
		try
		{
			String verifyProgramOffering = frontEndAboutCourseLocator.checkProgramOffering(programOfferingFromExcel);
			if (!(verifyProgramOffering).equals(programOfferingStatus))
			{
				markProcessFailed();
			}
			else if(verifyProgramOffering.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void typeOfCertificate(ArrayList<String> typeOfCertificateFromExcel)
	{
		try
		{
			ArrayList<String> verifyTypeOfCertificate = frontEndAboutCourseLocator.checkTypeOfCertificate(typeOfCertificateFromExcel);
			for(int i = 0; i < verifyTypeOfCertificate.size(); i++)
			{
				if(verifyTypeOfCertificate.size() == 1)
				{
					if(i == 0)
					{
						if(verifyTypeOfCertificate.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyTypeOfCertificate.size()  == 2)
				{
					if(i == 0)
					{
						if(!(verifyTypeOfCertificate.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyTypeOfCertificate.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void aboutCourse(ArrayList<String> aboutCourseFromExcel)
	{
		try
		{
			ArrayList<String> verifyAboutCourse = frontEndAboutCourseLocator.checkAboutCourse(aboutCourseFromExcel);
			for(int i = 0; i < verifyAboutCourse.size(); i++)
			{
				if(verifyAboutCourse.size() == 1)
				{
					if(i == 0)
					{
					if(verifyAboutCourse.get(i).equalsIgnoreCase("notProcessed"))
					{
						markProcessIgnored();
					}
					}
				}
				else if(verifyAboutCourse.size()  == 2)
				{
					if(i == 0)
					{
						if(!(verifyAboutCourse.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						
						if(!(verifyAboutCourse.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
				
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void includes(ArrayList<String> includesFromExcel)
	{
		try
		{
			ArrayList<String> verifyIncludes = frontEndAboutCourseLocator.checkIncludes(includesFromExcel);
			for(int i = 0; i < verifyIncludes.size(); i++)
			{
				if(verifyIncludes.size() == 1)
				{
					if(i == 0)
					{
						
						if(verifyIncludes.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyIncludes.size() == 2)
				{
					if(i == 0)
					{
						
						if(!(verifyIncludes.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyIncludes.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void create(ArrayList<String> createFromExcel)
	{
		try
		{
			ArrayList<String> verifyCreate = frontEndAboutCourseLocator.checkCreate(createFromExcel);
			for(int i = 0; i < verifyCreate.size(); i++)
			{
				if(verifyCreate.size() == 1)
				{
					if(i == 0)
					{
						if(verifyCreate.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyCreate.size() == 2)
				{
					if(i == 0)
					{
						if(!(verifyCreate.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyCreate.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void exerciseToExplore(ArrayList<String> exerciseToExploreFromExcel)
	{
		try
		{
			ArrayList<String> verifyExerciseToExplore = frontEndAboutCourseLocator.checkCreate(exerciseToExploreFromExcel);
			for(int i = 0; i < verifyExerciseToExplore.size(); i++)
			{
				if(verifyExerciseToExplore.size() == 1)
				{
					if(i == 0) {
						
						if(verifyExerciseToExplore.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyExerciseToExplore.size() == 2)
				{
					if(i == 0)
					{
						if(!(verifyExerciseToExplore.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 1)
					{
						if(!(verifyExerciseToExplore.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void courseOverview(ArrayList<String> courseOverviewFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseOverview = frontEndAboutCourseLocator.checkCourseOverview(courseOverviewFromExcel);
			for(int i = 0; i < verifyCourseOverview.size(); i++)
			{
				if(verifyCourseOverview.size() == 1)
				{
					if(i == 1)
					{
						if(verifyCourseOverview.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyCourseOverview.size() == 2)
				{
					if(i == 1)
					{
						if(!(verifyCourseOverview.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 2)
					{
						if(!(verifyCourseOverview.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void howItWorks(String howItWorksFromExcel)
	{
		String howItWorksStatus = "true";
		try
		{
			String verifyHowItWorks = frontEndAboutCourseLocator.checkHowItWorks(howItWorksFromExcel);
			if (!(verifyHowItWorks).equals(howItWorksStatus))
			{
				markProcessFailed();
			}
			else if(verifyHowItWorks.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void skillsYouWillGain(String skillsYouWillGainFromExcel)
	{
		String skillsYouWillGainStatus = "true";
		try
		{
			String verifySkillsYouWillGain = frontEndAboutCourseLocator.checkSkillsYouWillGain(skillsYouWillGainFromExcel);
			if (!(verifySkillsYouWillGain).equals(skillsYouWillGainStatus))
			{
				markProcessFailed();
			}
			else if(verifySkillsYouWillGain.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whoShouldEnrollOnThisCourse(String whoShouldEnrollOnThisCourseFromExcel)
	{
		String whoShouldEnrollOnThisCourseStatus = "true";
		try
		{
			String verifyWhoShouldEnrollOnThisCourse = frontEndAboutCourseLocator.checkWhoShouldEnrollOnThisCourse(whoShouldEnrollOnThisCourseFromExcel);
			if (!(verifyWhoShouldEnrollOnThisCourse).equals(whoShouldEnrollOnThisCourseStatus))
			{
				markProcessFailed();
			}
			else if(verifyWhoShouldEnrollOnThisCourse.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void prerequisites(String prerequisitesFromExcel)
	{
		String prerequisitesStatus = "true";
		try
		{
			String verifyPrerequisites = frontEndAboutCourseLocator.checkPrerequisites(prerequisitesFromExcel);
			if (!(verifyPrerequisites).equals(prerequisitesStatus))
			{
				markProcessFailed();
			}
			else if(verifyPrerequisites.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void earnYourCertificate(String earnYourCertificateFromExcel)
	{
		String earnYourCertificateContentStatus = "true";
		try
		{
			String verifyEarnYourCertificate = frontEndAboutCourseLocator.checkEarnYourCertificate(earnYourCertificateFromExcel);
			if (!(verifyEarnYourCertificate).equals(earnYourCertificateContentStatus))
			{
				markProcessFailed();
			}
			else if(verifyEarnYourCertificate.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void earnYourCertificateContent(String earnYourCertificateContentFromExcel)
	{
		String earnYourCertificateContentStatus = "true";
		try
		{
			String verifyEarnYourCertificateContent = frontEndAboutCourseLocator.checkEarnYourCertificateContent(earnYourCertificateContentFromExcel);
			if (!(verifyEarnYourCertificateContent).equals(earnYourCertificateContentStatus))
			{
				markProcessFailed();
			}
			else if(verifyEarnYourCertificateContent.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void expertTitle(String expertTitleFromExcel)
	{
		String expertTitleStatus = "true";
		try
		{
			String verifyExpertTitle = frontEndAboutCourseLocator.checkExpertTitle(expertTitleFromExcel);
			if (!(verifyExpertTitle).equals(expertTitleStatus))
			{
				markProcessFailed();
			}
			else if(verifyExpertTitle.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertName(String expertNameFromExcel)
	{
		String expertNameStatus = "true";
		try
		{
			String verifyExpertName = frontEndAboutCourseLocator.checkExpertName(expertNameFromExcel);
			if (!(verifyExpertName).equals(expertNameStatus))
			{
				markProcessFailed();
			}
			else if(verifyExpertName.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertRole(String expertRoleFromExcel)
	{
		String expertRoleStatus = "true";
		try
		{
			String verifyExpertRole = frontEndAboutCourseLocator.checkExpertRole(expertRoleFromExcel);
			if (!(verifyExpertRole).equals(expertRoleFromExcel))
			{
				markProcessFailed();
			}
			else if(verifyExpertRole.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertLink(String expertLinkFromExcel)
	{
		String expertLinkStatus = "true";
		try
		{
			String verifyExpertLink = frontEndAboutCourseLocator.checkExpertLink(expertLinkFromExcel);
			if (!(verifyExpertLink).equals(expertLinkStatus))
			{
				markProcessFailed();
			}
			else if(verifyExpertLink.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void NewsLetterUpdates(String NewsLetterUpdatesFromExcel)
	{
		String NewsLetterUpdatesStatus = "true";
		try
		{
			String verifyNewsLetterUpdates = frontEndAboutCourseLocator.checkNewsLetterUpdates(NewsLetterUpdatesFromExcel);
			if (!(verifyNewsLetterUpdates).equals(NewsLetterUpdatesStatus))
			{
				markProcessFailed();
			}
			else if(verifyNewsLetterUpdates.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void subscribeContent(String subscribeContentFromExcel)
	{
		String subscribeContentStatus = "true";
		try
		{
			String verifySubscribeContent = frontEndAboutCourseLocator.checkSubscribeContent(subscribeContentFromExcel);
			if (!(verifySubscribeContent).equals(subscribeContentStatus))
			{
				markProcessFailed();
			}
			else if(verifySubscribeContent.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void fullName(String fullNameFromExcel)
	{
		String fullNameStatus = "true";
		try
		{
			String verifyFullName = frontEndAboutCourseLocator.checkFullName(fullNameFromExcel);
			if (!(verifyFullName).equals(fullNameStatus))
			{
				markProcessFailed();
			}
			else if(verifyFullName.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void email(String emailFromExcel)
	{
		String emailStatus = "true";
		try
		{
			String verifyEmail = frontEndAboutCourseLocator.checkEmail(emailFromExcel);
			if (!(verifyEmail).equals(emailStatus))
			{
				markProcessFailed();
			}
			else if(verifyEmail.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void subscribeButton(String subscribeButtonFromExcel)
	{
		String subscribeButtonStatus = "true";
		try
		{
			String verifySubscribeButton = frontEndAboutCourseLocator.checkSubscribeButton(subscribeButtonFromExcel);
			if (!(verifySubscribeButton).equals(subscribeButtonStatus))
			{
				markProcessFailed();
			}
			else if(verifySubscribeButton.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void WhyLearnSkillupOnline(ArrayList<String> WhyLearnSkillupOnlineFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnline = frontEndAboutCourseLocator.checkWhyLearnSkillupOnline(WhyLearnSkillupOnlineFromExcel);
			for(int i = 0; i < verifyWhyLearnSkillupOnline.size(); i++)
			{
				if(verifyWhyLearnSkillupOnline.size() == 1)
				{
					if(i == 1)
					{
						if(verifyWhyLearnSkillupOnline.get(i).equalsIgnoreCase("notProcessed"))
						{
							markProcessIgnored();
						}
					}
				}
				else if(verifyWhyLearnSkillupOnline.size() == 8)
				{
					if(i == 1)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markProcessFailed();
						}
					}
					if(i == 2)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
					if(i == 3)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
					if(i == 4)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
					if(i == 5)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
					if(i == 6)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
					if(i == 7)
					{
						if(!(verifyWhyLearnSkillupOnline.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i);
						}
					}
				}
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void FAQs(String FAQsFromExcel)
	{
		String FAQsStatus = "true";
		try
		{
			String verifyFAQs = frontEndAboutCourseLocator.checkFAQs(FAQsFromExcel);
			if (!(verifyFAQs).equals(FAQsFromExcel))
			{
				markProcessFailed();
			}
			else if(verifyFAQs.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void question(String questionFromExcel)
	{
		String questionStatus = "true";
		try
		{
			String verifyQuestion = frontEndAboutCourseLocator.checkQuestion(questionFromExcel);
			if (!(verifyQuestion).equals(questionFromExcel))
			{
				markProcessFailed();
			}
			else if(verifyQuestion.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void answer(String answerFromExcel)
	{
		String answerStatus = "true";
		try
		{
			String verifyAnswer = frontEndAboutCourseLocator.checkAnswer(answerFromExcel);
			if (!(verifyAnswer).equals(answerFromExcel))
			{
				markProcessFailed();
			}
			else if(verifyAnswer.equals("notProcessed"))
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void markColumnFailed(int columnIndex) {
		String cellValue = TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW)
				.get(columnIndex);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(columnIndex,
				(cellValue + " - failed"));

		markProcessFailed();
	}

	private void markProcessFailed() {
		sheetStatus = "Fail";
		String process = TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - failed"));
	}

	private void markProcessIgnored() {
		String process = TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - ignored"));
	}

	private void markCellAsHeader() {
		String process = TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
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

		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(emptyRow);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(sheetStatusRow);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(startTimeRow);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(endTimeRow);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).add(durationRow);
	}

}
