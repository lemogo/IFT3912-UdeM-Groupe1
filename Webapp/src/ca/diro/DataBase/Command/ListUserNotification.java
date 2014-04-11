/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see the list of all cancelled events 
 * @author william
 *
 */
public class ListUserNotification extends  Command{	
	/**
	 * Constructor 
	 * @param userId 
	 */
	public ListUserNotification(String userId) {
		this.query_ = buildQuery(userId) ;
	}
	
	/**
	 * Method to define query
	 * @param userId 
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(String userId){
		String str="select event.eventid, event.suserid, username, event.title, event.location, event.dateevent, event.description from event "
				+ "join subsEventSigned on event.eventid=subsEventSigned.eventid"
				+ " join signeduser on event.suserid=signeduser.suserid " +
				" where dateevent >= CURRENT_DATE() AND UPPER(status) = 'CANCELLED' AND"+" subsEventSigned.suserid = " +userId  ;
		
		return str ;
	}
	

}
