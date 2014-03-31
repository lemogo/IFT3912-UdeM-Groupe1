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
		String str="select eventid, title, location, dateevent, description from event " +
				"where dateevent >= CURRENT_DATE() AND UPPER(status) = 'CANCELLED' AND"+" suserid = " +userId  ;
		//TODO parse query
		return str ;
	}
	

}
