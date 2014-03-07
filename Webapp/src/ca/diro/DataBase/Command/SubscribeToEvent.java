/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * This class implement a command  allowing anonymous user to subscribe in one  event 
 * @author william
 *
 */
public class SubscribeToEvent extends Command{

	/**
	 * Constructor 
	 * @param info String to build query
	 */
	public SubscribeToEvent(String info) {
		query_ = buildQuery(info);
		
	}
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}
}
