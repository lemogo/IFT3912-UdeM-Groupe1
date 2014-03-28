/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * this class permit to set query in order to allow a signed user to cancel
 * event
 * 
 * @author william
 */
public class CancelEvent extends CommandUpdate {

	/**
	 * Constructor
	 * 
	 * @param eventId string giving the event's Id
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CancelEvent(String eventId,DataBase db)  {
		this.myDb = db;
		query_ = cancelQuery(eventId) ;
		//nofifySignedUser(eventId) ;
		//removeSubcriteEvent(eventId);

	}
	

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return <code>String</code> Object which is the query
	 */

	private  String  cancelQuery(String eventId)  {
		String str = "" ;
		str = "update event set status = 'cancelled' " +
				"where eventid = "+ eventId ;
		
		return str;
	}
	/**
	 * Method to remove subscripted event 
	 * @param info give the eventId
	 * @return true if event removed well else false 
	 */
	
	public boolean removeSubcriteEvent(String eventId)  {
		boolean returnValue = false ;
		try {
			myDb.statement().executeUpdate("delete from subsEventSigned where eventid = "+ eventId );
			myDb.statement().executeUpdate("delete from subsEventGeneral where eventid = " + eventId );
			returnValue = true ;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
     *  This method provides the iduser list of all signed users subscripted in the cancelled event to notify them.
     *	Retrieves the current result as a <code>String</code> object.
	 * @param idEvent eventId of cancelled event
	 * @return rs <code>ResultSet</code> Object of the query .
	 */

	public boolean nofifySignedUser(String eventId)  {
		
		boolean returnValue = false ;
		try {
			ListToNotify = myDb.statement().executeQuery("select suserId from  subsEventSigned " +
															"where 	eventid = "+ eventId);
			returnValue = true ;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return returnValue ;
	}
	
	
	/**
	 * @return ListToNotify <code>ResultSet</code> Object the 
	 * list of all user to notify 
	 */
	public ResultSet getListToNotify() {
		return ListToNotify;
	}
		
	/**
	 * object DataBase 
	 */
	private DataBase myDb ;
	
	/**
	 * Liste of users to notify 
	 */
	ResultSet ListToNotify = null  ;

	

	
	
}
