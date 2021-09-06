package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseProcessor {
	
	private static String sqliteClassName = "org.sqlite.JDBC";
	private static String sqliteConnectionPrefix = "jdbc:sqlite:";
	
	public static String insertQuery;
	public static String deleteQuery,updateQuery;
	public static Statement statement;
	public static Connection sqliteConnection;
	
	/** Open SQLIYE database connection
	 * @param sqliteDatabaseFileName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection openSqliteConnection(String sqliteDatabaseFileName)
			throws ClassNotFoundException, SQLException {
		Class.forName(sqliteClassName);
		sqliteConnection = DriverManager.getConnection(sqliteConnectionPrefix + sqliteDatabaseFileName);
		return sqliteConnection;
	}
	
	/** insert values to table -EVENT_LOG_TBL
	 * @param column -- DATABASE column name
	 * @param value -- DATABASE column value
	 * @throws SQLException
	 */
	public static void insertToTable(String column, String value) throws SQLException {
		
		insertQuery= "INSERT INTO EVENT_LOG_TBL ("+ column + ") VALUES ("+ value +")";
		statement = sqliteConnection.createStatement();
		statement.executeUpdate(insertQuery);
	}
	
	/** delete the provided table
	 * @param tablename
	 * @throws SQLException
	 */
	public static void deleteTable(String tablename) throws SQLException {

		deleteQuery = "Delete FROM " + tablename;
		statement = sqliteConnection.createStatement();
		statement.executeUpdate(deleteQuery);
		
	}
	
	/** insert values to table -ALERT_LOG_TBL
	 * @param column
	 * @param value
	 * @throws SQLException
	 */
	public static void insertToAlertTable(String column, String value) throws SQLException {
		
		insertQuery= "INSERT INTO ALERT_LOG_TBL ("+ column + ") VALUES ("+ value +")";
		statement = sqliteConnection.createStatement();
		statement.executeUpdate(insertQuery);
	}
	
	/** Update ALert column in ALERT_LOG_TBL
	 * @param alert
	 * @param ID
	 * @throws SQLException
	 */
	public static void updateALertTBL(String alert,String ID) throws SQLException {

		updateQuery =  "UPDATE ALERT_LOG_TBL SET ALERT = '"+ alert +"' where ID='"+ ID +"'";
		statement = sqliteConnection.createStatement();
		statement.executeUpdate(updateQuery);
		
	}
	
	public static void closeDB() throws SQLException {
		sqliteConnection.close();
		
	}
	
	

}
