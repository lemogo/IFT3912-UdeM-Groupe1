/**
 * 
 */
package ca.diro.DataBase.Command;

/**
  * this class permit to set query in order to allow a signed user to delete a notification 
 * @author william
 */
public class DeleteNotification extends CommandUpdate{

	/**
	 * Constructor
	 * @param eventId string for query
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public DeleteNotification(String eventId, String userId)  {
		query_ = removeEvent(eventId, userId)  ;

	}
	
	/**
	 * Method to delete  notification 
	 * @param eventId give the eventId
	 * @return str <code>String</code> a String object which is the query 
	 */
	private String removeEvent(String eventId, String userId) {
		//TODO perform remove query
		String str ="";
		str = "delete from subsEventSigned where eventid = "+ eventId +" and suserid = " +userId ;
		return str ;
	}


	
}
