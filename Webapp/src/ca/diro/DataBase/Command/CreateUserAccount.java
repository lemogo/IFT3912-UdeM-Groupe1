/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * Command to create User Account using data from user
 * 
 * @author william
 * 
 */
public class CreateUserAccount extends Command {

	/**
	 * Constructor
	 * @param info string 
	 */
	public CreateUserAccount(String info) {
		query_ = buildQuery(info);

	}

	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	public String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
