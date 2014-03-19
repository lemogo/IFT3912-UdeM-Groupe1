/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;

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
	 * @param info string to build query
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CancelEvent(String info,DataBase db)  {
		this.myDb = db;
		
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		cancelQuery() ;
		nofifySignedUser() ;
		removeSubcriteEvent();

	}
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return <code>String</code> Object which is the query
	 */
	public boolean cancelQuery()  {
		
		boolean returnValue = false ;
		
		try {
			int  eventId = jsonInfo.getInt("eventId") ;
			myDb.statement().executeUpdate("update event set status = 'cancelled' " +
					"where eventid = "+ eventId);
			returnValue = true ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		// TODO parse query
		return returnValue ;
		
	}
	/**
	 * Method to remove subscripted event 
	 * @param info give the eventId
	 * @return true if event removed well else false 
	 */
	public boolean removeSubcriteEvent() {
		//TODO perform remove query
		
		boolean returnValue = false ;
		 try {
			 int  eventId = jsonInfo.getInt("eventId") ;
			 myDb.statement().executeUpdate("delete from subsEventSigned where eventid = "+ eventId );
			 myDb.statement().executeUpdate("delete from subsEventGeneral where eventid = " + eventId );
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		 return returnValue ;
	}
	
	/**
     *  This method provides the email list of all signed users subscripted in the cancelled event.
     *  Retrieves the current result as a <code>String</code> object.
	 * @param idEvent eventId of cancelled event
	 * @return rs <code>ResultSet</code> Object of the query .
	 */
	private boolean nofifySignedUser(){
		
		boolean returnValue = false ;
		
		try {
			int  eventId = jsonInfo.getInt("eventId") ;
			result_ = myDb.statement().executeQuery("select suserId from  subsEventSigned " +
													"where 	eventid = "+ eventId);
			returnValue = true ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
		}
		return returnValue;
		
	}
		
	/**
	 * object DataBase 
	 */
	private DataBase myDb ;

	
	
}
