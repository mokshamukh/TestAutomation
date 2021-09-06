package com.testCases;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.library.DatabaseProcessor;
import com.library.FileProcessor;
import com.library.ValidationFunctions;



public class LogTimestampValidation {
	
	@BeforeSuite
	public void beforeSuite() throws ClassNotFoundException, SQLException {
		DatabaseProcessor.openSqliteConnection("src/main/resources/com/files/LogEvents.db");
		DatabaseProcessor.deleteTable("EVENT_LOG_TBL");
		DatabaseProcessor.deleteTable("ALERT_LOG_TBL");
	}
	
	
	@BeforeTest
	public void beforeTest() {
		System.out.println("Test Case execution Started");
	}
	
	@Test
	public void test() throws Exception
	{
		
		FileProcessor.readTxtFile("src/main/resources/com/files/logfile.txt");
		ValidationFunctions.updateAlertField();
		ValidationFunctions.getLogDetailsofAlertID();
		
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("Test Case execution Finished");
	}
	
	
	@AfterSuite
	public void afterSUite() throws SQLException {
		DatabaseProcessor.closeDB();		
	}
}
