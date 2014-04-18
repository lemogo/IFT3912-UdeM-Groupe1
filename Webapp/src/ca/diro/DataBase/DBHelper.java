/**
 * 
 */
package ca.diro.DataBase;

import java.sql.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class to help database command to parse string parameter and build the string query
 * @author william
 *
 */
public class DBHelper {

	/**
	 * Constructor 
	 * @throws ClassNotFoundException 
	 */
	public DBHelper(DataBase db ) throws ClassNotFoundException  {
		// TODO something
		myDb  = db;
		
	}
	
	/**
	 * Static method to Convert a string table to a Set
	 * 
	 * @param table
	 *            an array <code>String</code>
	 * @return setTable a <code>TreeSet</code> Object to keep all objects from
	 *         array
	 */
	public static Set<String> convertToSet(String[] table) {

		Set<String> setTable = new TreeSet<String>(Arrays.asList(table));
		return setTable;
	}
	
	
	
	/**
	 * Method to give user'name, email and  of all users 
	 * @return allUser a <code>ResultSet</code> Object after good connection else null.
	 */
	public ResultSet getAllUser(){
		
		String str = "select username , email from signeduser";
		try {
			
			this.allUser = myDb.statement().executeQuery(str);
				
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 return this.allUser ;		
	}
	/**
	 * Method to check if an event is cancelled or not  
	 * @return true if good connection else false.
	 */
	public boolean checkEventStatus(String eventId){
		//TODO check event status
		ResultSet rs = null ;
		String status = "";
		
		String str = "select status  from event " +
				"where eventid = " + eventId ;
		try {
			
			rs  = myDb.statement().executeQuery(str);
			while (rs.next()) {
				status = rs.getString(1);
				//System.out.println(rs.getInt(1));
			}
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
			
		return  status.toUpperCase().equals("CANCELLED");
		
	}
	
	
	/**
	 * this is a Database object to using to connect with database
	 */
	DataBase myDb ;
	
	/**
	 * allUser a <code>ResultSet</code> Object giving list of 
	 * all signed user
	 */
	private ResultSet allUser = null ;


}
