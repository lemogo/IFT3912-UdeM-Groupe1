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
public class CreateUserAccountEvent extends AbstractCommand {

	/**
	 * Constructor
	 * @param info string 
	 */
	public CreateUserAccountEvent(String info) {
		query_ = buildQuery(info);

	}

	/**
	 * Parse String from JSON format in order to retrieve parameters
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
