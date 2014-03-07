/**
 * 
 */
package ca.diro.DataBase.Command;

/**
 * this command class give information on a user that can be use to generate user's own page
 * @author william
 *
 */
public class PageInfoUser extends Command{
	/**
	 * Constructor
	 * @param info String to build query
	 */
	public PageInfoUser(String info) {
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
