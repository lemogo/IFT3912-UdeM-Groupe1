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
		
			query_ = buildQuery(userId);
		
	}
	
	/**
	 * Method to build the right query
	 * @param userId 
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String userId)  {
			
		String str = "select event.eventid, title, location, dateevent, event.description "
				 +", event.suserid, username "
				 +"from  event, subsEventSigned  "+
				"join signeduser on event.suserid = signeduser.suserid "+
					"where event.eventid = subsEventSigned.eventid and " +
					" CURRENT_DATE() <= dateevent and " +
					"UPPER(event.status) not = 'CANCELLED' and "+
					"subsEventSigned.suserid = "+ userId ;
 
		return str;
	}
}
