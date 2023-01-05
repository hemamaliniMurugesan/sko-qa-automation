package com.test.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.seo.pompages.FrontEndAboutPageLocator;
import com.seo.utility.Utils;

public class FrontEndAboutPageValidator
{
	private String SHEET_NAME;
	private ArrayList<ArrayList<String>> ROWS;
	private int CURRENT_ROW = 0;
	private FrontEndAboutPageLocator frontEndAboutPageLocator;
	private String sheetStatus = "Pass";
	private String startTime = "";
	private String endTime = "";
	private String duration = "";

	public FrontEndAboutPageValidator(String sheetName, ArrayList<ArrayList<String>> rows)
	{
		this.SHEET_NAME = sheetName;
		this.ROWS = rows;
		frontEndAboutPageLocator = new FrontEndAboutPageLocator();
	}

	public String processSheetData()
	{
		startTime = new SimpleDateFormat(Utils.DEFAULT_DATA_FORMAT).format(Calendar.getInstance().getTime());
		frontEndAboutPageLocator.openDriver();
		for (CURRENT_ROW = 0; CURRENT_ROW < ROWS.size(); CURRENT_ROW++) {
			ArrayList<String> currentRow = ROWS.get(CURRENT_ROW);
			String process = currentRow.get(0);
			executeProcess(process, currentRow);
		}
		frontEndAboutPageLocator.getDriver().quit();
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
				courseTitle(row);
				break;
			case "courseDescription":
				courseDescription(row);
				break;
			case "courseType":
				courseType(row);
				break;
			case "courseLevel1":
				courseLevel1(row);
				break;
			case "courseLevel2":
				courseLevel2(row);
				break;
			case"startsOnIcon":
				startsOnIcon(row);
				break;
			case "startsOnHeading":
				startsOnHeading(row);
				break;
			case "startsOnContent":
				startsOnContent(row);
				break;
			case"durationIcon":
				durationIcon(row);
				break;
			case "durationHeading":
				durationHeading(row);
				break;
			case "durationContent":
				durationContent(row);
				break;
			case"feeIcon":
				feeIcon(row);
				break;
			case "feeHeading":
				feeHeading(row);
				break;
			case "feeContent":
				feeContent(row);
				break;
			case "enroll":
				enroll(row);
				break;
			case "consultation":
				consultation(row);
				break;
			case "overviewNavigation":
				overviewNavigation(row);
				break;
			case "outlineNavigation":
				outlineNavigation(row);
				break;
			case "skillupOnlineNavigation":
				skillupOnlineNavigation(row);
				break;
			case "FAQNavigation":
				FAQNavigation(row);
				break;
			case"enrollNavigation":
				enrollNavigation(row);
				break;
			case"consultationNavigation":
				consultationNavigation(row);
				break;
			case "programOffering":
				programOffering(row);
				break;
			case "typeOfCertificateHeading":
				typeOfCertificateHeading(row);
				break;
			case "typeOfCertificateContent":
				typeOfCertificateContent(row);
				break;
			case "aboutCourseHeading":
				aboutCourseHeading(row);
				break;
			case "aboutCourseContent":
				aboutCourseContent(row);
				break;
			case "includesHeading":
				includesHeading(row);
				break;
			case "includesContent":
				includesContent(row);
				break;
			case "createHeading":
				createHeading(row);
				break;
			case "createContent":
				createContent(row);
				break;
			case "exerciseToExploreHeading":
				exerciseToExploreHeading(row);
				break;
			case "exerciseToExploreContent":
				exerciseToExploreContent(row);
				break;
			case "courseOverviewHeading":
				courseOverviewHeading(row);
				break;
			case "courseOverviewContent":
				courseOverviewContent(row);
				break;
			case "howItWorks":
				howItWorks(row);
				break;
			case "skillsYouWillGain":
				skillsYouWillGain(row);
				break;
			case "whoShouldEnrollOnThisCourse":
				whoShouldEnrollOnThisCourse(row);
				break;
			case "prerequisites":
				prerequisites(row);
				break;
			case "earnYourCertificateHeading":
				earnYourCertificateHeading(row);
				break;
			case "earnYourCertificateContent":
				earnYourCertificateContent(row);
				break;
			case "expertTitle":
				expertTitle(row);
				break;
			case "expertName":
				expertName(row);
				break;
			case "expertRole":
				expertRole(row);
				break;
			case "expertLink":
				expertLink(row);
				break;
			case "newsLetterUpdates":
				newsLetterUpdates(row);
				break;
			case "subscribeContent":
				subscribeContent(row);
				break;
			case "fullName":
				fullName(row);
				break;
			case "email":
				email(row);
				break;
			case "subscribeButton":
				subscribeButton(row);
				break;
			case "whyLearnSkillupOnlineHeading":
				whyLearnSkillupOnlineHeading(row);
				break;
			case "whyLearnSkillupOnlineContent":
				whyLearnSkillupOnlineContent(row);
				break;
			case "whyLearnSkillupOnlineTickIcon":
				whyLearnSkillupOnlineTickIcon(row);
				break;
			case "whyLearnSkillupOnlineTickIconContent":
				whyLearnSkillupOnlineTickIconContent(row);
				break;
			case "whyLearnSkillupOnlineProfitPoint":
				whyLearnSkillupOnlineProfitPoint(row);
				break;
			case "whyLearnSkillupOnlineProfitPointHeading":
				whyLearnSkillupOnlineProfitPointHeading(row);
				break;
			case "whyLearnSkillupOnlineProfitPointContent":
				whyLearnSkillupOnlineProfitPointContent(row);
				break;
			case "FAQs":
				FAQs(row);
				break;
			case "question":
				question(row);
				break;
			case "answer":
				answer(row);
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
			String checkEnvironment = frontEndAboutPageLocator.setEnvironment(environmentFromExcel);
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
			String checkCourseCode = frontEndAboutPageLocator.getCourseCodeText(courseCodeFromExcel);
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
	

	
	private void courseTitle(ArrayList<String> courseTitleFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseTitle = courseTitleFromExcel;
			if(!verifyCourseTitle.contains("NA"))
			{
				verifyCourseTitle = frontEndAboutPageLocator.checkCourseTitle(courseTitleFromExcel);
				for(int i = 0; i < verifyCourseTitle.size(); i++)
				{
					if(!(verifyCourseTitle.size() == 1))
					{
						if(!(verifyCourseTitle.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void courseDescription(ArrayList<String> courseDescriptionFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseDescription = courseDescriptionFromExcel;
			if(!verifyCourseDescription.contains("NA"))
			{
				verifyCourseDescription = frontEndAboutPageLocator.checkCourseDescription(courseDescriptionFromExcel);
				for(int i = 0; i < verifyCourseDescription.size(); i++)
				{
					if(!(verifyCourseDescription.size() == 1))
					{
						if(!(verifyCourseDescription.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseType(ArrayList<String> courseTypeFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseType = courseTypeFromExcel;
			if(!verifyCourseType.contains("NA"))
			{
				verifyCourseType = frontEndAboutPageLocator.checkCourseType(courseTypeFromExcel);
				for(int i = 0; i < verifyCourseType.size(); i++)
				{
					if(!(verifyCourseType.size() == 1))
					{
						if(!(verifyCourseType.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseLevel1(ArrayList<String> courseLevel1FromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseLevel1 = courseLevel1FromExcel;
			if(!verifyCourseLevel1.contains("NA"))
			{
				verifyCourseLevel1 = frontEndAboutPageLocator.checkCourseLevel1(courseLevel1FromExcel);
				for(int i = 0; i < verifyCourseLevel1.size(); i++)
				{
					if(!(verifyCourseLevel1.size() == 1))
					{
						if(!(verifyCourseLevel1.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseLevel2(ArrayList<String> courseLevel2FromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseLeve2 = courseLevel2FromExcel;
			if(!verifyCourseLeve2.contains("NA"))
			{
				verifyCourseLeve2 = frontEndAboutPageLocator.checkCourseLevel2(courseLevel2FromExcel);
				for(int i = 0; i < verifyCourseLeve2.size(); i++)
				{
					if(!(verifyCourseLeve2.size() == 1))
					{
						if(!(verifyCourseLeve2.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void startsOnIcon(ArrayList<String> startsOnIconFromExcel)
	{
		try
		{
			ArrayList<String> verifyStartsOnIcon = startsOnIconFromExcel;
			if(!verifyStartsOnIcon.contains("NA"))
			{
				verifyStartsOnIcon = frontEndAboutPageLocator.checkStartsOnIcon(startsOnIconFromExcel);
				for(int i = 0; i < verifyStartsOnIcon.size(); i++)
				{
					if(!(verifyStartsOnIcon.size() == 1))
					{
						if(!(verifyStartsOnIcon.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("starts on Icon unable to find");
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void startsOnHeading(ArrayList<String> startsOnHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyStartsOnHeading = startsOnHeadingFromExcel;
			if(!verifyStartsOnHeading.contains("NA"))
			{
				verifyStartsOnHeading = frontEndAboutPageLocator.checkStartsOnHeading(startsOnHeadingFromExcel);
				for(int i = 0; i < verifyStartsOnHeading.size(); i++)
				{
					if(!(verifyStartsOnHeading.size() == 1))
					{
						if(!(verifyStartsOnHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("starts on heading unable to find");
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void startsOnContent(ArrayList<String> startsOnContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyStartsOnContent = startsOnContentFromExcel;
			if(!verifyStartsOnContent.contains("NA"))
			{
				verifyStartsOnContent = frontEndAboutPageLocator.checkStartsOnContent(startsOnContentFromExcel);
				for(int i = 0; i < verifyStartsOnContent.size(); i++)
				{
					if(!(verifyStartsOnContent.size() == 1))
					{
						if(!(verifyStartsOnContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("starts on content unable to find");
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void durationIcon(ArrayList<String> durationIconFromExcel)
	{
		try
		{
			ArrayList<String> verifyDurationIcon = durationIconFromExcel;
			if(!verifyDurationIcon.contains("NA"))
			{
				verifyDurationIcon = frontEndAboutPageLocator.checkDurationIcon(durationIconFromExcel);
				for(int i = 0; i < verifyDurationIcon.size(); i++)
				{
					if(!(verifyDurationIcon.size() == 1))
					{
						if(!(verifyDurationIcon.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("durartion Icon unable to find");
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void durationHeading(ArrayList<String> durationHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyDurationHeading = durationHeadingFromExcel;
			verifyDurationHeading = frontEndAboutPageLocator.checkDurationHeading(durationHeadingFromExcel);
			if(!verifyDurationHeading.contains("NA"))
			{
				for(int i = 0; i < verifyDurationHeading.size(); i++)
				{
					if(!(verifyDurationHeading.size() == 1))
					{
						if(!(verifyDurationHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("Duration heading unable to find");
				markProcessIgnored();
			}					
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void durationContent(ArrayList<String> durationContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyDurationContent = durationContentFromExcel;
			if(!verifyDurationContent.contains("NA"))
			{
				verifyDurationContent = frontEndAboutPageLocator.checkDurationContent(durationContentFromExcel);
				for(int i = 0; i < verifyDurationContent.size(); i++)
				{
					if(!(verifyDurationContent.size()==1))
					{
						if(!(verifyDurationContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("Duration content unable to find");
				markProcessIgnored();				
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void feeIcon(ArrayList<String> feeIconFromExcel)
	{
		try
		{
			ArrayList<String> verifyFeeIcon = feeIconFromExcel;
			if(!verifyFeeIcon.contains("NA"))
			{
				verifyFeeIcon = frontEndAboutPageLocator.checkFeeIcon(feeIconFromExcel);
				for(int i = 0; i < verifyFeeIcon.size(); i++)
				{
					if(!(verifyFeeIcon.size() == 1))
					{
						if(!(verifyFeeIcon.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				System.out.println("Fee Icon unable to find");
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void feeHeading(ArrayList<String> feeHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyFeeHeading = feeHeadingFromExcel;
			verifyFeeHeading =	frontEndAboutPageLocator.checkFeeHeading(feeHeadingFromExcel);
			if(!verifyFeeHeading.contains("NA"))
			{
				for(int i = 0; i < verifyFeeHeading.size(); i++)
				{
					if(!(verifyFeeHeading.size() == 1))
					{
						if(!(verifyFeeHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void feeContent(ArrayList<String> feeContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyFeeContent = feeContentFromExcel;
			if(!verifyFeeContent.contains("NA"))
			{
				verifyFeeContent = frontEndAboutPageLocator.checkFeeContent(feeContentFromExcel);
				for(int i = 0; i < verifyFeeContent.size(); i++)
				{
					if(!(verifyFeeContent.size() == 1))
					{
						if(!(verifyFeeContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void enroll(ArrayList<String> enrollFromExcel)
	{
		try
		{
			ArrayList<String> verifyEnroll = enrollFromExcel;
			if(!verifyEnroll.contains("NA"))
			{
				verifyEnroll = frontEndAboutPageLocator.checkEnrollStartNow(enrollFromExcel);
				for(int i = 0; i < verifyEnroll.size(); i++)
				{
					if(!(verifyEnroll.size() == 1))
					{
						if(!(verifyEnroll.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void consultation(ArrayList<String> consultationFromExcel)
	{
		try
		{
			ArrayList<String> verifyConsultation = consultationFromExcel;
			if(!verifyConsultation.contains("NA"))
			{
				verifyConsultation = frontEndAboutPageLocator.checkConsultation(consultationFromExcel);
				for(int i = 0; i < verifyConsultation.size(); i++)
				{
					if(!(verifyConsultation.size() == 1))
					{
						if(!(verifyConsultation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void overviewNavigation(ArrayList<String> overviewNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifyOverviewNavigation = overviewNavigationFromExcel;
			if(!verifyOverviewNavigation.contains("NA"))
			{
				verifyOverviewNavigation = frontEndAboutPageLocator.checkOverviewNavigation(overviewNavigationFromExcel);
				for(int i = 0; i < verifyOverviewNavigation.size(); i++)
				{
					if(!(verifyOverviewNavigation.size() == 1))
					{
						if(!(verifyOverviewNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void outlineNavigation(ArrayList<String> outlineNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifyOutlineNavigation = outlineNavigationFromExcel;
			if(!verifyOutlineNavigation.contains("NA"))
			{
				verifyOutlineNavigation = frontEndAboutPageLocator.checkOutlineNavigation(outlineNavigationFromExcel);
				for(int i = 0; i < verifyOutlineNavigation.size(); i++)
				{
					if(!(verifyOutlineNavigation.size() == 1))
					{
						if(!(verifyOutlineNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void skillupOnlineNavigation(ArrayList<String> skillupOnlineNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifySkillupOnlineNavigation = skillupOnlineNavigationFromExcel;
			if(!verifySkillupOnlineNavigation.contains("NA"))
			{
				verifySkillupOnlineNavigation = frontEndAboutPageLocator.checkSkillupOnlineNavigation(skillupOnlineNavigationFromExcel);
				for(int i = 0; i < verifySkillupOnlineNavigation.size(); i++)
				{
					if(!(verifySkillupOnlineNavigation.size() == 1))
					{
						if(!(verifySkillupOnlineNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void FAQNavigation(ArrayList<String> FAQNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifyFAQNavigation = FAQNavigationFromExcel;
			if(!verifyFAQNavigation.contains("NA"))
			{
				verifyFAQNavigation = frontEndAboutPageLocator.checkFAQNavigation(FAQNavigationFromExcel);
				for(int i = 0; i < verifyFAQNavigation.size(); i++)
				{
					if(!(verifyFAQNavigation.size() == 1))
					{
						if(!(verifyFAQNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		}
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void enrollNavigation(ArrayList<String> enrollNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifyEnrollNavigation = enrollNavigationFromExcel;
			if(!verifyEnrollNavigation.contains("NA"))
			{
				verifyEnrollNavigation = frontEndAboutPageLocator.checkEnrollNavigation(enrollNavigationFromExcel);
				for(int i = 0; i < verifyEnrollNavigation.size(); i++)
				{
					if(!(verifyEnrollNavigation.size() == 1))
					{
						if(!(verifyEnrollNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
				
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void consultationNavigation(ArrayList<String> consultationNavigationFromExcel)
	{
		try
		{
			ArrayList<String> verifyConsultationNavigation = consultationNavigationFromExcel;
			if(!verifyConsultationNavigation.contains("NA"))
			{
				verifyConsultationNavigation =	frontEndAboutPageLocator.checkConsultationNavigation(consultationNavigationFromExcel);
				for(int i = 0; i < verifyConsultationNavigation.size(); i++)
				{
					if(!(verifyConsultationNavigation.size() == 1))
					{
						if(!(verifyConsultationNavigation.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	private void programOffering(ArrayList<String> programOfferingFromExcel)
	{
		try
		{
			ArrayList<String> verifyprogramOffering = programOfferingFromExcel;
			if(!verifyprogramOffering.contains("NA"))
			{
				verifyprogramOffering =	frontEndAboutPageLocator.checkProgramOffering(programOfferingFromExcel);
				for(int i = 0; i < verifyprogramOffering.size(); i++)
				{
					if(!(verifyprogramOffering.size() == 1))
					{
						if(!(verifyprogramOffering.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void typeOfCertificateHeading(ArrayList<String> typeOfCertificateHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyTypeOfCertificateHeading = typeOfCertificateHeadingFromExcel;
			if(!verifyTypeOfCertificateHeading.contains("NA"))
			{
				verifyTypeOfCertificateHeading = frontEndAboutPageLocator.checkTypeOfCertificateHeading(typeOfCertificateHeadingFromExcel);
				for(int i = 0; i < verifyTypeOfCertificateHeading.size(); i++)
				{
					if(!(verifyTypeOfCertificateHeading.size() == 1))
					{
						if(!(verifyTypeOfCertificateHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void typeOfCertificateContent(ArrayList<String> typeOfCertificateContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyTypeOfCertificateContent = typeOfCertificateContentFromExcel;
			if(!verifyTypeOfCertificateContent.contains("NA"))
			{
				verifyTypeOfCertificateContent = frontEndAboutPageLocator.checkTypeOfCertificateContent(typeOfCertificateContentFromExcel);
				for(int i = 0; i < verifyTypeOfCertificateContent.size(); i++)
				{
					if(!(verifyTypeOfCertificateContent.size() == 1))
					{
						if(!(verifyTypeOfCertificateContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(NullPointerException e) 
		{
			markProcessFailed();
		}
	}
	
	private void aboutCourseHeading(ArrayList<String> aboutCourseHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyAboutCourseHeading = aboutCourseHeadingFromExcel;
			if(!verifyAboutCourseHeading.contains("NA"))
			{
				verifyAboutCourseHeading = frontEndAboutPageLocator.checkAboutCourseHeading(aboutCourseHeadingFromExcel);
				for(int i = 0; i < verifyAboutCourseHeading.size(); i++)
				{
					if(!(verifyAboutCourseHeading.size() == 1))
					{
						if(!(verifyAboutCourseHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void aboutCourseContent(ArrayList<String> aboutCourseContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyAboutCourseContent = aboutCourseContentFromExcel;
			if(!verifyAboutCourseContent.contains("NA"))
			{
				verifyAboutCourseContent = 	frontEndAboutPageLocator.checkAboutCourseContent(aboutCourseContentFromExcel);
				for(int i = 0; i < verifyAboutCourseContent.size(); i++)
				{
					if(!(verifyAboutCourseContent.size() == 1))
					{
						if(!(verifyAboutCourseContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void includesHeading(ArrayList<String> includesHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyIncludesHeading = includesHeadingFromExcel;
			if(!verifyIncludesHeading.contains("NA"))
			{
				frontEndAboutPageLocator.checkIncludesHeading(includesHeadingFromExcel);
				for(int i = 0; i < verifyIncludesHeading.size(); i++)
				{
					if(!(verifyIncludesHeading.size() == 1))
					{
						if(!(verifyIncludesHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void includesContent(ArrayList<String> includesContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyIncludesContent = includesContentFromExcel;
			if(!verifyIncludesContent.contains("NA"))
			{
				verifyIncludesContent = frontEndAboutPageLocator.checkIncludesContent(includesContentFromExcel);
				for(int i = 0; i < verifyIncludesContent.size(); i++)
				{
					if(!(verifyIncludesContent.size() == 1))
					{
						if(!(verifyIncludesContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void createHeading(ArrayList<String> createHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyCreateHeading = createHeadingFromExcel;
			if(!verifyCreateHeading.contains("NA"))
			{
				verifyCreateHeading = frontEndAboutPageLocator.checkCreateHeading(createHeadingFromExcel);
				for(int i = 0; i < verifyCreateHeading.size(); i++)
				{
					if(!(verifyCreateHeading.size() == 1))
					{
						if(!(verifyCreateHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void createContent(ArrayList<String> createContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyCreateContent = createContentFromExcel;
			if(!verifyCreateContent.contains("NA"))
			{
				verifyCreateContent = frontEndAboutPageLocator.checkCreateContent(createContentFromExcel);
				for(int i = 0; i < verifyCreateContent.size(); i++)
				{
					if(!(verifyCreateContent.size() == 1))
					{
						if(!(verifyCreateContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void exerciseToExploreHeading(ArrayList<String> exerciseToExploreHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyExerciseToExploreHeading = exerciseToExploreHeadingFromExcel;
			if(!verifyExerciseToExploreHeading.contains("NA"))
			{
				verifyExerciseToExploreHeading = frontEndAboutPageLocator.checkExerciseToExploreHeading(exerciseToExploreHeadingFromExcel);
				for(int i = 0; i < verifyExerciseToExploreHeading.size(); i++)
				{
					if(!(verifyExerciseToExploreHeading.size() == 1))
					{
						if(!(verifyExerciseToExploreHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void exerciseToExploreContent(ArrayList<String> exerciseToExploreContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyExerciseToExploreContent = exerciseToExploreContentFromExcel;
			if(!verifyExerciseToExploreContent.contains("NA"))
			{
				verifyExerciseToExploreContent = frontEndAboutPageLocator.checkExerciseToExploreContent(exerciseToExploreContentFromExcel);
				for(int i = 0; i < verifyExerciseToExploreContent.size(); i++)
				{
					if(!(verifyExerciseToExploreContent.size() == 1))
					{
						if(!(verifyExerciseToExploreContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}	
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseOverviewHeading(ArrayList<String> courseOverviewHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseOverviewHeading = courseOverviewHeadingFromExcel;
			if(!courseOverviewHeadingFromExcel.contains("NA"))
			{
				verifyCourseOverviewHeading = frontEndAboutPageLocator.checkCourseOverviewHeading(courseOverviewHeadingFromExcel);
				for(int i = 0; i < verifyCourseOverviewHeading.size(); i++)
				{
					if(!(verifyCourseOverviewHeading.size() == 1))
					{
						if(!(verifyCourseOverviewHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void courseOverviewContent(ArrayList<String> courseOverviewContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyCourseOverviewContent = courseOverviewContentFromExcel;
			if(!verifyCourseOverviewContent.contains("NA"))
			{
				verifyCourseOverviewContent = frontEndAboutPageLocator.checkCourseOverviewContent(courseOverviewContentFromExcel);
				for(int i = 0; i < verifyCourseOverviewContent.size(); i++)
				{
					if(!(verifyCourseOverviewContent.size() == 1))
					{
						if(!(verifyCourseOverviewContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void howItWorks(ArrayList<String> howItWorksFromExcel)
	{
		try
		{
			ArrayList<String> verifyhowItWorks = howItWorksFromExcel;
			if(!verifyhowItWorks.contains("NA"))
			{
				verifyhowItWorks = frontEndAboutPageLocator.checkHowItWorks(howItWorksFromExcel);
				for(int i = 0; i < verifyhowItWorks.size(); i++)
				{
					if(!(verifyhowItWorks.size() == 1))
					{
						if(!(verifyhowItWorks.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void skillsYouWillGain(ArrayList<String> skillsYouWillGainFromExcel)
	{
		try
		{
			ArrayList<String> verifySkillsYouWillGain = skillsYouWillGainFromExcel;
			if(!verifySkillsYouWillGain.contains("NA"))
			{
				verifySkillsYouWillGain = frontEndAboutPageLocator.checkSkillsYouWillGain(skillsYouWillGainFromExcel);
				for(int i = 0; i < verifySkillsYouWillGain.size(); i++)
				{
					if(!(verifySkillsYouWillGain.size() == 1))
					{
						if(!(verifySkillsYouWillGain.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whoShouldEnrollOnThisCourse(ArrayList<String> whoShouldEnrollOnThisCourseFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhoShouldEnrollOnThisCourse = whoShouldEnrollOnThisCourseFromExcel;
			if(!verifyWhoShouldEnrollOnThisCourse.contains("NA"))
			{
				verifyWhoShouldEnrollOnThisCourse = frontEndAboutPageLocator.checkWhoShouldEnrollOnThisCourse(whoShouldEnrollOnThisCourseFromExcel);
				for(int i = 0; i < verifyWhoShouldEnrollOnThisCourse.size(); i++)
				{
					if(!(verifyWhoShouldEnrollOnThisCourse.size() == 1))
					{
						if(!(verifyWhoShouldEnrollOnThisCourse.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void prerequisites(ArrayList<String> prerequisitesFromExcel)
	{
		try
		{
			ArrayList<String> verifyPrerequisites = prerequisitesFromExcel;
			if(!verifyPrerequisites.contains("NA"))
			{
				verifyPrerequisites = frontEndAboutPageLocator.checkPrerequisites(prerequisitesFromExcel);
				for(int i = 0; i < verifyPrerequisites.size(); i++)
				{
					if(!(verifyPrerequisites.size() == 1))
					{
						if(!(verifyPrerequisites.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void earnYourCertificateHeading(ArrayList<String> earnYourCertificateHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyEarnYourCertificateHeading = earnYourCertificateHeadingFromExcel;
			if(!verifyEarnYourCertificateHeading.contains("NA"))
			{
				verifyEarnYourCertificateHeading = frontEndAboutPageLocator.checkEarnYourCertificateHeading(earnYourCertificateHeadingFromExcel);
				for(int i = 0; i < verifyEarnYourCertificateHeading.size(); i++)
				{
					if(!(verifyEarnYourCertificateHeading.size() == 1))
					{
						if(!(verifyEarnYourCertificateHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void earnYourCertificateContent(ArrayList<String> earnYourCertificateContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyEarnYourCertificateContent = earnYourCertificateContentFromExcel;
			if(!verifyEarnYourCertificateContent.contains("NA"))
			{
				verifyEarnYourCertificateContent = 	frontEndAboutPageLocator.checkEarnYourCertificateContent(earnYourCertificateContentFromExcel);
				for(int i = 0; i < verifyEarnYourCertificateContent.size(); i++)
				{
					if(!(verifyEarnYourCertificateContent.size() == 1))
					{
						if(!(verifyEarnYourCertificateContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertTitle(ArrayList<String> expertTitleFromExcel)
	{
		try
		{
			ArrayList<String> verifyExpertTitle = expertTitleFromExcel;
			if(!verifyExpertTitle.contains("NA"))
			{
				verifyExpertTitle = frontEndAboutPageLocator.checkExpertTitle(expertTitleFromExcel);
				for(int i = 0; i < verifyExpertTitle.size(); i++)
				{
					if(!(verifyExpertTitle.size() == 1))
					{
						if(!(verifyExpertTitle.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertName(ArrayList<String> expertNameFromExcel)
	{
		try
		{
			ArrayList<String> verifyExpertName = expertNameFromExcel;
			if(!verifyExpertName.contains("NA"))
			{
				verifyExpertName = frontEndAboutPageLocator.checkExpertName(expertNameFromExcel);
				for(int i = 0; i < verifyExpertName.size(); i++)
				{
					if(!(verifyExpertName.size() == 1))
					{
						if(!(verifyExpertName.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void expertRole(ArrayList<String> expertRoleFromExcel)
	{
		try
		{
			ArrayList<String> verifyExpertRole = expertRoleFromExcel;
			if(!verifyExpertRole.contains("NA"))
			{
				verifyExpertRole = frontEndAboutPageLocator.checkExpertRole(expertRoleFromExcel);
				for(int i = 0; i < verifyExpertRole.size(); i++)
				{
					if(!(verifyExpertRole.size() == 1))
					{
						if(!(verifyExpertRole.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	
	private void expertLink(ArrayList<String> expertLinkFromExcel)
	{
		try
		{
			ArrayList<String> verifyExpertLink = expertLinkFromExcel;
			if(!verifyExpertLink.contains("NA"))
			{
				verifyExpertLink = 	frontEndAboutPageLocator.checkExpertLink(expertLinkFromExcel);
				for(int i = 0; i < verifyExpertLink.size(); i++)
				{
					if(!(verifyExpertLink.size() == 1))
					{
						if(!(verifyExpertLink.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void newsLetterUpdates(ArrayList<String> newsLetterUpdatesFromExcel)
	{
		try
		{
			ArrayList<String> verifyNewsLetterUpdates = newsLetterUpdatesFromExcel;
			if(!verifyNewsLetterUpdates.contains("NA"))
			{
				verifyNewsLetterUpdates = frontEndAboutPageLocator.checkNewsLetterUpdates(newsLetterUpdatesFromExcel);
				for(int i = 0; i < verifyNewsLetterUpdates.size(); i++)
				{
					if(!(verifyNewsLetterUpdates.size() == 1))
					{
						if(!(verifyNewsLetterUpdates.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void subscribeContent(ArrayList<String> subscribeContentFromExcel)
	{
		try
		{
			ArrayList<String> verifySubscribeContent = subscribeContentFromExcel;
			if(!verifySubscribeContent.contains("NA"))
			{
				verifySubscribeContent = frontEndAboutPageLocator.checkSubscribeContent(subscribeContentFromExcel);
				for(int i = 0; i < verifySubscribeContent.size(); i++)
				{
					if(!(verifySubscribeContent.size() == 1))
					{
						if(!(verifySubscribeContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void fullName(ArrayList<String> fullNameFromExcel)
	{
		try
		{
			ArrayList<String> verifyFullName = fullNameFromExcel;
			if(!verifyFullName.contains("NA"))
			{
				verifyFullName = frontEndAboutPageLocator.checkFullName(fullNameFromExcel);
				for(int i = 0; i < verifyFullName.size(); i++)
				{
					if(!(verifyFullName.size() == 1))
					{
						if(!(verifyFullName.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else {
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void email(ArrayList<String> emailFromExcel)
	{
		try
		{
			ArrayList<String> verifyEmail = emailFromExcel;
			if(!verifyEmail.contains("NA"))
			{
				verifyEmail = frontEndAboutPageLocator.checkEmail(emailFromExcel);
				for(int i = 0; i < verifyEmail.size(); i++)
				{
					if(!(verifyEmail.size() == 1))
					{
						if(!(verifyEmail.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void subscribeButton(ArrayList<String> subscribeButtonFromExcel)
	{
		try
		{
			ArrayList<String> verifySubscribeButton = subscribeButtonFromExcel;
			if(!verifySubscribeButton.contains("NA"))
			{
				verifySubscribeButton =	frontEndAboutPageLocator.checkSubscribeButton(subscribeButtonFromExcel);
				for(int i = 0; i < verifySubscribeButton.size(); i++)
				{
					if(!(verifySubscribeButton.size() == 1))
					{
						if(!(verifySubscribeButton.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineHeading(ArrayList<String> whyLearnSkillupOnlineHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineHeading = whyLearnSkillupOnlineHeadingFromExcel;
			if(!verifyWhyLearnSkillupOnlineHeading.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineHeading = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineHeading(whyLearnSkillupOnlineHeadingFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineHeading.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineHeading.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineContent(ArrayList<String> whyLearnSkillupOnlineContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineContent = whyLearnSkillupOnlineContentFromExcel;
			if(!verifyWhyLearnSkillupOnlineContent.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineContent = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineContent(whyLearnSkillupOnlineContentFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineContent.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineContent.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineTickIcon(ArrayList<String> whyLearnSkillupOnlineTickIconFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineTickIcon = whyLearnSkillupOnlineTickIconFromExcel;
			if(!verifyWhyLearnSkillupOnlineTickIcon.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineTickIcon = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineTickIcon(whyLearnSkillupOnlineTickIconFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineTickIcon.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineTickIcon.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineTickIcon.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineTickIconContent(ArrayList<String> whyLearnSkillupOnlineTickIconContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineTickIconContent = whyLearnSkillupOnlineTickIconContentFromExcel;
			if(!verifyWhyLearnSkillupOnlineTickIconContent.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineTickIconContent = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineTickIconContent(whyLearnSkillupOnlineTickIconContentFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineTickIconContent.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineTickIconContent.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineTickIconContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineProfitPoint(ArrayList<String> whyLearnSkillupOnlineProfitPointFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineProfitPoint = whyLearnSkillupOnlineProfitPointFromExcel;
			if(!verifyWhyLearnSkillupOnlineProfitPoint.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineProfitPoint = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineProfitPoint(whyLearnSkillupOnlineProfitPointFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineProfitPoint.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineProfitPoint.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineProfitPoint.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineProfitPointHeading(ArrayList<String> whyLearnSkillupOnlineProfitPointHeadingFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineProfitPointHeading = whyLearnSkillupOnlineProfitPointHeadingFromExcel;
			if(!verifyWhyLearnSkillupOnlineProfitPointHeading.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineProfitPointHeading = frontEndAboutPageLocator.checkWhyLearnSkillupOnlineProfitPointHeading(whyLearnSkillupOnlineProfitPointHeadingFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineProfitPointHeading.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineProfitPointHeading.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineProfitPointHeading.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
			
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void whyLearnSkillupOnlineProfitPointContent(ArrayList<String> whyLearnSkillupOnlineProfitPointContentFromExcel)
	{
		try
		{
			ArrayList<String> verifyWhyLearnSkillupOnlineProfitPointContent = whyLearnSkillupOnlineProfitPointContentFromExcel;
			if(!verifyWhyLearnSkillupOnlineProfitPointContent.contains("NA"))
			{
				verifyWhyLearnSkillupOnlineProfitPointContent =	frontEndAboutPageLocator.checkWhyLearnSkillupOnlineProfitPointContent(whyLearnSkillupOnlineProfitPointContentFromExcel);
				for(int i = 0; i < verifyWhyLearnSkillupOnlineProfitPointContent.size(); i++)
				{
					if(!(verifyWhyLearnSkillupOnlineProfitPointContent.size() == 1))
					{
						if(!(verifyWhyLearnSkillupOnlineProfitPointContent.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void FAQs(ArrayList<String> FAQsFromExcel)
	{
		try
		{
			ArrayList<String> verifyFAQs = FAQsFromExcel;
			if(!verifyFAQs.contains("NA"))
			{
				verifyFAQs = frontEndAboutPageLocator.checkFAQs(FAQsFromExcel);
				for(int i = 0; i < verifyFAQs.size(); i++)
				{
					if(!(verifyFAQs.size() == 1))
					{
						if(!(verifyFAQs.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void question(ArrayList<String> questionFromExcel)
	{
		try
		{
			ArrayList<String> verifyQuestion = questionFromExcel;
			if(!verifyQuestion.contains("NA"))
			{
				verifyQuestion = frontEndAboutPageLocator.checkQuestion(questionFromExcel);
				for(int i = 0; i < verifyQuestion.size(); i++)
				{
					if(!(verifyQuestion.size() == 1))
					{
						if(!(verifyQuestion.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
			{
				markProcessIgnored();
			}
		} 
		catch(Exception e) 
		{
			markProcessFailed();
		}
	}
	
	private void answer(ArrayList<String> answerFromExcel)
	{
		try
		{
			ArrayList<String> verifyAnswer = answerFromExcel;
			if(!verifyAnswer.contains("NA"))
			{
				verifyAnswer =	frontEndAboutPageLocator.checkAnswer(answerFromExcel);
				for(int i = 0; i < verifyAnswer.size(); i++)
				{
					if(!(verifyAnswer.size() == 1))
					{
						if(!(verifyAnswer.get(i)).equalsIgnoreCase("true"))
						{
							markColumnFailed(i+1);
						}
					}
					else
					{
						markWebElementFailed();
					}
				}
			}
			else
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
	private void markWebElementFailed()
	{
		sheetStatus = "Fail";
		String process = TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).get(0);
		TestFrontEndAboutCourse.EXCEL_DATA_AS_SHEEET_NAME_AND_ROWS_MAP.get(SHEET_NAME).get(CURRENT_ROW).set(0,
				(process + " - webElementNotFound"));
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

	private void collectSheetResult() 
	{
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
