/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see the list of all cancelled events 
 * @author william
 *
 */
public class CountUserNotification extends  Command{	
	/**
	 * Constructor 
	 * @param userId 
	 */
	public CountUserNotification(String userId) {
		this.query_ = buildQuery(userId) ;
	}
	
	/**
	 * Method to define query
	 * @param userId 
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(String userId){
		String str="select count(subsEventSigned.eventid) "
				+ " from subsEventSigned "
				+ "join event on event.eventid=subsEventSigned.eventid "
//				+ " join signeduser on event.suserid=signeduser.suserid " 
				+ " where dateevent >= CURRENT_DATE() AND "
				+ "UPPER(status) = 'CANCELLED' AND"+" subsEventSigned.suserid = " +userId  ;
		//TODO parse query
		return str ;
	}
	

}
