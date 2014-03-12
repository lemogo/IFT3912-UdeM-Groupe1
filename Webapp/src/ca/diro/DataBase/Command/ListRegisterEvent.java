/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see all event where a signed user is subscripted 
 * @author william
 *
 */
public class ListRegisterEvent extends Command{

	/**
	 * @param info String to build query
	 */
	public ListRegisterEvent(String userId) {
		query_ = buildQuery(userId);	
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String info) {
		
		int  S_userId = Integer.parseInt(info);
		String str = "select event.eventid, title, location, dateevent, event.description from  event, subsEventSigned " +
				"where 	event.suserid = subsEventSigned.suserid and " +
						"subsEventSigned.suserid = "+ S_userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		
		// TODO parse query
		return str;
	}
}
