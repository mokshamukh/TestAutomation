package com.library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;



/** Json processor class having reusable functions for json reading
 * @author mmukh
 *
 */
public class FileProcessor {

	public static List<String> linesInTextFile = null;



	/** readTxtFile- to read text file and convert into json format
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static  void readTxtFile(String filename) throws IOException, Exception {
		
		File file = new File(filename);
		if (!file.exists()) {
			Assert.fail("<<<<<<<<<<FILE NOT FOUND- Evenet log file does not exist at location "+ filename + ">>>>>>>>>");
		}

		int lineNumber = 0;
		String lineFromTextFileToString;
		linesInTextFile = Files.readAllLines(Paths.get(filename));

		if (linesInTextFile.size() == 0 ) {
			Assert.fail("<<<<<<<<<<EMPTY FILE- There is no data in file>>>>>>>>>");
		}
		else {

			for (Object lineFromTextFile : linesInTextFile) {
				lineFromTextFileToString = lineFromTextFile.toString();
				lineFromTextFileToString ="["+lineFromTextFileToString+"]";
				covertToJsonAndAddToDB(lineFromTextFileToString);

			}
		}

	}


	/** covert line data from file to json format and insert these values to tabble
	 * @param jsonData
	 * @throws IOException
	 * @throws Exception
	 */
	public static void covertToJsonAndAddToDB(String jsonData) throws IOException, Exception {
		//String jsonData = readTxtFile(filename);
		String strColumns ="", strColumnVal ="";
		JSONArray jsonarray = new JSONArray(jsonData);
		for (int i = 0; i <= jsonarray.length()-1; i++) {
			Object value = jsonarray.get(i); 
			Iterator<String> keysItr = ((JSONObject) value).keys();
			while(keysItr.hasNext()) {
				String key = keysItr.next();
				Object value1 = ((JSONObject) value).get(key);
				System.out.println(key+":"+value1);
				//addEventValueToDB(key,value1.toString());
				strColumns= strColumns + "," +key;
				strColumnVal= strColumnVal + ",'" +value1.toString()+"'";


			}
			DatabaseProcessor.insertToTable(strColumns.substring(1), strColumnVal.substring(1));

		}

	}



}
