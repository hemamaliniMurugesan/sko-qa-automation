package com.seo.manager;

import org.apache.log4j.Logger;

import com.seo.dataProvider.ConfigFileReader;
import com.seo.pompages.CourseDetailsPage;

public class FileReaderManager 
{
	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private FileReaderManager()
	{
		
	}
	
	public static FileReaderManager getInstance()
	{
		return fileReaderManager;
	}
	
	public ConfigFileReader getConfigReader()
	{
		return (configFileReader == null)? new ConfigFileReader() : configFileReader;
	}
}
