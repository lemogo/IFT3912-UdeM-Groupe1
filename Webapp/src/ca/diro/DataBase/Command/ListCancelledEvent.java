/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to see the list of all cancelled events 
 * @author william
 *
 */
public class ListCancelledEvent extends  Command{	
	/**
	 * Constructor 
	 */
	public ListCancelledEvent() {
		this.query_ = buildQuery() ;
	}
	
	/**
	 * Method to define query
	 * @return str <code>String</code> Object the query
	 */
	private String buildQuery(){
		String str="select eventid, title, location, dateevent, description from event " +
				"where dateevent >= CURRENT_DATE() AND UPPER(status) = 'CANCELLED'"  ;
		//TODO parse query
		return str ;
	}
	

}
