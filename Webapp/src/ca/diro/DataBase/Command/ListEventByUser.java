/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class implement command to view all events that one user has created
 * @author william
 *
 */
public class ListEventByUser extends Command{
	/**
	 * Constructor 
	 * @param info String to build query 
	 */
	public ListEventByUser(String suserId) {
		query_ = buildQuery(suserId);
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {

		int  S_userId = Integer.parseInt(info);
		String str = "select event.eventid, title, location, dateevent, event.description from  event " +
				"where 	event.suserid = "+ S_userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		// TODO parse query
		return str;
	}
}
