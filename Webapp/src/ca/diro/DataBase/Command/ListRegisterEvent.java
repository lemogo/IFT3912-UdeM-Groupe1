/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * Command to see all event where a signed user is subscripted 
 * @author William
 *
 */
public class ListRegisterEvent extends Command{

	/**
	 * @param userId String to build query
	 */
	public ListRegisterEvent(String userId) {
		
			query_ = buildQuery(null);
		
	}
	
	/**
	 * Method to build the right query
	 * @param userId 
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String userId)  {
			
		String str = "select event.eventid, title, location, dateevent, event.description from  event, subsEventSigned " +
				"where 	event.suserid = subsEventSigned.suserid and " +
						"subsEventSigned.suserid = "+ userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		
		// TODO parse query
		return str;
	}
}
