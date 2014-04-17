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
		query_ = setAlreadyNotification(eventId, userId)  ;

	}
	
	/**
	 * Method to delete  notification 
	 * @param eventId give the eventId
	 * @return str <code>String</code> a String object which is the query 
	 */
	private String setAlreadyNotification(String eventId, String userId) {
		//TODO perform remove query
		String str = "" ;
				str = "update subsEventSigned set notification = 'already' " +
						"where eventid = "+eventId + " AND  suserid = " +userId  ;
				return str ;
	}


	
}
