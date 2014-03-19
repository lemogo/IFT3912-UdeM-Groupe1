/**
 * 
 */
package ca.diro.DataBase.Command;

import org.json.JSONException;

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
	public ListEventByUser(String info) {
		try {
			jsonInfo = parseToJson(info);
			query_ = buildQuery();
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 * @throws JSONException 
	 */
	private String buildQuery() throws JSONException {
		
		int userId = jsonInfo.getInt("userId") ;
		String str = "select event.eventid, title, location, dateevent, event.description from  event " +
				"where 	event.suserid = "+ userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		// TODO parse query
		return str;
	}
}
