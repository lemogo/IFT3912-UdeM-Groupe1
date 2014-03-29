/**
 * 
 */
package ca.diro.DataBase.Command;


/**
 * Command to verify that a user is registered to an event
 * @author William, lionnel
 *
 */
public class VerifyUserRegisterToEvent extends Command{

	/**
	 * @param userId String to build query
	 * @param eventid String to build query 
	 */
	public VerifyUserRegisterToEvent(String userId, String eventid) {
			query_ = buildQuery(userId, eventid);
	}
	
	/**
	 * Method to build the right query
	 * @param userId 
	 * @param eventid 
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String userId, String eventid)  {
		
		String str = "select * from  subsEventSigned  "+
					"where eventid = "+eventid+" and " +
//					" CURRENT_DATE() <= dateevent and " +
//					"UPPER(event.status) not = 'CANCELLED' and "+
					"suserid = "+ userId ;
 
		return str;
	}
}
