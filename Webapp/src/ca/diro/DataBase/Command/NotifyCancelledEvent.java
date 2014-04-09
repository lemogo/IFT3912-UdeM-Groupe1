/**
 * 
 */
package ca.diro.DataBase.Command;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
 * this class make command to notify users subscripted in an event when  cancelled 
 * @author william
 *
 */
public class NotifyCancelledEvent extends Command{
	
	/**
	 * Constructor 
	 * @param eventId Sting to build query with
	 * @param db DataBase Object
	 */
	public NotifyCancelledEvent(String eventId, DataBase db) {
		query_ = buildQuery(eventId);
		myDb = db ;
		
	}
	
/**
	 * Method to build the right query
	 * @param info String eventId
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String eventId) {
		String str = "select suserId from  subsEventSigned " +
							"where 	eventid = "+ eventId;
		return str;
	}
	
	/**
	 * Method to verify notification status
	 * @param eventId
	 * @param userId
	 * @return true if already notify else false
	 */
	public boolean verifyNotification(String eventId, String userId){
		
		ResultSet rs = null;
		String value = "" ;
		String str = "select notification from subsEventSigned " +
				"where eventid = "+eventId + " AND  suserid = " +userId ;
		//+ "and  UPPER(notification) = 'ALREADY'"  ;
	
		try {
			 rs = myDb.statement().executeQuery(str);
			 rs.next();
			 value = rs.getString("notification");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return value.toUpperCase().equals("already".toUpperCase()) ;
		
		
	}
	
	/**
	 * Method to set notification status 
	 * @param eventId
	 * @param userId
	 * @return true execution run well false else  
	 */
	public boolean setNotification(String eventId, String userId){
		boolean returnValue = false ;
		String str = "update subsEventSigned set notification = 'already' " +
				"where eventid = "+eventId + " AND  suserid = " +userId ;
	
		try {
			  myDb.statement().executeUpdate(str);
			 returnValue = true ;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return returnValue ;
	}
	
	/**
	 * object DataBase 
	 */
	private DataBase myDb ;

}
