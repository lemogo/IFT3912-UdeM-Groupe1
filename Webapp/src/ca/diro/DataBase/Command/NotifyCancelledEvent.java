/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class make command to notify users subscripted in an event when  cancelled 
 * @author william
 *
 */
public class NotifyCancelledEvent extends Command{
	
	/**
	 * Constructor 
	 * @param info Sting to build query with
	 */
	public NotifyCancelledEvent(String info) {
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
