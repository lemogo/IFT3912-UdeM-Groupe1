/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * this command allow signed user to add an event in his account
 * 
 * @author william
 * 
 */
public class AddEvent extends Command {
	/**
	 * Constructor
	 * 
	 * @param a string info to parse
	 * @throws ClassNotFoundException 
	 */
	public AddEvent(String info) throws ClassNotFoundException {
		query_ = buildQuery(info);
		myDb = new DataBase() ;
	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info string type
	 * @return a string that is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
	
	/**
	 * Method to handle creation of account from anonymous user 
	 * @param info <code>String</code> Object to data of new created event get d
	 * @return true if successful execution else false
	 */
	public boolean addNewEvent(String info){
		
		String title = "bataille de chocolat" ;
		String date = "2014-12-07 23:21:45";
		String location = "plateau mont royal" ;
		String place = "42";
		String description = "je de peche tres evmouvant ";
		String userId = "1" ;
		//String  eventId = "1";
		
		boolean returnValue = false ;
		try {
				myDb.statement().executeUpdate("insert into event  (title, suserid, dateevent, location, description, numberplaces) "  +
						"values('" + title +"', '" + userId +"', '" + date +"', '"+ location  +"', '" + description + "' , "+ place + ")");
				returnValue = true ;
				
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		return returnValue ;
		
		//TODO create user account  	
	}
	
	
	/**
	 * object DataBase 
	 */
	private DataBase myDb ;
	

}
