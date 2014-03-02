/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class close an user's session
 * @author william
 *
 */
public class CloseSession extends AbstractCommand{
	/**
	 * Constructor 
	 * @param a string info to parse 
	 */
	public CloseSession(String info) {
		query_ = buildQuery(info);
		
	}
	
	/**
	 * Parses String from JSON format in order to retrieve parameters
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
