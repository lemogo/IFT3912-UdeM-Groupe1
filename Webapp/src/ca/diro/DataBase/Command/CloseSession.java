/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this class close an user's session
 * @author william
 *
 */
public class CloseSession extends Command{
	/**
	 * Constructor 
	 * @param a string info to parse 
	 */
	public CloseSession(String info) {
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
	private String buildQuery(String info) {
		String str = "";
		// TODO parse query
		return str;
	}

}
