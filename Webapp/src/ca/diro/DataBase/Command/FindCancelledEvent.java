/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see the list of all cancelled events 
 * @author william
 *
 */
public class FindCancelledEvent extends  Command{	
	/**
	 * Constructor 
	 */
	public FindCancelledEvent(String eventID) {
		this.query_ = buildQuery(eventID) ;
	}
	
	/**
	 * Method to define query
	 * @param eventID 
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(String eventID){
		String str="select eventid, title, location, dateevent, description from event " +
				"where (dateevent <= CURRENT_DATE() or UPPER(status) = 'CANCELLED') and eventid = '"+eventID+"'"  ;
		
		return str ;
	}
	

}
