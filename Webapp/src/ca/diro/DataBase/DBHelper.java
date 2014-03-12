/**
 * 
 */
package ca.diro.DataBase;

import java.sql.*;

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
	public DBHelper() throws ClassNotFoundException  {
		// TODO something
		myDb  = new DataBase();
		
	}
	
	/**
	 * Method to get the number of person who attend the event 
	 * @param eventId a <code>String</code> giving the Id of the event 
	 * @return
	 */
	public  int getAttendPlaces(String eventId){
		//TODO attended places
		int place = 0 ;
		ResultSet rs =  null ;
		String str = "select count(eventid) from subscriteEvent " +
					"where eventid = "+ Integer.parseInt(eventId) ;
		try {
			
			rs = myDb.statement().executeQuery(str);
			while (rs.next()) {
				place = rs.getInt(1);
				//System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		//place = rs.getInt(1);
		return place ;
	}
	/**
	 * Method to get the number of person who remain to complete  the event 
	 * @param eventId a <code>String</code> giving the Id of the event 
	 * @return
	 */
	public  int getRemainingPlaces(String eventId){
		//TODO total places 
		int place = 0 ;
		String str = "select numberplaces  from event " +
					"where eventid = "+ Integer.parseInt(eventId) ;
		try {
			
			ResultSet rs = myDb.statement().executeQuery(str);
			while (rs.next()) {
				place = rs.getInt(1);
				//System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return  place - this.getAttendPlaces(eventId) ;
	}
	
	/**
	 * Method to give user'name, password and  of all users 
	 * @return allUser a <code>ResultSet</code> Object after good connection else null.
	 */
	public ResultSet getAllUser(){
		//TODO give the list of all users
		String str = "select username , password from signeduser";
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
		boolean returnValue = false ;
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
	 * getter to get user'name, full name, password and email of all signed users  
	 * @return allUser a <code>ResultSet</code> Object giving list of 
	 * all signed user 
	 */
	//public ResultSet getAllUser() {
		//return allUser;
	//}
	
	
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
