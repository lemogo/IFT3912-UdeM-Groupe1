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
	 * @param userId  
	 */
	public ListEventByUser(String userId) {
		
			query_ = buildQuery(userId);
		
	}
	
	/**
	 * Method to build the right query
	 * @param userId 
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 
	 */
	private String buildQuery(String userId)  {	
		
		String str = "select event.eventid, title, location, dateevent, event.description "
				+ ", event.suserid, username "
				+"from  event "+
				"join signeduser on event.suserid = signeduser.suserid "+
						"where 	event.suserid = "+ userId +" and "+
						" dateevent >= CURRENT_DATE() and " +
						"UPPER(event.status) != 'CANCELLED' ";
		return str;
	}
}
