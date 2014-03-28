
package ca.diro.DataBase.Command;

/**
 * This class implement a command  allowing anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class SubscriteToEvent extends CommandUpdate {

	/**
	 * Constructor 
	 * @param userId String 
	 * @param select flag to select whether signed user or anonymous
	 */
	public SubscriteToEvent(String userId , String eventId, boolean select)  {
		query_ = buildQuery(userId, eventId, select);	
	}
	
	/**
	 * Method to handle subscription in event by anonymous user 
	 * @param userId 
	 * @param eventId
	 * @param select 
	 * @return <code>String</code> Object to give the query required
	 */
	public String buildQuery(String userId, String eventId, boolean select){
		
			String str1 = " insert into subsEventGeneral (eventid) values("+ eventId +")" ; 
				
			String str2 = "insert into subsEventSigned (eventid, suserid) values("+ eventId + ", "+ userId +")" ;
			if(select){
				return str2 ;
			}
			else{
				return str1 ;
			}
		
	}
	
	
}
