package com.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidationFunctions extends DatabaseProcessor{
	/** update the ALERT table - if duration is more than 4 then update alert column to true else false
	 * @throws SQLException
	 */
	public static void updateAlertField() throws SQLException {
		
		statement = sqliteConnection.createStatement();
		Statement statement1 = sqliteConnection.createStatement();
		String strIDSQL = "SELECT distinct ID from EVENT_LOG_TBL";
		ResultSet rs = statement.executeQuery(strIDSQL);
		ResultSet rsStartTime, rsEndTime;
		Long iDuration;
		String strID,strType,strHost,strColValues,iStartTime, iEndTime;
		String strcolName = "ID,DURATION,TYPE,HOST";
		String strStartTimeSQL, strENDTimeSQL;
		String strUpdateAlertSQL;
		
		while(rs.next()) {
			strID = rs.getString(1);
			strStartTimeSQL = "SELECT TIMESTAMP,TYPE,HOST from EVENT_LOG_TBL where STATE='STARTED' and ID = '" + strID +"'";
			strENDTimeSQL = "SELECT TIMESTAMP from EVENT_LOG_TBL where STATE='FINISHED' and ID = '" + strID +"'";
			rsStartTime = statement1.executeQuery(strStartTimeSQL);
			iStartTime = rsStartTime.getString(1);
			strType = rsStartTime.getString("TYPE");
			strHost = rsStartTime.getString("HOST");
			rsEndTime = statement1.executeQuery(strENDTimeSQL);
			iEndTime = rsEndTime.getString(1);
			iDuration = Long.parseLong(iEndTime) - Long.parseLong(iStartTime);
			rsStartTime.close();
			rsEndTime.close();
			strColValues="'"+ strID +"'," + iDuration +",'" +strType+"','" + strHost + "'";
			DatabaseProcessor.insertToAlertTable(strcolName, strColValues);
			if (iDuration > 4) {
				DatabaseProcessor.updateALertTBL("true",  strID);
				
			}else {
				DatabaseProcessor.updateALertTBL("false",  strID);
				
			}
			
		}
		rs.close();
	}
	
	/** printing the log details of ID whose duration is more than 4 on Console 
	 * @throws SQLException
	 */
	public static void getLogDetailsofAlertID() throws SQLException {
		String strAlertLogDatasql = "Select ID,DURATION,TYPE,HOST from ALERT_LOG_TBL where ALERT='true'";
		ResultSet rs = statement.executeQuery(strAlertLogDatasql);
		while(rs.next()) {
			System.out.println("----- ID"+":"+rs.getString("ID") + " , DURATION"+":"+rs.getInt("DURATION") + "  , TYPE"+":"+rs.getString("TYPE") + " , HOST"+":"+rs.getString("HOST")+" -----");
		}
		rs.close();
		
		
	}
	
	

}
