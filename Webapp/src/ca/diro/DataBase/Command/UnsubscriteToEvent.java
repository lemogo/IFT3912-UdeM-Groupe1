
package ca.diro.DataBase.Command;

/**
 * This class implement a command  allowing anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class UnsubscriteToEvent extends CommandUpdate {

	/**
	 * Constructor 
	 * @param userId String 
	 * @param select flag to select whether signed user or anonymous
	 */
	public UnsubscriteToEvent(String userId , String eventId)  {
		query_ = buildQuery(userId, eventId);	
	}
	
	/**
	 * Method to handle subscription in event by anonymous user 
	 * @param userId 
	 * @param eventId
	 * @param select 
	 * @return <code>String</code> Object to give the query required
	 */
	public String buildQuery(String userId, String eventId){
			String str2 = "delete from subsEventSigned where eventid = "+ eventId + " and suserid = "+ userId ;
				return str2 ;
	}
	
	
}
