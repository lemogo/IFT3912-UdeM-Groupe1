/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * This class implement a command  allowing anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class SubscribeToEvent extends AbstractCommand{

	/**
	 * Constructor 
	 * @param info string to buils query
	 */
	public SubscribeToEvent(String info) {
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
