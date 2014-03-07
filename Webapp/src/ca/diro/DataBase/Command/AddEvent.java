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
public class AddEvent extends Command {
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
	 * @param info String Object
	 * @return <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
