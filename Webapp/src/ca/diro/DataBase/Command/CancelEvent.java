/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;

import ca.diro.DataBase.DataBase;

/**
 * this class permit to set query in order to allow a signed user to cancel
 * event
 * 
 * @author william
 */
public class CancelEvent extends Command {

	/**
	 * Constructor
	 * 
	 * @param info String to build query
	 */
	public CancelEvent(String info) {
		query_ = buildQuery(info);
	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
	
	/**
	 * Method to remove cancelled event in the subscripted event list
	 * @param info give the eventId
	 * @return true if good connection to database or false when connection failed.
	 */
	public boolean removeSubcriteEvent(String info) {
		//TODO perform remove query
		int  eventId = Integer.parseInt(info);
		boolean returnValue = false ;
		
		 return returnValue ;
	}
	
	/**
     *  This method provides the email list of all users  in the cancelled event.
     *  Retrieves the current result as a <code>String</code> object.
	 * @param idEvent eventId of cancelled event
	 * @return result a <code>String</code> Object  query required.
	 */
	public String UsersTonifify(String idEvent){
		int  eventId = Integer.parseInt(idEvent);
		String str = "";
		return str;
	}
	
	/**
	 * object DataBase 
	 */
	DataBase db = new DataBase() ;
	/**
	 * result giving list of users who have to be notify after a cancelled event 
	 */
	ResultSet result = null ;
}
