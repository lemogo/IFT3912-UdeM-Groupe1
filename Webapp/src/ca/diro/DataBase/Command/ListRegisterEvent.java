/**
 * 
 */
package ca.diro.DataBase.Command;

import org.json.JSONException;

/**
 * Command to see all event where a signed user is subscripted 
 * @author william
 *
 */
public class ListRegisterEvent extends Command{

	/**
	 * @param info String to build query
	 */
	public ListRegisterEvent(String info) {
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
	public String buildQuery() throws JSONException {
		
		int  userId = jsonInfo.getInt("userId") ;
		String str = "select event.eventid, title, location, dateevent, event.description from  event, subsEventSigned " +
				"where 	event.suserid = subsEventSigned.suserid and " +
						"subsEventSigned.suserid = "+ userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		
		// TODO parse query
		return str;
	}
}
