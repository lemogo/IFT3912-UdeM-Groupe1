/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this command allow signed user to add an event in his account
 * 
 * @author william
 * 
 */
public class AddEvent extends AbstractCommand {
	/**
	 * Constructor
	 * 
	 * @param a string info to parse
	 */
	public AddEvent(String info) {
		query_ = buildQuery(info);

	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * 
	 * @param info
	 *            string type
	 * @return a string that is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
