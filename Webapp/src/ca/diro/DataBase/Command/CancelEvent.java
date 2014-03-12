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
public class CancelEvent extends Command {

	/**
	 * Constructor
	 * 
	 * @param info string to build query
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CancelEvent(String eventId) throws ClassNotFoundException, SQLException {

	}
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return <code>String</code> Object which is the query
	 */
	public boolean cancelQuery(String info)  {
		
		//boolean returnValue= false ;
		String  eventId = info;	
		String str = "update event set status = 'cancelled' " +
					"where eventid = "+"'"+ eventId +"'";
		try {
			myDb.statement().executeUpdate(str);
			return true ;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// TODO parse query
		return false ;
		
	}
	/**
	 * Method to remove subscripted event 
	 * @param info give the eventId
	 * @return true if event removed well else false 
	 */
	public boolean removeSubcriteEvent(String info) {
		//TODO perform remove query
		int  eventId = Integer.parseInt(info);
		boolean returnValue = false ;
		 try {
			myDb.statement().executeUpdate("delete from subsEventSigned where eventid = "+"'"+eventId +"'");
			myDb.statement().executeUpdate("delete from subsEventGeneral where eventid = "+"'"+eventId +"'");
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		 return returnValue ;
	}
	
	/**
     *  This method provides the email list of all signed users subscripted in the cancelled event.
     *  Retrieves the current result as a <code>String</code> object.
	 * @param idEvent eventId of cancelled event
	 * @return true if good connection else false.
	 */
	public boolean nofifySignedUser(String idEvent){
		
		boolean returnValue = false;
		String eventId = "1";
		String str =  "select signeduser.suserId, signeduser.email, event.title from  signeduser , subsEventSigned, event " +
						"where 	event.eventid = "+ eventId +" and " +
								"signeduser.suserid = subsEventSigned.suserid and " +
								"event.eventid = subsEventSigned.eventid";
		
		try {
			notifyForSigned = myDb.statement().executeQuery(str);
			returnValue = true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return returnValue;
		
	}
	
	/**
     *  This method provides the email list of all anonymous  users subscripted in the cancelled event.
     *  Retrieves the current result as a <code>String</code> object.
	 * @param idEvent eventId of cancelled event
	 * @return true if good connection else false .
	 */
	public boolean nofifyAnonymUser(String idEvent){
		
		boolean returnValue = false;
		String eventId = "1";
		String str =  "select generaluser.email,  event.title from  generaluser , subsEventgeneral, event " +
				"where 	event.eventid = "+ eventId +" and " +
				"generaluser.email = subsEventGeneral.email and " +
				"event.eventid = subsEventGeneral.eventid ";
		try {
			notifyForSigned = myDb.statement().executeQuery(str);
			returnValue = true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return returnValue;
	}
	
	
	/**
	 * @return notifyForSigned a <code>ResultSet</code> Object giving list of 
	 * signed users who have to be notify after a cancelled event 
	 */
	public ResultSet getNotifyForSigned() {
		return notifyForSigned;
	}
	/**
	 * @return notifyForAnonym  a <code>ResultSet</code> Object giving list 
	 * of anonymous users who have to be notify after a cancelled event
	 */
	public ResultSet getNotifyForAnonym() {
		return notifyForAnonym;
	}
	
	/**
	 * object DataBase 
	 */
	private DataBase myDb = new DataBase() ;
	/**
	 * notifyForSigned a <code>ResultSet</code> Object giving list 
	 * of signed users who have to be notify after a cancelled event 
	 */
	private ResultSet notifyForSigned = null ;
	
	/**
	 * notifyForAnonym  a <code>ResultSet</code> Object giving list of 
	 * anonymous users who have to be notify after a cancelled event 
	 */
	private ResultSet notifyForAnonym = null ;
}
