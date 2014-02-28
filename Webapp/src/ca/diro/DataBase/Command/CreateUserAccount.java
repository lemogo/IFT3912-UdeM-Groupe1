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
public class CreateUserAccount extends AbstractCommand {

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
