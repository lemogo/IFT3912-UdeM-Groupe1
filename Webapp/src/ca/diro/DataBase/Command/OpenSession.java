/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this this make command permitting user to open a session
 * @author william
 *
 */
public class OpenSession  extends AbstractCommand{

	/**
	 * @param sessionInfo string to buid query with
	 */
	public OpenSession(String sessionInfo) {
		query_ = buildQuery(sessionInfo);
		
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
