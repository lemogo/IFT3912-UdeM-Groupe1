/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class make command to notify users subscripted in an event when  cancelled 
 * @author william
 *
 */
public class NotifyCancelledEvent extends AbstractCommand{
	
	/**
	 * Constructor 
	 * @param info sting to build query with
	 */
	public NotifyCancelledEvent(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * 
	 * @param info
	 *            string to build query
	 * @return a string that is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
